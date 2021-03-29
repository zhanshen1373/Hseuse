/**
 * Project Name:hse-common-component
 * File Name:PhotoWallAdapter.java
 * Package Name:com.hd.hse.common.component.adapter
 * Date:2014年10月20日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.util.DensityUtil;
import com.hd.hse.common.component.phone.util.ThumbBitmapUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.logger.LogUtils;

/**
 * ClassName: PhotoWallAdapter (照片适配器)<br/>
 * date: 2015年1月7日  <br/>
 *
 * @author wenlin
 * @version @param <T>
 */
@SuppressLint("UseSparseArrays")
public class PhotoWallAdapter<T extends SuperEntity> extends BaseAdapter implements OnScrollListener{

	private static Logger logger = LogUtils.getLogger(PhotoWallAdapter.class);
	private List<T> dataList;
	private Context context;
	private GridView mGridView;
	private Set<BitmapTask> taskSet;
	private LruCache<String, Bitmap> mMemoryCache;
	private int mFirstVisibleItem;
	private int mVisibleItemCount;
	private boolean isFirstEnter = true;
	private boolean isShow = false;              //是否显示多选框
	private Map<Integer, Boolean> checkedMap;
	
	public PhotoWallAdapter(Context context, GridView gridView, List<T> dataList){
		this.context = context;
		this.mGridView = gridView;
		this.dataList = dataList;
		
		checkedMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < dataList.size(); i++) {
			checkedMap.put(i, false);
		}
		
		taskSet = new HashSet<BitmapTask>();
		// 获取可用的内存最大值
		int maxMemory = (int)Runtime.getRuntime().maxMemory();
		// 使用最大可用内存值的1/8作为缓存的大小
		int cacheSize = maxMemory/8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量
				return value.getByteCount();
			}
		};
		mGridView.setOnScrollListener(this);
	}
	
	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = View.inflate(context, R.layout.hd_hse_common_component_photo_wall_item, null);
			holder = new ViewHolder();
			holder.photoIV = (ImageView) convertView.findViewById(R.id.hd_hse_common_component_photo_wall_item_top_iv);
			holder.infoTV = (TextView) convertView.findViewById(R.id.hd_hse_common_component_photo_wall_item_bottom_tv);
			holder.overstoryRL = (RelativeLayout) convertView.findViewById(R.id.hd_hse_common_component_photo_wall_item_overstory_rl);
			holder.choiceCB = (CheckBox) convertView.findViewById(R.id.hd_hse_common_component_photo_wall_item_overstory_cb);
			holder.uncheckIV = (ImageView) convertView.findViewById(R.id.hd_hse_common_component_photo_wall_item_overstory_img);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.choiceCB.setTag(position);
		
		T entity = dataList.get(position);
		String imgPath = (String) entity.getAttribute("imagepath");
		String imgname = (String) entity.getAttribute("imagename");
		imgname = imgname.replaceFirst("_", System.getProperty("line.separator"));
		imgname = imgname.replaceAll(".jpg", "");
		
		holder.infoTV.setText(imgname);
		holder.photoIV.setTag(imgPath);
		int rotation = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(imgPath);
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
		
		holder.photoIV.setRotation(rotation);
		setImageView(imgPath, holder.photoIV);
		
		if(isShow){
			holder.overstoryRL.setVisibility(View.VISIBLE);
			holder.choiceCB.setChecked(checkedMap.get(position));
			//审批拍照不可删除，
			String tablename=(String) entity.getAttribute("tablename");
			if (null!=tablename&&"ud_zyxk_zyspryjl".equals(tablename)) {
				holder.uncheckIV.setVisibility(View.VISIBLE);
			}else {
				holder.uncheckIV.setVisibility(View.INVISIBLE);
			}
		}else{
			holder.overstoryRL.setVisibility(View.INVISIBLE);
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		ImageView photoIV;
		TextView infoTV;
		RelativeLayout overstoryRL;
		CheckBox choiceCB;
		ImageView uncheckIV;
	}
	
	/**
	 * clearCheckedSet:(重置图片状态). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 */
	public void clearCheckedSet(){
		checkedMap = new HashMap<Integer, Boolean>();
		for (int i = 0; i < dataList.size(); i++) {
			checkedMap.put(i, false);
		}
		
		taskSet = new HashSet<BitmapTask>();
		isFirstEnter = true;
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mFirstVisibleItem = firstVisibleItem;
		mVisibleItemCount = visibleItemCount;
		if(isFirstEnter && visibleItemCount > 0){
			loadBitmaps(firstVisibleItem, visibleItemCount);
			isFirstEnter = false;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// 停止滚动后加载图片
		if(scrollState == SCROLL_STATE_IDLE){
			loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
		}else{
			cancelAllTask();
		}
	}
	
	/**
	 * loadBitmaps:(当向ImageView中加载图片时，首先会在LruCache的缓存中进行检查). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param firstVisibleItem 当前能看见的第一个列表项ID
	 * @param visibleItemCount 当前能看见的列表项个数（小半个也算）
	 */
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount){
		for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
			//TODO 从数据集合中获取路径
			String imgPath = (String) dataList.get(i).getAttribute("imagepath");
			Bitmap bitmap = getBitmapFromMemoryFromCache(imgPath);
			if(bitmap == null){
				BitmapTask task = new BitmapTask();
				taskSet.add(task);
				task.execute(imgPath);
			}else{
				ImageView iv = (ImageView) mGridView.findViewWithTag(imgPath);
				if(iv != null && bitmap != null){
					iv.setImageBitmap(bitmap);
				}
			}
		}
	}
	
	private void cancelAllTask(){
		if(taskSet != null){
			for (BitmapTask task : taskSet) {
				task.cancel(false);
			}
		}
	}
	
	/**
	 * ClassName: BitmapTask ()<br/>
	 * date: 2015年1月12日  <br/>
	 *
	 * @author wenlin
	 * @version PhotoWallAdapter
	 */
	private class BitmapTask extends AsyncTask<String, Void, Bitmap>{

		private String imgPath = null;
		
		/**
		 * TODO 把新加载的图片的键值对放到缓存中
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Bitmap doInBackground(String... params) {
			imgPath = params[0];
			
			Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
			if(bitmap != null){
				bitmap = ThumbBitmapUtils.extractMiniThumb(bitmap, DensityUtil.dip2px(context, 184), DensityUtil.dip2px(context, 134));
				addBitmapToMemory(imgPath, bitmap);
			}
			return bitmap;
		}
		
		/**
		 * TODO 后台进程执行完毕后的处理
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			ImageView iv = (ImageView) mGridView.findViewWithTag(imgPath);
			if(iv != null && result != null){
				// 加载图片至控件中
				iv.setImageBitmap(result);
			}
			// 移除线程
			taskSet.remove(this);
		}
	}
	
	/**
	 * setImageView:(设置图片). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param imgPath
	 * @param iv
	 */
	private void setImageView(String imgPath, ImageView iv){
		Bitmap bitmap = getBitmapFromMemoryFromCache(imgPath);
		if(bitmap != null){
			iv.setImageBitmap(bitmap);
		}else{
			iv.setImageResource(R.drawable.hd_hse_common_component_phone_default_photo);
		}
	}
	
	/**
	 * addBitmapToMemory:(新加载的图片放入缓存中). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param imgPath
	 * @param bitmap
	 */
	private void addBitmapToMemory(String imgPath, Bitmap bitmap){
		if(getBitmapFromMemoryFromCache(imgPath) == null){
			mMemoryCache.put(imgPath, bitmap);
		}
	}
	
	/**
	 * getBitmapFromMemoryFromCache:(获取指定路径key的缓存图片). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param imgPath
	 * @return
	 */
	private Bitmap getBitmapFromMemoryFromCache(String imgPath){
		return mMemoryCache.get(imgPath);
	}
	
	/**
	 * getShowState:(返回编辑状态). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @return
	 */
	public boolean getShowState(){
		return isShow;
	}
	
	/**
	 * 切换GridView的显示状态
	 * setShowState:(). <br/>
	 * date: 2014年10月20日 <br/>
	 *
	 * @author flb
	 * @param isShow   是否进入有勾选的状态
	 */
	public void setShowState(boolean isShow){
		if(this.isShow != isShow){
			this.isShow = isShow;
			if(isShow){
				for (int i = 0; i < dataList.size(); i++) {
					checkedMap.put(i, false);
				}
			}else{
				checkedMap.clear();
			}
			refreshList();
		}
	}
	
	/**
	 * setOneCheckedState:(设置当前图片的状态). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param position
	 * @param isChecked
	 */
	public void setOneCheckedState(int position, boolean isChecked){
		checkedMap.put(position, isChecked);
		refreshList();
	}
	
	/**
	 * getOneCheckedState:(返回当前图片的状态). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param position
	 * @return
	 */
	public boolean getOneCheckedState(int position){
		return checkedMap.get(position);
	}
	
	/**
	 * setAllCheckedState:(设置全部图片的状态). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param isChecked
	 */
	public void setAllCheckedState(boolean isChecked){
		for (int i = 0; i < checkedMap.size(); i++) {
			checkedMap.put(i, isChecked);
		}
		refreshList();
	}
	
	/**
	 * getDeletedCheckItem:(获取已勾选的图片). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @return
	 */
	public List<SuperEntity> getDeletedCheckItem(){
		List<SuperEntity> deletedDataList = new ArrayList<SuperEntity>();
		for (int i = 0; i < checkedMap.size(); i++) {
			if(checkedMap.get(i)){
				T entity = dataList.get(i);
				// 设置图片删除标记
				entity.setDelete(true);
				deletedDataList.add(entity);
			}
		}
		return deletedDataList;
	}
	
	/**
	 * deleteCheckItem:(删除所选图片). <br/>
	 * date: 2015年1月7日 <br/>
	 *
	 * @author wenlin
	 */
	public void deleteCheckItem(){
		dataList.removeAll(getDeletedCheckItem());
	}
	
	public ArrayList<T> getDataList(){
		return (ArrayList<T>) dataList;
	}
	
	public Map<Integer, Boolean> getCheckedMap(){
		return checkedMap;
	}
	
	private void refreshList(){
		this.notifyDataSetChanged();
	}

}

