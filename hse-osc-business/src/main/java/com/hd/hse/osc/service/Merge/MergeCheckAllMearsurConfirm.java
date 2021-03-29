package com.hd.hse.osc.service.Merge;

import java.util.List;
import java.util.Map;

import android.R.integer;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: CheckAllMearsurConfirm (检查作业票下所有的措施是否确认)<br/>
 * date: 2015年9月2日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCheckAllMearsurConfirm extends AbstractCheckListener {

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> mapParas = objectCast(args[0]);
		// 此时需要校验
		if (mapParas.containsKey(WorkOrder.class.getName())) {
			Object obj = mapParas.get(WorkOrder.class.getName());
			// 验证集合
			if (obj instanceof List) {
				List<WorkOrder> listSuper = (List<WorkOrder>) obj;
				checkMeasureResult(listSuper);
			}
		}
		return null;
	}

	//
	// /**
	// * checkMeasureResult:(验证有票的措施确认结果). <br/>
	// * date: 2015年9月2日 <br/>
	// *
	// * @author lxf
	// * @param workConfig
	// * @param measureList
	// * @param approval
	// * @throws HDException
	// */
	// private void checkMeasureResult(List<WorkOrder> listworkorder)
	// throws HDException {
	// StringBuilder sbid = new StringBuilder();
	// for (WorkOrder workOrder : listworkorder) {
	// if (sbid.length() > 0) {
	// sbid.append(",");
	// }
	// sbid.append("'").append(workOrder.getUd_zyxk_zysqid()).append("'");
	// }
	// StringBuilder sbsql = new StringBuilder();
	// sbsql.append(
	// "select ud_zyxk_zysqid from ud_zyxk_zysq2precaution where ifnull(checkresult,-1)=-1 and ud_zyxk_zysqid in (")
	// .append(sbid.toString()).append(")  limit 1;");
	// Map<String, Object> mapResult = getMapResult(sbsql.toString());
	// if (mapResult != null && mapResult.size() > 0)
	// throw new HDException("合并审批中存在措施没有落实,请先落实措施");
	//
	// }

	// 作业票查询服务
	// private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();

	public void checkMeasureResult(List<WorkOrder> listworkorder)
			throws HDException {
		List<Map<String, Object>> listMap;
		Map<String,Object> tempMap;
		Integer conType;
		StringBuilder childSql = new StringBuilder();
		for (WorkOrder workOrder : listworkorder) {
			// 合并审批判断
			childSql.setLength(0);
			childSql.append("select sname,pscode,contype from ud_zyxk_zysqpdasc ");
			childSql.append(" where zypclass='")
					.append(workOrder.getAttribute("zypclass").toString())
					.append("' and pscode='").append(IConfigEncoding.SP)
					.append("' ");
			childSql.append(" and isactive=1 order by tab_order asc;");
			listMap = getListMapResult(childSql.toString());
			
			childSql.setLength(0);
			childSql.append("select cssavefied from ud_zyxk_zysq where ud_zyxk_zysqid='").append(workOrder.getAttribute("ud_zyxk_zysqid").toString()).append("';");
			tempMap=getMapResult(childSql.toString());
			workOrder.setAttribute("cssavefied", tempMap.get("cssavefied"));
			// 判断导航栏菜单
			if (listMap != null && listMap.size() > 0) {
				for (Map<String, Object> mapConfig : listMap) {
					conType = (Integer) mapConfig.get("contype");
					if (conType.equals(IConfigEncoding.HARM_TYPE)) {// 如果包含危害
						checkHazardIsFinished(workOrder);
					}
					if (conType.equals(IConfigEncoding.PPE_TYPE)) {
						checkPpeIsFinished(workOrder);
					}
					if (conType.equals(IConfigEncoding.MEASURE_TYPE)) {
						checkMeasureIsFinished(workOrder);
					}
				}
			}
		}
	}

	/**
	 * checkHazardIsFinished:(危害是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkHazardIsFinished(WorkOrder workorder) throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.HARM_TYPE))) {
			throw new AppException("危害未确认");
		}
	}

	/**
	 * checkPpeIsFinished:(个人防护装备是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkPpeIsFinished(WorkOrder workorder) throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.PPE_TYPE))) {
			throw new AppException("个人防护装备未确认");
		}
	}

	/**
	 * checkMeasureIsFinished:(措施是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkMeasureIsFinished(WorkOrder workorder)
			throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.MEASURE_TYPE))) {
			throw new AppException("措施未确认完成");
		}
	}

}
