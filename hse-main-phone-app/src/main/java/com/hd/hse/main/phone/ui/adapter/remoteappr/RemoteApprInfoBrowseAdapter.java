package com.hd.hse.main.phone.ui.adapter.remoteappr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.custom.IconForZYPClass;
import com.hd.hse.entity.workorder.RemoteApprLine;
import com.hd.hse.main.phone.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteApprInfoBrowseAdapter extends BaseAdapter {
    private Context context;
    private List<RemoteApprLine> remoteApprLines;
    private LayoutInflater layoutInflater;
    private Map<Integer, Boolean> checkedMap = new HashMap<Integer, Boolean>();
    private int state = normalState;
    public static final int normalState = 0;
    public static final int selectState = 1;

    public RemoteApprInfoBrowseAdapter(Context context,
                                       List<RemoteApprLine> remoteApprLines) {
        this.context = context;
        this.remoteApprLines = remoteApprLines;
        this.layoutInflater = LayoutInflater.from(context);
        initCheckMap();
    }

    private void initCheckMap() {
        for (int i = 0; i < remoteApprLines.size(); i++) {
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
        return remoteApprLines.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return remoteApprLines.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(
                    R.layout.hse_common_module_phone_zypgrid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvIcon = (TextView) convertView
                    .findViewById(R.id.hse_common_module_phone_grid_item_icon);
            viewHolder.tvSelected = (TextView) convertView
                    .findViewById(R.id.hse_common_module_phone_grid_item_state);
            viewHolder.tvState = (TextView) convertView
                    .findViewById(R.id.hse_common_module_phone_grid_item_workorder_state);
            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.hse_common_module_phone_grid_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvIcon.setBackgroundResource(IconForZYPClass
                .getIconIDByZYPClass(remoteApprLines.get(position)
                        .getZypclass()));
        if (remoteApprLines.get(position).getIssp() != null
                && "1".equals(remoteApprLines.get(position).getIssp())) {
            viewHolder.tvState
                    .setBackgroundResource(R.drawable.hd_hse_common_module_zyp_state_check);
        } else {
            String statu = remoteApprLines.get(position).getStatus();
            if (statu != null && !statu.equals("")) {
                try {
                    viewHolder.tvState
                            .setBackgroundResource(IconForZYPClass.getIconIDByZYPState(statu));
                } catch (HDException e) {
                    e.printStackTrace();
                    viewHolder.tvState
                            .setBackgroundResource(R.drawable.hd_hse_common_module_zyp_state_remote);
                }
            } else {
                viewHolder.tvState
                        .setBackgroundResource(R.drawable.hd_hse_common_module_zyp_state_remote);
            }


        }

        if (state == normalState) {
            viewHolder.tvSelected.setVisibility(View.INVISIBLE);
        } else if (state == selectState) {
            // 选择状态，分审批，远程审批
            if (remoteApprLines.get(position).getIssp() != null
                    && "1".equals(remoteApprLines.get(position).getIssp())) {
                viewHolder.tvSelected.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvSelected.setVisibility(View.VISIBLE);
                // 远程审批:已选择，未选择
                if (checkedMap.containsKey(position)
                        && checkedMap.get(position)) {
                    viewHolder.tvSelected
                            .setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_selected);
                } else {
                    viewHolder.tvSelected
                            .setBackgroundResource(R.drawable.dx_nochoice);
                }
            }
        }

        viewHolder.tvName.setText(remoteApprLines.get(position)
                .getZypclass_desc());
        return convertView;
    }

    /**
     * 返回当前状态
     *
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     * 设置当前状态
     */
    public void setState(int state) {
        this.state = state;
        initCheckMap();
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView tvIcon;
        TextView tvSelected;
        TextView tvState;
        TextView tvName;
    }

}
