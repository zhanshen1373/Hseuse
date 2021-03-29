package com.hd.hse.dc.business.common.webservice;

import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.AndroidInterface;
import com.hd.hse.padinterface.PadInterfaceContainers;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: HessianClient1 ()<br/>
 * date: 2015年3月19日 <br/>
 *
 * @author lxf
 */
public class HessianClient extends AbstractWebServiceClient {

    private static Logger logger = LogUtils.getLogger(HessianClient.class);

    private static HessianProxyFactory factory = null;

    /**
     * basic:TODO(接口交互).
     */
    private AndroidInterface basic;

    /**
     * newInstance:(实例化交互对象). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @return HessianClient 交互对象
     * @author lxf
     */
    public static HessianClient newInstance() {
        return new HessianClient();
    }

    /**
     * Creates a new instance of HessianClient1. 私有的
     */
    private HessianClient() {
        // initAttribut();
    }

    /**
     * initAttribut:初始化属性值
     */
    private void initAttribut() throws HDException {
        if (factory == null) {
            factory = new HessianProxyFactory();
            factory.setConnectTimeout(8000);
        }
        try {
            factory.setDebug(false);
            //这个有作用
            //如果读取数据的时间大于设置的时间，会抛出hessianclient 500异常
            //并且马上就登录进去
            factory.setReadTimeout(this.getWebconfig().getTimeOut());
            // 不设置会报 expected hessian reply at 0x48
            // factory.setHessian2Reply(false);
            basic = (AndroidInterface) factory.create(AndroidInterface.class,
                    this.getWebconfig().getUrl());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HDException(e.getMessage());
        }
    }

    /**
     * 交互方式的入口 根据action 执行相应的动作 TODO
     *
     * @see AbstractWebServiceClient#action(String,
     * Object[])
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Object action(String action, Object... args) throws HDException {
        Object obj = null;
        if (args != null) {
            switch (args.length) {
                case 1:
                    if (args[0] instanceof Map) {
                        obj = getRequestObject((HashMap) args[0], action, null,
                                null);
                    } else if (args[0] == null) {
                        obj = getRequestObject(null, action, null,
                                null);
                    }
                    break;
                case 2:
                    if (args[0] instanceof Map && args[1] instanceof byte[]) {
                        obj = getRequestObject((HashMap) args[0], action,
                                (byte[]) args[1], null);
                    }
                    break;
                case 3:
                    if (args[0] instanceof Map && args[2] instanceof InputStream) {
                        obj = getRequestObject((HashMap) args[0], action, null,
                                (InputStream) args[2]);
                    }
                    break;
                default:
                    break;
            }
        } else {
            obj = getRequestObject(null, action, null, null);
        }
        return obj;
    }

    /**
     * 上传文件 path 表示本地路径+文件 TODO actionType 默认值
     * PadInterfaceContainers.METHOD_COMMON_UPFILE
     *
     * @see AbstractWebServiceClient#upFile(String,
     * String, HashMap)
     */
    @Override
    public Object upFile(String actionType, String path,
                         HashMap<String, Object> map) throws HDException {
        try {
            InputStream data = new BufferedInputStream(
                    new FileInputStream(path));
            return getRequestObject(
                    map,
                    StringUtils.isEmpty(actionType) ? PadInterfaceContainers.METHOD_COMMON_UPFILE
                            : actionType, null, data);
        } catch (Exception e) {
            logger.error(e);
            throw new HDException("上传文件报错");
        }
    }

    /**
     * getRequestObject:(). <br/>
     * date: 2014年9月3日 <br/>
     *
     * @param map
     * @param type
     * @param bt
     * @param input
     * @return
     * @throws HDException
     * @author Administrator
     */
    private Object getRequestObject(HashMap<String, Object> map, String type,
                                    byte[] bt, InputStream input) throws HDException {
        Object retobject = null;
        try {
            initAttribut();
            if (null != basic) {
                retobject = basic.exchange(type, map, bt, input);
            }
        } catch (SocketTimeoutException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("网络异常，请确认！");
        } catch (ConnectException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("网络异常，请确认！");
        } catch (HessianConnectionException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("网络异常，请确认！");
        } catch (HessianRuntimeException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("网络异常，请确认！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HDException("网络异常，请确认！");
        }
        return retobject;

    }
}
