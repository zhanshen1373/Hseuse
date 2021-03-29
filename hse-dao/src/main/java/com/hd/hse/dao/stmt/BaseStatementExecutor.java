/**
 * Project Name:hse-dao
 * File Name:BaseStatementExecutor.java
 * Package Name:com.hd.hse.dao.stmt
 * Date:2014年8月13日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.stmt;

import org.apache.commons.lang.StringUtils;

/**
 * ClassName:BaseStatementExecutor ().<br/>
 * Date: 2014年8月13日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public abstract class BaseStatementExecutor implements IStatementExecutor {

	/**
	 * buildInsertSql:(). <br/>
	 * date: 2014年8月13日 <br/>
	 * 
	 * @author lg
	 * @param tableName
	 * @param cols
	 * @return
	 */
	public String buildInsertSql(String tableName, String[] cols) {
		StringBuilder sbInsert = new StringBuilder();
		StringBuilder sbValue = new StringBuilder();
		sbInsert.append("insert into ").append(tableName).append("(");
		sbValue.append(" values(");
		for (String col : cols) {
			sbInsert.append(col).append(",");
			sbValue.append("?,");
		}
		sbInsert.setLength(sbInsert.length() - 1);
		sbValue.setLength(sbValue.length() - 1);
		sbInsert.append(")");
		sbValue.append(")");
		sbInsert.append(sbValue);
		return sbInsert.toString();
	}

	/**
	 * buildDeleteSql:(). <br/>
	 * date: 2014年8月18日 <br/>
	 * 
	 * @author lg
	 * @param tableName
	 * @param primaryKey
	 * @param value
	 * @return
	 */
	public String buildDeleteSql(String tableName, String primaryKey,
			Object value) {
		StringBuilder sbDel = new StringBuilder();
		sbDel.append("delete from ").append(tableName);
		sbDel.append(" where ").append(primaryKey).append("=");
		if (value instanceof String) {
			sbDel.append("'").append(value).append("'");
		} else {
			sbDel.append(value);
		}
		return sbDel.toString();
	}

	/**
	 * buildUpdateSql:(). <br/>
	 * date: 2014年8月18日 <br/>
	 * 
	 * @author lg
	 * @param tableName
	 * @param cols
	 * @param primaryKey
	 * @return
	 */
	public String buildUpdateSql(String tableName, String[] cols,
			String primaryKey) {
		StringBuilder sbUpdate = new StringBuilder();
		sbUpdate.append("update ").append(tableName).append(" set ");
		for (String col : cols) {
			if (!col.equals(primaryKey)) {
				sbUpdate.append(col).append("=");
				sbUpdate.append("?,");
			}
		}
		sbUpdate.setLength(sbUpdate.length() - 1);
		sbUpdate.append(" where ").append(primaryKey).append("=?");
		return sbUpdate.toString();
	}

	/**
	 * buildQuerySql:(). <br/>
	 * date: 2014年8月19日 <br/>
	 *
	 * @author lg
	 * @param tableName
	 * @param cols
	 * @param where
	 * @return
	 */
	public String buildQuerySql(String tableName, String[] cols, String where) {
		StringBuilder sbQuery = new StringBuilder();
		sbQuery.append("select ");
		for (String col : cols) {
			sbQuery.append(col).append(",");
		}
		sbQuery.setLength(sbQuery.length() - 1);
		sbQuery.append(" from ").append(tableName);
		if (!StringUtils.isEmpty(where))
			sbQuery.append(" where ").append(where);
		return sbQuery.toString();
	}
}
