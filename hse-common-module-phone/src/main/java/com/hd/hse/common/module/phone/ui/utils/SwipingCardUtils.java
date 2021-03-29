/**
 * Project Name:hse-common-module
 * File Name:SwipingCardUtils.java
 * Package Name:com.hd.hse.common.module.ui.utils
 * Date:2014年10月25日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.ui.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.os.Handler;
import android.widget.EditText;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.business.readcard.ReadCardManyTimes;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.service.checkright.CheckApproveRight;
import com.hd.hse.service.checkright.ICheckApproveRight;

/**
 * ClassName:SwipingCardUtils (刷卡、权限校验).<br/>
 * Date: 2014年10月25日 <br/>
 * 
 * @author zhulei
 * @version
 * @see
 */
public class SwipingCardUtils {

	private static ICheckApproveRight auditRightChk = new CheckApproveRight();

	/**
	 * swipingCard:(读卡、权限校验。失败后抛出异常；成功后刷新审批组件). <br/>
	 * date: 2015年01月22日 <br/>
	 * 
	 * @author zhulei
	 * @param lsvExamine
	 *            审批环节点组件
	 * @param workOrder
	 *            作业票
	 * @param workApprovalPermission
	 *            当前审批环节点
	 * @throws HDException
	 */
	public static <T extends SuperEntity> void swipingCard(Context context, Handler handler,
			SuperEntity workOrder, T workApprovalPermission) throws HDException {
		swipingCard(context, handler, workOrder, workApprovalPermission, null);
	}

	/**
	 * swipingCard:(读卡、权限校验。失败后抛出异常；成功后刷新审批组件). <br/>
	 * date: 2014年10月25日 <br/>
	 * 
	 * @author lg
	 * @param lsvExamine
	 *            审批环节点组件
	 * @param workOrder
	 *            作业票
	 * @param workApprovalPermission
	 *            当前审批环节点
	 * @throws HDException
	 */
	public static <T extends SuperEntity> void swipingCard( Context context, Handler handler,
			SuperEntity workOrder, T workApprovalPermission, String cardnum)
			throws HDException {
		String cardNum = null;
		if (StringUtils.isEmpty(cardnum)) {
			if (!NFCReader.nfcReadCardEnable()) {
				cardNum = ReadCardManyTimes.getReadCardNum();
			}
		} else {
			cardNum = cardnum;
		}
//		if (IConfigEncoding.ISTEST) {
//			cardNum = "22";
//		}
		if (!StringUtils.isEmpty(cardNum)) {
			PersonCard psnCard = new PersonCard();
			psnCard.setPcardnum(cardNum);
			// 刷卡人权限判断
			auditRightChk.checkApproveRight(context, handler, workApprovalPermission, psnCard,
					workOrder);
		}

	}
	/**
	 * swipingCard:(读卡、权限校验。失败后抛出异常；成功后刷新审批组件). <br/>
	 * date: 2015年09月08日 <br/>
	 * 
	 * @author lxf
	 * @param lsvExamine
	 *            审批环节点组件
	 * @param workOrder
	 *            作业票
	 * @param workApprovalPermission
	 *            当前审批环节点
	 * @throws HDException
	 */
	public static <T extends SuperEntity> void swipingCard(
			List<SuperEntity> workOrder, T workApprovalPermission, String cardnum)
			throws HDException {
		String cardNum = null;
		if (StringUtils.isEmpty(cardnum)) {
			if (!NFCReader.nfcReadCardEnable()) {
				cardNum = ReadCardManyTimes.getReadCardNum();
			}
		} else {
			cardNum = cardnum;
		}
//		if (IConfigEncoding.ISTEST) {
//			cardNum = "22";
//		}
		if (!StringUtils.isEmpty(cardNum)) {
			PersonCard psnCard = new PersonCard();
			psnCard.setPcardnum(cardNum);
			// 刷卡人权限判断
			((CheckApproveRight)auditRightChk).checkApproveRight(workApprovalPermission, psnCard,
					workOrder);
		}

	}
}
