package com.hd.hse.osc.phone.ui.fragment.hoisting;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleZy;
import com.hd.hse.entity.workorder.HoistingWork;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;

/**
 * 吊装作业,吊物吊车信息，fragment
 * 
 * @author yn
 * 
 */
public class HoistingInfoFragment extends NaviFrameFragment {
	private static Logger logger = LogUtils
			.getLogger(HoistingInfoFragment.class);
	/**
	 * HwName: 吊装货物名称，edHwGg：货物规格(长*宽*高)；edHwZl：货物质量(最大单件质量)
	 * 
	 * edDzZl：吊装质量； edDzBj：吊装半径； edDzJd：吊装角度； edEdZh:额定载荷
	 */
	private EditText edHwName, edHwGg, edHwZl, edDzZl, edDzBj, edDzJd, edEdZh;
	/**
	 * 保存
	 */
	private TextView tvSave;

	/**
	 * mWorkOrder:TODO(表示票对象).
	 */
	private WorkOrder mWorkOrder;

	/**
	 * 吊装作业信息
	 */
	private HoistingWork hoistingWork;
	private boolean isUpdate = false;
	private BaseDao dao;

	private BusinessAction busAction;
	private BusinessAsyncTask asyTask;

	// private ProgressDialog proDialog;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View contentView = inflater
				.inflate(R.layout.hd_hse_scene_phone_hoisting_fragment,
						container, false);
		findView(contentView);

		return contentView;
	}

	private void findView(View view) {
		edHwName = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_name);
		edHwGg = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_gg);
		edHwZl = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_hwzl);
		edDzZl = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_dzzl);
		edDzBj = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_dzbj);
		edDzJd = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_dzjd);
		edEdZh = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_ed_edzh);
		tvSave = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_hoisting_fragment_tv_save);
		tvSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveHoistingWork();

			}

		});
	}

	@Override
	protected void init() {
		dao = new BaseDao();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ud_zyxk_dzzy where ud_zyxk_zysqid = '")
				.append(mWorkOrder.getUd_zyxk_zysqid()).append("';");
		try {
			hoistingWork = (HoistingWork) dao.executeQuery(sql.toString(),
					new EntityResult(HoistingWork.class));
			if (hoistingWork != null) {
				edHwName.setText(hoistingWork.getDzwjname());
				edHwGg.setText(hoistingWork.getWjsize());
				edHwZl.setText(hoistingWork.getWjzl());
				edDzZl.setText(hoistingWork.getDzzl());
				edDzBj.setText(hoistingWork.getDzgzbj());
				edDzJd.setText(hoistingWork.getDzgd());
				edEdZh.setText(hoistingWork.getQzjedzh());

				isUpdate = true;
			} else {
				isUpdate = false;
				hoistingWork = new HoistingWork();
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		busAction = new BusinessAction(new CheckControllerActionListener());
		asyTask = new BusinessAsyncTask(busAction, asyncCallBack);

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

			// proDialog.dismiss();
			ToastUtils.imgToast(getActivity(),
					R.drawable.hd_hse_common_msg_wrong,
					msgData.getString(IActionType.ACTION_TYPE_ELE));
		}

		@Override
		public void end(Bundle msgData) {

			// proDialog.dismiss();
			ToastUtils.toast(mActivity, "保存成功");
			PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
					.getNaviCurrentEntity();
			if (null != naviCurrentEntity) {
				naviCurrentEntity.setIsComplete(1);
				setNaviCurrentEntity(naviCurrentEntity);
			}
		}
	};

	@Override
	public void refreshData() {

	}

	@Override
	public void initData(List<Object> data) {
		// 在这个 Fragment中传递的值的协议是 WorkOrder和 PDAWorkOrderInfoConfig
		if (data.get(0) instanceof WorkOrder) {
			mWorkOrder = (WorkOrder) data.get(0);
		}
	}

	private void saveHoistingWork() {
		if (!checkEdIsEmpty()) {

			try {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(WorkOrder.class.getName(), mWorkOrder);
				map.put(PDAWorkOrderInfoConfig.class.getName(),
						(PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
								.getNaviCurrentEntity());

				map.put(HoistingWork.class.getName(), hoistingWork);
				asyTask.execute(IActionType.ACTION_TYPE_HOISTING, map);
			} catch (HDException e) {
				logger.error(e);
				ToastUtils.imgToast(mActivity,
						R.drawable.hd_hse_common_msg_wrong, e.getMessage());
			}

		}

	}

	/**
	 * 检查编辑框是否为空
	 */
	private boolean checkEdIsEmpty() {
		String hwName = edHwName.getText().toString().trim();
		if (null == hwName || "".equals(hwName)) {
			ToastUtils.toast(mActivity, "吊装货物名称不能为空！");
			return true;
		}
		String hwGg = edHwGg.getText().toString().trim();
		if (null == hwGg || "".equals(hwGg)) {
			ToastUtils.toast(mActivity, "货物规格不能为空！");
			return true;
		}

		String hwZl = edHwZl.getText().toString().trim();
		if (null == hwZl || "".equals(hwZl)) {
			ToastUtils.toast(mActivity, "货物质量不能为空！");
			return true;
		}

		String dzZl = edDzZl.getText().toString().trim();
		if (null == dzZl || "".equals(dzZl)) {
			ToastUtils.toast(mActivity, "吊装质量不能为空！");
			return true;
		}

		String dzBj = edDzBj.getText().toString().trim();
		if (null == dzBj || "".equals(dzBj)) {
			ToastUtils.toast(mActivity, "吊装半径不能为空！");
			return true;
		}

		String dzJd = edDzJd.getText().toString().trim();
		if (null == dzJd || "".equals(dzJd)) {
			ToastUtils.toast(mActivity, "吊装角度不能为空！");
			return true;
		}

		String edZh = edEdZh.getText().toString().trim();
		if (null == edZh || "".equals(edZh)) {
			ToastUtils.toast(mActivity, "额定载荷不能为空！");
			return true;
		}
		hoistingWork.setDzwjname(hwName);
		hoistingWork.setWjsize(hwGg);
		hoistingWork.setWjzl(hwZl);
		hoistingWork.setDzzl(dzZl);
		hoistingWork.setDzgzbj(dzBj);
		hoistingWork.setDzgd(dzJd);
		hoistingWork.setQzjedzh(edZh);

		return false;

	}

}
