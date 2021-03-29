/**
 * Project Name:hse-common-module
 * File Name:BaseListActivity.java
 * Package Name:com.hd.hse.common.module.model.activity
 * Date:2014年10月13日
 * Copyright (c) 2014, zhulei@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.common.module.phone.ui.module.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.hd.hse.common.component.phone.custom.MyAlertDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView.OnZYPItemClickListener;
import com.hd.hse.common.module.phone.custom.PopWinButton;
import com.hd.hse.common.module.phone.custom.ZYPOperatePopWindow;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.utils.UtilTools;

/**
 * 
 * ClassName: BaseListActivity1 ()<br/>
 * date: 2015年01月20日 <br/>
 * 
 */
public class BaseListActivity extends BaseFrameActivity {

	private final String zystarttimeKey = "zystarttime";
	/**
	 * mExpandableListView:TODO(显示列表的控件).
	 */
	public NormalZYPExpandableListView mExpandableListView;
	private ZYPOperatePopWindow popWindow;
	public RelativeLayout relativeLayout;
	// popWindow监听接口
	public IEventListener iEventLsn;

	/**
	 * dataList:TODO(数据集).
	 */
	private List<SuperEntity> dataList = null;
	// /**
	// * searchStr:TODO(查询描述).
	// */
	// private String searchStr = "";

	// private HseDyTable dytable;

	// /**
	// * ib:TODO(关闭按钮).
	// */
	// private ImageButton ib;

	// /**
	// * 作业票详情view
	// */
	// private View view;
	/**
	 * 作业票详情页面
	 */
	private MyAlertDialog mAlertDialog;

	/**
	 * getDataList:(获取数据集). <br/>
	 * date: 2014年10月13日 <br/>
	 * 
	 * @author zhulei
	 * @return
	 */
	public final List<SuperEntity> getDataList() {
		return dataList;
	}

	/**
	 * setDataList:(设置数据集). <br/>
	 * date: 2014年10月13日 <br/>
	 * 
	 * @author zhulei
	 * @param dataList
	 */
	public final void setDataList(List<SuperEntity> dataList) {
		this.dataList = dataList;
		// TO—DO
		if (dataList == null)
			return;
		// 数据排序
		setDataOrder();
		mExpandableListView.setData(dataList);
	}

	/**
	 * resId:TODO(详细列表页中的资源文件id).
	 */
	private int[] resId = {
			R.array.hd_hse_common_component_dytable_worktask_desc,
			R.array.hd_hse_common_component_dytable_worktask_num };

	/**
	 * setResId:(设置详细信息显示的字段和描述). <br/>
	 * date: 2014年10月13日 <br/>
	 * 
	 * @author zhulei
	 * @param resId
	 */
	public final void setResId(int[] resId) {
		this.resId = resId;
	}

	// /**
	// * getSearchStr:(获取查询描述). <br/>
	// * date: 2014年10月13日 <br/>
	// *
	// * @author zhulei
	// * @return
	// */
	// public final String getSearchStr() {
	// return searchStr;
	// }
	//
	// /**
	// * setSearchStr:(设置查询描述). <br/>
	// * date: 2014年10月13日 <br/>
	// *
	// * @author zhulei
	// * @param searchStr
	// */
	// public final void setSearchStr(String searchStr) {
	// this.searchStr = searchStr;
	// }

	/**
	 * iEventListener:TODO(设置作业票列表的单机事件).
	 */
	public void setItemClickListener(
			OnZYPItemClickListener mZYPItemClickListener) {
		mExpandableListView.setOnZYPItemClickListener(mZYPItemClickListener);// 实现跳转
	}

	/**
	 * 设置popwindow的监听
	 * 
	 * @param iEventLsn
	 */
	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
		mExpandableListView.setOnEventListener(iEventLsn);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化控件
		initView();
		// // 初始化数据
		initData();
	}

	protected void initView() {
		setContentView(R.layout.hd_hse_workorder_activity);
		mExpandableListView = (NormalZYPExpandableListView) findViewById(R.id.pop_zyp_expandable_listview);
	}

	/**
	 * initData:(初始化数据). <br/>
	 * date: 2014年10月10日 <br/>
	 * 
	 * @author zhulei
	 */
	protected void initData() {
		// TO—DO
		if (dataList == null)
			return;
		// 数据排序
		setDataOrder();
		mExpandableListView.setData(dataList);
	}

	public void defaultDataOrder(List<SuperEntity> tempdata) {
		if (tempdata == null)
			return;
		String[] workOderStr = getResources().getStringArray(
				R.array.hd_hse_main_app_workorder_order);
		if (null == workOderStr || workOderStr.length <= 1) {
			return;
		}
		int count = tempdata.size();
		List<SuperEntity> listchild = null;
		SuperEntity mainSup = null;
		for (int i = 0; i < count; i++) {
			mainSup = tempdata.get(i);
			listchild = mainSup.getChild(WorkOrder.class.getName());
			if (null == listchild || listchild.size() <= 1) {
				// 表示内容为1时不需要排序
				continue;
			}
			List<SuperEntity> listChildOrder = new ArrayList<SuperEntity>();
			String subSuperType = null;
			// 循环票的类型
			for (String zypOrder : workOderStr) {
				// 查找子类信息
				for (SuperEntity subSuper : listchild) {
					subSuperType = subSuper.getAttribute("zypclass") == null ? ""
							: subSuper.getAttribute("zypclass").toString();
					if (zypOrder.equalsIgnoreCase(subSuperType)) {
						listChildOrder.add(subSuper);
						listchild.remove(subSuper);
						break;
					}
				}
			}
			// 判断没有排序上的添加在后边
			if (listchild.size() > 0) {
				listChildOrder.addAll(listchild);
			}
			// 清除原来的KEY-Value
			mainSup.getChilds().remove(WorkOrder.class.getName());
			// 重新复制
			mainSup.setChild(WorkOrder.class.getName(), listChildOrder);
			// dataList.get(i).setChild(entityName, lstChild);
		}
		bubble_sort(tempdata);
	}

	/**
	 * setDataOrder:(设置数据排序). <br/>
	 * date: 2014年10月31日 <br/>
	 * 
	 * @author zhulei
	 */
	private void setDataOrder() {
		defaultDataOrder(dataList);
	}

	/**
	 * bubble_sort:(按照开始时间倒序). <br/>
	 * date: 2014年11月26日 <br/>
	 * 
	 * @author lxf
	 * @param unsorted
	 */
	private void bubble_sort(List<SuperEntity> unsorted) {
		if (unsorted == null || unsorted.size() == 1) {
			return;
		}
		int len = unsorted.size();
		for (int i = 0; i < len; i++) {
			for (int j = i; j < len; j++) {
				try {
					// 表示第一个大
					if (!UtilTools.dataTimeCompare(unsorted.get(i)
							.getAttribute(zystarttimeKey).toString(), unsorted
							.get(j).getAttribute(zystarttimeKey).toString())) {
						SuperEntity temp = unsorted.get(i);
						unsorted.set(i, unsorted.get(j));
						unsorted.set(j, temp);
					}
				} catch (HDException e) {
					ToastUtils.toast(getBaseContext(), "作业开始时间比较报错,请联系管理员");
				}
			}
		}
	}

	/**
	 * 此方法为关闭取消界面预留，当关闭取消点击单个作业票时，判断是否要显示暂停按钮
	 * 
	 * @param workOrder
	 *            点击的作业票
	 */
	public PopWinButton[] onZYPItemClick(WorkOrder workOrder,
			PopWinButton[] mPopWinButton) {
		return mPopWinButton;
	}

	private PopWinButton[] popWinButton;

	public void initPopWindows(final PopWinButton[] mPopWinButton,
			IEventListener iEventLsn) {
		// this.mPopWinButton = mPopWinButton;
		popWindow = new ZYPOperatePopWindow(this);
		popWindow.setEventListener(iEventLsn);
		popWinButton = mPopWinButton;
		mExpandableListView
				.setOnZYPItemClickListener(new OnZYPItemClickListener() {
					@Override
					public void onClick(WorkOrder workOrder, View anchorView,
							int pointerHorizontalPosition) {
						PopWinButton[] popWinButton=onZYPItemClick(workOrder, mPopWinButton);
						if (popWinButton==null) {
							return;
						}
						Log.e("popWinButton", popWinButton.length+"");
						popWindow.show(anchorView, 0,
								pointerHorizontalPosition,
								popWinButton,
								workOrder);	
					}
				});
	}

	/**
	 * showNavPopWindows:(显示弹出框popwindows). <br/>
	 * date: 2015年3月10日 <br/>
	 * 
	 * @author lxf
	 */
	public void showNavPopWindows(WorkOrder workOrder, View anchorView,
			int pointerHorizontalPosition) {
		if (popWindow != null) {
			popWindow.show(anchorView, 0, pointerHorizontalPosition,
					popWinButton, workOrder);
		}
	}

	public void popWindowsMiss() {
		if (popWindow != null && popWindow.isShowing()) {
			popWindow.dimiss();
		}
	}

	/**
	 * TODO 长按作业票图标显示作业票详细信息 showWorkOrderPopupWin:(). <br/>
	 * date: 2015年3月2日 <br/>
	 * 
	 * @author wenlin
	 * @param workOrder
	 */
	public void showWorkOrderPopupWin(SuperEntity workOrder) {
		if (mAlertDialog == null) {
			mAlertDialog = new MyAlertDialog(this, R.style.workorder_dialog,
					workOrder, true);
		} else {
			mAlertDialog.setTabRow(workOrder);
		}
		mAlertDialog.show();
		WindowManager.LayoutParams params = mAlertDialog.getWindow()
				.getAttributes();
		params.height = 800;
		mAlertDialog.getWindow().setAttributes(params);
	}

	public void setIsChoose(boolean ischoose) {
		if (mExpandableListView != null) {
			mExpandableListView.setChooseAndNotify(ischoose);
		}
	}
}
