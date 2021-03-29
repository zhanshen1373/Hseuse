package com.hd.hse.wov.phone.ui.statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.base.ZyxxTableName;
import com.hd.hse.wov.phone.R;

import java.util.List;

/**
 * Created by dubojian on 2017/11/10.
 */

public class DeptListAdapter3 extends BaseAdapter {


    private Context context;
    private List<ZyxxTableName> searchData;

    public DeptListAdapter3(Context context, List<ZyxxTableName> data) {

        this.searchData = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return searchData == null ? 0 : searchData.size();
    }

    @Override
    public Object getItem(int position) {
        return searchData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder2 holder2 = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.hd_hse_wov_dept_list_item_data, parent,
                    false);
            holder2 = new ViewHolder2();
            holder2.dataTxt = (TextView) convertView
                    .findViewById(R.id.data_txt);
            convertView.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertView.getTag();
        }
        ZyxxTableName department = (ZyxxTableName) getItem(position);

        holder2.dataTxt.setText(department.getZrcs());

        return convertView;
    }

    private class ViewHolder2 {
        public TextView dataTxt;
    }
}
