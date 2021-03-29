/**
 * Project Name:hse-wov-phone-app
 * File Name:StatisticsListActivity.java
 * Package Name:com.hd.hse.wov.phone.ui.statistics
 * Date:2015年11月17日
 * Copyright (c) 2015, liuyang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.wov.phone.ui.statistics;

import android.content.Intent;
import android.os.Bundle;
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
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.wov.phone.R;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * ClassName:StatisticsListActivity ().<br/>
 * Date: 2015年11月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class StatisticsOldListActivity extends BaseFrameActivity implements
		IEventListener {
	private static Logger logger = LogUtils
			.getLogger(StatisticsListActivity.class);
	private GridView grid;
	private List<AppModule> gridDatas;
	
	private GridView gridCommon;
	private List<AppModule> gridCommonDatas;

	// private static final int[] gridImgIDs = {R.drawable.hd_hse_tjfx_dhyyfx,
	// R.drawable.hd_hse_tjfx_zylxtj, R.drawable.hd_hse_tjfx_zyslytj,
	// R.drawable.hd_hse_tjfx_tzzyjbtj, R.drawable.hd_hse_tjfx_fpltj,
	// R.drawable.hd_hse_tjfx_wtpztj, R.drawable.hd_hse_tjfx_tzzyyytb,
	// R.drawable.hd_hse_tjfx_zypsltb};
	// private static final String[] gridtitles = {"动火原因分析", "作业类型统计",
	// "作业数量月统计",
	// "特种作业级别统计", "废票率统计", "问题票证统计", "特种作业原因同比", "作业票数量同比"};
	// private static final String[] bbbhs = {"UD_ZYXK_RPT_DHRESIONHZ",
	// "UD_ZYXK_RPT_GDWBTZYLXTJ", "UD_ZYXK_RPT_GDWZYPAYTJ",
	// "UD_ZYXK_RPT_GTZZYAJBTJ", "UD_ZYXK_RPT_FPLTJ",
	// "UD_ZYXK_RPT_WTPZTJ", "UD_ZYXK_RPT_GTZZYYYTB",
	// "UD_ZYXK_RPT_GDWZYPTB"};
	// private static final int[] tjTypes = {1, 1, 1, 2, 1, 1, 3, 3};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_wov_phone_activity_statisticsoldlist); // 初始化ActionBar
		setCustomActionBar(true, this, setActionBarItems());

		setNavContent(getNavContentData(), "hse-tjfx-phone-app");

		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);
		final IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
		try {
			gridDatas = queryWorkInfo.queryAppModule("TJFX", null);
			gridCommonDatas = queryWorkInfo.queryAppModule("TJFX_CY", null);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(StatisticsOldListActivity.this,
					R.drawable.hd_hse_common_msg_wrong, e.getMessage());
		}
		grid = (GridView) findViewById(R.id.gridview);
		grid.setAdapter(new GridAdapter(gridDatas));
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				AppModule app = gridDatas.get(position);
				go2NextPage(queryWorkInfo, app);
			}

		});
		gridCommon = (GridView) findViewById(R.id.gridview_common);
		gridCommon.setAdapter(new CommonGridAdapter(gridCommonDatas));
		gridCommon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				AppModule app = gridCommonDatas.get(position);
				go2NextPage(queryWorkInfo, app);
			}

		});
	}

	private void go2NextPage(final IQueryWorkInfo queryWorkInfo, AppModule app) {
		String imgUrl = null, tblUrl = null;
		try {
			List<Domain> urls = queryWorkInfo.queryTJUrls(
					app.getClickdealclass(), null);
			for (Domain domain : urls) {
				if (domain.getValue().contains("_TBL")) {
					tblUrl = domain.getDescription();
				} else {
					imgUrl = domain.getDescription();
				}
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(StatisticsOldListActivity.this,
					R.drawable.hd_hse_common_msg_wrong, e.getMessage());
			return;
		}
		Intent intent = new Intent(StatisticsOldListActivity.this,
				StatisticsOldActivity.class);
		// intent.putExtra("bbbh", bbbhs[position]);
		intent.putExtra(StatisticsActivity.IMGURL, imgUrl);
		intent.putExtra(StatisticsActivity.TBLURL, tblUrl);
		intent.putExtra("tjType", Integer.valueOf(app.getModelnum()));
		intent.putExtra("title", app.getName());
		startActivity(intent);
	}

	private String getTitileName() {
		return "统计分析";
	}

	public String[] setActionBarItems() {
		return new String[] { IActionBar.IBTN_RETURN, IActionBar.TV_TITLE };
	}

	private class GridAdapter extends BaseAdapter {
		private List<AppModule> data;

		public GridAdapter(List<AppModule> data) {
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
						.from(StatisticsOldListActivity.this)
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
					"drawable/" + data.get(position).getResimg(), "int",
					getPackageName());
			holder.img.setImageResource(resimg);
			holder.txt.setText(data.get(position).getName());
			return contentView;
		}

		private class ViewHolder {
			public ImageView img;
			public TextView txt;
		}

	}
	
	private class CommonGridAdapter extends BaseAdapter {
		private List<AppModule> data;

		public CommonGridAdapter(List<AppModule> data) {
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
						.from(StatisticsOldListActivity.this)
						.inflate(
								R.layout.hd_hse_wov_phone_activity_statisticslist_grid_common_item,
								null);
				holder = new ViewHolder();
				holder.img = (ImageView) contentView.findViewById(R.id.image);
				contentView.setTag(holder);
			} else {
				holder = (ViewHolder) contentView.getTag();
			}
			int resimg = getResources().getIdentifier(
					"drawable/" + data.get(position).getResimg() + "_common", "int",
					getPackageName());
			holder.img.setImageResource(resimg);
			return contentView;
		}

		private class ViewHolder {
			public ImageView img;
		}

	}

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
