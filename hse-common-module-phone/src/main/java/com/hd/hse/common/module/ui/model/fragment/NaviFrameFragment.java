/**
 * Project Name:hse-common-module
 * File Name:BaseFragment.java
 * Package Name:com.hd.hse.common.module.ui.activity
 * Date:2014年10月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.ui.model.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;

import java.util.List;

/**
 * 此类应作为 NaviFrameActivity 子类的 Fragment使用，用来切换 NaviFrameActivity 中间区域。如果不是作为
 * NaviFrameActivity 的 Fragment 会导致 异常。
 * 
 * ClassName:BaseFragment ().<br/>
 * Date: 2014年10月11日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public abstract class NaviFrameFragment extends Fragment {

	private final String TAG = this.getClass().getSimpleName();
	private final boolean DEBUG = true;
	
	/**
	 * iEventListener:TODO(事件).
	 */
	public IEventListener iEventListener;
	
	/**
	 * setIEventListener:(设置事件). <br/>
	 * date: 2014年11月10日 <br/>
	 * 
	 * @author lxf
	 * @param iEventListener
	 */
	public void setIEventListener(IEventListener iEventListener) {
		this.iEventListener = iEventListener;
	}
	
	
	/**
	 * getIeventListener:(获取监听事件). <br/>
	 * date: 2014年12月5日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public IEventListener getIeventListener()
	{
		return iEventListener;
	}
	
	// 属于 此 Fragment 的 View,用于缓存的时候不丢失界面状态和数据。
	protected View mRootView;

	// 该 Fragment 依附的 Activity
	protected NaviFrameActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActivity = (NaviFrameActivity) getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mRootView == null) {
			if (DEBUG) Log.w(TAG, "onCreateView-->initView");
			mRootView = initView(inflater, container);
			init();
		}
		
		if (DEBUG) Log.w(TAG, "onCreateView-->refreshData");
		refreshData();
		return mRootView;
	}

	abstract protected void init();

	protected void setNaviCurrentEntity(SuperEntity entity){
		mActivity.setNaviCurrentEntity(entity);
	}
	
	protected SuperEntity getNaviCurrentEntity(){
		return mActivity.getNaviCurrentEntity();
	}
	
	
	/**
	 * 切换 Activity 的中间内容。
	 * 
	 * switchContentView:(). <br/>
	 * date: 2014年10月13日 <br/>
	 * 
	 * @author xuxinwen
	 * @param clz
	 */
	protected void switchContentView(Class<? extends NaviFrameFragment> clz
			, List<Object> data) {
		mActivity.switchContentView(clz, data);
	}

	/**
	 * 刷新界面要显示的数据。该方法在每次界面重新显示的屏幕上的 时候都会被调用，所以应该在此方法中更新界面。
	 * 
	 * 如果用户在 initView 没有返回 View ，则 contentView 为null。 refreshData:(). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author xuxinwen
	 * @return
	 */
	public abstract void refreshData();

	/**
	 * 在此 方法中创建 并初始化 View， 此方法在页面切换过程中，由于该页面的缓存会导致该方法只
	 * 在页面刚刚创建的时候调用一次。所以对页面数据的更新操作不要放在此 方法中。 initView:(). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author xuxinwen
	 * @return
	 */
	protected abstract View initView(LayoutInflater inflater,
			ViewGroup container);

	/**
	 * 返回 该界面对应的 View getContentView:(). <br/>
	 * date: 2014年10月11日 <br/>
	 * 
	 * @author xuxinwen
	 * @return
	 */
	protected View getContentView() {
		return mRootView;
	}
	
	/**
	 * 给 Fragment 设置数据，不会自动刷新。用于在 Fragment显示出来之前也就是调用
	 * 生命周期函数之前将数据设置进来。
	 * 本方法应有子类来实现，因为可变参数里具体的参数情况要在真正实现某个Fragment和使用
	 * 该Fragment具体决定。那时才能确定，为什么现在要预留这个方法，为了在调用 NaviFrameActivity
	 * 的 切换Fragment的方法的时候能够同时传递参数，框架提供了传递参数的方法，当然框架使用
	 * 者可以不进行参数传递。
	 * 
	 * initData:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author xuxinwen
	 * @param data
	 */
	abstract public void initData(List<Object> data);
	


}
