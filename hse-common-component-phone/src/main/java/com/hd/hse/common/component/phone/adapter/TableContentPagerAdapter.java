/**
 * Project Name:hse-common-component-phone
 * File Name:TableContentPagerAdapter.java
 * Package Name:com.hd.hse.common.component.phone.adapter
 * Date:2015年1月21日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.viewpager.widget.PagerAdapter;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.custom.ExpandListView;

import java.util.List;
import java.util.Map;

/**
 * ClassName:TableContentPagerAdapter ().<br/>
 * Date: 2015年1月21日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class TableContentPagerAdapter extends PagerAdapter {

	private final String TAG = this.getClass().getSimpleName();
	private final boolean DEBUG = true;

	private List<String> mTitle;

	private List<Map<String, String>> mContent;

	private Context mContext;
	
	private ListView gTableTitleView;

	public TableContentPagerAdapter(Context ctx, List<String> title,
			List<Map<String, String>> content, ListView tableTitleView) {
		mContext = ctx;
		mTitle = title;
		mContent = content;
		gTableTitleView = tableTitleView;
	}

	@Override
	public int getCount() {
		if (mContent == null) {
			return 0;
		}
		return mContent.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (DEBUG)
			Log.w(TAG, "destroyItem position:" + position);

		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (DEBUG)
			Log.w(TAG, "instantiateItem position:" + position);

		ListView _resultView = null;

		_resultView = new ExpandListView(mContext);
		_resultView.setDivider(mContext.getResources().getDrawable(R.drawable.divider_10));
		_resultView.setEnabled(false);
		_resultView.setAdapter(new TableContentAdapter(mContext, mTitle,
				mContent.get(position), gTableTitleView));

		_resultView.setDivider(new ColorDrawable(0x00000000));
		_resultView.setDividerHeight(mContext.getResources().getDimensionPixelOffset(R.dimen.hd_hse_common_module_phone_gases_check_history_list_dividerHeight));
		
		container.addView(_resultView);
		return _resultView;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
