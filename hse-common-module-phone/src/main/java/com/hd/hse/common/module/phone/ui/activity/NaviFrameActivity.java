/**
 * Project Name:hse-common-module-phone
 * File Name:NaviFrameActivity.java
 * Package Name:com.hd.hse.common.module.phone.ui.activity
 * Date:2015年1月19日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hd.hse.common.component.phone.custom.HSEActionBar;
import com.hd.hse.common.component.phone.custom.HseActionBarBranchMenu;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.custom.PageSlidingTabView;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 该类实现了利用Fragment实现界面局部切换，与每个Fragment的状态缓存；设定导航栏的信息；
 * ClassName:NaviFrameActivity ().<br/>
 * Date: 2015年1月19日 <br/>
 *
 * @author xuxinwen
 * @version
 * @see
 */
public class NaviFrameActivity extends BaseActivity {
	private static Logger logger = LogUtils.getLogger(NaviFrameActivity.class);

	private final String TAG = "NaviFrameActivity";
	private final boolean DEBUG = true;

	/**
	 * Fragment 的缓存, 完整的类名做 key ， 实例对象最 value
	 */
	protected Map<String, NaviFrameFragment> mFragmentCache = new HashMap<String, NaviFrameFragment>();

	/**
	 * 当前显示的 界面
	 */
	private Class<? extends Fragment> mForegroundFragment;

	private PageSlidingTabView mNavigationBar;
	/**
	 * hseBBM:TODO(菜单栏配置类).
	 */
	private HseActionBarBranchMenu hseBBM;

	/**
	 * hseAB:TODO(导航栏配置类).
	 */
	private HSEActionBar hseAB;

	private IEventListener eventLst;
	/**
	 * workOrder:TODO(作业票实体).
	 */
	public WorkOrder mWorkOrder;
	/**
	 * queryWorkInfo:TODO(查询对象).
	 */
	public IQueryWorkInfo queryWorkInfo;
	/**
	 * pdaConfig:TODO(PDA配置表).
	 */
	private PDAWorkOrderInfoConfig currentNaviTouchEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_module_phone_activity_naviframe);
		mNavigationBar = (PageSlidingTabView) findViewById(R.id.hd_hse_common_module_phone_navigation_bar);
	}

	/**
	 * setCustomActionBar:(设置导航栏信息). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 * @param eventLst
	 * @param actionFlags 显示的控件(I)
	 */
	public void setCustomActionBar(IEventListener eventLst, String[] actionFlags) {
		hseAB = new HSEActionBar(this, eventLst, actionFlags);
		this.eventLst = eventLst;
		hseAB.setEventListener(eventProcessor);
	}

	/**
	 * setCustomMenuBar:(右侧菜单栏*需要先在导航栏显示IActionBar.IBTN_LEVELTWO_MORE*). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 * @param itemFlags 下拉列表显示的item(IAction)
	 */
	public void setCustomMenuBar(String[] itemFlags) {
		hseBBM = new HseActionBarBranchMenu(getApplicationContext(), eventLst,
				itemFlags);
		hseBBM.setEventListener(eventProcessor);
	}
	/**
	 * setActionBartitleContent:(设置导航栏标题内容). <br/>
	 * date: 2015年1月21日 <br/>
	 *
	 * @author wenlin
	 * @param zyName 标题内容
	 * @param isIconTip 是否显示下拉图标
	 */
	public void setActionBartitleContent(String zyName ,Boolean isIconTip){
		hseAB.setTitleContent(zyName , isIconTip);
	}
	/**
	 * setPopDetailWorkerOrer:(设置详细作业票信息实体). <br/>
	 * date: 2015年1月21日 <br/>
	 *
	 * @author wenlin
	 * @param superEntity
	 */
	public void setPopDetailWorkerOrer(SuperEntity superEntity){
		hseAB.setDyTable(superEntity);
	}
	/**
	 * eventProcessor:TODO().
	 */
	public IEventListener eventProcessor = new IEventListener() {

		@Override
		public void eventProcess(int arg0, Object... arg1) throws HDException {
			// TODO Auto-generated method stub
			if (arg0 == IEventType.ACTIONBAR_PHOTOGRAPH_CLICK) {
				touchImages();
			} else if (arg0 == IEventType.MENUBAR_LEVELTWO_PHOTOMANAGER_CLICK) {
				managerImages();
			} else if (arg0 == IEventType.ACTIONBAR_LEVELTWO_MORE_CLICK) {
				hseBBM.showAsDropDown((View) arg1[0]);
			} else {
				eventLst.eventProcess(arg0, arg1);
			}
		}
	};

	/**
	 * setQueryWorkInfo:(设置查询对象). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 * @param queryWorkInfo
	 */
	public void setQueryWorkInfo(IQueryWorkInfo queryWorkInfo) {
		this.queryWorkInfo = queryWorkInfo;
	}

	/**
	 * setWorkInfo:(作业票实体). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 * @param workOrder
	 */
	public void setWorkInfo(WorkOrder workOrder) {
		this.mWorkOrder = workOrder;
	}

	/**
	 * setCurrentNaviTouchEntity:(导航栏配置信息). <br/>
	 * date: 2015年1月28日 <br/>
	 *
	 * @author wenlin
	 * @param currentNaviTouchEntity
	 */
	public void setCurrentNaviTouchEntity(PDAWorkOrderInfoConfig currentNaviTouchEntity){
		this.currentNaviTouchEntity = currentNaviTouchEntity;
	}

	/**
	 * touchImages:(点击拍照). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 */
	public void touchImages() {
		if (null == queryWorkInfo) {
			logger.equals("请调用setQueryWorkInfo()方法设置查询对象");
			ToastUtils.toast(getBaseContext(), "请调用setQueryWorkInfo()方法设置查询对象");

		} else if (null == mWorkOrder) {
			logger.equals("请调用setWorkInfo()设置作业票实体");
			ToastUtils.toast(getBaseContext(), "请调用setWorkInfo()设置作业票实体");
			return;
		}
		Image image = new Image();
		image.setTablename(mWorkOrder.getDBTableName());// 表名
		image.setTableid(mWorkOrder.getUd_zyxk_zysqid());// 表主键
		image.setZysqid(mWorkOrder.getUd_zyxk_zysqid());
		image.setImagepath(SystemProperty.getSystemProperty().getRootDBpath()
				+ File.separator + mWorkOrder.getUd_zyxk_zysqid());// 文件夹路径
		image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
				.getPersonid());// 创建人
		image.setCreateusername(SystemProperty.getSystemProperty()
				.getLoginPerson().getPersonid_desc());
		PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
		image.setFuncode(config.getPscode());// 对应功能
		image.setContype(config.getContype());
		image.setImagename(config.getContypedesc());
		Intent intent = new Intent(getBaseContext(),
				CameraCaptureActivity.class);
		intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
		startActivity(intent);
	}

	/**
	 * managerImage:(照片管理). <br/>
	 * date: 2015年1月20日 <br/>
	 *
	 * @author wenlin
	 */
	public void managerImages() {
		PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
		try {
			if (null == queryWorkInfo) {
				logger.equals("请调用setQueryWorkInfo()方法设置查询对象");
				ToastUtils.toast(getBaseContext(),
						"请调用setQueryWorkInfo()方法设置查询对象");

			} else if (null == mWorkOrder) {
				logger.equals("请调用setWorkInfo()设置作业票实体");
				ToastUtils.toast(getBaseContext(), "请调用setWorkInfo()设置作业票实体");
				return;
			}
			List<Image> imageList = queryWorkInfo
					.queryPhoto(mWorkOrder, config);
			Intent intent = new Intent(getApplicationContext(),
					PhotoManagerActicity.class);
			intent.putExtra(PhotoManagerActicity.IMAGEENTITY,
					(Serializable) imageList);
			startActivity(intent);
		} catch (HDException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 个导航栏设置数据， setNaviData:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author xuxinwen
	 * @param datas
	 */
	public void setNaviData(List datas) {
		mNavigationBar.setDataList(datas);
	}

	public void setNaviItemOnClickListener(IEventListener listener) {
		mNavigationBar.setOnclickListener(listener);
	}

	public void setNaviCurrentEntity(SuperEntity entity) {
		mNavigationBar.setCurrentItem(entity);
	}
	/**
	 * notifyAllNaviItems:(刷新导航栏状态). <br/>
	 * date: 2015年3月3日 <br/>
	 *
	 * @author zhaofeng
	 * @param list
	 */
	public void notifyAllNaviItems(List<SuperEntity> list){
		mNavigationBar.noyifyAllDatas(list);
	}

	/**
	 * TODO 返回当前点击所在PDA实体
	 *
	 * getNaviCurrentEntity:(). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author wenlin
	 * @return
	 */
	public SuperEntity getNaviCurrentEntity() {
		if(currentNaviTouchEntity != null){
			return currentNaviTouchEntity;
		}else{
			return mNavigationBar.getCurrentItem();
		}
	}
	/**
	 * setNaviFinish:(设置导航栏完成标记). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author wenlin
	 * @param entity
	 */
	public void setNaviFinish(SuperEntity entity){
		mNavigationBar.setFinished(entity);
	}

	/**
	 * getFragmentByClass:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author xuxinwen
	 * @param clz
	 * @return
	 */
	public NaviFrameFragment getFragmentByClass(
			Class<? extends NaviFrameFragment> clz) {
		return mFragmentCache.get(clz.getName());
	}

	/**
	 *
	 * switchContentView:(). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author xuxinwen
	 * @param clz
	 * @param data
	 *            暂时用可变参数来实现，不知道是不是应该固定成 WorkOrder 和 PDAWorkOrderInfoConfig 如果这个
	 *            Fragment 之前已经设置过值，不用再设置，而是沿用之前的数据，则直接将此值设为null 即可。
	 */
	public void switchContentView(Class<? extends NaviFrameFragment> clz,
								  List<Object> data) {

		if (clz == null) {
			throw new IllegalArgumentException("clz 不能为 null");
		}

		// 判断是不是当前页面，如果是不用再进行页面切换直接返回。
		if (clz == mForegroundFragment) {
			return;
		}

		// 切换Fragment代码------------------------
		NaviFrameFragment _frg = mFragmentCache.get(clz.getName());

		// 创建 Fragment 及缓存的过程。------------
		if (_frg == null) {
			// 缓存中没有相应的 实例，所以要创建
			if (DEBUG)
				Log.w(TAG, "缓存中没有相应的 实例，所以要创建");
			try {
				_frg = clz.newInstance();
				// 放入缓存。
				mFragmentCache.put(clz.getName(), _frg);

			} catch (InstantiationException e) {

				// 如果创建过程出错，则不应该让程序继续运行下去。
				throw new RuntimeException(e);
				// TODO
			} catch (IllegalAccessException e) {

				// 如果创建过程出错，则不应该让程序继续运行下去。
				throw new RuntimeException(e);
			}
		}

		// 每一次切换都允许设置数据，也可以只在初始化的时候设置，
		// 此时只要将 data 设为null 就可以了，Fragment 就会
		// 使用之前已经设置的数据。
		if (data != null) {
			if (DEBUG)
				Log.w(TAG, "设置数据");

			_frg.initData(data);
		}

		// 系统功能，切换 Fragment
//		FragmentTransaction transaction = getFragmentManager()
//				.beginTransaction();
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.hd_hse_common_module_phone_container, _frg);
		transaction.commit();

		// 同步当前页面 。
		mForegroundFragment = clz;

	}

}
