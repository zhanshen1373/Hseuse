/**
 * Project Name:hse-common-component-phone
 * File Name:AutoSearchEditText.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年9月2日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import com.hd.hse.common.component.phone.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

/**
 * ClassName:AutoSearchEditText ().<br/>
 * Date: 2015年9月1日 <br/>
 * 带历史记录的EditText
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class AutoSearchEditText extends AutoCompleteTextView {

	private SharedPreferences sp;
	private String history;
	private String[] history_arr;
	private ArrayAdapter<String> arr_adapter;
	private Context context;
	private String historyName;

	public AutoSearchEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		sp = context.getSharedPreferences("search_history", 0);
	}

	/**
	 * save:(保存历史记录). <br/>
	 * date: 2015年9月2日 <br/>
	 * 
	 * @author LiuYang
	 */
	public void save() {
		// 获取搜索框信息
		String text = this.getText().toString();
		history = sp.getString(historyName, "暂时没有搜索记录");
		// 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
		if (!history.contains(text + ",")) {
			// 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
			StringBuilder builder = new StringBuilder();
			builder.append(text + ",");
			if (history.equals("暂时没有搜索记录")) {
				history_arr = new String[0];
			}
			// 保存前五条记录
			for (int i = 0; i < (history_arr.length >= 9 ? 9
					: history_arr.length); i++) {
				builder.append(history_arr[i] + ",");
			}
			getHistory(builder.toString());
			arr_adapter.notifyDataSetChanged();
			SharedPreferences.Editor myeditor = sp.edit();
			myeditor.putString(historyName, builder.toString());
			myeditor.commit();
			// Toast.makeText(context, text + "添加成功",
			// Toast.LENGTH_SHORT).show();
		}/*
		 * else { Toast.makeText(context, text + "已存在",
		 * Toast.LENGTH_SHORT).show(); }
		 */
	}

	public void getHistory(String history) {
		// 用逗号分割内容返回数组
		history_arr = history.split(",");

		// 新建适配器，适配器数据为搜索历史文件内容
		arr_adapter = new ArrayAdapter<String>(
				context,
				R.layout.hd_common_component_auto_search_editview_dropdown_item,
				history_arr);

		// 设置适配器
		this.setAdapter(arr_adapter);
	}

	/**
	 * cleanHistory:(清除搜索记录). <br/>
	 * date: 2015年9月2日 <br/>
	 * 
	 * @author LiuYang
	 */
	public void cleanHistory() {
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		Toast.makeText(context, "清除成功", Toast.LENGTH_SHORT).show();
	}

	/**
	 * get:(获取搜索记录并展示). <br/>
	 * date: 2015年9月2日 <br/>
	 * 
	 * @author LiuYang
	 * @param historyName
	 *            历史记录保存的名称
	 */
	public void get(String historyName) {
		this.historyName = historyName;
		// 获取搜索记录文件内容
		history = sp.getString(historyName, "暂时没有搜索记录");
		getHistory(history);
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AutoSearchEditText.this.showDropDown();
			}
		});
	}

}
