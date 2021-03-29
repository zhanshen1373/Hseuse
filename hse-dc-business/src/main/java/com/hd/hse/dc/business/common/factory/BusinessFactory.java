package com.hd.hse.dc.business.common.factory;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.business.util.SYSConstant;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.util.JsonDataAdapter;
import com.hd.hse.dc.business.common.util.XmlDataAdapter;
import com.hd.hse.dc.business.common.webservice.AbstractWebServiceClient;
import com.hd.hse.dc.business.common.webservice.HessainClientDel;
import com.hd.hse.dc.business.common.webservice.HessianClient;
import com.hd.hse.dc.business.common.webservice.HttpClient;
import com.hd.hse.dc.business.common.webservice.WebServiceClientDel;

/**
 * ClassName:ObjectFactory (工厂类).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */

public class BusinessFactory implements IBusinessFactory {

	
	/**
	 * newIntance:(). <br/>
	 * date: 2014年9月3日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public static BusinessFactory newIntance()
	{
		return new BusinessFactory();
	}
	
	/**
	 * TODO 
	 * @see com.hd.hse.business.factory.IBusinessFactory#getServerDataListAdapter(String)
	 */
	public AbstractDataAdapter getAnalyzeDataAdapter(WebConfig config) throws HDException
	{
		AbstractDataAdapter anazlyze=null;
		if(null==config || (StringUtils.isEmpty(config.getUrl())))
		{
			return null;
		}
		if(config.getDataAnalyzetype().equalsIgnoreCase(SYSConstant.ANALYZEXML))
		{
			anazlyze=XmlDataAdapter.getInstance();
		}
		else if (config.getDataAnalyzetype().equalsIgnoreCase(SYSConstant.ANALYZEJSON))
		{
			anazlyze=JsonDataAdapter.getInstance();
		}
		else
		{
			throw new HDException("传入参数类型不正确");
		}
			
		return anazlyze;
	}
	
	public WebServiceClientDel getServerDataClient(WebConfig config) throws HDException
	{
		if(null==config || (StringUtils.isEmpty(config.getUrl())))
		{
			return null;
		}
		WebServiceClientDel webclient=null;
		if(config.getClientType().equalsIgnoreCase(SYSConstant.CLIENTHESSION))
		{
			webclient=HessainClientDel.newInstance();
			webclient.setWebconfig(config);
		}
		else if (config.getClientType().equalsIgnoreCase(SYSConstant.CLIENTSERVLET))
		{
			webclient=HttpClient.newInstance();
			webclient.setWebconfig(config);
		}
		else
		{
			throw new HDException("传入参数类型不正确");
		}
		return webclient;
	}

	@Override
	public AbstractWebServiceClient getServerClient(WebConfig config)
			throws HDException {
		if(null==config || (StringUtils.isEmpty(config.getUrl())))
		{
			return null;
		}
		AbstractWebServiceClient webclient=null;
		if(config.getClientType().equalsIgnoreCase(SYSConstant.CLIENTHESSION))
		{
			webclient=HessianClient.newInstance();
			webclient.setWebconfig(config);
		}
		else if (config.getClientType().equalsIgnoreCase(SYSConstant.CLIENTSERVLET))
		{
			//webclient=HttpClient.newInstance();
			//webclient.setWebconfig(config);
		}
		else
		{
			throw new HDException("传入参数类型不正确");
		}
		return webclient;
	
	}

}
