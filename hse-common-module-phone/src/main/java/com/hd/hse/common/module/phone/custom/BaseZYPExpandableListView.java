/**
 * Project Name:hse-common-module-phone
 * File Name:BaseZYPExpandableListView.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2015年1月4日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.custom.MyAlertDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.workorder.WorkTask;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:BaseZYPExpandableListView ().<br/>
 * Date: 2015年1月4日 <br/>
 *
 * @author xuxinwen
 * @see
 */
public abstract class BaseZYPExpandableListView extends ExpandableListView
        implements IEventListener {

    private final String TAG = "BaseZYPExpandableListView";
    private final boolean DEBUG = true;

    protected BaseExpandableListAdapter mAdapter;

    protected List<SuperEntity> mData;

    protected CacheUtils mCache = new CacheUtils();

    protected IEventListener mEventListener;

    protected String title;
    private List<String> list = new ArrayList<>();
    // private HseDyTable dytable;
    // /**
    // * ib:TODO(关闭按钮).
    // */
    // private ImageButton ib;
    // /**
    // * 作业票详情view
    // */
    // private View workOrderView;
    /**
     * 作业票详情页面
     */
    private MyAlertDialog mAlertDialog;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            /*
             * 展开所有组
             */
            int _count = mAdapter.getGroupCount();

            switch (msg.what) {

                case 0:
                    for (int i = 0; i < _count; i++) {
                        BaseZYPExpandableListView.this.expandGroup(i);
                    }
                    break;
                case 1:
                    for (int i = 0; i < _count; i++) {
                        BaseZYPExpandableListView.this.collapseGroup(i);
                    }
                    break;
            }

        }
    };


    public void setOnEventListener(IEventListener eventListener) {
        mEventListener = eventListener;
    }

    public BaseZYPExpandableListView(Context context, AttributeSet attrs,
                                     int defStyle) {
        super(context, attrs, defStyle);
        init();

    }

    public BaseZYPExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public BaseZYPExpandableListView(Context context) {
        super(context);
        init();

    }

    private void init() {
        mAdapter = new Adapter();
        // this.setDivider(new ColorDrawable(Color.BLACK));
        this.setDividerHeight(1);
        /*
         * this.setChildDivider(getResources().getDrawable(
         * R.drawable.hd_hse_common_blank_divider));
         */
        this.setChildDivider(new ColorDrawable(0xffeaeaea));

        this.setGroupIndicator(null);
        this.setSelector(R.drawable.blank_selector);
        this.setAdapter(mAdapter);
        /*
         * setDividerHeight((int)
         * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
         * getContext().getResources().getDisplayMetrics()));
         */

    }

    public void setData(List<SuperEntity> data) {

        this.mData = data;
        mCache.setData(data);

        boolean fromxcjd = false;
        if (DEBUG)
            Log.w(TAG, "setData--mAdapter.notifyDataSetChanged();");// TODO

        // TODO 更新界面
        mAdapter.notifyDataSetChanged();


        if (mData != null && mData.size() > 0) {
            WorkTask workTask = (WorkTask) mData.get(0);
            //过滤
            if (workTask.getGl() != null) {
                if (workTask.getGl().equals("gl")) {
                    fromxcjd = true;
                }
            }

        }

        if (fromxcjd) {
            mHandler.sendEmptyMessage(1);
        } else {
            mHandler.sendEmptyMessage(0);
        }
    }

    protected void changeContentType(GroupHolder viewHolder, WorkTask _workTask) {
    }


    protected class Adapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mCache.getGroupCount();


        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return (mCache.getChildrenCount(groupPosition) + 3) / 4;
        }

        @Override
        public Object getGroup(int groupPosition) {

            return mCache.getGroup(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {

            return mCache.getChild(groupPosition, childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder _holder;

            if (convertView != null
                    && convertView.getTag() instanceof GroupHolder) {
                _holder = (GroupHolder) convertView.getTag();
            } else {
                convertView = View.inflate(getContext(),
                        R.layout.hse_common_component_phone_zyplist_item, null);
                _holder = new GroupHolder();
                _holder.title = (TextView) convertView
                        .findViewById(R.id.hse_common_module_phone_group_title_tv);
                _holder.icon = (ImageView) convertView
                        .findViewById(R.id.hse_common_module_phone_group_title_icon);
                _holder.indictor = (TextView) convertView
                        .findViewById(R.id.hse_common_module_phone_group_indictor_tv);

                onCreateGroupView(_holder, groupPosition);
                convertView.setTag(_holder);
            }
            // GroupView 不变的部分。
            WorkTask _workTask = (WorkTask) mData.get(groupPosition);

            if (_workTask != null && _workTask.getZylocation_desc() != null) {
                title = _workTask.getZylocation_desc() + "："
                        + _workTask.getZyname();
            } else {
                title = _workTask.getZyname();
            }
            _holder.title.setText(title);
            _holder.title.setOnClickListener(new TitleOnClickListener(
                    PhoneEventType.ZYPLIST_WORKTASK_DETAIL, _workTask));

            // 作业票下载和上传页面显示的和其他页面不同。
            changeContentType(_holder, _workTask);

            // lxf 添加
            // _holder.icon.setOnClickListener(new
            // TitleOnClickListener(PhoneEventType.ZYPLIST_ICON_CLICK,_workTask));

            if (isExpanded) {
                _holder.indictor
                        .setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_indicator_down);
            } else {
                _holder.indictor
                        .setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_indicator_right);
            }
            // if(isAll){
            // //全选
            // _holder.icon.setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector_check);
            // }
            // else{
            // _holder.icon.setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_group_icon_selector);
            // }

            // 给 GroupView 预留的灵活性。
            onGetGroupView(_holder, groupPosition);
            return convertView;
        }

        boolean isAll = false;

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            BaseZYPListRow _zypListRow = null;

            if (convertView != null && convertView instanceof BaseZYPListRow) {
                _zypListRow = (BaseZYPListRow) convertView;
            } else {
                if (DEBUG)
                    Log.d(TAG, "getChildView--创建一个Child对象");

                _zypListRow = onCreateChildView();
                /*
                 * 考虑到item 复用的情况，但是即使item 被复用了，因为这个对象提供的 功能仅仅是一个方法的调用，所以可以复用。
                 */
                _zypListRow.setOnEventListener(BaseZYPExpandableListView.this);
            }
            _zypListRow.setEventListenter(mEventListener);
            _zypListRow.setList(list);
            _zypListRow.setData(getChildData(groupPosition, childPosition));

            _zypListRow.setPosition(groupPosition, childPosition);


            //			
            onGetChildView(_zypListRow, groupPosition, childPosition);

            return _zypListRow;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    protected class GroupHolder {
        ImageView icon;
        TextView title;
        TextView indictor;
    }

    private class TitleOnClickListener implements View.OnClickListener {

        private WorkTask mWorkTask;
        private int evenType;

        public TitleOnClickListener(int eventype, WorkTask workTask) {
            mWorkTask = workTask;
            evenType = eventype;
        }

        @Override
        public void onClick(View v) {
            showWorkOrderPopupWin(mWorkTask);
            if (mEventListener != null) {
                try {
                    mEventListener.eventProcess(evenType, mWorkTask);
                } catch (HDException e) {
                    e.printStackTrace();

                }
            }
        }

    }

    /**
     * showTitleInfoPopupWin:(显示作业票详细信息). <br/>
     */
    private void showWorkOrderPopupWin(SuperEntity workTask) {
        AlertDialog.Builder builer = new AlertDialog.Builder(getContext());
        if (mAlertDialog == null) {
            mAlertDialog = new MyAlertDialog(getContext(),
                    R.style.workorder_dialog, workTask, false);
        } else {
            mAlertDialog.setTabRow(workTask);
        }
        mAlertDialog.show();
        WindowManager.LayoutParams params = mAlertDialog.getWindow()
                .getAttributes();
        params.height = 800;
        mAlertDialog.getWindow().setAttributes(params);
    }

    private List<SuperEntity> getChildData(int groupPosition, int childPosition) {

        List<SuperEntity> _group = mCache.getGroup(groupPosition);
        int _start = childPosition * 4;
        int _end = childPosition * 4 + 4;
        if (_end > _group.size()) {
            _end = _group.size();
        }

        System.out.println("_group.size:" + _group.size() + "  _start:"
                + _start + "  _end:" + _end);

        return _group.subList(_start, _end);
    }

    /**
     * 此方法是对 ZYPListRow 的事件的实现， TODO
     *
     * @see com.hd.hse.common.component.phone.listener.IEventListener#eventProcess(int,
     * java.lang.Object[])
     */
    @Override
    public void eventProcess(int type, Object... args) throws HDException {
        switch (type) {
            case PhoneEventType.ZYPLIST_ITEM_CLICK:
                /*
                 * 这里做过修改 之前的代码是这样： onItemClick((WorkOrder) args[0], (View) args[1],
                 * (int) args[2]); 之前做法的含义是在基类中已经开始限制传递的参数类型，因为当时只有
                 * NormalZYPExpandableListView 这个子类需要点击事件，就在这里将参数的类型固化成了这一种，当面临
                 * BaseZYPListRow 与 BaseZYPExpandableListView
                 * 都要派生新的子类的时候，因为点击事件很可能不同，所以这里的参数如果固化了，就限制了子类的 继续派生。所以这个限制应该放在实际的
                 * BaseZYPExpandableListView 子类中去做。
                 */

                onItemClick(args);

                break;

            case PhoneEventType.ZYPLIST_ITEM_LONG_CLICK:
                if (mEventListener != null) {
                    mEventListener.eventProcess(
                            PhoneEventType.ZYPLIST_ITEM_LONG_CLICK, args);
                }
                break;
        }

    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    /** 留给子类来实现的函数们*********************************************** **/

    /**
     * 该方法由给 BaseListRow 实现的单击事件的监听中分离出来，留给子类来完成
     * <p>
     * onItemClick:(). <br/>
     * date: 2015年1月15日 <br/>
     *
     * @param workOrder
     * @param anchorView
     * @param pointerHorizontalPosition
     * @author xuxinwen
     */
    protected void onItemClick(Object... args) {
    }

    /**
     * 给 作业票下载界面使用的回调方法，让他完成 点击左边图标全选的功能。 onGetGroupView:(). <br/>
     * date: 2015年1月4日 <br/>
     *
     * @param viewHolder
     * @param groupPosition
     * @param workTask
     * @author xuxinwen
     */
    protected void onGetGroupView(GroupHolder viewHolder, int groupPosition) {
    }

    ;

    /**
     * 给下载列表提供选择功能接口。 onGetChildView:(). <br/>
     * date: 2015年1月4日 <br/>
     *
     * @param zypListRow
     * @param groupPosition
     * @param childPosition
     * @author xuxinwen
     */
    protected void onGetChildView(BaseZYPListRow zypListRow, int groupPosition,
                                  int childPosition) {
    }

    ;

    /**
     * 当创建 用于复用的 控件时，BaseZYPExpandableListView不负责实际控件的创建 ，而是将此创建操作留给子类。
     * onCreateChildView:(). <br/>
     * date: 2015年1月15日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    abstract protected BaseZYPListRow onCreateChildView();

    /**
     * 当创建用于复用的 Group 控件时， BaseZYPExpandableListView 将控件创建出来 然后调用此方法，来允许子类对
     * GroupView做一些个性化的设置。 onCreateGroupView:(). <br/>
     * date: 2015年1月15日 <br/>
     *
     * @param viewHolder
     * @param groupPosition
     * @author xuxinwen
     */
    protected void onCreateGroupView(GroupHolder viewHolder, int groupPosition) {
    }

}
