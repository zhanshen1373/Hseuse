package com.hd.hse.dc.business.common.result;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName: BaseWebResult (基类)<br/>
 * date: 2015年3月20日  <br/>
 *
 * @author lxf
 * @version 
 */
public abstract class BaseWebResult implements IProcessResultSet {

	@Override
	public Object processResultSet(Object rs) throws HDException {
		// TODO Auto-generated method stub
		if (rs == null) {
			throw new HDException("结果集不允许为空！");
		}
		return changeResultType(rs);

	}

	/**
	 * changeResultType:(结果集类型转换). <br/>
	 * date: 2014年8月10日 <br/>
	 * 
	 * @author lg
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public abstract Object changeResultType(Object rs) throws HDException;
}
