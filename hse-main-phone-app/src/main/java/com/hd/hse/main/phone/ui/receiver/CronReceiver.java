/**
 * Project Name:hse-main-app
 * File Name:CronReceiver.java
 * Package Name:com.hd.hse.main.ui.receiver
 * Date:2014年11月4日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.main.phone.ui.receiver;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.main.phone.ui.service.WorkOrderCronSrv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ClassName:CronReceiver (后台任务广播收听者，负责后台任务的调度).<br/>
 * Date:     2014年11月4日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class CronReceiver extends BroadcastReceiver {
	private static Logger logger = LogUtils.getLogger(CronReceiver.class);
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS.equals(intent.getAction())){
			//logger.error("自动关闭作废",null);
			Intent srvIntent = new Intent(context, WorkOrderCronSrv.class);
			context.startService(srvIntent);
		}
	}

}

