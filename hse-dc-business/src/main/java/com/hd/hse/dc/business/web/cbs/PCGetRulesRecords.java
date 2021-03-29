package com.hd.hse.dc.business.web.cbs;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.GetDataListListener;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: PCGetInAndOutFactoryRecords (查看违章记录)<br/>
 * date: 2014年11月10日 <br/>
 * 
 * @author zhulei
 * @version
 */
public class PCGetRulesRecords extends GetDataListListener {
	private static Logger logger = LogUtils
			.getLogger(PCGetRulesRecords.class);
	private HashMap<String, Object> hashmap = new HashMap<String, Object>();
	private String personid = null;

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		List<SuperEntity> listEntity = null;
		try {
			this.SendMessage(IMessageWhat.PROCESSING, 0, 50, "努力加载中。。。");
			if (args.length > 0) {
				personid = args[0].toString();
			}

			Object obj = super.action(action, args);

			if (obj instanceof List) {
				listEntity = (List<SuperEntity>) obj;
				this.SendMessage(IMessageWhat.END, 99, 100, "成功", listEntity);
			} else {
				this.SendMessage(IMessageWhat.ERROR, 50, 50,
						"获取违章记录失败,请联系管理员.", obj);
			}
		} catch (HDException e) {
			this.SendMessage(IMessageWhat.ERROR, 50, 50, e.getMessage());
		}
		return listEntity;
	}

	@Override
	public String getBusinessType() {
		return PadInterfaceContainers.METHOD_CBS_CBSRULESRECORDS;
	}

	@Override
	public IProcessResultSet getType() {
		return new EntityListResult(getEntityClass());
	}

	@Override
	public Class<?> getEntityClass() {
		return WorkLogEntry.class;
	}

	@Override
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WebConfig getWebConfigParam() {
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

	@Override
	public Object initParam() {
		if (StringUtils.isEmpty(personid)) {
			personid = "";
		}
		hashmap.put(PadInterfaceRequest.KEYICCARD, personid);
		return hashmap;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

}
