package com.hd.hse.dc.business.web.zyxk;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.weblistener.down.InitialPageDataListener;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.HashMap;

import static com.hd.hse.system.SystemProperty.getSystemProperty;

/**
 * Created by dubojian on 2017/7/21.
 */

public class GainChartData extends InitialPageDataListener {


    private static Logger logger = LogUtils.getLogger(GainChartData.class);
    private HashMap<String, Object> hashmap = new HashMap<String, Object>();
    /**
     * search:TODO(查询条件).
     */
    private String search = null;
    private String pageType;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    @Override
    public BaseWebResult getResultChangeType() {
        // TODO Auto-generated method stub
//        try {
//            return new EntityListWebResult(getEntityClass(),
//                    getAnalyzeDataAdapter());
//        } catch (HDException e) {
//            setWritelog(e.getMessage());
//        }
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
        hashmap.put(PadInterfaceRequest.KEYPERSONID,
                getSystemProperty().getLoginPerson().getPersonid());

        // if(search!=null && search.length()>0){

        if (search != null) {
            hashmap.put(PadInterfaceRequest.KEYCHUANTOU, search);
        }
        if (pageType != null) {
            hashmap.put(PadInterfaceRequest.KEYPAGETYPE, pageType);
            if ("USEPAGE".equals(pageType)) {
                hashmap.put(PadInterfaceRequest.KEYDEPARTMENT,
                        getSystemProperty().getLoginPerson().getDepartment());
            }
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
        return PadInterfaceContainers.GET_DALIAN_TUBIAO_MESSAGE;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }
}
