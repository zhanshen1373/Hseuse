package com.hd.hse.service.workorder.checkrules;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import android.os.Message;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

/**
 * ClassName:AbstractCheckListener (业务 动作监听，特殊业务处理).<br/>
 * Date: 2014年8月10日 <br/>
 * 
 * @author ZhangJie
 * @version
 * @see
 * 
 * @pdOid 82d03f32-e067-4953-88e2-24b89ea5c342
 */
public abstract class AbstractCheckListener extends AbstractActionListener {
	protected static Logger logger = LogUtils
			.getLogger(AbstractCheckListener.class);

	/**
	 * connection:TODO().
	 */
	protected IConnection connection = null;
	/**
	 * relativeConfig:TODO(关系检查).
	 */
	public IQueryRelativeConfig relativeConfig = null;
	/**
	 * relationEntity:TODO(关系对象实体).
	 */
	public RelationTableName relationEntity = new RelationTableName();

	public AbstractCheckListener() {

	}

	public AbstractCheckListener(IConnection connection) {
		this.connection = connection;
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.business.listener.AbstractActionListener#action(String,
	 *      Object[])
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub

		return null;
	}

	/**
	 * getMapResult:(). <br/>
	 * date: 2014年10月17日 <br/>
	 * 
	 * @author ZhangJie
	 * @param sql
	 * @return
	 * @throws DaoException
	 * @throws HDException
	 */
	public Map<String, Object> getMapResult(String sql) throws DaoException {
		MapResult type = new MapResult();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) dao.executeQuery(
				connection, sql, type);
		return map;
	}

	/**
	 * getListMapResult:(). <br/>
	 * date: 2014年10月17日 <br/>
	 * 
	 * @author ZhangJie
	 * @param sql
	 * @return
	 * @throws HDException
	 */
	public List<Map<String, Object>> getListMapResult(String sql)
			throws DaoException {
		MapListResult type = new MapListResult();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao
				.executeQuery(connection, sql, type);
		return list;
	}

	/**
	 * sendAsyncProcessingMsg:(登录等待消息). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 * @param msg
	 */
	protected void sendAsyncProcessingMsg(String msg, String action) {
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.PROCESSING;
		message.getData().putString(action, msg);
		getAsyncTask().sendMessage(message);
	}

	/**
	 * sendAsyncSusMsg:(成功后发送消息). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author ZhangJie
	 */
	protected void sendAsyncSuccessMsg(String msg, String action) {
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.END;
		message.getData().putString(action, msg);
		getAsyncTask().sendMessage(message);
	}

	/**
	 * sendAsyncErrMsg:(发送异步错误消息). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author lg
	 * @param msg
	 */
	protected void sendAsyncErrMsg(String msg, String action) {
		Message message = getAsyncTask().obtainMessage();
		message.what = IMessageWhat.ERROR;
		message.getData().putString(action, msg);
		getAsyncTask().sendMessage(message);
	}

	/**
	 * ParseToDate:(字符转时间). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @param datetime
	 * @return
	 * @throws HDException
	 */
	protected Date ParseToDate(String datetime) throws HDException {
		// TODO Auto-generated method stub
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(datetime);
			return date;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new HDException(UNKNOW_ERROR);
		}
	}

	/**
	 * ParseToDate:(字符转时间). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @param datetime
	 * @return
	 * @throws HDException
	 */
	protected Calendar ParseToCalendar(String datetime) throws HDException {
		// TODO Auto-generated method stub
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(datetime);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new HDException(UNKNOW_ERROR);
		}
	}

	/**
	 * 转换参数值类型
	 * 
	 * @throws HDException
	 */
	protected Map<String, Object> objectCast(Object obj) throws HDException {
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> mapParas = ((Map<String, Object>) obj);
			return mapParas;
		} catch (ClassCastException e) {
			logger.error(e.getMessage(), e);
			throw new HDException(UNKNOW_ERROR, e);
		}
	}

	/**
	 * setRelativeInfo:(设置校验关系所需要的信息). <br/>
	 * date: 2014年11月26日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workEntity
	 * @param workConfig
	 */
	public void setRelativeInfo(Object workObj, Object configObj) {
		SuperEntity workEntity = null, workConfig = null;
		if (workObj instanceof SuperEntity)
			workEntity = (SuperEntity) workObj;
		if (configObj instanceof SuperEntity) {
			workConfig = (SuperEntity) configObj;
		} else if (configObj instanceof List) {
			workConfig = (SuperEntity) ((List) configObj).get(0);
		}
		if (StringUtils.isEmpty(relationEntity.getSys_fuction()))
			relationEntity.setSys_fuction((workConfig == null || workConfig
					.getAttribute("pscode") == null) ? "" : workConfig
					.getAttribute("pscode").toString());
		if (StringUtils.isEmpty(relationEntity.getSys_value()))
			relationEntity.setSys_value((workEntity == null || workEntity
					.getAttribute("zypclass") == null) ? "" : workEntity
					.getAttribute("zypclass").toString());
	}

	/**
	 * 是否电火票或大小票强制关联<预留方法> 所有关系的判断方法
	 * 
	 * @throws HDException
	 */
	protected boolean isRelationAction(String relationtype) throws HDException {
		// TODO Auto-generated method stub
		relativeConfig = new QueryRelativeConfig(connection);
		relationEntity.setSys_type(relationtype);
		return relativeConfig.isHadRelative(relationEntity);
	}

	/**
	 * getRelationTableName:(获取配置信息). <br/>
	 * date: 2014年11月26日 <br/>
	 * 
	 * @author zhaofeng
	 * @param relationtype
	 * @return
	 * @throws HDException
	 */
	protected RelationTableName getRelationTableName(String relationtype)
			throws HDException {
		relativeConfig = new QueryRelativeConfig(connection);
		relationEntity.setSys_type(relationtype);
		return relativeConfig.getRelativeObj(relationEntity);
	}
	
	/**
	 * isRelationActionType:(值判断编码，不判断票类型). <br/>
	 * date: 2015年11月26日 <br/>
	 *
	 * @author lxf
	 * @param relationtype
	 * @return
	 * @throws HDException
	 */
	protected boolean isRelationActionType(String relationtype) throws HDException {
		// TODO Auto-generated method stub
		relativeConfig = new QueryRelativeConfig(connection);
		return relativeConfig.isHadRelative(relationtype);
	}

	/** 数据操作类 */
	protected BaseDao dao = new BaseDao();
	/** 验证消息类 */

	/** 静态变量 */
	// 措施确认值
	public final String PRECAUTIONS_VALUE_0 = "0";// 措施确认否
	public final String PRECAUTIONS_VALUE_1 = "1";// 是
	public final String PRECAUTIONS_VALUE_2 = "2";// 不适用
	// 气体检测结果值
	public final String QTJC_ISHG_VALUE_0 = "0";// 不合格
	public final String QTJC_ISHG_VALUE_1 = "1";// 合格

	// 风险分析报告
	public static final String ZYPCLASS_FXBG = "fxbg";// 风险报告
	/** 消息 */
	public final String UNKNOW_ERROR = "系统错误，请联系管理员！";
	/** 动作 */
	public final String CHECKACTION = "CHECKRULES";
	/** 关联票处理动作 */
	public final String RELATION_CEHCK_ACTION_APPR = "APPR";// 签批判断
	public final String RELATION_CEHCK_ACTION_CLOSE = "CLOSE";// 签批判断
	public final String RELATION_CEHCK_ACTION_CANCEL = "CANCEL";// 签批判断
	public final String RELATION_CEHCK_ACTION_DELAY = "DELAY";// 延期

	/** 强制关联类型 */
	public final String RELATION_TYPE_ELECTRICITY_AND_FIRE = "ELECTRICITYANDFIRE";// 电火票关联
	public final String RELATION_TYPE_WORKORDER_AND_TASK = "WORKORDERANDTASK";// 大小票关联
	public final String RELATION_TYPE_TIME_GB = "GB";// 是否启用关闭的延迟时间的

	public void setConnection(IConnection connection) {
		this.connection = connection;
	}

}