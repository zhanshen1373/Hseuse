package com.hd.hse.common.module.phone.ui.module.measurefragment;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.component.phone.adapter.BaseStepCheckAdapter;
import com.hd.hse.common.component.phone.adapter.StepCheckPartAdapter;

/**
 * ClassName: MeasureFragmentStepCheckPart (非逐条)<br/>
 * date: 2015年1月26日 <br/>
 * 
 * @author lxf
 * @version 正常刷卡（非逐条 批量） 描述+结果
 */
public class MeasureFragmentStepCheckPart extends MeasureFragmentBase {

	/**
	 * showList:TODO(控制显示列).
	 */
	private List<String> showList;

	// /**
	// * baseAdapter:TODO(适配器).
	// */
	// @SuppressWarnings("rawtypes")
	// private BaseStepCheckAdapter baseAdapter;
	//
	// @Override
	// public BaseStepCheckAdapter getBaseAdapter() {
	// // TODO Auto-generated method stub
	// if (null == baseAdapter && listDataSoruce!=null) {
	// baseAdapter = new StepCheckPartAdapter(getActivity(),null,listDataSoruce,
	// showList, "isselect");
	// }
	// return baseAdapter;
	// }

	@Override
	public List<String> getShowList() {
		if (null == showList) {
			// 设置措施显示内容
			showList = new ArrayList<String>();
			if (isHaveTitle()) {
				showList.add("description");//标题
			}
			showList.add("description");// 描述
			showList.add("checkresult");// 状态
		}
		return showList;
	}

	@Override
	public boolean getAllData() {
		// TODO Auto-generated method stub
		return true;
	}
}
