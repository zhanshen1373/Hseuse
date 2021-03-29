package com.hd.hse.vp.queryinfo;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import java.util.List;

/**
 * ClassName: QueryVpInfo (查询位置卡信息)<br/>
 * date: 2015年9月1日  <br/>
 *
 * @author lxf
 * @version 
 */
public class QueryVpInfo {
	IQueryWorkInfo queryInfo;

	public QueryVpInfo() {
		queryInfo = new QueryWorkInfo();
	}

	public QueryVpInfo(IConnection connection) {
		queryInfo = new QueryWorkInfo(connection);
	}
	
	/**
	 * queryListPositionCard:(查询位置卡信息). <br/>
	 * date: 2015年9月1日 <br/>
	 *
	 * @author lxf
	 * @param callEventListener
	 * @return
	 * @throws HDException
	 */
	public List<PositionCard> queryListPositionCard(IQueryCallEventListener callEventListener) throws HDException{
		return queryInfo.queryVirtualCards(callEventListener);
	}
}
