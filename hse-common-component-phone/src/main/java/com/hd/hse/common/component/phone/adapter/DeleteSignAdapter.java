package com.hd.hse.common.component.phone.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.listener.OnDeleteListener;

/**
 * created by yangning on 2018/11/13 14:28.
 */
public class DeleteSignAdapter extends BaseAdapter {
    private String[] personNames;
    private LayoutInflater inflater;
    private OnDeleteListener listener;
    private Context context;

    public DeleteSignAdapter(Context context, String[] personNames) {
        this.context = context;
        this.personNames = personNames;
        inflater = LayoutInflater.from(context);
    }

    public void setListener(OnDeleteListener listener) {
        this.listener = listener;
    }

    public void setData(String[] personNames) {
        this.personNames = personNames;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return personNames.length;
    }

    @Override
    public Object getItem(int i) {
        return personNames[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.hd_hse_common_component_dialog_delete_signature_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tvDele = (TextView) view.findViewById(R.id.tv_delete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(personNames[position]);
        viewHolder.tvDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除人
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                SpannableStringBuilder b = new SpannableStringBuilder("是否要删除" + personNames[position] + "的签名");
                ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
                b.setSpan(redSpan, 5, 5 + personNames[position].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setTitle(b);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if (listener != null) {
                            listener.onDelete(position);
                        }
                        //

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        return view;
    }


    class ViewHolder {
        TextView tvName;
        TextView tvDele;

    }
}



