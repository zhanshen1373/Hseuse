package com.hd.hse.common.component.phone.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.adapter.DeleteSignAdapter;
import com.hd.hse.common.component.phone.listener.OnDeleteListener;
import com.hd.hse.common.component.phone.signbusiness.SignManager;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.workorder.WorkApprovalPermission;

import org.apache.log4j.Logger;

/**
 * created by yangning on 2018/11/13 10:44.
 * 删除签名的
 */
public class DeleteSignDialog extends Dialog {
    private ListView lv;
    private String[] personNames;
    private String[] personIds;
    private String[] defaultPersonNames;
    private String[] defaultPersonIds;
    private WorkApprovalPermission wap;
    private Context context;
    private DeleteSignAdapter adapter;
    private static final Logger loggle = LogUtils
            .getLogger(DeleteSignDialog.class);

    public DeleteSignDialog(@NonNull Context context, WorkApprovalPermission wap) {
        super(context, R.style.PaintSignatureDialog);
        this.context = context;
        setWorkApprovalPermission(wap);

    }

    public void setWorkApprovalPermission(WorkApprovalPermission wap) {
        this.wap = wap;
        personNames = null;
        personIds = null;
        if (wap != null && !TextUtils.isEmpty(wap.getPersondesc()) && !TextUtils.isEmpty(wap.getPersonid())) {
            personNames = wap.getPersondesc().split(",");
            personIds = wap.getPersonid().split(",");
            defaultPersonNames = wap.getDefaultpersondesc().split(",");
            defaultPersonIds = wap.getDefaultpersonid().split(",");
        } else {
            loggle.error("删除审批环节点时，wap为null或者wap.getPersondesc()isEmpty或者wap.getPersonid()isEmpty");
        }
    }

    @Override
    public void show() {
        if (personNames != null && personIds != null
                && defaultPersonNames != null && defaultPersonIds != null
                && personNames.length == personIds.length
                && personNames.length == defaultPersonNames.length
                && personNames.length == defaultPersonIds.length) {
            if (adapter != null) {
                adapter.setData(personNames);
            }
            super.show();
        } else {
            ToastUtils.toast(context, "获取环节点信息出错");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_component_dialog_delete_signature);
        lv = (ListView) findViewById(R.id.list_delete);
        adapter = new DeleteSignAdapter(context, personNames);
        adapter.setListener(new MyOnDeleteListener());
        lv.setAdapter(adapter);
    }

    class MyOnDeleteListener implements OnDeleteListener {
        @Override
        public void onDelete(int position) {
            if (SignManager.dispatchSign(wap, position)) {
                //成功，
                if (TextUtils.isEmpty(wap.getPersondesc())) {
                    dismiss();
                } else {
                    if (adapter != null) {
                        setWorkApprovalPermission(wap);
                        adapter.setData(personNames);

                    }

                }
            } else {
                ToastUtils.toast(context, "签名删除失败");
            }
        }
    }

}
