package com.hd.hse.scw.phone.ui.event.activity.workorder;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.scw.phone.R;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;

/**
 * TODO 交接班Activity ClassName: ShiftChangeActivity ()<br/>
 * date: 2014年11月6日 <br/>
 * 
 * @author zhulei
 * @version
 */
public class GuardianShiftChangeActivity extends NaviFrameActivity {
	private static Logger logger = LogUtils
			.getLogger(GuardianShiftChangeActivity.class);

	/**
	 * busAction:TODO(后台业务处理对象).
	 */
	private BusinessAction busAction;
	/**
	 * actionLsr:TODO().
	 */
	private AbstractCheckListener actionLsr;
	/**
	 * asyTask:TODO(后台异步任务).
	 */
	private BusinessAsyncTask asyTask;

	private ProgressDialog prgDialog;
	/**
	 * wmrEntity:TODO(作业措施复查).
	 */
	private WorkMeasureReview wmrEntity;
	/**
	 * personRecord:TODO(作业审批人员记录).
	 */
	private WorkApprovalPersonRecord personRecord;

	/**
	 * workConfigInfo:TODO(PDA作业申请配置信息).
	 */
	private PDAWorkOrderInfoConfig workConfigInfo;
	/**
	 * paras:TODO(危害/审批环节信息).
	 */
	private HashMap<String, Object> paras;

	/**
	 * 刷卡返回结果审批环节点
	 */
	private WorkApprovalPermission wPermission;

	/**
	 * workOrder:TODO(作业票).
	 */
	private WorkOrder workOrder;
	/**
	 * 审批环节信息
	 */
	private ExamineListView examineListView;
	private Intent intent;
	/**
	 * 作业人或监护人描述
	 */
	private String jjbPersonDesc;

	private String chooseCode;

	private String windowCode;

	private String jjbActionType;

	private Image image;
	private boolean istpqm; // 是否电子签名

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_scw_app_layout_handover);
		initParams();
		initControls();
		initApprovalInfo();
	}

	WorkApprovalPermission clon_currentAuditNode = null;

	/**
	 * TODO 初始化对象 initParams:(). <br/>
	 * date: 2014年11月7日 <br/>
	 * 
	 * @author zhulei
	 */
	private void initParams() {
		intent = getIntent();
		workOrder = (WorkOrder) intent
				.getSerializableExtra(TaskTabulationActivity.WORK_ORDER);
		actionLsr = new CheckControllerActionListener();
		busAction = new BusinessAction(actionLsr);
		paras = new HashMap<String, Object>();
	}

	/**
	 * TODO 初始化控件 initControls:(). <br/>
	 * date: 2014年11月12日 <br/>
	 * 
	 * @author zhulei
	 */
	@SuppressWarnings("rawtypes")
	private void initControls() {
		examineListView = (ExamineListView) findViewById(R.id.hd_hse_scw_app_el);
		examineListView.setIEventListener(eventLst);
		examineListView.setWorkOrder(workOrder);
	}

	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub
		super.initActionBar();
		// 设置导航栏信息
		setCustomActionBar(eventLst, new String[] { IActionBar.TV_BACK,
				IActionBar.IBTN_LEVELTWO_MORE, IActionBar.TV_TITLE });

		setCustomMenuBar(new String[] { IActionBar.ET_SEARCH,
				IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
				IActionBar.ITEM_UPWORKORDER });
		// 设置导航栏标题
		setActionBartitleContent(workOrder.getZypclassname(), true);
		// 未拍照设置实体
		setWorkInfo(workOrder);
		// 设置作业票详细信息弹出框
		setPopDetailWorkerOrer(workOrder);
		// 设置基础信息
		setCurrentNaviTouchEntity(workConfigInfo);
	}

	/**
	 * TODO 初始化审批环节 initApprovalInfo:(). <br/>
	 * date: 2014年11月7日 <br/>
	 * 
	 * @author zhulei
	 */
	public void initApprovalInfo() {
		if (intent == null)
			return;
		asyTask = new BusinessAsyncTask(busAction, auditCallBack);
		chooseCode = intent.getStringExtra(TaskTabulationActivity.CHOOSECODE);
		windowCode = intent.getStringExtra(TaskTabulationActivity.WINDOWCODE);
		jjbActionType = intent
				.getStringExtra(TaskTabulationActivity.JJBACTIONTYPE);
		IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();

		setQueryWorkInfo(quertWorkInfo);

		workConfigInfo = new PDAWorkOrderInfoConfig();
		workConfigInfo.setDycode(windowCode);
		workConfigInfo.setPscode(chooseCode);
		workConfigInfo.setContypedesc(intent
				.getStringExtra(TaskTabulationActivity.JJBCONTYPEDESC));
		workConfigInfo.setSname(intent
				.getStringExtra(TaskTabulationActivity.JJBCONTYPEDESC));

		if (IConfigEncoding.JJB01.equals(chooseCode)) {
			jjbPersonDesc = "作业人";
		} else {
			jjbPersonDesc = "监护人";
		}

		List<WorkApprovalPermission> lstWAP = null;
		try {
			wmrEntity = quertWorkInfo.queryReviewInfo(workOrder,
					workConfigInfo, null);
			lstWAP = quertWorkInfo.queryApprovalPermission(workOrder,
					workConfigInfo, null, null);
			if (lstWAP == null || lstWAP.size() < 1) {
				ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
						"没有" + jjbPersonDesc + "审批环节点！");
			} else {
				examineListView.setData(lstWAP);
			}
		} catch (HDException e) {
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	/**
	 * TODO 保存修改过后的数据 saveModifiedData:(). date: 2014年10月18日
	 * 
	 * @author zhulei
	 */
	public void saveModifiedData(WorkApprovalPermission wPermission)
			throws HDException {
		prgDialog = new ProgressDialog(this, jjbPersonDesc + "审核信息保存");
		prgDialog.show();
		paras.put(WorkOrder.class.getName(), workOrder);
		// 设置审批环节点信息
		paras.put(WorkApprovalPermission.class.getName(), wPermission);
		// 设置措施复查信息
		paras.put(WorkMeasureReview.class.getName(), wmrEntity);

		personRecord = new WorkApprovalPersonRecord();
		personRecord.setTableid(wmrEntity.getZycsfcnum());
		personRecord.setDycode(wPermission.getZylocation());
		personRecord.setTablename("UD_ZYXK_ZYCSFC");
		// 设置作业审批人员记录信息
		paras.put(WorkApprovalPersonRecord.class.getName(), personRecord);
		// 设置PDA配置表信息
		paras.put(PDAWorkOrderInfoConfig.class.getName(), workConfigInfo);

		try {
			asyTask.execute(jjbActionType, paras);
		} catch (HDException e) {
			logger.equals(e.getMessage());
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					"保存信息出错，请联系管理员!");
			throw e;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == IEventType.READCARD_TYPE && data != null) {
			wPermission = (WorkApprovalPermission) data
					.getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
			istpqm = false;
			if (resultCode == IEventType.TPQM_TYPE) {
				istpqm = true;
				image = (Image) data
						.getSerializableExtra(PaintSignatureActivity.IMAGE);
			}
			// 获取刷卡审批，拍照，照片信息
			if (resultCode == IEventType.READCARD_TYPE) {
				image = (Image) data
						.getSerializableExtra(ReadCardExamineActivity.IMAGE);
			}
			if (wPermission != null && wPermission.getPersonid() != null) {
				try {
					saveModifiedData(wPermission);
				} catch (HDException e) {
					ToastUtils.imgToast(GuardianShiftChangeActivity.this,
							R.drawable.hd_hse_common_msg_wrong, e.getMessage());
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		examineListView.destroyBroadcast();
	}

	/**
	 * TODO 关闭当前页签 closeActivity:(). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author zhulei
	 */
	public void closeActivity() {
		SystemApplication.getInstance().popActivity();
	}

	private AbstractAsyncCallBack auditCallBack = new AbstractAsyncCallBack() {
		@Override
		public void start(Bundle msgData) {
			// TODO Auto-generated method stub

		}

		@Override
		public void processing(Bundle msgData) {
			// TODO Auto-generated method stub
			if (msgData.containsKey(actionLsr.CHECKACTION)) {
				prgDialog.showMsg(msgData.getString(actionLsr.CHECKACTION));
			}
		}

		@Override
		public void error(Bundle msgData) {
			// TODO Auto-generated method stub
			if (msgData.containsKey(jjbActionType)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(GuardianShiftChangeActivity.this,
						R.drawable.hd_hse_common_msg_wrong,
						msgData.getString(jjbActionType));
			}
		}

		@Override
		public void end(Bundle msgData) {
			if (istpqm) {
				PaintSignatureActivity.saveImageToDB(false, wPermission,
						mWorkOrder, null, image);
			} else if (image != null && workConfigInfo != null) {
				ImageUtils mImageUtils = new ImageUtils();
				mImageUtils.saveImage(workConfigInfo, wPermission, mWorkOrder,
						image);

			}

			examineListView.setCurrentEntity(wPermission);
			prgDialog.dismiss();
		}
	};

	private IEventListener eventLst = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			if (IEventType.ACTIONBAR_ITEM_UP_CLICK == eventType) {
				// 作业票上传
				upWorkOrder();
			} else if (IEventType.DOWN_WORK_LIST_LOAD == eventType) {
				if (GuardianShiftChangeActivity.this != null) {
					GuardianShiftChangeActivity.this.finish();
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
	private void upWorkOrder() {
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
