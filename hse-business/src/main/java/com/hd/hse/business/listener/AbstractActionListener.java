/**
 * Project Name:hse-business
 * File Name:AbstractActionListener.java
 * Package Name:com.hd.hse.business.listener
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.business.listener;

import java.util.List;

import com.hd.hse.business.action.IAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;

/**
 * ClassName:AbstractActionListener (业务 动作监听，特殊业务处理).<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public abstract class AbstractActionListener implements IActionListener,
		IAction {
	public boolean isCancel;	// 任务中止标志
	
	/**
	 * asyncTask:TODO(异步任务).
	 */
	private BusinessAsyncTask asyncTask;

	public Object action(String action,Object... args ) throws HDException {
		// TODO Auto-generated method stub
		return null;

	}

	public SuperEntity setDefaultValueAdd(SuperEntity entity)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	public SuperEntity beforeSaveEntity(IConnection connection, BaseDao dao,
			SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	public SuperEntity afterSaveEntity(IConnection connection, BaseDao dao,
			SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SuperEntity> beforeSaveEntities(IConnection connection,
			BaseDao dao, List<SuperEntity> entities) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SuperEntity> afterSaveEntities(IConnection connection,
			BaseDao dao, List<SuperEntity> entities) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	public void beforeDeleteEntity(IConnection connection, BaseDao dao,
			SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub

	}

	public void afterDeleteEntity(IConnection connection, BaseDao dao,
			SuperEntity entity) throws HDException {
		// TODO Auto-generated method stub

	}

	public void afterDelteEntities(IConnection connection, BaseDao dao,
			List<SuperEntity> entities) throws HDException {
		// TODO Auto-generated method stub

	}

	public void beforeDelteEntities(IConnection connection, BaseDao dao,
			List<SuperEntity> entities) throws HDException {
		// TODO Auto-generated method stub

	}

	public BusinessAsyncTask getAsyncTask() {
		return asyncTask;
	}

	public void setAsyncTask(BusinessAsyncTask asyncTask) {
		this.asyncTask = asyncTask;
	}

}
