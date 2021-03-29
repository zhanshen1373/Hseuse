package com.hd.hse.dc.phone.ui.activity.updata;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.listener.basicdata.BasicDataUpdate;
import com.hd.hse.dc.phone.ui.common.progressdialog.MSGProgressDialog;

public class UpdateBaseProgressDialog extends MSGProgressDialog {

	public UpdateBaseProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public AbstractActionListener getActionListner() {
		// TODO Auto-generated method stub
		 super.getActionListner();
		 BasicDataUpdate basicupdate = new BasicDataUpdate();
		 return basicupdate;
	}
	
}
