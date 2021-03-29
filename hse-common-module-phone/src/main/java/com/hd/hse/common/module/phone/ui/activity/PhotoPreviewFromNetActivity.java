package com.hd.hse.common.module.phone.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.hd.hse.common.component.phone.util.ImageLoader;
import com.hd.hse.common.module.phone.R;

@SuppressLint("ValidFragment") 
public class PhotoPreviewFromNetActivity extends FragmentActivity {
	public static final String SELECTEDINDEX = "selectedindex";
	public static final String IMAGESET = "imageset";
	public static final String IMAGELOADER = "imageloader";
	//private static Logger logger = LogUtils.getLogger(PhotoPreviewFromNetActivity.class);
	// 当前预览的照片
	private int currentIndex = 0;
	// 照片List
	// private ArrayList<PhotoEntity> mPhotoEntitys;
	private String[] imgUrl;

	private static ViewPager mViewPager;

	private CustomPageAdapter adapter;

	private static RadioGroup rg;

	ArrayListFragment alf;
	private ImageLoader mImageLoader;
	public PhotoPreviewFromNetActivity() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hd_hse_common_module_phone_activity_photo_preview);
		
		mImageLoader = new ImageLoader();
		Intent intent = getIntent();
		currentIndex = intent.getIntExtra(SELECTEDINDEX, 0);
		// 显示的全部照片
		imgUrl = intent.getStringArrayExtra(IMAGESET);
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
			return new ArrayListFragment(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgUrl.length;
		}

	}

	public class ArrayListFragment extends Fragment {

		private int position;
		private ImageView iv;
		
		private String url;
		public ArrayListFragment(){
			
		}
		
		public ArrayListFragment(int position) {
			this.position = position;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(
					R.layout.hd_hse_common_module_phone_photo_priview_iv, null);

			iv = (ImageView) view
					.findViewById(R.id.hd_hse_common_module_phone_photo_preview_item_iv);
			url = imgUrl[position];
			
			//mImageLoader.setBg(url, iv);
			iv.setImageResource(R.drawable.hd_hse_common_component_phone_default_photo);
			mImageLoader.setBg(url, iv);
				
			
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
		for (int i = 0; i < imgUrl.length; i++) {
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
			if (imgUrl.length == 1) {
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
