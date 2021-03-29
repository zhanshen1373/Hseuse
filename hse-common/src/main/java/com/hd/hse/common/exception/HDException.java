/**
 * Project Name:hse-common
 * File Name:AbstractException.java
 * Package Name:com.hd.hse.common.exception
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.exception;

/**
 * ClassName:AbstractException ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class HDException extends Exception {
	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = -1088284949446610293L;

	/**
	 * msgkey:TODO(资源文件key).
	 */
	private String msgkey;

	private String message;

	private Object[] params;

	public HDException() {
		super();
	}

	public HDException(String msg) {
		super(msg);
		this.message = msg;
	}

	public HDException(String msg, Exception e) {
		super(msg, e);
		this.message = msg;
	}

	public HDException(String code, Object[] params) {
		this.msgkey = code;
	}

	public HDException(String code, Object[] params, Exception e) {
		this.msgkey = code;
	}

}
