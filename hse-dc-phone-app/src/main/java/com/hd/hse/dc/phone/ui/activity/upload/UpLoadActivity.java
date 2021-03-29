package com.hd.hse.dc.phone.ui.activity.upload;

/**
 * Project Name:hse-dc-app
 * File Name:UpLoadActivity.java
 * Package Name:com.hd.hse.dc.ui.activity.list
 * Date:2014年10月10日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.KeyEvent;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.custom.SelectableZYPExpandableListView;
import com.hd.hse.common.module.phone.custom.StateSelectableZYPExpandableListView;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.dc.phone.R;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: UpLoadActivity ()<br/>
 * date: 2014年10月10日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class UpLoadActivity extends BaseFrameActivity {

	/**
	 * upzyp:TODO(上传作业票).
	 */
	private UpZYPProgressDialog upzyp = null;
	private UpLoadWorkTaskSrv upload;
	private SelectableZYPExpandableListView selectable;
	private List<SuperEntity> dataList;

	private boolean isPro = true;
	private int initDHGX = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		selectable = new StateSelectableZYPExpandableListView(this);
		selectable.setOnEventListener(eventLst);
		setCustomActionBar(true, eventLst, new String[] { IActionBar.IV_LMORE,
				IActionBar.TV_TITLE, IActionBar.IBTN_SEARCH,
				IActionBar.IBTN_UPLOAD });
		// 设置不可见
		setHSEActionBarInVisible(new String[] { IActionBar.IBTN_UPLOAD });
		// 设置导航栏标题
		setActionBartitleContent("作业票上传", false);
		// 设置抽屉
		setNavContent(
				SystemProperty.getSystemProperty().getMainAppModulelist("SJ"),
				"hse-ud-phone-app");

		setContentView(selectable);
		iniParam();
		initData();
	}

	/**
	 * iniParam:(初始化参数). <br/>
	 * date: 2015年3月4日 <br/>
	 * 
	 * @author lxf
	 */
	protected void iniParam() {
		// TODO Auto-generated method stub
		upload = new UpLoadWorkTaskSrv();
		upzyp = new UpZYPProgressDialog(this);
		upzyp.setGetDataSourceListener(eventLst);
	}

	/**
	 * initData:(初始化数据). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author zhaofeng
	 */
	protected void initData() {
		loadDataInfo(getSearchStr());// 初始化，非查询
	}

	/**
	 * loadDataInfo:(加载上传列表方法). <br/>
	 * date: 2015年1月7日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void loadDataInfo(String searchStr) {
		try {
			if (null == upzyp) {
				upzyp = new UpZYPProgressDialog(this);
				upzyp.setGetDataSourceListener(eventLst);
			}
			dataList = upload.loadWorkTaskList(searchStr);
			// 设置数据集,getSearchStr()为查询条件
			if ((null == dataList || dataList.size() == 0) && isPro) {
				ToastUtils.toast(getBaseContext(), "没有作业票！");
			}
			setDataOrder();
			selectable.setData(dataList);// 界面加载数据
			setControlInfo();
			isPro = true;
		} catch (HDException e) {
			// TODO Auto-generated catch block
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					"数据集获取失败！");
		}
	}

	/**
	 * eventLst:TODO(上传和查询事件).
	 */
	private IEventListener eventLst = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			// TODO Auto-generated method stub
			switch (eventType) {

			case IEventType.ACTIONBAR_UPLOAD_CLICK:
				// 走上传作业票的方法
				List<SuperEntity> checkedList = selectable.getChoicedEntity();// 获取上传数据集
				if (null != checkedList && checkedList.size() > 0) {
					upzyp.execute("上传", "上传作业票信息，请耐心等待.....", "", checkedList);
				} else {
					ToastUtils.toast(getBaseContext(), "没有选择上传数据");
				}
				break;
			case IEventType.ACTIONBAR_SEARCH_CLICK:
				// 搜索 - 点击事件
				if (objects[0] instanceof String) {
					setSearchStr((String) objects[0]);
				}
				initData();
				break;
			case IEventType.DOWN_WORK_LIST_LOAD:
				// 上传完成后-加载上传列表数据
				// isPro = false;
				loadDataInfo("");// 初始化，非查询search
				setControlInfo();
				break;
			case IEventType.DOWN_WORK_LIST_SHOW:
				// 上传完成后-加载上传列表数据
				// isPro = false;
				loadDataInfo("");// 初始化，非查询search
				setControlInfo();
				break;
			case PhoneEventType.ZYPLIST_ITEM_CLICK:
				// 表示点击票事件
				setControlInfo();
				// 判断是否 有电必有火
				if (null != objects && objects.length > 4) {
					SelectableZYPExpandableListView _elv = (SelectableZYPExpandableListView) objects[0];
					boolean _isChecked = (Boolean) objects[1];
					SuperEntity _currEntitiy = (SuperEntity) objects[2];
					SuperEntity _allEntity = (SuperEntity) objects[3];
					List<boolean[]> _state = (List<boolean[]>) objects[4];

					// 读取数据库 判断是否有点必有火的关联
					if (initDHGX == -1) {
						QueryRelativeConfig queryRelation = new QueryRelativeConfig();
						RelationTableName relationInfo = new RelationTableName();
						relationInfo.setSys_type("DHYD");
						if (queryRelation.isHadRelative(relationInfo)) {
							initDHGX = 1;
						} else {
							initDHGX = 0;
						}
					}
					if (initDHGX == 1) {
						setChooseZYPInfo(_elv, _isChecked,
								(WorkOrder) _currEntitiy, _allEntity, _state,
								",zylx01,zylx07,");
					}
				}
				break;
			case PhoneEventType.ZYPLIST_ICON_CLICK:
				// 表示点击全选
				setControlInfo();
				break;
			default:
				break;
			}
		}
	};
	boolean isVisable = false;

	private void setControlInfo() {
		List<SuperEntity> checkedList = selectable.getChoicedEntity();// 获取下载数据集
		if (null != checkedList && checkedList.size() > 0) {
			// 设置下载按钮可见
			if (!isVisable) {
				setSearchVisible(false);
				setHSEActionBarInVisible(new String[] { IActionBar.IBTN_SEARCH });
				setHSEActionBarVisible(new String[] { IActionBar.IBTN_UPLOAD });
				isVisable = true;

			}
		} else {
			if (isVisable) {
				setHSEActionBarInVisible(new String[] { IActionBar.IBTN_UPLOAD });
				setHSEActionBarVisible(new String[] { IActionBar.IBTN_SEARCH });
				isVisable = false;
			}
		}
	}

	private final String zystarttimeKey = "zystarttime";

	/**
	 * setDataOrder:(设置数据排序). <br/>
	 * date: 2015年2月28日 <br/>
	 * 
	 * @author lxf
	 */
	private void setDataOrder() {
		if (dataList == null)
			return;
		String[] workOderStr = getResources().getStringArray(
				R.array.hd_hse_main_app_workorder_order);
		if (null == workOderStr || workOderStr.length <= 1) {
			return;
		}
		int count = dataList.size();
		List<SuperEntity> listchild = null;
		SuperEntity mainSup = null;
		for (int i = 0; i < count; i++) {
			mainSup = dataList.get(i);
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
		bubble_sort(dataList);
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
	 * setChooseZYPInfo:(设置票选中时，同时自动选中相关联的票). <br/>
	 * date: 2014年11月3日 <br/>
	 * 
	 * @author lxf
	 * @param listview
	 * @param currentWorkOrder
	 * @param strZypClass
	 */
	protected void setChooseZYPInfo(SelectableZYPExpandableListView elv,
			boolean isChecked, WorkOrder currentWorkOrder,
			SuperEntity allEntity, List<boolean[]> state, String strZypClass) {
		if (StringUtils.isEmpty(strZypClass)) {
			return;
		}
		// if (null != currentWorkOrder
		// && strZypClass.toLowerCase().contains(
		// "," + currentWorkOrder.getZypclass().toLowerCase()
		// + ",")) {
		// WorkTask _thisTask = null;
		// int _thisChildCount = 0;
		// List<SuperEntity> _thisChilds = null;
		// _thisTask = (WorkTask) allEntity;
		// _thisChilds = _thisTask.getChild(WorkOrder.class.getName());
		// _thisChildCount = _thisChilds.size();
		//
		// for (int j = 0; j < (_thisChildCount); j++) {
		// WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
		// if (strZypClass.toLowerCase().contains(
		// "," + _thisWork.getZypclass().toLowerCase() + ",")) {
		// //state.set(j + 1, isChecked);
		// setStateResult(state,j,isChecked);
		// }
		// }
		// }
		if (null != currentWorkOrder
				&& "zylx07".equalsIgnoreCase(currentWorkOrder.getZypclass())) {
			WorkTask _thisTask = null;
			int _thisChildCount = 0;
			List<SuperEntity> _thisChilds = null;
			_thisTask = (WorkTask) allEntity;
			_thisChilds = _thisTask.getChild(WorkOrder.class.getName());
			_thisChildCount = _thisChilds.size();

			for (int j = 0; j < (_thisChildCount); j++) {
				WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
				if (_thisWork.getUd_zyxk_zysqid().equalsIgnoreCase(
						currentWorkOrder.getDhzy_id())) {
					setStateResult(state, j, isChecked);
				}
			}
		}
		// 表示选中动火
		if (null != currentWorkOrder
				&& "zylx01".equalsIgnoreCase(currentWorkOrder.getZypclass())) {
			WorkTask _thisTask = null;
			int _thisChildCount = 0;
			List<SuperEntity> _thisChilds = null;
			_thisTask = (WorkTask) allEntity;
			_thisChilds = _thisTask.getChild(WorkOrder.class.getName());
			_thisChildCount = _thisChilds.size();

			for (int j = 0; j < (_thisChildCount); j++) {
				WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
				if (currentWorkOrder.getUd_zyxk_zysqid().equalsIgnoreCase(
						_thisWork.getDhzy_id())) {
					setStateResult(state, j, isChecked);
				}
			}
		}
		elv.notifyDataSetChanged();
	}

	/**
	 * setStateResult:(改变集合里结果). <br/>
	 * date: 2015年3月5日 <br/>
	 * 
	 * @author lxf
	 * @param state
	 * @param index
	 * @param res
	 */
	private void setStateResult(List<boolean[]> state, int index, boolean res) {
		int i = 0;
		for (boolean[] booleans : state) {
			int j = 0;
			for (boolean boolean1 : booleans) {
				if (i == index) {
					booleans[j] = res;
					return;
				}
				j++;
				i++;
			}
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		boolean ret=super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			if(LocationSwCard.mTimer != null){
				LocationSwCard.mTimer.cancel();
				LocationSwCard.mTimer = null;
			}
			// 清空刷卡位置
			SystemProperty.getSystemProperty().setPositionCard(null);
			return true;
		}
		return ret;
	}
}
