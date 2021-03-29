/**
 * Project Name:hse-common-component-phone
 * File Name:WHFHZBListView.java
 * Package Name:com.hd.hse.common.component.custom
 * Date:2015年1月6日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import java.util.List;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.entity.SuperEntity;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * ClassName:WHFHZBListView ().<br/>
 * Date: 2015年1月6日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class WHFHZBListView extends ListView {

	private final String TAG = "WHFHZBListView";
	private final boolean DEBUG = true;

	// 要显示的数据 集合
	private List<? extends SuperEntity> mData;

	// 　实体类的 key
	private String mPcStateKey;

	// 　实体类的 key
	private String mPadStateKey;

	// 　实体类的 key
	private String mShowTextKey;

	private Adapter mAdapter;

	public WHFHZBListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WHFHZBListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WHFHZBListView(Context context) {
		super(context);
		init();
	}

	private void init() {
		mAdapter = new Adapter();
		this.setAdapter(mAdapter);
		this.setSelector(R.drawable.blank_selector);
		this.setOnItemLongClickListener(new ItemLongClickListenerImpl());
	}

	/**
	 * 设置要显示的数据，
	 * 
	 * setData:(). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author xuxinwen
	 * @param data
	 */
	public void setData(List<? extends SuperEntity> data, String showTextKey,
			String pcStateKey, String padStateKey) {
		if (data == null) {
			return;
		}

		if (showTextKey == null || pcStateKey == null || padStateKey == null) {
			// data 不为 null ， 但是 key 为空，抛异常。
			throw new IllegalArgumentException(
					"showTextKey, pcStateKey or padStateKey can`t be null !!!");
		}

		this.mShowTextKey = showTextKey;
		this.mPcStateKey = pcStateKey;
		this.mPadStateKey = padStateKey;
		this.mData = data;

		// 更新界面.
		 mAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean performItemClick(View view, int position, long id) {

		if (DEBUG) {
			// TODO
			Log.d(TAG, "performItemClick");
		}

		// 覆写这个方法，在中间添加 点击 item 的背景切换效果。
		SuperEntity item = mData.get(position);

		Integer pcState = (Integer) item.getAttribute(mPcStateKey);
		Integer padState = (Integer) item.getAttribute(mPadStateKey);

		if (pcState != null && pcState == 1 && padState == null) {
			// 不可选状态。 什么也不做
			if (DEBUG) {
				// TODO
				Log.d(TAG, "不可选状态。 什么也不做");
			}
		} else {
			// 可选状态。
			if (padState != null && padState == 1) {
				// 已选中状态。将实体中信息设置为未选中状态，并且更改背景为未选中状态。
				if (DEBUG) {
					// TODO
					Log.d(TAG, "已选中状态。将实体中信息设置为未选中状态，并且更改背景为未选中状态。");
				}
				item.setAttribute(mPadStateKey, 0);

				setItemAppearance(view, ITEM_STATE_NORMAL);
			} else {
				// 未选中状态。 将实体中信息设置为选中状态，并且更改背景为选中状态。
				if (DEBUG) {
					// TODO
					Log.d(TAG, "未选中状态。 将实体中信息设置为选中状态，并且更改背景为选中状态。");
				}
				item.setAttribute(mPadStateKey, 1);

				setItemAppearance(view, ITEM_STATE_SELECED);
			}

			// 状态肯定被更改了。
			item.setModified(true);
		}

		return super.performItemClick(view, position, id);
	};

	/**
	 * 设置某个 item 的样式。 setItemAppearance:(). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author xuxinwen
	 * @param v
	 * @param appearance
	 */
	private void setItemAppearance(View v, int appearance) {
		setItemAppearance((ViewHolder) v.getTag(), appearance);
	}

	class Adapter extends BaseAdapter {

		public int getCount() {
			if (mData != null) {
				return mData.size();
			}

			return 0;
		}

		public Object getItem(int position) {
			if (mData != null) {
				return mData.get(position);
			}
			return null;
		}

		public long getItemId(int position) {
			// TODO
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO
			SuperEntity item = mData.get(position);
			ViewHolder holder = null;

			if (convertView == null) {
				convertView = View
						.inflate(
								getContext(),
								R.layout.hd_hse_common_component_phone_whfhzb_list_item,
								null);

				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(R.id.hse_common_component_phone_whfhzb_list_item_text);
				holder.icon = (CheckBox) convertView
						.findViewById(R.id.hse_common_component_phone_whfhzb_list_item_icon);
				holder.bg = (View) convertView
						.findViewById(R.id.hse_common_component_phone_whfhzb_list_item_bg);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 状态样式的设置

			// -------------------------------------------------------------------

			// 得到该 item 的状态信息。
			Integer pcState = (Integer) item.getAttribute(mPcStateKey);
			Integer padState = (Integer) item.getAttribute(mPadStateKey);

			if (pcState != null && pcState == 1 && padState == null) {
				// 不可选状态。 TODO
				if (DEBUG) {
					// TODO
					Log.d(TAG, "getView:不可选状态");
				}
				setItemAppearance(holder, ITEM_STATE_DISABLE);
			} else {
				// 可选状态。
				if (padState != null && padState == 1) {
					// 已选中状态。
					if (DEBUG) {
						// TODO
						Log.d(TAG, "getView:已选中状态。");
					}
					setItemAppearance(holder, ITEM_STATE_SELECED);
				} else {
					// 未选中状态。
					if (DEBUG) {
						// TODO
						Log.d(TAG, "getView:未选中状态。");
					}
					setItemAppearance(holder, ITEM_STATE_NORMAL);
				}
			}
			// end
			// 状态样式的设置-------------------------------------------------------------------

			// 设置文字
			holder.text.setText((String) item.getAttribute(mShowTextKey));

			return convertView;
		}

	}

	private class ViewHolder {
		View bg;
		TextView text;
		CheckBox icon;
	}

	/**
	 * 设置某个 item 项的样式。 setItemAppearance:(). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author xuxinwen
	 * @param holder
	 * @param appearance
	 */
	private void setItemAppearance(ViewHolder holder, int appearance) {
		// TODO 更改图片
		switch (appearance) {
		case ITEM_STATE_NORMAL:
			holder.bg
					.setBackgroundResource(R.drawable.hd_hse_common_component_phone_whfhzb_item_normal_bg);
			holder.icon.setVisibility(View.GONE);
			break;

		case ITEM_STATE_SELECED:
			holder.bg
					.setBackgroundResource(R.drawable.hd_hse_common_component_phone_whfhzb_item_selected_bg);
			holder.icon.setVisibility(View.VISIBLE);
			holder.icon.setChecked(true);
			break;

		case ITEM_STATE_DISABLE:
			holder.bg
					.setBackgroundResource(R.drawable.hd_hse_common_component_phone_whfhzb_item_normal_bg);
			holder.icon.setVisibility(View.VISIBLE);
			holder.icon.setChecked(false);
			break;
		}
	}

	// 未选状态。
	private final static int ITEM_STATE_NORMAL = 0;
	// 选中状态
	private final static int ITEM_STATE_SELECED = 1;
	// 不可选状态。
	private final static int ITEM_STATE_DISABLE = 2;

	class Holder {
		public TextView cancle;
		public TextView confirm;
		public RelativeLayout container;
	}

	// 显示的 dialog
	private Dialog dialog;

	private View vDialog;

	// 悬浮窗的 EditText
	private BracketEditText floatEditText;

	/**
	 * item 的长按监听，主要实现了长按某 item 的时候如果该item 的文字描述没有被完全显示出来
	 * 的情况会弹出悬浮框，将完整的文字内容显示出来。
	 * 
	 * ClassName: onItemLongClickListenerImpl ()<br/>
	 * date: 2014年9月24日 <br/>
	 * 
	 * @author xuxinwen
	 * @version SelectableGridView
	 */
	class ItemLongClickListenerImpl implements OnItemLongClickListener {

		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// 主要处理 悬浮窗。
			Holder _holder = null;
			SuperEntity item = mData.get(position);
			String text = (String) item.getAttribute(mShowTextKey);

			// TODO 可以优化的地方。
			floatEditText = new BracketEditText(getContext());
			floatEditText.setTextSize(getResources().getDimension(
					R.dimen.hd_common_textsize));

			floatEditText.setBackgroundDrawable(null);
			floatEditText.setHasBracketsText(text);

			if (vDialog == null) {
				_holder = new Holder();
				vDialog = View
						.inflate(
								getContext(),
								R.layout.hd_hse_common_component_step_check_text_dialog,
								null);
				_holder.container = (RelativeLayout) vDialog
						.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_rl_container);
				_holder.confirm = (TextView) vDialog
						.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_confirm);
				_holder.cancle = (TextView) vDialog
						.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_cancel);

				vDialog.setTag(_holder);

			}

			if (dialog == null) {
				dialog = new Builder(getContext()).setView(vDialog).create();
			}

			_holder = (Holder) vDialog.getTag();
			_holder.container.removeAllViews();
			_holder.container.addView(floatEditText);

			_holder.cancle.setOnClickListener(new ClickListenerImpl(null));
			_holder.confirm.setOnClickListener(new ClickListenerImpl(item));

			dialog.show();

			return true;

		}

	}

	class ClickListenerImpl implements View.OnClickListener {

		private SuperEntity selectedItem;

		public ClickListenerImpl(SuperEntity selectedItem) {
			this.selectedItem = selectedItem;
		}

		public void onClick(View v) {
			if (selectedItem == null) {
			} else {
				if (floatEditText != null) {
					// 将更改后的数据储存到 实体类中。
					selectedItem.setAttribute(mShowTextKey, floatEditText
							.getText().toString());
					selectedItem.setModified(true);
					mAdapter.notifyDataSetChanged();
				}
			}
			if (dialog != null) {
				InputMethodManager imm = (InputMethodManager) getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(floatEditText.getWindowToken(), 0);
				dialog.dismiss();
				floatEditText = null;
			}

		}
	}
}
