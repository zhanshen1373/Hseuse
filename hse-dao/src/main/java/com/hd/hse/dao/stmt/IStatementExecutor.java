/**
 * Project Name:hse-dao
 * File Name:IStatementExecutor.java
 * Package Name:com.hd.hse.dao.stmt
 * Date:2014年8月13日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.stmt;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.IProcessResultSet;

import java.sql.SQLException;
import java.util.List;

/**
 * ClassName:IStatementExecutor ().<br/>
 * Date: 2014年8月13日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IStatementExecutor {
	/**
	 * insert:(插入). <br/>
	 * date: 2014年8月13日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param entities
	 * @return
	 * @throws SQLException
	 */
	public int[] insert(IConnection connection, SuperEntity[] entities)
			throws SQLException;

	/**
	 * executeQuery:(查询). <br/>
	 * date: 2014年8月14日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param sql
	 * @param type
	 * @return
	 * @throws DaoException
	 */
	public Object executeQuery(IConnection connection, String sql,
                               IProcessResultSet type) throws DaoException;

	/**
	 * executeUpdate:(更新). <br/>
	 * date: 2014年8月14日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(IConnection connection, String sql)
			throws SQLException;

	/**
	 * executeUpdate:(批量更新). <br/>
	 * date: 2014年8月14日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param sqls
	 * @return
	 * @throws SQLException
	 */
	public int[] executeUpdate(IConnection connection, String[] sqls)
			throws SQLException;

	/**
	 * delete:(批量删除). <br/>
	 * date: 2014年8月18日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param entities
	 * @throws SQLException
	 */
	public void delete(IConnection connection, SuperEntity[] entities)
			throws SQLException;

	/**
	 * update:(批量更新). <br/>
	 * date: 2014年8月18日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param entities
	 * @throws SQLException
	 */
	public void update(IConnection connection, SuperEntity[] entities)
			throws SQLException;

	/**
	 * update:(批量更新指定字段). <br/>
	 * date: 2014年8月19日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param entities
	 * @param cols
	 * @throws SQLException
	 */
	public void update(IConnection connection, SuperEntity[] entities,
                       String[] cols) throws SQLException;

	/**
	 * executeQuery:(). <br/>
	 * date: 2014年8月19日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param entityClass
	 * @param cols
	 * @param where
	 * @param loadChild
	 * @return
	 * @throws DaoException
	 */
	public List<SuperEntity> executeQuery(IConnection connection,
                                          Class<?> entityClass, String[] cols, String where, boolean loadChild)
			throws DaoException;
}
