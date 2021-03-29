/**
 * Project Name:hse-dao
 * File Name:BaseDao.java
 * Package Name:com.hd.hse.dao
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.dao;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.factory.StatementExecutorManager;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.source.IConnectionSource;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * ClassName:BaseDao (数据库操作CDUQ).<br/>
 * Date: 2014年8月12日 <br/>
 *
 * @author lg
 * @version
 * @see
 */
public class BaseDao {

    private static Logger logger = LogUtils.getLogger(BaseDao.class);

    public BaseDao() {

    }

    /**
     * InsertEntity:(插入). <br/>
     * date: 2014年8月13日 <br/>
     *
     * @author lg
     * @param connection
     * @param entity
     * @return
     * @throws SQLException
     */
    public int insertEntity(IConnection connection, SuperEntity entity)
            throws DaoException {
        return this.insertEntities(connection, new SuperEntity[]{entity});
    }

    /**
     * InsertEntities:(批量插入). <br/>
     * date: 2014年8月13日 <br/>
     *
     * @author lg
     * @param connection
     * @param entities
     * @return
     * @throws SQLException
     */
    public int insertEntities(IConnection connection, SuperEntity[] entities)
            throws DaoException {
        if (entities == null || entities.length == 0) {
            return 0;
        }
        try {
            StatementExecutorManager.getExecutor().insert(connection, entities);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("保存异常：" + e.getMessage(), e);
        }
        return 1;
    }

    /**
     * InsertEntities:(批量插入). <br/>
     * date: 2014年8月13日 <br/>
     *
     * @author lg
     * @param connection
     * @param lstEntity
     * @return
     * @throws SQLException
     */
    public int insertEntities(IConnection connection,
                              List<SuperEntity> lstEntity) throws DaoException {
        if (lstEntity == null || lstEntity.isEmpty()) {
            return 0;
        }
        return this.insertEntities(connection,
                lstEntity.toArray(new SuperEntity[lstEntity.size()]));
    }

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
                               IProcessResultSet type) throws DaoException {
        return StatementExecutorManager.getExecutor().executeQuery(connection,
                sql, type);
    }

    /**
     * executeQuery:(查询). <br/>
     * date: 2014年9月3日 <br/>
     *
     * @author lg
     * @param sql
     * @param type
     * @return
     * @throws DaoException
     */
    public Object executeQuery(String sql, IProcessResultSet type) throws DaoException {
        IConnectionSource conSrc = null;
        IConnection connection = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = conSrc.getNonTransConnection();
            return executeQuery(connection, sql, type);
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

    /**
     * executeUpdate:(删除、更新). <br/>
     * date: 2014年8月14日 <br/>
     *
     * @author lg
     * @param connection
     * @param sql
     * @return
     * @throws DaoException
     */
    public int executeUpdate(IConnection connection, String sql)
            throws DaoException {
        try {
            return StatementExecutorManager.getExecutor().executeUpdate(
                    connection, sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("数据库操作异常：" + e.getMessage(), e);
        }
    }

    /**
     * executeUpdate:(批量删除、更新). <br/>
     * date: 2014年8月14日 <br/>
     *
     * @author lg
     * @param connection
     * @param sqls
     * @return
     * @throws DaoException
     */
    public int executeUpdate(IConnection connection, String[] sqls)
            throws DaoException {
        try {
            StatementExecutorManager.getExecutor().executeUpdate(connection,
                    sqls);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("数据库操作异常：" + e.getMessage(), e);
        }
        return 1;
    }

    /**
     * deleteEntity:(删除). <br/>
     * date: 2014年8月18日 <br/>
     *
     * @author lg
     * @param connection
     * @param entity
     * @throws DaoException
     */
    public void deleteEntity(IConnection connection, SuperEntity entity)
            throws DaoException {
        try {
            StatementExecutorManager.getExecutor().delete(connection,
                    new SuperEntity[]{entity});
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("删除异常：" + e.getMessage(), e);
        }
    }

    /**
     * deleteEntities:(批量删除). <br/>
     * date: 2014年8月18日 <br/>
     *
     * @author lg
     * @param connection
     * @param entities
     * @throws DaoException
     */
    public void deleteEntities(IConnection connection, SuperEntity[] entities)
            throws DaoException {
        try {
            StatementExecutorManager.getExecutor().delete(connection, entities);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("删除异常：" + e.getMessage(), e);
        }
    }

    /**
     * updateEntity:(更新). <br/>
     * date: 2014年8月18日 <br/>
     *
     * @author lg
     * @param connection
     * @param entity
     * @throws DaoException
     */
    public void updateEntity(IConnection connection, SuperEntity entity)
            throws DaoException {
        try {
            StatementExecutorManager.getExecutor().update(connection,
                    new SuperEntity[]{entity});
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("保存异常：" + e.getMessage(), e);
        }
    }

    /**
     * updateEntities:(批量更新). <br/>
     * date: 2014年8月18日 <br/>
     *
     * @author lg
     * @param connection
     * @param entities
     * @throws DaoException
     */
    public void updateEntities(IConnection connection, SuperEntity[] entities)
            throws DaoException {
        try {
            StatementExecutorManager.getExecutor().update(connection, entities);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("保存异常：" + e.getMessage(), e);
        }
    }

    /**
     * updateEntity:(更新指定字段). <br/>
     * date: 2014年8月19日 <br/>
     *
     * @author lg
     * @param connection
     * @param entity
     * @param cols
     * @throws DaoException
     */
    public void updateEntity(IConnection connection, SuperEntity entity,
                             String[] cols) throws DaoException {
        try {
            StatementExecutorManager.getExecutor().update(connection,
                    new SuperEntity[]{entity}, cols);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("保存异常：" + e.getMessage(), e);
        }
    }

    /**
     * updateEntities:(批量更新指定字段). <br/>
     * date: 2014年8月19日 <br/>
     *
     * @author lg
     * @param connection
     * @param entities
     * @param cols
     * @throws DaoException
     */
    public void updateEntities(IConnection connection, SuperEntity[] entities,
                               String[] cols) throws DaoException {
        try {
            StatementExecutorManager.getExecutor().update(connection, entities,
                    cols);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("保存异常：" + e.getMessage(), e);
        }
    }

    /**
     * executeQuery:(). <br/>
     * date: 2014年8月19日 <br/>
     *
     * @author lg
     * @param connection
     * @param entityClass
     * @param cols
     * @param where
     * @return
     * @throws DaoException
     */
    public List<SuperEntity> executeQuery(IConnection connection,
                                          Class<?> entityClass, String[] cols, String where)
            throws DaoException {
        return StatementExecutorManager.getExecutor().executeQuery(connection,
                entityClass, cols, where, false);
    }

    /**
     * executeQueryLoadChilds:(加载子表数据). <br/>
     * date: 2014年8月21日 <br/>
     *
     * @author lg
     * @param connection
     * @param entityClass
     * @param cols
     * @param where
     * @return
     * @throws DaoException
     */
    public List<SuperEntity> executeQueryLoadChilds(IConnection connection,
                                                    Class<?> entityClass, String[] cols, String where)
            throws DaoException {
        return StatementExecutorManager.getExecutor().executeQuery(connection,
                entityClass, cols, where, true);
    }
}
