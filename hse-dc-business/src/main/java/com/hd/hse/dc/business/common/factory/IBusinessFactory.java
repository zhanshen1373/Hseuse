package com.hd.hse.dc.business.common.factory;

import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.webservice.AbstractWebServiceClient;
import com.hd.hse.dc.business.common.webservice.WebServiceClientDel;

/**
 * ClassName:IObectFactory (工厂接口).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */

public interface IBusinessFactory {
	
	/** 获得一个Object对象
	 * @throws HDException */
	public WebServiceClientDel getServerDataClient(WebConfig config) throws HDException;
	
	/** 获得一个Object对象
	 * @throws HDException */
	public AbstractWebServiceClient getServerClient(WebConfig config) throws HDException;
	
	/** 获得一个Object对象
	 * @throws HDException */
	public AbstractDataAdapter getAnalyzeDataAdapter(WebConfig config) throws HDException;
	

}
