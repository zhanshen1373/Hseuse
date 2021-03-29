package com.hd.hse.common.module.phone.ui.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.adapter.PhotoWallAdapter;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.HSEActionBar;
import com.hd.hse.common.component.phone.custom.HseActionBarBranchMenu;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.DownLoadImage;
import com.hd.hse.common.component.phone.util.DownLoadImage.ImageCallback;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.cbs.GetPicInfor;
import com.hd.hse.entity.common.Image;
import com.hd.hse.system.SystemProperty;

/**
 * 查看图片，本地以及在线
 * 
 * @author yn 2017/5/16
 * 
 */

public class WatchPicture extends Activity implements OnItemClickListener {

	private static Logger logger = LogUtils
			.getLogger(PhotoManagerActicity.class);
	/**
	 * IMAGEENTITY:TODO(照片实体常量).
	 */
	public static final String IMAGEENTITY = "imageentity";
	public static final String WORKORDERID = "workorderid";

	/**
	 * actionBar:TODO(导航栏).
	 */
	private HSEActionBar actionBar;
	/**
	 * mGridView:TODO(图片预览).
	 */
	private GridView mGridView;

	/**
	 * adapter:TODO(GridView适配器).
	 */
	private PhotoWallAdapter<Image> adapter;

	/**
	 * branchMenu:TODO(菜单栏).
	 */
	private HseActionBarBranchMenu branchMenu;

	/**
	 * dataList:TODO(显示的图片).
	 */
	private List<Image> dataList;
	/**
	 * 图片缓存地址
	 */
	String imgCachePath = SystemProperty.getSystemProperty().getRootDBpath()
			+ File.separator + "imgCachePath";
	private String workorderid;
	private String appname = "appzysq";

	public WatchPicture() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_module_phone_picturemanager);
		// 删除缓存的图片
		delete(new File(imgCachePath));

		Intent intent = getIntent();
		// 待显示图片List
		dataList = (List<Image>) intent.getSerializableExtra(IMAGEENTITY);
		workorderid = intent.getStringExtra(WORKORDERID);
		mGridView = (GridView) findViewById(R.id.hd_hse_common_module_phone_photomanager_gv);
		adapter = new PhotoWallAdapter<Image>(WatchPicture.this, mGridView,
				dataList);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(WatchPicture.this);

		actionBar = new HSEActionBar(this, eventLst, new String[] {
				IActionBar.TV_BACK, IActionBar.TV_TITLE });
		actionBar.setTitleContent("照片查看", false);
		branchMenu = new HseActionBarBranchMenu(getApplicationContext(),
				eventLst, new String[] { IActionBar.ITEM_SELECTALL,
						IActionBar.ITEM_SELECTCANEL });

		GetNetPic(workorderid, appname);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		// 查看照片
		ArrayList<Image> imageList = adapter.getDataList();
		Intent intent = new Intent(this, PhotoPreviewActivity.class);
		intent.putExtra(PhotoPreviewActivity.SELECTEDINDEX, position);
		intent.putExtra(PhotoPreviewActivity.IMAGESET, imageList);
		startActivity(intent);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 删除缓存的图片
		delete(new File(imgCachePath));
	}

	public IEventListener eventLst = new IEventListener() {

		@Override
		public void eventProcess(int arg0, Object... arg1) throws HDException {
			if (arg0 == IEventType.ACTIONBAR_DELETE_CLICK) {
				// 点击删除
				// photosDeleteTip();
			} else if (arg0 == IEventType.ACTIONBAR_SUBTITLE_CLICK) {
				// 点击副标题
				branchMenu.showAsDropDown((View) arg1[0]);
			} else if (arg0 == IEventType.MENUBAR_SELECTALL_CLICK) {
				// 点击副标题，下拉选项，全部选取
				adapter.setAllCheckedState(true);

				// refreshSubTitle();

				branchMenu.hintPopWin();
			} else if (arg0 == IEventType.ACTIONBAR_BACK_CLICK) {
				/*
				 * if (adapter.getShowState()) { // 点击导航栏返回 //
				 * setPhotoStatusFalse(); return; }
				 */
				finish();
			} else if (arg0 == IEventType.MENUBAR_SELECTCANEL_CLICK) {
				if (adapter.getShowState()) {
					// 点击副标题，下拉选择，全部取消选取
					// setPhotoStatusFalse();

					branchMenu.hintPopWin();
				}
			}
		}
	};

	/**
	 * 得到后台的图片
	 */
	private void GetNetPic(String id, String appname) {
		// 加载动画，得到数据后停止
		final ProgressDialog dialog = new ProgressDialog(WatchPicture.this,
				true, "正在加载图片...");

		GetPicInfor record = new GetPicInfor(id, appname);

		BusinessAction action = new BusinessAction(record);

		BusinessAsyncTask asyncTask = new BusinessAsyncTask(action,
				new AbstractAsyncCallBack() {
					@Override
					public void start(Bundle msgData) {
						// TODO Auto-generated method stub
					}

					@Override
					public void processing(Bundle msgData) {

					}

					@Override
					public void error(Bundle msgData) {

						try {
							dialog.dismiss();
						} catch (Exception e) {
							logger.error(e);
						}

						AysncTaskMessage msg = (AysncTaskMessage) msgData
								.getSerializable("p");
						ToastUtils.imgToast(
								WatchPicture.this,
								R.drawable.hd_hse_common_msg_wrong,
								msg.getMessage().contains("获取超时") ? msg
										.getMessage() : "获取已上传数据失败！请联系管理员");

					}

					@Override
					public void end(Bundle msgData) {
						// TODO Auto-generated method stub

						AysncTaskMessage msg = (AysncTaskMessage) msgData
								.getSerializable("p");
						ArrayList<Image> images = (ArrayList<Image>) msg
								.getReturnResult();
						if (images != null && images.size() > 0) {
							DownLoadImage mImageLoader = new DownLoadImage();
							mImageLoader.downloadImage(images,
									new MyImageCallback(dialog));

						}else {
							try {
								dialog.dismiss();
								if (dataList==null||dataList.size()==0) {
									ToastUtils.toast(WatchPicture.this, "暂无图片");
								}
								
							} catch (Exception e1) {
								logger.error(e1);
							}
						}

					}
				});

		try {
			asyncTask.execute(IActionType.WEB_INOROUTRECORD, "15");

			if (dialog != null) {
				dialog.show();
			}

		} catch (HDException e) {
			ToastUtils.imgToast(WatchPicture.this,
					R.drawable.hd_hse_common_msg_wrong, "获取已上传数据失败");
			try {
				dialog.dismiss();
			} catch (Exception e1) {
				logger.error(e1);
			}

		}

	}

	private class MyImageCallback implements ImageCallback {
		ProgressDialog dialog;

		public MyImageCallback(ProgressDialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onImageLoaded(Bitmap bmp, Image img) {
			String picName = img.getImagename();
			saveBitmapToSD(bmp, imgCachePath + "/", picName, img);
		}

		@Override
		public void onImageLoadErr(Image img) {
		}

		@Override
		public void onImageAllLoaded() {
			try {
				dialog.dismiss();
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
			} catch (Exception e) {
				logger.error(e);
			}

		}

	}

	private void saveBitmapToSD(Bitmap bmp, String path, String picName,
			Image image) {
		File file = new File(path, picName);
		if (file.exists()) {
			// file.delete();
			return;
		}
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			bmp.recycle();
			image.setImagepath(path + picName);
			dataList.add(image);
		} catch (IOException e) {
			ToastUtils.toast(getApplicationContext(), "保存已上传图片失败");
			e.printStackTrace();
		}

	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

}
