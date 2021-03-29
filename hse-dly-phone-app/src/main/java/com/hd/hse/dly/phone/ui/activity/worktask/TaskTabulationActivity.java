package com.hd.hse.dly.phone.ui.activity.worktask;

import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dly.business.workorder.WorkTaskSrv;
import com.hd.hse.dly.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.workorder.WorkTaskDBSrv;

public class TaskTabulationActivity extends BaseListBusActivity {
	@Override
	public WorkTaskDBSrv getWorkTaskObject() {
		// TODO Auto-generated method stub
		return new WorkTaskSrv();
	}

	@Override
	public String getTitileName() {
		// TODO Auto-generated method stub
		return "延期操作";
	}

	@Override
	public String getNavCurrentKey() {
		// TODO Auto-generated method stub
		return "hse-dly-phone-app";
	}

	@Override
	public Class<?> getToActivityClass() {
		// TODO Auto-generated method stub
		return WorkOrderActivity.class;
	}

	@Override
	public String getFunctionCode() {
		// TODO Auto-generated method stub
		return IConfigEncoding.YQ;
	}
}
