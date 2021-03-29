package com.hd.hse.dc.business.web.zyxk;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.EntityListWebResult;
import com.hd.hse.dc.business.common.weblistener.down.DataListListener;
import com.hd.hse.entity.workorder.SxtBean;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class GainSxtList extends DataListListener {

    private static Logger logger = LogUtils.getLogger(GainSxtList.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private StringBuilder locations;


    public GainSxtList(StringBuilder locations) {
        this.locations = locations;
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
        return SxtBean.class;
    }

    @Override
    public String[] getColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object initParam() {
        hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
                .getSystemProperty().getLoginPerson().getPersonid());


        hashmap.put(PadInterfaceRequest.LOCATIONS, locations);

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
        return "SDGL_001";
    }

}
