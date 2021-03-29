/**
 * Project Name:hse-entity-service
 * File Name:QueryWorkInfo.java
 * Package Name:com.hd.hse.service.workorder.queryinfo
 * Date:2014年10月16日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.service.workorder.queryinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.result.ListListResult;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.EnergyIsolation;
import com.hd.hse.entity.base.EnergyIsolationDetail;
import com.hd.hse.entity.base.GasDetectSub;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.PriorityLine;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.base.ZyxxTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.common.SysActionAgeConfig;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleDevice;
import com.hd.hse.entity.tempele.TempEleZy;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.AppAnlsCfg;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.ServiceActivityUtils;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: QueryWorkInfo ()<br/>
 * date: 2014年10月16日 <br/>
 *
 * @author zhaofeng
 */
public class QueryWorkInfo implements IQueryWorkInfo {

    private final Logger logger = LogUtils.getLogger(QueryWorkInfo.class);
    /**
     * dao:TODO(数据库交互).
     */
    BaseDao dao;

    /**
     * connection:TODO(数据库连接).
     */
    private IConnection connection;

    /**
     * Creates a new instance of QueryWorkInfo.
     */
    public QueryWorkInfo() {
        dao = new BaseDao();
    }

    public QueryWorkInfo(IConnection connection) {
        dao = new BaseDao();
        this.connection = connection;
    }

    /**
     * executeQuery:(查询). <br/>
     * date: 2014年11月10日 <br/>
     *
     * @param sql
     * @param type
     * @return
     * @throws DaoException
     * @author lg
     */
    private Object executeQuery(String sql, IProcessResultSet type)
            throws DaoException {
        if (this.connection == null) {
            return dao.executeQuery(sql, type);
        } else {
            return dao.executeQuery(connection, sql, type);
        }
    }

    /**
     * TODO 查询作业票信息
     *
     * @see IQueryWorkInfo#queryWorkInfo(SuperEntity,
     * IQueryCallEventListener)
     */
    @Override
    public WorkOrder queryWorkInfo(SuperEntity workEntity,
                                   IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            StringBuilder workSbSql = new StringBuilder();
            workSbSql.setLength(0);
            workSbSql
                    .append("select ydkrjz,zwwxw,xftd,jcqgyjz,jcqwd,jcqyl,jchgyjz,jchwd,jchyl,ud_zyxk_zysqid,pausestatus,isxfdjh,ud_zyxk_sxpcpzid,sxkjlevel,zysqnum,zypclass,zyptype,ad1.description as zypclassname,parent_id,zyarea,zfpersontime,zfcause,zylocation_desc,zyarea_desc,spstatus,zylocation,");
            workSbSql
                    .append("sddept,zysite,zydept,zycontent,s.zylevel,ad.description as zylevel_desc,w.location_desc as location,zystarttime,zyendtime,isqtjc,isnlgl,iskyyq,csnum,cssavenum,");
            workSbSql
                    .append("sddept_desc,grfhzb,whshib,zydept_desc,zyname,ifnull(yqcount,0) as yqcount,ifnull(yyqcount,0) as yyqcount,beforeissuit,afterissuit,ischecked,status,iscbs,");
            workSbSql
                    .append("custodytype,dhzy_id,cssavefied,isqualify,checkresult,ischecked,issave,functionpoint,gbtype,gbsm,ad2.description as gbtype_desc,jclocation_desc,");
            workSbSql
                    .append("ud_zyxk_zyfxfxid,fxstatus,isrelation,ud_zyxk_jsaid,gjjy,zqx,sjendtime,sjstarttime,s.reasonqx,s.yqcount,s.yyqcount, s.whtype, s.zyptype,s.zyptypedesc from ud_zyxk_zysq s left join ud_zyxk_wzk w  ");
            workSbSql
                    .append("on s.zylocation = w.location left outer join alndomain ad on s.zylevel=ad.value and ad.domainid='UDZYLEVEL' ");
            workSbSql
                    .append("left outer join alndomain ad1 on s.zypclass=ad1.value and ad1.domainid='UDZYLX'");
            workSbSql
                    .append("left outer join alndomain ad2 on s.gbtype=ad2.value and ad2.domainid='UDZYGBYY'");
            workSbSql
                    .append("where ud_zyxk_zysqid ='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("';");

            // 非异步加载
            try {
                WorkOrder workOrder = (WorkOrder) dao
                        .executeQuery(workSbSql.toString(), new EntityResult(
                                WorkOrder.class));
                return workOrder;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryWorkInfo", new Class<?>[]{
                            SuperEntity.class, IQueryCallEventListener.class},
                    new Object[]{workEntity, null});
        }
        return null;
    }

    /*
     * (non-Javadoc)
	 *
	 * @see
	 * com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo#queryWorkInfoConfigInfo
	 * (com.hd.hse.common.entity.SuperEntity, java.lang.String,
	 * com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener)
	 */
    /*
     * (non-Javadoc)
	 *
	 * @see
	 * com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo#queryWorkInfoConfigInfo
	 * (com.hd.hse.common.entity.SuperEntity, java.lang.String,
	 * com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener)
	 */
    @SuppressWarnings("unchecked")
    @Override
    public List<PDAWorkOrderInfoConfig> queryWorkInfoConfigInfo(
            SuperEntity workEntity, String functionType,
            IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("zypclass") == null)
            throw new HDException("作业票信息的zypclass属性获取失败！");
        // if(workEntity.getAttribute("cssavefied") == null)
        // throw new HDException("作业票信息的cssavefied属性获取失败！");
        Object csSaveFied = workEntity.getAttribute("cssavefied");
        if (callEventListener == null) {
            // 判断是否一票制
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            boolean iszysqofone = false;
            StringBuilder sbSql = new StringBuilder();
            sbSql.append(
                    "select * from sys_relation_info where ifnull(sys_type,'')='")
                    .append(relationEntity.getSys_type())
                    .append("' and isqy='1' and dr!=1  ");
            sbSql.append(
                    " and  ','||(case when ifnull(sys_fuction,'')=='' then '")
                    .append(relationEntity.getSys_fuction())
                    .append("' else sys_fuction end) ||','  like '%,")
                    .append(relationEntity.getSys_fuction()).append(",%'  ");
            sbSql.append(
                    " and ','|| (case when ifnull(sys_value,'')=='' then '")
                    .append(relationEntity.getSys_value())
                    .append("' else sys_value end) ||',' like '%,")
                    .append(relationEntity.getSys_value()).append(",%'  ;");
            try {
                Object obj = executeQuery(sbSql.toString(), new EntityResult(
                        RelationTableName.class));
                if (obj != null)
                    iszysqofone = true;
            } catch (DaoException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
                iszysqofone = false;
            }
            String zypType = "";
            if (iszysqofone && workEntity.getAttribute("zyptype") != null) {
                String[] zyptypes = workEntity.getAttribute("zyptype")
                        .toString().split(",");
                for (String string : zyptypes) {
                    if (!workEntity.getAttribute("zypclass").equals(
                            IWorkOrderZypClass.ZYPCLASS_ZYDP)
                            || !string
                            .equals(IWorkOrderZypClass.ZYPCLASS_LSYDZY)) {
                        zypType += "'" + string + "',";
                    }
                }
                zypType = zypType + "'" + workEntity.getAttribute("zypclass")
                        + "'";
            } else {
                zypType = "'" + workEntity.getAttribute("zypclass").toString()
                        + "'";
            }

            StringBuilder configSbSql = new StringBuilder();
            // 主类pscode,contype,zypclass
            configSbSql.setLength(0);
            configSbSql
                    .append("select pscode,contype,zypclass,contypedesc,conlevel,batappr,dycode,ifnull(contypelie,0) as contypelie,cbisenable  ");
            configSbSql.append(" from ud_zyxk_zysqpdasc ");
            if (iszysqofone) {
                configSbSql.append(" where topzypclass='");
            } else {
                configSbSql.append(" where zypclass='");
            }
            configSbSql.append(workEntity.getAttribute("zypclass").toString())
                    .append("' and pscode='").append(functionType).append("' ");
            configSbSql.append(" and isactive=1 group by pscode ,contype,");
            if (iszysqofone) {
                configSbSql.append("topzypclass");
            } else {
                configSbSql.append("zypclass");
            }
            configSbSql.append(" order by tab_order asc;");

            StringBuilder childSql = new StringBuilder();
            // 子类
            childSql.setLength(0);
            childSql.append("select sname,pscode,contype,ifnull(contypelie,0) as contypelie,dycode,otherisenable,otherfield,cbisenable,cbtype,zypclass,");
            childSql.append("valuewheretype,cbname,ifnull(cstype,'') as cstype,isorder,issavebtn,conlevel,batappr,iscscheck from ud_zyxk_zysqpdasc ");
            childSql.append(" where zypclass in(").append(zypType)
                    .append(") and pscode='").append(functionType).append("' ");
            childSql.append(" and isactive=1 order by tab_order asc;");
            // 非异步加载
            try {
                List<PDAWorkOrderInfoConfig> mainConfig = (List<PDAWorkOrderInfoConfig>) executeQuery(
                        configSbSql.toString(), new EntityListResult(
                                PDAWorkOrderInfoConfig.class));
                List<PDAWorkOrderInfoConfig> childConfig = (List<PDAWorkOrderInfoConfig>) executeQuery(
                        childSql.toString(), new EntityListResult(
                                PDAWorkOrderInfoConfig.class));

                int count = 0;// 标记措施的数量
                for (int i = 0; i < mainConfig.size(); i++) {
                    List<SuperEntity> childList = null;
                    for (int j = 0; j < childConfig.size(); j++) {
                        if ((int) mainConfig.get(i).getContype() == (int) childConfig
                                .get(j).getContype()) {
                            if (childList == null)
                                childList = new ArrayList<SuperEntity>();
                            childList.add(childConfig.get(j));
                            count++;
                        }
                    }
                    mainConfig.get(i).setChildCount(count);// 标记子项的数量
                    for (SuperEntity superEntity : childList) {
                        (((PDAWorkOrderInfoConfig) superEntity))
                                .setChildCount(count);
                    }
                    if (csSaveFied != null && !csSaveFied.equals("")) {
                        String cs = (String) csSaveFied;
                        String[] split = cs.split(",");

                        boolean t = false;
                        for (int j = 0; j < split.length; j++) {

                            if (split[j] != null
                                    && (split[j].toString()).equals(mainConfig
                                    .get(i).getContype().toString())) {
                                t = true;
                                break;
                            } else {

                            }

                        }
                        if (t) {
                            mainConfig.get(i).setIsComplete(1);// 该类别已经操作
                        } else {
                            mainConfig.get(i).setIsComplete(0);// 表示该类别没有操作
                        }


                    }
//                        if (csSaveFied != null
//                                && (csSaveFied.toString()).contains(mainConfig
//                                .get(i).getContype().toString())) {
//                            mainConfig.get(i).setIsComplete(1);// 该类别已经操作
//                        } else {
//                            mainConfig.get(i).setIsComplete(0);// 表示该类别没有操作
//                        }
                    mainConfig.get(i).setChild(
                            PDAWorkOrderInfoConfig.class.getName(), childList);
                    childList = null;
                }
                //区分复查，延期
                if (functionType.equalsIgnoreCase(IConfigEncoding.FC)
                        || functionType.equalsIgnoreCase(IConfigEncoding.YQ)) {
                    WorkMeasureReview tempWmr = null;
                    StringBuilder sql = new StringBuilder();
                    sql.append("select * from ud_zyxk_zycsfc where pagetype='")
                            .append(functionType)
                            .append("' ");
                    sql.append(" and ud_zyxk_zysqid='")
                            .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                    .toString()).append("' ");
                    sql.append(" and status!='")
                            .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                            .append("';");
                    try {
                        tempWmr = (WorkMeasureReview) dao.executeQuery(sql
                                .toString(), new EntityResult(
                                WorkMeasureReview.class));
                    } catch (DaoException e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (tempWmr != null) {
                        for (int i = 0; i < mainConfig.size(); i++) {
                            if (mainConfig.get(i).getContype() == IConfigEncoding.MEASURE_TYPE) {
                                if (tempWmr.getCsnum() != null && tempWmr.getCsnum() > 0) {
                                    mainConfig.get(i).setIsComplete(0);
                                } else
                                    mainConfig.get(i).setIsComplete(1);
                            }
                        }
                    }
                }

                if (functionType.equalsIgnoreCase(IConfigEncoding.SP)
                        || functionType.equalsIgnoreCase(IConfigEncoding.FC)) {
                    List<SuperEntity> childList = null;
                    if (workEntity.getAttribute("isqtjc") != null
                            && Integer.parseInt(workEntity.getAttribute(
                            "isqtjc").toString()) == 1) {
                        // 如果该作业票有气体检测，增加气体检测导航栏
                        // 增加气体检测的导航栏信息
                        PDAWorkOrderInfoConfig gasConfig = new PDAWorkOrderInfoConfig();
                        if (functionType.equalsIgnoreCase(IConfigEncoding.SP)) {
                            gasConfig.setDycode(IConfigEncoding.SP_GAS_NUM);
                        } else if (functionType
                                .equalsIgnoreCase(IConfigEncoding.FC)) {
                            gasConfig.setDycode(IConfigEncoding.FC_GAS_NUM);
                        }
                        gasConfig.setContype(IConfigEncoding.GAS_TYPE);
                        gasConfig.setSname("气体检测");
                        gasConfig.setPscode(functionType);
                        gasConfig.setContypedesc("气体检测");
                        if (csSaveFied != null
                                && csSaveFied
                                .toString()
                                .contains(
                                        String.valueOf(IConfigEncoding.GAS_TYPE))) {
                            gasConfig.setIsComplete(1);
                        } else {
                            gasConfig.setIsComplete(0);
                        }
                        childList = new ArrayList<SuperEntity>();
                        childList.add(gasConfig);
                        gasConfig.setChild(
                                PDAWorkOrderInfoConfig.class.getName(),
                                childList);
                        mainConfig.add(gasConfig);
                    }
                    /**
                     * 能量隔离 Created by LiuYang on 2016/2/16
                     * 如果作业票没有勾选能量隔离的话应该不显示能量隔离页签
                     */
                    if (!functionType.equalsIgnoreCase(IConfigEncoding.SP)
                            || workEntity.getAttribute("isnlgl") == null
                            || Integer.parseInt(workEntity.getAttribute(
                            "isnlgl").toString()) != 1) {
                        for (int j = 0; j < mainConfig.size(); j++) {
                            if (mainConfig.get(j).getContype()
                                    .equals(IConfigEncoding.ENERGY_TYPE)) {
                                mainConfig.remove(j);
                            }
                        }
                        // PDAWorkOrderInfoConfig energyConfig = new
                        // PDAWorkOrderInfoConfig();
                        // energyConfig.setDycode(IConfigEncoding.SP_ENERGY_NUM);
                        // energyConfig.setContype(IConfigEncoding.ENERGY_TYPE);
                        // energyConfig.setSname("能量隔离");
                        // energyConfig.setPscode(functionType);
                        // energyConfig.setContypedesc("能量隔离");
                        // if (csSaveFied != null
                        // && csSaveFied
                        // .toString()
                        // .contains(
                        // String.valueOf(IConfigEncoding.ENERGY_TYPE))) {
                        // energyConfig.setIsComplete(1);
                        // } else {
                        // energyConfig.setIsComplete(0);
                        // }
                        // childList = new ArrayList<SuperEntity>();
                        // childList.add(energyConfig);
                        // energyConfig.setChild(
                        // PDAWorkOrderInfoConfig.class.getName(),
                        // childList);
                        // mainConfig.add(energyConfig);
                    }
                    if (functionType.equalsIgnoreCase(IConfigEncoding.SP)) {
                        // 增加会签的导航栏信息
                        PDAWorkOrderInfoConfig signConfig = new PDAWorkOrderInfoConfig();
                        signConfig.setDycode(IConfigEncoding.SP_SIGN_NUM);
                        signConfig.setContype(IConfigEncoding.SIGN_TYPE);
                        signConfig.setPscode(functionType);
                        signConfig.setSname("会签");
                        signConfig.setContypedesc("会签");
                        if (csSaveFied != null
                                && csSaveFied
                                .toString()
                                .contains(
                                        String.valueOf(IConfigEncoding.SIGN_TYPE))) {
                            signConfig.setIsComplete(1);
                        } else {
                            signConfig.setIsComplete(0);
                        }
                        childList = new ArrayList<SuperEntity>();
                        childList.add(signConfig);
                        signConfig.setChild(
                                PDAWorkOrderInfoConfig.class.getName(),
                                childList);
                        mainConfig.add(signConfig);
                    }
                } else if (functionType.equalsIgnoreCase(IConfigEncoding.YQ)) {
                    // 增加延期的导航栏信息
                    List<SuperEntity> childList = null;
                    PDAWorkOrderInfoConfig YQPDAConfigInfo = new PDAWorkOrderInfoConfig();
                    YQPDAConfigInfo.setDycode(IConfigEncoding.YQ_SIGN_NUM);
                    YQPDAConfigInfo.setContype(IConfigEncoding.YQ_SIGN_TYPE);
                    YQPDAConfigInfo.setSname("延期确认");
                    YQPDAConfigInfo.setPscode(IConfigEncoding.YQ);
                    YQPDAConfigInfo.setCstype("");
                    YQPDAConfigInfo.setCbisenable(0);
                    YQPDAConfigInfo.setContypedesc("延期确认");
                    if (csSaveFied != null
                            && csSaveFied
                            .toString()
                            .contains(
                                    String.valueOf(IConfigEncoding.YQ_SIGN_TYPE))) {
                        YQPDAConfigInfo.setIsComplete(1);
                    } else {
                        YQPDAConfigInfo.setIsComplete(0);
                    }
                    childList = new ArrayList<SuperEntity>();
                    childList.add(YQPDAConfigInfo);
                    YQPDAConfigInfo.setChild(
                            PDAWorkOrderInfoConfig.class.getName(), childList);
                    mainConfig.add(YQPDAConfigInfo);
                }
                return mainConfig;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询基础配置信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryWorkInfoConfigInfo",
                    new Class<?>[]{SuperEntity.class, String.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, functionType, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PDAWorkOrderInfoConfig> queryWorkInfoConfigInfo(
            SuperEntity workEntity, String functionType, Integer contye,
            IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("zypclass") == null)
            throw new HDException("作业票信息的zypclass属性获取失败！");
        if (callEventListener == null) {

            StringBuilder childSql = new StringBuilder();
            // 子类
            childSql.setLength(0);
            childSql.append("select sname,pscode,contype,ifnull(contypelie,0) as contypelie,dycode,otherisenable,otherfield,cbisenable,cbtype,zypclass,");
            childSql.append("valuewheretype,cbname,ifnull(cstype,'') as cstype,isorder,issavebtn,conlevel,batappr from ud_zyxk_zysqpdasc ");
            childSql.append(" where zypclass='")
                    .append(workEntity.getAttribute("zypclass").toString())
                    .append("' and pscode='").append(functionType).append("' ");
            if (contye != null) {
                childSql.append(" and contype='")
                        .append(String.valueOf(contye)).append("' ");
            }
            childSql.append(" and isactive=1 order by tab_order asc;");

            // 非异步加载
            try {
                List<PDAWorkOrderInfoConfig> configList = (List<PDAWorkOrderInfoConfig>) dao
                        .executeQuery(childSql.toString(),
                                new EntityListResult(
                                        PDAWorkOrderInfoConfig.class));
                for (int i = 0; i < configList.size(); i++) {
                    configList.get(i).setChildCount(configList.size());
                }
                return configList;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询基础配置信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryWorkInfoConfigInfo",
                    new Class<?>[]{SuperEntity.class, String.class,
                            Integer.class, IQueryCallEventListener.class},
                    new Object[]{workEntity, functionType, contye, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HazardNotify> queryHarmInfo(SuperEntity workEntity,
                                            PDAWorkOrderInfoConfig pdaConfig,
                                            IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            StringBuilder harmSbSql = new StringBuilder();
            harmSbSql.setLength(0);
            harmSbSql
                    .append("select whtable.ud_zyxk_zysq2hazardid,whtable.ud_zyxk_zysqid,whtable.hazardid,whtable.description,whtable.ispadselect, ");
            // harmSbSql
            // .append("(case when ifnull(selwh.selenum,'')!='' then 1 else whtable.isselect end) as isselect ");
            harmSbSql.append(" whtable.isselect ");
            harmSbSql
                    .append(" from ud_zyxk_zysq2hazard as whtable left join (select ifnull(whshib,'') as selenum from ud_zyxk_zysq where ifnull(whshib,'')!='' ");
            harmSbSql
                    .append(" and  ud_zyxk_zysqid='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("') as selwh");
            harmSbSql
                    .append(" on ','||selwh.selenum||',' like '%,'|| whtable.hazardid ||',%' ");
            harmSbSql
                    .append(" where ifnull(whtable.whtype,0)=0 and whtable.ud_zyxk_zysqid = '")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("'");
            if (pdaConfig != null && pdaConfig.getCstype() != null) {
                // 如果危害识别有分类
                harmSbSql.append(" and whtable.UDHAZTYPE = '")
                        .append(pdaConfig.getCstype()).append("'");
            }
            harmSbSql
                    .append(" order by whtable.paixu, whtable.ud_zyxk_zysq2hazardid;");

            // 非异步加载
            try {
                return (List<HazardNotify>) dao.executeQuery(harmSbSql
                        .toString(), new EntityListResult(HazardNotify.class));
            } catch (DaoException e) {
                // TODO: handle exception
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票危害信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryHarmInfo", new Class<?>[]{
                            SuperEntity.class, IQueryCallEventListener.class},
                    new Object[]{workEntity, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkApplyMeasure> queryMeasureInfo(SuperEntity workEntity,
                                                   SuperEntity workConfigEntity,
                                                   IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (workConfigEntity.getAttribute("cstype") == null)
            throw new HDException("获取界面配置信息的cstype属性失败！");
        if (callEventListener == null) {
            // 判断是否一票制
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            boolean iszysqofone = relation.isHadRelative(relationEntity);
            String csty = workConfigEntity.getAttribute("cstype").toString();
            String cstyStr = UtilTools.convertToSqlString(csty);// 措施项，带‘()’的
            String zysqid = workEntity.getAttribute("ud_zyxk_zysqid")
                    .toString();
            StringBuilder measureSbSql = new StringBuilder();
            measureSbSql.setLength(0);

            measureSbSql
                    .append("select cs.ud_zyxk_zysqid,cs.precautionid,cs.iszyfxfxadd as issupplement,cs.isnecessary,cs.ud_zyxk_zysq2precautionid,");
            measureSbSql
                    .append(" cs.vqtcsfield,cs.description,cs.isselect,cs.value,cs.hazardid,cs.inputtext,cs.cstype,ifnull(wh.description,'') as whdesc,aln.description as cstydesc,");
            measureSbSql
                    .append(" ifnull(wh.isselect,0) as wh,ifnull(per.opcode,'') as personid,ifnull(per.ud_id,'') as ud_id,ifnull(per.opname,'') as persondesc,cs.checkresult "
                            + " from ud_zyxk_zysq2precaution cs left join ")
                    .append(" ud_zyxk_zysq2hazard wh");
            measureSbSql
                    .append(" on cs.hazardid=wh.hazardid and cs.ud_zyxk_zysqid=wh.ud_zyxk_zysqid left join (select * from alndomain  where domainid='UDCSCLASS') aln on cs.cstype=aln.value "
                            + " left join (select ud_id,tableid,opid,opcode,opname,sptime from ");
            measureSbSql
                    .append(" getstepconfirmpernew where type='SP01' and objectname='UD_ZYXK_ZYSQ' and tableid='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("') ");
            measureSbSql
                    .append("as per  on cs.ud_zyxk_zysqid=per.tableid and  cs.precautionid=per.opid  where ");
            measureSbSql
                    .append(" cs.ud_zyxk_zysqid='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("'");
            if (!StringUtils.isEmpty(csty) && !"QTCS".equals(csty))
                measureSbSql.append(" and cstype in ").append(cstyStr)
                        .append(" ");
            if (iszysqofone) {
                measureSbSql.append(" and zyptype = '")
                        .append(workConfigEntity.getAttribute("zypclass"))
                        .append("' ");
            }
            measureSbSql
                    .append(" order by cs.paixu asc,cs.ud_zyxk_zysq2precautionid asc,cs.hazardid,cs.cstype desc; ");// cs.isnecessary

            // 非异步加载
            try {
                List<WorkApplyMeasure> measureList = (List<WorkApplyMeasure>) dao
                        .executeQuery(measureSbSql.toString(),
                                new EntityListResult(WorkApplyMeasure.class));
                if (Integer.valueOf(workConfigEntity.getAttribute("cbisenable")
                        .toString()) == 1) {
                    // 表示措施分组显示
                    if (Integer.valueOf(workConfigEntity.getAttribute(
                            "contypelie").toString()) == 1) {
                        // 表示四列-危害（危害表）+措施
                        return (List<WorkApplyMeasure>) UtilTools.groupList(
                                (List) measureList, WorkApplyMeasure.class,
                                "whdesc", "description");
                    } else {
                        // 表示三列-分类名（动态域）+措施
                        return (List<WorkApplyMeasure>) UtilTools.groupList(
                                (List) measureList, WorkApplyMeasure.class,
                                "cstydesc", "description");
                    }
                } else {
                    return measureList;
                }
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票措施信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryMeasureInfo", new Class<?>[]{
                    SuperEntity.class, SuperEntity.class,
                    IQueryCallEventListener.class}, new Object[]{workEntity,
                    workConfigEntity, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkGuardEquipment> queryPpeInfo(SuperEntity workEntity,
                                                 IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            StringBuilder ppeSbSql = new StringBuilder();
            ppeSbSql.setLength(0);
            ppeSbSql.append("select grfh.ud_zyxk_zysqid,grfh.ud_zyxk_zysq2ud_zyxk_grfhzbid,grfh.grfhzbnum,grfh.description,grfh.ispadselect, ");
            // ppeSbSql.append("(case when ifnull(selgrfh.selenum,'')!='' then 1 else grfh.isselect end) as isselect, ");
            ppeSbSql.append(" grfh.isselect, ");
            ppeSbSql.append(" grfh.value from  ud_zyxk_zysq2ud_zyxk_grfhzb as grfh left join (select ifnull(grfhzb,'') as selenum from ud_zyxk_zysq ");
            ppeSbSql.append(
                    " where ifnull(grfhzb,'')!='' and  ud_zyxk_zysqid='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("') as selgrfh ");
            ppeSbSql.append(" on ','||selgrfh.selenum||',' like '%,'|| grfh.grfhzbnum ||',%' ");
            ppeSbSql.append(" where  grfh.ud_zyxk_zysqid ='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString())
                    .append("' order by grfh.ud_zyxk_zysq2ud_zyxk_grfhzbid; ");

            // 非异步加载
            try {
                return (List<WorkGuardEquipment>) dao.executeQuery(ppeSbSql
                        .toString(), new EntityListResult(
                        WorkGuardEquipment.class));
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票个人防护信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryPpeInfo", new Class<?>[]{
                            SuperEntity.class, IQueryCallEventListener.class},
                    new Object[]{workEntity, null});
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public GasDetection queryGasInfo(SuperEntity workEntity,
                                     IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            // StringBuffer gasSbSql = new StringBuffer();
            // gasSbSql.setLength(0);
            // gasSbSql.append(
            // "select ud_zyxk_qtjcid,ud_zyxk_zysqid,jctime,jclocation_desc,jclocation,ishg,jcdept,jcmethod from ud_zyxk_qtjc where ud_zyxk_zysqid ='")
            // .append(workEntity.getAttribute("ud_zyxk_zysqid")
            // .toString()).append("';");
            StringBuilder domainSbSql = new StringBuilder();
            domainSbSql
                    .append("select * from alndomain where domainid='UDJCMETHOD';");
            StringBuilder childSbSql = new StringBuilder();
            childSbSql
                    .append("select aln.domainid as domainid,(case when ifnull(line.qttype,'')='' then aln.domainid||'|'||aln.value else line.qttype end) as qttype,line.qtvalue as qtvalue,aln.description as description,");
            childSbSql
                    .append(" aln.maxvalue as maxvalue,aln.minvalue as minvalue,aln.isbj as isbj from (select domainid,description,value,maxvalue,minvalue,isbj from");
            childSbSql
                    .append(" (select domainid,description,value from alndomain where domainid in (select domainid||value from alndomain where domainid = 'UDQTTYPE' order by value)) as alntable left join ");
            childSbSql
                    .append(" (select qtname, main_tab.qtlx,qtpz.maxvalue as maxvalue, qtpz.minvalue as minvalue,qtpz.isbj as isbj from ud_zyxk_qtjcpz qtpz inner join");
            childSbSql
                    .append(" (select (aln.domainid || aln.value) as qtlx,pz.qtlx as main_lx,pz.qtname as son_lx from ALNDOMAIN aln inner join  UD_ZYXK_QTJCPZ pz  on pz.QTLX=aln.value");
            childSbSql
                    .append(" where aln.domainid='UDQTTYPE' order by value ) as main_tab on qtpz.qtlx=main_tab.main_lx and qtpz.qtname=main_tab.son_lx) as alnpz ");
            childSbSql
                    .append(" on alntable.domainid=alnpz.qtlx and alntable.value=alnpz.qtname) as aln left join");
            childSbSql
                    .append(" (select qttype, qtvalue from ud_zyxk_qtjcline where ud_zyxk_qtjcid");
            childSbSql
                    .append(" in (select ud_zyxk_qtjcid from ud_zyxk_qtjc where ud_zyxk_zysqid = '")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString())
                    .append("' order by ud_zyxk_qtjcid desc limit 1))");
            childSbSql
                    .append("  as line on (aln.domainid||'|'||aln.value)=line.qttype  order by aln.domainid asc,line.qtvalue desc,value asc;");

            // 非异步加载
            try {
                // GasDetection gd = (GasDetection) dao.executeQuery(
                // sbSql.toString(), new EntityResult(GasDetection.class));
                GasDetection gd = null;
                if (gd == null) {
                    gd = new GasDetection();
                    gd.setUd_zyxk_zysqid(workEntity.getAttribute(
                            "ud_zyxk_zysqid").toString());
                }
                List domainList = (List) dao.executeQuery(domainSbSql
                        .toString(), new EntityListResult(Domain.class));
                gd.setChild(Domain.class.getName(), domainList);
                List<List<GasDetectSub>> gasLineList = (List<List<GasDetectSub>>) dao
                        .executeQuery(childSbSql.toString(),
                                new ListListResult(GasDetectSub.class,
                                        "domainid"));
                gd.setListChild(GasDetectSub.class.getName(),
                        (List) gasLineList);
                return gd;
            } catch (DaoException e) {
                // TODO: handle exception
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票气体检测信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryGasInfo", new Class<?>[]{
                            SuperEntity.class, IQueryCallEventListener.class},
                    new Object[]{workEntity, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<Map<String, String>> queryHistoryGasInfo(
            SuperEntity workEntity, IQueryCallEventListener callEventListener)
            throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            // 非异步加载
            StringBuffer historySbSql = new StringBuffer();
            ArrayList<Map<String, String>> mapLists = new ArrayList<Map<String, String>>();
            List<GasDetection> mainList = null;
            Map<String, String> map = null;

            historySbSql.setLength(0);
            historySbSql
                    .append("select ud_zyxk_qtjcid, jctime ,jclocation_desc ,ishg,jcdept , jcmethod,writenbypda from ud_zyxk_qtjc  ");
            historySbSql
                    .append(" where ud_zyxk_zysqid ='")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("'  order by jctime desc;");
            try {
                mainList = (List<GasDetection>) dao.executeQuery(historySbSql
                        .toString(), new EntityListResult(GasDetection.class));
                if (mainList == null || mainList.size() == 0)
                    return null;
                PDAWorkOrderInfoConfig workConfigEntity = new PDAWorkOrderInfoConfig();
                workConfigEntity.setDycode(IConfigEncoding.SP_GAS_NUM);
                WorkApprovalPersonRecord personRecord = new WorkApprovalPersonRecord();
                personRecord.setTablename(ITableName.UD_ZYXK_QTJC);
                for (int i = 0; i < mainList.size(); i++) {
                    GasDetection mainGasDetection = mainList.get(i);
                    if (map == null)
                        map = new LinkedHashMap<String, String>();
                    map.put("检测时间", mainGasDetection.getJctime());
                    map.put("检测位置", mainGasDetection.getJclocation_desc());
                    map.put("检测单位", mainGasDetection.getJcdept());
                    map.put("检测方式", mainGasDetection.getJcmethod());
                    map.put("是否合格",
                            (mainGasDetection.getIshg() == null || mainGasDetection
                                    .getIshg() == 0) ? "否" : "是");
                    // personRecord.setTableid(mainList.get(i).getUd_zyxk_qtjcid()
                    // .toString());
                    historySbSql.setLength(0);
                    historySbSql
                            .append("select dotable.description as description,line.qtvalue as qtvalue,line.qtsvalue as qtsvalue from ud_zyxk_qtjcline as line inner join (select (domainid||'|'||value) as qttype");
                    historySbSql
                            .append(" ,description from alndomain where domainid in (select domainid||value from alndomain where domainid='UDQTTYPE')) as dotable on line.qttype=dotable.qttype");
                    historySbSql
                            .append(" where ud_zyxk_qtjcid='")
                            .append(mainGasDetection.getUd_zyxk_qtjcid()
                                    .toString())
                            .append("' order by line.qttype asc;");
                    List<GasDetectSub> gasLineList = (List<GasDetectSub>) dao
                            .executeQuery(historySbSql.toString(),
                                    new EntityListResult(GasDetectSub.class));
                    for (int j = 0; j < gasLineList.size(); j++) {
                        map.put(gasLineList.get(j).getDescription(),
                                gasLineList.get(j).getQtvalue() == null ? gasLineList
                                        .get(j).getQtsvalue() == null ? null
                                        : gasLineList.get(j).getQtsvalue()
                                        .toString() : gasLineList
                                        .get(j).getQtvalue().toString());
                    }
                    // 审批人员信息
                    // if (IWorkOrderStatus.GAS_STATUS_FINISH
                    // .equals(mainGasDetection.getWritenbypda())) {
                    // personRecord.setTableid(mainGasDetection
                    // .getUd_zyxk_qtjcid());
                    // } else {
                    // personRecord.setTableid("UNDO");
                    // .getUd_zyxk_qtjcid());
                    // }
                    personRecord.setTableid(mainGasDetection
                            .getUd_zyxk_qtjcid());
                    // List<WorkApprovalPermission> approvalList =
                    // queryApprovalPermission(
                    // workEntity, workConfigEntity, personRecord, null);
                    // for (int j = 0; j < approvalList.size(); j++) {
                    // map.put(approvalList.get(j).getSpfield_desc(),
                    // approvalList.get(j).getPersondesc());
                    // }
                    List<WorkApprovalPersonRecord> records = queryHisetoryApprovalRecord(personRecord);
                    for (int j = 0; j < records.size(); j++) {
                        map.put(records.get(j).getValue(), records.get(j)
                                .getPerson_name());
                    }
                    mapLists.add(map);
                    map = null;
                }
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票气体检测历史信息失败！");
            }
            return mapLists;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryHistoryGasInfo",
                    new Class<?>[]{SuperEntity.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, null});
        }
        return null;
    }

    private List<WorkApprovalPersonRecord> queryHisetoryApprovalRecord(
            WorkApprovalPersonRecord personRecord) throws HDException {
        if (personRecord == null)
            return null;
        StringBuffer historyApprovalSbSql = new StringBuffer();
        historyApprovalSbSql
                .append("select person_name,value from ud_zyxk_zyspryjl where tablename='")
                .append(personRecord.getTablename()).append("' ");
        historyApprovalSbSql.append(" and ifnull(tableid,'')='")
                .append(personRecord.getTableid()).append("'");
        historyApprovalSbSql.append(" and ifnull(tableid,'')='")
                .append(personRecord.getTableid()).append("'");
        historyApprovalSbSql.append(";");
        try {
            return (List<WorkApprovalPersonRecord>) dao.executeQuery(
                    historyApprovalSbSql.toString(), new EntityListResult(
                            WorkApprovalPersonRecord.class));
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new HDException("查询历史审批人失败！", e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public GasDetection queryUndoneGasInfo(SuperEntity workEntity,
                                           IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            StringBuilder unDoSbSql = new StringBuilder();
            unDoSbSql.setLength(0);
            // 非异步加载
            try {
                unDoSbSql
                        .append("select ud_zyxk_qtjcid, jctime ,jclocation_desc ,ishg,jcdept ,jcmethod from ud_zyxk_qtjc  ");
                unDoSbSql
                        .append(" where ud_zyxk_qtjcid in (select ud_zyxk_qtjcid from ud_zyxk_qtjc where ud_zyxk_zysqid='")
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString())
                        .append("' order by jctime desc limit 1)");
                unDoSbSql.append(" and writenbypda='")
                        .append(IWorkOrderStatus.GAS_STATUS_PROCEED)
                        .append("';");
                GasDetection gd = (GasDetection) dao.executeQuery(unDoSbSql
                        .toString(), new EntityResult(GasDetection.class));
                if (gd == null || gd.getUd_zyxk_qtjcid() == null)
                    return null;
                unDoSbSql.setLength(0);
                unDoSbSql
                        .append("select aln.domainid as domainid,(case when ifnull(line.qttype,'')='' then aln.domainid||'|'||aln.value else line.qttype end) as qtlx,line.qtvalue as qtvalue,aln.description as description,");
                unDoSbSql
                        .append(" aln.maxvalue as maxvalue,aln.minvalue as minvalue,aln.isbj as isbj from (select domainid,description,value,maxvalue,minvalue,isbj from");
                unDoSbSql
                        .append(" (select domainid,description,value from alndomain where domainid in (select domainid||value from alndomain where domainid = 'UDQTTYPE' order by value)) as alntable left join ");
                unDoSbSql
                        .append(" (select qtname, main_tab.qtlx,qtpz.maxvalue as maxvalue, qtpz.minvalue as minvalue,qtpz.isbj as isbj from ud_zyxk_qtjcpz qtpz inner join");
                unDoSbSql
                        .append(" (select (aln.domainid || aln.value) as qtlx,pz.qtlx as main_lx,pz.qtname as son_lx from ALNDOMAIN aln inner join  UD_ZYXK_QTJCPZ pz  on pz.QTLX=aln.value");
                unDoSbSql
                        .append(" where aln.domainid='UDQTTYPE' order by value ) as main_tab on qtpz.qtlx=main_tab.main_lx and qtpz.qtname=main_tab.son_lx) as alnpz ");
                unDoSbSql
                        .append(" on alntable.domainid=alnpz.qtlx and alntable.description=alnpz.qtname) as aln left join");
                unDoSbSql
                        .append(" (select qttype, qtvalue from ud_zyxk_qtjcline where ud_zyxk_qtjcid");
                unDoSbSql.append(" ='").append(gd.getUd_zyxk_qtjcid())
                        .append("' )");
                unDoSbSql
                        .append("  as line on (aln.domainid||'|'||aln.value)=line.qttype order by aln.domainid asc,line.qtvalue desc,value asc;");
                List<List<GasDetectSub>> gasLineList = (List<List<GasDetectSub>>) dao
                        .executeQuery(unDoSbSql.toString(), new ListListResult(
                                GasDetectSub.class, "domainid"));
                gd.setListChild(GasDetectSub.class.getName(),
                        (List) gasLineList);
                return gd;
            } catch (DaoException e) {
                // TODO: handle exception
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票气体检测信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryUndoneGasInfo",
                    new Class<?>[]{SuperEntity.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, null});
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public WorkMeasureReview queryReviewInfo(SuperEntity workEntity,
                                             SuperEntity workConfigEntity,
                                             IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub

        if (workConfigEntity.getAttribute("pscode") == null)
            throw new HDException("获取作业配置信息的pscode属性失败！");
        if (callEventListener == null) {
            // 判断是否一票制
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            boolean iszysqofone = relation.isHadRelative(relationEntity);
            if (IConfigEncoding.JJB01.equals(workConfigEntity
                    .getAttribute("pscode"))
                    || IConfigEncoding.JJB02.equals(workConfigEntity
                    .getAttribute("pscode"))) {
                // 交接班
                WorkMeasureReview tempWmr = null;
                StringBuilder jjbSbSql = new StringBuilder();
                jjbSbSql.append("select * from ud_zyxk_zycsfc where pagetype='")
                        .append(workConfigEntity.getAttribute("pscode"))
                        .append("' ");
                jjbSbSql.append(" and ud_zyxk_zysqid='")
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString()).append("' ");
                jjbSbSql.append(" and status!='")
                        .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                        .append("';");
                try {
                    tempWmr = (WorkMeasureReview) dao.executeQuery(jjbSbSql
                            .toString(), new EntityResult(
                            WorkMeasureReview.class));
                } catch (DaoException e) {
                    logger.error(e.getMessage(), e);
                    tempWmr = null;
                }
                if (tempWmr == null) {
                    tempWmr = new WorkMeasureReview();
                    tempWmr.setZysqnum(workEntity.getAttribute("zysqnum") == null ? ""
                            : workEntity.getAttribute("zysqnum").toString());
                    tempWmr.setUd_zyxk_zysqid(workEntity.getAttribute(
                            "ud_zyxk_zysqid").toString());
                    tempWmr.setStatus(IWorkOrderStatus.REVIEW_STATUS_PROCEED);
                    tempWmr.setIsupload(0);
                    tempWmr.setDatasource("PDA");
                    tempWmr.setPagetype(workConfigEntity.getAttribute("pscode")
                            .toString());
                }
                return tempWmr;

            }
            WorkMeasureReview wmReview = getMainMeasureReview(workEntity,
                    workConfigEntity, true);
            if (wmReview == null)
                throw new HDException("获取复查信息失败！");
            StringBuilder reViewSbSql = new StringBuilder();
            String csty = workConfigEntity.getAttribute("cstype").toString();
            String zysqid = workEntity.getAttribute("ud_zyxk_zysqid")
                    .toString();
            reViewSbSql.setLength(0);
            reViewSbSql
                    .append("select cs.zycsfclinenum,cs.precautionid,cs.zycsfcnum,cs.vqtcsfield,cs.description,cs.isselect,cs.value,cs.hazardid,cs.cstype,cs.checkresult,single.opname as persondesc,");
            reViewSbSql
                    .append("ifnull(wh.description,'') as whdesc,aln.description as cstydesc, ifnull(wh.isselect,0) as wh  from ud_zyxk_zycsfcline cs left join")
                    .append(" (select * from ud_zyxk_zysq2hazard where ud_zyxk_zysqid='")
                    .append(zysqid).append("' )wh");
            reViewSbSql
                    .append("  on cs.HAZARDID=wh.HAZARDID left join (select * from alndomain  where domainid='UDCSCLASS') aln on cs.cstype=aln.value left join  (select tableid,opid,opcode,opname,sptime from getstepconfirmpernew where ");
            reViewSbSql.append("  type='")
                    .append(workConfigEntity.getAttribute("pscode").toString())
                    .append("'  and tableid='").append(wmReview.getZycsfcnum())
                    .append("') as single  ");
            reViewSbSql
                    .append(" on single.opid = cs.precautionid where cs.zycsfcnum ='")
                    .append(wmReview.getZycsfcnum()).append("'");
            if (!StringUtils.isEmpty(csty) && !"QTCS".equals(csty))
                reViewSbSql.append(" and cstype in ")
                        .append(UtilTools.convertToSqlString(csty)).append(" ");
            if (iszysqofone) {
                reViewSbSql.append(" and zyptype = '")
                        .append(workConfigEntity.getAttribute("zypclass"))
                        .append("' ");
            }
            reViewSbSql
                    .append(" order by cs.cstype desc,cs.hazardid,cs.value desc,cs.paixu asc,cs.precautionid asc ;");
            // 非异步加载
            try {
                List child = (List) dao.executeQuery(reViewSbSql.toString(),
                        new EntityListResult(MeasureReviewSub.class));
                // wmReview.setChild(MeasureReviewSub.class.getName(), child);
                if (Integer.valueOf(workConfigEntity.getAttribute("cbisenable")
                        .toString()) == 1) {
                    // 表示措施分组显示
                    if (Integer.valueOf(workConfigEntity.getAttribute(
                            "contypelie").toString()) == 1) {
                        // 表示四列-危害（危害表）+措施
                        wmReview.setChild(MeasureReviewSub.class.getName(),
                                (List<SuperEntity>) UtilTools.groupList(child,
                                        MeasureReviewSub.class, "whdesc",
                                        "description"));
                    } else {
                        // 表示三列-分类名（动态域）+措施
                        wmReview.setChild(MeasureReviewSub.class.getName(),
                                (List<SuperEntity>) UtilTools.groupList(child,
                                        MeasureReviewSub.class, "cstydesc",
                                        "description"));
                    }
                } else {
                    wmReview.setChild(MeasureReviewSub.class.getName(), child);
                }
                return wmReview;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业票措施复查信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryReviewInfo", new Class<?>[]{
                    SuperEntity.class, SuperEntity.class,
                    IQueryCallEventListener.class}, new Object[]{workEntity,
                    workConfigEntity, null});
        }
        return null;
    }

    @Override
    public WorkDelay queryDelayInfo(SuperEntity workEntity, String zyscNum,
                                    IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub

        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (callEventListener == null) {
            StringBuilder deLaySbSql = new StringBuilder();
            deLaySbSql.setLength(0);
            deLaySbSql
                    .append("select yqstarttime,yqendtime,ud_zyxk_zyyqid from ud_zyxk_zyyq where ud_zyxk_zysqid = '")
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("' ");
            deLaySbSql.append(" and zycsfcnum='").append(zyscNum)
                    .append("' order by ud_zyxk_zyyqid desc limit 1;");

            // 非异步加载
            try {
                WorkDelay workDelay = (WorkDelay) dao.executeQuery(
                        deLaySbSql.toString(),
                        new EntityResult(WorkDelay.class));
                if (workDelay == null) {
                    workDelay = new WorkDelay();
                    workDelay.setUd_zyxk_zysqid(workEntity.getAttribute(
                            "ud_zyxk_zysqid").toString());
                }
                return workDelay;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询环节点信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDelayInfo", new Class<?>[]{
                    SuperEntity.class, String.class,
                    IQueryCallEventListener.class}, new Object[]{workEntity,
                    zyscNum, null});
        }
        return null;
    }

    @Override
    public List<WorkApprovalPermission> queryApprovalPermission(
            SuperEntity workEntity, SuperEntity workConfigEntity,
            SuperEntity record, IQueryCallEventListener callEventListener)
            throws HDException {
        if (workEntity.getAttribute("sddept") == null)
            throw new HDException("获取作业票信息的sddept属性失败！");
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (workConfigEntity.getAttribute("dycode") == null)
            throw new HDException("获取基础配置信息的dycode属性失败！");
        if (callEventListener == null) {
            // 判断是否走一票制
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            boolean iszysqofone = relation.isHadRelative(relationEntity);
            String dycode = workConfigEntity.getAttribute("dycode").toString();
            if (!dycode.equals(IConfigEncoding.SP_SIGN_NUM)
                    && !dycode.equals(IConfigEncoding.GB_NUM)
                    && !dycode.equals(IConfigEncoding.QX_NUM)
                    && !dycode.equals(IConfigEncoding.YQ_SIGN_NUM)
                    && !dycode.equals(IConfigEncoding.PAUSE_NUM)
                    && !dycode.equals(IConfigEncoding.INTERRUPT_NUM)
                    && !dycode.equals(IConfigEncoding.INTERRUPTEND_NUM)) {
                iszysqofone = false;
            }
            if (iszysqofone) {
                // 一票制
                List<WorkApprovalPermission> permissions = new ArrayList<WorkApprovalPermission>();
                List<WorkOrder> orders = separateWorkorder((WorkOrder) workEntity);
                for (WorkOrder order : orders) {
                    if (workEntity.getAttribute("zypclass").toString()
                            .equals(IWorkOrderZypClass.ZYPCLASS_ZYDP)
                            && order.getZypclass().equals(
                            IWorkOrderZypClass.ZYPCLASS_LSYDZY)) {
                        break;
                    }
                    List<WorkApprovalPermission> pList = queryApprovalPermissions(
                            order, workConfigEntity, record, null);
                    if (permissions.size() == 0) {
                        for (WorkApprovalPermission p : pList) {
                            p.setStrzyspqxid("'" + p.getUd_zyxk_zyspqxid()
                                    + "'");
                        }
                        permissions.addAll(pList);
                    } else {
                        for (WorkApprovalPermission p1 : pList) {
                            boolean isInclude = false;
                            p1.setStrzyspqxid("'" + p1.getUd_zyxk_zyspqxid()
                                    + "'");
                            for (WorkApprovalPermission p2 : permissions) {
                                if (p1.getSpfield().equals(p2.getSpfield())) {
                                    p2.setStrzyspqxid(p2.getStrzyspqxid() + ","
                                            + p1.getStrzyspqxid());
                                    isInclude = true;
                                    break;
                                }
                            }
                            if (!isInclude) {
                                permissions.add(p1);
                            }
                        }
                    }
                }
                // 删除不启用的环节点
                List<WorkApprovalPermission> delWap = new ArrayList<>();
                for (WorkApprovalPermission wap : permissions) {
                    if (wap.getIsqy() == 0) {
                        delWap.add(wap);
                    }
                }
                permissions.removeAll(delWap);
                // 排序
                Collections.sort(permissions,
                        new Comparator<WorkApprovalPermission>() {

                            @Override
                            public int compare(WorkApprovalPermission arg0,
                                               WorkApprovalPermission arg1) {
                                if (arg0.getIsend() > arg1.getIsend()) {
                                    return 1;
                                } else if (arg0.getIsend() < arg1.getIsend()) {
                                    return -1;
                                } else {
                                    if (arg0.getPdapaixu() > arg1.getPdapaixu()) {
                                        return 1;
                                    } else if (arg0.getPdapaixu() < arg1
                                            .getPdapaixu()) {
                                        return -1;
                                    } else {
                                        if (arg0.getIloadorder() > arg1
                                                .getIloadorder()) {
                                            return 1;
                                        } else {
                                            return -1;
                                        }
                                    }
                                }
                            }
                        });
                // 最终批准人在最后
                // WorkApprovalPermission endPermission = null;
                // for (WorkApprovalPermission permission : permissions) {
                // if (permission.getIsend() != null
                // && permission.getIsend() == 1) {
                // endPermission = permission;
                // }
                // }
                // if (endPermission != null) {
                // permissions.remove(endPermission);
                // permissions.add(endPermission);
                // }
                return permissions;
            } else {
                return queryApprovalPermissions(workEntity, workConfigEntity,
                        record, null);
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler
                    .execute(this, "queryApprovalPermissions", new Class<?>[]{
                                    SuperEntity.class, SuperEntity.class,
                                    SuperEntity.class, IQueryCallEventListener.class},
                            new Object[]{workEntity, workConfigEntity,
                                    record, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<WorkApprovalPermission> queryApprovalPermissions(
            SuperEntity workEntity, SuperEntity workConfigEntity,
            SuperEntity record, IQueryCallEventListener callEventListener)
            throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("sddept") == null)
            throw new HDException("获取作业票信息的sddept属性失败！");
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (workConfigEntity.getAttribute("dycode") == null)
            throw new HDException("获取基础配置信息的dycode属性失败！");
        if (callEventListener == null) {
            // 判断是否走一票制
            // IQueryRelativeConfig relation = new QueryRelativeConfig();
            // RelationTableName relationEntity = new RelationTableName();
            // relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            // boolean iszysqofone = relation.isHadRelative(relationEntity);
            // String dycode =
            // workConfigEntity.getAttribute("dycode").toString();
            // if (!dycode.equals(IConfigEncoding.SP_SIGN_NUM)
            // && !dycode.equals(IConfigEncoding.GB_NUM)
            // && !dycode.equals(IConfigEncoding.QX_NUM)
            // && !dycode.equals(IConfigEncoding.YQ_SIGN_NUM)) {
            boolean iszysqofone = false;
            // }
            StringBuilder approvalSbSql = new StringBuilder();
            approvalSbSql.setLength(0);
            if (workConfigEntity.getAttribute("conlevel") != null
                    && Integer.parseInt(workConfigEntity.getAttribute(
                    "conlevel").toString()) == 1) {
                // 走逐条环节点加载查询
                approvalSbSql
                        .append(" select ud_zyxk_zyspqxid,is_remote_approve,is_spot_final_person,ifnull(spfield_desc,'没有配置') as spfield_desc,spfield ,spqx.zylocation,'1' as contype,isinput,isdzqm,pdapaixu,ismust,bpermulcard,");
                approvalSbSql
                        .append(" wttype,wtlevel,qxperson,spqx.iscbs,spqx.qxrole,spjl.person as personid,spjl.sptime as sptime,ifnull(isend,0) as isend,ifnull(iloadorder,0) as iloadorder,spqx.iscbszz,spqx.status as status, ");
                approvalSbSql
                        .append(" spjl.person_name as persondesc,bmsgremind as ismessage,spjl.person_name as defaultpersondesc,spjl.person as defaultpersonid  from");
                approvalSbSql
                        .append(" (select dept_spfa.ud_zyxk_spfaid,dept_spfa.vdept,dept_spfa.vreportdeptnum from (select deptnum, vreportdeptnum from ud_sy_dept as dep where");
                approvalSbSql
                        .append(" (select vreportdeptnum from ud_sy_dept where deptnum = '")
                        .append(workEntity.getAttribute("sddept").toString())
                        .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') desc)");
                approvalSbSql
                        .append(" as temp_dept left join (select distinct fa.ud_zyxk_spfaid,fa.vdept ,(case when ifnull(fa.vdept,'')='' then (select vreportdeptnum from ud_sy_dept as dep");
                approvalSbSql
                        .append(" where  (select vreportdeptnum from ud_sy_dept where deptnum = '")
                        .append(workEntity.getAttribute("sddept").toString())
                        .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') asc limit 1)");
                approvalSbSql
                        .append(" else dept.vreportdeptnum end) as vreportdeptnum from ud_sy_dept as dept left join ud_zyxk_spfa as fa");
                approvalSbSql
                        .append(" on ','|| fa.vdept ||',' like '%,' || dept.deptnum || ',%' or ifnull(fa.vdept,'')=''  where ifnull(fa.ud_zyxk_spfaid,'')!=''  and fa.visqy=1 )");
                approvalSbSql
                        .append(" as dept_spfa on temp_dept.vreportdeptnum=dept_spfa.vreportdeptnum order by dept_spfa.vreportdeptnum desc limit 1) as spfa,");
                approvalSbSql
                        .append(" ud_zyxk_zysq as zysq,ud_zyxk_zyspqx  as spqx left join  ( ");
                approvalSbSql
                        .append(" select opname as person_name,sptime,spnode,tabcode,opcode as person, tableid,");
                approvalSbSql
                        .append(" a.ud_id,a.tableid as ud_zyxk_zysqid,a.opid ,ud_type  from ud_onemeasure_person as a ");
                approvalSbSql
                        .append(" where a.ud_id in (select  ud_id from ud_onemeasure_person where ");
                approvalSbSql
                        .append(" tabcode='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString()).append("' ");
                approvalSbSql
                        .append(" and  ud_type='")
                        .append(workConfigEntity.getAttribute("pscode")
                                .toString()).append("'  ");
                if (record instanceof MultitermMeasureAffirm) {
                    MultitermMeasureAffirm itemRecord = (MultitermMeasureAffirm) record;
                    approvalSbSql.append(" and tablename='")
                            .append(itemRecord.getTablename())
                            .append("' and  tableid='")
                            .append(itemRecord.getTableid())
                            .append("' and  opid='")
                            .append(itemRecord.getOpid()).append("'  ");
                } else {
                    approvalSbSql
                            .append(" and tablename='' and  tableid='' and  opid=''  ");
                }
                approvalSbSql
                        .append(" group by tableid,opid,spnode order by sptime desc limit 1 ) ");
                approvalSbSql
                        .append("  ) as spjl on spqx.spfield=spjl.spnode and spqx.zylocation=spjl.tabcode ");
                approvalSbSql
                        .append(" where spfa.ud_zyxk_spfaid = spqx.ud_zyxk_spfaid and zysq.ud_zyxk_zysqid in ('")
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString())
                        .append("','@共用id') and ','|| wttype ||',' like '%,' ||zysq.zypclass || ',%' and");
                approvalSbSql
                        .append(" (wtlevel=ifnull(zysq.zylevel,'') or  ifnull(wtlevel,'')='')  and spqx.zylocation='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString())
                        .append("'  order by isend,pdapaixu,iloadorder ;");

            } else {
                approvalSbSql
                        .append(" select ud_zyxk_zyspqxid,is_remote_approve,is_spot_final_person,ifnull(spfield_desc,'没有配置') as spfield_desc,spfield ,spqx.zylocation,'1' as contype,isinput,isdzqm,pdapaixu,ismust,bpermulcard,");
                approvalSbSql
                        .append(" wttype,wtlevel,qxperson,spqx.iscbs,spqx.qxrole,spjl.person as personid,spjl.sptime as sptime,ifnull(isend,0) as isend,ifnull(iloadorder,0) as iloadorder,spqx.iscbszz,spqx.status as status, ");
                approvalSbSql
                        .append(" spjl.person_name as persondesc,bmsgremind as ismessage,spjl.ud_zyxk_zyspryjlid,spjl.person_name as defaultpersondesc,spjl.person as defaultpersonid,spqx.relatedzy,spqx.isqy,spqx.photoconfigur,spqx.isQtjcDirector,spjl.job as job,spjl.code as code, spjl.zcproject as zcproject from");
                approvalSbSql
                        .append(" (select dept_spfa.ud_zyxk_spfaid,dept_spfa.vdept,dept_spfa.vreportdeptnum from (select deptnum, vreportdeptnum from ud_sy_dept as dep where");
                approvalSbSql
                        .append(" (select vreportdeptnum from ud_sy_dept where deptnum = '")
                        .append(workEntity.getAttribute("sddept").toString())
                        .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') desc)");
                approvalSbSql
                        .append(" as temp_dept left join (select distinct fa.ud_zyxk_spfaid,fa.vdept ,(case when ifnull(fa.vdept,'')='' then (select vreportdeptnum from ud_sy_dept as dep");
                approvalSbSql
                        .append(" where  (select vreportdeptnum from ud_sy_dept where deptnum = '")
                        .append(workEntity.getAttribute("sddept").toString())
                        .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') asc limit 1)");
                approvalSbSql
                        .append(" else dept.vreportdeptnum end) as vreportdeptnum from ud_sy_dept as dept left join ud_zyxk_spfa as fa");
                approvalSbSql
                        .append(" on ','|| fa.vdept ||',' like '%,' || dept.deptnum || ',%' or ifnull(fa.vdept,'')=''  where ifnull(fa.ud_zyxk_spfaid,'')!=''  and fa.visqy=1 )");
                approvalSbSql
                        .append(" as dept_spfa on temp_dept.vreportdeptnum=dept_spfa.vreportdeptnum order by dept_spfa.vreportdeptnum desc limit 1) as spfa,");
                if (!iszysqofone) {
                    approvalSbSql.append(" ud_zyxk_zysq as zysq,");
                }
                approvalSbSql
                        .append(" ud_zyxk_zyspqx  as spqx left join (select ud_zyxk_zyspryjlid,person,sptime,inputall,spnode,dycode,person_name,ifnull(tableid,'0') as tableid,");
                approvalSbSql
                        .append(" ud_zyxk_zysqid,job,code,zcproject from ud_zyxk_zyspryjl where ud_zyxk_zysqid in ('")
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString())
                        .append("','@共用id') and dycode='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString()).append("' ");
                WorkApprovalPersonRecord personRecord = null;
                if (record instanceof WorkApprovalPersonRecord) {
                    personRecord = (WorkApprovalPersonRecord) record;
                }
                if (personRecord != null
                        && !StringUtils.isEmpty(personRecord.getTablename())
                        && !StringUtils.isEmpty(personRecord.getTableid())
                        && !"UNDO".equals(personRecord.getTableid())) {
                    approvalSbSql.append(" and ifnull(tablename,'')='")
                            .append(personRecord.getTablename())
                            .append("' and ifnull(tableid,'')='")
                            .append(personRecord.getTableid()).append("' ");
                } else {
                    approvalSbSql.append(" and ifnull(tablename,'')='' ")
                            .append(" and ifnull(tableid,'')='' ");
                }
                approvalSbSql
                        .append(" ) as spjl on spqx.spfield=spjl.spnode and spqx.zylocation=spjl.dycode");
                approvalSbSql
                        .append(" where spfa.ud_zyxk_spfaid = spqx.ud_zyxk_spfaid");
                if (iszysqofone) {
                    PriorityLine priority = queryPriority(
                            (WorkOrder) workEntity, null);
                    approvalSbSql.append(" AND spqx.priorityid = ").append(
                            priority.getUd_zyxk_prtylineid());
                } else {
                    approvalSbSql
                            .append(" and zysq.ud_zyxk_zysqid in ('")
                            .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                    .toString())
                            .append("','@共用id') and ','|| wttype ||',' like '%,' || '")
                            .append(workEntity.getAttribute("zypclass"))
                            .append("' || ',%' and")
                            .append(" (wtlevel=")
                            .append("'")
                            .append(workEntity.getAttribute("zylevel") == null ? ""
                                    : workEntity.getAttribute("zylevel"))
                            .append("'").append(" or  ifnull(wtlevel,'')='')");
                }
                approvalSbSql
                        .append(" and spqx.zylocation='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString())
                        .append("'  order by isend,pdapaixu,iloadorder ;");
            }
            // logger.error(approvalSbSql.toString(), null);
            // 非异步加载
            try {
                List<WorkApprovalPermission> permissions = (List<WorkApprovalPermission>) dao
                        .executeQuery(approvalSbSql.toString(),
                                new EntityListResult(
                                        WorkApprovalPermission.class));
                // if (iszysqofone) {
                // // 去除掉不相关的相关作业
                // List<WorkApprovalPermission> temp = new ArrayList<>();
                // for (WorkApprovalPermission wap : permissions) {
                // if (!wap.getWttype().equals(wap.getRelatedzy())
                // && !workEntity
                // .getAttribute("zyptype")
                // .toString()
                // .contains(
                // wap.getRelatedzy() == null ? ""
                // : wap.getRelatedzy())) {
                // temp.add(wap);
                // }
                // }
                // permissions.removeAll(temp);
                // }
                return permissions;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询环节点信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler
                    .execute(this, "queryApprovalPermission", new Class<?>[]{
                                    SuperEntity.class, SuperEntity.class,
                                    SuperEntity.class, IQueryCallEventListener.class},
                            new Object[]{workEntity, workConfigEntity,
                                    record, null});
        }
        return null;
    }

    /**
     * getMainMeasureReview:(获取复查主记录). <br/>
     * date: 2014年10月17日 <br/>
     *
     * @param workEntity
     * @param workConfigEntity
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private WorkMeasureReview getMainMeasureReview(SuperEntity workEntity,
                                                   SuperEntity workConfigEntity, boolean isLooper) throws HDException {
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (workConfigEntity.getAttribute("pscode") == null)
            throw new HDException("获取作业配置信息的pscode属性失败！");
        WorkMeasureReview wmr = null;
        try {
            StringBuilder measureReviewSbSql = new StringBuilder();
            measureReviewSbSql.setLength(0);
            measureReviewSbSql
                    .append("select pagetype,zysqnum,zycsfcnum,ud_zyxk_zysqid,status,csnum,cssavenum from ud_zyxk_zycsfc where pagetype='")
                    .append(workConfigEntity.getAttribute("pscode").toString())
                    .append("' and  ud_zyxk_zysqid ='");
            measureReviewSbSql
                    .append(workEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("' and status!='")
                    .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                    .append("'  ");
            measureReviewSbSql.append(" order by rowid desc limit 1 ; ");
            wmr = (WorkMeasureReview) dao.executeQuery(measureReviewSbSql
                    .toString(), new EntityResult(WorkMeasureReview.class));
            if (wmr == null) {
                // 表示该功能下的已经彻底完成，复制一份最新的数据
                measureReviewSbSql.setLength(0);
                measureReviewSbSql
                        .append("select pagetype,zysqnum,zycsfcnum,ud_zyxk_zysqid,status,csnum,cssavenum from ud_zyxk_zycsfc where pagetype in ('")
                        .append(IConfigEncoding.FC).append("','")
                        .append(IConfigEncoding.YQ)
                        .append("') and ud_zyxk_zysqid ='");
                measureReviewSbSql.append(
                        workEntity.getAttribute("ud_zyxk_zysqid").toString())
                        .append("' ");
                measureReviewSbSql.append(" order by rowid desc limit 1 ; ");
                WorkMeasureReview tempWmr = (WorkMeasureReview) dao
                        .executeQuery(measureReviewSbSql.toString(),
                                new EntityResult(WorkMeasureReview.class));
                if (tempWmr == null) {
                    // 表示没有操作过作业措施复查
                    tempWmr = new WorkMeasureReview();
                    tempWmr.setZysqnum(workEntity.getAttribute("zysqnum") == null ? ""
                            : workEntity.getAttribute("zysqnum").toString());
                    tempWmr.setUd_zyxk_zysqid(workEntity.getAttribute(
                            "ud_zyxk_zysqid").toString());
                    tempWmr.setStatus(IWorkOrderStatus.REVIEW_STATUS_PROCEED);
                    tempWmr.setIsupload(0);
                    tempWmr.setDatasource("PDA");
                    tempWmr.setCssavenum("");
                    if (workConfigEntity instanceof PDAWorkOrderInfoConfig) {
                        PDAWorkOrderInfoConfig pdaConfig = (PDAWorkOrderInfoConfig) workConfigEntity;
                        tempWmr.setCsnum(pdaConfig.getChildCount());
                    }
                    tempWmr.setPagetype(workConfigEntity.getAttribute("pscode")
                            .toString());
                    List<MeasureReviewSub> listSubs = new ArrayList<MeasureReviewSub>();
                    measureReviewSbSql.setLength(0);
                    measureReviewSbSql
                            .append("select * from ud_zyxk_zysq2precaution where ud_zyxk_zysqid='")
                            .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                    .toString()).append("';");
                    List<WorkApplyMeasure> applyMeasures = (List) dao
                            .executeQuery(
                                    measureReviewSbSql.toString(),
                                    new EntityListResult(WorkApplyMeasure.class));
                    MeasureReviewSub reviewSub = null;
                    for (int i = 0; i < applyMeasures.size(); i++) {
                        reviewSub = new MeasureReviewSub();
                        reviewSub.setCstype(applyMeasures.get(i).getCstype());
                        reviewSub.setDescription(applyMeasures.get(i)
                                .getDescription());
                        reviewSub.setHazardid(applyMeasures.get(i)
                                .getHazardid());
                        // reviewSub.setIsconfirm(applyMeasures.get(i).getIsconfirm());
                        reviewSub.setIsselect(applyMeasures.get(i)
                                .getIsselect());
                        reviewSub.setValue(applyMeasures.get(i).getValue());
                        reviewSub.setVqtcsfield(applyMeasures.get(i)
                                .getVqtcsfield());
                        reviewSub.setPrecautionid(applyMeasures.get(i)
                                .getPrecautionid());
                        reviewSub.setPaixu(applyMeasures.get(i).getPaixu());
                        reviewSub.setCheckresult(applyMeasures.get(i)
                                .getCheckresult());
                        reviewSub.setZyptype(applyMeasures.get(i).getZyptype());
                        listSubs.add(reviewSub);
                    }
                    tempWmr.setChild(MeasureReviewSub.class.getName(),
                            (List) listSubs);
                    SequenceGenerator.genPrimaryKeyValue(tempWmr,
                            MeasureReviewSub.class);// 创建新的主键
                    copyMeasureReview(workEntity, tempWmr);
                } else {
                    // 复制最近一条记录
                    measureReviewSbSql.setLength(0);
                    measureReviewSbSql
                            .append("select * from ud_zyxk_zycsfcline where zycsfcnum='")
                            .append(tempWmr.getZycsfcnum()).append("';");
                    List<MeasureReviewSub> childReview = (List) dao
                            .executeQuery(
                                    measureReviewSbSql.toString(),
                                    new EntityListResult(MeasureReviewSub.class));
                    for (int i = 0; i < childReview.size(); i++) {
                        childReview.get(i).setIsupload(0);
                        childReview.get(i).setIsconfirm(null);
                    }
                    tempWmr.setChild(MeasureReviewSub.class.getName(),
                            (List) childReview);
                    SequenceGenerator.genPrimaryKeyValue(tempWmr,
                            MeasureReviewSub.class);// 创建新的主键
                    tempWmr.setStatus(IWorkOrderStatus.REVIEW_STATUS_PROCEED);
                    tempWmr.setIsupload(0);
                    tempWmr.setPagetype(workConfigEntity.getAttribute("pscode")
                            .toString());
                    tempWmr.setDatasource("PDA");
                    tempWmr.setCssavenum("");
                    if (workConfigEntity instanceof PDAWorkOrderInfoConfig) {
                        PDAWorkOrderInfoConfig pdaConfig = (PDAWorkOrderInfoConfig) workConfigEntity;
                        tempWmr.setCsnum(pdaConfig.getChildCount());
                    }
                    copyMeasureReview(workEntity, tempWmr);
                }
            }
            if (wmr == null && isLooper)
                wmr = getMainMeasureReview(workEntity, workConfigEntity, false);
            return wmr;
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("判断是否进行措施复查失败！");
        }
    }

    /**
     * copyMeasureReview:(复制一份措施复查的信息). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @param wmr
     * @throws DaoException
     * @author zhaofeng
     */
    private void copyMeasureReview(SuperEntity workEntity, WorkMeasureReview wmr)
            throws DaoException {
        IConnectionSource connectionSource = null;
        IConnection connection = null;
        try {
            connectionSource = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = connectionSource.getConnection();
            dao.insertEntity(connection, wmr);
            dao.insertEntities(connection,
                    wmr.getChild(MeasureReviewSub.class.getName()));
            // 增加更新作业申请
            dao.executeUpdate(
                    connection,
                    "update ud_zyxk_zysq set CSNum=0,CSSaveFied='',CSSaveNum='' where ud_zyxk_zysqid='"
                            + wmr.getUd_zyxk_zysqid() + "';");
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("获取复查信息失败！", e);
        } finally {
            if (connectionSource != null) {
                try {
                    connectionSource.releaseConnection(connection);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                    throw new DaoException(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * TODO 进入审批中的查询
     *
     * @see IQueryWorkInfo#querySiteAuditBasicInfo(SuperEntity,
     * String,
     * IQueryCallEventListener)
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public WorkOrder querySiteAuditBasicInfo(SuperEntity workEntity,
                                             String functionType, IQueryCallEventListener callEventListener)
            throws HDException {
        // TODO Auto-generated method stub
        if (callEventListener == null) {
            // 非异步加载
            WorkOrder workOrder = queryWorkInfo(workEntity, callEventListener);
            List<PDAWorkOrderInfoConfig> workConfigList = queryWorkInfoConfigInfo(
                    workOrder, functionType, callEventListener);
            workOrder.setChild(PDAWorkOrderInfoConfig.class.getName(),
                    (List) workConfigList);
            if (!IConfigEncoding.SP.equals(functionType)) {
                // 判断是否是最新复查措施
                StringBuilder measureReviewSbSql = new StringBuilder();
                measureReviewSbSql.setLength(0);
                measureReviewSbSql
                        .append("select pagetype,zysqnum,zycsfcnum,ud_zyxk_zysqid,status,csnum from ud_zyxk_zycsfc where pagetype='")
                        .append(functionType)
                        .append("' and  ud_zyxk_zysqid ='");
                measureReviewSbSql
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString()).append("' and status!='")
                        .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                        .append("'  ");
                measureReviewSbSql
                        .append(" order by zycsfcnum desc limit 1 ; ");
                WorkMeasureReview wmr = (WorkMeasureReview) dao.executeQuery(
                        measureReviewSbSql.toString(), new EntityResult(
                                WorkMeasureReview.class));
                if (wmr == null) {
                    workOrder.setAttribute("CSNum", 0);
                    workOrder.setAttribute("CSSaveFied", "");
                    workOrder.setAttribute("CSSaveNum", "");
                    ServiceActivityUtils.getRefreshWorkConfigInfos(workOrder,
                            (List) workConfigList);
                }
                return workOrder;
            }
            if (workConfigList != null && workConfigList.size() > 0) {
                /*
                 * if (workConfigList.get(0).getContype() ==
				 * IConfigEncoding.HARM_TYPE) { // 危害 List harmList =
				 * queryHarmInfo(workEntity, callEventListener);
				 * workOrder.setChild(HazardNotify.class.getName(), harmList); }
				 * else
				 */
                if (workConfigList.get(0).getContype() == IConfigEncoding.PPE_TYPE) {
                    // 个人防护装备
                    List ppeList = queryPpeInfo(workEntity, callEventListener);
                    workOrder.setChild(WorkGuardEquipment.class.getName(),
                            ppeList);
                }
                // else if (workConfigList.get(0).getContype() ==
                // IConfigEncoding.MEASURE_TYPE) {
                // // 措施
                // List<PDAWorkOrderInfoConfig> configMeasureList = (List)
                // workConfigList
                // .get(0).getChild(
                // PDAWorkOrderInfoConfig.class.getName());
                // List<List<SuperEntity>> measureListList = new
                // ArrayList<List<SuperEntity>>();
                // for (int i = 0; i < configMeasureList.size(); i++) {
                // List measureList = queryMeasureInfo(workEntity,
                // configMeasureList.get(i), callEventListener);
                // measureListList.add(measureList);
                // }
                // workOrder.setListChild(WorkApplyMeasure.class.getName(),
                // measureListList);
                // }
            }
            return workOrder;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "querySiteAuditBasicInfo",
                    new Class<?>[]{SuperEntity.class, String.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, functionType, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> queryPhoto(SuperEntity srcEntity,
                                  SuperEntity configEntity) throws HDException {
        // TODO Auto-generated method stub
        String tablePKField = srcEntity.getPrimaryKey();
        String tableCode = srcEntity.getDBTableName();
        StringBuilder photoSbSql = new StringBuilder();
        photoSbSql.setLength(0);
        photoSbSql.append("select * from hse_sys_image where");
        photoSbSql.append(" ( tablename = '").append(tableCode).append("'");
        photoSbSql.append(" and tableid = '")
                .append(srcEntity.getAttribute(tablePKField)).append("'");
        if (configEntity != null) {
            String funcode = (String) configEntity.getAttribute("pscode");
            if (!StringUtils.isEmpty(funcode)) {
                photoSbSql.append(" and funcode = '").append(funcode)
                        .append("'");
            }
        }
        photoSbSql.append(" )");
        photoSbSql.append("or ( zysqid = '")
                .append(srcEntity.getAttribute(tablePKField)).append("'")
                .append("and tablename = 'ud_zyxk_zyspryjl' ");
        if (configEntity != null) {
            String funcode = (String) configEntity.getAttribute("pscode");
            if (!StringUtils.isEmpty(funcode)) {
                photoSbSql.append(" and funcode = '").append(funcode)
                        .append("'");
            }
        }
        photoSbSql.append(" )");

        List<Image> lstImage = (ArrayList<Image>) dao.executeQuery(
                photoSbSql.toString(), new EntityListResult(Image.class));
        return lstImage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> queryPhoto(List<WorkOrder> srcEntitys,
                                  SuperEntity configEntity) throws HDException {
        // TODO Auto-generated method stub
        StringBuilder orderids = new StringBuilder();
        for (WorkOrder workOrder : srcEntitys) {
            orderids.append(",").append(workOrder.getUd_zyxk_zysqid());
        }
        orderids.append(",");
        StringBuilder photoSbSql = new StringBuilder();
        photoSbSql.setLength(0);
        photoSbSql.append("select * from hse_sys_image where");
        photoSbSql.append(" ( ','||childids||',' like ('%' || '")
                .append(orderids).append("' || '%')");
        if (configEntity != null) {
            String funcode = (String) configEntity.getAttribute("pscode");
            if (!StringUtils.isEmpty(funcode)) {
                photoSbSql.append(" and funcode = '").append(funcode)
                        .append("'");
            }
        }
        photoSbSql.append(" )");
        // 去掉照片名相同的图片
        photoSbSql
                .append("or ( id in (select max (id) from hse_sys_image where ");
        String zysqids = "";
        for (int i = 0; i < srcEntitys.size(); i++) {
            WorkOrder workOrder = srcEntitys.get(i);
            if (i == 0) {
                zysqids += ("'" + workOrder.getUd_zyxk_zysqid() + "'");
            } else {
                zysqids += (", '" + workOrder.getUd_zyxk_zysqid() + "'");
            }

        }

        photoSbSql.append("zysqid in (").append(zysqids).append(" )")
                .append("and tablename = 'ud_zyxk_zyspryjl' ");
        if (configEntity != null) {
            String funcode = (String) configEntity.getAttribute("pscode");
            if (!StringUtils.isEmpty(funcode)) {
                photoSbSql.append(" and funcode = '").append(funcode)
                        .append("'");
            }
        }
        photoSbSql.append(" group by imagename))");

        List<Image> lstImage = (ArrayList<Image>) dao.executeQuery(
                photoSbSql.toString(), new EntityListResult(Image.class));
        return lstImage;
    }

    @Override
    public SysActionAgeConfig querySysActionAgeConfig(String action)
            throws HDException {
        // TODO Auto-generated method stub
        SysActionAgeConfig saConfig = null;
        try {
            StringBuilder sxSbSql = new StringBuilder();
            sxSbSql.append("select * from sys_action_sxpz where actiontype='")
                    .append(action).append("';");
            saConfig = (SysActionAgeConfig) dao.executeQuery(
                    sxSbSql.toString(), new EntityResult(
                            SysActionAgeConfig.class));
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("获取动作时效失败！", e);
        }
        return saConfig;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String queryReportFormUrl(SuperEntity workEntity) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        String id = workEntity.getAttribute("ud_zyxk_zysqid").toString();
        String zypclass = "";
        String url = null;
        try {
            StringBuilder urlSbSql = new StringBuilder();
            if (workEntity.getAttribute("zypclass") != null
                    && !StringUtils.isEmpty(workEntity.getAttribute("zypclass")
                    .toString())) {
                zypclass = workEntity.getAttribute("zypclass").toString();
                urlSbSql.append("select (forcast.ip||lastdesc.name) as tableurl from ");
                urlSbSql.append(" (select ('http://'||sysurl||'/') as ip from hse_sys_config where syscode in ('inurl','outurl') and enable=1) as forcast ,");
                urlSbSql.append(" (select (description||'")
                        .append(id)
                        .append("') as name from alndomain aln where aln.value='")
                        .append(zypclass).append("'");
                urlSbSql.append(" and domainid='UDRQURL') as lastdesc;");
            } else {
                urlSbSql.append("select (forcast.ip||lastdesc.name) as tableurl from ");
                urlSbSql.append(" (select ('http://'||sysurl||'/') as ip from hse_sys_config where syscode in ('inurl','outurl') and enable=1) as forcast ,");
                urlSbSql.append(" (select (description||zysq.ud_zyxk_zysqid) as name from alndomain aln inner join ud_zyxk_zysq zysq on aln.value=zysq.zypclass");
                urlSbSql.append(
                        " where domainid='UDRQURL' and zysq.ud_zyxk_zysqid='")
                        .append(workEntity.getAttribute("ud_zyxk_zysqid")
                                .toString()).append("') as lastdesc;");
            }
            Map<String, Object> map = (Map) dao.executeQuery(
                    urlSbSql.toString(), new MapResult());
            if (map == null || map.size() == 0)
                return null;
            url = map.get("tableurl") == null ? null : map.get("tableurl")
                    .toString();
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("访问服务失败！", e);
        }
        return url;
    }

    /**
     * 作业票浏览报表按级别显示
     *
     * @param workEntity
     * @return
     * @throws HDException
     */
    public String queryReportFormLevelUrl(SuperEntity workEntity) throws HDException {
        // TODO Auto-generated method stub
        if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
            throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        String id = workEntity.getAttribute("ud_zyxk_zysqid").toString();
        String zypclass = "";
        String zylevel = "";
        String url = null;
        try {
            StringBuilder urlSbSql = new StringBuilder();
            if (workEntity.getAttribute("zypclass") == null
                    || StringUtils.isEmpty(workEntity.getAttribute("zypclass")
                    .toString()))
                throw new HDException("获取作业申请信息的zypclass属性失败！");
            zypclass = workEntity.getAttribute("zypclass").toString();

            //先从synonymdomain表中查找看有几条，大于1条就是有多个表样
            StringBuilder buildSql = new StringBuilder();
            buildSql.append("select *  from synonymdomain " +
                    "aln where upper(aln.maxvalue)=upper('").append(zypclass).append("')");

            Map<String, Object> smap = (Map) dao.executeQuery(
                    buildSql.toString(), new MapResult());
            if (smap == null || smap.size() == 0)
                return null;
            else if (smap != null && smap.size() == 1) {
                zylevel = null;
            } else if (smap != null && smap.size() > 1) {
                if (workEntity.getAttribute("zylevel") != null) {
                    zylevel = workEntity.getAttribute("zylevel").toString();
                }
            }
//            if (workEntity.getAttribute("zylevel") == null
//                    || StringUtils.isEmpty(workEntity.getAttribute("zylevel")
//                    .toString()))
//                zylevel = null;
//            else
//                zylevel = workEntity.getAttribute("zylevel").toString();
            urlSbSql.append("select (forcast.ip||lastdesc.name) as tableurl from ");
            urlSbSql.append(" (select ('http://'||sysurl||'/') as ip from hse_sys_config where syscode in ('inurl','outurl') and enable=1) as forcast ,");
            urlSbSql.append(" (select (description||'")
                    .append(id)
                    .append("') as name from synonymdomain aln ");

            urlSbSql.append("where upper(aln.maxvalue)=upper('")
                    .append(zypclass).append("')");
            if (zylevel != null
                    && !StringUtils.isEmpty(zylevel)) {
                urlSbSql.append("and (upper(aln.value)=upper('")
                        .append(zylevel).append("')").append(" or aln.maxvalue=aln.value)");
            }


            urlSbSql.append(" and domainid='RQURL') as lastdesc;");

            Map<String, Object> map = (Map) dao.executeQuery(
                    urlSbSql.toString(), new MapResult());
            if (map == null || map.size() == 0)
                return null;
            url = map.get("tableurl") == null ? null : map.get("tableurl")
                    .toString();
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("访问服务失败！", e);
        }
        return url;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<MultitermMeasureAffirm> queryItemByItemInfo(
            MultitermMeasureAffirm multitermMeasureAffirm,
            IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (callEventListener == null) {
            List<MultitermMeasureAffirm> items = null;
            StringBuilder sqlSbBuilder = new StringBuilder();
            sqlSbBuilder.append("select * from ud_onemeasure_person where ");
            sqlSbBuilder.append(" ud_zyxk_zysqid='")
                    .append(multitermMeasureAffirm.getUd_zyxk_zysqid())
                    .append("' ");
            sqlSbBuilder.append(" and (ud_type='")
                    .append(multitermMeasureAffirm.getUd_type())
                    .append("' or tablename='")
                    .append(multitermMeasureAffirm.getTablename())
                    .append("') ");
            sqlSbBuilder.append(" and tableid='")
                    .append(multitermMeasureAffirm.getTableid()).append("' ");
            sqlSbBuilder.append(" and opid='")
                    .append(multitermMeasureAffirm.getOpid()).append("'; ");
            try {
                items = (List<MultitermMeasureAffirm>) executeQuery(
                        sqlSbBuilder.toString(), new EntityListResult(
                                MultitermMeasureAffirm.class));
                sqlSbBuilder.setLength(0);
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("查询逐条信息失败！", e);
            }
            return items;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryItemByItemInfo",
                    new Class<?>[]{MultitermMeasureAffirm.class,
                            IQueryCallEventListener.class}, new Object[]{
                            multitermMeasureAffirm, null});
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PositionCard> queryVirtualCards(
            IQueryCallEventListener callEventListener) throws HDException {
        // TODO Auto-generated method stub
        if (callEventListener == null) {
            List<PositionCard> positionCardList = null;
            StringBuilder sqlSbBuilder = new StringBuilder();
            sqlSbBuilder
                    .append("select * from ud_zyxk_wzk where isVirtualCard=1;");
            try {
                positionCardList = (List<PositionCard>) executeQuery(
                        sqlSbBuilder.toString(), new EntityListResult(
                                PositionCard.class));
                sqlSbBuilder.setLength(0);
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("查询虚拟位置信息失败！", e);
            }
            return positionCardList;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryVirtualCards",
                    new Class<?>[]{IQueryCallEventListener.class},
                    new Object[]{null});
        }
        return null;
    }

    // 合并审批新增的方法 2015-08-27 lxf

    /**
     * 获取合并会签 TODO
     *
     * @see IQueryWorkInfo#querySiteAuditBasicInfo(SuperEntity,
     * String,
     * IQueryCallEventListener)
     */
    public WorkOrder querySiteAuditSignInfo(SuperEntity workEntity,
                                            String functionType, IQueryCallEventListener callEventListener)
            throws HDException {
        // TODO Auto-generated method stub
        if (callEventListener == null) {
            // 非异步加载
            WorkOrder workOrder = (WorkOrder) workEntity;// queryWorkInfo(workEntity,
            // callEventListener);
            List<PDAWorkOrderInfoConfig> workConfigList = queryWorkInfoSignConfigInfo(
                    workOrder, functionType, callEventListener);
            workOrder.setChild(PDAWorkOrderInfoConfig.class.getName(),
                    (List) workConfigList);
            return workOrder;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "querySiteAuditSignInfo",
                    new Class<?>[]{SuperEntity.class, String.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, functionType, null});
        }
        return null;
    }

    /**
     * queryWorkInfoSignConfigInfo:(获取会签界面配置信息). <br/>
     * date: 2015年8月27日 <br/>
     *
     * @param workEntity
     * @param functionType
     * @param callEventListener
     * @return
     * @throws HDException
     * @author lxf
     */
    public List<PDAWorkOrderInfoConfig> queryWorkInfoSignConfigInfo(
            SuperEntity workEntity, String functionType,
            IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            // 非异步加载
            List<PDAWorkOrderInfoConfig> mainConfig = new ArrayList<PDAWorkOrderInfoConfig>();
            List<SuperEntity> childList = null;
            if (functionType.equalsIgnoreCase(IConfigEncoding.SP)) {
                // 增加会签的导航栏信息
                PDAWorkOrderInfoConfig signConfig = new PDAWorkOrderInfoConfig();
                signConfig.setDycode(IConfigEncoding.SP_SIGN_NUM);
                signConfig.setContype(IConfigEncoding.MEGER_SIGN_TYPE);
                signConfig.setPscode(functionType);
                signConfig.setSname("合并会签");
                signConfig.setContypedesc("合并会签");
                signConfig.setIsComplete(0);
                childList = new ArrayList<SuperEntity>();
                childList.add(signConfig);
                signConfig.setChild(PDAWorkOrderInfoConfig.class.getName(),
                        childList);
                mainConfig.add(signConfig);
            }
            return mainConfig;
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryWorkInfoSignConfigInfo",
                    new Class<?>[]{SuperEntity.class, String.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workEntity, functionType, null});
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkApprovalPermission> queryApprovalPermission(
            List<SuperEntity> listWorkEntity, SuperEntity workConfigEntity,
            SuperEntity record, IQueryCallEventListener callEventListener)
            throws HDException {
        // SuperEntity workEntity=workConfigEntity;
        StringBuilder sbdept = new StringBuilder();
        StringBuilder sbzysqid = new StringBuilder();

        for (SuperEntity superEntity : listWorkEntity) {
            if (sbdept.toString().length() > 0) {
                sbdept.append(",");
            }
            sbdept.append("'")
                    .append(superEntity.getAttribute("sddept").toString())
                    .append("'");
            if (sbzysqid.toString().length() > 0) {
                sbzysqid.append(",");
            }
            sbzysqid.append("'")
                    .append(superEntity.getAttribute("ud_zyxk_zysqid")
                            .toString()).append("'");

        }
        // TODO Auto-generated method stub
        // if (workEntity.getAttribute("sddept") == null)
        // throw new HDException("获取作业票信息的sddept属性失败！");
        // if (workEntity.getAttribute("ud_zyxk_zysqid") == null)
        // throw new HDException("获取作业申请信息的ud_zyxk_zysqid属性失败！");
        if (workConfigEntity.getAttribute("dycode") == null)
            throw new HDException("获取基础配置信息的dycode属性失败！");
        if (callEventListener == null) {
            StringBuilder approvalSbSql = new StringBuilder();
            approvalSbSql.setLength(0);
            if (workConfigEntity.getAttribute("conlevel") != null
                    && Integer.parseInt(workConfigEntity.getAttribute(
                    "conlevel").toString()) == 1) {
                // // 走逐条环节点加载查询
                // approvalSbSql
                // .append(" select ud_zyxk_zyspqxid,ifnull(spfield_desc,'没有配置') as spfield_desc,spfield ,spqx.zylocation,'1' as contype,isinput,pdapaixu,ismust,bpermulcard,");
                // approvalSbSql
                // .append(" wttype,wtlevel,qxperson,spqx.iscbs,spqx.qxrole,spjl.person as personid,spjl.sptime as sptime,ifnull(isend,0) as isend,spqx.iscbszz,spqx.status as status, ");
                // approvalSbSql
                // .append(" spjl.person_name as persondesc,bmsgremind as ismessage,spjl.person_name as defaultpersondesc,spjl.person as defaultpersonid  from");
                // approvalSbSql
                // .append(" (select dept_spfa.ud_zyxk_spfaid,dept_spfa.vdept,dept_spfa.vreportdeptnum from (select deptnum, vreportdeptnum from ud_sy_dept as dep where");
                // approvalSbSql
                // .append(" (select vreportdeptnum from ud_sy_dept where deptnum = '")
                // .append(workEntity.getAttribute("sddept").toString())
                // .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') desc)");
                // approvalSbSql
                // .append(" as temp_dept left join (select distinct fa.ud_zyxk_spfaid,fa.vdept ,(case when ifnull(fa.vdept,'')='' then (select vreportdeptnum from ud_sy_dept as dep");
                // approvalSbSql
                // .append(" where  (select vreportdeptnum from ud_sy_dept where deptnum = '")
                // .append(workEntity.getAttribute("sddept").toString())
                // .append("') like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') asc limit 1)");
                // approvalSbSql
                // .append(" else dept.vreportdeptnum end) as vreportdeptnum from ud_sy_dept as dept left join ud_zyxk_spfa as fa");
                // approvalSbSql
                // .append(" on ','|| fa.vdept ||',' like '%,' || dept.deptnum || ',%' or ifnull(fa.vdept,'')=''  where ifnull(fa.ud_zyxk_spfaid,'')!=''  and fa.visqy=1 )");
                // approvalSbSql
                // .append(" as dept_spfa on temp_dept.vreportdeptnum=dept_spfa.vreportdeptnum order by dept_spfa.vreportdeptnum desc limit 1) as spfa,");
                // approvalSbSql
                // .append(" ud_zyxk_zysq as zysq,ud_zyxk_zyspqx  as spqx left join  ( ");
                // approvalSbSql
                // .append(" select opname as person_name,sptime,spnode,tabcode,opcode as person, tableid,");
                // approvalSbSql
                // .append(" a.ud_id,a.tableid as ud_zyxk_zysqid,a.opid ,ud_type  from ud_onemeasure_person as a ");
                // approvalSbSql
                // .append(" where a.ud_id in (select  ud_id from ud_onemeasure_person where ");
                // approvalSbSql
                // .append(" tabcode='")
                // .append(workConfigEntity.getAttribute("dycode")
                // .toString()).append("' ");
                // approvalSbSql
                // .append(" and  ud_type='")
                // .append(workConfigEntity.getAttribute("pscode")
                // .toString()).append("'  ");
                // if (record instanceof MultitermMeasureAffirm) {
                // MultitermMeasureAffirm itemRecord = (MultitermMeasureAffirm)
                // record;
                // approvalSbSql.append(" and tablename='")
                // .append(itemRecord.getTablename())
                // .append("' and  tableid='")
                // .append(itemRecord.getTableid())
                // .append("' and  opid='")
                // .append(itemRecord.getOpid()).append("'  ");
                // } else {
                // approvalSbSql
                // .append(" and tablename='' and  tableid='' and  opid=''  ");
                // }
                // approvalSbSql
                // .append(" group by tableid,opid,spnode order by sptime desc limit 1 ) ");
                // approvalSbSql
                // .append("  ) as spjl on spqx.spfield=spjl.spnode and spqx.zylocation=spjl.tabcode ");
                // approvalSbSql
                // .append(" where spfa.ud_zyxk_spfaid = spqx.ud_zyxk_spfaid and zysq.ud_zyxk_zysqid in ('")
                // .append(workEntity.getAttribute("ud_zyxk_zysqid")
                // .toString())
                // .append("','@共用id') and ','|| wttype ||',' like '%,' ||zysq.zypclass || ',%' and");
                // approvalSbSql
                // .append(" (wtlevel=ifnull(zysq.zylevel,'') or  ifnull(wtlevel,'')='')  and spqx.zylocation='")
                // .append(workConfigEntity.getAttribute("dycode")
                // .toString())
                // .append("'  order by isend,pdapaixu,iloadorder ;");

            } else {
                approvalSbSql
                        .append(" select spqx.ud_zyxk_zyspqxid,ifnull(gw.[job_name],spfield_desc) as spfield_desc,spqx.jobnum,spqx.spfield,'1' as contype,gw.is_spot_final_person,gw.is_remote_approve,gw.isinput,gw.isdzqm,gw.pdapaixu,gw.ismust,gw.photoconfigur,spqx.isQtjcDirector,gw.bpermulcard,gw.iloadorder,gw.iscbs,");
                approvalSbSql
                        .append(" ifnull(spqx.isend,0) as isend,gw.iscbszz,gw.bmsgremind as ismessage,spqx.zylocation, wttype,wtlevel, ");
                approvalSbSql
                        .append(" qxperson,gw.role as qxrole,spjl.person as personid,spjl.sptime as sptime,spqx.status as status,spjl.person_name as persondesc,");
                approvalSbSql
                        .append(" spjl.ud_zyxk_zyspryjlid,spjl.person_name as defaultpersondesc,spjl.person as defaultpersonid,zysq.ud_zyxk_zysqid  from");
                approvalSbSql
                        .append(" (select dept_spfa.ud_zyxk_spfaid,dept_spfa.vdept,dept_spfa.vreportdeptnum from (select deptnum, vreportdeptnum from ud_sy_dept as dep where");
                approvalSbSql
                        .append(" (select vreportdeptnum from ud_sy_dept where deptnum in (")
                        .append(sbdept.toString())
                        .append(")) like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') desc)");
                approvalSbSql
                        .append(" as temp_dept left join (select distinct fa.ud_zyxk_spfaid,fa.vdept ,(case when ifnull(fa.vdept,'')='' then (select vreportdeptnum from ud_sy_dept as dep");
                approvalSbSql
                        .append(" where  (select vreportdeptnum from ud_sy_dept where deptnum in (")
                        .append(sbdept.toString())
                        .append(")) like dep.vreportdeptnum || '%' order by ifnull(vreportdeptnum, '') asc limit 1)");
                approvalSbSql
                        .append(" else dept.vreportdeptnum end) as vreportdeptnum from ud_sy_dept as dept left join ud_zyxk_spfa as fa");
                approvalSbSql
                        .append(" on ','|| fa.vdept ||',' like '%,' || dept.deptnum || ',%' or ifnull(fa.vdept,'')=''  where ifnull(fa.ud_zyxk_spfaid,'')!=''  and fa.visqy=1 )");
                approvalSbSql
                        .append(" as dept_spfa on temp_dept.vreportdeptnum=dept_spfa.vreportdeptnum order by dept_spfa.vreportdeptnum desc limit 1) as spfa,");
                approvalSbSql
                        .append(" ud_zyxk_zysq as zysq,ud_zyxk_zyspqx  as spqx left join (select ud_zyxk_zyspryjlid,person,sptime,inputall,spnode,dycode,person_name,ifnull(tableid,'0') as tableid,");
                approvalSbSql
                        .append(" ud_zyxk_zysqid from ud_zyxk_zyspryjl where ud_zyxk_zysqid in (")
                        .append(sbzysqid.toString())
                        .append(",'@共用id') and dycode='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString()).append("' ");
                WorkApprovalPersonRecord personRecord = null;
                if (record instanceof WorkApprovalPersonRecord) {
                    personRecord = (WorkApprovalPersonRecord) record;
                }
                if (personRecord != null
                        && !StringUtils.isEmpty(personRecord.getTablename())
                        && !StringUtils.isEmpty(personRecord.getTableid())
                        && !"UNDO".equals(personRecord.getTableid())) {
                    approvalSbSql.append(" and ifnull(tablename,'')='")
                            .append(personRecord.getTablename())
                            .append("' and ifnull(tableid,'')='")
                            .append(personRecord.getTableid()).append("' ");
                } else {
                    approvalSbSql.append(" and ifnull(tablename,'')='' ")
                            .append(" and ifnull(tableid,'')='' ");
                }
                approvalSbSql
                        .append(" ) as spjl on spqx.spfield=spjl.spnode and zysq.ud_zyxk_zysqid=spjl.ud_zyxk_zysqid and spqx.zylocation=spjl.dycode");
                // lxf新增 left join
                approvalSbSql
                        .append(" left join  ud_zyxk_gwgl as gw on spqx.jobnum=gw.job_num ");
                approvalSbSql
                        .append(" where spfa.ud_zyxk_spfaid = spqx.ud_zyxk_spfaid and zysq.ud_zyxk_zysqid in (")
                        .append(sbzysqid.toString())
                        .append(",'@共用id') and ','|| wttype ||',' like '%,' ||zysq.zypclass || ',%' and");
                approvalSbSql
                        .append(" (wtlevel=ifnull(zysq.zylevel,'') or  ifnull(wtlevel,'')='')  and spqx.zylocation='")
                        .append(workConfigEntity.getAttribute("dycode")
                                .toString())
                        .append("'  order by gw.isend,gw.pdapaixu,gw.iloadorder ;");
            }
            // 非异步加载
            try {

                List<WorkApprovalPermission> listdate = (List<WorkApprovalPermission>) dao
                        .executeQuery(approvalSbSql.toString(),
                                new EntityListResult(
                                        WorkApprovalPermission.class));

                listdate = dealDataInfo(listdate);
                return listdate;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                if (e.getMessage().contains("")) {
                    throw e;
                } else {
                    throw new DaoException("查询环节点信息失败！");
                }
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryApprovalPermission",
                    new Class<?>[]{SuperEntity.class, SuperEntity.class,
                            SuperEntity.class, IQueryCallEventListener.class},
                    new Object[]{listWorkEntity, workConfigEntity, record,
                            null});
        }
        return null;
    }

    /**
     * dealDataInfo:(特殊处理数据，去掉重复的，而且把刷卡人累加起来,同时判断岗位是否为空). <br/>
     * date: 2015年9月2日 <br/>
     *
     * @param listdate
     * @return
     * @throws DaoException
     * @author lxf
     */
    private List dealDataInfo(List<WorkApprovalPermission> listdate)
            throws DaoException {
        List<WorkApprovalPermission> retListDate = new ArrayList<WorkApprovalPermission>();
        if (listdate != null) {
            for (int i = 0; i < listdate.size(); i++) {
                if (listdate.get(i).getAttribute("jobnum") == null
                        || listdate.get(i).getAttribute("jobnum").toString() == "") {
                    throw new DaoException("验证失败：环节点【"
                            + listdate.get(i).getAttribute("spfield_desc")
                            + "】没有配置岗位");
                }
                for (int j = i + 1; j < listdate.size(); j++)
                    if (listdate.get(i).getAttribute("jobnum")
                            .equals(listdate.get(j).getAttribute("jobnum"))) {
                        // 刷卡人合并
                        // 1.判断两个人是否都刷过卡。
                        if (listdate.get(i).getAttribute("sptime") != null
                                && listdate.get(i).getAttribute("sptime")
                                .toString() != ""
                                && listdate.get(j).getAttribute("sptime") != null
                                && listdate.get(j).getAttribute("sptime")
                                .toString() != "") {
                            // 表示记录都刷过卡
                            String[] personids_i = listdate.get(i)
                                    .getAttribute("personid").toString()
                                    .split(",");
                            String[] personids_j = listdate.get(j)
                                    .getAttribute("personid").toString()
                                    .split(",");
                            String[] persondescs_i = listdate.get(i)
                                    .getAttribute("persondesc").toString()
                                    .split(",");
                            String[] personsescs_j = listdate.get(j)
                                    .getAttribute("persondesc").toString()
                                    .split(",");
                            String[] personidboth = (String[]) ArrayUtils
                                    .addAll(personids_i, personids_j);
                            String[] persondescboth = (String[]) ArrayUtils
                                    .addAll(persondescs_i, personsescs_j);
                            List<String> personidlist = new LinkedList<String>();
                            List<String> persondesclist = new LinkedList<String>();
                            for (int k = 0; k < personidboth.length; k++) {
                                if (!personidlist.contains(personidboth[k])) {
                                    personidlist.add(personidboth[k]);
                                    persondesclist.add(persondescboth[k]);
                                }
                            }
                            StringBuilder personidBuilder = new StringBuilder();
                            StringBuilder persondescBuilder = new StringBuilder();
                            for (int m = 0; m < personidlist.size(); m++) {
                                personidBuilder.append(personidlist.get(m))
                                        .append(",");
                                persondescBuilder.append(persondesclist.get(m))
                                        .append(",");
                            }
                            listdate.get(i).setAttribute(
                                    "personid",
                                    personidBuilder.toString()
                                            .substring(
                                                    0,
                                                    personidBuilder.toString()
                                                            .length() - 1));
                            listdate.get(i).setAttribute(
                                    "persondesc",
                                    persondescBuilder.toString().substring(
                                            0,
                                            persondescBuilder.toString()
                                                    .length() - 1));
                            // if (!(","
                            // + listdate.get(i).getAttribute("personid")
                            // .toString() + ",").contains(","
                            // + listdate.get(j).getAttribute("personid")
                            // .toString() + ",")) {
                            // // 表示刷卡人不想等时进行添加
                            // listdate.get(i).setAttribute(
                            // "personid",
                            // listdate.get(i)
                            // .getAttribute("personid")
                            // .toString()
                            // + ","
                            // + listdate
                            // .get(j)
                            // .getAttribute(
                            // "personid")
                            // .toString());
                            // listdate.get(i).setAttribute(
                            // "persondesc",
                            // listdate.get(i)
                            // .getAttribute("persondesc")
                            // .toString()
                            // + ","
                            // + listdate
                            // .get(j)
                            // .getAttribute(
                            // "persondesc")
                            // .toString());
                            // }

                        } else {
                            // 表示有一个刷过卡，或者都没有刷过卡
                            listdate.get(i).setAttribute("personid", "");
                            listdate.get(i).setAttribute("persondesc", "");
                            listdate.get(i).setAttribute("sptime", "");
                        }
                        // 拼刷卡记录，这里不管刷卡不刷卡都要拼， 如果（单人刷卡）环节点对应的有值，表示已经刷过卡不进行新增
                        Object objzyspryjlid = listdate.get(i).getAttribute(
                                "strzyspryjlid");
                        if (objzyspryjlid != null
                                && objzyspryjlid.toString().length() > 0) {
                            listdate.get(i).setAttribute(
                                    "strzyspryjlid",
                                    objzyspryjlid
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zyspryjlid"));
                        } else {
                            listdate.get(i).setAttribute(
                                    "strzyspryjlid",
                                    listdate.get(i).getAttribute(
                                            "ud_zyxk_zyspryjlid")
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zyspryjlid"));
                        }

                        Object objzysqid = listdate.get(i).getAttribute(
                                "strzysqid");
                        if (objzysqid != null
                                && objzysqid.toString().length() > 0) {
                            listdate.get(i).setAttribute(
                                    "strzysqid",
                                    objzysqid
                                            + ",'"
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zysqid") + "'");
                        } else {
                            listdate.get(i).setAttribute(
                                    "strzysqid",
                                    "'"
                                            + listdate.get(i).getAttribute(
                                            "ud_zyxk_zysqid")
                                            + "','"
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zysqid") + "'");
                        }
                        Object objzyspqxid = listdate.get(i).getAttribute(
                                "strzyspqxid");
                        if (objzyspqxid != null
                                && objzyspqxid.toString().length() > 0) {
                            listdate.get(i).setAttribute(
                                    "strzyspqxid",
                                    objzyspqxid
                                            + ",'"
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zyspqxid") + "'");
                        } else {
                            listdate.get(i).setAttribute(
                                    "strzyspqxid",
                                    "'"
                                            + listdate.get(i).getAttribute(
                                            "ud_zyxk_zyspqxid")
                                            + "','"
                                            + listdate.get(j).getAttribute(
                                            "ud_zyxk_zyspqxid") + "'");
                        }
                        // lxf添加 审批环节点的编码
                        Object objzyspqxspfield = listdate.get(i).getAttribute(
                                "strzyspqxfiled");
                        if (objzyspqxspfield != null
                                && objzyspqxspfield.toString().length() > 0) {
                            listdate.get(i).setAttribute(
                                    "strzyspqxfiled",
                                    objzyspqxspfield
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "spfield"));
                        } else {
                            listdate.get(i).setAttribute(
                                    "strzyspqxfiled",
                                    listdate.get(i).getAttribute("spfield")
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "spfield"));
                        }
                        // 添加强制拍照权限
                        Object photoconfigur = listdate.get(i).getAttribute(
                                "photoconfigur");
                        Object photoconfigur2 = listdate.get(j).getAttribute(
                                "photoconfigur");
                        if (photoconfigur == null && photoconfigur2 != null) {
                            listdate.get(i).setAttribute("photoconfigur",
                                    photoconfigur2);
                        }

                        // lxf 移植到上边，放这里问题：如果只有一个审批就会去记录
                        /*
                         * Object objzyspryjlid = listdate.get(i).getAttribute(
						 * "strzyspryjlid"); if (objzyspryjlid != null &&
						 * objzyspryjlid.toString().length() > 0) {
						 * listdate.get(i).setAttribute( "strzyspryjlid",
						 * objzyspryjlid + "," + listdate.get(j).getAttribute(
						 * "ud_zyxk_zyspryjlid")); } else {
						 * listdate.get(i).setAttribute( "strzyspryjlid",
						 * listdate.get(i).getAttribute( "ud_zyxk_zyspryjlid") +
						 * "," + listdate.get(j).getAttribute(
						 * "ud_zyxk_zyspryjlid")); }
						 */
                        Object objisend = listdate.get(i).getAttribute(
                                "strisend");
                        if (objisend != null
                                && objisend.toString().length() > 0) {
                            listdate.get(i).setAttribute(
                                    "strisend",
                                    objisend
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "isend"));
                        } else {
                            listdate.get(i).setAttribute(
                                    "strisend",
                                    listdate.get(i).getAttribute("isend")
                                            + ","
                                            + listdate.get(j).getAttribute(
                                            "isend"));
                        }
                        // 如果都刷过
                        listdate.remove(j);
                        j--;
                    }
            }
        }
        // 表示把没有合并的环节进行相应的处理
        for (int i = 0; i < listdate.size(); i++) {
            if (listdate.get(i).getAttribute("strisend") == null
                    || listdate.get(i).getAttribute("strisend").toString() == "") {
                // 作业申请ID
                listdate.get(i).setAttribute(
                        "strzysqid",
                        "'" + listdate.get(i).getAttribute("ud_zyxk_zysqid")
                                + "'");
                // 作业审批权限id
                listdate.get(i).setAttribute(
                        "strzyspqxid",
                        "'" + listdate.get(i).getAttribute("ud_zyxk_zyspqxid")
                                + "'");
                // 作业审批记录id
                listdate.get(i).setAttribute("strzyspryjlid",
                        listdate.get(i).getAttribute("ud_zyxk_zyspryjlid"));
                // 环节点编码
                listdate.get(i).setAttribute("strzyspqxfiled",
                        listdate.get(i).getAttribute("spfield"));
                // isend
                listdate.get(i).setAttribute("strisend",
                        "" + listdate.get(i).getAttribute("isend"));
            }
            listdate.get(i).setDefaultpersonid(null);
            listdate.get(i).setDefaultpersondesc(null);
        }
        return listdate;
    }

    /**
     * TODO 气体检测复用
     * <p>
     * IQueryCallEventListener)
     */
    @SuppressWarnings("unchecked")
    @Override
    public GasDetection queryAndMultiplexGasInfo(SuperEntity workEntity,
                                                 SuperEntity workConfigEntity,
                                                 IQueryCallEventListener callEventListener,
                                                 List<WorkApprovalPermission> approvalPermission, String spCode)
            throws HDException {
        GasDetection gasDetection = null;
        if (callEventListener == null) {
            IConnectionSource connectionSource = null;
            IConnection connection = null;
            try {

                // 1.判断气体检测是否存在
                StringBuilder sbsql = new StringBuilder();
                String parentid;
                WorkOrder workorder = (WorkOrder) workEntity;
                PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) workConfigEntity;
                if (!workorder.getZypclass().equalsIgnoreCase(
                        IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
                    // 表示不是大票
                    parentid = workorder.getParent_id();
                } else {
                    parentid = workorder.getUd_zyxk_zysqid();
                }
                sbsql.append(
                        "select ud_zyxk_qtjcid,ud_zyxk_zysqid,jctime,jclocation,jclocation_desc,yxlimit,ishg,jcdept,jcmethod,sddwxmfzyj,seqnum,datasource,instrumentnum,jcperson,qrperson,audittime,ypfxqrperson,ypcjtime,sddwxmfzperson,writenbypda from ud_zyxk_qtjc ")
                        .append("where ishg = 1 and writenbypda = 'FINISH' ")
                        // 只有完成并且合格的检测结果才能被复用
                        .append("and ud_zyxk_zysqid in ")
                        .append("(select ud_zyxk_zysqid from ud_zyxk_zysq where parent_id='")
                        .append(parentid)
                        .append("' or (ifnull(parent_id,0) = 0 and ud_zyxk_zysqid='")
                        .append(parentid)
                        .append("')) order by jctime desc limit 1;");
                // 2.取出最新的气体检测
                gasDetection = (GasDetection) dao.executeQuery(
                        sbsql.toString(), new EntityResult(GasDetection.class));
                if (gasDetection == null) {
                    throw new HDException("没有查询到，可以复用的气体检测");
                }

                String domainSbSql = "select * from alndomain where domainid='UDJCMETHOD';";
                List domainList = (List) dao.executeQuery(domainSbSql,
                        new EntityListResult(Domain.class));
                gasDetection.setChild(Domain.class.getName(), domainList);

                sbsql.delete(0, sbsql.length());
                // 2.1获取子表数据数据
                sbsql.append(
                        "select * from ud_zyxk_qtjcline where ud_zyxk_qtjcid='")
                        .append(gasDetection.getUd_zyxk_qtjcid()).append("'");
                @SuppressWarnings("unchecked")
                List<SuperEntity> gasDetectSubList = (List<SuperEntity>) dao
                        .executeQuery(sbsql.toString(), new EntityListResult(
                                GasDetectSub.class));
                // 2.2查询审批记录表数据
                sbsql.delete(0, sbsql.length());
                if (approvalPermission != null) {
                    String spNode = "";
                    for (WorkApprovalPermission wap : approvalPermission) {
                        if (spNode.equals("")) {
                            spNode = "'" + wap.getSpfield() + "'";
                        } else {
                            spNode += ",'" + wap.getSpfield() + "'";
                        }
                    }
                    sbsql.append(
                            "select * from ud_zyxk_zyspryjl where spnode in (")
                            .append(spNode)
                            .append(") and tablename='UD_ZYXK_QTJC' and tableid='");
                } else {
                    // 表示没有环节点
                    sbsql.append("select * from ud_zyxk_zyspryjl where ")
                            .append(" tablename='UD_ZYXK_QTJC' and tableid='");
                }

                sbsql.append(gasDetection.getUd_zyxk_qtjcid()).append("'");
                @SuppressWarnings("unchecked")
                List<SuperEntity> workApprovalPersonRecordList = (List<SuperEntity>) dao
                        .executeQuery(sbsql.toString(), new EntityListResult(
                                WorkApprovalPersonRecord.class));

                // 记录作业审批人员记录ID
                List<String> zyspryjlids = new ArrayList<>();
                for (SuperEntity superEntity : workApprovalPersonRecordList) {
                    zyspryjlids.add((String) superEntity
                            .getAttribute("ud_zyxk_zyspryjlid"));
                }

                // 获取图片表的数据
                List<SuperEntity> images = null;
                StringBuilder sbtableid = new StringBuilder();
                for (SuperEntity entity : workApprovalPersonRecordList) {
                    if (entity.getAttribute("person").equals("图片签名")
                            && entity.getAttribute("person_name")
                            .equals("图片签名")) {
                        if (sbtableid.length() > 0) {
                            sbtableid.append(",");
                        }
                        sbtableid
                                .append("'")
                                .append(entity
                                        .getAttribute("ud_zyxk_zyspryjlid"))
                                .append("'");
                    }
                }
                if (sbtableid.length() > 0) {
                    sbsql.delete(0, sbsql.length());
                    sbsql.append(
                            "select * from hse_sys_image where tablename = 'ud_zyxk_zyspryjl' and tableid in(")
                            .append(sbtableid.toString()).append(");");
                    images = (List<SuperEntity>) dao
                            .executeQuery(sbsql.toString(),
                                    new EntityListResult(Image.class));
                }

                // 2.3修改数据
                gasDetection.setUd_zyxk_zysqid(workorder.getUd_zyxk_zysqid());
                gasDetection.setJctime(ServerDateManager.getCurrentTime());
                // 设置气体检测作业申请ID
                setObjectInfo(gasDetectSubList, new String[]{
                                "ud_zyxk_zysqid", "jctime"},
                        new String[]{
                                workorder.getUd_zyxk_zysqid(),
                                SystemProperty.getSystemProperty()
                                        .getSysDateTime()});

                if (gasDetectSubList != null && gasDetectSubList.size() > 0) {
                    gasDetection.setChild(GasDetectSub.class.getName(),
                            gasDetectSubList);
                }
                SequenceGenerator.genPrimaryKeyValue(gasDetection,
                        GasDetectSub.class);
                if (workApprovalPersonRecordList != null
                        && workApprovalPersonRecordList.size() > 0) {
                    SuperEntity[] superArray = new WorkApprovalPersonRecord[workApprovalPersonRecordList
                            .size()];
                    SequenceGenerator
                            .genPrimaryKeyValue(workApprovalPersonRecordList
                                    .toArray(superArray));
                    for (SuperEntity entity : workApprovalPersonRecordList) {
                        if (spCode.equals(IConfigEncoding.SP)) {
                            entity.setAttribute("dycode",
                                    IConfigEncoding.SP_GAS_NUM);
                            entity.setAttribute("type", IConfigEncoding.SP);
                        } else if (spCode.equals(IConfigEncoding.FC)) {
                            entity.setAttribute("dycode",
                                    IConfigEncoding.FC_GAS_NUM);
                            entity.setAttribute("type", IConfigEncoding.FC);
                        }
                    }
                }
                // 设置人员审批记录表作业申请ID
                setObjectInfo(workApprovalPersonRecordList, new String[]{
                                "ud_zyxk_zysqid", "tableid"},
                        new String[]{workorder.getUd_zyxk_zysqid(),
                                gasDetection.getUd_zyxk_qtjcid()});
                if (images != null) {
                    for (SuperEntity image : images) {
                        String tableid = (String) image.getAttribute("tableid");
                        int i = zyspryjlids.indexOf(tableid);
                        image.setAttribute("tableid",
                                (String) workApprovalPersonRecordList.get(i)
                                        .getAttribute("ud_zyxk_zyspryjlid"));
                        // 设置跟在那个作业票 （业务）下边（没有改导致没有上传）
                        image.setAttribute("zysqid",
                                workorder.getUd_zyxk_zysqid());
                        String oldPath = (String) image
                                .getAttribute("imagepath");
                        String newName = ServerDateManager.getCurrentTimeMillis() + i + ".jpg";
                        // String newPath =
                        // Environment.getExternalStorageDirectory()
                        // .getPath() + "/zyxkapp/pic/" + newName;
                        String newPath = SystemProperty.getSystemProperty()
                                .getRootDBpath()
                                + File.separator
                                + "pic"
                                + File.separator + newName;
                        try {
                            File outFile = new File(newPath);
                            InputStream fosfrom = new FileInputStream(oldPath);
                            OutputStream fosto = new FileOutputStream(outFile);
                            if (!outFile.exists()) {
                                outFile.createNewFile();
                            }
                            byte bt[] = new byte[1024];
                            int c;
                            while ((c = fosfrom.read(bt)) > 0) {
                                fosto.write(bt, 0, c);
                            }
                            fosfrom.close();
                            fosto.close();
                            image.setAttribute("imagepath", newPath);
                            image.setAttribute("imageName", newName);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                    if (images != null && images.size() != 0) {
                        SuperEntity[] superEntities = new Image[images.size()];
                        SequenceGenerator.genPrimaryKeyValue(images
                                .toArray(superEntities));
                    }
                }
                // 验证气体检测是否 合格
                // boolean ishg = isQtjchg(gasDetectSubList);
                // if (ishg) {
                // gasDetection.setIshg(1);
                // } else {
                // gasDetection.setIshg(0);
                // }
                connectionSource = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
                connection = connectionSource.getConnection();

                // 3.新增气体检测、审批记录
                dao.insertEntity(connection, gasDetection);
                // 保存气体检测子表
                if (gasDetectSubList != null && gasDetectSubList.size() > 0) {
                    dao.insertEntities(connection, gasDetectSubList);
                }
                // 保存审批记录表数据
                if (workApprovalPersonRecordList != null
                        && workApprovalPersonRecordList.size() > 0) {
                    dao.insertEntities(connection, workApprovalPersonRecordList);
                }

                if (images != null && images.size() > 0) {
                    dao.insertEntities(connection, images);
                }

                // connection.commit();
                // 判断气体检测最终刷卡人有没有刷卡
                boolean isendok = isEndOk(connection, workorder,
                        workConfig.getDycode(),
                        gasDetection.getUd_zyxk_qtjcid());
                if (!isendok) {
                    // 表示最终刷卡人没有刷卡，此时需要修改气体检测状态
                    gasDetection
                            .setWritenbypda(IWorkOrderStatus.GAS_STATUS_PROCEED);
                    dao.updateEntity(connection, gasDetection,
                            new String[]{"writenbypda"});
                } else {
                    // 表示最终刷卡人已经刷卡
                    // 4.保存气体检测到作业票信息
                    if (spCode.equals(IConfigEncoding.SP)) {
                        // 表示审批功能
                        workorder.setNeedupload(1);
                        workorder.setIsupload(0);
                        if (workorder.getCssavefied() == null) {
                            workorder.setCssavefied(workConfig.getContype()
                                    .toString());
                        } else {
                            workorder.setCssavefied(workorder.getCssavefied()
                                    + "," + workConfig.getContype());
                        }
                        dao.updateEntity(connection, workorder, new String[]{
                                "cssavefied", "needupload", "isupload"});
                    } else {
                        // 表示气体检测延期功能
                        workorder.setIsupload(0);
                        workorder.setSpstatus(IWorkOrderStatus.SPSTATUS_REVIEW);
                        dao.updateEntity(connection, workorder, new String[]{
                                "status", "spstatus", "isupload", "yyqcount"});
                    }
                }
                connection.commit();

                StringBuilder unDoSbSql = new StringBuilder();
                // unDoSbSql.setLength(0);
                unDoSbSql
                        .append("select aln.domainid as domainid,(case when ifnull(line.qttype,'')='' then aln.domainid||'|'||aln.value else line.qttype end) as qtlx,line.qtvalue as qtvalue,aln.description as description,");
                unDoSbSql
                        .append(" aln.maxvalue as maxvalue,aln.minvalue as minvalue,aln.isbj as isbj from (select domainid,description,value,maxvalue,minvalue,isbj from");
                unDoSbSql
                        .append(" (select domainid,description,value from alndomain where domainid in (select domainid||value from alndomain where domainid = 'UDQTTYPE' order by value)) as alntable left join ");
                unDoSbSql
                        .append(" (select qtname, main_tab.qtlx,qtpz.maxvalue as maxvalue, qtpz.minvalue as minvalue,qtpz.isbj as isbj from ud_zyxk_qtjcpz qtpz inner join");
                unDoSbSql
                        .append(" (select (aln.domainid || aln.value) as qtlx,pz.qtlx as main_lx,pz.qtname as son_lx from ALNDOMAIN aln inner join  UD_ZYXK_QTJCPZ pz  on pz.QTLX=aln.value");
                unDoSbSql
                        .append(" where aln.domainid='UDQTTYPE' order by value ) as main_tab on qtpz.qtlx=main_tab.main_lx and qtpz.qtname=main_tab.son_lx) as alnpz ");
                unDoSbSql
                        .append(" on alntable.domainid=alnpz.qtlx and alntable.description=alnpz.qtname) as aln left join");
                unDoSbSql
                        .append(" (select qttype, qtvalue from ud_zyxk_qtjcline where ud_zyxk_qtjcid");
                unDoSbSql.append(" ='")
                        .append(gasDetection.getUd_zyxk_qtjcid()).append("' )");
                unDoSbSql
                        .append("  as line on (aln.domainid||'|'||aln.value)=line.qttype order by aln.domainid asc,line.qtvalue desc,value asc;");
                List<List<GasDetectSub>> gasLineList = (List<List<GasDetectSub>>) dao
                        .executeQuery(connection, unDoSbSql.toString(),
                                new ListListResult(GasDetectSub.class,
                                        "domainid"));
                gasDetection.setListChild(GasDetectSub.class.getName(),
                        (List) gasLineList);
                // List domainList = (List)
                // dao.executeQuery(connection,domainSbSql
                // .toString(), new EntityListResult(Domain.class));
                // gasDetection.setChild(Domain.class.getName(), domainList);

                // connection.commit();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                logger.error(e.getMessage(), e);
                throw new DaoException("气体检测复用报错!", e);
            } finally {
                if (connectionSource != null) {
                    try {
                        connectionSource.releaseConnection(connection);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        logger.error(e.getMessage(), e);
                        throw new DaoException(e.getMessage(), e);
                    }
                }
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryAndMultiplexGasInfo",
                    new Class<?>[]{SuperEntity.class, SuperEntity.class,
                            IQueryCallEventListener.class, List.class,
                            String.class},
                    new Object[]{workEntity, workConfigEntity, null,
                            approvalPermission, spCode});
        }
        return gasDetection;
    }

    /**
     * setObjectInfo:(设置集合对象的值). <br/>
     * date: 2015年9月10日 <br/>
     *
     * @param listSuper
     * @param attr
     * @param value
     * @author lxf
     */
    private void setObjectInfo(List<SuperEntity> listSuper, String attr,
                               String value) {
        if (listSuper != null && listSuper.size() > 0) {
            for (SuperEntity superEntity : listSuper) {
                superEntity.setAttribute(attr, value);
            }
        }
    }

    /**
     * setObjectInfo:(设置集合对象的值). <br/>
     * date: 2015年9月10日 <br/>
     *
     * @param listSuper
     * @param attr
     * @param value
     * @author lxf
     */
    private void setObjectInfo(List<SuperEntity> listSuper, String[] attr,
                               String[] value) {
        if (attr.length != value.length) {
            return;
        }
        int length = attr.length;
        if (listSuper != null && listSuper.size() > 0) {
            for (SuperEntity superEntity : listSuper) {
                for (int i = 0; i < length; i++) {
                    superEntity.setAttribute(attr[i], value[i]);
                }
            }
        }
    }

    /**
     * isQtjchg:(判断气体检测是否合格). <br/>
     * date: 2015年9月10日 <br/>
     *
     * @param childList
     * @return
     * @author lxf
     */
    private boolean isQtjchg(List<SuperEntity> childList) {
        boolean ishg = false;
        for (int i = 0; i < childList.size(); i++) {
            GasDetectSub childGas = (GasDetectSub) childList.get(i);
            if (childGas.getQtvalue() != null
                    && !StringUtils.isEmpty(childGas.getQtvalue().toString())) {
                if (!UtilTools
                        .isRangeIn(
                                childGas.getMaxvalue() == null ? "" : childGas
                                        .getMaxvalue().toString(),
                                childGas.getQtvalue(),
                                childGas.getMinvalue() == null ? "" : childGas
                                        .getMinvalue().toString(),
                                (childGas.getIsbj() == null
                                        || StringUtils.isEmpty(childGas
                                        .getIsbj().toString()) || childGas
                                        .getIsbj() == 0) ? false : true)) {
                    ishg = false;
                    break;
                } else {
                    ishg = true;
                }
            }
        }
        return ishg;
    }

    /**
     * isEndOk:(表示判断最终刷卡人数会否刷卡). <br/>
     * date: 2015年9月10日 <br/>
     *
     * @param workOrder
     * @param qtjcid
     * @return
     * @throws DaoException
     * @author lxf
     */
    private boolean isEndOk(IConnection connection, WorkOrder workOrder,
                            String dycode, String qtjcid) throws DaoException {
        boolean ret = false;
        if (workOrder == null) {
            return ret;
        }
        StringBuilder sbsql = new StringBuilder();
        sbsql.append(
                "select ud_zyxk_zyspryjlid from ud_zyxk_zyspryjl where ud_zyxk_zysqid='")
                .append(workOrder.getUd_zyxk_zysqid())
                .append("' and tablename='UD_ZYXK_QTJC' and dycode='")
                .append(dycode)
                .append("' and tableid='")
                .append(qtjcid)
                .append("' and spnode in(")
                .append("select spfield from ud_zyxk_zyspqx where isend=1 and dr=0  and zylocation='")
                .append(dycode)
                .append("' and ','|| wttype ||',' like '%,' || '")
                .append(workOrder.getZypclass()).append("' || ',%'");
        if (workOrder.getZylevel() != null
                && workOrder.getZylevel().length() > 0) {
            sbsql.append(" and (wtlevel='").append(workOrder.getZylevel())
                    .append("'").append(" or ifnull(wtlevel,'')='')");
        }
        sbsql.append(");");
        @SuppressWarnings("unchecked")
        HashMap<String, Object> mapRestult = (HashMap<String, Object>) dao
                .executeQuery(connection, sbsql.toString(), new MapResult());
        if (mapRestult != null && mapRestult.size() > 0) {
            ret = true;
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Image> querySignImgUrls(String tableName, String[] tableIds)
            throws HDException {
        List<Image> images = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select * from hse_sys_image where tablename = '")
                .append(tableName).append("'");
        for (int i = 0; i < tableIds.length; i++) {
            String id = tableIds[i];
            if (i == 0) {
                sql.append(" and (tableid like '%").append(id).append("%'");
            } else {
                sql.append(" or tableid like '%").append(id).append("%'");
            }
        }
        sql.append(")");
        images = (List<Image>) executeQuery(sql.toString(),
                new EntityListResult(Image.class));
        return images;
    }

    /**
     * queryDomain:(查询查询动态域). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    @Override
    public List<Domain> queryDomain(String domain,
                                    IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            String sql = "select * from alndomain where domainid in('" + domain
                    + "') and value <> 'zylx00';";
            try {
                @SuppressWarnings("unchecked")
                List<Domain> workTypes = (List<Domain>) dao.executeQuery(sql,
                        new EntityListResult(Domain.class));
                return workTypes;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDomain", new Class<?>[]{
                            String.class, IQueryCallEventListener.class},
                    new Object[]{domain, null});
        }
        return null;
    }


    /**
     * queryDomain:(查询查询动态域). <br/>
     * date: 2018年05月17日 <br/>
     *
     * @param callEventListener
     * @return
     * @throws HDException
     * @author DuBoJian
     * 解决现场监督得到的数据为code，要显示description
     */
    @Override
    public List<Domain> queryDomain(String domain, String value,
                                    IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            String sql = "select * from alndomain where domainid in('" + domain
                    + "') and value='" + value + "';";
            try {
                @SuppressWarnings("unchecked")
                List<Domain> workTypes = (List<Domain>) dao.executeQuery(sql,
                        new EntityListResult(Domain.class));
                return workTypes;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDomain", new Class<?>[]{
                            String.class, IQueryCallEventListener.class},
                    new Object[]{domain, null});
        }
        return null;
    }

    /**
     * queryTJUrls:(通过code查询统计分析的URL). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param code
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    @Override
    public List<Domain> queryTJUrls(String code,
                                    IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            String sql = "select * from alndomain where domainid = 'UDRQURL' and value in ("
                    + code + ");";
            try {
                @SuppressWarnings("unchecked")
                List<Domain> workTypes = (List<Domain>) dao.executeQuery(sql,
                        new EntityListResult(Domain.class));
                return workTypes;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryTJUrls", new Class<?>[]{
                            String.class, IQueryCallEventListener.class},
                    new Object[]{code, null});
        }
        return null;
    }

    /**
     * queryAppModule:(根据type查询AppModule). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param type
     * @param callEventListener
     * @return
     * @throws HDException
     * @author LiuYang
     */
    @Override
    public List<AppModule> queryAppModule(String type,
                                          IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            String sql = "select * from hse_sys_appmodule where type = '"
                    + type + "' order by layoutorder;";
            try {
                @SuppressWarnings("unchecked")
                List<AppModule> appModules = (List<AppModule>) dao
                        .executeQuery(sql,
                                new EntityListResult(AppModule.class));
                return appModules;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询信息失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryAppModule", new Class<?>[]{
                            String.class, IQueryCallEventListener.class},
                    new Object[]{type, null});
        }
        return null;
    }

    @Override
    public List<AppAnlsCfg> queryAppAnlsCfg() throws HDException {
        String sql = "select * from ud_sys_appanlscfg ";
        List<AppAnlsCfg> appAnlsCfg = null;
        try {
            appAnlsCfg = (List<AppAnlsCfg>) dao.executeQuery(sql,
                    new EntityListResult(AppAnlsCfg.class));

        } catch (DaoException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new DaoException("查询信息失败！");
        }
        return appAnlsCfg;
    }

    /**
     * 查询部门列表 仅查询当前部门以下的部门 queryDept:(). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @author LiuYang
     */
    @SuppressWarnings("unchecked")
    public List<Department> queryDept(String currentDeptNum,
                                      IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            try {
                String sql = "select vreportdeptnum from ud_sy_dept where parent = 'ZZJG';";
                List<Department> department = (List<Department>) dao
                        .executeQuery(sql, new EntityListResult(
                                Department.class));
                if (department == null || department.size() == 0) {
                    sql = "select * from ud_sy_dept;";
                } else {
                    sql = "select * from ud_sy_dept where parent = 'ZZJG'"
                            + " or parent in (select vreportdeptnum from ud_sy_dept where parent = 'ZZJG');";
                }
                department = (List<Department>) dao.executeQuery(sql,
                        new EntityListResult(Department.class));
                return department;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询部门列表失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDept",
                    new Class<?>[]{IQueryCallEventListener.class},
                    new Object[]{null});
        }
        return null;
    }

    /**
     * 查询部门列表 仅查询当前部门以下的部门 queryDept:(). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @author LiuYang
     */
    @SuppressWarnings("unchecked")
    public List<Department> queryDept(boolean isquery,
                                      IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            try {
                String sql = "select * from ud_sy_dept where dr = 0 and isuseful = 1";
                if (isquery) {
                    sql += " and isquery = 1 order by px;";
                } else {
                    sql += " order by px;";
                }
                // Log.e("Department", sql);
                List<Department> department = (List<Department>) dao
                        .executeQuery(sql, new EntityListResult(
                                Department.class));
                return department;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询部门列表失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDept",
                    new Class<?>[]{IQueryCallEventListener.class},
                    new Object[]{null});
        }
        return null;
    }

    /**
     * 从hse专业详细定义里查主管科室,所属专业
     *
     * @return
     */
    public List<ZyxxTableName> queryDeptFromhdrcgl() {

        String sql = "select distinct zrcs,zrcscode from ud_hsebz_zyxx";

        List<ZyxxTableName> department = null;
        try {
            department = (List<ZyxxTableName>) dao
                    .executeQuery(sql, new EntityListResult(
                            ZyxxTableName.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return department;

    }

    /**
     * 从hse专业详细定义里查所属专业
     *
     * @return
     */
    public List<ZyxxTableName> queryZyFromhdrcgl(String mr) {

        String sql = null;
        if ("".equals(mr)) {
            sql = "select distinct zyname from ud_hsebz_zyxx";
        } else {
            sql = "select distinct zyname from ud_hsebz_zyxx where zrcscode=" + "'" + mr + "'";
        }

        List<ZyxxTableName> department = null;
        try {
            department = (List<ZyxxTableName>) dao
                    .executeQuery(sql, new EntityListResult(
                            ZyxxTableName.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return department;

    }

    /**
     * 查询部门列表 仅查询当前部门以下的部门 queryDept:(). <br/>
     * date: 2017年3月9日 <br/>
     *
     * @throws HDException
     * @author yn
     */
    public List<Department> queryDept(String currentDeptNum) throws HDException {
        String sql = "select * from ud_sy_dept where parent = '"
                + currentDeptNum + "'";
        try {
            List<Department> department = (List<Department>) dao.executeQuery(
                    sql, new EntityListResult(Department.class));
            return department;
        } catch (DaoException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new DaoException("查询部门列表失败！");
        }
    }

    /**
     * 查询传入部门的父级部门 queryDefaultDept:(). <br/>
     * date: 2015年11月19日 <br/>
     *
     * @param callEventListener
     * @return
     * @author LiuYang
     */
    @Override
    public Department queryDefaultDept(String currentDeptNum,
                                       IQueryCallEventListener callEventListener) throws HDException {
        if (callEventListener == null) {
            try {
                String sql = "select * from ud_sy_dept where deptnum = (select parent from ud_sy_dept where deptnum = '"
                        + currentDeptNum + "' and isgetparent = 1);";
                Department department = (Department) dao.executeQuery(sql,
                        new EntityResult(Department.class));
                return department;
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询父级部门失败！");
            }
        } else {
            // 异步加载
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    callEventListener);
            asyncQueryHandler.execute(this, "queryDefaultDept", new Class<?>[]{
                            String.class, IQueryCallEventListener.class},
                    new Object[]{currentDeptNum, null});
        }
        return null;
    }

    @Override
    public String queryIPAndPort(IQueryCallEventListener queryCallEventListener)
            throws HDException {
        if (queryCallEventListener == null) {
            String sql = "select sysurl from hse_sys_config where syscode in ('inurl', 'outurl') and enable = 1";
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) dao.executeQuery(
                    sql, new MapResult());
            if (map != null && map.size() != 0) {
                String url = map.get("sysurl").toString();
                return url;
            }
        } else {
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    queryCallEventListener);
            asyncQueryHandler.execute(this, "queryIPAndPort",
                    new Class<?>[]{IQueryCallEventListener.class},
                    new Object[]{null});
        }
        return null;
    }

    @Override
    public String[] querySignInfoItemByItem(String sqId, String opId)
            throws HDException {
        String sql = "select ud_id from getstepconfirmpernew where tableid = '"
                + sqId + "' and opid = '" + opId + "';";
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) dao
                    .executeQuery(sql, new MapListResult());
            List<String> list = new ArrayList<>();
            for (Map<String, Object> map : mapList) {
                list.add((String) map.get("ud_id"));
            }
            String[] strs = new String[list.size()];
            strs = list.toArray(strs);
            return strs;
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询失败！");
        }
    }

    /**
     * 查询能量隔离单主表和子表数据
     * <p>
     * Created by liuyang on 2016年2月17日
     *
     * @param ud_zyxk_zysqid
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public EnergyIsolation queryEnergyIsolation(String ud_zyxk_zysqid,
                                                IQueryCallEventListener queryCallEventListener) throws HDException {
        if (queryCallEventListener == null) {
            try {
                String sql = "select ud_zyxk_nlgldid, glasset, glstatus, status, hazard, nlgldnum, isupload from ud_zyxk_nlgld d"
                        + " where d.nlgldnum = (select nlgldnum from ud_zyxk_zysq where ud_zyxk_zysqid = '"
                        + ud_zyxk_zysqid + "');";
                EnergyIsolation ei = (EnergyIsolation) dao.executeQuery(sql,
                        new EntityResult(EnergyIsolation.class));
                if (ei != null && ei.getUd_zyxk_nlgldid() != 0) {
                    sql = "select * from ud_zyxk_nlgldline l where l.ud_zyxk_nlgldid = "
                            + ei.getUd_zyxk_nlgldid() + ";";
                    @SuppressWarnings("unchecked")
                    List<SuperEntity> details = (List<SuperEntity>) dao
                            .executeQuery(sql, new EntityListResult(
                                    EnergyIsolationDetail.class));
                    ei.setChild("details", details);
                    return ei;
                }
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询失败！");
            }
        } else {
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    queryCallEventListener);
            asyncQueryHandler.execute(this, "queryEnergyIsolation",
                    new Class<?>[]{String.class,
                            IQueryCallEventListener.class}, new Object[]{
                            ud_zyxk_zysqid, null});
        }
        return null;
    }

    /**
     * 查询危害识别tabs
     * <p>
     * Created by liuyang on 2016年3月9日
     *
     * @param workOrder
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public List<PDAWorkOrderInfoConfig> queryHarmTabConfigs(
            WorkOrder workOrder, IQueryCallEventListener queryCallEventListener)
            throws HDException {
        if (queryCallEventListener == null) {
            try {
                String sql = "select sname,	pscode, contype, contypelie, dycode, otherisenable,	otherfield,	cbisenable,	cbname,"
                        + " cbtype,	zypclass, cstype, isorder, tab_order, issavebtn, changedate, isactive, conlevel, valuewheretype,"
                        + " itemtype, dr, tag, scode, contypedesc, batappr from ud_zyxk_zysqpdasc a where a.zypclass = '"
                        + workOrder.getZypclass()
                        + "' and a.cstype in (select domain.value  from alndomain domain where domain.domainid = 'UDWHTYPE') and isactive = 1;";
                @SuppressWarnings("unchecked")
                List<PDAWorkOrderInfoConfig> list = (List<PDAWorkOrderInfoConfig>) dao
                        .executeQuery(sql, new EntityListResult(
                                PDAWorkOrderInfoConfig.class));
                return list;
            } catch (HDException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询危害识别类型列表失败！");
            }
        } else {
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    queryCallEventListener);
            asyncQueryHandler.execute(this, "queryHarmTabConfigs",
                    new Class<?>[]{WorkOrder.class,
                            IQueryCallEventListener.class}, new Object[]{
                            workOrder, null});
        }
        return null;
    }

    /**
     * 查询用于合并会签拍照的大票信息
     * <p>
     * Created by liuyang on 2016年7月12日
     *
     * @param workOrders
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    public List<WorkOrder> queryWorkOrderListForCamera(
            List<WorkOrder> workOrders,
            IQueryCallEventListener queryCallEventListener) throws HDException {
        if (queryCallEventListener == null) {
            try {
                StringBuilder orderIds = new StringBuilder();
                for (WorkOrder w : workOrders) {
                    orderIds.append("'").append(w.getUd_zyxk_zysqid())
                            .append("',");
                }
                orderIds.setLength(orderIds.length() - 1);
                StringBuilder sql = new StringBuilder();
                sql.append(
                        "select ud_zyxk_zysqid from ud_zyxk_zysq where (ud_zyxk_zysqid in(")
                        .append(orderIds)
                        .append(") and parent_id is null) or ud_zyxk_zysqid in (select parent_id from ud_zyxk_zysq where ud_zyxk_zysqid in(")
                        .append(orderIds)
                        .append(") and parent_id is not null);");
                @SuppressWarnings("unchecked")
                List<WorkOrder> list = (List<WorkOrder>) dao.executeQuery(
                        sql.toString(), new EntityListResult(WorkOrder.class));
                return list;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询作业大票列表失败！");
            }
        } else {
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    queryCallEventListener);
            asyncQueryHandler
                    .execute(this, "queryWorkOrderListForCamera",
                            new Class<?>[]{List.class,
                                    IQueryCallEventListener.class},
                            new Object[]{workOrders, null});
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> queryTempEleInfo(WorkOrder workOrder,
                                                IQueryCallEventListener queryCallEventListener) throws HDException {
        if (queryCallEventListener == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            try {
                StringBuilder builder = new StringBuilder();
                builder.append(
                        "select l.* from ud_zyxk_lsydzy l where l.ud_zyxk_zysqid = '")
                        .append(workOrder.getUd_zyxk_zysqid()).append("';");
                TempEleZy tempEleZy = (TempEleZy) dao.executeQuery(
                        builder.toString(), new EntityResult(TempEleZy.class));
                map.put(TempEleZy.class.getName(), tempEleZy);
                builder.setLength(0);
                builder.append(
                        "select a.* from ud_zyxk_ydasset a where a.ud_zyxk_zysqid = '")
                        .append(workOrder.getUd_zyxk_zysqid()).append("';");
                List<TempEleAsset> assets = (List<TempEleAsset>) dao
                        .executeQuery(builder.toString(), new EntityListResult(
                                TempEleAsset.class));
                map.put(TempEleAsset.class.getName(), assets);
                builder.setLength(0);
                builder.append(
                        "select s.*, a.count from ud_zyxk_ydsb s left join ud_zyxk_ydasset a on s.ud_zyxk_ydsbid = a.ud_zyxk_ydsbid and a.ud_zyxk_zysqid = '")
                        .append(workOrder.getUd_zyxk_zysqid()).append("';");
                List<TempEleDevice> devices = (List<TempEleDevice>) dao
                        .executeQuery(builder.toString(), new EntityListResult(
                                TempEleDevice.class));
                map.put(TempEleDevice.class.getName(), devices);
                return map;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询已选用电设备列表失败！");
            }
        }
        return null;
    }

    /**
     * 查询优先级（一票制） Created by liuyang on 2016年9月18日
     *
     * @param workOrder
     * @param queryCallEventListener
     * @return
     * @throws HDException
     */
    @Override
    public PriorityLine queryPriority(WorkOrder workOrder,
                                      IQueryCallEventListener queryCallEventListener) throws HDException {
        if (queryCallEventListener == null) {
            try {
                String sql = "select * from ud_zyxk_prtyline order by priority;";
                List<PriorityLine> priorities = (List<PriorityLine>) dao
                        .executeQuery(sql, new EntityListResult(
                                PriorityLine.class));
                sql = "select zyptype,dhlevel,sxkjlevel,sxlevel,gclevel from ud_zyxk_zysq where ud_zyxk_zysqid = '"
                        + workOrder.getUd_zyxk_zysqid() + "';";
                WorkOrder order = (WorkOrder) dao.executeQuery(sql,
                        new EntityResult(WorkOrder.class));
                String[] zypTypes = order.getZyptype().split(",");
                for (PriorityLine priority : priorities) {
                    String zypclass = priority.getZypclass();
                    for (String string : zypTypes) {
                        if (zypclass.contains(string)) {
                            if (string.equals(IWorkOrderZypClass.ZYPCLASS_DHZY)
                                    && priority.getZyplevel() != null
                                    && !priority.getZyplevel().equals("")) {
                                String worklevel = order.getDhlevel() == null ? ""
                                        : order.getDhlevel();
                                String zyplevel = priority.getZyplevel() == null ? ""
                                        : priority.getZyplevel();
                                if (worklevel.equals(zyplevel)) {
                                    return priority;
                                }
                            } else if (string
                                    .equals(IWorkOrderZypClass.ZYPCLASS_SXKJ)
                                    && priority.getZyplevel() != null
                                    && !priority.getZyplevel().equals("")) {
                                String worklevel = order.getSxkjlevel() == null ? ""
                                        : order.getSxkjlevel();
                                String zyplevel = priority.getZyplevel() == null ? ""
                                        : priority.getZyplevel();
                                if (worklevel.equals(zyplevel)) {
                                    return priority;
                                }
                            } else if (string
                                    .equals(IWorkOrderZypClass.ZYPCLASS_SXKJ02)
                                    && priority.getZyplevel() != null
                                    && !priority.getZyplevel().equals("")) {
                                String worklevel = order.getSxkjlevel() == null ? ""
                                        : order.getSxkjlevel();
                                String zyplevel = priority.getZyplevel() == null ? ""
                                        : priority.getZyplevel();
                                if (worklevel.equals(zyplevel)) {
                                    return priority;
                                }
                            } else if (string
                                    .equals(IWorkOrderZypClass.ZYPCLASS_SXZY)
                                    && priority.getZyplevel() != null
                                    && !priority.getZyplevel().equals("")) {
                                String worklevel = order.getSxlevel() == null ? ""
                                        : order.getSxlevel();
                                String zyplevel = priority.getZyplevel() == null ? ""
                                        : priority.getZyplevel();
                                if (worklevel.equals(zyplevel)) {
                                    return priority;
                                }
                            } else if (string
                                    .equals(IWorkOrderZypClass.ZYPCLASS_GCZY)
                                    && priority.getZyplevel() != null
                                    && !priority.getZyplevel().equals("")) {
                                String worklevel = order.getGclevel() == null ? ""
                                        : order.getGclevel();
                                String zyplevel = priority.getZyplevel() == null ? ""
                                        : priority.getZyplevel();
                                if (worklevel.equals(zyplevel)) {
                                    return priority;
                                }
                            } else {
                                return priority;
                            }
                        }
                    }
                }
            } catch (DaoException e) {
                logger.error(e.getMessage(), e);
                throw new DaoException("查询优先级列表失败！");
            }
        } else {
            AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
                    queryCallEventListener);
            asyncQueryHandler.execute(this, "queryPriority", new Class<?>[]{
                            WorkOrder.class, IQueryCallEventListener.class},
                    new Object[]{workOrder, null});
        }
        return null;

    }

    public List<WorkOrder> separateWorkorder(WorkOrder workOrder)
            throws HDException {
        try {
            String sql = "select * from ud_zyxk_prtyline order by priority;";
            List<PriorityLine> priorities = (List<PriorityLine>) dao
                    .executeQuery(sql, new EntityListResult(PriorityLine.class));
            sql = "select sddept,ud_zyxk_zysqid,zyptype,dhlevel,sxkjlevel,sxlevel,gclevel from ud_zyxk_zysq where ud_zyxk_zysqid = '"
                    + workOrder.getUd_zyxk_zysqid() + "';";
            WorkOrder order = (WorkOrder) dao.executeQuery(sql,
                    new EntityResult(WorkOrder.class));
            String[] zypTypes = order.getZyptype().split(",");
            List<WorkOrder> orderPriority = new ArrayList<>();
            for (PriorityLine priority : priorities) {
                String zypclass = priority.getZypclass();
                String zyplevel = priority.getZyplevel() == null ? ""
                        : priority.getZyplevel();
                for (String zyptype : zypTypes) {
                    String zypgrade = "";
                    if (zyptype.equals(IWorkOrderZypClass.ZYPCLASS_DHZY)) {
                        // 动火作业
                        zypgrade = order.getDhlevel() == null ? "" : order
                                .getDhlevel();
                    } else if (zyptype.equals(IWorkOrderZypClass.ZYPCLASS_SXKJ)) {
                        // 受限空间
                        zypgrade = order.getSxkjlevel() == null ? "" : order
                                .getSxkjlevel();
                    } else if (zyptype
                            .equals(IWorkOrderZypClass.ZYPCLASS_SXKJ02)) {
                        // 受限空间02
                        zypgrade = order.getSxkjlevel() == null ? "" : order
                                .getSxkjlevel();
                    } else if (zyptype.equals(IWorkOrderZypClass.ZYPCLASS_SXZY)) {
                        // 射线作业
                        zypgrade = order.getSxlevel() == null ? "" : order
                                .getSxlevel();
                    } else if (zyptype.equals(IWorkOrderZypClass.ZYPCLASS_GCZY)) {
                        // 高处作业
                        zypgrade = order.getGclevel() == null ? "" : order
                                .getGclevel();
                    } else {
                        // 其他
                        zypgrade = "";
                    }
                    if (zypclass.contains(zyptype)
                            && (zypgrade.equals(zyplevel) || zyplevel
                            .equals(""))) {
                        try {
                            WorkOrder workOrder2 = (WorkOrder) order.clone();
                            workOrder2.setZypclass(zyptype);
                            workOrder2.setZylevel(zypgrade);
                            orderPriority.add(workOrder2);
                        } catch (CloneNotSupportedException e) {
                            logger.error(e.getMessage(), e);
                            throw new DaoException("查询优先级列表失败！");
                        }
                    }
                }
            }
            return orderPriority;
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询优先级列表失败！");
        }

    }

}
