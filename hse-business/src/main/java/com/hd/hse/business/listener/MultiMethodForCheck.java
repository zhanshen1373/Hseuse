/**
 * Project Name:hse-business
 * File Name:MultiMethodForCheck.java
 * Package Name:com.hd.hse.business.listener
 * Date:2014年10月20日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.business.listener;

import java.util.HashMap;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName:MultiMethodForCheck (需要在服务气短进行校验的入口类).<br/>
 * Date:     2014年10月20日  <br/>
 * @author   ZhangJie
 * @version  
 * @see 	 
 */
public abstract class MultiMethodForCheck extends AbstractWebListener {

	public MultiMethodForCheck() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object action(String action, Object... args) throws HDException {
		Object obj=null;
		try {
			super.action(action, args);
			HashMap<String, Object> hasmap = (HashMap<String, Object>)args[0];
			obj = client.DoServletMethod(action, hasmap);
			PadInterfaceResponse response = null;
			if (PadInterfaceResponse.class.isInstance(obj)) {
				response = (PadInterfaceResponse) obj;
				setWritelog("pc报错："+response.getExceptionmsg());
				throw new HDException("pc端验证失败,请联系管理员");
			}
		} catch (HDException e) {
			setWritelog("验证失败：" + e.getMessage());
			throw  e;
		}
		return obj;
	}
}

