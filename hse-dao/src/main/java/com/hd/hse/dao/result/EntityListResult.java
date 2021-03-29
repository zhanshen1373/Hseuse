/**
 * Project Name:hse-dao
 * File Name:EntityListResult.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.dao.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:EntityListResult (结果集转换成实体集).<br/>
 * Date:     2014年8月11日  <br/>
 * @author   lg
 * @version  
 * @see 	 
 */
public class EntityListResult extends BaseResult {
	
	private Class<?> clazz;
	
	public EntityListResult(Class<?> clazz){
		this.clazz = clazz;
	}

	@Override
	public Object changeResultType(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return ProcessorUtils.toEntityList(rs, clazz);
	}

}

