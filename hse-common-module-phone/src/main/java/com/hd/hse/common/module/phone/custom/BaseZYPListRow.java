/**
 * Project Name:hse-common-module-phone
 * File Name:ZYPListRow.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2014年12月26日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.custom.MyAlertDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ViewMeasureUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.camera.Base64Util;
import com.hd.hse.common.module.phone.camera.CloseSxt;
import com.hd.hse.common.module.phone.ui.activity.VlcActivity;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.workorder.SxtBean;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ZYPListRow ().<br/>
 * Date: 2014年12月26日 <br/>
 *
 * @author xuxinwen
 * @see
 */
public abstract class BaseZYPListRow extends LinearLayout implements
        OnClickListener, OnLongClickListener {

    private static Logger logger = LogUtils.getLogger(BaseZYPListRow.class);

    private final String TAG = "ZYPListRow";
    private boolean DEBUG = true;
    protected List<String> list;
    private boolean pd;

    /*
     * 控制一行显示几个 item
     */
    protected final int CTRL_ITEM_COUNT = 4;

    protected List<SuperEntity> mData;

    protected ViewHolder[] mHolders;
    private Intent intent;

    protected void setList(List list) {
        this.list = list;
    }

    /**
     * 在选择状态下选择图标的半径。
     */
    private int mSelectIconRadiu;

    private View _v;
    private Context context;
    /**
     * 作业票详情页面
     */
    private MyAlertDialog mAlertDialog;
    private IQueryWorkInfo quertWorkInfo;
    private CloseSxt closeSxt;

    @SuppressLint("NewApi")
    public BaseZYPListRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public BaseZYPListRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseZYPListRow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    protected class ViewHolder {
        View root;
        TextView icon;
        TextView text;
        TextView state;
        TextView workOrderState;
        TextView zypid;
        TextView sxt;
    }

    IEventListener eventLst = new IEventListener() {
        @SuppressWarnings("unchecked")
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.CLOSE_SXT:

                    if (objects[0] instanceof String){
                        intent.putExtra("address", (String) objects[0]);
                    }
                    //跳转新activity，播放流媒体
                    context.startActivity(intent);

                    break;

            }
        }
    };

    private void init() {
        closeSxt = new CloseSxt(context);
        closeSxt.setGetDataSourceListener(eventLst);
        // 设置方向
        setOrientation(LinearLayout.HORIZONTAL);

        // 每一个 item 对应的 View 树对应的 Holder
        ViewHolder _holder;

        mHolders = new ViewHolder[CTRL_ITEM_COUNT];

        /*
         * 这里为了实现每一行之间的分隔，但是对弹出 PopupWindow 的位置有影响
         */
        this.setPadding(
                0,
                getContext()
                        .getResources()
                        .getDimensionPixelOffset(
                                R.dimen.hd_hse_common_module_phone_zyplist_row_paddingTop),
                0,
                getContext()
                        .getResources()
                        .getDimensionPixelOffset(
                                R.dimen.hd_hse_common_module_phone_zyplist_row_paddingBottom));

        /*
         * 由于本控件与ListView结合使用，ListView的复用特性导致该控件自己维护的集合没有实际意义，需要ListView每次传递进来
         * 而且该传递进来的集合还必须是地址传递的，也就是在这里对集合的改变必须要影响ListView 中集合的值。
         *
         * 总的来说，状态的 数组，不能自己 new ，自己new 的外层的ListView 不好控制，所以需要由外层的ListView 传递进来。
         */

        for (int i = 0; i < CTRL_ITEM_COUNT; i++) {
            _v = View.inflate(getContext(),
                    R.layout.hse_common_module_phone_zypgrid_item, null);
            LayoutParams _lp = new LayoutParams(0,
                    LayoutParams.WRAP_CONTENT, 1);
            _v.setLayoutParams(_lp);
            _v.setTag(i);
            _v.setOnClickListener(this);
            _v.setOnLongClickListener(this);

            _holder = new ViewHolder();
            _holder.root = _v;
            _holder.icon = (TextView) _v
                    .findViewById(R.id.hse_common_module_phone_grid_item_icon);
            _holder.text = (TextView) _v
                    .findViewById(R.id.hse_common_module_phone_grid_item_text);
            _holder.state = (TextView) _v
                    .findViewById(R.id.hse_common_module_phone_grid_item_state);
            _holder.workOrderState = (TextView) _v
                    .findViewById(R.id.hse_common_module_phone_grid_item_workorder_state);

            _holder.zypid = (TextView) _v
                    .findViewById(R.id.hse_common_module_phone_grid_item_workorder_zypid);
            _holder.sxt = (TextView) _v.findViewById(R.id.hse_common_module_phone_grid_item_sxt);
            mHolders[i] = _holder;
            // 间距矫正， 勾选的图片适配问题，计算图片的大小设置 margin
            int _margin = ViewMeasureUtils.getMeasuredHeight(_holder.state) / 2;

            RelativeLayout.LayoutParams _iconLp = (RelativeLayout.LayoutParams) _holder.icon
                    .getLayoutParams();
            _iconLp.topMargin = _margin;
            _iconLp.rightMargin = _margin;

            RelativeLayout.LayoutParams _stateLp = (RelativeLayout.LayoutParams) _holder.state
                    .getLayoutParams();
            _stateLp.topMargin = -_margin;
            _stateLp.rightMargin = -_margin;

            // 记录的目的是提供给外部 PopupWindow 校准使用。
            mSelectIconRadiu = _margin;

            this.addView(_v);
        }


    }


    /**
     * 更改右上角表示状态的图标， setStateDrawable:(). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @author xuxinwen
     */
    public void setStateDrawable(int id) {
        for (int i = 0; i < CTRL_ITEM_COUNT; i++) {
            mHolders[i].state.setBackgroundResource(id);
        }
    }

    public void setData(List<SuperEntity> data) {
        if (data.size() > 4)
            throw new IllegalArgumentException(
                    "ZYPListRow.setData 只接受Size小于4的集合！！！");
        // TODO 这里对于
        mData = data;
        syncDataToUI();
    }

    private void syncDataToUI() {
        if (mData == null) {
            // TODO 暂定是否需要提供清空界面的功能
            return;
        }

        // 实体
        WorkOrder _workOrder;

        // 相应的 item
        View _child;

        // 数据 最大的 Index
        int _dataMaxIndex = mData.size() - 1;

        ViewHolder _holder;

        for (int i = 0; i < CTRL_ITEM_COUNT; i++) {
            _child = getChildAt(i);
            _holder = mHolders[i];

            if (i > _dataMaxIndex) {
                // 走到这里说明 数据不足 4 个，隐藏掉没有数据的控件
                _child.setVisibility(View.INVISIBLE);

                /*
                 * 控件被隐藏了，为什么还要设置图片呢？ 控件虽然被隐藏了，但是他还是占地方的，他还是参与空间的分配的
                 * 所以给他设置图片是为了让他的大小与其他控件的大小保持一致，不 会因为图片大小不同而导致与其他控件大小不同，因为如果不设置，
                 * 可能使用默认图片。
                 */
                _holder.icon.setBackgroundResource(IconForZYPClass
                        .getIconIDByZYPClass(IWorkOrderZypClass.ZYPCLASS_DHZY));

                continue;
            }

            // 获得承载数据的实体
            _workOrder = (WorkOrder) mData.get(i);

            // 获得item 对应的 View的跟节点，为了控制显示与隐藏，不是修改数据
            _child = getChildAt(i);
            _child.setVisibility(View.VISIBLE);

            if (_workOrder.getCameras() != null && _workOrder.getCameras().size() > 0) {
                _holder.sxt.setVisibility(VISIBLE);
                _holder.sxt.setBackgroundResource(R.drawable.sxt);
            } else {
                _holder.sxt.setVisibility(INVISIBLE);
                _holder.sxt.setBackgroundResource(0);
            }
            final WorkOrder final_workOrder = _workOrder;
            List<String> bitmapList = final_workOrder.getBitmapList();
            final List<Bitmap> list = new ArrayList<>();
            if (bitmapList != null && bitmapList.size() > 0) {
                for (int w = 0; w < bitmapList.size(); w++) {
                    Bitmap bitmap = Base64Util.base64ToBitmap(bitmapList.get(w));
                    list.add(bitmap);
                }
            }

            _holder.sxt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View inflate = LayoutInflater.from(context).inflate(R.layout.ninegrid, null);

                    PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0xaaaaaaaa));
                    popupWindow.showAsDropDown(v, 30, -30);
//                    popupWindow.showAtLocation(mainLinearlayout, Gravity.CENTER, 0, 0);
                    GridView gridView = (GridView) inflate.findViewById(R.id.surface_gridview);
                    NineGridAdapter nineGridAdapter = new NineGridAdapter(context, list);
                    gridView.setAdapter(nineGridAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            //跳转新activity，播放流媒体
                            intent = new Intent(context, VlcActivity.class);
                            String rtsp = final_workOrder.getCameras().get(position).getRtsp();
                            SxtBean.CamerasBean camerasBean = final_workOrder.getCameras().get(position);
                            intent.putExtra("cameras", camerasBean);
                            closeSxt.geteDownLoadWorkList("", rtsp,"");

                        }
                    });

                }
            });


            if (separateDownloadAndNot()) {

                if ("1".equals(_workOrder.getIsaccord())) {

                    _holder.icon.setBackgroundResource(IconForZYPClass
                            .getIsaccordZYPIconIDByZYPClass(_workOrder
                                    .getZypclass()));
                } else {
                    // 控制是否区别已下载与未下载的作业票
                    if (_workOrder.isDownloaded()) {
                        // 作业票已经下载
                        // _holder.root.setEnabled(false);
                        _holder.icon.setBackgroundResource(IconForZYPClass
                                .getIconIDByZYPClass(_workOrder.getZypclass()));
                    } else {
                        // _holder.root.setEnabled(true);
                        _holder.icon.setBackgroundResource(IconForZYPClass
                                .getDisabledZYPIconIDByZYPClass(_workOrder
                                        .getZypclass()));
                    }
                }

            } else {
                _holder.icon.setBackgroundResource(IconForZYPClass
                        .getIconIDByZYPClass(_workOrder.getZypclass()));
            }

            // 修改数据要通过 Holder 来完成。

            _holder.text.setText("[" + _workOrder.getZypclassname().replace("*", "") + "] " + _workOrder.getZyname());
            final WorkOrder mWorkOrder = _workOrder;
            _holder.text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mWorkOrder.isDownloaded()) {
                        showWorkOrderPopupWin(mWorkOrder);
                    } else {
                        if (quertWorkInfo == null) {
                            quertWorkInfo = new QueryWorkInfo();
                        }
                        try {
                            showWorkOrderPopupWin(quertWorkInfo.queryWorkInfo(mWorkOrder, null));
                        } catch (HDException e) {
                            e.printStackTrace();
                            logger.error(e.toString(), null);
                        }
                    }

                }
            });
            _holder.zypid.setText(_workOrder.getUd_zyxk_zysqid());

            if (showWorkOrderState()) {
                // 控制是否显示作业票的状态。
                // _holder.workOrderState.setVisibility(View.VISIBLE);
                try {
                    _holder.workOrderState
                            .setBackgroundResource(IconForZYPClass
                                    .getIconIDByZYPState(_workOrder.getStatus()));
                } catch (HDException e) {
                    _holder.workOrderState
                            .setBackgroundResource(R.drawable.hd_hse_common_module_zyp_state_appr);
                    Log.e(TAG, e.getMessage() + "  使用默认作业状态");
                    logger.error(e + "使用默认作业票");
                }
            } else {
                _holder.workOrderState.setVisibility(View.GONE);
            }

        }

    }

    public void showWorkOrderPopupWin(SuperEntity workOrder) {
        if (mAlertDialog == null) {
            mAlertDialog = new MyAlertDialog(context, R.style.workorder_dialog, workOrder, true);
        } else {
            mAlertDialog.setTabRow(workOrder);
        }
        mAlertDialog.show();
        WindowManager.LayoutParams params = mAlertDialog.getWindow()
                .getAttributes();
        params.height = 800;
        mAlertDialog.getWindow().setAttributes(params);
    }

    /**
     * 子类覆写该方法控制是否要区别显示已下载与未下载的作业票 separateDownloadAndNot:(). <br/>
     * date: 2015年3月2日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    protected boolean separateDownloadAndNot() {

        return false;
    }

    /**
     * 子类可覆写该方法调整返回值来控制是否要显示作业票的 状态。 showState:(). <br/>
     * date: 2015年3月2日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    protected boolean showWorkOrderState() {
        return false;
    }

    protected IEventListener mEventListener;

    public void setOnEventListener(IEventListener eventListener) {
        mEventListener = eventListener;
    }

    /**
     * 从activity中传过来的
     */
    protected IEventListener iEventListener;

    public void setEventListenter(IEventListener eventListener) {
        iEventListener = eventListener;
    }

    /**
     * 此控件的区别在于对点击事件的响应，比方 可选的 在点击事件中就要自己处理 点击后的选择状态，而弹窗的则要给外部
     * PopupWindow提供所要依附的 View
     *
     * @see OnClickListener#onClick(View)
     */
    @SuppressLint("NewApi")
    @Override
    abstract public void onClick(View v);

    /**
     * 此控件的长按事件都是相同的，所以把点击事件直接在基类中完成。 TODO
     *
     * @see OnLongClickListener#onLongClick(View)
     */
    @Override
    public boolean onLongClick(View v) {

        if (mEventListener != null) {
            try {
                int _offSet = v.getLeft() + v.getWidth() / 2;
                int _index = (int) v.getTag();

                mEventListener.eventProcess(
                        PhoneEventType.ZYPLIST_ITEM_LONG_CLICK,
                        mData.get(_index) // 对应的实体。
                );

            } catch (HDException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 得到 选择图标的 半径。 getSelectIconRadiu:(). <br/>
     * date: 2015年3月12日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    public int getSelectIconRadiu() {
        return mSelectIconRadiu;
    }

    protected int mGroupPosition;
    protected int mChildPosition;

    public int getGroupPosition() {
        return mGroupPosition;
    }

    public int getChildPosition() {
        return mChildPosition;
    }

    public void setGroupPosition(int groupPosition) {
        mGroupPosition = groupPosition;
    }

    public void setChildPosition(int childPosition) {
        mChildPosition = childPosition;
    }

    public void setPosition(int groupPosition, int childPosition) {
        mGroupPosition = groupPosition;
        mChildPosition = childPosition;
    }


}
