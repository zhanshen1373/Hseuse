/**
 * Project Name:hse-main-app
 * File Name:IAppModuleClick.java
 * Package Name:com.hd.hse.main.ui.event
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.event.homepage;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.sys.AppModule;

/**
 * ClassName:IAppModuleClick (功能模块按钮点击事件处理).<br/>
 * Date: 2014年9月23日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IAppModuleClick {
	
	/**
	 * appModuleOnClick:(单击功能模块图标事件). <br/>
	 * date: 2014年9月23日 <br/>
	 *
	 * @author lg
	 * @throws HDException
	 */
	public void appModuleOnClick(AppModule aModule) throws HDException;
}
