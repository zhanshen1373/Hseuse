package com.hd.hse.business.listener;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;

import android.os.Message;
import android.widget.Toast;

import com.hd.hse.business.factory.BusinessFactory;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.util.AbstractDataAdapter;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.business.webservice.WebServiceClient;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.source.IConnectionSource;

/**
 * ClassName:AbstractWebListener (数据交互监听类).<br/>
 * Date: 2014年8月25日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */

public abstract class AbstractWebListener extends AbstractActionListener {

	public WebServiceClient client = null;
	private BusinessAsyncTask aysncTask;

	public Object action(String action, Object... args) throws HDException {

		if (null == getLogger()) {
			throw new HDException("必须实现getLogger重载，并不能设置为null;");
		}
		client = this.getServiceClient();
		if (null == client) {
			throw new HDException("实例化web对象为null,请检查配置参数;");
		}
/*		if(!client.isConnect())
		{
			throw new HDException("请求超时,请设置网络连接!");
		}*/
		return null;
	}

	/**
	 * 表示服务器的连接对象
	 * 
	 * @return
	 * @throws HDException
	 */
	public WebServiceClient getServiceClient() throws HDException {
		WebConfig config = getWebConfigParam();
		if (null == config || StringUtils.isEmpty(config.getUrl()))
			throw new HDException("必须实现getWebConfigParam()重载，并不能设置为null;");
		return BusinessFactory.newIntance().getServerDataClient(config);
	}

	// 数据适配器的对象
	public AbstractDataAdapter getAnalyzeDataAdapter() throws HDException {
		WebConfig config = getWebConfigParam();
		if (null == config || StringUtils.isEmpty(config.getDataAnalyzetype()))
			return null;
		return BusinessFactory.newIntance().getAnalyzeDataAdapter(config);
	}

	/**
	 * getWebConfigParam:(设置web初始化参数). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract WebConfig getWebConfigParam();

	/**
	 * initParam:(设置初始化参数). <br/>
	 * date: 2014年8月25日 <br/>
	 * 
	 * @author lxf
	 * @return Object
	 */
	public abstract Object initParam();

	/**
	 * 获取日志对象
	 * */
	public abstract Logger getLogger();

	/**
	 * setWritelog:(记录日志). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author lxf
	 * @param msg
	 * @param e
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
	 * @author lxf
	 * @param msg
	 */
	public void setWritelog(String msg) {
		setWritelog(msg, null);
	}
	/**
	 * setWriteDublog:(记录dug日志). <br/>
	 * date: 2015年01月22日 <br/>
	 * 
	 * @author lxf
	 * @param msg
	 */
	public void setWriteDublog(String msg){
		Logger logger = getLogger();
		if (null != logger) {
			logger.debug(msg, null);
		}
	}
	/**
	 * ResDowninfo:(解析成SQL语句). <br/>
	 * date: 2014年8月21日 <br/>
	 * 
	 * @author lxf
	 * @throws HDException
	 */
	public HashMap<String, List<String>> resDowninfo(
			HashMap<String, TableDesc> haspmap, String str) throws HDException {
		AbstractDataAdapter OP = getAnalyzeDataAdapter();
		if (OP == null)
			return null;
		return OP.resSql(str, haspmap);
	}

	/**
	 * ResDowninfo:(解析成SQL语句). <br/>
	 * date: 2014年8月21日 <br/>
	 * 
	 * @author lxf
	 * @throws HDException
	 * @throws JSONException
	 */
	public HashMap<String, List<String>> resDowninfo(String str)
			throws HDException, JSONException {
		return resDowninfo(str, null);
	}

	/**
	 * ResDowninfo:(解析成SQL语句). <br/>
	 * date: 2014年8月21日 <br/>
	 * 
	 * @author lxf
	 * @throws HDException
	 * @throws JSONException
	 */
	public HashMap<String, List<String>> resDowninfo(String str, String key)
			throws HDException, JSONException {
		AbstractDataAdapter OP = getAnalyzeDataAdapter();
		return OP.resSql(str, key);
	}

	/**
	 * execute:(执行sql语句). <br/>
	 * date: 2014年8月21日 <br/>
	 * 
	 * @author lxf
	 * @param list
	 *            <String> sql
	 * @throws HDException
	 * @throws SQLException
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
	 * @author lxf
	 * @param string
	 *            sql
	 * @throws HDException
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
	public Object Query(String sql, IProcessResultSet type) throws HDException {

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

	public void SendMessage(int status, int oldpercentage, int percentage,
			String message) {
		SendMessage(status, oldpercentage, percentage, message, null);
	}

	public void SendMessage(int status, int oldpercentage, int percentage,
			String message, Object returnResult) {

		if (aysncTask == null) {
			aysncTask = getAsyncTask();
		}
		Message msg;
		if (null != aysncTask) {
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
				// Toast.makeText(this, "Service中子线程启动！",
				// Toast.LENGTH_LONG).show();
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
}
