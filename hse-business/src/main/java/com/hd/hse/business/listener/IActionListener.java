/**
 * Project Name:hse-business
 * File Name:IActionListener.java
 * Package Name:com.hd.hse.business.listener
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.business.listener;

import java.util.List;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;

/**
 * ClassName:IActionListener (业务操作监听).<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IActionListener {
	/**
	 * setDefaultValueAdd:(新增时，设置默认值). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 * @throws HDException
	 */
	public SuperEntity setDefaultValueAdd(SuperEntity entity)
			throws HDException;

	/**
	 * beforeSaveEntity:(保存前处理). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entity
	 * @return
	 * @throws HDException
	 */
	public SuperEntity beforeSaveEntity(IConnection connection, BaseDao dao,
                                        SuperEntity entity) throws HDException;

	/**
	 * afterSaveEntity:(保存后处理). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entity
	 * @return
	 * @throws HDException
	 */
	public SuperEntity afterSaveEntity(IConnection connection, BaseDao dao,
                                       SuperEntity entity) throws HDException;

	/**
	 * beforeSaveEntities:(). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entities
	 * @return
	 * @throws HDException
	 */
	public List<SuperEntity> beforeSaveEntities(IConnection connection,
                                                BaseDao dao, List<SuperEntity> entities) throws HDException;

	/**
	 * afterSaveEntities:(). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entities
	 * @return
	 * @throws HDException
	 */
	public List<SuperEntity> afterSaveEntities(IConnection connection,
                                               BaseDao dao, List<SuperEntity> entities) throws HDException;

	/**
	 * beforeDeleteEntity:(删除前处理). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entity
	 * @throws HDException
	 */
	public void beforeDeleteEntity(IConnection connection, BaseDao dao,
                                   SuperEntity entity) throws HDException;

	/**
	 * afterDeleteEntity:(删除后处理). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entity
	 * @throws HDException
	 */
	public void afterDeleteEntity(IConnection connection, BaseDao dao,
                                  SuperEntity entity) throws HDException;

	/**
	 * afterDelteEntities:(). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entities
	 * @throws HDException
	 */
	public void afterDelteEntities(IConnection connection, BaseDao dao,
                                   List<SuperEntity> entities) throws HDException;

	/**
	 * beforeDelteEntities:(). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param dao
	 * @param entities
	 * @throws HDException
	 */
	public void beforeDelteEntities(IConnection connection, BaseDao dao,
                                    List<SuperEntity> entities) throws HDException;
}
