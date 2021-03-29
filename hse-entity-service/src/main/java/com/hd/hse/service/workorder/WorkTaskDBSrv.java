/**
 * Project Name:hse-entity-service
 * File Name:WorkTaskDBSrv.java
 * Package Name:com.hd.hse.service.workorder
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.service.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import android.util.Log;

/**
 * ClassName:WorkTaskDBSrv (作业任务后台服务，数据库操作).<br/>
 * Date: 2014年9月23日 <br/>
 *
 * @author lg
 * @see
 */
public class WorkTaskDBSrv implements IWorkTaskSrv {

    /**
     * action:TODO().
     */
    private BaseDao dao = new BaseDao();

    private String condition;

    /**
     * TODO
     *
     * @see IWorkTaskSrv#loadWorkTaskList(String)
     */
    public List<SuperEntity> loadWorkTaskList(String condition)
            throws HDException {
        // Log.e("loadWorkTaskList", "loadWorkTaskList");
        // TODO Auto-generated method stub
        this.condition = StringEscapeUtils.escapeSql(condition);
        List<SuperEntity> lstWorkTask = queryWorkTask();
        if (lstWorkTask != null && !lstWorkTask.isEmpty()) {
            List<WorkOrder> lstWorkOrder = queryWorkOrder();
            if (lstWorkOrder != null && !lstWorkOrder.isEmpty()) {
                HashMap<String, List<SuperEntity>> mapWorkOrder = new HashMap<String, List<SuperEntity>>();
                String taskID;// 作业任务主键
                for (WorkOrder workOrder : lstWorkOrder) {
                    taskID = workOrder.getUd_zyxk_worktaskid();
                    if (!mapWorkOrder.containsKey(taskID)) {
                        mapWorkOrder.put(taskID, new ArrayList<SuperEntity>());
                    }
                    mapWorkOrder.get(taskID).add(workOrder);
                }
                // 作业任务与作业票关系
                String clazz = WorkOrder.class.getName();
                for (SuperEntity workTask : lstWorkTask) {
                    taskID = (String) workTask
                            .getAttribute("ud_zyxk_worktaskid");// 待修改
                    workTask.setChild(clazz, mapWorkOrder.get(taskID));
                }
            }
        }
        return lstWorkTask;
    }

    /**
     * queryWorkTask:(查询任务). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    @SuppressWarnings("unchecked")
    protected List<SuperEntity> queryWorkTask() throws HDException {
        String sql = buildQryWorkTaskSql();
        List<SuperEntity> lstWorkTask = (List<SuperEntity>) dao.executeQuery(
                sql, new EntityListResult(WorkTask.class));
        // Log.e("queryWorkTask", sql);
        return lstWorkTask;
    }

    /**
     * queryWorkOrder:(查询作业票). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    @SuppressWarnings("unchecked")
    public List<WorkOrder> queryWorkOrder() throws HDException {
        String sql = buildQryWorkOrderSql();
        List<WorkOrder> lstWorkOrder = (List<WorkOrder>) dao.executeQuery(sql,
                new EntityListResult(WorkOrder.class));
        // Log.e("queryWorkOrder", sql);
        return lstWorkOrder;
    }

    /**
     * buildQryWorkTaskSql:(查询作业任务sql). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String buildQryWorkTaskSql() throws HDException {
        return buildSql("ud_zyxk_worktask task", getWorkTaskCols(),
                buildWorkTaskWhere());
    }

    /**
     * getWorkTaskCols:(作业任务字段：作业名称、属地部门、属地单位、作业区域、作业单位、作业地点、作业开始时间、作业结束时间、作业内容)
     * . <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String[] getWorkTaskCols() throws HDException {
        return new String[]{"task.ud_zyxk_worktaskid", "task.zyname",
                "task.zyarea_desc", "task.sddept_desc",
                "task.zylocation_desc",
                // "task.zydept",
                "task.zydept_desc", "task.zysite", "task.zystarttime",
                "task.zyendtime", "task.zycontent", "task.zyptype",
                "task.zyptypedesc"};
    }

    /**
     * buildWorkTaskWhere:(任务查询条件). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String buildWorkTaskWhere() throws HDException {
        StringBuilder sbWhere = new StringBuilder();
        String workOrderWhere = buildWorkOrderWhere();// 作业票过滤条件
        String extWhere = getWorkTaskExtWhere();// 特殊条件
        if (!StringUtils.isEmpty(workOrderWhere)) {
            sbWhere.append("task.ud_zyxk_worktaskid in(");
            sbWhere.append("select ud_zyxk_worktaskid from ud_zyxk_zysq zysq");
            sbWhere.append(" left outer join alndomain adm");
            sbWhere.append("  on zysq.zypclass = adm.value and adm.domainid='UDZYLX'");
            sbWhere.append(" where ").append(workOrderWhere).append(")");
            if (!StringUtils.isEmpty(extWhere)) {
                sbWhere.append(" and ");
            }
        }
        // 特殊条件
        if (!StringUtils.isEmpty(extWhere)) {
            sbWhere.append(extWhere);
        }
        return sbWhere.toString();
    }

    /**
     * getWorkTaskExtWhere:(任务查询特殊条件，可重载). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String getWorkTaskExtWhere() throws HDException {
        return null;
    }

    /**
     * buildQryWorkOrderSql:(查询作业票sql). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
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
     * @return
     * @throws HDException
     * @author lg
     */
    protected String[] getWorkOrderCols() throws HDException {
        return new String[]{"zysq.isxfdjh", "zysq.pausestatus", "zysq.ud_zyxk_worktaskid", "zysq.ud_zyxk_zysqid", "zysq.zyname",
                "zysq.zystarttime", "zysq.zyendtime", "zysq.topzypclass",
                "ifnull(zysq.ispause ,0) as ispause", "zysq.pausetime",
                "zysq.iskyyq", "zysq.yyqcount", "zysq.yqcount",
                "zysq.sjendtime as sjendtime",
                "zysq.sjstarttime as sjstarttime",
                "adm.description as zypclassname",
                "sdm.description as statusname", "zysq.zypclass as zypclass",
                "zysq.status", "zysq.isqtjc as isqtjc",
                "zysq.dhzy_id as dhzy_id", "zysq.iscbs as iscbs",
                "zysq.sddept as sddept", "zysq.yyqcount as yyqcount",
                "zysq.dhzy_id as dhzy_id", "zysq.parent_id", "zysq.cssavefied",
                "zysq.zydept as zydept", "zysq.nlgldnum as nlgldnum",
                "zysq.whtype as whtype"};
    }

    /**
     * buildWorkOrderWhere:(作业票查询条件). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String buildWorkOrderWhere() throws HDException {
        StringBuilder sbWhere = new StringBuilder();
        String extWhere = getWorkOrderExtWhere();// 特殊条件
        // 搜索过滤
        if (!StringUtils.isEmpty(condition)) {
            sbWhere.append("(adm.description like '%").append(condition)
                    .append("%'");
            sbWhere.append(" or zysq.zyname like '%").append(condition)
                    .append("%'");
            sbWhere.append(" or zysq.zylocation_desc like '%")
                    .append(condition).append("%'");
            sbWhere.append(" or zysq.sddept_desc like '%").append(condition)
                    .append("%') ");
            if (!StringUtils.isEmpty(extWhere)) {
                sbWhere.append(" and ");
            }
        }
        // 特殊条件
        if (!StringUtils.isEmpty(extWhere)) {
            sbWhere.append(extWhere);
        }
        return sbWhere.toString();
    }

    /**
     * getWorkOrderExtWhere:(作业票查询扩展条件，可重载). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    protected String getWorkOrderExtWhere() throws HDException {
        return null;
    }

    /**
     * buildSql:(构造sql). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @param table
     * @param cols
     * @param where
     * @return
     * @throws HDException
     * @author lg
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
