/**
 * Project Name:hse-entity-service
 * File Name:CheckCanApprove.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年10月24日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.service.workorder.checkrules;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName:CheckCanApprove (判断是否可以审核，主要判断是否已刷卡).<br/>
 * Date: 2014年10月24日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class CheckCanApprove extends AbstractCheckListener {

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> mapParam = objectCast(args[0]);
		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParam, WorkApprovalPermission.class, false);
		// 必须签字
		if (approval != null && approval.getIsmust() == 1
				&& StringUtils.isEmpty(approval.getPersondesc())) {
			throw new HDException("必须签字后再进行审核！");
		}
		return super.action(action, args);
	}

}
