/**
 * Project Name:hse-common
 * File Name:InterfaceException.java
 * Package Name:com.hd.hse.common.exception
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.exception;

/**
 * ClassName:InterfaceException ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class InterfaceException extends HDException {
	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = -6251242086322071704L;

	public InterfaceException(String msg) {
		super(msg);
	}

	public InterfaceException(String msg, Exception e) {
		super(msg, e);
	}
}
