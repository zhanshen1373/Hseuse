package com.hd.hse.common.component.phone.adapter;

import java.util.ArrayList;











import org.apache.log4j.Logger;
import com.hd.hse.common.component.phone.R;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OptionsAdapter extends BaseAdapter {
	private static Logger logger = LogUtils.getLogger(OptionsAdapter.class);
	// 调用接口
	private IEventListener iEventLsn;
	private ArrayList<String> list = new ArrayList<String>(); 
    private Activity activity = null; 
	private Handler handler;
    
	/**
	 * 自定义构造方法
	 * @param activity
	 * @param handler
	 * @param list
	 */
    public OptionsAdapter(Activity activity,Handler handler,ArrayList<String> list){
    	this.activity = activity;
    	this.handler = handler;
    	this.list = list;
    }
	
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null; 
        if (convertView == null) { 
            holder = new ViewHolder(); 
            //下拉项布局
            convertView = LayoutInflater.from(activity).inflate(R.layout.hd_hse_main_app_login_option_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.hd_hse_option_tv01); 
//            holder.imageView = (ImageView) convertView.findViewById(R.id.hd_hse_ImageB_del); 
            convertView.setTag(holder); 
        } else { 
            holder = (ViewHolder) convertView.getTag(); 
        } 
        holder.textView.setText(list.get(position));
        
        //为下拉框选项文字部分设置事件，最终效果是点击将其文字填充到文本框
        holder.textView.setOnClickListener(new ItemClickListenerImpl(position));
        
        //为下拉框选项删除图标部分设置事件，最终效果是点击将该选项删除
/**
        holder.imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Message msg = new Message();
				Bundle data = new Bundle();
				//设置删除索引
				data.putInt("delIndex", position);
				msg.setData(data);
				msg.what = 2;
				//发出消息
				handler.sendMessage(msg);
			}
		});
**/
        return convertView; 
	}
	
	public IEventListener getiEventLsn() {
		return iEventLsn;
	}

	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
	}

	/**
	 * 点击审核按钮事件
	 * @author zhulei
	 *
	 */
	class ItemClickListenerImpl implements OnClickListener {
		private int position;

		public ItemClickListenerImpl(int position) {
			this.position = position;
		}

		public void onClick(View arg0) {
			Message msg = new Message();
			Bundle data = new Bundle();
			//设置选中索引
			data.putInt("selIndex", position);
			msg.setData(data);
			msg.what = 1;
			//发出消息
			handler.sendMessage(msg);
			if (iEventLsn != null) {
				try {
					iEventLsn.eventProcess(IEventType.MEASURE_TYPE_CLICK,
							list.get(position),position);
				} catch (HDException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

}

class ViewHolder { 
    TextView textView; 
    ImageView imageView; 
} 
