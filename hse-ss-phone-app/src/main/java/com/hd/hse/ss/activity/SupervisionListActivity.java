package com.hd.hse.ss.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.ss.R;
import com.hd.hse.ss.adapter.SupervisionListAdapter;
import com.hd.hse.ss.adapter.SupervisionListAdapter.OnNetItemClickListener;
import com.hd.hse.ss.fragment.SupervisionListUpLoadedFragment;
import com.hd.hse.ss.fragment.SupervisionListWaitUpFragment;
import com.hd.hse.ss.fragment.SupervisionListWaitUpFragment.Listener;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 现场监督列表界面 Created by liuyang on 2016年9月26日
 */
public class SupervisionListActivity extends BaseFrameActivity implements
        OnClickListener, IEventListener, OnNetItemClickListener, Listener {
    private static Logger logger = LogUtils
            .getLogger(SupervisionListActivity.class);
    public static final int REQUESTCODE = 0;
    public static final int SUCESSCODE = 1;
    public static final int ERRORCODE = 2;
    /**
     * 现场监督列表数据 Created by liuyang on 2016年9月27日
     */
    private List<WorkLogEntry> mDatas;
    private List<WorkLogEntry> temp;
    private List<WorkLogEntry> uploadedData;

    /**
     * 数据
     */
    private SupervisionListAdapter mSupervisionListAdapter;
    private SupervisionListAdapter uploadedSupervisionListAdapter;
    /**
     * 添加新条目按钮
     */
    private ImageView imgAdd;
    /**
     * 勾选状态时，最下面的区域
     */
    private LinearLayout llBottom;
    /**
     * 删除按钮
     */
    private Button btDele;
    /**
     * 上传按钮
     */
    private Button btUpload;
    /**
     * 未上传按钮
     */
    private Button btWaitUp;
    /**
     * 已上传按钮
     */
    private Button btUpLoaded;

    private Fragment currentFragment;
    private SupervisionListUpLoadedFragment fragUpLoaded;
    private SupervisionListWaitUpFragment fragWaitUp;


    /**
     * 本地数据（未上传）
     */
    // private TextView tvLocation;

    /**
     * 后台数据（已上传）
     */
    // private TextView tvNet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_ss_phone_app_supervision_list_layout);
        initNormalActionbar();
        initView();
        initFragment();
        setSearchHint("属地单位/问题描述");
    }

    private void initFragment() {
        fragUpLoaded = new SupervisionListUpLoadedFragment();
        fragWaitUp = new SupervisionListWaitUpFragment();
        // 默认fragWaitUp
        currentFragment = fragWaitUp;
        getFragmentManager().beginTransaction()
                .add(R.id.hse_ss_phone_app_supervision_list_frame, fragWaitUp)
                .commit();
        btWaitUp.setTextColor(getResources().getColor(R.color.blue));
        btUpLoaded.setTextColor(Color.BLACK);
    }

    private void switchFragment(Fragment fragment) {
        if (!currentFragment.equals(fragment)) {
            if (!fragment.isAdded()) {
                getFragmentManager()
                        .beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.hse_ss_phone_app_supervision_list_frame,
                                fragment).commitAllowingStateLoss();
            } else {
                getFragmentManager().beginTransaction()

                        .hide(currentFragment).show(fragment).commitAllowingStateLoss();
            }
        }
        currentFragment = fragment;
    }

    private void initView() {

        imgAdd = (ImageView) findViewById(R.id.hse_ss_phone_app_supervision_list_img_add);
        llBottom = (LinearLayout) findViewById(R.id.hse_ss_phone_app_supervision_list_li_bottom);
        btDele = (Button) findViewById(R.id.hse_ss_phone_app_supervision_list_bt_dele);
        btUpload = (Button) findViewById(R.id.hse_ss_phone_app_supervision_list_bt_upload);
        btWaitUp = (Button) findViewById(R.id.hse_ss_phone_app_supervision_list_bt_wait_up);
        btUpLoaded = (Button) findViewById(R.id.hse_ss_phone_app_supervision_list_bt_up_loaded);

        imgAdd.setOnClickListener(this);
        btDele.setOnClickListener(this);
        btUpload.setOnClickListener(this);
        btWaitUp.setOnClickListener(this);
        btUpLoaded.setOnClickListener(this);
    }

    /**
     * 正常状态下的Actionbar
     */
    private void initNormalActionbar() {
        // 初始化ActionBar
        setCustomActionBar(true, this, new String[]{IActionBar.IV_LMORE,
                IActionBar.TV_TITLE, IActionBar.BT_SELECT,
                IActionBar.IBTN_SEARCH});
        setNavContent(getNavContentData(), "hse-ss-phone-app");
        // 设置导航栏标题
        setActionBartitleContent("现场监督", false);
    }

    /**
     * 标题栏右上角没有选择
     */
    private void noSelectActionbar() {
        // 初始化ActionBar
        setCustomActionBar(true, this, new String[]{IActionBar.IV_LMORE,
                IActionBar.TV_TITLE, IActionBar.IBTN_SEARCH});
        setNavContent(getNavContentData(), "hse-ss-phone-app");
        // 设置导航栏标题
        setActionBartitleContent("现场监督", false);
    }

    /**
     * 勾选状态下的Actionbar
     */
    private void initSelectActionbar() {
        // 初始化ActionBar
        setCustomActionBar(true, this, new String[]{IActionBar.IV_LMORE,
                IActionBar.TV_TITLE, IActionBar.BT_CANCLE});
        setNavContent(getNavContentData(), "hse-ss-phone-app");
        // 设置导航栏标题
        setActionBartitleContent("请选择操作", false);

    }

    /**
     * 改变listview为勾选状态
     */
    private void changeLvStateToSelect() {

        if (fragWaitUp != null) {
            llBottom.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.GONE);
            fragWaitUp.changeLvStateToSelect();
        }
    }

    /**
     * 改变listview为正常状态
     */
    private void changeLvStateToNormal() {
        if (fragWaitUp != null) {
            llBottom.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
            fragWaitUp.changeLvStateToNormal();
        }
    }

    /**
     * 删除选中条目
     */
    private void deleSelectedItem() {

        if (fragWaitUp != null) {
            fragWaitUp.deleSelectedItem();
        }
    }

    /**
     * 上传选中条目到服务器
     */
    private void uploadSelectedItem() {

        if (fragWaitUp != null) {
            fragWaitUp.uploadSelectedItem();
        }

    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.hse_ss_phone_app_supervision_list_img_add) {
            // 跳转到添加新条目
            Intent i = new Intent(this, SupervisionDetailActivity.class);
            startActivity(i);

        } else if (id == R.id.hse_ss_phone_app_supervision_list_bt_dele) {
            // 删除选中条目
            deleSelectedItem();

        } else if (id == R.id.hse_ss_phone_app_supervision_list_bt_upload) {
            // 上传选中条目
            uploadSelectedItem();

        } else if (id == R.id.hse_ss_phone_app_supervision_list_bt_wait_up) {
            // 未上传
            switchFragment(fragWaitUp);
            btWaitUp.setTextColor(getResources().getColor(R.color.blue));
            fragWaitUp.changeLvStateToNormal();
            btUpLoaded.setTextColor(Color.BLACK);
            initNormalActionbar();
            llBottom.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
        } else if (id == R.id.hse_ss_phone_app_supervision_list_bt_up_loaded) {
            // 已上传
            btUpLoaded.setTextColor(getResources().getColor(R.color.blue));
            btWaitUp.setTextColor(Color.BLACK);
            switchFragment(fragUpLoaded);
            fragUpLoaded.cancelSearch();
            noSelectActionbar();
            llBottom.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        if (eventType == IEventType.ACTIONBAR_BT_SELECT_CLICK) {
            // 点击选择按钮
            initSelectActionbar();
            changeLvStateToSelect();

        } else if (eventType == IEventType.ACTIONBAR_BT_CANCLE_CLICK) {
            // 点击取消按钮
            initNormalActionbar();
            changeLvStateToNormal();
        } else if (IEventType.ACTIONBAR_CALNEL_CLICK == eventType) {
            // 取消搜索
            if (currentFragment.equals(fragWaitUp)) {
                fragWaitUp.cancelSearch();
            } else {
                fragUpLoaded.cancelSearch();
            }
        } else if (eventType == IEventType.ACTIONBAR_SEARCH_CLICK) {
            // 取消搜索
            if (currentFragment.equals(fragWaitUp)) {
                fragWaitUp.search((String) objects[0]);
            } else {
                fragUpLoaded.search((String) objects[0]);
            }
        }

    }

    /**
     * getNavContentData:(获取导航数据). <br/>
     * date: 2015年2月6日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<AppModule> getNavContentData() {
        return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
    }

    @Override
    protected void onResume() {
        // 更新数据
        // initData();
        if (currentFragment.equals(fragWaitUp)) {
            initNormalActionbar();
        } else {
            noSelectActionbar();
        }
        changeLvStateToNormal();
        super.onResume();
    }

    @Override
    public void onNetItemClick(int postion) {

        Intent intent = new Intent(SupervisionListActivity.this,
                SupervisionDetailActivity.class);
        intent.putExtra(SupervisionDetailActivity.ISSAVE, false);
        intent.putExtra(SupervisionDetailActivity.UD_CBSGL_RZID,
                uploadedData.get(postion));
        startActivity(intent);

    }

    @Override
    public void OnDeleSucess() {
        initNormalActionbar();
        llBottom.setVisibility(View.GONE);
        imgAdd.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnUpSucess() {
        initNormalActionbar();
        llBottom.setVisibility(View.GONE);
        imgAdd.setVisibility(View.VISIBLE);
        // 刷新已上传列表
        flashUpList();
    }

    @Override
    public void OnNoItem() {

    }

    public List<WorkLogEntry> getwaitUpDatas() {
        List<WorkLogEntry> waitUpDatas = null;
        if (fragWaitUp != null) {
            waitUpDatas=fragWaitUp.getwaitUpDatas();
        }
        return waitUpDatas;
    }

    /**
     * 刷新已上传列表
     */
    private void flashUpList() {
        if (fragUpLoaded != null) {
            fragUpLoaded.flashUpList();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            /* 隐藏软键盘 */
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        }
        return super.dispatchKeyEvent(event);
    }

}
