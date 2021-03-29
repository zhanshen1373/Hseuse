package com.hd.hse.main.phone.ui.adapter.remoteappr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hd.hse.entity.workorder.RemoteApprInfo;
import com.hd.hse.main.phone.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页远程审批展示的adapter
 *
 * @author yn 2017/7/20
 */
public class RemoteApprListAdapter extends BaseAdapter {
    private Context context;
    private List<RemoteApprInfo> remoteApprInfos;
    private LayoutInflater layoutInflater;
    private Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();

    public RemoteApprListAdapter(Context context,
                                 List<RemoteApprInfo> remoteApprInfos) {
        this.context = context;
        this.remoteApprInfos = remoteApprInfos;
        this.layoutInflater = LayoutInflater.from(context);
        initCheckMap();
    }

    public void setRemoteApprInfos(List<RemoteApprInfo> remoteApprInfos) {
        this.remoteApprInfos = remoteApprInfos;
        initCheckMap();
        notifyDataSetChanged();
    }

    private void initCheckMap() {
        for (int i = 0; i < remoteApprInfos.size(); i++) {
            checkedMap.put(i, false);
        }
    }

    public Boolean getCheckBoxChecked(int position) {
        if (checkedMap.containsKey(position)) {
            return checkedMap.get(position);
        }
        return null;
    }

    public void setCheckBoxChecked(int position, boolean checked) {
        checkedMap.put(position, checked);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return remoteApprInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return remoteApprInfos.get(position);
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
                    R.layout.hd_hse_main_phone_app_remote_appr_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView
                    .findViewById(R.id.hd_hse_main_phone_app_remote_appr_list_item_checkbox);
            viewHolder.tv = (TextView) convertView
                    .findViewById(R.id.hd_hse_main_phone_app_remote_appr_list_item_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setChecked(checkedMap.get(position));
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedMap.put(position, !checkedMap.get(position));
                ((CheckBox) view).setChecked(checkedMap.get(position));
            }
        });

        viewHolder.tv.setText(remoteApprInfos.get(position)
                .getSend_remind_message());
        return convertView;
    }

    public class ViewHolder {
        private CheckBox checkBox;
        private TextView tv;
    }

}
