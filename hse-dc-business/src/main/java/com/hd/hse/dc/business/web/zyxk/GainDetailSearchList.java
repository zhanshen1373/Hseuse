package com.hd.hse.dc.business.web.zyxk;

import com.google.gson.Gson;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.EntityListWebResult;
import com.hd.hse.dc.business.common.weblistener.down.DataListListener;
import com.hd.hse.entity.common.DetailSearch;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by dubojian on 2017/8/24.
 */

public class GainDetailSearchList extends DataListListener {

    private static Logger logger = LogUtils.getLogger(GainZYXKDataList.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    private boolean browseFlag = false;
    /**
     * search:TODO(查询条件).
     */
    private String search = null;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isBrowseFlag() {
        return browseFlag;
    }

    public void setBrowseFlag(boolean browseFlag) {
        this.browseFlag = browseFlag;
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
        return WorkTask.class;
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
        if (browseFlag) {
            // 表示查看本地作业票是，浏览PC端票
            hashmap.put(PadInterfaceRequest.KEYFUNCODE, "true");
        } else {
            // 表示作业票下载浏览
            hashmap.put(PadInterfaceRequest.KEYFUNCODE, "false");
        }
        // if(search!=null && search.length()>0){
        if (search != null) {
            String[] split = search.split(",");
            DetailSearch detailSearch=new DetailSearch();
            detailSearch.setZYSTARTTIME(split[0]);
            detailSearch.setZYENDTIME(split[1]);
            detailSearch.setZYDEPT(split[2]);
            detailSearch.setZYPCLASS(split[3]);
            detailSearch.setZYNAME(split[4]);
            detailSearch.setZYLOCATION_DESC(split[5]);
            hashmap.put(PadInterfaceRequest.KEYWHERE, new Gson().toJson(detailSearch));

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
        return PadInterfaceContainers.METHOD_ZYP_DETAILSEARCH;
    }


}
