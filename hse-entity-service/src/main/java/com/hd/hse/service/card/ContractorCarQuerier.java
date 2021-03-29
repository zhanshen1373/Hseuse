/**
 * Project Name:hse-cbs-app
 * File Name:ContractorCarQuerier.java
 * Package Name:com.hd.hse.business.readcard
 * Date:2014年9月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.service.card;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.contractor.ContractorCar;

/**
 * ClassName:ContractorCarQuerier (承包商车辆查询).<br/>
 * Date:     2014年9月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class ContractorCarQuerier extends CardQuerier {
	
	private static Logger logger = LogUtils.getLogger(ContractorCarQuerier.class);

	@Override
	protected Class<?> getMainEntityClass() {
		// TODO Auto-generated method stub
		return ContractorCar.class;
	}

	@Override
	protected String getCardNumField() {
		// TODO Auto-generated method stub
		return "mjknum";
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
	
}

