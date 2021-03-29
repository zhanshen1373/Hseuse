package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * ClassName: RecentlyLoginPerson ()<br/>
 * date: 2014年9月3日 <br/>
 * 
 * 
 * @author zhulei
 * @version
 * 
 * @pdOid a18a8e3c-2dce-4a72-a5f2-826cf59e70de
 */
@DBTable(tableName = "hse_sys_login")
public class RecentlyLoginPerson extends com.hd.hse.common.entity.SuperEntity {
	/**
	 * serialVersionUID:TODO().
	 * 
	 * 
	 * @pdOid c9558b13-4a64-4299-aad4-10cc49ed425f
	 */
	private static final long serialVersionUID = -5237143299162077915L;
	/**
	 * 用户编码
	 * 
	 * @pdOid 149651d2-9da3-4875-9bbb-3b11afacf035
	 */
	@DBField
	private String usercode;
	/**
	 * 用户名称
	 * 
	 * @pdOid d78f770c-68b1-4e3e-a654-1d4ad416b66c
	 */
	@DBField
	private String username;
	/**
	 * 登录时间
	 * 
	 * @pdOid 266d751c-2ff5-4dbb-8cc9-c7a01244657c
	 */
	@DBField
	private String createdate;
	/**
	 * 顺序
	 * 
	 * @pdOid ef39e7bb-28eb-4e6e-bdb8-2b646105f8a4
	 */
	@DBField
	private Integer sysorder;

	/**
	 * uuid:消息服务ID
	 */
	@DBField
	private String uuid;
	/**
	 * password :用户密码
	 * 
	 * Created by wdf on 2016年3月30日
	 */
	@DBField
	private String password;
	/**
	 * autologin :自动登录
	 * 
	 */
    @DBField
	private Integer autologin;
	

	public Integer getAutologin() {
		return autologin;
	}

	public void setAutologin(Integer autologin) {
		this.autologin = autologin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 设置 用户编码 该字段是：用户编码
	 * 
	 * 
	 * @param usercode
	 * @pdOid 9e4b9acf-ea7d-4e9b-81ec-d162875cac68
	 */
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	/**
	 * 获取 用户编码 该字段是：用户编码
	 * 
	 * 
	 * @pdOid 614b112a-a594-444b-a0ad-c6b52cc20cc7
	 */
	public String getUsercode() {
		return usercode;
	}

	/**
	 * 设置 用户名称 该字段是：用户名称
	 * 
	 * 
	 * @param username
	 * @pdOid 538bc4cd-d933-4f93-b6ab-cfa08bb00362
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取 用户名称 该字段是：用户名称
	 * 
	 * 
	 * @pdOid 006a6a2c-e70a-42c7-9559-33880dc55f24
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置 登录时间 该字段是：登录时间
	 * 
	 * 
	 * @param createdate
	 * @pdOid c8b07c9e-a99b-4423-8601-605f68e4f858
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	/**
	 * 获取 登录时间 该字段是：登录时间
	 * 
	 * 
	 * @pdOid ea9e41cc-a010-4e19-bffb-ff79f6ab09f8
	 */
	public String getCreatedate() {
		return createdate;
	}

	/** @pdOid fd7d2da5-ee92-490e-a3ca-45835d1d9a45 */
	public Integer getSysorder() {
		return sysorder;
	}

	/**
	 * @param sysorder
	 * @pdOid 235dd9db-780b-49b1-a102-3675d3eefb5b
	 */
	public void setSysorder(Integer sysorder) {
		this.sysorder = sysorder;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUuid() {
		return uuid;
	}

}