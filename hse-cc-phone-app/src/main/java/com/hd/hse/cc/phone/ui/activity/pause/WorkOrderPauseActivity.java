package com.hd.hse.cc.phone.ui.activity.pause;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.cc.phone.R;
import com.hd.hse.cc.phone.ui.activity.cancel.WorkOrderCanelActivity;
import com.hd.hse.cc.phone.ui.activity.worktask.BaseCloseOrCancelActivity;
import com.hd.hse.cc.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.utils.UtilTools;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * 暂停作业票页签2017/2/24，适用于大连隔夜延期（非连续延期缓冲）
 * 
 * @author yn
 * 
 */
public class WorkOrderPauseActivity extends BaseCloseOrCancelActivity {
	public static Logger logger = LogUtils
			.getLogger(WorkOrderCanelActivity.class);
	/**
	 * actionBar:TODO(标题栏).
	 */
	// private HSEActionBarBack actionBar;
	/**
	 * PAGE_TYPE:TODO(取消界面类型).
	 */
	private final static String PAGE_TYPE = "PGTYPE304";
	/**
	 * workOrder:TODO(作业票).
	 */
	private WorkOrder workOrder;
	/**
	 * workInfoConfig:TODO(配置信息).
	 */
	private PDAWorkOrderInfoConfig workInfoConfig;
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
	// 作业票查询服务
	private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();

	private ProgressDialog prgDialog;
	/**
	 * wap:TODO(审批环节).
	 */
	public WorkApprovalPermission wap;
	/**
	 * wap:TODO(当前审批环节).
	 */
	private WorkApprovalPermission currentWAP;

	private List<WorkApprovalPermission> examineDatas = null;

	private Image image;
	private boolean isSQ; // 是否图片签名
	private boolean isSaved = false;// 是否保存过

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initData();
		setQueryWorkInfo(quertWorkInfo);
		saveBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isSaved) {
					ToastUtils.toast(getApplicationContext(), "作业票已经暂停");
					return;
				}
				try {
					saveModifiedData(null);
				} catch (HDException e) {
					ToastUtils.imgToast(WorkOrderPauseActivity.this,
							R.drawable.hd_hse_common_msg_wrong, e.getMessage());
				}
			}
		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		super.initData();
		initParam();
		// 判断环节点是否上传，若已上传删掉
		handleApprovalPermission();
		initInfo();
		initPage();
	}

	private void handleApprovalPermission() {
		BaseDao dao = new BaseDao();
		String sql = "select * from ud_zyxk_zyspryjl where ud_zyxk_zysqid = '"
				+ workOrder.getUd_zyxk_zysqid()
				+ "' and isupload=1 and type= '" + IConfigEncoding.PAUSE + "'";
		IConnectionSource conSrc = null;
		IConnection connection = null;
		try {
			List<WorkApprovalPersonRecord> mWorkApprovalPersonRecords = (List<WorkApprovalPersonRecord>) dao
					.executeQuery(sql, new EntityListResult(
							WorkApprovalPersonRecord.class));
			if (mWorkApprovalPersonRecords != null
					&& mWorkApprovalPersonRecords.size() > 0) {
				
				conSrc = ConnectionSourceManager.getInstance()
						.getJdbcPoolConSource();
				connection = conSrc.getNonTransConnection();
				WorkApprovalPersonRecord[] workApprovalPersonRecords = mWorkApprovalPersonRecords
						.toArray(new WorkApprovalPersonRecord[mWorkApprovalPersonRecords
								.size()]);
				dao.deleteEntities(connection, workApprovalPersonRecords);
				connection.commit();
			}

		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (connection != null) {
				try {
					conSrc.releaseConnection(connection);
				} catch (SQLException e) {

				}
			}
		}

	}

	@Override
	public void initActionBar() {
		super.initActionBar();
		// 设置导航栏信息
		setCustomActionBar(eventListent, new String[] { IActionBar.TV_BACK,
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
		setCurrentNaviTouchEntity(workInfoConfig);
	}

	/**
	 * TODO 初始化全局对象 initParam:(). date: 2014年10月25日
	 * 
	 * @author zhulei
	 */
	private void initParam() {
		workOrder = (WorkOrder) getIntent().getSerializableExtra(
				TaskTabulationActivity.WORK_ORDER);
		// 后台业务处理对象
		actionLsr = new CheckControllerActionListener();
		busAction = new BusinessAction(new CheckControllerActionListener());
		asyTask = new BusinessAsyncTask(busAction, auditCallBack);
	}

	/**
	 * TODO 初始化作业票详细信息 initTopView:(). date: 2014年10月25日
	 * 
	 * @author zhulei
	 */
	private void initInfo() {
		if (workInfoConfig == null) {
			workInfoConfig = new PDAWorkOrderInfoConfig();
			workInfoConfig.setContype(27);
			workInfoConfig.setPscode(IConfigEncoding.PAUSE);
			workInfoConfig.setContypedesc("暂停");
			workInfoConfig.setSname("暂停");
			workInfoConfig.setDycode(PAGE_TYPE);
			BaseDao dao = new BaseDao();
			String sql = "select ifnull(issavebtn ,0) as issavebtn from ud_zyxk_zysqpdasc where pscode = '"
					+ IConfigEncoding.PAUSE + "'";
			try {
				PDAWorkOrderInfoConfig tempWorkInfoConfig = (PDAWorkOrderInfoConfig) dao
						.executeQuery(sql, new EntityResult(
								PDAWorkOrderInfoConfig.class));
				if (tempWorkInfoConfig != null) {
					workInfoConfig.setIssavebtn(tempWorkInfoConfig
							.getIssavebtn());
				}

			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		try {
			// 查询作业票信息
			workOrder = quertWorkInfo.queryWorkInfo(workOrder, null);
			// 查询审批环节
			examineDatas = quertWorkInfo.queryApprovalPermission(workOrder,
					workInfoConfig, null, null);
		} catch (HDException e) {
			ToastUtils.imgToast(WorkOrderPauseActivity.this,
					R.drawable.hd_hse_common_msg_wrong, e.getMessage());
		}
		setData(examineDatas);

		setiEventLsn(eventListent);
		examineListView.setWorkOrder(workOrder);
		if ((null == examineDatas || examineDatas.size() == 0)
				&& null != workInfoConfig.getIssavebtn()
				&& workInfoConfig.getIssavebtn() == 1) {
			saveBt.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		// 不显示暂停原因
		/*
		 * rsnET.setFocusable(true); rsnTV.setText("取消原因：");
		 */
		rsnET.setVisibility(View.GONE);
		rsnTV.setVisibility(View.GONE);
	}

	/**
	 * TODO 初始化界面控件状态 initPage:(). <br/>
	 * date: 2014年11月14日 <br/>
	 * 
	 * @author zhulei
	 */
	private void initPage() {
		// TODO Auto-generated method stub
		/*
		 * if (examineDatas != null && examineDatas.size() > 0 &&
		 * examineDatas.get(0).getPersondesc() != null) { //
		 * rsnET.setEnabled(false);
		 */
		rsnBtn.setVisibility(View.GONE);
		// }
		// rsnET.setText(workOrder.getReasonqx());
	}

	private IEventListener eventListent = new IEventListener() {

		@SuppressWarnings("unchecked")
		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			switch (eventType) {
			case IEventType.ACTIONBAR_ITEM_UP_CLICK:
				// 上传作业票
				// 作业票上传
				upWorkOrder();
				break;
			case IEventType.DOWN_WORK_LIST_LOAD:
				// 上传作业票成功
				if (WorkOrderPauseActivity.this != null) {
					WorkOrderPauseActivity.this.finish();
				}
				break;
			case IEventType.DOWN_WORK_LIST_SHOW:
				// 上传作业票失败
				break;
			}

		}
	};

	public void saveModifiedData(WorkApprovalPermission workApprovalPermission)
			throws HDException {
		validate();
		HashMap<String, Object> paras = new HashMap<String, Object>();
		// 作业票信息
		workOrder.setIspause(1);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		workOrder.setPausetime(ServerDateManager.getCurrentTime());
		paras.put(WorkOrder.class.getName(), workOrder);
		// 审批环节信息
		if (workApprovalPermission != null) {
			paras.put(WorkApprovalPermission.class.getName(),
					workApprovalPermission);
		}
		paras.put(PDAWorkOrderInfoConfig.class.getName(), workInfoConfig);
		prgDialog = new ProgressDialog(this, "作业暂停信息保存");
		prgDialog.show();
		asyTask.execute(IActionType.ACTION_TYPE_PAUSE, paras);
	}

	/**
	 * TODO 校验字段值 validate:(). date: 2014年10月27日
	 * 
	 * @author zhulei
	 * @throws AppException
	 */
	private void validate() throws AppException {
		// TODO Auto-generated method stub
		/*
		 * String crValue = rsnET.getText().toString(); if
		 * (StringUtils.isEmpty(crValue)) { throw new AppException("请输入取消原因！");
		 * } else {
		 */
		// workOrder.setReasonqx(crValue);
		workOrder.setPausetime(UtilTools.getSysCurrentTime());
		// }
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
			if (msgData.containsKey(IActionType.ACTION_TYPE_CANCEL)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(WorkOrderPauseActivity.this,
						R.drawable.hd_hse_common_msg_wrong,
						msgData.getString(IActionType.ACTION_TYPE_CANCEL));
			}
		}

		@Override
		public void end(Bundle msgData) {
			// TODO Auto-generated method stub
			if (isSQ) {
				PaintSignatureActivity.saveImageToDB(false, currentWAP,
						workOrder, null, image);
			} else if (image != null && workInfoConfig != null) {
				// 保存拍照
				ImageUtils mImageUtils = new ImageUtils();
				mImageUtils.saveImage(workInfoConfig,
						(WorkApprovalPermission) currentWAP, mWorkOrder, image);
			}
			prgDialog.showMsg("成功！");
			initPage();
			examineListView.setCurrentEntity(currentWAP);
			prgDialog.dismiss();
			//
			isSaved = true;
			setResult(BaseListBusActivity.PAUSE_SUCCESS_CODE);
			// ToastUtils.toast(getApplicationContext(), "作业票暂停成功");
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == IEventType.READCARD_TYPE && intent != null) {
			currentWAP = (WorkApprovalPermission) intent
					.getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
			isSQ = false;
			if (resultCode == IEventType.TPQM_TYPE) {
				isSQ = true;
				image = (Image) intent
						.getSerializableExtra(PaintSignatureActivity.IMAGE);
			}
			// 获取刷卡审批，拍照，照片信息
			if (resultCode == IEventType.READCARD_TYPE) {
				image = (Image) intent
						.getSerializableExtra(ReadCardExamineActivity.IMAGE);
			}

			if (currentWAP != null && currentWAP.getPersonid() != null) {
				try {
					saveModifiedData(currentWAP);
				} catch (HDException e) {
					ToastUtils.imgToast(WorkOrderPauseActivity.this,
							R.drawable.hd_hse_common_msg_wrong, e.getMessage());
				}
			}
		}
	}

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
			upzyp.setGetDataSourceListener(eventListent);
		}
		QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
		String[] ud_zyxk_zysqids = null;
		// 判断是否合并审批，并赋值ud_zyxk_zysqids
		if (!getIntent().getBooleanExtra("isJoin", false)) {
			ud_zyxk_zysqids = new String[1];
			ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
		}

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
