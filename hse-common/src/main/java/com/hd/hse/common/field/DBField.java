/**
 * Project Name:hse-common
 * File Name:DBField.java
 * Package Name:com.hd.hse.common.field
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:DBField (数据库字段注解).<br/>
 * Date:     2014年8月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBField {
	/**
	 * id:(是否主键). <br/>
	 * date: 2014年8月11日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	boolean id() default false;
	
	/**
	 * foreign:(是否外键). <br/>
	 * date: 2014年8月11日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	boolean foreign() default false;
	
}

