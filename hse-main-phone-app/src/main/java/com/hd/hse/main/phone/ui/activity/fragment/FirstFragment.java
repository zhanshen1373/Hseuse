package com.hd.hse.main.phone.ui.activity.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.other.ChartsData;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.adapter.ListViewAdapter;
import com.hd.hse.main.phone.ui.activity.business.workorder.ServerWorkTask;
import com.hd.hse.main.phone.ui.activity.main.ManagerMainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dubojian on 2017/7/19.
 */

public class FirstFragment extends Fragment {

    private String bd = "ActionBarHeight";
    private ServerWorkTask workorderdown = null;
    private Object acceptData;
    // ListView
    private ListView listview;
    private ListViewAdapter listViewAdapter;


    private FragmentManager childFragmentManager;
    private RadioGroup radioGroup;
    private FrameLayout frameLayout;
    private View inflate;
    private SwipeRefreshLayout swipelayout;


    //集合
    private List<String> peichartlist;
    private List<String> peichartnumlist;
    private List<String> axislist;
    private List<String> columnchartlist;
    private List<String> columnchartnumlist;
    private List<String> axisdept;
    private List<String> columnchartunicode;
    private List<ChartsData.TABLEBean> tableBeenlist;

    private List<String> peichartlist_sx = new ArrayList<>();
    private List<String> peichartnumlist_sx = new ArrayList<>();
    private List<String> axislist_sx = new ArrayList<>();
    private List<String> columnchartlist_sx = new ArrayList<>();
    private List<String> columnchartnumlist_sx = new ArrayList<>();
    private List<String> axisdept_sx = new ArrayList<>();
    private List<String> columnchartunicode_sx = new ArrayList<>();
    private List<ChartsData.TABLEBean> tableBeenlist_sx = new ArrayList<>();


    //高度
    private int radioGroupHeight;
    private int frameLayoutHeight;
    private int actionBarHeight;
    private int sumheight;
    //Fragment
    private PieChartViewFragment pvf;
    private ColumnChartViewFragment cvf;

    //handler
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    swipelayout.setRefreshing(false);

                    workorderdown = new ServerWorkTask(getActivity());
                    workorderdown.setGetDataSourceListener(eventLst);
                    workorderdown.geteDownLoadWorkList("");// 异步查询数据
                    workorderdown.setPageType("MANPAGE");
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * eventLst:TODO(标题栏事件).
     */
    public IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub
            if (arg0 == IEventType.DOWN_WORK_LIST_SHOW) {

                if (arg1[0] instanceof Object) {
                    acceptData = arg1[0];
                    json = acceptData.toString();

                    if (json != null) {
                        tableBeenlist.clear();
                        ChartsData chartsData = new Gson().fromJson(json,
                                ChartsData.class);
                        if (chartsData != null) {

                            List<ChartsData.TABLEBean> table = chartsData.getTABLE();
                            if (table != null && table.size() > 0) {
                                for (ChartsData.TABLEBean tableBean : table) {
                                    tableBeenlist.add(tableBean);
                                }
                            }

                            List<ChartsData.CHART2Bean> chart2 = chartsData.getCHART2();
                            if (chart2 != null && chart2.size() > 0) {
                                columnchartlist_sx.clear();
                                columnchartnumlist_sx.clear();
                                columnchartunicode_sx.clear();
                                for (ChartsData.CHART2Bean chart2Bean : chart2) {
                                    String zydept_desc = chart2Bean.getZydept_desc();
                                    String zypcount = chart2Bean.getZylx99();
                                    String zydept = chart2Bean.getZydept();
                                    columnchartlist_sx.add(zydept_desc);
                                    columnchartnumlist_sx.add(zypcount);
                                    columnchartunicode_sx.add(zydept);
                                }
                            }

                            List<ChartsData.AXISBean> axis = chartsData.getAXIS();
                            if (axis != null && axis.size() > 0) {
                                axislist_sx.clear();
                                axisdept_sx.clear();

                                for (ChartsData.AXISBean axisBean : axis) {
                                    String dept_desc = axisBean.getDept_desc();
                                    String dept = axisBean.getDept();
                                    String[] split1 = dept.split(",");
                                    String[] split = dept_desc.split(",");

                                    for (int j = 0; j < split.length; j++) {
                                        axislist_sx.add(split[j]);

                                    }
                                    for (int k = 0; k < split1.length; k++) {
                                        axisdept_sx.add(split1[k]);
                                    }
                                }

                                cvf.setdata(axislist_sx, axisdept_sx, columnchartlist_sx, columnchartnumlist_sx, columnchartunicode_sx);

                            }


                            List<ChartsData.CHART1Bean> pieChart = chartsData.getCHART1();
                            if (pieChart != null && pieChart.size() > 0) {
                                peichartlist_sx.clear();
                                peichartnumlist_sx.clear();
                                for (ChartsData.CHART1Bean chart1Bean : pieChart) {
                                    String zydept_desc = chart1Bean.getZydept_desc();
                                    String zypcount = chart1Bean.getZylx99();
                                    peichartlist_sx.add(zydept_desc);
                                    peichartnumlist_sx.add(zypcount);
                                }


                                pvf.setdata(peichartlist_sx, peichartnumlist_sx);
                            }

                        }
                    }
                    listViewAdapter.notifyDataSetChanged();
                }

            }
        }
    };
    private String json;
    private Bundle peichartbd;
    private Bundle columnbd;
    //表单在第一层还是第二层的标志
    private int tag;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.dalian_main_firstview, null);
        radioGroup = (RadioGroup) inflate.findViewById(R.id.radiogroup);
        swipelayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipeLayout);
        listview = (ListView) inflate.findViewById(R.id.today_work_frag_layout_lv);
        frameLayout = (FrameLayout) inflate.findViewById(R.id.frame_layout);

        swipelayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);


        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroupChange());

        initAllData();

        listViewAdapter = new ListViewAdapter(getActivity(), tableBeenlist, listview);
        listview.setAdapter(listViewAdapter);

        initPieViewData();
        initColumnViewData();


        radioGroup.post(new Runnable() {
            @Override
            public void run() {
                radioGroupHeight = radioGroup.getHeight();
            }
        });

        frameLayout.post(new Runnable() {
            @Override
            public void run() {
                frameLayoutHeight = frameLayout.getHeight();

                if (radioGroupHeight != 0 && frameLayoutHeight != 0) {
                    sumheight = actionBarHeight + radioGroupHeight + frameLayoutHeight;
                    ManagerMainActivity activity = (ManagerMainActivity) getActivity();
                    activity.setSumheight(sumheight);
                }
            }
        });
        initFragment();
        return inflate;
    }

    private void initAllData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            //actionbar高度
            actionBarHeight = arguments.getInt(bd);
            //图表控件数据
            peichartlist = (List<String>) arguments.getSerializable("peichartlist");
            peichartnumlist = (List<String>) arguments.getSerializable("peichartnumlist");
            axislist = (List<String>) arguments.getSerializable("axislist");
            axisdept = (List<String>) arguments.getSerializable("axisdept");
            columnchartlist = (List<String>) arguments.getSerializable("columnchartlist");
            columnchartnumlist = (List<String>) arguments.getSerializable("columnchartnumlist");
            columnchartunicode = (List<String>) arguments.getSerializable("columnchartunicode");
            tableBeenlist = (List<ChartsData.TABLEBean>) arguments.getSerializable("schedule");
        }
    }

    private void initColumnViewData() {
        cvf = new ColumnChartViewFragment();
        columnbd = new Bundle();
        columnbd.putSerializable("axis", (Serializable) axislist);
        columnbd.putSerializable("axisdept", (Serializable) axisdept);
        columnbd.putSerializable("columnchartlist", (Serializable) columnchartlist);
        columnbd.putSerializable("columnchartnumlist", (Serializable) columnchartnumlist);
        columnbd.putSerializable("columnchartunicode", (Serializable) columnchartunicode);
        cvf.setArguments(columnbd);

    }

    private void initPieViewData() {


        pvf = new PieChartViewFragment();
        peichartbd = new Bundle();
        peichartbd.putSerializable("peichartlist", (Serializable) peichartlist);
        peichartbd.putSerializable("peichartnumlist", (Serializable) peichartnumlist);
        pvf.setArguments(peichartbd);

    }


    private void initFragment() {

        childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, pvf).commit();

    }

    public void settag(int tag) {
        this.tag = tag;
        if (tag == 2) {
            listview.setAdapter(listViewAdapter);
        }
    }


    class RadioGroupChange implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (checkedId == R.id.today_radiobutton) {
                FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, pvf).commit();

            }
            if (checkedId == R.id.tomorrow_radiobutton) {
                FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, cvf).commit();
            }
        }
    }
}
