package com.hd.hse.main.phone.ui.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.main.phone.R;

import java.util.List;

/**
 * Created by dubojian on 2017/9/18.
 */

public class GasDetectAdapter extends BaseAdapter {


    private ViewHold vh;
    private List<String> gslist;
    private Context mContext;
    private GasTime time;
    private List<Long> millionSecond;
    private boolean pag;


    public GasDetectAdapter(Context mContext, List<String> list,
                            List<Long> millionSecond,boolean pag) {
        this.gslist = list;
        this.mContext = mContext;
        this.millionSecond = millionSecond;
        this.pag=pag;
        time = new GasTime();
        time.setFlag(true);



    }

    @Override
    public int getCount() {
        return gslist != null ? gslist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return gslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            vh = new ViewHold();
            convertView = View.inflate(mContext, R.layout.gasview, null);
            vh.tv_status = (TextView) convertView.findViewById(R.id.tv_gasstatus);
            vh.tv_content = (TextView) convertView.findViewById(R.id.tv_gascontent);
            vh.tv_distance = (TextView) convertView.findViewById(R.id.tv_gasdistance);
            vh.time_minute = (TextView) convertView.findViewById(R.id.time_gasminute);
            vh.time_second = (TextView) convertView.findViewById(R.id.time_gassecond);
            vh.middle_time = (TextView) convertView.findViewById(R.id.minute_middle_second);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }


        vh.tv_content.setText(gslist.get(position));

        vh.time_second.setTag(millionSecond.get(position));
        time.setView(mContext, vh);

        if (pag && position==0){
            time.start();
            pag=false;
        }

        return convertView;
    }

    static class ViewHold {
        TextView tv_status;
        TextView tv_content;
        TextView tv_distance;
        TextView time_minute;
        TextView time_second;
        TextView middle_time;
    }
}
