/**
 * Project Name:hse-common-module-phone
 * File Name:SelectableZYPExpandableListView.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2015年1月4日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkTask;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * ClassName:SelectableZYPExpandableListView ().<br/>
 * Date: 2015年1月4日 <br/>
 *
 * @author xuxinwen
 * @see
 */
public class SelectableZYPExpandableListView extends BaseZYPExpandableListView {

    private final String TAG = "SelectableZYPExpandableListView";
    private final boolean DEBUG = true;

    private static Logger logger = LogUtils.getLogger(SelectableZYPExpandableListView.class);

    public SelectableZYPExpandableListView(Context context, AttributeSet attrs,
                                           int defStyle) {
        super(context, attrs, defStyle);

    }

    public SelectableZYPExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectableZYPExpandableListView(Context context) {
        super(context);
    }

    @Override
    protected void onGetGroupView(GroupHolder viewHolder, int groupPosition) {
        if (isGroupAllSelected(groupPosition)) {
            //全选
            viewHolder.icon.setImageResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector_check);
        } else {
            viewHolder.icon.setImageResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector);
        }
        viewHolder.icon.setOnClickListener(new GroupSelecteAllListener(
                groupPosition));

    }

    /**
     * 组中icon 的点击事件， ClassName: GroupSelecteAllListener ()<br/>
     * date: 2015年1月5日 <br/>
     *
     * @author xuxinwen
     * @version SelectableZYPExpandableListView
     */
    class GroupSelecteAllListener implements View.OnClickListener {

        private int mGroupPosition;

        public GroupSelecteAllListener(int mGroupPosition) {
            super();
            this.mGroupPosition = mGroupPosition;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onClick(View v) {
            if (!isGroupExpanded(mGroupPosition)) {
                // 如果该组没有处在展开的状态，不实现全选功能，避免误操作
                return;
            }

            if (isGroupAllSelected(mGroupPosition)) {
                // 该组全部被选中了，让该组全部不选中
                mSelectedAllGroupsPosition.remove(mGroupPosition);
                selecteNone(mGroupPosition);
            } else {
                // 全选该组
                mSelectedAllGroupsPosition.add(mGroupPosition);
                selecteAll(mGroupPosition);
            }
            mAdapter.notifyDataSetChanged();

            if (mEventListener != null) {
                try {
                    mEventListener.eventProcess(PhoneEventType.ZYPLIST_ICON_CLICK, mGroupPosition);
                } catch (HDException e) {
                    logger.error(e);
                }
            }

//			if (isture) {
//				if (v instanceof TextView) {
//				//	((TextView) v).setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector);
////					((TextView) v)
////							.setBackgroundDrawable(getResources()
////									.getDrawable(
////											R.drawable.hd_hse_common_module_zyplist_group_icon_selector));
//				}
//			} else {
//				if (v instanceof TextView) {
//					((TextView) v).setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector_check);
////					((TextView) v)
////							.setBackgroundDrawable(getResources()
////									.getDrawable(
////											R.drawable.hd_hse_common_module_zyplist_group_icon_selector_check));
//				}
//			}
        }

    }

    @Override
    protected void changeContentType(GroupHolder viewHolder, WorkTask _workTask) {
        super.changeContentType(viewHolder, _workTask);
        BaseDao dao = new BaseDao();
        // 读取IP
        String sql = "select * from sys_relation_info where sys_type='ISZJSDDWXX';";

        RelationTableName relationTableName = null;
        try {
            relationTableName = (RelationTableName) dao.
                    executeQuery(sql, new EntityResult(RelationTableName.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (relationTableName != null) {
            if (relationTableName.getIsqy() != null) {
                if (relationTableName.getIsqy() == 1) {
                    String title = null;
                    if (_workTask != null && _workTask.getZylocation_desc() != null) {
                        title = _workTask.getSddept_desc() + "\n" + _workTask.getZylocation_desc() + "："
                                + _workTask.getZyname();
                    }
                    viewHolder.title.setText(title);
                }
            }
        }


    }

    private void selecteAll(int groupPosition) {
        setSelectState(groupPosition, true);

    }

    private void setSelectState(int groupPosition, boolean state) {
        ArrayList<boolean[]> _groupState = mChoiceStates.get(groupPosition);
        int _len;

        for (boolean[] row : _groupState) {
            _len = row.length;

            for (int i = 0; i < _len; i++) {
                row[i] = state;
            }
        }
    }

    private void selecteNone(int groupPosition) {
        setSelectState(groupPosition, false);
    }

    /**
     * 判断某个组是否全部被选中 isGroupAllSelected:(). <br/>
     * date: 2015年1月4日 <br/>
     *
     * @param groupPosition
     * @return
     * @author xuxinwen
     */
    private boolean isGroupAllSelected(int groupPosition) {
        ArrayList<boolean[]> _groupSelectedState = mChoiceStates
                .get(groupPosition);

        for (boolean[] row : _groupSelectedState) {
            for (boolean item : row) {
                if (!item) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 得到被选中的item对应的实体，如果还没有设置数据则返回 null getChoicedEntity:(). <br/>
     * date: 2015年1月5日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    public List<SuperEntity> getChoicedEntity() {
        if (mData == null) {
            return null;
        }

        // 两层循环， 外层循环 WorkTask，内层循环 WorkTask 中的
        // WorkOrder 如果找到了选中的位置，直接用外层的循环参数和
        // 内层的循环参数来取 WorkOrder
        List<SuperEntity> _result = new ArrayList<SuperEntity>();

        // 某个具体组中 WorkOrer 的数量
        int _workOrerCount;

        // 某个具体 组中 WorkOrer 的选中状态。
        ArrayList<boolean[]> _workOrderStates;

        // 当前第几行
        int _row;

        // 当前行中的 索引。
        int _index;

        // 作业任务的数量，也就是组的数量
        int _workTaskCount = mData.size();

        for (int outer = 0; outer < _workTaskCount; outer++) {
            _workOrerCount = mCache.getChildrenCount(outer);
            _workOrderStates = mChoiceStates.get(outer);

            // TODO Thingking !!!
            for (int inner = 0; inner < _workOrerCount; inner++) {
                _row = inner / 4;
                _index = inner % 4;

                if (_workOrderStates.get(_row)[_index]) {
                    if (DEBUG)
                        Log.d(TAG, "第 " + _row + " 行,第 " + _index + " 列被选中了！");

                    _result.add(mCache.getChild(outer, inner));
                }
            }
        }

        return _result;
    }

    @Override
    public void setData(List<SuperEntity> data) {
        super.setData(data);
        createChoiceStatesNote();
    }

    /**
     * 创建标记每个item选中情况的数据结构。 createChoiceStatesNote:(). <br/>
     * date: 2015年1月5日 <br/>
     *
     * @author xuxinwen
     */
    private void createChoiceStatesNote() {

        if (mData == null) {
            if (DEBUG) // TODO
                Log.w(TAG, "createChoiceStatesNote--if(mData == null) 成立");

            return;
        }

        mChoiceStates = new ArrayList<ArrayList<boolean[]>>();
        ArrayList<boolean[]> _thisGroup;
        boolean[] _rowChilds = null;

        // 组 的数量
        int _size = mData.size();

        // 某个组中 item的总数量
        int _childCount;

        // 某个组中 最后一行 item 的数量。
        int _lastRowItemCount;

        // 某个组有几行
        int _rowSize;

		/*
         * 选择构建以行为单位的数据结构是为了方便给每一行赋值。
		 */
        for (int i = 0; i < _size; i++) {
            _thisGroup = new ArrayList<boolean[]>();

            _childCount = mCache.getChildrenCount(i);
            _lastRowItemCount = _childCount % 4;

            _rowSize = _childCount / 4;

            for (int j = 0; j < _rowSize; j++) {
                _thisGroup.add(new boolean[4]);
            }

            // 处理最后一行。
            if (_lastRowItemCount != 0) {
                _thisGroup.add(new boolean[_lastRowItemCount]);
            }

            mChoiceStates.add(_thisGroup);
        }

        if (DEBUG) // TODO
            Log.w(TAG,
                    "createChoiceStatesNote--mAdapter.notifyDataSetChanged();");

        mAdapter.notifyDataSetChanged();

    }

    private List<ArrayList<boolean[]>> mChoiceStates;

    @Override
    protected void onGetChildView(BaseZYPListRow zypListRow, int groupPosition,
                                  int childPosition) {

        // 设置选中状态在每次重新显示到界面的时候。
        if (DEBUG)
            Log.e(TAG, "onGetChildView--groupPosition:" + groupPosition
                    + " childPosition:" + childPosition);

        ((SelectableZYPListRow) zypListRow).setChoiceStates(mChoiceStates.get(
                groupPosition) // 组
                .get(childPosition)); // 行。
    }

    @Override
    protected BaseZYPListRow onCreateChildView() {
        return new SelectableZYPListRow(getContext());
    }

    @Override
    protected void onItemClick(Object... args) {
        super.onItemClick(args);

        int _groupPositon = (int) args[0];
        if (isGroupAllSelected(_groupPositon)) {
            // 如果该组中已经全部被选中，同步组的图标
            mSelectedAllGroupsPosition.add(_groupPositon);
            mAdapter.notifyDataSetChanged();
        } else {
            if (mSelectedAllGroupsPosition.contains(_groupPositon)) {
                // 该组之前是全选状态，
                mSelectedAllGroupsPosition.remove(_groupPositon);
                mAdapter.notifyDataSetChanged();
            }
        }

        if (mEventListener != null) {
            try {
                mEventListener.eventProcess(PhoneEventType.ZYPLIST_ITEM_CLICK,
                        SelectableZYPExpandableListView.this,  // 当前控件对象
                        mChoiceStates.get((int) args[0]).get((int) args[1])[(int) args[2]],  // 当前的点击选中状态。
                        args[3],            // 当前点击的票
                        mData.get((int) args[0]), // 当前组
                        mChoiceStates.get((int) args[0]) // 当前组的选中情况集合 ArrayList<boolean[]>
                );
            } catch (HDException e) {
                logger.error(e);
            }
        }
    }

    /**
     * 记录已经全选的组，起初目的是用来判断每次item 点击之后是否该组从全选变为不全选。
     */
    private HashSet<Integer> mSelectedAllGroupsPosition = new HashSet<Integer>();
}
