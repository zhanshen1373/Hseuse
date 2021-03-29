/**
 * Project Name:hse-osc-phone-app
 * File Name:PersonalProtectEquipment.java
 * Package Name:com.hd.hse.osc.phone.ui.fragment.personalprotect
 * Date:2015年1月27日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.osc.phone.ui.fragment.personalprotect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.adapter.CSQRListAdapter;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.SaveOrExamine;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.custom.ExamineDialog;
import com.hd.hse.common.module.phone.ui.module.measurefragment.MeasureFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragmentExmaine;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * ClassName:PersonalProtectEquipment ().<br/>
 * Date: 2015年1月27日 <br/>
 * 
 * @author flb
 * @version
 * @see 个人防护
 */
@SuppressLint("NewApi")
@SuppressWarnings("all")
public class PersonalProtectEquipment extends NaviFrameFragmentExmaine {

	private static Logger logger = LogUtils
			.getLogger(PersonalProtectEquipment.class);
	private static final String desc = "description";
	private static final String ispadSelect = "ispadselect";
	private static final String isPcSelect = "isselect";

	private List<SuperEntity> listEntities = null;

	private List<SuperEntity> listApp;

	private ListView mListView = null;
	private View inflate = null;
	private TextView saveTV = null;

	private CSQRListAdapter adapter = null;

	@Override
	public void initData(List<Object> data) {

		if (data != null) {
			if (data.get(0) instanceof WorkOrder) {
				setWorkEntity((WorkOrder) data.get(0));
			}
			if (data.get(1) instanceof PDAWorkOrderInfoConfig) {
				setTabPADConfigInfo((SuperEntity) data.get(1));
			}
			if (data.get(2) instanceof List) {
				listEntities = (List<SuperEntity>) data.get(2);
			}
			if (data.get(3) instanceof List) {
				listApp = (List<SuperEntity>) data.get(3);
				setExamineList(listApp);
			}
		}
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		inflate = inflater.inflate(R.layout.hd_hse_fragment_harm_distinguish,
				null);
		mListView = (ListView) inflate
				.findViewById(R.id.hd_hse_fragment_harm_distinguish_lv);
		saveTV = (TextView) inflate
				.findViewById(R.id.hd_hse_fragment_harm_distinguish_save_btn);
		initParam();

		return inflate;
	}

	private boolean ischecksave = true;

	private void initParam() {
		if (getExamineList() != null && getExamineList().size() > 0) {
			saveTV.setText("审  核");
			saveTV.setTag("1");
			ischecksave = true;
		} else {
			saveTV.setText("保  存");
			saveTV.setTag("0");
			ischecksave = false;
		}
		saveTV.setOnClickListener(clickListener);
		adapter = new CSQRListAdapter(mListView, listEntities, desc,
				isPcSelect, ispadSelect);
	}

	@Override
	public void refreshData() {

	}

	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!IWorkOrderStatus.INPRG.equalsIgnoreCase(getWorkEntity()
					.getStatus())) {
				ToastUtils.toast(getActivity(), "非审批中的作业票，不允许数据变更！");
				return;
			}
			if (((TextView) v).getTag().toString().equalsIgnoreCase("1")) {
				// 显示环节点
				ShowExmaineDialog();
			} else {
				// 调用保存
				exmaineSave();
			}
		}
	};

	@Override
	public String getIAtionType() {
		// TODO Auto-generated method stub
		return IActionType.ACTION_TYPE_PPE;
	}

	@Override
	public String getMeasureClassName() {
		// TODO Auto-generated method stub
		return WorkGuardEquipment.class.getName();
	}

	@Override
	public List<SuperEntity> getSaveDatalist() {
		List<SuperEntity> listTempdate = new ArrayList<SuperEntity>();
		List<SuperEntity> listModify=new ArrayList<SuperEntity>();
		// TODO Auto-generated method stub
		if (adapter != null && adapter.getData().size() > 0) {
			for (SuperEntity superEntity : adapter.getData()) {
				Object obj1 = superEntity.getAttribute(isPcSelect);
				//添加PC端自动勾选的数据
				if (obj1 != null && obj1.toString().equalsIgnoreCase("1")) {
					listTempdate.add(superEntity);
				} else {
					//添加PC端勾选的数据
					Object obj = superEntity.getAttribute(ispadSelect);
					if (obj != null && obj.toString().equalsIgnoreCase("1")) {
						listTempdate.add(superEntity);
					}
					else{
						//判断是否修改过的数据
						if(superEntity.isModified()){
							listModify.add(superEntity);
						}
					}
				}
			}
		}
		if (listTempdate.size() == 0 && !isCheckSaveData()) {
			// 此时，不校验。而且没有要保存的数据时,默认添加一条保存记录
			if (adapter.getData() != null && adapter.getData().size() > 0) {
				listTempdate.add(adapter.getData().get(0));
			}
		}
		if(listTempdate.size()>0){
			listTempdate.addAll(listModify);
		}
		// adapter.getModifiedData();
		return listTempdate;
	}

	@Override
	public Map<String, Object> getMapParam() {
		// TODO Auto-generated method stub
		return null;
	}

	int init = 0;
	boolean isRelation;

	@Override
	public boolean isCheckSaveData() {
		if (init == 0) {
			RelationTableName relationTableName = new RelationTableName();
			relationTableName.setSys_type(IRelativeEncoding.ISPPENOSURE);
			relationTableName.setSys_fuction(IConfigEncoding.SP);
			relationTableName.setSys_value(getWorkEntity() == null ? ""
					: getWorkEntity().getZypclass());
			IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
			isRelation = relativeConfig.isHadRelative(relationTableName);
			init++;
		}
		return isRelation;
	}

	@SuppressLint("NewApi")
	@Override
	public void refreshViewControl(Object... objects) {
		// TODO Auto-generated method stub
		if (objects != null) {
			if (objects[0] == null) {
				if (saveTV.getTag().toString().equalsIgnoreCase("0")) {
					((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
				}
			}
			if (objects[0] instanceof WorkApprovalPermission) {
				WorkApprovalPermission workapp = (WorkApprovalPermission) objects[0];
				if (workapp.getIsend() != null && workapp.getIsend() == 1) {
					((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
					adapter.setItemsEnabled(false);
				}
			}
		}
	}

}
