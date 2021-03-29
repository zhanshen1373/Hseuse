package com.hd.hse.osc.phone.ui.adapter.remoteappr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.osc.phone.R;

import java.util.List;
import java.util.Map;

public class SelectPersonAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<PersonCard> personCards;
	private Map<Integer, Boolean> checkMap;

	@SuppressLint("NewApi")
	public SelectPersonAdapter(Context context, List<PersonCard> personCards) {
		this.context = context;
		layoutInflater = layoutInflater.from(context);
		this.personCards = personCards;
		checkMap = new ArrayMap<Integer, Boolean>();
		for (int i = 0; i < personCards.size(); i++) {
			checkMap.put(i, false);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return personCards.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return personCards.get(position);
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
			convertView = layoutInflater.inflate(
					R.layout.hd_hse_common_module_phone_select_person_item,
					null);
			viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.hd_hse_common_module_phone_select_person_item_checkbox);
			viewHolder.tvCode = (TextView) convertView
					.findViewById(R.id.hd_hse_common_module_phone_select_person_item_tv_code);
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.hd_hse_common_module_phone_select_person_item_tv_name);
			viewHolder.tvDept = (TextView) convertView
					.findViewById(R.id.hd_hse_common_module_phone_select_person_item_tv_dept);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.checkBox.setChecked(checkMap.get(position));
		viewHolder.tvCode.setText(personCards.get(position).getPersonid());
		viewHolder.tvName.setText(personCards.get(position).getPersonid_desc());
		viewHolder.tvDept.setText(personCards.get(position).getDepartment_desc());
		return convertView;
	}

	public Boolean getCheckBoxChecked(int position) {
		if (checkMap.containsKey(position)) {
			return checkMap.get(position);
		}
		return null;
	}

	public void setCheckBoxChecked(int position, boolean checked) {
		checkMap.put(position, checked);
	}

	public Map<Integer, Boolean> getCheckMap() {
		return checkMap;
	}

	public class ViewHolder {
		CheckBox checkBox;//
		TextView tvCode;// 员工编码
		TextView tvName;// 员工名字
		TextView tvDept;// 部门

	}

	public void setOtherItemFalse(int position) {
		for (int i = 0; i < personCards.size(); i++) {
			if (i != position) {
				checkMap.put(i, false);
			}
		}

	}
}
