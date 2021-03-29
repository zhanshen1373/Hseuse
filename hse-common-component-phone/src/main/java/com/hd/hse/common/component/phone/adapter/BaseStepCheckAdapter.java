/**
 * Project Name:hse-common-component-phone
 * File Name:BaseStepCheckAdapter.java
 * Package Name:org.hse.common.component.phone.adapter
 * Date:2014年12月24日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.component.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.custom.EditableDialogManager;
import com.hd.hse.common.component.phone.custom.EditableDialogManager.EditableDialogListener;
import com.hd.hse.common.component.phone.custom.ShowSignDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.entity.workorder.WorkApplyMeasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 所有列表适配器的基类(手机版) ClassName:BaseStepCheckAdapter ().<br/>
 * Date: 2014年12月24日 <br/>
 *
 * @author flb
 * @see
 */
public abstract class BaseStepCheckAdapter<T extends SuperEntity>
        extends
        BaseAdapter implements OnLongClickListener {

    public void nextItem() {
    }

    public void previousItem() {
    }

    protected abstract int getItemResourceId();

    protected IEventListener mIEventListener;

    public void setIEventListener(IEventListener listener) {
        this.mIEventListener = listener;
    }

    /**************** 公用逻辑抽取 *********************/
    protected Context mContext = null;
    protected ListView mListView = null;
    protected List<T> mOriginalValues = null;
    protected List<String> attrs = null;
    protected String pcAttr = null;
    protected String isSelectedAttr = null;
    protected EditableDialogManager manager = null;

    protected int[] iconResLight = new int[]{
            R.drawable.hd_cbs_icon_step_check_no_one,
            R.drawable.hd_cbs_icon_step_check_yes_one,
            R.drawable.hd_cbs_icon_step_check_not_sure_one};
    // protected int[] iconResDark = new
    // int[]{R.drawable.hd_cbs_icon_step_check_no_two,
    // R.drawable.hd_cbs_icon_step_check_yes_two,
    // R.drawable.hd_cbs_icon_step_check_not_sure_two};

    protected int mFirstVisibleItem = 0;
    protected int mVisibleItemCount = 0;

    /**
     * @param mContext 上下文对象
     * @param dataList 数据列表
     * @param attrs    属性名称列表
     * @param attr     pc标识属性名称
     */
    protected BaseStepCheckAdapter(Context mContext, ListView mListView,
                                   List<T> mOriginalValues, List<String> attrs, String pcAttr) {
        this.mContext = mContext;
        this.mListView = mListView;
        this.mOriginalValues = mOriginalValues == null
                ? new ArrayList<T>()
                : new ArrayList<T>(mOriginalValues);
        this.attrs = attrs == null
                ? new ArrayList<String>()
                : new ArrayList<String>(attrs);
        this.pcAttr = pcAttr;
        this.manager = new EditableDialogManager();

        // 注册ListView滚动事件
        this.mListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                mFirstVisibleItem = firstVisibleItem;
                mVisibleItemCount = visibleItemCount;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

        });
    }

    @Override
    public int getCount() {
        return mOriginalValues.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mOriginalValues.size()) {
            return null;
        }
        return mOriginalValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getSourceValues() {
        return mOriginalValues;
    }

    public List<T> getCurrentSelectedValues() {
        List<T> mSelectedValues = new ArrayList<T>();
        for (int i = 0; i < mOriginalValues.size(); i++) {
            T t = mOriginalValues.get(i);
            boolean isSelected = (boolean) t.getAttribute(isSelectedAttr);
            if (isSelected) {
                mSelectedValues.add(t);
            }
        }

        if (mSelectedValues.size() != 0) {
            return mSelectedValues;
        } else {
            return null;
        }
    }

    /**
     * 得到有PC状态的对象
     *
     * @return
     */
    public List<T> getHavePcStatusValues() {
        List<T> mSelectedValues = new ArrayList<T>();
        for (int i = 0; i < mOriginalValues.size(); i++) {
            T t = mOriginalValues.get(i);
            if (t.getAttribute(pcAttr) != null && (int) t.getAttribute(pcAttr) != 0) {
                mSelectedValues.add(t);
            }
        }
        if (mSelectedValues.size() != 0) {
            return mSelectedValues;
        } else {
            return null;
        }

    }

    public List<T> getSelectedValues() {
        List<T> mSelectedValues = new ArrayList<T>();
        for (int i = 0; i < mOriginalValues.size(); i++) {
            T t = mOriginalValues.get(i);
            boolean isSelected = (boolean) t.getAttribute(isSelectedAttr);
            if (t.isModified() || isSelected) {
                mSelectedValues.add(t);
            }
        }

        if (mSelectedValues.size() != 0) {
            return mSelectedValues;
        } else {
            return null;
        }
    }

    public void updateValues(List<T> values) {
        for (int j = 0; j < values.size(); j++) {
            T temp = values.get(j);
            for (int i = 0; i < mOriginalValues.size(); i++) {
                T entity = mOriginalValues.get(i);
                if (entity.getAttribute(entity.getPrimaryKey()).equals(
                        temp.getAttribute(temp.getPrimaryKey()))) {
                    temp.setModified(false);
                    temp.setAttribute(isSelectedAttr, false);
                    mOriginalValues.set(i, temp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateCurrentValues(List<T> values) {
        for (int j = 0; j < values.size(); j++) {
            T temp = values.get(j);
            for (int i = 0; i < mOriginalValues.size(); i++) {
                T entity = mOriginalValues.get(i);
                if (entity.getAttribute(entity.getPrimaryKey()).equals(
                        temp.getAttribute(temp.getPrimaryKey()))) {
                    temp.setAttribute(isSelectedAttr, false);
                    mOriginalValues.set(i, temp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setIsSelectedAttr(String attrName) {
        this.isSelectedAttr = attrName;
    }

    @SuppressWarnings("unchecked")
    public static class ViewHolder {

        public static <T extends View> T getView(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view
                    .getTag(R.string.hd_hse_component_list_item_holder);
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(R.string.hd_hse_component_list_item_holder,
                        viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, getItemResourceId(), null);
        }

        return getItemView(position, convertView);
    }

    public OnLongClickListener signClickListener = new OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            Integer position = (Integer) v.getTag();
            T bean = mOriginalValues.get(position);
            if (bean instanceof WorkApplyMeasure
                    && bean.getAttribute("persondesc") != null
                    && !bean.getAttribute("persondesc").equals("")) {
                ShowSignDialog dialog = new ShowSignDialog(mContext,
                        (WorkApplyMeasure) bean);
                dialog.show();
                return true;
            } else {
                return false;
            }
        }
    };

    public abstract View getItemView(int position, View convertView);

    @Override
    public boolean onLongClick(View v) {
        Integer position = (Integer) v.getTag();
        final Integer order = (Integer) v
                .getTag(R.string.hd_hse_component_list_item_attribute_order);
        final T bean = mOriginalValues.get(position);
        String text = (String) bean.getAttribute(attrs.get(order));

        if (!TextUtils.isEmpty(text)) {
            manager.showDialog(mContext, text, brackets(text));
            manager.setOnEditableDialogListener(new EditableDialogListener() {

                @Override
                public void onConfirmClick(String content) {
                    bean.setAttribute(attrs.get(order), content);
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelClick(String content) {

                }
            });

            return true;
        } else {
            return false;
        }
    }

    private static boolean brackets(String text) {
        if (text.contains("(") || text.contains("（")) {
            Stack<Character> stack = new Stack<Character>();
            char[] charArray = text.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c != '\0') {
                    if (c == '(' || charArray[i] == '（') {
                        stack.push(c);
                    } else if (charArray[i] == ')') {
                        if (!stack.empty() && stack.peek() == '(') {
                            stack.pop();
                            continue;
                        } else {
                            return false;
                        }
                    } else if (charArray[i] == '）') {
                        if (!stack.empty() && stack.peek() == '（') {
                            stack.pop();
                            continue;
                        } else {
                            return false;
                        }
                    }
                }
            }

            if (stack.empty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}