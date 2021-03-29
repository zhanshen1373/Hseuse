package com.hd.hse.pc.phone.adapter;

import java.util.ArrayList;

import org.apache.commons.lang.ObjectUtils.Null;

import com.hd.hse.pc.phone.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 人员信息类型页面内的adapter
 * 
 * @author yn
 * 
 */
public class PersonCheckAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private ArrayList<PeopleInformation> PeopleInformations;

	public PersonCheckAdapter(ArrayList<PeopleInformation> PeopleInformations,
			Context mContext) {
		this.PeopleInformations = PeopleInformations;
		this.mContext = mContext;

		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PeopleInformations.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return PeopleInformations.get(arg0);
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
					R.layout.hse_pc_phone_app_item_person_check, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvInformation = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_item_person_check_tv_information);
			mViewHolder.tvType = (TextView) arg1
					.findViewById(R.id.hse_pc_phone_app_item_person_check_tv_type);
			arg1.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) arg1.getTag();
		}
		if (PeopleInformations.get(arg0).getInformationType() != null
				&& PeopleInformations.get(arg0).getInformationType()
						.equals("状态")
				&& PeopleInformations.get(arg0).getDetailInformation() != null
				&& !PeopleInformations.get(arg0).getDetailInformation()
						.equals("已办卡")) {

			// 异常状态
			mViewHolder.tvInformation.setTextColor(Color.RED);
		} else {
			// 正常状态
			mViewHolder.tvInformation.setTextColor(mContext.getResources()
					.getColor(R.color.hd_hse_common_component_phone_fontnum));
		}
		mViewHolder.tvType.setText(PeopleInformations.get(arg0)
				.getInformationType());
		mViewHolder.tvInformation.setText(PeopleInformations.get(arg0)
				.getDetailInformation());
		return arg1;
	}

	private class ViewHolder {
		private TextView tvType;
		private TextView tvInformation;
	}

}
