/**
 * Project Name:hse-business
 * File Name:BusinessAction.java
 * Package Name:com.hd.hse.business.action
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.business.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;

/**
 * ClassName:BusinessAction (业务处理).<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class BusinessAction extends AbstractAction {

	private static Logger logger = LogUtils.getLogger(BusinessAction.class);

	/**
	 * actionListener:TODO(业务动作监听).
	 */
	private AbstractActionListener actionListener;

	public BusinessAction() {
		super();
	}

	public BusinessAction(AbstractActionListener actionListener) {
		super();
		this.actionListener = actionListener;
	}

	/**
	 * action:(特殊业务处理). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param action
	 * @throws HDException
	 */
	public void action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		if (actionListener == null)
			return;
		this.actionListener.action(action, args);
	}

	/**
	 * action:(特殊业务，异步耗时操作). <br/>
	 * date: 2014年8月27日 <br/>
	 * 
	 * @author lg
	 * @param action
	 * @param asyncTask
	 * @throws HDException
	 */
	public void action(String action, BusinessAsyncTask asyncTask,
			Object... args) throws HDException {
		if (actionListener == null)
			return;
		this.actionListener.setAsyncTask(asyncTask);
		this.actionListener.action(action, args);
	}

	@Override
	public SuperEntity addEntity(Class<?> entityClass) throws HDException {
		// TODO Auto-generated method stub
		SuperEntity entity = null;
		try {
			entity = (SuperEntity) Class.forName(entityClass.getName())
					.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("未能创建对象:" + entityClass.getName());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("未能创建对象:" + entityClass.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("未能创建对象:" + entityClass.getName());
		}
		// 设置默认值
		setDefaultValue(entity);
		return entity;
	}

	/**
	 * setDefaultValue:(新增时默认值设置). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @throws HDException
	 */
	protected void setDefaultValue(SuperEntity entity) throws HDException {
		// 通用字段处理
		entity.setAdd(true);
		SequenceGenerator.genPrimaryKeyValue(new SuperEntity[] { entity });// 主键

		if (actionListener != null) {
			this.actionListener.setDefaultValueAdd(entity);
		}
	}

	@Override
	public SuperEntity saveEntity(SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeSaveEntity(connection, dao, entity);
			}
			// 新增
			if (entity.toBeAdd()) {
				dao.insertEntity(connection, entity);
				entity.setAdd(false);
			}
			// 更新
			else if (entity.toBeUpdate()) {
				dao.updateEntity(connection, entity);
				entity.setModified(false);
			}
			// 删除
			else if (entity.toBeDelete()) {
				dao.deleteEntity(connection, entity);
			}
			// 不合理
			else {
				throw new HDException("数据状态错误");
			}
			if (actionListener != null) {
				actionListener.afterSaveEntity(connection, dao, entity);
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据保存异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
		return entity;
	}

	@Override
	public List<SuperEntity> saveEntities(List<SuperEntity> entities)
			throws HDException {
		// TODO Auto-generated method stub
		List<SuperEntity> lstNew = new ArrayList<SuperEntity>();
		List<SuperEntity> lstUpdate = new ArrayList<SuperEntity>();
		List<SuperEntity> lstDelete = new ArrayList<SuperEntity>();
		for (SuperEntity entity : entities) {
			if (entity.toBeAdd()) {
				lstNew.add(entity);
				entity.setAdd(false);
			} else if (entity.toBeUpdate()) {
				lstUpdate.add(entity);
			} else if (entity.toBeDelete()) {
				lstDelete.add(entity);
			} else {
				throw new HDException("数据状态错误");
			}
		}
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeSaveEntities(connection, dao, entities);
			}
			if (!lstNew.isEmpty()) {
				dao.insertEntities(connection, lstNew);
			}
			if (!lstUpdate.isEmpty()) {
				dao.updateEntities(connection,
						lstUpdate.toArray(new SuperEntity[lstUpdate.size()]));
			}
			if (!lstDelete.isEmpty()) {
				dao.deleteEntities(connection,
						lstDelete.toArray(new SuperEntity[lstDelete.size()]));
			}
			if (actionListener != null) {
				actionListener.afterSaveEntities(connection, dao, entities);
			}
			for (SuperEntity entity : entities) {
				entity.setAdd(false);
				entity.setModified(false);
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("数据保存异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
		return entities;
	}

	@Override
	public void deleteEntity(SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub
		String primaryKey = entity.getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new HDException("实体未指定主键：" + entity.getClass().getName());
		}
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeDeleteEntity(connection, dao, entity);
			}
			dao.deleteEntity(connection, entity);
			if (actionListener != null) {
				actionListener.afterDeleteEntity(connection, dao, entity);
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据删除异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

	@Override
	public void delteEntities(List<SuperEntity> entities) throws HDException {
		// TODO Auto-generated method stub
		if (entities == null || entities.isEmpty())
			return;
		String primaryKey = entities.get(0).getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new HDException("实体未指定主键："
					+ entities.get(0).getClass().getName());
		}
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeDelteEntities(connection, dao, entities);
			}
			dao.deleteEntities(connection,
					entities.toArray(new SuperEntity[entities.size()]));
			if (actionListener != null) {
				actionListener.afterDelteEntities(connection, dao, entities);
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据删除异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

	@Override
	public void updateEntity(SuperEntity entity, String[] cols)
			throws HDException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(entity.getPrimaryKey())) {
			throw new HDException("实体未指定主键：" + entity.getClass().getName());
		}
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeSaveEntity(connection, dao, entity);
			}
			dao.updateEntity(connection, entity, cols);
			if (actionListener != null) {
				actionListener.afterSaveEntity(connection, dao, entity);
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据更新异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

	@Override
	public void updateEntities(List<SuperEntity> entities, String[] cols)
			throws HDException {
		// TODO Auto-generated method stub
		if (entities == null || entities.isEmpty())
			return;
		String primaryKey = entities.get(0).getPrimaryKey();
		if (StringUtils.isEmpty(primaryKey)) {
			throw new HDException("实体未指定主键："
					+ entities.get(0).getClass().getName());
		}
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			if (actionListener != null) {
				actionListener.beforeSaveEntities(connection, dao, entities);
			}
			dao.updateEntities(connection,
					entities.toArray(new SuperEntity[entities.size()]), cols);
			if (actionListener != null) {
				actionListener.afterSaveEntities(connection, dao, entities);
			}
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据更新异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

	@Override
	public SuperEntity queryEntity(Class<?> entityClass, String[] cols,
			String where) throws HDException {
		// TODO Auto-generated method stub
		List<SuperEntity> lstEntity = this.queryEntities(entityClass, cols,
				where);
		if (lstEntity != null && !lstEntity.isEmpty()) {
			return lstEntity.get(0);
		}
		return null;
	}

	@Override
	public List<SuperEntity> queryEntities(Class<?> entityClass, String[] cols,
			String where) throws HDException {
		// TODO Auto-generated method stub
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			return dao.executeQuery(connection, entityClass, cols, where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据查询异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

	@Override
	public SuperEntity queryEntityLoadChilds(Class<?> entityClass,
			String[] cols, String where) throws HDException {
		// TODO Auto-generated method stub
		List<SuperEntity> lstEntity = this.queryEntitiesLoadChilds(entityClass,
				cols, where);
		if (lstEntity != null && !lstEntity.isEmpty()) {
			return lstEntity.get(0);
		}
		return null;
	}

	@Override
	public List<SuperEntity> queryEntitiesLoadChilds(Class<?> entityClass,
			String[] cols, String where) throws HDException {
		// TODO Auto-generated method stub
		IConnectionSource connectionSource = null;
		IConnection connection = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			BaseDao dao = new BaseDao();
			return dao.executeQueryLoadChilds(connection, entityClass, cols,
					where);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据查询异常：" + e.getMessage());
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

}
