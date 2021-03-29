/***********************************************************************
 * Module:  ControllerOfCheck.java
 * Author:  Administrator
 * Purpose: Defines the Class ControllerOfCheck
 ***********************************************************************/

package com.hd.hse.service.workorder.checkrules;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * ClassName: CheckControllerActionListener (校验类总调度，业务动作唯一入口)<br/>
 * date: 2014年10月18日 <br/>
 * 
 * @author ZhangJie
 * @version
 */
public class CheckControllerActionListener extends AbstractCheckListener {

	Map map = null;
	/**
	 * TODO
	 * 
	 * @param args
	 *            以类名为key Object[]为value的参数
	 * @see AbstractCheckListener#action(String,
	 *      Object[])
	 */
	@SuppressWarnings("unchecked")
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		IConnectionSource connectionSource = null;
		try {
			connectionSource = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			connection = connectionSource.getConnection();
			// 克隆缓存数据，便于任务执行失败后回滚
			Map<String, Object> mapClone = new HashMap<String, Object>();
			List<Map<String, Object>> list = readCfg(action);
			map = objectCast(args[0]);
//			setRelativeInfo(map.get(WorkOrder.class.getName()), map.get(PDAWorkOrderInfoConfig.class.getName()));
			doActions(action, list, connection, args[0], mapClone);
			if (args[0] instanceof Map) {
				cloneToCache((Map<String, Object>) args[0], mapClone);
			}
			connection.commit();
			String msg = "操作已完成";
			sendAsyncSuccessMsg(msg, action);
		} catch (SQLException e) {
			logger.error("业务操作【" + action + "】：" + e.getMessage(), e);
			sendAsyncErrMsg(e.getMessage(), action);
		} catch (HDException e) {
			logger.error("业务操作【" + action + "】：" + e.getMessage(), e);
			sendAsyncErrMsg(e.getMessage(), action);
		} catch (Exception e) {
			logger.error("业务操作【" + action + "】：" + e.getMessage(), e);
			sendAsyncErrMsg(UNKNOW_ERROR, action);
		} finally {
			if (connectionSource != null) {
				try {
					connectionSource.releaseConnection(connection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}

		return null;
	}

	/**
	 * readCfg:(读取配置获得校验动作列表). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @param action
	 * @return
	 */
	public List<Map<String, Object>> readCfg(String action) throws HDException {
		String sql = "select classname,classnamedesc from checkActionCfg where actiontype='"
				+ action + "' order by px asc";
		List<Map<String, Object>> list = getListMapResult(sql);
		return list;
	}

	/**
	 * doActions:(按顺序执行注册动作类). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @return
	 */
	public void doActions(String action, List<Map<String, Object>> list,
			IConnection connection, Object... args) throws HDException {
		// TODO: implement
		for (Map<String, Object> map : list) {
			// 类名
			String classname = map.get("classname").toString();
			String point = map.get("classnamedesc").toString();
			try {
				String msg = "正在处理业务" + "[" + point + "]";
				sendAsyncProcessingMsg(msg, action);
				AbstractActionListener actionLsr = newActionInstance(
						connection, classname);
				actionLsr.action(action, args);
			} catch (HDException e) {
				logger.error(e.getMessage(), e);
				throw e;
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
				throw new HDException(UNKNOW_ERROR);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new HDException(UNKNOW_ERROR);
			}
		}
	}

	/**
	 * newActionInstance:(创建带数据库连接的后台处理对象). <br/>
	 * date: 2014年10月21日 <br/>
	 * 
	 * @author lg
	 * @param connection
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private AbstractActionListener newActionInstance(IConnection connection,
			String clazz) throws Exception {
		AbstractCheckListener actionLsr = (AbstractCheckListener) Class
				.forName(clazz).newInstance();
		actionLsr.setConnection(connection);
		actionLsr.setRelativeInfo(map.get(WorkOrder.class.getName()), map.get(PDAWorkOrderInfoConfig.class.getName()));
		return actionLsr;
	}

	/**
	 * cloneToCache:(克隆2缓存，目前只处理单个对象【作业票、审批环节点】). <br/>
	 * date: 2014年10月24日 <br/>
	 * 
	 * @author lg
	 * @param mapCache
	 * @param mapClone
	 */
	private void cloneToCache(Map<String, Object> mapCache,
			Map<String, Object> mapClone) {
		if (mapClone != null && !mapClone.isEmpty()) {
			Set<String> setClazz = mapClone.keySet();
			for (String clazz : setClazz) {
				Object cloneParam = mapClone.get(clazz);
				if (cloneParam instanceof SuperEntity) {
					SuperEntity cacheParam = (SuperEntity) mapCache.get(clazz);
					cacheParam.cloneProperties((SuperEntity) cloneParam);
				}
			}
		}
	}
}
