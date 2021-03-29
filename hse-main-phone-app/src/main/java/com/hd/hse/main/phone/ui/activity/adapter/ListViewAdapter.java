package com.hd.hse.main.phone.ui.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.other.ChartsData;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.business.workorder.ServerWorkTask;
import com.hd.hse.main.phone.ui.activity.main.ManagerMainActivity;

import java.util.List;

/**
 * Created by dubojian on 2017/7/28.
 */

public class ListViewAdapter extends BaseAdapter {


    private Context mcontext;
    private List<ChartsData.TABLEBean> tableBeenlist;
    private ServerWorkTask workorderdown = null;
    private Object acceptData;
    private SecondAdapter secondAdapter;
    private ListView listview;

    public ListViewAdapter(Context mcontext, List<ChartsData.TABLEBean> alist,ListView lv) {
        this.mcontext = mcontext;
        this.tableBeenlist = alist;
        this.listview=lv;
    }

    @Override
    public int getCount() {
        return tableBeenlist.size();
    }

    @Override
    public Object getItem(int position) {
        return tableBeenlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHold vh = null;
        if (convertView == null) {
            vh = new ViewHold();
            convertView = View.inflate(mcontext, R.layout.dl_scheduledata, null);
            vh.tv_dw = (TextView) convertView.findViewById(R.id.dw);
            vh.tv_zysl = (TextView) convertView.findViewById(R.id.zysl);
            vh.tv_dh = (TextView) convertView.findViewById(R.id.dh);
            vh.tv_sxkj = (TextView) convertView.findViewById(R.id.sxkj);
            vh.tv_gc = (TextView) convertView.findViewById(R.id.gc);
            vh.tv_dz = (TextView) convertView.findViewById(R.id.dz);
            vh.tv_gx = (TextView) convertView.findViewById(R.id.gx);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workorderdown = new ServerWorkTask(mcontext);
                workorderdown.setGetDataSourceListener(eventLst);
                workorderdown.geteDownLoadWorkList(tableBeenlist.get(position).getZydept());// 异步查询数据
                workorderdown.setPageType("MANPAGE");
                secondAdapter=new SecondAdapter(mcontext);
            }
        });

        String description = tableBeenlist.get(position).getDescription();
        if (description.length() > 4) {
            description = description.substring(0, 4) + "\n" + description
                    .substring(4, description.length());
        }

        vh.tv_dw.setText(description);
        vh.tv_zysl.setText(tableBeenlist.get(position).getZylx99());
        vh.tv_dh.setText(tableBeenlist.get(position).getZylx01());
        vh.tv_sxkj.setText(tableBeenlist.get(position).getZylx02());
        vh.tv_gc.setText(tableBeenlist.get(position).getZylx04());
        vh.tv_dz.setText(tableBeenlist.get(position).getZylx05());
        vh.tv_gx.setText(tableBeenlist.get(position).getZylx06());


        return convertView;
    }


    static class ViewHold {
        //单位
        TextView tv_dw;
        //作业数量
        TextView tv_zysl;
        //动火
        TextView tv_dh;
        //受限空间
        TextView tv_sxkj;
        //高处
        TextView tv_gc;
        //吊装
        TextView tv_dz;
        //管线
        TextView tv_gx;

    }

    /**
     *
     */
    public IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub

            if(arg0 == IEventType.DOWN_WORK_LIST_SHOW){

                if(arg1[0] instanceof Object){
                    acceptData = arg1[0];
                    ChartsData chartsData = new Gson().
                            fromJson(acceptData.toString(),
                                    ChartsData.class);
                    if(chartsData!=null){
                        List<ChartsData.TABLEBean> table = chartsData.getTABLE();
                        if(table!=null && table.size()>0){

                            secondAdapter.setData(table);
                            listview.setAdapter(secondAdapter);
                            ManagerMainActivity managerMainActivity = (ManagerMainActivity) mcontext;
                            //设置是表单的第一层还是第二层的标志
                            managerMainActivity.setCeng(2);
                        }
                    }
                    Log.e("zxczx",acceptData.toString());
                }

            }
        }
    };


}
