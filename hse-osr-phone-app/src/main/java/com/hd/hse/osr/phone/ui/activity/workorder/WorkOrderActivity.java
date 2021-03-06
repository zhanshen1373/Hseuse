package com.hd.hse.osr.phone.ui.activity.workorder;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.module.activity.WorkOrderBaseActivity;
import com.hd.hse.common.module.phone.ui.module.frament.CounterSignFramment;
import com.hd.hse.common.module.ui.model.fragment.GasesCheckFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osr.phone.R;
import com.hd.hse.osr.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.osr.phone.ui.fragment.measure.MeasureFragmentosr;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.ServiceActivityUtils;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorkOrderActivity extends WorkOrderBaseActivity {
    public WorkMeasureReview workMeasureReview = null;
    private static final Logger logger = LogUtils
            .getLogger(WorkOrderActivity.class);
    private BaseDao dao = null;

    @Override
    public void setCustomMenuBarInfo() {
        //??????????????????????????????????????????????????????????????????
        QueryRelativeConfig relativeConfig = new QueryRelativeConfig();
        boolean isShow = relativeConfig
                .isHadRelative(IRelativeEncoding.GASDETECTPAUSE);
        // ?????????????????????????????????
        if (getIntent().hasExtra(TaskTabulationActivity.QTJC_FC) && isShow) {
            if (isClicked() && isInTime()) {
                setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER,
                        IActionBar.QTJC_PAUSE});

//                IActionBar.QTJC_STOP
            } else {
                setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER
                });

//                IActionBar.QTJC_STOP
            }

        } else {
            setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                    IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER});
        }

    }


    private boolean isClicked() {
        dao = new BaseDao();
        String sql = "select qtjcpausetime from ud_zyxk_zysq where ud_zyxk_zysqid='" + workOrder.getUd_zyxk_zysqid() + "'";
        try {
            WorkOrder executeQuery = (WorkOrder) dao.executeQuery(sql.toString(), new EntityResult(WorkOrder.class));
            if (executeQuery != null) {
                if (executeQuery.getQtjcpausetime() != null)
                    return false;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        //????????????
        return true;
    }


    private boolean isInTime() {

        boolean flag = false;
        Calendar instance = Calendar.getInstance();
        instance.setTime(ServerDateManager.getCurrentDate());

        //?????????
        int year = instance.get(Calendar.YEAR);
        //?????????
        int month = (instance.get(Calendar.MONTH)) + 1;
        //????????????????????????????????????
        int day_of_month = instance.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String sql = "select * from alndomain where domainid='UDPAUSETIME'";

        List<Domain> domains = null;
        try {
            domains = (List<Domain>) dao.executeQuery(sql.toString(), new EntityListResult(Domain.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < domains.size(); i++) {

            String value = domains.get(i).getDescription();
            if (value != null) {
                String[] split = value.split("-");
                String starttime = year + "-" + month + "-" + day_of_month + " " + split[0];
                String endtime = year + "-" + month + "-" + day_of_month + " " + split[1];

                Date parse = null;
                Date parse1 = null;
                try {
                    parse = simpleDateFormat.parse(starttime);
                    parse1 = simpleDateFormat.parse(endtime);
                    Date date = ServerDateManager.getCurrentDate();
                    if (date.after(parse) && date.before(parse1)) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }

        return flag;


    }

    @Override
    public void setNaviBarIsCompLete() {
        try {
            // ??????PDA???????????????????????????
            SuperEntity currentNaviTouchEntity = ServiceActivityUtils
                    .getRefreshWorkConfigInfo(workMeasureReview,
                            (PDAWorkOrderInfoConfig) getNaviCurrentEntity());
            // ????????????????????????
            setNaviFinish(currentNaviTouchEntity);
        } catch (HDException e) {
            logger.error(e.getMessage());
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
    }

    @Override
    public Class<? extends NaviFrameFragment> getNavFragmentClass(
            Integer contype) {
        Class<? extends NaviFrameFragment> resultClz = null;
        switch (contype) {
            case IConfigEncoding.HARM_TYPE:
                // ??????????????????Fragment

                break;
            case IConfigEncoding.ENERGY_TYPE:
                // resultClz = ???????????? Fragment???????????????
                break;
            case IConfigEncoding.GAS_TYPE:
                resultClz = GasesCheckFragment.class;
                break;
            case IConfigEncoding.MEASURE_TYPE:
                // resultClz = ???????????? Fragment???????????????
                resultClz = MeasureFragmentosr.class;
                break;
            case IConfigEncoding.PPE_TYPE:

                break;
            case IConfigEncoding.SIGN_TYPE:
                resultClz = CounterSignFramment.class;
                break;
            case IConfigEncoding.YQ_SIGN_TYPE:
                break;
            default:
                throw new IllegalArgumentException("IConfigEncoding ??????????????????:"
                        + contype);
        }
        return resultClz;

    }

    /**
     * TODO
     *
     * @see com.hd.hse.common.module.phone.ui.module.activity.WorkOrderBaseActivity#distribute()
     */
    @Override
    public void distribute() {
        // TODO Auto-generated method stub
        // super.distribute();
        //
        // try {
        // notifyAllNaviItems(ServiceActivityUtils.getRefreshWorkConfigInfos(workOrder,
        // listPDAConfigInfo));
        // } catch (HDException e) {
        // // TODO Auto-generated catch block
        // logger.error(e.getMessage(), e);
        // ToastUtils.imgToast(this, R.drawable.hd_common_message_error,
        // "????????????????????????????????????");
        // }
    }

    @Override
    public IEventListener getiEnventListener() {
        // TODO Auto-generated method stub
        return eventLst;
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            if (IEventType.ACTIONBAR_ITEM_UP_CLICK == eventType) {
                // ???????????????
                upWorkOrder();
            } else if (IEventType.DOWN_WORK_LIST_LOAD == eventType) {
                if (WorkOrderActivity.this != null) {
                    WorkOrderActivity.this.finish();
                }

            } else if (IEventType.DOWN_WORK_LIST_SHOW == eventType) {
                // finish();
            } else if (IEventType.QTJCPAUSE == eventType) {
                // ??????????????????
                qtjcPause();
            } else if (IEventType.QTJCSTOP == eventType) {
                qtjcStop();
                // ??????????????????
            }
        }
    };

    /**
     * upzyp:TODO(???????????????).
     */
    private UpZYPProgressDialog upzyp = null;

    /**
     * ???????????????
     */
    public void upWorkOrder() {
        if (upzyp == null) {
            upzyp = new UpZYPProgressDialog(WorkOrderActivity.this);
            upzyp.setGetDataSourceListener(eventLst);
        }
        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // ?????????ud_zyxk_zysqids
        ud_zyxk_zysqids = new String[1];
        ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
        try {
            List<WorkOrder> data = mQueryWorkOrderInfo
                    .queryWorkOrder(ud_zyxk_zysqids);
            if (null != data && data.size() > 0) {
                upzyp.execute("??????", "???????????????????????????????????????.....", "", data);
            } else {
                ToastUtils.toast(WorkOrderActivity.this, "????????????????????????");
            }
        } catch (HDException e) {
            e.printStackTrace();
        }

    }

    /**
     * ??????????????????
     */
    private void qtjcPause() {
        String[] cols = {"qtjcpauseby", "qtjcpausetime", "isupload",
                "spstatus"};
        mWorkOrder.setQtjcpauseby(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid());
        mWorkOrder.setQtjcpausetime(SystemProperty.getSystemProperty()
                .getSysDateTime());
        mWorkOrder.setIsupload(0);
        mWorkOrder.setSpstatus("REVIEW");
        if (saveInfo("????????????????????????????????????", cols, mWorkOrder)) {
            upWorkOrder();
        }
    }

    /**
     * ??????????????????
     */
    private void qtjcStop() {
        String[] cols = {"qtjcstopby", "qtjcstoptime", "isupload", "spstatus"};
        mWorkOrder.setQtjcstopby(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid());
        mWorkOrder.setQtjcstoptime(SystemProperty.getSystemProperty()
                .getSysDateTime());
        mWorkOrder.setIsupload(0);
        mWorkOrder.setSpstatus("REVIEW");
        if (saveInfo("????????????????????????????????????", cols, mWorkOrder)) {
            upWorkOrder();
        }
    }

    /**
     * ??????????????????????????????
     */
    private boolean saveInfo(String errorInfo, String[] cols,
                             WorkOrder mWorkOrder) {
        boolean isSave = false;
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();

            dao.updateEntity(con, mWorkOrder, cols);

            con.commit();
            isSave = true;
        } catch (SQLException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "?????????????????????");
            isSave = false;
        } catch (DaoException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    errorInfo);
            isSave = false;
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
        return isSave;
    }
}
