package com.hd.hse.ss.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.ss.R;
import com.hd.hse.ss.activity.SupervisionDetailActivity;
import com.hd.hse.ss.activity.SupervisionListActivity;

public class SupervisionListAdapter extends BaseAdapter {
	/**
	 * 选择状态
	 */
	public static final int SELECTSTATE = 0;
	/**
	 * 正常状态
	 */
	public static final int NORMALSTATE = 1;

	private LayoutInflater mLayoutInflater;
	private Context mContext;
	private List<WorkLogEntry> mSupervisionList;
	private List<WorkLogEntry> temp;
	private int state = 1;
	OnNetItemClickListener mOnNetItemClickListener;
	/**
	 * 标记是否被选中
	 */
	private HashMap<Integer, Boolean> marks;

	public SupervisionListAdapter(List<WorkLogEntry> mDatas,
			List<WorkLogEntry> temp, Context mContext) {
		this.mSupervisionList = mDatas;
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		marks = new HashMap<Integer, Boolean>();
		this.temp = temp;
		initMarks();
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSupervisionList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mSupervisionList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder mViewHolder = null;
		if (arg1 == null) {
			arg1 = mLayoutInflater.inflate(
					R.layout.hse_ss_phone_app_supervision_list_item, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvContent = (TextView) arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_tv_content);
			mViewHolder.tvLocation = (TextView) arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_tv_location);
			mViewHolder.tvName = (TextView) arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_tv_name);
			mViewHolder.tvTime = (TextView) arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_tv_time);
			mViewHolder.checkBox = (CheckBox) arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_checkbox);
			mViewHolder.divider = arg1
					.findViewById(R.id.hse_ss_phone_app_supervision_list_item_divider);
			arg1.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) arg1.getTag();
		}
		if (arg0 == mSupervisionList.size() - 1) {
			mViewHolder.divider.setVisibility(View.GONE);
		} else {
			mViewHolder.divider.setVisibility(View.VISIBLE);
		}
		mViewHolder.tvContent.setText(mSupervisionList.get(arg0).getFxwt());
		// 地点默认为海顿，地点没有上传
		if (mSupervisionList.get(arg0).getSddept_desc() == null
				|| "".equals(mSupervisionList.get(arg0).getSddept_desc())) {
			mViewHolder.tvLocation.setText("属地单位 : " + "空");
		} else {
			mViewHolder.tvLocation.setText("属地单位 : "
					+ mSupervisionList.get(arg0).getSddept_desc());
		}

		mViewHolder.tvName.setText("检查人 : "
				+ mSupervisionList.get(arg0).getCreatebydesc());
		mViewHolder.tvTime.setText(mSupervisionList.get(arg0).getCreatedate());
		if (state == SELECTSTATE) {
			mViewHolder.checkBox.setVisibility(View.VISIBLE);
		} else {
			mViewHolder.checkBox.setVisibility(View.GONE);
		}
		mViewHolder.checkBox.setChecked(marks.get(arg0));

		arg1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state == SELECTSTATE) {
					// 选择状态下的点击事件
					if (marks.get(arg0)) {
						marks.put(arg0, false);
						if (temp != null) {
							temp.remove(mSupervisionList.get(arg0));
						}

					} else {
						marks.put(arg0, true);
						if (temp != null) {
							temp.add(mSupervisionList.get(arg0));
						}
					}
					notifyDataSetChanged();

				} else {
					// 平常状态的的点击事件,进入现场监督编辑页
					
					if (temp==null) {
						if (mOnNetItemClickListener!=null) {
							mOnNetItemClickListener.onNetItemClick(arg0);
						}
					}else {
						Intent intent = new Intent(mContext,
								SupervisionDetailActivity.class);
						intent.putExtra(SupervisionDetailActivity.ISSAVE,
								true);
						intent.putExtra(WorkLogEntry.class.getName(),
								mSupervisionList.get(arg0));
						mContext.startActivity(intent);
					}
					
				}

			}
		});

		return arg1;
	}

	private class ViewHolder {
		private TextView tvContent;
		private TextView tvLocation;
		private TextView tvName;
		private TextView tvTime;
		private CheckBox checkBox;
		private View divider;
	}

	/**
	 * 设置为勾选状态
	 */
	public void setStateToSelect() {
		state = SELECTSTATE;
		initMarks();
		notifyDataSetChanged();
	}

	/**
	 * 设置为正常状态
	 */
	public void setStateToNormal() {
		state = NORMALSTATE;
		initMarks();
		notifyDataSetChanged();
	}

	/**
	 * 初始化标记
	 */
	private void initMarks() {
		marks.clear();
		for (int i = 0; i < mSupervisionList.size(); i++) {
			marks.put(i, false);
		}
	}
	
	public interface OnNetItemClickListener{
		void onNetItemClick(int postion);
	}
	
	public void setOnNetItemClickListener(OnNetItemClickListener mOnNetItemClickListener){
		this.mOnNetItemClickListener=mOnNetItemClickListener;
	}

	public void setmSupervisionList(List<WorkLogEntry> mSupervisionList) {
		this.mSupervisionList = mSupervisionList;
		initMarks();
	}
}
