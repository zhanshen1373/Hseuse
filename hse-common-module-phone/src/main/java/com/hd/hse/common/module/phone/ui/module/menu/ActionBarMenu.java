package com.hd.hse.common.module.phone.ui.module.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hd.hse.common.component.phone.adapter.PopMenuAdapter;
import com.hd.hse.common.component.phone.adapter.PopMenuItem;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.dc.business.common.weblistener.MessageEvent;
import com.hd.hse.service.checkdata.CheckDataConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * created by yangning on 2018/9/10 13:59.
 * 现场核查、关闭取消右侧more
 */
public class ActionBarMenu {

    public Context context;

    protected PopupWindow popupWindow;
    protected PopMenuAdapter menuAdapter;
    private final String join = "合并";
    private final String powerUpdate = "权限更新";
    private String[] menus;
    private IEventListener eventListener;
    private CheckDataConfig checkDataConfig = new CheckDataConfig();

    public ActionBarMenu(Context context,
                         String[] menus, IEventListener eventListener) {
        this.context = context;
        this.menus = menus;
        this.eventListener = eventListener;
        initPopupWindow();
    }

    private void initPopupWindow() {
        // TODO Auto-generated method stub
        List<PopMenuItem> lstMenuItem = getListViewItem();
        menuAdapter = new PopMenuAdapter(context, lstMenuItem);
        LayoutInflater inflater = LayoutInflater.from(context);
        View poupWindow = inflater
                .inflate(
                        R.layout.hd_hse_common_component_phone_actionbar_listview,
                        null);
        ListView lstView = (ListView) poupWindow
                .findViewById(R.id.hd_hse_common_phone_popmenu_listview);
        lstView.setAdapter(menuAdapter);
        lstView.setOnItemClickListener(itemClick);
        popupWindow = new PopupWindow(poupWindow, context.getResources()
                .getDisplayMetrics().widthPixels * 2 / 7,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

    }


    private List<PopMenuItem> getListViewItem() {
        List<PopMenuItem> popMenuItems = new ArrayList<>();
        if (menus != null && menus.length > 0) {
            for (String menu : menus) {
                if (IActionBar.TV_JOIN.equals(menu)) {
                    PopMenuItem popMenuItem = new PopMenuItem();
                    popMenuItem.setDescription(join);
                    popMenuItems.add(popMenuItem);
                } else if (IActionBar.TV_POWER_UPDATE.equals(menu)) {
                    PopMenuItem popMenuItem = new PopMenuItem();
                    popMenuItem.setDescription(powerUpdate);
                    popMenuItems.add(popMenuItem);
                }

            }
        }
        return popMenuItems;

    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> list, View view, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            PopMenuItem operateItem = (PopMenuItem) list
                    .getItemAtPosition(position);
            String operteName = operateItem.getDescription();
            // 权限更新
            if (powerUpdate.equals(operteName)) {
                updateData();
            } else if (join.equals(operteName)) {
                //回调给外层处理
                if (eventListener != null) {

                    try {
                        eventListener.eventProcess(IEventType.ACTIONBAR_JOIN_BTN_CLICK);
                    } catch (HDException e) {
                        e.printStackTrace();
                    }

                }

            }

            popupWindow.dismiss();
        }

    };

    /**
     * 权限更新
     */
    public void updateData() {
        // 提示是否更新数据
        MessageDialog.Builder builder = new MessageDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("是否确定数据更新?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UpdateBaseProgressDialog updateBase = new UpdateBaseProgressDialog(
                        context);
                updateBase.setGetDataSourceListener(new IEventListener() {

                    @Override
                    public void eventProcess(int eventType, Object... objects)
                            throws HDException {
                        // TODO Auto-generated method
                        // stub
                        if (eventType == IEventType.DOWN_WORK_LIST_LOAD) {
                            /*if (context instanceof MainActivity) {
                                ((MainActivity) context).initAppModule();
                            }*/
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.APP_MODULE_TYPE));
                        } else if (eventType == IEventType.DOWN_WORK_LIST_SHOW) {
                            // 标记数据更新失败
                            checkDataConfig.updateFail();
                        }
                        // 校验数据库完整性
                        //((MainActivity) context).checkDb();
                    }
                });

                updateBase.execute("数据更新", "基础数据更新,请耐心等待....", "");
                dialog.dismiss();


            }
        });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    /**
     * showAsDropDown:(显示弹出框). <br/>
     * date: 2014年12月29日 <br/>
     *
     * @param view
     * @author wenlin
     */
    public void showAsDropDown(View view) {
        popupWindow.showAsDropDown(view, 0, 0);
    }
}
