/**
 * Project Name:hse-business
 * File Name:AbstractAction.java
 * Package Name:com.hd.hse.business.action
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.business.action;

import java.util.List;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName:AbstractAction ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public abstract class AbstractAction {

	/**
	 * addEntity:(新增). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entityClass
	 * @return
	 * @throws HDException
	 */
	public abstract SuperEntity addEntity(Class<?> entityClass) throws HDException;

	/**
	 * saveEntity:(保存). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @return
	 * @throws HDException
	 */
	public abstract SuperEntity saveEntity(SuperEntity entity)
			throws HDException;

	/**
	 * saveEntities:(批量保存). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entities
	 * @return
	 * @throws HDException
	 */
	public abstract List<SuperEntity> saveEntities(List<SuperEntity> entities)
			throws HDException;

	/**
	 * deleteEntity:(删除). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @throws HDException
	 */
	public abstract void deleteEntity(SuperEntity entity) throws HDException;

	/**
	 * delteEntities:(批量删除). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param entities
	 * @throws HDException
	 */
	public abstract void delteEntities(List<SuperEntity> entities)
			throws HDException;

	/**
	 * updateEntity:(指定字段更新). <br/>
	 * date: 2014年8月19日 <br/>
	 * 
	 * @author lg
	 * @param entity
	 * @param cols
	 * @throws HDException
	 */
	public abstract void updateEntity(SuperEntity entity, String[] cols)
			throws HDException;

	/**
	 * updateEntities:(批量更新指定字段). <br/>
	 * date: 2014年8月19日 <br/>
	 * 
	 * @author lg
	 * @param entities
	 * @param cols
	 * @throws HDException
	 */
	public abstract void updateEntities(List<SuperEntity> entities,
			String[] cols) throws HDException;

	/**
	 * queryEntity:(). <br/>
	 * date: 2014年8月19日 <br/>
	 *
	 * @author lg
	 * @param entityClass
	 * @param cols
	 * @param where
	 * @return
	 * @throws HDException
	 */
	public abstract SuperEntity queryEntity(Class<?> entityClass,
			String[] cols, String where) throws HDException;

	/**
	 * queryEntities:(). <br/>
	 * date: 2014年8月19日 <br/>
	 *
	 * @author lg
	 * @param entityClass
	 * @param cols
	 * @param where
	 * @return
	 * @throws HDException
	 */
	public abstract List<SuperEntity> queryEntities(Class<?> entityClass,
			String[] cols, String where) throws HDException;
	
	/**
	 * queryEntityLoadChilds:(). <br/>
	 * date: 2014年9月11日 <br/>
	 *
	 * @author lg
	 * @param entityClass
	 * @param cols
	 * @param where
	 * @return
	 * @throws HDException
	 */
	public abstract SuperEntity queryEntityLoadChilds(Class<?> entityClass,
			String[] cols, String where) throws HDException;
	
	/**
	 * queryEntitiesLoadChilds:(). <br/>
	 * date: 2014年9月11日 <br/>
	 *
	 * @author lg
	 * @param entityClass
	 * @param cols
	 * @param where
	 * @return
	 * @throws HDException
	 */
	public abstract List<SuperEntity> queryEntitiesLoadChilds(Class<?> entityClass,
			String[] cols, String where) throws HDException;

}
