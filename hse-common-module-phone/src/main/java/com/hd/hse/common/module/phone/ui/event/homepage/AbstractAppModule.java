/**
 * Project Name:hse-main-app
 * File Name:AbstractAppModule.java
 * Package Name:com.hd.hse.main.ui.event.homepage
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.event.homepage;

import android.content.Context;
import android.media.audiofx.BassBoost;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

/**
 * ClassName:AbstractAppModule (功能模块事件处理).<br/>
 * Date: 2014年9月23日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public abstract class AbstractAppModule implements IAppModuleClick {

	/**
	 * parent:TODO(父窗体).
	 */
	private Context context;

	/**
	 * isSwCard:TODO(是否刷卡).
	 */
	public static boolean isSwCard = true;
	
	public AbstractAppModule() {

	}

	public AbstractAppModule(Context context) {
		this.context = context;
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.main.ui.event.homepage.IAppModuleClick#appModuleOnClick()
	 */
	public void appModuleOnClick(AppModule aModule) throws HDException {
		// TODO Auto-generated method stub
		// if(LocationSwCard.isReUseLocation)
		// return ;
		// 但凡点击功能模块，则取消位置卡定时器
		// if(LocationSwCard.mTimer != null){
		// LocationSwCard.mTimer.cancel();
		// }
		// 校验当前模块是否是需要刷卡
		if (aModule.getIsswcard() == 0) {
			isSwCard = false;
		}else{
			isSwCard = true;
		}
		SystemApplication.getInstance().popActivityBesidesClassz();
	}

	public Context getParent() {
		return context;
	}

	/**
	 * isRelation:(判断是否有关系). <br/>
	 * date: 2015年3月26日 <br/>
	 * 
	 * @author zhaofeng
	 * @param sys_type 关系编码
	 * @return
	 */
	public boolean isRelation(String sys_type) {
		IQueryRelativeConfig config = new QueryRelativeConfig();
		return config.isHadRelative(sys_type);
	}

}
