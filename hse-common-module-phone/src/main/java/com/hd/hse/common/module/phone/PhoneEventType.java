/**
 * Project Name:hse-common-component-phone
 * File Name:PhoneEventType.java
 * Package Name:com.hd.hse.common.component.event
 * Date:2014年12月30日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone;
/**
 * ClassName:PhoneEventType ().<br/>
 * Date:     2014年12月30日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public interface PhoneEventType {
	/**
	 * 作业票列表，关闭作业票事件
	 */
	final static int ZYPLIST_WORKORDER_CLOSE = 600;
	
	/**
	 * 作业票列表，取消作业票事件
	 */
	final static int ZYPLIST_WORKORDER_CANCLE = 602;
	
	/**
	 * 作业票列表，作业任务详情
	 */
	final static int ZYPLIST_WORKTASK_DETAIL = 603;
	
	/**
	 * 作业票列表，WorkOrder长按。
	 */
	final static int ZYPLIST_ITEM_LONG_CLICK = 604;
	
	/**
	 * 作业票列表，WorkOrder点击。
	 */
	final static int ZYPLIST_ITEM_CLICK = 605;
	
	/**
	 * ZYPLIST_ICON_CLICK:TODO(作业ICON点击事件).
	 */
	final static int ZYPLIST_ICON_CLICK=606;
	
	
}

