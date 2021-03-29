package com.hd.hse.common.component.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

/**
 * TODO 弹出详细信息列表信息 ClassName: HSEDialogAdapter ()<br/>
 * date: 2015年3月25日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class HSEDialogAdapter extends BaseAdapter {

	private Context context;
	private SuperEntity superEntity;
	private LayoutInflater inflater;
	/**
	 * arrayDesc:TODO(字段描述).
	 */
	public String[] arrayDesc = null;
	/**
	 * arrayNum:TODO(字段编码).
	 */
	public String[] arrayNum = null;

	public HSEDialogAdapter() {
		// TODO Auto-generated constructor stub
	}

	public HSEDialogAdapter(Context context, SuperEntity superEntity,
			Boolean isDjLx) {
		this.context = context;
		this.superEntity = superEntity;
		this.inflater = LayoutInflater.from(context);
		// 判断是否走一票制
		IQueryRelativeConfig relation = new QueryRelativeConfig();
		RelationTableName relationEntity = new RelationTableName();
		relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
		boolean iszysqofone = relation.isHadRelative(relationEntity);
		if (isDjLx) {
			if (iszysqofone) {
				// 一票制
				arrayDesc = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_workorder_desc_for_ypz);
				arrayNum = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_workorder_num_for_ypz);
			} else {
				arrayDesc = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_workorder_desc);
				arrayNum = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_workorder_num);
			}
		} else {
			if (iszysqofone) {
				arrayDesc = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_worktask_desc_for_ypz);
				arrayNum = context.getResources().getStringArray(
						R.array.hd_hse_common_component_phone_dytable_worktask_num_for_ypz);
			} else {
				arrayDesc = context
						.getResources()
						.getStringArray(
								R.array.hd_hse_common_component_phone_dytable_worktask_desc);
				arrayNum = context.getResources().getStringArray(
						R.array.hd_hse_common_component_phone_dytable_worktask_num);
			}
		}
	}

	@Override
	public int getCount() {
		return arrayNum.length;
	}

	@Override
	public Object getItem(int position) {
		return arrayNum[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.hd_hse_common_component_dialog_detailinfo, null);
			viewHolder.tv01 = (TextView) convertView
					.findViewById(R.id.hd_hse_common_component_dialog_tv01);
			viewHolder.tv02 = (TextView) convertView
					.findViewById(R.id.hd_hse_common_component_dialog_tv02);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 设置字段描述
		//viewHolder.tv01.setText(arrayDesc[position].toString());
		// 设置字段值
		viewHolder.tv02
				.setText(superEntity.getAttribute(arrayNum[position]) != null ? superEntity
						.getAttribute(arrayNum[position]).toString() : "（空）");
		return convertView;
	}

	class ViewHolder {
		TextView tv01;
		TextView tv02;
	}

	public void setCurentSuperEntity(SuperEntity superEntity) {
		this.superEntity = superEntity;
		this.notifyDataSetChanged();
	}
}
