package com.hd.hse.osc.phone.ui.activity.remoteappr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dc.business.web.remoteappr.UpWaitRemoteApprData;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.RemoteAppr;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.adapter.remoteappr.RemoteApprAdapter;
import com.hd.hse.osc.phone.ui.adapter.remoteappr.RemoteApprAdapter.OnClickAddListener;

import java.util.ArrayList;

public class RemoteApprActivity extends BaseFrameActivity implements
        OnClickAddListener, OnClickListener {
    public static final String REMOTEAPPRS = "remoteApprs";
    public static final String POSITION = "position";
    private ListView lv;
    private ArrayList<RemoteAppr> remoteApprs = null;
    private RemoteApprAdapter remoteApprAdapter;
    private Button btCancle;
    private Button btEnsure;
    public static int RESULTCODE = 0x416;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_module_phone_remote_appr_layout);
        initView();
        initData();
    }

    private void initData() {
        setCustomActionBar(false, eventLst, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        setActionBartitleContent("远程审批", false);

        remoteApprs = (ArrayList<RemoteAppr>) getIntent().getSerializableExtra(
                REMOTEAPPRS);
        if (remoteApprs != null) {
            remoteApprAdapter = new RemoteApprAdapter(this, remoteApprs, this);
            lv.setAdapter(remoteApprAdapter);
        } else {
            ToastUtils.toast(getApplicationContext(), "获取待远程审批环节点失败");
        }

    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1) {
            if (eventType == IEventType.ACTIONBAR_RETURN_CLICK) {
                finish();
            }
        }
    };

    private void initView() {
        lv = (ListView) findViewById(R.id.hd_hse_common_module_phone_remote_appr_layout_lv);
        btCancle = (Button) findViewById(R.id.hd_hse_common_module_phone_remote_appr_layout_bt_cancle);
        btEnsure = (Button) findViewById(R.id.hd_hse_common_module_phone_remote_appr_layout_bt_ensure);
        btCancle.setOnClickListener(this);
        btEnsure.setOnClickListener(this);
    }

    @Override
    public void OnClickAdd(ArrayList<RemoteAppr> remoteApprs, int position) {
        Intent intent = new Intent(this, SelectPersonActivity.class);
        intent.putExtra(REMOTEAPPRS, remoteApprs);
        intent.putExtra(POSITION, position);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SelectPersonActivity.RESULTCODE) {
            remoteApprs = (ArrayList<RemoteAppr>) data
                    .getSerializableExtra(REMOTEAPPRS);
            if (remoteApprs != null) {
                remoteApprAdapter.setDataList(remoteApprs);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hd_hse_common_module_phone_remote_appr_layout_bt_cancle) {
            // 取消
            finish();
        } else if (v.getId() == R.id.hd_hse_common_module_phone_remote_appr_layout_bt_ensure) {
            // 确定
            clickEnsure();
        }

    }

    private long delayTime = 500;
    private long oldTime;

    private void clickEnsure() {
        //防止过快点击
        long currentTime = ServerDateManager.getCurrentTimeMillis();
        if (currentTime - oldTime < delayTime)
            return;
        oldTime = currentTime;
        for (RemoteAppr appr : remoteApprs) {
            if (appr.getIsmust() != null && appr.getIsmust() == 1 && (appr.getApprove_personid() == null
                    || appr.getApprove_personid().equals(""))) {
                ToastUtils.toast(getApplicationContext(), appr.getSpnode_desc()
                        + "还未选择远程审批人");
                return;
            }
        }
        final ProgressDialog dialog = new ProgressDialog(
                RemoteApprActivity.this, "正在上传远程审批信息...");
        dialog.setCanceledOnTouchOutside(false);
        UpWaitRemoteApprData upRemoteApprInfo = new UpWaitRemoteApprData(
                remoteApprs);
        BusinessAction action = new BusinessAction(upRemoteApprInfo);
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
                        if (RemoteApprActivity.this != null &&dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        ToastUtils.toast(RemoteApprActivity.this, "上传远程审批信息失败");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        if (RemoteApprActivity.this != null) {
                            ToastUtils.toast(RemoteApprActivity.this, "上传远程审批信息成功");
                            setResult(IEventType.REMOTEAPPR);
                            finish();
                        }
                    }
                });
        try {
            task.execute("");
            if (RemoteApprActivity.this != null && dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (HDException e) {
            if (RemoteApprActivity.this != null &&dialog != null && dialog.isShowing()) {
                dialog.cancel();
            }
            ToastUtils.toast(RemoteApprActivity.this, "上传远程审批信息失败");
        }

    }
}
