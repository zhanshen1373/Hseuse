package com.hd.hse.pc;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.workorder.PersonSpecialTypeWork;

import java.util.List;

public class CheckPersonRecords {
    private static CheckPersonRecords instance;

    private CheckPersonRecords() {
        dao = new BaseDao();
    }

    private static BaseDao dao;

    public static CheckPersonRecords getInstance() {
        if (instance == null) {
            instance = new CheckPersonRecords();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public List<PersonSpecialTypeWork> checkPersonCertificateRecords(String personid) throws HDException {
        String sql = "select r.idcard, a.description as zsname, r.zsname as zsname_for_wsh, r.zsnum , r.fzdate, r.dqdate, r.ud_cbsgl_rytzzyid, r.zcproject from ud_cbsgl_rytzzy r "
                + "left join alndomain a on a.domainid = 'ZZ_CERTIFICATE' and a.value = r.zsname where r.idcard = '"
                + personid + "';";
        try {
            return (List<PersonSpecialTypeWork>) dao.executeQuery(sql, new EntityListResult(PersonSpecialTypeWork.class));
        } catch (DaoException e) {
            throw new HDException("查询证书记录失败!");
        }
    }
}
