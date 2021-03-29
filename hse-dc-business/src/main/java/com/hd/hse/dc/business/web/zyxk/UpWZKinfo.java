package com.hd.hse.dc.business.web.zyxk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.up.UpListenerNew1;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: UpWZKinfo (上传位置卡信息)<br/>
 * date: 2015年8月26日 <br/>
 * 
 * @author lxf
 * @version
 */
public class UpWZKinfo extends UpListenerNew1 {
	private static Logger logger = LogUtils.getLogger(UpWZKinfo.class);
	private static List<TableDesc> listTableRelation = null;
	private StringBuilder strid = new StringBuilder();
	SuperEntity supetemp;

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		try {
			strid.delete(0, strid.length());
			if (null != args) {
				for (Object ob : args) {
					if (ob instanceof List) {
						@SuppressWarnings("unchecked")
						List<SuperEntity> listEntity = (List<SuperEntity>) ob;
						if (null != listEntity && listEntity.size() > 0) {
							for (SuperEntity supe : listEntity) {
								strid.append("'");
								strid.append(supe.getAttribute(supe
										.getPrimaryKey()));
								strid.append("',");
								supetemp = supe;
							}
							if (null != strid && strid.length() > 0) {
								strid.delete(strid.length() - 1, strid.length());
							}
						}
						break;
					} else if (args[0] instanceof SuperEntity) {
						supetemp = (SuperEntity) args[0];
						strid.append("'");
						strid.append(supetemp.getAttribute(supetemp
								.getPrimaryKey()));
						strid.append("'");
					} else if (args[0] instanceof String) {
						strid.append("'");
						strid.append(args[0].toString());
						strid.append("'");
					}
				}
			}
			// 表示上传的id
			if (StringUtils.isEmpty(strid.toString())) {
				throw new HDException("没有传入上传的数据");
			}
			super.action(action, args);
			return 1;
		} catch (HDException e) {
			getLogger().error(e.getMessage(), e);
			// this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
			throw new HDException(e.getMessage());
		}
	}

	@Override
	public void afterFileInfo(Object pcdata, Object... obj) throws HDException {
		// 上传文件成功后执行的动作
	}

	@Override
	public List<TableDesc> getRelation() {
		if (null == listTableRelation) {
			listTableRelation = new ArrayList<TableDesc>();
			TableDesc tb = null;
			// 位置卡
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_wzk");
			tb.setPrimarykey("ud_zyxk_wzkid");
			listTableRelation.add(tb);
		}
		return listTableRelation;
	}

	@Override
	public HashMap<String, String> getUpDateSql() {
		StringBuilder sbsql = new StringBuilder();
		HashMap<String, String> hashsql = new HashMap<String, String>();

		if (supetemp != null) {
			sbsql.append("select ud_zyxk_wzkid,'")
					.append(supetemp.getAttribute("longitude"))
					.append("' as longitude,'")
					.append(supetemp.getAttribute("latitude"))
					.append("' as latitude,'")
					.append(supetemp.getAttribute("radiu"))
					.append("' as radiu,'update' as v_action from ud_zyxk_wzk where ud_zyxk_wzkid in(")
					.append(strid.toString()).append(")");
		} else {
			// 岗位表信息
			sbsql.append(
					"select ud_zyxk_wzkid,longitude,latitude,radiu,'update' as v_action from ud_zyxk_wzk where ud_zyxk_wzkid in(")
					.append(strid.toString()).append(")");
		}
		hashsql.put("ud_zyxk_wzk", sbsql.toString());
		sbsql.delete(0, sbsql.length());

		return hashsql;
	}

	@Override
	public void afterUploadDataError(String error) throws HDException {

	}

	@Override
	public void afterUploadDataInfo(Object pcdata) throws HDException {

	}

	@Override
	public List<HashMap<String, HashMap<String, Object>>> getFilePathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object initParam() throws HDException {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
				.getSystemProperty().getLoginPerson().getPersonid());
		String dept = SystemProperty.getSystemProperty().getLoginPerson()
				.getDepartment();
		if (!StringUtils.isEmpty(dept)) {
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
		}
		return hashmap;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.METHOD_COMMON_UPTABLE;
	}

}
