package com.hd.hse.osc.phone.ui.fragment.environment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dubojian on 2018年8月03日
 */
public class EnvironmentHarmJudgeFragment extends NaviFrameFragment {

    private static Logger logger = LogUtils
            .getLogger(EnvironmentHarmJudgeFragment.class);
    private WorkOrder mWorkOrder;
    private PDAWorkOrderInfoConfig pdaConfig;


    private View contentView;


    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    private EditText gyjz_jcq;
    private EditText temperature_jcq;
    private EditText pressure_jcq;
    private EditText gyjz_jch;
    private EditText temperature_jch;
    private EditText pressure_jch;
    private TextView saveTV;
    //有毒可燃介质
    private EditText conditionensure_ydkrjz;
    //工艺介质
    private EditText conditionensure_gyjz;
    //周围危险物
    private EditText conditionensure_zwwxw;
    //温度℃
    private EditText conditionensure_wd;
    //消防通道
    private EditText conditionensure_xftd;
    //压力MPa
    private EditText conditionensure_yl;


    private BaseDao dao;
    private BusinessAsyncTask asyTask;
    private BusinessAction busAction;
    public ProgressDialog mDialog = null;


    @Override
    protected void init() {

        busAction = new BusinessAction(new CheckControllerActionListener());
        asyTask = new BusinessAsyncTask(busAction, appCallBack);

    }

    /**
     * auditCallBack:TODO(审批环节的回调).
     */
    private AbstractAsyncCallBack appCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub
        }

        @Override
        public void error(Bundle msgData) {
            // TODO Auto-generated method stub

            DismissDialog();
            ToastUtils.imgToast(getActivity(),
                    com.hd.hse.common.module.phone.R.drawable.hd_hse_common_msg_wrong,
                    msgData.getString(IActionType.ACTION_TYPE_ENVIRONMENTHARMJUDGE));

        }

        @Override
        public void end(Bundle msgData) {
            // TODO Auto-generated method stub
            DismissDialog();
            ToastUtils.toast(mActivity, "保存成功");

            PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
                    .getNaviCurrentEntity();
            if (null != naviCurrentEntity) {
                naviCurrentEntity.setIsComplete(1);
                setNaviCurrentEntity(naviCurrentEntity);
            }

        }
    };


    @SuppressWarnings("unchecked")
    @Override
    public void initData(List<Object> data) {
        if (data != null) {
            if (data.get(0) instanceof WorkOrder) {
                mWorkOrder = (WorkOrder) data.get(0);
            }
            if (data.get(1) instanceof PDAWorkOrderInfoConfig) {
                pdaConfig = (PDAWorkOrderInfoConfig) data.get(1);
            }
//            if (data.get(2) instanceof List) {
//                pdaConfigList = (List<PDAWorkOrderInfoConfig>) data.get(2);
//            }
        }
    }

    private void showData() {

        if (mWorkOrder != null && mWorkOrder.getZypclass().equals(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
            gyjz_jcq.setText(mWorkOrder.getJcqgyjz());
            temperature_jcq.setText(mWorkOrder.getJcqwd());
            pressure_jcq.setText(mWorkOrder.getJcqyl());
            gyjz_jch.setText(mWorkOrder.getJchgyjz());
            temperature_jch.setText(mWorkOrder.getJchwd());
            pressure_jch.setText(mWorkOrder.getJchyl());
        } else {


            conditionensure_ydkrjz.setText(mWorkOrder.getYdkrjz());
            conditionensure_gyjz.setText(mWorkOrder.getJchgyjz());
            conditionensure_zwwxw.setText(mWorkOrder.getZwwxw());
            conditionensure_wd.setText(mWorkOrder.getJchwd());
            conditionensure_xftd.setText(mWorkOrder.getXftd());
            conditionensure_yl.setText(mWorkOrder.getJchyl());

        }

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup arg1) {


        if (contentView == null) {

            if (mWorkOrder.getZypclass().equals(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
                contentView = inflater.inflate(
                        R.layout.hd_hse_common_module_phone_frag_environmentharm_judge, null);
            } else {
                contentView = inflater.inflate(
                        R.layout.hd_hse_common_module_phone_frag_conditionensure, null);
            }

            getActivity().getWindowManager().getDefaultDisplay()
                    .getMetrics(mDisplayMetrics);
            findViewById(contentView);


        }
        showData();

        return contentView;
    }

    private void findViewById(View contentView) {

        if (mWorkOrder.getZypclass().equals(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
            gyjz_jcq = (EditText) contentView.findViewById(R.id.environmentharm_gyjz_jcq);
            temperature_jcq = (EditText) contentView.findViewById(R.id.environmentharm_temperature_jcq);
            pressure_jcq = (EditText) contentView.findViewById(R.id.environmentharm_pressure_jcq);
            gyjz_jch = (EditText) contentView.findViewById(R.id.environmentharm_gyjz_jch);
            temperature_jch = (EditText) contentView.findViewById(R.id.environmentharm_temperature_jch);
            pressure_jch = (EditText) contentView.findViewById(R.id.environmentharm_pressure_jch);

            saveTV = (TextView) contentView.findViewById(R.id.environmentharm_save_btn);
            saveTV.setText("保  存");
            saveTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao = new BaseDao();
                    String sql = "update ud_zyxk_zysq set jcqgyjz ='" + gyjz_jcq.getText().toString() + "'," +
                            "jcqwd='" + temperature_jcq.getText().toString() + "',jcqyl='" + pressure_jcq.getText().toString() + "'," +
                            "jchgyjz='" + gyjz_jch.getText().toString() + "',jchwd='" + temperature_jch.getText().toString() + "'," +
                            "jchyl='" + pressure_jch.getText().toString() + "' where ud_zyxk_zysqid='" + mWorkOrder.getUd_zyxk_zysqid() + "'";

                    IConnectionSource conSrc = null;
                    IConnection connection = null;
                    try {
                        conSrc = ConnectionSourceManager.getInstance()
                                .getJdbcPoolConSource();
                        connection = conSrc.getConnection();
                        dao.executeUpdate(connection, sql);
                        connection.commit();
                    } catch (SQLException e) {

                    } catch (DaoException e) {

                    } finally {
                        if (connection != null) {
                            try {
                                conSrc.releaseConnection(connection);
                            } catch (SQLException e) {

                            }
                        }
                    }
                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    // 1.作业票
                    mapParam.put(WorkOrder.class.getName(), mWorkOrder);
                    // 2.
                    mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                            pdaConfig);

                    // 异步保存数据
                    ShowDialogMessage("保存信息。。。");
                    try {
                        asyTask.execute(IActionType.ACTION_TYPE_ENVIRONMENTHARMJUDGE, mapParam);
                    } catch (HDException e) {
                        logger.error(e);
                        ToastUtils.toast(getActivity(), "保存数据失败!请联系管理员.");
                    }

                }
            });


        } else {


            conditionensure_ydkrjz = (EditText) contentView.findViewById(R.id.conditionensure_ydkrjz);
            conditionensure_gyjz = (EditText) contentView.findViewById(R.id.conditionensure_gyjz);
            conditionensure_zwwxw = (EditText) contentView.findViewById(R.id.conditionensure_zwwxw);
            conditionensure_wd = (EditText) contentView.findViewById(R.id.conditionensure_wd);
            conditionensure_xftd = (EditText) contentView.findViewById(R.id.conditionensure_xftd);
            conditionensure_yl = (EditText) contentView.findViewById(R.id.conditionensure_yl);

            saveTV = (TextView) contentView.findViewById(R.id.environmentharm_save_btn);
            saveTV.setText("保  存");
            saveTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dao = new BaseDao();
                    String sql = "update ud_zyxk_zysq set ydkrjz ='" + conditionensure_ydkrjz.getText().toString() + "'," +
                            "jchgyjz='" + conditionensure_gyjz.getText().toString() + "',zwwxw='" + conditionensure_zwwxw.getText().toString() + "'," +
                            "jchwd='" + conditionensure_wd.getText().toString() + "',xftd='" + conditionensure_xftd.getText().toString() + "'," +
                            "jchyl='" + conditionensure_yl.getText().toString() + "' where ud_zyxk_zysqid='" + mWorkOrder.getUd_zyxk_zysqid() + "'";

                    IConnectionSource conSrc = null;
                    IConnection connection = null;
                    try {
                        conSrc = ConnectionSourceManager.getInstance()
                                .getJdbcPoolConSource();
                        connection = conSrc.getConnection();
                        dao.executeUpdate(connection, sql);
                        connection.commit();
                    } catch (SQLException e) {

                    } catch (DaoException e) {

                    } finally {
                        if (connection != null) {
                            try {
                                conSrc.releaseConnection(connection);
                            } catch (SQLException e) {

                            }
                        }
                    }
                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    // 1.作业票
                    mapParam.put(WorkOrder.class.getName(), mWorkOrder);
                    // 2.
                    mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                            pdaConfig);

                    // 异步保存数据
                    ShowDialogMessage("保存信息。。。");
                    try {
                        asyTask.execute(IActionType.ACTION_TYPE_CONDITIONENSURE, mapParam);
                    } catch (HDException e) {
                        logger.error(e);
                        ToastUtils.toast(getActivity(), "保存数据失败!请联系管理员.");
                    }

                }
            });


        }


    }


    /**
     * ShowDialogMessage:(显示dialog). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param msg 显示内容
     * @author lxf
     */
    public void ShowDialogMessage(String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity(), msg);
            mDialog.show();
        } else {
            mDialog.showMsg(msg);
            mDialog.show();
        }
    }

    /**
     * DismissDialog:(是否dialog). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @author lxf
     */
    public void DismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }


    @Override
    public void refreshData() {

    }


}
