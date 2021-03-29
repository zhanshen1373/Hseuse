/**
 * Project Name:hse-osc-phone-app
 * File Name:HarmDistinguishFragment.java
 * Package Name:com.hd.hse.osc.phone.ui.fragment.harmdistinguish
 * Date:2015年1月26日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.osc.phone.ui.fragment.harmdistinguish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

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
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.custom.ExamineDialog;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.osc.phone.ui.fragment.personalprotect.PersonalProtectEquipment;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * ClassName:HarmDistinguishFragment ().<br/>
 * Date:     2015年1月26日  <br/>
 * @author   flb
 * @version 
 * @see  
 * 
 * 危害识别
 */
@SuppressWarnings("all")
public class HarmDistinguishFragment extends PersonalProtectEquipment {
	@Override
	public String getIAtionType() {
		// TODO Auto-generated method stub
		return IActionType.ACTION_TYPE_HAZ_STRINGARD;
	}
	@Override
	public String getMeasureClassName() {
		// TODO Auto-generated method stub
		return HazardNotify.class.getName();
	}
	@Override
	public boolean isCheckSaveData() {
		RelationTableName relationTableName = new RelationTableName();
		relationTableName.setSys_type(IRelativeEncoding.ISWHNOSURE);
		relationTableName.setSys_fuction(IConfigEncoding.SP);
		relationTableName.setSys_value(getWorkEntity() == null ? ""
				: getWorkEntity().getZypclass());
		IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
		boolean isRelation = relativeConfig
				.isHadRelative(relationTableName);
		return isRelation;
	}
}

