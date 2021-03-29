package com.hd.hse.service.checkdata;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;

public class CheckDataConfig {
	private BaseDao dao;
	private static Logger logger = LogUtils.getLogger(CheckDataConfig.class);

	public CheckDataConfig() {
		dao = new BaseDao();
	}

	private void updateCheckdata(String function, int value) {
		String sql = "update checkdata set value = " + value
				+ " where function= '" + function + "'";
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			dao.executeUpdate(con, sql);
			con.commit();
		} catch (SQLException e) {
			logger.error(e);
		} catch (DaoException e) {
			logger.error(e);
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {
				}
			}
		}
	}

	/**
	 * 初始化成功标记
	 */
	public void initDataSuccess() {
		updateCheckdata("initdata", 1);
	}

	/**
	 * 初始化失败标记
	 */
	public void initDataFail() {
		updateCheckdata("initdata", 0);
	}

	/**
	 * 更新成功标记
	 */
	public void updateSuccess() {
		updateCheckdata("update", 1);
	}

	/**
	 * 更新失败标记
	 */
	public void updateFail() {
		updateCheckdata("update", 0);
	}

	private int getValue(String funtion) throws HDException {
		int value = 0;
		String sql = "select value from checkdata where function = '" + funtion
				+ "'";
		try {
			Map<String, Object> map = (Map<String, Object>) dao.executeQuery(
					sql, new MapResult());
			if (map != null && map.containsKey("value")) {
				value=(int) map.get("value");
			} else {
				throw new HDException("没有找到相应字段");
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new HDException(e.toString());
		}
		return value;
	}

	/**
	 * 得到初始化状态
	 * 
	 * @return 1初始化成功，0初始化失败
	 * @throws HDException
	 */
	public int getinitDataValue() throws HDException {
		return getValue("initdata");
	}

	/**
	 * 得到更新状态
	 * 
	 * @return 1更新成功，0更新失败
	 * @throws HDException
	 */
	public int getupdateValue() throws HDException {
		return getValue("update");
	}

}
