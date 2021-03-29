/**
 * Project Name:hse-dao
 * File Name:DBConnectionSource.java
 * Package Name:com.hd.hse.dao.source
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.connection.JdbcConnection;

/**
 * ClassName:DBConnectionSource (jdbc数据源).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class JdbcConnectionSource implements IConnectionSource {

	private static Logger logger = LogUtils
			.getLogger(JdbcConnectionSource.class);

	/**
	 * url:TODO().
	 */
	private String url;
	/**
	 * userName:TODO().
	 */
	private String userName;
	/**
	 * password:TODO().
	 */
	private String password;
	/**
	 * driver:TODO(jdbc驱动).
	 */
	private String driver;
	/**
	 * extDriver:TODO().
	 */
	private String extDriver;
	/**
	 * connection:TODO().
	 */
	private IConnection connection;

	public JdbcConnectionSource(String userName, String pwd, String url,
			String driver) throws SQLException {
		this.userName = userName;
		this.password = pwd;
		this.url = url;
		this.driver = driver;
		init();
	}

	public JdbcConnectionSource(String userName, String pwd, String url,
			String driver, String extDriver) throws SQLException {
		this.userName = userName;
		this.password = pwd;
		this.url = url;
		this.driver = driver;
		this.extDriver = extDriver;
		init();
	}

	/**
	 * init:(初始化). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	private void init() throws SQLException {
		try {
			if (!StringUtils.isEmpty(extDriver)) {
				Class.forName(extDriver);
			}
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new SQLException("不能加载jdbc驱动");
		}
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.dao.source.IConnectionSource#getConnection()
	 */
	public IConnection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		if (connection == null) {
			connection = createConnection(logger);
			connection.setAutoCommit(false);
		}
		return connection;
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.dao.source.IConnectionSource#releaseConnection(IConnection)
	 */
	public void releaseConnection(IConnection connection) throws SQLException {
		// TODO Auto-generated method stub
		this.close();
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.dao.source.IConnectionSource#close()
	 */
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		if (connection != null) {
			connection.commit();
			connection.close();
		}
	}

	public void close(IConnection connection) throws SQLException {
		// TODO Auto-generated method stub
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * createConnection:(创建数据库连接). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @param logger
	 * @return
	 * @throws SQLException
	 */
	protected IConnection createConnection(Logger logger) throws SQLException {
		Properties info = new Properties();
		if (!StringUtils.isEmpty(userName)) {
			info.setProperty("user", userName);
		}
		if (!StringUtils.isEmpty(password)) {
			info.setProperty("password", password);
		}
		Connection connection = DriverManager.getConnection(url, info);
		if (connection == null) {
			throw new SQLException("无法读取数据库：" + url);
		}
		JdbcConnection jdbcConnection = new JdbcConnection(connection);
		// jdbcConnection.setAutoCommit(false);
		logger.debug("创建jdbc数据库连接");
		return jdbcConnection;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getExtDriver() {
		return extDriver;
	}

	public void setExtDriver(String extDriver) {
		this.extDriver = extDriver;
	}

	@Override
	public IConnection getNonTransConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
