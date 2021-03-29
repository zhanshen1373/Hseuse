/**
 * Project Name:hse-main-app
 * File Name:WorkOrderCronSrv.java
 * Package Name:com.hd.hse.main.ui.service
 * Date:2014年11月4日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.service;

import java.util.HashMap;

import org.apache.log4j.Logger;

import android.app.IntentService;
import android.content.Intent;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;

/**
 * ClassName:WorkOrderCronSrv (作业票自动作废、关闭).<br/>
 * Date: 2014年11月4日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class WorkOrderCronSrv extends IntentService {

	private static Logger logger = LogUtils.getLogger(WorkOrderCronSrv.class);

	/**
	 * action:TODO(后台业务处理).
	 */
	private BusinessAction action;

	/**
	 * actLsr:TODO(后台业务处理).
	 */
	private AbstractCheckListener actLsr;

	/**
	 * asyTask:TODO(异步任务).
	 */
	private BusinessAsyncTask asyTask;

	public WorkOrderCronSrv() {
		super(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS);
		init();
	}

	public WorkOrderCronSrv(String name) {
		super(name);
		init();
	}

	/**
	 * init:(初始化). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author lg
	 */
	private void init() {
		actLsr = new CheckControllerActionListener();
		action = new BusinessAction(actLsr);
		asyTask = new BusinessAsyncTask(action, null);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		executeCron(intent);
		//logger.error("作业票自动作废、关闭！");
	}

	/**
	 * executeCron:(). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author lg
	 * @param intent
	 */
	private void executeCron(Intent intent) {
		try {
			asyTask.execute(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS,
					new HashMap<>());
		} catch (HDException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}

}
