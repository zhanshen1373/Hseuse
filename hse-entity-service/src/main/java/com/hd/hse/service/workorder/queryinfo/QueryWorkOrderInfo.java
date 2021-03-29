package com.hd.hse.service.workorder.queryinfo;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.workorder.WorkOrder;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class QueryWorkOrderInfo {
	private BaseDao dao;
	private String[] ud_zyxk_zysqids;

	public QueryWorkOrderInfo() {
		dao = new BaseDao();
	}

	/**
	 * 得到要上传的workorder数据
	 */
	public List<WorkOrder> queryWorkOrder(String[] ud_zyxk_zysqids)
			throws HDException {
		this.ud_zyxk_zysqids = ud_zyxk_zysqids;
		if (ud_zyxk_zysqids==null) {
			return null;
		}
		String sql = buildQryWorkOrderSql();
		List<WorkOrder> lstWorkOrder = (List<WorkOrder>) dao.executeQuery(sql,
				new EntityListResult(WorkOrder.class));
		return lstWorkOrder;
	}

	protected String buildQryWorkOrderSql() throws HDException {
		StringBuilder table = new StringBuilder();
		table.append(" ud_zyxk_zysq zysq left outer join alndomain adm");
		table.append(" on zysq.zypclass = adm.value and adm.domainid='UDZYLX'");
		table.append(" left outer join synonymdomain sdm");
		table.append(" on zysq.status = sdm.value and sdm.domainid='UDZYSQSTATUS' ");
		return buildSql(table.toString(), getWorkOrderCols(),
				buildWorkOrderWhere());
	}

	/**
	 * getWorkOrderCols:(作业票列：开始时间、结束时间、作业票类型、作业票状态). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws HDException
	 */
	protected String[] getWorkOrderCols() throws HDException {
		return new String[] { "zysq.ud_zyxk_worktaskid", "zysq.ud_zyxk_zysqid",
				"zysq.zystarttime", "zysq.zyendtime",
				"zysq.sjendtime as sjendtime",
				"zysq.sjstarttime as sjstarttime",
				"adm.description as zypclassname",
				"sdm.description as statusname", "zysq.zypclass as zypclass",
				"zysq.status", "zysq.isqtjc as isqtjc",
				"zysq.dhzy_id as dhzy_id", "zysq.iscbs as iscbs",
				"zysq.sddept as sddept", "zysq.yyqcount as yyqcount",
				"zysq.dhzy_id as dhzy_id", "zysq.parent_id", "zysq.cssavefied",
				"zysq.zydept as zydept", "zysq.nlgldnum as nlgldnum",
				"zysq.whtype as whtype" };
	}

	/**
	 * buildWorkOrderWhere:(作业票查询条件). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws HDException
	 */
	protected String buildWorkOrderWhere() throws HDException {
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("(zysq.status='APPAUDITED' "
				+ "or (ifnull(zysq.spstatus,'') !='' "
				+ "and ifnull(isupload,0)=0 " + "and zysq.spstatus!='UPLOAD' "
				+ "and zysq.status!='INPRG' )"
				+ "or ifnull(ispause,0)=1 )" + "and ud_zyxk_zysqid in (");
				
		for (int i = 0; i < ud_zyxk_zysqids.length; i++) {
			if (i==0) {
				sbWhere.append("'"+ud_zyxk_zysqids[i]+"'");
			}else {
				sbWhere.append(",'"+ud_zyxk_zysqids[i]+"'");
			}
		}	
		sbWhere.append(")order by zysq.zystarttime desc");
		return sbWhere.toString();
	}

	/**
	 * buildSql:(构造sql). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author lg
	 * @param table
	 * @param cols
	 * @param where
	 * @return
	 * @throws HDException
	 */
	private String buildSql(String table, String[] cols, String where)
			throws HDException {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select ");
		for (String col : cols) {
			sbSql.append(col).append(",");
		}
		sbSql.setLength(sbSql.length() - 1);
		sbSql.append(" from ").append(table);
		if (!StringUtils.isEmpty(where)) {
			sbSql.append(" where ").append(where);
		}
		return sbSql.toString();
	}
}
