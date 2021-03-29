package com.hd.hse.main.phone.ui.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dubojian on 2017/9/19.
 */

public class Time extends Thread {


    private String formatsecond;
    private String formatminute;
    private Handler hd = new Handler();
    private boolean flag;
    private int t = 0;
    private Context mContext;
    private List<MyUniformListAdapter.ViewHold> list = new ArrayList<>();
    private boolean clear;


    public void setView(Context context, MyUniformListAdapter.ViewHold view) {
        this.mContext = context;
        if (!list.contains(view)) {
            list.add(view);
        }
    }

    @Override
    public void run() {
        super.run();

        while (flag) {
            t++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < list.size(); i++) {
                doTime(list.get(i), (long) list.get(i).time_second.getTag());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    private void doTime(final MyUniformListAdapter.ViewHold view, final long second) {


        hd.post(new Runnable() {
            @Override
            public void run() {
                if (second < 0) {
                    view.tv_distance.setText("已过期");
                    view.tv_status.setText("过期");
                    view.tv_status.setBackgroundColor(mContext.getResources().getColor(
                            android.R.color.holo_red_light
                    ));
                    view.time_minute.setVisibility(View.INVISIBLE);
                    view.time_second.setVisibility(View.INVISIBLE);
                    view.time_point.setVisibility(View.INVISIBLE);
                } else {
                    long finalMillionSecond = second - t;
                    if (finalMillionSecond >= 0) {
                        view.tv_distance.setText("距结束");
                        view.time_minute.setVisibility(View.VISIBLE);
                        view.time_second.setVisibility(View.VISIBLE);
                        view.time_point.setVisibility(View.VISIBLE);
                        view.time_point.setText(":");
                        if (finalMillionSecond / 60 < 30) {
                            view.tv_status.setText("关注");
                            view.tv_status.setBackgroundColor(Color.parseColor("#FFCC00"));
                        } else {
                            view.tv_status.setText("");
                            view.tv_status.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }
                        formatsecond = new DecimalFormat("00").format(finalMillionSecond % 60);
                        formatminute = new DecimalFormat("00").format(finalMillionSecond / 60);
                        view.time_minute.setBackgroundColor(mContext.getResources().getColor(
                                android.R.color.holo_blue_dark));
                        view.time_second.setBackgroundColor(mContext.getResources().getColor(
                                android.R.color.holo_blue_dark));
                        view.time_minute.setText(formatminute);
                        view.time_second.setText(formatsecond);

                    } else {
                        view.tv_distance.setText("已过期");
                        view.tv_status.setText("过期");
                        view.tv_status.setBackgroundColor(mContext.getResources().getColor(
                                android.R.color.holo_red_light
                        ));
                        view.time_minute.setVisibility(View.INVISIBLE);
                        view.time_second.setVisibility(View.INVISIBLE);
                        view.time_point.setVisibility(View.INVISIBLE);

                    }
                }


            }
        });


    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
        t = 0;
    }

    public void setClear(boolean clear) {
        this.clear = clear;
        list.clear();
    }
}
