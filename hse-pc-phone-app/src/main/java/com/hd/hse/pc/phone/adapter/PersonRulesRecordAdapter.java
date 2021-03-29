package com.hd.hse.pc.phone.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.pc.phone.R;

public class PersonRulesRecordAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private ArrayList<WorkLogEntry> mRulesRecords;

	public PersonRulesRecordAdapter(ArrayList<WorkLogEntry> mRulesRecords,
			Context mContext) {
		this.mRulesRecords = mRulesRecords;
		this.mContext = mContext;

		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mRulesRecords.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mRulesRecords.get(arg0);
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
					R.layout.hse_pc_phone_app_person_rules_record_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvContent = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_person_rules_record_item_tv_content);
			mViewHolder.tvLocation = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_person_rules_record_item_tv_location);
			mViewHolder.tvTime = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_person_rules_record_item_tv_time);
			arg1.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) arg1.getTag();
		}
		mViewHolder.tvContent.setText(mRulesRecords.get(arg0).getFxwt());
		mViewHolder.tvLocation.setText("属地单位: "
				+ (mRulesRecords.get(arg0).getSddept_desc() == null ? ""
						: mRulesRecords.get(arg0).getSddept_desc()));
		mViewHolder.tvTime.setText("违章时间: "
				+ mRulesRecords.get(arg0).getCreatedate());
		return arg1;
	}

	private class ViewHolder {
		private TextView tvContent;
		private TextView tvLocation;
		private TextView tvTime;
	}
}
