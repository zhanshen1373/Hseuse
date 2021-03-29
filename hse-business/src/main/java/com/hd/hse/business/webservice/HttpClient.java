package com.hd.hse.business.webservice;

import java.util.HashMap;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName:WebServlet (支持servlet通信).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 **/
public class HttpClient extends WebServiceClient {



	public static HttpClient newInstance() {

		return new HttpClient();

	}

	private HttpClient() {
	
	}

	@Override
	public Object InitBaseInfo(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object UpdateBaseInfo(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object DownLoadBusiness(HashMap<String, Object> map,
			String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object UpLoadBusiness(HashMap<String, Object> map) {
		return null;
	}



	@Override
	public void DownLoadFile(HashMap<String, Object> map) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object UpLoadFile(String path,HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object checkUser(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getVersionInfo(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPeoplePhotoUrl(HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPCDateInfo(String action, HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object DownLoadBuesinessList(HashMap<String, Object> map, String type)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object ReturnPCInfo(String action, HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object DoServletMethod(String action, HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

}
