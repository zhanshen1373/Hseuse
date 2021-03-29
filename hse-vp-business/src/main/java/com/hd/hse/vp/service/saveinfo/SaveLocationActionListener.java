package com.hd.hse.vp.service.saveinfo;


import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.web.zyxk.UpWZKinfo;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

/**
 * ClassName: SaveLocationActionListener (保存位置卡信息模块)<br/>
 * date: 2015年9月1日 <br/>
 * 
 * @author lxf
 * @version
 */
public class SaveLocationActionListener extends AbstractCheckListener {
	private static Logger logger = LogUtils
			.getLogger(SaveLocationActionListener.class);
	public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();

	@Override
	public Object action(String action, Object... args) throws HDException {
		// 保存虚拟位置信息
		if (IActionType.VP_SAVELOCTION.equals(action)) {
			return saveInfo(args[0]);
		}
		else if(IActionType.VP_LOCATIONSAVELOCTION.equals(action)) {
			PositionCard pCard = (PositionCard) args[0];
			savePCardInfo(pCard);
			return 1;
		}
		return super.action(action);
	}

	private int saveInfo(Object location) throws HDException {
		PositionCard pCard = (PositionCard) location;
		// 保存远程数据
		boolean saves = savePCInfo(pCard);
		if (saves) {
			// 表示保存本地
			//savePCardInfo(pCard);
			sendAsyncSuccessMsg("保存成功", IActionType.VP_SAVELOCTION);
		}
		return 1;
	}

	/**
	 * savePCInfo:(远程保存数据). <br/>
	 * date: 2015年9月1日 <br/>
	 * 
	 * @author lxf
	 * @param location
	 * @return
	 * @throws AppException
	 */
	private boolean savePCInfo(PositionCard pcard) throws AppException {
		try {
			// 远程保存虚拟位置
			sendAsyncProcessingMsg("远程保存位置信息",IActionType.VP_SAVELOCTION);
			UpWZKinfo wzkinfo = new UpWZKinfo();
			wzkinfo.setAsyncTask(getAsyncTask());
			wzkinfo.action(IActionType.VP_SAVELOCTION, pcard);
		} catch (HDException e) {
			logger.error(e.getMessage(), e);
			//throw new AppException(e.getMessage());
			sendAsyncErrMsg(e.getMessage(),IActionType.VP_SAVELOCTION);
			return false;
		}
		return true;
	}

	/**
	 * savePCardInfo:(保存本地信息). <br/>
	 * date: 2015年9月1日 <br/>
	 * 
	 * @author lxf
	 * @param pcard
	 * @throws HDException 
	 */
	private void savePCardInfo(PositionCard pcard) throws HDException {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			//经度纬度、半径
			dao.updateEntity(con, pcard, new String[] { "longitude",
					"latitude", "radiu" });
			con.commit();
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("保存位置信息失败！");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("保存位置信息失败！");
		}finally {
			if (conSrc != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {
					// TODO Auto-generated catch block

				}
			}
		}
	}

}
