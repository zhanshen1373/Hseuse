package com.hd.hse.common.component.phone.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.common.Image;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

public class DownLoadImage {
	private Handler handler=new Handler();
	private String iPAndPort;

	public DownLoadImage() {
		QueryWorkInfo queryWorkInfo = new QueryWorkInfo();
		try {
			iPAndPort = "http://" + queryWorkInfo.queryIPAndPort(null)
					+ "/";
		} catch (HDException e) {
			e.printStackTrace();
		}
	}
	public void downloadImage(final ArrayList<Image> images,final ImageCallback callback){
		if (null!=images&&images.size()>0) {
			new Thread(){
				public void run() {
					final Bitmap bmp=getBitmapFromNet(iPAndPort+images.get(0).getImagepath());
					if (bmp!=null) {
						handler.post(new Runnable() {
							@Override
							public void run() {
								callback.onImageLoaded(bmp, images.get(0));
								images.remove(0);
								downloadImage(images, callback);
							}
						});
					}else {
						handler.post(new Runnable() {
							@Override
							public void run() {
								callback.onImageLoadErr(images.get(0));
								images.remove(0);
								downloadImage(images, callback);
							}
						});
					}
				};
			}.start();
		}else {
			callback.onImageAllLoaded();
		}
		
	}
	
	
	/**
	 * 从网络上取数据方法
	 * 
	 * @param imageUrl
	 * @return
	 */
	private Bitmap getBitmapFromNet(String imageUrl) {
		Bitmap bmp = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection mHttpURLConnection = (HttpURLConnection) url
					.openConnection();
			InputStream is = mHttpURLConnection.getInputStream();
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			bmp=null;
			e.printStackTrace();
			
		}
		return bmp;
	}
	
	/**
	 * 对外界开放的回调接口
	 * 
	 */
	public interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void onImageLoaded(Bitmap bmp, Image img);

		public void onImageLoadErr(Image img);
		public void onImageAllLoaded();
	}
}
