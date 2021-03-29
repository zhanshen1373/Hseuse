package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hd.hse.common.module.phone.R;

/**
 * Created by dubojian on 2018/6/6.
 */

public class SingleChoiceAdapter extends BaseAdapter {


    private Context mContext;
    private String[] items;
    private int choseobj = -1;

    public SingleChoiceAdapter(Context mContext, String[] items) {
        this.mContext = mContext;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
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
            convertView = View.inflate(mContext, R.layout.zdydialog_listview_innerview, null);
            vh.name = (TextView) convertView.findViewById(R.id.zdydialog_listview_innerview_name);
            vh.code = (TextView) convertView.findViewById(R.id.zdydialog_listview_innerview_code);
            vh.radioButton = (RadioButton) convertView.findViewById(R.id.zdydialog_listview_innerview_radiobutton);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }

        String[] split = items[position].split(":");
        vh.name.setText(split[0]);
        vh.code.setText(split[1]);
        if (choseobj == position) {
            vh.radioButton.setChecked(true);
        } else {
            vh.radioButton.setChecked(false);
        }
        return convertView;
    }

    public void setchoseobj(int choseobj) {
        this.choseobj = choseobj;

    }

    static class ViewHold {
        TextView name;
        TextView code;
        RadioButton radioButton;
    }


}
