/**
 * Project Name:hse-osc-business
 * File Name:CheckHazardInfo.java
 * Package Name:com.hd.hse.osc.service.checkrules
 * Date:2015年3月9日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.osc.service.checkrules;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName:CheckHazardInfo (校验危害信息是否满足条件).<br/>
 * Date: 2015年3月9日 <br/>
 * 
 * @author zhaofeng
 * @version
 * @see
 */
public class CheckHazardInfo extends AbstractCheckListener {

	public CheckHazardInfo() {
		// TODO Auto-generated constructor stub

	}

	public CheckHazardInfo(IConnection connection) {
		super(connection);
		// TODO Auto-generated constructor stub

	}

	/**
	 * TODO 继承自父类
	 * 
	 * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
	 *      Object[])
	 */
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub

		Map<String, Object> mapParas = objectCast(args[0]);
		List<HazardNotify> hazardList = (List<HazardNotify>) UtilTools
				.judgeMapListValue(mapParas, HazardNotify.class, true);
		checkHazardInfo(hazardList);
		return super.action(action, args);
	}

	/**
	 * checkHazardInfo:(检验危害中的已经被选择的项中，必填项是否已经填写). <br/>
	 * date: 2015年3月9日 <br/>
	 * 
	 * @author zhaofeng
	 * @param hazardList
	 * @throws HDException
	 */
	private void checkHazardInfo(List<HazardNotify> hazardList)
			throws HDException {
		String desc = "";
		for (int i = 0; i < hazardList.size(); i++) {
			desc = hazardList.get(i).getDescription();
			if (desc == null)
				continue;
			if (((hazardList.get(i).getIsselect() != null && hazardList.get(i)
					.getIsselect() == 1) || (hazardList.get(i).getIspadselect() != null && hazardList
					.get(i).getIspadselect() == 1))
					&& (desc.replace(" ", "").contains("()") || desc.replace(
							" ", "").contains("（）"))) {
				throw new HDException(desc + "为必填项，未填写，不能保存！");
			}
		}
	}

}
