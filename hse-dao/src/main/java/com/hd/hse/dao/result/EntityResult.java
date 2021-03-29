/**
 * Project Name:hse-dao
 * File Name:EntityRsult.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年8月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:EntityRsult (结果集转换成指定实体).<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class EntityResult extends BaseResult {

	private Class<?> clazz;

	public EntityResult(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Object changeResultType(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return ProcessorUtils.toEntity(rs, clazz);
	}

}
