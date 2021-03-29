package com.hd.hse.dc.business.common.weblistener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

import android.os.Message;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.common.factory.BusinessFactory;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.webservice.AbstractWebServiceClient;
import com.hd.hse.dc.business.common.webservice.WebServiceClientDel;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: AbsWebListener ()<br/>
 * date: 2015年3月19日 <br/>
 *
 * @author Administrator
 */
public abstract class AbsWebListener extends AbstractActionListener {

    public AbstractWebServiceClient sClient;
    private BusinessAsyncTask aysncTask;

    @Override
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        if (null == getLogger()) {
            throw new HDException("必须实现getLogger重载,并不能设置为null;");
        }
        sClient = getServiceClient();
        if (null == sClient) {
            throw new HDException("实例化web交互方式失败,请检查配置参数;");
        }
        return null;
    }

    /**
     * 获取服务器的连接对象
     *
     * @return
     * @throws HDException
     */
    public AbstractWebServiceClient getServiceClient() throws HDException {
        WebConfig config = getWebConfigParam();
        if (null == config || StringUtils.isEmpty(config.getUrl()))
            throw new HDException("请配置web交互方式!，并不能设置为null;");
        return BusinessFactory.newIntance().getServerClient(config);
    }

    // 数据适配器的对象
    public AbstractDataAdapter getAnalyzeDataAdapter() throws HDException {
        WebConfig config = getWebConfigParam();
        if (null == config || StringUtils.isEmpty(config.getDataAnalyzetype())) {
            throw new HDException("请配置解析和格式化的对象!");
        }
        return BusinessFactory.newIntance().getAnalyzeDataAdapter(config);
    }

    /**
     * isDataAnalyze:(是否解析). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @return 默认得到数据需要解析
     * @author lxf
     */
    public boolean isDataAnalyze() {
        return true;
    }

//	/**
//	 * ResDowninfo:(解析成SQL语句). <br/>
//	 * date: 2014年8月21日 <br/>
//	 * 
//	 * @author lxf
//	 * @throws HDException
//	 */
//	public HashMap<String, List<String>> resDowninfo(
//			HashMap<String, TableDesc> haspmap, String str) throws HDException {
//		AbstractDataAdapter opAbstratct = getAnalyzeDataAdapter();
//		if (opAbstratct == null)
//			return null;
//		return opAbstratct.resSql(str, haspmap);
//	}

//	/**
//	 * ResDowninfo:(解析成SQL语句). <br/>
//	 * date: 2014年8月21日 <br/>
//	 * 
//	 * @author lxf
//	 * @throws HDException
//	 * @throws JSONException
//	 */
//	public HashMap<String, List<String>> resDowninfo(String str)
//			throws HDException, JSONException {
//		return resDowninfo(str, null);
//	}
//
//	/**
//	 * ResDowninfo:(解析成SQL语句). <br/>
//	 * date: 2014年8月21日 <br/>
//	 * 
//	 * @author lxf
//	 * @throws HDException
//	 * @throws JSONException
//	 */
//	public HashMap<String, List<String>> resDowninfo(String str, String key)
//			throws HDException, JSONException {
//		AbstractDataAdapter opAbstratct = getAnalyzeDataAdapter();
//		return opAbstratct.resSql(str, key);
//	}

    /**
     * setWritelog:(记录日志). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @param msg
     * @param e
     * @author lxf
     */
    public void setWritelog(String msg, Exception e) {
        Logger logger = getLogger();
        if (null != logger) {
            logger.error(msg, e);
        }
    }

    /**
     * setWritelog:(记录日志). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @param msg
     * @author lxf
     */
    public void setWritelog(String msg) {
        setWritelog(msg, null);
    }

    /**
     * setWriteDublog:(记录dug日志). <br/>
     * date: 2015年01月22日 <br/>
     *
     * @param msg
     * @author lxf
     */
    public void setWriteDubuglog(String msg) {
        Logger logger = getLogger();
        if (null != logger) {
            logger.debug(msg, null);
        }
    }

    // 数据库操作写入
    // 数据库查询
    // 异步发送消息

    /**
     * getWebConfigParam:(设置web初始化参数). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @return
     * @author lxf
     */
    public WebConfig getWebConfigParam() {
        return SystemProperty.getSystemProperty().getWebDataConfig();
    }

    /**
     * execute:(执行sql语句). <br/>
     * date: 2014年8月21日 <br/>
     *
     * @param list <String> sql
     * @throws HDException
     * @throws SQLException
     * @author lxf
     */
    public void execute(List<String> listsql) throws HDException {

        IConnectionSource conSrc = null;
        IConnection con = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();

            dao.executeUpdate(con, listsql.toArray(new String[listsql.size()]));

            con.commit();
        } catch (SQLException e) {
            setWritelog("传入参数类型不正确", e);
            throw new HDException("传入参数类型不正确" + e.getMessage());

        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     * execute:(执行sql语句). <br/>
     * date: 2014年8月21日 <br/>
     *
     * @param string sql
     * @throws HDException
     * @author lxf
     */
    public void execute(String sql) throws HDException {
        List<String> listsql = new ArrayList<String>();
        listsql.add(sql);
        execute(listsql);
    }

    /**
     * @throws HDException
     * @pdOid
     */
    public Object query(String sql, IProcessResultSet type) throws HDException {

        Object retobject = null;
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            retobject = dao.executeQuery(con, sql, type);
        } catch (SQLException e) {
            throw new HDException("查询报错" + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
        return retobject;
    }

    /**
     * sendMessage:(发送消息). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @param status
     * @param oldpercentage
     * @param percentage
     * @param message
     * @author lxf
     */
    public void sendMessage(int status, int oldpercentage, int percentage,
                            String message) {
        sendMessage(status, oldpercentage, percentage, message, null);
    }

    /**
     * sendMessage:(发送消息). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @param status
     * @param oldpercentage
     * @param percentage
     * @param message
     * @param returnResult
     * @author lxf
     */
    public void sendMessage(int status, int oldpercentage, int percentage,
                            String message, Object returnResult) {

        if (aysncTask == null) {
            aysncTask = getAsyncTask();
        }
        Message msg;
        if (null != aysncTask) {
            // 定义异步任务返回消息对象
            AysncTaskMessage retMsg = new AysncTaskMessage();
            retMsg.setMessage(message);
            if (null != returnResult) {
                retMsg.setReturnResult(returnResult);
            }
            retMsg.setCurrent(percentage - oldpercentage);
            if (status != IMessageWhat.ERROR) {
                if (status == IMessageWhat.END) {
                    retMsg.setCurrent(100);
                    msg = getAsyncTask().obtainMessage();
                    msg.what = status;
                    msg.getData().putSerializable("p", retMsg);
                    aysncTask.sendMessage(msg);
                } else {
                    // 进行中、循环发送百分比
                    for (int i = oldpercentage; i <= percentage; i++) {
                        msg = getAsyncTask().obtainMessage();
                        msg.what = status;
                        msg.getData().putSerializable("p", retMsg);
                        aysncTask.sendMessage(msg);
                        retMsg.setCurrent(i);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {

                        }
                    }
                }
            } else {
                // 报错时执行的
                for (int j = 0; j < 1; j++) {
                    msg = getAsyncTask().obtainMessage();
                    msg.what = status;
                    msg.getData().putSerializable("p", retMsg);
                    aysncTask.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    retMsg.setRelease(true);
                }
            }

        }
    }

    /**
     * initParam:(设置初始化参数). <br/>
     * date: 2014年8月25日 <br/>
     *
     * @return Object
     * @author lxf
     */
    public abstract Object initParam() throws HDException;

    /**
     * 获取日志对象
     */
    public abstract Logger getLogger();

    /**
     * getMethodType:(获取动作标识). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract String getMethodType();
}
