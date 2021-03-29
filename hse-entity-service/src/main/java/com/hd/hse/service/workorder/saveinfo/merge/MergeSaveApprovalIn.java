package com.hd.hse.service.workorder.saveinfo.merge;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: MergeSaveApprovalIn (保存审批信息，)<br/>
 * date: 2015年9月6日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeSaveApprovalIn extends AbstractCheckListener {
	@SuppressWarnings("unused")
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		WorkApprovalPermission cloneApproval = null;// 克隆对象
		//
		Map<String, Object> map = objectCast(object[0]);
		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
				.judgeMapValue(map, WorkApprovalPermission.class, false);
		// Object objWorkOrder = UtilTools.judgeMapValue(map,WorkOrder.class,
		// false);

		String[] zysqids = new String[] {};
		if (approval.getStrzysqid() != null) {
			zysqids = approval.getStrzysqid().split(",");
		}
		String[] zyspryjlids = new String[zysqids.length];
		if (approval.getStrzyspryjlid() != null) {
			zyspryjlids = approval.getStrzyspryjlid().split(",");
		}
		String[] zyspqxfields = approval.getStrzyspqxfiled().split(",");

		Object objWorkOrder = null;
		// 此时需要校验
		if (map.containsKey(WorkOrder.class.getName())) {
			objWorkOrder = map.get(WorkOrder.class.getName());
		}
		if (approval == null)
			return null;
		PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
				.judgeMapValue(map, PDAWorkOrderInfoConfig.class, false);
		if (workConfig != null && workConfig.getConlevel() != null
				&& workConfig.getConlevel() == 1)
			return null;// 逐条非批量不保存审批记录

		// 克隆
		try {
			// 在读数据库的地方用用到克隆的数据
			Map<String, Object> cloneMap = objectCast(object[1]);//
			cloneApproval = (WorkApprovalPermission) approval.clone();
			cloneMap.put(WorkApprovalPermission.class.getName(), cloneApproval);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统错误，请联系管理员！");
		}
		int index = 0;
		cloneApproval.setModified_spryjlid(""); // 清空被修改的审批记录ID的记录
		cloneApproval.setStrzyspryjlid("");
		// 存在同意票中--同岗位的而合并，因此保存时需要用环节点上的循环来保存
		for (String strid : zysqids) {
			// 循环票中id相等---存在多个票时
			if (objWorkOrder instanceof List) {
				List<WorkOrder> listworkorder = (List<WorkOrder>) objWorkOrder;
				WorkApprovalPersonRecord personRecord;
				String zyspryjlid = null;
				for (WorkOrder workOrder : listworkorder) {
					if (!strid
							.equals("'" + workOrder.getUd_zyxk_zysqid() + "'")) {
						// 表示不是当前票
						continue;
					}
					zyspryjlid = zyspryjlids[index];
					personRecord = new WorkApprovalPersonRecord();
					if (zyspryjlid != null && !zyspryjlid.equals("")
							&& !zyspryjlid.equals("null")) {
						// 该环节点已签过
						if (approval.getBpermulcard() == 1) {
							// 该环节点多人刷卡
							personRecord.setUd_zyxk_zyspryjlid(zyspryjlid);
						} else {
							// 表示单人刷卡
							/***** 保存一个空的数据用来判断这个记录没有修改 *******/
							if (cloneApproval.getModified_spryjlid() != null
									&& !cloneApproval.getModified_spryjlid()
											.equals("")) {
								cloneApproval
										.setModified_spryjlid(cloneApproval
												.getModified_spryjlid()
												+ ",null");
							} else {
								cloneApproval.setModified_spryjlid("null");
							}
							/***********************/
							//重置审批记录id
							if (cloneApproval.getStrzyspryjlid() != null
									&& !cloneApproval.getStrzyspryjlid().equals("")) {
								cloneApproval.setStrzyspryjlid(cloneApproval
										.getStrzyspryjlid()
										+ ","
										+ zyspryjlid);
							} else {
								cloneApproval.setStrzyspryjlid(zyspryjlid);
							}
							continue;
						}
					}

					if (workConfig != null) {
						personRecord.setType(workConfig.getPscode());
						// personRecord.setSpnode(approval.getSpfield());
						personRecord.setSpnode(zyspqxfields[index]);
						personRecord.setValue(approval.getSpfield_desc());
						personRecord.setSptime(UtilTools.getSysCurrentTime());
						personRecord.setPerson(approval.getPersonid());
						personRecord.setPerson_name(approval.getPersondesc());
						personRecord.setDycode(approval.getZylocation());
						personRecord.setUd_zyxk_zysqid(workOrder
								.getUd_zyxk_zysqid());
						cloneApproval.setSptime(personRecord.getSptime());
						cloneApproval
								.setDefaultpersonid(approval.getPersonid());
						cloneApproval.setDefaultpersondesc(approval
								.getPersondesc());
						saveApprovalIn(personRecord, null);
						cloneApproval.setUd_zyxk_zyspryjlid(personRecord
								.getUd_zyxk_zyspryjlid());
						// 表示记录下每次的审批记录
						//zyspryjlid = personRecord.getUd_zyxk_zyspryjlid();

						if (cloneApproval.getStrzyspryjlid() != null
								&& !cloneApproval.getStrzyspryjlid().equals("")) {
							cloneApproval.setStrzyspryjlid(cloneApproval
									.getStrzyspryjlid()
									+ ","
									+ personRecord.getUd_zyxk_zyspryjlid());
						} else {
							cloneApproval.setStrzyspryjlid(personRecord
									.getUd_zyxk_zyspryjlid());
						}
						// if (cloneApproval.getStrzyspryjlid() != null
						// && !cloneApproval.getStrzyspryjlid().equals("")) {
						// cloneApproval.setStrzyspryjlid(cloneApproval
						// .getStrzyspryjlid()
						// + ","
						// + personRecord.getUd_zyxk_zyspryjlid());
						// } else {
						// cloneApproval.setStrzyspryjlid(personRecord
						// .getUd_zyxk_zyspryjlid());
						// }
						if (cloneApproval.getModified_spryjlid() != null
								&& !cloneApproval.getModified_spryjlid()
										.equals("")) {
							cloneApproval.setModified_spryjlid(cloneApproval
									.getModified_spryjlid()
									+ ","
									+ personRecord.getUd_zyxk_zyspryjlid());
						} else {
							cloneApproval.setModified_spryjlid(personRecord
									.getUd_zyxk_zyspryjlid());
						}
						// if (cloneApproval.getStrzysqid() != null
						// && !cloneApproval.getStrzysqid().equals("")) {
						// cloneApproval.setStrzysqid(cloneApproval
						// .getStrzysqid()
						// + ",'"
						// + workOrder.getUd_zyxk_zysqid() + "'");
						// } else {
						// cloneApproval.setStrzysqid("'"
						// + workOrder.getUd_zyxk_zysqid() + "'");
						// }
					}
				}
			}
			index++;
		}
//		approval.setDefaultpersonid("");
//		approval.setDefaultpersondesc("");
		cloneApproval.setDefaultpersonid("");
		cloneApproval.setDefaultpersondesc("");
		return null;
	}

	/**
	 * saveApprovalIn:(保存审批记录). <br/>
	 * date: 2015年9月6日 <br/>
	 * 
	 * @author lxf
	 * @param personRecord
	 * @param cloneApprovalPermission
	 * @throws HDException
	 */
	private void saveApprovalIn(WorkApprovalPersonRecord personRecord,
			WorkApprovalPermission cloneApprovalPermission) throws HDException {
		try {
			if (!StringUtils.isEmpty(personRecord.getUd_zyxk_zyspryjlid())) {
				// 走更新
				StringBuilder sql = new StringBuilder();
				sql.append("update ud_zyxk_zyspryjl set person = (");
				sql.append("case person when '' then '");
				sql.append(personRecord.getPerson());
				sql.append("' when null then '");
				sql.append(personRecord.getPerson());
				sql.append("' else person||','||'");
				sql.append(personRecord.getPerson());
				sql.append("' end), person_name = (case person_name when '' then '");
				sql.append(personRecord.getPerson_name());
				sql.append("' when null then '");
				sql.append(personRecord.getPerson_name());
				sql.append("' else person_name||','||'");
				sql.append(personRecord.getPerson_name());
				sql.append("' end), sptime = '");
				sql.append(personRecord.getSptime());
				sql.append("' where ud_zyxk_zyspryjlid = '");
				sql.append(personRecord.getUd_zyxk_zyspryjlid());
				sql.append("';");
//				dao.updateEntity(connection, personRecord, new String[] {
//						"person_name", "person", "sptime" });
				dao.executeUpdate(connection, sql.toString());
			} else {
				// 走新增
				SequenceGenerator
						.genPrimaryKeyValue(new SuperEntity[] { personRecord });
				// cloneApprovalPermission.setUd_zyxk_zyspryjlid(personRecord
				// .getUd_zyxk_zyspryjlid());
				dao.insertEntity(connection, personRecord);
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("审批信息保存失败！", e);
		}
	}
}
