package com.hd.hse.dly.phone.ui.activity.workorder;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.module.activity.WorkOrderBaseActivity;
import com.hd.hse.common.module.phone.ui.module.frament.CounterSignFramment;
import com.hd.hse.common.module.ui.model.fragment.GasesCheckFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dly.phone.R;
import com.hd.hse.dly.phone.ui.fragment.dly.DelayFragment;
import com.hd.hse.dly.phone.ui.fragment.measure.MeasureFragmentdly;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.utils.ServiceActivityUtils;

import org.apache.log4j.Logger;

import java.util.List;

public class WorkOrderActivity extends WorkOrderBaseActivity {
	private static Logger logger = LogUtils
			.getLogger(WorkOrderActivity.class);

	public WorkMeasureReview workMeasureReview = null;
	@Override
	public void setCustomMenuBarInfo() {
		// 添加作业票上传
		setCustomMenuBar(new String[] { IActionBar.ITEM_PHOTOGTAPH,
				IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER });

	}

	@Override
	public Class<? extends NaviFrameFragment> getNavFragmentClass(
			Integer contype) {
		Class<? extends NaviFrameFragment> resultClz=null;
		switch (contype) {
		case IConfigEncoding.HARM_TYPE:
			// 切换到危害的Fragment

			break;
		case IConfigEncoding.ENERGY_TYPE:
			// resultClz = 把对应的 Fragment字节码填上
			break;
		case IConfigEncoding.GAS_TYPE:
			resultClz = GasesCheckFragment.class;
			break;
		case IConfigEncoding.MEASURE_TYPE:
			// resultClz = 把对应的 Fragment字节码填上
			resultClz = MeasureFragmentdly.class;
			break;
		case IConfigEncoding.PPE_TYPE:

			break;
		case IConfigEncoding.SIGN_TYPE:
			resultClz = CounterSignFramment.class;
			break;
		case IConfigEncoding.YQ_SIGN_TYPE:
			 resultClz = DelayFragment.class;
			break;
		default:
			throw new IllegalArgumentException("IConfigEncoding 没有这个类别:"
					+ contype);
		}
		return resultClz;

	}

	@Override
	public void setNaviBarIsCompLete() {
		try {
			// 设置PDA配置实体中完成标记
			SuperEntity currentNaviTouchEntity = ServiceActivityUtils
					.getRefreshWorkConfigInfo(workMeasureReview,
							(PDAWorkOrderInfoConfig) getNaviCurrentEntity());
			// 设置功能导航状态
			setNaviFinish(currentNaviTouchEntity);
		} catch (HDException e) {
			logger.error(e.getMessage());
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	@Override
	public Object beforeValidate(Object... objects)throws HDException {
		// TODO Auto-generated method stub
		PDAWorkOrderInfoConfig pdaConfig = null;
		if(objects[2] !=null && objects[2] instanceof PDAWorkOrderInfoConfig){
			//
			pdaConfig = (PDAWorkOrderInfoConfig)objects[2];
		}
		if(pdaConfig == null || pdaConfig.getContype() != IConfigEncoding.YQ_SIGN_TYPE){
			return Boolean.TRUE;
		}
		if(workMeasureReview!=null && workMeasureReview.getCsnum()!=null){
			if(workMeasureReview.getCsnum() == 0){
				//措施完成，直接延期
				return Boolean.TRUE;
			}
		}
//		if(objects[1]!= null && objects[1] instanceof WorkOrder){
//			WorkOrder workOrder  = (WorkOrder)objects[1];
//			if(!StringUtils.isEmpty(workOrder.getCssavefied()) && workOrder.getCsnum() == 0){
//				//措施完成，直接延期
//				return Boolean.TRUE;
//			}
//		}
		//措施尚未完成，给出提示，
		ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong, "措施尚未确认完成，不能进行延期操作！");
		return Boolean.FALSE;

	}


	@Override
	public IEventListener getiEnventListener() {
		// TODO Auto-generated method stub
		return eventLst;
	}
	private IEventListener eventLst = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			if (IEventType.ACTIONBAR_ITEM_UP_CLICK == eventType) {
				// 作业票上传
				upWorkOrder();
			} else if (IEventType.DOWN_WORK_LIST_LOAD == eventType) {
				if (WorkOrderActivity.this != null) {
					WorkOrderActivity.this.finish();
				}

			} else if (IEventType.DOWN_WORK_LIST_SHOW == eventType) {
				// finish();
			}
		}
	};

	/**
	 * upzyp:TODO(上传作业票).
	 */
	private UpZYPProgressDialog upzyp = null;

	/**
	 * 上传作业票
	 */
	public void upWorkOrder() {
		if (upzyp == null) {
			upzyp = new UpZYPProgressDialog(this);
			upzyp.setGetDataSourceListener(eventLst);
		}
		QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
		String[] ud_zyxk_zysqids = null;
		// 并赋值ud_zyxk_zysqids
		ud_zyxk_zysqids = new String[1];
		ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
		try {
			List<WorkOrder> data = mQueryWorkOrderInfo
					.queryWorkOrder(ud_zyxk_zysqids);
			if (null != data && data.size() > 0) {
				upzyp.execute("上传", "上传作业票信息，请耐心等待.....", "", data);
			} else {
				ToastUtils.toast(getBaseContext(), "请完成操作后重试");
			}
		} catch (HDException e) {
			e.printStackTrace();
		}

	}

}
