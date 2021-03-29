/**
 * Project Name:hse-entity-service
 * File Name:PushMessage.java
 * Package Name:com.hd.hse.entity.common
 * Date:2015年9月17日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.table.DBTable;

/**
 * ClassName:PushMessage ().<br/>
 * Date: 2015年9月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
@SuppressWarnings("serial")
@DBTable(tableName = "ud_zyxk_xxjl")
public class PushMessage extends SuperEntity {
	/**
	 * 记录ID
	 */
	private int ud_zyxk_xxjlid;
	/**
	 * 作业申请ID
	 */
	private String ud_zyxk_zysqid;
	/**
	 * 消息类型
	 */
	private String xxlx;
	/**
	 * 功能编码
	 */
	private String gnbm;
	/**
	 * 消息服务端发送时间
	 */
	private String fssj;
	/**
	 * 消息内容
	 */
	private String xxnr;

	/**
	 * isOpen:TODO(是否打开).
	 */
	private boolean isopen;
	

	public static final String MSGTYPE_NOTIFICATION = "msgType01";
	public static final String MSGTYPE_WORK = "msgType02";
	
	/**
	 * GNBM_COLSE:TODO(功能编码：到期关闭).
	 */
	public static final String GNBM_COLSE = "gnbm_close";
	/**
	 * GNMB_QTJC:TODO(功能编码：气体检测).
	 */
	public static final String GNBM_QTJC = "gnbm_qtjc";
	
	/**
	 * parse:(消息解析方法). <br/>
	 * date: 2015年9月24日 <br/>
	 *
	 * @author zhaofeng
	 * @return
	 */
	public static PushMessage parse(JSONObject jsonItem) {
		PushMessage msg = new PushMessage();
		try {
			if (jsonItem.has("ALERT_CODE")) {
				msg.setUd_zyxk_xxjlid(jsonItem.getInt("ALERT_CODE"));
			}
			if (jsonItem.has("DATA_CODE")) {
				msg.setUd_zyxk_zysqid(jsonItem.getString("DATA_CODE"));
			}
			if (jsonItem.has("ALERT_TYPE")) {
				String xxlx = jsonItem.getString("ALERT_TYPE");
				if (xxlx.equals("TZ01")) {
					msg.setXxlx(MSGTYPE_NOTIFICATION);
				} else {
					msg.setXxlx(MSGTYPE_WORK);
				}
			}
			if (jsonItem.has("SEND_TYPE")) {
				String gnbm = jsonItem.getString("SEND_TYPE");
				if (gnbm.equals("QTJC")) {
					msg.setGnbm(GNBM_QTJC);
				} else if (gnbm.equals("DQGB")) {
					msg.setGnbm(GNBM_COLSE);
				}
			}
			if (jsonItem.has("ALERT_SEND_TIME")) {
				msg.setFssj(jsonItem.getString("ALERT_SEND_TIME"));
			}
			if (jsonItem.has("ALERT_TITLE")) {
				msg.setXxnr(jsonItem.getString("ALERT_TITLE"));
			}
			if (jsonItem.has("ALERT_FLAG")) {
				int isOpen = jsonItem.getInt("ALERT_FLAG");
				msg.isopen = isOpen > 1;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return msg;
	}
	
	/**
	 * parseAll:(消息列表解析方法). <br/>
	 * date: 2015年9月24日 <br/>
	 *
	 * @author zhaofeng
	 * @return
	 */
	public static List<PushMessage> parseAll(String jsonStr) {
		List<PushMessage> msgs = new ArrayList<PushMessage>();
		try {
			JSONObject json = new JSONObject(jsonStr);
			JSONArray jsonArray = json.getJSONArray("UD_PAD_ALERT");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonItem = jsonArray.getJSONObject(i);
				PushMessage msg = parse(jsonItem);
				msgs.add(msg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return msgs;
	}

	public int getUd_zyxk_xxjlid() {
		return ud_zyxk_xxjlid;
	}
	public void setUd_zyxk_xxjlid(int ud_zyxk_xxjlid) {
		this.ud_zyxk_xxjlid = ud_zyxk_xxjlid;
	}
	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}
	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}
	public String getXxlx() {
		return xxlx;
	}
	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	public String getGnbm() {
		return gnbm;
	}
	public void setGnbm(String gnbm) {
		this.gnbm = gnbm;
	}
	public String getFssj() {
		return fssj;
	}
	public void setFssj(String fssj) {
		this.fssj = fssj;
	}
	public String getXxnr() {
		return xxnr;
	}
	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public boolean isIsopen() {
		return isopen;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}
}
