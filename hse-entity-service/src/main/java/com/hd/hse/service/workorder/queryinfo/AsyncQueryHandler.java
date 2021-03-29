/**
 * Project Name:hse-entity-service
 * File Name:AsyncQueryHandler.java
 * Package Name:com.hd.hse.service.workorder.queryinfo
 * Date:2014年10月16日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.service.workorder.queryinfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

import android.os.Handler;
import android.os.Message;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

/**
 * ClassName: AsyncQueryHandler ()<br/>
 * date: 2014年10月16日 <br/>
 *
 * @author zhaofeng
 */
public class AsyncQueryHandler extends Handler {
    private final Logger logger = LogUtils.getLogger(AsyncQueryHandler.class);
    /**
     * asynThread:TODO(异步线程).
     */
    private AsyncQueryThread asynThread;
    /**
     * callEventListener:TODO(回调函数).
     */
    private IQueryCallEventListener callEventListener;

    /**
     * iQueryWorkInfo:TODO(接口).
     */
    private IQuery iQuery;
    /**
     * methodName:TODO(主方法名称).
     */
    private String methodName;
    /**
     * paramClass:TODO(主方法参数类型).
     */
    private Class<?>[] paramClass;
    /**
     * paObjects:TODO(主方法参数数据).
     */
    private Object[] paObjects;

    /**
     * entityClasss:TODO(子实体集合).
     */
    private Class<?>[] entityClasss;

    /**
     * entityClasss.
     *
     * @param entityClasss the entityClasss to set
     */
    public void setEntityClasss(Class<?>[] entityClasss) {
        this.entityClasss = entityClasss;
    }

    /**
     * childMathodNames:TODO(子方法名称集合).
     */
    private String[] childMethodNames;

    /**
     * childMathodNames.
     *
     * @param childMathodNames the childMathodNames to set
     */
    public void setChildMethodNames(String[] childMethodNames) {
        this.childMethodNames = childMethodNames;
    }

    /**
     * childListPaClasses:TODO(子方法参数类型集合).
     */
    private List<Class<?>[]> childListPaClasses;

    /**
     * childListPaClasses.
     *
     * @param childListPaClasses the childListPaClasses to set
     */
    public void setChildListPaClasses(List<Class<?>[]> childListPaClasses) {
        this.childListPaClasses = childListPaClasses;
    }

    /**
     * childListObjects:TODO(子参数集合数据).
     */
    private List<Object[]> childListObjects;

    /**
     * childListObjects.
     *
     * @param childListObjects the childListObjects to set
     */
    public void setChildListObjects(List<Object[]> childListObjects) {
        this.childListObjects = childListObjects;
    }

    /**
     * Creates a new instance of AsyncQueryHandler.
     *
     * @param callEventListener 回调函数
     */
    public AsyncQueryHandler(IQueryCallEventListener callEventListener) {
        this.callEventListener = callEventListener;
    }

    /**
     * excute:(启动异步查询线程). <br/>
     * date: 2014年10月20日 <br/>
     *
     * @param iQueryWorkInfo 接口
     * @param methodName     方法名称
     * @param paramClass     参数类型
     * @param paObjects      参数数据
     * @author zhaofeng
     */

    public void execute(IQuery iQuery, String methodName,
                        Class<?>[] paramClass, Object[] paObjects) {
        // TODO Auto-generated method stub
        this.iQuery = iQuery;
        this.methodName = methodName;
        this.paramClass = paramClass;
        this.paObjects = paObjects;
        if (asynThread == null)
            asynThread = new AsyncQueryThread();
        new Thread(asynThread).start();
    }

    public void execute(IQuery iQuery, String methodName,
                        Class<?>[] paramClass, Object[] paObjects, int isqy) {
        // TODO Auto-generated method stub
        this.iQuery = iQuery;
        this.methodName = methodName;
        this.paramClass = paramClass;
        this.paObjects = paObjects;
        if (asynThread == null)
            asynThread = new AsyncQueryThread();
        new Thread(asynThread).start();
    }

    /**
     * getObject:(获取对象). <br/>
     * date: 2014年10月20日 <br/>
     *
     * @return
     * @author zhaofeng
     */
    public Object getObject() {
        try {
            SuperEntity superEntity = null;
            Method method = iQuery.getClass().getDeclaredMethod(
                    methodName, paramClass);
            Object object = method.invoke(iQuery, paObjects);
            if (object instanceof SuperEntity) {
                superEntity = (SuperEntity) object;
                if (childMethodNames != null && childMethodNames.length > 0) {
                    for (int i = 0; i < childMethodNames.length; i++) {
                        Method childMethod = iQuery.getClass()
                                .getDeclaredMethod(childMethodNames[i],
                                        childListPaClasses.get(i));
                        Object childObject = method.invoke(iQuery,
                                childListObjects.get(i));
                        if (childObject instanceof List) {
                            superEntity.setChild(entityClasss[i].getName(),
                                    (List) childObject);
                        }
                    }
                }
            }
            return object;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            new HDException("系统错误，请联系管理员！", e);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            new HDException("系统错误，请联系管理员！", e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            new HDException("系统错误，请联系管理员！", e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            new HDException("系统错误，请联系管理员！", e);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            new HDException("系统错误，请联系管理员！", e);
        }
        return null;
    }

    /**
     * TODO
     *
     * @see android.os.Handler#handleMessage(android.os.Message)
     */
    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
        int msgWhat = msg.what;
        switch (msgWhat) {
            case IMessageWhat.START:
                logger.info("开始执行。。。");
                break;
            case IMessageWhat.PROCESSING:
                logger.info("执行中。。。");
                break;
            case IMessageWhat.END:
                logger.info("执行完成。。。");
                try {
                    callEventListener.callBackProcess(IMessageWhat.END, msg.obj);
                } catch (HDException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage(), e);
                    try {
                        throw new HDException("异步加载数据失败！");
                    } catch (HDException e1) {
                        // TODO Auto-generated catch block
                        logger.error(e1.getMessage(), e1);
                    }
                }
                break;
            case IMessageWhat.ERROR:
                logger.error("异步查询信息执行失败。。。");
                break;
        }
        super.handleMessage(msg);
    }

    /**
     * ClassName: AsyncQueryThread (子线程)<br/>
     * date: 2014年10月20日 <br/>
     *
     * @author zhaofeng
     * @version AsyncQueryHandler
     */
    class AsyncQueryThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            final Message startMsg = obtainMessage();
            startMsg.what = IMessageWhat.START;
            sendMessage(startMsg);
            final Object object = getObject();
            final Message message = obtainMessage();
            message.what = IMessageWhat.END;// 表示成功
            message.obj = object;
            sendMessage(message);
        }
    }

}
