package com.hd.hse.common.module.phone.ui.module.measurefragment;

import android.content.Context;

import com.hd.hse.common.component.phone.adapter.BaseStepCheckExpanableListAdapter;
import com.hd.hse.common.component.phone.adapter.CheckPartExpanableListAdapter;
import com.hd.hse.common.component.phone.adapter.CheckPartMultipleExpanableListAdapter;
import com.hd.hse.common.component.phone.adapter.CheckPointExpanableListAdapter;
import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.constant.IConfigEncoding;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MeasureExpanableListAdapter (可扩展的listview适配器)<br/>
 * date: 2015年1月27日 <br/>
 *
 * @author lxf
 */
public class MeasureExpanableListAdapter implements MeasureAatapterInterface {

    /**
     * childKey:TODO(取子表用的KEY).
     */
    private String childKey;
    private BaseStepCheckExpanableListAdapter baseListAdapter;

    /**
     * Creates a new instance of MeasureExpanableListAdapter.
     *
     * @param mContext       上下文对象
     * @param listDataSoruce ListView对象
     * @param showList       需要显示的属性名称列表
     * @param pcAttr         来自PC端的属性标识名称
     */
    public MeasureExpanableListAdapter(int configType, Context mContext,
                                       List<SuperEntity> listDataSoruce, List<String> showList,
                                       String pcAttr, PinnedSectionExpandableListView listView,
                                       String childKey) {
        this.childKey = childKey;
        // 非逐条
        if (configType == IConfigEncoding.MEASURE_TYPENOONEBYNOE) {
            baseListAdapter = new CheckPartExpanableListAdapter(mContext,
                    listDataSoruce, showList, pcAttr, listView, childKey);
        } else if (configType == IConfigEncoding.MEASURE_TYPEONEBYNOEBATCH) {
            // 逐条批量
            baseListAdapter = new CheckPartMultipleExpanableListAdapter(
                    mContext, listDataSoruce, showList, pcAttr, listView,
                    childKey);
        } else if (configType == IConfigEncoding.MEASURE_TYPEONEBYNOE) {
            // 逐条
            baseListAdapter = new CheckPointExpanableListAdapter(mContext,
                    listDataSoruce, showList, pcAttr, listView, childKey);
        }
    }

    @Override
    public List<SuperEntity> getSelectedValues() {
        // TODO Auto-generated method stub
        if (baseListAdapter != null) {
            return baseListAdapter.getSelectedValues();
        }
        return null;
    }

    @Override
    public List<SuperEntity> getSourceValues() {
        // TODO Auto-generated method stub
        if (baseListAdapter != null) {
            return getChildList(baseListAdapter.getSourceValues());
        }
        return null;
    }

    @Override
    public void updateValues(List<SuperEntity> values) {
        // TODO Auto-generated method stub
        if (baseListAdapter != null) {
            baseListAdapter.updateValues(values);
        }
    }

    @Override
    public List<SuperEntity> getHavePcStatusValues() {

        return null;
    }

    @Override
    public void nextItem() {
        // TODO Auto-generated method stub
        if (baseListAdapter != null) {
            baseListAdapter.nextItem();
        }
    }

    @Override
    public void previousItem() {
        // TODO Auto-generated method stub
        if (baseListAdapter != null) {
            baseListAdapter.previousItem();
        }
    }

    @Override
    public Object getAdapter() {
        // TODO Auto-generated method stub
        return baseListAdapter;
    }

    @Override
    public void setIEventListener(IEventListener listener) {
        // TODO Auto-generated method stub
        // mIEventListener=listener;
        if (baseListAdapter != null) {
            baseListAdapter.setIEventListener(listener);
        }
    }

    /**
     * getChildList:(获取子表数据). <br/>
     * date: 2015年2月3日 <br/>
     *
     * @param listSuper
     * @return
     * @author lxf
     */
    private List<SuperEntity> getChildList(List<SuperEntity> listSuper) {
        List<SuperEntity> tempList = new ArrayList<SuperEntity>();
        if (listSuper != null && listSuper.size() > 0) {
            for (SuperEntity superEntity : listSuper) {
                tempList.addAll(superEntity.getChild(childKey));
            }
        }
        return tempList;
    }

    @Override
    public void setIsselectAttr(String isselectAttr) {
        if (baseListAdapter != null) {
            baseListAdapter.setIsSelectedAttr(isselectAttr);
        }

    }

    @Override
    public List<SuperEntity> getCurrentSelectedValues() {
        if (baseListAdapter != null) {
            return baseListAdapter.getCurrentSelectedValues();
        }
        return null;
    }

    @Override
    public void updateCurrentValues(List<SuperEntity> values) {
        if (baseListAdapter != null) {
            baseListAdapter.updateCurrentValues(values);
        }
    }

}
