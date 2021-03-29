package com.hd.hse.main.phone.ui.activity.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by dubojian on 2017/7/19.
 * 解决viewpager和viewpager里面控件的事件冲突
 */

public class MyViewPager extends ViewPager {


    private int sumheight;
    private float down_x;
    private float move_x;
    private float y;
    private int pageflag;
    private float move_y;

    public int getSumheight() {
        return sumheight;
    }

    public void setSumheight(int sumheight) {
        this.sumheight = sumheight;
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                y = ev.getRawY();
                down_x = ev.getRawX();
                break;

            case MotionEvent.ACTION_MOVE:
                move_x = ev.getRawX();
                move_y = ev.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                break;

        }
        //表单部分
        if (y > sumheight) {
            //第一页
            if (pageflag == 0) {
                //向左滑
                if (move_x - down_x < -50 || move_y - y < 50) {
                    //拦截
                    Log.e("jj", "myviewpager_<-50");
                    //这是一行神奇的代码
                    return super.onInterceptTouchEvent(ev);
                } else {
                    Log.e("ll", "myviewpager_>-50");

                    return false;
                }


            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    public void setpageflag(int pageflag) {
        this.pageflag = pageflag;
    }


}
