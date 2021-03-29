/**
 * Project Name:hse-common
 * File Name:DBTableInfo.java
 * Package Name:com.hd.hse.common.table
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.table;

import java.util.Map;

import com.hd.hse.common.field.DBFieldInfo;

/**
 * ClassName:DBTableInfo (数据库表信息).<br/>
 * Date: 2014年8月11日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class DBTableInfo<T> {
	/**
	 * clazz:TODO(实体类型).
	 */
	private Class<T> clazz;

	/**
	 * tableName:TODO(数据库表名).
	 */
	private String tableName;

	/**
	 * primaryKey:TODO(主键).
	 */
	private String primaryKey;

	/**
	 * foreignKey:TODO(外键).
	 */
	private String foreignKey;

	/**
	 * fields:TODO(数据库字段).
	 */
	private String[] columns;

	/**
	 * fields:TODO(数据库字段对应的属性).
	 */
	private Map<String, DBFieldInfo<T>> fields;

	public DBTableInfo(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public Map<String, DBFieldInfo<T>> getFields() {
		return fields;
	}

	public void setFields(Map<String, DBFieldInfo<T>> fields) {
		this.fields = fields;
	}
}
