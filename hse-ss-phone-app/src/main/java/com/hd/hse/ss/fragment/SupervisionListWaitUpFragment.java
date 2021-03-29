package com.hd.hse.ss.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.web.wtdj.UpWtdjInfo;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.ss.CheckSupervisionRecords;
import com.hd.hse.ss.R;
import com.hd.hse.ss.activity.SupervisionListActivity;
import com.hd.hse.ss.adapter.SupervisionListAdapter;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 未上传列表
 * 
 * @author yn
 * 
 */
public class SupervisionListWaitUpFragment extends Fragment {
	private static Logger logger = LogUtils
			.getLogger(SupervisionListActivity.class);
	private View view;
	private Activity activity;
	private ListView lv;
	private SupervisionListAdapter mSupervisionListAdapter;
	private List<WorkLogEntry> mDatas;
	private List<WorkLogEntry> temp;
	private boolean isFirst = true;
	private Listener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
		try {
			mListener = (Listener) activity;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}

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
			if (mSupervisionListAdapter != null)
				mSupervisionListAdapter.setStateToNormal();
			mDatas = getData();
			if (mDatas != null) {
				mSupervisionListAdapter.setmSupervisionList(mDatas);
				mSupervisionListAdapter.notifyDataSetChanged();
			}
		} else {
			isFirst = false;
			initData();
		}

	}

	private void initData() {

		mDatas = getData();
		temp = new ArrayList<WorkLogEntry>();
		mSupervisionListAdapter = new SupervisionListAdapter(mDatas, temp,
				activity);
		lv.setAdapter(mSupervisionListAdapter);
		mSupervisionListAdapter.setStateToNormal();
	}

	/**
	 * 得到数据
	 */
	private List<WorkLogEntry> getData() {
		List<WorkLogEntry> mDatas;
		try {
			mDatas = CheckSupervisionRecords.getInstance()
					.checkSupervisionList(
							SystemProperty.getSystemProperty().getLoginPerson()
									.getPersonid());
		} catch (HDException e) {
			ToastUtils.imgToast(activity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
			mDatas = null;
		}
		return mDatas;
	}

	public void changeLvStateToSelect() {
		if (mDatas != null && mDatas.size() > 0
				&& mSupervisionListAdapter != null) {
			mSupervisionListAdapter.setStateToSelect();
		}

	}

	public void changeLvStateToNormal() {
		if (mDatas != null && mSupervisionListAdapter != null) {
			mSupervisionListAdapter.setmSupervisionList(mDatas);
			mSupervisionListAdapter.setStateToNormal();
		}
	}

	public void deleSelectedItem() {
		if (temp == null || temp.size() <= 0) {
			ToastUtils.toast(activity, "您未选择任何条目");
			return;
		}
		// 将mSupervisionItem从mSupervisionList中移除
		mDatas.removeAll(temp);
		// 将mSupervisionItem从本地数据库移除
		deleteWorkLogsFromDB(temp.toArray(new WorkLogEntry[] {}));
		// 清空temp
		temp.clear();
		changeLvStateToNormal();
		// 回调通知activi状态恢复正常
		mListener.OnDeleSucess();

	}

	private boolean deleteWorkLogsFromDB(WorkLogEntry[] workLogs) {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			BaseDao dao = new BaseDao();
			dao.deleteEntities(con, workLogs);
			con.commit();
			return true;
		} catch (SQLException e) {
			logger.error(e);
			ToastUtils.imgToast(activity, R.drawable.hd_hse_common_msg_wrong,
					"数据库连接异常");
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(activity, R.drawable.hd_hse_common_msg_wrong,
					"保存问题登记失败");
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {

				}
			}
		}
		return false;
	}

	public void uploadSelectedItem() {
		if (temp == null || temp.size() <= 0) {
			ToastUtils.toast(activity, "您未选择任何条目");
			return;
		}
		// 把mSupervisionItem上传到服务器
		upWorkLog(temp);

	}

	private void upWorkLog(List<WorkLogEntry> data) {
		final ProgressDialog dialog = new ProgressDialog(activity, true,
				"正在上传...");
		// CBSWorklogUp worklog = new CBSWorklogUp();
		UpWtdjInfo worklog = new UpWtdjInfo();
		BusinessAction action = new BusinessAction(worklog);
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
						try {
							dialog.dismiss();
						} catch (Exception e) {
							logger.error(e);
						}
						ToastUtils
								.imgToast(activity,
										R.drawable.hd_hse_common_msg_wrong,
										"上传问题登记失败！");
					}

					@Override
					public void end(Bundle msgData) {
						try {
							dialog.dismiss();
							deleSelectedItem();
						} catch (Exception e) {
							logger.error(e);
						}
						ToastUtils
								.imgToast(activity,
										R.drawable.hd_hse_common_msg_right,
										"上传违章记录成功！");
						mListener.OnUpSucess();
					}
				});
		try {
			task.execute("", data);
			if (dialog != null) {
				dialog.show();
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(activity, R.drawable.hd_hse_common_msg_wrong,
					"上传问题登记失败！");
		}
	}

	public List<WorkLogEntry> getwaitUpDatas() {
		return mDatas;
	}

	/**
	 * 上传，删除监听
	 * 
	 * @author yn
	 * 
	 */
	public interface Listener {
		public void OnDeleSucess();

		public void OnUpSucess();

		public void OnNoItem();
	}

	public void cancelSearch() {
		if (mDatas != null && mDatas.size() > 0) {
			mSupervisionListAdapter.setmSupervisionList(mDatas);
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
					if ((sddept!=null&&sddept.contains(string)||(fxwt!=null&&fxwt.contains(string)))) {
						searchDatas.add(mWorkLog);
					}
				}
				mSupervisionListAdapter.setmSupervisionList(searchDatas);
				mSupervisionListAdapter.notifyDataSetChanged();
				if (searchDatas.size()==0) {
					ToastUtils.toast(activity, "未搜索到相关数据");
				}
			}
		} else {
			ToastUtils.toast(activity, "暂无数据可搜索");
		}

	}
}
