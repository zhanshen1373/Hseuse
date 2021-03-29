/**
 * Project Name:hse-main-app
 * File Name:NFCReader.java
 * Package Name:com.hd.hse.business.readcard
 * Date:2014年10月24日
 * Copyright (c) 2014, zhangjie1@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.business.readcard;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.MapResult;

import android.annotation.SuppressLint;

/**
 * ClassName:NFCReader ().<br/>
 * Date:     2014年10月24日  <br/>
 * @author   ZhangJie
 * @version  
 * @see 	 
 */
@SuppressLint("DefaultLocale")
public class NFCReader {

	public NFCReader() {
		// TODO Auto-generated constructor stub

	}
	private static Logger logger = LogUtils.getLogger(NFCReader.class);
	
	private static String  isPositived = "";//读卡反转
	
	public static boolean getIsPositived()
	{
		if(isPositived.length()>0)
		{
			return isPositived.equalsIgnoreCase("1")?true:false;
		}
		String sql = "select value,description from alndomain where domainid ='ISPOSITIVED'";
    	BaseDao dao = new BaseDao();
    	HashMap<String, Object> configMainRes;
		try {
			configMainRes = (HashMap<String, Object>) dao.executeQuery(sql,
					new MapResult());
        	if(!configMainRes.isEmpty())
        	{
        		String enable = configMainRes.get("description")==null?"0":configMainRes.get("description").toString();
        		if(enable.equalsIgnoreCase("1"))
        		{
        			isPositived = "1";
        		}else
        		{
        			isPositived = "0";
        		}
        	}
		} catch (DaoException e) {
			logger.error("获取阈值错误",e);
		}
		return isPositived.equalsIgnoreCase("1")?true:false;
	}
	
	/**
	 * readCardId:(读取卡号). <br/>
	 * date: 2014年10月27日 <br/>
	 *
	 * @author ZhangJie
	 * @param paramArrayOfByte
	 * @return
	 */
	public static String readCardId(byte[] paramArrayOfByte,boolean isReverseid)
	{
		if(getIsPositived())
		{
			return positiveId(paramArrayOfByte);
		}else
		{
			return reverseId(paramArrayOfByte);
		}
	}
	
    private static String reverseId(byte[] paramArrayOfByte)
    {
      String str = "";
      if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0));
      for (int i = paramArrayOfByte.length-1; ; i--)
      {
        if (i < 0 )
          return str.toUpperCase();
        str = str + Integer.toString(256 + (0xFF & paramArrayOfByte[i]), 16).substring(1);
      }
    }
  //字符序列转换为16进制字符串    
	private static String positiveId(byte[] src) {      
        StringBuilder stringBuilder = new StringBuilder();      
        if (src == null || src.length <= 0) {
            return null;      
        }      
        char[] buffer = new char[2];      
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);      
            stringBuilder.append(buffer);      
        }      
        return stringBuilder.toString().toUpperCase();      
    }   
    
    private static String nfcReadEnable = "";
    @SuppressWarnings("unchecked")
	public static boolean nfcReadCardEnable(){
    	if(nfcReadEnable.equalsIgnoreCase(""))
    	{
        	String sql = "select syscode,sysurl,enable from hse_sys_config where syscode in('readcarddriver')";
        	BaseDao dao = new BaseDao();
        	HashMap<String, Object> configMainRes;
			try {
				configMainRes = (HashMap<String, Object>) dao.executeQuery(sql,
						new MapResult());
	        	if(!configMainRes.isEmpty())
	        	{
	        		String enable = configMainRes.get("enable")==null?"0":configMainRes.get("enable").toString();
	        		if(enable.equalsIgnoreCase("1"))
	        		{
	        			nfcReadEnable = "1";
	        			return false;
	        		}else
	        		{
	        			nfcReadEnable = "0";
	        			return true;
	        		}
	        	}else
	        	{
	        		nfcReadEnable = "0";
	        		return true;
	        	}
				
			} catch (DaoException e) {
				logger.error("获取刷卡开关错误",e);
			}

    	}else
    	{
    		return nfcReadEnable.equalsIgnoreCase("1")?false:true;
    	}
		return true;

	}
}

