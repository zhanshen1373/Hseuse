/**
 * Project Name:hse-entity-service
 * File Name:PersonQuerier.java
 * Package Name:com.hd.hse.service.card
 * Date:2014年10月15日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.service.card;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.common.PersonCard;

/**
 * ClassName:PersonQuerier (厂内人员查询服务，根据personid查询).<br/>
 * Date: 2014年10月15日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class PersonQuerier extends CardQuerier {

	private static Logger logger = LogUtils.getLogger(PersonQuerier.class);

	@Override
	protected Class<?> getMainEntityClass() {
		// TODO Auto-generated method stub
		return PersonCard.class;
	}

	@Override
	protected String getCardNumField() {
		// TODO Auto-generated method stub
		return "personid";
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

}
