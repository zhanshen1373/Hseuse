package com.hd.hse.common.module.phone.ui.activity;

import java.util.List;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.vp.queryinfo.QueryVpInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LocationListActivity extends BaseFrameActivity implements OnItemClickListener, IQueryCallEventListener, IEventListener{

	public static final String PARAMS = "params";
	public static final String POSITION = "position";
	
	private ListView locationList;
	private List<PositionCard> locations;
	private LocationListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_module_location_list_activity);

		//TODO 初始化ActionBar
		setCustomActionBar(true, this, setActionBarItems());
		
		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);
		
		setNavContent(getNavContentData(), "hse-vp-phone-app");
		
		//获取数据
		QueryVpInfo queryVpInfo = new QueryVpInfo();
		try {
			queryVpInfo.queryListPositionCard(this);
		} catch (HDException e) {
			e.printStackTrace();
		}
		
		locationList = (ListView) findViewById(R.id.list_location);
		locationList.setOnItemClickListener(this);
	}
	
	private String getTitileName() {
		return "虚拟位置维护";
	}

	public String[] setActionBarItems() {
		return new String[] {IActionBar.IBTN_RETURN, IActionBar.TV_TITLE};
	}

	private class LocationListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return locations == null? 0: locations.size();
		}

		@Override
		public PositionCard getItem(int position) {
			return locations.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(LocationListActivity.this, R.layout.list_item_location, null);
				holder = new ViewHolder();
				holder.desc = (TextView) convertView.findViewById(R.id.tv_location_name);
				holder.longitude = (TextView) convertView.findViewById(R.id.tv_longitude);
				holder.latitude = (TextView) convertView.findViewById(R.id.tv_latitude);
				holder.radius = (TextView) convertView.findViewById(R.id.tv_radius);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			PositionCard positionCard = getItem(position);
			holder.desc.setText(positionCard.getLocation_desc());
			holder.longitude.setText(/*String.valueOf(*/positionCard.getLongitude()/*)*/);
			holder.latitude.setText(/*String.valueOf(*/positionCard.getLatitude()/*)*/);
			holder.radius.setText(String.valueOf(positionCard.getRadiu()));
			
			return convertView;
		}
	}
	
	static class ViewHolder { 
		TextView desc;        //位置描述
		TextView longitude;   //经度
		TextView latitude;    //纬度
		TextView radius;      //半径
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PositionCard positionCard = locations.get(position);
		Intent intent = new Intent(this, LocationCardActivity.class);
		intent.putExtra(LocationCardActivity.POSITION, position);
		intent.putExtra(LocationCardActivity.PARAMS, positionCard);
		startActivityForResult(intent, 100);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 100) {
			int position = data.getIntExtra(POSITION, -1);
			PositionCard positionCard = (PositionCard) data.getSerializableExtra(PARAMS);
			if (position != -1) {
				locations.set(position, positionCard);
				adapter.notifyDataSetChanged();
			} else {
				Log.i("zink", "回传位置错误！");
			}
		}
	}
	
	@Override
	public void callBackProcess(int type, Object... objects) throws HDException {
		locations = (List<PositionCard>) objects[0];
		adapter = new LocationListAdapter();
		locationList.setAdapter(adapter);
	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * getNavContentData:(获取导航数据). <br/>
	 * date: 2015年2月6日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public List<AppModule> getNavContentData() {
		return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
	}
}
