package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.adapter.PopMenuAdapter;
import com.hd.hse.common.component.phone.adapter.PopMenuItem;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ActionBarBranchMenu (分支界面导航栏右侧菜单栏)<br/>
 * date: 2014年12月30日 <br/>
 *
 * @author wenlin
 * @version
 */
public class HseActionBarBranchMenu {

	private Context context;

	private PopupWindow popupWindow;

	/**
	 * itemNames:TODO(菜单栏描述).
	 */
	private String[] itemNames;

	/**
	 * itemVisible:TODO(显示的菜单栏项).
	 */
	private String[] itemVisible;

	/**
	 * eventListener:TODO(实现点击ITEM的事件监听).
	 */
	private IEventListener eventListener;

	private int descArrayResId = 0;

	/**
	 * @param context
	 * @param eventListener
	 *            实现点击ITEM的事件监听
	 * @param itemVisible
	 *            显示的item
	 */
	public HseActionBarBranchMenu(Context context,
								  IEventListener eventListener, String[] itemVisible) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.eventListener = eventListener;
		this.itemVisible = itemVisible;
		this.descArrayResId = R.array.hd_hse_common_component_phone_app_item;
		initPopupWindow();
	}

	public HseActionBarBranchMenu(Context context,
								  IEventListener eventListener, String[] itemVisible,
								  int descArrayResId) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.eventListener = eventListener;
		this.itemVisible = itemVisible;
		if (descArrayResId > 0) {
			this.descArrayResId = descArrayResId;
		} else {
			this.descArrayResId = R.array.hd_hse_common_component_phone_app_item;
		}
		initPopupWindow();
	}

	private void initPopupWindow() {
		List<PopMenuItem> lstMenuItem = getListViewItem();
		PopMenuAdapter menuAdapter = new PopMenuAdapter(context, lstMenuItem);
		LayoutInflater inflater = LayoutInflater.from(context);
		View poupWindow = inflater
				.inflate(
						R.layout.hd_hse_common_component_phone_actionbar_listview,
						null);
		ListView lstView = (ListView) poupWindow
				.findViewById(R.id.hd_hse_common_phone_popmenu_listview);
		lstView.setAdapter(menuAdapter);
		lstView.setOnItemClickListener(itemClick);

		popupWindow = new PopupWindow(poupWindow, context.getResources()
				.getDisplayMetrics().widthPixels / 3,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new PaintDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);

	}

	private OnItemClickListener itemClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> list, View view, int position,
								long arg3) {
			// TODO Auto-generated method stub
			PopMenuItem operateItem = (PopMenuItem) list
					.getItemAtPosition(position);
			String operteName = operateItem.getDescription();
			try {
				if (eventListener != null) {
					if (itemNames[0].equals(operteName)) {
						// 照相机
						eventListener.eventProcess(
								IEventType.ACTIONBAR_PHOTOGRAPH_CLICK, view);
					} else if (itemNames[1].equals(operteName)) {
						// 照片管理
						eventListener.eventProcess(
								IEventType.MENUBAR_LEVELTWO_PHOTOMANAGER_CLICK,
								view);
					} else if (itemNames[2].equals(operteName)) {
						// 作业票退回
						eventListener.eventProcess(
								IEventType.ACTIONBAR_RETURN_CLICK, view);
					} else if (itemNames[3].equals(operteName)) {
						// 作业票作废
						eventListener.eventProcess(
								IEventType.ACTIONBAR_INVAILD_CLICK, view);
					} else if (itemNames[4].equals(operteName)) {
						// 全部选中
						eventListener.eventProcess(
								IEventType.MENUBAR_SELECTALL_CLICK, view);
					} else if (itemNames[5].equals(operteName)) {
						// 全部取消选取
						eventListener.eventProcess(
								IEventType.MENUBAR_SELECTCANEL_CLICK, view);
					} else if (itemNames[6].equals(operteName)) {
						// 作业票浏览
						eventListener.eventProcess(
								IEventType.ACTIONBAR_SEETICKET_CLICK, view);
					} else if (itemNames[7].equals(operteName)) {
						// 问题登记
						eventListener.eventProcess(
								IEventType.ACTIONBAR_RECORD_CLICK, view);
					} else if (itemNames[8].equals(operteName)) {
						// 作业票上传
						eventListener.eventProcess(
								IEventType.ACTIONBAR_ITEM_UP_CLICK, view);
					} else if (itemNames[9].equals(operteName)) {
						// 图片查看
						eventListener.eventProcess(
								IEventType.ACTIONBAR_ITEM_WATCH_PIC, view);
					} else if (itemNames[10].equals(operteName)) {
						// 作业符合
						eventListener.eventProcess(IEventType.ACTIONBAR_ZYFH,
								view);
					} else if (itemNames[11].equals(operteName)) {
						// 气体检测暂停
						eventListener.eventProcess(IEventType.QTJCPAUSE, view);
					} else if (itemNames[12].equals(operteName)) {
						// 气体检测结束
						eventListener.eventProcess(IEventType.QTJCSTOP, view);
					} else if (itemNames[13].equals(operteName)) {
						// 远程审批
						eventListener.eventProcess(IEventType.REMOTEAPPR, view);
					}
					popupWindow.dismiss();
				}

			} catch (HDException e) {
				// TODO Auto-generated catch block
				LogUtils.getLogger(HseActionBarBranchMenu.class).error(
						e.getMessage(), e);
				try {
					throw new HDException(e.getMessage());
				} catch (HDException e1) {
					// TODO Auto-generated catch block
					LogUtils.getLogger(HseActionBarBranchMenu.class).error(
							e.getMessage(), e1);
				}
			}
		}
	};

	private List<PopMenuItem> getListViewItem() {
		// TODO Auto-generated method stub
		List<PopMenuItem> lstMenuItem = new ArrayList<PopMenuItem>();
		itemNames = context.getResources().getStringArray(descArrayResId);
		for (int i = 0; i < itemVisible.length; i++) {
			// 作业符合
			if (itemVisible[i].contains(IActionBar.ZYLL_ZYFH)) {
				PopMenuItem zyfh = new PopMenuItem();
				zyfh.setDescription(itemNames[10]);
				zyfh.setDrawable(R.drawable.work);
				lstMenuItem.add(zyfh);
			}
			// 照相机
			if (itemVisible[i].contains(IActionBar.ITEM_PHOTOGTAPH)) {
				PopMenuItem camera = new PopMenuItem();
				camera.setDescription(itemNames[0]);
				camera.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_camera);
				lstMenuItem.add(camera);
			}
			// 照片管理
			if (itemVisible[i].contains(IActionBar.ITEM_PHOTOMANAGER)) {
				PopMenuItem photoManager = new PopMenuItem();
				photoManager.setDescription(itemNames[1]);
				photoManager
						.setDrawable(R.drawable.hd_hse_common_component_phone_icon_menubar_photomaneger);
				lstMenuItem.add(photoManager);
			}
			// 作业票退回
			if (itemVisible[i].contains(IActionBar.ITEM_RETURN)) {
				PopMenuItem workOrderReturn = new PopMenuItem();
				workOrderReturn.setDescription(itemNames[2]);
				workOrderReturn
						.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_return);
				lstMenuItem.add(workOrderReturn);
			}
			// 作业票作废
			if (itemVisible[i].contains(IActionBar.ITEM_CANNEL)) {
				PopMenuItem workOrderCanel = new PopMenuItem();
				workOrderCanel.setDescription(itemNames[3]);
				workOrderCanel
						.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_canel);
				lstMenuItem.add(workOrderCanel);
			}
			// 全部选中
			if (itemVisible[i].contains(IActionBar.ITEM_SELECTALL)) {
				PopMenuItem selectAll = new PopMenuItem();
				selectAll
						.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_selected);
				selectAll.setDescription(itemNames[4]);
				lstMenuItem.add(selectAll);
			}
			// 全部取消选取
			if (itemVisible[i].contains(IActionBar.ITEM_SELECTCANEL)) {
				PopMenuItem selectCanel = new PopMenuItem();
				selectCanel
						.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_selected);
				selectCanel.setDescription(itemNames[5]);
				lstMenuItem.add(selectCanel);
			}
			// 作业票浏览
			if (itemVisible[i].contains(IActionBar.ITEM_WORKSCAN)) {
				PopMenuItem workScan = new PopMenuItem();
				workScan.setDrawable(R.drawable.hd_hse_common_actionbar_custom_img_ibtn_seeticket);
				workScan.setDescription(itemNames[6]);
				lstMenuItem.add(workScan);
			}
			// 问题登记
			if (itemVisible[i].contains(IActionBar.ITEM_RECORD)) {
				PopMenuItem dailyrecord = new PopMenuItem();
				dailyrecord.setDescription(itemNames[7]);
				dailyrecord
						.setDrawable(R.drawable.hd_hse_common_component_phone_item_icon_record);
				lstMenuItem.add(dailyrecord);
			}
			// 作业票上传
			if (itemVisible[i].contains(IActionBar.ITEM_UPWORKORDER)) {
				PopMenuItem dailyrecord = new PopMenuItem();
				dailyrecord.setDescription(itemNames[8]);
				dailyrecord
						.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_upload);
				lstMenuItem.add(dailyrecord);
			}
			// 照片查看
			if (itemVisible[i].contains(IActionBar.ITEM_WATCHPIC)) {
				PopMenuItem dailyrecord = new PopMenuItem();
				dailyrecord.setDescription(itemNames[9]);
				dailyrecord
						.setDrawable(R.drawable.hd_hse_common_component_phone_icon_menubar_photomaneger);
				lstMenuItem.add(dailyrecord);
			}

			// 气体检测暂停
			if (itemVisible[i].contains(IActionBar.QTJC_PAUSE)) {
				PopMenuItem zyfh = new PopMenuItem();
				zyfh.setDescription(itemNames[11]);
				zyfh.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_pause);
				lstMenuItem.add(zyfh);

			}

			// 气体检测结束
			if (itemVisible[i].contains(IActionBar.QTJC_STOP)) {
				PopMenuItem zyfh = new PopMenuItem();
				zyfh.setDescription(itemNames[12]);
				zyfh.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_stop);
				lstMenuItem.add(zyfh);

			}
			//远程审批
			if (itemVisible[i].contains(IActionBar.REMOTE_APPR)) {
				PopMenuItem remote = new PopMenuItem();
				remote.setDescription(itemNames[13]);
				remote.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_remote_appr);
				lstMenuItem.add(remote);

			}
		}

		return lstMenuItem;
	}

	/**
	 * setItemIsVisible:(设置显示的菜单项). <br/>
	 * date: 2014年12月30日 <br/>
	 *
	 * @author wenlin
	 * @param items
	 */
	public void setItemIsVisible(String[] items) {
		itemVisible = items;
	}

	/**
	 * setEventListener:(设置菜单栏监听). <br/>
	 * date: 2015年1月12日 <br/>
	 *
	 * @author wenlin
	 * @param eventLst
	 */
	public void setEventListener(IEventListener eventLst) {
		this.eventListener = eventLst;
	}

	/**
	 * showAsDropDown:(). <br/>
	 * date: 2015年1月7日 <br/>
	 *
	 * @author wenlin
	 * @param view
	 */
	public void showAsDropDown(View view) {
		popupWindow.showAsDropDown(view, 0, 0);
	}

	/**
	 * hintPopWin:(隐藏弹出框). <br/>
	 * date: 2015年1月7日 <br/>
	 *
	 * @author wenlin
	 */
	public void hintPopWin() {
		popupWindow.dismiss();
	}
}
