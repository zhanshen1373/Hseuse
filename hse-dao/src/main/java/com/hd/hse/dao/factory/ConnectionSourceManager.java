/**
 * Project Name:hse-dao
 * File Name:ConnectionSourceManager.java
 * Package Name:com.hd.hse.dao.factory
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.factory;

import java.sql.SQLException;

import com.hd.hse.dao.config.DBConfig;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dao.source.JdbcConnectionSource;
import com.hd.hse.dao.source.JdbcPoolConnectionSource;

/**
 * ClassName:ConnectionSourceManager (数据源工厂).<br/>
 * Date: 2014年8月12日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class ConnectionSourceManager {
	/**
	 * jdbcPoolConnectionSource:TODO().
	 */
	private IConnectionSource jdbcPoolConnectionSource;

	private Object lock = new Object();

	private static ConnectionSourceManager sourceManager = new ConnectionSourceManager();

	private ConnectionSourceManager() {

	}

	public static ConnectionSourceManager getInstance() {
		return sourceManager;
	}

	/**
	 * getJdbcPoolConSource:(创建jdbc数据源连接池). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws SQLException
	 */
	public IConnectionSource getJdbcPoolConSource() throws SQLException {
		if (jdbcPoolConnectionSource == null) {
			synchronized (lock) {
				if (jdbcPoolConnectionSource == null) {
					DBConfig config = DBConfig.getConfig();
					jdbcPoolConnectionSource = new JdbcPoolConnectionSource(
							config.getUserName(), config.getPassword(),
							config.getUrl(), config.getType().getDriver());
				}
			}
		}
		return jdbcPoolConnectionSource;
	}

	/**
	 * getJdbcConSource:(创建jdbc数据源). <br/>
	 * date: 2014年8月12日 <br/>
	 * 
	 * @author lg
	 * @return
	 * @throws SQLException
	 */
	public IConnectionSource getJdbcConSource() throws SQLException {
		DBConfig config = DBConfig.getConfig();
		return new JdbcConnectionSource(config.getUserName(),
				config.getPassword(), config.getUrl(), config.getType()
						.getDriver());
	}
}
