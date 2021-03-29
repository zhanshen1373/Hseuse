package com.hd.hse.wov.business.workorder;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.service.workorder.WorkTaskDBSrv;

public class WorkTaskSrv extends WorkTaskDBSrv {
	@Override
	protected String getWorkTaskExtWhere() throws HDException {
		// TODO Auto-generated method stub
		return super.getWorkTaskExtWhere();
	}

	@Override
	protected String getWorkOrderExtWhere() throws HDException {
		// TODO Auto-generated method stub
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("zysq.status <> '").append(IWorkOrderStatus.WAPPR)
				.append("'");// 非草稿
		return sbWhere.toString();
	}
}
