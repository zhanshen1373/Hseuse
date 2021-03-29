/**
 * Project Name:hse-main-phone-app
 * File Name:TableAdapter.java
 * Package Name:com.hd.hse.main.phone.ui.activity.welcome
 * Date:2015年1月21日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.adapter;

import java.util.List;

import com.hd.hse.common.component.phone.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ClassName:TableAdapter ().<br/>
 * Date:     2015年1月21日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class TableTitleAdapter extends HDBaseAdapter {

	private List<String> mData ;
	private Context mContext;
	
	public TableTitleAdapter(Context ctx, List<String> data){
		mContext = ctx;
		mData = data;
	}
	
	public void setData(List<String> data){
		mData = data;
	}	
	
	@Override
	public int getCount() {
		if(mData == null){
			return 0;
		}
		return mData.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View _resultView = View.inflate(mContext, R.layout.hd_hse_common_module_gase_detect_history_item, null);
		
		TextView _text = (TextView) _resultView.findViewById(R.id.hd_hse_common_module_text);
		
		String _txt = mData.get(position);
		if(_txt != null && _txt.contains("#")){
			// 如果字符串中包含 #， 将#及其前面的字符去掉
			_txt = _txt.substring(_txt.indexOf('#')+1);
		}
		
		_text.setText(_txt);
		
		
		return _resultView;
	}

}

