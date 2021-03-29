package com.hd.hse.business.task;

import java.io.Serializable;

/**
 * ClassName: ReturnAysncMessage (异步任务返回的消息对象)<br/>
 * date: 2014年8月28日  <br/>
 *
 * @author lxf
 * @version 
 */
public class AysncTaskMessage implements Serializable {
	

	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = 659663453650460577L;

	private  String message;
	
	private  int current;
	
	private boolean release=false;
	
	private Object returnResult;

	

	public boolean getRelease() {
		return release;
	}
	public void setRelease(boolean isRelease) {
		this.release = isRelease;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	
	/**
	 * getReturnResult:(获取返回结果对象). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public Object getReturnResult() {
		return returnResult;
	}
	/**
	 * setReturnResult:(设置返回结果对象). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @param returnResult
	 */
	public void setReturnResult(Object returnResult) {
		this.returnResult = returnResult;
	}
	
}
