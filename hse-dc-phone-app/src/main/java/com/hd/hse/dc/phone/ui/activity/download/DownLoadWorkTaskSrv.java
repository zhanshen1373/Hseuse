/**
 * Project Name:hse-dc-app
 * File Name:DownLoadWorkTaskSrv.java
 * Package Name:com.hd.hse.dc.ui.activity.list.download.datasource
 * Date:2014年10月10日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.dc.phone.ui.activity.download;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Context;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.listener.GetListActionListener;

/**
 * ClassName: DownLoadWorkTaskSrv ()<br/>
 * date: 2014年10月10日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class DownLoadWorkTaskSrv {

	/**
	 * logger:TODO(日志).
	 */
	private Logger logger = LogUtils.getLogger(DownLoadWorkTaskSrv.class);
	/**
	 * context:TODO(上下文).
	 */
	private Context context;
	/**
	 * action:TODO(后台业务处理).
	 */
	private BusinessAction action;

	/**
	 * actionLsnr:TODO(特殊业务动作处理).
	 */
	private GetListActionListener actionLsnr;

	/**
	 * asyncTask:TODO(登录异步处理).
	 */
	private BusinessAsyncTask asyncTask;

	/**
	 * prsDlg:TODO(获取列表等待).
	 */
	private ProgressDialog prsDlg;

	/**
	 * Creates a new instance of DownLoadWorkTaskSrv.
	 * 
	 * @param context
	 */
	public DownLoadWorkTaskSrv(Context context) {
		this.context = context;
	}

	/**
	 * initialize:(异步加载数据). <br/>
	 * date: 2014年10月10日 <br/>
	 * 
	 * @author zhaofeng
	 */
	public void geteDownLoadWorkList(String searchStr) {
		if (action == null) {
			actionLsnr = new GetListActionListener(false);
			action = new BusinessAction(actionLsnr);
		}
		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(action,
					new AbstractAsyncCallBack() {

						@Override
						public void start(Bundle msgData) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void processing(Bundle msgData) {
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							prsDlg.showMsg(msg.getMessage());
						}

						@Override
						public void error(Bundle msgData) {
							// TODO Auto-generated method stub
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							prsDlg.dismiss();
							// String msg =
							// msgData.getString(IActionType.LOGIN_LOGIN);
							ToastUtils.toast(context, msg.getMessage());
						}

						@SuppressWarnings("unchecked")
						@Override
						public void end(Bundle msgData) {
							// TODO Auto-generated method stub
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							if(getDataSourceListener!=null){
								try {
									getDataSourceListener.eventProcess(IEventType.DOWN_WORK_LIST_SHOW, (List<SuperEntity>)msg.getReturnResult());
								} catch (HDException e) {
									// TODO Auto-generated catch block
									logger.error(e.getMessage(), e);									
								}
							}
							prsDlg.dismiss();
						}
					});
		}
		// 异步处理
		try {
			asyncTask.execute("", searchStr);
			prsDlg = new ProgressDialog(context, "远程获取数据");
			prsDlg.setCancelable(true);
			prsDlg.setCanceledOnTouchOutside(false);
			prsDlg.show();
		} catch (HDException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		} 
	}
	
	/**
	 * getDataSourceListener:TODO(获取数据时的监听事件).
	 */
	private IEventListener getDataSourceListener = null;

	public final void setGetDataSourceListener(IEventListener getDataSourceListener) {
		this.getDataSourceListener = getDataSourceListener;
	}
}
