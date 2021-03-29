package com.hd.hse.scw.phone.ui.fragment.measure;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import android.annotation.SuppressLint;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.module.measurefragment.MeasureFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.workorder.WorkMeasureReview;

public class MeasureFragmentosr extends MeasureFragment {
	private WorkMeasureReview workMeasureReview;
	private static Logger logger = LogUtils.getLogger(MeasureFragmentosr.class);
	@SuppressLint("NewApi")
	@Override
	public List<SuperEntity> getMeasureList(SuperEntity currentTouchEntity) {
		List<SuperEntity> listMeasure=null;
		try {
			workMeasureReview = getQueryWorkInfo().queryReviewInfo(getWorkEntity(),
					currentTouchEntity, null);
			listMeasure = workMeasureReview
					.getChild(MeasureReviewSub.class.getName());
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.toast(getActivity(), "读取措施信息报错,请联系管理员.");
		}
		return listMeasure;
	}

	@Override
	public String getChildKey() {
		// TODO Auto-generated method stub
		return MeasureReviewSub.class.getName();
	}

	@Override
	public String getIAtionType() {
		// TODO Auto-generated method stub
		return IActionType.ACTION_TYPE_RECHECKPRECAUTION;
	}

	@Override
	public String getMeasureClassName() {
		// TODO Auto-generated method stub
		return MeasureReviewSub.class.getName();
	}

	@Override
	public List<SuperEntity> getSaveDatalist() {
		return getOpSaveDatalist();
	}

	@Override
	public Map<String, Object> getMapParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refreshViewControl(Object... objects) {
		// TODO Auto-generated method stub

	}

}
