package com.hd.hse.common.module.phone.ui.activity;

import android.app.Activity;
import android.app.Application;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.Stack;

public class SystemApplication extends Application {

	/**
	 * logger:TODO(日志).
	 */
	private static final Logger logger = LogUtils.getLogger(SystemApplication.class);
	/**
	 * isLogger:TODO(是否打印日志).
	 */
	private boolean isLogger = false;
	/**
	 * stackActivity:TODO(栈).
	 */
	private Stack<Activity> stackActivity = new Stack<Activity>();

	private static SystemApplication instance;

	private SystemApplication() {

	}

	public synchronized static SystemApplication getInstance() {
		if (instance == null) {
			instance = new SystemApplication();
		}
		return instance;
	}

	/**
	 * pushActivity:(缓存). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
		if(isLogger){
			logger.error("插入---pushActivity======"+activity.getClass().getName().toString());
		}
		stackActivity.push(activity);
	}

	/**
	 * popActivity:(销毁当前界面). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 */
	public void popActivity() {
		Activity activity = stackActivity.lastElement();
		stackActivity.removeElement(activity);
		if(isLogger){
			logger.error("删除---popActivity======"+activity.getClass().getName().toString());
		}
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	/**
	 * popActivityBesidesClassz:(保持最后两层不被删除). <br/>
	 * date: 2015年3月26日 <br/>
	 *
	 * @author zhaofeng
	 * @param classz
	 */
	public void popActivityBesidesClassz() {
		if(isLogger){
			loggerStack();
		}
		if (stackActivity.size() <= 2) {
			return;
		}
		Activity activity = stackActivity.lastElement();
		stackActivity.removeElement(activity);
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}

	/**
	 * getLastActivity:(获取当前界面对应的activity). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author zhaofeng
	 * @return
	 */
	public Activity getLastActivity() {
		return stackActivity.lastElement();
	}
	/**
	 * isHadActivity:(). <br/>
	 * date: 2015年4月16日 <br/>
	 *
	 * @author wenlin
	 * @param classz
	 * @return
	 */
	public boolean isHadActivity(Class<?> classz){
		for(int i = 0 ; i < stackActivity.size() ; i++){
			if(stackActivity.get(i).getClass() == classz){
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * popActivity:(销毁指定界面). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 * @param activity
	 */
	public void popActivity(Activity activity) {
		if (activity != null) {
			stackActivity.removeElement(activity);
			activity.finish();
			activity = null;
		}
		if(isLogger){
			loggerStack();
		}
	}

	/**
	 * removePopActivty:(只移除，关闭在外边执行). <br/>
	 * date: 2014年11月18日 <br/>
	 * 
	 * @author lxf
	 * @param activity
	 */
	public void removePopActivty(Activity activity) {
		if (activity != null) {
			if (stackActivity.contains(activity)) {
				stackActivity.removeElement(activity);
			}
			activity = null;
		}
		if(isLogger){
			loggerStack();
		}
	}

	/**
	 * exit:(退出). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 */
	public void exit() {
		//移除缓存的位置卡信息
		SystemProperty.getSystemProperty().clearPositionCardList();
		if(isLogger){
			loggerStack();
		}
		for (int i = stackActivity.size(); i > 1; i--) {
			popActivity();
		}
	}
	/**
	 * exitProcess:(). <br/>
	 * date: 2015年4月1日 <br/>
	 *
	 * @author zhaofeng
	 */
	public void exitProcess(){
		if(isLogger){
			loggerStack();
		}
		for (int i = stackActivity.size(); i > 0; i--) {
			popActivity();
		}
		android.os.Process.killProcess(android.os.Process.myPid());//关闭进程
	}

	/**
	 * exit:(退出到主界面). <br/>
	 * date: 2014年12月10日 <br/>
	 * 
	 * @author lych
	 */
	public void exitToMain() {
		if(isLogger){
			loggerStack();
		}
		for (int i = stackActivity.size(); i > 2; i--) {
			popActivity();
		}
	}
	private static int groupId = 0;
	private void loggerStack(){
		for(Activity activity:stackActivity){
			if(isLogger){
				logger.error("第"+groupId+"组++遍历---popActivity======"+activity.getClass().getName().toString());
			}
		}
		groupId++;
	}
}
