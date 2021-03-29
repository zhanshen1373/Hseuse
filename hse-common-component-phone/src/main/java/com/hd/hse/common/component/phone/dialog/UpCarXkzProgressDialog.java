package com.hd.hse.common.component.phone.dialog;

import android.content.Context;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.dc.business.web.zyxk.UpCarXkzInfo;
import com.hd.hse.dc.business.web.zyxk.UpZYXKInfoNew1;

public class UpCarXkzProgressDialog extends MSGProgressDialog {

    private Context context;
    public UpCarXkzProgressDialog(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }
    @Override
    public AbstractActionListener getActionListner() {
        // TODO Auto-generated method stub
        super.getActionListner();
        UpCarXkzInfo zyxkinfo = new UpCarXkzInfo(context);
        return zyxkinfo;
    }
}
