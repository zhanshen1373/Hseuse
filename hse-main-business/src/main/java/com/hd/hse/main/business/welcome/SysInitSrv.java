/**
 * Project Name:hse-main-app
 * File Name:SysInitSrv.java
 * Package Name:com.hd.hse.main.business.welcome
 * Date:2014年9月25日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.main.business.welcome;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:SysInitSrv (系统启动服务).<br/>
 * Date: 2014年9月25日 <br/>
 *
 * @author lg
 * @see
 */
public class SysInitSrv {
    private static Logger logger = LogUtils.getLogger(SysInitSrv.class);

    /**
     * upgradeDB:(数据库升级). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @param lstSql
     * @throws HDException
     * @author lg
     */
    public void upgradeDB(List<String> lstSql, String desVersion)
            throws HDException {
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            if (lstSql != null && !lstSql.isEmpty()) {
                //解决数据库版本2.5.6提前插入了insert into hse_sys_record_ts values(27,'ud_sys_appanlscfg','ud_sys_appanlscfgid','统计分析表',1,1,0,null,0,1,0)语句
                for (int i = 0; i < lstSql.size(); i++) {
                    String sql = lstSql.get(i);
                    if (sql != null &&
                            sql.equals("insert into hse_sys_record_ts values(27,'ud_sys_appanlscfg','ud_sys_appanlscfgid','统计分析表',1,1,0,null,0,1,0)")) {
                        //查询数据库是不是已经存在此数据，如果存在，从队列移除，
                        String qureySql = "select sys_id from hse_sys_record_ts where sys_id=27";
                        Map<String, Integer> map = (Map<String, Integer>) dao.executeQuery(con, qureySql, new MapResult());
                        if (map != null && map.containsKey("sys_id") && map.get("sys_id") == 27) {
                            lstSql.remove(i);
                            break;
                        }
                    }
                }
                dao.executeUpdate(con,
                        lstSql.toArray(new String[lstSql.size()]));

            }
            // 更新目标版本
            StringBuilder updateSql = new StringBuilder();
            updateSql.append("update hse_sys_config set sysurl='");
            updateSql.append(desVersion).append("' where syscode='dbversion'");
            dao.executeUpdate(con, updateSql.toString());
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new AppException("系统升级出错，请联系系统管理员！");
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
     * getCurVersion:(获取当前版本). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @return
     * @throws HDException
     * @author lg
     */
    @SuppressWarnings("unchecked")
    public String getCurVersion() throws HDException {
        BaseDao dao = new BaseDao();
        String sql = "select syscode,sysurl from hse_sys_config where syscode='dbversion'";
        HashMap<String, Object> mapVersion = null;
        mapVersion = (HashMap<String, Object>) dao.executeQuery(sql,
                new MapResult());
        return mapVersion.get("sysurl").toString();
    }
}
