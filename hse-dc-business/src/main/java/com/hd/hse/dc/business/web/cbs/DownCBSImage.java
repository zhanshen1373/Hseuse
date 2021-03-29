package com.hd.hse.dc.business.web.cbs;


import java.io.File;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.DownFilelistener;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.system.SystemProperty;

public class DownCBSImage extends DownFilelistener {

	private static Logger logger= LogUtils.getLogger(DownCBSImage.class);
	private String downUrl;
	
	@Override
	public Object action(String action, Object... args) throws HDException {
		
		
		String persionid=null;
		try
		{
		if(args[0] instanceof String)
		{
			persionid=args[0].toString();
		}
		if(StringUtils.isEmpty(persionid))
		{
			throw new HDException("传入承包商persionid不能为空!");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(PadInterfaceRequest.KEYPERSONID, persionid);
		//表示获取服务器的访问信息
		client=getServiceClient();
		Object obj=this.client.getPeoplePhotoUrl(map);
		//Object obj="http://192.168.6.11:7001/maximo/upload/appcbsry/中油一建/131024199401012021.jpg";
		if (obj instanceof String) {
			//此时取出下载的URL
			downUrl=obj.toString();
			//读取条件读取用户照片
			String savepath=getSavePath();
			//表示下载保存照片信息
			setDownFile(downUrl, savepath);
		} else {
			if (obj instanceof PadInterfaceResponse) {
				PadInterfaceResponse pad = (PadInterfaceResponse) obj;
				throw new HDException("图片不存在，请联系管理员!");
			}
			else
			{
				throw new HDException("下载服务器照片报错!");
			}
		}
		this.SendMessage(IMessageWhat.END, 99, 100,"下载成功!");
		}
		catch(HDException e)
		{
			this.setWritelog("下载承包商上人员照片失败!", e);
			this.SendMessage(IMessageWhat.ERROR, 9, 9,e.getMessage().indexOf("超时") > 0 ? e.getMessage(): "下载失败,请联系管理员!");
		}
		
		 
		 return null;
	}
	
	@Override
	public String getDownUrl() {
		
		return downUrl;
	}

	@Override
	public String getSavePath() {
		return SystemProperty.getSystemProperty().getRootImagePath()+File.separator+"personphoto";
	}

	@Override
	public WebConfig getWebConfigParam() {
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

	@Override
	public Object initParam() {
	/*	HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(SYSConstant.KEYPERSONID, SystemProperty.getSystemProperty().getLoginPerson().getPersonid());
		//hashmap.put(SYSConstant.KEYDEPTNUM, SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
		String dept=SystemProperty.getSystemProperty().getLoginPerson().getDepartment();
		if(!StringUtils.isEmpty(dept))
		{
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM,dept);
		}
		*/
		return null;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}
	

}
