package com.hd.hse.dc.phone.ui.activity.upload;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.web.zyxk.UpZYXKInfoNew1;
import com.hd.hse.dc.phone.ui.common.progressdialog.MSGProgressDialog;

public class UpZYPProgressDialog extends MSGProgressDialog {

	private Context context;
	public UpZYPProgressDialog(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public AbstractActionListener getActionListner() {
		// TODO Auto-generated method stub
		 super.getActionListner();
		UpZYXKInfoNew1 zyxkinfo = new UpZYXKInfoNew1(context);
		return zyxkinfo;
	}

}
