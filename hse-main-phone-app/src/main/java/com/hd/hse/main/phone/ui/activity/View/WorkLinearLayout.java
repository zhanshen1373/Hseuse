package com.hd.hse.main.phone.ui.activity.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hd.hse.main.phone.ui.activity.main.MainActivity;
import com.hd.hse.main.phone.ui.activity.main.ManagerMainActivity;
import com.hd.hse.system.SystemProperty;

/**
 * Created by dubojian on 2017/10/19.
 * 使用人员首页
 */

public class WorkLinearLayout extends LinearLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private GestureDetector gd;
    private float down_x;
    private float move_x;
    private float down_y;
    private float move_y;


    public WorkLinearLayout(Context context) {
        this(context, null);
    }

    public WorkLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        setLongClickable(true);
        gd = new GestureDetector(getContext(), this);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_x = ev.getRawX();
                down_y = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                move_x = ev.getRawX();
                move_y = ev.getRawY();
                break;

        }
        gd.onTouchEvent(ev);

        if (Math.abs(move_y-down_y)>10) {
            //不拦截
            return super.onInterceptTouchEvent(ev);
        }
        if (move_x - down_x > 50) {
            //拦截
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        if (SystemProperty.getSystemProperty().isBothShouYe()) {
            if (e2.getX() - e1.getX() > 30 && Math.abs(velocityX) > 0) {
                //切换Activity
                Intent intent = new Intent(getContext(), ManagerMainActivity.class);
                getContext().startActivity(intent);
            }
        } else if (SystemProperty.getSystemProperty().isLanZhouWShouYe()) {
            if (e2.getX() - e1.getX() > 30 && Math.abs(velocityX) > 0) {
                //切换Activity
                Intent intent = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        }


        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }
}
