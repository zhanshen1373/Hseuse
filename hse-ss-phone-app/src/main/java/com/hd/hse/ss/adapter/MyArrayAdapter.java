package com.hd.hse.ss.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.base.Domain;
import com.hd.hse.ss.R;
import com.lidroid.xutils.view.annotation.event.OnItemSelected;

public class MyArrayAdapter extends BaseAdapter {
	private Context mContext;
	private List<Domain> mDomains;
	private Domain selectedDoain;

	public MyArrayAdapter(Context context, List<Domain> mDomains) {
		//super(context, android.R.layout.simple_spinner_item, stringArray);
		mContext = context;
		this.mDomains = mDomains;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner展开后的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_dropdown_item, parent,
					false);
		}

		// 此处text1是Spinner默认的用来显示文字的TextView
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(mDomains.get(position).getDescription());
		tv.setTextSize(px2sp(mContext, mContext.getResources().getDimension(
				R.dimen.hd_common_textsize)));
		tv.setTextColor(mContext.getResources().getColor(
				R.color.hd_hse_common_component_phone_fontnum));

		return convertView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 修改Spinner选择后结果的字体颜色
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_item, parent, false);
		}

		// 此处text1是Spinner默认的用来显示文字的TextView
		TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
		tv.setText(mDomains.get(position).getDescription());
		selectedDoain=mDomains.get(position);
		tv.setTextSize(px2sp(mContext, mContext.getResources().getDimension(
				R.dimen.hd_common_textsize)));
		tv.setTextColor(mContext.getResources().getColor(
				R.color.hd_hse_common_component_phone_fontnum));
		return convertView;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
	
	/**
	 * 得到当前选中的String
	 * @return
	 */
	public Domain getselectedDomain(){
		return selectedDoain;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDomains.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDomains.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
