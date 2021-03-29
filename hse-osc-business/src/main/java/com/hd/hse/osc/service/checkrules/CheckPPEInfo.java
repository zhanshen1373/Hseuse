/**
 * Project Name:hse-osc-business
 * File Name:CheckPPEInfo.java
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
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName:CheckPPEInfo (判断PPE是否满足条件).<br/>
 * Date: 2015年3月9日 <br/>
 * 
 * @author zhaofeng
 * @version
 * @see
 */
public class CheckPPEInfo extends AbstractCheckListener {

	public CheckPPEInfo() {
		// TODO Auto-generated constructor stub

	}

	public CheckPPEInfo(IConnection connection) {
		super(connection);
		// TODO Auto-generated constructor stub

	}

	/**
	 * TODO 继承
	 * 
	 * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
	 *      Object[])
	 */
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> mapParas = objectCast(args[0]);
		List<WorkGuardEquipment> ppeList = (List<WorkGuardEquipment>) UtilTools
				.judgeMapListValue(mapParas, WorkGuardEquipment.class, true);
		checkPPEInfo(ppeList);
		return super.action(action, args);
	}

	/**
	 * checkPPEInfo:(检验PPE被勾选的项中，必填项是否已经填写). <br/>
	 * date: 2015年3月9日 <br/>
	 * 
	 * @author zhaofeng
	 * @param ppeList
	 * @throws HDException
	 */
	private void checkPPEInfo(List<WorkGuardEquipment> ppeList)
			throws HDException {
		String desc = "";
		for (int i = 0; i < ppeList.size(); i++) {
			desc = ppeList.get(i).getDescription();
			if (desc == null)
				continue;
			if (((ppeList.get(i).getIsselect() != null && ppeList.get(i)
					.getIsselect() == 1) || (ppeList.get(i).getIspadselect() != null && ppeList
					.get(i).getIspadselect() == 1))
					&& (desc.replace(" ", "").contains("()") || desc.replace(
							" ", "").contains("（）"))) {
				throw new HDException(desc + "为必填项，未填写，不能保存！");
			}
		}
	}

}
