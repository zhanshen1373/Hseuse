/**
 * Project Name:hse-dao
 * File Name:ListListResult.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年10月18日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.dao.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: ListListResult ()<br/>
 * date: 2014年10月18日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class ListListResult extends BaseResult {

	private Class<?> clazz;
	private String column;
	public ListListResult(Class<?> clazz,String column){
		this.clazz = clazz;
		this.column = column;
	}
	/**
	 * TODO 
	 * @see BaseResult#changeResultType(ResultSet)
	 */
	@Override
	public Object changeResultType(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return ProcessorUtils.toListListEntity(rs, clazz, column);
	}

}
