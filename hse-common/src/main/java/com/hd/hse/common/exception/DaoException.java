/**
 * Project Name:hse-common
 * File Name:DaoException.java
 * Package Name:com.hd.hse.common.exception
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.exception;

/**
 * ClassName:DaoException ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class DaoException extends HDException {
	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = 631684648239798586L;

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String msg, Exception e) {
		super(msg, e);
	}
}
