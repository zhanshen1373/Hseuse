/**
 * Project Name:hse-dao
 * File Name:StatementExecutorManager.java
 * Package Name:com.hd.hse.dao.factory
 * Date:2014年8月13日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.factory;

import com.hd.hse.dao.config.DBConfig;
import com.hd.hse.dao.config.DBType;
import com.hd.hse.dao.stmt.IStatementExecutor;
import com.hd.hse.dao.stmt.SqliteStatementExecutor;

/**
 * ClassName:StatementExecutorManager (sql执行器工厂).<br/>
 * Date: 2014年8月13日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class StatementExecutorManager {

	/**
	 * getExecutor:(). <br/>
	 * date: 2014年8月13日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public static IStatementExecutor getExecutor() {
		if (DBType.Sqlite.equals(DBConfig.getConfig().getType())) {
			return new SqliteStatementExecutor();
		}
		return null;
	}
}
