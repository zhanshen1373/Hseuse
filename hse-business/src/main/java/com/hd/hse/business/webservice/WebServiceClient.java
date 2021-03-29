package com.hd.hse.business.webservice;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
 

import com.hd.hse.common.exception.HDException;

/**
 * ClassName:AbstractWebReport (web通讯抽象类).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 **/
public abstract class WebServiceClient {
	
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
		//if(outtime>60000)
		{
			config.setTimeOut(outtime);
		}
		//config.setTimeOut(1000);
		return config;
	}
	
	
	/**
	 * checkUser:(用户验证). <br/>
	 * date: 2014年9月4日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException 
	 */
	public abstract Object checkUser(HashMap<String,Object> map) throws HDException;
	
	
	/**
	 * initBase:(初始化) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param opcode
	 *            人员登录编码
	 * @param depcode
	 *            人员部门
	 * @return 字符串
	 * */
	public abstract Object InitBaseInfo(HashMap<String,Object> map) throws HDException ;

	/**
	 * downLoadBase:(更新基础数据) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param opcode
	 *            人员登录编码
	 * @param depcode
	 *            人员部门
	 * @return 字符串
	 * */
	public abstract Object UpdateBaseInfo(HashMap<String,Object> map)throws HDException;

	
	/**
	 * DownLoadBuesinessList:(得到下载业务数据列表). <br/>
	 * date: 2014年9月24日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @param type
	 * @return
	 * @throws HDException
	 */
	public abstract Object DownLoadBuesinessList(HashMap<String,Object> map,String type)throws HDException;
	
	
	/**
	 * downLoadBusiness:(下载业务数据) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param strid
	 *            主键
	 * @return 字符串
	 * @throws HDException 
	 * */
	public abstract Object DownLoadBusiness(HashMap<String,Object> map,String type) throws HDException;

	/**
	 * upLoadBusiness:(上传业务数据) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param strvalue
	 *            上传数据 Json格式
	 * @return 字符串 是成功或异常信息
	 * @throws HDException 
	 * */
	public abstract Object UpLoadBusiness(HashMap<String,Object> map) throws HDException;



	/**
	 * downLoadFile:(下载文件) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param savepath
	 *            保存路径+文件名
	 * @param url
	 *            请求路径
	 * @return 字符串 是成功或异常信息
	 * */
	public abstract void DownLoadFile(HashMap<String, Object> map);



	/**
	 * upLoadFile:(上传文件) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param uppath
	 *            上传路径
	 * @return 字符串 是成功或异常信息
	 * @throws Exception 
	 * */
	public abstract Object UpLoadFile(String path,HashMap<String, Object> map) throws Exception;

	/**
	 * getVersionInfo:(获取版本信息). <br/>
	 * date: 2014年9月9日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException 
	 */
	public abstract Object getVersionInfo(HashMap<String, Object> map) throws HDException;

	/**
	 * getPeoplePhotoUrl:(获取人的照片url). <br/>
	 * date: 2014年9月13日 <br/>
	 *
	 * @author lxf
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public abstract Object getPeoplePhotoUrl(HashMap<String, Object> map) throws HDException;
	/**
	 * getPCDateInfo:(获取服务器返回的对象). <br/>
	 * date: 2014年9月13日 <br/>
	 *
	 * @author lxf
	 * @param action
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public abstract Object getPCDateInfo(String action,HashMap<String, Object> map) throws HDException;
	
	/**
	 * isConnect:(是否连接). <br/>
	 * date: 2014年9月13日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public abstract boolean isConnect();
	/**
	 * ReturnPCInfo:(操作成功，返回PC端信息). <br/>
	 * date: 2014年10月9日 <br/>
	 *
	 * @author lxf
	 * @return
	 * @throws HDException
	 */
	public abstract Object ReturnPCInfo(String action,HashMap<String, Object> map)throws HDException;
	
	/**
	 * DoServletMethod:(通过动作编码，调用web接口服务方法). <br/>
	 * date: 2014年10月20日 <br/>
	 * @author ZhangJie
	 * @param action
	 * @param map
	 * @return
	 * @throws HDException
	 */
	public abstract Object DoServletMethod(String action,HashMap<String,Object> map) throws HDException;
	
}
