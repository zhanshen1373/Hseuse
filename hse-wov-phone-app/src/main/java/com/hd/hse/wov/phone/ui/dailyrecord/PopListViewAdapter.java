package com.hd.hse.wov.phone.ui.dailyrecord;

import java.util.List;

import com.hd.hse.common.component.phone.custom.HSEActionBar.OnClickLstner;
import com.hd.hse.wov.phone.R;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class PopListViewAdapter extends BaseAdapter  {
	private String[] datas;
	private Context context;
	private Handler handler;


	public PopListViewAdapter(String[] listdataStrings, Context context,Handler handler) {
		super();
		this.datas = listdataStrings;
		this.context = context;
		this.handler=handler;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup arg2) {
		ViewHolder vh=null;
		if(converView==null){
			converView=LayoutInflater.from(context).inflate(R.layout.hd_hse_wov_daliyrecord_poplist_item, null);
			vh=new ViewHolder();
			vh.tv=(TextView) converView.findViewById(R.id.hd_hse_wov_daliyrecord_poplistitem_tv);
			converView.setTag(vh);
			
		}else {
			vh=(ViewHolder) converView.getTag();
		}
		vh.tv.setText(datas[position]);
		vh.tv.setOnClickListener(new onItemTextClick(datas[position]));
		return converView;
	}
	public class onItemTextClick implements OnClickListener{
		String itemcontent;
         public onItemTextClick(String content){
        	 this.itemcontent=content;
         }
		@Override
		public void onClick(View arg0) {
			Message message=new Message();
			message.obj=itemcontent;
			message.what=233;
			handler.sendMessage(message);
		}
		
	} 
    class ViewHolder {
    	TextView tv;
    }
}
