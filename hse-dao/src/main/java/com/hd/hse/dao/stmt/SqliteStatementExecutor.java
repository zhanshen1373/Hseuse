/**
 * Project Name:hse-dao
 * File Name:StatementExecutor.java
 * Package Name:com.hd.hse.stmt
 * Date:2014年8月13日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.dao.stmt;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.IProcessResultSet;

import org.apache.commons.lang.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:StatementExecutor (sql执行器).<br/>
 * Date: 2014年8月13日 <br/>
 *
 * @author lg
 * @see
 */
public class SqliteStatementExecutor extends BaseStatementExecutor {

    /**
     * TODO
     *
     * @see IStatementExecutor#insert(IConnection,
     * SuperEntity[])
     */
    public int[] insert(IConnection connection, SuperEntity[] entities)
            throws SQLException {
        String[] cols = entities[0].getDBTableColumns();
        String insertSql = buildInsertSql(entities[0].getDBTableName(), cols);
        PreparedStatement stmt = null;
        int[] result = null;
        try {
            stmt = connection.prepareStatement(insertSql);
            for (SuperEntity entity : entities) {
                int i = 1;
                for (String col : cols) {
                    stmt.setObject(i, entity.getAttribute(col));
                    i++;
                }
                stmt.addBatch();
            }
            result = stmt.executeBatch();
        } finally {
            if (stmt != null)
                stmt.close();
        }
        return result;
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#executeQuery(IConnection,
     * String, IProcessResultSet)
     */
    public Object executeQuery(IConnection connection, String sql,
                               IProcessResultSet type) throws DaoException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return type.processResultSet(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new DaoException(e.getMessage(), e);
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                }
        }
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#executeUpdate(IConnection,
     * String)
     */
    public int executeUpdate(IConnection connection, String sql)
            throws SQLException {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
        }
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#executeUpdate(IConnection,
     * String[])
     */
    public int[] executeUpdate(IConnection connection, String[] sqls)
            throws SQLException {
        // TODO Auto-generated method stub
        Statement stmt = null;
        int[] updateRow = new int[sqls.length];
        int i = 0;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            for (String sql : sqls) {
                //long l = System.currentTimeMillis();
                updateRow[i++] = stmt.executeUpdate(sql);
                /*long l1 = System.currentTimeMillis();
                if (l1 - l > 1000) {
                    Log.e("qq", sql + "-----sqlitestatementExecutor:");
                    Log.e("bb", l1 - l + "单条sql时间大于1秒");
                }*/
            }
            connection.commit();
            return updateRow;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        } finally {
            if (stmt != null)
                try {
                    stmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
        }
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#delete(IConnection,
     * SuperEntity[])
     */
    public void delete(IConnection connection, SuperEntity[] entities)
            throws SQLException {
        String primaryKey = entities[0].getPrimaryKey();
        String tableName = entities[0].getDBTableName();
        String[] childClasses = entities[0].getChildClasses();
        String[][] childs = null;
        if (childClasses != null) {
            childs = new String[childClasses.length][2];
            int i = 0;
            for (String childClass : childClasses) {
                SuperEntity entity = newInstance(childClass);
                childs[i][0] = entity.getDBTableName();
                childs[i][1] = entity.getForeignKey();
                i++;
            }
        }
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            for (SuperEntity entity : entities) {
                stmt.executeUpdate(buildDeleteSql(tableName, primaryKey,
                        entity.getAttribute(primaryKey)));
                if (childs != null) {
                    for (String[] child : childs) {
                        stmt.executeUpdate(buildDeleteSql(child[0], child[1],
                                entity.getAttribute(primaryKey)));
                    }
                }
            }
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#update(IConnection,
     * SuperEntity[])
     */
    public void update(IConnection connection, SuperEntity[] entities)
            throws SQLException {
        String primaryKey = entities[0].getPrimaryKey();
        String tableName = entities[0].getDBTableName();
        String[] cols = entities[0].getDBTableColumns();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(buildUpdateSql(tableName, cols,
                    primaryKey));
            for (SuperEntity entity : entities) {
                int i = 1;
                for (String col : cols) {
                    if (!col.equalsIgnoreCase(primaryKey)) {
                        stmt.setObject(i, entity.getAttribute(col));
                        i++;
                    }
                }
                stmt.setObject(i, entity.getAttribute(primaryKey));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    private SuperEntity newInstance(String className) throws SQLException {
        try {
            SuperEntity newEntity = (SuperEntity) Class.forName(className).newInstance();
            newEntity.setAdd(false);
            return newEntity;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new SQLException(e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new SQLException(e);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            throw new SQLException(e);
        }
    }

    /**
     * TODO
     *
     * @see IStatementExecutor#update(IConnection,
     * SuperEntity[], String[])
     */
    public void update(IConnection connection, SuperEntity[] entities,
                       String[] cols) throws SQLException {
        // TODO Auto-generated method stub
        String primaryKey = entities[0].getPrimaryKey();
        String tableName = entities[0].getDBTableName();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(buildUpdateSql(tableName, cols,
                    primaryKey));
            for (SuperEntity entity : entities) {
                int i = 1;
                for (String col : cols) {
                    if (!col.equalsIgnoreCase(primaryKey)) {
                        stmt.setObject(i, entity.getAttribute(col));
                        i++;
                    }
                }
                stmt.setObject(i, entity.getAttribute(primaryKey));
                stmt.addBatch();
            }
            stmt.executeBatch();
        } finally {
            if (stmt != null)
                stmt.close();
        }
    }

    /**
     * TODO
     * <p>
     * Class, String[], String)
     */
    @SuppressWarnings("unchecked")
    public List<SuperEntity> executeQuery(IConnection connection,
                                          Class<?> entityClass, String[] cols, String where, boolean loadChild)
            throws DaoException {
        // TODO Auto-generated method stub
        SuperEntity newEntity;
        String tableName = null;
        try {
            newEntity = newInstance(entityClass.getName());
            if (cols == null || cols.length == 0) {
                cols = newEntity.getDBTableColumns();
            }
            tableName = newEntity.getDBTableName();
            if (StringUtils.isEmpty(tableName)) {
                throw new DaoException("实体未指定表名：" + entityClass.getName());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new DaoException(e.getMessage(), e);
        }
        // 主表数据查询
        String sql = buildQuerySql(tableName, cols, where);
        List<SuperEntity> lstEntity = (List<SuperEntity>) this.executeQuery(
                connection, sql, new EntityListResult(entityClass));
        // 子表数据查询
        if (loadChild && lstEntity != null && !lstEntity.isEmpty()) {
            String[] childsClass = newEntity.getChildClasses();
            if (childsClass != null && childsClass.length > 0) {
                loadChilds(connection, childsClass, lstEntity);
            }
        }
        return lstEntity;
    }

    /**
     * loadChilds:(加载子表数据). <br/>
     * date: 2014年8月19日 <br/>
     *
     * @param connection
     * @param childsClass
     * @param lstParentEntity
     * @throws DaoException
     * @author lg
     */
    @SuppressWarnings("unchecked")
    private void loadChilds(IConnection connection, String[] childsClass,
                            List<SuperEntity> lstParentEntity) throws DaoException {
        StringBuilder sbForeignKey = new StringBuilder();
        String parentPrimaryKey = lstParentEntity.get(0).getPrimaryKey();
        for (SuperEntity entity : lstParentEntity) {
            sbForeignKey.append("'")
                    .append(entity.getAttribute(parentPrimaryKey)).append("',");
        }
        sbForeignKey.setLength(sbForeignKey.length() - 1);
        // 按主表主键分组
        HashMap<String, HashMap<String, List<SuperEntity>>> mapMapListChild = new HashMap<String, HashMap<String, List<SuperEntity>>>();
        for (String childClass : childsClass) {
            try {
                SuperEntity newChildEntity = newInstance(childClass);
                String foreignKey = newChildEntity.getForeignKey();// 外键
                String childTableName = newChildEntity.getDBTableName();// 表名
                String[] childCols = newChildEntity.getDBTableColumns();// 列
                String sql = buildQuerySql(childTableName, childCols,
                        foreignKey + " in(" + sbForeignKey + ")");
                List<SuperEntity> lstChild = (List<SuperEntity>) this
                        .executeQuery(connection, sql, new EntityListResult(
                                newChildEntity.getClass()));
                if (lstChild != null && !lstChild.isEmpty()) {
                    for (SuperEntity entity : lstChild) {
                        String foreignValue = (String) entity
                                .getAttribute(foreignKey);
                        if (!mapMapListChild.containsKey(foreignValue)) {
                            HashMap<String, List<SuperEntity>> mapLstChild = new HashMap<String, List<SuperEntity>>();
                            mapMapListChild.put(foreignValue, mapLstChild);
                            mapLstChild.put(childClass,
                                    new ArrayList<SuperEntity>());
                        } else if (!mapMapListChild.get(foreignValue)
                                .containsKey(childClass)) {
                            mapMapListChild.get(foreignValue).put(childClass,
                                    new ArrayList<SuperEntity>());
                        }
                        mapMapListChild.get(foreignValue).get(childClass)
                                .add(entity);
                    }
                    for (SuperEntity entity : lstParentEntity) {
                        String primaryValue = (String) entity
                                .getAttribute(parentPrimaryKey);
                        if (mapMapListChild.containsKey(primaryValue)) {
                            entity.setChild(
                                    childClass,
                                    mapMapListChild.get(primaryValue).get(
                                            childClass));
                        }
                    }
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                throw new DaoException(e.getMessage(), e);
            }
        }
    }
}
