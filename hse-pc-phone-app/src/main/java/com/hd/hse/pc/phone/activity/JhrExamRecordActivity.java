package com.hd.hse.pc.phone.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dc.business.web.cbs.GainJhrScores;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.rycheck.JhrData;
import com.hd.hse.entity.rycheck.JhrExamRecord;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.JhrExamRecordAdapter;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 监护人考试成绩
 * created by yangning on 2019/3/25 10:11.
 */
public class JhrExamRecordActivity extends BaseFrameActivity implements IEventListener {
    private static Logger logger = LogUtils
            .getLogger(JhrExamRecordActivity.class);

    private PersonCard mCard;
    private JhrExamRecordAdapter adapter;
    private ListView listView;
    private List<JhrExamRecord> jhrExamRecords;


    private BusinessAsyncTask asyncTask;
    private BusinessAction actionRules;
    private GainJhrScores scores;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jhr_exam_record_layout);
        initActionbar();
        initView();
        initData();
    }

    private void initData() {
        /*//假数据
        String data = "{\"DATA\":[{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"临电\"},{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"吊装\"},{\"RESULT\":\"不合格\",\"SCORE\":\"77\",\"JOBTYPE\":\"脚手架\"},{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"动火\"},{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"高处\"},{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"受限\"},{\"RESULT\":\"合格\",\"SCORE\":\"81\",\"JOBTYPE\":\"挖掘\"},{\"RESULT\":\"不合格\",\"SCORE\":\"78\",\"JOBTYPE\":\"管线设备\"},{\"RESULT\":\"不合格\",\"SCORE\":\"79\",\"JOBTYPE\":\"射线探伤\"}]}\n" +
                "\n";
        Gson gson = new Gson();
        JhrData jhrData = gson.fromJson(data, JhrData.class);*/


        mCard = (PersonCard) getIntent().getSerializableExtra(
                PersonCard.class.getName());

        dialog = new ProgressDialog(JhrExamRecordActivity.this, true, "正在加载监护人考试成绩...");

        getNetData(mCard.getPersonid());
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, setActionBarItems());
        // 设置导航栏标题
        setActionBartitleContent("监护人考试成绩", false);
    }

    private String[] setActionBarItems() {
        return new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE};
    }

    @Override
    public void eventProcess(int eventType, Object... objects) throws HDException {

    }


    /**
     * 获取监护人考试成绩
     */
    private void getNetData(String personid) {
        // 加载动画，得到数据后停止
        scores = new GainJhrScores(personid);

        actionRules = new BusinessAction(scores);

        asyncTask = new BusinessAsyncTask(actionRules,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        try {
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                        } catch (Exception e) {
                            logger.error(e);
                        }
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        ToastUtils.imgToast(
                                getApplicationContext(),
                                R.drawable.hd_hse_common_msg_wrong,
                                msg.getMessage().contains("获取超时") ? msg
                                        .getMessage() : "获取监护人考试成绩数据失败");

                    }

                    @Override
                    public void end(Bundle msgData) {
                        // TODO Auto-generated method stub
                        try {
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                        } catch (Exception e) {
                            logger.error(e);
                        }
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        JhrData jhrData = (JhrData) msg.getReturnResult();
                        if (jhrData != null && jhrData.getDATA() != null && jhrData.getDATA().size() > 0) {
                            jhrExamRecords = jhrData.getDATA();
                            adapter = new JhrExamRecordAdapter(jhrExamRecords, JhrExamRecordActivity.this);
                            listView.setAdapter(adapter);
                        }

                    }
                });
        try {

            asyncTask.execute(null, "15");
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (HDException e) {
            ToastUtils.imgToast(getApplicationContext(), R.drawable.hd_hse_common_msg_wrong,
                    "获取监护人考试成绩数据失败");
            try {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            } catch (Exception e1) {
                logger.error(e1);
            }
        }

    }
}
