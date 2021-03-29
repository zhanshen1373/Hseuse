package com.hd.hse.ss.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.worklog.KhbzEntity;
import com.hd.hse.ss.R;

public class WztkAdapter extends BaseAdapter {
	private Context context;
	private List<KhbzEntity> data;

	public WztkAdapter(Context context, List<KhbzEntity> data) {
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.hd_hse_wov_dept_list_item_data, viewGroup, false);
			holder = new ViewHolder();
			holder.dataTxt = (TextView) view.findViewById(R.id.data_txt);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.dataTxt.setText(data.get(position).getWztk());
		return view;

	}

	private class ViewHolder {
		public TextView dataTxt;
	}

}

