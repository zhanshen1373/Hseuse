/***********************************************************************
 * Module:  SaveDelayIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveDelayIn
 ***********************************************************************/

package com.hd.hse.dly.service.saveinfo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 保存延期信息。。。
 * 
 * @pdOid c3827072-e93c-4db3-bc11-b9ed50e7e9ba
 */
public class SaveDelayIn extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid 022d9c85-f249-4ffd-a160-7bce795daacd
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object delayObject = UtilTools
				.judgeMapValue(map, WorkDelay.class, true);
		Object reviewObject = UtilTools.judgeMapValue(map,
				WorkMeasureReview.class, true);
		WorkApprovalPersonRecord record = (WorkApprovalPersonRecord) UtilTools
				.judgeMapValue(map, WorkApprovalPersonRecord.class, false);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveDelayIn((WorkDelay) delayObject, (WorkMeasureReview) reviewObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		if(record == null){
			record = new WorkApprovalPersonRecord();
			map.put(WorkApprovalPersonRecord.class.getName(), record);
		}
		record.setTablename(((WorkDelay) delayObject).getDBTableName().toUpperCase());
		record.setTableid(((WorkDelay) delayObject).getUd_zyxk_zyyqid());// 在接下来的保存中使用
		return null;
	}

	/**
	 * saveDelayIn:(保存延期信息). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workDelay
	 * @param workMeasureReview
	 * @param approvalPermission
	 * @throws HDException
	 */
	private void saveDelayIn(WorkDelay workDelay,
			WorkMeasureReview workMeasureReview,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (StringUtils.isEmpty(workDelay.getUd_zyxk_zyyqid())) {
				workDelay.setZycsfcnum(workMeasureReview.getZycsfcnum());
				SequenceGenerator
						.genPrimaryKeyValue(new SuperEntity[] { workDelay });
				dao.insertEntity(connection, workDelay);
			} else {
				dao.updateEntity(connection, workDelay, new String[] {
						"yqendtime", "yqstarttime" });
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("延期信息保存失败！", e);
		}
	}

}