package com.hd.hse.common.module.phone.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.hd.hse.common.component.phone.custom.HSEActionBar;
import com.hd.hse.common.component.phone.custom.HseActionBarBranchMenu;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.event.homepage.IAppModuleClick;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static android.view.Gravity.START;

public class BaseFrameActivity extends BaseActivity {

    private static Logger logger = LogUtils.getLogger(BaseFrameActivity.class);
    /**
     * contentContainer:TODO(内容容器).
     */
    private FrameLayout contentContainer;
    /**
     * navigationContainer:TODO(导航栏容器).
     */
    private ListView navigationContainer;
    /**
     * drawer:TODO(布局).
     */
    private DrawerLayout drawer;
    /**
     * appModule:TODO(系统功能模块实体).
     */
    private AppModule appModule;

    /**
     * list:TODO(导航栏数据源).
     */
    private List list;

    /**
     * eventLst:TODO(事件监听).
     */
    private IEventListener eventLst;

    /**
     * hseBBM:TODO(菜单栏配置类).
     */
    private HseActionBarBranchMenu hseBBM;
    /**
     * hseAB:TODO(导航栏配置类).
     */
    private HSEActionBar hseAB;
    /**
     * queryWorkInfo:TODO(查询对象).
     */
    public IQueryWorkInfo queryWorkInfo;
    /**
     * pdaConfig:TODO(PDA配置表).
     */
    private PDAWorkOrderInfoConfig currentNaviTouchEntity;
    /**
     * workOrder:TODO(作业票实体).
     */
    public WorkOrder workOrder;

    /**
     * currentModuleName:TODO(当前模块名称).
     */
    public String currentModuleCode;

    public String searchStr;

    public boolean isfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.hd_hse_phone_base_frame_layout);

        contentContainer = (FrameLayout) this
                .findViewById(R.id.hd_hse_base_frame_layout_content_container);
        navigationContainer = (ListView) this
                .findViewById(R.id.hd_hse_base_frame_layout_navigation_container);
        drawer = (DrawerLayout) findViewById(R.id.hd_hse_phone_base_frame_layout_drawer_layout);
    }

    /**
     * setCustomActionBar:(设置自定义actionbar). <br/>
     * date: 2015年1月6日 <br/>
     *
     * @param isDrawerLayout 是否显示主功能导航栏
     * @param eventLst       下拉菜单的监听事件
     * @param actionFlags    actionBar显示的内容
     * @author zhaofeng
     */
    public void setCustomActionBar(boolean isDrawerLayout,
                                   IEventListener eventLst, String[] actionFlags) {
        hseAB = null;
        if (isDrawerLayout) {
            hseAB = new HSEActionBar(this, eventLst, actionFlags, drawer,
                    navigationContainer);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            hseAB = new HSEActionBar(this, eventLst, actionFlags);
        }
        this.eventLst = eventLst;
        hseAB.setEventListener(eventProcessor);
    }

    public void setCustomActionBar(boolean isDrawerLayout,
                                   IEventListener eventLst, String[] actionFlags, boolean needConfirm) {
        hseAB = null;
        if (isDrawerLayout) {
            hseAB = new HSEActionBar(this, eventLst, actionFlags, drawer,
                    navigationContainer);
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            hseAB = new HSEActionBar(this, eventLst, actionFlags, needConfirm);
        }
        this.eventLst = eventLst;
        hseAB.setEventListener(eventProcessor);
    }

    /**
     * setCustomMenuBar:(自定义菜单栏*需要先在导航栏显示IActionBar.IBTN_LEVELTWO_MORE*). <br/>
     * date: 2015年1月9日 <br/>
     *
     * @param controlsVisible 待显示的菜单栏项
     * @author wenlin
     */
    public void setCustomMenuBar(String[] controlsVisible) {
        hseBBM = new HseActionBarBranchMenu(getApplicationContext(), eventLst,
                controlsVisible);
        hseBBM.setEventListener(eventProcessor);
    }

    /**
     * setCurrentNaviTouchEntity:(拍照功能需预先设置实体信息). <br/>
     * date: 2015年1月12日 <br/>
     *
     * @param currentNaviTouchEntity
     * @author wenlin
     */
    public void setCurrentNaviTouchEntity(
            PDAWorkOrderInfoConfig currentNaviTouchEntity) {
        this.currentNaviTouchEntity = currentNaviTouchEntity;
    }

    /**
     * setQueryWorkInfo:(设置查询对象). <br/>
     * date: 2015年1月12日 <br/>
     *
     * @param queryWorkInfo
     * @author wenlin
     */
    public void setQueryWorkInfo(IQueryWorkInfo queryWorkInfo) {
        this.queryWorkInfo = queryWorkInfo;
    }

    /**
     * setWorkInfo:(作业票实体). <br/>
     * date: 2015年1月12日 <br/>
     *
     * @param workOrder
     * @author wenlin
     */
    public void setWorkInfo(WorkOrder workOrder) {
        this.workOrder = workOrder;

    }

    /**
     * setPopDetailWorkerOrer:(设置详细作业票信息实体). <br/>
     * date: 2015年1月21日 <br/>
     *
     * @param superEntity
     * @author wenlin
     */
    public void setPopDetailWorkerOrer(SuperEntity superEntity) {
        hseAB.setDyTable(superEntity);
    }

    /**
     * setActionBartitleContent:(设置导航栏标题内容). <br/>
     * date: 2015年1月21日 <br/>
     *
     * @param zyName
     * @param isIconTip 是否显示下拉图标
     * @author wenlin
     */
    public void setActionBartitleContent(String zyName, Boolean isIconTip) {
        hseAB.setTitleContent(zyName, isIconTip);
    }

    /**
     * eventProcessor:TODO().
     */
    public IEventListener eventProcessor = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub
            if (arg0 == IEventType.ACTIONBAR_PHOTOGRAPH_CLICK) {
                touchImages();
            } else if (arg0 == IEventType.MENUBAR_LEVELTWO_PHOTOMANAGER_CLICK) {
                managerImages();
            } else if (arg0 == IEventType.ACTIONBAR_LEVELTWO_MORE_CLICK) {
                hseBBM.showAsDropDown((View) arg1[0]);
            } else {
                eventLst.eventProcess(arg0, arg1);
            }
        }
    };

    /**
     * touchImages:(点击拍照). <br/>
     * date: 2015年1月9日 <br/>
     *
     * @author wenlin
     */
    private void touchImages() {
        if (null == queryWorkInfo) {
            logger.equals("请调用setQueryWorkInfo()方法设置查询对象");
            ToastUtils.toast(getBaseContext(), "请调用setQueryWorkInfo()方法设置查询对象");

        } else if (null == workOrder) {
            logger.equals("请调用setWorkInfo()设置作业票实体");
            ToastUtils.toast(getBaseContext(), "请调用setWorkInfo()设置作业票实体");
            return;
        }
        Image image = new Image();
        image.setTablename(workOrder.getDBTableName());// 表名
        image.setTableid(workOrder.getUd_zyxk_zysqid());// 表主键
        image.setImagepath(SystemProperty.getSystemProperty().getRootDBpath()
                + File.separator + workOrder.getUd_zyxk_zysqid());// 文件夹路径
        image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
                .getPersonid());// 创建人
        image.setCreateusername(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) currentNaviTouchEntity;
        image.setFuncode(config.getPscode());// 对应功能
        image.setContype(config.getContype());
        image.setImagename(config.getContypedesc());
        Intent intent = new Intent(getBaseContext(),
                CameraCaptureActivity.class);
        intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
        startActivity(intent);
    }

    /**
     * managerImage:(照片管理). <br/>
     * date: 2015年1月9日 <br/>
     *
     * @author wenlin
     */
    public void managerImages() {
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) currentNaviTouchEntity;
        try {
            if (null == queryWorkInfo) {
                logger.equals("请调用setQueryWorkInfo()方法设置查询对象");
                ToastUtils.toast(getBaseContext(),
                        "请调用setQueryWorkInfo()方法设置查询对象");

            } else if (null == workOrder) {
                logger.equals("请调用setWorkInfo()设置作业票实体");
                ToastUtils.toast(getBaseContext(), "请调用setWorkInfo()设置作业票实体");
                return;
            }
            List<Image> imageList = queryWorkInfo.queryPhoto(workOrder, config);
            Intent intent = new Intent(getApplicationContext(),
                    PhotoManagerActicity.class);
            intent.putExtra(PhotoManagerActicity.IMAGEENTITY,
                    (Serializable) imageList);
            startActivity(intent);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        View view = getLayoutInflater().inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        // TODO Auto-generated method stub
        contentContainer.removeAllViews();
        contentContainer.addView(view, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * getSearchStr:(查询时-获取查询条件). <br/>
     * date: 2015年1月7日 <br/>
     *
     * @return
     * @author zhaofeng
     */
    public String getSearchStr() {
        return searchStr == null ? "" : searchStr;
    }

    /**
     * setSearchStr:(设置查询条件). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @param str
     * @author lxf
     */
    public void setSearchStr(String str) {
        searchStr = str;
    }

    /**
     * onMenuClick:(任务列表单选). <br/>
     * date: 2015年1月7日 <br/>
     *
     * @param transitionDrawable
     * @return
     * @author lxf
     */
    public OnClickListener onMenuClick(
            final TransitionDrawable transitionDrawable) {
        OnClickListener lsn = new OnClickListener() {
            public void onClick(View arg0) {
                if (null != drawer) {
                    if (drawer.isDrawerVisible(START)) {
                        drawer.closeDrawer(START);
                    }
                }
                // 渐变
                transitionDrawable.startTransition(200);
                transitionDrawable.reverseTransition(100);
                // onMenuClickSub(arg0);
                if (currentModuleCode != null
                        && (!"woa-oea-app".equals(currentModuleCode)
                        && !"hse-wov-phone-app"
                        .equals(currentModuleCode) && !"hse-main-phone-app"
                        .equals(currentModuleCode))) {
                    logout(arg0);
                } else {
                    onMenuClickSub(arg0);
                }

            }
        };
        return lsn;
    }

    /**
     * logout:(安全退出). <br/>
     * date: 2015年1月5日 <br/>
     *
     * @author zhaofeng
     */
    private void logout(final View arg0) {
        String msg = null;
        if (arg0 instanceof TextView) {
            TextView menu = (TextView) arg0;
            appModule = (AppModule) menu.getTag();
            msg = "确定到" + appModule.getName() + "？";
        }
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(msg == null ? "您确定要退出?" : msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onMenuClickSub(arg0);
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

    /**
     * onMenuClickSub:(). <br/>
     * date: 2015年1月8日 <br/>
     *
     * @author lxf
     */
    public void onMenuClickSub(View arg0) {
        // Log.e("BaseFrameActivity", (new Date()).toString());
        TextView menu = (TextView) arg0;
        appModule = (AppModule) menu.getTag();
        if (appModule != null) {
            String clazz = appModule.getClickdealclass();
            if (StringUtils.isEmpty(clazz)) {
                ToastUtils.toast(getApplicationContext(), "该功能未上线，敬请期待~~~");
                return;
            }

//            if (!isIsfinish()) {
//                //后台没完成，并且点击的不是作业票浏览和作业票上传
//                if (!clazz.equals("com.hd.hse.wov.phone.ui.event.homepage.WorkOVApp") &&
//                        !clazz.equals("com.hd.hse.dc.phone.ui.event.homepage.WorkOrderUpdate")) {
//                    Toast.makeText(this, "正在进行权限更新,请稍后", Toast.LENGTH_LONG).show();
//                    return;
//
//                }
//            }

            try {
                // if (!"hse-main-phone-app".equals(currentModuleCode)) {
                // popActivity();
                // }
                // 单击事件处理
                IAppModuleClick appClick = newAppModuleClick(clazz);
                appClick.appModuleOnClick(appModule);
                // Log.e("BaseFrameActivity", (new Date()).toString());
                // if (!"hse-main-phone-app".equals(appModule.getCode())) {
                // // 单击事件处理
                // IAppModuleClick appClick = newAppModuleClick(clazz);
                // appClick.appModuleOnClick();
                // }else{
                // if(LocationSwCard.mTimer!=null){
                // LocationSwCard.mTimer.cancel();
                // LocationSwCard.mTimer = null;
                // }
                // // 清空刷卡位置
                // SystemProperty.getSystemProperty().setPositionCard(null);
                // }
            } catch (HDException e) {
                ToastUtils.imgToast(getApplicationContext(),
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }
        } else {
            ToastUtils.imgToast(getApplicationContext(),
                    R.drawable.hd_hse_common_msg_wrong, "未配置该模块功能，请联系系统管理员！");
        }
    }

    ;

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        // if (contentContainer == null)
        // contentContainer = (FrameLayout) this
        // .findViewById(R.id.hd_hse_base_frame_layout_content_container);
        // if (navigationContainer == null)
        // navigationContainer = (ListView) this
        // .findViewById(R.id.hd_hse_base_frame_layout_navigation_container);
        // if (drawer == null)
        // drawer = (DrawerLayout)
        // findViewById(R.id.hd_hse_phone_base_frame_layout_drawer_layout);

        super.onResume();
        // Log.e("BaseFrameActivity---onResume", (new Date()).toString());
    }

    public IAppModuleClick newAppModuleClick(String clazz) throws HDException {
        if (StringUtils.isEmpty(clazz)) {
            throw new HDException("未配置该模块功能，请联系系统管理员！");
        }
        try {
            Constructor constructor = Class.forName(clazz).getConstructor(
                    Context.class);
            try {
                IAppModuleClick appClick = (IAppModuleClick) constructor
                        .newInstance(this);
                return appClick;
            } catch (InstantiationException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("系统配置错误，请联系系统管理员！");
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("系统配置错误，请联系系统管理员！");
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("系统配置错误，请联系系统管理员！");
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("系统配置错误，请联系系统管理员！");
            }
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("系统配置错误，请联系系统管理员！");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("系统配置错误，请联系系统管理员！");
        }
    }

    /**
     * setNavContent:(设置导航栏列表信息). <br/>
     * date: 2015年1月6日 <br/>
     *
     * @param list
     * @param currentModuleName 当前所在app模块code
     * @author zhaofeng
     */
    public void setNavContent(List list, String currentModuleCode) {
        if (list != null) {
            this.list = list;
            this.currentModuleCode = currentModuleCode;
            NavListAdapter listAdapter = new NavListAdapter(currentModuleCode);
            navigationContainer.setAdapter(listAdapter);
        }
    }

    /**
     * setHSEActionBarVisible:(设置导航栏可见的按钮). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @param controlsName
     * @author lxf
     */
    public void setHSEActionBarVisible(String[] controlsName) {
        if (hseAB != null) {
            hseAB.setControlVisible(controlsName);
        }
    }

    /**
     * setHSEActionBarInVisible:(设置导航栏不可见的按钮). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @param controlsName
     * @author lxf
     */
    public void setHSEActionBarInVisible(String[] controlsName) {
        if (hseAB != null) {
            hseAB.setControlInVisible(controlsName);
        }
    }

    /**
     * setSearchVisible:(控制搜索框显示和隐藏). <br/>
     * date: 2015年3月18日 <br/>
     *
     * @param visible
     * @author lxf
     */
    public void setSearchVisible(boolean visible) {
        if (hseAB != null) {
            hseAB.setSearchVisible(visible);
        }
    }

    public class NavListAdapter extends BaseAdapter {
        // 当前所在模块名称
        private String currentModuleCode;

        public NavListAdapter(String currentModuleCode) {
            this.currentModuleCode = currentModuleCode;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int location, View view, ViewGroup group) {
            // TODO Auto-generated method stub
            TextView tv = null;
            if (view == null) {
                view = BaseFrameActivity.this.getLayoutInflater().inflate(
                        R.layout.hd_hse_common_item_textview, group, false);
                tv = (TextView) view
                        .findViewById(R.id.hd_hse_common_item_textview_tv);
                view.setTag(tv);
            } else {
                tv = (TextView) view.getTag();
            }
            if (list.get(location) instanceof AppModule) {
                AppModule aModule = (AppModule) list.get(location);

                int resimg = getResources().getIdentifier(
                        "drawable/" + aModule.getResimg(), "int",
                        getPackageName());
                Drawable drawable = getResources().getDrawable(resimg);
                tv.setId(location);
                // tv.setTextSize(getResources().getDimension(R.dimen.hd_hse_common_module_phone_drawer_textsize));
                tv.setTextColor(getResources().getColor(
                        R.color.hd_hse_common_white));
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTag(aModule);// 缓存
                tv.setHint(aModule.getCode());
                tv.setText(aModule.getName());

                // transition
                TransitionDrawable transitionDrawable = new TransitionDrawable(
                        new Drawable[]{drawable, drawable});
                transitionDrawable.setBounds(0, 0, drawable.getMinimumWidth(),
                        drawable.getMinimumHeight());
                tv.setCompoundDrawables(null, transitionDrawable, null, null);
                tv.setCompoundDrawablePadding(-1 * dip2px(BaseFrameActivity.this,
                        10f));
                // 添加事件
                OnClickListener lsner = onMenuClick(transitionDrawable);
                tv.setOnClickListener(lsner);
                // 当前所在app模块做高亮效果
                if (null != currentModuleCode
                        && currentModuleCode
                        .equalsIgnoreCase(aModule.getCode())) {
                    tv.setBackgroundResource(R.drawable.hd_common_component_phone_actionbar_item_shape);
                    tv.setClickable(false);
                } else {
                    tv.setBackgroundResource(0);
                    tv.setClickable(true);
                }

            } else {
                tv.setText((String) list.get(location));
            }
            return view;
        }
    }

    public void displayNewMore(boolean isNew) {
        if (hseAB != null) {
            hseAB.displayNewMore(isNew);
        }
    }

    /**
     * dismissActionbarJoinStstus:(取消显示actionBar上的合并确定和取消按钮). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @author LiuYang
     */
    public void dismissActionbarJoinStstus() {
        hseAB.whenJoinActionbarStyle(false);
    }

    public void showActionbarJoinStstus() {
        if (hseAB != null)
            hseAB.whenJoinActionbarStyle(true);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置标题栏搜索框提示语
     */
    public void setSearchHint(String hint) {
        if (hseAB != null) {
            hseAB.setSearchHint(hint);
        }

    }

    public boolean isIsfinish() {
        return isfinish;
    }

    public void setIsfinish(boolean isfinish) {
        this.isfinish = isfinish;
    }

}
