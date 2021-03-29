package com.hd.hse.osc.phone.ui.fragment.tempelectricity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ExpandListView;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.custom.ExamineDialog;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.ITableName;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleDevice;
import com.hd.hse.entity.tempele.TempEleZy;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.activity.tempelectricity.TempEleChoiceListActivity;
import com.hd.hse.osc.phone.ui.fragment.tempelectricity.TempElePopUpWindow.CallBackListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

public class TempEleFragment extends NaviFrameFragment implements
		CallBackListener {
	private static Logger logger = LogUtils.getLogger(TempEleFragment.class);
	private static final int REQUEST_CODE = 1;

	private EditText purposeEdit; // 临时用电目的
	private EditText voltageEdit; // 工作电压
	private TextView licenseTxt; // 用火许可证编号
	private EditText pointEdit; // 电源接入点
	private TextView seleTxt; // 设备选择按钮，点击跳转到用电设备清单
	private TextView addTxt;// 设备添加按钮，手动输入
	private ExpandListView listView; // 用电设备清单列表
	private TextView totalTxt; // 负荷合计
	private TextView shenheBtn; // 审核按钮

	private BusinessAction busAction;
	private BusinessAsyncTask asyTask;
	private ProgressDialog proDialog;

	private List<WorkApprovalPermission> examineList;
	private ExamineDialog<WorkApprovalPermission> exmaineDialog;

	private boolean isSaving = false; // 正在保存

	/**
	 * mWorkOrder:TODO(表示票对象).
	 */
	private WorkOrder mWorkOrder;

	/**
	 * mPDAWorkOrderInfoConfig:TODO(表示临时用电配置信息对象).
	 */
	private PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig;
	private TempEleZy mTempEleZy;
	private List<TempEleDevice> mDevices;
	private List<TempEleAsset> choiceDevices;
	private IQueryWorkInfo queryWorkInfo;
	private MyListAdapter mAdapter;
	/**
	 * 添加用电设备的pop
	 */
	private TempElePopUpWindow mTempElePopUpWindow;
	private Activity activity;

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View contentView = inflater.inflate(
				R.layout.hd_hse_scene_phone_tempele_fragment, container, false);
		mTempElePopUpWindow = new TempElePopUpWindow(activity);
		// 设置回调监听
		mTempElePopUpWindow.setCallBackListener(this);
		findView(contentView);
		return contentView;
	}

	private void findView(View view) {
		purposeEdit = (EditText) view.findViewById(R.id.purpose_edit);
		voltageEdit = (EditText) view.findViewById(R.id.voltage_edit);
		licenseTxt = (TextView) view.findViewById(R.id.license_txt);
		pointEdit = (EditText) view.findViewById(R.id.point_edit);
		seleTxt = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_sele);
		addTxt = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_add);
		listView = (ExpandListView) view.findViewById(R.id.listview);
		totalTxt = (TextView) view.findViewById(R.id.total_num);
		shenheBtn = (TextView) view.findViewById(R.id.shenhe_btn);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init() {
		if (queryWorkInfo == null) {
			queryWorkInfo = new QueryWorkInfo();
		}
		try {
			Map<String, Object> map = queryWorkInfo.queryTempEleInfo(
					mWorkOrder, null);
			mTempEleZy = (TempEleZy) map.get(TempEleZy.class.getName());
			mDevices = (List<TempEleDevice>) map.get(TempEleDevice.class
					.getName());
			choiceDevices = (List<TempEleAsset>) map.get(TempEleAsset.class
					.getName());
			if (mDevices == null) {
				mDevices = new ArrayList<>();
			}
			if (choiceDevices == null) {
				choiceDevices = new ArrayList<>();
			}
			if (mTempEleZy != null && mTempEleZy.getLsydyt() != null) {
				purposeEdit.setText(mTempEleZy.getLsydyt());
			}
			if (mTempEleZy != null && mTempEleZy.getGzdy() != null) {
				voltageEdit.setText(mTempEleZy.getGzdy());
			}
			if (mTempEleZy != null && mTempEleZy.getDyjrd() != null) {
				pointEdit.setText(mTempEleZy.getDyjrd());
			}
			mAdapter = new MyListAdapter();
			listView.setAdapter(mAdapter);
			// 设置listview的单击事件
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					mTempElePopUpWindow.showPopupWindow(arg1,
							choiceDevices.get(arg2));
				}
			});
			totalTxt.setText(getHeji() + "");
			seleTxt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(mActivity,
							TempEleChoiceListActivity.class);
					intent.putExtra(TempEleChoiceListActivity.WORK_ORDER,
							mWorkOrder);
					intent.putExtra(TempEleChoiceListActivity.DEVICE,
							(Serializable) mDevices);
					intent.putExtra(TempEleChoiceListActivity.CHOICE_DEVICE,
							(Serializable) choiceDevices);
					startActivityForResult(intent, REQUEST_CODE);
				}
			});
			/**
			 * addTxt添加单击事件，单击弹出mTempElePopUpWindow
			 */
			addTxt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					mTempElePopUpWindow.showPopupWindow(arg0,
							mWorkOrder.getUd_zyxk_zysqid());
				}
			});
			busAction = new BusinessAction(new CheckControllerActionListener());
			asyTask = new BusinessAsyncTask(busAction, asyncCallBack);
			WorkApprovalPersonRecord _queryPersonCard = new WorkApprovalPersonRecord();
			_queryPersonCard.setTablename(ITableName.UD_ZYXK_LSYDZY);
			_queryPersonCard.setTableid(mTempEleZy.getUd_zyxk_lsydzyid() + "");
			examineList = queryWorkInfo.queryApprovalPermission(mWorkOrder,
					mPDAWorkOrderInfoConfig, _queryPersonCard, null);
			if (examineList == null || examineList.size() == 0) {
				shenheBtn.setText("保  存");
				shenheBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (!isSaving) {
							proDialog = new ProgressDialog(mActivity,
									"正在保存临时用电信息...");
							proDialog.show();
							isSaving = true;
							saveTempEleInfo();
						}
					}
				});
			} else {
				exmaineDialog = new ExamineDialog<WorkApprovalPermission>(
						getActivity(), 0);
				exmaineDialog.setWorkOrder(mWorkOrder);
				exmaineDialog.setExamineList(examineList);
				// exmaineDialog.setiEventLsn(getDialogIEventLsn());
				exmaineDialog.setFragment(this);
				shenheBtn.setText("审  核");
				shenheBtn.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (exmaineDialog != null && !exmaineDialog.isShowing()) {
							exmaineDialog.reFresh();
							exmaineDialog.show();
						}
					}
				});
			}
		} catch (HDException e) {
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
			logger.error(e);
		}
	}

	private AbstractAsyncCallBack asyncCallBack = new AbstractAsyncCallBack() {

		@Override
		public void start(Bundle msgData) {

		}

		@Override
		public void processing(Bundle msgData) {

		}

		@Override
		public void error(Bundle msgData) {
			isSaving = false;
			proDialog.dismiss();
			ToastUtils.imgToast(getActivity(),
					R.drawable.hd_hse_common_msg_wrong,
					msgData.getString(IActionType.ACTION_TYPE_ELE));
		}

		@Override
		public void end(Bundle msgData) {
			isSaving = false;
			proDialog.dismiss();
			ToastUtils.toast(mActivity, "保存成功");
			PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
					.getNaviCurrentEntity();
			if (null != naviCurrentEntity) {
				naviCurrentEntity.setIsComplete(1);

				setNaviCurrentEntity(naviCurrentEntity);
			}
		}
	};

	private void saveTempEleInfo() {
		String purpose = purposeEdit.getText().toString();
		mTempEleZy.setLsydyt(purpose);
		String voltage = voltageEdit.getText().toString();
		if (voltage == null || voltage.equals("")) {
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"工作电压不能为空!");
			isSaving = false;
			proDialog.dismiss();
			return;
		} else {
			mTempEleZy.setGzdy(voltage);
		}
		if (choiceDevices == null || choiceDevices.size() == 0) {
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					"用电设备清单不能为空!");
			isSaving = false;
			proDialog.dismiss();
			return;
		}
		for (TempEleAsset asset : choiceDevices) {
			if (asset.getCount() == 0) {
				ToastUtils.imgToast(mActivity,
						R.drawable.hd_hse_common_msg_wrong, "用电设备数量不能为空或0!");
				isSaving = false;
				proDialog.dismiss();
				return;
			}
		}
		String dyjrd = pointEdit.getText().toString();
		mTempEleZy.setDyjrd(dyjrd);
		mTempEleZy.setSbfh(getHeji());
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(WorkOrder.class.getName(), mWorkOrder);
			map.put(PDAWorkOrderInfoConfig.class.getName(),
					mPDAWorkOrderInfoConfig);
			map.put(TempEleZy.class.getName(), mTempEleZy);
			map.put(TempEleAsset.class.getName(), choiceDevices);
			asyTask.execute(IActionType.ACTION_TYPE_ELE, map);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(mActivity, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	@Override
	public void refreshData() {

	}

	@Override
	public void initData(List<Object> data) {
		// 在这个 Fragment中传递的值的协议是 WorkOrder和 PDAWorkOrderInfoConfig
		if (data.size() < 2) {
			return;
		}
		mWorkOrder = (WorkOrder) data.get(0);
		mPDAWorkOrderInfoConfig = (PDAWorkOrderInfoConfig) data.get(1);
	}

	private double getHeji() {
		double heji = 0.0;
		for (TempEleAsset device : choiceDevices) {
			heji += Double.valueOf(device.getFh()) * device.getCount();
		}
		/**
		 * 处理double浮点计算时产生的误差
		 */
		BigDecimal b = new BigDecimal(heji);
		b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		double d = b.doubleValue();
		return d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			choiceDevices.clear();
			choiceDevices = (List<TempEleAsset>) data
					.getSerializableExtra(TempEleChoiceListActivity.CHOICE_DEVICE);
			mDevices = (List<TempEleDevice>) data
					.getSerializableExtra(TempEleChoiceListActivity.DEVICE);
			mAdapter.notifyDataSetChanged();
			double heji = getHeji();
			totalTxt.setText(heji + "");
			mTempEleZy.setSbfh(heji);
		}
	}

	private class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return choiceDevices.size();
		}

		@Override
		public Object getItem(int arg0) {
			return choiceDevices.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup viewGroup) {

			view = LayoutInflater.from(getActivity()).inflate(
					R.layout.hd_hse_scence_tempele_fragment_recycle_item,
					viewGroup, false);
			TextView deviceNameTxt = (TextView) view
					.findViewById(R.id.devicename_txt);
			TextView deviceModelTxt = (TextView) view
					.findViewById(R.id.model_txt);
			TextView powerTxt = (TextView) view.findViewById(R.id.power_txt);
			TextView countTxt = (TextView) view.findViewById(R.id.count_edit);
			deviceNameTxt.setText(choiceDevices.get(position).getAssetname());
			deviceModelTxt.setText(choiceDevices.get(position).getVersion());
			powerTxt.setText(choiceDevices.get(position).getFh() + "");
			countTxt.setText(choiceDevices.get(position).getCount() + "");
			return view;
		}

	}

	@Override
	public void onSaveEditYdsb(TempEleAsset tempEleAsset) {
		mAdapter.notifyDataSetChanged();
		double heji = getHeji();
		totalTxt.setText(heji + "");
		mTempEleZy.setSbfh(heji);
	}

	@Override
	public void onSaveCreateYdsb(TempEleAsset tempEleAsset) {
		choiceDevices.add(tempEleAsset);
		mAdapter.notifyDataSetChanged();
		double heji = getHeji();
		totalTxt.setText(heji + "");
		mTempEleZy.setSbfh(heji);
	}

	@Override
	public void onDeleYdsb(TempEleAsset tempEleAsset) {
		choiceDevices.remove(tempEleAsset);
		mAdapter.notifyDataSetChanged();
		double heji = getHeji();
		totalTxt.setText(heji + "");
		mTempEleZy.setSbfh(heji);
	}

}
