package com.hd.hse.main.phone.ui.activity.fragment;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hd.hse.main.phone.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by dubojian on 2017/7/19.
 */

public class ColumnChartViewFragment extends Fragment {


    // columnchartview
    private ColumnChartView columnchart;
    private TextView columnchart_textview;
    private static final int DEFAULT_DATA = 0;

    private ColumnChartData columnchartdata;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private int dataType = DEFAULT_DATA;
    //数据集合
    private List<String> axis;
    private List<String> columnchartlist;
    private List<String> columnchartnumlist;
    private List<String> axisdept;
    private List<String> columnchartunicode;

    private List<String> axis_sx;
    private List<String> columnchartlist_sx;
    private List<String> columnchartnumlist_sx;
    private List<String> axisdept_sx;
    private List<String> columnchartunicode_sx;


    public void setdata(Object... objects) {
        if (objects[0] instanceof List) {
            axis_sx = (List<String>) objects[0];
        }
        if (objects[1] instanceof List) {
            axisdept_sx = (List<String>) objects[1];
        }
        if (objects[2] instanceof List) {
            columnchartlist_sx = (List<String>) objects[2];
        }
        if (objects[3] instanceof List) {
            columnchartnumlist_sx = (List<String>) objects[3];
        }
        if (objects[4] instanceof List) {
            columnchartunicode_sx = (List<String>) objects[4];
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.column_chart_view_fragment, null);


        Bundle arguments = getArguments();
        if (arguments != null) {


            //描述
            axis = (List<String>) arguments.getSerializable("axis");
            //编码
            axisdept = (List<String>) arguments.getSerializable("axisdept");
            columnchartlist = (List<String>) arguments.getSerializable("columnchartlist");
            columnchartnumlist = (List<String>) arguments.getSerializable("columnchartnumlist");
            columnchartunicode = (List<String>) arguments.getSerializable("columnchartunicode");


            if (axis_sx != null) {
                if (axis != null) {
                    axis.clear();
                    axis.addAll(axis_sx);
                }
            }
            if (axisdept_sx != null) {
                if (axisdept != null) {
                    axisdept.clear();
                    axisdept.addAll(axisdept_sx);
                }
            }
            if (columnchartlist_sx != null) {
                if (columnchartlist != null) {
                    columnchartlist.clear();
                    columnchartlist.addAll(columnchartlist_sx);
                }
            }
            if (columnchartnumlist_sx != null) {
                if (columnchartnumlist != null) {
                    columnchartnumlist.clear();
                    columnchartnumlist.addAll(columnchartnumlist_sx);
                }
            }
            if (columnchartunicode_sx != null) {
                if (columnchartunicode != null) {
                    columnchartunicode.clear();
                    columnchartunicode.addAll(columnchartunicode_sx);
                }
            }


        }
        if (axisdept.size() > 0) {
            initColumnChartView(view);
        }

        return view;

    }

    private void initColumnChartView(View inflate) {
        // columnchartview
        columnchart = (ColumnChartView) inflate.findViewById(R.id.columnchart);
//        columnchart.setOnValueTouchListener(new ValueColumnTouchListener()); //为图表设置值得触摸事件
//        columnchart.setZoomEnabled(true);//设置是否支持缩放
//        columnchart.setValueSelectionEnabled(false);//设置图表数据是否选中进行显示


        generateColumnChartData();
    }


    private class ValueColumnTouchListener implements
            ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex,
                                    SubcolumnValue value) {
            Toast.makeText(getActivity(), "Selected: " + value,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

    private void generateColumnChartData() {

        switch (dataType) {
            case DEFAULT_DATA:
                generateDefaultData();
                break;
            default:
                generateDefaultData();
                break;
        }

    }


    private void generateDefaultData() {

        int numColumns = axisdept.size();
        // Column can have many subcolumns, here by default I use 1 subcolumn in
        // each of 8 columns.
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
//        float f[]=new float[]{10,80,30,100,60,50,20,40,20,35,11,76};
        String color[] = new String[]{"#1BBC9B", "#25B7FA", "#2DCC70", "#96B4CC", "#3396AF"
                , "#FAB24E", "#E57C21", "#B73E35", "#DF5169", "#9B58B5", "#FFFFFF", "#E3E957"
                , "#F92440"};
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            if (columnchartunicode != null && columnchartunicode.size() > 0)
                for (int k = 0; k < columnchartunicode.size(); k++) {
                    if (axisdept.get(i).equals(columnchartunicode.get(k))) {
                        float v;
                        if (columnchartnumlist != null && columnchartnumlist.size() > 0) {
                            v = Float.parseFloat(columnchartnumlist.get(k));
                        } else {
                            v = 0;
                        }

                        //柱的高度
                        values.add(new SubcolumnValue(v, Color.parseColor(color[i])));
                    }
                }

            Column column = new Column(values);
            column.setHasLabels(true); // 是否显示节点数据
            column.setHasLabelsOnlyForSelected(false); // 隐藏数据，触摸可以显示
            columns.add(column);
        }

        columnchartdata = new ColumnChartData(columns);
//        String tt="012345678911";
        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            Axis axisXTop=new Axis();
            if (hasAxesNames) {
                axisX.setName(".");
//                axisY.setName("Axis Y");
            }

            ArrayList<AxisValue> axisValuesX = new ArrayList<AxisValue>();//定义X轴刻度值的数据集合
            ArrayList<AxisValue> axisValuesY = new ArrayList<AxisValue>();//定义Y轴刻度值的数据集合
            ArrayList<AxisValue> axisValuesTop =new ArrayList<AxisValue>(); //定义顶部轴数据集合
            for (int j = 0; j < numColumns; j++) {//循环为节点、X、Y轴添加数据
//                pointValues.add(new SubcolumnValue(float, int color);// 添加节点数据并为其设置颜色
                String s = null;
                if (axis != null && axis.size() > 0) {
                    s = axis.get(j);
                }
                if (s != null && s.length() > 3) {
                    s = s.substring(0, 3) + "...";
                }
                axisValuesTop.add(new AxisValue(j).setValue(j)); //添加顶部轴刻度值
                axisValuesY.add(new AxisValue(j).setValue(j));// 添加Y轴显示的刻度值
                axisValuesX.add(new AxisValue(j).setValue(j).setLabel(
                        s));// 添加X轴显示的刻度值并设置X轴显示的内容
            }

            axisXTop.setValues(axisValuesTop);//为顶部轴的刻度值设置数据集合
            axisXTop.setTextColor(Color.WHITE);
            axisX.setValues(axisValuesX);//为X轴显示的刻度值设置数据集合
            axisX.setTextSize(10);// 设置X轴文字大小
            axisX.setTextColor(Color.BLACK);
//            axisX.setInside(true);// 设置X轴文字是否在X轴内部
            axisX.setTypeface(Typeface.DEFAULT);// 设置文字样式，此处为默认
            axisX.setHasTiltedLabels(true);// 设置X轴文字向左旋转45度
            columnchartdata.setAxisXTop(axisXTop);//设置顶部显示的轴
            columnchartdata.setAxisXBottom(axisX);
            columnchartdata.setAxisYLeft(axisY);
            columnchartdata.setValueLabelTextSize(15);// 设置数据文字大小
            columnchartdata.setValueLabelTypeface(Typeface.MONOSPACE);// 设置数据文字样式
        } else {
            columnchartdata.setAxisXBottom(null);
            columnchartdata.setAxisYLeft(null);
        }

        columnchart.setColumnChartData(columnchartdata);

    }


}
