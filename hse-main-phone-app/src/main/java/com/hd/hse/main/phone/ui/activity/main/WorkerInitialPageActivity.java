package com.hd.hse.main.phone.ui.activity.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hd.hse.cc.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.event.homepage.IAppModuleClick;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dc.phone.ui.activity.download.DownLoadActivity;
import com.hd.hse.entity.other.WorkerPageData;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.adapter.GasDetectAdapter;
import com.hd.hse.main.phone.ui.activity.adapter.GasTime;
import com.hd.hse.main.phone.ui.activity.adapter.MyListViewAdapter;
import com.hd.hse.main.phone.ui.activity.adapter.MyUniformListAdapter;
import com.hd.hse.main.phone.ui.activity.adapter.Time;
import com.hd.hse.main.phone.ui.activity.business.workorder.ServerWorkTask;
import com.hd.hse.system.SystemProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dubojian on 2017/9/15.
 */

public class WorkerInitialPageActivity extends BaseFrameActivity implements View.OnClickListener {


    private TextView messageTextView;
    private ListView messageListView;
    private ListView pzNoticeListview;
    private ListView gasDetectNoticeListview;
    private TextView pzNoticeTextview;
    private TextView gasDetectNoticeTextview;
    private MyListViewAdapter listViewAdapter;
    private ProgressDialog dialog;

    private MyUniformListAdapter uniformListAdapter;
    private GasDetectAdapter gasDetectAdapter;


    //expandablelistview的childview的集合
    private ArrayList<String> list;

    private String count;

    //票证名称集合
    private List<String> pzlist;

    //总id
    private List<String> pzIdList;
    //本地票证id
    private List<String> localpzIdList;
    //网络id的集合，保证有线程的不在创建线程
    private List<String> net_pzIdList;
    private List<Long> pzMillionSecond;

    //气体检测的集合
    private List<String> gaslist;

    //总Id
    private List<String> gasIdList;
    //本地气体id
    private List<String> localGasIdList;
    //网络气体id
    private List<String> net_gasIdList;
    private List<Long> millionSecond;

    private BaseDao dao = new BaseDao();

    //右侧菜单栏
//    private ActionBarMainMenu actionBarMainMenu;
    /**
     * listAppModule:TODO(主功能更菜单模块).
     */
    private List<AppModule> listAppModule;
    //联网请求数据的类
    private ServerWorkTask workorderdown = null;
    private Object acceptData;
    //本地票证数据的集合
    private List<WorkOrder> lstWorkOrder;

    private List<WorkerPageData.ZYPTIMEBean> combine = new ArrayList<>();
    private List<WorkerPageData.ZYPTIMEBean> newCombine = new ArrayList<>();

    private List<WorkerPageData.QTJCTIMEBean> gascombine = new ArrayList<>();
    private List<WorkerPageData.QTJCTIMEBean> gasNewCombine = new ArrayList<>();

    private GasTime gasTime;
    private Time time;
    private List<WorkerPageData.ZYPTIMEBean> zyptime;
    private ImageView refreshImageView1;
    private ImageView refreshImageView2;
    private ImageView refreshImageView3;
    private boolean r2;
    private boolean r3;
    private boolean r1;
    private ImageView dot1;
    private ImageView dot2;
    private ImageView dot3;
    private LinearLayout dot_linear;
    private ImageView dot1_image;
    private ImageView dot2_image;
    private ImageView dot3_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_main_phone_app_layout_dalian_main_worker);

        // 设置导航栏信息
        setCustomActionBar(false, event, new String[]{IActionBar.DALIAN_INITIAL_PAGE_IMAGE,
                IActionBar.DALIAN_INITIAL_PAGE_UNIT, IActionBar.DALIAN_INITIAL_PAGE_NAME,
                IActionBar.DALIAN_INITIAL_PAGE_CENTER_IMAGE, IActionBar.DALIAN_INITIAL_PAGE_DATE,
        });
//        IActionBar.IV_LMORE
//        IActionBar.TV_MORE

        // 设置右侧菜单栏
//        actionBarMainMenu = new ActionBarMainMenu(this);

        time = new Time();
        gasTime = new GasTime();
        list = new ArrayList();

        pzlist = new ArrayList<>();
        pzMillionSecond = new ArrayList<>();
        pzIdList = new ArrayList<>();
        localpzIdList = new ArrayList<>();
        net_pzIdList = new ArrayList<>();


        gaslist = new ArrayList<>();
        millionSecond = new ArrayList<>();
        gasIdList = new ArrayList<>();
        localGasIdList = new ArrayList<>();
        net_gasIdList = new ArrayList<>();

        initParam();
        initView();


    }


    private void initParam() {

        // 获取公共模块信息
        listAppModule = SystemProperty.getSystemProperty()
                .getMainAppModulelist("SJ");
        // 设置左侧app模块抽屉
        setNavContent(listAppModule, "hse-main-phone-app");
        // 如果只有一个模块的话就自动跳转到该界面
        if (listAppModule.size() == 2) {
            for (AppModule app : listAppModule) {
                if (app.getCode().equals("hse-main-phone-app")) {
                    for (AppModule appModule : listAppModule) {
                        if (!appModule.equals("hse-main-phone-app")) {
                            try {
                                IAppModuleClick appClick = newAppModuleClick(appModule
                                        .getClickdealclass());
                                appClick.appModuleOnClick(appModule);
                            } catch (HDException e) {
                                ToastUtils.imgToast(WorkerInitialPageActivity.this,
                                        R.drawable.hd_hse_common_msg_wrong,
                                        e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        if (listAppModule.size() == 1
                && !listAppModule.get(0).getCode().equals("hse-main-phone-app")) {
            try {
                IAppModuleClick appClick = newAppModuleClick(listAppModule.get(
                        0).getClickdealclass());
                appClick.appModuleOnClick(listAppModule.get(0));
            } catch (HDException e) {
                ToastUtils.imgToast(WorkerInitialPageActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }
        }
    }


    private void initData() {
        //联网请求
        workorderdown = new ServerWorkTask(this);
        workorderdown.setGetDataSourceListener(event);
        workorderdown.setPageType("USEPAGE");
        workorderdown.geteDownLoadWorkList("");// 异步查询数据

        //本地票证数据
        StringBuilder zypTime = new StringBuilder();

        zypTime.append("select case when y.YQENDTIME is not null then YQENDTIME else SJENDTIME ")
                .append(" end  SJENDTIME,s.ud_zyxk_zysqid,p.hctime,ZYNAME||lx.description ZYNAME from")
                .append(" UD_ZYXK_ZYSQ s left join  (select * from ALNDOMAIN where DOMAINID='UDZYLX') lx on  ")
                .append(" s.zypclass=lx.value left join (select UD_ZYXK_ZYSQID,MAX(YQENDTIME) YQENDTIME from ")
                .append(" UD_ZYXK_ZYYQ group by UD_ZYXK_ZYSQID) y on s.ud_zyxk_zysqid=y.ud_zyxk_zysqid left join (select case when RELN.INPUT_VALUE is not null then RELN.INPUT_VALUE " +
                        "else 0 end hctime FROM SYS_RELATION_INFO RELN  where RELN.SYS_TYPE ='HCSJPZ' and RELN.ISQY=1 and RELN.dr = 0) p where ")
                .append("sddept='").append(SystemProperty.getSystemProperty().getLoginPerson().getDepartment())
                .append("' and STATUS IN ('APPR')  and (((sjendtime)-julianday('" + ServerDateManager.getCurrentTime() + "'))*24+(p.hctime/60))<1");

        String sql = zypTime.toString();
        lstWorkOrder = null;
        try {

            lstWorkOrder = (List<WorkOrder>) dao.executeQuery(
                    sql, new EntityListResult(WorkOrder.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        combine.clear();
        localpzIdList.clear();
        if (lstWorkOrder != null && lstWorkOrder.size() > 0) {
            for (int i = 0; i < lstWorkOrder.size(); i++) {
                WorkerPageData.ZYPTIMEBean zyptimeBean = new WorkerPageData.ZYPTIMEBean();
                zyptimeBean.setZyname(lstWorkOrder.get(i).getZyname());
                long l = parseDateToMillis(lstWorkOrder.get(i).getSjendtime());
                int hctime = Integer.parseInt(lstWorkOrder.get(i).getHctime());
                long ft = l + hctime * 60 * 1000;
                String SjendTime = parseMillisToDate(ft);
                zyptimeBean.setSjendtime(SjendTime);
                zyptimeBean.setId(lstWorkOrder.get(i).getUd_zyxk_zysqid());
                combine.add(zyptimeBean);
                localpzIdList.add(lstWorkOrder.get(i).getUd_zyxk_zysqid());
            }
        }

        //本地气体数据
        String gassql = "select q.UD_ZYXK_ZYSQID ud_zyxk_zysqid, zyname || lx.description zyname, jctime, cjcsx"
                + " from (select sddept,status,zyname,zypclass,ud_zyxk_zysqid,case when jcsx='qtjcsx01'"
                + " then 2*60  when jcsx='qtjcsx02' then  2*2*60  when jcsx='qtjcsx03'  then 2*2*2*60  end  cjcsx" +
                " from UD_ZYXK_ZYSQ x left join UD_ZYXK_QTJCSX j on x.zypclass = j.wttype where (julianday(x.sjendtime)>julianday('" + ServerDateManager.getCurrentTime() + "'))) s" +
                " left join (select UD_ZYXK_ZYSQID, MAX(JCTIME) JCTIME from UD_ZYXK_QTJC group by UD_ZYXK_ZYSQID) q on s.ud_zyxk_zysqid = q.ud_zyxk_zysqid" +
                " left join (select * from ALNDOMAIN where DOMAINID = 'UDZYLX') lx on s.zypclass = lx.value where jctime is not null and status IN ('APPR')" +
                " and (((julianday(JCTIME)-julianday('" + ServerDateManager.getCurrentTime() + "'))* 24*60)+cjcsx)<60";


        List<WorkOrder> gasWorkOrder = null;
        try {
            gasWorkOrder = (List<WorkOrder>) dao.executeQuery(gassql, new EntityListResult(WorkOrder.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        gascombine.clear();
        localGasIdList.clear();
        if (gasWorkOrder != null && gasWorkOrder.size() > 0) {


            for (int k = 0; k < gasWorkOrder.size(); k++) {

                WorkerPageData.QTJCTIMEBean qtjctimeBean = new WorkerPageData.QTJCTIMEBean();
                qtjctimeBean.setZyname(gasWorkOrder.get(k).getZyname());
                qtjctimeBean.setJctime(gasWorkOrder.get(k).getJctime());
                qtjctimeBean.setSx(gasWorkOrder.get(k).getCjcsx() + "");
                qtjctimeBean.setId(gasWorkOrder.get(k).getUd_zyxk_zysqid());
                gascombine.add(qtjctimeBean);
                localGasIdList.add(gasWorkOrder.get(k).getUd_zyxk_zysqid());
            }
        }


    }

    private String parseMillisToDate(long ft) {
        //毫秒转日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(ft);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }


    private long parseDateToMillis(String sjendtime) {
        // 日期转毫秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(sjendtime).getTime();
        } catch (ParseException e) {

        }
        return millionSeconds;
    }

    private void initView() {
        dot_linear = (LinearLayout) findViewById(R.id.linearlayout_dot);
        dot1_image = new ImageView(this);
        dot2_image = new ImageView(this);
        dot3_image = new ImageView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0, 0, 82, 0);  //设置间距
        dot1_image.setLayoutParams(layout);
        dot2_image.setLayoutParams(layout);
        LinearLayout.LayoutParams layoutp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutp.setMargins(0, 0, 0, 0);  //设置间距
        dot3_image.setLayoutParams(layoutp);
        dot1_image.setImageResource(R.drawable.yuan2);
        dot2_image.setImageResource(R.drawable.yuan2);
        dot3_image.setImageResource(R.drawable.yuan1);

        if (SystemProperty.getSystemProperty().isBothShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot_linear.addView(dot2_image);
            dot_linear.addView(dot3_image);

        } else if (SystemProperty.getSystemProperty().isLanZhouMShouYe() ||
                SystemProperty.getSystemProperty().isLanZhouWShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot_linear.addView(dot3_image);

        } else {
            dot_linear.removeAllViews();
        }

//        dot1_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(WorkerInitialPageActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        dot2_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(WorkerInitialPageActivity.this,ManagerMainActivity.class);
//                startActivity(intent);
//            }
//        });


        refreshImageView1 = (ImageView) findViewById(R.id.refresh);
        refreshImageView2 = (ImageView) findViewById(R.id.refresh2);
        refreshImageView3 = (ImageView) findViewById(R.id.refresh3);
        messageTextView = (TextView) findViewById(R.id.worker_messagenotify_textview);
        messageListView = (ListView) findViewById(R.id.worker_messagenotify_listview);
        pzNoticeListview = (ListView) findViewById(R.id.worker_pz_notice_listview);
        gasDetectNoticeListview = (ListView) findViewById(R.id.worker_gasdetect_notice_listview);
        pzNoticeTextview = (TextView) findViewById(R.id.worker_pz_notice_textview);
        gasDetectNoticeTextview = (TextView) findViewById(R.id.worker_gasdetect_notice_textview);

        refreshImageView1.setOnClickListener(this);
        refreshImageView2.setOnClickListener(this);
        refreshImageView3.setOnClickListener(this);

    }


    /**
     * eventLst:TODO(标题栏事件).
     */
    public IEventListener event = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub
            if (arg0 == IEventType.ACTIONBAR_MORE_CLICK) {
//                actionBarMainMenu.showAsDropDown((View) arg1[0]);
            } else if (arg0 == IEventType.DALIANCENTTERPIC) {
                Intent intent = new Intent(WorkerInitialPageActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (arg0 == IEventType.DOWN_WORK_LIST_SHOW) {

                if (arg1[0] instanceof Object) {
                    acceptData = arg1[0];
                    if (acceptData.toString() != null) {
                        list.clear();
                        WorkerPageData workerPageData = new Gson().fromJson(acceptData.toString(), WorkerPageData.class);

                        List<WorkerPageData.MESSAGEBean> message = workerPageData.getMESSAGE();
                        if (message != null && message.size() > 0) {
                            for (int i = 0; i < message.size(); i++) {

                                if (message.get(i).getContent() != null) {
                                    list.add(message.get(i).getContent());
                                }
                                if (message.get(i).getMsgcount() != null) {
                                    count = message.get(i).getMsgcount();
                                }

                            }
                        }


                        zyptime = workerPageData.getZYPTIME();

                        for (int j = 0; j < combine.size(); j++) {
                            for (int w = 0; w < zyptime.size(); w++) {
                                if (localpzIdList.get(j).equals(zyptime.get(w).getId())) {
                                    zyptime.remove(zyptime.get(w));
                                }
                            }
                        }
                        newCombine.clear();
                        newCombine.addAll(combine);
                        newCombine.addAll(zyptime);
                        //按照时间对作业票进行排序
                        for (int s = 0; s < newCombine.size(); s++) {

                            for (int k = s + 1; k < newCombine.size(); k++) {
                                if (parseDateToMillis(newCombine.get(s).getSjendtime()) <
                                        parseDateToMillis(newCombine.get(k).getSjendtime())) {
                                    WorkerPageData.ZYPTIMEBean zyptimeBean = newCombine.get(k);
                                    newCombine.set(k, newCombine.get(s));
                                    newCombine.set(s, zyptimeBean);
                                }
                            }

                        }


                        net_pzIdList.clear();
                        if (zyptime != null && zyptime.size() > 0) {
                            for (int j = 0; j < zyptime.size(); j++) {

                                net_pzIdList.add(zyptime.get(j).getId());

                            }
                        }

                        List<WorkerPageData.QTJCTIMEBean> qtjctime = workerPageData.getQTJCTIME();


                        for (int z = 0; z < gascombine.size(); z++) {
                            for (int m = 0; m < qtjctime.size(); m++) {
                                if (localGasIdList.get(z).equals(qtjctime.get(m).getId())) {
                                    qtjctime.remove(qtjctime.get(m));
                                }
                            }
                        }
                        gasNewCombine.clear();
                        gasNewCombine.addAll(gascombine);
                        gasNewCombine.addAll(qtjctime);


                        //按照时间对气体进行排序
                        for (int p = 0; p < gasNewCombine.size(); p++) {

                            for (int e = p + 1; e < gasNewCombine.size(); e++) {
                                long preM = parseDateToMillis(gasNewCombine.get(p).getJctime());
                                int preS = Integer.parseInt(gasNewCombine.get(p).getSx());
                                long postM = parseDateToMillis(gasNewCombine.get(e).getJctime());
                                int postS = Integer.parseInt(gasNewCombine.get(e).getSx());
                                if ((preM + preS * 60 * 1000) < (postM + postS * 60 * 1000)) {
                                    WorkerPageData.QTJCTIMEBean qtjctimeBean = gasNewCombine.get(e);
                                    gasNewCombine.set(e, gasNewCombine.get(p));
                                    gasNewCombine.set(p, qtjctimeBean);
                                }
                            }

                        }

                        net_gasIdList.clear();
                        if (qtjctime != null && qtjctime.size() > 0) {

                            for (int w = 0; w < qtjctime.size(); w++) {
                                net_gasIdList.add(qtjctime.get(w).getId());
                            }

                        }

                    }
                    if (!r1) {
                        refreshView1();
                    }
                    if (!r2) {
                        refreshView2();

                    }
                    if (!r3) {
                        refreshView3();

                    }


                }

            }
        }
    };

    private void refreshView3() {

        gaslist.clear();
        millionSecond.clear();
        gasIdList.clear();

        for (int c = 0; c < gasNewCombine.size(); c++) {

            gaslist.add(gasNewCombine.get(c).getZyname());
            long gasTime = parseDateToMillis(gasNewCombine.get(c).getJctime());
            long currentTime = ServerDateManager.getCurrentTimeMillis();
            String cjcsx = gasNewCombine.get(c).getSx();
            long totalTime = gasTime + Integer.parseInt(cjcsx) * 60 * 1000;
            long second = (totalTime - currentTime) / 1000;
            millionSecond.add(second);
            gasIdList.add(gasNewCombine.get(c).getId());


        }


        gasDetectAdapter = new GasDetectAdapter(WorkerInitialPageActivity.this, gaslist, millionSecond, true);
        gasDetectNoticeListview.setAdapter(gasDetectAdapter);
        gasDetectNoticeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t = (TextView) view.findViewById(R.id.tv_gasdistance);
                //网络id包含点击的id

                if (localGasIdList.contains(gasIdList.get(position))) {
//
                    Intent intent = new Intent(WorkerInitialPageActivity.this, LocationSwCard.class);
                    Bundle mBundle = new Bundle();
                    // 传递目标跳转activity类
                    mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
                            com.hd.hse.osr.phone.ui.activity.worktask.TaskTabulationActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);


                } else if (net_gasIdList.contains(gasIdList.get(position))) {
                    if ("已过期".equals(t.getText())) {
                        for (int p = 0; p < newCombine.size(); p++) {
                            if (gasIdList.get(position).equals(newCombine.get(p).getId())) {
                                long l = parseDateToMillis(newCombine.get(p).getSjendtime());
                                long xt = 120 * 60 * 1000;
                                long currentTime = ServerDateManager.getCurrentTimeMillis();
                                if ((l - currentTime - xt) < 0) {
                                    //过期
                                    ToastUtils.toast(WorkerInitialPageActivity.this, "对应的作业票已过期");
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(WorkerInitialPageActivity.this)
                                            .setMessage("是否下载作业票")
                                            .setNegativeButton("取消", null)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(WorkerInitialPageActivity.this,
                                                            DownLoadActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                    builder.show();
                                }
                            }
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WorkerInitialPageActivity.this)
                                .setMessage("是否下载作业票")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(WorkerInitialPageActivity.this,
                                                DownLoadActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        builder.show();
                    }

                }


            }
        });

    }


    private void refreshView2() {

        pzlist.clear();
        pzIdList.clear();
        pzMillionSecond.clear();

        for (int i = 0; i < newCombine.size(); i++) {

            pzlist.add(newCombine.get(i).getZyname());
            long sjTime = parseDateToMillis(newCombine.get(i).getSjendtime());
            long currentTime = ServerDateManager.getCurrentTimeMillis();
            long second = (sjTime - currentTime) / 1000;
            pzMillionSecond.add(second);
            pzIdList.add(newCombine.get(i).getId());

        }

        uniformListAdapter = new MyUniformListAdapter(WorkerInitialPageActivity.this, pzlist, pzMillionSecond, true);
        pzNoticeListview.setAdapter(uniformListAdapter);
        pzNoticeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //本地id包含点击的id
                if (localpzIdList.contains(pzIdList.get(position))) {
                    TextView t = (TextView) view.findViewById(R.id.tv_distance);
                    if ("已过期".equals(t.getText())) {
                        ToastUtils.toast(WorkerInitialPageActivity.this, "请到PC端操作");
                    } else {
                        Intent intent = new Intent(WorkerInitialPageActivity.this, LocationSwCard.class);
                        Bundle mBundle = new Bundle();
                        // 传递目标跳转activity类
                        mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
                                TaskTabulationActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }
                } else if (net_pzIdList.contains(pzIdList.get(position))) {
                    TextView tt = (TextView) view.findViewById(R.id.tv_distance);
                    if ("已过期".equals(tt.getText())) {
                        ToastUtils.toast(WorkerInitialPageActivity.this, "请到PC端操作");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WorkerInitialPageActivity.this)
                                .setMessage("是否下载作业票")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(WorkerInitialPageActivity.this,
                                                DownLoadActivity.class);
                                        startActivity(intent);
                                    }
                                });
                        builder.show();
                    }

                }
            }
        });

    }

    private void refreshView1() {
        if ("0".equals(count)) {
            messageTextView.setText("消息通知");
        } else {
            messageTextView.setText("消息通知" + "(共" + count + "条)");
        }
        listViewAdapter = new MyListViewAdapter(WorkerInitialPageActivity.this, list);
        messageListView.setAdapter(listViewAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            logout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logout() {
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确定要退出?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                SystemApplication.getInstance().exit();

                dialog.dismiss();
                // 设置你的操作事项
            }
        });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gasTime.setFlag(false);
        gasTime.setClear(true);
        time.setFlag(false);
        time.setClear(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.refresh) {
            r1 = false;
            r2 = true;
            r3 = true;
            initData();

        } else if (id == R.id.refresh2) {
            r2 = false;
            r1 = true;
            r3 = true;
            initData();
        } else if (id == R.id.refresh3) {
            r3 = false;
            r1 = true;
            r2 = true;
            initData();
        }
    }

}
