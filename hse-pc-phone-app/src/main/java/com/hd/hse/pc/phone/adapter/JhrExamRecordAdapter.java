package com.hd.hse.pc.phone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.entity.rycheck.JhrExamRecord;
import com.hd.hse.pc.phone.R;

import java.util.List;

/**
 * created by yangning on 2019/3/25 10:24.
 */
public class JhrExamRecordAdapter extends BaseAdapter {

    private List<JhrExamRecord> jhrExamRecords;
    private Context context;
    private LayoutInflater inflater;

    public JhrExamRecordAdapter(List<JhrExamRecord> jhrExamRecords, Context context) {
        this.context = context;
        this.jhrExamRecords = jhrExamRecords;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return jhrExamRecords != null ? jhrExamRecords.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return jhrExamRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.jhr_exam_record_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvSubject = (TextView) convertView.findViewById(R.id.tvSubject);
            viewHolder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
            viewHolder.tvResult = (TextView) convertView.findViewById(R.id.tvResult);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSubject.setText(jhrExamRecords.get(position).getJOBTYPE());
        viewHolder.tvScore.setText(jhrExamRecords.get(position).getSCORE());
        viewHolder.tvResult.setText(jhrExamRecords.get(position).getRESULT());
        if ("合格".equals(jhrExamRecords.get(position).getRESULT())) {
            viewHolder.tvResult.setTextColor(Color.GREEN);
        } else {
            viewHolder.tvResult.setTextColor(Color.RED);
        }

        return convertView;
    }

    public class ViewHolder {
        private TextView tvSubject;//科目
        private TextView tvScore;//成绩
        private TextView tvResult;//结果
    }


}
