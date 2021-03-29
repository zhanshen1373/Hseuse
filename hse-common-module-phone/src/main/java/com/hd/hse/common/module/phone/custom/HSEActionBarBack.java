package com.hd.hse.common.module.phone.custom;

import android.app.Activity;
import android.view.View;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.HSEActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;

/**
 * ClassName: HSEActionBarBack (关闭(小于号样式))
 * date: 2014年10月10日  
 *
 * @author wenlin
 * @version 
 */
public class HSEActionBarBack extends HSEActionBar {
	
	private IEventListener eventListener;
	private IEventListener eventListenerBack;
	/**
	 * currentTabName:TODO(当前页签名称).
	 */
	private String currentTabName;

	public HSEActionBarBack(Activity activity, IEventListener eventListener,
			String[] controlsVisible , String currentTabName) {
		super(activity, controlsVisible);
		this.eventListener = eventListener;
		this.currentTabName = currentTabName;
		super.setEventListener(eventProcessor);
		// TODO Auto-generated constructor stub
		initActionBar();
	}
	public HSEActionBarBack(Activity activity, IEventListener eventListener,
			String[] controlsVisible , String currentTabName,IEventListener eventListenerBack) {
		super(activity, controlsVisible);
		this.eventListener = eventListener;
		this.eventListenerBack=eventListenerBack;
		this.currentTabName = currentTabName;
		super.setEventListener(eventProcessor);
		// TODO Auto-generated constructor stub
		initActionBar();
	}
	
	
	/**
	 * initActionBar:(设置back控件). 
	 * date: 2014年10月10日
	 *
	 * @author wenlin
	 */
	private void initActionBar() {
		// TODO Auto-generated method stub
		//super.tvBack.setVisibility(View.VISIBLE);
		//super.tvBack.setText(splitWorkName(currentTabName));
	}
	/**
	 * TODO 设置导航栏标题
	 * setCurTabName:(). <br/>
	 * date: 2014年11月20日 <br/>
	 *
	 * @author wenlin
	 * @param curTabName
	 */
	public void setCurTabName(String curTabName){
		//super.setTextContent(new String[]{IActionBar.TV_BACK}, new String[]{curTabName});
	}

	/**
	 * eventProcessor:TODO(拦截back事件并做关闭处理，否则继续向上传事件).
	 */
	private IEventListener eventProcessor = new IEventListener() {
		
		public void eventProcess(int arg0, Object... arg1) throws HDException {
			// TODO Auto-generated method stub
			if(arg0 == IEventType.ACTIONBAR_BACK_CLICK){
				if (eventListenerBack == null) {
					setHomeAction();
				} else {
					eventListenerBack.eventProcess(arg0,arg1);
				}
			}else{
				HSEActionBarBack.this.eventListener.eventProcess(arg0 , arg1);
			}
		}

	};
	/**
	 * setHomeAction:(). 
	 * date: 2014年10月10日
	 *
	 * @author wenlin
	 */
	private void setHomeAction(){
		SystemApplication.getInstance().popActivity();
	}
	@Override
	public void setEventListener(IEventListener eventListener) {
		this.eventListener = eventListener;
	}
		
}
