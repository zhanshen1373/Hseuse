package com.hd.hse.pc.phone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.other.InAndOutFactoryRecordEntity;
import com.hd.hse.pc.phone.R;

/**
 * 近一周出入场记录的adapter
 * 
 * @author yn
 * 
 */
public class PersonAccessRecordAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private List<InAndOutFactoryRecordEntity> mPersonAccessRecords; // 证书列表

	public PersonAccessRecordAdapter(Context mContext,
			List<InAndOutFactoryRecordEntity> mPersonAccessRecords) {
		this.mContext = mContext;
		this.mPersonAccessRecords = mPersonAccessRecords;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPersonAccessRecords.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mPersonAccessRecords.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder mViewHolder = null;
		if (arg1 == null) {
			arg1 = mLayoutInflater.inflate(
					R.layout.hse_pc_phone_app_item_record_or_certificate, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvTime = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate_tv1);
			mViewHolder.tvLocation = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate_tv2);
			arg1.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) arg1.getTag();
		}
		mViewHolder.tvTime.setText(mPersonAccessRecords.get(arg0)
				.getCrdatetime());
		mViewHolder.tvLocation.setText(mPersonAccessRecords.get(arg0)
				.getLocation());
		return arg1;
	}

	private class ViewHolder {
		private TextView tvTime;
		private TextView tvLocation;
	}
}
