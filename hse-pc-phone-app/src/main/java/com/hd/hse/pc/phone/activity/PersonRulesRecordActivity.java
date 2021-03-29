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
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.cbs.PCGetRulesRecords;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.PersonRulesRecordAdapter;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 人员违章记录Activity Created by liuyang on 2016年9月26日
 */
public class PersonRulesRecordActivity extends BaseFrameActivity implements
        IEventListener {
    private static Logger logger = LogUtils
            .getLogger(PersonRulesRecordActivity.class);
    /**
     * listview
     */
    private ListView lv;
    /**
     * 数据
     */
    private ArrayList<WorkLogEntry> mRulesRecords;

    private PersonRulesRecordAdapter mPersonRulesRecordAdapter;

    private ProgressDialog prsDlg;
    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction action;
    private BusinessAsyncTask asyncTask;
    private BusinessAction actionRules;
    private PCGetRulesRecords record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_pc_phone_app_person_rules_record_layout);
        initActionbar();
        initView();
        initData();
    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        // 设置导航栏标题
        setActionBartitleContent("违章记录", false);
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.hse_pc_phone_app_person_rules_record_layout_lv);
        mRulesRecords = new ArrayList<WorkLogEntry>();
        mPersonRulesRecordAdapter = new PersonRulesRecordAdapter(mRulesRecords,
                this);
        lv.setAdapter(mPersonRulesRecordAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        prsDlg = new ProgressDialog(this, "数据努力加载中。。。");
        if (null == record) {
            record = new PCGetRulesRecords();
        }
        if (null == actionRules) {
            actionRules = new BusinessAction(record);
        }
        if (asyncTask == null) {
            asyncTask = new BusinessAsyncTask(actionRules,
                    new AbstractAsyncCallBack() {

                        @Override
                        public void start(Bundle msgData) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void processing(Bundle msgData) {
                            prsDlg.show();
                        }

                        @Override
                        public void error(Bundle msgData) {
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            ToastUtils.imgToast(
                                    PersonRulesRecordActivity.this,
                                    R.drawable.hd_hse_common_msg_wrong,
                                    msg.getMessage().contains("获取超时") ? msg
                                            .getMessage() : "获取违章记录错误！请联系管理员");
                            prsDlg.dismiss();
                        }

                        @Override
                        public void end(Bundle msgData) {
                            // TODO Auto-generated method stub
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            List<WorkLogEntry> listDatas = (List<WorkLogEntry>) msg
                                    .getReturnResult();
                            if (listDatas != null && listDatas.size() > 0) {
                                // 按时间排序
                                listDatas = Sort(listDatas, "getCreatedate",
                                        "desc");
                                mRulesRecords.addAll(listDatas);
                                mPersonRulesRecordAdapter.notifyDataSetChanged();
                            }
                            prsDlg.dismiss();
                        }
                    });
            try {
                String personid = null;
                if (((PersonCard) getIntent()
                        .getSerializableExtra(PersonCard.class.getName())).getIscbs() == 1) {

//                    personid = ((RyLine) getIntent().getSerializableExtra(RyLine.class.getName())).getIdcard();

                    personid = ((PersonCard) getIntent()
                            .getSerializableExtra(PersonCard.class.getName()))
                            .getPersonid();
                } else {
                    personid = ((PersonCard) getIntent()
                            .getSerializableExtra(PersonCard.class.getName()))
                            .getIccard();
                }

                if (personid != null && !"".equals(personid)) {
                    asyncTask.execute(IActionType.WEB_INOROUTRECORD, personid);
                } else {
                    ToastUtils.toast(PersonRulesRecordActivity.this, "没有违章记录");

                    finish();
                    return;
                }
            } catch (HDException e) {
                ToastUtils.imgToast(PersonRulesRecordActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, "加载数据出错，请联系管理员");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<WorkLogEntry> Sort(List<WorkLogEntry> list,
                                   final String method, final String sort) {
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try {
                    Method m1 = ((WorkLogEntry) a).getClass().getMethod(method,
                            new Class[]{});
                    Method m2 = ((WorkLogEntry) b).getClass().getMethod(method,
                            new Class[]{});
                    if (sort != null && "desc".equals(sort))// 倒序
                        ret = m2.invoke(((WorkLogEntry) b), new Object[]{})
                                .toString()
                                .compareTo(
                                        m1.invoke(((WorkLogEntry) a), new Object[]{})
                                                .toString());
                    else
                        // 正序
                        ret = m1.invoke(((WorkLogEntry) a), new Object[]{})
                                .toString()
                                .compareTo(
                                        m2.invoke(((WorkLogEntry) b), new Object[]{})
                                                .toString());
                } catch (NoSuchMethodException ne) {
                    logger.error(ne.getMessage());
                } catch (IllegalAccessException ie) {
                    logger.error(ie.getMessage());
                } catch (InvocationTargetException it) {
                    logger.error(it.getMessage());
                }
                return ret;
            }
        });
        return list;
    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        // TODO Auto-generated method stub

    }
}
