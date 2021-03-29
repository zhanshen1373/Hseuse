package com.hd.hse.business.webservice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.AndroidInterface;
import com.hd.hse.padinterface.PadInterfaceContainers;

/**
 * ClassName:WebHessian (支持Hessian通信).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 **/
public class HessainClient extends WebServiceClient {

	private static Logger logger = LogUtils.getLogger(HessainClient.class);

	private static HessianProxyFactory factory = null;

	private AndroidInterface basic;

	/*
	 * COMMON_001 -- 通用：下载表信息 ok COMMON_002 -- 通用：下载文件 COMMON_003 -- 通用：上传文件
	 * COMMON_004 -- 通用：上传表信息
	 * 
	 * CBS_002 -- 承包商人员及特种作业证 CBS_003 -- 承包商入厂车辆及驾驶员
	 */
	public static HessainClient newInstance() {
		return new HessainClient();
	}

	private HessainClient() {
		// initAttribut();
	}

	/**
	 * initAttribut:初始化属性值
	 * */
	private void initAttribut() {
		if (factory == null) {
			factory = new HessianProxyFactory();
			factory.setConnectTimeout(8000);
		}
		try {
			factory.setDebug(false);
			factory.setReadTimeout(this.getWebconfig().getTimeOut());
			// 不设置会报 expected hessian reply at 0x48
//			factory.setHessian2Reply(false);
			
			basic = (AndroidInterface) factory.create(AndroidInterface.class,
					this.getWebconfig().getUrl());
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 初始化基础数据
	 * 
	 * @throws HDException
	 * */
	@Override
	public Object InitBaseInfo(HashMap<String, Object> map) throws HDException {
		return getRequestObject(map,PadInterfaceContainers.METHOD_COMMON_DOWNTABLE, null, null);
	}

	/**
	 * 更新基础数据
	 * 
	 * @throws HDException
	 * */
	@Override
	public Object UpdateBaseInfo(HashMap<String, Object> map)
			throws HDException {
		return InitBaseInfo(map);
	}
	
	/**
	 * TODO 
	 * @see com.hd.hse.business.webservice.WebServiceClient#DownLoadBuesinessList(HashMap, String)
	 */
	@Override
	public Object DownLoadBuesinessList(HashMap<String, Object> map, String type)
			throws HDException {
		return getRequestObject(map, type, null, null);
	}

	/**
	 * 表示下载业务数据
	 * 
	 * @throws HDException
	 * */
	public Object DownLoadBusiness(HashMap<String, Object> map, String type)
			throws HDException {
		return getRequestObject(map, type, null, null);
	}

	/**
	 * 表示业务数据上传
	 * 
	 * @throws HDException
	 * */
	@Override
	public Object UpLoadBusiness(HashMap<String, Object> map)
			throws HDException {
		return getRequestObject(map,PadInterfaceContainers.METHOD_COMMON_UPTABLE, null, null);
	}

	@Override
	public void DownLoadFile(HashMap<String, Object> map) {

		/*
		 * String url = "http://192.168.6.11:7001/maximo/exchangeData.do";
		 * HessianProxyFactory factory = new HessianProxyFactory(); try {
		 * factory.setDebug(false); factory.setReadTimeout(500000);
		 * AndroidInterface basic =
		 * (AndroidInterface)factory.create(AndroidInterface.class, url);
		 * Map<String,Object> map = new HashMap<String,Object>(); String
		 * filename = "Visual.Basic+2010入门经典（第6版）+高清完整版+%2B+源代码.zip";
		 * map.put("appname", "testapp"); map.put("uniqueid", "99100");
		 * map.put("filename", filename);
		 * 
		 * Object size = basic.exchange("COMMON_101", map,null,null);
		 * if(Long.class.isInstance(size)) { return
		 * Long.parseLong(size.toString()); } } catch (Exception e) {
		 * e.printStackTrace(); } return 0l;
		 */

	}

	@Override
	public Object UpLoadFile(String path, HashMap<String, Object> map)
			throws Exception {
		try {
			InputStream data = new BufferedInputStream(
					new FileInputStream(path));
			return getRequestObject(map, PadInterfaceContainers.METHOD_COMMON_UPFILE, null, data);
		} catch (Exception e) {
			logger.error("上传图片报错" + e.getMessage());
			throw e;
		}
	}

	/**
	 * getRequestObject:(). <br/>
	 * date: 2014年9月3日 <br/>
	 * 
	 * @author Administrator
	 * @param map
	 * @param type
	 * @param bt
	 * @param input
	 * @return
	 * @throws HDException
	 */
	private Object getRequestObject(HashMap<String, Object> map, String type,
			byte[] bt, InputStream input) throws HDException {
		Object retobject = null;
		try {
			initAttribut();
			if (null != basic) {
				retobject = basic.exchange(type, map, bt, input);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new HDException("网络异常，请确认！");
		}
		return retobject;

	}

	@Override
	public Object checkUser(HashMap<String, Object> map) throws HDException {
		return getRequestObject(map, PadInterfaceContainers.METHOD_COMMON_CHECKUSER, null, null);
	}

	@Override
	public Object getVersionInfo(HashMap<String, Object> map)
			throws HDException {
		return getRequestObject(map,PadInterfaceContainers.METHOD_COMMON_VERSIONIFNO, null, null);
	}

	@Override
	public Object getPeoplePhotoUrl(HashMap<String, Object> map)
			throws HDException {
		return getRequestObject(map,PadInterfaceContainers.METHOD_CBS_CBSHEADIAMGEURL, null, null);
	}

	@Override
	public Object getPCDateInfo(String action, HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnect() {
		boolean ret=false;
		try {
			String ip=null;
			try {
				 ip=getIp(this.getWebconfig().getUrl());
			} catch (HDException e) {
				logger.error("获取ip报错", e);
			}
			if(StringUtils.isEmpty(ip))
			{
				return false;
			}
			Process p = Runtime.getRuntime().exec(
					"/system/bin/ping -c " + 2 + " " + ip);// 10.83.50.111
																		// m_strForNetAddress
			int status = p.waitFor();
			if (status == 0) {
				ret=true;
			} 
		} catch (IOException e) {
			logger.error("判断网络是否连接报错", e);
		} catch (InterruptedException e) {
			logger.error("判断网络是否连接报错", e);
		}
		return ret;
	}

	private String getIp(String url) {
		String ip;
		String[] str = url.replace("http://", "").split("/");
		ip = str[0].split(":")[0];
		return ip;

	}

	@Override
	public Object ReturnPCInfo(String action, HashMap<String, Object> map)
			throws HDException {
		return getRequestObject(map, action, null, null);
	}

	@Override
	public Object DoServletMethod(String action, HashMap<String, Object> map)
			throws HDException {
		// TODO Auto-generated method stub
		return getRequestObject(map, action, null, null);
	}



}
