package com.hd.hse.dc.business.common.webservice;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.business.action.IAction;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;

public abstract class AbstractWebServiceClient implements IAction,
		IWebActionListener {

	private WebConfig config;
	/**
	 * setWebconfig:(设置数据交互配置). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @param config
	 */
	public void setWebconfig(WebConfig config)
	{
		this.config=config;
	}
	
	/**
	 * getWebconfig:(获取数据交互配置). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @return
	 * @throws HDException
	 */
	public WebConfig getWebconfig() throws HDException
	{
		if(null==config)
		{
			throw new HDException("请设置setWebconfig()方法;");
		}
		int outtime=config.getTimeOut();
		if(StringUtils.isEmpty(config.getUrl()))
		{
			throw new HDException("请配置交互地址url.");
		}
		//表示相应时间低于1秒或者大约10分钟 将默认值设置为3分钟
		if(outtime<1000 || outtime>600000)
		{
			config.setTimeOut(180000);
		}
		return config;
	}
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object validate(HashMap<String, Object> map) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object upData(HashMap<String, Object> map) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object downData(HashMap<String, Object> map) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object upFile(String actionType,String path,HashMap<String, Object> map) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object downFile(HashMap<String, Object> map) throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
