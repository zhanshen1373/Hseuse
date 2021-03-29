/**
 * Project Name:virtualPosittionCard
 * File Name:GPSStateUtil.java
 * Package Name:com.ushayden.virtualposittioncard
 * Date:2015��3��26��
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * ClassName:GPSStateUtil ().<br/>
 * Date:     2015年3月26日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class GPSStateUtil {
	public static boolean isGPSOpen(Context context){
		LocationManager alm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return true;
        }
		return false;
	}
	
	public static void openGPS(Activity context){
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivityForResult(intent,0);
	}
}

