/**
 * Project Name:hse-common
 * File Name:Log4jConfigurator.java
 * Package Name:com.hd.hse.common.logger
 * Date:2014年8月9日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.logger;

import java.io.File;

import org.apache.log4j.Level;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * ClassName: Log4jConfigurator (log4j日志配置)<br/>
 * date: 2014年8月9日 <br/>
 * 
 * @author lg
 * @version
 */
public class Log4jConfigurator {
	private static Level rootLevel = Level.ERROR;
	private static String filePattern = "%d - [%p::%c::%C] - %m%n";
	private static String logCatPattern = "%m%n";
	private static String fileName = "hse_app.log";
	private static int maxBackupSize = 5;
	private static long maxFileSize = 2048 * 1024;
	private static boolean immediateFlush = true;
	private static boolean useLogCatAppender = true;
	private static boolean useFileAppender = true;
	private static boolean resetConfiguration = true;
	private static boolean internalDebugging = false;

	/**
	 * initConfigDefault:(默认属性初始化). <br/>
	 * date: 2014年8月9日 <br/>
	 * 
	 * @author lg
	 */
	public static void configDefault() {
		LogConfigurator logConfig = new LogConfigurator();
		logConfig.setRootLevel(rootLevel);
		logConfig.setFileName(Environment.getExternalStorageDirectory()
				+ File.separator + "zyxkapp" + File.separator + fileName);
		initProperties(logConfig);
		logConfig.configure();
	}

	/**
	 * config:(指定级别、日志文件路径). <br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @param level
	 * @param logFile
	 */
	public static void config(Level level, String logFile) {
		LogConfigurator logConfig = new LogConfigurator();
		logConfig.setRootLevel(level);
		logConfig.setFileName(logFile);
		initProperties(logConfig);
		logConfig.configure();
	}
	
	private static void initProperties(LogConfigurator logConfig){
		logConfig.setFilePattern(filePattern);
		logConfig.setLogCatPattern(logCatPattern);
		logConfig.setMaxBackupSize(maxBackupSize);
		logConfig.setMaxFileSize(maxFileSize);
		logConfig.setImmediateFlush(immediateFlush);
		logConfig.setUseLogCatAppender(useLogCatAppender);
		logConfig.setUseFileAppender(useFileAppender);
		logConfig.setResetConfiguration(resetConfiguration);
		logConfig.setInternalDebugging(internalDebugging);
	}

}
