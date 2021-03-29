package com.hd.hse.ss.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.cbs.GetSupervisionList;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.ss.R;
import com.hd.hse.ss.activity.SupervisionDetailActivity;
import com.hd.hse.ss.activity.SupervisionListActivity;
import com.hd.hse.ss.adapter.SupervisionListAdapter;
import com.hd.hse.ss.adapter.SupervisionListAdapter.OnNetItemClickListener;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 现场监督已上传列表
 *
 * @author yn
 */
public class SupervisionListUpLoadedFragment extends Fragment {
    private static Logger logger = LogUtils
            .getLogger(SupervisionListActivity.class);
    private View view;
    private Activity activity;
    private ListView lv;
    private SupervisionListAdapter mSupervisionListAdapter;
    private List<WorkLogEntry> mDatas;
    /**
     * 当前listview里面的数据
     */
    private List<WorkLogEntry> currentDatas;
    /**
     * action:TODO(后台业务处理).
     */
    // private BusinessAction action;
    private BusinessAsyncTask asyncTask;
    private BusinessAction actionRules;
    private GetSupervisionList record;
    private boolean isFirst = true;
    private ProgressDialog dialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.supervision_list_fragment_layout,
                null, false);
        lv = (ListView) view
                .findViewById(R.id.hse_ss_phone_app_supervision_list_frag_lv);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            getNetData();
        } else {
            isFirst = false;
            initData();
        }

    }

    private void initData() {
        dialog = new ProgressDialog(activity, true, "正在加载已上传数据...");
        mDatas = new ArrayList<WorkLogEntry>();

        mSupervisionListAdapter = new SupervisionListAdapter(mDatas, null,
                activity);
        currentDatas = mDatas;
        mSupervisionListAdapter
                .setOnNetItemClickListener(new OnNetItemClickListener() {
                    @Override
                    public void onNetItemClick(int postion) {
                        Intent intent = new Intent(activity,
                                SupervisionDetailActivity.class);
                        intent.putExtra(SupervisionDetailActivity.ISSAVE, false);
                        intent.putExtra(
                                SupervisionDetailActivity.UD_CBSGL_RZID,
                                currentDatas.get(postion));
                        startActivity(intent);
                    }
                });
        lv.setAdapter(mSupervisionListAdapter);
        getNetData();
    }

    /**
     * 得到已上传数据
     */
    private void getNetData() {
        // 加载动画，得到数据后停止
        record = new GetSupervisionList();

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
                                activity,
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
                        mDatas = (List<WorkLogEntry>) msg.getReturnResult();
                        if (mDatas != null) {
                            if (mSupervisionListAdapter == null) {
                                mSupervisionListAdapter = new SupervisionListAdapter(mDatas, null,
                                        activity);
                            } else {
                                mSupervisionListAdapter.setmSupervisionList(mDatas);
                            }
                            //过滤数据，若未上传存在相同rzid的日志，过滤掉
                            filterDatas(mDatas);
                            currentDatas = mDatas;
                            mSupervisionListAdapter.notifyDataSetChanged();
                        }

                    }
                });
        try {

            asyncTask.execute(IActionType.WEB_INOROUTRECORD, "15");
            if (dialog != null) {
                dialog.show();
            }
        } catch (HDException e) {
            ToastUtils.imgToast(activity, R.drawable.hd_hse_common_msg_wrong,
                    "获取已上传数据失败");
            try {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            } catch (Exception e1) {
                logger.error(e1);
            }
        }

    }

    private void filterDatas(List<WorkLogEntry> datas) {
        List<WorkLogEntry> waitUpDatas = ((SupervisionListActivity) activity).getwaitUpDatas();
        if (waitUpDatas != null) {
            for (int i = 0; i < datas.size(); i++) {
                for (int j = 0; j < waitUpDatas.size(); j++) {
                    if (datas.get(i).getPadrzid().equals(waitUpDatas.get(j).getPadrzid())) {
                        datas.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    /**
     * 刷新已上传列表
     */
    public void flashUpList() {
        if (mSupervisionListAdapter != null) {
            getNetData();
        }
    }

    public void cancelSearch() {
        if (mDatas != null && mDatas.size() > 0) {
            mSupervisionListAdapter.setmSupervisionList(mDatas);
            currentDatas = mDatas;
            mSupervisionListAdapter.notifyDataSetChanged();
        }
    }

    public void search(String string) {
        if (mDatas != null && mDatas.size() > 0) {
            if (string == null || string.equals("")) {
                ToastUtils.toast(activity, "请输入搜索条件");
            } else {
                List<WorkLogEntry> searchDatas = new ArrayList<WorkLogEntry>();
                for (WorkLogEntry mWorkLog : mDatas) {
                    String sddept = mWorkLog.getSddept_desc();
                    String fxwt = mWorkLog.getFxwt();
                    if ((sddept != null && sddept.contains(string) || (fxwt != null && fxwt.contains(string)))) {
                        searchDatas.add(mWorkLog);
                    }
                }
                mSupervisionListAdapter.setmSupervisionList(searchDatas);
                currentDatas = searchDatas;
                mSupervisionListAdapter.notifyDataSetChanged();
                if (searchDatas.size() == 0) {
                    ToastUtils.toast(activity, "未搜索到相关数据");
                }
            }
        } else {
            ToastUtils.toast(activity, "暂无数据可搜索");
        }

    }
}
