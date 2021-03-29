package com.hd.hse.ss.business.workorder;

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
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dc.business.listener.GetCarXkzListActionListener;
import com.hd.hse.dc.business.listener.GetListActionListener;
import com.hd.hse.service.workorder.WorkTaskDBSrv;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * ClassName: WorkTaskSrv ()<br/>
 * date: 2015年2月3日 <br/>
 * 
 * @author lych
 * @version
 */
public class ServerWorkTaskSrv extends WorkTaskDBSrv {

	/**
	 * logger:TODO(日志).
	 */
	private Logger logger = LogUtils.getLogger(ServerWorkTaskSrv.class);
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

	private GetCarXkzListActionListener carXkzListActionListener;

	/**
	 * asyncTask:TODO(登录异步处理).
	 */
	private BusinessAsyncTask asyncTask;

	/**
	 * prsDlg:TODO(获取列表等待).
	 */
	private ProgressDialog prsDlg;

	/**
	 * getDataSourceListener:TODO(获取数据时的监听事件).
	 */
	private IEventListener getDataSourceListener = null;

	/**
	 * Creates a new instance of WorkTaskSrv.
	 * 
	 * @param context
	 */
	public ServerWorkTaskSrv(Context context) {
		this.context = context;
	}

	/**
	 * Creates a new instance of WorkTaskSrv.
	 * 
	 */
	public ServerWorkTaskSrv() {
	}

	/**
	 * 查询回调
	 * 
	 */
	private AbstractAsyncCallBack abstractAsyncCallBack = new AbstractAsyncCallBack() {

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
			AysncTaskMessage msg = (AysncTaskMessage) msgData
					.getSerializable("p");
			prsDlg.dismiss();
			ToastUtils.toast(context, msg.getMessage());
		}

		@SuppressWarnings("unchecked")
		@Override
		public void end(Bundle msgData) {
			AysncTaskMessage msg = (AysncTaskMessage) msgData
					.getSerializable("p");
			if (getDataSourceListener != null) {
				try {
					getDataSourceListener.eventProcess(
							IEventType.DOWN_WORK_LIST_SHOW,
							(List<SuperEntity>) msg.getReturnResult());
				} catch (HDException e) {
					logger.error(e.getMessage(), e);
				}
			}
			prsDlg.dismiss();
		}

	};

	/**
	 * initialize:(异步加载数据). <br/>
	 * date: 2014年10月10日 <br/>
	 * 
	 * @author zhaofeng
	 */
	public void geteDownLoadWorkList(String searchStr) {
		if (action == null) {
			actionLsnr = new GetListActionListener(true);
			action = new BusinessAction(actionLsnr);
		}
		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(action, abstractAsyncCallBack);
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

	public void geteDownLoadWorkList(String[] searchStr) {
		if (action == null) {
			carXkzListActionListener = new GetCarXkzListActionListener(true);
			action = new BusinessAction(carXkzListActionListener);
		}
		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(action, abstractAsyncCallBack);
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




	public final void setGetDataSourceListener(
			IEventListener getDataSourceListener) {
		this.getDataSourceListener = getDataSourceListener;
	}

	/*
	 * 查询作业票的特殊条件
	 */
	@Override
	protected String getWorkOrderExtWhere() throws HDException {
		StringBuilder sbWhere = new StringBuilder();
		// 查询不是草稿的作业票
		sbWhere.append("zysq.status <> '").append(IWorkOrderStatus.WAPPR)
				.append("'");
		return sbWhere.toString();

		// com.hd.hse.osc.business.WorkTaskSrv.getWorkOrderExtWhere()
		// 当前系统时间
		// String dateTime =
		// SystemProperty.getSystemProperty().getSysDateTime();
		// positioncard
		// PositionCard positionCard = SystemProperty.getSystemProperty()
		// .getPositionCard();
		// StringBuilder sbWhere = new StringBuilder();
		// 查询状态为：INPRG的票
		// sbWhere.append("zysq.status = '").append(IWorkOrderStatus.INPRG)
		// .append("'");// 现场核查
		// 再加上位置卡信息
		// if (positionCard != null)
		// sbWhere.append(" and zysq.zylocation = '")
		// .append(positionCard.getLocation()).append("'");// 位置卡
		// 再加上实际结束时间
		// sbWhere.append(" and zysq.sjendtime > '").append(dateTime).append("'");//
		// 作业实际结束时间
		// sbWhere.append(" order by zysq.zystarttime desc");// 排序
		// 然后返回
		// return sbWhere.toString();
	}
}
