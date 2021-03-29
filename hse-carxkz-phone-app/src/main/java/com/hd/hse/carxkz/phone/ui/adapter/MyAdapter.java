package com.hd.hse.carxkz.phone.ui.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.carxkz.phone.R;
import com.hd.hse.entity.workorder.CarXkz;

import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private String[] xkz_des;
    private HashMap<Integer, String> hashMap = new HashMap<>();
    private CarXkz carXkz;
    EditTextWatcher editTextWatcher = null;

    public MyAdapter(Context mContext, String[] xkz_des, CarXkz carXkz, HashMap hashMap) {
        this.mContext = mContext;
        this.xkz_des = xkz_des;
        this.carXkz = carXkz;
        this.hashMap = hashMap;
    }

    @Override
    public int getCount() {
        return xkz_des.length;
    }

    @Override
    public Object getItem(int position) {
        return xkz_des[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.carxkz, null);
            holder.tv1 = (TextView) convertView.findViewById(R.id.carxkz_textview);
            holder.ed1 = (EditText) convertView.findViewById(R.id.carxkz_edittext);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(xkz_des[position]);
        String s = hashMap.get(xkz_des[position]);

        if (carXkz.getAttribute(s) != null) {
            holder.ed1.setText((String) carXkz.getAttribute(s));
        } else {
            holder.ed1.setText("");
        }

        final ViewHolder finalHolder = holder;
        holder.ed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editTextWatcher = new EditTextWatcher(position);
                    finalHolder.ed1.addTextChangedListener(editTextWatcher);
                }


            }
        });


        return convertView;

    }

    private void data(int position, String s) {
        if (xkz_des[position].equals("属地单位:")) {
            carXkz.setAttribute("deptunit", s);
        } else if (xkz_des[position].equals("车辆单位:")) {
            carXkz.setAttribute("carunit", s);
        } else if (xkz_des[position].equals("车辆驾驶员:")) {
            carXkz.setAttribute("driver", s);
        } else if (xkz_des[position].equals("车辆牌号:")) {
            carXkz.setAttribute("carnumber", s);
        } else if (xkz_des[position].equals("监护人:")) {
            carXkz.setAttribute("jhr", s);
        } else if (xkz_des[position].equals("进入部位:")) {
            carXkz.setAttribute("enterposition", s);
        } else if (xkz_des[position].equals("进入原因:")) {
            carXkz.setAttribute("enterreason", s);
        } else if (xkz_des[position].equals("执行安全措施:")) {
            carXkz.setAttribute("zxaqcs", s);
        } else if (xkz_des[position].equals("补充安全措施:")) {
            carXkz.setAttribute("bcaqcs", s);
        }
    }

    static class ViewHolder {
        private TextView tv1;
        private EditText ed1;
    }

    class EditTextWatcher implements TextWatcher {

        private int position;

        public EditTextWatcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            data(position, s.toString());

        }
    }
}
