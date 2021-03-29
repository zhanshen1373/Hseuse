package com.hd.hse.common.module.phone.business.readcard;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.readcard.AbstractReadCardListener;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.MapResult;

/**
 * ClassName: ReadCard (读卡对象)<br/>
 * date: 2014年9月5日 <br/>
 * 
 * @author lxf
 * @version
 */
public class ReadCard {
	private static Logger logger = LogUtils.getLogger(ReadCard.class);
	private static AbstractReadCardListener readCardlistener;
	private static String drivertype;

	public static String getReadCardNum() {
		String str = null;
		if (null == readCardlistener)
			try {
				if (StringUtils.isEmpty(drivertype)) {
					getDriverType();
				}
				if (StringUtils.isEmpty(drivertype)) {
					logger.error("请配置读卡驱动!");
				} else {
					readCardlistener = (AbstractReadCardListener) Class
							.forName(drivertype).newInstance();
				}
			} catch (InstantiationException e) {
				logger.error("实例化读卡对象失败!", e);

			} catch (IllegalAccessException e) {
				logger.error("实例化读卡对象失败!", e);

			} catch (ClassNotFoundException e) {
				logger.error("实例化读卡对象失败!", e);
			}
		if (null != readCardlistener) {
			try {
				str = readCardlistener.getCardNum();
			} catch (Exception e) {
				logger.error("读取报错", e);
			}
		}
		return str;
	}

	private static void getDriverType() {
		BaseDao dao = new BaseDao();
		String sql = "select sysurl from hse_sys_config where syscode='readcarddriver';";
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, String> result = (HashMap<String, String>) dao
					.executeQuery(sql, new MapResult());
			if (result.containsKey("sysurl")) {
				drivertype = result.get("sysurl");
			}

		} catch (DaoException e) {
			logger.error("读取读卡驱动报错", e);
		}

	}

	/**
	 * setClose:(关闭读卡功能). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public static Object setClose() {
		return readCardlistener.setClose();
	}

}
