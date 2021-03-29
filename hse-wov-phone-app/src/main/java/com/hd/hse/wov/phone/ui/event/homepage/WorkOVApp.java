/**
 * Project Name:hse-main-app
 * File Name:OnSiteChkApp.java
 * Package Name:com.hd.hse.main.ui.event.homepage
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.wov.phone.ui.event.homepage;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.wov.phone.ui.activity.worktask.TaskTabulationActivity;

/**
 * ClassName: WorkOVApp (作业票浏览APP)<br/>
 * date: 2015年1月20日 <br/>
 * 
 * @author lych
 * @version
 */
public class WorkOVApp extends AbstractAppModule {

	
	private PersonCard loginPerson;
	
	public WorkOVApp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		
		//从系统属性类得到数据
        String flag=SystemProperty.getSystemProperty().getFlag();
		// 跳转至作业票浏览列表
		Intent intent = new Intent(getParent(), TaskTabulationActivity.class);
		intent.putExtra("flag", flag);
		getParent().startActivity(intent);
	}
	

}
