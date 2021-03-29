package com.hd.hse.main.phone.ui.activity.remoteappr;

import android.app.Activity;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.workorder.RemoteApprLine;

import java.sql.SQLException;
import java.util.List;

import static com.hd.hse.constant.IWorkOrderStatus.NULLIFY;
import static com.hd.hse.constant.IWorkOrderZypClass.ZYPCLASS_ZYDP;

/**
 * 远程审批不同意时，如果存在远程审批大票，将关联的小票置为作废
 * created by yangning on 2017/11/30 15:27.
 */

public class RemoteApprDisagreeBusiness {
    // private ProgressDialog progressDialog;
    /**
     * 上传成功后的远程审批信息
     */
    private List<RemoteApprLine> upData;
    private Activity activity;

    public RemoteApprDisagreeBusiness(List<RemoteApprLine> upData, Activity activity) {
        this.upData = upData;
        this.activity = activity;

    }

    public void handleZyp() {

        String zyDpids = null;
        for (RemoteApprLine apprLine : upData) {
            if (apprLine.getZysqid() != null && apprLine.getZypclass() != null && ZYPCLASS_ZYDP.equalsIgnoreCase(apprLine.getZypclass())) {
                zyDpids = (zyDpids == null ? ("'" + apprLine.getZysqid() + "'") : (zyDpids + "," + "'" + apprLine.getZysqid()) + "'");
            }
        }
        if (zyDpids == null) {

            return;
        }
        String sql = "update ud_zyxk_zysq set status = '" + NULLIFY + "' where parent_id in (" + zyDpids + ")";
        IConnectionSource conSrc = null;
        IConnection con = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();

            dao.executeUpdate(con, sql);

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (DaoException e) {
            e.printStackTrace();

        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);

                } catch (SQLException e) {

                }
            }

        }


    }

}
