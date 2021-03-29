package com.hd.hse.dc.business.listener.common;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: PCCheckUser (验证用户)<br/>
 * date: 2015年3月19日  <br/>
 *
 * @author lxf
 */
public class PCCheckUser extends GainPCDataListener {
    private static Logger logger = LogUtils.getLogger(PCCheckUser.class);
    private HashMap<String, Object> hashmap;
    PersonCard pcard = null;

    @Override
    public Object action(String action, Object... args) throws HDException {
        Object obj = args[0];
        pcard = (PersonCard) obj;
        if (pcard == null) {
            throw new HDException("请传入验证的用户对象!");
        }
        super.action(action, args);
        return pcard;
    }

    @Override
    public void beforDataInfo(Object... args) throws HDException {
        hashmap = new HashMap<String, Object>();
        String str = pcard.getPcardnum();
        if (StringUtils.isEmpty(str)) {
            str = pcard.getPersonid();
        }
        if (StringUtils.isEmpty(str)) {
            throw new HDException("请刷卡或输入账号!");
        }
        String pw = pcard.getPassword();
        if (pcard.getEncryptParams() != null) {
            hashmap.put(PadInterfaceRequest.LOGINFROM, "ptapp");
        } else {
            hashmap.put(PadInterfaceRequest.LOGINFROM, "");
        }
        hashmap.put(PadInterfaceRequest.KEYPERSONID, str);
        hashmap.put(PadInterfaceRequest.KEYPASSWORD, pw);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
        if (pcdata instanceof PadInterfaceResponse) {
            PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
            setWritelog("pc报错：" + response.getExceptionmsg());
            throw new HDException("pc端验证失败,请联系管理员");
        }
        if (pcdata instanceof Map) {
            hashmap = (HashMap<String, Object>) pcdata;
            if (hashmap.size() == 0) {
                pcard = null;
            } else {
                if (hashmap.containsKey(PadInterfaceRequest.KEYPERSONID))
                    pcard.setPersonid(hashmap.get(PadInterfaceRequest.KEYPERSONID)
                            .toString());
                if (hashmap.containsKey(PadInterfaceRequest.KEYPERSONDESC))
                    pcard.setPersonid_desc(hashmap.get(PadInterfaceRequest.KEYPERSONDESC)
                            .toString());
                if (hashmap.containsKey(PadInterfaceRequest.KEYDEPTNUM))
                    pcard.setDepartment(hashmap.get(PadInterfaceRequest.KEYDEPTNUM)
                            .toString());
                if (hashmap.containsKey(PadInterfaceRequest.KEYDEPTDESC))
                    pcard.setDepartment_desc(hashmap.get(PadInterfaceRequest.KEYDEPTDESC)
                            .toString());
                if (hashmap.containsKey(PadInterfaceRequest.KEYUUID)) {
                    pcard.setUuid(hashmap.get(PadInterfaceRequest.KEYUUID).toString());
                }
            }
        } else {
            pcard = null;
        }
    }

    @Override
    public Object initParam() {
        // TODO Auto-generated method stub
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
        return PadInterfaceContainers.METHOD_COMMON_CHECKUSER;
    }

}
