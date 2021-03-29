/**
 * Project Name:hse-dao
 * File Name:IConnectionSource.java
 * Package Name:com.hd.hse.dao.source
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.source;

import java.sql.SQLException;

import com.hd.hse.dao.connection.IConnection;

/**
 * ClassName:IConnectionSource (数据库连接池).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IConnectionSource {

	/**
	 * getConnection:(取数据库连接). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws SQLException
	 */
	public IConnection getConnection() throws SQLException;

	/**
	 * getNonTransConnection:(去数据库连接，非独立事务). <br/>
	 * date: 2014年10月29日 <br/>
	 *
	 * @author lg
	 * @return
	 * @throws SQLException
	 */
	public IConnection getNonTransConnection() throws SQLException;

	/**
	 * releaseConnection:(释放指定数据库连接到连接池). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @throws SQLException
	 */
	public void releaseConnection(IConnection connection) throws SQLException;

	/**
	 * close:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @throws SQLException
	 */
	public void close() throws SQLException;

	/**
	 * close:(). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @throws SQLException
	 */
	public void close(IConnection connection) throws SQLException;
}
