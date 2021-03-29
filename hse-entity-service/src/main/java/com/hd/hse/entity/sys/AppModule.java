/**
 * Project Name:hse-entity-service
 * File Name:appmodule.java
 * Package Name:com.hd.hse.entity.sys
 * Date:2014年9月28日
 * Copyright (c) 2014, zhulei@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.sys;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * ClassName: appmodule ()<br/>
 * date: 2014年9月28日 <br/>
 * 
 * 
 * @author HD
 * @version
 * 
 * @pdOid afcd1cd3-5804-4e85-84f8-3e5dae143d4a
 */
@DBTable(tableName = "hse_sys_appmodule")
public class AppModule extends com.hd.hse.common.entity.SuperEntity {
	/**
	 * 功能模块编码
	 * 
	 * @pdOid 6d1854b3-7550-4690-82f5-72af0f08150b
	 */
	@DBField(id = true)
	private String code;
	/**
	 * 功能模块名称
	 * 
	 * @pdOid 1ce00090-5a09-4626-803a-00e5342dc47b
	 */
	@DBField
	private String name;
	/**
	 * 资源图片
	 * 
	 * @pdOid c9f7ff25-aa8a-4410-aed8-e9ff1f943725
	 */
	@DBField
	private String resimg;
	/**
	 * 资源图片聚焦
	 * 
	 * @pdOid 8952b646-0def-41d2-bb2c-f83aa4a60b60
	 */
	@DBField
	private String resimgfocus;
	/**
	 * 资源图片按下
	 * 
	 * @pdOid a98dbb21-c0f6-48d6-b0c0-d5f809efbd25
	 */
	@DBField
	private String resimgpress;
	/**
	 * 事件处理类
	 * 
	 * @pdOid d28ea30b-caee-4180-8861-7f42f8349337
	 */
	@DBField
	private String clickdealclass;
	/**
	 * 显示顺序
	 * 
	 * @pdOid 7cbe9712-08b2-47d9-876e-9dbfb4d52a73
	 */
	@DBField
	private Integer layoutorder;
	/**
	 * 是否启用
	 * 
	 * @pdOid 65674278-dbee-44a1-a178-92460f10f5f2
	 */
	@DBField
	private Integer enabled;

	/**
	 * type:TODO(类别).
	 */
	@DBField
	private String type;
	
	/**
	 * modelnum:TODO(模块编码).
	 */
	@DBField
	private String modelnum;
	/**
	 * isswcard:TODO(是否刷卡)
	 * */
	@DBField
	private Integer isswcard;

	/** @pdOid 93d44cec-9cf8-45e5-a053-6859a825471d */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 * @pdOid e9244f7e-6985-46dc-8341-69d570a69c11
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/** @pdOid df41073b-7091-4af3-b419-207c573f940d */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * @pdOid 70060b1c-04ec-4fae-8679-a03b0d11c11a
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** @pdOid 25ec75bf-bee2-45f3-a63c-5411e020253e */
	public String getResimg() {
		return resimg;
	}

	/**
	 * @param resimg
	 * @pdOid e8db04cd-6ad8-4cc7-968f-07d8ca8015e2
	 */
	public void setResimg(String resimg) {
		this.resimg = resimg;
	}

	/** @pdOid 87802cd4-ecfe-49c8-9388-39b71bbf7b0d */
	public String getResimgfocus() {
		return resimgfocus;
	}

	/**
	 * @param resimgfocus
	 * @pdOid b89636c1-00e9-4bbb-8fb7-53032df32e18
	 */
	public void setResimgfocus(String resimgfocus) {
		this.resimgfocus = resimgfocus;
	}

	/** @pdOid bbddea6f-be6d-4c40-9ba8-76ca8436a275 */
	public String getResimgpress() {
		return resimgpress;
	}

	/**
	 * @param resimgpress
	 * @pdOid a30c4cb5-7aa6-4d40-8686-1b03488c2aca
	 */
	public void setResimgpress(String resimgpress) {
		this.resimgpress = resimgpress;
	}

	/** @pdOid e6ffb285-8267-4780-a4fc-79771bad40c9 */
	public String getClickdealclass() {
		return clickdealclass;
	}

	/**
	 * @param clickdealclass
	 * @pdOid f5984d25-0696-41cd-a8a7-373936225047
	 */
	public void setClickdealclass(String clickdealclass) {
		this.clickdealclass = clickdealclass;
	}

	/** @pdOid 6459f939-63f0-4652-be4a-62161811e8e8 */
	public Integer getLayoutorder() {
		return layoutorder;
	}

	/**
	 * @param layoutorder
	 * @pdOid ff077d43-9bc0-4405-8ca9-40307a36178f
	 */
	public void setLayoutorder(Integer layoutorder) {
		this.layoutorder = layoutorder;
	}

	/** @pdOid f7acaa5a-56be-437d-b880-234deed0ca31 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 * @pdOid 822ee5d2-e43b-44ec-8a89-46d2eadd2495
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * zypCount:TODO(该作模块对应的作业票的数量).
	 */
	private Integer zypCount;

	/**
	 * getZypCount:(获取该作模块对应的作业票的数量). <br/>
	 * date: 2015年2月11日 <br/>
	 *
	 * @author zhaofeng
	 * @return
	 */
	public Integer getZypCount() {
		return zypCount;
	}

	/**
	 * setZypCount:(设置该作模块对应的作业票的数量). <br/>
	 * date: 2015年2月11日 <br/>
	 *
	 * @author zhaofeng
	 * @param zypCount
	 */
	public void setZypCount(Integer zypCount) {
		this.zypCount = zypCount;
	}

	public String getModelnum() {
		return modelnum;
	}

	public void setModelnum(String modelnum) {
		this.modelnum = modelnum;
	}

	public Integer getIsswcard() {
		return isswcard;
	}

	public void setIsswcard(Integer isswcard) {
		this.isswcard = isswcard;
	}

}