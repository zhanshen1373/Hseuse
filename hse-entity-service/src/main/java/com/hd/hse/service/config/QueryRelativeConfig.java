/**
 * Project Name:hse-entity-service
 * File Name:QueryRelativeConfig.java
 * Package Name:com.hd.hse.service.config
 * Date:2014年11月25日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.config;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.entity.base.RelationTableName;

/**
 * ClassName: QueryRelativeConfig ()<br/>
 * date: 2014年11月25日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class QueryRelativeConfig implements IQueryRelativeConfig {
	
	private final Logger logger = LogUtils.getLogger(QueryRelativeConfig.class);
	
	private BaseDao dao;
	
	/**
	 * connection:TODO(数据库连接).
	 */
	private IConnection connection;

	public QueryRelativeConfig() {
		this.dao = new BaseDao();
	}

	/**
	 * Creates a new instance of QueryRelativeConfig.
	 *
	 * @param connection
	 */
	public QueryRelativeConfig(IConnection connection) {
		this.dao = new BaseDao();
		this.connection = connection;
	}

	/**
	 * executeQuery:(查询). <br/>
	 * date: 2014年11月10日 <br/>
	 * 
	 * @author lg
	 * @param sql
	 * @param type
	 * @return
	 * @throws DaoException
	 */
	private Object executeQuery(String sql, IProcessResultSet type)
			throws DaoException {
		if (this.connection == null) {
			return dao.executeQuery(sql, type);
		} else {
			return dao.executeQuery(connection, sql, type);
		}
	}
	
	@Override
	public boolean isHadRelative(RelationTableName relationEntity) {
		// TODO Auto-generated method stub
		
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from sys_relation_info where ifnull(sys_type,'')='").append(relationEntity.getSys_type()).append("' and isqy='1' and dr!=1  ");
		sbSql.append(" and  ','||(case when ifnull(sys_fuction,'')=='' then '").append(relationEntity.getSys_fuction()).append("' else sys_fuction end) ||','  like '%,").append(relationEntity.getSys_fuction()).append(",%'  ");
		sbSql.append(" and ','|| (case when ifnull(sys_value,'')=='' then '").append(relationEntity.getSys_value()).append("' else sys_value end) ||',' like '%,").append(relationEntity.getSys_value()).append(",%'  ;");
		try {
			Object obj = executeQuery(sbSql.toString(),new EntityResult(RelationTableName.class));
			if(obj!=null)
				return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block		
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}

	@Override
	public RelationTableName getRelativeObj(RelationTableName relationEntity) {
		// TODO Auto-generated method stub
		RelationTableName newRelationTableName;
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from sys_relation_info where ifnull(sys_type,'')='").append(relationEntity.getSys_type()).append("' and isqy='1' and dr!=1  ");
		sbSql.append(" and  ','||(case when ifnull(sys_fuction,'')=='' then '").append(relationEntity.getSys_fuction()).append("' else sys_fuction end) ||','  like '%,").append(relationEntity.getSys_fuction()).append(",%'  ");
		sbSql.append(" and ','|| (case when ifnull(sys_value,'')=='' then '").append(relationEntity.getSys_value()).append("' else sys_value end) ||',' like '%,").append(relationEntity.getSys_value()).append(",%'  ;");
		try {
			newRelationTableName = (RelationTableName)executeQuery(sbSql.toString(),new EntityResult(RelationTableName.class));
			if(newRelationTableName!=null)
				return newRelationTableName;
		} catch (DaoException e) {
			// TODO Auto-generated catch block		
			logger.error(e.getMessage(), e);
			return null;
		}
		return null;
	}

	@Override
	public boolean isHadRelative(String sys_type) {
		// TODO Auto-generated method stub
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from sys_relation_info where ifnull(sys_type,'')='").append(sys_type).append("' and isqy='1' and dr!=1 ; ");
		try {
			Object obj = executeQuery(sbSql.toString(),new EntityResult(RelationTableName.class));
			if(obj!=null)
				return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block		
			logger.error(e.getMessage(), e);
			return false;
		}
		return false;
	}

	@Override
	public RelationTableName getRelativeObj(String sys_type) {
		// TODO Auto-generated method stub
		RelationTableName newRelationTableName;
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select * from sys_relation_info where ifnull(sys_type,'')='").append(sys_type).append("' and isqy='1' and dr!=1 ; ");
		try {
			newRelationTableName = (RelationTableName)executeQuery(sbSql.toString(),new EntityResult(RelationTableName.class));
			if(newRelationTableName!=null)
				return newRelationTableName;
		} catch (DaoException e) {
			// TODO Auto-generated catch block		
			logger.error(e.getMessage(), e);
			return null;
		}
		return null;
	}

}
