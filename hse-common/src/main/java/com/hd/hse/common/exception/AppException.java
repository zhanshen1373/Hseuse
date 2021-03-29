/**
 * Project Name:hse-common
 * File Name:AppException.java
 * Package Name:com.hd.hse.common.exception
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.exception;

/**
 * ClassName:AppException ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class AppException extends HDException {
	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = -8427958245900708479L;

	public AppException(String msg) {
		super(msg);
	}

	public AppException(String msg, Exception e) {
		super(msg, e);
	}
}
