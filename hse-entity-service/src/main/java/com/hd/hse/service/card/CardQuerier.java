/**
 * Project Name:hse-cbs-app
 * File Name:CardQuerier.java
 * Package Name:com.hd.hse.business.readcard
 * Date:2014年9月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.service.card;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName:CardQuerier (根据卡号查询对应实体).<br/>
 * Date: 2014年9月11日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public abstract class CardQuerier {

	/**
	 * query:(根据卡号查询). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @param cardNum
	 * @return
	 */
	public SuperEntity query(String cardNum) throws HDException {
		BusinessAction action = new BusinessAction();
		SuperEntity mainEntity = null;
		String where = buildWhere(cardNum);
		try {
			if (!isLoadChilds()) {
				mainEntity = action.queryEntity(getMainEntityClass(),
						getMainEntityCols(), where);
			} else {
				mainEntity = action.queryEntityLoadChilds(getMainEntityClass(),
						getMainEntityCols(), where);
			}
		} catch (HDException e) {
			// TODO Auto-generated catch block
			getLogger().error(e.getMessage(), e);
			throw e;
		}
		return mainEntity;
	}

	/**
	 * buildWhere:(构造where语句). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @param cardNum
	 * @return
	 */
	protected String buildWhere(String cardNum) {
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append(getCardNumField()).append("='").append(cardNum)
				.append("'");
		String extWhere = getExtendWhere();
		if (!StringUtils.isEmpty(extWhere)) {
			sbWhere.append(" and (").append(extWhere).append(")");
		}
		return sbWhere.toString();
	}

	/**
	 * getMainEntityClass:(待查询实体类型). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @return
	 */
	protected abstract Class<?> getMainEntityClass();

	/**
	 * getCardNumField:(卡号对应数据库字段). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @return
	 */
	protected abstract String getCardNumField();

	/**
	 * getMainEntityCols:(待查询列). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @return
	 */
	protected String[] getMainEntityCols() {
		return null;
	}

	/**
	 * getExtendWhere:(扩展条件). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @return
	 */
	protected String getExtendWhere() {
		return null;
	}

	/**
	 * isLoadChilds:(是否需要加载子表数据). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lg
	 * @return
	 */
	protected boolean isLoadChilds() {
		return false;
	}

	/**
	 * getLogger:(). <br/>
	 * date: 2014年9月12日 <br/>
	 * 
	 * @author lg
	 */
	protected abstract Logger getLogger();

}
