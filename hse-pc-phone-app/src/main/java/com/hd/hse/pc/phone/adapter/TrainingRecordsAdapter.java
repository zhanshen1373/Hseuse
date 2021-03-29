package com.hd.hse.pc.phone.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hd.hse.entity.rycheck.TrainingAchievement;
import com.hd.hse.pc.phone.R;

import java.util.List;

/**
 * created by yangning on 2018/1/8 15:07.
 */

public class TrainingRecordsAdapter extends BaseExpandableListAdapter {
    private List<TrainingAchievement> data;
    private Context context;
    private LayoutInflater inflater;

    public TrainingRecordsAdapter(List<TrainingAchievement> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getSECONDEDU() != null ?
                data.get(i).getSECONDEDU().size() : 0;
    }

    @Override
    public Object getGroup(int i) {
        return data.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return data.get(i).getSECONDEDU() != null ?
                data.get(i).getSECONDEDU().get(i1) : null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.hse_pc_phone_app_training_records_group_item, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.imgisExpand = (ImageView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_img);
            groupViewHolder.tvfactoryName = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_factoryname);
            groupViewHolder.tvJoinTime = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_jointime);
            groupViewHolder.tvScore = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_score);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        if (b)
            groupViewHolder.imgisExpand.setImageResource(R.drawable.hse_pc_phone_app_training_records_group_item_expand);
        else
            groupViewHolder.imgisExpand.setImageResource(R.drawable.hse_pc_phone_app_training_records_group_item_fold);
        groupViewHolder.tvfactoryName.setText(data.get(i).getFACTORYNAME());
        groupViewHolder.tvJoinTime.setText(data.get(i).getDATE());
        groupViewHolder.tvScore.setText(TextUtils.isEmpty(data.get(i).getSCORE()) ? "" : data.get(i).getSCORE() + "分");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.hse_pc_phone_app_training_records_group_item, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.imgisExpand = (ImageView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_img);
            groupViewHolder.tvfactoryName = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_factoryname);
            groupViewHolder.tvJoinTime = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_jointime);
            groupViewHolder.tvScore = (TextView) view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_tv_score);
            groupViewHolder.divider = view.findViewById(R.id.hse_pc_phone_app_training_records_group_item_divider);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.tvfactoryName.setTextColor(context.getResources().getColor(R.color.hd_hse_common_gray));
        groupViewHolder.tvJoinTime.setTextColor(context.getResources().getColor(R.color.hd_hse_common_gray));
        groupViewHolder.tvScore.setTextColor(context.getResources().getColor(R.color.hd_hse_common_gray));
        /*groupViewHolder.tvfactoryName.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_medium));
        groupViewHolder.tvJoinTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_medium));
        groupViewHolder.tvScore.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_medium));*/

        groupViewHolder.imgisExpand.setVisibility(View.INVISIBLE);
        if (data.get(i).getSECONDEDU() != null) {
            groupViewHolder.tvfactoryName.setText(data.get(i).getSECONDEDU().get(i1).getWORKSHOPNAME());
            groupViewHolder.tvJoinTime.setText(data.get(i).getSECONDEDU().get(i1).getDATE());
            groupViewHolder.tvScore.setText(TextUtils.isEmpty(data.get(i).getSECONDEDU().get(i1).getSCORE()) ? "" : data.get(i).getSECONDEDU().get(i1).getSCORE() + "分");
            if (i1 == data.get(i).getSECONDEDU().size() - 1)
                groupViewHolder.divider.setVisibility(View.VISIBLE);
            else
                groupViewHolder.divider.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public class GroupViewHolder {
        ImageView imgisExpand;
        TextView tvfactoryName;
        TextView tvJoinTime;
        TextView tvScore;
        View divider;
    }

    public class ChildViewHolder {
        TextView tvdeptName;
        TextView tvJoinTime;
        TextView tvScore;
    }
}
