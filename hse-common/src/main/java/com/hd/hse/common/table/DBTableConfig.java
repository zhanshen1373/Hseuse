/**
 * Project Name:hse-common
 * File Name:DBTableConfig.java
 * Package Name:com.hd.hse.common.table
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.field.DBField;

/**
 * ClassName:DBTableConfig (数据库表配置相关).<br/>
 * Date: 2014年8月11日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class DBTableConfig<T> {

	/**
	 * clazz:TODO(实体类型).
	 */
	private Class<T> clazz;

	/**
	 * tableInfo:TODO(表信息).
	 */
	private DBTableInfo<T> tableInfo;

	public DBTableConfig(Class<T> clazz, DBTableInfo<T> tableInfo) {
		this.clazz = clazz;
		this.tableInfo = tableInfo;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}

	public DBTableInfo<T> getTableInfo() {
		return tableInfo;
	}

	/**
	 * fromClass:(创建表配置信息). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param clazz
	 * @return
	 */
	public static <T> DBTableConfig<T> fromClass(Class<T> clazz) {
		return new DBTableConfig<T>(clazz, extractTableInfo(clazz));
	}

	/**
	 * extractTableInfo:(解析实体类注解). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param clazz
	 * @return
	 */
	private static <T> DBTableInfo<T> extractTableInfo(Class<T> clazz) {
		DBTableInfo<T> tableInfo = new DBTableInfo<T>(clazz);
		// 表
		DBTable dbTable = clazz.getAnnotation(DBTable.class);
		if (dbTable != null) {
			tableInfo.setTableName(dbTable.tableName());// 表名
		}
		// 字段
		Field[] fields = clazz.getDeclaredFields();
		List<String> lstCol = new ArrayList<String>();
		for (Field field : fields) {
			DBField dbField = field.getAnnotation(DBField.class);
			if (dbField == null) {
				continue;
			}
			String colName = field.getName();// 字段编码
			if (dbField.id()) {
				tableInfo.setPrimaryKey(colName);// 主键
			}
			if (dbField.foreign()) {
				tableInfo.setForeignKey(colName);// 外键
			}
			lstCol.add(colName);// 列名
		}
		tableInfo.setColumns(lstCol.toArray(new String[lstCol.size()]));
		return tableInfo;
	}

}
