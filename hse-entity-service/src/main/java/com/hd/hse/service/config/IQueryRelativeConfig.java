/**
 * Project Name:hse-entity-service
 * File Name:IQueryRelativeConfig.java
 * Package Name:com.hd.hse.service.config
 * Date:2014年11月25日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.config;

import com.hd.hse.entity.base.RelationTableName;

/**
 * ClassName: IQueryRelativeConfig ()<br/>
 * date: 2014年11月25日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public interface IQueryRelativeConfig {

	/**
	 * isHadRelative:(判断是否存在关系). <br/>
	 * date: 2014年11月25日 <br/>
	 *
	 * @author zhaofeng
	 * @param relationEntity
	 * @return
	 */
	public boolean isHadRelative(RelationTableName relationEntity); 
	/**
	 * isHadRelative:(判断是否存在关系). <br/>
	 * date: 2015年3月17日 <br/>
	 *
	 * @author zhaofeng
	 * @param sys_type 关系编码
	 * @return
	 */
	public boolean isHadRelative(String sys_type);
	
	/**
	 * getRelativeObj:(获取关系配置信息). <br/>
	 * date: 2014年11月25日 <br/>
	 *
	 * @author zhaofeng
	 * @param relationEntity
	 * @return
	 */
	public RelationTableName getRelativeObj(RelationTableName relationEntity);
	
	/**
	 * getRelativeObj:(获取关系配置信息). <br/>
	 * date: 2015年3月17日 <br/>
	 *
	 * @author zhaofeng 
	 * @param sys_type 关系编码
	 * @return
	 */
	public RelationTableName getRelativeObj(String sys_type);
}
