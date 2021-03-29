package com.hd.hse.main.phone.ui.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.main.phone.R;

import java.util.List;

/**
 * Created by dubojian on 2017/10/16.
 */

public class MyListViewAdapter extends BaseAdapter {

    private List<String> data;
    private Context mContext;

    public MyListViewAdapter(Context mContext,List<String> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
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
        ViewHold vh = null;
        if (convertView == null) {
            vh = new ViewHold();
            convertView = View.inflate(mContext, R.layout.worker_message_notify, null);
            vh.tv = (TextView) convertView.findViewById(R.id.worker_messagenotify_content);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }

        vh.tv.setText("\t\t\t\t"+data.get(position));
        return convertView;
    }

    static class ViewHold{
        TextView tv;
    }

}
