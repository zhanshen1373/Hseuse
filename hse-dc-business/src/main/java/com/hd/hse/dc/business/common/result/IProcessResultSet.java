package com.hd.hse.dc.business.common.result;
import com.hd.hse.common.exception.HDException;

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
	public Object processResultSet(Object rs) throws HDException;
}
