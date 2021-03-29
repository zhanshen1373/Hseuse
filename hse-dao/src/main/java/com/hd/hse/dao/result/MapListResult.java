/**
 * Project Name:hse-dao
 * File Name:MapListResult.java
 * Package Name:com.hd.hse.dao.result
 * Date:2014年8月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dao.result;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:MapListResult (结果集转换成hashmap集).<br/>
 * Date: 2014年8月11日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class MapListResult extends BaseResult {

	@Override
	public Object changeResultType(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return ProcessorUtils.toMapList(rs);
	}

}
