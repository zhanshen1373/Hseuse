/**
 * Project Name:hse-entity-service
 * File Name:ICheckApproveRight.java
 * Package Name:com.hd.hse.service.checkright
 * Date:2014年10月13日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.checkright;

import android.content.Context;
import android.os.Handler;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName: ICheckApproveRight ()<br/>
 * date: 2014年10月13日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public interface ICheckApproveRight {

	/**
	 * checkApproveRight:(). <br/>
	 * date: 2014年10月13日 <br/>
	 *
	 * @author zhaofeng
	 * @param nodeEntity 环节点信息
	 * @param personCard 人员信息
	 * @param workEntity 作业票信息
	 * @return
	 * @throws HDException 
	 */
	public void checkApproveRight(Context context, Handler handler, SuperEntity nodeEntity, SuperEntity personCard, SuperEntity workEntity) throws HDException;
}
