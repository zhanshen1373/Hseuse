package com.hd.hse.common.module.phone.ui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.component.phone.adapter.PhotoWallAdapter;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.HSEActionBar;
import com.hd.hse.common.component.phone.custom.HseActionBarBranchMenu;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.common.Image;

/**
 * ClassName: PhotoManagerActicity (照片管理)<br/>
 * date: 2015年1月6日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class PhotoManagerActicity extends Activity implements
		OnItemLongClickListener, OnItemClickListener {

	private static Logger logger = LogUtils
			.getLogger(PhotoManagerActicity.class);
	/**
	 * IMAGEENTITY:TODO(照片实体常量).
	 */
	public static final String IMAGEENTITY = "imageentity";
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

	public PhotoManagerActicity() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_module_phone_picturemanager);
		Log.i(PhotoManagerActicity.class.getName(),
				"PhotoManagerActicity.onCteate");

		Intent intent = getIntent();
		// 待显示图片List
		dataList = (List<Image>) intent.getSerializableExtra(IMAGEENTITY);

		mGridView = (GridView) findViewById(R.id.hd_hse_common_module_phone_photomanager_gv);
		adapter = new PhotoWallAdapter<Image>(this, mGridView, dataList);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemLongClickListener(this);
		mGridView.setOnItemClickListener(this);

		actionBar = new HSEActionBar(this, eventLst, new String[] {
				IActionBar.TV_BACK, IActionBar.TV_TITLE });
		actionBar.setTitleContent("照片管理", false);
		branchMenu = new HseActionBarBranchMenu(getApplicationContext(),
				eventLst, new String[] { IActionBar.ITEM_SELECTALL,
						IActionBar.ITEM_SELECTCANEL });
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if (!adapter.getShowState()) {
			setPhotoStatusTrue();
			// 审批图片不可勾选删除
			String tablename=dataList.get(position).getTablename();
			if (null!=tablename&&!"ud_zyxk_zyspryjl".equals(tablename)) {
				adapter.setOneCheckedState(position, true);
			}

			refreshSubTitle();
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		// 判断当前状态是否是编辑状态
		if (adapter.getShowState()) {
			// 审批图片不可勾选删除
			String tablename=dataList.get(position).getTablename();
			if (null!=tablename&&"ud_zyxk_zyspryjl".equals(tablename)) {
				ToastUtils.toast(getApplicationContext(), "审批图片不可勾选删除");
				return;
			}
			// 设置照片编辑状态（勾选、不勾选）
			adapter.setOneCheckedState(position,
					!adapter.getOneCheckedState(position));

			refreshSubTitle();

			// 点击的照片是最后一张被勾选的则将图片编辑状态还原
			if (adapter.getDeletedCheckItem().size() < 1) {
				setPhotoStatusFalse();
			}
		} else {
			// 查看照片
			ArrayList<Image> imageList = adapter.getDataList();
			Intent intent = new Intent(this, PhotoPreviewActivity.class);
			intent.putExtra(PhotoPreviewActivity.SELECTEDINDEX, position);
			intent.putExtra(PhotoPreviewActivity.IMAGESET, imageList);
			startActivity(intent);
		}
	}

	public IEventListener eventLst = new IEventListener() {

		@Override
		public void eventProcess(int arg0, Object... arg1) throws HDException {
			if (arg0 == IEventType.ACTIONBAR_DELETE_CLICK) {
				// 点击删除
				photosDeleteTip();
			} else if (arg0 == IEventType.ACTIONBAR_SUBTITLE_CLICK) {
				// 点击副标题
				branchMenu.showAsDropDown((View) arg1[0]);
			} else if (arg0 == IEventType.MENUBAR_SELECTALL_CLICK) {
				// 点击副标题，下拉选项，全部选取
				adapter.setAllCheckedState(true);

				refreshSubTitle();

				branchMenu.hintPopWin();
			} else if (arg0 == IEventType.ACTIONBAR_BACK_CLICK) {
				if (adapter.getShowState()) {
					// 点击导航栏返回
					setPhotoStatusFalse();
					return;
				}
			} else if (arg0 == IEventType.MENUBAR_SELECTCANEL_CLICK) {
				if (adapter.getShowState()) {
					// 点击副标题，下拉选择，全部取消选取
					setPhotoStatusFalse();

					branchMenu.hintPopWin();
				}
			}
		}
	};

	/**
	 * photosDeleteTip:(删除照片的提示). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author wenlin
	 */
	public void photosDeleteTip() {
		MessageDialog.Builder builder = new MessageDialog.Builder(
				PhotoManagerActicity.this);
		builder.setTitle("提示");
		builder.setMessage("要删除所选照片吗？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				deleteSelectedPhoto();
				dialog.dismiss();
			}
		});
		builder.createWarm().show();
	}

	/**
	 * deleteSelectedPhoto:(删除所选照片). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author wenlin
	 */
	private void deleteSelectedPhoto() {
		// TODO Auto-generated method stub
		final BusinessAction business = new BusinessAction();
		// 所选中的删除照片List
		final List<SuperEntity> deletedCheckItem = adapter
				.getDeletedCheckItem();

		new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < deletedCheckItem.size(); i++) {
						SuperEntity entity = deletedCheckItem.get(i);
						String path = (String) entity.getAttribute("imagepath");
						File file = new File(path);
						if (file != null)
							// 删除文件目录下的照片
							file.delete();
					}
					// 更新数据库中Image表信息
					if (deletedCheckItem.size() > 0)
						business.saveEntities(deletedCheckItem);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							// 删除照片
							adapter.deleteCheckItem();
							adapter.clearCheckedSet();

							setPhotoStatusFalse();
							adapter.notifyDataSetChanged();
						}
					});
				} catch (HDException e) {
					logger.error(e.getMessage(), e);
					ToastUtils.imgToast(getApplicationContext(),
							R.drawable.hd_hse_common_msg_wrong, "照片删除失败！");
				}
			}
		}.start();
	}

	/**
	 * setPhotoStatusTrue:(设置图片可编辑状态). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author wenlin
	 */
	public void setPhotoStatusTrue() {
		actionBar.setControlVisible(new String[] { IActionBar.IBTN_DELETE,
				IActionBar.TV_SUBTITLE });
		actionBar.setControlInVisible(new String[] { IActionBar.TV_TITLE });
		adapter.setShowState(true);
	}

	/**
	 * setPhotoStatusFalse:(还原至原始状态). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author wenlin
	 */
	public void setPhotoStatusFalse() {
		actionBar.setControlInVisible(new String[] { IActionBar.IBTN_DELETE,
				IActionBar.TV_SUBTITLE });
		actionBar.setControlVisible(new String[] { IActionBar.TV_TITLE });
		actionBar.setTitleContent("照片管理", false);
		adapter.setShowState(false);
	}

	/**
	 * refreshSubTitle:(设置副标题内容). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author wenlin
	 */
	private void refreshSubTitle() {
		// TODO Auto-generated method stub
		actionBar.setSubTitleContent("选中了 "
				+ adapter.getDeletedCheckItem().size() + " 项");
	}
}
