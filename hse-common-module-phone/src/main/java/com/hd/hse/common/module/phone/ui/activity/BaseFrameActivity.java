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
     * contentContainer:TODO(????????????).
     */
    private FrameLayout contentContainer;
    /**
     * navigationContainer:TODO(???????????????).
     */
    private ListView navigationContainer;
    /**
     * drawer:TODO(??????).
     */
    private DrawerLayout drawer;
    /**
     * appModule:TODO(????????????????????????).
     */
    private AppModule appModule;

    /**
     * list:TODO(??????????????????).
     */
    private List list;

    /**
     * eventLst:TODO(????????????).
     */
    private IEventListener eventLst;

    /**
     * hseBBM:TODO(??????????????????).
     */
    private HseActionBarBranchMenu hseBBM;
    /**
     * hseAB:TODO(??????????????????).
     */
    private HSEActionBar hseAB;
    /**
     * queryWorkInfo:TODO(????????????).
     */
    public IQueryWorkInfo queryWorkInfo;
    /**
     * pdaConfig:TODO(PDA?????????).
     */
    private PDAWorkOrderInfoConfig currentNaviTouchEntity;
    /**
     * workOrder:TODO(???????????????).
     */
    public WorkOrder workOrder;

    /**
     * currentModuleName:TODO(??????????????????).
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
     * setCustomActionBar:(???????????????actionbar). <br/>
     * date: 2015???1???6??? <br/>
     *
     * @param isDrawerLayout ??????????????????????????????
     * @param eventLst       ???????????????????????????
     * @param actionFlags    actionBar???????????????
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
     * setCustomMenuBar:(??????????????????*???????????????????????????IActionBar.IBTN_LEVELTWO_MORE*). <br/>
     * date: 2015???1???9??? <br/>
     *
     * @param controlsVisible ????????????????????????
     * @author wenlin
     */
    public void setCustomMenuBar(String[] controlsVisible) {
        hseBBM = new HseActionBarBranchMenu(getApplicationContext(), eventLst,
                controlsVisible);
        hseBBM.setEventListener(eventProcessor);
    }

    /**
     * setCurrentNaviTouchEntity:(???????????????????????????????????????). <br/>
     * date: 2015???1???12??? <br/>
     *
     * @param currentNaviTouchEntity
     * @author wenlin
     */
    public void setCurrentNaviTouchEntity(
            PDAWorkOrderInfoConfig currentNaviTouchEntity) {
        this.currentNaviTouchEntity = currentNaviTouchEntity;
    }

    /**
     * setQueryWorkInfo:(??????????????????). <br/>
     * date: 2015???1???12??? <br/>
     *
     * @param queryWorkInfo
     * @author wenlin
     */
    public void setQueryWorkInfo(IQueryWorkInfo queryWorkInfo) {
        this.queryWorkInfo = queryWorkInfo;
    }

    /**
     * setWorkInfo:(???????????????). <br/>
     * date: 2015???1???12??? <br/>
     *
     * @param workOrder
     * @author wenlin
     */
    public void setWorkInfo(WorkOrder workOrder) {
        this.workOrder = workOrder;

    }

    /**
     * setPopDetailWorkerOrer:(?????????????????????????????????). <br/>
     * date: 2015???1???21??? <br/>
     *
     * @param superEntity
     * @author wenlin
     */
    public void setPopDetailWorkerOrer(SuperEntity superEntity) {
        hseAB.setDyTable(superEntity);
    }

    /**
     * setActionBartitleContent:(???????????????????????????). <br/>
     * date: 2015???1???21??? <br/>
     *
     * @param zyName
     * @param isIconTip ????????????????????????
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
     * touchImages:(????????????). <br/>
     * date: 2015???1???9??? <br/>
     *
     * @author wenlin
     */
    private void touchImages() {
        if (null == queryWorkInfo) {
            logger.equals("?????????setQueryWorkInfo()????????????????????????");
            ToastUtils.toast(getBaseContext(), "?????????setQueryWorkInfo()????????????????????????");

        } else if (null == workOrder) {
            logger.equals("?????????setWorkInfo()?????????????????????");
            ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
            return;
        }
        Image image = new Image();
        image.setTablename(workOrder.getDBTableName());// ??????
        image.setTableid(workOrder.getUd_zyxk_zysqid());// ?????????
        image.setImagepath(SystemProperty.getSystemProperty().getRootDBpath()
                + File.separator + workOrder.getUd_zyxk_zysqid());// ???????????????
        image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
                .getPersonid());// ?????????
        image.setCreateusername(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) currentNaviTouchEntity;
        image.setFuncode(config.getPscode());// ????????????
        image.setContype(config.getContype());
        image.setImagename(config.getContypedesc());
        Intent intent = new Intent(getBaseContext(),
                CameraCaptureActivity.class);
        intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
        startActivity(intent);
    }

    /**
     * managerImage:(????????????). <br/>
     * date: 2015???1???9??? <br/>
     *
     * @author wenlin
     */
    public void managerImages() {
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) currentNaviTouchEntity;
        try {
            if (null == queryWorkInfo) {
                logger.equals("?????????setQueryWorkInfo()????????????????????????");
                ToastUtils.toast(getBaseContext(),
                        "?????????setQueryWorkInfo()????????????????????????");

            } else if (null == workOrder) {
                logger.equals("?????????setWorkInfo()?????????????????????");
                ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
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
     * getSearchStr:(?????????-??????????????????). <br/>
     * date: 2015???1???7??? <br/>
     *
     * @return
     * @author zhaofeng
     */
    public String getSearchStr() {
        return searchStr == null ? "" : searchStr;
    }

    /**
     * setSearchStr:(??????????????????). <br/>
     * date: 2015???3???4??? <br/>
     *
     * @param str
     * @author lxf
     */
    public void setSearchStr(String str) {
        searchStr = str;
    }

    /**
     * onMenuClick:(??????????????????). <br/>
     * date: 2015???1???7??? <br/>
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
                // ??????
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
     * logout:(????????????). <br/>
     * date: 2015???1???5??? <br/>
     *
     * @author zhaofeng
     */
    private void logout(final View arg0) {
        String msg = null;
        if (arg0 instanceof TextView) {
            TextView menu = (TextView) arg0;
            appModule = (AppModule) menu.getTag();
            msg = "?????????" + appModule.getName() + "???";
        }
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("??????");
        builder.setMessage(msg == null ? "???????????????????" : msg);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onMenuClickSub(arg0);
                // ????????????????????????

            }
        });
        builder.setNegativeButton("??????",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    /**
     * onMenuClickSub:(). <br/>
     * date: 2015???1???8??? <br/>
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
                ToastUtils.toast(getApplicationContext(), "?????????????????????????????????~~~");
                return;
            }

//            if (!isIsfinish()) {
//                //????????????????????????????????????????????????????????????????????????
//                if (!clazz.equals("com.hd.hse.wov.phone.ui.event.homepage.WorkOVApp") &&
//                        !clazz.equals("com.hd.hse.dc.phone.ui.event.homepage.WorkOrderUpdate")) {
//                    Toast.makeText(this, "????????????????????????,?????????", Toast.LENGTH_LONG).show();
//                    return;
//
//                }
//            }

            try {
                // if (!"hse-main-phone-app".equals(currentModuleCode)) {
                // popActivity();
                // }
                // ??????????????????
                IAppModuleClick appClick = newAppModuleClick(clazz);
                appClick.appModuleOnClick(appModule);
                // Log.e("BaseFrameActivity", (new Date()).toString());
                // if (!"hse-main-phone-app".equals(appModule.getCode())) {
                // // ??????????????????
                // IAppModuleClick appClick = newAppModuleClick(clazz);
                // appClick.appModuleOnClick();
                // }else{
                // if(LocationSwCard.mTimer!=null){
                // LocationSwCard.mTimer.cancel();
                // LocationSwCard.mTimer = null;
                // }
                // // ??????????????????
                // SystemProperty.getSystemProperty().setPositionCard(null);
                // }
            } catch (HDException e) {
                ToastUtils.imgToast(getApplicationContext(),
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }
        } else {
            ToastUtils.imgToast(getApplicationContext(),
                    R.drawable.hd_hse_common_msg_wrong, "??????????????????????????????????????????????????????");
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
            throw new HDException("??????????????????????????????????????????????????????");
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
                throw new HDException("????????????????????????????????????????????????");
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("????????????????????????????????????????????????");
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("????????????????????????????????????????????????");
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage(), e);
                throw new HDException("????????????????????????????????????????????????");
            }
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("????????????????????????????????????????????????");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("????????????????????????????????????????????????");
        }
    }

    /**
     * setNavContent:(???????????????????????????). <br/>
     * date: 2015???1???6??? <br/>
     *
     * @param list
     * @param currentModuleName ????????????app??????code
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
     * setHSEActionBarVisible:(??????????????????????????????). <br/>
     * date: 2015???3???4??? <br/>
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
     * setHSEActionBarInVisible:(?????????????????????????????????). <br/>
     * date: 2015???3???4??? <br/>
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
     * setSearchVisible:(??????????????????????????????). <br/>
     * date: 2015???3???18??? <br/>
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
        // ????????????????????????
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
                tv.setTag(aModule);// ??????
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
                // ????????????
                OnClickListener lsner = onMenuClick(transitionDrawable);
                tv.setOnClickListener(lsner);
                // ????????????app?????????????????????
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
     * dismissActionbarJoinStstus:(????????????actionBar?????????????????????????????????). <br/>
     * date: 2015???9???6??? <br/>
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
     * ??????????????????????????? px(??????) ????????? ????????? dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * ??????????????????????????? dp ????????? ????????? px(??????)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * ?????????????????????????????????
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
