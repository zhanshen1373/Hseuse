package com.hd.hse.dc.business.common.weblistener.down;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.checkdata.CheckDataConfig;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: InitListener (基础数据 操作基类)<br/>
 * date: 2015年3月19日 <br/>
 *
 * @author lxf
 */
public abstract class InitListener extends GainPCDataListener {
    // tag=1
    List<String> listsql = new ArrayList<String>();
    // 删除tag=1的数据sql
    List<String> listDelete = new ArrayList<String>();

    List<String> listUpdate = new ArrayList<String>();
    // 删除dr=1的数据sql
    List<String> listDeleteDr = new ArrayList<String>();
    /**
     * 针对新增数据sql处理， hse_sys_record_ts_sub hse_sys_record_ts数据插入sql，
     * sys_ts为对应数据表最新的changedate
     */
    List<String> listNewAddSql = new ArrayList<String>();
    CheckDataConfig checkDataConfig = new CheckDataConfig();
    public static final String UPDATE = "update";
    public static final String INITDATA = "initdata";


    @Override
    public void beforDataInfo(Object... obj) throws HDException {
        // TODO Auto-generated method stub
        super.beforDataInfo(obj);
        if (isCancel) {
            isCancel = false;
            return;
        }
        this.sendMessage(IMessageWhat.PROCESSING, 0, 5, "连接服务器,请求下载数据");
        listsql.clear();
        listDelete.clear();
        listUpdate.clear();
        listDeleteDr.clear();
        listNewAddSql.clear();
        // 添加查询条件
        @SuppressWarnings("unchecked")
        HashMap<String, Object> hasmap = (HashMap<String, Object>) initParam();
        if (!hasmap.containsKey(PadInterfaceRequest.KEYDEPTNUM)) {
            throw new HDException("initParam()必须设置设置部门参数;");
        }
        // String deptcode =
        // hasmap.get(PadInterfaceRequest.KEYDEPTNUM).toString();
        // hasmap.put(PadInterfaceRequest.KEYTABLE, getOpSql(deptcode));
        // 连接数据库
        if (isCancel) {
            isCancel = false;
            return;
        }
        this.sendMessage(IMessageWhat.PROCESSING, 5, 15, "连接服务器,请求下载数据");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterDataInfo(final Object pcdata, Object... obj) throws HDException {
        // TODO Auto-generated method stub
        super.afterDataInfo(pcdata, obj);
        // Log.e("pcdata大小", pcdata.toString().getBytes().length/1024+"");


        List<String> listsqlInsert = null;
        if (pcdata instanceof List) {
            listsqlInsert = (List<String>) pcdata;
            setWriteDubuglog("更新数据:" + listsqlInsert.toString());
        }
        if (isCancel) {
            isCancel = false;
            return;
        }
        InitListener.this.sendMessage(IMessageWhat.PROCESSING, 15, 35, "开始本地处理数据");
        if (null == listsqlInsert || listsqlInsert.size() == 0) {
            InitListener.this.sendMessage(IMessageWhat.END, 98, 100, "成功");
            return;
        }
        // 标记初始化失败
        if (getFuction() != null && getFuction().equals(INITDATA)) {
            checkDataConfig.initDataFail();
        }
        // 标记更新失败
        if (getFuction() != null && getFuction().equals(UPDATE)) {
            checkDataConfig.updateFail();
        }
        // 3.打标记和插入数据库同意事物执行
        listsql.addAll(listsqlInsert);

        /*
        List<String> list = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        for (int w = 0; w < listsqlInsert.size(); w++) {
            int i = listsqlInsert.get(w).indexOf("(");
            String substring = listsqlInsert.get(w).substring(12, i);
            if (!list.contains(substring)) {
                list.add(substring);
                integerList.add(w);
            }

        }

        List<String> strings = listsqlInsert.subList(21, 6456);
        List<String> dd=new ArrayList<>();
        for (int k=0;k<strings.size();k++){
            String[] values = strings.get(k).split("values");
            String[] split = values[1].split(",");
            if (!dd.contains(split[2])){
                dd.add(split[2]);
            }
        }
     */

        // 新增数据的操作
        listsql.addAll(listNewAddSql);
        listsql.addAll(listDelete);
        listsql.addAll(listUpdate);
        listsql.addAll(listDeleteDr);
        if (getInit()) {
            listsql.add("delete from hse_sys_record_ts_sub where sys_dept <> '"
                    + SystemProperty.getSystemProperty().getLoginPerson()
                    .getDepartment() + "';");
        }

        //数据库的操作和整体是在同一线程中
        if (null != listsql && listsql.size() > 0) {
            /*long l3 = ServerDateManager.getCurrentTimeMillis();*/
            execute(listsql);
            /*long l4 = ServerDateManager.getCurrentTimeMillis();
            if (getMethodType().equals("COMMON_001")) {
                Log.e("pp", l4 - l3 + "本地时间");
                Log.e("dd", listsql.size() + "本地条数");
            }*/
        }


        // 标记初始化成功,并将更新置为成功
        if (getFuction() != null && getFuction().equals(INITDATA)) {
            checkDataConfig.initDataSuccess();
            checkDataConfig.updateSuccess();
        }
        // 标记更新成功
        if (getFuction() != null && getFuction().equals(UPDATE)) {
            checkDataConfig.updateSuccess();
        }

        InitListener.this.sendMessage(IMessageWhat.END, 98, 100, "成功");


    }

    @Override
    public Object initParam() throws HDException {
        // TODO Auto-generated method stub
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
                .getSystemProperty().getLoginPerson().getPersonid());
        String dept = SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment();
        if (!StringUtils.isEmpty(dept)) {
            hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
        }
        hashmap.put(PadInterfaceRequest.KEYTABLE, getOpSql(dept));
        return hashmap;
    }

    /**
     * 拼写打标记、插入、删除 语句
     */
    @SuppressWarnings("unchecked")
    private String getOpSql(String deptcode) throws HDException {

        listsql.clear();
        listNewAddSql.clear();
        listDelete.clear();
        listUpdate.clear();
        listDeleteDr.clear();
        try {
            String sql = "select main.SYS_TABLENAME,main.SYS_TABLEID,main.sys_ts as maints,sub.SYS_TS,main.sys_isdept from hse_sys_record_ts main left join (select * from hse_sys_record_ts_sub where sys_dept='"
                    + deptcode
                    + "' )sub on main.sys_tablename=sub.sys_tablename ";
            sql += " where 1=1 ";
            String where = getWhere();
            // 默认表示初始化
            if (!StringUtils.isEmpty(where)) {
                sql += " and " + where + ";";
            } else {
                sql += " and SYS_INIT=1;";
            }
            // sbtable样式：tablename&time,分隔
            StringBuilder sbtable = new StringBuilder();

            List<HashMap<String, Object>> retlist = null;
            retlist = (ArrayList<HashMap<String, Object>>) this.query(sql,
                    new MapListResult());

            if (retlist.size() > 0) {

                StringBuilder sbsql = new StringBuilder();
                // 此处使用循环
                String tabename = null;
                for (HashMap<String, Object> map : retlist) {
                    // 表名不存在抛出异常
                    if (!map.containsKey("sys_tablename")) {
                        throw new HDException("key:sys_tablename  不存在"
                                + this.getClass().getName());
                    }
                    tabename = (map.get("sys_tablename")).toString();
                    if (!StringUtils.isEmpty(tabename)) {
                        // 打标记
                        sbsql.append("update ").append(tabename)
                                .append(" set tag=1 where tag is null or tag=0;");
                        listsql.add(sbsql.toString());
                        sbsql.setLength(0);
                        if (sbtable.length() > 0) {
                            sbtable.append(",");
                        }
                        sbtable.append(tabename);
                        sbtable.append("&");
                        if (getInit()) {
                            // 初始化,时间戳设置的超级早
                            sbtable.append("1999-03-26 15:51:14");
                            sbsql.setLength(0);
                            // 删除打标记的数据
                            sbsql.append("delete from ").append(tabename)
                                    .append(" where tag=1;");
                            listDelete.add(sbsql.toString());
                            sbsql.setLength(0);
                        } else {
                            String ts;
                            // 表示启用部门配置
                            if ((int) map.get("sys_isdept") > 0) {
                                ts = map.get("sys_ts") == null ? "" : map.get(
                                        "sys_ts").toString();
                            } else {
                                ts = map.get("maints") == null ? "" : map.get(
                                        "maints").toString();
                            }
                            // 更新
                            if (StringUtils.isEmpty(ts)
                                    || ts.equalsIgnoreCase("null")) {
                                // 如果时间戳为空，时间戳设置的相当早相当于初始化
                                sbtable.append("1999-03-26 15:51:14");
                            } else {
                                sbtable.append(ts);
                            }

                            sbsql.setLength(0);
                            // 删除重复的数据
                            String tableid = map.get("sys_tableid").toString();
                            sbsql.append("delete from ").append(tabename)
                                    .append(" where tag=1 and ")
                                    .append(tableid).append(" in (")
                                    .append("select ").append(tableid)
                                    .append(" from ").append(tabename)
                                    .append(" group by ").append(tableid)
                                    .append(" having count(*)>1);");
                            listDelete.add(sbsql.toString());
                            sbsql.setLength(0);
                            // 表示还原标记
//                            sbsql.append("update ").append(tabename)
//                                    .append(" set tag=0;");
//                            listDelete.add(sbsql.toString());
//                            sbsql.setLength(0);
                        }
                        sbsql.setLength(0);
                        // 先插入最新的时间戳记录，再删除以前记录
                        if ((int) map.get("sys_isdept") > 0) {
                            // lxf 2015-01-22
                            if (tabename.equalsIgnoreCase("ud_zyxk_zyspqx")) {
                                sbsql.append(
                                        "insert into hse_sys_record_ts_sub(sys_tablename,sys_dept,sys_ts,dr,tag) select '")
                                        .append(tabename)
                                        .append("','")
                                        .append(deptcode)
                                        .append("',max(changedate),0,0 from ")
                                        .append(tabename)
                                        .append("  where ud_zyxk_spfaid in(select ud_zyxk_spfaid from ud_zyxk_spfa where visqy=1 and ( vdept is null or vdept in(select deptnum from ud_sy_dept where (select vreportdeptnum from ud_sy_dept where deptnum='")
                                        .append(deptcode)
                                        .append("') like  parent||'%'))order by vdept desc limit 1)");
                                sbsql.append(";");
                            } else if (tabename
                                    .equalsIgnoreCase("persongroupteam")) {
                                sbsql.append(
                                        "insert into hse_sys_record_ts_sub(sys_tablename,sys_dept,sys_ts,dr,tag) select '")
                                        .append(tabename)
                                        .append("','")
                                        .append(deptcode)
                                        .append("',max(changedate),0,0 from ")
                                        .append(tabename)
                                        .append(" where resppartygroup in(select personid from ud_zyxk_ryk where department='")
                                        .append(deptcode).append("') ");
                                sbsql.append(";");
                            } else {
                                sbsql.append(
                                        "insert into hse_sys_record_ts_sub(sys_tablename,sys_dept,sys_ts,dr,tag) select '")
                                        .append(tabename).append("','")
                                        .append(deptcode)
                                        .append("',max(changedate),0,0 from ")
                                        .append(tabename)
                                        .append(" where ifnull(tag,0)=0 ");
                                sbsql.append(";");
                            }
                            // 删除重复的记录
                            listNewAddSql.add(sbsql.toString());
                            sbsql.setLength(0);
                            sbsql.append(
                                    "delete from hse_sys_record_ts_sub where sys_dept='")
                                    .append(deptcode)
                                    .append("' and sys_tablename='")
                                    .append(tabename)
                                    .append("' and sys_subid not in (select max(sys_subid) from hse_sys_record_ts_sub where sys_dept='")
                                    .append(deptcode)
                                    .append("' and sys_tablename='")
                                    .append(tabename).append("')");
                        } else {
                            sbsql.append("update hse_sys_record_ts")
                                    .append(" set SYS_TS=")
                                    .append(" (select max(changedate) from  ")
                                    .append(tabename)
                                    .append(") where SYS_TABLENAME='")
                                    .append(tabename).append("';");
                        }
                        listUpdate.add(sbsql.toString());
                        sbsql.setLength(0);

                        sbsql.append("delete from ").append(tabename)
                                .append(" where dr=1;");
                        listDeleteDr.add(sbsql.toString());
                        sbsql.setLength(0);
                    }
                }
            } else {
                throw new HDException("hse_sys_record_ts表没有配置要下载的表;");
            }
            // 判断下载的票
            if (sbtable.length() == 0) {
                throw new HDException("hse_sys_record_ts 记录配置不正确;");
            }

            return sbtable.toString();
        } catch (HDException e) {
            throw e;
        }
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.METHOD_COMMON_DOWNTABLE;
    }

    public abstract boolean getInit();

    /**
     * 设置条件读取数据
     */
    public abstract String getWhere();

    /**
     * 得到当前正在进行的操作是初始化还是更新
     *
     * @return
     */
    public abstract String getFuction();


}
