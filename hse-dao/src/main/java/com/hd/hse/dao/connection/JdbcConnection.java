/**
 * Project Name:hse-dao
 * File Name:JdbcConnection.java
 * Package Name:com.hd.hse.dao.connection
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.connection;

import com.hd.hse.common.logger.LogUtils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName:JdbcConnection (JDBC数据库连接).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class JdbcConnection implements IConnection {
	private static Logger logger = LogUtils.getLogger(JdbcConnection.class);

	/**
	 * connection:TODO(连接).
	 */
	private Connection connection;

	public JdbcConnection(Connection connection) {
		this.connection = connection;
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// TODO Auto-generated method stub
		connection.setAutoCommit(autoCommit);
		logger.debug("设置事务属性");
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub
		connection.close();
		logger.debug("关闭连接");
	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		connection.commit();
		logger.debug("事务提交");
	}

	public void rollback() throws SQLException {
		// TODO Auto-generated method stub
		connection.rollback();
		logger.debug("事务回滚");
	}

	public PreparedStatement prepareStatement(String sql)
			throws SQLException {
		// TODO Auto-generated method stub
		logger.debug(sql);
		return connection.prepareStatement(sql);
	}

	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("创建游标");
		return connection.createStatement();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return connection.getAutoCommit();
	}
	
}
