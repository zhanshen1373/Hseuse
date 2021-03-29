package com.hd.hse.common.component.phone.custom;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.drawerarrow.MaterialMenuDrawable;
import com.hd.hse.common.component.phone.drawerarrow.MaterialMenuView;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.system.SystemProperty;

import java.util.HashMap;

import static android.view.Gravity.START;

/**
 * ClassName: HSEActionBar ()<br/>
 * date: 2014年12月24日 <br/>
 *
 * @author wenlin
 */
@SuppressLint("NewApi")
public class HSEActionBar {

    private Activity activity;
    public IEventListener eventListener;
    public String[] controlsVisible;
    private View actionBar;
    private LayoutInflater inflater;
    private DrawerLayout mDrawerLayout;
    HashMap<String, View> controlMap;
    private ListView mDrawerList;
    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;
    /**
     * ibtnBack:TODO(左侧返回).
     */
    public ImageButton ibtnBack;
    /**
     * tvSubTitle:TODO(副标题).
     */
    public TextView tvSubTitle;
    public ImageButton ibtnLMore;
    public TextView tvTitle;
    public ImageButton ibtnUpload;
    public ImageButton ibtnDown;
    public ImageButton ibtnRMore;
    public ImageView imgRemind;
    public MaterialMenuView imageView;
    public ImageButton ibtnLevelTwoMore;
    public ImageButton ibtnDelete;
    public ImageButton ibtnSearch;
    public AutoSearchEditText etSearch;
    private MyAlertDialog mAlertDialog;
    private SuperEntity superEntity;
    private TextView tvCanel;
    private TextView tvJoin;
    private TextView tvJoinOK;
    private TextView tvJoinCancel;
    private boolean isFlag;
    private boolean direction;
    private boolean needConfirm; // 退出界面的时候是否需要确认
    private TextView zyplldx;
    private TextView zypllzyfh;
    private ImageView dlipi;
    private TextView dlipu;
    private TextView dlipn;
    private ImageView dlipci;
    private TextView dlipd;


    /**
     * mIconSearchClear:TODO(清除图标).
     */
    private Drawable mIconSearchClear;

    /**
     * mIconSearch:TODO(搜索图标).
     */
    private Drawable mIconSearch;

    /**
     * mIconSearchDetail:搜索优化
     */
    private Drawable mIconSearchDetail;

    /**
     * isIconTip:TODO(主标题可下拉点击图标标示).
     */
    private boolean isIconTip = false;
    // 筛选
    private TextView tvSelect;
    /**
     * 选择
     */
    private Button btSelect;
    /**
     * 取消
     */
    private Button btCancle;
    /**
     * 保存
     */
    private Button btSave;
    /**
     * 上传
     */
    private Button btUp;

    private String hint;

    public HSEActionBar(Activity activity, String[] controlsVisible) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        initActionBar(controlsVisible);
    }

    public HSEActionBar(Activity activity, IEventListener eventListener,
                        String[] controlsVisible) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.eventListener = eventListener;
        this.inflater = LayoutInflater.from(activity);
        initActionBar(controlsVisible);
    }

    public HSEActionBar(Activity activity, IEventListener eventListener,
                        String[] controlsVisible, boolean needConfirm) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.eventListener = eventListener;
        this.inflater = LayoutInflater.from(activity);
        this.needConfirm = needConfirm;
        initActionBar(controlsVisible);
    }

    /**
     * Creates a new instance of HSEActionBar.
     *
     * @param activity
     * @param eventListener
     * @param controlsVisible
     * @param mDrawerLayout   应用界面的布局
     * @param mDrawerList     应用界面的ListView
     */
    public HSEActionBar(Activity activity, IEventListener eventListener,
                        String[] controlsVisible, DrawerLayout mDrawerLayout,
                        ListView mDrawerList) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.eventListener = eventListener;
        this.inflater = LayoutInflater.from(activity);
        this.mDrawerLayout = mDrawerLayout;
        this.mDrawerList = mDrawerList;
        initActionBar(controlsVisible);
        initDrawer();
    }

    /**
     * initActionBar:(). <br/>
     * date: 2014年12月24日 <br/>
     *
     * @author wenlin
     */
    private void initActionBar(String[] controlsVisible) {
        // TODO Auto-generated method stub
        this.controlsVisible = controlsVisible;
        actionBar = inflater.inflate(
                R.layout.hd_hse_common_component_phone_actionbar, null, false);

        mIconSearchClear = activity.getResources().getDrawable(
                R.drawable.hd_hse_commom_component_phone_etsearch_clear_it);
        mIconSearch = activity.getResources().getDrawable(
                R.drawable.hd_hse_common_component_phone_icon_search_sel);
        mIconSearchDetail = activity.getResources().getDrawable(
                R.drawable.shaixuan);

        OnClickListener clickLst = new OnClickLstner();

        initControl(clickLst);

        setControlVisible(controlsVisible);

        initActivityActionBar();
    }


    private void initTime() {
        dlipd.setText(ServerDateManager.getYear() + " - "
                + ServerDateManager.getMonth()
                + " - " + ServerDateManager.getDay()
                + " " + ServerDateManager.getWeek());
    }


    /**
     * initControl:(初始化控件). <br/>
     * date: 2014年12月24日 <br/>
     *
     * @param clickLst
     * @author wenlin
     */
    private void initControl(OnClickListener clickLst) {
        // TODO Auto-generated method stub


        dlipi = (ImageView) actionBar.findViewById(R.id.dlipi);
        dlipu = (TextView) actionBar.findViewById(R.id.dlipu);
        dlipn = (TextView) actionBar.findViewById(R.id.dlipn);
        PersonCard loginPerson =
                SystemProperty.getSystemProperty().getLoginPerson();
        String personid_desc = null;
        String department_desc = null;
        if (loginPerson != null) {
            personid_desc = loginPerson.getPersonid_desc();
            department_desc = loginPerson.getDepartment_desc();
        }
        if (department_desc.length() > 4) {
            department_desc = department_desc.substring(0, 4) + "\n" +
                    department_desc.substring(4, department_desc.length());
        }
        if (personid_desc.length() > 3) {
            personid_desc = personid_desc.substring(0, 3) + "\n" +
                    personid_desc.substring(3, personid_desc.length());
        }
        dlipu.setText(department_desc);
        dlipn.setText(personid_desc);
        dlipci = (ImageView) actionBar.findViewById(R.id.dlipci);
        dlipci.setOnClickListener(clickLst);
        dlipd = (TextView) actionBar.findViewById(R.id.dlipd);
        initTime();
        zyplldx = (TextView) actionBar.findViewById(R.id.zyplldx);
        zyplldx.setOnClickListener(clickLst);
        zypllzyfh = (TextView) actionBar.findViewById(R.id.zypllzyfh);
        zypllzyfh.setOnClickListener(clickLst);
        ibtnBack = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_tv_back);
        ibtnBack.setOnClickListener(clickLst);
        tvSubTitle = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custon_subtitle);
        tvSubTitle.setOnClickListener(clickLst);
        imageView = (MaterialMenuView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_tv_more_left);
        tvTitle = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_title);
        tvTitle.setOnClickListener(clickLst);
        ibtnUpload = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_upload);
        ibtnUpload.setOnClickListener(clickLst);
        ibtnDown = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_dowmload);
        ibtnDown.setOnClickListener(clickLst);
        ibtnRMore = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_tv_more);
        ibtnRMore.setOnClickListener(clickLst);
        imgRemind = (ImageView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_tv_remind);
        ibtnLevelTwoMore = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_more_leveltwo);
        ibtnLevelTwoMore.setOnClickListener(clickLst);
        ibtnDelete = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_delete);
        ibtnDelete.setOnClickListener(clickLst);
        etSearch = (AutoSearchEditText) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_et_search);
        etSearch.get("actionbar_search");
        etSearch.setOnClickListener(clickLst);
        etSearch.setOnKeyListener(etInputKeylistener);
        etSearch.setOnTouchListener(etSearch_OnTouch);
        etSearch.addTextChangedListener(etSearch_TextChanged);

        /**
         * 这段代码可以根据配置
         */
        Drawable drawable_left = activity.getResources().getDrawable(R.drawable.hd_hse_common_component_phone_icon_search_sel);
        Drawable drawable_right = activity.getResources().getDrawable(R.drawable.shaixuan);
        drawable_left.setBounds(0, 0, drawable_left.getMinimumWidth(), drawable_left.getMinimumHeight());
        drawable_right.setBounds(0, 0, drawable_right.getMinimumWidth(), drawable_right.getMinimumHeight());
        etSearch.setCompoundDrawables(drawable_left, null, drawable_right, null);

        if (hint != null && !"".equals(hint)) {
            etSearch.setHint(hint);
        } else {
            etSearch.setHint("类型/名称");
        }
        ibtnSearch = (ImageButton) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_search);
        ibtnSearch.setOnClickListener(clickLst);
        tvCanel = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_tv_canel);
        tvCanel.setOnClickListener(clickLst);
        tvJoin = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_tv_join);
        tvJoin.setOnClickListener(clickLst);
        tvJoinOK = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_tv_join_OK);
        tvJoinOK.setOnClickListener(clickLst);
        tvJoinCancel = (TextView) actionBar
                .findViewById(R.id.hd_hse_common_phone_actionbar_custom_tv_join_cancel);
        tvJoinCancel.setOnClickListener(clickLst);
        tvSelect = (TextView) actionBar
                .findViewById(R.id.hd_hse_statistic_actionbar_tv_select);
        tvSelect.setOnClickListener(clickLst);

        btSelect = (Button) actionBar
                .findViewById(R.id.hd_hse_statistic_actionbar_bt_select);
        btSelect.setOnClickListener(clickLst);

        btCancle = (Button) actionBar
                .findViewById(R.id.hd_hse_statistic_actionbar_bt_cancle);
        btCancle.setOnClickListener(clickLst);

        btSave = (Button) actionBar
                .findViewById(R.id.hd_hse_statistic_actionbar_bt_save);
        btSave.setOnClickListener(clickLst);

        btUp = (Button) actionBar
                .findViewById(R.id.hd_hse_statistic_actionbar_bt_up);
        btUp.setOnClickListener(clickLst);

        controlMap = new HashMap<String, View>() {
            /**
             * serialVersionUID:TODO().
             */
            private static final long serialVersionUID = 1L;

            {
                put(IActionBar.TV_TITLE, tvTitle);
                put(IActionBar.TV_BACK, ibtnBack);
                put(IActionBar.TV_MORE, ibtnRMore);
                put(IActionBar.IMG_REMIND, imgRemind);
                put(IActionBar.IV_LMORE, imageView);
                put(IActionBar.IBTN_UPLOAD, ibtnUpload);
                put(IActionBar.IBTN_DOWNLOAD, ibtnDown);
                put(IActionBar.IBTN_DELETE, ibtnDelete);
                put(IActionBar.IBTN_LEVELTWO_MORE, ibtnLevelTwoMore);
                put(IActionBar.TV_SUBTITLE, tvSubTitle);
                put(IActionBar.ET_SEARCH, etSearch);
                put(IActionBar.IBTN_SEARCH, ibtnSearch);
                put(IActionBar.TV_CANEL, tvCanel);
                put(IActionBar.TV_JOIN, tvJoin);
                put(IActionBar.TV_JOIN_OK, tvJoinOK);
                put(IActionBar.tV_JOIN_CANCEL, tvJoinCancel);
                put(IActionBar.STATISTIC_SELECT, tvSelect);
                put(IActionBar.BT_SELECT, btSelect);
                put(IActionBar.BT_CANCLE, btCancle);
                put(IActionBar.BT_SAVE, btSave);
                put(IActionBar.BT_UP, btUp);
                put(IActionBar.ZYLL_DX, zyplldx);
                put(IActionBar.ZYLL_ZYFH, zypllzyfh);
                put(IActionBar.DALIAN_INITIAL_PAGE_IMAGE, dlipi);
                put(IActionBar.DALIAN_INITIAL_PAGE_UNIT, dlipu);
                put(IActionBar.DALIAN_INITIAL_PAGE_NAME, dlipn);
                put(IActionBar.DALIAN_INITIAL_PAGE_CENTER_IMAGE, dlipci);
                put(IActionBar.DALIAN_INITIAL_PAGE_DATE, dlipd);


            }

        };
    }

    /**
     * initDrawer:(初始化左侧抽屉). <br/>
     * date: 2014年12月24日 <br/>
     *
     * @author wenlin
     */
    private void initDrawer() {
        imageView.setVisibility(View.VISIBLE);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlag) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    isFlag = false;
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    isFlag = true;
                }
            }
        });
        mDrawerLayout
                .setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        imageView
                                .getDrawable()
                                .setTransformationOffset(
                                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                                        direction ? 2 - slideOffset
                                                : slideOffset);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        direction = true;
                        isFlag = true;
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        direction = false;
                        isFlag = false;
                    }
                });
    }

    /**
     * initActivityActionBar:(将布局显示在目标布局上). date: 2014年10月10日
     *
     * @author wenlin
     */
    public void initActivityActionBar() {
        // TODO Auto-generated method stub
        ActionBar activityActionBar = activity.getActionBar();
        ActionBar.LayoutParams params = new LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        activityActionBar.setDisplayShowHomeEnabled(false);
        activityActionBar.setDisplayShowTitleEnabled(false);
        activityActionBar.setDisplayHomeAsUpEnabled(false);
        activityActionBar.setDisplayShowCustomEnabled(true);
        activityActionBar.setCustomView(actionBar, params);
    }

    /**
     * setEventListener:(设置监听). <br/>
     * date: 2015年1月9日 <br/>
     *
     * @param eventListener
     * @author wenlin
     */
    public void setEventListener(IEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public class OnClickLstner implements OnClickListener {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            try {
                if (eventListener == null)
                    return;

                if (mDrawerLayout != null
                        && mDrawerLayout.isDrawerVisible(START)) {
                    mDrawerLayout.closeDrawer(START);
                }

                if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_dowmload) {
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_DOWNLOAD_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_upload) {
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_UPLOAD_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_tv_back) {
                    if (etSearch.getVisibility() == View.VISIBLE) {
                        whenSearchActionbarStyle(false, view);
                    } else {
                        if (needConfirm) {
                            new AlertDialog.Builder(activity)
                                    .setMessage("是否确定退出")
                                    .setPositiveButton(
                                            "确定",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {
                                                    activity.finish();
                                                }
                                            })
                                    .setNegativeButton(
                                            "取消",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface arg0,
                                                        int arg1) {

                                                }
                                            }).create().show();
                        } else {
                            activity.finish();
                        }
                    }
                    eventListener.eventProcess(IEventType.ACTIONBAR_BACK_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_tv_more_left) {
                    if (mDrawerLayout != null)
                        mDrawerLayout.closeDrawer(mDrawerList);
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_LEFTMORE_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_title) {
                    if (isIconTip)
                        showTitleInfoPopupWin();
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_tv_more) {
                    eventListener.eventProcess(IEventType.ACTIONBAR_MORE_CLICK,
                            view);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_more_leveltwo) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_LEVELTWO_MORE_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_delete) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_DELETE_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custon_subtitle) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_SUBTITLE_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_ibtn_search) {
                    whenSearchActionbarStyle(true, view);

                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_RETURN_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_img_et_search) {
                    eventListener.eventProcess(IEventType.ET_SEARCH_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_tv_canel) {
                    whenSearchActionbarStyle(false, view);
                    etSearch.setText("");
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_CALNEL_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_tv_join) {
                    whenJoinActionbarStyle(true);
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_JOIN_BTN_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_tv_join_OK) {
                    // whenJoinActionbarStyle(false);
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_JOIN_OK_CLICK);
                } else if (view.getId() == R.id.hd_hse_common_phone_actionbar_custom_tv_join_cancel) {
                    whenJoinActionbarStyle(false);
                    eventListener
                            .eventProcess(IEventType.ACTIONBAR_JOIN_CANCEL_CLICK);
                } else if (view.getId() == R.id.hd_hse_statistic_actionbar_tv_select) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_STATISTIC_SELECT_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_statistic_actionbar_bt_select) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_BT_SELECT_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_statistic_actionbar_bt_cancle) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_BT_CANCLE_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_statistic_actionbar_bt_save) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_BT_SAVE_CLICK, view);
                } else if (view.getId() == R.id.hd_hse_statistic_actionbar_bt_up) {
                    eventListener.eventProcess(
                            IEventType.ACTIONBAR_BT_UP_CLICK, view);
                } else if (view.getId() == R.id.zyplldx) {
                    eventListener.eventProcess(IEventType.ACTIONBAR_DX, view);
                } else if (view.getId() == R.id.zypllzyfh) {
                    eventListener.eventProcess(IEventType.ACTIONBAR_ZYFH, view);
                } else if (view.getId() == R.id.dlipci) {
                    eventListener.eventProcess(IEventType.DALIANCENTTERPIC, view);
                }
            } catch (HDException e) {
                LogUtils.getLogger(HSEActionBar.class).error(e.getMessage(), e);
                try {
                    throw new HDException(e.getMessage());
                } catch (HDException e1) {
                    // TODO Auto-generated catch block
                    LogUtils.getLogger(HSEActionBar.class).error(
                            e.getMessage(), e1);
                }
            }
        }

    }

    private OnKeyListener etInputKeylistener = new OnKeyListener() {

        @Override
        public boolean onKey(View arg0, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (eventListener != null) {
                    try {
                        etSearch.save();
                        // 隐藏输入键盘
                        InputMethodManager imm = (InputMethodManager) activity
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
                        eventListener.eventProcess(
                                IEventType.ACTIONBAR_SEARCH_CLICK,
                                getSearchContent());
                    } catch (HDException e) {
                        try {
                            throw new HDException(e.getMessage());
                        } catch (HDException e1) {
                            LogUtils.getLogger(HSEActionBar.class).error(
                                    e.getMessage(), e);
                        }
                    }
                }
            }
            return false;
        }
    };

    private OnTouchListener etSearch_OnTouch = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    etSearch.showDropDown();

                    int curX = (int) event.getX();
                    if (curX > v.getWidth() - 48
                            && !TextUtils.isEmpty(etSearch.getText())) {

                        etSearch.setText("");
                        // 显示输入键盘
                        InputMethodManager imm = (InputMethodManager) activity
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(v, 0);
                        try {
                            eventListener.eventProcess(
                                    IEventType.ACTIONBAR_SEARCH_CLICK,
                                    getSearchContent());
                        } catch (HDException e) {
                            LogUtils.getLogger(HSEActionBar.class).error(
                                    e.getMessage(), e);
                        }

                        return true;
                    }
                    if (curX > v.getWidth() - 48
                            && TextUtils.isEmpty(etSearch.getText())) {
                        InputMethodManager imm = (InputMethodManager) activity
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        try {
                            eventListener.eventProcess(IEventType.
                                    ACTIONBAR_SEARCH_DETAIL, etSearch);
                        } catch (HDException e) {
                            LogUtils.getLogger(HSEActionBar.class).error(
                                    e.getMessage(), e);
                        }

                        return true;
                    }
                    break;
            }
            return false;
        }
    };

    private TextWatcher etSearch_TextChanged = new TextWatcher() {
        // 判断上一次文本框内容是否为空

        private boolean isNull = true;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable content) {
            if (TextUtils.isEmpty(content)) {
                if (!isNull) {
                    // 隐藏清除
                    etSearch.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            mIconSearch, null, mIconSearchDetail, null);
                    isNull = true;

                }
            } else {
                if (isNull) {
                    // 显示清除图标
                    etSearch.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            mIconSearch, null, mIconSearchClear, null);
                    isNull = false;
                }
            }

        }
    };

    /**
     * showTitleInfoPopupWin:(显示导航栏作业票详细信息). <br/>
     * date: 2014年12月31日 <br/>
     *
     * @author wenlin
     */
    public void showTitleInfoPopupWin() {
        if (mAlertDialog == null && isIconTip) {
            mAlertDialog = new MyAlertDialog(activity,
                    R.style.workorder_dialog, superEntity, true);
        }
        mAlertDialog.show();
        WindowManager.LayoutParams params = mAlertDialog.getWindow()
                .getAttributes();
        // params.height = 800;

        // params.width = 680;
        mAlertDialog.getWindow().setAttributes(params);
    }

    public void setDyTable(SuperEntity superEntity) {
        this.superEntity = superEntity;
    }

    /**
     * setControlVisible:(显示控件). date: 2014年10月9日
     *
     * @param controlsName
     * @author wenlin
     */
    public void setControlVisible(String[] controlsName) {
        setControl(controlsName, View.VISIBLE);
    }

    /**
     * setControlInVisible:(隐藏控件). date: 2014年10月9日
     *
     * @param controlsName
     * @author wenlin
     */
    public void setControlInVisible(String[] controlsName) {
        setControl(controlsName, View.GONE);
    }

    /**
     * controls:(控件是否显示属性设置). date: 2014年10月8日
     *
     * @param controlsVisible 控件数组
     * @param visible         是否显示
     * @author wenlin
     */
    private void setControl(String[] controlsVisible, int visible) {
        if (controlsVisible != null) {
            for (String controlName : controlsVisible) {
                if (controlName != null && controlMap.containsKey(controlName)) {
                    controlMap.get(controlName).setVisibility(visible);
                }
            }
        }
    }

    /**
     * setSearchVisible:(控制搜索框显示和隐藏). <br/>
     * date: 2015年3月18日 <br/>
     *
     * @param visible
     * @author lxf
     */
    public void setSearchVisible(boolean visible) {
        if (etSearch != null) {
            whenSearchActionbarStyle(visible, etSearch);
        }
    }

    /**
     * actionbarChanged:(搜索功能时导航栏的布局). <br/>
     * date: 2015年1月15日 <br/>
     *
     * @param etSearchVisible 搜索功能是否显示
     * @author wenlin
     */
    public void whenSearchActionbarStyle(Boolean etSearchVisible, View v) {
        if (etSearchVisible) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            // 显示键盘
            imm.showSoftInput(v, 0);
            ibtnBack.setVisibility(View.GONE);
            etSearch.setVisibility(View.VISIBLE);
            tvCanel.setVisibility(View.VISIBLE);
            ibtnSearch.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            tvJoin.setVisibility(View.GONE);
            tvJoinOK.setVisibility(View.GONE);
            tvJoinCancel.setVisibility(View.GONE);
            zypllzyfh.setVisibility(View.GONE);

        } else {
            etSearch.setVisibility(View.INVISIBLE);
            ibtnBack.setVisibility(View.GONE);
            tvCanel.setVisibility(View.INVISIBLE);
            ibtnSearch.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            setControlVisible(controlsVisible);
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            // 隐藏输入键盘
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

    }

    public void whenJoinActionbarStyle(boolean isJoin) {
        if (isJoin) {
            ibtnBack.setVisibility(View.GONE);
            etSearch.setVisibility(View.GONE);
            tvCanel.setVisibility(View.GONE);
            ibtnSearch.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            tvJoin.setVisibility(View.GONE);
            tvJoinOK.setVisibility(View.VISIBLE);
            tvJoinCancel.setVisibility(View.VISIBLE);
        } else {
            etSearch.setVisibility(View.INVISIBLE);
            ibtnBack.setVisibility(View.GONE);
            tvCanel.setVisibility(View.INVISIBLE);
            ibtnSearch.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            tvJoinOK.setVisibility(View.GONE);
            tvJoinCancel.setVisibility(View.GONE);
            setControlVisible(controlsVisible);
        }
    }

    /**
     * setTitleContent:(设置导航栏标题内容). <br/>
     * date: 2015年1月6日 <br/>
     *
     * @param titleInfo 内容信息
     * @param isIconTip 是否显示下拉图标
     * @author wenlin
     */
    public void setTitleContent(String titleInfo, Boolean isIconTip) {
        if (titleInfo != null) {
            tvTitle.setText(titleInfo);
            this.isIconTip = isIconTip;
            setTitleClick();
        }
    }

    /**
     * setSubTitleContent:(设置副标题内容). <br/>
     * date: 2015年1月7日 <br/>
     *
     * @param subTitleInfo
     * @author wenlin
     */
    public void setSubTitleContent(String subTitleInfo) {
        if (subTitleInfo != null)
            tvSubTitle.setText(subTitleInfo);
    }

    /**
     * setTitleClick:(设置标题是否可以点击，并弹出dialog). <br/>
     * date: 2015年1月14日 <br/>
     *
     * @author wenlin
     */
    public void setTitleClick() {
        if (isIconTip) {
            tvTitle.setClickable(true);
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    activity.getResources()
                            .getDrawable(
                                    R.drawable.hd_hse_common_phone_actionbar_custon_icon_subtitle),
                    null);
        } else {
            tvTitle.setEnabled(false);
        }
    }

    /**
     * getSearchContent:(返回输入的搜索内容). <br/>
     * date: 2015年1月15日 <br/>
     *
     * @return
     * @author wenlin
     */
    public String getSearchContent() {
        return etSearch.getText().toString();
    }

    /**
     * displayNewMore:(). <br/>
     * date: 2015年8月14日 <br/>
     *
     * @param isok
     * @author lxf
     */
    public void displayNewMore(boolean isok) {

        if (ibtnRMore != null) {
            if (isok) {
                // hd_hse_common_component_phone_actionbar_icon_rightmore;
                // ibtnRMore.setImageResource(activity.getResources().getDrawable(R.drawable.hd_hse_common_component_phone_actionbar_icon_rightmore));
                ibtnRMore
                        .setImageDrawable(activity
                                .getResources()
                                .getDrawable(
                                        R.drawable.hd_hse_common_component_phone_actionbar_icon_rightmore_new));
                // 表示显示新的图标
                // Drawable
                // drawableRight=activity.getResources().getDrawable(R.drawable.hd_hse_common_component_title_more_new);
                // drawableRight.setBounds(0, 0,
                // drawableRight.getMinimumWidth(),
                // drawableRight.getMinimumHeight());
                // Drawable
                // drawableLeft=activity.getResources().getDrawable(R.drawable.hd_hse_common_component_more_line_vertical);
                // drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                // drawableLeft.getMinimumHeight());
                // ibtnRMore.setCompoundDrawables(drawableLeft, null,
                // drawableRight, null);
            } else {
                // 表示还原图标
                ibtnRMore
                        .setImageDrawable(activity
                                .getResources()
                                .getDrawable(
                                        R.drawable.hd_hse_common_component_phone_actionbar_icon_rightmore));
                // Drawable
                // drawableRight=activity.getResources().getDrawable(R.drawable.hd_hse_common_component_title_more);
                // drawableRight.setBounds(0, 0,
                // drawableRight.getMinimumWidth(),
                // drawableRight.getMinimumHeight());
                // Drawable
                // drawableLeft=activity.getResources().getDrawable(R.drawable.hd_hse_common_component_more_line_vertical);
                // drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(),
                // drawableLeft.getMinimumHeight());
                // ibtnRMore.setCompoundDrawables(drawableLeft, null,
                // drawableRight, null);
            }
        }
    }

    /**
     * 设置标题栏搜索框提示语
     */
    public void setSearchHint(String hint) {
        this.hint = hint;
        if (etSearch != null) {
            etSearch.setHint(hint);
        }

    }
}
