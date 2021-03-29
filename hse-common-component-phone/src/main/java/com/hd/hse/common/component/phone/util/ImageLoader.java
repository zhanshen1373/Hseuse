package com.hd.hse.common.component.phone.util;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {
	public Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	private ExecutorService executorService = Executors.newFixedThreadPool(5); // 固定五个线程来执行任务
	private final Handler handler = new Handler();

	public void setBitmap(final String imageUrl,
			final ImageCallback mImageCallback) {
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				if (mImageCallback != null) {
					BitmapDrawable bd = (BitmapDrawable) softReference.get();
					Bitmap bmp = bd.getBitmap();
					mImageCallback.onImageLoaded(bmp);
					return;
				}

			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					int position=imageUrl.lastIndexOf("/");
					String oldName=imageUrl.substring(position+1, imageUrl.length());
					String newName=URLEncoder.encode(oldName, "utf-8");
					String headUrl=imageUrl.substring(0, position+1);
					String url=headUrl+newName;
					
					final Drawable drawable = Drawable.createFromStream(
							new URL(url).openStream(), "image.png");
					
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));

					handler.post(new Runnable() {
						public void run() {
							// 验证
							if (mImageCallback != null) {
								BitmapDrawable bd = (BitmapDrawable) drawable;
								Bitmap bmp = bd.getBitmap();
								mImageCallback.onImageLoaded(bmp);

							}
						}
					});
				} catch (Exception e) {
					if (mImageCallback != null) {
						mImageCallback.onError();
					}
					Log.e("error", "error");
					throw new RuntimeException(e);

				}
			}
		});
	}
	
	public void setBg(final String imageUrl, final ImageView img) {
		// 设置tag
		img.setTag(imageUrl);
		//img.setImageResource(R.drawable.hd_hse_common_component_phone_default_photo);
		// 如果缓存过就从缓存中取出数据
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			if (softReference.get() != null) {
				if (imageUrl.equals((String) img.getTag())) {
					img.setImageDrawable(softReference.get());
					return;
				}
			}
		}
		// 缓存中没有图像，则从网络上取出数据，并将取出的数据缓存到内存中
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(imageUrl).openStream(), "image.png");

					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));

					handler.post(new Runnable() {
						public void run() {
							// 验证
							if (imageUrl.equals((String) img.getTag())) {
								img.setImageDrawable(drawable);
							}
						}
					});
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	/*
	 * // 从网络上取数据方法 protected Drawable loadImageFromUrl(String imageUrl) { try {
	 * return Drawable.createFromStream(new URL(imageUrl).openStream(),
	 * "image.png"); } catch (Exception e) { throw new RuntimeException(e); } }
	 */

	// 对外界开放的回调接口
	public interface ImageCallback {
		// 注意 此方法是用来设置目标对象的图像资源
		public void onImageLoaded(Bitmap bmp);

		public void onError();
	}

	public void rec() {
		for (int i = 0; i < imageCache.size(); i++) {
			SoftReference<Drawable> drawable = imageCache.get(i);
			// drawable.clear();

		}
	}
}
