package com.hd.hse.dc.business.web.cbs;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

/**
 * ClassName:GetDataListListener (查询PC数据监听类).<br/>
 * Date: 2014年8月24日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */

public class GetPicInfor extends GainPCDataListener {
	private static Logger logger = LogUtils.getLogger(PCGetRulesRecords.class);
	private HashMap<String, Object> hashmap = new HashMap<String, Object>();
	private String id = null;
	private String appname=null;
	private ArrayList<Image> mImages = new ArrayList<Image>();

	public GetPicInfor(String id,String appname) {
		this.id = id;
		this.appname=appname;
		
	}

	@Override
	public Object action(String action, Object... args) throws HDException {

		super.action(action, args);
		this.sendMessage(IMessageWhat.END, 100, 100, "", mImages);
		return mImages;
	}

	@Override
	public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
		super.afterDataInfo(pcdata, obj);
		//logger.error("pcdata:" + pcdata.toString());
		try {
			JSONObject jsonObject = new JSONObject(pcdata.toString());
			JSONArray mJSONArray = jsonObject.optJSONArray("HSE_SYS_IMAGE");
			if (mJSONArray != null && mJSONArray.length() > 0) {
				for (int i = 0; i < mJSONArray.length(); i++) {
					JSONObject mJSONObject = mJSONArray.optJSONObject(i);
					Image image = new Image();
					image.setId(mJSONObject.optString("id"));
					image.setImagepath(mJSONObject.optString("imagepath"));
					image.setImagename(mJSONObject.optString("imagename"));
					mImages.add(image);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Object initParam() {
		hashmap.put(PadInterfaceRequest.KEYTABLIEID, id);
		hashmap.put(PadInterfaceRequest.KEYAPPNAME, appname);

		return hashmap;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getMethodType() {
		// TODO Auto-generated method stub
		return PadInterfaceContainers.GET_XCJD_PIC;
	}

}
