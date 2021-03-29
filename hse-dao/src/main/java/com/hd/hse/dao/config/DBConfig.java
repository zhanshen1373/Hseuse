/**
 * Project Name:hse-dao
 * File Name:DBConfig.java
 * Package Name:com.hd.hse.dao.config
 * Date:2014年8月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.dao.config;
/**
 * ClassName:DBConfig (数据库配置信息，app启动时须初始化).<br/>
 * Date:     2014年8月12日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class DBConfig {
	private String userName;
	
	private String password;
	
	private String url;
	
	private DBType type;
	
	private static DBConfig config = new DBConfig();
	
	private DBConfig(){
		
	}
	
	/**
	 * getConfig:(). <br/>
	 * date: 2014年8月12日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public static DBConfig getConfig(){
		return config;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public DBType getType() {
		return type;
	}

	public void setType(DBType type) {
		this.type = type;
	}

}

