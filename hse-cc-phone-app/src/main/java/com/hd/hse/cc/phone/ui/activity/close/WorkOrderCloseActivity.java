package com.hd.hse.cc.phone.ui.activity.close;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.cc.phone.R;
import com.hd.hse.cc.phone.ui.activity.worktask.BaseCloseOrCancelActivity;
import com.hd.hse.cc.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.PhotoManagerActicity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.custom.AlertDialogListView;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:WorkOrderObsoleteActivity (???????????????).<br/>
 * Date: 2014???10???24??? <br/>
 * 
 * @author zhulei
 * @version
 * @see
 */
public class WorkOrderCloseActivity extends BaseCloseOrCancelActivity {
	private final Logger logger = LogUtils.getLogger(getClass());
	public final static String WORK_ORDER = "workorder";

	/**
	 * PAGE_TYPE:TODO(??????????????????).
	 */
	private final static String PAGE_TYPE = "PGTYPE301";
	private PDAWorkOrderInfoConfig workInfoConfig;
	private ProgressDialog prgDialog;
	// private PopWindowListView popWinLst;
	// ?????????????????????
	private IQueryWorkInfo quertWorkInfo;
	/**
	 * actionBarTitleName:TODO(???????????????).
	 */
	private String actionBarTitleName;

	/**
	 * workOrder:TODO(?????????).
	 */
	private WorkOrder workOrder;

	/**
	 * workOrderList:TODO(??????????????????????????????).
	 */
	private List<WorkOrder> workOrderList;

	/**
	 * busAction:TODO(????????????????????????).
	 */
	private BusinessAction busAction;

	/**
	 * actionLsr:TODO().
	 */
	private AbstractCheckListener actionLsr;

	/**
	 * isJoin:TODO(?????????????????????).
	 */
	private boolean isJoin;

	private Image image;
	private boolean isSQ; // ?????????????????????

	/**
	 * asyTask:TODO(??????????????????).
	 */
	private BusinessAsyncTask asyTask;
	private AlertDialogListView mAlertDialoglst;
	private WorkApprovalPermission curApproveNode;
	private ListView lv;
	private List<Domain> chooseList;
	// ??????????????????????????????????????????
	List<WorkApprovalPermission> datas = null;
	private Boolean isEnable = true;
	/**
	 * ????????????
	 */
	private View moreChooseView;
	/**
	 * selectedGBReason:TODO(??????????????????).
	 */
	private Domain selectedGBReason;

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// // TODO Auto-generated method stub
	// super.onCreate(savedInstanceState);
	// // ???????????????
	// initView();
	// // ???????????????
	// initData();
	// }

	@Override
	public void initActionBar() {
		super.initActionBar();
		// ?????????????????????
		if (isJoin) {
			setCustomActionBar(eventListener, new String[] {
					IActionBar.TV_BACK, IActionBar.TV_TITLE,
					IActionBar.IBTN_LEVELTWO_MORE });
			setCustomMenuBar(new String[] { IActionBar.ITEM_PHOTOGTAPH,
					IActionBar.ITEM_PHOTOMANAGER,
					IActionBar.ITEM_UPWORKORDER });
			setActionBartitleContent("????????????", false);

		} else {
			setCustomActionBar(eventListener, new String[] {
					IActionBar.TV_BACK, IActionBar.IBTN_LEVELTWO_MORE,
					IActionBar.TV_TITLE });
			setCustomMenuBar(new String[] { IActionBar.ET_SEARCH,
					IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
					IActionBar.ITEM_UPWORKORDER });
			setActionBartitleContent(actionBarTitleName, true);
		}
		// ?????????????????????workOrder.getAttribute("zyptype").toString()
		// ?????????????????????
		setWorkInfo(workOrder);
		// ????????????????????????????????????
		setPopDetailWorkerOrer(workOrder);
		// ??????????????????
		setCurrentNaviTouchEntity(workInfoConfig);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		super.initView();
		// ???????????????????????????????????????
		// rsnET.setFocusable(false);
		rsnET.setVisibility(View.GONE);
		rsnBtn.setVisibility(View.GONE);
		// ???????????????????????????
		closeExpTR.setVisibility(View.VISIBLE);
		rsnTV.setText("???????????????");
		rsnSP.setVisibility(View.VISIBLE);
	}

	@Override
	protected void initData() {
		super.initData();
		// ??????????????????
		isJoin = getIntent().getBooleanExtra("isJoin", false);
		workInfoConfig = new PDAWorkOrderInfoConfig();
		workInfoConfig.setPscode(IConfigEncoding.GB);
		workInfoConfig.setContype(8);
		workInfoConfig.setContypedesc("??????");
		workInfoConfig.setSname("??????");
		workInfoConfig.setDycode(PAGE_TYPE);
		// ????????????????????????
		actionLsr = new CheckControllerActionListener();
		busAction = new BusinessAction(actionLsr);
		asyTask = new BusinessAsyncTask(busAction, auditCallBack);
		quertWorkInfo = new QueryWorkInfo();
		// auditRightChk = new CheckApproveRight();
		// ????????????????????????
		workOrder = (WorkOrder) getIntent().getSerializableExtra(WORK_ORDER);
		workOrderList = (List<WorkOrder>) getIntent().getSerializableExtra(
				TaskTabulationActivity.WORK_ORDERS);
		try {
			workOrder = (WorkOrder) quertWorkInfo
					.queryWorkInfo(workOrder, null);
			actionBarTitleName = workOrder.getZypclassname();
			// rsnET.setText(workOrder.getGbtype_desc());
			// rsnET.setHint(workOrder.getGbtype());
			closeExpET.setText(workOrder.getGbsm());
		} catch (HDException e) {
			logger.error(e.getMessage());
		}
		IQueryWorkInfo qry = new QueryWorkInfo();
		try {
			if (isJoin) {
				List<SuperEntity> ls=new ArrayList<>();
				for (int i=0;i<workOrderList.size();i++){
					WorkOrder workOrder = workOrderList.get(i);
					ls.add(workOrder);
				}
				datas = qry.queryApprovalPermission(ls,
						workInfoConfig, null, null);
			} else {
				datas = qry.queryApprovalPermission(workOrder, workInfoConfig,
						null, null);
			}
		} catch (HDException e) {
			logger.error(e.getMessage());
		}
		setData(datas);
		setiEventLsn(eventListener);
		setQueryWorkInfo(quertWorkInfo);
		examineListView.setWorkOrder(workOrder);
		// ???????????????????????????
		rsnSP.setAdapter(new SuperEntityAdapter(initChooseDatas(),
				"description"));
		rsnSP.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				selectedGBReason = initChooseDatas().get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		// ??????UI??????
		if (IWorkOrderStatus.CLOSE.equals(workOrder.getStatus())
				|| workOrder.getGbtype() != null) {
			closeExpET.setEnabled(false);
			rsnBtn.setVisibility(View.GONE);

			String gbtypestr = "";
			for (Domain domain : chooseList) {
				if (domain.getValue().equals(workOrder.getGbtype())) {
					gbtypestr = domain.getDescription();
					break;
				}
			}

			// ????????????????????????????????????????????????
			rsnSP.setAdapter(new ArrayAdapter<String>(
					this,
					R.layout.hd_hse_common_module_phone_spinner_enable_false_style,
					R.id.hd_hse_common_module_phone_spinner_enable_false_style,
					new String[] { gbtypestr }));
			// ????????????
			rsnSP.setEnabled(false);
			isEnable = false;
		}
	}

	WorkApprovalPermission clon_currentAuditNode = null;
	private IEventListener eventListener = new IEventListener() {

		@Override
		public void eventProcess(int eventType, Object... objects)
				throws HDException {
			// TODO Auto-generated method stub
			switch (eventType) {
			// ????????????
			case IEventType.CLOSEORCANCEL_CLICK:
				showMoreChoose();
				break;
			// ????????????
			case IEventType.POPUPWINDOW_CHOICE_MULTIPLEORSINGLE:
				Domain domain = ((Domain) objects[0]);
				rsnET.setText(domain.getDescription());
				rsnET.setHint(domain.getValue());
				workOrder.setGbtype(domain.getDescription());
				break;
			case IEventType.EXAMINE_EXAMINE_ClICK:
				try {
					RelationTableName relationTableName = new RelationTableName();
					relationTableName.setSys_type(IRelativeEncoding.PROMPTQT);
					relationTableName.setSys_fuction(IConfigEncoding.GB);
					relationTableName.setSys_value(workOrder == null ? ""
							: workOrder.getZypclass());
					IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
					boolean isRelation = relativeConfig
							.isHadRelative(relationTableName);
					if (objects[0] instanceof WorkApprovalPermission) {
						curApproveNode = (WorkApprovalPermission) objects[0];
						if (workOrder.getIsqtjc() == 1 && isRelation
								&& curApproveNode.getIsend() == 1) {
							addRecordQtjc(eventType, objects);
						} else {
							auditOnClick(eventType, objects);
						}
					} else if (isRelation) {
						addRecordQtjc(eventType, objects);
					} else {
						auditOnClick(eventType, objects);
					}
				} catch (HDException e) {
					ToastUtils.imgToast(WorkOrderCloseActivity.this,
							R.drawable.hd_hse_common_msg_wrong, e.getMessage());
					throw e;
				}
				break;
			case IEventType.ACTIONBAR_ITEM_UP_CLICK:
				// ???????????????
				// ???????????????
				upWorkOrder();
				break;
			case IEventType.DOWN_WORK_LIST_LOAD:
				// ?????????????????????
				if (WorkOrderCloseActivity.this != null) {
					WorkOrderCloseActivity.this.finish();
				}
				break;
			case IEventType.DOWN_WORK_LIST_SHOW:
				// ?????????????????????
				break;
			}

		}
	};

	/**
	 * TODO ????????????????????????????????? addRecordQtjc:(). <br/>
	 * date: 2014???12???1??? <br/>
	 * 
	 * @author zhulei
	 */
	private void addRecordQtjc(final int eventType, final Object... objects) {
		// TODO Auto-generated method stub
		MessageDialog.Builder builder = new MessageDialog.Builder(
				WorkOrderCloseActivity.this);
		builder.setTitle("??????");
		builder.setMessage("??????????????????????????????????????????????????????");
		builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
				try {
					auditOnClick(eventType, objects);
				} catch (HDException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
					ToastUtils.imgToast(WorkOrderCloseActivity.this,
							R.drawable.hd_hse_common_msg_wrong, e.getMessage());
				}
			}
		});
		builder.setNegativeButton("??????",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.createWarm().show();

	}

	/**
	 * auditOnClick:(?????????????????????). <br/>
	 * date: 2014???10???22??? <br/>
	 * 
	 * @author zhulei
	 * @param eventType
	 * @param objects
	 * @throws HDException
	 */
	private void auditOnClick(int eventType, Object... objects)
			throws HDException {
		// ??????
		// validate();
		// ????????????
		WorkOrder workOrder = getDataFromUI();
		Map<String, Object> mapParam = new HashMap<String, Object>();
		if (isJoin) {
			mapParam.put(WorkOrder.class.getName(), getDatasFromUI());
		} else {
			mapParam.put(WorkOrder.class.getName(), getDataFromUI());
		}
		// if (IEventType.EXAMINE_EXAMINE_ClICK == eventType
		// && objects[0] instanceof WorkApprovalPermission) {
		// ???????????????
		if (objects != null && objects.length > 0) {
			curApproveNode = (WorkApprovalPermission) objects[0];
		}
		mapParam.put(WorkApprovalPermission.class.getName(), curApproveNode);
		// }
		// ????????????
		PDAWorkOrderInfoConfig workConfig = new PDAWorkOrderInfoConfig();
		workConfig.setPscode(IConfigEncoding.GB);
		mapParam.put(PDAWorkOrderInfoConfig.class.getName(), workConfig);
		// ??????????????????
		prgDialog = new ProgressDialog(WorkOrderCloseActivity.this, "??????????????????");
		prgDialog.show();
		if (isJoin) {
			asyTask.execute(IActionType.ACTION_TYPE_MERGECLOSE, mapParam);
		} else {
			asyTask.execute(IActionType.ACTION_TYPE_CLOSE, mapParam);
		}
		// ??????UI??????
		// txtCloseRsn.setEnabled(false);

	}

	private void showMoreChoose() {
		mAlertDialoglst = new AlertDialogListView(WorkOrderCloseActivity.this,
				initChooseDatas(), ListView.CHOICE_MODE_SINGLE);
		mAlertDialoglst.setPopWinTitle("????????????");
		mAlertDialoglst.setOnClickListener(eventListener);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Domain> initChooseDatas() {
		try {
			if (chooseList == null) {
				chooseList = (List) busAction.queryEntities(Domain.class,
						new String[] { "value", "description" },
						"domainid = 'UDZYGBYY' and value != 'zygbyy03'");
				if (chooseList == null || chooseList.size() < 1) {
					ToastUtils.imgToast(this,
							R.drawable.hd_hse_common_msg_wrong,
							"?????????????????????????????????????????????????????????");
				}
			}

		} catch (HDException e) {
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					"?????????????????????????????????????????????????????????");
		}
		return chooseList;
	}

	/**
	 * auditCallBack:TODO(??????????????????????????????????????????).
	 */
	private AbstractAsyncCallBack auditCallBack = new AbstractAsyncCallBack() {

		@Override
		public void start(Bundle msgData) {
			// TODO Auto-generated method stub

		}

		@Override
		public void processing(Bundle msgData) {
			// TODO Auto-generated method stub
			if (msgData.containsKey(IActionType.ACTION_TYPE_CLOSE)) {
				prgDialog.showMsg(msgData
						.getString(IActionType.ACTION_TYPE_CLOSE));
			}
			// ????????????
			if (msgData.containsKey(IActionType.ACTION_TYPE_MERGECLOSE)) {
				prgDialog.showMsg(msgData
						.getString(IActionType.ACTION_TYPE_MERGECLOSE));
			}
		}

		@Override
		public void error(Bundle msgData) {
			// TODO Auto-generated method stub
			if (msgData.containsKey(IActionType.ACTION_TYPE_CLOSE)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(WorkOrderCloseActivity.this,
						R.drawable.hd_hse_common_msg_wrong,
						msgData.getString(IActionType.ACTION_TYPE_CLOSE));
			}
			// ????????????
			if (msgData.containsKey(IActionType.ACTION_TYPE_MERGECLOSE)) {
				prgDialog.dismiss();
				ToastUtils.imgToast(WorkOrderCloseActivity.this,
						R.drawable.hd_hse_common_msg_wrong,
						msgData.getString(IActionType.ACTION_TYPE_MERGECLOSE));
			}

		}

		@Override
		public void end(Bundle msgData) {
			// TODO Auto-generated method stub

			if (isSQ) {
				if (isJoin) {
					List<WorkOrder> orders = new ArrayList<WorkOrder>();
					for (SuperEntity entity : workOrderList) {
						orders.add((WorkOrder) entity);
					}
					PaintSignatureActivity.saveImageToDB(false, curApproveNode,
							null, orders, image);
				} else {
					PaintSignatureActivity.saveImageToDB(false, curApproveNode,
							mWorkOrder, null, image);
				}
			} else if (image != null && workInfoConfig != null) {
				ImageUtils mImageUtils = new ImageUtils();
				if (isJoin) {
					// ????????????????????????
					List<WorkOrder> orders = new ArrayList<WorkOrder>();
					for (SuperEntity entity : workOrderList) {
						orders.add((WorkOrder) entity);
					}
					mImageUtils.saveImage(workInfoConfig, curApproveNode,
							orders);
				} else {
					mImageUtils.saveImage(workInfoConfig,
							(WorkApprovalPermission) curApproveNode,
							mWorkOrder, image);
				}
			}

			prgDialog.dismiss();
			// ??????UI??????
			// rsnET.setEnabled(false);
			// ????????????????????????????????????????????????
			rsnSP.setEnabled(false);
			isEnable = false;
			((BaseAdapter) rsnSP.getAdapter()).notifyDataSetChanged();

			closeExpET.setEnabled(false);
			// txtCloseTime.setText(SystemProperty.getSystemProperty().getSysDateTime());
			// ???????????????????????????
			examineListView.setCurrentEntity(curApproveNode);
			rsnBtn.setVisibility(View.GONE);
		}
	};

	/**
	 * getDataFromUI:(?????????????????????). <br/>
	 * date: 2014???10???18??? <br/>
	 * 
	 * @author zhulei
	 * @return
	 * @throws HDException
	 */
	private WorkOrder getDataFromUI() throws HDException {
		// workOrder.setGbtype(rsnET.getHint().toString());// ????????????
		if (selectedGBReason != null) {
			workOrder.setGbsm(closeExpET.getText().toString());// ????????????
			workOrder
					.setGbtype((String) selectedGBReason.getAttribute("value"));
			workOrder.setGbtype_desc((String) selectedGBReason
					.getAttribute("description"));
		}

		return workOrder;
	}

	private List<SuperEntity> getDatasFromUI() {
		if (selectedGBReason != null) {
			for (SuperEntity workOrder : workOrderList) {
				((WorkOrder) workOrder)
						.setGbsm(closeExpET.getText().toString());
				((WorkOrder) workOrder).setGbtype((String) selectedGBReason
						.getAttribute("value"));
				((WorkOrder) workOrder)
						.setGbtype_desc((String) selectedGBReason
								.getAttribute("description"));
			}
		}
		List<SuperEntity> lsp=new ArrayList<>();
		for (int j=0;j<workOrderList.size();j++){
			lsp.add(workOrderList.get(j));
		}

		return lsp;
	}

	/**
	 * validate:(????????????). <br/>
	 * date: 2014???10???18??? <br/>
	 * 
	 * @author zhulei
	 * @throws AppException
	 */
	private void validate() throws AppException {
		String obsltRsn = rsnET.getText().toString();
		if (StringUtils.isEmpty(obsltRsn)) {
			throw new AppException("????????????????????????");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == IEventType.READCARD_TYPE && intent != null) {
			curApproveNode = (WorkApprovalPermission) intent
					.getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
			isSQ = false;
			if (resultCode == IEventType.TPQM_TYPE) {
				isSQ = true;
				image = (Image) intent
						.getSerializableExtra(PaintSignatureActivity.IMAGE);
			}
			// ??????????????????????????????????????????
			if (resultCode == IEventType.READCARD_TYPE) {
				image = (Image) intent
						.getSerializableExtra(ReadCardExamineActivity.IMAGE);
			}

			if (curApproveNode != null && curApproveNode.getPersonid() != null) {
				try {
					eventListener.eventProcess(
							IEventType.EXAMINE_EXAMINE_ClICK, curApproveNode);
				} catch (HDException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public void touchImages() {
		if (null == queryWorkInfo) {
			logger.equals("?????????setQueryWorkInfo()????????????????????????");
			ToastUtils.toast(getBaseContext(), "?????????setQueryWorkInfo()????????????????????????");
			return;
		} else if (null == mWorkOrder) {
			logger.equals("?????????setWorkInfo()?????????????????????");
			ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
			return;
		}
		Image image = new Image();
		if (!getIntent().getBooleanExtra("isJoin", false)) {
			image.setTableid(mWorkOrder.getUd_zyxk_zysqid());// ?????????
			image.setZysqid(mWorkOrder.getUd_zyxk_zysqid());
			image.setImagepath(SystemProperty.getSystemProperty()
					.getRootDBpath()
					+ File.separator
					+ mWorkOrder.getUd_zyxk_zysqid());// ???????????????
		} else {
			try {
				List<WorkOrder> list = queryWorkInfo
						.queryWorkOrderListForCamera(workOrderList, null);
				if (list != null && list.size() != 0) {
					StringBuilder orderIds = new StringBuilder();
					for (WorkOrder w : list) {
						orderIds.append(w.getUd_zyxk_zysqid()).append(",");
					}
					orderIds.setLength(orderIds.length() - 1);
					image.setTableid(orderIds.toString());
					image.setZysqid(orderIds.toString());
					orderIds.setLength(0);
					for (WorkOrder w : workOrderList) {
						orderIds.append(w.getUd_zyxk_zysqid()).append(",");
					}
					orderIds.setLength(orderIds.length() - 1);
					image.setChildids(orderIds.toString());
				} else {
					ToastUtils.toast(getBaseContext(), "???????????????????????????????????????????????????");
					return;
				}
			} catch (HDException e) {
				logger.error(e.getMessage());
				ToastUtils.toast(getBaseContext(), e.getMessage());
			}
		}
		image.setTablename(mWorkOrder.getDBTableName());// ??????
		image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
				.getPersonid());// ?????????
		image.setCreateusername(SystemProperty.getSystemProperty()
				.getLoginPerson().getPersonid_desc());
		PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
		image.setFuncode(config.getPscode());// ????????????
		image.setContype(config.getContype());
		image.setImagename(config.getContypedesc());
		Intent intent = new Intent(getBaseContext(),
				CameraCaptureActivity.class);
		intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
		startActivity(intent);
	}

	/**
	 * ClassName: SuperEntityAdapter ()<br/>
	 * date: 2015???3???11??? <br/>
	 * 
	 * @author wenlin
	 * @version WorkOrderCloseActivity
	 */
	class SuperEntityAdapter extends BaseAdapter {

		public SuperEntityAdapter(List<Domain> list, String textKey) {
			this.data = list;
			this.key = textKey;
		}

		// ????????? ??????????????? key
		private String key;

		// ??????
		private List<Domain> data;

		/**
		 * data.
		 * 
		 * @return the data
		 */
		public List<Domain> getData() {
			return this.data;
		}

		/**
		 * data.
		 * 
		 * @param data
		 *            the data to set
		 */
		public void setData(List<Domain> data, String textKey) {
			this.data = data;
			this.key = textKey;
		}

		@Override
		public int getCount() {
			if (this.data != null) {
				return this.data.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			if (this.data != null) {
				this.data.get(position);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			SuperEntity item = (SuperEntity) this.data.get(position);

			if (convertView == null) {
				convertView = View.inflate(WorkOrderCloseActivity.this,
						R.layout.hd_hse_common_module_simple_list_item1, null);

				holder = new ViewHolder();
				holder.tv = (TextView) convertView
						.findViewById(android.R.id.text1);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ????????????????????????????????????
			if (!isEnable) {
				holder.tv.setTextColor(getResources().getColor(
						R.color.hd_hse_common_describetext_gray));
			}
			CharSequence text = (CharSequence) item.getAttribute(key);

			holder.tv.setText(text != null ? text : " ");

			return convertView;
		}

		class ViewHolder {
			TextView tv;
		}

	}

	@Override
	public void managerImages() {
		PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
		try {
			if (null == queryWorkInfo) {
				logger.equals("?????????setQueryWorkInfo()????????????????????????");
				ToastUtils.toast(getBaseContext(),
						"?????????setQueryWorkInfo()????????????????????????");

			} else if (null == mWorkOrder) {
				logger.equals("?????????setWorkInfo()?????????????????????");
				ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
				return;
			}
			List<Image> imageList = null;
			if (!getIntent().getBooleanExtra("isJoin", false)) {
				imageList = queryWorkInfo.queryPhoto(mWorkOrder, config);
			} else {
				List<WorkOrder> orders = new ArrayList<WorkOrder>();
				for (SuperEntity entity : workOrderList) {
					orders.add((WorkOrder) entity);
				}
				imageList = queryWorkInfo.queryPhoto(orders, config);
			}
			Intent intent = new Intent(getApplicationContext(),
					PhotoManagerActicity.class);
			intent.putExtra(PhotoManagerActicity.IMAGEENTITY,
					(Serializable) imageList);
			startActivity(intent);
		} catch (HDException e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * upzyp:TODO(???????????????).
	 */
	private UpZYPProgressDialog upzyp = null;

	/**
	 * ???????????????
	 */
	private void upWorkOrder() {
		if (upzyp == null) {
			upzyp = new UpZYPProgressDialog(this);
			upzyp.setGetDataSourceListener(eventListener);
		}
		QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
		String[] ud_zyxk_zysqids = null;
		// ????????????????????????????????????ud_zyxk_zysqids
		if (!getIntent().getBooleanExtra("isJoin", false)) {
			ud_zyxk_zysqids = new String[1];
			ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
		} else {
			// ????????????
			ud_zyxk_zysqids = new String[workOrderList.size()];
			for (int i = 0; i < workOrderList.size(); i++) {
				List<WorkOrder> orders = new ArrayList<WorkOrder>();
				for (SuperEntity entity : workOrderList) {
					orders.add((WorkOrder) entity);
				}
				ud_zyxk_zysqids[i] = orders.get(i).getUd_zyxk_zysqid();
			}
		}

		try {
			List<WorkOrder> data = mQueryWorkOrderInfo
					.queryWorkOrder(ud_zyxk_zysqids);
			if (null != data && data.size() > 0) {
				upzyp.execute("??????", "???????????????????????????????????????.....", "", data);
			} else {
				ToastUtils.toast(getBaseContext(), "????????????????????????");
			}
		} catch (HDException e) {
			e.printStackTrace();
		}

	}
}
