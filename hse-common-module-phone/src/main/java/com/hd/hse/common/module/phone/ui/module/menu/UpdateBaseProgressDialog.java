package com.hd.hse.common.module.phone.ui.module.menu;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.common.component.phone.dialog.MSGProgressDialog;
import com.hd.hse.dc.business.listener.basicdata.BasicDataUpdate;

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
