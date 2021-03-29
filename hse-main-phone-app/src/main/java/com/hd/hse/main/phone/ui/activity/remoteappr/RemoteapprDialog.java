package com.hd.hse.main.phone.ui.activity.remoteappr;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.main.phone.R;

/**
 * created by yangning on 2017/9/13 15:43.
 */

public class RemoteapprDialog implements View.OnClickListener {
    private AlertDialog dialog;
    private Activity mActivity;
    private AlertDialog.Builder builder;
    private View view;
    private RadioButton rbAgree;
    private RadioButton rbDisagree;
    private EditText edReason;
    private Button btCancle;
    private Button btEnsure;
    private IEventListener iEventListener;

    public RemoteapprDialog(Activity activity, IEventListener iEventListener) {
        this.iEventListener = iEventListener;
        this.mActivity = activity;
        builder = new AlertDialog.Builder(activity);
        view = LayoutInflater.from(activity).inflate(R.layout.hd_hse_common_module_phone_remote_appr_dialog_layout, null);
        rbAgree = (RadioButton) view.findViewById(R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_agree);
        rbDisagree = (RadioButton) view.findViewById(R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_disagree);
        edReason = (EditText) view.findViewById(R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_reason);
        btEnsure = (Button) view.findViewById(R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_ensure);
        btCancle = (Button) view.findViewById(R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_cancle);
        /*rbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    edReason.setText("");
                    edReason.setFocusable(false);
                    //将弹出的键盘收起
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                } else {
                    edReason.setFocusable(true);
                    edReason.setFocusableInTouchMode(true);
                }
            }
        });*/
        rbAgree.setChecked(true);
        btEnsure.setOnClickListener(this);
        btCancle.setOnClickListener(this);
        builder.setView(view);

    }

    public void show() {
        if (dialog == null)
            dialog = builder.show();

        if (dialog != null && !dialog.isShowing())
            dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_cancle) {
            dismiss();
        } else if (id == R.id.hd_hse_common_module_phone_remote_appr_dialog_layout_ensure) {

            if (iEventListener != null) {
                boolean isAgree = rbAgree.isChecked() ? true : false;
                String reason = edReason.getText().toString().trim();
                try {
                    if (!isAgree && (reason == null || reason.equals(""))) {
                        ToastUtils.toast(mActivity, "您还未填写不同意的原因");
                        return;
                    } else
                        iEventListener.eventProcess(IEventType.REMOTEAPPRDIALOG, isAgree, reason);
                } catch (HDException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
        }
    }
}
