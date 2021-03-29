/**
 * Project Name:hse-dao
 * File Name:IProcessResultSet.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.result;

import java.sql.ResultSet;

import com.hd.hse.common.exception.DaoException;

/**
 * ClassName:IProcessResultSet ().<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public interface IProcessResultSet {
	
	/**
	 * processResultSet:(处理结果集). <br/>
	 * date: 2014年8月10日 <br/>
	 *
	 * @author lg
	 * @param rs
	 * @return
	 * @throws DaoException
	 */
	public Object processResultSet(ResultSet rs) throws DaoException;
}
