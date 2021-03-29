/**
 * 关闭取消界面的公共基类。里面包含了两者公共界面。
 * zhulei
 */
package com.hd.hse.cc.phone.ui.activity.worktask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hd.hse.cc.phone.R;
import com.hd.hse.common.component.phone.custom.ShowSignDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.entity.workorder.WorkApprovalPermission;

import org.apache.log4j.Logger;

import java.util.List;

public class BaseCloseOrCancelActivity extends NaviFrameActivity {
    private static Logger logger = LogUtils
            .getLogger(BaseCloseOrCancelActivity.class);
    private IEventListener iEventLsn;
    public TextView rsnTV;
    public EditText rsnET;
    public TextView rsnBtn;
    public EditText closeExpET;
    public TableRow closeExpTR;
    public ExamineListView examineListView;
    public Button saveBt;
    private List<WorkApprovalPermission> datas;
    public Spinner rsnSP;
    public TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        // TODO Auto-generated method stub
    }

    /**
     * 初始化界面
     */
    protected void initView() {
        setContentView(R.layout.hd_hse_common_layout_workordertype);

        tableLayout = (TableLayout) findViewById(R.id.hd_hse_common_workclose_tablelayout);
        rsnTV = (TextView) findViewById(R.id.hd_hse_common_workordertype_reason_tv);
        rsnET = (EditText) findViewById(R.id.hd_hse_common_workordertype_reason_et);
        rsnBtn = (TextView) findViewById(R.id.hd_hse_common_workordertype_more_b);
        closeExpET = (EditText) findViewById(R.id.hd_hse_common_workordertype_explain);
        closeExpTR = (TableRow) findViewById(R.id.hd_hse_common_explain_tr);
        saveBt = (Button) findViewById(R.id.hd_hse_common_workorder_bt_save);

        View mainView = findViewById(R.id.hd_hse_common_workorder_ll);
        examineListView = (ExamineListView) findViewById(R.id.hd_hse_common_workordertype_examinelistview);
        examineListView.setItemEditTextEms(10);
        examineListView.setHandler(mHandler);
        examineListView
                .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0,
                                                   View arg1, int arg2, long arg3) {
                        if (datas != null && datas.size() != 0) {
                            WorkApprovalPermission wap = datas.get(arg2);
                            ShowSignDialog dialog = new ShowSignDialog(
                                    BaseCloseOrCancelActivity.this, mHandler,
                                    wap);
                            dialog.show();
                        }
                        return false;
                    }
                });

        rsnSP = (Spinner) findViewById(R.id.hd_hd_hse_common_workordertype_reason_sp);
        rsnBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    iEventLsn.eventProcess(IEventType.CLOSEORCANCEL_CLICK,
                            rsnET);
                } catch (HDException e) {
                    logger.error(e.getMessage());
                }
            }
        });
        mainView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                hideInputKeyboard(view);
            }
        });
    }

    /**
     * 设置审批环节数据源
     */
    protected void setData(List<WorkApprovalPermission> datas) {
        this.datas = datas;
        examineListView.setData(datas);
    }

    /**
     * 添加监听
     *
     * @param iEventLsn
     */
    public void setiEventLsn(IEventListener iEventLsn) {
        this.iEventLsn = iEventLsn;
        examineListView.setIEventListener(iEventLsn);
    }

    @Override
    protected void onDestroy() {
        // 销毁广播
        if (examineListView != null) {
            examineListView.destroyBroadcast();
        }
        super.onDestroy();
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ShowSignDialog.HANDLER_WHAT:
                    examineListView
                            .setCurrentEntity((WorkApprovalPermission) msg.obj);
                    break;

                default:
                    break;
            }
        }

    };

}
