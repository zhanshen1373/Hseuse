/**
 * Project Name:hse-business
 * File Name:AbstractAsyncCallBack.java
 * Package Name:com.hd.hse.business.task
 * Date:2014年8月27日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.business.task;

import com.hd.hse.common.exception.HDException;

import android.os.Bundle;

/**
 * ClassName:AbstractAsyncCallBack (异步处理回调类).<br/>
 * Date:     2014年8月27日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public abstract class AbstractAsyncCallBack {
	/**
	 * start:(任务开始). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lg
	 * @param msgData
	 * @throws HDException
	 */
	public abstract void start(Bundle msgData);
	
	/**
	 * processing:(任务执行中). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lg
	 * @param msgData
	 */
	public abstract void processing(Bundle msgData);
	
	/**
	 * end:(任务结束). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lg
	 * @param msgData
	 */
	public abstract void end(Bundle msgData);
	
	/**
	 * error:(任务异常). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lg
	 * @param msgData
	 */
	public abstract void error(Bundle msgData);
}

