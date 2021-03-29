package com.hd.hse.pc.phone.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ExpandableListView;

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
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.cbs.GetTrainingRecords;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.RyLine;
import com.hd.hse.entity.rycheck.TrainingAchievement;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.TrainingRecordsAdapter;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * 培训记录
 * created by yangning on 2018/1/8 11:07.
 */

public class TrainingRecordsActivity extends BaseFrameActivity implements
        IEventListener {
    private static Logger logger = LogUtils
            .getLogger(TrainingRecordsActivity.class);
    private ExpandableListView lv;
    private TrainingRecordsAdapter adapter;
    private List<TrainingAchievement> data;
    private BusinessAsyncTask asyncTask;
    private BusinessAction actionRules;
    private GetTrainingRecords record;
    private ProgressDialog dialog;
    private String idcard;
    private PersonCard mCard;
    private RyLine ryLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_pc_phone_app_training_records_layout);
        initActionbar();
        initView();
        initData();
    }

    private void initView() {
        lv = (ExpandableListView) findViewById(R.id.hse_pc_phone_app_training_records_layout_lv);
    }


    private void initData() {
        mCard = (PersonCard) getIntent().getSerializableExtra(
                PersonCard.class.getName());
//        ryLine= (RyLine) getIntent().getSerializableExtra(RyLine.class.getName());

        if (mCard.getIscbs()==1){
//            idcard=ryLine.getIdcard();
//            if (TextUtils.isEmpty(idcard)){
//                ToastUtils.toast(getApplicationContext(),"未获取到身份证号");
//                finish();
//                return;
//            }

//            idcard=mCard.getIccard();
            idcard=mCard.getPersonid();

            if (TextUtils.isEmpty(idcard)){
                ToastUtils.toast(getApplicationContext(),"没有培训记录");
                finish();
                return;
            }

        }else{
            idcard=mCard.getIccard();

            if (TextUtils.isEmpty(idcard)){
                ToastUtils.toast(getApplicationContext(),"没有培训记录");
                finish();
                return;
            }
        }

        dialog = new ProgressDialog(TrainingRecordsActivity.this, true, "正在加载培训记录...");
        getNetData(idcard);

       /* //假数据
        data = new ArrayList<>();
        TrainingAchievement achievement = new TrainingAchievement();
        achievement.setFactoryName("化肥厂");
        achievement.setJoinTime("2018/01/08");
        achievement.setScore("88分");
        TwoLevelAchievement twoLevelAchievement = new TwoLevelAchievement();
        twoLevelAchievement.setDeptName("电气车间");
        twoLevelAchievement.setJoinTime("2018/01/08");
        twoLevelAchievement.setScore("88分");
        TwoLevelAchievement twoLevelAchievement2 = new TwoLevelAchievement();
        twoLevelAchievement2.setDeptName("电气车间");
        twoLevelAchievement2.setJoinTime("2018/02/12");
        twoLevelAchievement2.setScore("99分");
        List<TwoLevelAchievement> twoLevelAchievementList = new ArrayList<>();
        twoLevelAchievementList.add(twoLevelAchievement);
        twoLevelAchievementList.add(twoLevelAchievement2);
        achievement.setTwoLevelAchievementList(twoLevelAchievementList);
        try {
            TrainingAchievement achievement2 = (TrainingAchievement) achievement.clone();
            data.add(achievement2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        data.add(achievement);


        adapter = new TrainingRecordsAdapter(data, TrainingRecordsActivity.this);
        lv.setAdapter(adapter);*/
    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        // 设置导航栏标题
        setActionBartitleContent("培训记录", false);
        // 设置右侧菜单栏

    }

    @Override
    public void eventProcess(int eventType, Object... objects) throws HDException {

    }


    /**
     * 得到已上传数据
     */
    private void getNetData(String idcard) {
        // 加载动画，得到数据后停止
        record = new GetTrainingRecords(idcard);

        actionRules = new BusinessAction(record);

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
                                        .getMessage() : "获取已上传数据失败！请联系管理员");

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
                       // mDatas = (List<WorkLogEntry>) msg.getReturnResult();
                        data=(List<TrainingAchievement>)msg.getReturnResult();
                        adapter = new TrainingRecordsAdapter(data, TrainingRecordsActivity.this);
                        lv.setAdapter(adapter);


                    }
                });
        try {

            asyncTask.execute(IActionType.WEB_INOROUTRECORD, "15");
            if (dialog != null) {
                dialog.show();
            }
        } catch (HDException e) {
            ToastUtils.imgToast(getApplicationContext(), R.drawable.hd_hse_common_msg_wrong,
                    "获取已上传数据失败");
            try {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            } catch (Exception e1) {
                logger.error(e1);
            }
        }

    }
}
