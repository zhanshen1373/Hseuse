package com.hd.hse.padinterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: PadInterfaceResponse (接口交互返回的对象)<br/>
 * date: 2014年9月16日 <br/>
 * 
 * @author ZhangJie
 * @version
 */
public class PadInterfaceResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flag = PadInterfaceContainers.METHOD_FAILD;
	/** 根据请求返回的业务数据[备用] */
	private Object data = null;
	/** 成功执行的记录 */
	private List<Map<String, String>> sucessfulList = new ArrayList<Map<String, String>>();
	/** 失败执行的记录 */
	private List<Map<String, String>> failedList = new ArrayList<Map<String, String>>();
	private String exceptionmsg = "";

	public String getExceptionmsg() {
		return exceptionmsg;
	}

	public void setExceptionmsg(String exceptionmsg) {
		this.exceptionmsg += ";" + exceptionmsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @description 获取成功消息
	 * @author ZhangJie
	 * @date 2014-8-27
	 * @return {<id:value>,<mesg:value>}{<id:value>,<mesg:value>}
	 */
	public List<Map<String, String>> getSucessfulList() {
		return sucessfulList;
	}

	/**
	 * @description 设置成功消息
	 * @author ZhangJie
	 * @date 2014-8-27
	 * @param successfulmap
	 *            {<id:value>,<mesg:value>}
	 */
	public void setSucessfulList(Map<String, String> successfulmap) {
		this.sucessfulList.add(successfulmap);
	}

	/**
	 * @description 错误消息获取
	 * @author ZhangJie
	 * @date 2014-8-27
	 * @return {<id:value>,<mesg:value>}{<id:value>,<mesg:value>}
	 */
	public List<Map<String, String>> getFailedList() {
		return failedList;
	}

	/**
	 * @description 错误消息记录
	 * @author ZhangJie
	 * @date 2014-8-27
	 * @param failedmap
	 *            <li>id:value<li>mesg:value
	 */
	public void setFailedList(Map<String, String> failedmap) {
		this.failedList.add(failedmap);
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		if (this.flag.equalsIgnoreCase(PadInterfaceContainers.METHOD_SUCCESS)
				|| flag.equalsIgnoreCase(PadInterfaceContainers.METHOD_SUCCESS)) {
			this.flag = PadInterfaceContainers.METHOD_SUCCESS;
		} else {
			this.flag = flag;
		}

	}
}
