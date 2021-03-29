package com.hd.hse.osc.phone.ui.fragment.measure;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;

import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.module.measurefragment.MeasureFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.osc.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

@SuppressLint("NewApi")
public class MeasureFragmentosc extends MeasureFragment {
	private static Logger logger = LogUtils.getLogger(MeasureFragmentosc.class);

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setBtnAppClickBefore(btnAppClickBefore);
		// refreshInfo(null, 1);
	}

	/**
	 * btnAppClickBefore:TODO(每次点击保存时，都判断是否满足保存事件的操作).
	 */
	private IEventListener btnAppClickBefore = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			// TODO Auto-generated method stub
			if (!IWorkOrderStatus.INPRG.equalsIgnoreCase(getWorkEntity()
					.getStatus())) {
				throw new HDException("非审批中的作业票，不允许数据变更！");
			}
		}
	};

	@Override
	public String getIAtionType() {
		// TODO Auto-generated method stub
		return IActionType.ACTION_TYPE_PRECAUTION;
	}

	@Override
	public String getMeasureClassName() {
		// TODO Auto-generated method stub
		return WorkApplyMeasure.class.getName();
	}

	@Override
	public Map<String, Object> getMapParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("NewApi")
	@Override
	public void refreshViewControl(Object... objects) {
		if (objects != null) {
			if (objects[0] == null) {
				((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
				updateValues();
			}
			if (objects[0] instanceof WorkApprovalPermission) {
				WorkApprovalPermission workapp = (WorkApprovalPermission) objects[0];
				if (workapp.getIsend() != null && workapp.getIsend() == 1) {
					((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
				}
			}
		}

	}

	@Override
	public String getChildKey() {
		// TODO Auto-generated method stub
		return WorkApplyMeasure.class.getName();
	}

	@Override
	public List<SuperEntity> getSaveDatalist() {

		return getOpSaveDatalist();
	}

	@Override
	public List<SuperEntity> getMeasureList(SuperEntity currentTouchEntity) {
		// TODO Auto-generated method stub
		try {
			return changeToSuperEntity(getQueryWorkInfo().queryMeasureInfo(
					getWorkEntity(), currentTouchEntity, null));
		} catch (HDException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			ToastUtils.toast(getActivity(), "读取措施信息报错,请联系管理员.");
		}
		return null;
	}

	int times = 0;
	boolean isRelation;
	@Override
	public boolean isRelation() {
		if (times == 0) {
			RelationTableName relationTableName = new RelationTableName();
			relationTableName.setSys_type(IRelativeEncoding.PCCSNOAPPLY);
			relationTableName.setSys_fuction(IConfigEncoding.SP);
			relationTableName.setSys_value(getWorkEntity() == null ? ""
					: getWorkEntity().getZypclass());
			IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
			isRelation = relativeConfig.isHadRelative(relationTableName);
			times++;
		}
		return isRelation;

	}
}
