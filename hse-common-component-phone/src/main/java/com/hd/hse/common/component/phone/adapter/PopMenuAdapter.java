package com.hd.hse.common.component.phone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;

import java.util.List;

/**
 * ClassName: PopMenuAdapter (导航栏弹出菜单适配器)<br/>
 * date: 2014年12月26日  <br/>
 *
 * @author wenlin
 */
public class PopMenuAdapter extends BaseAdapter {

    private Context context;

    private List<PopMenuItem> lstMenuItem;

    private LayoutInflater inflater;

    public PopMenuAdapter(Context context, List<PopMenuItem> lstMenuItem) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.lstMenuItem = lstMenuItem;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return lstMenuItem.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lstMenuItem.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater
                    .inflate(R.layout.hd_hse_common_component_phone_actionbar_listview_item,
                            null, false);
            holder.txtView = (TextView) view
                    .findViewById(R.id.hd_hse_common_phone_popmenu_listview_item_tv);
            holder.bView = (ImageView) view.findViewById(R.id.hd_hse_common_phone_popmenu_listview_item_tb);
            holder.remindView = (ImageView) view.findViewById(R.id.hd_hse_common_phone_popmenu_listview_item_img);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // 设置描述
        holder.txtView.setText(lstMenuItem.get(index).getDescription());
        // 设置图片
        if (lstMenuItem.get(index).getDrawable() != null) {
            holder.bView.setVisibility(View.VISIBLE);
            Drawable drawable = context.getResources().getDrawable(
                    lstMenuItem.get(index).getDrawable());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            holder.bView.setImageDrawable(drawable);

        } else {
            holder.bView.setVisibility(View.GONE);
        }

        holder.remindView.setVisibility(lstMenuItem.get(index).isRemind() ? View.VISIBLE : View.INVISIBLE);
        return view;
    }

    class ViewHolder {
        TextView txtView;
        ImageView bView;
        ImageView remindView;
    }
}
