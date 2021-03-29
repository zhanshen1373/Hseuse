package com.hd.hse.osc.service.Merge;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: MergeSaveWorkRealStartTime (签发界面，最终审核人审核后，更改作业票的实际开始时间，)<br/>
 * date: 2015年9月6日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeSaveWorkRealStartTime extends AbstractCheckListener {
	public Object action(String action, Object... args) throws HDException {
		Map<String, Object> mapParas = objectCast(args[0]);
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
				if (isend != null
						&& (isend.equalsIgnoreCase("1") || isend
								.equalsIgnoreCase("'1'"))) {
					// 此时需要校验
					if (mapParas.containsKey(WorkOrder.class.getName())) {
						Object obj = mapParas.get(WorkOrder.class.getName());
						// 验证集合
						if (obj instanceof List) {
							@SuppressWarnings("unchecked")
							List<SuperEntity> listSuper = (List<SuperEntity>) obj;
							for (SuperEntity superEntity : listSuper) {
								String workId = superEntity.getAttribute(
										superEntity.getPrimaryKey()).toString();
								if (("'" + workId + "'")
										.equalsIgnoreCase(strzysqid[i])) {
									saveWorkRealStartTime((WorkOrder) superEntity);
								}
							}
						}
					}
				}
				i++;
			}
		}
		return null;
	}

	/**
	 * saveWorkRealStartTime:(). <br/>
	 * date: 2015年9月6日 <br/>
	 * 
	 * @author lxf
	 * @param workOrder
	 * @param approvalPermission
	 * @throws HDException
	 */
	private void saveWorkRealStartTime(WorkOrder workOrder) throws HDException {
		try {
			workOrder.setSjstarttime(UtilTools.getSysCurrentTime());
			dao.updateEntity(connection, workOrder, new String[]{"sjstarttime"});
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的实际开始时间字段的值失败！");
		}
	}
}
