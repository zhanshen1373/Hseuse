package com.hd.hse.wov.phone.ui.activity.worktask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.dialog.UpOnlineZypProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.camera.Base64Util;
import com.hd.hse.common.module.phone.custom.StateDownloadZYPExpandableListView2;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.other.Persongroupteam;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.SxtBean;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;
import com.hd.hse.utils.WorkOrderUtilTools;
import com.hd.hse.wov.business.workorder.WorkTaskSrv;
import com.hd.hse.wov.phone.R;
import com.hd.hse.wov.phone.business.workorder.ServerWorkTaskSrv;
import com.hd.hse.wov.phone.business.workorder.WorkSxt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * ClassName: TaskTabulationActivity ()<br/>
 * date: 2015???1???27??? <br/>
 *
 * @author lych
 */
public class TaskTabulationActivity extends BaseListBusActivity implements View.OnClickListener {
    /**
     * down:TODO(??????????????????).
     */
    private ServerWorkTaskSrv workorderdown = null;
    /**
     * downzyp:TODO(????????????????????????).
     */
    private static final String UD_ZYXK_ZYSQID = "ud_zyxk_zysqid";

    private WorkSxt workSxt = null;

    /**
     * APP_MODULE:TODO(?????????????????????????????????).
     */
    private static final String APP_MODULE = "appmodule";
    private List<SuperEntity> refreshListData;
    // private DownZYPProgressDiaolog downzyp = null;
    private StateDownloadZYPExpandableListView2 selectable;
    private List<SuperEntity> dataList;
    // ???????????????list????????????
    private int dataPosition = -1;
    // ?????????????????????
    private String flag = null;
    private boolean fat = false;
    private int initDHGX = -1;
    private List list;
    private List<WorkOrder> emptylist;

    private QueryWorkInfo queryWorkInfo;
    private List<String> unitList = new ArrayList<>();
    HashMap<String, String> hm = new HashMap<>();
    private BaseDao dao;
    //????????????
    private EditText zymc;
    //????????????
    private EditText zyqy;

    private String jsdate;
    private String ksdate;
    private String workUnit;
    private String zuoYeType;
    private String zuoYeType_value;
    private String workUnit_value;
    private PopupWindow popupWindow;
    private AppModule appModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");
        appModule = (AppModule) intent.getSerializableExtra(APP_MODULE);
        initPrarm();
        super.onCreate(savedInstanceState);
        // initData();
        // initView();
        queryWorkInfo = new QueryWorkInfo();
        dao = new BaseDao();
    }

    /**
     * initView:(). <br/>
     * date: 2015???3???4??? <br/>
     *
     * @author lxf
     */
    @Override
    public void initView() {
        setContentView(selectable);

        if (("????????????").equals(flag)) {
            // ??????????????????ActionBar
            setCustomActionBar(true, eventLst, new String[]{
                    IActionBar.IV_LMORE, IActionBar.TV_TITLE,
                    IActionBar.IBTN_SEARCH, IActionBar.ZYLL_DX,
                    IActionBar.ZYLL_ZYFH}); // ?????????????????????

        } else {
            // ??????????????????ActionBar
            setCustomActionBar(true, eventLst, new String[]{
                    IActionBar.IV_LMORE, IActionBar.TV_TITLE,
                    IActionBar.IBTN_SEARCH}); // ?????????????????????
        }
        String name = null;
        if (appModule != null) {
            name = appModule.getName();
        }
        if (name != null) {
            setActionBartitleContent(name, false);
        } else {
            setActionBartitleContent("???????????????", false);
        }
//        setActionBartitleContent("???????????????", false);
        // ??????????????????????????????
        setNavContent(
                SystemProperty.getSystemProperty().getMainAppModulelist("SJ"),
                getNavCurrentKey());
    }

    /**
     * initPrarm:(???????????????). <br/>
     * date: 2015???3???4??? <br/>
     *
     * @author lxf
     */
    private void initPrarm() {

        // selectable = new StateDownloadZYPExpandableListView(this);
        selectable = new StateDownloadZYPExpandableListView2(this);
        selectable.setOnEventListener(eventLst);

        // selected.setOnEventListener(eventLst);
        workorderdown = new ServerWorkTaskSrv(this);
        workorderdown.setGetDataSourceListener(eventLst);

        workSxt = new WorkSxt(this);
        workSxt.setGetDataSourceListener(eventLst);
        // downzyp = new DownZYPProgressDiaolog(this);
        // downzyp.setGetDataSourceListener(eventLst);

    }

    /**
     * initData:(???????????????). <br/>
     * date: 2015???3???4??? <br/>
     *
     * @author lxf
     */
    @Override
    public void initData() {

        PositionCard positionCard = null;
        if (appModule != null) {
            positionCard = SystemProperty.getSystemProperty().getPositionCard();
            if (positionCard != null) {
                setSearchStr("location=" + positionCard.getLocation() + "");
            }
            positionCard = null;
        }

        if (workorderdown != null) {

            workorderdown.geteDownLoadWorkList(getSearchStr());// ??????????????????

        }


    }

    private Bitmap generate(SxtBean.CamerasBean camerasBean) {

        int w = 320, h = 240;
        String mstrTitle = camerasBean.getName();
        Bitmap mbmpTest = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvasTemp = new Canvas(mbmpTest);
        canvasTemp.drawColor(Color.BLACK);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(28);
        canvasTemp.drawText(mstrTitle, 160, 120, p);
        return mbmpTest;

    }


    private void getNetVideoBitmap(WorkOrder order, List<SxtBean.CamerasBean> videoUrl) {

        List<String> bitmapList = new ArrayList<>();
        for (int i = 0; i < videoUrl.size(); i++) {
            try {
                bitmapList.add(Base64Util.bitmapToBase64(generate(videoUrl.get(i))));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        order.setBitmapList(bitmapList);
    }

    IEventListener eventLst = new IEventListener() {
        @SuppressWarnings("unchecked")
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.DOWN_WORK_LIST_SHOW:

                    List<SuperEntity> serverWorkTask = null;
                    if (objects[0] instanceof List<?>) {
                        // setDataList((List<SuperEntity>) objects[0]);
                        serverWorkTask = (List<SuperEntity>) objects[0];
                    }
                    // ??????????????????
                    if (serverWorkTask != null) {
                        // ????????????WorkOrder
                        List<SuperEntity> localListTask = new WorkTaskSrv()
                                .loadWorkTaskList(getSearchStr());
                        // ??????WorkOrder
                        dataList = WorkOrderUtilTools.compareWorkOrder(
                                serverWorkTask, localListTask);
                        //??????????????????????????????????????????
                        if (appModule != null) {
                            for (int i = 0; i < dataList.size(); i++) {
                                WorkTask workTask = (WorkTask) dataList.get(i);
                                List<SuperEntity> lwo = workTask
                                        .getChild(WorkOrder.class
                                                .getName());
                                for (int t = 0; t < lwo.size(); t++) {
                                    WorkOrder workOrder = (WorkOrder) lwo.get(t);
                                    if (!workOrder.getStatus().equals("APPR")) {
                                        lwo.remove(t);
                                        t--;
                                    }
                                }
                                if (lwo.size() == 0) {
                                    dataList.remove(i);
                                }

                            }
                        }

//                        List<String> locations = new ArrayList<>();
                        StringBuilder stringBuilder = new StringBuilder();
                        if (appModule == null) {

                            for (int i = 0; i < dataList.size(); i++) {
                                WorkTask workTask = (WorkTask) dataList.get(i);
                                List<SuperEntity> lwo = workTask
                                        .getChild(WorkOrder.class
                                                .getName());
                                for (int t = 0; t < lwo.size(); t++) {
                                    WorkOrder workOrder = (WorkOrder) lwo.get(t);
                                    if (t != lwo.size() - 1) {
                                        stringBuilder.append(workOrder.getZylocation() + ",");
                                    } else {
                                        stringBuilder.append(workOrder.getZylocation());
                                    }
//                                    locations.add(workOrder.getZylocation());
                                }
                                if (i != dataList.size() - 1)
                                    stringBuilder.append(",");

                            }
                        }

                        //????????????????????????????????????
                        String sql = "select resppartygroup from persongroupteam where persongroup='cctv' COLLATE NOCASE;";
                        BaseDao dao = new BaseDao();
                        try {
                            Persongroupteam persongroupteam = (Persongroupteam) dao.executeQuery(sql, new EntityResult(Persongroupteam.class));
                            if (persongroupteam!=null){
                                String resppartygroup = persongroupteam.getResppartygroup();
                                if (resppartygroup!=null){
                                    if (resppartygroup.contains(SystemProperty.getSystemProperty().getLoginPerson().getPersonid())){
                                        workSxt.geteDownLoadWorkList(getSearchStr(), stringBuilder);
                                    }
                                }
                            }

                        } catch (DaoException e) {
                            throw new HDException("??????????????????????????????!");
                        }

                        // ??????
                        setDataOrder();
                        selectable.setData(dataList);

                    }
                    break;
                case IEventType.DOWN_SXT_LIST:
                    if (objects[0] instanceof List<?>) {
                        List<SxtBean> sxtBeanlist = (List<SxtBean>) objects[0];

                        //?????????????????????
                        if (sxtBeanlist != null && sxtBeanlist.size() > 0) {

                            for (int i = 0; i < dataList.size(); i++) {
                                WorkTask workTask = (WorkTask) dataList.get(i);
                                List<SuperEntity> lwo = workTask
                                        .getChild(WorkOrder.class
                                                .getName());
                                for (int t = 0; t < lwo.size(); t++) {
                                    WorkOrder workOrder = (WorkOrder) lwo.get(t);
                                    for (int w = 0; w < sxtBeanlist.size(); w++) {
                                        if (sxtBeanlist.get(w).getLocation().equals(workOrder.getZylocation())) {
                                            getNetVideoBitmap(workOrder, sxtBeanlist.get(w).getCameras());
                                            workOrder.setCameras(sxtBeanlist.get(w).getCameras());

                                        }
                                    }

                                }

                            }

                        }
                        setDataOrder();
                        selectable.setData(dataList);
                    }


                    break;
                case IEventType.ACTIONBAR_SEARCH_CLICK:
                    // ????????????
                    if (objects[0] instanceof String) {
                        setSearchStr((String) objects[0]);
                        initData();
                    }
                    break;
                case IEventType.ACTIONBAR_SEARCH_DETAIL:
                    if (objects[0] instanceof View) {
                        View view = (View) objects[0];

                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View inflate = layoutInflater.inflate(R.layout.searchcontent, null);

                        TextView kssj = (TextView) inflate.findViewById(R.id.kssj_content);
                        kssj.setOnClickListener(TaskTabulationActivity.this);
                        TextView jssj = (TextView) inflate.findViewById(R.id.jssj_content);
                        jssj.setOnClickListener(TaskTabulationActivity.this);
                        TextView zydw = (TextView) inflate.findViewById(R.id.zydw_content);
                        zydw.setOnClickListener(TaskTabulationActivity.this);
                        TextView zylb = (TextView) inflate.findViewById(R.id.zylb_content);
                        zylb.setOnClickListener(TaskTabulationActivity.this);
                        zymc = (EditText) inflate.findViewById(R.id.zymc_content);
                        zyqy = (EditText) inflate.findViewById(R.id.zyqy_content);

                        Button qd = (Button) inflate.findViewById(R.id.queding);
                        qd.setOnClickListener(TaskTabulationActivity.this);

                        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        popupWindow = new PopupWindow(inflate);
                        popupWindow.setWidth(width);
                        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        //????????????
                        popupWindow.setFocusable(true);
                        //??????????????????????????????
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(0xaaaaaaaa));
                        popupWindow.setAnimationStyle(R.style.StatisticPopWindow);
                        popupWindow.showAsDropDown(view, 0, 30);
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                            @Override
                            public void onDismiss() {
                                // TODO Auto-generated method stub
                                backgroundAlpha(1.0f);
                            }
                        });


                        backgroundAlpha(0.6f);
                    }


                    break;
                case IEventType.ACCEPT:
                    if (objects[0] != null) {
                        list = (List) objects[0];
                        // Log.e("aaa",list.size()+"");
                    }
                    break;

                case IEventType.ACTIONBAR_ZYFH:
                    if (!fat) {
                        list = null;
                    }
                    if (list != null && list.size() > 0) {
                        new AlertDialog.Builder(TaskTabulationActivity.this)
                                .setMessage("?????????????????????????????????")
                                .setNegativeButton("??????", null)
                                .setPositiveButton("??????",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface arg0, int arg1) {
                                                // TODO Auto-generated method stub
                                                emptylist = new ArrayList<>();
                                                List<SuperEntity> lwt = dataList;
                                                for (int j = 0; j < dataList.size(); j++) {
                                                    WorkTask wt = (WorkTask) lwt
                                                            .get(j);
                                                    List<SuperEntity> lwo = wt
                                                            .getChild(WorkOrder.class
                                                                    .getName());
                                                    for (int l = 0; l < lwo.size(); l++) {
                                                        WorkOrder woo = (WorkOrder) lwo
                                                                .get(l);
                                                        for (int k = 0; k < list
                                                                .size(); k++) {
                                                            if (woo.getUd_zyxk_zysqid()
                                                                    .equals(list
                                                                            .get(k))) {
                                                                woo.setIsaccord("1");
                                                                if (woo.isDownloaded()) {
                                                                    IConnectionSource conSrc = null;
                                                                    IConnection con = null;

                                                                    try {
                                                                        conSrc = ConnectionSourceManager
                                                                                .getInstance()
                                                                                .getJdbcPoolConSource();
                                                                        con = conSrc
                                                                                .getConnection();
                                                                        BaseDao dao = new BaseDao();

                                                                        dao.executeUpdate(
                                                                                con,
                                                                                "update ud_zyxk_zysq set isaccord = '1' where ud_zyxk_zysqid = '"
                                                                                        + woo.getUd_zyxk_zysqid()
                                                                                        + "'");
                                                                        con.commit();
                                                                    } catch (Exception e) {
                                                                        // TODO
                                                                        // Auto-generated
                                                                        // catch
                                                                        // block
                                                                        e.printStackTrace();
                                                                    } finally {
                                                                        if (con != null) {
                                                                            try {
                                                                                conSrc.releaseConnection(con);
                                                                            } catch (SQLException e) {

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                emptylist.add(woo);
                                                                break;
                                                            }
                                                        }
                                                    }

                                                }
                                                if (emptylist.size() < 1) {
                                                    ToastUtils
                                                            .toast(getApplicationContext(),
                                                                    "??????????????????????????????");
                                                } else {
                                                    // ???????????????
                                                    // for(int
                                                    // w=0;w<emptylist.size();w++){
                                                    // WorkOrder
                                                    // workod=emptylist.get(w);
                                                    // String zysqid=(String)
                                                    // workod.getUd_zyxk_zysqid();
                                                    // //Log.e("bbb",zysqid);
                                                    // }
                                                    upWorkOrder(emptylist);
                                                }

                                            }
                                        })

                                .show();

                    } else if (list != null && list.size() == 0) {
                        Toast.makeText(TaskTabulationActivity.this, "??????????????????", Toast.LENGTH_LONG)
                                .show();
                    } else if (list == null) {
                        Toast.makeText(TaskTabulationActivity.this, "????????????????????????????????????",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case IEventType.ACTIONBAR_DX:

                    if (fat) {
                        // ????????????
                        fat = false;
                        selectable.setBz(fat);
                        selectable.setFlag(fat);

                    } else {
                        // ????????????
                        fat = true;
                        selectable.setBz(fat);
                        selectable.setFlag(fat);
                    }

                    break;
                case PhoneEventType.ZYPLIST_ITEM_CLICK:

                    if (fat) {

                    } else {

                        dataPosition = (Integer) objects[1];
                        Intent intent = new Intent();
                        intent.setClass(TaskTabulationActivity.this,
                                WorkOrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(WorkOrderDetailActivity.WORK_ORDER,
                                (WorkOrder) objects[0]);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,
                                WorkOrderDetailActivity.REQUESTCODE);

                    }
                    break;
                case IEventType.DOWN_WORK_LIST_LOAD:
                    // ?????????????????????

                    workorderdown.setGetDataSourceListener(eventLst);
                    workorderdown.geteDownLoadWorkList(getSearchStr());// ??????????????????

                    selectable.notifyDataSetChanged();

                    break;

            }
        }
    };

    private final String zystarttimeKey = "zystarttime";


    /**
     * ????????????????????????????????????
     *
     * @param bgAlpha (0-1 ??????????????????)
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }


    /**
     * setDataOrder:(??????????????????). <br/>
     * date: 2015???2???28??? <br/>
     *
     * @author lxf
     */
    private void setDataOrder() {
        if (dataList == null)
            return;
        String[] workOderStr = getResources().getStringArray(
                R.array.hd_hse_main_app_workorder_order);
        if (null == workOderStr || workOderStr.length <= 1) {
            return;
        }
        int count = dataList.size();
        List<SuperEntity> listchild = null;
        SuperEntity mainSup = null;
        for (int i = 0; i < count; i++) {
            mainSup = dataList.get(i);
            listchild = mainSup.getChild(WorkOrder.class.getName());
            if (null == listchild || listchild.size() <= 1) {
                // ???????????????1??????????????????
                continue;
            }
            List<SuperEntity> listChildOrder = new ArrayList<SuperEntity>();
            String subSuperType = null;
            // ??????????????????
            for (String zypOrder : workOderStr) {
                // ??????????????????
                for (SuperEntity subSuper : listchild) {
                    subSuperType = subSuper.getAttribute("zypclass") == null ? ""
                            : subSuper.getAttribute("zypclass").toString();
                    if (zypOrder.equalsIgnoreCase(subSuperType)) {
                        listChildOrder.add(subSuper);
                        listchild.remove(subSuper);
                        break;
                    }
                }
            }
            // ???????????????????????????????????????
            if (listchild.size() > 0) {
                listChildOrder.addAll(listchild);
            }
            // ???????????????KEY-Value
            mainSup.getChilds().remove(WorkOrder.class.getName());
            // ????????????
            mainSup.setChild(WorkOrder.class.getName(), listChildOrder);
            // dataList.get(i).setChild(entityName, lstChild);
        }
        // bubble_sort(dataList);
    }

    /**
     * bubble_sort:(????????????????????????). <br/>
     * date: 2014???11???26??? <br/>
     *
     * @param unsorted
     * @author lxf
     */
    private void bubble_sort(List<SuperEntity> unsorted) {
        if (unsorted == null || unsorted.size() == 1) {
            return;
        }
        int len = unsorted.size();
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                try {
                    // ??????????????????
                    if (!UtilTools.dataTimeCompare(unsorted.get(i)
                            .getAttribute(zystarttimeKey).toString(), unsorted
                            .get(j).getAttribute(zystarttimeKey).toString())) {
                        SuperEntity temp = unsorted.get(i);
                        unsorted.set(i, unsorted.get(j));
                        unsorted.set(j, temp);
                    }
                } catch (HDException e) {
                    ToastUtils.toast(getBaseContext(), "??????????????????????????????,??????????????????");
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param workOrder
     */
    public void refreshDatas(SuperEntity workOrder) {
        if (workOrder == null && dataPosition != -1)
            return;
        refreshListData = dataList.get(dataPosition).getChild(
                WorkOrder.class.getName());
        for (SuperEntity subEntity : refreshListData) {
            if (workOrder.getAttribute(UD_ZYXK_ZYSQID).equals(
                    subEntity.getAttribute(UD_ZYXK_ZYSQID))) {
                refreshListData.remove(subEntity);
                break;
            }
        }
        // ?????????????????????????????????????????????
        if (refreshListData.size() < 1) {
            dataList.remove(dataPosition);
        } else {
            dataList.get(dataPosition).getChilds()
                    .remove(WorkOrder.class.getName());
            dataList.get(dataPosition).setChild(WorkOrder.class.getName(),
                    refreshListData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == WorkOrderDetailActivity.REQUESTCODE && data != null) {
            refreshDatas((SuperEntity) data
                    .getSerializableExtra(WorkOrderDetailActivity.WORK_ORDER));
            if (selectable != null && dataList != null)
                selectable.setData(dataList);
        }
        if (resultCode == WorkOrderDetailActivity.CODE) {
            workorderdown.setGetDataSourceListener(eventLst);
            if (workorderdown != null) {
                workorderdown.geteDownLoadWorkList(getSearchStr());// ??????????????????
            }

        }
    }

    @Override
    public WorkTaskDBSrv getWorkTaskObject() {
        // TODO Auto-generated method stub
        return new WorkTaskSrv();
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "???????????????";
    }

    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return appModule != null ? "hse-nd-phone-app" : "hse-wov-phone-app";
    }

    /**
     * upzyp:TODO(???????????????).
     */
    private UpOnlineZypProgressDialog upzyp = null;

    /**
     * ???????????????
     */
    private void upWorkOrder(List<WorkOrder> workOrderList) {

        upzyp = new UpOnlineZypProgressDialog(this, workOrderList);
        upzyp.setGetDataSourceListener(eventLst);
        upzyp.execute("??????", "???????????????????????????????????????.....", "", workOrderList);

    }

    @Override
    public void onClick(final View v) {
        final TextView textView = (TextView) v;
        int id = v.getId();
        //??????????????????????????????????????????dialog
        View dateTimeLayout = getLayoutInflater().inflate(
                R.layout.datepicker_dialog_and_timepicker, null);
        final DatePicker datePicker = (DatePicker) dateTimeLayout
                .findViewById(R.id.search_detail_datepicker);
        final TimePicker timePicker = (TimePicker) dateTimeLayout.
                findViewById(R.id.search_detail_timepicker);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create().setCanceledOnTouchOutside(true);
        //???????????????dialog
        View inflate = getLayoutInflater().inflate(R.layout.unifylistivew, null);
        if (id == R.id.kssj_content) {
            builder.setTitle("??????????????????");
            builder.setView(dateTimeLayout);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth() + 1;
                    int dayOfMonth = datePicker.getDayOfMonth();
                    Integer currentHour = timePicker.getCurrentHour();
                    Integer currentMinute = timePicker.getCurrentMinute();

                    ksdate = year + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + (dayOfMonth < 10 ?
                            ("0" + dayOfMonth) : (dayOfMonth)) + "\t\t" + (currentHour < 10 ?
                            ("0" + currentHour) : (currentHour)) + ":" + (currentMinute < 10 ?
                            ("0" + currentMinute) : (currentMinute));
                    textView.setText(ksdate);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        } else if (id == R.id.jssj_content) {

            builder.setTitle("??????????????????");
            builder.setView(dateTimeLayout);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth() + 1;
                    int dayOfMonth = datePicker.getDayOfMonth();
                    Integer currentHour = timePicker.getCurrentHour();
                    Integer currentMinute = timePicker.getCurrentMinute();

                    jsdate = year + "-" + (month < 10 ? ("0" + month) : (month)) + "-" + (dayOfMonth < 10 ?
                            ("0" + dayOfMonth) : (dayOfMonth)) + "\t\t" + (currentHour < 10 ?
                            ("0" + currentHour) : (currentHour)) + ":" + (currentMinute < 10 ?
                            ("0" + currentMinute) : (currentMinute));
                    textView.setText(jsdate);
                }
            });
            builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else if (id == R.id.zydw_content) {
            View inflate1 = getLayoutInflater().inflate(R.layout.hd_hse_wov_dept_dialog_layout, null);
            hm.clear();
            unitList.clear();
            builder.setTitle("????????????");
            builder.setView(inflate1);
            final AlertDialog show = builder.show();
//            DisplayMetrics displayMetrics=new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            show.getWindow().setLayout((displayMetrics.widthPixels-200), (displayMetrics.heightPixels-500));
            List<Department> departments = null;
            ListView unityListView = (ListView) inflate1.findViewById(R.id.data_listview);
            final EditText searchTxt = (EditText) inflate1
                    .findViewById(R.id.search_txt);
            Button button = (Button) inflate1.findViewById(R.id.search_btn);
            ArrayAdapter adapter = null;
            final ArrayList convertList = new ArrayList();
            try {

                departments = queryWorkInfo.queryDept(false, null);
                if (departments != null && departments.size() > 0) {
                    for (int i = 0; i < departments.size(); i++) {
                        String description = departments.get(i).getDescription();
                        String deptnum = departments.get(i).getDeptnum();
                        unitList.add(description);
                        hm.put(description, deptnum);
                    }
                    convertList.addAll(unitList);
                }
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, convertList);
                unityListView.setAdapter(adapter);

                unityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        workUnit = (String) parent.getAdapter().getItem(position);
                        workUnit_value = hm.get(workUnit);
                        textView.setText(workUnit);
                        show.cancel();
                    }
                });

            } catch (HDException e) {
                e.printStackTrace();
            }

            final ArrayAdapter finalAdapter = adapter;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    String str = searchTxt.getText().toString();
                    if (str != null && !str.equals("")) {
                        convertList.clear();
                        for (int i = 0; i < unitList.size(); i++) {
                            if (unitList.get(i).contains(str)) {
                                String s = unitList.get(i);
                                convertList.add(s);
                            }
                        }
                        finalAdapter.notifyDataSetChanged();
                    } else {

                        finalAdapter.notifyDataSetChanged();
                    }
                }
            });

        } else if (id == R.id.zylb_content) {
            hm.clear();
            unitList.clear();
            builder.setTitle("????????????");
            builder.setView(inflate);
            final AlertDialog show = builder.show();
            final List<Domain> workType = getWorkType();
            ListView unityListView = (ListView) inflate.findViewById(R.id.unitylistview);

            if (workType != null && workType.size() > 0) {
                for (int i = 0; i < workType.size(); i++) {
                    String description = workType.get(i).getDescription();
                    String value = workType.get(i).getValue();
                    unitList.add(description);
                    hm.put(description, value);
                }
            }
            unityListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, unitList));

            unityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    zuoYeType = (String) parent.getAdapter().getItem(position);
                    zuoYeType_value = hm.get(zuoYeType);
                    textView.setText(zuoYeType);
                    show.cancel();
                }
            });
        } else if (id == R.id.queding) {

            if (("".equals(zymc.getText().toString())) &&
                    ("".equals(zyqy.getText().toString()))) {
                setSearchStr(ksdate + "," + jsdate + "," + workUnit_value + "," + zuoYeType_value + "," +
                        "null" + "," + "null");
            } else if ("".equals(zymc.getText().toString())) {
                setSearchStr(ksdate + "," + jsdate + "," + workUnit_value + "," + zuoYeType_value + "," +
                        "null" + "," + zyqy.getText().toString());
            } else if ("".equals(zyqy.getText().toString())) {
                setSearchStr(ksdate + "," + jsdate + "," + workUnit_value + "," + zuoYeType_value + "," +
                        zymc.getText().toString() + "," + "null");
            } else {
                setSearchStr(ksdate + "," + jsdate + "," + workUnit_value + "," + zuoYeType_value + "," +
                        zymc.getText().toString() + "," + zyqy.getText().toString());
            }


            workorderdown.getDetailSearchWorkList(getSearchStr());// ??????????????????
            ksdate = null;
            jsdate = null;
            workUnit_value = null;
            zuoYeType_value = null;
            popupWindow.dismiss();
        }


    }

    public List<Domain> getWorkType() {

        String sql = "select value,description from alndomain where domainid='UDZYLX'";
        List<Domain> domainList = null;
        try {
            domainList = (List<Domain>) dao.
                    executeQuery(sql, new EntityListResult(Domain.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }


        return domainList;
    }
}
