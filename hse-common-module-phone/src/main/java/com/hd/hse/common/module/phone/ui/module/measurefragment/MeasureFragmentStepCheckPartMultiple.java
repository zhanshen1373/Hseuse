package com.hd.hse.common.module.phone.ui.module.measurefragment;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.component.phone.adapter.BaseStepCheckAdapter;

/**
 * ClassName: MeasureFragmentStepCheckPartMultiple (逐条批量)<br/>
 * date: 2015年1月26日  <br/>
 *
 * @author lxf
 * @version 
 */
public class MeasureFragmentStepCheckPartMultiple extends MeasureFragmentBase {

	/**
	 * showList:TODO(控制显示列).
	 */
	private List<String> showList;

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
			showList.add("persondesc");// 人员描述
		}
		return showList;
	}

	@Override
	public boolean getAllData() {
		// TODO Auto-generated method stub
		return false;
	}
}
