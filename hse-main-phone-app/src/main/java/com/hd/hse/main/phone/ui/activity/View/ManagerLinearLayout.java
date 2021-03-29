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
import com.hd.hse.main.phone.ui.activity.main.WorkerInitialPageActivity;
import com.hd.hse.system.SystemProperty;

/**
 * Created by dubojian on 2017/7/26.
 * 处理DaLianActivity到MainActivity的事件冲突
 */

public class ManagerLinearLayout extends LinearLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {


    private GestureDetector gd;
    private boolean isleftmove;
    private float down_x;
    private float move_x;
    private int pagenum;
    private int forbidheight;
    private float down_y;
    private float move_y;

    public ManagerLinearLayout(Context context) {
        this(context, null);
    }

    public ManagerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ManagerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
        setLongClickable(true);
        gd = new GestureDetector(getContext(), this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isleftmove = false;
                down_x = ev.getRawX();
                down_y = ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                move_x = ev.getRawX();
                move_y = ev.getRawY();
                //向右滑
//                if (move_x - down_x > 50) {
//                    //拦截
//                    isleftmove = true;
//                } else {
//                    isleftmove = false;
//                }
                if (Math.abs(move_x - down_x) > 50) {
                    isleftmove = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                isleftmove = false;
                break;


        }
        //表单部分
        if (down_y > forbidheight) {

            //第一页
            if (pagenum == 0) {
                gd.onTouchEvent(ev);
                if (move_y - down_y > 20) {
                    //不拦截
                    isleftmove = false;
                }
//                else{
//                    isleftmove=true;
//                }
                return isleftmove;
            } else {
                //不拦截
                return super.onInterceptTouchEvent(ev);
            }
        } else {
            //不拦截
            return super.onInterceptTouchEvent(ev);

        }


    }

    public void setpageflag(int arg) {
        this.pagenum = arg;
    }

    public void setfrobidheight(int sumheight) {
        this.forbidheight = sumheight;
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
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


        if (e2.getX() - e1.getX() > 30 && Math.abs(velocityX) > 0) {
            //切换Activity
            Intent intent = new Intent(getContext(), MainActivity.class);
            getContext().startActivity(intent);
        }
        if (SystemProperty.getSystemProperty().isBothShouYe()) {
            if (e1.getX() - e2.getX() > 30 && Math.abs(velocityX) > 0) {
                //切换Activity
                Intent intent = new Intent(getContext(), WorkerInitialPageActivity.class);
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
