package com.hd.hse.ss;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.worklog.WorkLogEntry;

import java.util.List;

public class CheckSupervisionRecords {
    private static CheckSupervisionRecords instance;

    private CheckSupervisionRecords() {
        dao = new BaseDao();
    }

    private static BaseDao dao;

    public static CheckSupervisionRecords getInstance() {
        if (instance == null) {
            instance = new CheckSupervisionRecords();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public List<WorkLogEntry> checkSupervisionList(String jcperson) throws HDException {
        String sql = "select * from ud_cbsgl_rz where createby = '" + jcperson + "' order by createdate desc;";
        try {
            return (List<WorkLogEntry>) dao.executeQuery(sql, new EntityListResult(WorkLogEntry.class));
        } catch (DaoException e) {
            throw new HDException("查询检查记录列表失败!");
        }
    }
}
