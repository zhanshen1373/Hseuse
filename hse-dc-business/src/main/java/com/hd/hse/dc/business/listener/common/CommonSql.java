package com.hd.hse.dc.business.listener.common;

import com.hd.hse.common.exception.HDException;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * ClassName: DeleteDataInfo ()<br/>
 * date: 2014年11月19日 <br/>
 *
 * @author lxf
 */
public class CommonSql {

    private static LinkedHashMap<String, String> hashDealTable;

    /**
     * getDealTable:(获得要删除的表). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @return
     * @author lxf
     */
    private static LinkedHashMap<String, String> getDealTable() {
        if (null == hashDealTable) {
            hashDealTable = new LinkedHashMap<String, String>();

            // 删除图片表信息 Image2ZYSQ
            // hashDealTable.put("hse_sys_image", "tableid");

            // 删除能量隔离单信息 UD_ZYXK_NLGLD
            // hashDealTable.put("UD_ZYXK_NLGLD", "UD_ZYXK_ZYSQID");
            // 删除人员审批记录表 UD_ZYXK_ZYSPRYJL
            hashDealTable.put("UD_ZYXK_ZYSPRYJL", "UD_ZYXK_ZYSQID");
            // 逐条措施确认表
            //hashDealTable.put("UD_ONEMEASURE_PERSON", "tableid");
            hashDealTable.put("UD_ONEMEASURE_PERSON", "UD_ZYXK_ZYSQID");
            // 危害识别 UD_ZYXK_ZYSQ2HAZARD
            hashDealTable.put("UD_ZYXK_ZYSQ2HAZARD", "UD_ZYXK_ZYSQID");
            // 措施 UD_ZYXK_ZYSQ2PRECAUTION
            hashDealTable.put("UD_ZYXK_ZYSQ2PRECAUTION", "UD_ZYXK_ZYSQID");
            // 个人防护装备 UD_ZYXK_ZYSQ2UD_ZYXK_GRFHZB
            hashDealTable.put("UD_ZYXK_ZYSQ2UD_ZYXK_GRFHZB", "UD_ZYXK_ZYSQID");
            // 现场复查子表
            hashDealTable.put("UD_ZYXK_ZYCSFCLINE", "UD_ZYXK_ZYSQID");
            // 现场复查主表
            hashDealTable.put("UD_ZYXK_ZYCSFC", "UD_ZYXK_ZYSQID");
            // 延期
            hashDealTable.put("UD_ZYXK_ZYYQ", "UD_ZYXK_ZYSQID");
            // 挖掘隐藏措施
            hashDealTable.put("UD_ZYXK_YCSSQK", "UD_ZYXK_ZYSQID");
            // 吊装作业
            hashDealTable.put("UD_ZYXK_DZZY", "UD_ZYXK_ZYSQID");
            // // 高处作业
            // hashDealTable.put("UD_ZYXK_GCZY", "UD_ZYXK_ZYSQID");
            // // 管线设备 UD_ZYXK_GXASSET
            // hashDealTable.put("UD_ZYXK_GXASSET", "UD_ZYXK_ZYSQID");
            // // 临时用电 UD_ZYXK_LSYDZY
            // hashDealTable.put("UD_ZYXK_LSYDZY", "UD_ZYXK_ZYSQID");
            // 气体检测子表
            hashDealTable.put("UD_ZYXK_QTJCLINE", "ud_zyxk_qtjcid");
            // 气体检测表
            hashDealTable.put("UD_ZYXK_QTJC", "UD_ZYXK_ZYSQID");
            // 能量隔离子表
            hashDealTable.put("UD_ZYXK_NLGLDLINE", "ud_zyxk_nlgldid");
            // 能量隔离单
            hashDealTable.put("UD_ZYXK_NLGLD", "UD_ZYXK_ZYSQID");
            // 受限空间表
            // hashDealTable.put("UD_ZYXK_SXKJ", "UD_ZYXK_ZYSQID");
            // // 违章检查
            // hashDealTable.put("UD_ZYXK_WZJC", "UD_ZYXK_ZYSQID");
            // // 挖掘作业
            // hashDealTable.put("UD_ZYXK_WJZY", "UD_ZYXK_ZYSQID");

            // 作业申请表
            hashDealTable.put("UD_ZYXK_ZYSQ", "UD_ZYXK_ZYSQID");
            // 任务表
            hashDealTable.put("ud_zyxk_worktask", "ud_zyxk_worktaskid");

            hashDealTable.put("ud_zyxk_lsydzy", "UD_ZYXK_ZYSQID");
            hashDealTable.put("ud_zyxk_ydasset", "UD_ZYXK_ZYSQID");

        }
        // return null;
        return hashDealTable;
    }

    /**
     * getExecListSql:(根据动作类型返回更新或者删除语句). <br/>
     * date: 2014年10月10日 <br/>
     *
     * @param str
     * @param action delete 或update
     * @return
     * @throws HDException
     * @author lxf
     */
    public static List<String> getExecListSql(String str, String action)
            throws HDException {

        List<String> listsql = new ArrayList<String>();
        StringBuilder sbsql = new StringBuilder();
        StringBuilder sbsqltask = new StringBuilder();
        String tablename;
        // 获取要删除的表数据
        HashMap<String, String> dealTable = getDealTable();
        if (null != dealTable && dealTable.size() > 0) {
            Iterator<Entry<String, String>> iter = dealTable.entrySet()
                    .iterator();
            while (iter.hasNext()) {
                Entry<String, String> entry = iter.next();
                tablename = entry.getKey();// entry.getValue();

                if (tablename.equalsIgnoreCase("UD_ZYXK_ZYCSFCLINE")) {
                    // 作业措施复查子表需要特殊处理
                    if (action.equalsIgnoreCase("delete")) {
                        sbsql.append("delete from UD_ZYXK_ZYCSFCLINE ");
                    } else {
                        sbsql.append("update UD_ZYXK_ZYCSFCLINE set isupload=1 ");
                    }
                    sbsql.append(
                            " where zycsfcnum in (select zycsfcnum from ud_zyxk_zycsfc where ud_zyxk_zysqid in(")
                            .append(str).append("));");
                } else if (tablename.equalsIgnoreCase("UD_ZYXK_NLGLD")) {
                    // 能量隔离单需要特殊处理
                    if (action.equalsIgnoreCase("delete")) {
                        sbsql.append("delete from UD_ZYXK_NLGLD ");
                    } else {
                        sbsql.append("update UD_ZYXK_NLGLD set isupload=1 ");
                    }
                    sbsql.append(
                            " where NLGLDNUM in (select NLGLDNUM from UD_ZYXK_ZYSQ where UD_ZYXK_ZYSQID in(")
                            .append(str).append("));");
                } else if (tablename.equalsIgnoreCase("UD_ZYXK_NLGLDLINE")) {
                    // 能量隔离单子表处理
                    if (action.equalsIgnoreCase("delete")) {
                        sbsql.append("delete from UD_ZYXK_NLGLDLINE ");
                    } else {
                        sbsql.append("update UD_ZYXK_NLGLDLINE set isupload=1 ");
                    }
                    sbsql.append(
                            " where NLGLDNUM in (select NLGLDNUM from UD_ZYXK_ZYSQ where UD_ZYXK_ZYSQID in(")
                            .append(str).append("));");
                } else if (tablename.equalsIgnoreCase("hse_sys_image")) {

                } else if (tablename.equalsIgnoreCase("ud_zyxk_worktask")) {
                    //
                    if (action.equalsIgnoreCase("delete")) {
                        sbsqltask
                                .append(" delete from ud_zyxk_worktask where ")
                                .append(entry.getValue())
                                .append(" not in(select ud_zyxk_worktaskid from ud_zyxk_zysq);");
                    }
                } else if (tablename.equalsIgnoreCase("ud_zyxk_qtjcline")) {
                    // 上传子表信息
                    if (action.equalsIgnoreCase("delete")) {
                        sbsqltask
                                .append(" delete from ud_zyxk_qtjcline where ")
                                .append(entry.getValue())
                                .append(" not in(select ud_zyxk_qtjcid from ud_zyxk_qtjc);");
                    }
                } else if (!StringUtils.isEmpty(tablename)
                        && !StringUtils.isEmpty(entry.getKey())) {
                    if (action.equalsIgnoreCase("delete")) {
                        sbsql.append("delete from ").append(entry.getKey());
                    } else {
                        sbsql.append("update ").append(entry.getKey())
                                .append(" set isupload=1 ");
                    }
                    sbsql.append(" where ").append(entry.getValue())
                            .append(" in (").append(str).append(");");
                }
                if (sbsql.length() > 0) {
                    listsql.add(sbsql.toString());
                    sbsql.delete(0, sbsql.length());
                }
            }
            if (listsql.size() > 0 && sbsqltask.length() > 0) {
                listsql.add(sbsqltask.toString());
            }
        }
        return listsql;
    }
}
