package com.hd.hse.main.phone.ui.activity.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hd.hse.main.phone.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by dubojian on 2017/7/19.
 */

public class PieChartViewFragment extends Fragment {


    // piechartview
    private PieChartView chart;
    private PieChartData data;

    private boolean hasLabelForSelected = false;
    //数据集合
    private List<String> peichartlist;
    private List<String> peichartnumlist;
    private List<String> peichartlist_sx;
    private List<String> peichartnumlist_sx;

    private Handler hd = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    toast.cancel();
                    break;
                default:

                    break;
            }
        }
    };
    private Toast toast;

    public void setdata(List<String> pl, List<String> pnl) {
        this.peichartlist_sx = pl;
        this.peichartnumlist_sx = pnl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pie_chart_view_fragement, null);
        Bundle arguments = getArguments();
        if (arguments != null) {

            peichartlist = (List<String>) arguments.getSerializable("peichartlist");
            peichartnumlist = (List<String>) arguments.getSerializable("peichartnumlist");

            if (peichartlist_sx != null) {
                peichartlist.clear();
                peichartlist.addAll(peichartlist_sx);
            }
            if (peichartnumlist_sx != null) {
                peichartnumlist.clear();
                peichartnumlist.addAll(peichartnumlist_sx);
            }
        }

        initPieChartView(view);

        return view;
    }


    private void initPieChartView(View inflate) {
        // piechartview
        chart = (PieChartView) inflate.findViewById(R.id.chart);
        chart.setViewportCalculationEnabled(true);//设置饼图自动适应大小
        chart.setChartRotationEnabled(false);//设置饼图是否可以手动旋转
        chart.setCircleFillRatio((float) 0.8);//设置饼图其中的比例
        if (peichartlist != null && (peichartlist.size() > 0) && peichartnumlist != null && (peichartnumlist.size() > 0)) {
            chart.setOnValueTouchListener(new ValueTouchListener());
            generateData();
        }
    }


    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {

            hd.removeMessages(0);
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), peichartnumlist.get(arcIndex),
                    Toast.LENGTH_SHORT);
            toast.show();

            hd.sendEmptyMessageDelayed(0, 500);


        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    private void generateData() {
        int numValues = peichartlist.size();
        List<SliceValue> values = new ArrayList<SliceValue>();


//        float f[]=new float[]{10,80,30,100,60,50,20,40,20,35,11,76};
//        String content[] = new String[]{"橡胶", "炼油", "化肥",
//                "石化", "乙烯", "动力", "围堵", "催化剂", "助剂", "油品储运", "化工储运", "聚丙烯"};
        String color[] = new String[]{"#1BBC9B", "#25B7FA", "#2DCC70", "#96B4CC", "#3396AF"
                , "#FAB24E", "#E57C21", "#B73E35", "#DF5169", "#9B58B5", "#FFFFFF", "#E3E957"
                , "#F92440"};
        for (int i = 0; i < numValues; ++i) {
            //循环为饼图设置数据
            String s = peichartlist.get(i);

            if (s.length() > 4) {
                s = s.substring(0, 4) + "...";
            }
            float peinum = Float.parseFloat(peichartnumlist.get(i));
            SliceValue sliceValue = new SliceValue(
                    peinum, Color.parseColor(color[i])).setLabel(s);
            values.add(sliceValue);
        }

        data = new PieChartData(values);

        data.setHasLabels(true);
        data.setHasLabelsOutside(true);//设置饼图外面是否显示值
        data.setHasLabelsOnlyForSelected(hasLabelForSelected); //设置当值被选中才显示
        data.setCenterText1Typeface(Typeface.DEFAULT);//设置文本字体
        data.setValueLabelsTextColor(Color.BLACK);//设置显示值的字体颜色

        chart.setPieChartData(data);
    }


}
