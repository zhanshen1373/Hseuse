package com.hd.hse.pc.phone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.pc.phone.R;

import java.util.List;

/**
 * created by yangning on 2018/1/9 17:45.
 */

public class PersonCertificateRecordAdapter2 extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<PersonSpecialTypeWork> mSpecialTypeWorks;    // 证书列表

    public PersonCertificateRecordAdapter2(Context mContext, List<PersonSpecialTypeWork> mSpecialTypeWorks) {
        this.mContext = mContext;
        this.mSpecialTypeWorks = mSpecialTypeWorks;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mSpecialTypeWorks.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mSpecialTypeWorks.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.hse_pc_phone_app_item_record_or_certificate2, null);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate2_context);
            viewHolder.tvNum = (TextView) view.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate2_num);
            viewHolder.tvTimeLimit = (TextView) view.findViewById(R.id.hse_pc_phone_app_item_record_or_certificate2_time_limit);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvContent.setText(mSpecialTypeWorks.get(i).getZcproject());
        viewHolder.tvNum.setText(mSpecialTypeWorks.get(i).getZsnum());
        if (mSpecialTypeWorks.get(i).getFzdate() == null) {
            viewHolder.tvTimeLimit.setText("空至" + mSpecialTypeWorks.get(i).getDqdate());

        } else {
            viewHolder.tvTimeLimit.setText(mSpecialTypeWorks.get(i).getFzdate() + "至" + mSpecialTypeWorks.get(i).getDqdate());

        }
        return view;
    }

    class ViewHolder {
        TextView tvContent;
        TextView tvNum;
        TextView tvTimeLimit;
    }
}
