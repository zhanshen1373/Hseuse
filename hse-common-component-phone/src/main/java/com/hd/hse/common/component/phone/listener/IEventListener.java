/**
 * Project Name:hse-common-component
 * File Name:IEventListener.java
 * Package Name:com.hd.hse.common.component.listener
 * Date:2014年9月26日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.listener;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName:IEventListener (组件事件处理回调接口).<br/>
 * Date: 2014年9月26日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IEventListener {

	/**
	 * eventProcess:(事件回调处理方法). <br/>
	 * date: 2014年9月26日 <br/>
	 *
	 * @author lg
	 * @param eventType
	 * @param objects
	 * @throws HDException
	 */
	public void eventProcess(int eventType, Object... objects)
			throws HDException;
}
