/**
 * Project Name:hse-entity-service
 * File Name:PositionQuerier.java
 * Package Name:com.hd.hse.service.card
 * Date:2014年10月15日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.service.card;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.common.PositionCard;

/**
 * ClassName:PositionQuerier (位置卡查询服务).<br/>
 * Date: 2014年10月15日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class PositionQuerier extends CardQuerier {
	private static Logger logger = LogUtils.getLogger(PositionQuerier.class);

	@Override
	protected Class<?> getMainEntityClass() {
		// TODO Auto-generated method stub
		return PositionCard.class;
	}

	@Override
	protected String getCardNumField() {
		// TODO Auto-generated method stub
		return "locationcardid";
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	protected String[] getMainEntityCols() {
		// TODO Auto-generated method stub
		return new String[] { "ud_zyxk_wzkid", "location", "description",
				"locationcardid", "location_desc", "isVirtualCard","txtime",
				"ifnull(longitude,'0') as longitude",
				"ifnull(latitude,'0') as latitude", "ifnull(radiu,0) as radiu" };
	}

}
