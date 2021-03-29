package com.hd.hse.osc.phone.ui.adapter.remoteappr;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.entity.workorder.RemoteAppr;
import com.hd.hse.osc.phone.R;

import java.util.ArrayList;

/**
 * 远程审批adapter
 *
 * @author yn
 */
public class RemoteApprAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<RemoteAppr> dataList;
    private OnClickAddListener mListner;

    public RemoteApprAdapter(Context context,
                             ArrayList<RemoteAppr> remoteApprs, OnClickAddListener mListner) {
        this.context = context;
        this.dataList = remoteApprs;
        this.layoutInflater = LayoutInflater.from(context);
        this.mListner = mListner;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(
                    R.layout.hd_hse_common_module_phone_remote_appr_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvGw = (TextView) convertView
                    .findViewById(R.id.hd_hse_common_module_phone_remote_appr_item_tv_gw);
            viewHolder.tvAppr = (TextView) convertView
                    .findViewById(R.id.hd_hse_common_module_phone_remote_appr_item_tv_appr);
            viewHolder.imgAdd = (ImageView) convertView
                    .findViewById(R.id.hd_hse_common_module_phone_remote_appr_item_img_add);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvGw.setText(
                dataList.get(position).getSpnode_desc()
                        .split("#").length > 1 ? dataList.get(position).getSpnode_desc().split("#")[1]
                        .concat("：") : dataList.get(position).getSpnode_desc().concat("："));
        viewHolder.tvAppr.setText(dataList.get(position)
                .getApprove_person_name());
        //必须审批红色，非必须黑色
        if (dataList.get(position)
                .getIsmust() != null && dataList.get(position)
                .getIsmust() == 1)
            viewHolder.tvGw.setTextColor(Color.RED);
        else
            viewHolder.tvGw.setTextColor(Color.BLACK);
        viewHolder.imgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListner != null) {
                    mListner.OnClickAdd(dataList, position);
                }
            }
        });
        return convertView;
    }

    public void setDataList(ArrayList<RemoteAppr> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvGw;// 岗位名
        TextView tvAppr;// 审批人
        ImageView imgAdd;// 添加审批人按钮

    }

    /**
     * 点击add的回调接口
     *
     * @author nl
     */
    public interface OnClickAddListener {
        void OnClickAdd(ArrayList<RemoteAppr> remoteApprs, int position);
    }
}
