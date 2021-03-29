/**
 * Project Name:hse-common-module-phone
 * File Name:CacheUtils.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2014年12月26日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import java.util.List;

import android.util.Log;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * ClassName:CacheUtils ().<br/>
 * Date:     2014年12月26日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class CacheUtils {
	private final String TAG = this.getClass().getSimpleName();
	private final boolean DEBUG = true;
	
	private List<SuperEntity> mData;

	public CacheUtils(){}
	
	public CacheUtils(List<SuperEntity> data){
		mData = data;
	}
	
	public void setData(List<SuperEntity> data){
		if (DEBUG) Log.w(TAG, "setData:"+data);
		mData = data;
		
		resetCache();
	}

	private void resetCache() {
		mCurrentGroupPosition = -1;
		mCurrentGroup = null;
	}
	
	/**
	 * 获得 一组 数据。
	 * getGroupChilds:(). <br/>
	 * date: 2014年11月21日 <br/>
	 *
	 * @author xuxinwen
	 * @param groupPosition
	 * @return
	 */
	public List<SuperEntity> getGroup(int groupPosition){
		if(mData == null){
			return null;
		}
		
		if(mCurrentGroupPosition == groupPosition){
			
		}else{
			mCurrentGroup = mData.get(groupPosition).getChild(WorkOrder.class.getName());
			mCurrentGroupPosition = groupPosition;
		}
		
		return mCurrentGroup;
	}
	
	private List<SuperEntity> mCurrentGroup;
	private int mCurrentGroupPosition = -1;
	
	public WorkOrder getChild(int groupPosition, int childPosition){
		if(mData == null){
			return null;
		}
		
		if(groupPosition == mCurrentGroupPosition){
			
		}else{
			mCurrentGroup = getGroup(groupPosition);
			mCurrentGroupPosition = groupPosition;
		}
		return (WorkOrder) mCurrentGroup.get(childPosition);
	}
	
	public int getGroupCount(){
		if(mData == null){
			return 0;
		}
		return mData.size();
	}
	
	public int getChildrenCount(int groupPosition){
		if(mData == null){
			return 0;
		}
		
		List<SuperEntity> _groupChilds = getGroup(groupPosition);
		
		if(_groupChilds == null){
			return 0;
		}
		
		return _groupChilds.size();
	}
	
}

