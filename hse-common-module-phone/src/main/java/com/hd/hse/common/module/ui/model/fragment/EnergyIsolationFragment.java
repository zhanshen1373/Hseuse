package com.hd.hse.common.module.ui.model.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.EnergyDangerousDentifyRectiyActivity;
import com.hd.hse.common.module.phone.ui.activity.EnergyIsloationDetailEditorActivity;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.ITableName;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.EnergyIsolation;
import com.hd.hse.entity.base.EnergyIsolationDetail;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 能量隔离Fragment
 * 
 * Created by liuyang on 2016年2月17日
 */
public class EnergyIsolationFragment extends NaviFrameFragment implements
		ViewPager.OnPageChangeListener {
	private WorkOrder mWorkOrder;
	private PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig;
	private EnergyIsolation mEnergyIsolation;

	private static final int REQUEST_EDITOR = 0;
	private static final int REQUEST_WHSB = 1;

	private IQueryWorkInfo queryWorkInfo;
	private static Logger logger = LogUtils
			.getLogger(EnergyIsolationFragment.class);

	private EditText glSYSEdit; // 隔离系统/设备
	private TextView whsbText; // 危害识别
	private ImageView whsbBtn; // 危害识别修改按钮
	private ViewPager viewPager; // 能量隔离子表展示ViewPager
	private Button addGLFFBtn; // 新增隔离方法按钮
	private Button updateGLFFBtn; // 修改隔离方法按钮
	private ExamineListView<WorkApprovalPermission> examineListView; // 审批换节点

	private List<Domain> whsbList; // 危害识别备选列表
	private List<Domain> glffList; // 隔离方法备选列表
	private ViewPagerAdapter adapter;
	private LinearLayout circleglffLinearLayout;
	private LayoutInflater inflater;

	private List<WorkApprovalPermission> workApprovalPermissions; // 审批环节点
	private WorkApprovalPermission curApproveNode;

	private BusinessAction busAction;
	private BusinessAsyncTask asyTask;
	private ProgressDialog prgDialog;
	private String actionType = IActionType.ACTION_TYPE_ENERGY;

	/**
	 * 用于从下一个界面返回时刷新界面
	 * 
	 * created by wangdanfeng
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		if (mEnergyIsolation.getChild("details").size() != 0) {
			viewPager.setVisibility(View.VISIBLE);
			adapter = new ViewPagerAdapter(mEnergyIsolation.getChild("details"));
			viewPager.setAdapter(adapter);
		} else {
			viewPager.setVisibility(View.GONE);
		}
		try {
			WorkOrder order = queryWorkInfo.queryWorkInfo(mWorkOrder, null);
			if (!order.getStatus().equals(IWorkOrderStatus.INPRG)) {
				bannedEditing();
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.toast(mActivity, "查询作业票失败!");
		}
		super.onStart();
	}

	@Override
	protected void init() {
		if (mEnergyIsolation != null) {
			glSYSEdit.setText(mEnergyIsolation.getGlasset());
			whsbText.setText(mEnergyIsolation.getHazard());

			adapter = new ViewPagerAdapter(mEnergyIsolation.getChild("details"));
			viewPager.setAdapter(adapter);
			prgDialog = new ProgressDialog(mActivity, "信息保存中");
			if (!mEnergyIsolation.getStatus().equals(IWorkOrderStatus.INPRG)) {
				bannedEditing();
			}
		}
	}

	@Override
	public void refreshData() {
		if (mEnergyIsolation.getChild("details").size() != 0) {
			viewPager.setVisibility(View.VISIBLE);
			adapter = new ViewPagerAdapter(mEnergyIsolation.getChild("details"));
			viewPager.setAdapter(adapter);
		} else {
			viewPager.setVisibility(View.GONE);
		}
	}
	
	

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		// 每次创建时执行
		View _view = inflater
				.inflate(
						R.layout.hd_hse_common_module_phone_frag_energy_isolation,
						null);
		findViewById(_view);

		whsbBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// showWhsbDialog();
				showDangerousRectifyActivity();
			}

		});
		addGLFFBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDetailEditorActivity(null);
			}
		});
		updateGLFFBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mEnergyIsolation.getChild("details") == null
						|| mEnergyIsolation.getChild("details").size() == 0) {
					ToastUtils.toast(mActivity, "当前没有可编辑的内容，请新增序号");
				} else {
					showDetailEditorActivity((EnergyIsolationDetail) mEnergyIsolation
							.getChild("details").get(
									mEnergyIsolation.getChild("details").size()
											- viewPager.getCurrentItem() - 1));
				}
			}
		});
		examineListView.clearDatas();
		examineListView.setData(workApprovalPermissions);
		examineListView.setWorkOrder(mWorkOrder);
		examineListView.setFragment(EnergyIsolationFragment.this);

		this.inflater = inflater;
		initCircleGlff();
		return _view;
	}

	@Override
	public void initData(List<Object> data) {
		mWorkOrder = (WorkOrder) data.get(0);
		mPDAWorkOrderInfoConfig = (PDAWorkOrderInfoConfig) data.get(1);
		queryWorkInfo = new QueryWorkInfo();
		try {
			mEnergyIsolation = queryWorkInfo.queryEnergyIsolation(
					mWorkOrder.getUd_zyxk_zysqid(), null);
			whsbList = queryWorkInfo.queryDomain("UDHARM", null);
			glffList = queryWorkInfo.queryDomain("NLGLFF", null);
			WorkApprovalPersonRecord _queryPersonCard = new WorkApprovalPersonRecord();
			_queryPersonCard.setTablename(ITableName.UD_ZYXK_NLGLD);
			_queryPersonCard.setTableid(String.valueOf(mEnergyIsolation
					.getUd_zyxk_nlgldid()));
			workApprovalPermissions = queryWorkInfo
					.queryApprovalPermission(mWorkOrder,
							mPDAWorkOrderInfoConfig, _queryPersonCard, null);
			busAction = new BusinessAction(new CheckControllerActionListener());
			asyTask = new BusinessAsyncTask(busAction, asyncCallBack);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	private void findViewById(View v) {
		glSYSEdit = (EditText) v.findViewById(R.id.gl_sys_edit);
		whsbText = (TextView) v.findViewById(R.id.whsb_text);
		whsbBtn = (ImageView) v.findViewById(R.id.whsb_btn);
		viewPager = (ViewPager) v.findViewById(R.id.viewpager);
		addGLFFBtn = (Button) v.findViewById(R.id.add_glff_btn);
		updateGLFFBtn = (Button) v.findViewById(R.id.update_glff_btn);
		circleglffLinearLayout = (LinearLayout) v
				.findViewById(R.id.circle_glff_linear);
		examineListView = (ExamineListView) v.findViewById(R.id.listview);
		viewPager.setOnPageChangeListener(this);
		glSYSEdit.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				if (glSYSEdit.getText().toString().trim().equals("")) {
					ToastUtils.toast(mActivity, "当前没有内容");
				} else {
					new AlertDialog.Builder(mActivity)
							.setMessage(glSYSEdit.getText().toString())
							.setTitle("具体内容")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated method stub

										}
									}).create().show();

				}

				return false;

			}
		});
		// 长按显示TextView文本
		whsbText.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {

				if (whsbText.getText().toString().trim().equals("")) {
					ToastUtils.toast(mActivity, "当前没有内容");
				} else {
					new AlertDialog.Builder(mActivity)
							.setMessage(whsbText.getText().toString())
							.setTitle("具体内容")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											// TODO Auto-generated method stub

										}
									}).create().show();

				}

				return false;
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_EDITOR) {
			if (mEnergyIsolation.getChild("details") != null
					&& mEnergyIsolation.getChild("details").size() != 0) {
				viewPager.setCurrentItem(0);
			}
		}
		if (requestCode == REQUEST_EDITOR && resultCode == Activity.RESULT_OK) {
			EnergyIsolationDetail detail = (EnergyIsolationDetail) data
					.getSerializableExtra(EnergyIsloationDetailEditorActivity.DATA_CODE);
			saveDetail(detail);
			initCircleGlff();
			// notifyDataChanged为什么不好用。。。
			adapter = new ViewPagerAdapter(mEnergyIsolation.getChild("details"));
			viewPager.setAdapter(adapter);
			viewPager.setCurrentItem(0);

		} else if (requestCode == IEventType.READCARD_TYPE
				&& resultCode == IEventType.READCARD_TYPE) {
			if (data == null) {
				return;
			}
			WorkApprovalPermission _curr = (WorkApprovalPermission) data
					.getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
			saveWorkApprovalPermission(_curr);
		}

		if (requestCode == REQUEST_WHSB && resultCode == Activity.RESULT_OK) {
			String selectedwhsb = data
					.getStringExtra(EnergyDangerousDentifyRectiyActivity.REQUEST_CODE);
			whsbText.setText(selectedwhsb);
			mEnergyIsolation.setHazard(selectedwhsb.toString());
			saveWhsb(selectedwhsb.toString());

		}
	}

	/**
	 * asyncCallBack:TODO(换节点保存 异步回调函数).
	 */
	private AbstractAsyncCallBack asyncCallBack = new AbstractAsyncCallBack() {

		@Override
		public void start(Bundle msgData) {

		}

		@Override
		public void processing(Bundle msgData) {
			if (msgData.containsKey(actionType)) {
				prgDialog.showMsg(msgData.getString(actionType));
			}
		}

		@Override
		public void end(Bundle msgData) {
			if (msgData.containsKey(actionType)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(mActivity,
						R.drawable.hd_hse_common_msg_right, "保存成功");
			}
			examineListView.setCurrentEntity(curApproveNode);
			if (curApproveNode == null || curApproveNode.getIsend() == 1) {
				PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
						.getNaviCurrentEntity();
				if (null != naviCurrentEntity) {
					naviCurrentEntity.setIsComplete(1);
					bannedEditing();
					setNaviCurrentEntity(naviCurrentEntity);
				}
			}
		}

		@Override
		public void error(Bundle msgData) {
			if (msgData.containsKey(actionType)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(mActivity,
						R.drawable.hd_hse_common_msg_wrong,
						msgData.getString(actionType));
			}
		}

	};

	/**
	 * 禁止编辑
	 * 
	 * Created by liuyang on 2016年2月29日
	 */
	private void bannedEditing() {
		glSYSEdit.setEnabled(false);
		whsbBtn.setVisibility(View.INVISIBLE);
		addGLFFBtn.setVisibility(View.INVISIBLE);
		updateGLFFBtn.setVisibility(View.INVISIBLE);
	}

	/**
	 * 初始化圆点
	 * 
	 * Created by wangdanfeng on 2016年2月29日
	 */
	private void initCircleGlff() {
		circleglffLinearLayout.removeAllViews();
		for (int i = 0; i < mEnergyIsolation.getChild("details").size(); i++) {
			if (i <= 5) {
				View view = inflater.inflate(
						R.layout.hd_hse_common_module_circle_glff,
						circleglffLinearLayout, false);
				circleglffLinearLayout.addView(view);
			}
		}
		if (circleglffLinearLayout.getChildAt(0) != null) {
			circleglffLinearLayout.getChildAt(0).setBackgroundResource(
					R.drawable.circle_glff_selected);
		}

	}

	/**
	 * 显示危害识别Dialog
	 * 
	 * Created by liuyang on 2016年2月18日
	 */
	private void showWhsbDialog() {
		final String[] items = new String[whsbList.size()];
		final boolean[] selectedStatus = new boolean[whsbList.size()];
		for (int i = 0; i < whsbList.size(); i++) {
			items[i] = whsbList.get(i).getDescription();
			if (mEnergyIsolation.getHazard().contains(
					whsbList.get(i).getDescription())) {
				selectedStatus[i] = true;
			} else {
				selectedStatus[i] = false;
			}
		}

		new AlertDialog.Builder(getActivity())
				.setTitle("请选择危害识别")
				.setMultiChoiceItems(items, selectedStatus,
						new OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface arg0,
									int position, boolean isChecked) {
								// 来回重复选择取消，得相应去改变item对应的bool值，点击确定时，根据这个bool[],得到选择的内容
								selectedStatus[position] = isChecked;
							}

						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						StringBuilder whsb = new StringBuilder();
						for (int i = 0; i < selectedStatus.length; i++) {
							if (selectedStatus[i]) {
								whsb.append(items[i] + ",");
							}
						}
						whsbText.setText(whsb);
						mEnergyIsolation.setHazard(whsb.toString());
						saveWhsb(whsb.toString());
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 危害识别
	 * 
	 * Created by wangdanfeng on 2016年2月29日
	 */
	private void showDangerousRectifyActivity() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(mActivity,
				EnergyDangerousDentifyRectiyActivity.class);
		intent.putExtra(EnergyDangerousDentifyRectiyActivity.REQUEST_CODE,
				mEnergyIsolation.getHazard());
		intent.putExtra("title", "危害识别");
		startActivityForResult(intent, REQUEST_WHSB);

	}

	private void showDetailEditorActivity(EnergyIsolationDetail detail) {
		Intent intent = new Intent(mActivity,
				EnergyIsloationDetailEditorActivity.class);
		intent.putExtra(EnergyIsloationDetailEditorActivity.DATA_CODE, detail);
		if (detail != null) {
			intent.putExtra("title", "编辑当前");
		} else {
			intent.putExtra("title", "新增序号");
		}

		startActivityForResult(intent, REQUEST_EDITOR);
	}

	/**
	 * 保存危害识别
	 * 
	 * Created by liuyang on 2016年2月18日
	 * 
	 * @param whsb
	 */
	private void saveWhsb(String whsb) {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			BaseDao dao = new BaseDao();
			dao.updateEntity(con, mEnergyIsolation, new String[] { "hazard" });
			String sql = "update ud_zyxk_nlgld set hazard = '" + whsb
					+ "' where ud_zyxk_nlgldid = '"
					+ mEnergyIsolation.getUd_zyxk_nlgldid() + "';";
			dao.executeUpdate(con, sql);
			con.commit();
		} catch (SQLException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"数据库连接异常");
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"保存危害识别失败");
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {

				}
			}
		}
	}

	/**
	 * 新增或者修改能量隔离详情
	 * 
	 * Created by liuyang on 2016年2月22日
	 * 
	 * @param detail
	 */
	private void saveDetail(EnergyIsolationDetail detail) {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			BaseDao dao = new BaseDao();
			if (detail.getUd_zyxk_nlgldlineid() == 0) {
				int seqNum = mEnergyIsolation.getChild("details").size() + 1;
				detail.setSeqnum(seqNum);
				detail.setUd_zyxk_nlgldid(mEnergyIsolation.getUd_zyxk_nlgldid());
				detail.setUd_zyxk_nlgldlineid(mEnergyIsolation
						.getUd_zyxk_nlgldid() * 10 + seqNum);
				detail.setNlgldnum(mEnergyIsolation.getNlgldnum());
				dao.insertEntity(con, detail);
				// mEnergyIsolation.getChild("details").add(detail);
			} else {
				dao.updateEntity(con, detail);
			}
			con.commit();
		} catch (SQLException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"数据库连接异常");
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"保存失败");
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {

				}
			}
		}
		try {
			mEnergyIsolation = queryWorkInfo.queryEnergyIsolation(
					mWorkOrder.getUd_zyxk_zysqid(), null);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	private void saveWorkApprovalPermission(WorkApprovalPermission wap) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put(WorkOrder.class.getName(), mWorkOrder);

		// 审批环节点
		curApproveNode = wap;
		mapParam.put(WorkApprovalPermission.class.getName(), curApproveNode);
		mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
				mPDAWorkOrderInfoConfig);
		mEnergyIsolation.setGlasset(glSYSEdit.getText().toString());
		mapParam.put(EnergyIsolation.class.getName(), mEnergyIsolation);
		WorkApprovalPersonRecord personCard = new WorkApprovalPersonRecord();
		personCard.setTablename(ITableName.UD_ZYXK_NLGLD);
		personCard.setTableid(String.valueOf(mEnergyIsolation
				.getUd_zyxk_nlgldid()));
		personCard.setDycode(mPDAWorkOrderInfoConfig.getDycode());
		mapParam.put(WorkApprovalPersonRecord.class.getName(), personCard);
		prgDialog.show();
		try {
			asyTask.execute(actionType, mapParam);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	private class ViewPagerAdapter extends PagerAdapter {
		private List<SuperEntity> datas;

		public ViewPagerAdapter(List<SuperEntity> datas) {
			this.datas = datas;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Log.w(this.getClass().getSimpleName(), "destroyItem position:"
					+ position);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LayoutInflater.from(mActivity).inflate(
					R.layout.hd_hse_item_energy_isolation_fragment_detail_item,
					null);
			TextView nlwlText = (TextView) view.findViewById(R.id.nlwl_text);
			TextView ssgpdText = (TextView) view.findViewById(R.id.ssgpd_text);
			TextView gpdText = (TextView) view.findViewById(R.id.gpd_text);
			final TextView glffText = (TextView) view
					.findViewById(R.id.glff_text);

			glffText.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View arg0) {

					if (glffText.getText().toString().trim().equals("")) {
						ToastUtils.toast(mActivity, "当前没有内容");
					} else {
						new AlertDialog.Builder(mActivity)
								.setMessage(glffText.getText().toString())
								.setTitle("具体内容")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method
												// stub

											}
										}).create().show();

					}

					return false;
				}
			});

			nlwlText.setText(((EnergyIsolationDetail) datas.get(getCount()
					- position - 1)).getNlwl());
			ssgpdText.setText(((EnergyIsolationDetail) datas.get(getCount()
					- position - 1)).getSsgpd());
			gpdText.setText(((EnergyIsolationDetail) datas.get(getCount()
					- position - 1)).getGpd());
			// 隔离方法编码转文字用于展示
			String glffString = ((EnergyIsolationDetail) datas.get(getCount()
					- position - 1)).getGlff();
			if (glffString != null && !glffString.equals("")) {
				String[] glffkeys = glffString.split(",");
				String glffStr = "";
				for (String k : glffkeys) {
					for (Domain d : glffList) {
						if (d.getValue().equals(k)) {
							String desc = d.getDescription();
							if (k.equals("QT")) {
								desc = desc
										+ ((EnergyIsolationDetail) datas
												.get(getCount() - position - 1))
												.getNlglqt();

							}
							glffStr += glffStr.equals("") ? desc : ("," + desc);
						}
					}
				}
				glffText.setText(glffStr);
			}
			container.addView(view);
			return view;
		}

	}

	@Override
	public void onResume() {
		Log.e("界面调回", 0 + "");
		viewPager.setCurrentItem(0);
		super.onResume();
	}

	// 对ViewPager进行监听
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		int childcount = circleglffLinearLayout.getChildCount();
		for (int i = 0; i < childcount; i++) {
			circleglffLinearLayout.getChildAt(i).setBackgroundResource(
					R.drawable.circle_glff);
		}
		if (arg0 <= 5) {
			circleglffLinearLayout.getChildAt(arg0).setBackgroundResource(
					R.drawable.circle_glff_selected);
		} else {
			circleglffLinearLayout.getChildAt(childcount - 1)
					.setBackgroundResource(R.drawable.circle_glff_selected);

		}
	}

}
