package com.hd.hse.common.component.phone.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.dytable.DyTable;
import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName: DyTablePopupWindow ()<br/>
 * date: 2014年10月9日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class DyTablePopupWindow extends RelativeLayout {

	/**
	 * inflater:TODO(填充类).
	 */
	LayoutInflater inflater;

	/**
	 * dyTable:TODO(详细信息列表).
	 */
	private DyTable dyTable;

	/**
	 * arrayDesc:TODO(详细信息列表中的描述资源).
	 */
	private int arrayDesc = 0;

	public final void setArrayDesc(int arrayDesc) {
		this.arrayDesc = arrayDesc;
	}

	/**
	 * arrayNum:TODO(详细信息列表中的编码).
	 */
	private int arrayNum = 0;

	public final void setArrayNum(int arrayNum) {
		this.arrayNum = arrayNum;
	}

	/**
	 * superEntity:TODO(实体类).
	 */
	private SuperEntity superEntity;

	public SuperEntity getSuperEntity() {
		return superEntity;
	}

	public void setSuperEntity(SuperEntity superEntity) {
		this.superEntity = superEntity;
	}

	/**
	 * Creates a new instance of DyTablePopupWindow.
	 * 
	 * @param context
	 */
	public DyTablePopupWindow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		initlize();
	}

	/**
	 * Creates a new instance of DyTablePopupWindow.
	 * 
	 * @param context
	 * @param attrs
	 */
	public DyTablePopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		initlize();
	}

	/**
	 * initlize:(初始化). <br/>
	 * date: 2014年9月28日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void initlize() {
		View view = inflater
				.inflate(
						R.layout.hd_hse_common_component_popupwindow_dytable_basic_info,
						this, true);
		dyTable = (DyTable) view
				.findViewById(R.id.hd_hse_common_component_popupwindow_dytable_basic_info_dytable);
	}

	/**
	 * setDyTableValue:(给信息展示列表赋值). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void setDyTableValue() {
		if (arrayDesc > 0)
			dyTable.setArrayDesc(arrayDesc);
		if (arrayNum > 0)
			dyTable.setArrayNum(arrayNum);
		dyTable.initTabRow(superEntity);
	}

	/**
	 * createPopupWindow:(创建自适应弹出菜单). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void createPopupWindow() {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		dyTable.measure(w, h);
		// int height = dyTable.getMeasuredHeight();
		int width = dyTable.getMeasuredWidth();

		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		// float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		// int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		// float xdpi = dm.xdpi;
		// float ydpi = dm.ydpi;
		// int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
		int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）

		if (width > screenWidth - 20) {
			pw = new PopupWindow(this, 724,
					LayoutParams.WRAP_CONTENT);
		} else {
			pw = new PopupWindow(this, (width + 10) > 724 ? 724 : width + 10,
					LayoutParams.WRAP_CONTENT);
		}
	}

	/**
	 * 
	 */
	private void createLayoutPopupWindow() {

		pw = new PopupWindow(this, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	/**
	 * pw:TODO(弹出菜单).
	 */
	private PopupWindow pw = null;

	/**
	 * show:(展示详细信息). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author zhaofeng
	 * @param view
	 */
	public void show(View view) {
		if (pw == null) {
			setDyTableValue();
			createPopupWindow();
		}
		pw.setFocusable(true);
		// 设置允许在外点击消失
		pw.setOutsideTouchable(true);
		pw.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
		pw.showAsDropDown(view, mathDistance(view), 0);
	}

	/**
	 * @param view
	 * @param gravity
	 */
	public void showAsView(View view) {
		setDyTableValue();
		if (pw == null) {
			createLayoutPopupWindow();
		}
		pw.setFocusable(true);
		// 设置允许在外点击消失
		pw.setOutsideTouchable(true);
		pw.setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));
		pw.showAsDropDown(view, 13, 0);
	}

	/**
	 * TODO 计算popwindow与按钮的距离 mathDistance:(). <br/>
	 * date: 2014年11月11日 <br/>
	 * 
	 * @author wenlin
	 * @param view
	 * @return
	 */
	public int mathDistance(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		// 显示的坐标
		int x = location[0];
		int y = location[1];
		// X坐标
		x = (320 - x) / 2;
		return x;
	}
}
