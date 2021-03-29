/**
 * Project Name:hse-common
 * File Name:DBTableHelper.java
 * Package Name:com.hd.hse.common.table
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.table;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName:DBTableHelper (数据库表、字段).<br/>
 * Date: 2014年8月11日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class DBTableHelper {

	private static Map<String, DBTableConfig<SuperEntity>> cache = new HashMap<String, DBTableConfig<SuperEntity>>();

	private static DBTableHelper helper = new DBTableHelper();

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	private DBTableHelper(){
		
	}

	public static DBTableHelper getInstance() {
		return helper;
	}

	/**
	 * getDBTableColumns:(表列). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 */
	public static String[] getDBTableColumns(SuperEntity entity) {
		return getInstance().getDBTableConfig(entity).getTableInfo()
				.getColumns();
	}

	/**
	 * getTableName:(表). <br/>
	 * date: 2014年8月12日 <br/>
	 *
	 * @author lg
	 * @param entity
	 * @return
	 */
	public static String getTableName(SuperEntity entity) {
		return getInstance().getDBTableConfig(entity).getTableInfo()
				.getTableName();
	}

	/**
	 * getPrimaryKey:(). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 */
	public static String getPrimaryKey(SuperEntity entity) {
		return getInstance().getDBTableConfig(entity).getTableInfo()
				.getPrimaryKey();
	}

	/**
	 * getForeignKey:(). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 */
	public static String getForeignKey(SuperEntity entity) {
		return getInstance().getDBTableConfig(entity).getTableInfo()
				.getForeignKey();
	}

	/**
	 * getDBTableConfig:(). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 */
	private DBTableConfig<SuperEntity> getDBTableConfig(SuperEntity entity) {
		DBTableConfig<SuperEntity> tableConfig = null;
		rwl.readLock().lock();
		try {
			String key = entity.getClass().getName();
			tableConfig = cache.get(key);
			if (tableConfig != null)
				return tableConfig;
			tableConfig = cacheDBTableConfig(key, entity);
		} finally {
			rwl.readLock().unlock();
		}
		return tableConfig;
	}

	/**
	 * cacheDBTableConfig:(缓存). <br/>
	 * date: 2014年8月11日 <br/>
	 * 
	 * @author lg
	 * @param key
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private DBTableConfig<SuperEntity> cacheDBTableConfig(String key,
			SuperEntity entity) {
		DBTableConfig<SuperEntity> tableConfig = null;
		rwl.readLock().unlock();
		rwl.writeLock().lock();
		rwl.readLock().lock();
		try {
			tableConfig = (DBTableConfig<SuperEntity>) DBTableConfig
					.fromClass(entity.getClass());
			cache.put(key, tableConfig);
		} finally {
			rwl.writeLock().unlock();
		}
		return tableConfig;
	}

}
