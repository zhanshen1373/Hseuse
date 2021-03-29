package com.hd.hse.osc.phone.ui.activity.tempelectricity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleDevice;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;

public class TempEleChoiceListActivity extends BaseFrameActivity implements IEventListener {
	private ListView mListView;
	private TextView saveButton;
	private WorkOrder mOrder;
	private List<TempEleDevice> mDevices;
	private List<TempEleAsset> choiceDevices;
	public static final String WORK_ORDER = "workorder";
	public static final String DEVICE = "device";
	public static final String CHOICE_DEVICE = "choicedevice";
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_scence_tmepele_choice_list_activity);
		// 初始化ActionBar
		setCustomActionBar(false, this, setActionBarItems());
		// 设置导航栏标题
		setActionBartitleContent("用电设备清单", false);
		mListView = (ListView) findViewById(R.id.listview);
		saveButton = (TextView) findViewById(R.id.save_btn);
		mOrder = (WorkOrder) getIntent().getSerializableExtra(WORK_ORDER);
		mDevices = (List<TempEleDevice>) getIntent().getSerializableExtra(DEVICE);
		choiceDevices = (List<TempEleAsset>) getIntent().getSerializableExtra(CHOICE_DEVICE);
		
		if (mDevices == null) {
			mDevices = new ArrayList<>();
		}
		if (choiceDevices == null) {
			choiceDevices = new ArrayList<>();
		}
		for (int i = 0; i < mDevices.size(); i++) {
			mDevices.get(i).setChoiced(false);
			for (int j = 0; j < choiceDevices.size(); j++) {
				if (mDevices.get(i).getUd_zyxk_ydsbid() == choiceDevices.get(j).getUd_zyxk_ydsbid()) {
					mDevices.get(i).setChoiced(true);
					//把这个从choiceDevices移除
					choiceDevices.remove(j);
					break;
				}
			}
		}
		mListView.setAdapter(new MyListAdapter());
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//choiceDevices.clear();
				for (TempEleDevice device : mDevices) {
					if (device.isChoiced()) {
						if (device.getCount() == null || device.getCount() == 0) {
							device.setCount(1);
						}
						TempEleAsset asset = new TempEleAsset();
						asset.setUd_zyxk_ydsbid(device.getUd_zyxk_ydsbid());
						asset.setUd_zyxk_zysqid(mOrder.getUd_zyxk_zysqid());
						asset.setAssetname(device.getAssetname());
						asset.setVersion(device.getVersion());
						asset.setCount(device.getCount());
						asset.setFh(device.getFh());
						choiceDevices.add(asset);
					} else {
						device.setCount(0);
					}
				}
				Intent data = getIntent();
				data.putExtra(DEVICE, (Serializable) mDevices);
				data.putExtra(CHOICE_DEVICE, (Serializable) choiceDevices);
				setResult(RESULT_OK, data);
				finish();
			}
		});
	}
	
	private String[] setActionBarItems() {
		return new String[] { IActionBar.TV_BACK, IActionBar.TV_TITLE };
	}
	
	private class MyListAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mDevices.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mDevices.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup viewGroup) {
			ViewHolder holder = null;
			if (view == null) {
				view = LayoutInflater.from(TempEleChoiceListActivity.this).inflate(R.layout.hd_hse_scence_tempele_choice_list_item, viewGroup, false);
				holder = new ViewHolder();
				holder.itemView = view.findViewById(R.id.item_view);
				holder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
				holder.deviceNameTxt = (TextView) view.findViewById(R.id.devicename_txt);
				holder.modelTxt = (TextView) view.findViewById(R.id.model_txt);
				holder.powerTxt = (TextView) view.findViewById(R.id.power_txt);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.deviceNameTxt.setText(mDevices.get(position).getAssetname());
			holder.modelTxt.setText(mDevices.get(position).getVersion());
			BigDecimal b = new BigDecimal(mDevices.get(position).getFh());
			b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
			double d = b.doubleValue();
			holder.powerTxt.setText(d + "");
//			boolean isChecked = false;
//			for (TempEleDevice device : choiceDevices) {
//				if (device.getVersion().equals(mDevices.get(position).getVersion())) {
//					isChecked = true;
//					break;
//				}
//			}
//			holder.checkBox.setChecked(isChecked);
//			mDevices.get(position).setChoiced(isChecked);
			holder.checkBox.setChecked(mDevices.get(position).isChoiced());
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (mDevices.get(position).isChoiced()) {
						mDevices.get(position).setChoiced(false);
						((CheckBox)arg0.findViewById(R.id.checkbox)).setChecked(false);
					} else {
						mDevices.get(position).setChoiced(true);
						((CheckBox)arg0.findViewById(R.id.checkbox)).setChecked(true);
					}
				}
			});
			return view;
		}
		
		private class ViewHolder {
			View itemView;
			CheckBox checkBox;
			TextView deviceNameTxt;
			TextView modelTxt;
			TextView powerTxt;
		}
		
	}

	@Override
	public void eventProcess(int eventType, Object... objects)
			throws HDException {
		// TODO Auto-generated method stub
		
	}
}
