/**
 * Project Name:hse-main-phone-app
 * File Name:WaiteWorkAdapter.java
 * Package Name:com.hd.hse.main.phone.ui.activity
 * Date:2015年9月21日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.activity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.common.PushMessage;
import com.hd.hse.main.phone.R;

/**
 * ClassName:WaiteWorkAdapter ().<br/>
 * Date: 2015年9月21日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class MessageListAdapter extends BaseAdapter {

	private Context context;
	private List<PushMessage> datas;

	public MessageListAdapter(Context context, List<PushMessage> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_waite_work_listview, null);
			holder = new ViewHolder();
			holder.tv = (TextView) view.findViewById(R.id.txtView);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		PushMessage msg = datas.get(position);
		holder.tv.setText(msg.getXxnr());
		if (msg.isIsopen()) {
			holder.tv.setTextColor(context.getResources().getColor(
					R.color.hd_hse_common_describetext_gray));
		} else {
			holder.tv.setTextColor(context.getResources().getColor(
					R.color.hd_hse_common_red));
		}
		return view;
	}

	private class ViewHolder {
		public TextView tv;
	}

}
