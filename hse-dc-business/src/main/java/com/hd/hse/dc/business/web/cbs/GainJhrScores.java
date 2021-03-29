package com.hd.hse.dc.business.web.cbs;

import com.google.gson.Gson;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.entity.rycheck.JhrData;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * created by yangning on 2019/3/26 10:02.
 */
public class GainJhrScores extends GainPCDataListener {
    private static Logger logger = LogUtils.getLogger(GainJhrScores.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private String personid = null;

    private JhrData jhrData;

    public GainJhrScores(String personid) {
        this.personid = personid;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        try {
            super.action(action, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "", jhrData);
        } catch (HDException e) {
            this.sendMessage(IMessageWhat.ERROR, 50, 50, e.getMessage(), null);
        }
        return jhrData;
    }

    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        super.afterDataInfo(pcdata, obj);
        Gson gson = new Gson();
        jhrData = gson.fromJson(pcdata.toString(), JhrData.class);
    }

    @Override
    public Object initParam() throws HDException {
        hashmap.put(PadInterfaceRequest.KEYPERSONID, personid);
        return hashmap;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getMethodType() {
        return PadInterfaceContainers.JHR_KSCJ;
    }
}
