/**
 * Project Name:hse-wov-phone-app
 * File Name:DeptListAdapter2.java
 * Package Name:com.hd.hse.wov.phone.ui.statistics
 * Date:2015年11月20日
 * Copyright (c) 2015, liuyang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.wov.phone.ui.statistics;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.entity.base.Department;
import com.hd.hse.wov.phone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ClassName:DeptListAdapter2 ().<br/>
 * Date: 2015年11月20日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class DeptListAdapter2 extends BaseAdapter {
	private Context context;
	private List<Department> data;
	private List<Department> searchData;

	private static final int TYPE_SEARCH = 0;
	private static final int TYPE_DATA = 1;

	public DeptListAdapter2(Context context, List<Department> data) {
//		this.data = data;
//		searchData = new ArrayList<>();
//		searchData.addAll(data);
		searchData = data;
		this.context = context;
	}

	// @Override
	// public int getViewTypeCount() {
	// return 2;
	// }
	//
	// @Override
	// public int getItemViewType(int position) {
	// if (position == 0) {
	// return TYPE_SEARCH;
	// } else {
	// return TYPE_DATA;
	// }
	// }

	@Override
	public int getCount() {
		return searchData.size()/* + 1 */;
	}

	@Override
	public Object getItem(int arg0) {
		// if (arg0 == 0) {
		// return null;
		// } else {
		return searchData.get(arg0/* - 1 */);
		// }
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
//		int type = getItemViewType(position);
//		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		if (view == null) {
//			switch (type) {
//				case TYPE_SEARCH :
//					view = LayoutInflater.from(context).inflate(
//							R.layout.hd_hse_wov_dept_list_item_search,
//							viewGroup, false);
//					holder1 = new ViewHolder1();
//					Button searchBtn = (Button) view
//							.findViewById(R.id.search_btn);
//					final EditText searchTxt = (EditText) view
//							.findViewById(R.id.search_txt);
//					searchBtn.setOnClickListener(new View.OnClickListener() {
//
//						@Override
//						public void onClick(View arg0) {
//							String str = searchTxt.getText().toString();
//							if (str != null && !str.equals("")) {
//								searchData.clear();
//								for (Department d : data) {
//									if (d.getDescription().contains(str)) {
//										searchData.add(d);
//									}
//								}
//								notifyDataSetChanged();
//							}
//						}
//					});
//					holder1.searchBtn = searchBtn;
//					holder1.searchTxt = searchTxt;
//					view.setTag(holder1);
//					break;
//				case TYPE_DATA :
					view = LayoutInflater.from(context).inflate(
							R.layout.hd_hse_wov_dept_list_item_data, viewGroup,
							false);
					holder2 = new ViewHolder2();
					holder2.dataTxt = (TextView) view
							.findViewById(R.id.data_txt);
					view.setTag(holder2);
//					break;
//				default :
//					break;
//			}
		} else {
//			switch (type) {
//				case TYPE_SEARCH :
//					holder1 = (ViewHolder1) view.getTag();
//					break;
//				case TYPE_DATA :
					holder2 = (ViewHolder2) view.getTag();
//					break;
//				default :
//					break;
//			}
		}

//		switch (type) {
//			case TYPE_SEARCH :
//
//				break;
//			case TYPE_DATA :
				Department department = (Department) getItem(position);
				holder2.dataTxt.setText(department.getDescription());
//				break;
//			default :
//				break;
//		}
		return view;
	}

//	private class ViewHolder1 {
//		public EditText searchTxt;
//		public Button searchBtn;
//	}

	private class ViewHolder2 {
		public TextView dataTxt;
	}

}
