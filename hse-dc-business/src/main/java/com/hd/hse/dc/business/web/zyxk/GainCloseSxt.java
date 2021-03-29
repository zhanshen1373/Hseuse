package com.hd.hse.dc.business.web.zyxk;

import android.text.TextUtils;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.weblistener.down.DataListListener;
import com.hd.hse.entity.workorder.SxtBean;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class GainCloseSxt extends DataListListener {

    private static Logger logger = LogUtils.getLogger(GainSxtList.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private SxtBean.CamerasBean locations;
    private String rtsp;
    private String delay;


    public GainCloseSxt(SxtBean.CamerasBean locations, String rtsp, String delay) {
        this.locations = locations;
        this.rtsp = rtsp;
        this.delay = delay;
    }

    @Override
    public BaseWebResult getResultChangeType() {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public Class<?> getEntityClass() {
        // TODO Auto-generated method stub
        return String.class;
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
        if (rtsp != null) {
            hashmap.put(PadInterfaceRequest.RTSP, rtsp);
        } else if (locations != null) {
            hashmap.put(PadInterfaceRequest.RTSP, locations.getRtsp());
        }


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
        if (rtsp != null) {
            return "SDGL_002";
        } else if (locations != null) {
            if (TextUtils.isEmpty(delay)){
                return "SDGL_003";
            }else{
                return "SDGL_004";
            }
        }
        return null;

    }

}
