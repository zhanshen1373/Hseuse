package com.hd.hse.common.component.phone.dialog;

import java.util.List;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.web.zyxk.UpOnlineZYXKInfoNew;
import com.hd.hse.dc.business.web.zyxk.UpZYXKInfoNew1;
import com.hd.hse.entity.workorder.WorkOrder;

import android.content.Context;

/**
 * 上传在线作业票（作业符合上床）
 * 
 * @author yn
 * 
 */
public class UpOnlineZypProgressDialog extends MSGProgressDialog {
	private Context context;
	private List<WorkOrder> workOrderList;

	public UpOnlineZypProgressDialog(Context context,
			List<WorkOrder> workOrderList) {
		super(context);
		this.context = context;
		this.workOrderList = workOrderList;
	}
	
	
	public void setWorkOrderList(List<WorkOrder> workOrderList){
	    this.workOrderList=workOrderList;
	  }

	@Override
	public AbstractActionListener getActionListner() {
		// TODO Auto-generated method stub
		super.getActionListner();
		UpOnlineZYXKInfoNew zyxkinfo = new UpOnlineZYXKInfoNew(context,
				workOrderList);
		return zyxkinfo;
	}

}
