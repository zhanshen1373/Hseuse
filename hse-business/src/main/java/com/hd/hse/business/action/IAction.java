/**
 * Project Name:hse-business
 * File Name:IAction.java
 * Package Name:com.hd.hse.business.action
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.business.action;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName:IAction (特殊业务处理接口).<br/>
 * Date:     2014年8月10日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public interface IAction {

	public Object action(String action, Object... args)throws HDException;
	
}

