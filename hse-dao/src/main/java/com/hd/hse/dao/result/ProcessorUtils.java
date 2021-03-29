/**
 * Project Name:hse-dao
 * File Name:ProcessorUtils.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hd.hse.common.entity.EntityHelper;

/**
 * ClassName:ProcessorUtils ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class ProcessorUtils {
	private static class MethodAndTypes {
		public Method[] invokes = null;

		public int[] types = null;
	}

	/**
	 * toEntity:(将结果集转换成单个实体). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param rs结果集
	 * @param clazz实体类型
	 * @return
	 * @throws SQLException
	 */
	public static Object toEntity(ResultSet rs, Class<?> clazz)
			throws SQLException {
		Object entity = null;
		MethodAndTypes methodAndTypes = getEntityInvokeAndTypes(clazz, rs, null);
		if (rs.next()) {
			entity = newEntity(rs, clazz, methodAndTypes);
		}
		return entity;
	}

	/**
	 * toEntityList:(将结果集转换成实体集). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static Object toEntityList(ResultSet rs, Class<?> clazz)
			throws SQLException {
		MethodAndTypes methodAndTypes = getEntityInvokeAndTypes(clazz, rs, null);
		ArrayList<Object> lstEntity = new ArrayList<Object>();
		while (rs.next()) {
			lstEntity.add(newEntity(rs, clazz, methodAndTypes));
		}
		return lstEntity;
	}

	/**
	 * toListListEntity:(结果集转化成list<List>). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author zhaofeng
	 * @param rs
	 * @param clazz
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	public static Object toListListEntity(ResultSet rs, Class<?> clazz,
			String column) throws SQLException {
		List<List<Object>> listListEntity = new ArrayList<List<Object>>();
		MethodAndTypes methodAndTypes = getEntityInvokeAndTypes(clazz, rs, null);
		List<String> list = getColumnList(rs, column);
		rs.beforeFirst();
		List<Object> entityList = null;
		for (int i = 0; i < list.size(); i++) {
			while (rs.next()) {
				if (rs.getString(column).equals(list.get(i))) {
					if (entityList == null)
						entityList = new ArrayList<Object>();
					entityList.add(newEntity(rs, clazz, methodAndTypes));
				}
			}
			listListEntity.add(entityList);
			entityList = null;
			rs.beforeFirst();
		}
		return listListEntity;
	}

	/**
	 * getColumnList:(获取不重复的一列值). <br/>
	 * date: 2014年10月18日 <br/>
	 *
	 * @author zhaofeng
	 * @param rs
	 * @param column
	 * @return
	 * @throws SQLException
	 */
	private static List<String> getColumnList(ResultSet rs, String column)
			throws SQLException {
		List<String> columnList = new ArrayList<String>();
		while (rs.next()) {
			String value = rs.getString(column);
			if (!columnList.contains(value))
				columnList.add(value);
		}
		return columnList;
	}

	/**
	 * toMap:(结果集转换成map). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Object toMap(ResultSet rs) throws SQLException {
		HashMap<String, Object> mapRS = new HashMap<String, Object>();
		ResultSetMetaData metaData = rs.getMetaData();
		int iColCount = metaData.getColumnCount();
		if (rs.next()) {
			for (int i = 1; i <= iColCount; i++) {
				Object value = getColumnValue(metaData.getColumnType(i), rs, i);
				mapRS.put(metaData.getColumnName(i).toLowerCase(), value);
			}
		}
		return mapRS;
	}

	/**
	 * toMapList:(结果集转换成hashmap集). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Object toMapList(ResultSet rs) throws SQLException {
		ArrayList<HashMap<String, Object>> mapListRS = new ArrayList<HashMap<String, Object>>();
		ResultSetMetaData metaData = rs.getMetaData();
		int iColCount = metaData.getColumnCount();
		while (rs.next()) {
			HashMap<String, Object> mapRS = new HashMap<String, Object>();
			for (int i = 1; i <= iColCount; i++) {
				Object value = getColumnValue(metaData.getColumnType(i), rs, i);
				mapRS.put(metaData.getColumnName(i).toLowerCase(), value);
			}
			mapListRS.add(mapRS);
		}
		return mapListRS;
	}

	/**
	 * setEntityValue:(设置属性). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @param clazz
	 * @param methodAndTypes
	 * @return
	 * @throws SQLException
	 */
	private static Object newEntity(ResultSet rs, Class<?> clazz,
			MethodAndTypes methodAndTypes) throws SQLException {
		Object entity = newInstance(clazz);
		for (int i = 1; i <= methodAndTypes.types.length; i++) {
			Object value = getColumnValue(methodAndTypes.types[i - 1], rs, i);
			if (value == null)
				continue;
			Method invoke = methodAndTypes.invokes[i - 1];
			if (invoke == null) {
				// 如果是null则不赋值
				continue;
			}
			EntityHelper.invokeMethod(entity, invoke, value);
		}
		return entity;
	}

	/**
	 * getEntityInvokeAndTypes:(获取字段set方法、字段类型). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param clazz
	 * @param rs
	 * @param columns
	 * @return
	 * @throws SQLException
	 */
	private static MethodAndTypes getEntityInvokeAndTypes(Class<?> clazz,
			ResultSet rs, String[] columns) throws SQLException {
		MethodAndTypes retResult = new MethodAndTypes();
		Object entity = newInstance(clazz);
		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount();
		Method[] invokes = new Method[cols];
		String[] colName = new String[cols];
		int[] types = new int[cols];
		for (int i = 0; i < cols; i++) {
			types[i] = metaData.getColumnType(i + 1);
			if (columns != null)
				colName[i] = columns[i].toLowerCase();
			else
				colName[i] = metaData.getColumnName(i + 1).toLowerCase();
			String propName = colName[i];
			invokes[i] = EntityHelper.getSetMethod(entity, propName);
		}
		retResult.invokes = invokes;
		retResult.types = types;
		return retResult;
	}

	/**
	 * newInstance:(). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	static private Object newInstance(Class<?> clazz) throws SQLException {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new SQLException("不能创建" + clazz.getName() + ": "
					+ e.getMessage());

		} catch (IllegalAccessException e) {
			throw new SQLException("不能创建" + clazz.getName() + ": "
					+ e.getMessage());
		}
	}

	/**
	 * getColumnValue:(取数据库值). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param type
	 * @param resultSet
	 * @param i
	 * @return
	 * @throws SQLException
	 */
	public static Object getColumnValue(int type, ResultSet resultSet, int i)
			throws SQLException {
		Object value;
		switch (type) {
		case Types.VARCHAR:
		case Types.CHAR:
			value = resultSet.getString(i);
			break;
		case Types.DATE:
			value = resultSet.getDate(i);
			break;
		case Types.CLOB:
			value = getClob(resultSet, i);
			break;
		case Types.BLOB:
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
			value = null;
			break;
		default:
			value = resultSet.getObject(i);
			break;
		}
		return value;
	}

	/**
	 * getClob:(clob字段转字符串). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public static String getClob(ResultSet rs, int columnIndex)
			throws SQLException {
		Reader rsReader = rs.getCharacterStream(columnIndex);
		if (rsReader == null)
			return null;
		BufferedReader reader = new BufferedReader(rsReader);
		StringBuffer buffer = new StringBuffer();
		try {
			while (true) {
				String c = reader.readLine();
				if (c == null) {
					break;
				}
				buffer.append(c);
			}
			reader.close();
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return buffer.toString();
	}
}
