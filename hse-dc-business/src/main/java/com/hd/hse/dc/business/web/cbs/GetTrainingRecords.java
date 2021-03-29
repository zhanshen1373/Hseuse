package com.hd.hse.dc.business.web.cbs;

import com.google.gson.Gson;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.entity.rycheck.FirstEduList;
import com.hd.hse.entity.rycheck.TrainingAchievement;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * created by yangning on 2018/1/26 15:27.
 */

public class GetTrainingRecords extends GainPCDataListener {
    private static Logger logger = LogUtils.getLogger(GetTrainingRecords.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private String idcard = null;
    private List<TrainingAchievement> trainingAchievements;

    public GetTrainingRecords(String idcard) {
        this.idcard = idcard;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        try {
            super.action(action, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "", trainingAchievements);
        } catch (HDException e) {
            this.sendMessage(IMessageWhat.ERROR, 50, 50, e.getMessage(), null);
        }
        return trainingAchievements;
    }

    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        super.afterDataInfo(pcdata, obj);
        // TODO Auto-generated method stub
        //logger.error("list"+pcdata.toString());
        Gson gson = new Gson();
        FirstEduList firstEduList
                = gson.fromJson(pcdata.toString(), FirstEduList.class);
        if (firstEduList != null) {
            trainingAchievements= firstEduList.getFIRSTEDU();
        }

    }

    @Override
    public Object initParam() {

        // hashmap.put(PadInterfaceRequest.KEYFUNCODE, value);
        hashmap.put(PadInterfaceRequest.KEY_IDCARD, idcard);
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
        return PadInterfaceContainers.METHOD_CBS_TRAININGRECORDS;
    }
}


