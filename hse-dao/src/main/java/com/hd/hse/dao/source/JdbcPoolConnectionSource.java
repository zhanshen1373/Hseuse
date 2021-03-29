/**
 * Project Name:hse-dao
 * File Name:JdbcPoolConnectionSource.java
 * Package Name:com.hd.hse.dao.source
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.source;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.connection.IConnection;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Vector;

/**
 * ClassName:JdbcPoolConnectionSource (jdbc数据源连接池).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class JdbcPoolConnectionSource extends JdbcConnectionSource implements
		IConnectionSource {

	private static Logger logger = LogUtils
			.getLogger(JdbcPoolConnectionSource.class);

	private Vector<IConnection> vctTransConnection = new Vector<IConnection>();

	private Vector<IConnection> vctNonTransConnection = new Vector<IConnection>();

	public JdbcPoolConnectionSource(String userName, String pwd, String url,
			String driver) throws SQLException {
		super(userName, pwd, url, driver);
	}

	public JdbcPoolConnectionSource(String userName, String pwd, String url,
			String driver, String extDriver) throws SQLException {
		super(userName, pwd, url, driver, extDriver);
	}

	@Override
	public IConnection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		IConnection jdbcConnection = null;
		synchronized (vctTransConnection) {
			if (vctNonTransConnection.size() > 0) {
				throw new SQLException("系统繁忙，请稍后再试！");
			}
			// 从连接池中取空闲连接
			if (vctTransConnection.size() > 0) {
				jdbcConnection = vctTransConnection.get(0);
				if (!jdbcConnection.getAutoCommit()) {
					throw new SQLException("系统繁忙，请稍后再试！");
				}
			}
			// 创建新连接
			else {
				jdbcConnection = createConnection(logger);
				vctTransConnection.add(jdbcConnection);
			}
			jdbcConnection.setAutoCommit(false);
			return jdbcConnection;
		}
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.dao.source.JdbcConnectionSource#getNonTransConnection()
	 */
	@Override
	public IConnection getNonTransConnection() throws SQLException {
		// TODO Auto-generated method stub
		IConnection jdbcConnection = null;
		synchronized (vctTransConnection) {
			if (vctTransConnection.size() > 0) {
				jdbcConnection = vctTransConnection.get(0);
				if (!jdbcConnection.getAutoCommit()) {
					throw new SQLException("系统繁忙，请稍后再试！");
				}
			}
			jdbcConnection = createConnection(logger);
			vctNonTransConnection.add(jdbcConnection);
			return jdbcConnection;
		}
	}

	@Override
	public void releaseConnection(IConnection connection) throws SQLException {
		// TODO Auto-generated method stub
		if (connection != null) {
			try {
				synchronized (vctTransConnection) {
					if (vctTransConnection.contains(connection)) {
						if (!connection.getAutoCommit()) {
							connection.rollback();
							connection.setAutoCommit(true);
						}
					} else {
						vctNonTransConnection.remove(connection);
						connection.close();
					}
				}
			} catch (Exception e) {
				throw new SQLException("并发异常", e);
			}
		}
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

}
