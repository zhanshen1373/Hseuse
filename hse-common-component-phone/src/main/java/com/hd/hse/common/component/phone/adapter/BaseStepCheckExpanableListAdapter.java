/**
 * Project Name:hse-common-component-phone
 * File Name:BaseStepCheckExpanableListAdapter.java
 * Package Name:com.hd.hse.common.component.phone.adapter
 * Date:2015年1月19日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.custom.EditableDialogManager;
import com.hd.hse.common.component.phone.custom.EditableDialogManager.EditableDialogListener;
import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView;
import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView.HeaderAdapter;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * ClassName:BaseStepCheckExpanableListAdapter ().<br/>
 * Date:     2015年1月19日  <br/>
 * @author   flb
 * @version 
 * @see     
 */
public abstract class BaseStepCheckExpanableListAdapter extends BaseExpandableListAdapter implements HeaderAdapter, OnLongClickListener{
	
	/**
	 * 给非逐条提供的获取方法
	 * getSelectedValues:(). <br/>
	 * date: 2015年1月19日 <br/>
	 * @author flb
	 * @return
	 */
	public List<SuperEntity> getSelectedValues() {
		List<SuperEntity> tempCollection = new ArrayList<SuperEntity>();
		Iterator<Map.Entry<Integer, List<SuperEntity>>> iter = referenceTable.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Integer, List<SuperEntity>> entry = (Map.Entry<Integer, List<SuperEntity>>) iter.next();
			List<SuperEntity> value = entry.getValue();
			for(SuperEntity entity : value){
				boolean isSelected = (boolean)(entity.getAttribute(isSelectedAttr));
				if(entity.isModified() || isSelected){
					tempCollection.add(entity);
				}
			}
		}
		return tempCollection;
	}
	
	public List<SuperEntity> getSourceValues() {
		return mSourceValues;
	}
	
	/**
	 * 提供给逐条的方法
	 * getCurrentSelectedValue:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author flb
	 * @return
	 */
	public void updateValues(List<SuperEntity> values) {
		for(int x=0; x<values.size(); x++){
			SuperEntity entity = values.get(x);
			for (int y = 0; y < referenceTable.size(); y++) {
				List<SuperEntity> list = referenceTable.get(y);
				for (int z = 0; z < list.size(); z++) {
					SuperEntity innerEntity = list.get(z);
					if(innerEntity.getAttribute(innerEntity.getPrimaryKey()).equals(entity.getAttribute(entity.getPrimaryKey()))){
						entity.setModified(false);
						entity.setAttribute(isSelectedAttr, false);
						list.set(z, entity);
					}
				}
			}
		}
		notifyDataSetChanged();
	}
	
	public List<SuperEntity> getCurrentSelectedValues() {
		List<SuperEntity> tempCollection = new ArrayList<SuperEntity>();
		Iterator<Map.Entry<Integer, List<SuperEntity>>> iter = referenceTable.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Integer, List<SuperEntity>> entry = (Map.Entry<Integer, List<SuperEntity>>) iter.next();
			List<SuperEntity> value = entry.getValue();
			for(SuperEntity entity : value){
				boolean isSelected = (boolean) entity.getAttribute(isSelectedAttr);
				if(isSelected){
					tempCollection.add(entity);
				}
			}
		}
		return tempCollection;
	}
	
	public void updateCurrentValues(List<SuperEntity> values) {
		for(int x = 0; x < values.size(); x++){
			SuperEntity entity = values.get(x);
			for (int y = 0; y < referenceTable.size(); y++) {
				List<SuperEntity> list = referenceTable.get(y);
				for (int z = 0; z < list.size(); z++) {
					SuperEntity innerEntity = list.get(z);
					if(innerEntity.getAttribute(innerEntity.getPrimaryKey()).equals(entity.getAttribute(entity.getPrimaryKey()))){
						entity.setAttribute(isSelectedAttr, false);
						list.set(z, entity);
					}
				}
			}
		}
		notifyDataSetChanged();
	}
	
	public void setIsSelectedAttr(String attrName){
		this.isSelectedAttr = attrName;
	}

	/**
	 * 提供给逐条和逐条批量的方法
	 * nextItem:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author flb
	 */
	public void nextItem(){}
	public void previousItem(){}
	
	protected IEventListener mIEventListener;
	public void setIEventListener(IEventListener listener){
		this.mIEventListener = listener;
	}
	
	/************业务逻辑抽取*****************/
	protected Context mContext;
	protected LayoutInflater mInflater;
	
	protected PinnedSectionExpandableListView mListView;
	protected String isSelectedAttr;
	protected List<SuperEntity> mSourceValues;
	protected List<String> showAttrs;
	protected String pcAttr;
	
	protected String childKey = "";
	
	protected EditableDialogManager manager  = null;
	protected SparseIntArray groupStatusMap;

	/*****解析出来的临时分组*****/
	protected Map<Integer, List<SuperEntity>> referenceTable = new HashMap<Integer, List<SuperEntity>>();
	
	protected int[] iconResLight = new int[]{R.drawable.hd_cbs_icon_step_check_no_one, R.drawable.hd_cbs_icon_step_check_yes_one, R.drawable.hd_cbs_icon_step_check_not_sure_one};
//	protected int[] iconResDark = new int[] {R.drawable.hd_cbs_icon_step_check_no_two, R.drawable.hd_cbs_icon_step_check_yes_two, R.drawable.hd_cbs_icon_step_check_not_sure_two };
	
	protected BaseStepCheckExpanableListAdapter(Context mContext,
			List<SuperEntity> mSourceValues, List<String> showAttrs,
			String pcAttr, PinnedSectionExpandableListView listView,
			String childKey){
		this.mContext = mContext;
		this.mSourceValues = mSourceValues;
		this.showAttrs = showAttrs;
		this.pcAttr = pcAttr;
		this.childKey = childKey;
		this.manager = new EditableDialogManager();
		
		this.mListView = listView;
		mInflater = LayoutInflater.from(this.mContext);
		this.mListView.setHeaderView(mInflater.inflate(
				R.layout.hd_hse_component_list_item_step_check_sample_title,
				this.mListView, false));
		groupStatusMap = new SparseIntArray(mSourceValues.size());

		analysisSourceValues();
	}
	
	private void analysisSourceValues() {
		referenceTable.clear();
		if (mSourceValues != null) {
			for (int i = 0; i < mSourceValues.size(); i++) {
				SuperEntity groupEntity = mSourceValues.get(i);
				referenceTable.put(i, groupEntity.getChild(childKey));
			}
		}
	}
	
	@Override
	public int getGroupCount() {
		return mSourceValues.size();

	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return referenceTable.get(groupPosition).size();
	}

	@Override
	public SuperEntity getGroup(int groupPosition) {
		return mSourceValues.get(groupPosition);
	}

	@Override
	public SuperEntity getChild(int groupPosition, int childPosition) {
		return referenceTable.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return ExpandableListView.getPackedPositionForGroup(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return ExpandableListView.getPackedPositionForChild(groupPosition,
				childPosition);
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.hd_hse_component_list_item_step_check_sample_title, null);
		}
		
		TextView titleTV = ViewHolder.getView(convertView, R.id.step_check_title_tv);
		ImageView arrowIV = ViewHolder.getView(convertView, R.id.step_check_arrow_iv);
		
		titleTV.setText((String)(getGroup(groupPosition).getAttribute(showAttrs.get(0))));
		
		if (isExpanded) {
			arrowIV.setImageResource(R.drawable.hd_hse_common_module_zyplist_group_indicator_down);
		} else {
			arrowIV.setImageResource(R.drawable.hd_hse_common_module_zyplist_group_indicator_right);
		}
		
		return convertView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(getItemResourceId(), null);
		}
		
		return getItemView(groupPosition, childPosition, convertView);
	}
	
	protected abstract int getItemResourceId();
	public abstract View getItemView(int groupPosition, int childPosition, View convertView);
	
	public static class ViewHolder{
		@SuppressWarnings("unchecked")
		public static <T extends View> T getView(View view, int id){
			SparseArray<View> viewHolder = (SparseArray<View>)view.getTag();
			if(viewHolder == null){
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if(childView == null){
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T)childView;
		}
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	protected boolean isLastGroup(int groupPosition) {
		return (groupPosition == (getGroupCount() - 1));
	}

	protected boolean isLastChild(int groupPosition, int childPosition) {
		int childrenCount = getChildrenCount(groupPosition);
		return (childPosition == (childrenCount - 1));
	}
	
	@Override
	public int getHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if(childPosition == childCount - 1){
			return PINNED_HEADER_PUSHED_UP;
		}else if(childPosition == -1 && !mListView.isGroupExpanded(groupPosition)){
			return PINNED_HEADER_GONE;
		}else{
			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
		((TextView)header.findViewById(R.id.step_check_title_tv)).setText((String)getGroup(groupPosition).getAttribute(showAttrs.get(0)));
	}

//	protected SparseIntArray groupStatusMap = new SparseIntArray();

	@Override
	public void setGroupClickStatus(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getGroupClickStatus(int groupPosition) {
		if(groupStatusMap.keyAt(groupPosition) >= 0){
			return groupStatusMap.get(groupPosition);
		}else{
			return 0;
		}
	}
	
	@Override
	public boolean onLongClick(View v) {
		Integer groupPosition = (Integer) v.getTag(R.string.hd_hse_component_list_item_attribute_groud_position);
		Integer childPosition = (Integer) v.getTag(R.string.hd_hse_component_list_item_attribute_child_position);
		Integer attrOrder = (Integer) v.getTag(R.string.hd_hse_component_list_item_attribute_order);
		
		final SuperEntity childEntity = getChild(groupPosition, childPosition);
		String text = (String) childEntity.getAttribute(showAttrs.get(attrOrder));
		if(!TextUtils.isEmpty(text)){
			manager.showDialog(mContext, text, brackets(text));
			manager.setOnEditableDialogListener(new EditableDialogListener() {
				
				@Override
				public void onConfirmClick(String content) {
					childEntity.setAttribute(showAttrs.get(1), content);
					notifyDataSetChanged();
				}
				
				@Override
				public void onCancelClick(String content) {
					
				}
			});
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean brackets (String text){
		if(text.contains("(") || text.contains("（")){
			Stack<Character> stack = new Stack<Character>();
			char[] charArray = text.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if(c != '\0'){
					if(c == '(' || charArray[i] == '（'){
						stack.push(c);
					}else if(charArray[i] == ')'){
						if(!stack.empty() && stack.peek() == '('){
							stack.pop();
							continue ;
						}else{
							return false;
						}
					}else if(charArray[i] == '）'){
						if(!stack.empty() && stack.peek() == '（'){
							stack.pop();
							continue ;
						}else{
							return false;
						}
					}
				}
			}
			
			if(stack.empty()){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}

