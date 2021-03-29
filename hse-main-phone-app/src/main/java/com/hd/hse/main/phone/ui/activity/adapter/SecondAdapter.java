package com.hd.hse.main.phone.ui.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.other.ChartsData;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.main.ManagerMainActivity;
import com.hd.hse.main.phone.ui.activity.main.ScheduleActivity;

import java.util.List;

/**
 * Created by dubojian on 2017/8/3.
 */

public class SecondAdapter extends BaseAdapter {

    private Context mcontext;
    private List<ChartsData.TABLEBean> table;
    private String description;



    public SecondAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return table == null ? 0 : table.size();
    }

    @Override
    public Object getItem(int position) {
        return table.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHold vh = null;
        if (convertView == null) {
            vh = new ViewHold();
            convertView = View.inflate(mcontext, R.layout.dl_scheduledata, null);
            vh.tv_dw = (TextView) convertView.findViewById(R.id.dw);
            vh.tv_zysl = (TextView) convertView.findViewById(R.id.zysl);
            vh.tv_dh = (TextView) convertView.findViewById(R.id.dh);
            vh.tv_sxkj = (TextView) convertView.findViewById(R.id.sxkj);
            vh.tv_gc = (TextView) convertView.findViewById(R.id.gc);
            vh.tv_dz = (TextView) convertView.findViewById(R.id.dz);
            vh.tv_gx = (TextView) convertView.findViewById(R.id.gx);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }


        description = table.get(position).getDescription();
        if (description.length() > 4) {
            description = description.substring(0, 4) + "\n" + description
                    .substring(4, description.length());
        }

        vh.tv_dw.setText(description);
        vh.tv_zysl.setText(table.get(position).getZylx99());
        vh.tv_dh.setText(table.get(position).getZylx01());
        vh.tv_sxkj.setText(table.get(position).getZylx02());
        vh.tv_gc.setText(table.get(position).getZylx04());
        vh.tv_dz.setText(table.get(position).getZylx05());
        vh.tv_gx.setText(table.get(position).getZylx06());

        vh.tv_zysl.setOnClickListener(new OnClick(position));
        vh.tv_dh.setOnClickListener(new OnClick(position));
        vh.tv_sxkj.setOnClickListener(new OnClick(position));
        vh.tv_gc.setOnClickListener(new OnClick(position));
        vh.tv_dz.setOnClickListener(new OnClick(position));
        vh.tv_gx.setOnClickListener(new OnClick(position));


        return convertView;
    }

    public void setData(List<ChartsData.TABLEBean> data) {
        this.table = data;
    }


    static class ViewHold {
        //单位
        TextView tv_dw;
        //作业数量
        TextView tv_zysl;
        //动火
        TextView tv_dh;
        //受限空间
        TextView tv_sxkj;
        //高处
        TextView tv_gc;
        //吊装
        TextView tv_dz;
        //管线
        TextView tv_gx;

    }

    class OnClick implements View.OnClickListener {

        public int position;
        public OnClick(int ps){
            position=ps;
        }
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ScheduleActivity.CLICK_DEPT,
                    table.get(position).getZydept());
            ManagerMainActivity managerMainActivity = (ManagerMainActivity) mcontext;
            if (v.getId() == R.id.zysl) {

                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx99");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);

            } else if (v.getId() == R.id.dh) {
                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx01");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);
            } else if (v.getId() == R.id.sxkj) {
                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx02");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);
            } else if (v.getId() == R.id.gc) {
                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx04");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);
            } else if (v.getId() == R.id.dz) {
                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx05");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);
            } else if (v.getId() == R.id.gx) {
                Intent intent = new Intent();
                intent.setClass(managerMainActivity,
                        ScheduleActivity.class);
                bundle.putSerializable(ScheduleActivity.ZYLX, "zylx06");
                intent.putExtras(bundle);
                managerMainActivity.startActivity(intent);
            }
        }
    }

}
