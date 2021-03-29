package com.hd.hse.common.component.phone.signbusiness;

import android.text.TextUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.utils.UtilTools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by yangning on 2018/11/13 16:06.
 * 签名删除
 */
public class SignManager {
    public static boolean dispatchSign(WorkApprovalPermission wap, int delePosition) {
        String[] personNames = wap.getPersondesc().split(",");
        String[] personIds = wap.getPersonid().split(",");
        String[] defaultPersonNames = wap.getDefaultpersondesc().split(",");
        String[] defaultPersonIds = wap.getDefaultpersonid().split(",");

        //人员卡号
        List<String> codeList = null;
        if (wap.getCode() != null) {
            codeList = transform2List(wap.getCode().split(","));
        }
        //工种编码
        List<String> jobList = null;
        if (wap.getJob() != null) {
            jobList = transform2List(wap.getJob().split(","));
        }
        //准操项目
        List<String> zcprojectList = null;
        if (wap.getZcproject() != null) {
            zcprojectList = transform2List(wap.getZcproject().split(","));
        }

        List<String> personNameList = transform2List(personNames);
        List<String> personIdList = transform2List(personIds);
        List<String> defaultPersonNameList = transform2List(defaultPersonNames);
        List<String> defaultPersonIdList = transform2List(defaultPersonIds);

        //将要删除的id
        String deleId = personIdList.get(delePosition);
        personNameList.remove(delePosition);
        personIdList.remove(delePosition);
        defaultPersonNameList.remove(delePosition);
        defaultPersonIdList.remove(delePosition);
        //刷卡拍照照片删除
        //电子签名处理
        //特种资质处理
        if (codeList != null) {
            int position = -1;//删除位置
            for (int i = 0; i < codeList.size(); i++) {
                String c = codeList.get(i);
                if (c.equals(deleId)) {
                    position = i;
                    break;
                }
            }
            if (position != -1) {
                codeList.remove(position);
                wap.setCode(list2String(codeList));
                if (jobList != null && jobList.size() > position) {
                    jobList.remove(position);
                    wap.setJob(list2String(jobList));
                }
                if (zcprojectList != null && zcprojectList.size() > position) {
                    zcprojectList.remove(position);
                    wap.setZcproject(list2String(zcprojectList));
                }
            }
        }


        if (personNameList.isEmpty()) {
            wap.setPersondesc(null);
            wap.setPersonid(null);
            wap.setDefaultpersondesc(null);
            wap.setDefaultpersonid(null);
        } else if (personNameList.size() == personIdList.size()) {
            wap.setPersondesc(list2String(personNameList));
            wap.setPersonid(list2String(personIdList));
            wap.setDefaultpersondesc(list2String(defaultPersonNameList));
            wap.setDefaultpersonid(list2String(defaultPersonIdList));

        } else {
            return false;
        }

        if (TextUtils.isEmpty(wap.getPersondesc())) {
            //环节点变为可操作状态
            wap.setIsexmaineable(1);
            return deleteSign(wap);
        } else {
            return updateSign(wap);
        }
    }

    /**
     * 更新记录
     *
     * @param wap
     * @return
     */
    public static boolean updateSign(WorkApprovalPermission wap) {

        WorkApprovalPersonRecord personRecord = getJl(wap);

        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            dao.updateEntity(con, personRecord, new String[]{
                    "person_name", "person", "sptime", "job", "code", "zcproject", "isupload", "ud_zyxk_zyspryjlid"});

            con.commit();
            return true;
        } catch (SQLException e) {
            return false;
        } catch (DaoException e) {
            return false;
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     * 删除记录
     *
     * @param wap
     * @return
     */
    public static boolean deleteSign(WorkApprovalPermission wap) {
        IConnectionSource conSrc = null;
        IConnection con = null;
        String sql = "delete from ud_zyxk_zyspryjl where ud_zyxk_zyspryjlid='" + wap.getUd_zyxk_zyspryjlid() + "'";
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            dao.executeUpdate(con, sql);
            con.commit();
            //将审批人员记录id设为空,将审批时间置为空
            wap.setUd_zyxk_zyspryjlid(null);
            wap.setSptime(null);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (DaoException e) {
            return false;
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     * 得到审批记录
     *
     * @param wap
     * @return
     */
    private static WorkApprovalPersonRecord getJl(WorkApprovalPermission wap) {
        WorkApprovalPersonRecord personRecord = new WorkApprovalPersonRecord();
        personRecord.setUd_zyxk_zyspryjlid(wap.getUd_zyxk_zyspryjlid());
        personRecord.setSpnode(wap.getSpfield());
        personRecord.setValue(wap.getSpfield_desc());
        personRecord.setSptime(UtilTools.getSysCurrentTime());
        personRecord.setPerson(wap.getPersonid());
        personRecord.setPerson_name(wap.getPersondesc());
        personRecord.setDycode(wap.getZylocation());
        personRecord.setJob(wap.getJob());
        personRecord.setCode(wap.getCode());
        personRecord.setZcproject(wap.getZcproject());
        personRecord.setIsupload(0);
        return personRecord;
    }

    /**
     * 数组转list
     *
     * @param group
     * @return
     */
    private static List<String> transform2List(String[] group) {
        List<String> list = null;
        if (group != null) {
            list = new ArrayList<>();
            for (String s : group) {
                list.add(s);
            }
        }
        return list;
    }

    private static String list2String(List<String> list) {
        String s = "";
        if (list != null && !list.isEmpty()) {
            for (String l : list) {
                s += TextUtils.isEmpty(s) ? l : "," + l;
            }
        }
        return s;
    }

    /**
     * 是否删除签名
     */
    public static boolean checkIsDeleSign() {
        boolean isqy = false;
        BaseDao baseDao = new BaseDao();
        String sql = "select * from sys_relation_info where sys_type='ISDELESIGN'";
        RelationTableName relationTableName = null;
        try {
            relationTableName = (RelationTableName) baseDao.executeQuery(sql,
                    new EntityResult(RelationTableName.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (relationTableName != null) {
            isqy = relationTableName.getIsqy() == 1 ? true : false;
        }
        return isqy;

    }
}
