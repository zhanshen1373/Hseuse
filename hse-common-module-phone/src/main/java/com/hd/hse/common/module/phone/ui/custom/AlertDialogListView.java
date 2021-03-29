package com.hd.hse.common.module.phone.ui.custom;

import java.util.List;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.base.Domain;

/**
 * TODO 更多选择器弹出器 ClassName: PopWindowListView ()<br/>
 * date: 2014年11月19日 <br/>
 * 
 * @author zhulei
 * @version
 */
public class AlertDialogListView {
	private final Logger logger = LogUtils.getLogger(AlertDialogListView.class);
	private Context context;
	private AlertDialog mAlertDialog;

	/**
	 * mIEventListner:TODO(监听事件).
	 */
	private IEventListener mIEventListner;

	/**
	 * lstDomain:TODO(操作的数据).
	 */
	private List<Domain> lstDomain;

	/**
	 * mPopWinAdapter:TODO(ListView适配器).
	 */
	private BaseAdapter mAlertDialogAdapter;
	/**
	 * titleTV:TODO(弹出框标题).
	 */
	private TextView titleTV;
	/**
	 * choiceMode:TODO(选择模式).
	 */
	private int choiceMode;
	private Resources resource;

	public AlertDialogListView(Context context, List<Domain> lstDomain,
			int choiceMode) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.lstDomain = lstDomain;
		this.choiceMode = choiceMode;
		initPopupWindow();
	}

	/**
	 * TODO 初始化 initPopupWindow:(). <br/>
	 * date: 2014年11月18日 <br/>
	 * 
	 * @author zhulei
	 */
	private void initPopupWindow() {
		resource = context.getResources();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		mAlertDialogAdapter = new mAlertDialogAdapter(context, lstDomain);
		LayoutInflater inflater = LayoutInflater.from(context);
		View mAltDlg = inflater.inflate(R.layout.hd_hse_common_domain_dialog,
				null);
//		mAltDlg.setBackground(resource.getDrawable(R.drawable.hd_hse_config_bg_style));
		titleTV = (TextView) mAltDlg
				.findViewById(R.id.hd_hse_common_pager_title_tab_dialog_title);
		TextView sureTV = (TextView) mAltDlg
				.findViewById(R.id.hd_hse_common_domain_sure_tv);
		ListView lv = (ListView) mAltDlg
				.findViewById(R.id.hd_hse_common_pager_title_tab_dialog_lv);
		lv.setAdapter(mAlertDialogAdapter);
		lv.setOnItemClickListener(listener);
		lv.setChoiceMode(choiceMode);
		// 多选显示‘确定’按钮
		if (choiceMode == ListView.CHOICE_MODE_MULTIPLE) {
			sureTV.setVisibility(View.VISIBLE);
		}
		sureTV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					mIEventListner.eventProcess(
							IEventType.POPUPWINDOW_CHOICE_MULTIPLEORSINGLE,
							lstDomain);
					mAlertDialog.dismiss();
				} catch (HDException e) {
					ToastUtils.imgToast(context,
							R.drawable.hd_hse_common_msg_wrong, "选择失败！");
				}
			}
		});
		AlertDialog.Builder builer = new AlertDialog.Builder(context);
		mAlertDialog = builer.create();
		mAlertDialog.setView(mAltDlg, 0, 0, 0, 0);
		Window window = mAlertDialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
		mAlertDialog.show();
		WindowManager.LayoutParams params = mAlertDialog.getWindow()
				.getAttributes();
		// params.width = 680;
		if (lstDomain != null && lstDomain.size() > 6) {
			params.height = height / 2;
		} else {
			params.height = LayoutParams.WRAP_CONTENT;
		}
		mAlertDialog.getWindow().setAttributes(params);
	}

	/**
	 * TODO 设置弹出框的标题 setPopWinTitle:(). <br/>
	 * date: 2014年11月21日 <br/>
	 * 
	 * @author zhulei
	 * @param title
	 */
	public void setPopWinTitle(String title) {
		titleTV.setText(title);
	}

	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			CheckedTextView checkTV = (CheckedTextView) view
					.findViewById(singleOrmultipleCheckTV());
			Log.i(AlertDialogListView.class.getName(), String.valueOf(position));
			if (mIEventListner != null) {
				if (choiceMode == ListView.CHOICE_MODE_SINGLE) {
					try {
						for (int i = 0; i < lstDomain.size(); i++) {
							lstDomain.get(i).setIsselected(0);
						}
						view.setBackgroundColor(resource
								.getColor(R.color.hd_hse_common_item_down_bg));
						lstDomain.get(position).setIsselected(1);
						checkTV.toggle();
						checkTV.setCheckMarkDrawable(resource
								.getDrawable(R.drawable.hd_hse_common_component_phone_whfhzb_list_icon_checked));
						checkTV.setTextColor(resource.getColor(R.color.hd_hse_common_white));
						mIEventListner.eventProcess(
								IEventType.POPUPWINDOW_CHOICE_MULTIPLEORSINGLE,
								lstDomain.get(position));
						mAlertDialogAdapter.notifyDataSetChanged();
						mAlertDialog.dismiss();
					} catch (HDException e) {
						logger.error(e.getMessage());
					}
				} else {
					if (checkTV.isChecked()) {
						lstDomain.get(position).setIsselected(0);
						checkTV.setChecked(false);
						checkTV.setCheckMarkDrawable(null);
						checkTV.setBackgroundColor(resource
								.getColor(R.color.hd_hse_common_item_bg));
						checkTV.setTextColor(resource.getColor(R.color.hd_hse_common_alerttext_black));
					} else {
						checkTV.setChecked(true);
						checkTV.setBackgroundColor(resource
								.getColor(R.color.hd_hse_common_item_down_bg));

						checkTV.setTextColor(resource.getColor(R.color.hd_hse_common_white));
						checkTV.setCheckMarkDrawable(resource
								.getDrawable(R.drawable.hd_hse_common_component_phone_whfhzb_list_icon_checked));
						lstDomain.get(position).setIsselected(1);
					}
				} 
			}
		}
	};

	public void setOnClickListener(IEventListener mIEventListner) {
		this.mIEventListner = mIEventListner;
	}

	/**
	 * TODO 设置数据 setLstomains:(). <br/>
	 * date: 2014年11月21日 <br/>
	 * 
	 * @author zhulei
	 * @param lstDomains
	 */
	// public void setLstDomains(List<Domain> lstDomains) {
	// this.lstDomain = lstDomains;
	// mAlertDialogAdapter.notifyDataSetChanged();
	// }

	public class mAlertDialogAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public mAlertDialogAdapter(Context context, List<Domain> LstDomains) {
			lstDomain = LstDomains;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lstDomain.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lstDomain.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(singleOrmultipleLayout(), null, false);
				holder.checkView = (CheckedTextView) view
						.findViewById(singleOrmultipleCheckTV());
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.checkView.setText(lstDomain.get(position).getDescription());
			holder.checkView.setHint(lstDomain.get(position).getValue());
			if (lstDomain.get(position).getIsselected() == 1) {
				holder.checkView.setBackgroundColor(resource
						.getColor(R.color.hd_hse_common_item_down_bg));
				holder.checkView.setChecked(true);
				holder.checkView.setTextColor(resource.getColor(R.color.hd_hse_common_white));
				holder.checkView
						.setCheckMarkDrawable(resource
								.getDrawable(R.drawable.hd_hse_common_component_phone_whfhzb_list_icon_checked));
			} else {
				holder.checkView.setChecked(false);
				holder.checkView.setCheckMarkDrawable(null);
				holder.checkView.setTextColor(resource.getColor(R.color.hd_hse_common_alerttext_black));
				holder.checkView.setBackgroundColor(resource
						.getColor(R.color.hd_hse_common_item_bg));
			}
			return view;
		}
	}

	class ViewHolder {
		CheckedTextView checkView;
	}

	/**
	 * TODO 根据选择类型返回特定的布局 singleOrmultipleLayout:(). <br/>
	 * date: 2014年11月28日 <br/>
	 * 
	 * @author zhulei
	 * @return
	 */
	public int singleOrmultipleLayout() {
		int mLayout = 0;
		if (choiceMode == ListView.CHOICE_MODE_SINGLE) {
			mLayout = R.layout.hd_hse_common_module_alertdialog_singleselect_style;
		} else {
			mLayout = R.layout.hd_hse_common_module_alertdialog_mulselect_item;
		}
		return mLayout;
	}

	/**
	 * TODO 根据选择类型返回特定的CheckTextView的id singleOrmultipleCheckTV:(). <br/>
	 * date: 2014年11月28日 <br/>
	 * 
	 * @author zhulei
	 * @return
	 */
	public int singleOrmultipleCheckTV() {
		int mCheckTextView = 0;
		if (choiceMode == ListView.CHOICE_MODE_SINGLE) {
			mCheckTextView = R.id.hd_hse_common_alertdialog_singleselect_cv;
		} else {
			mCheckTextView = R.id.hd_hse_common_alertdialog_mulselect_cv;
		}
		return mCheckTextView;
	}

	public AlertDialog getPopupWindow() {
		return mAlertDialog;
	}
}
