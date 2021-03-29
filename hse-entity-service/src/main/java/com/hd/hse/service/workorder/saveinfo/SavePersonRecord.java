/***********************************************************************
 * Module:  SavePersonRecord.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SavePersonRecord
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更新人员审批记录表中的标记（‘tablename’,
 * 'tableid'）主要用于最终审核人更新本次审批记录信息中的审批所属记录（主要有，复查多次操作或者气体检测多次操作）
 * 
 * @pdOid 7ca409a5-f420-4db4-a8c3-68350b324f28
 */
public class SavePersonRecord extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid fdb31edc-606a-4f3d-8dcf-eaa97e80f43f
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		Object personRecordObject = UtilTools.judgeMapValue(map,
				WorkApprovalPersonRecord.class, true);
//		Object approvalObject = UtilTools.judgeMapValue(map,
//				WorkApprovalPermission.class, false);
		
		Map<String, Object> cloneMap = objectCast(object[1]);//
		WorkApprovalPermission cloneApproval = (WorkApprovalPermission)UtilTools.judgeMapValue(cloneMap, WorkApprovalPermission.class, false);
		
		savePersonRecord((WorkOrder) workObject,
				(WorkApprovalPersonRecord) personRecordObject,
				cloneApproval);
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	/**
	 * savePersonRecord:(审批信息更新). <br/>
	 * date: 2014年10月22日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workOrder
	 *            作业票
	 * @param personRecord
	 *            审批记录信息（的设置位置编码，对应的数据库表格和对应的数据中的主键）
	 * @throws HDException
	 */
	private void savePersonRecord(WorkOrder workOrder,
			WorkApprovalPersonRecord personRecord,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if(approvalPermission!=null){
				StringBuilder sbSql = new StringBuilder();
				sbSql.append("update ud_zyxk_zyspryjl set tablename='")
						.append(personRecord.getTablename())
						.append("',tableid='")
						.append(personRecord.getTableid())
						.append("' ");
				sbSql.append(" where ud_zyxk_zyspryjlid='")
						.append(approvalPermission.getUd_zyxk_zyspryjlid()).append("' ;");
				dao.executeUpdate(connection, sbSql.toString());
			}
//			if (approvalPermission == null
//					|| approvalPermission.getIsend() == 1) {
//				StringBuilder sbSql = new StringBuilder();
//				sbSql.append("update ud_zyxk_zyspryjl set tablename='")
//						.append(personRecord.getTablename())
//						.append("',tableid='")
//						.append(personRecord.getTableid())
//						.append("' ,temptableid='")
//						.append(personRecord.getTemptableid()).append("' ");
//				sbSql.append(" where ud_zyxk_zysqid='")
//						.append(workOrder.getUd_zyxk_zysqid()).append("' ");
//				sbSql.append(" and dycode='").append(personRecord.getDycode())
//						.append("' ");
//				sbSql.append(
//						" and ifnull(isupload,'0')='0' and ifnull(tableid,'')='' ;");
//				dao.executeUpdate(connection, sbSql.toString());
//			} else {
//				StringBuilder sbSql = new StringBuilder();
//				sbSql.append("update ud_zyxk_zyspryjl set tablename='")
//						.append(personRecord.getTablename())
//						.append("',temptableid='")
//						.append(personRecord.getTemptableid()).append("' ");
//				sbSql.append(" where ud_zyxk_zysqid='")
//						.append(workOrder.getUd_zyxk_zysqid()).append("' ");
//				sbSql.append(" and dycode='").append(personRecord.getDycode())
//						.append("' ");
//				sbSql.append(" and ifnull(isupload,'0')='0' and ifnull(tablename,'')='' and ifnull(tableid,'')='' ;");
//				dao.executeUpdate(connection, sbSql.toString());
//			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("更新审批信息失败！", e);
		}
	}

}