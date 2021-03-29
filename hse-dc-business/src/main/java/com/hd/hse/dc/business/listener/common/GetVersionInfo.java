package com.hd.hse.dc.business.listener.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * ClassName: GetVersionInfo (获取服务器版本信息)<br/>
 * date: 2015年3月19日 <br/>
 *
 * @author lxf
 */
public class GetVersionInfo extends GainPCDataListener {

    private static Logger logger = LogUtils.getLogger(GetVersionInfo.class);
    private static String KEYVALUEVERSION = "ZYXKVERSION";
    private Context context;

    public GetVersionInfo(Context context) {
        this.context = context;
    }

    /**
     * setKeyValueVersion:(添加设置取动态域的主键). <br/>
     * date: 2015年5月4日 <br/>
     *
     * @param keyValue
     * @author lxf
     */
    public void setKeyValueVersion(String keyValue) {
        KEYVALUEVERSION = keyValue;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        Object obj = null;
        try {
            obj = super.action(action, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "", obj);
        } catch (HDException e) {
            getLogger().error(e.getMessage(), e);
            this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
        }
        return obj;
    }

    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        // TODO Auto-generated method stub
        super.afterDataInfo(pcdata, obj);
        if (pcdata instanceof PadInterfaceResponse) {
            PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
            setWritelog("pc报错：" + response.getExceptionmsg());
            throw new HDException("下载版本信息报错,请联系管理员");
        }
    }

    @Override
    public Object initParam() {
        // TODO Auto-generated method stub
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        String dept = null;
        if (SystemProperty.getSystemProperty().getLoginPerson() != null) {
            hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
                    .getSystemProperty().getLoginPerson().getPersonid());
            dept = SystemProperty.getSystemProperty().getLoginPerson()
                    .getDepartment();
        }


        if (!StringUtils.isEmpty(dept)) {
            hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
        }
        // value 表示请求承包商的url
        hashmap.put(PadInterfaceRequest.KEYPADAPKURL, KEYVALUEVERSION);
        try {
            hashmap.put(PadInterfaceRequest.KEYPADVERSION, getVersionName());
        } catch (NameNotFoundException e) {
            logger.error(e);
        }
        return hashmap;
    }

    private String getVersionName() throws NameNotFoundException {
        PackageManager packageManger = context.getPackageManager();
        PackageInfo packinfo = packageManger.getPackageInfo(
                context.getPackageName(), 0);
        return packinfo.versionName;
    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.METHOD_COMMON_VERSIONIFNO;
    }

}
