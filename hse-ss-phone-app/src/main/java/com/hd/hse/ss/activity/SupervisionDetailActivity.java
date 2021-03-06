package com.hd.hse.ss.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.adapter.PopMenuItem;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.DateTimePickDialogUtil;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.custom.HorizontalListView;
import com.hd.hse.common.module.phone.custom.MyGridView;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PhotoPreviewActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.web.cbs.GetPicInfor;
import com.hd.hse.dc.business.web.wtdj.UpWtdjInfo;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.worklog.KhbzEntity;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.ss.ImageHandle;
import com.hd.hse.ss.ImageLoader;
import com.hd.hse.ss.ImageLoader.ImageCallback;
import com.hd.hse.ss.R;
import com.hd.hse.ss.adapter.DeptListAdapter2;
import com.hd.hse.ss.adapter.ImgAdapter;
import com.hd.hse.ss.adapter.MyArrayAdapter;
import com.hd.hse.ss.adapter.WzlxAdapter;
import com.hd.hse.ss.adapter.WztkAdapter;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ???????????????????????? Created by liuyang on 2016???9???26???
 */
public class SupervisionDetailActivity extends BaseFrameActivity implements
        IEventListener, OnClickListener {
    private static Logger logger = LogUtils
            .getLogger(SupervisionDetailActivity.class);

    public static final int REQUEST_CODE = 0;
    /**
     * ?????????????????? Created by liuyang on 2016???9???27???
     */
    private WorkOrder mWorkOrder;
    private WorkLogEntry mWorkLog;

    private PDAWorkOrderInfoConfig pdaConfig;

    private RelativeLayout.LayoutParams mLayoutParams;
    private LayoutParams mLvLayoutParams;
    private LinearLayout.LayoutParams mReLayoutParams;
    /**
     * ????????????
     */
    private TextView mJcdeptTxt;
    /**
     * ?????????
     */
    private TextView mJcPersonTxt;
    /**
     * ????????????
     */
    private TextView mJcTimeTxt;
    /**
     * ????????????
     */
    private TextView mWorkOrderTxt;
    /**
     * ????????????
     */
    private Button mZyareaBt;
    /**
     * ????????????
     */
    private Button mSddeptBt;
    /**
     * ????????????
     */
    private MyGridView mGridView;
    private GridViewCheckBoxAdapter mGridViewCheckBoxAdapter;// ???????????????adapter
    /**
     * Spinner??????
     */
    private List<Domain> mDomains;
    /**
     * ??????????????????
     */
    private List<Domain> wzlxDatas;
    /**
     * ????????????
     */
    private EditText problemDescEd;
    /**
     * ???????????????
     */
    private EditText liveFzrEd;
    /**
     * ????????????
     */
    private EditText wzPersonEd;
    /**
     * ????????????
     */
    private EditText zrDeptEd;
    private Button btzrDept;
    /**
     * ????????????
     */
    private EditText siteEd;
    /**
     * ????????????
     */
    private EditText fkMoneyEd;
    /**
     * ????????????
     */
    // private EditText wytkEd;
    /**
     * ????????????
     */
    private RadioGroup zgRadioGruop;
    /**
     * ??????????????????
     */
    private CheckBox blacklistCheckBox;
    /**
     * ??????????????????????????????
     */
    private LinearLayout llClickBl;
    /**
     * ????????????
     */
    private Spinner spJrReason;
    /**
     * ????????????
     */
    private EditText edLrRemark;
    /**
     * ????????????
     */
    private EditText edWzlx;
    /**
     * ????????????bt
     */
    private Button btWzlx;

    /**
     * ????????????
     */
    private EditText edWztk;
    /**
     * ????????????bt
     */
    private Button btWztk;
    /**
     * ?????????????????????
     */
    private CheckBox isLoadkhd;
    /**
     * ??????????????????
     */
    private EditText examYj;

    /**
     * ????????????????????????textview??????
     */
    private TextView examYjTv;

    private MyArrayAdapter myArrayAdapter;
    private boolean isSave = false;
    public static final String ISSAVE = "issave";
    public static final String UD_CBSGL_RZID = "ud_cbsgl_rzid";

    /**
     * ???????????????listview
     */
    private HorizontalListView mHorizontalListView;
    private ImgAdapter mImgAdapter;
    private LinearLayout liZg;
    private HorizontalListView hvZg;
    private ImgAdapter mImgAdapterZg;
    /**
     * ????????????
     */
    private ArrayList<Image> imageList = new ArrayList<>();
    /**
     * ?????????
     */
    private ArrayList<Image> imageListZgq = new ArrayList<>();
    /**
     * ?????????
     */
    private ArrayList<Image> imageListZgh = new ArrayList<>();
    /**
     * ?????????
     */
    private ArrayList<Image> imageListYsh = new ArrayList<>();

    private IQueryWorkInfo queryWorkInfo;

    private List<Domain> problemTypeStr;
    private List<Domain> recordTypes;
    private final String PROBLEM = "PROBLEM";
    private final String TOCHECK = "TOCHECK";
    private final String PRAISE = "PRAISE";

    public static final String NAME = "name";
    public static final String EDITTEXTISFOCUSABLE = "edittextisfacuable";
    public static final String ISSINGLEPERSON = "issingleperson";
    public static final int SINGLEPERSON = 1;

    public static final int REQUESTCODEONE = 1;
    public static final int REQUESTCODETWO = 2;
    public static final int REQUESTCODETHREE = 3;
    public static final int REQUESTCODEFOUR = 4;
    public static final int REQUESTCODEFIVE = 5;
    public static final int REQUESTCODESIX = 6;
    public static final int READCARDEXAMINEACTIVITYCODE = 1545;

    private PopupWindow popupWindow;
    private String[] itemNames = new String[]{"??????", "??????"};

    private List<Department> depts;
    private List<Department> zrDepts;

    /**
     * ????????????????????????????????????
     */
    private boolean isSelected = false;
    /**
     * ???????????????id
     */
    private final String WZLBALN = "WZLBALN";

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????view
     */
    private LinearLayout liRecordType, liSdbm, liSddw, liWorkorder, liZrgs, liWzlb, liWztk,
            liWtms, liKhbzyj, liXcfzr, liWzry, liZrdw, liFkje, liSite;
    // codes ???????????????????????????
    private String[] codes;

    private Button btRecordType;
    private LinearLayout liAll;
    private ImageLoader mImageLoader;
    private ImageView imageRecordTypee, imageSdbm, imageSddw, imageZyp;
    private LinearLayout penalty_pointli;
    private EditText zgcsEd;
    private CheckBox typicpCheckBox;
    private CheckBox worktypepbCheckBox;
    private EditText penaltyPointEd;
    private CheckBox checkCheckBox;
    private LinearLayout zgyzjl;
    private HorizontalListView yshlv;
    private ImgAdapter mImgAdapterYs;
    private EditText zgyzjlEd;
    private LinearLayout liCheckBox;
    private TextView reformDateTxt;
    private LinearLayout liTimeLi;
    private LinearLayout zgcsLi;
    private LinearLayout zgyzjlLi;
    private LinearLayout typicpLi;
    private LinearLayout isLoadkhdLi;
    private LinearLayout worktypbLi;
    private LinearLayout sgdwzrrLi;
    private LinearLayout sgdwzyjhrLi;
    private LinearLayout sgdwxmaqyLi;
    private LinearLayout sgdwxmfzrLi;
    private EditText sgdwzrrEt;
    private EditText sgdwzyjhrEt;
    private EditText sgdwxmaqyEt;
    private EditText sgdwxmfzrEt;

    private boolean isShowPenaltyLi = false;
    private LinearLayout zgStatusLi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_pc_phone_app_layout_surveillance);
//        initActionbar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //??????????????????????????????????????????
                initView();
                initData();
            }
        }, 500);

        initActionbar();

    }




    private void initActionbar() {
        // ?????????ActionBar
        setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE, IActionBar.BT_SAVE, IActionBar.BT_UP});
        // ?????????????????????
        setActionBartitleContent("????????????", false);
        // ?????????????????????

        // setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE});

    }

    private void initView() {
        final int width = getImgWidth();
        mLayoutParams = getLayoutParams(width);
        mLvLayoutParams = getLvLayoutParams(width);
        mReLayoutParams = getReLayoutParams(width);
        isLoadkhdLi = (LinearLayout) findViewById(R.id.li_checkbox);
        zgStatusLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_radio);
        typicpLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_typicp);
        worktypbLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_worktypeproblem_li);
        sgdwzrrLi= (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_sgdwzrr_li);
        sgdwzrrEt= (EditText) findViewById(R.id.hse_pc_phone_app_layout_sgdwzrr_et);
        sgdwzyjhrLi= (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_sgdwzyjhr_li);
        sgdwzyjhrEt= (EditText) findViewById(R.id.hse_pc_phone_app_layout_sgdwzyjhr_et);
        sgdwxmaqyLi= (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_sgdwxmaqy_li);
        sgdwxmaqyEt= (EditText) findViewById(R.id.hse_pc_phone_app_layout_sgdwxmaqy_et);
        sgdwxmfzrLi= (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_sgdwxmfzr_li);
        sgdwxmfzrEt= (EditText) findViewById(R.id.hse_pc_phone_app_layout_sgdwxmfzr_et);
        liTimeLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_xqzg_li_time);
        reformDateTxt = (TextView) findViewById(R.id.hse_pc_phone_app_layout_xqzg_tv_time);
        liCheckBox = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_check);
        zgyzjlLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_zgyzjl);
        zgyzjlEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_zgyzjl);
        yshlv = (HorizontalListView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_lv_ys);
        zgyzjl = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_ys);
        checkCheckBox = (CheckBox) findViewById(R.id.hse_pc_phone_app_layout_surveillance_checkbox_check);
        typicpCheckBox = (CheckBox) findViewById(R.id.hse_pc_phone_app_layout_surveillance_checkbox_typicp);
        worktypepbCheckBox = (CheckBox) findViewById(R.id.hse_pc_phone_app_layout_worktypeproblem_li_ck);
        zgcsLi = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_zgcs);
        zgcsEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_zgcs);
        penalty_pointli = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_penalty_point);
        penaltyPointEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_penalty_point);
        mWorkOrderTxt = (TextView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_workorder);
        mZyareaBt = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_Department);
        mSddeptBt = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_location_company);
        mJcdeptTxt = (TextView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_organization);
        mJcPersonTxt = (TextView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_name);
        mJcTimeTxt = (TextView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_time);
        mGridView = (MyGridView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_gridview_checkbox);
        problemDescEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_describe_problem);
        liveFzrEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_leader);
        wzPersonEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_illegal_person);
        zrDeptEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_duty_company);
        siteEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_site);
        btzrDept = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_bt_duty_company);
        fkMoneyEd = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_penalty_money);
        // wytkEd=(EditText)
        // findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_wytk);
        zgRadioGruop = (RadioGroup) findViewById(R.id.hse_pc_phone_app_layout_surveillance_checkbox_rectify);
        blacklistCheckBox = (CheckBox) findViewById(R.id.hse_pc_phone_app_layout_surveillance_checkbox_blacklist);
        mHorizontalListView = (HorizontalListView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_lv);
        liZg = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_zg);
        hvZg = (HorizontalListView) findViewById(R.id.hse_pc_phone_app_layout_surveillance_lv_zg);
        llClickBl = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ll_click_blacklist);
        spJrReason = (Spinner) findViewById(R.id.hse_pc_phone_app_layout_surveillance_sp_jryy);
        edLrRemark = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_ed_remark);
        edWzlx = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_wzlx);
        btWzlx = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_button_wzlx);
        edWztk = (EditText) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_wztk);
        btWztk = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_button_wztk);
        isLoadkhd = (CheckBox) findViewById(R.id.checkbox);
        examYj = (EditText) findViewById(R.id.examine_answer);
        examYjTv = (TextView) findViewById(R.id.examine_answer_textview);
        btRecordType = (Button) findViewById(R.id.hse_pc_phone_app_layout_surveillance_tv_record_type);
        liAll = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_all);
        imageRecordTypee = (ImageView) findViewById(R.id.jl_re_image);
        imageSdbm = (ImageView) findViewById(R.id.sd_re_image);
        imageSddw = (ImageView) findViewById(R.id.dw_re_image);
        imageZyp = (ImageView) findViewById(R.id.zyp_re_iamge);

        reformDateTxt.setOnClickListener(this);
        liveFzrEd.setOnClickListener(this);
        wzPersonEd.setOnClickListener(this);
        mWorkOrderTxt.setOnClickListener(this);
        mZyareaBt.setOnClickListener(this);
        mSddeptBt.setOnClickListener(this);
        zrDeptEd.setOnClickListener(this);
        btzrDept.setOnClickListener(this);
        btWzlx.setOnClickListener(this);
        btWztk.setOnClickListener(this);
        isLoadkhd.setOnClickListener(this);
        imageRecordTypee.setOnClickListener(this);
        imageSdbm.setOnClickListener(this);
        imageSddw.setOnClickListener(this);
        imageZyp.setOnClickListener(this);
        sgdwzrrEt.setOnClickListener(this);
        sgdwzyjhrEt.setOnClickListener(this);
        sgdwxmaqyEt.setOnClickListener(this);
        sgdwxmfzrEt.setOnClickListener(this);


        mHorizontalListView.setLayoutParams(mLvLayoutParams);
        hvZg.setLayoutParams(mLvLayoutParams);
        yshlv.setLayoutParams(mLvLayoutParams);

        zgRadioGruop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes){
                    liZg.setVisibility(View.VISIBLE);
                    isViewGone(zgcsLi, "ZGCS");
                    group.check(checkedId);
                }else {
                    if (imageListYsh != null && imageListYsh.size() > 0) {
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "??????????????????????????????????????????");
                        group.check(R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes);
                        return;
                    }
                    checkCheckBox.setChecked(false);
                    if (imageListZgh != null && imageListZgh.size() > 0) {
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "??????????????????????????????????????????");
                        group.check(R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes);
                    } else {
                        liZg.setVisibility(View.GONE);
                        zgcsLi.setVisibility(View.GONE);
                        group.check(R.id.hse_pc_phone_app_layout_surveillance_radiobutton_no);
                    }
                }
            }
        });

//        zgCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//                // TODO Auto-generated method stub
//                if (arg1) {
//                    liZg.setVisibility(View.VISIBLE);
//                    isViewGone(zgcsLi, "ZGCS");
//                    //zgcsLi.setVisibility(View.VISIBLE);
//                    zgCheckBox.setChecked(true);
//                } else {
//                    if (imageListYsh != null && imageListYsh.size() > 0) {
//                        ToastUtils.toast(SupervisionDetailActivity.this,
//                                "??????????????????????????????????????????");
//                        zgCheckBox.setChecked(true);
//                        return;
//                    }
//                    checkCheckBox.setChecked(false);
//                    if (imageListZgh != null && imageListZgh.size() > 0) {
//                        ToastUtils.toast(SupervisionDetailActivity.this,
//                                "??????????????????????????????????????????");
//                        zgCheckBox.setChecked(true);
//                    } else {
//                        liZg.setVisibility(View.GONE);
//                        zgcsLi.setVisibility(View.GONE);
//                        zgCheckBox.setChecked(false);
//                    }
//
//                }
//
//            }
//        });
        blacklistCheckBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        if (arg1) {
                            if (wzPersonEd.getText().toString() == null
                                    || wzPersonEd.getText().toString()
                                    .equals("")) {
                                ToastUtils.toast(
                                        SupervisionDetailActivity.this,
                                        "????????????????????????");
                                blacklistCheckBox.setChecked(false);
                                llClickBl.setVisibility(View.GONE);
                            } else {
                                llClickBl.setVisibility(View.VISIBLE);
                            }
                        } else {
                            llClickBl.setVisibility(View.GONE);
                        }
                    }
                });

        checkCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkCheckBox.setChecked(true);
                    int checkedRadioButtonId = zgRadioGruop.getCheckedRadioButtonId();
                    if (checkedRadioButtonId==R.id.hse_pc_phone_app_layout_surveillance_radiobutton_no) {
                        checkCheckBox.setChecked(false);
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "????????????????????????");
                    } else {
                        zgyzjl.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (imageListYsh != null && imageListYsh.size() > 0) {
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "??????????????????????????????????????????");
                        checkCheckBox.setChecked(true);
                    } else {
                        zgyzjl.setVisibility(View.GONE);
                        checkCheckBox.setChecked(false);
                    }
                }
            }
        });
        mImgAdapter = new ImgAdapter(this, mLayoutParams, mReLayoutParams,
                imageListZgq);
        mHorizontalListView.setAdapter(mImgAdapter);
        mHorizontalListView.setOnItemClickListener(new MyItemClickListener(
                "?????????", imageListZgq, mImgAdapter));
        mHorizontalListView.setOnItemLongClickListener(new MyLongClickListener(
                mImgAdapter));

        mImgAdapterZg = new ImgAdapter(this, mLayoutParams, mReLayoutParams,
                imageListZgh);
        hvZg.setAdapter(mImgAdapterZg);
        hvZg.setOnItemClickListener(new MyItemClickListener("?????????",
                imageListZgh, mImgAdapterZg));
        hvZg.setOnItemLongClickListener(new MyLongClickListener(mImgAdapterZg));

        mImgAdapterYs = new ImgAdapter(this, mLayoutParams,
                mReLayoutParams, imageListYsh);
        yshlv.setAdapter(mImgAdapterYs);
        yshlv.setOnItemClickListener(new MyItemClickListener("?????????",
                imageListYsh, mImgAdapterYs));
        yshlv.setOnItemLongClickListener(new MyLongClickListener(mImgAdapterYs));

        fkMoneyEd.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0)
                    return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });
        liRecordType = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_record_type);
        liSdbm = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_Department);
        liSddw = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_location_company);

        liWorkorder = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_workorder);
        liZrgs = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_checkbox);
        liWzlb = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_wzlx);

        liWztk = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_wztk);

        liWtms = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_describe_problem);
        liKhbzyj = (LinearLayout) findViewById(R.id.examine_answer_li);
        liXcfzr = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_leader);

        liWzry = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_illegal_person);
        liZrdw = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_duty_company);
        liFkje = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_penalty_money);
        liSite = (LinearLayout) findViewById(R.id.hse_pc_phone_app_layout_surveillance_li_site);
        codes = getcodes();
        selectQuestion();

        typicpCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    liCheckBox.setVisibility(View.VISIBLE);
                    String s = "REFORM_DATE";
                    boolean flag = false;
                    if (codes != null) {
                        for (int w = 0; w < codes.length; w++) {
                            if (codes[w].toLowerCase().equals(s.toLowerCase())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            liTimeLi.setVisibility(View.VISIBLE);
                        }
                    } else {
                        liTimeLi.setVisibility(View.VISIBLE);
                    }
                } else {
                    liCheckBox.setVisibility(View.GONE);
                    liTimeLi.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * recordtype?????????
     */
    private void selectQuestion() {
        isViewGone(liSite, "BREACHLOCATION");
        isViewGone(liRecordType, "RECORDTYPE");
        isViewGone(liSdbm, "ZYAREA");
        //isViewGone(liSdbm, "ZYAREA");
        isViewGone(liSddw, "SDDEPT");
        isViewGone(liWorkorder, "UD_ZYXK_ZYSQID");
        isViewGone(liZrgs, "RZTYPE");
        isViewGone(liWzlb, "WZLB");
        isViewGone(liWztk, "WZTK");
        isViewGone(liWtms, "FXWT");
        isViewGone(liKhbzyj, "EXAMYJ");
        isViewGone(isLoadkhdLi, "ISKHD");

        isViewGone(liXcfzr, "FZPERSON");
        isViewGone(liWzry, "INNERCARD");
        isViewGone(liZrdw, "ZRDEPT");
        isViewGone(liFkje, "WYJ");
        isViewGone(zgStatusLi, "ISZG");
        isViewGone(blacklistCheckBox, "ISHMD");
        isViewGone(typicpLi, "ISTYPICISSUE");
        isViewGone(worktypbLi, "ISDDH");
        isViewGone(zgcsLi, "ZGCS");
        isViewGone(checkCheckBox, "ISYS");
        isViewGone(zgyzjlLi, "ZGYZJL");
        isViewGone(sgdwzrrLi,"CBSZJZRR");
        isViewGone(sgdwzyjhrLi,"CBSJHR");
        isViewGone(sgdwxmaqyLi,"CBSAQGLRY");
        isViewGone(sgdwxmfzrLi,"CBSXMFZR");
        if (isShowPenaltyLi) {
            penalty_pointli.setVisibility(View.VISIBLE);
        }

    }

    /**
     * ??????
     */
    private void selectDaojian() {
        selectQuestion();
        selectPraise();
        liZrdw.setVisibility(View.GONE);
        typicpLi.setVisibility(View.GONE);
    }

    /**
     * ??????
     */
    private void selectPraise() {
        selectQuestion();
        liZrgs.setVisibility(View.GONE);
        liWzlb.setVisibility(View.GONE);
        liWztk.setVisibility(View.GONE);
        liKhbzyj.setVisibility(View.GONE);
        isLoadkhdLi.setVisibility(View.GONE);
        liXcfzr.setVisibility(View.GONE);
        liWzry.setVisibility(View.GONE);
        liFkje.setVisibility(View.GONE);
        zgStatusLi.setVisibility(View.GONE);
        blacklistCheckBox.setVisibility(View.GONE);
        liZrdw.setVisibility(View.VISIBLE);
        typicpLi.setVisibility(View.GONE);
        if (liZrgs.getVisibility() != View.VISIBLE) {
            penalty_pointli.setVisibility(View.GONE);
        }

    }

    private class MyItemClickListener implements OnItemClickListener {
        private String name;
        private ArrayList<Image> imageList;
        private ImgAdapter mImgAdapter;

        public MyItemClickListener(String name, ArrayList<Image> imageList,
                                   ImgAdapter mImgAdapter) {
            this.name = name;
            this.imageList = imageList;
            this.mImgAdapter = mImgAdapter;
        }

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            int shotPostion = imageList.size();
            if (shotPostion == arg2) {
                // ??????
                touchImages(name);
            } else if (!mImgAdapter.isDele()) {
                // ????????????
                Intent intent = new Intent(SupervisionDetailActivity.this,
                        PhotoPreviewActivity.class);
                intent.putExtra(PhotoPreviewActivity.SELECTEDINDEX, arg2);
                intent.putExtra(PhotoPreviewActivity.IMAGESET, imageList);
                startActivity(intent);
            }
        }
    }

    private class MyLongClickListener implements OnItemLongClickListener {
        private ImgAdapter mImgAdapter;

        public MyLongClickListener(ImgAdapter mImgAdapter) {
            this.mImgAdapter = mImgAdapter;
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            boolean isDele = mImgAdapter.isDele();
            if (!isDele) {
                mImgAdapter.setDele(true);
                mImgAdapter.notifyDataSetChanged();
            }
            return false;
        }

    }

    private void initData() {
        pdaConfig = new PDAWorkOrderInfoConfig();
        pdaConfig.setPscode("wtdj");
        queryWorkInfo = new QueryWorkInfo();

        boolean flag = false;
        if (codes != null) {
            for (int w = 0; w < codes.length; w++) {
                if (codes[w].toLowerCase().equals("RZTYPE".toLowerCase())) {
                    flag = true;
                    break;
                }
            }

        }

        if (!flag) {
            try {
                problemTypeStr = queryWorkInfo.queryDomain("UDISSUETYPE", null);
                recordTypes = queryWorkInfo.queryDomain("RECORDTYPE", null);
                if (problemTypeStr != null && problemTypeStr.size() > 0) {
                    mGridViewCheckBoxAdapter = new GridViewCheckBoxAdapter<>(
                            problemTypeStr);
                    mGridView.setAdapter(mGridViewCheckBoxAdapter);
                } else {
                    ToastUtils.toast(getApplicationContext(),
                            "????????????????????????????????????????????????????????????????????????");
                }
            } catch (HDException e) {
                e.printStackTrace();
            }
        }

        Intent intent = getIntent();
        if (intent.hasExtra(WorkLogEntry.class.getName())) {
            // ??????
            isSave = true;
            mWorkLog = (WorkLogEntry) intent
                    .getSerializableExtra(WorkLogEntry.class.getName());
            initWorkorder(mWorkLog);
            fillUi(mWorkLog);

        } else if (intent.hasExtra(UD_CBSGL_RZID)) {
            // ????????????
            mWorkLog = (WorkLogEntry) intent
                    .getSerializableExtra(UD_CBSGL_RZID);
            if (null != mWorkLog) {
                initWorkorder(mWorkLog);
                fillUi(mWorkLog);
            } else {
                ToastUtils.toast(getApplicationContext(), "????????????????????????");
                return;
            }

            // getNetData(ud_cbsgl_rzid);

            GetNetPic(mWorkLog.getPadrzid());

        } else if (intent.hasExtra(WorkOrder.class.getName())) {
            // ????????????

            newWorkLog();
            mWorkOrder = (WorkOrder) intent
                    .getSerializableExtra(WorkOrder.class.getName());
            setWorkOrderInfo(mWorkOrder);

        } else {
            // ??????

            newWorkLog();

        }
        setBtRecordType();
        initSpiner();
    }

    private void setBtRecordType() {
        if (mWorkLog.getRecordtype() != null) {
            if (mWorkLog.getRecordtype().equals(PROBLEM)) {
                selectQuestion();
            } else if (mWorkLog.getRecordtype().equals(TOCHECK)) {
                selectDaojian();
            } else if (mWorkLog.getRecordtype().equals(PRAISE)) {
                selectPraise();
            }
            if (recordTypes != null) {
                for (Domain domain : recordTypes) {
                    if (domain.getValue().equals(mWorkLog.getRecordtype())) {
                        btRecordType.setText(domain.getDescription());
                        break;
                    }
                }
            }
        } else {
            btRecordType.setText("??????");
            mWorkLog.setRecordtype(PROBLEM);
        }
    }

    /**
     * ??????worklog?????????workorder
     */
    private void initWorkorder(WorkLogEntry mWorkLog) {
        mWorkOrder = new WorkOrder();
        mWorkOrder.setUd_zyxk_zysqid(mWorkLog.getUd_zyxk_zysqid());
        mWorkOrder.setZyname(mWorkLog.getZyname());
        mWorkOrder.setZyarea(mWorkLog.getZyarea());
        mWorkOrder.setZyarea_desc(mWorkLog.getZyarea_desc());
        mWorkOrder.setSddept(mWorkLog.getSddept());
        mWorkOrder.setSddept_desc(mWorkLog.getSddept_desc());
        mWorkOrder.setZydate(mWorkLog.getZrdept());
        mWorkOrder.setZydept_desc(mWorkLog.getZrdept_desc());

    }

    /**
     * ?????????????????????
     */
    private void GetNetPic(String rzid) {
        // ????????????????????????????????????
        final ProgressDialog dialog = new ProgressDialog(
                SupervisionDetailActivity.this, true, "???????????????????????????...");

        GetPicInfor record = new GetPicInfor(rzid, "appcbsrz");

        BusinessAction action = new BusinessAction(record);

        BusinessAsyncTask asyncTask = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {
                    @Override
                    public void start(Bundle msgData) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        /*
                         * try { dialog.dismiss(); } catch (Exception e) {
                         * logger.error(e); }
                         */
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        ToastUtils.imgToast(
                                SupervisionDetailActivity.this,
                                R.drawable.hd_hse_common_msg_wrong,
                                msg.getMessage().contains("????????????") ? msg
                                        .getMessage() : "????????????????????????????????????????????????");

                    }

                    @Override
                    public void end(Bundle msgData) {
                        // TODO Auto-generated method stub
                        /*
                         * try { dialog.dismiss(); } catch (Exception e) {
                         * logger.error(e); }
                         */
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        ArrayList<Image> images = (ArrayList<Image>) msg
                                .getReturnResult();
                        if (images != null && images.size() > 0) {
                            mImageLoader = new ImageLoader(images, mWorkLog.getPadrzid());
                            mImageLoader.setImageCallback(new MyImageCallback());
                            mImageLoader.execute();
                        }

                    }
                });

        try {
            asyncTask.execute(IActionType.WEB_INOROUTRECORD, "15");
            /*
             * if (dialog != null) { dialog.show(); }
             */
        } catch (HDException e) {
            ToastUtils.imgToast(SupervisionDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
            /*
             * try { dialog.dismiss(); } catch (Exception e1) {
             * logger.error(e1); }
             */
        }

    }

    private class MyImageCallback implements ImageCallback {

        @Override
        public void onImageLoaded(Image img) {
            saveImgToDB(img);
        }

        @Override
        public void onImageLoadErr(Image img) {
            ToastUtils.toast(getApplicationContext(), "????????????????????????");
        }

        @Override
        public void onImageAllLoaded() {
            showPic();
        }

    }


    /**
     * ??????????????? ??????????????????
     */
    private void saveImgToDB(Image image) {
        Image tempImage = null;
        BusinessAction businessAction = null;
        try {
            businessAction = new BusinessAction();
            tempImage = (Image) businessAction.addEntity(Image.class);
            tempImage.setAttribute("tablename", "UD_CBSGL_RZ");
            tempImage.setAttribute("tableid", mWorkLog.getPadrzid());
            // tempImage.setAttribute("zysqid", image.getZysqid());
            tempImage.setAttribute("imagepath", SystemProperty
                    .getSystemProperty().getRootDBpath()
                    + File.separator
                    + mWorkLog.getPadrzid() + "/" + image.getImagename());
            tempImage.setAttribute("imagename", image.getImagename());
            tempImage.setAttribute("createdate", UpWtdjInfo.ISLOADED);
            /*
             * tempImage.setAttribute("createuser", "sda");
             * tempImage.setAttribute("createusername", "asd");
             */
            tempImage.setAttribute("funcode", "wtdj");
            tempImage.setAttribute("contype", 0);
            businessAction.saveEntity(tempImage);

        } catch (HDException ex) {
            // TODO
        }

    }

    /**
     * ??????worklog????????????
     */
    private void fillUi(WorkLogEntry mWorkLog) {
        mJcdeptTxt.setText(mWorkLog.getJcdept_desc());
        mJcPersonTxt.setText(mWorkLog.getCreatebydesc());
        mJcTimeTxt.setText(mWorkLog.getCreatedate());
        mWorkOrderTxt.setText(mWorkLog.getZyname());
        mZyareaBt.setText(mWorkLog.getZyarea_desc());
        mSddeptBt.setText(mWorkLog.getSddept_desc());
        if (mWorkLog.getRztype() != null) {
            String problemtypes[] = mWorkLog.getRztype().split(",");
            if (mGridViewCheckBoxAdapter != null) {
                HashMap<Integer, Boolean> check = mGridViewCheckBoxAdapter
                        .getCheck();
                for (int i = 0; i < problemtypes.length; i++) {
                    for (int j = 0; j < problemTypeStr.size(); j++) {
                        Domain mDomain = problemTypeStr.get(j);
                        if (mDomain.getValue().equals(problemtypes[i])) {
                            check.put(j, true);
                            break;
                        }
                    }
                }
                if (liZrgs.getVisibility() == View.VISIBLE) {
                    if (check.get(0)) {

                        String s = "PENALTYPOINT";
                        boolean flag = false;
                        if (codes != null) {
                            for (int w = 0; w < codes.length; w++) {
                                if (codes[w].toLowerCase().equals(s.toLowerCase())) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                penalty_pointli.setVisibility(View.VISIBLE);
                            }
                        } else {
                            //????????????????????????
                            penalty_pointli.setVisibility(View.VISIBLE);
                        }

                        if (mWorkLog.getPenaltypoint() != null) {
                            penaltyPointEd.setText(mWorkLog.getPenaltypoint());
                        }
                    } else if (check.get(0) == false) {
                        penalty_pointli.setVisibility(View.GONE);
                    }
                }

                mGridViewCheckBoxAdapter.notifyDataSetChanged();

            }
        }

        isLoadkhd.setChecked(mWorkLog.getIskhd() == 0 ? false : true);
        if (mWorkLog.getIskhd() == 0) {
            examYjTv.setText("??????????????????");
        } else {
            examYjTv.setText("*??????????????????");
        }
        examYj.setText(mWorkLog.getExamyj());
        problemDescEd.setText(mWorkLog.getFxwt());
        liveFzrEd.setText(mWorkLog.getFzperson_desc());
        wzPersonEd.setText(mWorkLog.getRulesdescription());
        fkMoneyEd.setText(mWorkLog.getWyj());
        zrDeptEd.setText(mWorkLog.getZrdept_desc());
        siteEd.setText(mWorkLog.getBreachlocation());
        zgRadioGruop.check(mWorkLog.getIszg() == 1 ? R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes : R.id.hse_pc_phone_app_layout_surveillance_radiobutton_no);
        checkCheckBox.setChecked(mWorkLog.getIsys() == 1 ? true : false);
        typicpCheckBox.setChecked(mWorkLog.getIstypicissue() == 1 ? true : false);
        worktypepbCheckBox.setChecked(mWorkLog.getIsddh() == 1 ? true : false);

        blacklistCheckBox.setChecked(mWorkLog.getIshmd() == 1 ? true : false);
        if (mWorkLog.getZgcs() != null) {
            zgcsEd.setText(mWorkLog.getZgcs());
        }

        List<Domain> domains = null;
        try {
            domains = queryWorkInfo.queryDomain(WZLBALN, mWorkLog.getWzlb(), null);
        } catch (HDException e) {
            e.printStackTrace();
        }
        if (domains != null && domains.size() > 0) {
            edWzlx.setText(domains.get(0).getDescription());
        } else {
            edWzlx.setText("");
        }

        edWztk.setText(mWorkLog.getWztk());
        if (mWorkLog.getZgyzjl() != null) {
            zgyzjlEd.setText(mWorkLog.getZgyzjl());
        }
        if (mWorkLog.getReform_date() != null) {
            reformDateTxt.setText(mWorkLog.getReform_date());
        }

    }


    private void initSpiner() {
        if (blacklistCheckBox.isChecked()) {
            edLrRemark.setText(mWorkLog.getRemarks());
        }

        QueryWorkInfo mQueryWorkInfo = new QueryWorkInfo();
        try {
            mDomains = mQueryWorkInfo.queryDomain("UDRYJRYY", null);
            myArrayAdapter = new MyArrayAdapter(this, mDomains);
            spJrReason.setAdapter(myArrayAdapter);

            if (blacklistCheckBox.isChecked()) {
                String rejectreason = mWorkLog.getRejectreason();
                logger.error("rejectreason:" + rejectreason);
                for (int i = 0; i < mDomains.size(); i++) {
                    if (rejectreason != null
                            && rejectreason.equals(mDomains.get(i).getValue())) {
                        spJrReason.setSelection(i, true);
                        break;
                    }
                }
            }
        } catch (HDException e) {
            e.printStackTrace();
        }

    }

    /**
     * ??????WorkLog
     */
    private void newWorkLog() {
        mWorkLog = new WorkLogEntry();
        try {
            SequenceGenerator
                    .genPrimaryKeyValue(new SuperEntity[]{mWorkLog});
            mWorkLog.setUd_cbsgl_rzid(mWorkLog.getPadrzid());
        } catch (DaoException e) {
            logger.error(e);
            ToastUtils.imgToast(SupervisionDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
        }
        mWorkLog.setJcdept(SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment());

        mWorkLog.setJcdept_desc(SystemProperty.getSystemProperty()
                .getLoginPerson().getDepartment_desc());
        mWorkLog.setCreateby(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid());
        mWorkLog.setCreatebydesc(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        String time = SystemProperty.getSystemProperty().getSysDateTime();
        mWorkLog.setCreatedate(time);
        mJcdeptTxt.setText(SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment_desc());
        mJcPersonTxt.setText(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        mJcTimeTxt.setText(time);
    }

    private void setWorkOrderInfo(WorkOrder workOrder) {
        mWorkLog.setUd_zyxk_zysqid(workOrder.getUd_zyxk_zysqid());
        mWorkLog.setZyarea(workOrder.getZyarea());
        mWorkLog.setZyarea_desc(workOrder.getZyarea_desc());
        mWorkLog.setSddept(workOrder.getSddept());
        mWorkLog.setSddept_desc(workOrder.getSddept_desc());
        mWorkLog.setZrdept(workOrder.getZydept());
        mWorkLog.setBreachlocation(workOrder.getZysite());
        String zydept_desc = zrDeptEd.getText().toString().trim();
        if (zydept_desc != null && !"".equals(zydept_desc)
                && !zydept_desc.equals(workOrder.getZydept_desc())) {
            ToastUtils.toast(this, "???????????????????????????????????????????????????????????????????????????,???????????????????????????");
            isSelected = false;
        }
        mWorkLog.setZrdept_desc(workOrder.getZydept_desc());
        mWorkLog.setZyname(workOrder.getZyname());
        mWorkOrderTxt.setText(workOrder.getZyname());
        mZyareaBt.setText(workOrder.getZyarea_desc());
        mSddeptBt.setText(workOrder.getSddept_desc());
        zrDeptEd.setText(workOrder.getZydept_desc());
        siteEd.setText(workOrder.getZysite());
    }

    private void goToNextActivity() {
        Intent intent = new Intent(SupervisionDetailActivity.this,
                TaskTabulationActivity.class);
        String sddept = mWorkLog.getSddept();

        //?????????????????????????????????
        if (sddept != null && !sddept.equals("")) {
            intent.putExtra(SupervisionDetailActivity.class.getClass().getName(), sddept);
        }
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mWorkOrder = (WorkOrder) data.getSerializableExtra(WorkOrder.class
                    .getName());
            setWorkOrderInfo(mWorkOrder);
        } else if (requestCode == REQUESTCODEONE
                && resultCode == READCARDEXAMINEACTIVITYCODE) {
            // ???????????????????????????
            ArrayList<PersonCard> mPersonCards = (ArrayList<PersonCard>) data
                    .getSerializableExtra(PersonCard.class.getName());
            if (mPersonCards != null && mPersonCards.size() != 0) {
                PersonCard card = mPersonCards.get(0);
                mWorkLog.setFzperson(card.getPersonid());
                mWorkLog.setFzperson_desc(card.getPersonid_desc());
                liveFzrEd.setText(mWorkLog.getFzperson_desc());
                String zydept_desc = zrDeptEd.getText().toString().trim();
                if (zydept_desc != null && !"".equals(zydept_desc)
                        && !zydept_desc.equals(card.getDepartment_desc())
                        && !isSelected) {
                    ToastUtils.toast(this,
                            "???????????????????????????????????????????????????????????????????????????,???????????????????????????");
                } else {
                    zrDeptEd.setText(card.getDepartment_desc());
                    mWorkLog.setZrdept_desc(card.getDepartment_desc());
                    mWorkLog.setZrdept(card.getDepartment());
                    isSelected = false;
                }

            }
        } else if (requestCode == REQUESTCODETWO
                && resultCode == READCARDEXAMINEACTIVITYCODE) {
            // ????????????????????????
            ArrayList<PersonCard> mPersonCards = (ArrayList<PersonCard>) data
                    .getSerializableExtra(PersonCard.class.getName());
            if (mPersonCards != null && mPersonCards.size() != 0) {
                String wzry = mWorkLog.getInnercard();
                String wzrydesc = mWorkLog.getRulesdescription();
                for (PersonCard personCard : mPersonCards) {
                    if (wzry != null && wzry.length() != 0) {
                        wzry += "," + personCard.getPersonid();
                    } else {
                        wzry = personCard.getPersonid();
                    }
                    if (wzrydesc != null && wzrydesc.length() != 0) {
                        wzrydesc += "," + personCard.getPersonid_desc();
                    } else {
                        wzrydesc = personCard.getPersonid_desc();
                    }
                }
                mWorkLog.setInnercard(wzry);
                mWorkLog.setRulesdescription(wzrydesc);
                wzPersonEd.setText(wzrydesc);
            }
        }else if ((requestCode == REQUESTCODETHREE||requestCode==REQUESTCODEFOUR||
                requestCode==REQUESTCODEFIVE||requestCode==REQUESTCODESIX)
                && resultCode == READCARDEXAMINEACTIVITYCODE) {
            // ????????????????????????
            ArrayList<PersonCard> mPersonCards = (ArrayList<PersonCard>) data
                    .getSerializableExtra(PersonCard.class.getName());
            if (mPersonCards != null && mPersonCards.size() != 0) {

                PersonCard card = mPersonCards.get(0);
                if (requestCode==REQUESTCODETHREE){
                    mWorkLog.setCbszjzrr(card.getPersonid());
                    mWorkLog.setCbszjzrr_desc(card.getPersonid_desc());
                    sgdwzrrEt.setText(mWorkLog.getCbszjzrr_desc());
                }else if (requestCode==REQUESTCODEFOUR){
                    mWorkLog.setCbsjhr(card.getPersonid());
                    mWorkLog.setCbsjhr_desc(card.getPersonid_desc());
                    sgdwzyjhrEt.setText(mWorkLog.getCbsjhr_desc());
                }else if (requestCode==REQUESTCODEFIVE){
                    mWorkLog.setCbsaqglry(card.getPersonid());
                    mWorkLog.setCbsaqglry_desc(card.getPersonid_desc());
                    sgdwxmaqyEt.setText(mWorkLog.getCbsaqglry_desc());
                }else if (requestCode==REQUESTCODESIX){
                    mWorkLog.setCbsxmfzr(card.getPersonid());
                    mWorkLog.setCbsxmfzr_desc(card.getPersonid_desc());
                    sgdwxmfzrEt.setText(mWorkLog.getCbsxmfzr_desc());
                }



            }
        }

    }

    /**
     * ??????img?????????
     */
    private int getImgWidth() {
        int screenWidth, imgWidth;
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels; // ????????????????????????
        Resources res = getResources();
        // ????????????????????????
        float margin = res
                .getDimension(R.dimen.hse_pc_phone_app_layout_surveillance_padding);
        // img???????????????

        float imgMargin = res
                .getDimension(R.dimen.hse_pc_phone_app_fragment_layout_surveillance_img_marginright);
        imgWidth = (int) ((screenWidth - 2 * margin - 3 * imgMargin) / 3.5);
        return imgWidth;
    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        if (isFastDoubleClick(eventType)) {
            return;
        }
        if (eventType == IEventType.ACTIONBAR_BT_SAVE_CLICK) {
            // ??????
            if (isSetDataComplete()) {
                if (saveWorkLog(mWorkLog)) {
                    ToastUtils.toast(SupervisionDetailActivity.this, "????????????");
                }
            }
        } else if (eventType == IEventType.ACTIONBAR_BT_UP_CLICK) {
            // ??????
            if (isSetDataComplete()) {
                if (saveWorkLog(mWorkLog)) {
                    upWorkLog(mWorkLog);
                }

            }
        }

    }

    /**
     * ???mWorkLog????????????
     *
     * @return mWorkLog
     */
    private boolean isSetDataComplete() {

        mWorkLog.setLlbj("APPCBSRZ");
        mWorkLog.setJcdept(SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment());
        mWorkLog.setJcdept_desc(SystemProperty.getSystemProperty()
                .getLoginPerson().getDepartment_desc());
        mWorkLog.setCreateby(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid());
        mWorkLog.setCreatebydesc(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        String time = SystemProperty.getSystemProperty().getSysDateTime();
        mWorkLog.setCreatedate(time);

        Map<Integer, Boolean> check;
        StringBuilder problemtype = new StringBuilder();
        if (mGridViewCheckBoxAdapter != null) {
            check = mGridViewCheckBoxAdapter.getCheck();
            // ??????????????????????????????????????????????????????
            for (int i = 0; i < check.size(); i++) {
                if (check.get(i)) {
                    if (problemtype.length() != 0) {
                        problemtype.append("," + problemTypeStr.get(i).getValue());
                    } else {
                        problemtype.append(problemTypeStr.get(i).getValue());
                    }

                }
            }

            // ??????????????????????????????????????????????????????
            for (int i = 0; i < check.size(); i++) {
                if (check.get(0)) {
                    if (penalty_pointli.getVisibility() == View.VISIBLE) {
                        if (penaltyPointEd.getText().toString() == null ||
                                ("").equals(penaltyPointEd.getText().toString())) {
                            ToastUtils.toast(this, "???????????????");
                            return false;
                        }
                    }

                }
            }
        }

        String fxwt = problemDescEd.getText().toString();

        if (mJcdeptTxt.getText().toString() == null
                || mJcdeptTxt.getText().toString().equals("")) {
            ToastUtils.toast(this, "???????????????????????????");
            return false;
        } else if (mJcPersonTxt.getText().toString() == null
                || mJcPersonTxt.getText().toString().equals("")) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (mJcTimeTxt.getText().toString() == null
                || mJcTimeTxt.getText().toString().equals("")) {
            ToastUtils.toast(this, "????????????????????????");
            return false;
        } else if (liSdbm.getVisibility() == View.VISIBLE
                && (mZyareaBt.getText().toString() == null || mZyareaBt
                .getText().toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liSddw.getVisibility() == View.VISIBLE
                && (mSddeptBt.getText().toString() == null || mSddeptBt
                .getText().toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liZrgs.getVisibility() == View.VISIBLE
                && problemtype.length() <= 0) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liWzlb.getVisibility() == View.VISIBLE
                && (edWzlx.getText().toString() == null || edWzlx.getText()
                .toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        }

        // ???????????????
        else if (liWztk.getVisibility() == View.VISIBLE
                && (edWztk.getText().toString() == null || edWztk.getText()
                .toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liWtms.getVisibility() == View.VISIBLE
                && (fxwt == null || "".equals(fxwt))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liTimeLi.getVisibility() == View.VISIBLE &&
                (reformDateTxt.getText().toString() == null ||
                        ("").equals(reformDateTxt.getText().toString()))) {

            ToastUtils.toast(this, "???????????????????????????");
            return false;

        } else if (liTimeLi.getVisibility() == View.VISIBLE &&
                (reformDateTxt.getText().toString() != null ||
                        !("").equals(reformDateTxt.getText().toString()))) {


            long selectTime = parseDateToMillis(reformDateTxt.getText().toString());
            if (selectTime < ServerDateManager.getCurrentTimeMillis()) {
                ToastUtils.toast(this, "??????????????????????????????????????????");
                return false;
            }


        } else if (isLoadkhd.isChecked()) {

            if (liKhbzyj.getVisibility() == View.VISIBLE
                    && (examYj.getText().toString() == null || examYj.getText()
                    .toString().equals(""))) {
                ToastUtils.toast(this, "???????????????????????????");
                return false;
            } else if (liZrdw.getVisibility() == View.VISIBLE
                    && (zrDeptEd.getText().toString() == null || zrDeptEd
                    .getText().toString().equals(""))) {
                ToastUtils.toast(this, "?????????????????????");
                return false;

            }
        } else if (liZrdw.getVisibility() == View.VISIBLE
                && (zrDeptEd.getText().toString() == null || zrDeptEd.getText()
                .toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        } else if (liSite.getVisibility() == View.VISIBLE
                && (siteEd.getText().toString() == null || siteEd.getText()
                .toString().equals(""))) {
            ToastUtils.toast(this, "?????????????????????");
            return false;
        }


        mWorkLog.setIskhd(isLoadkhd.isChecked() ? 1 : 0);
        mWorkLog.setExamyj(examYj.getText().toString());
        mWorkLog.setFxwt(fxwt);
        mWorkLog.setPenaltypoint(penaltyPointEd.getText().toString());
        mWorkLog.setIstypicissue(typicpCheckBox.isChecked() ? 1 : 0);
        mWorkLog.setIsddh(worktypepbCheckBox.isChecked() ? 1 : 0);
        mWorkLog.setIsys(checkCheckBox.isChecked() ? 1 : 0);


        mWorkLog.setReform_date(reformDateTxt.getText().toString());

        if (zgRadioGruop.getCheckedRadioButtonId()==R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes) {
            if (zgcsLi.getVisibility() == View.VISIBLE) {
                if (zgcsEd.getText().toString() == null ||
                        ("").equals(zgcsEd.getText().toString())) {
                    ToastUtils.toast(this, "?????????????????????");
                    return false;
                } else {
                    mWorkLog.setZgcs(zgcsEd.getText().toString());
                }
            }

        }

        if (checkCheckBox.isChecked()) {
            if (zgyzjlLi.getVisibility() == View.VISIBLE) {
                if (zgyzjlEd.getText().toString() == null ||
                        ("").equals(zgyzjlEd.getText().toString())) {
                    ToastUtils.toast(this, "???????????????????????????");
                    return false;
                } else {
                    mWorkLog.setZgyzjl(zgyzjlEd.getText().toString());
                }
            }

        }
        String fkje = fkMoneyEd.getText().toString();
        mWorkLog.setWyj(fkje);
        String sdbm = mZyareaBt.getText().toString();
        mWorkLog.setZyarea_desc(sdbm);
        String sddw = mSddeptBt.getText().toString();
        mWorkLog.setSddept_desc(sddw);

        mWorkLog.setRztype(problemtype.toString());
        mWorkLog.setIszg(zgRadioGruop.getCheckedRadioButtonId()==R.id.hse_pc_phone_app_layout_surveillance_radiobutton_yes ? 1 : 0);
        mWorkLog.setIshmd(blacklistCheckBox.isChecked() ? 1 : 0);
//        mWorkLog.setWzlb(edWzlx.getText().toString());
//        mWorkLog.setWztk(edWztk.getText().toString());
        if (blacklistCheckBox.isChecked()) {
            if (myArrayAdapter != null) {
                mWorkLog.setRejectreason(myArrayAdapter.getselectedDomain()
                        .getValue());
                logger.error("getselectedDomain:"
                        + myArrayAdapter.getselectedDomain().getValue());
            }
            String remarks = edLrRemark.getText().toString();
            mWorkLog.setRemarks(remarks);

        }
        // ????????????
        mWorkLog.setZrdept_desc(zrDeptEd.getText().toString().trim());
        mWorkLog.setBreachlocation(siteEd.getText().toString().trim());
        return true;
    }

    private long parseDateToMillis(String sjendtime) {
        // ???????????????
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds = 0;//??????
        try {
            millionSeconds = sdf.parse(sjendtime).getTime();
        } catch (ParseException e) {

        }
        return millionSeconds;
    }

    private List<PopMenuItem> getListViewItem() {
        List<PopMenuItem> lstMenuItem = new ArrayList<PopMenuItem>();

        // ??????
        PopMenuItem workOrderDown = new PopMenuItem();
        workOrderDown.setDescription(itemNames[0]);
        workOrderDown
                .setDrawable(R.drawable.hse_pc_phone_app_layout_surveillance_save);
        lstMenuItem.add(workOrderDown);

        // ??????
        PopMenuItem workOrderUp = new PopMenuItem();
        workOrderUp.setDescription(itemNames[1]);
        workOrderUp
                .setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_upload);
        lstMenuItem.add(workOrderUp);
        return lstMenuItem;
    }

    private OnItemClickListener itemClick = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> list, View view, int position,
                                long arg3) {

            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
            // ??????
            if (position == 0) {
                if (isSetDataComplete()) {
                    if (saveWorkLog(mWorkLog)) {
                        ToastUtils
                                .toast(SupervisionDetailActivity.this, "????????????");
                    }
                }
            }
            // ??????
            else if (position == 1) {
                if (isSetDataComplete()) {
                    if (saveWorkLog(mWorkLog)) {
                        upWorkLog(mWorkLog);
                    }

                }
            }
        }
    };

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.zyp_re_iamge) {
            goToNextActivity();
        } else if (id == R.id.hse_pc_phone_app_layout_surveillance_ed_leader) {
            // ???????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "???????????????");
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            i.putExtra(EDITTEXTISFOCUSABLE, true);

            startActivityForResult(i, REQUESTCODEONE);

        }else if (id==R.id.hse_pc_phone_app_layout_sgdwzrr_et){
            // ???????????????????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "???????????????????????????");
            i.putExtra(EDITTEXTISFOCUSABLE, false);
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            startActivityForResult(i, REQUESTCODETHREE);
        }else if (id==R.id.hse_pc_phone_app_layout_sgdwzyjhr_et){
            // ???????????????????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "???????????????????????????");
            i.putExtra(EDITTEXTISFOCUSABLE, false);
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            startActivityForResult(i, REQUESTCODEFOUR);
        }else if (id==R.id.hse_pc_phone_app_layout_sgdwxmaqy_et){
            // ???????????????????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "???????????????????????????");
            i.putExtra(EDITTEXTISFOCUSABLE, false);
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            startActivityForResult(i, REQUESTCODEFIVE);
        }else if (id==R.id.hse_pc_phone_app_layout_sgdwxmfzr_et){
            // ???????????????????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "???????????????????????????");
            i.putExtra(EDITTEXTISFOCUSABLE, false);
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            startActivityForResult(i, REQUESTCODESIX);
        }
        else if (id == R.id.hse_pc_phone_app_layout_surveillance_ed_illegal_person) {
            // ????????????
            Intent i = new Intent(this, ReadCardXcjdActivity.class);
            i.putExtra(NAME, "????????????");
            i.putExtra(EDITTEXTISFOCUSABLE, true);
            i.putExtra(ISSINGLEPERSON, SINGLEPERSON);
            startActivityForResult(i, REQUESTCODETWO);
        } else if (id == R.id.sd_re_image) {

            boolean qy = getSysRelationConfig();

            // ??????????????????????????????????????????????????????????????????????????????
            // ??????,??????????????????isfilter??????

            // ????????????

            try {
                depts = queryWorkInfo.queryDept(false, null);
                if (qy) {
                    // ??????????????????
                    for (int i = 0; i < depts.size(); i++) {

                        Department dept = depts.get(i);
                        String parent = dept.getParent();
                        int isfilter = dept.getIsfilter();
                        // ??????1???????????????
                        if (isfilter == 1
                                || (null != parent && "A11".equals(parent))) {
                            depts.remove(dept);
                            i--;
                        }

                    }
                    depts = filterDept(depts);

                    for (int i = 0; i < depts.size(); i++) {
                        Department dept = depts.get(i);
                        int layer = dept.getLayer();
                        //???????????????????????????
                        if (layer != 2) {
                            depts.remove(dept);
                            i--;
                        }
                    }
                } else {
                    // ??????????????????
                    for (int i = 0; i < depts.size(); i++) {
                        Department dept = depts.get(i);
                        String parent = dept.getParent();

                        if (null != parent && "A11".equals(parent)) {
                            depts.remove(dept);
                            i--;
                        }

                    }
                    depts = filterDept(depts);

                    for (int i = 0; i < depts.size(); i++) {
                        Department dept = depts.get(i);
                        int layer = dept.getLayer();
                        //???????????????????????????
                        if (layer != 2) {
                            depts.remove(dept);
                            i--;
                        }
                    }
                }

            } catch (HDException e) {
                ToastUtils.imgToast(SupervisionDetailActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }

            showDeptDialog(depts, mZyareaBt);

        } else if (id == R.id.dw_re_image) {
            // ??????????????????????????????
            String deptnum = mWorkLog.getZyarea();
            if (null != deptnum && !"".equals(deptnum)) {
                // ????????????

                List<Department> sdDepts = new ArrayList<Department>();
                List<Department> tempDepts = new ArrayList<Department>();

                boolean qy = getSysRelationConfig();
                try {
                    depts = queryWorkInfo.queryDept(false, null);
                    if (qy) {
                        // ??????????????????
                        for (int i = 0; i < depts.size(); i++) {

                            Department dept = depts.get(i);
                            String parent = dept.getParent();
                            int isfilter = dept.getIsfilter();
                            // ??????1???????????????
                            if (isfilter == 1
                                    || (null != parent && "A11".equals(parent))) {
                                depts.remove(dept);
                                i--;
                            }

                        }
                        depts = filterDept(depts);


                    } else {
                        // ??????????????????
                        for (int i = 0; i < depts.size(); i++) {
                            Department dept = depts.get(i);
                            String parent = dept.getParent();

                            if (null != parent && "A11".equals(parent)) {
                                depts.remove(dept);
                                i--;
                            }

                        }
                        depts = filterDept(depts);


                    }

                } catch (HDException e) {
                    ToastUtils.imgToast(SupervisionDetailActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }


                for (int i = 0; i < depts.size(); i++) {
                    String parentDeptnum = depts.get(i).getParent();
                    if (null != parentDeptnum && !"".equals(parentDeptnum)
                            && deptnum.equals(parentDeptnum)) {
                        tempDepts.add(depts.get(i));
                    }
                }

                if (tempDepts.size() == 0) {
                    Department dept = new Department();
                    dept.setDeptnum(deptnum);
                    dept.setDescription(mWorkLog.getZyarea_desc());
                    sdDepts.add(dept);
                } else {
                    sdDepts.addAll(tempDepts);
                    getDepts(sdDepts, depts, tempDepts);
                }

                /*
                 * List<Department> sdDepts = new ArrayList<Department>(); for
                 * (int i = 0; i < depts.size(); i++) { String childDeptnum =
                 * depts.get(i).getDeptnum(); if (null != childDeptnum &&
                 * !"".equals(childDeptnum) && childDeptnum.startsWith(deptnum))
                 * { sdDepts.add(depts.get(i)); } }
                 */

                showDeptDialog(sdDepts, mSddeptBt);

            } else {
                ToastUtils.toast(SupervisionDetailActivity.this, "????????????????????????");
            }

        } else if (id == R.id.hse_pc_phone_app_layout_surveillance_bt_duty_company) {
            // ????????????
            if (zrDepts == null) {
                try {
                    zrDepts = queryWorkInfo.queryDept(false, null);
                    if (zrDepts != null) {
                        zrDepts = filterDept(zrDepts);
                    }

                } catch (HDException e) {
                    ToastUtils.imgToast(SupervisionDetailActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
            showDeptDialog(zrDepts, zrDeptEd);
        } else if (id == R.id.hse_pc_phone_app_layout_surveillance_button_wzlx) {
            // ????????????
            if (wzlxDatas == null || wzlxDatas.size() <= 0) {
                try {
                    wzlxDatas = queryWorkInfo.queryDomain(WZLBALN, null);
                    if (null != wzlxDatas && wzlxDatas.size() > 0) {
                        showWzlxDialog(wzlxDatas, edWzlx);
                    } else {
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "????????????????????????");
                    }

                } catch (HDException e) {
                    ToastUtils
                            .toast(SupervisionDetailActivity.this, "????????????????????????");
                    e.printStackTrace();
                }
            } else {
                showWzlxDialog(wzlxDatas, edWzlx);
            }

        }

        // ??????????????????
        else if (id == R.id.hse_pc_phone_app_layout_surveillance_button_wztk) {
            // ????????????
            String wzlx = edWzlx.getText().toString().trim();
            if (null == wzlx || "".equals(wzlx)) {
                ToastUtils.toast(SupervisionDetailActivity.this, "?????????????????????");
            } else {
                // ????????????????????????????????????

                BaseDao dao = new BaseDao();
                String sql = "select * from ud_cbsgl_khbz where wzlb='" + mWorkLog.getWzlb()
                        + "'";
                try {
                    List<KhbzEntity> mKhbzEntitys = (List<KhbzEntity>) dao
                            .executeQuery(sql, new EntityListResult(
                                    KhbzEntity.class));
                    if (null != mKhbzEntitys && mKhbzEntitys.size() > 0) {
                        showWztkDialog(mKhbzEntitys, edWztk);
                    } else {
                        ToastUtils.toast(SupervisionDetailActivity.this,
                                "?????????????????????");
                    }
                } catch (DaoException e) {
                    ToastUtils
                            .toast(SupervisionDetailActivity.this, "????????????????????????");
                    e.printStackTrace();
                }

            }

        } else if (id == R.id.checkbox) {
            if (isLoadkhd.isChecked()) {
                examYjTv.setText("*??????????????????");
                isLoadkhd.setChecked(true);
            } else {
                isLoadkhd.setChecked(false);
                examYjTv.setText("??????????????????");
            }
        } else if (id == R.id.jl_re_image) {
            // ????????????
            clickRecordType();
        } else if (id == R.id.hse_pc_phone_app_layout_xqzg_tv_time) {
            //??????????????????
            String str = ((TextView) arg0).getText().toString();
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                    SupervisionDetailActivity.this, str);
            dateTimePicKDialog.dateTimePicKDialog(((TextView) arg0));
        }
    }

    /**
     * ??????????????????
     */
    private void clickRecordType() {
        if (recordTypes == null || recordTypes.size() == 0) {
            if (queryWorkInfo == null) {
                queryWorkInfo = new QueryWorkInfo();
            }
            try {
                recordTypes = queryWorkInfo.queryDomain("RECORDTYPE", null);
            } catch (HDException e) {
                e.printStackTrace();
            }
        }
        if (recordTypes == null || recordTypes.size() == 0) {
            ToastUtils.toast(getApplicationContext(),
                    "????????????????????????????????????????????????????????????????????????");
            return;
        }

        String[] descriptions = new String[recordTypes.size()];
        for (int i = 0; i < recordTypes.size(); i++) {
            descriptions[i] = recordTypes.get(i).getDescription();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("??????????????????");
        final String[] items = descriptions;
        mWorkLog.getRecordtype();
        int position = 0;
        for (int i = 0; i < recordTypes.size(); i++) {
            if (recordTypes.get(i).getValue().equals(mWorkLog.getRecordtype())) {
                position = i;
            }
        }
        builder.setSingleChoiceItems(items, position,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        btRecordType.setText(items[arg1]);
                        mWorkLog.setRecordtype(recordTypes.get(arg1).getValue());
                        if (recordTypes.get(arg1).getValue().equals(PROBLEM)) {
                            selectQuestion();
                        } else if (recordTypes.get(arg1).getValue()
                                .equals(TOCHECK)) {
                            selectDaojian();
                        } else if (recordTypes.get(arg1).getValue()
                                .equals(PRAISE)) {
                            selectPraise();
                        }
                        arg0.dismiss();
                    }
                });
        builder.create().show();
    }

    private boolean getSysRelationConfig() {
        String sql = "select * from sys_relation_info where sys_type = '"
                + IRelativeEncoding.AQJDDEPTGL + "'";
        BaseDao dao = new BaseDao();
        try {
            RelationTableName mRelationTableName = (RelationTableName) dao
                    .executeQuery(sql,
                            new EntityResult(RelationTableName.class));
            if (mRelationTableName != null && mRelationTableName.getIsqy() == 1) {
                // ???????????????
                return true;
            } else if (mRelationTableName != null
                    && mRelationTableName.getIsqy() == 0) {
                // ??????????????????
                return false;
            }
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private void getDepts(List<Department> sdDepts, List<Department> depts,
                          List<Department> tempDepts2) {
        ArrayList<Department> tempDepts = new ArrayList<Department>();
        for (int i = 0; i < tempDepts2.size(); i++) {
            String deptnum = tempDepts2.get(i).getDeptnum();
            for (int j = 0; j < depts.size(); j++) {
                String parentDeptnum = depts.get(j).getParent();
                if (null != parentDeptnum && !"".equals(parentDeptnum)
                        && deptnum.equals(parentDeptnum)) {
                    tempDepts.add(depts.get(j));
                }
            }
        }
        if (tempDepts.size() > 0) {
            sdDepts.addAll(tempDepts);
            getDepts(sdDepts, depts, tempDepts);
        }

    }

    /**
     * ?????????????????????dialog
     */
    private void showWzlxDialog(final List<Domain> datas, final EditText ed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("??????????????????");
        View view = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_wov_dept_dialog_layout, null);
        RelativeLayout parent = (RelativeLayout) view
                .findViewById(R.id.search_txt_parent);
        parent.setVisibility(View.GONE);
        ListView listView = (ListView) view.findViewById(R.id.data_listview);
        final WzlxAdapter adapter = new WzlxAdapter(
                SupervisionDetailActivity.this, datas);
        listView.setAdapter(adapter);

        builder.setView(view).create();
        final AlertDialog dialog = builder.show();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // choiceDept = searchDatas.get(arg2);
                ed.setText(datas.get(arg2).getDescription());
                // ??????????????????
                mWorkLog.setWzlb(datas.get(arg2).getValue());
                // ?????????????????????
                edWztk.setText("");
                mWorkLog.setWztk(null);
                mWorkLog.setFxjb(null);
                mWorkLog.setUd_cbsgl_khbzid(null);
                mWorkLog.setWzfxz(null);

                dialog.cancel();
            }
        });
    }

    /**
     * ?????????????????????dialog
     */
    private void showWztkDialog(final List<KhbzEntity> datas, final EditText ed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("??????????????????");
        View view = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_wov_dept_dialog_layout, null);
        RelativeLayout parent = (RelativeLayout) view
                .findViewById(R.id.search_txt_parent);
        parent.setVisibility(View.GONE);
        ListView listView = (ListView) view.findViewById(R.id.data_listview);
        final WztkAdapter adapter = new WztkAdapter(
                SupervisionDetailActivity.this, datas);
        listView.setAdapter(adapter);

        builder.setView(view).create();
        final AlertDialog dialog = builder.show();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // choiceDept = searchDatas.get(arg2);
                ed.setText(datas.get(arg2).getWztk());
                // ??????????????????
                mWorkLog.setFxjb(datas.get(arg2).getFxjb());
                mWorkLog.setUd_cbsgl_khbzid(datas.get(arg2)
                        .getUd_cbsgl_khbzid());
                mWorkLog.setWzfxz(datas.get(arg2).getWzfxz());
                mWorkLog.setWzlb(datas.get(arg2).getWzlb());
                mWorkLog.setWztk(datas.get(arg2).getWztk());
                dialog.cancel();
            }
        });
    }

    /**
     * ????????????????????????dialog
     *
     * @param datas
     * @param ed
     */
    private void showDeptDialog(final List<Department> datas, final TextView ed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final List<Department> searchDatas = new ArrayList<>();
        searchDatas.addAll(datas);
        builder.setTitle("????????????");
        View view = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_wov_dept_dialog_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.data_listview);
        final EditText searchTxt = (EditText) view
                .findViewById(R.id.search_txt);
        final DeptListAdapter2 adapter = new DeptListAdapter2(this, searchDatas);
        listView.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.search_btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = searchTxt.getText().toString();
                if (str != null && !str.equals("")) {
                    searchDatas.clear();
                    for (Department d : datas) {
                        if (d.getDescription().contains(str)) {
                            searchDatas.add(d);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    searchDatas.clear();
                    searchDatas.addAll(datas);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setView(view).create();
        final AlertDialog dialog = builder.show();
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // choiceDept = searchDatas.get(arg2);
                ed.setText(searchDatas.get(arg2).getDescription());
                if (ed.equals(mZyareaBt)) {
                    // ????????????
                    mWorkLog.setZyarea(searchDatas.get(arg2).getDeptnum());
                    mWorkLog.setZyarea_desc(searchDatas.get(arg2)
                            .getDescription());
                    // ????????????????????????
                    mSddeptBt.setText("");
                    mWorkLog.setSddept(null);
                    mWorkLog.setSddept_desc(null);

                } else if (ed.equals(mSddeptBt)) {
                    // ????????????
                    mWorkLog.setSddept(searchDatas.get(arg2).getDeptnum());
                    mWorkLog.setSddept_desc(searchDatas.get(arg2)
                            .getDescription());
                } else if (ed.equals(zrDeptEd)) {
                    // ????????????
                    mWorkLog.setZrdept(searchDatas.get(arg2).getDeptnum());
                    mWorkLog.setZrdept_desc(searchDatas.get(arg2)
                            .getDescription());
                    isSelected = true;
                }
                dialog.cancel();
            }
        });
    }

    private void touchImages(String name) {
        // ??????Image
        Image image = new Image();
        image.setTablename("UD_CBSGL_RZ");// ??????
        image.setTableid(mWorkLog.getPadrzid());// ?????????
        image.setImagepath(SystemProperty.getSystemProperty().getRootDBpath()
                + File.separator + mWorkLog.getPadrzid());// ???????????????,???????????????id???????????????

        image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
                .getPersonid());// ?????????
        image.setCreateusername(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        // PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig)
        // currentNaviTouchEntity;
        // image.setFuncode(config.getPscode());// ????????????
        // image.setContype(config.getContype());
        image.setFuncode("wtdj");// ????????????
        image.setContype(0);
        image.setImagename(name);
        Intent intent = new Intent(getBaseContext(),
                CameraCaptureActivity.class);
        intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
        startActivity(intent);
    }

    // ?????????mLayoutParams
    private RelativeLayout.LayoutParams getLayoutParams(int width) {

        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(
                width, width);
        Resources res = getResources();
        // float imgMargin =
        // res.getDimension(R.dimen.hse_pc_phone_app_fragment_layout_surveillance_img_marginright);
        mLayoutParams.setMargins(0, 0, 5, 0);
        return mLayoutParams;
    }

    private LinearLayout.LayoutParams getReLayoutParams(int width) {
        // int imgMargin =
        // getResources().getDimensionPixelOffset(R.dimen.hse_pc_phone_app_fragment_layout_surveillance_img_marginright);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                width, width);
        return mLayoutParams;
    }

    // ????????????listview???LayoutParams
    private LayoutParams getLvLayoutParams(int width) {

        LayoutParams mLayoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, width);
        int margin = getResources().getDimensionPixelOffset(
                R.dimen.hse_pc_phone_app_layout_surveillance_padding);
        mLayoutParams.setMargins(margin, margin, margin, margin);
        return mLayoutParams;
    }

    /**
     * ?????????????????? Created by liuyang on 2016???3???24???
     *
     * @param workLog
     */
    private boolean saveWorkLog(WorkLogEntry workLog) {
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            if (!isSave) {
                dao.insertEntity(con, workLog);
            } else {
                dao.updateEntity(con, workLog);
            }

            con.commit();
            isSave = true;
            return true;
        } catch (SQLException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "?????????????????????");
        } catch (DaoException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "????????????????????????");
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param entry
     */
    private void upWorkLog(WorkLogEntry entry) {
        final ProgressDialog dialog = new ProgressDialog(
                SupervisionDetailActivity.this, true, "????????????...");
        UpWtdjInfo worklog = new UpWtdjInfo();
        BusinessAction action = new BusinessAction(worklog);
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        showPic();
                        try {
                            dialog.dismiss();
                        } catch (Exception e) {
                            logger.error(e);
                        }
                        ToastUtils
                                .imgToast(SupervisionDetailActivity.this,
                                        R.drawable.hd_hse_common_msg_wrong,
                                        "???????????????????????????");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        try {
                            dialog.dismiss();
                            WorkLogEntry[] workLogs = new WorkLogEntry[1];
                            workLogs[0] = mWorkLog;
                            deleteWorkLogsFromDB(workLogs);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                        ToastUtils
                                .imgToast(SupervisionDetailActivity.this,
                                        R.drawable.hd_hse_common_msg_right,
                                        "???????????????????????????");
                        SupervisionDetailActivity.this.finish();
                    }
                });
        try {
            List<WorkLogEntry> data = new ArrayList<>();
            data.add(entry);
            task.execute("", data);
            if (dialog != null) {
                dialog.show();
            }
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.imgToast(SupervisionDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
        }
    }

    @Override
    protected void onResume() {

        showPic();
        super.onResume();
    }

    /**
     * ????????????
     */
    private void showPic() {
        if (mWorkLog != null) {
            try {
                imageList = (ArrayList<Image>) queryWorkInfo.queryPhoto(
                        mWorkLog, pdaConfig);
                imageListZgh.clear();
                imageListZgq.clear();
                imageListYsh.clear();
                for (int i = 0; i < imageList.size(); i++) {
                    if (imageList.get(i).getImagename().contains("?????????")) {
                        imageListZgq.add(imageList.get(i));

                    } else if (imageList.get(i).getImagename().contains("?????????")) {
                        imageListZgh.add(imageList.get(i));
                    } else if (imageList.get(i).getImagename().contains("?????????")) {
                        imageListYsh.add(imageList.get(i));
                    }
                }

                mImgAdapter.setDele(false);
                mImgAdapter.setShotCamraPostion(imageListZgq.size());
                mImgAdapter.notifyDataSetChanged();
                mImgAdapterZg.setDele(false);
                mImgAdapterZg.setShotCamraPostion(imageListZgh.size());
                mImgAdapterZg.notifyDataSetChanged();

                mImgAdapterYs.setDele(false);
                mImgAdapterYs.setShotCamraPostion(imageListYsh.size());
                mImgAdapterYs.notifyDataSetChanged();
            } catch (HDException e) {
                logger.error(e);
                ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                        "????????????????????????");
            }
        }
    }

    /**
     * @param <T>
     * @author yn ??????problemTypeStr????????????????????????checkbox,
     */
    class GridViewCheckBoxAdapter<T extends SuperEntity> extends BaseAdapter {

        private List<Domain> problemTypeStr;
        private HashMap<Integer, Boolean> check;

        public GridViewCheckBoxAdapter(List<Domain> problemTypeStr) {
            this.problemTypeStr = problemTypeStr;
            check = new HashMap<Integer, Boolean>();
            if (problemTypeStr != null && problemTypeStr.size() > 0) {
                for (int i = 0; i < problemTypeStr.size(); i++) {
                    check.put(i, false);
                }
            }
        }

        @Override
        public int getCount() {

            return problemTypeStr.size();
        }

        @Override
        public Object getItem(int arg0) {
            return problemTypeStr.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup arg2) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater
                        .from(getApplicationContext())
                        .inflate(
                                R.layout.hd_hse_wov_daliyrecord_gridview_checkbox_item,
                                null);

                vh.checkbox = (CheckBox) convertView
                        .findViewById(R.id.checkbox_problemtype);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.checkbox.setChecked(check.get(position));
            vh.checkbox.setText(problemTypeStr.get(position).getDescription());

            vh.checkbox
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            if (isChecked) {
                                check.put(position, true);
                            } else {
                                check.put(position, false);

                            }
                            if (check.get(0)) {

                                String s = "PENALTYPOINT";
                                boolean flag = false;
                                if (codes != null) {
                                    for (int w = 0; w < codes.length; w++) {
                                        if (codes[w].toLowerCase().equals(s.toLowerCase())) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        isShowPenaltyLi = true;
                                        penalty_pointli.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    //????????????????????????
                                    penalty_pointli.setVisibility(View.VISIBLE);
                                    isShowPenaltyLi = true;
                                }

                            } else if (check.get(0) == false) {
                                isShowPenaltyLi = false;
                                penalty_pointli.setVisibility(View.GONE);
                            }

                        }
                    });

            return convertView;

        }

        class ViewHolder {
            CheckBox checkbox;
        }

        /**
         * @return check
         */
        public HashMap<Integer, Boolean> getCheck() {
            return check;
        }

        public void setCheck(HashMap<Integer, Boolean> check) {
            this.check = check;
        }

    }

    /**
     * ???????????????????????????
     */
    private StringBuilder getSelectedQuestionType() {
        StringBuilder problemtype = new StringBuilder();
        HashMap<Integer, Boolean> check = null;

        if (mGridViewCheckBoxAdapter != null) {
            check = mGridViewCheckBoxAdapter.getCheck();
        }
        // ??????????????????????????????????????????????????????
        for (int i = 0; i < check.size(); i++) {
            if (check.get(i)) {
                if (problemtype.length() != 0) {
                    problemtype.append("," + problemTypeStr.get(i).getValue());
                } else {
                    problemtype.append(problemTypeStr.get(i).getValue());
                }

            }
        }
        return problemtype;
    }

    private boolean deleteWorkLogsFromDB(WorkLogEntry[] workLogs) {
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            dao.deleteEntities(con, workLogs);
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.error(e);
            ToastUtils.imgToast(SupervisionDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "?????????????????????");
        } catch (DaoException e) {
            logger.error(e);
            ToastUtils.imgToast(SupervisionDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "????????????????????????");
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
        return false;
    }

    private static long lastClickTime;
    private static boolean isExit = false;
    private static int oldViewId = 0;

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????;????????????????????????????????????????????????????????????????????????
     *
     * @param viewId
     * @return
     */
    public static boolean isFastDoubleClick(int viewId) {
        return isFastDoubleClick(viewId, 800);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????;????????????????????????????????????????????????????????????????????????
     *
     * @param viewId
     * @return
     */
    public static boolean isFastDoubleClick(int viewId, int longtime) {


        long time = ServerDateManager.getCurrentTimeMillis();
        if (oldViewId == viewId) {
            long timeD = time - lastClickTime;
            if (0 < timeD && timeD < longtime) {
                return true;
            }
        }
        lastClickTime = time;
        oldViewId = viewId;
        return false;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    private List<Department> filterDept(List<Department> depts) {
        for (int i = 0; i < depts.size(); i++) {

            String deptNum = depts.get(i).getDeptnum();
            if (deptNum != null
                    && (deptNum.equals("A11") || deptNum.equals("ZZJG"))) {
                depts.remove(i);
                i--;
            }
        }
        return depts;
    }

    /**
     * ????????????????????????,
     *
     * @param view ??????????????????
     * @param code ???????????????
     * @return true????????????false??????
     */
    private void isViewGone(View view, String code) {
        view.setVisibility(View.VISIBLE);
        if (codes == null)
            return;
        for (int i = 0; i < codes.length; i++) {
            if (codes[i].toLowerCase().equals(code.toLowerCase())) {
                view.setVisibility(View.GONE);
            }
        }

    }

    private String[] getcodes() {
        String[] codes = null;
        try {
            BaseDao dao = new BaseDao();
            String sql = "select isqy , input_value from sys_relation_info where sys_type = '"
                    + IRelativeEncoding.SUPERVISEDISPLAYCFG + "'";
            Map<String, Object> map = (Map<String, Object>) dao.executeQuery(
                    sql, new MapResult());

            if (map != null && map.containsKey("isqy")
                    && ((int) map.get("isqy")) == 1
                    && map.containsKey("input_value")
                    && map.get("input_value") != null) {
                codes = ((String) map.get("input_value")).split(",");
            }
        } catch (DaoException e) {
            e.printStackTrace();
            logger.error(e, null);
        }
        return codes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageLoader != null) {
            mImageLoader.isCancel();
        }
        ImageHandle.getInstance().shutDownNow();

    }
}
