/**
 * Project Name:hse-main-business
 * File Name:QueryMainInfo.java
 * Package Name:com.hd.hse.main.business.main.service
 * Date:2015年2月11日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
/**
 * Project Name:hse-main-business
 * File Name:QueryMainInfo.java
 * Package Name:com.hd.hse.main.business.main.service
 * Date:2015年2月11日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.business.main.service;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.main.business.main.constant.IModelCode;
import com.hd.hse.service.workorder.queryinfo.AsyncQueryHandler;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * ClassName:QueryMainInfo (查询接口的实现).<br/>
 * Date:     2015年2月11日  <br/>
 * @author   zhaofeng
 * @version  
 * @see 	 
 */
/**
 * ClassName: QueryMainInfo (查询接口的实现)<br/>
 * date: 2015年2月11日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class QueryMainInfo implements IQueryMainInfo {

	/**
	 * logger:TODO(日志).
	 */
	private final Logger logger = LogUtils.getLogger(QueryWorkInfo.class);
	/**
	 * Creates a new instance of QueryMainInfo.
	 * 
	 */

	/**
	 * connection:TODO(数据库连接).
	 * 
	 * 防止在同一连接中需要查询时，重新创建连接
	 * 
	 */
	private IConnection connection;
	/**
	 * dao:TODO(数据库操作类).
	 */
	private BaseDao dao;

	/**
	 * 
	 * 默认构造器 Creates a new instance of QueryMainInfo.
	 * 
	 */
	public QueryMainInfo() {
		// TODO Auto-generated constructor stub
		dao = new BaseDao();
	}

	/**
	 * Creates a new instance of QueryMainInfo. 含参构造器
	 * 
	 * @param connection
	 *            传入的数据库连接参数
	 */
	public QueryMainInfo(IConnection connection) {
		this.connection = connection;
		dao = new BaseDao();
	}

	/**
	 * executeQuery:(集中处理数据库连接模式). <br/>
	 * date: 2015年2月11日 <br/>
	 * 
	 * @author zhaofeng
	 * @param sql
	 * @param type
	 * @return
	 * @throws DaoException
	 */
	private Object executeQuery(String sql, IProcessResultSet type)
			throws DaoException {
		if (connection == null) {
			return dao.executeQuery(sql, type);
		} else {
			return dao.executeQuery(connection, sql, type);
		}
	}

	/**
	 * TODO 方法实现-查询模块的作业票数量
	 * 
	 * @see IQueryMainInfo#queryAppModuleZypCount(List,
	 *      com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener)
	 */
	@Override
	public List<AppModule> queryAppModuleZypCount(
			List<AppModule> appModuleList,
			IQueryCallEventListener callEventListener) throws DaoException {
		// TODO Auto-generated method stub
		if (callEventListener == null) {
			// 非异步查询
			try{
			for (int i = 0; i < appModuleList.size(); i++) {
				Map<String, Object> map = (Map) executeQuery(
						buildSql(appModuleList.get(i)), new MapResult());
				if (map == null || map.size() == 0)
					return null;
				appModuleList.get(i).setZypCount(
						Integer.valueOf(map.get("zypcount").toString()));
			}
			}catch(DaoException e){
				logger.error(e.getMessage(), e);
				throw e;
			}
			return appModuleList;
		} else {
			// 异步查询
			AsyncQueryHandler asyncQueryHandler = new AsyncQueryHandler(
					callEventListener);
			asyncQueryHandler
					.execute(this, "queryAppModuleZypCount", new Class<?>[] {
							List.class, IQueryCallEventListener.class },
							new Object[] { appModuleList, null });
		}
		return null;
	}

	/**
	 * buildSql:(创建查询语句). <br/>
	 * date: 2015年2月11日 <br/>
	 * 
	 * @author zhaofeng
	 * @param appModule
	 * @return
	 */
	private String buildSql(AppModule appModule) {
		// 当前时间-暂时不加等待业务进一步确认
		//String dateTime = SystemProperty.getSystemProperty().getSysDateTime();
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) as zypcount from ud_zyxk_zysq zysq  ");
		if(IModelCode.CHECK_MODEL.equals(appModule.getCode())){
			// ----审批模块----
			// 数量为已下载到手持机中状态为“审核中”的票证数量，
			// 点击后弹出界面为按照位置卡分别显示各位置卡下待核查作业票证数量，点击位置卡，提示刷位置卡，刷位置卡后显示该位置卡下需要核查的票证列表。
			sql.append(" where zysq.status='").append(IWorkOrderStatus.INPRG)
					.append("';");
		}else if(IModelCode.REVIEW_MODEL.equals(appModule.getCode())){
			// ----复查模块----
			// 数量为已下载到手持机中，状态为“作业中”，需要进行气体检测且超过检测时效的票证数量，
			// 点击后弹出界面为按照位置卡分别显示各位置卡下待核查作业票证数量，点击位置卡，提示刷位置卡，刷位置卡后显示该位置卡下需要复测的票证列表。
			sql.append(" where zysq.status='").append(IWorkOrderStatus.APPR)
					.append("';");
		}else if(IModelCode.DELAY_MODEL.equals(appModule.getCode())){
			// ----延期模块----
			// 数量为已下载到手持机中，状态为“作业中”，允许延期并可延期数量不能为0的票证数量,
			// 点击后弹出界面为按照位置卡分别显示各位置卡下待延期作业票证数量，点击位置卡，提示刷位置卡，刷位置卡后显示该位置卡下需要延期的票证列表。
			sql.append(" where zysq.status='").append(IWorkOrderStatus.APPR)
					.append("' and zysq.yqcount-zysq.yyqcount>0;");
		}else if(IModelCode.ENDING_MODEL.equals(appModule.getCode())){
			// ----结束模块----
			// 数量为已下载到手持机中状态为“作业中”的票证数量，
			// 点击后弹出界面为按照位置卡分别显示各位置卡下待核查作业票证数量，点击位置卡，提示刷位置卡，刷位置卡后显示该位置卡下需要关闭/取消的票证列表。
			sql.append(" where zysq.status='").append(IWorkOrderStatus.APPR)
					.append("';");
		}else if(IModelCode.UPLOAD_MODEL.equals(appModule.getCode())){
			// ----上传模块----
			// 数量为需要上传的作业票数量，点击后进入票证上传功能，点击后进入票证上传功能。
			sql.append(
					" where ifnull(zysq.spstatus,'') !='' and ifnull(isupload,0)=0 and zysq.spstatus!='")
					.append(IWorkOrderStatus.VIRTUAL_UPLOAD).append("'");
			sql.append(" and zysq.status!='").append(IWorkOrderStatus.INPRG)
					.append("' ;");
		}else if(IModelCode.LOOK_MODEL.equals(appModule.getCode())){
			// ----查看模块----
			// 数量为已下载到手持机中的票证数量，点击后进入查看已下载票证功能。
			sql.append(" where zysq.status <> '")
					.append(IWorkOrderStatus.WAPPR).append("';");
		}
		return sql.toString();
	}

}
