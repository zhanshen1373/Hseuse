package com.hd.hse.main.phone.ui.activity.remoteappr;

import android.content.Intent;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.RemoteApprLine;
import com.hd.hse.main.business.remoteappr.CheckRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.DisagreeRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness.UpRemoteApprInfoListener;
import com.hd.hse.system.SystemProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程审批的作业票详情
 *
 * @author yn
 */
public class RemoteApprWorkOrderDetailActivity extends WorkOrderDetailActivity {
    public static final int resultCodeagree = 0x1456;
    public static final int resultCodeDisagree = 0x145655;
    private RemoteApprLine apprLine;
    private boolean isAppr;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initActionBar() {
        initData();
        super.initActionBar();
        String[] actionBar = new String[]{IActionBar.TV_BACK,
                IActionBar.IBTN_LEVELTWO_MORE, IActionBar.TV_TITLE};
        setCustomActionBar(iEventListener, actionBar);
        setCustomMenuBar(new String[]{IActionBar.REMOTE_APPR});
        setActionBartitleContent(actionBarTitleName, false);

    }

    private void initData() {
        Intent intent = getIntent();
        apprLine = (RemoteApprLine) intent
                .getSerializableExtra(RemoteApprInfoBrowse.class.getName());
        if (apprLine.getIssp() != null && apprLine.getIssp().equals("1"))
            isAppr = true;

    }

    IEventListener iEventListener = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            if (IEventType.REMOTEAPPR == eventType) {
                if (!isAppr) {
                    List<RemoteApprLine> apprLines = getRemoteApprData();
                    if (apprLines == null || apprLines.size() == 0) {
                        ToastUtils.toast(getApplicationContext(), "获取审批数据出错");
                        return;
                    }
                    remoteAppr(apprLines);
                } else {
                    ToastUtils.toast(getApplicationContext(), "已经审批");
                }
            }
        }
    };

    private List<RemoteApprLine> getRemoteApprData() {
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        List<RemoteApprLine> upData = new ArrayList<RemoteApprLine>();
        apprLine.setApprove_person_name(personCard.getPersonid_desc());
        apprLine.setApprove_personid(personCard.getPersonid());
        apprLine.setSPTIME(SystemProperty.getSystemProperty().getSysDateTime());
        upData.add(apprLine);

        return upData;
    }

    private void remoteAppr(final List<RemoteApprLine> upData) {
        CheckRemoteApprBusiness checkRemoteApprBusiness = new CheckRemoteApprBusiness(RemoteApprWorkOrderDetailActivity.this);
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
                RemoteapprDialog dialog = new RemoteapprDialog(RemoteApprWorkOrderDetailActivity.this, new IEventListener() {
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
                RemoteApprWorkOrderDetailActivity.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录成功");
                        // 修改票的状态，由远变为审
                        isAppr = true;
                        setResult(resultCodeagree);
                        if (RemoteApprWorkOrderDetailActivity.this != null) {
                            RemoteApprWorkOrderDetailActivity.this.finish();
                        }
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
                RemoteApprWorkOrderDetailActivity.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "上传远程审批记录成功");
                        setResult(resultCodeDisagree);
                        RemoteApprDisagreeBusiness remoteApprDisagreeBusiness = new RemoteApprDisagreeBusiness(upData, RemoteApprWorkOrderDetailActivity.this);
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


}
