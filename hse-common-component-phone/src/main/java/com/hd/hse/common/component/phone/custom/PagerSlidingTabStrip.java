package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;

/**
 * 
 * ClassName: PagerSlidingTabStrip ()<br/>
 * date: 2015年1月4日  <br/>
 *
 * @author flb
 * @version
 */
public class PagerSlidingTabStrip extends HorizontalScrollView {

	private int currentPosition = 0;
	private float currentPositionOffset = 0.0f;
	
	private FrameLayout mFrameLayout;
	private LinearLayout tabsContainer;
	private InnerFrameLayout mFinishedContainer;
	private int tabCount;
	
	private Paint dividerPaint;
	private Paint underlinePaint;
	private Paint bitmapPaint;
	
	private BitmapDrawable arrowDrawable;
	private BitmapDrawable finishDrawable;
	private LinearLayout.LayoutParams defaultLayoutParams;
	private PagerSlidingTabAdapter adapter;
	
	private int lastScrollX = 0;
	private IEventListener mIEventListener;
	
	private int arrowIconHeight = 0;                  //箭头的高度             
	private int finishIconHeight = 0;                 //完成图片的高度             
	
	/***************颜色常量************************/
	private int dividerPassColor = 0xff4ab6f4;        //item之间分割线颜色(被选中)
	private int dividerUnPassColor = 0xffc9c9c9;      //item之间分割线颜色(没有被选中)
	
	private int underlineColor = 0xff4ab6f4;          //导航与Content之间的分割线颜色
	/***************数值常量**********************/
	private int dividerHeight = 4;                    //item之间分割线的高度
	private int underlineHeight = 2;                  //导航与Content之间的分割线的高度
	
	private int scrollOffset = 52;                    //点击item之后，将item滚动的位置
	private int tabPadding;                      //每个tab的padding(处理第一个item的左边和最后一个item的右边)
	
	private int lllayoutLeftRightPadding = 8;         //LinearLayout容器的左右Padding
	private int lllayoutTopPadding = 8;               //LinearLayout容器的上方padding
	private int lllayoutBottomPadding = 8;     	      //LinearLayout容器的下方padding
	private int bmpHeight = 0;                        //item中资源图片的高度
	
	public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setFillViewport(true);
		setWillNotDraw(false);
		
		this.setBackgroundColor(Color.WHITE);
		
		mFrameLayout = new FrameLayout(context);
		mFrameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mFrameLayout);
		
		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mFrameLayout.addView(tabsContainer);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		dividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerHeight, dm);
		underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
		tabPadding = (int) getResources().getDimension(R.dimen.hd_common_component_nav_one_gap);
		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		lllayoutLeftRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lllayoutLeftRightPadding, dm);
		lllayoutTopPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lllayoutTopPadding, dm);
		lllayoutBottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lllayoutBottomPadding, dm);
		
		//TODO 获取自定义属性
		
		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setColor(dividerUnPassColor);
		dividerPaint.setStrokeWidth(dividerHeight);
		
		underlinePaint = new Paint();
		underlinePaint.setAntiAlias(true);
		underlinePaint.setColor(underlineColor);
		underlinePaint.setStrokeWidth(underlineHeight);
		
		bitmapPaint = new Paint();
		bitmapPaint.setAntiAlias(true);
		
		defaultLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

		finishDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.hd_hse_common_nav_item_finished);
		finishIconHeight = finishDrawable.getIntrinsicHeight();
		arrowDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.indicator_arrow);
		arrowIconHeight = arrowDrawable.getIntrinsicHeight();
		tabsContainer.setPadding(lllayoutLeftRightPadding, Math.max(lllayoutTopPadding, finishIconHeight / 2), lllayoutLeftRightPadding, Math.max(lllayoutBottomPadding, arrowIconHeight));
		
		mFinishedContainer = new InnerFrameLayout(context);
		mFinishedContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, finishIconHeight+lllayoutTopPadding));
		mFinishedContainer.setPadding(lllayoutLeftRightPadding, Math.max(lllayoutTopPadding, finishIconHeight / 2), lllayoutLeftRightPadding, 0);
		mFrameLayout.addView(mFinishedContainer);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(isInEditMode() || tabCount == 0){
			return ;
		}
		
		final int height = getHeight();
		
		//绘制underline
		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, underlinePaint);
		
		//绘制箭头
		View currentTab = tabsContainer.getChildAt(currentPosition);
		float tabLeft = currentTab.getLeft();
		float tabRight = currentTab.getRight();
		int arrowWidth = arrowDrawable.getIntrinsicWidth();
		float arrowLeft = (tabRight + tabLeft - arrowWidth - tabPadding) / 2.0f;
		if(currentPosition > 0 && currentPosition < tabCount - 1){
			View nextTab = tabsContainer.getChildAt(currentPosition + 1);
			final float nextTabLeft = nextTab.getLeft();
			final float nextTabRight = nextTab.getRight();
			arrowLeft = ((1.0f - currentPositionOffset) * (tabRight + tabLeft) + currentPositionOffset * ((nextTabRight + nextTabLeft)) - arrowWidth)/2.0f;
		}else if(currentPosition == 0){
			arrowLeft = (tabRight + tabLeft - arrowWidth - tabPadding) / 2.0f;
		}else{
			arrowLeft = (tabRight + tabLeft - arrowWidth + tabPadding) / 2.0f;
		}
		canvas.drawBitmap(arrowDrawable.getBitmap(), arrowLeft, height - arrowIconHeight + 1, bitmapPaint);
		
		float distance = 0;
		if(bmpHeight == 0){
			distance = (currentTab.getHeight() - dividerHeight) / 2.0f;
		}else{
			distance = (bmpHeight - dividerHeight) / 2.0f;
		}
		
		//绘制分割线
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = tabsContainer.getChildAt(i);
			View nextTab = tabsContainer.getChildAt(i+1);
			if(adapter.isFinished(i)){
				dividerPaint.setColor(dividerPassColor);
			}else{
				dividerPaint.setColor(dividerUnPassColor);
			}
			
			canvas.drawRect(tab.getLeft() + tab.getWidth() / 2, tab.getTop() + distance, nextTab.getLeft() + nextTab.getWidth() / 2, tab.getTop() + distance + dividerHeight, dividerPaint);
			canvas.save();
		}
	}
	
	public interface PagerSlidingTabAdapter{
		public int getTabCount();
		public Object getItem(int position);
		public View getView(int position);
		
		/**
		 * 为业务单独创建的方法
		 * 
		 * isFinished:(). <br/>
		 * date: 2015年1月5日 <br/>
		 * @author flb
		 * @return
		 */
		public boolean isFinished(int position);
	}
	
	public void setPagerSlidingAdapter(PagerSlidingTabAdapter adapter, int bmpHeight){
		this.adapter = adapter;
		this.bmpHeight = bmpHeight;
		notifiyDataSetChanged();
	}
	
	public void notifiyDataSetChanged(){
		tabsContainer.removeAllViews();
		tabCount = adapter.getTabCount();
		for(int i=0; i < tabCount; i++){
			View tab = adapter.getView(i);
			tab.setBackgroundColor(Color.parseColor("#00FFFFFF"));
			addTab(i, tab);
		}
		
		invalidate();
		mFinishedContainer.invalidate();
	}
	
	private void addTab(final int position, View tab){
		tab.setFocusable(true);
		tab.setClickable(true);
		
		tab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentPosition != position){
					scrollToChild(position, 0);
				}
				
				if(mIEventListener != null){
					try {
						mIEventListener.eventProcess(IEventType.NAVIGATION_ITME_SINGLE_CLICK, position);
					} catch (HDException e) {
						//TODO 异常处理
						e.printStackTrace();
					}
				}
			}
		});
		
		if(position == 0){
			tab.setPadding(0, 0, tabPadding, 0);
		}else if(position == tabCount - 1){
			tab.setPadding(tabPadding, 0, 0, 0);
		}else{
			tab.setPadding(tabPadding, 0, tabPadding, 0);
		}
		tabsContainer.addView(tab, position, defaultLayoutParams);
	}
	
	public void setCurrentItem(int position){
		scrollToChild(position, 0);
		notifiyDataSetChanged();
	}
	
	public int getCurrentItem(){
		return currentPosition;
	}
	
	private void scrollToChild(int position, int offset) {
		if(tabCount == 0){
			return ;
		}
		
		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset - lllayoutLeftRightPadding;
		if(position > 0 || offset > 0){
			newScrollX -= scrollOffset;
		}
		
		if(newScrollX != lastScrollX){
			lastScrollX = newScrollX;
			smoothScrollTo(newScrollX, 0);
		}
		
		currentPosition = position;
	}
	
	public void setOnItemClickListener(IEventListener listener){
		this.mIEventListener = listener;
	}
	
	private class InnerFrameLayout extends FrameLayout{
		
		public InnerFrameLayout(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init(context);
		}

		public InnerFrameLayout(Context context, AttributeSet attrs) {
			super(context, attrs);
			init(context);
		}

		public InnerFrameLayout(Context context) {
			super(context);
			init(context);
		}

		private void init(Context context) {
			setWillNotDraw(false);
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			//绘制右上角标记
			for (int i = 0; i < tabCount; i++) {
				View tab = tabsContainer.getChildAt(i);
				if(adapter.isFinished(i)){
					if(i == 0){
						canvas.drawBitmap(finishDrawable.getBitmap(), tab.getRight() - finishIconHeight/2 - tabPadding, tab.getTop() - finishIconHeight/2, bitmapPaint);
					}else if(i == tabCount - 1){
						canvas.drawBitmap(finishDrawable.getBitmap(), tab.getRight() - finishIconHeight/2, tab.getTop() - finishIconHeight/2, bitmapPaint);
					}else{
						canvas.drawBitmap(finishDrawable.getBitmap(), tab.getLeft() + tab.getWidth()/2 + bmpHeight/2 - finishIconHeight/2, tab.getTop() - finishIconHeight/2, bitmapPaint);
					}
					canvas.save();
				}
			}
		}
	}
}
