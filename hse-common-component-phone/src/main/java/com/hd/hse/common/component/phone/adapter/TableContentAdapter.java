/**
 * Project Name:hse-main-phone-app
 * File Name:TableAdapter.java
 * Package Name:com.hd.hse.main.phone.ui.activity.welcome
 * Date:2015年1月21日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.util.ViewMeasureUtils;

import java.util.List;
import java.util.Map;


/**
 * ClassName:TableAdapter ().<br/>
 * Date:     2015年1月21日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class TableContentAdapter extends HDBaseAdapter {

	private final String TAG = this.getClass().getSimpleName();
	private final boolean DEBUG = true;
	
	private List<String> mTitle ;
	
	private Map<String, String> mContent;
	
	private Context mContext;
	
	private ListView gTableTitleView;
	
	public TableContentAdapter(Context ctx, List<String> title, Map<String, String> content,
			ListView tableTitleView){
		mContext = ctx;
		mTitle = title;
		mContent = content;
		gTableTitleView = tableTitleView;
	}
	
	public void setData(List<String> title, Map<String, String> content){
		mTitle = title;
		mContent = content;
	}	
	
	@Override
	public int getCount() {
		if(mTitle == null){
			return 0;
		}
		return mTitle.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View _resultView = View.inflate(mContext, R.layout.hd_hse_common_module_gase_detect_history_content_item, null);
		
		TextView _text = (TextView) _resultView.findViewById(R.id.hd_hse_common_module_text);
		
		String _txt = mContent.get(mTitle.get(position));
		if(_txt != null && _txt.contains("#")){
			// 如果字符串中包含 #， 将#及其前面的字符去掉
			_txt = _txt.substring(_txt.indexOf('#')+1);
		}
		
		_text.setText(_txt);
		
		// 矫正高度，使其与对应的表头View的高度相同。
		View _tableTitleView = gTableTitleView.getAdapter().getView(position, null, null);
		
		int _titleViewHeight = ViewMeasureUtils.getMeasuredHeightWithFixWidth(_tableTitleView, gTableTitleView.getLayoutParams().width);
		_text.getLayoutParams().height = _titleViewHeight;
		
		return _resultView;
	}

}

