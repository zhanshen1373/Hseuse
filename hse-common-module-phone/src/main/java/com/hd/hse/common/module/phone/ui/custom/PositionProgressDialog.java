package com.hd.hse.common.module.phone.ui.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.common.module.phone.R;

public class PositionProgressDialog implements OnClickListener, LocationListener, OnDismissListener, OnShowListener{

	private Context context;
	private AlertDialog dialog;
	private View rootView;
	private PositionCallback callback;
	
	private LocationManager locationManager;
	private Location location;
	
	private ProgressBar pgbar;
	private TextView longitudeTV;
	private TextView latitudeTV;
	private TextView saveTV;
	private TextView cancelTV;
	
	private boolean isGetLocation;
	
	public PositionProgressDialog(Context context){
		this.context = context;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		this.rootView = View.inflate(this.context, R.layout.hd_hse_common_module_position_dialog_layout, null);
		this.dialog = builder.create();
		dialog.setView(this.rootView, 0, 0, 0, 0);
		
		locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
		this.dialog.setOnShowListener(this);
		this.dialog.setOnDismissListener(this);
		
		pgbar = (ProgressBar) this.rootView.findViewById(R.id.pgbar);
		longitudeTV = (TextView) this.rootView.findViewById(R.id.tv_longitude);
		latitudeTV = (TextView) this.rootView.findViewById(R.id.tv_latitude);
		saveTV = (TextView) this.rootView.findViewById(R.id.tv_save_position);
		cancelTV = (TextView) this.rootView.findViewById(R.id.tv_cancel_position);
		
		saveTV.setOnClickListener(this);
		cancelTV.setOnClickListener(this);
	}
	
	public boolean isShowing(){
		if(this.dialog != null){
			return this.dialog.isShowing();
		}
		return false;
	}
	
	public void show(){
		if(this.dialog != null){
			this.dialog.show();
		}
	}
	
	public void dimiss(){
		if(this.dialog != null){
			this.dialog.dismiss();
		}
	}
	
	public interface PositionCallback{
		public void onCancel();
		public void onSaveData(Location location);
	}
	
	public void setPositionCallback(PositionCallback callback){
		this.callback = callback;
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if(vid == R.id.tv_cancel_position){
			if(this.dialog.isShowing()){
				if(this.callback != null){
					this.callback.onCancel();
				}
				this.dialog.dismiss();
				this.location = null;
			}
		} else if(vid == R.id.tv_save_position){
			if(this.callback != null){
				this.callback.onSaveData(this.location);
			}
			this.dialog.dismiss();
			this.location = null;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		if(!isGetLocation){
			this.pgbar.setVisibility(View.VISIBLE);
		} else {
			this.pgbar.setVisibility(View.INVISIBLE);
		}
		
		if(loc != null){
			this.isGetLocation = true;
			this.location = loc;
			this.longitudeTV.setText(String.valueOf(this.location.getLongitude()));
			this.latitudeTV.setText(String.valueOf(this.location.getLatitude()));
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this.context, provider + "GPS不可用", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onShow(DialogInterface dialog) {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		locationManager.removeUpdates(this);
	}
}
