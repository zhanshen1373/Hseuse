/**
 * Project Name:hse-common-component-phone
 * File Name:BaseCheckPointExpanableListAdapter.java
 * Package Name:com.hd.hse.common.component.phone.adapter
 * Date:2015年1月19日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView;
import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName:BaseCheckPointExpanableListAdapter ().<br/>
 * Date: 2015年1月19日 <br/>
 * 
 * @author flb
 * @version
 * @see 逐条带标题 样式: 文字标题 文字描述--审核人--状态图片
 */
public class CheckPointExpanableListAdapter extends BaseStepCheckExpanableListAdapter implements OnClickListener {

	/**
	 * oldcheckresult:TODO(记录开始的操作结果).
	 */
	private String oldcheckresult = "oldcheckresult";

	private int currentGroup = 0;
	private int currentChild = -1;

	public CheckPointExpanableListAdapter(Context mContext,
			List<SuperEntity> mSourceValues, List<String> showAttrs,
			String pcAttr, PinnedSectionExpandableListView listView,
			String childKey) {
		super(mContext, mSourceValues, showAttrs, pcAttr, listView, childKey);
	}

	@Override
	public void nextItem() {
		if (isLastGroup(currentGroup)) { // 是最后一组，不可再加组的序列
			if (!isLastChild(currentGroup, currentChild)) { // 不是某组最后一条数据，可再加子组的序列
				SuperEntity entity;
				if (currentChild != -1) { // 避免currentChild == -1
					entity = getChild(currentGroup, currentChild);
					entity.setAttribute(isSelectedAttr, false);
					if (entity.isModified()) {
						entity.setModified(false);
						entity.setAttribute(showAttrs.get(2), -1);
					}
				}

				currentChild += 1;
				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup,
						currentChild);
				int flatListPosition = mListView
						.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			} else {
				Toast.makeText(mContext, "已经是最后一条数据", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			if (!isLastChild(currentGroup, currentChild)) { // 不是某组最后一条数据，可再加子组的序列
				SuperEntity entity;
				if (currentChild != -1) { // 避免currentChild == -1
					entity = getChild(currentGroup, currentChild);
					entity.setAttribute(isSelectedAttr, false);
					if (entity.isModified()) {
						entity.setModified(false);
						entity.setAttribute(showAttrs.get(2), -1);
					}
				}

				currentChild += 1;
				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup,
						currentChild);
				int flatListPosition = mListView
						.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			} else { // 是某组最后一组数据，要加组的序列
				SuperEntity entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, false);
				if (entity.isModified()) {
					entity.setModified(false);
					entity.setAttribute(showAttrs.get(2), -1);
				}
				currentGroup += 1;
				currentChild = 0;

				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup,
						currentChild);
				int flatListPosition = mListView
						.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			}
		}

		if (!mListView.isGroupExpanded(currentGroup)) {
			mListView.expandGroup(currentGroup);
		}
	}

	@Override
	public void previousItem() {
		if (currentGroup == 0) {
			if (currentChild > 0) {
				SuperEntity entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, false);
				if (entity.isModified()) {
					entity.setModified(false);
					entity.setAttribute(showAttrs.get(2), -1);
				}
				currentChild -= 1;

				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup,
						currentChild);
				int flatListPosition = mListView
						.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			} else if (currentChild == 0) {
				Toast.makeText(mContext, "已经是最先一条数据", Toast.LENGTH_SHORT)
						.show();
			} else { // currentChild == -1
				// 不处理
				int lastGroupPosition = getGroupCount() - 1;
				int lastChildPosition = getChildrenCount(lastGroupPosition)-1;
				SuperEntity entity = getChild(lastGroupPosition, lastChildPosition);
				entity.setAttribute(isSelectedAttr, true);
				
				currentGroup = lastGroupPosition;
				currentChild = lastChildPosition;
				
				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup, currentChild);
				int flatListPosition = mListView.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			}
		} else {
			if (currentChild > 0) {
				SuperEntity entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, false);
				if (entity.isModified()) {
					entity.setModified(false);
					entity.setAttribute(showAttrs.get(2), -1);
				}
				currentChild -= 1;

				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup, currentChild);
				int flatListPosition = mListView.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			} else if (currentChild == 0) {
				SuperEntity entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, false);
				if (entity.isModified()) {
					entity.setModified(false);
					entity.setAttribute(showAttrs.get(2), -1);
				}
				currentGroup -= 1;
				currentChild = getChildrenCount(currentGroup) - 1;

				entity = getChild(currentGroup, currentChild);
				entity.setAttribute(isSelectedAttr, true);

				mListView.setSelectedChild(currentGroup, currentChild, true);
				notifyDataSetChanged();

				long packedPositionForChild = getChildId(currentGroup,
						currentChild);
				int flatListPosition = mListView
						.getFlatListPosition(packedPositionForChild);
				mListView.setSelection(flatListPosition - 1);
			} else {
				// 不处理
			}
		}

		if (!mListView.isGroupExpanded(currentGroup)) {
			mListView.expandGroup(currentGroup);
		}
	}

	@Override
	public List<SuperEntity> getCurrentSelectedValues() {
		List<SuperEntity> temp = new ArrayList<SuperEntity>();
		// lxf添加条件
		if (currentGroup >= 0 && currentChild >= 0) {
			temp.add(getChild(currentGroup, currentChild));
		}
		return temp;
	}

	@Override
	protected int getItemResourceId() {
		return R.layout.hd_hse_component_list_item_step_check_sample_1;
	}

	@Override
	public View getItemView(int groupPosition, int childPosition, View convertView) {
		LinearLayout rootLL = ViewHolder.getView(convertView, R.id.step_check_root_ll);
		TextView descTV = ViewHolder.getView(convertView, R.id.step_check_left_tv);
		TextView personTV = ViewHolder.getView(convertView, R.id.step_check_center_tv);
		ImageView statusIV = ViewHolder.getView(convertView, R.id.step_check_right_iv);
		ImageView pcIV = ViewHolder.getView(convertView, R.id.step_check_left_iv_pc);
		
		final SuperEntity childEntity = getChild(groupPosition, childPosition);
		String desc = (String) childEntity.getAttribute(showAttrs.get(1));
		Integer status = (Integer) childEntity.getAttribute(showAttrs.get(2));
		String name = (String) childEntity.getAttribute(showAttrs.get(3));
		Integer pcStatus = (Integer) childEntity.getAttribute(pcAttr);
		boolean isSelected = (boolean) childEntity.getAttribute(isSelectedAttr);

		descTV.setTag(R.string.hd_hse_component_list_item_attribute_groud_position, groupPosition);
		descTV.setTag(R.string.hd_hse_component_list_item_attribute_child_position, childPosition);
		descTV.setTag(R.string.hd_hse_component_list_item_attribute_order, 1);
		
		statusIV.setTag(R.string.hd_hse_component_list_item_attribute_groud_position, groupPosition);
		statusIV.setTag(R.string.hd_hse_component_list_item_attribute_child_position, childPosition);
		statusIV.setTag(R.string.hd_hse_component_list_item_attribute_order, 2);
		
		personTV.setTag(R.string.hd_hse_component_list_item_attribute_groud_position, groupPosition);
		personTV.setTag(R.string.hd_hse_component_list_item_attribute_child_position, childPosition);
		personTV.setTag(R.string.hd_hse_component_list_item_attribute_order, 3);
		
		descTV.setText(desc);
		personTV.setText(name);

		//显示左侧标记
		if(pcStatus == null || pcStatus == 0){
			pcIV.setVisibility(View.GONE);
		}else{
			pcIV.setVisibility(View.VISIBLE);
		}
		
		// 对于选择结果的显示
		if (status == null || status == -1) { // 无结果数值
			statusIV.setVisibility(View.INVISIBLE);
			statusIV.setImageResource(iconResLight[0]);
		} else { // 有结果数值
			statusIV.setVisibility(View.VISIBLE);
			statusIV.setImageResource(iconResLight[status]);
		}
		
		// 设置条目背景色
		if (groupPosition == currentGroup && currentChild == childPosition) { // 当前被选中项目
			rootLL.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected_current);
			descTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
		} else { // 非当前项
			if (isSelected) { // 历史被选中的条目
				rootLL.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected);
				descTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
			} else { // 非历史选中条目
				if (childEntity.isModified()) {
					rootLL.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected);
					descTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
				} else {
					rootLL.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_normal);
					descTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_normal));
				}
			}
		}

		// 增加点击长按事件
		descTV.setOnLongClickListener(this);
		personTV.setOnLongClickListener(this);

		descTV.setOnClickListener(this);
		personTV.setOnClickListener(this);
		statusIV.setOnClickListener(this);
		
		return convertView;
	}
	
	@Override
	public void onClick(View v) {
		Integer groupPosition = (Integer) v.getTag(R.string.hd_hse_component_list_item_attribute_groud_position);
		Integer childPosition = (Integer) v.getTag(R.string.hd_hse_component_list_item_attribute_child_position);
		
		SuperEntity childEntity = getChild(groupPosition, childPosition);
		boolean isSelected = (boolean) childEntity.getAttribute(isSelectedAttr);
		if (isSelected) { // 取消某项
			childEntity.setAttribute(isSelectedAttr, false);
			currentGroup = 0;
			currentChild = -1;
		} else { // 确定某项
			if (currentGroup == 0 && currentChild == -1) {
				// 不处理
			} else {
				SuperEntity innerEntity = getChild(currentGroup, currentChild);
				innerEntity.setAttribute(isSelectedAttr, false);
				if (innerEntity.isModified()) {
					innerEntity.setModified(false);
					//innerEntity.setAttribute(showAttrs.get(2), childEntity.getAttribute(oldcheckresult));
					//lxf 2015-03-26 
					innerEntity.setAttribute(showAttrs.get(2), innerEntity.getAttribute(oldcheckresult));
				}
			}

			childEntity.setAttribute(isSelectedAttr, true);
			currentGroup = groupPosition;
			currentChild = childPosition;
		}
		notifyDataSetChanged();
	}
}