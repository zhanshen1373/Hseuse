/**
 * Project Name:hse-entity-service
 * File Name:CheckMeasureReview.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年11月4日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.workorder.checkrules;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.utils.UtilTools;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CheckMeasureReview (措施项不能为否+措施项必须确认。措施复查、延期调用)<br/>
 * date: 2014年11月4日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class CheckMeasureReview extends AbstractCheckListener {

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> mapParas = objectCast(args[0]);
		PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
				.judgeMapValue(mapParas, PDAWorkOrderInfoConfig.class, true);
		List<MeasureReviewSub> measureList = (List<MeasureReviewSub>) UtilTools
				.judgeMapListValue(mapParas, MeasureReviewSub.class, true);
		// lxf 添加 获取当前环节点对象
		Object approvalObject = UtilTools.judgeMapValue(mapParas,
				WorkApprovalPermission.class, false);
		checkMeasureReview(workConfig, measureList,approvalObject == null ? null
				: (WorkApprovalPermission) approvalObject);// 非逐条校验
		return super.action(action, args);
	}

	/**
	 * checkMeasureReview:(措施复查校验). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workConfig
	 * @param measureList
	 * @throws HDException
	 */
	private void checkMeasureReview(PDAWorkOrderInfoConfig workConfig,
			List<MeasureReviewSub> measureList, WorkApprovalPermission approval)
			throws HDException {
		boolean isOperate = false;
		String desc = "";
		for (int i = 0; i < measureList.size(); i++) {
			desc = measureList.get(i).getDescription();
			if (measureList.get(i).getCheckresult() == null
					|| measureList.get(i).getCheckresult() == -1) {
				throw new HDException(measureList.get(i).getDescription()
						+ "没有操作，不能保存！");
			} else if (measureList.get(i).getCheckresult() == 0) {
				throw new HDException(measureList.get(i).getDescription()
						+ "结果为否，不能保存！");
			}
			if (measureList.get(i).getCheckresult() == 1
					&& (desc.replace(" ", "").contains("()") || desc.replace(
							" ", "").contains("（）"))) {
				throw new HDException(measureList.get(i).getDescription()
						+ "为必填项，未填写，不能保存！");
			}// 测试-分支项目
				// 源项目-测试
				// if (workConfig.getConlevel() != null
			// && workConfig.getConlevel() == 1)
			// isOperate = true;// 逐条
			// if (!isOperate) {
			// if (measureList.get(i).getCheckresult() == 1
			// || measureList.get(i).getCheckresult() == 0) {
			// isOperate = true;
			// }
			// }
			if (measureList.get(i).getCheckresult() == 1
					|| measureList.get(i).getCheckresult() == 0) {
				isOperate = true;
			}
			// lxf 添加 非逐条、isend时判断措施中是否包含否的，如果包含，不准许通过
			if (measureList.get(i).getCheckresult() == 0 && approval != null
					&& approval.getIsend() == 1
					&& workConfig.getConlevel() != null
					&& workConfig.getConlevel() != 1) {
				throw new HDException(measureList.get(i).getDescription()
						+ "  不能为“否”");
			}
		}
		if (workConfig.getConlevel() != null && workConfig.getConlevel() == 1) {
			isOperate = true;
		}
		if (!isOperate) {
			throw new HDException("至少有一项为“是”或者“否”！");
		}
	}

}
