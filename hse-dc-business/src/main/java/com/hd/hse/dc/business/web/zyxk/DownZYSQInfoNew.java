package com.hd.hse.dc.business.web.zyxk;

import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.BusDownListener;
import com.hd.hse.dc.business.listener.basicdata.LoginDataUpdate;
import com.hd.hse.dc.business.listener.common.CommonSql;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class DownZYSQInfoNew extends BusDownListener {
	private static HashMap<String, TableDesc> hashRelation = null;
	private static Logger logger = LogUtils.getLogger(DownZYSQInfoNew.class);
	private StringBuilder strid = new StringBuilder();

	
	@Override
	public Object action(String action, Object... args) throws HDException {
		strid.delete(0,strid.length());
		if (null != args) {
			for (Object ob : args) {
				if (ob instanceof List) {
					@SuppressWarnings("unchecked")
					List<SuperEntity> listEntity = (List<SuperEntity>) ob;
					if (null != listEntity && listEntity.size() > 0) {
						for (SuperEntity supe : listEntity) {
							strid.append("'");
							strid.append(supe.getAttribute(supe.getPrimaryKey()));
							strid.append("',");
						}
						if (null != strid && strid.length() > 0) {
							strid.delete(strid.length() - 1, strid.length());
						}
					}
					break;
				}
			}
		}
		//先删除本地存在的
		deleteTrashinfo(strid.toString());
		//strid.append("'582666','582726','582727'");
		Object obj=super.action(action, args);
		LoginDataUpdate baseupdate=new LoginDataUpdate();
//		baseupdate.setAsyncTask(getAsyncTask());
		baseupdate.action("");
		return obj;
	}
	
	/**
	 * deleteTrashinfo:(删除垃圾数据). <br/>
	 * date: 2014年9月25日 <br/>
	 * 当票的状态是“CLOSE”或“CANCEL”或“NULLIFY”或“WAPPR”或“CQCLOSE”时即“关闭”，“取消”，“作废”，“退回”，“
	 * 超期关闭”删除所有该票信息 应该是STATUS='CQCLOSE' spstatus='OVERCLOSE'超期关闭/
	 * STATUS='NULLIFY' spstatus='OVERSTATE'自动作废
	 * 
	 * @author lxf
	 * @throws HDException
	 */
	private void deleteTrashinfo(String zysqid) throws HDException {

		List<String> listsql;
		// 取得要处理的id 以逗号分隔.
		if (StringUtils.isEmpty(zysqid)) {
			// 没有数据返回
			return;
		}
		listsql = CommonSql.getExecListSql(zysqid, "delete");
		if (null != listsql && listsql.size() > 0) {

			this.execute(listsql);
		}

	}
	@Override
	public HashMap<String, TableDesc> getTableRelation() {
		// TODO Auto-generated method stub
		// 配置下载表的关系
		if (null == hashRelation) {
			hashRelation = new HashMap<String, TableDesc>();
			// 作业任务
			TableDesc tb = new TableDesc();
			tb.setTableName("ud_zyxk_worktask");
			tb.setPrimarykey("ud_zyxk_worktaskid");
			hashRelation.put("ud_zyxk_worktask", tb);

			// 作业申请表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zysq");
			tb.setPrimarykey("ud_zyxk_zysqid");
			tb.setForeignkey("ud_zyxk_worktaskid");
			tb.setForeignMainTable("ud_zyxk_worktask");
			hashRelation.put("ud_zyxk_zysq", tb);
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
			// 措施表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zysq2precaution");
			tb.setPrimarykey("ud_zyxk_zysq2precautionid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_zysq2precaution", tb);
			// 个人防护装备表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zysq2ud_zyxk_grfhzb");
			tb.setPrimarykey("ud_zyxk_zysq2ud_zyxk_grfhzbid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_zysq2ud_zyxk_grfhzb", tb);
			// 危害识别表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zysq2hazard");
			tb.setPrimarykey("hazardid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_zysq2hazard", tb);
			// 挖掘隐藏措施 UD_ZYXK_YCSSQK
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_ycssqk");
			tb.setPrimarykey("ud_zyxk_ycssqkid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_ycssqk", tb);
			// 吊装作业 UD_ZYXK_DZZY
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_dzzy");
			tb.setPrimarykey("ud_zyxk_dzzyid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_dzzy", tb);
			// 措施复查表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zycsfc");
			tb.setPrimarykey("zysqnum");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_zycsfc", tb);
			// 措施复查子表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_zycsfcline");
			tb.setPrimarykey("zycsfclinenum");
			tb.setForeignkey("zysqnum");
			tb.setForeignMainTable("ud_zyxk_zycsfc");
			hashRelation.put("ud_zyxk_zycsfcline", tb);
			// 临时用电子表
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_lsydzy");
			tb.setPrimarykey("ud_zyxk_lsydzyid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_lsydzy", tb);
			// 用电设备清单
			tb = new TableDesc();
			tb.setTableName("ud_zyxk_ydasset");
			tb.setPrimarykey("ud_zyxk_ydassetid");
			tb.setForeignkey("ud_zyxk_zysqid");
			tb.setForeignMainTable("ud_zyxk_zysq");
			hashRelation.put("ud_zyxk_ydasset", tb);

		}
		return hashRelation;
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
		deleteTrashinfo(str);
	}

	@Override
	public Object initParam() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
				.getSystemProperty().getLoginPerson().getPersonid());
		// hashmap.put(PadInterfaceRequest.KEYDEPTNUM,
		// SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
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
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getMethodType() {
		// TODO Auto-generated method stub
		return PadInterfaceContainers.METHOD_ZYP_DOWN;
	}

}
