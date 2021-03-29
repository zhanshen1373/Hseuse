package com.hd.hse.wov.phone.ui.common.progressdialog;

import org.apache.log4j.Logger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.MessageProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

/**
 * 进度条父类 ClassName: MSGProgressDialog (progressDialog)<br/>
 * date: 2014年10月11日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MSGProgressDialog {
	/**
	 * logger:TODO(日志).
	 */
	private Logger logger = LogUtils.getLogger(MSGProgressDialog.class);
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
	private AbstractActionListener actionLsnr;

	/**
	 * asyncTask:TODO(异步处理).
	 */
	private BusinessAsyncTask asyncTask;

	/**
	 * mDialog:TODO(显示进度条).
	 */
	private MessageProgressDialog mDialog = null;

	/**
	 * getDataSourceListener:TODO(获取数据时的监听事件).
	 */
	private IEventListener getDataSourceListener = null;

	public MSGProgressDialog(Context context) {
		this.context = context;
	}


	/**
	 * setGetDataSourceListener:(). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author lxf
	 * @param getDataSourceListener
	 */
	public final void setGetDataSourceListener(
			IEventListener getDataSourceListener) {
		this.getDataSourceListener = getDataSourceListener;
	}

	private void showDialog(String titile, String message) {
		mDialog = new MessageProgressDialog(context);
		mDialog.setTitle(titile);
		mDialog.setMessage(message);
		mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		// mDialog.executSuccessful();
		mDialog.setOnCancelListener(new OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				// cancel(true);
			}
		});
		mDialog.show();
		mDialog.setMax(100);
	}

	/**
	 * getActionListner:(得到动作类型). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public AbstractActionListener getActionListner() {
		return null;
	}

	/**
	 * initInfo:(初始化参数). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author lxf
	 */
	private void initInfo() {
		if (action == null) {
			actionLsnr = getActionListner();// new GetListActionListener();
			if (null == actionLsnr) {
				return;
			}
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
							int process = msg.getCurrent();
							if (null != mDialog) {
								mDialog.setMessage(msg.getMessage());
								if (process >= 100) {
									// mDialog.dismiss();
									// mDialog=null;
								} else {
									mDialog.setProgress(process);
								}
							}

						}

						@Override
						public void error(Bundle msgData) {
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							if (null != mDialog) {
								mDialog.setMessage(msg.getMessage());
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
								}
								// 设置显示按钮 提示错误
								mDialog.executFailed();
							} else {
								ToastUtils.toast(context, msg.getMessage());
							}
							//ToastUtils.toast(context, msg.getMessage());
						}

						@SuppressWarnings("unchecked")
						@Override
						public void end(Bundle msgData) {
							// TODO Auto-generated method stub
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							if (null != mDialog) {
								mDialog.setMessage("恭喜你,操作成功!");
								mDialog.setProgress(100);
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
								}
								mDialog.executSuccessful();
							}
							if (getDataSourceListener != null) {
								try {
									// 回调函数
									getDataSourceListener.eventProcess(
											IEventType.DOWN_WORK_LIST_LOAD,
											msg.getReturnResult());
								} catch (HDException e) {
									logger.error(e.getMessage(), e);
								}
							}
						}
					});
		}
	}

	/**
	 * execute:(表示执行). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author lxf
	 * @param Action
	 * @param args
	 */
	public void execute(String titile, String message, String Action,
			Object... args) {
		initInfo();
		if (null == asyncTask) {
			return;
		}
		try {
			showDialog(titile, message);
			asyncTask.execute(Action, args);
		} catch (HDException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
