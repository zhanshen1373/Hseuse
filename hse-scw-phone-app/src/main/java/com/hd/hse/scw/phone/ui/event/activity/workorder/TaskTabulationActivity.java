package com.hd.hse.scw.phone.ui.event.activity.workorder;

import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView.OnZYPItemClickListener;
import com.hd.hse.common.module.phone.custom.PopWinButton;
import com.hd.hse.common.module.phone.custom.ZYPOperatePopWindow;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.scw.business.workorder.WorkTaskSrv;
import com.hd.hse.scw.phone.R;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

public class TaskTabulationActivity extends BaseListBusActivity {
	/**
	 * 是否是第一次
	 */
	private boolean isfirst = false;
	public final static String WORK_ORDER = "workorder";
	public final static String CHOOSECODE = "choosecode";
	public final static String WINDOWCODE = "windowcode";
	public final static String JJBACTIONTYPE = "jbbactiontype";
	public final static String JJBCONTYPEDESC = "jjbdesc";
	private static Logger logger = LogUtils.getLogger(TaskTabulationActivity.class);
	private IQueryWorkInfo quertWorkInfo;
	private ZYPOperatePopWindow popWindow;
	private String zypClass;
	Intent intent = null ;
	/**
	 * 按钮组
	 */
	private PopWinButton[] mPopWinButton;
	@Override
	public WorkTaskDBSrv getWorkTaskObject() {
		// TODO Auto-generated method stub
		return new WorkTaskSrv();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		isfirst = true;
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		isfirst = false;
		popWindowsMiss();
		initData();
		// lsv.addSetions();
	}
	
	/**
	 * TODO 重写方法
	 * 
	 * @see com.hd.hse.dc.ui.activity.list.base.BaseDataCenterActivity#initData()
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
//		super.initData();
		if(intent == null){
			intent = new Intent(TaskTabulationActivity.this, GuardianShiftChangeActivity.class);
		}
		WorkTaskSrv wts = new WorkTaskSrv();
		List<SuperEntity> lstEntity = null;
		try {
			lstEntity = wts.loadWorkTaskList(getSearchStr());
			if(!isfirst && (lstEntity == null || lstEntity.size() < 1)){
				ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong, "没有作业票！");
			}
			// 设置数据集,getSearchStr()为查询条件
			setDataList(lstEntity);
		} catch (HDException e) {
			// TODO Auto-generated catch block
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					"数据集获取失败！");
		}
	}
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		mPopWinButton = new PopWinButton[2];
		String[] strBtn = new String[] { "作业人交接", "监护人交接" };
		int[] strEventType = new int[] { IEventType.ACTION_ZYRJJ_SCW,
				IEventType.ACTION_JHRJJ_SCW };
		int[] strDrawable = new int[] {
				R.drawable.hd_hse_common_module_pop_btn,
				R.drawable.hd_hse_common_module_pop_btn };
		for (int i = 0; i < 2; i++) {
			mPopWinButton[i] = new PopWinButton();
			mPopWinButton[i].eventType = strEventType[i];
			mPopWinButton[i].text = strBtn[i];
			mPopWinButton[i].drawableId = strDrawable[i];
		}
		setiEventLsn(iEventLsn);
		popWindow = new ZYPOperatePopWindow(this);
		popWindow.setEventListener(iEventLsn);
		setItemClickListener(zypItemClickListener);
	}

	/**
	 * eventListener:TODO().
	 */
	private IEventListener iEventLsn = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			switch (eventType) {
			case IEventType.ACTIONBAR_SEARCH_CLICK:
				if (objects[0] instanceof String) {
					setSearchStr((String) objects[0]);
					initView();
					initData();
				}
				break;

			case IEventType.ACTION_ZYRJJ_SCW:
				intent.putExtra(CHOOSECODE, IConfigEncoding.JJB01);
				intent.putExtra(WINDOWCODE, IConfigEncoding.JJB_WORK_PERSON_CHANGE_NUM);
				intent.putExtra(JJBACTIONTYPE, IActionType.JJB_WORK_PERSON_CHANGE);
				intent.putExtra(JJBCONTYPEDESC, "作业人交接");
				startActivity(intent);
				overridePendingTransition(R.anim.hd_hse_common_zoomin,
						R.anim.hd_hse_common_zoomout); // Activity切换动画
				break;
			case IEventType.ACTION_JHRJJ_SCW:
				intent.putExtra(CHOOSECODE, IConfigEncoding.JJB02);
				intent.putExtra(WINDOWCODE, IConfigEncoding.JJB_GUARDIAN_CHANGE_NUM);
				intent.putExtra(JJBACTIONTYPE, IActionType.JJB_GUARDIAN_CHANGE);
				intent.putExtra(JJBCONTYPEDESC, "监护人交接");
				startActivity(intent);
				overridePendingTransition(R.anim.hd_hse_common_zoomin,
						R.anim.hd_hse_common_zoomout); // Activity切换动画
				break;
			case PhoneEventType.ZYPLIST_ITEM_LONG_CLICK:
				if(quertWorkInfo==null){
					quertWorkInfo = new QueryWorkInfo();
				}
				showWorkOrderPopupWin(quertWorkInfo.queryWorkInfo((WorkOrder) objects[0], null));
			break;
			}
		}
	};
	
	private OnZYPItemClickListener zypItemClickListener = new OnZYPItemClickListener() {
		
		@Override
		public void onClick(WorkOrder workOrder, View anchorView,
				int pointerHorizontalPosition) {
			if(quertWorkInfo == null){
				quertWorkInfo = new QueryWorkInfo();
			}
			try {
				workOrder = (WorkOrder) quertWorkInfo.queryWorkInfo(workOrder,null);
				
				intent.putExtra(WORK_ORDER, workOrder);
				
				zypClass = workOrder.getZypclass();
				if (!isRelative(IRelativeEncoding.JJBZYRBG,IConfigEncoding.JJB01)
						&& !isRelative(IRelativeEncoding.JJBJHRBG,IConfigEncoding.JJB02)) {
					ToastUtils.imgToast(TaskTabulationActivity.this,R.drawable.hd_hse_common_msg_wrong,
							"当前作业票不存在监护人变更和监护人变更！");
				// 即有监护人变更，又有作业人变更
				} else if (isRelative(IRelativeEncoding.JJBZYRBG,IConfigEncoding.JJB01)
						&& isRelative(IRelativeEncoding.JJBJHRBG,IConfigEncoding.JJB02)) {
					
					popWindow.show(anchorView, 0,pointerHorizontalPosition, mPopWinButton,workOrder);
					return;
				
				} else if (isRelative(IRelativeEncoding.JJBZYRBG,IConfigEncoding.JJB01)
						&& !isRelative(IRelativeEncoding.JJBJHRBG,IConfigEncoding.JJB02)) {
					// 作业人变更
					intent.putExtra(CHOOSECODE, IConfigEncoding.JJB01);
					intent.putExtra(WINDOWCODE, IConfigEncoding.JJB_WORK_PERSON_CHANGE_NUM);
					intent.putExtra(JJBACTIONTYPE, IActionType.JJB_WORK_PERSON_CHANGE);
					intent.putExtra(JJBCONTYPEDESC, "作业人交接");
				} else {
					// 监护人变更
					intent.putExtra(CHOOSECODE, IConfigEncoding.JJB02);
					intent.putExtra(WINDOWCODE, IConfigEncoding.JJB_GUARDIAN_CHANGE_NUM);
					intent.putExtra(JJBACTIONTYPE, IActionType.JJB_GUARDIAN_CHANGE);
					intent.putExtra(JJBCONTYPEDESC, "监护人交接");
				}
				
				startActivity(intent);
				
			} catch (HDException e) {
				logger.error(e.getMessage());
				ToastUtils.imgToast(TaskTabulationActivity.this, R.drawable.hd_hse_common_msg_wrong,
						e.getMessage());
			}
		}
	};
	 
	/**
	 * 关闭popwindow
	 */
	public void popWindowsMiss() {
		if(popWindow!=null && popWindow.isShowing()){
			popWindow.dimiss();
		}
	}
	
	/**
	 * TODO 判断是否存在关联表名信息
	 * isRelative:(). <br/>
	 * date: 2014年12月2日 <br/>
	 *
	 * @author wenlin
	 * @param type
	 * @param function
	 * @return
	 */
	public boolean isRelative(String type , String function ){
		Boolean isRelative = false;
		IQueryRelativeConfig queryRelativeCfg = new QueryRelativeConfig();
		RelationTableName RltTabName = new RelationTableName();
		RltTabName.setSys_type(type);
		RltTabName.setSys_fuction(function);
		RltTabName.setSys_value(zypClass);
		isRelative = queryRelativeCfg.isHadRelative(RltTabName);
		return isRelative;
	}
	
	@Override
	public String getTitileName() {
		// TODO Auto-generated method stub
		return "交接班";
	}

	@Override
	public String getNavCurrentKey() {
		// TODO Auto-generated method stub
		return "hse-scw-phone-app";
	}

	@Override
	public Class<?> getToActivityClass() {
		// TODO Auto-generated method stub
		return GuardianShiftChangeActivity.class;
	}

	@Override
	public String getFunctionCode() {
		// TODO Auto-generated method stub
		return IConfigEncoding.JJB_GUARDIAN_CHANGE_NUM;
	}
}
