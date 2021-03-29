/**
 * Project Name:hse-dao
 * File Name:DBType.java
 * Package Name:com.hd.hse.dao.config
 * Date:2014年8月13日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.config;

/**
 * ClassName:DBType (数据库类型).<br/>
 * Date: 2014年8月13日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public enum DBType {
	
	/**
	 * Sqlite:TODO().
	 */
	Sqlite("SQLite", "org.sqldroid.SQLDroidDriver");

	/**
	 * type:TODO(类型).
	 */
	private String type;

	/**
	 * driver:TODO(驱动).
	 */
	private String driver;
	
	/**
	 * extDriver:TODO().
	 */
	private String extDriver;
	
	private DBType(String type, String driver) {
		this.type = type;
		this.driver = driver;
	}

	private DBType(String type, String driver, String extDriver) {
		this.type = type;
		this.driver = driver;
		this.extDriver = extDriver;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getExtDriver() {
		return extDriver;
	}

	public void setExtDriver(String extDriver) {
		this.extDriver = extDriver;
	}
}
