package com.hd.hse.dc.phone.ui.activity.download;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.web.zyxk.DownZYSQInfoNew;
import com.hd.hse.dc.phone.ui.common.progressdialog.MSGProgressDialog;

public class DownZYPProgressDiaolog extends MSGProgressDialog {

	public DownZYPProgressDiaolog(Context context) {
		super(context);
	}
	
	public AbstractActionListener getActionListner() {
		super.getActionListner();
		DownZYSQInfoNew downinfo = new DownZYSQInfoNew();
		return downinfo;
	};

}
