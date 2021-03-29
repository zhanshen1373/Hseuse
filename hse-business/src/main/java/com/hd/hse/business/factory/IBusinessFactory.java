package com.hd.hse.business.factory;

import com.hd.hse.business.util.AbstractDataAdapter;
import com.hd.hse.business.webservice.WebServiceClient;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;

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
	public WebServiceClient getServerDataClient(WebConfig config) throws HDException;
	
	/** 获得一个Object对象
	 * @throws HDException */
	public AbstractDataAdapter getAnalyzeDataAdapter(WebConfig config) throws HDException;
	

}
