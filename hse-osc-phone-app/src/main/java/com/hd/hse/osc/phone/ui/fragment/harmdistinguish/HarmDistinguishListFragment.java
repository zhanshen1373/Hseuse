package com.hd.hse.osc.phone.ui.fragment.harmdistinguish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.StepCheckTabView;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Created by liuyang on 2016年3月9日
 */
/**
 * 
 * Created by liuyang on 2016年3月10日
 */
public class HarmDistinguishListFragment extends NaviFrameFragment {
	private static Logger logger = LogUtils
			.getLogger(HarmDistinguishListFragment.class);
	private WorkOrder mWorkOrder;
	private PDAWorkOrderInfoConfig pdaConfig;
	private List<PDAWorkOrderInfoConfig> pdaConfigList;
	private SuperEntity currentTabPADConfigInfo;

	private IQueryWorkInfo queryWorkInfo;

	private View contentView;
	private StepCheckTabView stepCheckTabView;
	private LinearLayout linerLayoutTab;

	private Map<Object, Object> dataListCache = new HashMap<>();

	// private HarmDistinguishFragment currentFragment;

	@Override
	protected void init() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initData(List<Object> data) {
		if (data != null) {
			if (data.get(0) instanceof WorkOrder) {
				mWorkOrder = (WorkOrder) data.get(0);
			}
			if (data.get(1) instanceof PDAWorkOrderInfoConfig) {
				pdaConfig = (PDAWorkOrderInfoConfig) data.get(1);
			}
			if (data.get(2) instanceof List) {
				pdaConfigList = (List<PDAWorkOrderInfoConfig>) data.get(2);
			}
			queryWorkInfo = new QueryWorkInfo();
		}
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup arg1) {
		if (contentView == null) {
			contentView = inflater.inflate(
					R.layout.hd_hse_common_module_phone_measure_fragmentlayout_harm,
					null);
			stepCheckTabView = (StepCheckTabView) contentView
					.findViewById(R.id.hd_hse_common_component_phone_pageindicator_harm_id);

			linerLayoutTab = (LinearLayout) contentView
					.findViewById(R.id.hd_hse_common_module_phone_harm_tag_id);

			initTab();
		}
		return contentView;
	}

	SuperEntity curentpdaConfig;

	/**
	 * 初始化tab
	 * 
	 * Created by liuyang on 2016年3月9日
	 */
	private void initTab() {
		if (pdaConfigList == null || pdaConfigList.size() == 0) {
			linerLayoutTab.setVisibility(View.GONE);
			curentpdaConfig = pdaConfig;
			// switchTab(pdaConfig);

		} else if (pdaConfigList.size() == 1) {
			linerLayoutTab.setVisibility(View.GONE);
			curentpdaConfig = pdaConfigList.get(0);
			// switchTab(pdaConfigList.get(0));
		} else {
			linerLayoutTab.setVisibility(View.VISIBLE);
			stepCheckTabView.setDataList(pdaConfigList);
			stepCheckTabView.setOnIEventListener(eventListener);
			stepCheckTabView.setCurrentItem(pdaConfigList.get(0));
			curentpdaConfig = pdaConfigList.get(0);
			// switchTab(pdaConfigList.get(0));
		}
	}

	private void switchTab(SuperEntity pdaConfig) {
		String cstype = (String) pdaConfig.getAttribute("cstype");
		if (cstype == null || cstype.equals("")) {
			cstype = "危害识别";
		}
		List<Object> dataList = null;
		boolean isinit = false;
		if (dataListCache.get(cstype) == null) {
			try {
				dataList = new ArrayList<>();
				dataList.add(mWorkOrder);
				dataList.add(pdaConfig);
				dataList.add(queryWorkInfo.queryHarmInfo(mWorkOrder,
						(PDAWorkOrderInfoConfig) pdaConfig, null));
				List<SuperEntity> appList = changeToSuperEntity((List) queryWorkInfo
						.queryApprovalPermission(mWorkOrder, pdaConfig, null,
								null));
				dataList.add(appList);
				dataListCache.put(cstype, dataList);
			} catch (HDException e) {
				logger.error(e);
				ToastUtils.imgToast(mActivity,
						R.drawable.hd_hse_common_msg_wrong, e.getMessage());
			}
			isinit = true;
		} else {
			dataList = (List<Object>) dataListCache.get(cstype);
		}
		switchContentView(cstype, HarmDistinguishFragment.class);
		HarmDistinguishFragment nav = null;
		nav = getFragmentByClass(cstype, HarmDistinguishFragment.class);
		if (null != nav && isinit) {
			// 设置数据源
			nav.initData(dataList);
		}

		// HarmDistinguishFragment frg = new HarmDistinguishFragment();
		// frg.initData(dataList);
		// // 切换 Fragment
		// FragmentTransaction transaction = getFragmentManager()
		// .beginTransaction();
		// transaction.replace(R.id.hd_hse_common_component_phone_content_id,
		// frg);
		// transaction.commit();
		// currentFragment = frg;
	}

	@SuppressWarnings("rawtypes")
	private Map<String, HarmDistinguishFragment> cacheFragment = new HashMap<String, HarmDistinguishFragment>();
	/**
	 * 表示当前界面的Fragment类
	 */
	private Class<? extends HarmDistinguishFragment> currentFragment;
	/**
	 * currentFragmentObject:TODO(当前Fragment对象).
	 */
	public HarmDistinguishFragment currentFragmentObject;
	/**
	 * currentFragmentKey:TODO(当前Fragmentkey).
	 */
	private String currentFragmentKey;
	
	private boolean isload=true;

	/**
	 * TODO 表示切换tab页的显示
	 * 
	 * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#switchContentView(java.lang.Class)
	 */
	public void switchContentView(String key,
			Class<? extends HarmDistinguishFragment> clz) {
		if (clz == null) {
			throw new IllegalArgumentException("clz 不能为 null");
		}
		key = key + clz.getName();
		// 判断是不是当前页面，如果是不用再进行页面切换直接返回。
		if (clz == currentFragment && key.equalsIgnoreCase(currentFragmentKey)) {
			return;
		}
		// 切换Fragment代码------------------------
		@SuppressWarnings("rawtypes")
		HarmDistinguishFragment frg = cacheFragment.get(key);
		// 创建 Fragment 及缓存的过程。------------
		if (frg == null) {
			// 缓存中没有相应的 实例，所以要创建
			try {
				frg = (HarmDistinguishFragment) clz.newInstance();
				// 放入缓存。
				cacheFragment.put(key, frg);
			} catch (java.lang.InstantiationException e) {
				logger.error("措施切换tab页报错", e);
			} catch (IllegalAccessException e) {
				logger.error("措施切换tab页报错", e);
			}
		}
		// 切换 Fragment
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.hd_hse_common_component_phone_content_harm_id, frg);
		transaction.commit();
		// 同步当前页面 。
		currentFragment = clz;
		currentFragmentObject = frg;
		currentFragmentKey = key;
	}

	/**
	 * 根据字节码获得缓存中的 实例对象，如果缓存中没有则返回 null getCurrentFragment:(). <br/>
	 * date: 2014年10月16日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public HarmDistinguishFragment getFragmentByClass(String key,
			Class<? extends Fragment> clz) {
		if (null == cacheFragment) {
			return null;
		}
		return cacheFragment.get(key + clz.getName());
	}

	/**
	 * tab页点击事件
	 * 
	 * created by liuyang on 2016/03/10
	 */
	private IEventListener eventListener = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			// 表示tab点击事件
			if (eventType == IEventType.TOP_CIRCLE_CHECKED) {
				if (null != objects) {
					if (objects.length > 0) {
						if (objects[0] instanceof SuperEntity) {
							currentTabPADConfigInfo = (SuperEntity) objects[0];
							curentpdaConfig = currentTabPADConfigInfo;
							// 根据选择的内容区读取数据库数据
							switchTab(currentTabPADConfigInfo);
						}
					}
				}
			}
		}
	};

	@Override
	public void refreshData() {
		if (isload) {
			isload = true;
			if (curentpdaConfig != null) {
				switchTab(curentpdaConfig);
			}
		}
		// initTab();
	}

	/**
	 * TODO 转换成标准实体类型 changeToSuperEntity:(). date: 2014年10月22日
	 * 
	 * @author wenlin
	 * @param list
	 * @return
	 */
	public List<SuperEntity> changeToSuperEntity(List list) {
		List<SuperEntity> listTemp = list;
		return listTemp;
	}

}
