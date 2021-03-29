/**
 * Project Name:hse-wov-phone-app
 * File Name:StatisticsListActivity.java
 * Package Name:com.hd.hse.wov.phone.ui.statistics
 * Date:2015年11月17日
 * Copyright (c) 2015, liuyang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.wov.phone.ui.statistics;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.AppAnlsCfg;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.wov.phone.R;

/**
 * ClassName:StatisticsListActivity ().<br/>
 * Date: 2015年11月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class StatisticsListActivity extends BaseFrameActivity implements
		IEventListener {
	private static Logger logger = LogUtils
			.getLogger(StatisticsListActivity.class);
	private GridView grid;
	private List<AppAnlsCfg> gridDatas;

	/*
	 * private GridView gridCommon; private List<AppModule> gridCommonDatas;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_wov_phone_activity_statisticslist); // 初始化ActionBar
		setCustomActionBar(true, this, setActionBarItems());

		setNavContent(getNavContentData(), "hse-tjfx-phone-app");

		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);
		final IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
		PersonCard p = SystemProperty.getSystemProperty().getLoginPerson();
		try {
			gridDatas = queryWorkInfo.queryAppAnlsCfg();
			if (gridDatas != null && gridDatas.size() > 0) {
				for (int i = 0; i < gridDatas.size(); i++) {
					AppAnlsCfg appAnlsCfg = gridDatas.get(i);
					if (appAnlsCfg.getIsenable() != 1) {
						gridDatas.remove(i);
						i--;
						continue;
					}
					if (appAnlsCfg.getChilddept() == null
							|| appAnlsCfg.getChilddept().equals("")) {
						continue;
					}
					String childdepts[] = appAnlsCfg.getChilddept().split(",");
					boolean isInchilddepts = false;
					for (int j = 0; j < childdepts.length; j++) {
						if (p.getDepartment().equals(childdepts[j])) {
							isInchilddepts = true;
							break;
						}
					}
					if (!isInchilddepts) {
						gridDatas.remove(i);
						i--;
					}
				}
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(StatisticsListActivity.this,
					R.drawable.hd_hse_common_msg_wrong, e.getMessage());
		}
		if (gridDatas == null || gridDatas.size() < 1) {
			ToastUtils.imgToast(StatisticsListActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "暂无统计数据");
			// go2NextPage(null);
		} else {
			grid = (GridView) findViewById(R.id.gridview);
			grid.setAdapter(new GridAdapter(gridDatas));
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long id) {
					AppAnlsCfg app = gridDatas.get(position);
					go2NextPage(app);
				}

			});
		}

		/*
		 * gridCommon = (GridView) findViewById(R.id.gridview_common);
		 * gridCommon.setAdapter(new CommonGridAdapter(gridCommonDatas));
		 * gridCommon.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View view, int
		 * position, long id) { AppModule app = gridCommonDatas.get(position);
		 * go2NextPage(queryWorkInfo, app); }
		 * 
		 * });
		 */
	}

	private void go2NextPage(AppAnlsCfg app) {

		Intent intent = new Intent(StatisticsListActivity.this,
				StatisticsActivity.class);
		// intent.putExtra("bbbh", bbbhs[position]);
		intent.putExtra(StatisticsActivity.APPANLSCFG, app);
		startActivity(intent);
	}

	private String getTitileName() {
		return "统计分析";
	}

	public String[] setActionBarItems() {
		return new String[] { IActionBar.IBTN_RETURN, IActionBar.TV_TITLE };
	}

	private class GridAdapter extends BaseAdapter {
		private List<AppAnlsCfg> data;

		public GridAdapter(List<AppAnlsCfg> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int arg0) {
			return data.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup viewGroup) {
			ViewHolder holder = null;
			if (contentView == null) {
				contentView = LayoutInflater
						.from(StatisticsListActivity.this)
						.inflate(
								R.layout.hd_hse_wov_phone_activity_statisticslist_grid_item,
								null);
				holder = new ViewHolder();
				holder.img = (ImageView) contentView.findViewById(R.id.image);
				holder.txt = (TextView) contentView.findViewById(R.id.title);
				contentView.setTag(holder);
			} else {
				holder = (ViewHolder) contentView.getTag();
			}
			int resimg = getResources().getIdentifier(
					"drawable/" + data.get(position).getIcon(), "int",
					getPackageName());
			if (resimg != 0) {
				//若没有相应图标，使用默认图标
				holder.img.setImageResource(resimg);
			} else {
				holder.img.setImageResource(R.drawable.hd_hse_tjfx_general);
			}

			holder.txt.setText(data.get(position).getAnlsname());
			return contentView;
		}

		private class ViewHolder {
			public ImageView img;
			public TextView txt;
		}

	}

	/*
	 * private class CommonGridAdapter extends BaseAdapter { private
	 * List<AppModule> data;
	 * 
	 * public CommonGridAdapter(List<AppModule> data) { this.data = data; }
	 * 
	 * @Override public int getCount() { return data.size(); }
	 * 
	 * @Override public Object getItem(int arg0) { return data.get(arg0); }
	 * 
	 * @Override public long getItemId(int arg0) { return arg0; }
	 * 
	 * @Override public View getView(int position, View contentView, ViewGroup
	 * viewGroup) { ViewHolder holder = null; if (contentView == null) {
	 * contentView = LayoutInflater .from(StatisticsListActivity.this) .inflate(
	 * R.layout.hd_hse_wov_phone_activity_statisticslist_grid_common_item,
	 * null); holder = new ViewHolder(); holder.img = (ImageView)
	 * contentView.findViewById(R.id.image); contentView.setTag(holder); } else
	 * { holder = (ViewHolder) contentView.getTag(); } int resimg =
	 * getResources().getIdentifier( "drawable/" +
	 * data.get(position).getResimg() + "_common", "int", getPackageName());
	 * holder.img.setImageResource(resimg); return contentView; }
	 * 
	 * private class ViewHolder { public ImageView img; }
	 * 
	 * }
	 */

	/**
	 * getNavContentData:(获取导航数据). <br/>
	 * date: 2015年2月6日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public List<AppModule> getNavContentData() {
		return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {

	}
}
