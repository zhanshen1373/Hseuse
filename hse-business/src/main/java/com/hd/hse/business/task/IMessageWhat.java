/**
 * Project Name:hse-business
 * File Name:MessageWhat.java
 * Package Name:com.hd.hse.business.task
 * Date:2014年8月27日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.business.task;
/**
 * ClassName:MessageWhat (异步处理消息类型).<br/>
 * Date:     2014年8月27日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public interface IMessageWhat {
	/**
	 * START:TODO(任务开始).
	 */
	public final static int START = 0;
	
	/**
	 * PROCESSING:TODO(任务进行中).
	 */
	public final static int PROCESSING = 1;
	
	/**
	 * END:TODO(任务结束).
	 */
	public final static int END = 2;
	
	/**
	 * ERROR:TODO(异常终止).
	 */
	public final static int ERROR = 3;
}

