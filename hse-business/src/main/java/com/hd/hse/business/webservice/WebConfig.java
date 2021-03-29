package com.hd.hse.business.webservice;

/**
 * ClassName:WebConfig (数据交互参数配置).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 **/
public class WebConfig {

	/**
	 * url:TODO(交互地址).
	 */
	private String url;
	
	/**
	 * outTime:TODO(超时时间).
	 */
	private int timeOut=60000;
	
	/**
	 * dataAnalyzetype:TODO(数据解析方式).
	 */
	private String dataAnalyzetype;
	//
	/**
	 * clientType:TODO(数据交互类型).
	 */
	private String clientType;
	
	/**
	 * getClientType:(获取监听方式). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * setClientType:(设置监听方式). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @param clientType
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public static WebConfig newInstance() {
		return new WebConfig();
	}

	/**
	 * getOutTime:(获取超时时间). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * setOutTime:(设置超时时间). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author Administrator
	 * @param outTime
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * getUrl:(获取交互地址). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl:(设置交互地址). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author Administrator
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * getDataAnalyzetype:(获取解析方式). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getDataAnalyzetype() {
		return dataAnalyzetype;
	}

	/**
	 * setDataAnalyzetype:(设置解析方式). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @param dataAnalyzetype
	 */
	public void setDataAnalyzetype(String dataAnalyzetype) {
		this.dataAnalyzetype = dataAnalyzetype;
	}

}
