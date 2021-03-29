package com.hd.hse.osc.phone.ui.fragment.tempelectricity;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.osc.phone.R;
/**
 * 临时用电设备支持手书
 * @author yn
 *
 */
public class TempElePopUpWindow {
	/**
	 * 依赖的activity
	 */
	private Activity activity;
	/**
	 * popupwindow
	 */
	private PopupWindow popupWin;
	/**
	 * popupwindow 的xml布局
	 */
	private View view;
	/**
	 * 设备名称
	 */
	private EditText edName;
	/**
	 * 型号规格
	 */
	private EditText edXhgg;
	/**
	 * 功率
	 */
	private EditText edGl;
	/**
	 * 数量
	 */
	private EditText edCount;
	/**
	 * 确定按钮
	 */
	private TextView tvEntrue;
	/**
	 * 删除按钮
	 */
	private TextView tvDele;
	/**
	 * 取消按钮
	 */
	private TextView tvCancle;
	/**
	 * 临时用电设备
	 */
	private TempEleAsset tempEleAsset;
	/**
	 * 接口回调
	 */
	private CallBackListener mListener;
	private int state;
	private final int createState = 1;
	private final int editState = 2;

	public TempElePopUpWindow(Activity activity) {
		this.activity = activity;
		initPopupWindow();
	}

	public void setCallBackListener(CallBackListener mListener) {
		this.mListener = mListener;
	}

	/**
	 * 初始化popupWindow
	 */
	private void initPopupWindow() {
		view = LayoutInflater.from(activity).inflate(
				R.layout.hd_hse_scene_phone_tempele_fragment_popupwindow, null);
		edName = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_ed_sb_name);
		edXhgg = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_ed_xhgg);
		edGl = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_ed_gl);
		edCount = (EditText) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_ed_count);
		tvEntrue = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_tv_entrue);
		tvDele = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_tv_dele);
		tvCancle = (TextView) view
				.findViewById(R.id.hd_hse_scene_phone_tempele_fragment_popupwindow_tv_cancle);
		popupWin = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				backgroundAlpha(1f);
			}
		});
		// 确定按钮
		tvEntrue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 新建或者修改
				if (state == createState) {
					// 新建
					if (mListener != null) {
						if (saveCreateYdsb()) {
							mListener.onSaveCreateYdsb(tempEleAsset);
						} else {
							return;
						}

					}

				} else if (state == editState) {
					// 编辑
					if (mListener != null) {

						if (saveEditYdsb()) {
							mListener.onSaveEditYdsb(tempEleAsset);
						} else {
							return;
						}

					}
				}
				dismissPopupWindow();
			}
		});
		// 删除按钮
		tvDele.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 删除该条数据
				if (mListener != null) {
					mListener.onDeleYdsb(tempEleAsset);
				}
				dismissPopupWindow();
			}
		});
		// 取消按钮
		tvCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 直接关闭pop
				dismissPopupWindow();

			}
		});

	}

	/**
	 * 居中显示popupwindow
	 * 
	 * @param view
	 */
	public void showPopupWindow(View view, TempEleAsset tempEleAsset) {
		if (null==tempEleAsset) {
			ToastUtils.toast(activity, "未传入临时用电设备");
			return;
		}
		if (popupWin != null && !popupWin.isShowing()) {
			popupWin.showAtLocation(view, Gravity.CENTER, 0, 0);
			backgroundAlpha(0.5f);
		}
		// 编辑用电设备
		editYdsb(tempEleAsset);

	}

	public void showPopupWindow(View view, String ud_zyxk_zysqid) {
		if (null == ud_zyxk_zysqid || "".equals(ud_zyxk_zysqid)) {
			ToastUtils.toast(activity, "错误，未传入作业票申请id");
			return;
		}
		if (popupWin != null && !popupWin.isShowing()) {
			popupWin.showAtLocation(view, Gravity.CENTER, 0, 0);
			backgroundAlpha(0.5f);
		}
		// 新建用电设备
		createYdsb(ud_zyxk_zysqid);
	}

	/**
	 * 隐藏pop
	 */
	public void dismissPopupWindow() {
		if (popupWin != null && popupWin.isShowing()) {
			popupWin.dismiss();
		}
	}

	/**
	 * 设置背景透明度
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		activity.getWindow().setAttributes(lp);
	}

	/**
	 * 新建用电设备
	 */
	private void createYdsb(String ud_zyxk_zysqid) {
		state = createState;
		tempEleAsset = new TempEleAsset();
		tempEleAsset.setUd_zyxk_zysqid(ud_zyxk_zysqid);
		tvDele.setVisibility(View.GONE);
		edCount.setText("");
		edGl.setText("");
		edName.setText("");
		edXhgg.setText("");
	}

	/**
	 * 编辑用电设备
	 */
	private void editYdsb(TempEleAsset tempEleAsset) {
		state = editState;
		this.tempEleAsset = tempEleAsset;
		tvDele.setVisibility(View.VISIBLE);
		edCount.setText(tempEleAsset.getCount() + "");
		edGl.setText(tempEleAsset.getFh() + "");
		edName.setText(tempEleAsset.getAssetname());
		edXhgg.setText(tempEleAsset.getVersion());
	}

	/**
	 * 保存新建的用电设备
	 */
	private boolean saveCreateYdsb() {
		// 设置id之类的操作
		return isCompleted();
	}

	/**
	 * 保存编辑的用电设备
	 */
	private boolean saveEditYdsb() {
		return isCompleted();
	}

	/**
	 * 校验所需要的信息是否填写完，设置TempEleAsset,
	 */
	private boolean isCompleted() {
		// 校验设备名称是否填写
		String sbName = edName.getText().toString().trim();
		if (null == sbName || "".equals(sbName)) {
			ToastUtils.toast(activity, "请填写设备名称");
			return false;
		} else {
			tempEleAsset.setAssetname(sbName);
		}
		// 校验型号规格是否填写
		String xhgg = edXhgg.getText().toString().trim();
		if (null == xhgg || "".equals(xhgg)) {
			ToastUtils.toast(activity, "请填写型号规格");
			return false;
		} else {
			tempEleAsset.setVersion(xhgg);
		}
		// 校验功率是否填写
		String gl = edGl.getText().toString().trim();
		if (null == gl || "".equals(gl)) {
			ToastUtils.toast(activity, "请填写功率");
			return false;
		} else {
			tempEleAsset.setFh(Double.parseDouble(gl));
		}
		// 校验数量是否填写
		String count = edCount.getText().toString().trim();
		if (null == count || "".equals(count)) {
			ToastUtils.toast(activity, "请填写数量");
			return false;
		} else {
			tempEleAsset.setCount(Integer.parseInt(count));
		}
		return true;

	}

	/**
	 * 接口回调
	 */
	interface CallBackListener {
		/**
		 * 保存编辑的用电设备
		 */
		void onSaveEditYdsb(TempEleAsset tempEleAsset);

		/**
		 * 保存新建的用电设备
		 */
		void onSaveCreateYdsb(TempEleAsset tempEleAsset);

		/**
		 * 删除用电设备
		 */
		void onDeleYdsb(TempEleAsset tempEleAsset);
	}
}
