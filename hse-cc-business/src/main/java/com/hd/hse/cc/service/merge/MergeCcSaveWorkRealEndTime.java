package com.hd.hse.cc.service.merge;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 合并审批，保存作业票关闭时间 ClassName: MergeCcSaveWorkRealEndTime ()<br/>
 * date: 2015年9月14日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCcSaveWorkRealEndTime extends AbstractCheckListener {

	/**
	 * @param action
	 * @param object
	 * @pdOid 9554a5f9-810f-4f6a-bf1c-23edf02c9e4b
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		WorkOrder cloneWorkOrder = null;
		Map<String, Object> mapParas = objectCast(object[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		// 此处要判断环节点是否是最终刷卡人，如果是校验气体检测时效判断
		String strid = approvalObject.getStrisend();
		if (strid != null && strid.length() > 0) {
			String[] strisend = strid.split(",");
			String[] strzysqid = approvalObject.getStrzysqid().split(",");
			if (strisend.length != strzysqid.length) {
				throw new AppException("读取数据库，组织数据异常,请联系管理员");
			}
			int i = 0;
			for (String isend : strisend) {
				if (isend != null && isend.equalsIgnoreCase("1")) {
					// 此时需要校验
					if (mapParas.containsKey(WorkOrder.class.getName())) {
						Object obj = mapParas.get(WorkOrder.class.getName());
						// 验证集合
						if (obj instanceof List) {
							@SuppressWarnings("unchecked")
							List<SuperEntity> listSuper = (List<SuperEntity>) obj;
							for (SuperEntity superEntity : listSuper) {
								//合并审批的时候strzysqid[i]有‘’
								if (("'"+superEntity
										.getAttribute(
												superEntity.getPrimaryKey())
										.toString()+"'")
										.equalsIgnoreCase(strzysqid[i])) {
									try {
										cloneWorkOrder = (WorkOrder) superEntity
												.clone();
										setWorkAttributes(action,
												cloneWorkOrder);
										saveWorkRealEndTime(cloneWorkOrder);
									} catch (CloneNotSupportedException e) {
										logger.error(e.getMessage(), e);
										throw new HDException("系统错误，请联系管理员！");
									}

								}
							}
						}
					}
				}
				i++;
			}
		}

		return super.action(action, object);
	}

	private void saveWorkRealEndTime(WorkOrder workOrder) throws HDException {
		try {
			dao.updateEntity(connection, workOrder,
					new String[] { "sjendtime" });
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的实际结束时间字段的值失败！");
		}
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkRealEndTime#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder,
	 *      com.hd.hse.common.entity.SuperEntity)
	 */
	public void setWorkAttributes(String action, WorkOrder workOrder)
			throws HDException {
		if (IActionType.ACTION_TYPE_MERGECLOSE.equalsIgnoreCase(action)) {
			// 关闭--大港有特殊业务（欲留）
			RelationTableName relation = getRelationTableName(IRelativeEncoding.HCSJPZ);
			int hcsj = 0;
			if (relation != null
					&& !StringUtils.isEmpty(relation.getInput_value())
					&& NumberUtils.isNumber(relation.getInput_value())) {
				hcsj = Integer.valueOf(relation.getInput_value());
			}
			String now = UtilTools.getSysCurrentTime();
			String endtime = "";
			if (!UtilTools.dataTimeCompare(now, workOrder.getSjendtime())) {
				// 当前时间<作业票实际结束时间
				endtime = now;
			} else if (UtilTools.dataTimeCompare(now, workOrder.getSjendtime())
					&& !UtilTools.dataTimeCompare(now, UtilTools.toAddMinutes(
							workOrder.getSjendtime(), hcsj))) {
				// 实际结束时间<当前时间<=作业票实际结束时间+缓冲时间
				endtime = workOrder.getSjendtime();
			} else {
				// 当前时间>作业票实际结束时间+缓冲时间
				endtime = now;
				workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_CQCLOSE);
			}
			workOrder.setSjendtime(endtime);
		}
	}
}
