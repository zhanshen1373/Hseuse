package com.hd.hse.common.module.phone.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.common.Image;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ClassName: PhotoPreviewActivity (照片浏览)<br/>
 * date: 2015年1月8日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class PhotoPreviewActivity extends FragmentActivity {

	public static final String SELECTEDINDEX = "selectedindex";
	public static final String IMAGESET = "imageset";
	private static Logger logger = LogUtils.getLogger(PhotoPreviewActivity.class);
	// 当前预览的照片
	private int currentIndex = 0;
	// 照片List
	private static ArrayList<Image> imageList = null;

	private static ViewPager mViewPager;

	private CustomPageAdapter adapter;

	private static RadioGroup rg;

	public PhotoPreviewActivity() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hd_hse_common_module_phone_activity_photo_preview);

		Intent intent = getIntent();
		currentIndex = intent.getIntExtra(SELECTEDINDEX, 0);
		// 显示的全部照片
		imageList = (ArrayList<Image>) intent.getSerializableExtra(IMAGESET);

		mViewPager = (ViewPager) findViewById(R.id.hd_hse_common_module_phone_photo_priview_vp);
		adapter = new CustomPageAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(currentIndex);
		// 一次加载两张图片
		mViewPager.setOffscreenPageLimit(2);
		rg = (RadioGroup) findViewById(R.id.hd_hse_common_module_phone_photo_rg);
		addRadioGroupView();
	}

	public class CustomPageAdapter extends FragmentPagerAdapter {

		public CustomPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return ArrayListFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageList.size();
		}

	}

	public static class ArrayListFragment extends Fragment {

		private int position;

		public static ArrayListFragment newInstance(int position) {
			ArrayListFragment alf = new ArrayListFragment();

			Bundle args = new Bundle();
			args.putInt("position", position);
			alf.setArguments(args);

			return alf;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			position = getArguments() != null ? getArguments().getInt(
					"position") : 0;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(
					R.layout.hd_hse_common_module_phone_photo_priview_iv,
					null);

			ImageView iv = (ImageView) view
					.findViewById(R.id.hd_hse_common_module_phone_photo_preview_item_iv);

			Image image = imageList.get(position);
			String path = image.getImagepath();
			int rotation = 0;
			try {
				ExifInterface exifInterface = new ExifInterface(path);
				String orientation = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION);
				switch (Integer.valueOf(orientation)) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotation = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotation = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotation = 270;
					break;
				default:
					break;
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			iv.setRotation(rotation);
			iv.setImageBitmap(bitmap);
			return view;
		}
	}

	/**
	 * addRadioGroupView:(添加RadioGroup). <br/>
	 * date: 2015年1月9日 <br/>
	 * 
	 * @author wenlin
	 */
	public void addRadioGroupView() {
		for (int i = 0; i < imageList.size(); i++) {
			final RadioButton rb = new RadioButton(getBaseContext());
			rb.setId(i);
			rb.setLayoutParams(new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT));
			rb.setPadding(1, 1, 1, 1);
			rb.setTag(i);
			rb.setButtonDrawable(R.drawable.hd_hse_main_phone_app_dot_bg);
			rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked) {
						mViewPager.setCurrentItem((Integer) rb.getTag());
					}
				}
			});
			rg.addView(rb);
			if (imageList.size() == 1) {
				rg.setVisibility(View.GONE);
			}
		}
		// 显示当前打开的图片的所在位置点
		rg.check(currentIndex);
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// 划屏时将下一次张图片的RG所在位置点亮
						((RadioButton) rg.getChildAt(position))
								.setChecked(true);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
					}
				});
	}
}
