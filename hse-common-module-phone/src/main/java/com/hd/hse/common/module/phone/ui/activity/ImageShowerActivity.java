package com.hd.hse.common.module.phone.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.hd.hse.common.component.phone.util.ImageLoader;
import com.hd.hse.common.component.phone.util.ImageLoader.ImageCallback;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.module.phone.R;

public class ImageShowerActivity extends FragmentActivity {
	public static final String HEADIMG = "headimage";
	public static final String FROM_HTTP = "fromHttp";
	private ImageView img;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.hd_hse_common_module_imageshoweractivity_layout);
		img = (ImageView) findViewById(R.id.hd_hse_common_module_imageshoweractivity_layout_img);
		String path = getIntent().getStringExtra(HEADIMG);
		boolean fromHttp = getIntent().getBooleanExtra(FROM_HTTP, false);
		if (fromHttp) {
			dialog = new ProgressDialog(ImageShowerActivity.this);
			dialog.setMessage("正在加载文件...");
			dialog.show();
			ImageLoader loader = new ImageLoader();
			//loader.setBg(path, img, ImageShowerActivity.this);
			loader.setBitmap(path, new ImageCallback() {
				
				@Override
				public void onImageLoaded(Bitmap bmp) {
					//img.setLayoutParams(getLayoutParams(bmp));
					img.setImageBitmap(bmp);
					dialog.cancel();
				}
				
				@Override
				public void onError() {
					dialog.cancel();
					ToastUtils.toast(ImageShowerActivity.this, "图片加载失败");
				}
			});
			

		} else {
			Bitmap bmp = BitmapFactory.decodeFile(path);
			//img.setLayoutParams(getLayoutParams(bmp));
			img.setImageBitmap(bmp);
		}
	}
	
	/*private LayoutParams getLayoutParams(Bitmap bmp){
		int height = bmp.getHeight();
		int width = bmp.getWidth();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int screenWidth = metric.widthPixels;
		int imgHeight = (int) (screenWidth * 1.0 / width * height);
		LayoutParams mLayoutParams = new LayoutParams(screenWidth,
				imgHeight);
		return mLayoutParams;
	}*/
}
