package com.hd.hse.dc.business.web.zyxk;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.DownListener;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dc.business.common.weblistener.down.BusDownListener;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

public class DownZYSQQtjc extends BusDownListener {
	private static HashMap<String, TableDesc> hashRelation = null;
	private static Logger logger = LogUtils.getLogger(DownZYSQQtjc.class);
	private StringBuilder strid = new StringBuilder();

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		strid.delete(0, strid.length());
		SuperEntity supe=null;
		if (null != args && args.length > 0) {
			if (args[0] instanceof SuperEntity) {
				supe = (SuperEntity) args[0];
				//strid.append("'");
				strid.append(supe.getAttribute(supe.getPrimaryKey()));
				//strid.append("'");
			}

		}
		Object obj = super.action(action, args);
		// 同步数据成功后更改作业票气体检测状态
		if (obj instanceof HashMap) {

			HashMap<String, Object> hasObj = (HashMap<String, Object>) obj;
			if (hasObj == null || hasObj.size() == 0) {
				throw new HDException("没有可用的气体检测");
			}
			Iterator iter = hasObj.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				if ("1".equalsIgnoreCase(entry.getValue().toString())) {
					upateZYXKQtjcStatus(entry.getKey().toString());
				}
			}
		}
		if(null!=supe){
			String cssavefied=supe.getAttribute("cssavefied")==null?"":supe.getAttribute("cssavefied").toString();
			supe.setAttribute("cssavefied",StringUtils.isEmpty(cssavefied)?IConfigEncoding.GAS_TYPE+"":cssavefied+","+IConfigEncoding.GAS_TYPE);
		}
		return obj;
	}

	/**
	 * upateZYXKQtjcStatus:(更新气体检测状态). <br/>
	 * date: 2014年12月1日 <br/>
	 * 
	 * @author lxf
	 * @param zysqid
	 * @throws HDException 
	 */
	private void upateZYXKQtjcStatus(String qtjcid) throws HDException {
		// 1.判断该票下气体检测是否合格.
		// 2.如果合格更改票的气体检测状态
		int type = IConfigEncoding.GAS_TYPE;
		StringBuilder sbsql = new StringBuilder();
		sbsql.append(
				"update ud_zyxk_zysq set cssavefied=(case when  length(ifnull(cssavefied,''))>0   then cssavefied || ',")
				.append(type)
				.append("' else '")
				.append(type)
				.append("' end) ")
				.append("where ud_zyxk_zysqid in (select ud_zyxk_zysqid from ud_zyxk_qtjc where ifnull(ishg,0)=1 and  ud_zyxk_qtjcid='")
				.append(qtjcid).append("');");
		this.execute(sbsql.toString());

	}

	// 作业票表的关系
	@Override
	public HashMap<String, TableDesc> getTableRelation() {
		// 配置下载表的关系
		if (null == hashRelation) {
			hashRelation = new HashMap<String, TableDesc>();
			// 作业任务
			TableDesc tb = new TableDesc();

			// 气体检测表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_qtjc");
			tb.setPrimarykey("ud_zyxk_qtjcid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_qtjc", tb);
			// 气体检测子表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_qtjcline");
			tb.setPrimarykey("ud_zyxk_qtjclineid");
			tb.setForeignkey("ud_zyxk_qtjcid");
			tb.setForeignMainTable("ud_zyxk_qtjc");
			hashRelation.put("ud_zyxk_qtjcline", tb);

			// 人员审批记录表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zyspryjl");
			tb.setPrimarykey("tableid");
			tb.setForeignkey("ud_zyxk_qtjcid");
			tb.setForeignMainTable("ud_zyxk_qtjc");
			hashRelation.put("ud_zyxk_zyspryjl", tb);

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
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
				.getSystemProperty().getLoginPerson().getPersonid());
		String dept = SystemProperty.getSystemProperty().getLoginPerson()
				.getDepartment();
		if (!StringUtils.isEmpty(dept)) {
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
		}
		if (strid.length() > 0) {
			hashmap.put(PadInterfaceRequest.KEYZYPSTRID, strid.toString());
		} else {
			hashmap.put(PadInterfaceRequest.KEYZYPSTRID, "");
		}
		return hashmap;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getReturnPCAction() {
		// TODO Auto-generated method stub
		return PadInterfaceContainers.METHOD_ZYP_DOWNRETURN;
	}

	@Override
	public HashMap<String, Object> getReturnPCMapInfo() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
				.getSystemProperty().getLoginPerson().getPersonid());
		hashmap.put(PadInterfaceRequest.KEYMAC, SystemProperty
				.getSystemProperty().getPadmac());
		return hashmap;
	}

	@Override
	public void returnPCinfoError(String str) throws HDException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMethodType() {
		// TODO Auto-generated method stub
		return PadInterfaceContainers.METHOD_ZYP_DOWNQTJC;
	}
	@Override
	public boolean getIsThrowException(){
		return true;
	}

}
