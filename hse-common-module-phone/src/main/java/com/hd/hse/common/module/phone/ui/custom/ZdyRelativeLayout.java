package com.hd.hse.common.module.phone.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class ZdyRelativeLayout extends RelativeLayout {

    private Handler hd;


    public ZdyRelativeLayout(Context context) {
        super(context);

    }

    public ZdyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ZdyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHandler(Handler hd) {
        this.hd = hd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                hd.removeMessages(0);
                break;
            case MotionEvent.ACTION_MOVE:
                hd.removeMessages(0);
                break;
            case MotionEvent.ACTION_UP:
                hd.sendEmptyMessageDelayed(0, 5000);
                break;

        }
        return  super.onTouchEvent(event);
    }


}
