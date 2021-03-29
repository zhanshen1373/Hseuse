/**
 * Project Name:hse-common
 * File Name:DBFieldInfo.java
 * Package Name:com.hd.hse.common.field
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.field;
/**
 * ClassName:DBFieldInfo (数据库字段).<br/>
 * Date:     2014年8月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class DBFieldInfo<T> {
	/**
	 * clazz:TODO(实体类型).
	 */
	private Class<T> clazz;
	
	/**
	 * colName:TODO(数据库列名).
	 */
	private String colName;
	
	/**
	 * fieldName:TODO(实体属性名).
	 */
	private String fieldName;
	
	/**
	 * bPrimaryKey:TODO(是否主键).
	 */
	private boolean bPrimaryKey;
	
	/**
	 * bForeignKey:TODO(是否外键).
	 */
	private boolean bForeignKey;

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isbPrimaryKey() {
		return bPrimaryKey;
	}

	public void setbPrimaryKey(boolean bPrimaryKey) {
		this.bPrimaryKey = bPrimaryKey;
	}

	public boolean isbForeignKey() {
		return bForeignKey;
	}

	public void setbForeignKey(boolean bForeignKey) {
		this.bForeignKey = bForeignKey;
	}
	
}

