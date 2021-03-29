package com.hd.hse.dc.business.common.weblistener.down;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.hd.hse.padinterface.PadInterfaceRequest.KEY_ZCPROJECT;

public class GainCertificateImgURLs extends GainPCDataListener {
    private static Logger logger = LogUtils.getLogger(GainNewServiceUrl.class);
    private Map<String, Object> map;
    private Map<String, String[]> retStr;
    private String personid; // 作业任务ID
    private String ud_cbsgl_rytzzyid;
    private String zcproject;

    public GainCertificateImgURLs(String personid, String ud_cbsgl_rytzzyid, String zcproject) {
        this.personid = personid;
        this.ud_cbsgl_rytzzyid = ud_cbsgl_rytzzyid;
        this.zcproject = zcproject;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        retStr = null;

        try {
            super.action(action, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "", retStr);
        } catch (HDException e) {
            this.sendMessage(IMessageWhat.ERROR, 50, 50, e.getMessage(), null);
        }

        return retStr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        super.afterDataInfo(pcdata, obj);
        if (pcdata instanceof PadInterfaceResponse) {
            PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
            setWritelog("pc报错：" + response.getExceptionmsg());
            throw new HDException("未获取到该文件");
        } else if (pcdata instanceof Map) {
            map = (Map<String, Object>) pcdata;
            if (map.size() == 0) {
                retStr = null;
            } else {
                if (map.containsKey(PadInterfaceRequest.KEY_URL)
                        && map.get(PadInterfaceRequest.KEY_URL) != null
                        && !map.get(PadInterfaceRequest.KEY_URL).equals("")) {
                    String[] urls = map.get(PadInterfaceRequest.KEY_URL)
                            .toString().split("\\^");
                    String[] names = map.get(PadInterfaceRequest.KEY_NAME)
                            .toString().split("\\^");
                    retStr = new HashMap<>();
                    retStr.put(PadInterfaceRequest.KEY_URL, urls);
                    retStr.put(PadInterfaceRequest.KEY_NAME, names);
                }
            }
        } else {
            retStr = null;
        }
    }

    @Override
    public Object initParam() throws HDException {
        map = new HashMap<String, Object>();
        map.put(PadInterfaceRequest.KEYPERSONID, this.personid);
        map.put(PadInterfaceRequest.KEY_UDCBSGLRYTZZYID, this.ud_cbsgl_rytzzyid);
        map.put(KEY_ZCPROJECT, zcproject);

        return map;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getMethodType() {
        return PadInterfaceContainers.GET_CERTIFICATE_IMG;
    }
}
