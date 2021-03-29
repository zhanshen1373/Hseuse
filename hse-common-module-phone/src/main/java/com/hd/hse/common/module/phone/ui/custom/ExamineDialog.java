package com.hd.hse.common.module.phone.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.entity.workorder.WorkOrder;

import org.apache.log4j.Logger;

import java.util.List;

public class ExamineDialog<T extends SuperEntity> extends Dialog {
	private Context context = null;
	private static Logger logger = LogUtils.getLogger(ExamineDialog.class);
	private ExamineListView examine;
	private IEventListener iEventLsn = null;
	private int height;

	/**
	 * isfeast:TODO(防止多次点击).
	 */
	private boolean isfeast = true;

	public ExamineDialog(Context context, int theme) {
		super(context, R.style.hd_hse_common_examine_dialog);
		this.context = context;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		height = wm.getDefaultDisplay().getHeight();
		setContentView(R.layout.hd_hse_common_examine_layout);
		Window myDialog = this.getWindow();
		WindowManager.LayoutParams p = myDialog.getAttributes(); // 获取对话框当前的参数值
		p.height = height / 2;
		myDialog.setAttributes(p);
		initView();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// 注册广播
		examine.registerBoradcastReceiver();
	}

	/**
	 * 页面初始化
	 * 
	 * @param context
	 */
	private void initView() {
		Window dialogWindow = this.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
		// TODO Auto-generated method stub
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		examine = (ExamineListView) this.findViewById(R.id.hd_hse_examine_elv);
		examine.setContext(context);
	}

	public Fragment getFragment() {
		return examine.getFragment();
	}

	public void setFragment(Fragment fragment) {
		examine.setFragment(fragment);
	}

	/**
	 * setIsClick:(是否可以点击). <br/>
	 * date: 2015年2月5日 <br/>
	 * 
	 * @author lxf
	 * @param isclick
	 */
	public void setIsClick(boolean isclick) {
		if (examine != null) {
			examine.setIsClick(isclick);
		}
	}

	/**
	 * 设置作业票信息
	 * 
	 * @param workOrder
	 */
	public void setWorkOrder(WorkOrder workOrder) {
		examine.setWorkOrder(workOrder);
	}

	/**
	 * 设置监听接口
	 * 
	 * @param iEventLsn
	 */
	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
		examine.setIEventListener(iEventLsn);
	}

	/**
	 * 设置审批环节数据集合
	 * 
	 * @param examineList
	 */
	@SuppressWarnings("unchecked")
	public void setExamineList(List<T> examineList) {
		examine.setData(examineList);
	}

	/**
	 * 设置刷卡实体
	 * 
	 * @param currentEntity
	 */
	public void setCurrentEntity(T currentEntity) {
		examine.setCurrentEntity(currentEntity);
	}

	/**
	 * reFresh:(刷新环节点). <br/>
	 * date: 2015年2月28日 <br/>
	 * 
	 * @author lxf
	 */
	public void reFresh() {
		if (examine != null) {
			examine.reFresh();
		}
		isfeast = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!(event.getX() >= -10 && event.getY() >= -10)
					|| event.getX() >= examine.getWidth() + 10
					|| (event.getY() + 20) <= (height - examine.getHeight())) {// 如果点击位置在当前View外部则销毁当前视图,其中10与20为微调距离
				existDialog();
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			existDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * existDialog:(退出adialog). <br/>
	 * date: 2015年2月11日 <br/>
	 * 
	 * @author lxf
	 */
	private void existDialog() {
		// 销毁广播
		if (!isfeast) {
			return;
		}
		isfeast = false;
		try {
			examine.destroyBroadcast();
			this.dismiss();
			if (iEventLsn != null) {
				SuperEntity currentSuperEntity = null;
				if (examine != null) {
					currentSuperEntity = examine.getCurrentEntity();
				}
				iEventLsn.eventProcess(IEventType.ACTION_DIALOG_DIS,
						currentSuperEntity);
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.toast(getContext(), "返回报错，请联系管理!");
		}
	}
}
