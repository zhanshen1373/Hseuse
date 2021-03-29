/**
 * Project Name:hse-cbs-app
 * File Name:MainActionListener.java
 * Package Name:com.hd.hse.cbs.business.main
 * Date:2014年9月5日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.main.business.main;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * ClassName:MainActionListener (主界面业务处理).<br/>
 * Date:     2014年9月5日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class MainActionListener extends AbstractActionListener {

	private BusinessAction action;
	private static Logger logger = LogUtils.getLogger(MainActionListener.class);
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		// 退出系统
		switch (action) {
		case IActionType.MAIN_LOGOUT:
			logout();
			break;

		case IActionType.MAIN_TASKLIST:
			return initDate("TASKLIST");
		default: 
			break;
		}
		
		return super.action(action, args);
	}
	
	/**
	 * logout:(退出). <br/>
	 * date: 2014年9月5日 <br/>
	 *
	 * @author lg
	 * @throws AppException
	 */
	private void logout() throws AppException{
		// 清空系统当前登录人
		SystemProperty.getSystemProperty().setLoginPerson(null);
		//清空位置刷卡信息
		SystemProperty.getSystemProperty().setPositionCard(null);
	}
	

	/**
	 * initDate:(读取功能模块). <br/>
	 * date: 2015年1月6日 <br/>
	 *
	 * @author LXF
	 * @param type
	 * @return
	 * @throws HDException
	 */
	public List<SuperEntity> initDate(String type) throws HDException {
		List<SuperEntity> list;
		
		try {
			action=new BusinessAction();
			list = action.queryEntities(AppModule.class, null,
					"enabled = 1 and type='"+type+"' order by layoutorder asc");
		} catch (HDException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("功能模块初始化出错，请联系系统管理员！");
		}
		return list;
	}

}

