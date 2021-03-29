package com.hd.hse.common.module.phone.ui.custom;
/**
 * 虚拟位置卡显示列表
 */
import java.util.List;

import org.apache.log4j.Logger;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.ExamineListView;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PositionDialog<T extends SuperEntity> extends Dialog {
	private static Logger logger = LogUtils.getLogger(PositionDialog.class);
	private static final String PSNDESC = "location_desc";
	private Context context = null;
	private int height;
	private ListView psnListView;
	private List<T> datas;
	private IEventListener iEventLsn;
	private Resources resources;
	private BaseAdapter adapter;
	
	public PositionDialog(Context context) {
		super(context, R.style.hd_hse_common_examine_dialog);
		this.context = context;
		resources = context.getResources();
		
		//获取屏幕高度
		height = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
		
		setContentView(R.layout.hd_hse_common_position_card);

		initView();
	}
	
	private void initView() {
		View cancel = findViewById(R.id.hd_hse_common_position_cancel);
		psnListView = (ListView) findViewById(R.id.hd_hse_common_position_lv);
		
		adapter = new PositionAdapter();
		psnListView.setAdapter(adapter);
		psnListView.setDivider(resources.getDrawable(R.drawable.hd_hse_examine_blank_divider));
		
		//设置listview的高度为屏幕的1/3
		psnListView.getLayoutParams().height = height / 3;
		
		cancel.setOnClickListener(new android.view.View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				closeDialog();
			}
		});
		
		psnListView.setOnItemClickListener(itemlsn);
	}
	
	/**
	 * 设置数据源
	 * @param datas
	 */
	public void setDatas(List<T> datas) {
		this.datas = datas;
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * 设置监听
	 * @param iEventLsn
	 */
	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
	}
	
	/**
	 * 关闭当前dialog
	 */
	private void closeDialog(){
		this.dismiss();
	}
	

	/**
	 * item的单击事件
	 */
	private OnItemClickListener itemlsn = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long arg3) {
			if (iEventLsn == null) {
				return;
			}
			try {
				iEventLsn.eventProcess(IEventType.POSITION_CARD,datas.get(position));
				closeDialog();
			} catch (HDException e) {
				logger.error(e.getMessage());
			}

		}
	};
	
	/**
	 * listview适配器
	 * @author zhulei
	 *
	 */
	class PositionAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			if (datas != null && datas.size() > 0) {
				return datas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (datas != null && datas.size() > position) {
				return datas.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
//			TextView psnView ;
			ViewHolder holder = null;
			if(resources == null){
				resources = context.getResources();
			}
			//重用textview
			if(convertView == null){
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.hd_hse_common_position_item, null);
				
				holder.textView = (TextView) convertView.findViewById(R.id.hd_hse_common_position_item_tv);
				
				convertView.setTag(holder);
				
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			logger.error("提供列表供用户选择："+(String)datas.get(position).getAttribute(PSNDESC));
			
			holder.textView.setText((String)datas.get(position).getAttribute(PSNDESC));
			
			return convertView;
		}
		
	}
	
	class ViewHolder {
		TextView textView;
	}

}
