/**
 * Project Name:hse-dao
 * File Name:IConnection.java
 * Package Name:com.hd.hse.dao.connection
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ClassName:IConnection (数据库连接).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IConnection {
	/**
	 * setAutoCommit:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @param autoCommit
	 * @throws SQLException
	 */
	public void setAutoCommit(boolean autoCommit) throws SQLException;

	/**
	 * getAutoCommit:(). <br/>
	 * date: 2014年10月29日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	public boolean getAutoCommit() throws SQLException;

	/**
	 * close:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	public void close() throws SQLException;

	/**
	 * commit:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	public void commit() throws SQLException;

	/**
	 * rollback:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	public void rollback() throws SQLException;

	/**
	 * prepareStatement:(). <br/>
	 * date: 2014年8月13日 <br/>
	 * 
	 * @author lg
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException;

	/**
	 * createStatement:(). <br/>
	 * date: 2014年8月14日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException;
}
