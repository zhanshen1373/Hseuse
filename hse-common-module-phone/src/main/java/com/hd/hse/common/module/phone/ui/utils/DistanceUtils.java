/**
 * Project Name:virtualPosittionCard
 * File Name:DistanceUtils.java
 * Package Name:com.ushayden.virtualposittioncard
 * Date:2015��3��25��
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.utils;
import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;

import android.location.Location;

/**
 * ClassName:DistanceUtils ().<br/>
 * 
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class DistanceUtils {
	private static final double EARTH_RADIUS = 6378137;

	private static Logger logger = LogUtils.getLogger(LocationSwCard.class);
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		
		
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		
		logger.error("getDistance:"+s);
		return s;
	}
}
