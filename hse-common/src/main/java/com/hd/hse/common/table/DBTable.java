/**
 * Project Name:hse-common
 * File Name:DBTable.java
 * Package Name:com.hd.hse.common.table
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.table;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
/**
 * ClassName:DBTable (数据库表注解).<br/>
 * Date:     2014年8月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
	/**
	 * tableName:(数据库表名). <br/>
	 * date: 2014年8月11日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	String tableName() default "";
}

