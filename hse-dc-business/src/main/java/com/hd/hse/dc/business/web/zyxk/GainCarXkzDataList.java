package com.hd.hse.dc.business.web.zyxk;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.EntityListWebResult;
import com.hd.hse.dc.business.common.weblistener.down.DataListListener;
import com.hd.hse.entity.workorder.CarXkz;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class GainCarXkzDataList extends DataListListener {

    private static Logger logger = LogUtils.getLogger(GainCarXkzDataList.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private boolean browseFlag = false;
    /**
     * search:TODO(查询条件).
     */
    private Object[] search = null;


    public void setSearch(Object... s) {
        this.search = s;
    }


    @Override
    public BaseWebResult getResultChangeType() {
        // TODO Auto-generated method stub
        try {
            return new EntityListWebResult(getEntityClass(),
                    getAnalyzeDataAdapter());
        } catch (HDException e) {
            setWritelog(e.getMessage());
        }
        return null;
    }

    @Override
    public Class<?> getEntityClass() {
        // TODO Auto-generated method stub
        return CarXkz.class;
    }

    @Override
    public String[] getColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object initParam() {
        // if(search!=null && search.length()>0){
        if (search != null) {
            hashmap.put(PadInterfaceRequest.STARTTIME, search[0].toString());
            hashmap.put(PadInterfaceRequest.ENDTIME, search[1].toString());
            hashmap.put(PadInterfaceRequest.CARNUMBER, search[2].toString());
        }
        // String
        // dept=SystemProperty.getSystemProperty().getLoginPerson().getDepartment();
        // if(!StringUtils.isEmpty(dept))
        // {
        // hashmap.put(PadInterfaceRequest.KEYDEPTNUM,dept);
        // }
        // hashmap.put(PadInterfaceRequest.KEYDEPTNUM,
        // SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
        return hashmap;
    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.METHOD_CAR_XKZ_LIST;
    }

}
