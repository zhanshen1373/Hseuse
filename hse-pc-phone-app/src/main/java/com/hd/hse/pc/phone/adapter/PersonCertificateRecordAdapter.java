package com.hd.hse.pc.phone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.pc.phone.R;
/**
 * 证书列表adapter
 * @author nl
 *
 */
public class PersonCertificateRecordAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private List<PersonSpecialTypeWork> mSpecialTypeWorks;	// 证书列表
	public PersonCertificateRecordAdapter(Context mContext,List<PersonSpecialTypeWork> mSpecialTypeWorks){
		this.mContext=mContext;
		this.mSpecialTypeWorks=mSpecialTypeWorks;
		mLayoutInflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSpecialTypeWorks.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mSpecialTypeWorks.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder mViewHolder=null;
		if (arg1==null) {
			arg1=mLayoutInflater.inflate(R.layout.hse_pc_phone_app_item_record_or_certificate, null);
			mViewHolder=new ViewHolder();
			mViewHolder.tvCertificateType=(TextView) arg1.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate_tv1);
			mViewHolder.tvCertificateNum=(TextView) arg1.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate_tv2);
			arg1.setTag(mViewHolder);
		}else {
			mViewHolder=(ViewHolder) arg1.getTag();
		}
		String zsname = mSpecialTypeWorks.get(arg0).getZsname();
		if (zsname == null || zsname.equals("")) {
			zsname = mSpecialTypeWorks.get(arg0).getZsname_for_wsh();
		}
		mViewHolder.tvCertificateType.setText(zsname);
		mViewHolder.tvCertificateNum.setText(mSpecialTypeWorks.get(arg0).getZsnum());
		return arg1;
	}
	
	private class ViewHolder{
		private TextView tvCertificateType;
		private TextView tvCertificateNum;
	}

}
