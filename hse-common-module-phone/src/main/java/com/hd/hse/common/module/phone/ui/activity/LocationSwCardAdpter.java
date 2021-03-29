package com.hd.hse.common.module.phone.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.common.PositionCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 位置卡缓存的adapter
 * @author yn
 *
 */
public class LocationSwCardAdpter extends BaseAdapter{
	private List<PositionCard>positionCardList;
	private Context context;
	private LayoutInflater mLayoutInflater;
	public LocationSwCardAdpter(List<PositionCard>positionCardList,Context context){
		this.positionCardList=positionCardList;
		this.context=context;
		this.mLayoutInflater=LayoutInflater.from(context);
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return positionCardList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return positionCardList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if (convertView==null) {
			vh=new ViewHolder();
			convertView=mLayoutInflater.inflate(R.layout.hd_hse_common_module_phone_swingcard_location_cache_item, null);
			vh.tv=(TextView) convertView.findViewById(R.id.hd_hse_common_module_phone_swingcard_location_cache_item_tv);
			convertView.setTag(vh);
		}else {
			vh=(ViewHolder) convertView.getTag();
		}
		vh.tv.setText(positionCardList.get(position).getLocation_desc());
		return convertView;
	}
	class ViewHolder{
		private TextView tv;
	}

}
