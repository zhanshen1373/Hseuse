/**
 * Project Name:hse-common-component
 * File Name:SpBar.java
 * Package Name:com.hd.hse.common.component.custom
 * Date:2014年10月9日
 * Copyright (c) 2014, zhulei@ushayden.com All Rights Reserved.
 */
package com.hd.hse.common.module.phone.custom;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.hd.hse.common.component.phone.custom.EditableDialogManager;
import com.hd.hse.common.component.phone.custom.ShowSignDialog;
import com.hd.hse.common.component.phone.custom.DeleteSignDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.signbusiness.SignManager;
import com.hd.hse.common.component.phone.util.ExamineNodeUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.List;

public class ExamineListView<T extends SuperEntity> extends ListView {
    private static Logger logger = LogUtils.getLogger(ExamineListView.class);
    private List<T> datas;
    // 记录 List 中选中项。
    private int selectedItemPosition;
    // 调用接口
    private IEventListener eventListent;
    // ListView 对应的 adapter
    private BaseAdapter adapter;
    // ListView 中选中项对应的 View
    public View currentItem;
    // 是否第一次刷新
    private boolean isNew = false;
    private String[] spfildstr;
    private Resources resources = getResources();
    private Context context;
    private IntentFilter myIntentFilter;
    // item居右距离
    private float itemPaddingRigth;
    // item中刷卡人文本框宽度
    private float itemEditTextWidth;
    // item刷卡人文本框字符长度
    private int itemEditTextEms;
    /**
     * edManager:TODO(弹出框).
     */
    private EditableDialogManager edManager;
    /**
     * 是否可以点击刷卡按钮
     */
    private boolean isClick = true;
    /**
     * fragment:TODO().
     */
    private Fragment fragment;


    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * setIsClick:(是否可以点击). <br/>
     * date: 2015年2月5日 <br/>
     *
     * @param isclick
     * @author lxf
     */
    public void setIsClick(boolean isclick) {
        this.isClick = isclick;
    }

    // 设置作业票信息
    private WorkOrder workOrder;

    private Handler handler;

    public Handler getHandler() {
        return handler;
    }


    private DeleteSignDialog deleteSignDialog;
    private boolean isDeleSign = false;//是否启用删除签名

    public void setHandler(Handler mHandler) {
        this.handler = mHandler;
    }

    /**
     * Creates a new instance of NavigationBar.
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ExamineListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    /**
     * Creates a new instance of NavigationBar.
     *
     * @param context
     * @param attrs
     */
    public ExamineListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * Creates a new instance of NavigationBar.
     *
     * @param context
     */
    public ExamineListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void init() {
        isDeleSign = SignManager.checkIsDeleSign();
        // 给 ListView 设置 Adapter，虽然现在还没有数据。
        adapter = new BaseAdapterImlp();
        isClick = true;
        setAdapter(adapter);
        this.setSelected(false);
        this.setSelector(R.color.hd_hse_common_component_phone_transparent);
        // setBackgroundResource(R.drawable.hd_hse_common_component_navigation_bg
        // );
        // 初始化长按弹出框对象
        edManager = new EditableDialogManager();
        setDivider(getResources().getDrawable(
                R.drawable.hd_hse_examine_blank_divider));
        // 注册广播
        registerBoradcastReceiver();

    }

    /**
     * 设置item居右的距离
     *
     * @param resId
     */
    public void setItemPadWidth(int resId) {
        if (null != resources) {
            itemPaddingRigth = resources.getDimension(resId);
        }
    }

    /**
     * 设置Item刷卡人的宽度
     *
     * @param resId
     */
    public void setItemEditTextWidth(int resId) {
        if (null != resources) {
            itemEditTextWidth = resources.getDimension(resId);
        }
    }

    /**
     * 设置item刷卡人的字符长度
     *
     * @param ems
     */
    public void setItemEditTextEms(int ems) {
        if (ems > 0) {
            itemEditTextEms = ems;
        }
    }

    /**
     * 获得当前被选中的 item 对应的 Entity，用于更改该实体的 状态，然后通过 setCurrentEntity 来更新界面。
     * <p>
     * setCurrentEntity:(). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @param currentEntity
     * @author zhulei
     */
    public SuperEntity getCurrentEntity() {
        if (datas != null && datas.size() > 0 && selectedItemPosition >= 0) {
            return (SuperEntity) datas.get(selectedItemPosition);
        }
        return null;
    }

    /**
     * 设置当前被选中的 item 对应的 Entity
     * <p>
     * setCurrentEntity:(). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @param currentEntity
     * @author zhulei
     */
    public void setCurrentEntity(T currentEntity) {
        SystemProperty.getSystemProperty().setClickSHbt(false);
        if (datas != null && datas.size() > 0) {
            // TODO
            datas.remove(selectedItemPosition);
            datas.add(selectedItemPosition, currentEntity);
            // updataItem(currentEntity, currentItem);
            // setData(datas);
        }
        if (adapter != null) {
            reFresh();
        }
    }

    private void updateView() {
        adapter.notifyDataSetChanged();
    }


    /**
     * reFresh:(lxf刷新). <br/>
     * date: 2014年11月17日 <br/>
     *
     * @author lxf
     */
    public void reFresh() {
        isNew = true;

        WorkApprovalPermission workApprovalPermission = null;
        String dycode = null;
        String udzysqid = workOrder.getUd_zyxk_zysqid();
        if (datas.get(0) instanceof WorkApprovalPermission) {
            workApprovalPermission = (WorkApprovalPermission) datas.get(0);
            dycode = workApprovalPermission.getZylocation();
        }

        //最后一项
//        if (selectedItemPosition == datas.size() - 1) {
//            //现在存的在会签最终环节点会用
//            SharedPreferences sharedPreferences = context.getSharedPreferences("lz", Context.MODE_PRIVATE); //私有数据
//            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//            editor.putString("dycode", dycode);
//            editor.putString("udzysqid", udzysqid);
//            editor.commit();//提交修改
//
//        }
//        SharedPreferences sp = context.getSharedPreferences("lz", Context.MODE_PRIVATE); //私有数据
//        String isCanClick = sp.getString("isCanClick", "");
//        String[] split = null;
//        if (!isCanClick.equals("")) {
//            //走过了一次会签
//            split = isCanClick.split("&");
//        }

        //同一套票
//        if (split != null && udzysqid.equals(split[1])) {
//
//        } else {

//            if (!isCanClick.equals(dycode + "&" + udzysqid)) {

        //措施确认里记录审核通过前后选中项是否相同
        if (SystemProperty.getSystemProperty().isCheckResultIsUpdate()) {
            //通过点击审核按钮过来的
            if (SystemProperty.getSystemProperty().isClickSHbt()) {

                if (datas != null && datas.size() > 0) {
                    //最后一个环节点的值不为空
                    if (datas.get(datas.size() - 1).getAttribute("persondesc") != null) {

                        for (int j = 0; j < datas.size(); j++) {

//                                    if (0 == (Integer) datas.get(j)
//                                            .getAttribute("isexmaineable")) {
//
//                                        datas.get(j).setAttribute("isCanLj", 0);
//                                        //记录的是不能累加的
//                                    } else {
//                                        datas.get(j).setAttribute("isCanLj", 1);
//                                    }

                            datas.get(j).setAttribute("sptime", null);

                        }


                    }

                }
            }


        }

//            }

//        }

        adapter.notifyDataSetChanged();
    }


    /**
     * 审核监听 setButtonListener:(). <br/>
     * date: 2014年10月10日 <br/>
     *
     * @param listener
     * @author zhulei
     */
    public void setIEventListener(IEventListener eventListent) {
        this.eventListent = eventListent;
    }

    public List<T> getDate() {
        return datas;
    }

    public void setData(List<T> datas) {
        this.datas = datas;
        // lxf 添加，如果环节点开始点击，后边换了数据源，此时记录的位置有问题。
        selectedItemPosition = 0;
        if (datas != null && datas.size() > 0) {
            // 判断审核顺序是否为空
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i) != null
                        && datas.get(i).getAttribute("pdapaixu") == null) {
                    return;
                }
            }
            isNew = true;
        }
        updateView();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void clearDatas() {
        datas = null;
    }

    /**
     * 设置作业票信息
     *
     * @param workOrder
     */
    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    /**
     * 自定义构造方法
     *
     * @param activity
     * @param handler
     * @param list
     */

    class BaseAdapterImlp extends BaseAdapter {
        private String personid, persondesc, spfield_desc;

        /**
         * TODO
         *
         * @see android.widget.Adapter#getCount()
         */
        public int getCount() {
            if (datas != null && datas.size() > 0) {
                return datas.size();
            }
            return 0;
        }

        /**
         * TODO
         *
         * @see android.widget.Adapter#getItem(int)
         */
        public Object getItem(int position) {
            if (datas != null && datas.size() > 0) {
                return datas.get(position);
            }
            return 0;
        }

        /**
         * TODO
         *
         * @see android.widget.Adapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * TODO
         *
         * @see android.widget.Adapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @SuppressLint("NewApi")
        public View getView(final int position, View convertView, ViewGroup arg2) {
            ViewHolder holder = null;
            // if (convertView == null) {
            convertView = View.inflate(getContext(),
                    R.layout.hd_hse_common_examine_item, null);

            holder = new ViewHolder();
            // 获取组件
            holder.textView = (TextView) convertView
                    .findViewById(R.id.hd_hse_common_examine_desc_tv);
            holder.editText = (EditText) convertView
                    .findViewById(R.id.hd_hse_common_examine_per_et);
            holder.button = (TextView) convertView
                    .findViewById(R.id.hd_hse_common_examine_bt);
            convertView.setTag(holder);

            if (itemPaddingRigth > 0) {
                holder.rl = (RelativeLayout) convertView
                        .findViewById(R.id.hd_hse_common_examine_rl);
                holder.rl.setGravity(Gravity.RIGHT);
                holder.rl.setPadding(0, 0, (int) itemPaddingRigth, 0);
            }
            if (itemEditTextWidth > 0) {
                holder.editText.setWidth((int) itemEditTextWidth);
            }

            if (itemEditTextEms > 0) {
                holder.editText.setEms(itemEditTextEms);
            }

            // } else {
            // holder = (ViewHolder) convertView.getTag();
            // }
            final SuperEntity itemData = datas.get(position);
            holder.editText.setFocusable(false);
            holder.editText.setTextColor(resources
                    .getColor(R.color.hd_hse_common_alerttext_black));

            holder.editText
                    .setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View arg0) {
                            selectedItemPosition = position;
                            T t = datas.get(position);

                            if (!isDeleSign) {
                                if (t instanceof WorkApprovalPermission) {
                                    WorkApprovalPermission wap = (WorkApprovalPermission) t;
                                    if (wap.getPersondesc() != null
                                            && !wap.getPersondesc().equals("")) {
                                        ShowSignDialog dialog = new ShowSignDialog(
                                                context, handler, wap);
                                        dialog.show();
                                    }
                                }
                            } else {
                                //吉林启用删除签名配置
                                if (checkWorkApprovalPermission(datas, position)) {
                                    if (t instanceof WorkApprovalPermission) {
                                        WorkApprovalPermission wap = (WorkApprovalPermission) t;
                                        if (deleteSignDialog == null) {
                                            deleteSignDialog = new DeleteSignDialog(context, wap);
                                            deleteSignDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                @Override
                                                public void onDismiss(DialogInterface dialogInterface) {
                                                    //dialog关闭时刷新listview
                                                    ExamineNodeUtils.sortExamineNode(datas);
                                                    notifyDataSetChanged();
                                                }
                                            });
                                        } else {
                                            deleteSignDialog.setWorkApprovalPermission(wap);
                                        }
                                        deleteSignDialog.show();
                                    }
                                }
                            }
                            return true;
                        }
                    });

            holder.button.setOnClickListener(new ItemButtonClickListenerImpl(
                    position, convertView)); // 为审核按钮加监听
            // 多人刷卡
            // if (itemData.getAttribute("bpermulcard") != null
            // && (int) itemData.getAttribute("bpermulcard") == 1) {
            // holder.editText
            // .setOnLongClickListener(new OnLongClickListener() {
            //
            // @Override
            // public boolean onLongClick(View v) {
            // if (itemData.getAttribute("persondesc") != null) {
            // edManager.showDialog(context, itemData
            // .getAttribute("persondesc")
            // .toString(), false);
            // }
            // return false;
            // }
            // });
            // }


            // 为组件赋值
            persondesc = (String) itemData.getAttribute("persondesc");
            personid = (String) itemData.getAttribute("personid");
            spfield_desc = (String) itemData.getAttribute("spfield_desc") + "：";
            spfildstr = spfield_desc.split("#");
            if (spfildstr.length > 1) {
                spfield_desc = spfildstr[spfildstr.length - 1];
            }
            if (persondesc != null && persondesc.replace(",", "").length() > 20) {
                int charNum = 0;
                charNum = persondesc.substring(0, 18).split(",").length;
                persondesc = persondesc.substring(0, 18 + charNum) + "...";
            }
            holder.textView.setText(spfield_desc);
            holder.editText.setText(persondesc);
            holder.editText.setHint(personid);


            if (isNew) {
                ExamineNodeUtils.sortExamineNode(datas);
                isNew = false;
            }


            if (0 == (Integer) datas.get(position)
                    .getAttribute("isexmaineable")) {
                holder.editText.setTextColor(resources
                        .getColor(R.color.hd_hse_common_describetext_gray));
                holder.button
                        .setBackground(resources
                                .getDrawable(R.drawable.hd_hse_common_button_examine_unusable));
                holder.button.setClickable(false);// 屏蔽监听事件
            }

            return convertView;
        }

        class ViewHolder {
            TextView textView;
            EditText editText;
            TextView button;
            RelativeLayout rl;
        }

        class ItemButtonClickListenerImpl implements OnClickListener {
            private int position;
            private View convertView;

            public ItemButtonClickListenerImpl(int position, View convertView) {
                this.position = position;
                this.convertView = convertView;
            }

            public void onClick(View v) {
                if (!isClick)
                    return;
                try {
                    if (eventListent != null) {
                        eventListent.eventProcess(
                                IEventType.EXAMINE_TOEXAMINE_ClICK, position,
                                datas.get(position));
                    }
                    selectedItemPosition = position;
                    datas.get(position).setAttribute("index", position);

                    Intent intent = null;
                    if (datas.get(position).getAttribute("isdzqm") != null
                            && datas.get(position).getAttribute("isdzqm")
                            .equals("1")) {
                        intent = new Intent(context,
                                PaintSignatureActivity.class);
                    } else {
                        intent = new Intent(context,
                                ReadCardExamineActivity.class);
                        // intent = new Intent(context,
                        // PaintSignatureActivity.class);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("workOrder", workOrder);
                    bundle.putSerializable("workApprovalPermission",
                            (WorkApprovalPermission) datas.get(position));
                    intent.putExtras(bundle);
                    if (fragment != null) {
                        fragment.startActivityForResult(intent,
                                IEventType.READCARD_TYPE);
                    } else {
                        ((Activity) context).startActivityForResult(intent,
                                IEventType.READCARD_TYPE);
                    }
                    // 防止多点击
                    isClick = false;
                } catch (HDException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 注册广播
     */
    public void registerBoradcastReceiver() {
        if (myIntentFilter == null) {
            myIntentFilter = new IntentFilter();
        }
        myIntentFilter.addAction(ReadCardExamineActivity.READCADRACTION);
        // 注册广播
        if (context == null)
            return;
        context.getApplicationContext().registerReceiver(mBroadcastReceiver,
                myIntentFilter);
    }

    /**
     * 广播接受
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ReadCardExamineActivity.READCADRACTION)) {
                return;
            }
            if (intent.getIntExtra(ReadCardExamineActivity.READCADRACTION, 0) == 1) {
                isClick = true;
            } else {
                isClick = false;
            }
        }

    };

    /**
     * 销毁广播
     */
    public void destroyBroadcast() {
        if (context.getApplicationContext() != null
                && mBroadcastReceiver != null) {
            context.getApplicationContext().unregisterReceiver(
                    mBroadcastReceiver);
        }
    }

    private boolean checkWorkApprovalPermission(List<T> datas, int position) {
        //1.最终换节点是否填写，未填写才能弹出
        for (T t : datas) {
            if (t instanceof WorkApprovalPermission
                    && ((WorkApprovalPermission) t).getIsend() == 1
                    && !TextUtils.isEmpty(((WorkApprovalPermission) t).getPersondesc())
                    && ((WorkApprovalPermission) t).getIsexmaineable() == 0) {
                return false;
            }
        }
        //
        T t = datas.get(position);
        if (t instanceof WorkApprovalPermission) {
            WorkApprovalPermission wap = (WorkApprovalPermission) t;
            if (TextUtils.isEmpty(wap.getPersondesc()))
                return false;
        }
        return true;
    }
}
