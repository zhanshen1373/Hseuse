/**
 * Project Name:hse-cbs-app
 * File Name:ContractorPersonQuerier.java
 * Package Name:com.hd.hse.business.readcard
 * Date:2014年9月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.service.card;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.common.PersonCard;

/**
 * ClassName:ContractorPersonQuerier (承包商人员查询).<br/>
 * Date:     2014年9月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class ContractorPersonQuerier extends CardQuerier {
	
	private static Logger logger = LogUtils.getLogger(ContractorPersonQuerier.class);

	@Override
	protected Class<?> getMainEntityClass() {
		// TODO Auto-generated method stub
		return PersonCard.class;
	}

	@Override
	protected String getCardNumField() {
		// TODO Auto-generated method stub
		return "pcardnum";
	}

	@Override
	protected boolean isLoadChilds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	/**
	 * TODO 只查询承包商人员
	 * @see com.hd.hse.business.readcard.CardQuerier#getExtendWhere()
	 */
	@Override
	protected String getExtendWhere() {
		// TODO Auto-generated method stub
		return "iscbs=1";
	}

}

