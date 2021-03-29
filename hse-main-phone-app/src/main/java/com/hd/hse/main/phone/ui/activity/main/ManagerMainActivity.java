package com.hd.hse.main.phone.ui.activity.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.entity.other.ChartsData;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.View.ManagerLinearLayout;
import com.hd.hse.main.phone.ui.activity.View.MyViewPager;
import com.hd.hse.main.phone.ui.activity.adapter.MyFragmentAdapter;
import com.hd.hse.main.phone.ui.activity.business.workorder.ServerWorkTask;
import com.hd.hse.main.phone.ui.activity.fragment.FirstFragment;
import com.hd.hse.system.SystemProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ManagerMainActivity extends BaseFrameActivity {


    private String ah = "ActionBarHeight";
    private ActionBarMainMenu actionBarMainMenu;

    private MyViewPager viewPager;

    private ImageView dot1;
    private ImageView dot2;
    private ImageView dot3;



    private ArrayList<Fragment> pagerlist;
    private MyFragmentAdapter myFragmentAdapter;
    private FirstFragment firstFragment;
    //得到FirstFragment的部分高度
    private int sumheight;
    //数据集合
    private List<String> peichartlist = new ArrayList<>();
    private List<String> peichartnumlist = new ArrayList<>();
    private List<String> columnchartlist = new ArrayList<>();
    private List<String> columnchartnumlist = new ArrayList<>();
    private List<String> columnchartunicode = new ArrayList<>();
    private List<String> axislist = new ArrayList<>();
    private List<String> axisdept = new ArrayList<>();
    private List<ChartsData.TABLEBean> tableBeenlist = new ArrayList<>();


    private ManagerLinearLayout ll;
    private Bundle bd;
    private int height;
    private ChartsData chartsData;
    //表单是第几层的标志
    private int whichCeng;


    private ServerWorkTask workorderdown = null;
    private Object acceptData;
    private LinearLayout dot_linear;
    private ImageView dot1_image;
    private ImageView dot2_image;
    private ImageView dot3_image;

    public int getSumheight() {
        return sumheight;
    }

    public void setSumheight(int sumheight) {
        this.sumheight = sumheight;
        if (this.sumheight != 0) {
            viewPager.setSumheight(sumheight);
            ll.setfrobidheight(sumheight);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_main_phone_app_layout_dalian_main);

        dot_linear = (LinearLayout) findViewById(R.id.linearlayout_dot);


        dot1_image = new ImageView(this);
        dot2_image = new ImageView(this);
        dot3_image = new ImageView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0, 0, 82, 0);  //设置间距
        dot1_image.setLayoutParams(layout);
        dot2_image.setLayoutParams(layout);
        LinearLayout.LayoutParams layoutp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutp.setMargins(0, 0, 0, 0);  //设置间距
        dot3_image.setLayoutParams(layoutp);
        dot1_image.setImageResource(R.drawable.yuan2);
        dot2_image.setImageResource(R.drawable.yuan1);
        dot3_image.setImageResource(R.drawable.yuan2);

        if (SystemProperty.getSystemProperty().isBothShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot_linear.addView(dot2_image);
            dot_linear.addView(dot3_image);

        } else if (SystemProperty.getSystemProperty().isLanZhouMShouYe() ||
                SystemProperty.getSystemProperty().isLanZhouWShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot3_image.setImageResource(R.drawable.yuan1);
            dot_linear.addView(dot3_image);

        } else {
            dot_linear.removeAllViews();
        }

//        dot1_image.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ManagerMainActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        dot3_image.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ManagerMainActivity.this,WorkerInitialPageActivity.class);
//                startActivity(intent);
//            }
//        });



        // 设置导航栏信息
        setCustomActionBar(false, event, new String[]{IActionBar.DALIAN_INITIAL_PAGE_IMAGE,
                IActionBar.DALIAN_INITIAL_PAGE_UNIT, IActionBar.DALIAN_INITIAL_PAGE_NAME,
                IActionBar.DALIAN_INITIAL_PAGE_CENTER_IMAGE, IActionBar.DALIAN_INITIAL_PAGE_DATE
        });
//                , IActionBar.TV_MORE
        // 设置导航栏主标题
//        setActionBartitleContent("欢迎主页", false);
        // 设置右侧菜单栏
        actionBarMainMenu = new ActionBarMainMenu(this);

        if(SystemProperty.getSystemProperty().isLanZhouMShouYe()||
                SystemProperty.getSystemProperty().isBothShouYe()){
            workorderdown = new ServerWorkTask(this);
            workorderdown.setGetDataSourceListener(event);
            workorderdown.setPageType("MANPAGE");
            workorderdown.geteDownLoadWorkList("");// 异步查询数据
        }

        Intent intent = getIntent();
        height = intent.getIntExtra("height", 1);
//        String json = intent.getStringExtra("json");
//        Log.e("cv", json );


    }


    private void initPrarm() {



        viewPager = (MyViewPager) findViewById(R.id.viewpager);
//        dot1 = (ImageView) findViewById(R.id.dot1);
//        dot2 = (ImageView) findViewById(R.id.dot2);
//        dot3 = (ImageView) findViewById(R.id.dot3);
//        dot1.setOnClickListener(new Iclick());
//        dot2.setOnClickListener(new Iclick());
//        dot3.setOnClickListener(new Iclick());

        firstFragment = new FirstFragment();
        pagerlist = new ArrayList<>();
        pagerlist.add(firstFragment);


        bd.putInt(ah, height);
        firstFragment.setArguments(bd);

        /*
        pagerlist.add(new SecondFragment());
        pagerlist.add(new ThirdFragment());
        */
        myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), pagerlist);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.setpageflag(0);
        /*
        dot1.setImageResource(R.drawable.dx_nochoice);
        */

        ll = (ManagerLinearLayout) findViewById(R.id.dl_linear);
        ll.setpageflag(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(arg0);
                viewPager.setpageflag(arg0);
                ll.setpageflag(arg0);
                /*
                if (arg0 == 0) {
                    dot1.setImageResource(R.drawable.dx_nochoice);
                    dot2.setImageResource(R.drawable.no_selected);
                    dot3.setImageResource(R.drawable.no_selected);
                }
                if (arg0 == 1) {
                    dot2.setImageResource(R.drawable.dx_nochoice);
                    dot1.setImageResource(R.drawable.no_selected);
                    dot3.setImageResource(R.drawable.no_selected);
                }
                if (arg0 == 2) {
                    dot3.setImageResource(R.drawable.dx_nochoice);
                    dot1.setImageResource(R.drawable.no_selected);
                    dot2.setImageResource(R.drawable.no_selected);
                }
                */
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && whichCeng == 2) {
            firstFragment.settag(whichCeng);
            whichCeng=0;
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * eventLst:TODO(标题栏事件).
     */
    public IEventListener event = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub
            if (arg0 == IEventType.ACTIONBAR_MORE_CLICK) {
                actionBarMainMenu.showAsDropDown((View) arg1[0]);
            } else if (arg0 == IEventType.DALIANCENTTERPIC) {
                Intent intent=new Intent(ManagerMainActivity.this,MainActivity.class);
                startActivity(intent);
            }else if(arg0 ==IEventType.DOWN_WORK_LIST_SHOW){

                if(arg1[0] instanceof Object){
                    acceptData = arg1[0];
//                    ll.setAcceptData(acceptData.toString());

                    if (acceptData.toString() != null) {
                        chartsData = new Gson().fromJson(acceptData.toString(),
                                ChartsData.class);
                        if (chartsData != null) {

                            List<ChartsData.TABLEBean> table = chartsData.getTABLE();
                            if (table != null && table.size() > 0) {
                                for (ChartsData.TABLEBean tableBean : table) {
                                    tableBeenlist.add(tableBean);
                                }
                            }

                            List<ChartsData.AXISBean> axis = chartsData.getAXIS();
                            if (axis != null && axis.size() > 0) {
                                for (ChartsData.AXISBean axisBean : axis) {
                                    String dept_desc = axisBean.getDept_desc();
                                    String dept = axisBean.getDept();
                                    String[] split1 = dept.split(",");
                                    String[] split = dept_desc.split(",");
                                    for (int j = 0; j < split.length; j++) {
                                        axislist.add(split[j]);


                                    }
                                    for (int k = 0; k < split1.length; k++) {
                                        axisdept.add(split1[k]);
                                    }
                                }
                            }

                            List<ChartsData.CHART2Bean> chart2 = chartsData.getCHART2();
                            if (chart2 != null && chart2.size() > 0) {
                                for (ChartsData.CHART2Bean chart2Bean : chart2) {
                                    String zydept_desc = chart2Bean.getZydept_desc();
                                    String zypcount = chart2Bean.getZylx99();
                                    String zydept = chart2Bean.getZydept();
                                    columnchartlist.add(zydept_desc);
                                    columnchartnumlist.add(zypcount);
                                    columnchartunicode.add(zydept);
                                }
                            }

                            List<ChartsData.CHART1Bean> pieChart = chartsData.getCHART1();
                            if (pieChart != null && pieChart.size() > 0) {
                                for (ChartsData.CHART1Bean chart1Bean : pieChart) {
                                    String zydept_desc = chart1Bean.getZydept_desc();
                                    String zypcount = chart1Bean.getZylx99();
                                    peichartlist.add(zydept_desc);
                                    peichartnumlist.add(zypcount);
                                }
                            }

                        }
                    }

                    bd = new Bundle();
                    bd.putSerializable("peichartlist", (Serializable) peichartlist);
                    bd.putSerializable("peichartnumlist", (Serializable) peichartnumlist);
                    bd.putSerializable("axislist", (Serializable) axislist);
                    bd.putSerializable("axisdept", (Serializable) axisdept);
                    bd.putSerializable("columnchartlist", (Serializable) columnchartlist);
                    bd.putSerializable("columnchartnumlist", (Serializable) columnchartnumlist);
                    bd.putSerializable("columnchartunicode", (Serializable) columnchartunicode);
                    bd.putSerializable("schedule", (Serializable) tableBeenlist);
                    initPrarm();

                }

            }
        }
    };



    public void setCeng(int s) {
        this.whichCeng = s;
    }


    class Iclick implements OnClickListener {

        @Override
        public void onClick(View v) {
//            if (v.getId() == R.id.dot1) {
//                viewPager.setCurrentItem(0);
//            } else if (v.getId() == R.id.dot2) {
//                viewPager.setCurrentItem(1);
//            } else if (v.getId() == R.id.dot3) {
//                viewPager.setCurrentItem(2);
//            }
        }
    }


}
