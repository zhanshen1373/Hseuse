package com.hd.hse.common.module.phone.business.readcard;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;

/**
 * ClassName: ReadCardManyTimes (多次读卡)<br/>
 * date: 2014年10月21日 <br/>
 * 
 * @author lxf
 * @version
 */
public class ReadCardManyTimes {
	private static Logger logger =LogUtils.getLogger(ReadCardManyTimes.class);
	/**
	 * looptimes:TODO(表示控制循环次数).
	 */
	private final static int LOOPTIMES = 5;

	/**
	 * getReadCardNum:(多次读卡). <br/>
	 * date: 2014年10月21日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public static String getReadCardNum() {
		int currenttimes = 0;
		String cardcode = null;
		while (currenttimes < LOOPTIMES) {
			currenttimes++;
			cardcode = ReadCard.getReadCardNum();
			if (!StringUtils.isEmpty(cardcode)) {
				break;
			}
		}
		return cardcode;
	}
}
