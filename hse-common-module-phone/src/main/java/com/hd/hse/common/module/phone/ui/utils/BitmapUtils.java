/**
 * Project Name:hse-common-module
 * File Name:BitmapUtils.java
 * Package Name:com.hd.hse.common.module.ui.utils
 * Date:2015年1月26日
 * Copyright (c) 2015, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

/**
 * ClassName:BitmapUtils (图片工具).<br/>
 * Date: 2015年1月26日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class BitmapUtils {

	/**
	 * decodeBitmap:(图片压缩). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author lg
	 * @param resources
	 * @param resId
	 * @return
	 */
	public static BitmapDrawable decodeBitmap(Resources resources, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = resources.openRawResource(resId);
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return new BitmapDrawable(resources, bitmap);
	}
}
