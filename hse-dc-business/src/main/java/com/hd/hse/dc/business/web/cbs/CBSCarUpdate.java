package com.hd.hse.dc.business.web.cbs;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.SpecialDownListener;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: CBSCarUpdate (承包商车辆更新。eg：主表是更新，子表是删除的)<br/>
 * date: 2014年8月28日 <br/>
 * 
 * @author Administrator
 * @version
 */
public class CBSCarUpdate extends SpecialDownListener {

	private static HashMap<String, TableDesc> hashRelation=null;
	private static Logger logger = LogUtils.getLogger(CBSCarUpdate.class);
	@Override
	public Object action(String action, Object... args) throws HDException {
		return super.action(action, args);
	}

	/**
	 * 获取下载业务动作类型，默认值是下载承包商车辆表
	 * */
	@Override
	public String getBusinessType() {
		return PadInterfaceContainers.METHOD_CBS_CARDOWN;
	}

	/**
	 * 获取下载业务动表的关联关系，默认值是下载承包商车辆表
	 * */
	@Override
	public HashMap<String, TableDesc> getTableRelation() {
		if(null==hashRelation)
		{
			// 配置下载表的关系
			hashRelation = new HashMap<String, TableDesc>();
			TableDesc tb = new TableDesc();
			tb.setTableName("ud_cbsgl_cl");
			tb.setPrimarykey("carnum");
			hashRelation.put("ud_cbsgl_cl", tb);
			
			tb = new TableDesc();
			tb.setTableName("ud_cbsgl_cljs");
			tb.setPrimarykey("ud_cbsgl_cljsid");
			tb.setForeignkey("carnum");
			tb.setForeignMainTable("ud_cbsgl_cl");
			hashRelation.put("ud_cbsgl_cljs", tb);
		}
		return hashRelation;
	}

	@Override
	public WebConfig getWebConfigParam() {
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

	@Override
	public Object initParam() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty.getSystemProperty().getLoginPerson().getPersonid());
		//hashmap.put(PadInterfaceRequest.KEYDEPTNUM, SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
		String dept=SystemProperty.getSystemProperty().getLoginPerson().getDepartment();
		if(!StringUtils.isEmpty(dept))
		{
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM,dept);
		}
		return hashmap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTableChangeDate() {
		String str = null;
		String sql = "select max(ifnull(changedate,'')) as changedate from ud_cbsgl_cl;";
		// 读取时间戳
		HashMap<String, Object> map;
		try {
			map = (HashMap<String, Object>) Query(sql, new MapResult());
			String key = "changedate";
			if (map.containsKey(key)) {
				if (map.get(key) != null
						&& !StringUtils.isEmpty(map.get(key).toString())) {
					str = map.get(key).toString();
				} else {
					str = "1999-03-26 15:51:14";
				}
			}
		} catch (HDException e) {
			setWritelog("查询时间戳报错：",e);
			str = "1999-03-26 15:51:14";
		}
		return str;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getUpdateMainTable() {
		// TODO Auto-generated method stub
		return "ud_cbsgl_cl";
	}

}