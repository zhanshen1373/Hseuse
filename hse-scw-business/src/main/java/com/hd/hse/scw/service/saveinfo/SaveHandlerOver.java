/**
 * Project Name:hse-scw-app
 * File Name:SaveHandlerOver.java
 * Package Name:com.hd.hse.scw.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.scw.service.saveinfo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: SaveHandlerOver ()<br/>
 * date: 2014年11月28日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class SaveHandlerOver extends AbstractCheckListener {
	
	@Override
	public Object action(String action, Object... object) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> map = objectCast(object[0]);
		WorkMeasureReview measureObject = (WorkMeasureReview) UtilTools
				.judgeMapValue(map, WorkMeasureReview.class, true);
		Object personRecordObject = UtilTools.judgeMapValue(map,
				WorkApprovalPersonRecord.class, true);
		WorkMeasureReview clonMeasureReview = null;
		Map<String, Object> cloneMap = objectCast(object[1]);
		try {
			clonMeasureReview = (WorkMeasureReview) measureObject.clone();
			clonMeasureReview.setCsnum(0);
			cloneMap.put(WorkMeasureReview.class.getName(),
					clonMeasureReview);
			saveJJBReviewIn(clonMeasureReview,(WorkApprovalPersonRecord)personRecordObject);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统出错，请联系管理员！", e);
		}
		return super.action(action, object);
	}
	
	/**
	 * saveJJBReviewIn:(交接班信息保存). <br/>
	 * date: 2014年11月7日 <br/>
	 * 
	 * @author zhaofeng
	 * @param measureReview
	 * @throws HDException
	 */
	private void saveJJBReviewIn(WorkMeasureReview measureReview,WorkApprovalPersonRecord personRecordObject)
			throws HDException {
		if (StringUtils.isEmpty(measureReview.getZycsfcnum())) {
			// 新增
			try {
				SequenceGenerator
						.genPrimaryKeyValue(new SuperEntity[] { measureReview });
				dao.insertEntity(connection, measureReview);
				personRecordObject.setTableid(measureReview.getZycsfcnum());
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new HDException("保存数据失败！", e);
			}
		} else {
			// 不操作
		}
	}
}
