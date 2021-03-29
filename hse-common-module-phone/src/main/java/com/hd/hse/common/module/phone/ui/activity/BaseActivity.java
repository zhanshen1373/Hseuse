package com.hd.hse.common.module.phone.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.FragmentActivity;

import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.utils.BitmapUtils;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.system.SystemProperty;

public class BaseActivity extends FragmentActivity {

    //从系统属性类得到flag，用来区分使用人员.
    protected String flag;
    private int titleResId = R.string.hd_hse_main_app_main_title;
    protected int actionBarHeight;

    public int getTitleResId() {
        return titleResId;
    }

    public void setTitleResId(int titleResId) {
        this.titleResId = titleResId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        SystemApplication.getInstance().pushActivity(this);
        flag = SystemProperty.getSystemProperty().getFlag();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initActionBar();

        //changeFont((ViewGroup)this.getWindow().getDecorView());
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        SystemApplication.getInstance().removePopActivty(this);
        super.finish();
    }

    /**
     * initActionBar:(设置状态栏). <br/>
     * date: 2014年10月28日 <br/>
     *
     * @author lxf
     */
    @SuppressLint("NewApi")
    public void initActionBar() {
        ActionBar actionBar = getActionBar();
        //得到actionbar的高度
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        if (actionBar != null) {
            actionBar.setTitle(titleResId);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.hd_hse_common_component_actionbar_bg));
        }
    }

    /**
     * popActivity:(销毁当前界面). <br/>
     * date: 2014年10月11日 <br/>
     *
     * @author lxf
     */
    public void popActivity() {
        SystemApplication.getInstance().popActivity();
    }


    private static long lastClickTime;

    /**
     * isFastDoubleClick:(防止多次快速点击). <br/>
     * date: 2014年9月15日 <br/>
     *
     * @return
     * @author lxf
     */
    public boolean isFastDoubleClick() {
        long time = ServerDateManager.getCurrentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500) {
            String msg = getResources().getString(
                    R.string.hd_hse_main_app_Multipleclicks_title);
            ToastUtils.toast(BaseActivity.this, msg);
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * setBackground:(设置背景). <br/>
     * date: 2015年1月26日 <br/>
     *
     * @param view
     * @param resId
     * @author zhulei
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(View view, int resId) {
        if (view == null) {
            return;
        }
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(BitmapUtils.decodeBitmap(getResources(),
                    resId));
        } else {
            view.setBackground(BitmapUtils.decodeBitmap(getResources(), resId));
        }
    }


    /**
     * hideInputKeyboard:(隐藏输入法键盘). <br/>
     * date: 2014年10月18日 <br/>
     *
     * @param v
     * @author lxf
     */
    public void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            popActivity();
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * changeFont:(修改文字显示格式). <br/>
     * date: 2015年2月9日 <br/>
     *
     * @author zhaofeng
     * @param root
     */
    /*protected void changeFont(ViewGroup root) {
		Typeface tf = Typeface.createFromAsset(getAssets(),
		"fonts/STHEITI-MEDIUM.TTC");    
        for(int i = 0; i <root.getChildCount(); i++) {
                 View v = root.getChildAt(i);
                 if(v instanceof TextView ) {
                         ((TextView)v).setTypeface(tf);
//                         ((TextView)v).setTextSize(15);
//                         ((TextView)v).setTextColor(Color.GRAY);
                 } else if(v instanceof Button) {
                         ((Button)v).setTypeface(tf);
//                         ((Button)v).setTextSize(15);
//                         ((Button)v).setTextColor(Color.GRAY);
                 } else if(v instanceof EditText) {
                         ((EditText)v).setTypeface(tf);
//                         ((EditText)v).setTextSize(15); 
//                         ((EditText)v).setTextColor(Color.GRAY);
                 } else if(v instanceof ViewGroup) {
                         changeFont((ViewGroup)v);
                 }
         }
         
     }*/
}
