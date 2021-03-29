package com.hd.hse.common.module.phone.ui.module.measurefragment;

import android.content.Context;
import android.widget.ListView;

import com.hd.hse.common.component.phone.adapter.BaseStepCheckAdapter;
import com.hd.hse.common.component.phone.adapter.StepCheckPartAdapter;
import com.hd.hse.common.component.phone.adapter.StepCheckPartMultipleAdapter;
import com.hd.hse.common.component.phone.adapter.StepCheckPointAdapter;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.constant.IConfigEncoding;

import java.util.List;

/**
 * ClassName: MeasureListAdapter (listview适配器)<br/>
 * date: 2015年1月27日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MeasureListAdapter implements MeasureAatapterInterface {

	private BaseStepCheckAdapter<SuperEntity> baseAdapter;

	/**
	 * Creates a new instance of MeasureListAdapter.
	 * 
	 * @param mContext
	 *            上下文对象
	 * @param listDataSoruce
	 *            ListView对象
	 * @param showList
	 *            需要显示的属性名称列表
	 * @param pcAttr
	 *            来自PC端的属性标识名称
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MeasureListAdapter(int configType, Context mContext,ListView mListView,
			List<SuperEntity> listDataSoruce, List<String> showList,
			String pcAttr) {
		// 非逐条
		if (configType == IConfigEncoding.MEASURE_TYPENOONEBYNOE) {
			baseAdapter = new StepCheckPartAdapter(mContext, mListView,
					listDataSoruce, showList, "isselect");
		} else if (configType == IConfigEncoding.MEASURE_TYPEONEBYNOEBATCH) {
			// 逐条批量
			baseAdapter = new StepCheckPartMultipleAdapter(mContext, mListView,
					listDataSoruce, showList, "isselect");
		} else if (configType == IConfigEncoding.MEASURE_TYPEONEBYNOE) {
			// 逐条
			baseAdapter = new StepCheckPointAdapter(mContext, mListView,
					listDataSoruce, showList, "isselect");
		}
	}

	@Override
	public List<SuperEntity> getSelectedValues() {

		if (baseAdapter != null) {
			return baseAdapter.getSelectedValues();
		}
		return null;
	}

	@Override
	public List<SuperEntity> getSourceValues() {
		if (baseAdapter != null) {
			return baseAdapter.getSourceValues();
		}
		return null;
	}

	@Override
	public void updateValues(List<SuperEntity> values) {
		if (baseAdapter != null) {
			baseAdapter.updateValues(values);
		}

	}

	@Override
	public List<SuperEntity> getHavePcStatusValues() {
		if (baseAdapter != null) {
			return baseAdapter.getHavePcStatusValues();
		}
		return null;
	}

	@Override
	public void nextItem() {
		if (baseAdapter != null) {
			baseAdapter.nextItem();
		}

	}

	@Override
	public void previousItem() {
		if (baseAdapter != null) {
			baseAdapter.previousItem();
		}
	}

	@Override
	public Object getAdapter() {
		return baseAdapter;

	}
	@Override
	public void setIEventListener(IEventListener listener) {
		// TODO Auto-generated method stub
		if(baseAdapter!=null){
			baseAdapter.setIEventListener(listener);
		}
	}
	@Override
	public void setIsselectAttr(String isselectAttr) {
		// TODO Auto-generated method stub
		if (baseAdapter != null) {
			baseAdapter.setIsSelectedAttr(isselectAttr);
		}
	}



	@Override
	public List<SuperEntity> getCurrentSelectedValues() {
		if (baseAdapter != null) {
			return baseAdapter.getCurrentSelectedValues();
		}
		return null;
	}

	@Override
	public void updateCurrentValues(List<SuperEntity> values) {
		if (baseAdapter != null) {
			baseAdapter.updateCurrentValues(values);
		}
	}

}
