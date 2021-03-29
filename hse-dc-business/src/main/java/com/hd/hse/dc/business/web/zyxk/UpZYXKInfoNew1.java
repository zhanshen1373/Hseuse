package com.hd.hse.dc.business.web.zyxk;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dc.business.common.weblistener.up.UpListenerNew1;
import com.hd.hse.dc.business.listener.common.CommonSql;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UpZYXKInfoNew1 extends UpListenerNew1 {

    private static Logger logger = LogUtils.getLogger(UpZYXKInfoNew1.class);
    private static List<TableDesc> listTableRelation = null;
    private StringBuilder strid = new StringBuilder();
    private StringBuilder stridNoBack = new StringBuilder();
    /**
     * upStrid:TODO(记录上传主表数据的主键).
     */
    private String upStrid = null;
    /**
     * yn2016/10/11
     * 获取imei所需要的上下文
     */
    private Context context;

    public UpZYXKInfoNew1(Context context) {
        this.context = context;
    }

    @Override
    public List<TableDesc> getRelation() {
        // 配置下载Json表的关系
        if (null == listTableRelation) {
            listTableRelation = new ArrayList<TableDesc>();

            TableDesc tb = null;
            // 气体检测子表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_qtjcline");
            tb.setPrimarykey("ud_zyxk_qtjclineid");
            tb.setForeignkey("ud_zyxk_qtjcid");
            tb.setForeignMainTable("ud_zyxk_qtjc");
            listTableRelation.add(tb);
            // 气体检测表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_qtjc");
            tb.setPrimarykey("ud_zyxk_qtjcid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);

            // 措施表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zysq2precaution");
            tb.setPrimarykey("ud_zyxk_zysq2precautionid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 个人防护装备表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zysq2ud_zyxk_grfhzb");
            tb.setPrimarykey("ud_zyxk_zysq2ud_zyxk_grfhzbid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 危害识别表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zysq2hazard");
            tb.setPrimarykey("ud_zyxk_zysq2hazardid");//ud_zyxk_zysq2hazardid hazardid
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 挖掘隐藏措施 UD_ZYXK_YCSSQK
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_ycssqk");
            tb.setPrimarykey("ud_zyxk_ycssqkid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 吊装作业 UD_ZYXK_DZZY
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_dzzy");
            tb.setPrimarykey("ud_zyxk_dzzyid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 措施复查子表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zycsfcline");
            tb.setPrimarykey("zycsfclinenum");
            tb.setForeignkey("zycsfcnum");
            tb.setForeignMainTable("ud_zyxk_zycsfc");
            listTableRelation.add(tb);
            // 措施复查表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zycsfc");
            tb.setPrimarykey("zycsfcnum");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);

            // 延期表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zyyq");
            tb.setPrimarykey("ud_zyxk_zyyqid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 人员审批记录表
            tb = new TableDesc();
            tb.setTableName("ud_onemeasure_person");
            tb.setPrimarykey("ud_id");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 逐条措施确认表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zyspryjl");
            tb.setPrimarykey("ud_zyxk_zyspryjlid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 作业申请表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_zysq");
            tb.setPrimarykey("ud_zyxk_zysqid");
            listTableRelation.add(tb);
            // 能量隔离单子表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_nlgldline");
            tb.setPrimarykey("ud_zyxk_nlgldlineid");
            tb.setForeignkey("ud_zyxk_nlgldid");
            listTableRelation.add(tb);
            // 能量隔离单
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_nlgld");
            tb.setPrimarykey("ud_zyxk_nlgldid");
            tb.setForeignkey("ud_zyxk_zysqid");
            listTableRelation.add(tb);
            // 临时用电作业
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_lsydzy");
            tb.setPrimarykey("ud_zyxk_lsydzyid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
            // 临时用电设备清单
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_ydasset");
            tb.setPrimarykey("ud_zyxk_ydassetid");
            tb.setForeignkey("ud_zyxk_zysqid");
            tb.setForeignMainTable("ud_zyxk_zysq");
            listTableRelation.add(tb);
        }
        return listTableRelation;
    }

    @Override
    public HashMap<String, String> getUpDateSql() {
        StringBuilder sbsql = new StringBuilder();
        HashMap<String, String> hashsql = new HashMap<String, String>();

        String mac = SystemProperty.getSystemProperty().getPadmac();
        if (StringUtils.isEmpty(mac)) {
            mac = "mac失败";
        }
        String imei = getIMEI(context);
        if (StringUtils.isEmpty(imei)) {
            imei = "imei失败";
        }
        // 表示只上传没有上传过的数据 ifnull(IsUpLoad,0) = 0
        stridNoBack.insert(0, "(");
        stridNoBack.append(")");
        String sqlwhere = " ifnull(main.IsUpLoad,0) = 0 and main.UD_ZYXK_ZYSQID in "
                + stridNoBack.toString();
        // 作业申请 ud_zyxk_zysq
        // 当票的状态是“CLOSE”或“CANCEL”或“NULLIFY”或“WAPPR”或“CQCLOSE”时即“关闭”，“取消”，“作废”，“退回”，“
        // (status in('作废、退回、超期自动关闭') or
        sbsql.append("select *,'")
                .append(imei).append("' as imei,'")
                .append(mac)
                .append("' as pdamac,'update' as v_action,ifnull(error.iserror,0) as iserror from ud_zyxk_zysq main ")
                .append(" left join (select tag as 'iserror',tableid from hse_sys_record_error  where tag=1 and tablename='ud_zyxk_zysq' and  tableid in (")
                .append(strid.toString())
                .append(")) as error on main.UD_ZYXK_ZYSQID=error.tableid ")
                .append(" where ifnull(main.IsUpLoad,0) = 0 and main.UD_ZYXK_ZYSQID in (")
                .append(strid.toString()).append(");");
        hashsql.put("ud_zyxk_zysq", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 气体检查主表 ud_zyxk_qtjc
        sbsql.append(
                "select ud_zyxk_qtjcid,ud_zyxk_zysqid,jctime,jclocation,jclocation_desc,qttype,qtvalue,yxlimit,ishg,jcdept,jcmethod,sddwxmfzyj,seqnum,instrumentnum,jcperson,qrperson,audittime,ypfxqrperson,ypcjtime,sddwxmfzperson,writenbypda,")
                .append(" 'insert' as v_action,ud_zyxk_qtjcid as uuid from ud_zyxk_qtjc main where  writenbypda='FINISH'  and ifnull(datasource,'') != 'pc' and ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_qtjc", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 气体检查子表 UD_ZYXK_QTJCLINE
        sbsql.append(
                "select ud_zyxk_qtjclineid,ud_zyxk_qtjcid,seqnum,ud_zyxk_zysqid,qttype,qtvalue,qtsvalue,yxlimit,qtresult,")
                .append(" 'insert' as v_action,ud_zyxk_qtjclineid as uuid from ud_zyxk_qtjcline  where ifnull(IsUpLoad,0) = 0  and UD_ZYXK_QTJCID in (select UD_ZYXK_QTJCID from UD_ZYXK_QTJC main  where writenbypda='FINISH'  and ifnull(datasource,'') != 'pc' and")
                .append(sqlwhere).append(");");
        hashsql.put("ud_zyxk_qtjcline", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 措施表 ud_zyxk_zysq2precaution
        sbsql.append(
                "select ud_zyxk_zysq2precautionid,ud_zyxk_zysqid,precautionid,description,cstype,isselect,value,hazardid,vqtcsfield,isconfirm,isnecessary,issupplement,iszyfxfxadd,")
                .append("'update' as v_action,checkresult from ud_zyxk_zysq2precaution main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_zysq2precaution", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 防护设备信息 ud_zyxk_zysq2ud_zyxk_grfhzb
        sbsql.append(
                "select ud_zyxk_zysqid,ud_zyxk_zysq2ud_zyxk_grfhzbid,description,isselect,value,")
                .append("'update' as v_action from ud_zyxk_zysq2ud_zyxk_grfhzb main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_zysq2ud_zyxk_grfhzb", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 危害识别 ud_zyxk_zysq2hazard
        sbsql.append(
                "select ud_zyxk_zysqid,ud_zyxk_zysq2hazardid,hazardid,description,ifnull(ispadselect,isselect) as isselect,whtype,")
                .append("'update' as v_action from ud_zyxk_zysq2hazard main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_zysq2hazard".toLowerCase(), sbsql.toString());

        //liuyangliuyang
//		setWritelog(sbsql.toString());

        sbsql.delete(0, sbsql.length());


        // 复查 UD_ZYXK_ZYCSFC
        sbsql.append(
                "select description,ud_zyxk_zysqid,zysqnum,spdate,zycsfcnum,pagetype,status,jhfs,shift,spperson,qrperson,qrdate,pzperson,pzdate,jhperson,jhperson_desc,jhdate,")
                .append("'insert' as v_action,zycsfcnum as uuid from ud_zyxk_zycsfc main where ifnull(main.datasource,'') != 'pc' and status='")
                .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                .append("' and ifnull(PAGETYPE,'') !='' and ").append(sqlwhere)
                .append(";");
        hashsql.put("ud_zyxk_zycsfc", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 复查子表 UD_ZYXK_ZYCSFCLINE
        sbsql.append(
                "select sub.zycsfclinenum,sub.zycsfcnum,sub.cstype,sub.description,sub.hazardid,sub.precautionid,sub.isselect,sub.value,sub.vqtcsfield,")
                .append("'insert' as v_action,checkresult,sub.zycsfclinenum as uuid from ud_zyxk_zycsfcline sub inner join ud_zyxk_zycsfc main on sub.zycsfcnum=main.zycsfcnum where  main.status='")
                .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                .append("' and ifnull(main.PAGETYPE,'') !='' and  ifnull(main.IsUpLoad,0) = 0 and ifnull(main.datasource,'') != 'pc'  and main.UD_ZYXK_ZYSQID in")
                .append(stridNoBack.toString()).append(";");
        hashsql.put("ud_zyxk_zycsfcline", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        // // 延期表UD_ZYXK_ZYYQ
        sbsql.append(
                "select main.ud_zyxk_zyyqid,main.ud_zyxk_zysqid,main.description,main.yqstarttime,main.yqendtime,main.yqsqperson,main.xgfperson,main.yqpzperson,main.jhperson,main.sddw,main.gddw,main.dqzgdw,main.auditime,main.overtag,main.chq_yqxdjhr,main.chq_yqzydwjhr,main.zycsfcnum,main.yqtepttime,")
                .append("'insert' as v_action,main.ud_zyxk_zyyqid as uuid from ud_zyxk_zyyq main inner join  ud_zyxk_zycsfc on main.zycsfcnum=ud_zyxk_zycsfc.zycsfcnum where ud_zyxk_zycsfc.Status='")
                .append(IWorkOrderStatus.REVIEW_STATUS_FINISH)
                .append("' and ifnull(PAGETYPE,'') !='' and ").append(sqlwhere)
                .append(";");
        hashsql.put("ud_zyxk_zyyq", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 逐条措施确认表 ud_onemeasure_person
        // lxf 2014-11-27 注释
        sbsql.append(
                "select ud_id,tableid,status,opid,opcode,opname,sptime,spnode,tabcode,tablename,objectname,spnodename,ud_type,")
                .append("'insert' as v_action,ud_zyxk_zysqid,ud_id as uuid from ud_onemeasure_person where ifnull(IsUpLoad,0) = 0 and ifnull(datasource,'')!='pc'  and ifnull(ud_type,'') in('SP01','FC01','YQ01','GB01','QX01','JJB01','JJB02','') and ud_zyxk_zysqid in ")
                .append(stridNoBack.toString()).append(";");
        hashsql.put("ud_onemeasure_person", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // // 人员审批记录表 ud_zyxk_zyspryjl
        sbsql.append(
                "select ud_zyxk_zyspryjlid,ud_zyxk_zysqid,dycode,dept,person,person_name as person_desc,job,code,zcproject,sptime,value,spnode,tablename,tableid,type,inputall,")
                .append("'insert' as v_action,ud_zyxk_zyspryjlid as uuid from ud_zyxk_zyspryjl main where  ifnull(datasource,'')!='pc'  and ifnull(type,'') in('SP01','FC01','YQ01','GB01','QX01','JJB01','JJB02','PAUSE01','') and ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_zyspryjl", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        // 能量隔离单 UD_ZYXK_NLGLD
        sbsql.append(
                "select UD_ZYXK_NLGLDID,SSDEPT,ZZLOCATION,GLASSET,GLSTATUS,STATUS,	HAZARD,HAZARD_DESC,ZYLOCATION,BXPERSON,CSPERSON,ZYPERSON,PZPERSON,PZTIME,NLGLDNUM,IsUpLoad,NLY, 'update' as v_action, ud_zyxk_nlgldid as uuid from ud_zyxk_nlgld main where status <> 'INPRG' and ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_nlgld", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        // 能量隔离单子表 UD_ZYXK_NLGLDLINE
        sbsql.append(
                "select *, 'insert' as v_action, ud_zyxk_nlgldlineid as uuid from ud_zyxk_nlgldline where ifnull(IsUpLoad,0) = 0 and UD_ZYXK_NLGLDID in (select UD_ZYXK_NLGLDID from ud_zyxk_nlgld main where status <> 'INPRG' and ")
                .append(sqlwhere).append(");");
        hashsql.put("ud_zyxk_nlgldline", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        sbsql.append(
                "select *, 'insert' as v_action, ud_zyxk_ydassetid as uuid from ud_zyxk_ydasset main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_ydasset", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        sbsql.append(
                "select *, 'update' as v_action, ud_zyxk_lsydzyid as uuid from ud_zyxk_lsydzy main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_lsydzy", sbsql.toString());
        sbsql.delete(0, sbsql.length());
        //吊装作业
        sbsql.append(
                "select *, 'insert' as v_action, ud_zyxk_dzzyid as uuid from ud_zyxk_dzzy main where ")
                .append(sqlwhere).append(";");
        hashsql.put("ud_zyxk_dzzy", sbsql.toString());
        sbsql.delete(0, sbsql.length());

        // 挖机作业 UD_ZYXK_YCSSQK
        // 手机防护装备信息
        // 风险分析单主表
        // 风险分析单子表

        return hashsql;

        //
        // //--风险分析主表
        // sbsql.append("select fx.UD_ZYXK_ZYFXFXID as UD_ZYXK_ZYFXFXID,fx.status as status,zysq.UD_ZYXK_ZYSQID as sqlid,'UD_ZYXK_ZYFXFX' as tablename,'UPDATE' as TransportInsertOper,");
        // sbsql.append("fx.CHKCONTENT as CHKCONTENT,fx.TOTALCOUNT as TOTALCOUNT,fx.CHKCOUNT as CHKCOUNT from UD_ZYXK_ZYFXFX as fx inner join(");
        // sbsql.append("select UD_ZYXK_ZYSQID,UD_ZYXK_ZYFXFXID from UD_ZYXK_ZYSQ where  UD_ZYXK_ZYSQID in {0} and fxstatus='COMP'");
        // sbsql.append(") as zysq on fx.UD_ZYXK_ZYFXFXID=zysq.UD_ZYXK_ZYFXFXID group by fx.UD_ZYXK_ZYFXFXID;");
        // //--分析明细--更新
        // sbsql.append("select line.UD_ZYXK_ZYFXFXLINEID as UD_ZYXK_ZYFXFXLINEID,zysq.UD_ZYXK_ZYSQID as sqlid,line.ISADD as ISADD,line.CSSTATUS as CSSTATUS,line.ISNECESSARY as ISNECESSARY,line.VTYPE as VTYPE,line.VCODE as VCODE,line.VCODE_DESC as VCODE_DESC,");
        // sbsql.append("'UD_ZYXK_ZYFXFXLINE' as tablename,'UPDATE' as TransportInsertOper  from UD_ZYXK_ZYFXFXLINE as line inner join(");
        // sbsql.append("select UD_ZYXK_ZYSQID,UD_ZYXK_ZYFXFXID from UD_ZYXK_ZYSQ where  UD_ZYXK_ZYSQID in {0} and fxstatus='COMP'");
        // sbsql.append(") as zysq on line.UD_ZYXK_ZYFXFXID=zysq.UD_ZYXK_ZYFXFXID WHERE line.DATASOURCE='PC' group by line.UD_ZYXK_ZYFXFXLINEID;");
        // //--分析明细--插入
        // sbsql.append("select zysq.UD_ZYXK_ZYSQID as sqlid,line.UD_ZYXK_ZYFXFXID as UD_ZYXK_ZYFXFXID,line.ISADD as ISADD,line.CSSTATUS as CSSTATUS,line.ISNECESSARY as ISNECESSARY,line.VTYPE as VTYPE,line.VCODE as VCODE,line.VCODE_DESC as VCODE_DESC,");
        // sbsql.append("'UD_ZYXK_ZYFXFXLINE' as tablename,'INSERT' as TransportInsertOper  from UD_ZYXK_ZYFXFXLINE as line inner join(");
        // sbsql.append("select UD_ZYXK_ZYSQID,UD_ZYXK_ZYFXFXID from UD_ZYXK_ZYSQ where  UD_ZYXK_ZYSQID in {0} and fxstatus='COMP'");
        // sbsql.append(") as zysq on line.UD_ZYXK_ZYFXFXID=zysq.UD_ZYXK_ZYFXFXID WHERE line.DATASOURCE='PDA' group by line.VCODE;");

    }

    @Override
    public Object initParam() throws HDException {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
                .getSystemProperty().getLoginPerson().getPersonid());
        String dept = SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment();
        if (!StringUtils.isEmpty(dept)) {
            hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
        }
        //得到imei并上传
        String imei = getIMEI(context);
        if (imei != null) {
            hashmap.put(PadInterfaceRequest.KEYIMEI, imei);
        }
        return hashmap;
    }

    /**
     * 得到imei
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.METHOD_COMMON_UPTABLE;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        try {
            strid.delete(0, strid.length());
            stridNoBack.delete(0, stridNoBack.length());
            if (null != args) {
                for (Object ob : args) {
                    if (ob instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<SuperEntity> listEntity = (List<SuperEntity>) ob;
                        if (null != listEntity && listEntity.size() > 0) {
                            for (SuperEntity supe : listEntity) {
                                strid.append("'");
                                strid.append(supe.getAttribute(supe
                                        .getPrimaryKey()));
                                strid.append("',");
                            }
                            if (null != strid && strid.length() > 0) {
                                strid.delete(strid.length() - 1, strid.length());
                            }
                        }
                        break;
                    }
                }
            }
            // 表示上传的id
            if (StringUtils.isEmpty(strid.toString())) {
                throw new HDException("没有传入上传的数据");
            }
            String zfid = getDealZYSQID("select", null);
            if (!StringUtils.isEmpty(zfid)) {
                strid.append(",").append(zfid);
            }
            // 查询票中不包含退回的作业申请ID
            stridNoBack.append(getDealNoBackZYSQID(strid.toString()));
            upStrid = stridNoBack.toString();
            return super.action(action, args);
        } catch (HDException e) {
            getLogger().error(e.getMessage(), e);
            this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
        }
        return 0;
    }

    @Override
    public void beforUpDataInfo(Object... args) throws HDException {
        // TODO Auto-generated method stub
        super.beforUpDataInfo(args);
        // 删除退回作业票的照片信息
        dealBackZYSQInfo();
    }

    @Override
    public void afterUploadDataError(String error) throws HDException {
        // 将错误信息插入到数据库中
        List<String> listsql = new ArrayList<String>();
        if (!StringUtils.isEmpty(upStrid)) {
            String tablename = "ud_zyxk_zysq";
            String[] str = upStrid.split(",");
            StringBuilder sbsql = new StringBuilder();
            listsql.add("delete from hse_sys_record_error where tablename='"
                    + tablename + "' and tableid in (" + upStrid + ");");
            for (int i = 0; i < str.length; i++) {
                sbsql.delete(0, sbsql.length());
                sbsql.append(
                        "insert into hse_sys_record_error(tableid,tablename,tag)values(")
                        .append(str[i]).append(",'").append(tablename)
                        .append("','1');");
                listsql.add(sbsql.toString());
            }
        }
        if (listsql.size() > 0) {
            this.execute(listsql);
        }
    }

    /**
     * dealBackZYSQInfo:(处理退回作业票的信息和图片信息). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @throws HDException
     * @author lxf
     */
    private void dealBackZYSQInfo() throws HDException {
        // 1.获取退货作业票的主键
        String backZysqid = getDealBackZYSQID(strid.toString());
        deleteZYSQImageInfo(backZysqid);
    }

    /**
     * getListImagePath:(删除图片信息). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @param zysqid
     * @throws HDException
     * @author lxf
     */
    private void deleteZYSQImageInfo(String zysqid) throws HDException {
        // 3.删除图片表信息和删除本地照片信息
        List<String> listdeleteimage = new ArrayList<String>();
        if (!StringUtils.isEmpty(zysqid)) {

            List<HashMap<String, Object>> maplist = getDBimageInfo(zysqid);
            // 取出图片路径
            if (null != maplist && maplist.size() > 0) {
                for (HashMap<String, Object> hash : maplist) {
                    // 表示取出每一行
                    Iterator<Entry<String, Object>> iter = hash.entrySet()
                            .iterator();
                    String path = null;

                    while (iter.hasNext()) {
                        Entry<String, Object> entry = iter.next();
                        if (entry.getKey().equalsIgnoreCase("imagepath")) {
                            path = entry.getValue().toString();
                            listdeleteimage.add(path);
                        }
                    }
                }
            }
            if (listdeleteimage.size() > 0) {
                deleteSDcardFile(listdeleteimage);
            }
        }
    }

    /**
     * getDealNoBackZYSQID:(获取不包含退回的作业票ID). <br/>
     * date: 2014年11月7日 <br/>
     *
     * @param zysqid
     * @return
     * @throws HDException
     * @author lxf
     */
    private String getDealNoBackZYSQID(String zysqid) throws HDException {
        StringBuilder sbstr = new StringBuilder();
        String sql = null;

        sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where status !='WAPPR'  and ud_zyxk_zysqid in ("
                + zysqid + ");";
        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) this
                .query(sql, new MapListResult());
        if (null != result && result.size() > 0) {
            for (HashMap<String, Object> map : result) {
                // 取大写数据
                if (map.containsKey("UD_ZYXK_ZYSQID")) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID").toString())
                            .append("',");
                }
                // 取小写数据
                if (map.containsKey("UD_ZYXK_ZYSQID".toLowerCase())) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID".toLowerCase())
                                    .toString()).append("',");
                }

            }
        }
        if (sbstr.length() > 1) {
            sbstr.delete(sbstr.length() - 1, sbstr.length());
            return sbstr.toString();
        }
        return null;
    }

    /**
     * getStrZYSQID:(获取要处理票的主键). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @return
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    private String getDealZYSQID(String action, String zysqid)
            throws HDException {
        StringBuilder sbstr = new StringBuilder();
        String sql = null;
        // select
        if ("select".equals(action)) {
            sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where (status='NULLIFY' and spstatus='OVERSTATE') or (status='CQCLOSE' and spstatus='OVERCLOSE');";
        } else if ("delete".equals(action)) {
            // 判断是否全部删除
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            boolean rel = relation
                    .isHadRelative(IRelativeEncoding.UPDATAHANDLE);
            // HashMap<String, Object> hashMapReuslt;
            // hashMapReuslt = (HashMap<String, Object>) this
            // .query("select sys_value,Input_value from sys_relation_info where ifnull(dr,0)=0 and ifnull(isqy,1)=1 and sys_type='"
            // + IRelativeEncoding.UPDATAHANDLE + "';",
            // new MapResult());
            // if (null != hashMapReuslt && hashMapReuslt.size() > 0) {
            if (rel) {
                sql = " select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where ud_zyxk_zysqid in ("
                        + zysqid + ");";
            } else {
                sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where status in ('CLOSE','CAN','WAPPR','CQCLOSE','REMOTE','NULLIFY','"
                        + IWorkOrderStatus.APPAUDITED
                        + "') and ud_zyxk_zysqid in (" + zysqid + ");";
            }
        }
        // close:关闭;CAN:取消; WAPPR:退回(票初始状态);CQCLOSE:超期关闭;APPR：审批中;NULLIFY:作废
        // 应该是STATUS='CQCLOSE' spstatus='OVERCLOSE'超期关闭/ STATUS='NULLIFY'
        // spstatus='OVERSTATE'自动作废

        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) this
                .query(sql, new MapListResult());
        if (null != result && result.size() > 0) {
            for (HashMap<String, Object> map : result) {
                // 取大写数据
                if (map.containsKey("UD_ZYXK_ZYSQID")) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID").toString())
                            .append("',");
                }
                // 取小写数据
                if (map.containsKey("UD_ZYXK_ZYSQID".toLowerCase())) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID".toLowerCase())
                                    .toString()).append("',");
                }

            }
        }
        if (sbstr.length() > 1) {
            sbstr.delete(sbstr.length() - 1, sbstr.length());
            return sbstr.toString();
        }
        return null;
    }

    /**
     * getDealBackZYSQID:(获取作退货作业票的主键信息). <br/>
     * date: 2014年12月4日 <br/>
     *
     * @param zysqid
     * @return
     * @throws HDException
     * @author lxf
     */
    private String getDealBackZYSQID(String zysqid) throws HDException {
        StringBuilder sbstr = new StringBuilder();
        String sql = null;
        sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where status ='WAPPR'  and ud_zyxk_zysqid in ("
                + zysqid + ");";
        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) this
                .query(sql, new MapListResult());
        if (null != result && result.size() > 0) {
            for (HashMap<String, Object> map : result) {
                // 取大写数据
                if (map.containsKey("UD_ZYXK_ZYSQID")) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID").toString())
                            .append("',");
                }
                // 取小写数据
                if (map.containsKey("UD_ZYXK_ZYSQID".toLowerCase())) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID".toLowerCase())
                                    .toString()).append("',");
                }

            }
        }
        if (sbstr.length() > 1) {
            sbstr.delete(sbstr.length() - 1, sbstr.length());
            return sbstr.toString();
        }
        return null;
    }

    /**
     * getDBimageInfo:(读取数据库照片信息). <br/>
     * date: 2014年12月4日 <br/>
     *
     * @param zysqid
     * @return
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings({"unchecked"})
    private List<HashMap<String, Object>> getDBimageInfo(String zysqid)
            throws HDException {
        String sql;
        if (StringUtils.isEmpty(zysqid)) {
            sql = "select replace(imagepath,x'0a','') as imagepath,tablename,tableid,funcode,createdate,createuser,createusername,zysqid,childids from hse_sys_image where tablenamein('ud_zyxk_zysq', 'ud_onemeasure_person' ,'ud_zyxk_zyspryjl') order by  createdate desc; ";

        } else {
            sql = "select replace(imagepath,x'0a','') as imagepath,tablename,tableid,funcode,createdate,createuser,createusername,zysqid,childids from hse_sys_image where tablename='ud_zyxk_zysq' and tableid in ("
                    + zysqid
                    + ") or (tablename in ('ud_onemeasure_person' ,'ud_zyxk_zyspryjl') and zysqid in ("
                    + zysqid + ")) order by  createdate desc;";
        }
        List<HashMap<String, Object>> maplist = null;
        maplist = (ArrayList<HashMap<String, Object>>) this.query(sql,
                new MapListResult());

        return maplist;
    }

    /**
     * deleteSDcardFile:(删除SDCard文件). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @param listfile
     * @throws HDException
     * @author lxf
     */

    private void deleteSDcardFile(List<String> listfile) throws HDException {
        // 表示上传完文件后执行的动作
        if (null != listfile) {
            StringBuilder sbdelete = new StringBuilder();
            List<String> listDelete = new ArrayList<String>();
            // 此处删除文件和删除数据库记录
            for (String sql : listfile) {
                sbdelete.append(
                        "delete from hse_sys_image where tablename in('ud_zyxk_zysq', 'ud_zyxk_zyspryjl', 'ud_onemeasure_person') and imagepath='")
                        .append(sql).append("';");
                listDelete.add(sbdelete.toString());
                sbdelete.delete(0, sbdelete.length());
            }
            // 删除数据库图片信息
            if (listDelete != null && listDelete.size() > 0) {
                try {
                    this.execute(listDelete);
                } catch (HDException e) {
                    setWritelog("删除数据库图片信息报错:" + e.getMessage());
                    throw e;
                }
            }
            // 删除本地文件信息
            for (String path : listfile) {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                // 删除空文件夹
                String directoryPath = path.substring(0, path.lastIndexOf("/"));
                file = new File(directoryPath);
                if (file.isDirectory()) {
                    String[] fileNames = file.list();
                    if (fileNames == null || fileNames.length == 0) {
                        file.delete();
                    }
                }
            }

        }
    }

    /**
     * 根绝路径返回文件名
     *
     * @param path 路径
     * @return 文件名
     */
    private String getFileName(String path) {
        String filename;
        int start = path.lastIndexOf("/");
        int end = path.length();
        if (start != -1 && end != -1) {
            filename = path.substring(start + 1, end);
        } else {
            filename = "hd_zhangjie.jpg";
        }
        return filename;
    }

    /**
     * deleteTrashinfo:(删除垃圾数据). <br/>
     * date: 2014年9月25日 <br/>
     * 当票的状态是“CLOSE”或“CANCEL”或“NULLIFY”或“WAPPR”或“CQCLOSE”时即“关闭”，“取消”，“作废”，“退回”，“
     * 超期关闭”删除所有该票信息 应该是STATUS='CQCLOSE' spstatus='OVERCLOSE'超期关闭/
     * STATUS='NULLIFY' spstatus='OVERSTATE'自动作废
     *
     * @throws HDException
     * @author lxf
     */
    private void deleteTrashinfo(String zysqid) throws HDException {

        List<String> listsql;
        // 取得要处理的id 以逗号分隔.
        String str = getDealZYSQID("delete", zysqid);
        if (StringUtils.isEmpty(str)) {
            // 没有数据返回
            return;
        }
        listsql = CommonSql.getExecListSql(str, "delete");
        if (null != listsql && listsql.size() > 0) {
            this.setWriteDubuglog("lxfsql:" + listsql.toString());
            this.execute(listsql);
        }

    }

    @Override
    public void afterUploadDataInfo(Object pcdata) throws HDException {
        PadInterfaceResponse response = null;
        if (pcdata instanceof PadInterfaceResponse) {
            response = (PadInterfaceResponse) pcdata;
        }
        if (null != response) {
            // 表示根据返回结果执行相应的处理
            StringBuilder sbsucess = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            List<String> listslq = new ArrayList<String>();
            for (Map<String, String> hasmap : response.getSucessfulList()) {
                String strid = null;
                if (hasmap.containsKey(PadInterfaceRequest.KEYRETURNID)) {
                    strid = hasmap.get(PadInterfaceRequest.KEYRETURNID)
                            .toString();
                    sbsucess.append("'").append(strid).append("',");
                }
                if (hasmap.containsKey(PadInterfaceRequest.KEYRETURNMESSAGE)) {
                    String msg = hasmap
                            .get(PadInterfaceRequest.KEYRETURNMESSAGE);
                    // 表示把审批结束的票，上传成功后，更新上作业许可证号
                    if (!StringUtils.isEmpty(msg) && !StringUtils.isEmpty(msg)) {
                        // 更改审批中的信息
                        sql.append(
                                "update ud_zyxk_zysq set needupload = 0,spstatus='UPLOAD',ZYSQNUM='")
                                .append(msg)
                                .append("' where status='APPR' and ud_zyxk_zysqid='")
                                .append(strid).append("';");
                    }
                    // 添加主表的sql语句
                    if (sql.length() > 0) {
                        listslq.add(sql.toString());
                        sql.delete(0, sql.length());
                    }
                }
            }

            if (sbsucess.length() > 0) {
                sbsucess.delete(sbsucess.length() - 1, sbsucess.length());
                // 获取打标记的语句
                List<String> updatelistsql = CommonSql.getExecListSql(
                        sbsucess.toString(), "update");
                if (null != updatelistsql && updatelistsql.size() > 0) {
                    listslq.addAll(updatelistsql);
                }
                // // 添加删除错误标记记录
                // String sqlerror =
                // " delete from hse_sys_record_error where tablename='ud_zyxk_zysq' and tableid in("
                // + sbsucess.toString() + ");";
                // listslq.add(sqlerror);
                //
                // // 表示图片上传成功---删除本地图片
                // deleteZYSQImageInfo(sbsucess.toString());
            }
            if (listslq.size() > 0) {
                this.execute(listslq);
            }
            if (sbsucess.length() > 0) {
                // 删除垃圾数据
                deleteTrashinfo(sbsucess.toString());
            }

        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterFileInfo(Object pcdata, Object... obj) throws HDException {
        // 表示上传完文件后执行的动作
        List<String> listsuccess = null;
        if (pcdata instanceof List) {
            listsuccess = (List<String>) pcdata;
        }
        if (null != listsuccess) {
            StringBuilder sbdelete = new StringBuilder();
            List<String> listDelete = new ArrayList<String>();
            // 此处删除文件和删除数据库记录
            for (String sql : listsuccess) {
                sbdelete.append(
                        "delete from hse_sys_image where tablename in('ud_zyxk_zysq', 'ud_zyxk_zyspryjl', 'ud_onemeasure_person') and imagepath='")
                        .append(sql).append("';");
                listDelete.add(sbdelete.toString());
                sbdelete.delete(0, sbdelete.length());
            }
            // 删除数据库图片信息
            if (listDelete.size() > 0) {
                try {
                    this.execute(listDelete);
                } catch (HDException e) {
                    setWritelog("删除数据库图片信息报错:" + e.getMessage());
                    throw e;
                }
            }
            // 删除本地文件信息
            for (String path : listsuccess) {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                // 删除空文件夹
                String directoryPath = path.substring(0, path.lastIndexOf("/"));
                file = new File(directoryPath);
                if (file.isDirectory()) {
                    String[] fileNames = file.list();
                    if (fileNames == null || fileNames.length == 0) {
                        file.delete();
                    }
                }
            }

        }

    }

    @Override
    public List<HashMap<String, HashMap<String, Object>>> getFilePathInfo() {
        // TODO Auto-generated method stub
        List<HashMap<String, Object>> maplist = null;
        List<HashMap<String, HashMap<String, Object>>> listImage = new ArrayList<HashMap<String, HashMap<String, Object>>>();
        HashMap<String, HashMap<String, Object>> imagehash;
        HashMap<String, Object> mapwhere;
        String filename;
        try {
            maplist = getDBimageInfo(strid.toString());
            if (null != maplist) {
                for (HashMap<String, Object> hash : maplist) {
                    // 表示取出每一行
                    Iterator<Entry<String, Object>> iter = hash.entrySet()
                            .iterator();
                    String path = null;
                    mapwhere = new HashMap<String, Object>();
                    imagehash = new HashMap<String, HashMap<String, Object>>();
                    while (iter.hasNext()) {
                        Entry<String, Object> entry = iter.next();
                        if (entry.getKey().equalsIgnoreCase("imagepath")) {
                            path = entry.getValue().toString();
                        }
                        if (entry.getKey().equalsIgnoreCase("tablename")) {
                            mapwhere.put(PadInterfaceRequest.TABLENAME,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("tableid")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.TABLEID,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("zysqid")) {
                            mapwhere.put(PadInterfaceRequest.KEYZYPSTRID,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("childids")) {
                            mapwhere.put(PadInterfaceRequest.CHILDIDS,
                                    entry.getValue());
                        }
                        // 业务类型
                        if (entry.getKey().equalsIgnoreCase("funcode")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYFUNCODE,
                                    entry.getValue());
                        }
                        // 创建时间
                        if (entry.getKey().equalsIgnoreCase("createdate")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYCREATETIME,
                                    entry.getValue());
                        }
                        // 登录拍照人
                        if (entry.getKey().equalsIgnoreCase("createuser")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYPERSONID,
                                    entry.getValue());
                        }
                        // 登陆拍照人姓名
                        if (entry.getKey().equalsIgnoreCase("createusername")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYPERSONDESC,
                                    entry.getValue());
                        }
                    }
                    if (null != path && path.length() > 0) {
                        filename = getFileName(path);
                        // 文件名
                        mapwhere.put(PadInterfaceRequest.KEYFILENAME, filename);
                        // appName
                        mapwhere.put(PadInterfaceRequest.KEYAPPNAME, "appzysq");
                        // MAC地址
                        mapwhere.put(PadInterfaceRequest.KEYMAC, SystemProperty
                                .getSystemProperty().getPadmac());
                        imagehash.put(path, mapwhere);
                        listImage.add(imagehash);
                        // imagehash.clear();
                    }
                }
            }
        } catch (HDException e) {
            setWritelog("读取数据库报错:" + e.getMessage());
        }
        return listImage;
    }
}
