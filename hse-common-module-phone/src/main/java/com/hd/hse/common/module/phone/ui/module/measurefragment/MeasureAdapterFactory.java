package com.hd.hse.common.module.phone.ui.module.measurefragment;

import java.util.List;

import android.content.Context;
import android.widget.ListView;

import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;

/**
 * ClassName: MeasureAdapterFactory (措施适配器工厂类)<br/>
 * date: 2015年1月27日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MeasureAdapterFactory {

	/**
	 * newIntance:(实例化). <br/>
	 * date: 2015年1月27日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	@SuppressWarnings("unused")
	public static MeasureAdapterFactory newIntance() {
		return new MeasureAdapterFactory();
	}

	public MeasureAatapterInterface getMeasureAdapter(
			PDAWorkOrderInfoConfig config,int configType, Context mContext,
			List<SuperEntity> listDataSoruce, List<String> showList,
			String pcAttr,ListView mListView,PinnedSectionExpandableListView listView,String childKey) {
		MeasureAatapterInterface mAdapter = null;
		if (config.getCbisenable() == 1) {
			//表示启用标题（可扩展的listview）
			mAdapter = new MeasureExpanableListAdapter(configType, mContext,
					listDataSoruce, showList, pcAttr, listView,childKey);
		} else {
			//表示listview
			mAdapter = new MeasureListAdapter(configType, mContext,mListView,listDataSoruce,
					showList, pcAttr);
		}
		return mAdapter;
	}



}
