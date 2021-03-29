package com.hd.hse.common.module.phone.custom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.activity.ExamineActivity;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class SaveOrExamine<T extends SuperEntity> extends Button {
	private static Logger logger = LogUtils.getLogger(SaveOrExamine.class);
	private WorkOrder workOrder;
	private List<T> examineList;
	private IEventListener iEventLsn;
	private Context context;
	private ProgressDialog prgDialog;
	/**
	 * 基础配置信息
	 */
	SuperEntity workConfigEntity;
	/**
	 * 逐条审批记录
	 */
	SuperEntity record;
	/**
	 * 回调函数
	 */
	IQueryCallEventListener callEventListener;
	/**
	 * asyTask:TODO(后台异步任务).
	 */
	private BusinessAsyncTask asyTask;
	/**
	 * busAction:TODO(后台业务处理对象).
	 */
	private BusinessAction busAction;
	/**
	 * 事件类型
	 */
	private String iActionType;
	/**
	 * paras:TODO(危害/审批环节信息).
	 */
	private HashMap<String, Object> parasMap;

	public SaveOrExamine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.setText("稍等");
	}

	/**
	 * 
	 * Creates a new instance of NavigationBar.
	 * 
	 * @param context
	 * @param attrs
	 */
	public SaveOrExamine(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setText("稍等");
	}

	public SaveOrExamine(Context context) {
		super(context);
		this.context = context;
		this.setText("稍等");
	}

	/**
	 * 初始化控件样式
	 */
	public void initExamineView() {
		initExamineData();
		initView();
	}

	private void initExamineData() {
		IQueryWorkInfo qry = new QueryWorkInfo();
		try {
			examineList = (List<T>) qry.queryApprovalPermission(workOrder,
					workConfigEntity, record, callEventListener);
		} catch (HDException e) {
			logger.error(e.getMessage());
		}
	}

	private void initView() {
		this.setBackground(getResources().getDrawable(
				R.drawable.hd_hse_common_btn_save_style));
		parasMap = new HashMap<String, Object>();
		busAction = new BusinessAction(new CheckControllerActionListener());
		asyTask = new BusinessAsyncTask(busAction);
		if (examineList != null && examineList.size() > 0) {
			this.setText("审核");
			this.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					try {
						iEventLsn.eventProcess(
								IEventType.EXAMINE_EXAMINE_ClICK, parasMap);
						Intent intent = new Intent(context,
								ExamineActivity.class);
						intent.putExtra("examineList",
								(Serializable) examineList);
						intent.putExtra("workOrder", workOrder);
						intent.putExtra("parasMap", parasMap);
						intent.putExtra("iactiontype", iActionType);
						ExamineActivity.iEventLsn = iEventLsn;
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(
								R.anim.hd_hse_common_zoomin,
								R.anim.hd_hse_common_zoomout);
					} catch (HDException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} else {
			this.setText("保存");
			saveModifiedData();
		}
	}

	/**
	 * TODO 保存 saveModifiedData:(). date: 2014年10月23日
	 * 
	 * @author zhulei
	 * @param eventType
	 * @param objects
	 * @throws HDException
	 */
	private void saveModifiedData() {
		busAction = new BusinessAction(new CheckControllerActionListener());
		asyTask = new BusinessAsyncTask(busAction);
		prgDialog = new ProgressDialog(context, "信息保存");
		prgDialog.show();
		try {
			iEventLsn.eventProcess(IEventType.EXAMINE_SAVE_ClICK, parasMap);
			asyTask.execute(iActionType, parasMap);
			iEventLsn.eventProcess(IEventType.EXAMINE_SAVE_ClICK_AFTER);
		} catch (HDException e) {
			logger.equals(e.getMessage());
			ToastUtils.imgToast(context, R.drawable.hd_hse_common_msg_wrong,
					"保存信息出错，请联系管理员!");
		}
	}

	/**
	 * 设置环节点数据
	 * 
	 * @param examineList
	 */
	public void setExamineList(List<T> examineList) {
		this.examineList = examineList;
		initView();
	}

	// 设置接口
	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
	}

	// 设置作业票信息
	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	/**
	 * 设置基础配置信息
	 * 
	 * @param workConfigEntity
	 */
	public void setWorkConfigEntity(SuperEntity workConfigEntity) {
		this.workConfigEntity = workConfigEntity;
	}

	/**
	 * 设置逐条审批记录
	 * 
	 * @param record
	 */
	public void setRecord(SuperEntity record) {
		this.record = record;
	}

	/**
	 * 设置事件类型
	 * 
	 * @param iActionType
	 */
	public void setiActionType(String iActionType) {
		this.iActionType = iActionType;
	}

	/**
	 * 设置回调函数
	 * 
	 * @param callEventListener
	 */
	public void setCallEventListener(IQueryCallEventListener callEventListener) {
		this.callEventListener = callEventListener;
	}

}
