package com.hd.hse.main.phone.ui.activity.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hd.hse.main.phone.ui.activity.main.ManagerMainActivity;
import com.hd.hse.main.phone.ui.activity.main.WorkerInitialPageActivity;
import com.hd.hse.system.SystemProperty;

/**
 * Created by dubojian on 2017/7/26.
 * 处理MainActivity到DaLianActivity的事件冲突
 */

public class MainActivity_Linearlayout extends LinearLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {


    private GestureDetector gd;
    private int abheight;
    private float down_x;
    private float move_x;
    private boolean isleftmove;

    public int getAbheight() {
        return abheight;
    }

    public void setAbheight(int abheight) {
        this.abheight = abheight;
    }

    public MainActivity_Linearlayout(Context context) {
        this(context, null);
    }

    public MainActivity_Linearlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MainActivity_Linearlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnTouchListener(this);
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
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (SystemProperty.getSystemProperty().isLanZhouMShouYe()) {

            if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 0) {
                Intent intent = new Intent(getContext(), ManagerMainActivity.class);
                intent.putExtra("height", abheight);
                getContext().startActivity(intent);

            }

        }else if (SystemProperty.getSystemProperty().isLanZhouWShouYe()){

            if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 0) {
                Intent intent = new Intent(getContext(), WorkerInitialPageActivity.class);
                getContext().startActivity(intent);

            }

        }else if (SystemProperty.getSystemProperty().isBothShouYe()){
            if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 0) {
                Intent intent = new Intent(getContext(), ManagerMainActivity.class);
                intent.putExtra("height", abheight);
                getContext().startActivity(intent);

            }

        }

        return false;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gd.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                down_x = ev.getRawX();

                break;

            case MotionEvent.ACTION_MOVE:
                move_x = ev.getRawX();
                if (move_x - down_x < -100) {
                    isleftmove = true;
                } else {
                    isleftmove = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                isleftmove = false;
                break;
        }
        if (SystemProperty.getSystemProperty().isLanZhouMShouYe()||
                SystemProperty.getSystemProperty().isLanZhouWShouYe() ||
                SystemProperty.getSystemProperty().isBothShouYe()) {

            setLongClickable(true);

            gd.onTouchEvent(ev);
        }
        return isleftmove;
    }


}
