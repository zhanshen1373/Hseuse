package com.hd.hse.osc.business.workorder;

import org.apache.log4j.Logger;

import android.os.Message;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.zyxk.DownZYSQQtjc;

public class WorkTaskActionListener extends AbstractActionListener {
	private static Logger logger = LogUtils
			.getLogger(WorkTaskActionListener.class);

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		// 登录
		if (IActionType.QTJC_SYNCHRONOUS.equals(action)) {
			//return login(args[0]);
			qTJCSynchronous(args);
			return null;
		}
		return super.action(action);
	}
	
	/**
	 * initDownLoadData:(数据初始化). <br/>
	 * date: 2014年11月19日 <br/>
	 *
	 * @author lxf
	 */
	public void  qTJCSynchronous(Object... args)
	{
		if(args==null || args.length<1)
		{
			return ;
		}
		
		sendAsyncProcessingMsg("同步气体检测");
		DownZYSQQtjc qtjcSYN = new DownZYSQQtjc();
		qtjcSYN.setAsyncTask(getAsyncTask());
		try {
			qtjcSYN.action(null,args);
			sendAsyncQtjcSusMsg();
		} catch (HDException e) {
			// TODO Auto-generated catch block
			logger.error("同步气体检测：" + e.getMessage(), e);
			sendAsyncErrMsg(e.getMessage());
		} catch (Exception e) {
			logger.error("同步气体检测：" + e.getMessage(), e);
			sendAsyncErrMsg("同步气体检测失败");
		}
		
	}

	/**
	 * sendAsyncProcessingMsg:(同步等待消息). <br/>
	 * date: 2014年12月1日 <br/>
	 *
	 * @author lxf
	 * @param msg
	 */
	private void sendAsyncProcessingMsg(String msg){
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.PROCESSING;
		message.getData().putString(IActionType.QTJC_SYNCHRONOUS, msg);
		getAsyncTask().sendMessage(message);
	}

	/**
	 * sendAsyncSusMsg:(同步成功后发送消息). <br/>
	 * date: 2014年12月1日 <br/>
	 *
	 * @author lxf
	 * @param msg
	 */
	private void sendAsyncQtjcSusMsg() {
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.END;
		message.getData().putString(IActionType.QTJC_SYNCHRONOUS, "同步气体检测成功");
		getAsyncTask().sendMessage(message);
	}
	 
	/**
	 * sendAsyncErrMsg:(同步失败时发送的错误消息). <br/>
	 * date: 2014年12月1日 <br/>
	 *
	 * @author lxf
	 * @param msg
	 */
	private void sendAsyncErrMsg(String msg) {
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.ERROR;
		message.getData().putString(IActionType.QTJC_SYNCHRONOUS, msg);
		getAsyncTask().sendMessage(message);
	}

}
