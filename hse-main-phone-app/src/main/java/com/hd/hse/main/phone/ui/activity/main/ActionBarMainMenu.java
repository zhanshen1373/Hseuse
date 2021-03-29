package com.hd.hse.main.phone.ui.activity.main;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.component.phone.adapter.PopMenuAdapter;
import com.hd.hse.common.component.phone.adapter.PopMenuItem;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.phone.ui.activity.download.DownLoadActivity;
import com.hd.hse.dc.phone.ui.activity.init.InitBaseProgressDialog;
import com.hd.hse.dc.phone.ui.activity.updata.UpdateBaseProgressDialog;
import com.hd.hse.dc.phone.ui.activity.upload.UpLoadActivity;
import com.hd.hse.dc.phone.ui.activity.vision.VersionUp;
import com.hd.hse.main.business.main.MainActionListener;
import com.hd.hse.main.phone.R;
import com.hd.hse.service.checkdata.CheckDataConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ActionBarMainMenu (一级导航栏右侧菜单)<br/>
 * date: 2014年12月30日 <br/>
 *
 * @author wenlin
 */
public class ActionBarMainMenu {

    public Context context;

    protected PopupWindow popupWindow;
    protected PopMenuAdapter menuAdapter;

    private boolean isVersionUp = false; // 是否需要版本更新

    /**
     * itemNames:TODO(菜单项描述).
     */
    private String[] itemNames;
    private CheckDataConfig checkDataConfig = new CheckDataConfig();

    public ActionBarMainMenu(Context context) {
        this.context = context;
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
                .getDisplayMetrics().widthPixels * 2 / 5,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        adjustInitPopupWindow(lstView);
    }

    /**
     * 预留给子类，让子类可以调整 PopupWindow 的一些设置。
     */
    protected void adjustInitPopupWindow(ListView lstView) {

    }

    private OnItemClickListener itemClick = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> list, View view, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            Intent intent = null;
            PopMenuItem operateItem = (PopMenuItem) list
                    .getItemAtPosition(position);
            String operteName = operateItem.getDescription();
            // 作业票下载
            if (itemNames[0].equals(operteName)) {
                intent = new Intent(context, DownLoadActivity.class);
                context.startActivity(intent);
            }
            // 作业票上传
            else if (itemNames[1].equals(operteName)) {
                intent = new Intent(context, UpLoadActivity.class);
                context.startActivity(intent);
            }
            // 权限更新
            else if (itemNames[2].equals(operteName)) {
//                if (((MainActivity) context).isIsfinish()) {
//                } else {
//                    Toast.makeText(context, "正在进行权限更新,请稍后", Toast.LENGTH_LONG).show();
//                }
                updateData();

            }
            // 初始化
            else if (itemNames[3].equals(operteName)) {
//                if (((MainActivity) context).isIsfinish()) {
//                } else {
//                    Toast.makeText(context, "正在进行权限更新,请稍后", Toast.LENGTH_LONG).show();
//                }
                initData();


            }
            // 版本升级
            else if (itemNames[4].equals(operteName)) {
                VersionUp vp = new VersionUp(context);
//                if (((MainActivity) context).isIsfinish()) {
//                } else {
//                    Toast.makeText(context, "正在进行权限更新,请稍后", Toast.LENGTH_LONG).show();
//                }
                vp.getVersionUpgradeInfo();

            } else if (itemNames[5].equals(operteName)) {
                // 退出
                logout();
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
                            if (context instanceof MainActivity) {
                                ((MainActivity) context).initAppModule();
                            }
                        } else if (eventType == IEventType.DOWN_WORK_LIST_SHOW) {
                            // 标记数据更新失败
                            checkDataConfig.updateFail();
                        }
                        // 校验数据库完整性
                        ((MainActivity) context).checkDb();
                    }
                });

                updateBase.execute("数据更新", "基础数据更新,请耐心等待....", "");
                dialog.dismiss();


            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        // 提示是初始化数据
        MessageDialog.Builder builder = new MessageDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("是否确定初始化?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                InitBaseProgressDialog initPro = null;
                initPro = new InitBaseProgressDialog(context);
                initPro.setGetDataSourceListener(new IEventListener() {
                    @Override
                    public void eventProcess(int eventType, Object... objects)
                            throws HDException {
                        // TODO Auto-generated method stub
                        if (eventType == IEventType.DOWN_WORK_LIST_LOAD) {
                            if (context instanceof MainActivity) {
                                ((MainActivity) context).initAppModule();
                            }
                        } else if (eventType == IEventType.DOWN_WORK_LIST_SHOW) {
                            // 标记初始化失败
                            checkDataConfig.initDataFail();
                        }
                        // 校验数据库完整性
                        ((MainActivity) context).checkDb();
                    }
                });
                initPro.execute("初始化", "基础数据初始化,请耐心等待....", "");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    /**
     * logout:(安全退出). <br/>
     * date: 2015年1月5日 <br/>
     *
     * @author zhaofeng
     */
    protected void logout() {
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("您确定要退出?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //即使线程没执行完，也要强行停止线程
//                SystemProperty.getSystemProperty().setStop(true);
                MainActionListener actionListener = new MainActionListener();
                BusinessAction action = new BusinessAction(actionListener);
                try {
                    // 后台业务处理
                    action.action(IActionType.MAIN_LOGOUT);
                    // 前台处理
                    SystemApplication.getInstance().exit();
                    NotificationManager systemService = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    systemService.cancelAll();
                } catch (HDException e) {
                    // TODO Auto-generated catch block
                    ToastUtils.toast(context, e.getMessage());
                }
                dialog.dismiss();
                // 设置你的操作事项
            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    protected List<PopMenuItem> getListViewItem() {
        // TODO Auto-generated method stub
        List<PopMenuItem> lstMenuItem = new ArrayList<PopMenuItem>();
        itemNames = context.getResources().getStringArray(
                R.array.hd_hse_main_phone_app_item);

        // 作业票下载
        PopMenuItem workOrderDown = new PopMenuItem();
        workOrderDown.setDescription(itemNames[0]);
        workOrderDown
                .setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_download);
        lstMenuItem.add(workOrderDown);

        // 作业票上传
        PopMenuItem workOrderUp = new PopMenuItem();
        workOrderUp.setDescription(itemNames[1]);
        workOrderUp
                .setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_upload);
        lstMenuItem.add(workOrderUp);

        // 权限更新
        PopMenuItem pmsnUpdate = new PopMenuItem();
        pmsnUpdate.setDescription(itemNames[2]);
        pmsnUpdate
                .setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_renew);
        lstMenuItem.add(pmsnUpdate);

        // 初始化
        PopMenuItem init = new PopMenuItem();
        init.setDescription(itemNames[3]);
        init.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_init);
        lstMenuItem.add(init);

        // 版本更新
        PopMenuItem versionUp = new PopMenuItem();
        versionUp.setDescription(itemNames[4]);
        versionUp
                .setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_upversion);
        versionUp.setRemind(isVersionUp);
        lstMenuItem.add(versionUp);
        // 退出
        PopMenuItem out = new PopMenuItem();
        out.setDescription(itemNames[5]);
        out.setDrawable(R.drawable.hd_hse_main_phone_app_item_icon_goback);
        lstMenuItem.add(out);

        return lstMenuItem;
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

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    /**
     * getMenuAdapter:(获取适配器). <br/>
     * date: 2015年8月17日 <br/>
     *
     * @return
     * @author lxf
     */
    public PopMenuAdapter getMenuAdapter() {
        return menuAdapter;
    }

    public boolean isVersionUp() {
        return isVersionUp;
    }

    public void setVersionUp(boolean isVersionUp) {
        this.isVersionUp = isVersionUp;
        initPopupWindow();
    }
}
