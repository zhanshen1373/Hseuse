/**
 * Project Name:hse-common-component-phone
 * File Name:CSQRListAdapter.java
 * Package Name:com.hd.hse.common.component.phone.adapter
 * Date:2015年1月29日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.component.phone.adapter;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.custom.BracketEditText;
import com.hd.hse.common.entity.SuperEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:CSQRListAdapter ().<br/>
 * Date: 2015年1月29日 <br/>
 *
 * @author xuxinwen
 * @version
 * @see
 */
public class CSQRListAdapter extends BaseAdapter {

    private final String TAG = this.getClass().getSimpleName();
    private final boolean DEBUG = true;

    private Context mContext;

    // 未选状态。
    private final static int ITEM_STATE_NORMAL = 0;
    // 选中状态
    private final static int ITEM_STATE_SELECED = 1;
    // 不可选状态。
    private final static int ITEM_STATE_DISABLE = 2;

    // 要显示的数据 集合
    private List<? extends SuperEntity> data;

    // 显示的 dialog
    private Dialog dialog;

    // 　实体类的 key
    private String showTextKey;

    // 　实体类的 key
    private String pcStateKey;

    // 　实体类的 key
    private String padStateKey;

    // 悬浮窗的 EditText
    private BracketEditText floatEditText;

    private int mLastPressedItem = -1;

    private ListView mListView;

    public CSQRListAdapter(ListView listView, List<? extends SuperEntity> data,
                           String showTextKey, String pcStateKey, String padStateKey) {

        if (showTextKey == null || pcStateKey == null || padStateKey == null) {
            // data 不为 null ， 但是 key 为空，抛异常。
            throw new IllegalArgumentException(
                    "showTextKey, pcStateKey or padStateKey can`t be null !!!");
        }

        mListView = listView;
        mContext = listView.getContext();
        this.data = data;
        this.showTextKey = showTextKey;
        this.pcStateKey = pcStateKey;
        this.padStateKey = padStateKey;

        mListView.setSelector(R.drawable.blank_selector);
        mListView.setPadding(0, 5, 0, 5);
        mListView.setDivider(mContext.getResources().getDrawable(
                R.drawable.divider_10));
        mListView.setDividerHeight(10);
        mListView.setOnItemClickListener(new InnerItemClickListener());
        mListView.setOnItemLongClickListener(new ItemLongClickListenerImpl());
        mListView.setAdapter(this);

    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SuperEntity item = data.get(position);
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.hd_hse_common_component_protection_item, null);

            holder = new ViewHolder();
            holder.text = (TextView) convertView
                    .findViewById(R.id.hd_hse_common_component_protection_text_tv);
            holder.image = (ImageView) convertView
                    .findViewById(R.id.hd_hse_common_component_protection_rightImage_iv);
            holder.background = (RelativeLayout) convertView
                    .findViewById(R.id.hd_hse_common_component_protection_itembg_rl);
            holder.isPcIcon = (ImageView) convertView
                    .findViewById(R.id.hd_hse_common_component_protection_isPc);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 状态样式的设置

        // -------------------------------------------------------------------

        // 得到该 item 的状态信息。
        Integer pcState = (Integer) item.getAttribute(pcStateKey);
        Integer padState = (Integer) item.getAttribute(padStateKey);

        //lxf 修改  不加判断
        // if (pcState != null && pcState == 1 && padState == null)
        if (pcState != null && pcState == 1) {
            // 不可选状态。 TODO
            setItemAppearance(holder, ITEM_STATE_DISABLE);
        } else {
            // 可选状态。
            if (padState != null && padState == 1) {
                // 已选中状态。
                setItemAppearance(holder, ITEM_STATE_SELECED);
            } else {
                // 未选中状态。
                setItemAppearance(holder, ITEM_STATE_NORMAL);
            }
        }

        if (position == mLastPressedItem) {
            holder.background
                    .setBackgroundResource(R.drawable.hd_hse_common_component_selectable_gridview_selected_bg);
            holder.text.setTextColor(Color.WHITE);
        } else {
            holder.background
                    .setBackgroundResource(R.drawable.hd_hse_common_component_selectable_gridview_normal_bg);
            holder.text.setTextColor(Color.BLACK);
        }
        // end
        // 状态样式的设置-------------------------------------------------------------------

        // 设置文字
        holder.text.setText((String) item.getAttribute(showTextKey));

        return convertView;
    }

    /**
     * 设置某个 item 的样式。 setItemAppearance:(). <br/>
     * date: 2014年10月9日 <br/>
     *
     * @author xuxinwen
     * @param v
     * @param appearance
     */
    private void setItemAppearance(View v, int appearance) {
        setItemAppearance((ViewHolder) v.getTag(), appearance);
    }

    /**
     * 设置某个 item 项的样式。 setItemAppearance:(). <br/>
     * date: 2014年10月9日 <br/>
     *
     * @author xuxinwen
     * @param holder
     * @param appearance
     */
    private void setItemAppearance(ViewHolder holder, int appearance) {
        // TODO 更改图片
        switch (appearance) {
            case ITEM_STATE_NORMAL:
                holder.background
                        .setBackgroundResource(R.drawable.hd_hse_common_component_selectable_gridview_selected_bg);
                holder.image.setVisibility(View.GONE);
                holder.isPcIcon.setVisibility(View.GONE);
                break;

            case ITEM_STATE_SELECED:
                holder.background
                        .setBackgroundResource(R.drawable.hd_hse_common_component_selectable_gridview_selected_bg);
                holder.image.setVisibility(View.VISIBLE);
                holder.image
                        .setImageResource(R.drawable.hd_cbs_icon_step_check_yes_one);
                holder.isPcIcon.setVisibility(View.GONE);
                break;

            case ITEM_STATE_DISABLE:
                holder.background
                        .setBackgroundResource(R.drawable.hd_hse_common_component_selectable_gridview_selected_bg);

                holder.image.setVisibility(View.VISIBLE);
                holder.image
                        .setImageResource(R.drawable.hd_cbs_icon_step_check_yes_two);

                holder.isPcIcon.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 返回已经修改过的 实体。 getModifiedData:(). <br/>
     * date: 2014年10月9日 <br/>
     *
     * @author xuxinwen
     * @return
     */
    public List<SuperEntity> getModifiedData() {
        List<SuperEntity> modified = new ArrayList<SuperEntity>();

        for (SuperEntity item : data) {
            if (item.isModified()) {
                modified.add(item);
            }
        }

        return modified;
    }

    /**
     * 得到通过 setData(List<SuperEntity> data) 方法设置的数据。
     *
     * getData:(). <br/>
     * date: 2014年9月23日 <br/>
     *
     * @author xuxinwen
     * @return
     */
    public List<? extends SuperEntity> getData() {

        return data;
    }

    class ViewHolder {
        TextView text;
        ImageView image; // 可能会用到
        RelativeLayout background;
        ImageView isPcIcon;
    }

    /**
     * 设置给 ListView 的 item 点击监听，实现点击条目变化。 ClassName: InnerItemClickListener ()<br/>
     * date: 2015年1月29日 <br/>
     *
     * @author xuxinwen
     * @version CSQRListAdapter
     */
    class InnerItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            if (isItemEnabled) {
                // 覆写这个方法，在中间添加 点击 item 的背景切换效果。
                SuperEntity item = data.get(position);

                Integer pcState = (Integer) item.getAttribute(pcStateKey);
                Integer padState = (Integer) item.getAttribute(padStateKey);

                if (pcState != null && pcState == 1 && padState == null) {
                    // 不可选状态。 什么也不做

                } else {
                    mLastPressedItem = position;

                    // 可选状态。
                    if (padState != null && padState == 1) {
                        // 已选中状态。将实体中信息设置为未选中状态，并且更改背景为未选中状态。
                        item.setAttribute(padStateKey, 0);

                        setItemAppearance(view, ITEM_STATE_NORMAL);
                    } else {
                        // 未选中状态。 将实体中信息设置为选中状态，并且更改背景为选中状态。
                        item.setAttribute(padStateKey, 1);

                        setItemAppearance(view, ITEM_STATE_SELECED);
                    }

                    // 状态肯定被更改了。
                    if (item.isModified()) {
                        item.setModified(false);
                    } else {
                        item.setModified(true);
                    }
                    notifyDataSetChanged();
                }
            }

        }

    }

    private View vDialog;

    /**
     * item 的长按监听，主要实现了长按某 item 的时候如果该item 的文字描述没有被完全显示出来
     * 的情况会弹出悬浮框，将完整的文字内容显示出来。
     *
     * ClassName: onItemLongClickListenerImpl ()<br/>
     * date: 2014年9月24日 <br/>
     *
     * @author xuxinwen
     * @version SelectableGridView
     */
    class ItemLongClickListenerImpl implements OnItemLongClickListener {

        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                       int position, long id) {
            // 主要处理 悬浮窗。
            Holder _holder = null;
            SuperEntity item = data.get(position);
            String text = (String) item.getAttribute(showTextKey);

            // TODO 可以优化的地方。
            floatEditText = new BracketEditText(mContext);
            floatEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(
                    R.dimen.hd_common_textsize));

            floatEditText.setBackgroundDrawable(null);
            floatEditText.setHasBracketsText(text);

            if (vDialog == null) {
                _holder = new Holder();
                vDialog = View
                        .inflate(
                                mContext,
                                R.layout.hd_hse_common_component_step_check_text_dialog,
                                null);
                _holder.container = (RelativeLayout) vDialog
                        .findViewById(R.id.hd_hse_common_component_step_check_text_dialog_rl_container);
                _holder.confirm = (TextView) vDialog
                        .findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_confirm);
                _holder.cancle = (TextView) vDialog
                        .findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_cancel);

                vDialog.setTag(_holder);

            }

            if (dialog == null) {
                dialog = new Builder(mContext).setView(vDialog).create();
            }

            _holder = (Holder) vDialog.getTag();
            _holder.container.removeAllViews();
            _holder.container.addView(floatEditText);

            _holder.cancle.setOnClickListener(new ClickListenerImpl(null));
            _holder.confirm.setOnClickListener(new ClickListenerImpl(item));

            if (floatEditText.isTextHasBrackets()) {
                // 文本可编辑 ，显示确定按钮
                _holder.confirm.setVisibility(View.VISIBLE);
            } else {
                // 文本不可编辑，隐藏确定按钮
                _holder.confirm.setVisibility(View.GONE);
            }

            dialog.show();

            return true;

        }

    }

    private boolean isItemEnabled = true;

    public void setItemsEnabled(boolean enabled) {
        isItemEnabled = enabled;
    }

    class Holder {
        public TextView cancle;
        public TextView confirm;
        public RelativeLayout container;
    }

    class ClickListenerImpl implements View.OnClickListener {

        private SuperEntity selectedItem;

        public ClickListenerImpl(SuperEntity selectedItem) {
            this.selectedItem = selectedItem;
        }

        public void onClick(View v) {

            if (selectedItem == null) {
            } else {
                if (floatEditText != null) {
                    // 将更改后的数据储存到 实体类中。
                    selectedItem.setAttribute(showTextKey, floatEditText
                            .getText().toString());
                    selectedItem.setModified(true);
                    notifyDataSetChanged();
                }
            }
            if (dialog != null) {
                InputMethodManager imm = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(floatEditText.getWindowToken(), 0);
                dialog.dismiss();
                floatEditText = null;
            }

        }
    }
}
