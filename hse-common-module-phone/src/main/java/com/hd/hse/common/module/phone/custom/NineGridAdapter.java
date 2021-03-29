package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hd.hse.common.module.phone.R;

import java.util.List;

public class NineGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<Bitmap> list;


    public NineGridAdapter(Context mContext, List<Bitmap> data) {
        this.mContext = mContext;
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.ninegridsubview, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.ninegridsubview_imageview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv.setImageBitmap(list.get(position));

        return convertView;
    }

    static class ViewHolder {
        private ImageView iv;
    }
}
