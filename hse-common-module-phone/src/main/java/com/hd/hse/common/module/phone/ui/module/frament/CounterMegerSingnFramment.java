package com.hd.hse.common.module.phone.ui.module.frament;

import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.entity.workorder.WorkOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CounterMegerSingnFramment (合并会签)<br/>
 * date: 2015年8月28日  <br/>
 *
 * @author lxf
 * @version
 */
public class CounterMegerSingnFramment extends CounterSignFramment {
	/**
	 * mlistWorkOrder:TODO(合并审批的数据源).
	 */
	//private List<WorkOrder> mlistWorkOrder;

	@SuppressWarnings("unchecked")
	@Override
	public void initData(List<Object> data) {
		if (null != data && data.size() > 1) {
			// 获取数据
			workAPLst = (List<SuperEntity>) data.get(0);
			workOrders = (List<WorkOrder>) data.get(1);
		} else {
			workOrders = (List<WorkOrder>) data.get(0);
			workAPLst = new ArrayList<SuperEntity>();
		}
	}
	@Override
	public Intent getNextIntent(Object obj){
		// 当前点击所在环节点
		currentWPP = (SuperEntity) obj;

		Intent intent = new Intent();
		if (currentWPP.getAttribute("isdzqm") != null
				&& currentWPP.getAttribute("isdzqm").equals("1")) {
			// 图片签名
			intent.setClass(getActivity(), PaintSignatureActivity.class);
		} else {
			// 开启刷卡
			intent.setClass(getActivity(), ReadCardExamineActivity.class);
//			intent.setClass(getActivity(), PaintSignatureActivity.class);
		}

		Bundle bundle = new Bundle();
		bundle.putSerializable(ReadCardExamineActivity.WORKAPPROVALPERMISSION,
				currentWPP);
		bundle.putSerializable(ReadCardExamineActivity.WORKORDER, (Serializable) workOrders);
		intent.putExtras(bundle);
		return intent;
	}
	@Override
	public Map<String, Object> getMapParamInfo(WorkOrder mmWorkOrder) {
		// TODO Auto-generated method stub
		Map<String, Object> mapParam = new HashMap<String, Object>();
		if (workOrders != null) {
			mapParam.put(WorkOrder.class.getName(), workOrders);
		}
		return mapParam;
	}
	@Override
	public String getActionType() {
		// TODO Auto-generated method stub
		return  IActionType.ACTION_TYPE_MERGE_SIGN;
	}
}
