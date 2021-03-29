/**
 * Project Name:hse-wov-phone-app
 * File Name:StatisticsActivity.java
 * Package Name:com.hd.hse.wov.phone.ui.statistics
 * Date:2015年11月17日
 * Copyright (c) 2015, liuyang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.wov.phone.ui.statistics;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.ZyxxTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.AppAnlsCfg;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.wov.phone.R;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:StatisticsActivity ().<br/>
 * Date: 2015年11月17日 <br/>
 *
 * @author LiuYang
 * @see
 */
public class StatisticsActivity extends BaseFrameActivity implements
        OnClickListener, IEventListener {
    /**
     * 部门
     */
    private final String dept = "DEPT";
    /**
     * 作业类型
     */
    private final String zyType = "ZYTYPE";
    /**
     * 作业级别
     */
    private final String zyLevel = "ZYLEVEL";
    /**
     * 统计方式
     */
    private final String tjfs = "TJFS";
    /**
     * 开始日期，结束日期
     */
    private final String date = "DATE";
    /**
     * 开始月份，结束月份
     */
    private final String yearAndMonth = "YEARANDMONTH";
    /**
     * 主管科室
     */
    private final String zgks = "ZGKS";

    /**
     * 所属专业
     */
    private final String zy = "ZY";

    /**
     * 检查表名称
     */
    private final String jcbmc = "JCBMC";


    AppAnlsCfg appAnlsCfg = null;

    public static final String IMGURL = "IMGURL";
    public static final String TBLURL = "TBLURL";
    public static final String APPANLSCFG = "AppAnlsCfg";
    private static Logger logger = LogUtils.getLogger(StatisticsActivity.class);
    private View checkTableLayout;
    private View sszyLayout;
    private View zgksLayout;
    private View deptLayout;
    private View zyTypeLayout;
    private View zyLevelLayout;
    private View tjfsLayout;
    private View dateLayout;
    private View yearAndMonthLayout;
    private View yearLayout;
    private View quarterLayout;
    private View monthLayout;
    private TextView deptTxt; // 部门
    private TextView zyTypeTxt; // 作业类型
    private TextView zyLevelTxt; // 作业级别
    private TextView tjfsTxt; // 统计方式
    private TextView startDateTxt, endDateTxt; // 开始/结束日期
    private TextView startYearAndMonth, endYearAndMonth; // 开始/结束月份
    private TextView startYearTxt, endYearTxt; // 开始/结束年度
    private TextView startQuarterTxt, endQuarterTxt; // 开始/结束季度
    private TextView startMonthTxt, endMonthTxt; // 开始/结束月度
    private Button queryBtn;
    // private Button imgBtn;
    // private Button tblBtn;
    private WebView webViewImg;
    // private WebView webViewTbl;
    // private View tabLayout; // 选项卡
    // private String imgUrl, tblUrl; // 从数据库读到的两个URL地址

    private int choiceposition = 0;

    private AlertDialog mDatePickerDialog;

    private List<Domain> workTypes; // 作业类型列表
    private List<Domain> workLevels; // 作业级别列表
    private List<Department> depts;

    private IQueryWorkInfo queryWorkInfo;

    private Department choiceDept;
    private PersonCard person;
    // private String bbbh;
    private String ip;

    private View queryParamLayout;
    // private View groupIndicatorView;
    // private ImageView groupIndicatorImg;
    private PopupWindow popselect;
    private View popview;

	/*
     * private View viewlineimg; private View viewlinetbl;
	 */

    private boolean needClearHistory = false; // 是否需要清除webview历史的标志
    private boolean hasMeasure = false;
    private WindowManager wm;
    private TextView zgksTxt;
    private TextView sszyTxt;
    private EditText checkTableNameEdt;
    private List<ZyxxTableName> zyxxTableNames;
    private List<ZyxxTableName> zyxxTableName;
    private String zrcscode;
    private String zycode;
    private String zrcs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_wov_phone_activity_statistics);
        // 初始化ActionBar
        setCustomActionBar(false, this, setActionBarItems());
        initPopWindow();
        findView();
        initData();
        // 设置导航栏标题
        setActionBartitleContent(getTitileName(), false);
        if (appAnlsCfg != null && appAnlsCfg.getCondition() != null
                && !"".equals(appAnlsCfg.getCondition())) {
            String condition = appAnlsCfg.getCondition();
            initView(condition);
            defaultQuery();
        } else {
            ToastUtils.toast(StatisticsActivity.this, "获取的数据有误");
        }

    }

    private void findView() {

        webViewImg = (WebView) findViewById(R.id.webview_img);
        /*
         * webViewTbl = (WebView) findViewById(R.id.webview_tbl);
		 *
		 * imgBtn = (Button) findViewById(R.id.btn_img); tblBtn = (Button)
		 * findViewById(R.id.btn_tbl); tabLayout =
		 * findViewById(R.id.tab_layout); viewlineimg =
		 * findViewById(R.id.viewimg_bottomline); viewlinetbl =
		 * findViewById(R.id.viewitbl_bottomline);
		 */

        float size = wm.getDefaultDisplay().getWidth() / 320.0f * 100;
        webViewImg.setInitialScale(150);
        // webViewTbl.setInitialScale(150);
        // groupIndicatorView = findViewById(R.id.group_indicator_view);
        // groupIndicatorImg = (ImageView)
        // findViewById(R.id.group_indicator_img);
    }

    private void initPopWindow() {
        // TODO Auto-generated method stub
        popview = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_statistic_select_pop, null);
        zgksTxt = (TextView) popview.findViewById(R.id.zgks);
        sszyTxt = (TextView) popview.findViewById(R.id.sszy);
        checkTableNameEdt = (EditText) popview.findViewById(R.id.checktablename);
        zgksLayout = popview.findViewById(R.id.zgkslayout);
        sszyLayout = popview.findViewById(R.id.sszylayout);
        checkTableLayout = popview.findViewById(R.id.anothermaterlayout);
        deptLayout = popview.findViewById(R.id.deptlayout);
        zyTypeLayout = popview.findViewById(R.id.zytypelayout);
        zyLevelLayout = popview.findViewById(R.id.zylevellayout);
        tjfsLayout = popview.findViewById(R.id.tjfslayout);
        dateLayout = popview.findViewById(R.id.datelayout);
        yearAndMonthLayout = popview.findViewById(R.id.yearandmonthlayout);
        yearLayout = popview.findViewById(R.id.yearlayout);
        quarterLayout = popview.findViewById(R.id.quarterlayout);
        monthLayout = popview.findViewById(R.id.monthlayout);
        deptTxt = (TextView) popview.findViewById(R.id.dept);
        zyTypeTxt = (TextView) popview.findViewById(R.id.zytype);
        zyLevelTxt = (TextView) popview.findViewById(R.id.zylevel);
        tjfsTxt = (TextView) popview.findViewById(R.id.tjfs);
        startDateTxt = (TextView) popview.findViewById(R.id.startdate);
        endDateTxt = (TextView) popview.findViewById(R.id.enddate);
        startYearAndMonth = (TextView) popview
                .findViewById(R.id.startyearandmonth);
        endYearAndMonth = (TextView) popview.findViewById(R.id.endyearandmonth);
        startYearTxt = (TextView) popview.findViewById(R.id.startyear);
        endYearTxt = (TextView) popview.findViewById(R.id.endyear);
        startQuarterTxt = (TextView) popview.findViewById(R.id.startquarter);
        endQuarterTxt = (TextView) popview.findViewById(R.id.endquarter);
        startMonthTxt = (TextView) popview.findViewById(R.id.startmonth);
        endMonthTxt = (TextView) popview.findViewById(R.id.endmonth);
        queryBtn = (Button) popview.findViewById(R.id.querybtn);
        queryParamLayout = popview.findViewById(R.id.query_param_layout);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        popview.setFocusable(true);
        popselect = new PopupWindow();
        popselect.setWidth(width);
        popselect.setContentView(popview);
        popselect.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popselect.setOutsideTouchable(true);
        popselect.setBackgroundDrawable(new ColorDrawable(0xaaaaaaaa));
        popselect.setFocusable(true);
        popselect.setAnimationStyle(R.style.StatisticPopWindow);
        popselect.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                backgroundAlpha(1.0f);
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha (0-1 不透明到透明)
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

    private void initData() {
        queryWorkInfo = new QueryWorkInfo();
        Intent intent = getIntent();
        appAnlsCfg = (AppAnlsCfg) intent
                .getSerializableExtra(StatisticsActivity.APPANLSCFG);
        try {
            ip = queryWorkInfo.queryIPAndPort(null);
        } catch (HDException e) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
        if (workTypes == null) {
            try {
                workTypes = queryWorkInfo.queryDomain("UDZYLX", null);
            } catch (HDException e) {
                ToastUtils.imgToast(StatisticsActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }
        }
    }

    private void initView(String condition) {
        // 根据condition值判断要显示的控件
        // 部门
        if (condition.contains(dept)) {
            deptLayout.setVisibility(View.VISIBLE);
        } else {
            deptLayout.setVisibility(View.GONE);
        }

        // 作业类型
        if (condition.contains(zyType)) {
            zyTypeLayout.setVisibility(View.VISIBLE);
        } else {
            zyTypeLayout.setVisibility(View.GONE);
        }

        // 作业级别
        if (condition.contains(zyLevel)) {
            zyLevelLayout.setVisibility(View.VISIBLE);
        } else {
            zyLevelLayout.setVisibility(View.GONE);
        }
        // 开始月份，结束月份
        if (condition.contains(yearAndMonth)) {
            yearAndMonthLayout.setVisibility(View.VISIBLE);
        } else {
            yearAndMonthLayout.setVisibility(View.GONE);
        }
        // 开始日期，结束日期
        if (condition.contains(date)) {
            dateLayout.setVisibility(View.VISIBLE);
            // 开始日期结束日期显示时，开始月份结束月份不显示
            yearAndMonthLayout.setVisibility(View.GONE);
        } else {
            dateLayout.setVisibility(View.GONE);
        }

        // 统计方式
        if (condition.contains(tjfs)) {
            tjfsLayout.setVisibility(View.VISIBLE);
            yearLayout.setVisibility(View.VISIBLE);
            // 统计方式显示时，1.开始月份结束月份不显示2.开始日期结束日期不显示
            yearAndMonthLayout.setVisibility(View.GONE);
            dateLayout.setVisibility(View.GONE);
        } else {
            tjfsLayout.setVisibility(View.GONE);
            yearLayout.setVisibility(View.GONE);
        }

        // 吉林增加
        if (condition.contains(zgks)) {
            //主管科室
            zgksLayout.setVisibility(View.VISIBLE);
        } else {
            zgksLayout.setVisibility(View.GONE);
        }

        sszyLayout.setVisibility(View.GONE);
        String[] split = condition.split(",");
        for (int p = 0; p < split.length; p++) {
            if (split[p].equals(zy)) {
                //所属专业
                sszyLayout.setVisibility(View.VISIBLE);
            }
        }

        if (condition.contains(jcbmc)) {
            //检查表名称
            checkTableLayout.setVisibility(View.VISIBLE);
        } else {
            checkTableLayout.setVisibility(View.GONE);
        }


        zgksTxt.setOnClickListener(this);
        sszyTxt.setOnClickListener(this);
        quarterLayout.setVisibility(View.GONE);
        monthLayout.setVisibility(View.GONE);

        person = SystemProperty.getSystemProperty().getLoginPerson();
        deptTxt.setOnClickListener(this);
        // 获取部门默认值 // 这里不知道有没有什么好办法，现在判断很麻烦有几个写死的条件库里编码不能变的
        Department defaultDept = null;

        try {
            defaultDept = queryWorkInfo.queryDefaultDept(
                    person.getDepartment(), null);
        } catch (HDException e) {
            ToastUtils.imgToast(StatisticsActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }
        if (defaultDept != null && defaultDept.getDescription() != null) {
            choiceDept = defaultDept;
            deptTxt.setText(defaultDept.getDescription());
        } else {
            deptTxt.setText(person.getDepartment_desc());
        }
        if (dateLayout.getVisibility() == View.VISIBLE) {
            // 设置默认的开始日期和结束日期（开始时间：当前日期-7，结束时间：当天）
            Calendar c = Calendar.getInstance();
            c.setTime(ServerDateManager.getCurrentDate());
            String dateStr = c.get(Calendar.YEAR)
                    + "-"
                    + ((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c
                    .get(Calendar.MONTH) + 1))
                    : (c.get(Calendar.MONTH) + 1))
                    + "-"
                    + (c.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + c
                    .get(Calendar.DAY_OF_MONTH)) : c
                    .get(Calendar.DAY_OF_MONTH));
            endDateTxt.setText(dateStr);
            c.add(Calendar.WEEK_OF_MONTH, -1);
            dateStr = c.get(Calendar.YEAR)
                    + "-"
                    + ((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c
                    .get(Calendar.MONTH) + 1))
                    : (c.get(Calendar.MONTH) + 1))
                    + "-"
                    + (c.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + c
                    .get(Calendar.DAY_OF_MONTH)) : c
                    .get(Calendar.DAY_OF_MONTH));
            startDateTxt.setText(dateStr);
        }
        zyTypeTxt.setOnClickListener(this);
        for (Domain domain : workTypes) {
            if (domain.getValue().equals("zylx01")) {
                zyTypeTxt.setText(domain.getDescription());
            }
        }
        zyLevelTxt.setOnClickListener(this);
        tjfsTxt.setOnClickListener(this);
        tjfsTxt.setText("年度");
        startDateTxt.setOnClickListener(this);
        endDateTxt.setOnClickListener(this);
        if (yearAndMonthLayout.getVisibility() == View.VISIBLE) {
            // 设置默认的开始月份和结束月份
            Calendar c = Calendar.getInstance();
            c.setTime(ServerDateManager.getCurrentDate());
            String dateStr = c.get(Calendar.YEAR)
                    + "-"
                    + ((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c
                    .get(Calendar.MONTH) + 1))
                    : (c.get(Calendar.MONTH) + 1));
            endYearAndMonth.setText(dateStr);
            startYearAndMonth.setText(dateStr);
        }
        startYearAndMonth.setOnClickListener(this);
        endYearAndMonth.setOnClickListener(this);
        if (yearLayout.getVisibility() == View.VISIBLE) {
            // 设置默认的开始年度和结束年度（开始年度：去年，结束年度：今年）
            Calendar c = Calendar.getInstance();
            c.setTime(ServerDateManager.getCurrentDate());
            String dateStr = c.get(Calendar.YEAR) + "";
            endYearTxt.setText(dateStr);
            c.add(Calendar.YEAR, -1);
            dateStr = c.get(Calendar.YEAR) + "";
            startYearTxt.setText(dateStr);
        }
        startYearTxt.setOnClickListener(this);
        endYearTxt.setOnClickListener(this);
        startQuarterTxt.setOnClickListener(this);
        endQuarterTxt.setOnClickListener(this);
        startMonthTxt.setOnClickListener(this);
        endMonthTxt.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        /*
         * imgBtn.setOnClickListener(this); tblBtn.setOnClickListener(this);
		 */
        // groupIndicatorView.setOnClickListener(this);
        WebSettings webSettings = webViewImg.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        // WebSettings webSetting = webViewTbl.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        /*
         * webSetting.setJavaScriptEnabled(true); // 设置可以访问文件
		 * webSetting.setAllowFileAccess(true); // 设置支持缩放
		 * webSetting.setBuiltInZoomControls(true);
		 * webSetting.setDisplayZoomControls(false);
		 */
        webViewImg.setWebViewClient(new MyWebViewClient());
        // webViewTbl.setWebViewClient(new MyWebViewClient());
    }

    private void showDatePickerDialog(String title, final int id) {
        View view = popview.findViewById(id);
        View dateTimeLayout = (View) getLayoutInflater().inflate(
                R.layout.datepicker_dialog_layout, null);
        final DatePicker datePicker = (DatePicker) dateTimeLayout
                .findViewById(R.id.datepicker_dialog_layout_dp);
        AlertDialog.Builder builder = new Builder(StatisticsActivity.this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                if (id == R.id.startyear || id == R.id.endyear) {
                    ((TextView) popview.findViewById(id)).setText(year + "");
                } else if (id == R.id.startmonth || id == R.id.endmonth) {
                    ((TextView) popview.findViewById(id)).setText((month + 1)
                            + "");
                } else if (id == R.id.startyearandmonth
                        || id == R.id.endyearandmonth) {
                    String date = year
                            + "-"
                            + ((month + 1) < 10 ? ("0" + (month + 1))
                            : (month + 1));
                    ((TextView) popview.findViewById(id)).setText(date);
                } else {
                    String date = year
                            + "-"
                            + ((month + 1) < 10 ? ("0" + (month + 1))
                            : (month + 1)) + "-"
                            + (day < 10 ? ("0" + day) : day);
                    ((TextView) popview.findViewById(id)).setText(date);
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle(title);
        builder.setView(dateTimeLayout);
        mDatePickerDialog = builder.show();

		/*
         * mDatePickerDialog = new DatePickerDialog(this,
		 * DatePickerDialog.THEME_HOLO_LIGHT, null, c.get(Calendar.YEAR),
		 * c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		 * mDatePickerDialog.setTitle(title);
		 * mDatePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
		 * new DialogInterface.OnClickListener() {
		 *
		 * @Override public void onClick(DialogInterface arg0, int arg1) {
		 *
		 * int year = datePicker.getYear(); int month = datePicker.getMonth();
		 * int day = datePicker.getDayOfMonth(); if (id == R.id.startyear || id
		 * == R.id.endyear) { ((TextView) popview.findViewById(id)).setText(year
		 * + ""); } else if (id == R.id.startmonth || id == R.id.endmonth) {
		 * ((TextView) popview.findViewById(id)) .setText((month + 1) + ""); }
		 * else if (id == R.id.startyearandmonth || id == R.id.endyearandmonth)
		 * { String date = year + "-" + ((month + 1) < 10 ? ("0" + (month + 1))
		 * : (month + 1)); ((TextView) popview.findViewById(id)).setText(date);
		 * } else { String date = year + "-" + ((month + 1) < 10 ? ("0" + (month
		 * + 1)) : (month + 1)) + "-" + (day < 10 ? ("0" + day) : day);
		 * ((TextView) popview.findViewById(id)).setText(date); } } });
		 * mDatePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
		 * new DialogInterface.OnClickListener() {
		 *
		 * @Override public void onClick(DialogInterface arg0, int arg1) {
		 *
		 * } });
		 *
		 * mDatePickerDialog.show();
		 */

        if (id == R.id.startyear || id == R.id.endyear) {
            ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                    .getChildAt(1).setVisibility(View.GONE); // 隐藏月份
            ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE); // 隐藏日期
        } else if (id == R.id.startmonth || id == R.id.endmonth) {
            ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                    .getChildAt(0).setVisibility(View.GONE); // 隐藏年份
            ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE); // 隐藏日期
        } else if (id == R.id.startyearandmonth || id == R.id.endyearandmonth) {
            ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE); // 隐藏日期
        }

    }

    private void showDeptDialog(final List<Department> datas) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final List<Department> searchDatas = new ArrayList<>();
        searchDatas.addAll(datas);
        builder.setTitle("选择部门");
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
                choiceDept = searchDatas.get(arg2);
                deptTxt.setText(searchDatas.get(arg2).getDescription());
                dialog.cancel();
            }
        });
    }

    private void showSingleChoiceDialog(final int id, String title,
                                        final String[] datas) {
        View view = popview.findViewById(id);
        choiceposition = 0;
        if (view instanceof TextView) {
            String txt = ((TextView) view).getText().toString();
            for (int i = 0; i < datas.length; i++) {
                if (datas[i].equals(txt)) {
                    choiceposition = i;
                    break;
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setSingleChoiceItems(datas, choiceposition,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        choiceposition = arg1;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (id == R.id.zylevel && choiceposition == 0) {
                    ((TextView) popview.findViewById(id)).setText("");
                } else {
                    ((TextView) popview.findViewById(id))
                            .setText(datas[choiceposition]);
                }

                if (id == R.id.tjfs) {
                    switch (choiceposition) {
                        case 0:
                            yearLayout.setVisibility(View.VISIBLE);
                            quarterLayout.setVisibility(View.GONE);
                            monthLayout.setVisibility(View.GONE);
                            break;
                        case 1:
                            yearLayout.setVisibility(View.VISIBLE);
                            quarterLayout.setVisibility(View.VISIBLE);
                            monthLayout.setVisibility(View.GONE);
                            break;
                        case 2:
                            yearLayout.setVisibility(View.VISIBLE);
                            quarterLayout.setVisibility(View.GONE);
                            monthLayout.setVisibility(View.VISIBLE);
                            break;

                        default:
                            break;
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        builder.show();
    }

    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    private void dismissPop() {
        if (popselect != null && popselect.isShowing()) {
            popselect.dismiss();
        }
    }

    private void btnQueryOnClick() {
        StringBuilder param = new StringBuilder();
        if (choiceDept != null) {
            param.append("&ZYDEPT=").append(choiceDept.getDeptnum());
        } else {
            param.append("&ZYDEPT=").append(person.getDepartment());
        }
        if (zgksTxt.getText().toString() != null &&
                !("").equals(zgksTxt.getText().toString())) {
            param.append("&ZRCSCODE=").append(zrcscode);
        }
        if (sszyTxt.getText().toString() != null &&
                !("").equals(sszyTxt.getText().toString())) {
            param.append("&ZYCODE=").append(zycode);
        }
        if (checkTableNameEdt.getText().toString() != null &&
                !("").equals(checkTableNameEdt.getText().toString())) {
            param.append("&NAME=").append(checkTableNameEdt.getText().toString());
        }
        if (startDateTxt.getText() != null
                && !startDateTxt.getText().equals("")) {
            param.append("&DATEFROM=").append(startDateTxt.getText());
        } else {
            if (dateLayout.getVisibility() == dateLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入开始日期");
                return;
            }
        }
        if (endDateTxt.getText() != null && !endDateTxt.getText().equals("")) {
            param.append("&DATEEND=").append(endDateTxt.getText());
        } else {
            if (dateLayout.getVisibility() == dateLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入结束日期");
                return;
            }
        }
        if (startYearAndMonth.getText() != null
                && !startYearAndMonth.getText().equals("")) {
            param.append("&DATEFROM=").append(startYearAndMonth.getText());
        } else {
            if (yearAndMonthLayout.getVisibility() == yearAndMonthLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入开始月份");
                return;
            }
        }
        if (endYearAndMonth.getText() != null
                && !endYearAndMonth.getText().equals("")) {
            param.append("&DATEEND=").append(endYearAndMonth.getText());
        } else {
            if (yearAndMonthLayout.getVisibility() == yearAndMonthLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入结束月份");
                return;
            }
        }
        int tjfs = 0;
        if (tjfsTxt.getText() != null && !tjfsTxt.getText().equals("")) {
            String key = null;
            switch (tjfsTxt.getText().toString()) {
                case "年度":
                    key = "ND";
                    tjfs = 1;
                    break;
                case "季度":
                    key = "JD";
                    tjfs = 2;
                    break;
                case "月度":
                    key = "YD";
                    tjfs = 3;
                    break;

                default:
                    break;
            }
            if (key != null) {
                param.append("&TJFS=").append(key);
            }
        } else {
            if (tjfsLayout.getVisibility() == tjfsLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入统计方式");
                return;
            }
        }
        if (startYearTxt.getText() != null
                && !startYearTxt.getText().equals("")) {
            param.append("&YEARFROM=").append(startYearTxt.getText());
        } else {
            if (yearLayout.getVisibility() == yearLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入开始年度");
                return;
            }
        }
        if (endYearTxt.getText() != null && !endYearTxt.getText().equals("")) {
            param.append("&YEAREND=").append(endYearTxt.getText());
        } else {
            if (yearLayout.getVisibility() == yearLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入结束年度");
                return;
            }
        }
        if (startQuarterTxt.getText() != null
                && !startQuarterTxt.getText().equals("")) {
            String quarteStr = startQuarterTxt.getText().toString();
            int quarte = 0;
            switch (quarteStr) {
                case "一季度":
                    quarte = 1;
                    break;
                case "二季度":
                    quarte = 2;
                    break;
                case "三季度":
                    quarte = 3;
                    break;
                case "四季度":
                    quarte = 4;
                    break;

                default:
                    break;
            }
            if (quarte != 0) {
                param.append("&QUARTERFROM=").append(quarte);
            }
        } else {
            if (quarterLayout.getVisibility() == quarterLayout.VISIBLE
                    && tjfs == 2) {
                ToastUtils.toast(StatisticsActivity.this, "请输入开始季度");
                return;
            }
        }
        if (endQuarterTxt.getText() != null
                && !endQuarterTxt.getText().equals("")) {
            String quarteStr = endQuarterTxt.getText().toString();
            int quarte = 0;
            switch (quarteStr) {
                case "一季度":
                    quarte = 1;
                    break;
                case "二季度":
                    quarte = 2;
                    break;
                case "三季度":
                    quarte = 3;
                    break;
                case "四季度":
                    quarte = 4;
                    break;

                default:
                    break;
            }
            if (quarte != 0) {
                param.append("&QUARTEREND=").append(quarte);
            }
        } else {
            if (quarterLayout.getVisibility() == quarterLayout.VISIBLE
                    && tjfs == 2) {
                ToastUtils.toast(StatisticsActivity.this, "请输入结束季度");
                return;
            }
        }
        if (startMonthTxt.getText() != null
                && !startMonthTxt.getText().equals("")) {
            param.append("&MONTHFROM=").append(startMonthTxt.getText());
        } else {
            if (monthLayout.getVisibility() == monthLayout.VISIBLE && tjfs == 3) {
                ToastUtils.toast(StatisticsActivity.this, "请输入开始月度");
                return;
            }
        }
        if (endMonthTxt.getText() != null && !endMonthTxt.getText().equals("")) {
            param.append("&MONTHEND=").append(endMonthTxt.getText());
        } else {
            if (monthLayout.getVisibility() == monthLayout.VISIBLE && tjfs == 3) {
                ToastUtils.toast(StatisticsActivity.this, "请输入结束月度");
                return;
            }
        }
        if (zyTypeTxt.getText() != null && !zyTypeTxt.getText().equals("")) {
            for (Domain d : workTypes) {
                if (d.getDescription().equals(zyTypeTxt.getText().toString())) {
                    param.append("&ZYPTYPEPARAM=").append(d.getValue());
                    break;
                }
            }
        } else {
            if (zyTypeLayout.getVisibility() == zyTypeLayout.VISIBLE) {
                ToastUtils.toast(StatisticsActivity.this, "请输入作业类型");
                return;
            }
        }
        if (zyLevelTxt.getText() != null && !zyLevelTxt.getText().equals("")) {
            for (Domain d : workLevels) {
                if (d.getDescription().equals(zyLevelTxt.getText().toString())) {
                    param.append("&ZYLEVELPARAM=").append(d.getValue());
                    break;
                }
            }
        }
        needClearHistory = true;
        webViewImg.loadUrl("http://" + ip + appAnlsCfg.getRqurl()
                + param.toString());
        webViewImg.setVisibility(View.VISIBLE);

        dismissPop();
        // queryParamLayout.setVisibility(View.GONE);
        // groupIndicatorImg
        // .setImageResource(R.drawable.tjfx_group_indicator_right);

    }

    private void defaultQuery() {
        btnQueryOnClick();
    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.dept) {
            if (depts == null) {
                try {
                    depts = queryWorkInfo.queryDept(true, null);
                    // int strDigit = 0;
                    // for (Department dept : depts) {
                    // if (dept.getVreportdeptnum().length() > strDigit) {
                    // strDigit = dept.getVreportdeptnum().length();
                    // }
                    // }
                    // for (int i = 0; i < depts.size(); i++) {
                    // if (depts.get(i).getVreportdeptnum().length() ==
                    // strDigit) {
                    // depts.remove(i);
                    // }
                    // }
                } catch (HDException e) {
                    ToastUtils.imgToast(StatisticsActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
            showDeptDialog(depts);
        } else if (id == R.id.zgks) {
            //从hse专业详细定义里查主管科室
            if (zyxxTableNames == null) {
                try {
                    zyxxTableNames = queryWorkInfo.queryDeptFromhdrcgl();
                } catch (HDException e) {
                    ToastUtils.imgToast(StatisticsActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }

            showZgksDialog(zyxxTableNames);
        } else if (id == R.id.sszy) {
            //从hse专业详细定义里查所属专业

            try {
                if (zrcs == null) {
                    zyxxTableName = queryWorkInfo.queryZyFromhdrcgl("");
                } else {
                    zyxxTableName = queryWorkInfo.queryZyFromhdrcgl(zrcscode);

                }
            } catch (HDException e) {
                ToastUtils.imgToast(StatisticsActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }

            if (zyxxTableName != null) {
                showSszyDialog(zyxxTableName);
            }
        } else if (id == R.id.zytype) {
            if (workTypes == null) {
                try {
                    workTypes = queryWorkInfo.queryDomain("UDZYLX", null);
                } catch (HDException e) {
                    ToastUtils.imgToast(StatisticsActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }

            if (workTypes != null && workTypes.size() != 0) {
                String[] datas = new String[workTypes.size()];
                for (int i = 0; i < workTypes.size(); i++) {
                    datas[i] = workTypes.get(i).getDescription();
                }
                showSingleChoiceDialog(id, "选择作业类型", datas);
            }
        } else if (id == R.id.zylevel) {
            if (workLevels == null) {
                try {
                    workLevels = queryWorkInfo.queryDomain("UDZYLEVEL", null);
                } catch (HDException e) {
                    ToastUtils.imgToast(StatisticsActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }

            if (workLevels != null && workLevels.size() != 0) {
                String[] datas = new String[workLevels.size() + 1];
                datas[0] = "无 ";
                for (int i = 0; i < workLevels.size(); i++) {
                    datas[i + 1] = workLevels.get(i).getDescription();
                }
                showSingleChoiceDialog(id, "选择作业级别", datas);
            }
        } else if (id == R.id.tjfs) {
            String[] datas = new String[]{"年度", "季度", "月度"};
            showSingleChoiceDialog(id, "选择统计方式", datas);
        } else if (id == R.id.startyear || id == R.id.endyear) {
            showDatePickerDialog("选择年度", id);
        } else if (id == R.id.startyearandmonth || id == R.id.endyearandmonth) {
            showDatePickerDialog("选择月份", id);
        } else if (id == R.id.startquarter || id == R.id.endquarter) {
            String[] datas = new String[]{"一季度", "二季度", "三季度", "四季度"};
            showSingleChoiceDialog(id, "选择季度", datas);
        } else if (id == R.id.startmonth || id == R.id.endmonth) {
            showDatePickerDialog("选择月度", id);
        } else if (id == R.id.querybtn) {
            btnQueryOnClick();
        } else {
            showDatePickerDialog("选择日期", id);
        }
    }

    private void showZgksDialog(final List<ZyxxTableName> zxjcTableNames) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final List<ZyxxTableName> searchDatas = new ArrayList<>();
        //加了一条空数据，相当于清除数据
        List<ZyxxTableName> temp = new ArrayList<>();
        temp.add(new ZyxxTableName());
        searchDatas.addAll(temp);
        searchDatas.addAll(zxjcTableNames);
        builder.setTitle("选择主管科室");
        View view = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_wov_dept_dialog_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.data_listview);
        final EditText searchTxt = (EditText) view
                .findViewById(R.id.search_txt);
        final DeptListAdapter3 adapter = new DeptListAdapter3(this, searchDatas);
        listView.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.search_btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = searchTxt.getText().toString();
                if (str != null && !str.equals("")) {
                    searchDatas.clear();
                    for (ZyxxTableName d : zxjcTableNames) {
                        if (d.getZrcs() != null) {
                            if (d.getZrcs().contains(str)) {
                                searchDatas.add(d);
                            }
                        }

                    }
                    adapter.notifyDataSetChanged();
                } else {
                    searchDatas.clear();
                    searchDatas.addAll(zxjcTableNames);
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
                zgksTxt.setText(searchDatas.get(arg2).getZrcs());
                zrcs = searchDatas.get(arg2).getZrcs();
                zrcscode = searchDatas.get(arg2).getZrcscode();
                dialog.cancel();
            }
        });
    }


    private void showSszyDialog(final List<ZyxxTableName> zxjcTableNames) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final List<ZyxxTableName> searchDatas = new ArrayList<>();
        //加了一条空数据，相当于清除数据
        List<ZyxxTableName> temp = new ArrayList<>();
        temp.add(new ZyxxTableName());
        searchDatas.addAll(temp);
        searchDatas.addAll(zxjcTableNames);
        builder.setTitle("选择所属专业");
        View view = LayoutInflater.from(this).inflate(
                R.layout.hd_hse_wov_dept_dialog_layout, null);
        ListView listView = (ListView) view.findViewById(R.id.data_listview);
        final EditText searchTxt = (EditText) view
                .findViewById(R.id.search_txt);
        final DeptListAdapter5 adapter = new DeptListAdapter5(this, searchDatas);
        listView.setAdapter(adapter);
        Button button = (Button) view.findViewById(R.id.search_btn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String str = searchTxt.getText().toString();
                if (str != null && !str.equals("")) {
                    searchDatas.clear();
                    for (ZyxxTableName d : zxjcTableNames) {
                        if (d.getZyname() != null) {
                            if (d.getZyname().contains(str)) {
                                searchDatas.add(d);
                            }
                        }

                    }
                    adapter.notifyDataSetChanged();
                } else {
                    searchDatas.clear();
                    searchDatas.addAll(zxjcTableNames);
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
                sszyTxt.setText(searchDatas.get(arg2).getZyname());
                //实际用的就是Zyname
                zycode = searchDatas.get(arg2).getZyname();
                dialog.cancel();
            }
        });

    }

    private String getTitileName() {
        if (appAnlsCfg != null) {
            return appAnlsCfg.getAnlsname();
        } else {
            return "统计分析";
        }

    }

    private String[] setActionBarItems() {
        return new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE,
                IActionBar.STATISTIC_SELECT};
    }

    private class CustomerDatePickerDialog extends DatePickerDialog {
        private String title;

        public CustomerDatePickerDialog(Context context, String title,
                                        OnDateSetListener callBack, int year, int monthOfYear,
                                        int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            this.title = title;
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
            mDatePickerDialog.setTitle(this.title);
        }
    }

    @Override
    public void eventProcess(int arg0, Object... arg1) throws HDException {
        if (arg0 == IEventType.ACTIONBAR_STATISTIC_SELECT_CLICK) {

            popselect.showAsDropDown((View) arg1[0]);
            backgroundAlpha(0.6f);

        }
    }

    class MyWebViewClient extends WebViewClient {
        // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://")) {
//                url = url.replace("http://", "http://" + ip + "/");
            } else {
                url = "http://" + ip + "/" + url;
            }
            /*url = url.replace("http://", "http://" + ip + "/");
            url = url.replace("rqreport", "rqReport");*/
            view.loadUrl(url);
            // Map<String, String> params = URLRequest(url);
            // String zydept = params.get("ZYDEPT");
			/*
			 * if (view == webViewTbl && url.indexOf("_TBL") != -1 &&
			 * url.indexOf("ZYDEPT") != -1) { String urlImg =
			 * url.replace("_TBL", "_IMG"); webViewImg.loadUrl(urlImg); }
			 */

            // 如果不需要其他对点击链接事件的处理返回true，否则返回false
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (needClearHistory) {
                webViewImg.clearHistory();
                needClearHistory = false;
            }
        }
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim().toLowerCase();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webViewImg.getVisibility() == View.VISIBLE
                    && webViewImg.canGoBack()) {
                webViewImg.goBack();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
