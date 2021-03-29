package com.hd.hse.carxkzscan.phone.ui.worktask;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.carxkzscan.phone.ui.R;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.CarXkzActivity;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.entity.workorder.CarXkz;
import com.hd.hse.ss.business.workorder.ServerWorkTaskSrv;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class TaskTabulationActivity extends BaseListBusActivity implements View.OnClickListener {


    private TextView startTimeTv;
    private TextView endTimeTv;
    private Calendar ca;
    private Button cha;
    private String startTime;
    private String endTime;
    private ListView listView;
    private EditText editText;

    private ServerWorkTaskSrv workorderdown = null;
    private List<CarXkz> serverWorkTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_tabulation_carxkzscan);
        startTimeTv = (TextView) findViewById(R.id.carxkzscan_starttime);
        endTimeTv = (TextView) findViewById(R.id.carxkzscan_endtime);
        cha = (Button) findViewById(R.id.carxkz_cha);
        editText = (EditText) findViewById(R.id.carxkz_edittext);
        listView = (ListView) findViewById(R.id.carxkz_listview);
        startTimeTv.setOnClickListener(this);
        endTimeTv.setOnClickListener(this);
        cha.setOnClickListener(this);

        ca = Calendar.getInstance();
        workorderdown = new ServerWorkTaskSrv(this);
        workorderdown.setGetDataSourceListener(eventLst);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //弹到新页面，加载url

                Intent intent = new Intent();
                intent.setClass(TaskTabulationActivity.this,
                        CarXkzActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WorkOrderDetailActivity.CAR_KXZ,
                        serverWorkTask.get(position-1));
                intent.putExtras(bundle);
                TaskTabulationActivity.this.startActivity(intent);
            }
        });

        View inflate = getLayoutInflater().inflate(R.layout.carxkz_listview_header, null);
        listView.addHeaderView(inflate,null,false);
    }

    @Override
    public String[] setActionBarItems() {
        return new String[]{IActionBar.IV_LMORE, IActionBar.TV_TITLE};
    }

    @Override
    protected void initData() {

    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "车辆许可证浏览";
    }


    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-carxkzscan-phone-app";
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.carxkzscan_starttime) {


            final DatePickerDialog datePicker = new DatePickerDialog(TaskTabulationActivity.this, null,
                    ca.get(Calendar.YEAR),
                    ca.get(Calendar.MONTH),
                    ca.get(Calendar.DAY_OF_MONTH));
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "确认",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定的逻辑代码在监听中实现
                            DatePicker picker = datePicker.getDatePicker();
                            int year = picker.getYear();
                            int monthOfYear = picker.getMonth() + 1;
                            int dayOfMonth = picker.getDayOfMonth();
                            DecimalFormat df=new DecimalFormat("00");
                            String str2=df.format(monthOfYear);
                            String str3=df.format(dayOfMonth);
                            startTime = year + "-" + str2 + "-" + str3;
                            startTimeTv.setText(startTime);
                        }
                    });
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消什么也不用做
                        }
                    });
            datePicker.getDatePicker().setSpinnersShown(false);
            datePicker.getDatePicker().setCalendarViewShown(true);
            datePicker.show();

        } else if (v.getId() == R.id.carxkzscan_endtime) {

            final DatePickerDialog datePicker = new DatePickerDialog(TaskTabulationActivity.this, null,
                    ca.get(Calendar.YEAR),
                    ca.get(Calendar.MONTH),
                    ca.get(Calendar.DAY_OF_MONTH));
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "确认",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定的逻辑代码在监听中实现
                            DatePicker picker = datePicker.getDatePicker();
                            int year = picker.getYear();
                            int monthOfYear = picker.getMonth() + 1;
                            int dayOfMonth = picker.getDayOfMonth();
                            DecimalFormat df=new DecimalFormat("00");
                            String str2=df.format(monthOfYear);
                            String str3=df.format(dayOfMonth);
                            endTime = year + "-" + str2 + "-" + str3;
                            endTimeTv.setText(endTime);
                        }
                    });
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消什么也不用做
                        }
                    });
            datePicker.getDatePicker().setSpinnersShown(false);
            datePicker.getDatePicker().setCalendarViewShown(true);
            datePicker.show();
        } else if (v.getId() == R.id.carxkz_cha) {

            if (TextUtils.isEmpty(editText.getText().toString()) && (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime))) {
                ToastUtils.toast(this, "时间和车牌号至少有一项不为空");
                return;
            }

            String[] s = new String[]{startTimeTv.getText().toString(), endTimeTv.getText().toString(), editText.getText().toString()};
            if ((!TextUtils.isEmpty(startTime)) && (!TextUtils.isEmpty(endTime))) {
                String[] split = startTime.split("-");
                String[] split1 = endTime.split("-");
                boolean compare = Compare(split, split1, split.length);
                if (compare) {
                    //查询接口
                    if (workorderdown != null) {

                        workorderdown.geteDownLoadWorkList(s);// 异步查询数据

                    }
                }
            } else {
                if (workorderdown != null) {

                    workorderdown.geteDownLoadWorkList(s);// 异步查询数据

                }
            }


        }
    }


    IEventListener eventLst = new IEventListener() {
        @SuppressWarnings("unchecked")
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.DOWN_WORK_LIST_SHOW:

                    serverWorkTask = null;
                    if (objects[0] instanceof List<?>) {
                        // setDataList((List<SuperEntity>) objects[0]);
                        serverWorkTask = (List<CarXkz>) objects[0];
                    }
                    // 显示下载列表
                    if (serverWorkTask != null) {

                        Adapter adapter = new Adapter(TaskTabulationActivity.this, serverWorkTask);
                        listView.setAdapter(adapter);
                    }
                    break;

            }
        }
    };


    private boolean Compare(String split[], String split1[], int index) {

        for (int i = 0; i < index; i++) {
            if (Integer.parseInt(split[i]) < Integer.parseInt(split1[i])) {
                break;
            } else if (Integer.parseInt(split[i]) > Integer.parseInt(split1[i])) {
                ToastUtils.toast(this, "开始时间不能晚于结束时间");
                return false;
            } else if (Integer.parseInt(split[i]) == Integer.parseInt(split1[i])) {

            }
        }
        return true;

    }

    private class Adapter extends BaseAdapter {

        private Context mContext;
        private List<CarXkz> serverWorkTask;

        public Adapter(Context mContext, List<CarXkz> serverWorkTask) {
            this.mContext = mContext;
            this.serverWorkTask = serverWorkTask;
        }

        @Override
        public int getCount() {
            return serverWorkTask.size();
        }

        @Override
        public Object getItem(int position) {
            return serverWorkTask.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder = new ViewHolder();

            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.carxkz_listview_header, null);
                holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
                holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
                holder.tv3 = (TextView) convertView.findViewById(R.id.tv3);
                holder.tv4 = (TextView) convertView.findViewById(R.id.tv4);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CarXkz carXkz = serverWorkTask.get(position);

            holder.tv1.setText(carXkz.getDeptunit_desc());
            holder.tv2.setText(carXkz.getCarnumber());
            holder.tv3.setText(carXkz.getEnterposition());
            holder.tv4.setText(carXkz.getEntertime());
            return convertView;
        }


    }

    static class ViewHolder {
        private TextView tv1;
        private TextView tv2;
        private TextView tv3;
        private TextView tv4;
    }

}
