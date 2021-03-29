package com.hd.hse.main.phone.ui.activity.remoteappr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.RemoteApprInfo;
import com.hd.hse.entity.workorder.RemoteApprLine;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.main.business.remoteappr.CheckRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.DisagreeRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness.UpRemoteApprInfoListener;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.adapter.remoteappr.RemoteApprInfoBrowseAdapter;
import com.hd.hse.system.SystemProperty;

import java.util.ArrayList;
import java.util.List;

public class RemoteApprInfoBrowse extends BaseFrameActivity implements
        OnClickListener {
    private LinearLayout liAll;
    private TextView tvName;
    private TextView tvAppr;
    private GridView gv;
    private RemoteApprInfoBrowseAdapter adapter;
    private List<RemoteApprLine> apprLines;
    private TextView tvSeleOrCancle;
    /**
     * 记录点击跳转下一页的点击位置
     */
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_module_phone_remote_appr_brower_layout);
        initView();
        initData();
    }

    private void initData() {
        RemoteApprInfo remoteApprInfo = (RemoteApprInfo) getIntent()
                .getSerializableExtra(RemoteApprInfoBrowse.class.getName());
        if (remoteApprInfo == null) {
            ToastUtils.toast(getApplicationContext(), "获取传入的数据出错");
            return;
        }
        tvName.setText(remoteApprInfo.getSpnode_desc());
        apprLines = getApprLines(remoteApprInfo);
        adapter = new RemoteApprInfoBrowseAdapter(this, apprLines);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int state = adapter.getState();
                if (state == adapter.normalState) {
                    // 正常状态直接跳转
                    position = arg2;
                    goToNextActivity(apprLines.get(arg2));
                } else if (state == adapter.selectState
                        && apprLines.get(arg2).getIssp() != null
                        && !apprLines.get(arg2).getIssp().equals("1")) {
                    adapter.setCheckBoxChecked(arg2,
                            !adapter.getCheckBoxChecked(arg2));
                }

            }

        });

    }

    private List<RemoteApprLine> getApprLines(RemoteApprInfo remoteApprInfo) {
        List<RemoteApprLine> remoteApprLines;
        remoteApprLines = remoteApprInfo.getUD_ZYXK_REMOTEAPPROVE_LINE();
        if (remoteApprInfo.getZysqidall() != null &&
                remoteApprInfo.getZyptypeall_desc() != null &&
                remoteApprInfo.getZyptypeall() != null &&
                remoteApprInfo.getStatusall() != null) {
            String[] zysqidall = remoteApprInfo.getZysqidall().split(",");
            String[] zyptypeall_desc = remoteApprInfo.getZyptypeall_desc().split(",");
            String[] zyptypeall = remoteApprInfo.getZyptypeall().split(",");
            String[] statusall = remoteApprInfo.getStatusall().split(",");
            String[] zylevelall = remoteApprInfo.getZylevelall().split(",", -1);
            if (zysqidall.length == zyptypeall_desc.length
                    && zysqidall.length == zyptypeall.length
                    && zyptypeall.length == statusall.length
                    && zyptypeall.length == zylevelall.length) {
                for (int i = 0; i < zysqidall.length; i++) {
                    boolean tag = false;
                    for (RemoteApprLine apprLine : remoteApprLines) {
                        if (apprLine.getZysqid().equals(zysqidall[i])) {
                            tag = true;
                            apprLine.setZylevel(zylevelall[i]);
                            break;
                        }
                    }
                    if (!tag) {
                        RemoteApprLine apprLine = new RemoteApprLine();
                        apprLine.setIssp("1");
                        apprLine.setZysqid(zysqidall[i]);
                        apprLine.setZypclass(zyptypeall[i]);
                        apprLine.setZypclass_desc(zyptypeall_desc[i]);
                        apprLine.setStatus(statusall[i]);
                        apprLine.setZylevel(zylevelall[i]);
                        remoteApprLines.add(apprLine);
                    }

                }
            }

        }


        return remoteApprLines;
    }

    /**
     * 跳转到作业票报表界面
     */
    private void goToNextActivity(RemoteApprLine apprLine) {
        Intent intent = new Intent(RemoteApprInfoBrowse.this,
                RemoteApprWorkOrderDetailActivity.class);
        WorkOrder workorder = new WorkOrder();
        workorder.setZypclassname(apprLine.getZypclass_desc());
        workorder.setZypclass(apprLine.getZypclass());
        workorder.setZylevel(apprLine.getZylevel());
        workorder.setUd_zyxk_zysqid(apprLine.getZysqid());
        workorder.setZylevel(apprLine.getZylevel());
        Bundle bundle = new Bundle();
        bundle.putSerializable(RemoteApprInfoBrowse.class.getName(), apprLine);
        bundle.putSerializable(WorkOrderDetailActivity.WORK_ORDER, workorder);
        intent.putExtras(bundle);
        startActivityForResult(intent, WorkOrderDetailActivity.REQUESTCODE);
    }

    private void initView() {
        setCustomActionBar(false, eventLst, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        setActionBartitleContent("远程审批", false);
        liAll = (LinearLayout) findViewById(R.id.hd_hse_common_module_phone_remote_appr_brower_layout_all);
        tvName = (TextView) findViewById(R.id.hd_hse_common_module_phone_remote_appr_brower_layout_title_tv);
        tvAppr = (TextView) findViewById(R.id.hd_hse_common_module_phone_remote_appr_brower_layout_tv_appr);
        gv = (GridView) findViewById(R.id.hd_hse_common_module_phone_remote_appr_brower_layout_gv);
        tvSeleOrCancle = (TextView) findViewById(R.id.hd_hse_common_module_phone_remote_appr_brower_layout_tv_select_cancle);
        tvAppr.setOnClickListener(this);
        tvSeleOrCancle.setOnClickListener(this);
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1) {
            if (eventType == IEventType.ACTIONBAR_RETURN_CLICK) {
                finish();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hd_hse_common_module_phone_remote_appr_brower_layout_tv_appr) {
            // 点击审批
            List<RemoteApprLine> apprLines = getRemoteApprData();
            if (apprLines == null || apprLines.size() == 0) {
                ToastUtils.toast(getApplicationContext(), "你还未勾选远程审批的作业票");
                return;
            }
            clickAppr(apprLines);
        } else if (v.getId() == R.id.hd_hse_common_module_phone_remote_appr_brower_layout_tv_select_cancle) {
            if (adapter.getState() == adapter.normalState) {
                // 点击选择
                clickSelect();
            } else if (adapter.getState() == adapter.selectState) {
                // 点击取消
                clickCancle();
            }

        }

    }

    private void clickSelect() {
        tvSeleOrCancle.setText("取消");
        adapter.setState(adapter.selectState);
        tvAppr.setVisibility(View.VISIBLE);
    }

    private void clickCancle() {
        tvSeleOrCancle.setText("选择");
        adapter.setState(adapter.normalState);
        tvAppr.setVisibility(View.INVISIBLE);
    }

    private List<RemoteApprLine> getRemoteApprData() {
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        List<RemoteApprLine> upData = new ArrayList<RemoteApprLine>();
        for (int i = 0; i < apprLines.size(); i++) {
            if (adapter.getCheckBoxChecked(i)) {
                apprLines.get(i).setApprove_person_name(
                        personCard.getPersonid_desc());
                apprLines.get(i).setApprove_personid(personCard.getPersonid());
                apprLines.get(i).setSPTIME(
                        SystemProperty.getSystemProperty().getSysDateTime());
                upData.add(apprLines.get(i));
            }
        }
        return upData;
    }

    private void clickAppr(final List<RemoteApprLine> upData) {
        CheckRemoteApprBusiness checkRemoteApprBusiness = new CheckRemoteApprBusiness(RemoteApprInfoBrowse.this);
        checkRemoteApprBusiness.checkRemoteAppr(upData);
        checkRemoteApprBusiness.setOnCheckRemoteApprListener(new CheckRemoteApprBusiness.OnCheckRemoteApprListener() {
            @Override
            public void onCheckFail(String failReason) {
                ToastUtils.toast(getApplicationContext(), failReason);
            }

            @Override
            public void onCheckError() {
                ToastUtils.toast(getApplicationContext(), "校验远程审批记录失败");
            }

            @Override
            public void onCheckSuccess() {
                RemoteapprDialog dialog = new RemoteapprDialog(RemoteApprInfoBrowse.this, new IEventListener() {
                    @Override
                    public void eventProcess(int eventType, Object... objects) throws HDException {
                        if (eventType == IEventType.REMOTEAPPRDIALOG) {
                            if (objects[0] != null && (boolean) objects[0]) {
                                //同意
                                agreeRemoteAppr(upData, objects[1].toString());
                            } else if (objects[0] != null && !(boolean) objects[0]) {
                                //不同意
                                if (objects[1] != null)
                                    disagreeRemoteAppr(upData, objects[1].toString());
                                else
                                    ToastUtils.toast(getApplicationContext(), "您未填写不同意原因");
                            } else {
                                ToastUtils.toast(getApplicationContext(), "程序出错");
                            }
                        }
                    }
                });
                dialog.show();
            }
        });


    }

    private void agreeRemoteAppr(final List<RemoteApprLine> upData, String agreeReason) {
        for (RemoteApprLine apprLine : upData) {
            apprLine.setDisagree_reason(agreeReason);
        }
        RemoteApprBusiness remoteApprBusiness = new RemoteApprBusiness(
                RemoteApprInfoBrowse.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录成功");
                        // 修改票的状态，由远变为审
                        for (RemoteApprLine apprLine : upData) {
                            apprLine.setIssp("1");
                        }
                        clickCancle();
                    }

                    @Override
                    public void UpRemoteApprInfoError() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录失败");
                    }
                });
        remoteApprBusiness.upRemoteAppr(upData);
    }

    private void disagreeRemoteAppr(final List<RemoteApprLine> upData, String disagreeReason) {
        for (RemoteApprLine apprLine : upData) {
            apprLine.setDisagree_reason(disagreeReason);
        }
        DisagreeRemoteApprBusiness remoteApprBusiness = new DisagreeRemoteApprBusiness(
                RemoteApprInfoBrowse.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录成功");
                        RemoteApprDisagreeBusiness remoteApprDisagreeBusiness = new RemoteApprDisagreeBusiness(upData, RemoteApprInfoBrowse.this);
                        remoteApprDisagreeBusiness.handleZyp();
                        finish();
                    }

                    @Override
                    public void UpRemoteApprInfoError() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录失败");
                    }
                });
        remoteApprBusiness.upRemoteAppr(upData);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RemoteApprWorkOrderDetailActivity.resultCodeagree) {
            if (position != -1) {
                apprLines.get(position).setIssp("1");
                adapter.notifyDataSetChanged();
            }
        } else if (resultCode == RemoteApprWorkOrderDetailActivity.resultCodeDisagree) {
            finish();
        }
    }
}
