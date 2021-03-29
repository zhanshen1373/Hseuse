/**
 * Project Name:hse-main-business
 * File Name:IQueryMainInfo.java
 * Package Name:com.hd.hse.main.business.main.service
 * Date:2015年2月10日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.main.business.main.service;

import java.util.List;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.service.workorder.queryinfo.IQuery;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;

/**
 * ClassName:IQueryMainInfo (main模块中信息查询业务).<br/>
 * Date:     2015年2月10日  <br/>
 * @author   zhaofeng
 * @version  
 * @see 	 
 */
public interface IQueryMainInfo extends IQuery{
	
	/**
	 * queryAppModuleZypCount:(获取模块对应的作业票数量). <br/>
	 * date: 2015年2月11日 <br/>
	 *
	 * @author zhaofeng
	 * @param appModuleList 传入模块对应的实体集合
	 * @param lister 异步回调函数
	 * @return 返回传入的集合，更新了实体中表示作业票数量字段的值
	 * @throws DaoException
	 */
	public List<AppModule> queryAppModuleZypCount(List<AppModule> appModuleList, IQueryCallEventListener lister) throws DaoException;
}

