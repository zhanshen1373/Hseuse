/**
 * Project Name:hse-common
 * File Name:LogUtils.java
 * Package Name:com.hd.hse.common.logger
 * Date:2014年8月9日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.logger;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * ClassName:LogUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 日志文件输出到sd卡，对应app的AndroidManifest.xml文件中设置可写权限<uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" />. <br/>
 * Date: 2014年8月9日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class LogUtils {

	/**
	 * initConfigDefault:(初始化日志属性). <br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 */
	public static void initConfigDefault() {
		Log4jConfigurator.configDefault();
	}

	/**
	 * initConfig:(指定日志级别、日志文件路径). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param level级别
	 * @param logFile文件
	 */
	public static void initConfig(Level level, String logFile) {
		Log4jConfigurator.config(level, logFile);
	}

	/**
	 * getLogger:(). <br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 * @param clazz
	 * @return
	 */
    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz);
    }
}
