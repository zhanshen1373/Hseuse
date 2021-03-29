/**
 * Project Name:hse-entity-service
 * File Name:IQueryCallEventListener.java
 * Package Name:com.hd.hse.service.workorder.queryinfo
 * Date:2014年10月16日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.workorder.queryinfo;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName: IQueryCallEventListener ()<br/>
 * date: 2014年10月16日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public interface IQueryCallEventListener {

	/**
	 * callBackProcess:(回调函数). <br/>
	 * date: 2014年10月16日 <br/>
	 *
	 * @author zhaofeng
	 * @param type
	 * @param objects
	 * @throws HDException
	 */
	public void callBackProcess(int type, Object... objects)throws HDException;
}
