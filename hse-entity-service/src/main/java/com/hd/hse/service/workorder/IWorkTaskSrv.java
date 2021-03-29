/**
 * Project Name:hse-entity-service
 * File Name:IWorkTaskSrv.java
 * Package Name:com.hd.hse.service.workorder
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.service.workorder;

import java.util.List;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;


/**
 * ClassName:IWorkTaskSrv (作业任务后台服务接口).<br/>
 * Date:     2014年9月23日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public interface IWorkTaskSrv {
	
	/**
	 * loadWorkTaskList:(作业任务列表，带作业票). <br/>
	 * date: 2014年9月23日 <br/>
	 *
	 * @author lg
	 * @param condition
	 * @return
	 * @throws HDException
	 */
	public List<SuperEntity> loadWorkTaskList(String condition) throws HDException;
	
}

