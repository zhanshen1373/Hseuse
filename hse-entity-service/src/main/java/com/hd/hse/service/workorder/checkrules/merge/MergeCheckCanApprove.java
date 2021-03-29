package com.hd.hse.service.workorder.checkrules.merge;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 合并会签
 * ClassName: MergeCheckCanApprove (合并会签审核环节点的校验)<br/>
 * date: 2015年9月6日  <br/>
 *
 * @author lxf
 * @version 
 */
public class MergeCheckCanApprove extends AbstractCheckListener {
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
