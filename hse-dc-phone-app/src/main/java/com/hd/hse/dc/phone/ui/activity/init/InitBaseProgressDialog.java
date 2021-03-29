package com.hd.hse.dc.phone.ui.activity.init;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.listener.basicdata.BasicDataInit;
import com.hd.hse.dc.phone.ui.common.progressdialog.MSGProgressDialog;

public class InitBaseProgressDialog extends MSGProgressDialog {

	public InitBaseProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AbstractActionListener getActionListner() {
		// TODO Auto-generated method stub
		super.getActionListner();
		BasicDataInit basicinit = new BasicDataInit();
		return basicinit;
	}
	

}
