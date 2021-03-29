/**
 * Project Name:hse-dao
 * File Name:SequenceGenerator.java
 * Package Name:com.hd.hse.dao
 * Date:2014年8月14日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;

/**
 * ClassName:SequenceGenerator (主键生成器).<br/>
 * Date: 2014年8月14日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class SequenceGenerator {
	private static Logger logger = LogUtils.getLogger(SequenceGenerator.class);
	/**
	 * KEY:TODO().
	 */
	private static String KEY = "PAD";

	@SuppressWarnings("unchecked")
	public static synchronized void genPrimaryKeyValue(SuperEntity[] entities)
			throws DaoException {
		
		guidType(entities);
		//dbtype(entities);
	}
	/**
	 * genPrimaryKeyValue:(给主子表设置主外键). <br/>
	 * date: 2014年10月23日 <br/>
	 *
	 * @author zhaofeng
	 * @param entity
	 * @param childClass
	 * @throws HDException
	 */
	public static synchronized void genPrimaryKeyValue(SuperEntity entity,Class<?> childClass)
			throws HDException {
		
		guidType(entity,childClass);
		//dbtype(entities);
	}
	/**
	 * guidType:(给主子表设置主键). <br/>
	 * date: 2014年10月23日 <br/>
	 *
	 * @author zhaofeng
	 * @param entity
	 * @param childClass
	 * @throws HDException
	 */
	private static void guidType(SuperEntity entity, Class<?> childClass) throws HDException {
		// TODO Auto-generated method stub
		String primaryKey = entity.getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new HDException("实体未设置主键："
					+ entity.getClass().getName());
		}
		String uuid = getUUID();
		entity.setAttribute(primaryKey, uuid);
		List<SuperEntity> list = entity.getChild(childClass.getName());
		for (SuperEntity sigleEntity : list) {
			sigleEntity.setAttribute(primaryKey, uuid);//外键赋值
			guidType(new SuperEntity[] {sigleEntity});//主键赋值
		}
	}
	/**
	 * genKeyValue:(). <br/>
	 * date: 2014年8月15日 <br/>
	 * 
	 * @author lg
	 * @param l
	 * @return
	 */
	private static String genKeyValue(long l) {
		return KEY + StringUtils.leftPad(String.valueOf(l), 17, "0");
	}

	/**
	 * getUUID:(获取唯一值). <br/>
	 * date: 2014年9月19日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	private static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * guidType:(用全球唯一ID的方式设置主键). <br/>
	 * date: 2014年9月19日 <br/>
	 *
	 * @author lxf
	 * @param entities
	 * @throws DaoException
	 */
	private static void guidType(SuperEntity[] entities) throws DaoException {
		String primaryKey = entities[0].getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new DaoException("实体未设置主键："
					+ entities[0].getClass().getName());
		}
		for (SuperEntity entity : entities) {
			entity.setAttribute(primaryKey, getUUID());
		}

	}

	/**
	 * dbtype:(用自定义的规则设置主键). <br/>
	 * date: 2014年9月19日 <br/>
	 *
	 * @author lxf
	 * @param entities
	 * @throws DaoException
	 */
	@SuppressWarnings("unused")
	private static void dbtype(SuperEntity[] entities) throws DaoException {
		IConnectionSource conSrc = null;
		IConnection connection = null;
		long seed = 0l;
		boolean insert = true;
		String primaryKey = entities[0].getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new DaoException("实体未设置主键："
					+ entities[0].getClass().getName());
		}
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = conSrc.getConnection();
			String sql = "select seed from hse_sys_sequence";
			BaseDao dao = new BaseDao();
			HashMap<String, Object> mapSeq = (HashMap<String, Object>) dao
					.executeQuery(connection, sql, new MapResult());
			if (mapSeq != null && !mapSeq.isEmpty()) {
				seed = Long.parseLong(mapSeq.get("seed").toString());
				insert = false;
			}
			for (SuperEntity entity : entities) {
				seed++;
				entity.setAttribute(primaryKey, genKeyValue(seed));
			}
			// 更新数据库
			StringBuilder sbSql = new StringBuilder();
			if (insert) {
				sbSql.append("insert into hse_sys_sequence(seed) values(")
						.append(seed).append(")");
			} else {
				sbSql.append("update hse_sys_sequence set seed=").append(seed);
			}
			dao.executeUpdate(connection, sbSql.toString());
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new DaoException(e.getMessage(), e);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			if (conSrc != null) {
				try {
					conSrc.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}
}
