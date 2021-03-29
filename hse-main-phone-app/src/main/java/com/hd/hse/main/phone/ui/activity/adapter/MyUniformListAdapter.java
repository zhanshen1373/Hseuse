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

public class MyUniformListAdapter extends BaseAdapter {



    private ViewHold vh;
    private Context mContext;
    private Time time;
    private List<Long> millionSecond;
    private List<String> data;
    private boolean blag;




    public MyUniformListAdapter(Context mContext, List<String> pzlist,
                                List<Long> second,boolean pag) {


        this.data = pzlist;
        this.mContext = mContext;
        this.millionSecond = second;
        this.blag=pag;
        time = new Time();
        time.setFlag(true);


    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            vh = new ViewHold();
            convertView = View.inflate(mContext, R.layout.uniformview, null);
            vh.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            vh.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            vh.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
            vh.time_minute = (TextView) convertView.findViewById(R.id.time_minute);
            vh.time_second = (TextView) convertView.findViewById(R.id.time_second);
            vh.time_point = (TextView) convertView.findViewById(R.id.time_point);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }


        vh.tv_content.setText(data.get(position));

        vh.time_second.setTag(millionSecond.get(position));
        time.setView(mContext, vh);

        if (blag && position==0){
            time.start();
            blag=false;
        }
        return convertView;
    }



    static class ViewHold {
        TextView tv_status;
        TextView tv_content;
        TextView tv_distance;
        TextView time_minute;
        TextView time_second;
        TextView time_point;
    }
}
