/**
 * Project Name:hse-entity-service
 * File Name:UtilTools.java
 * Package Name:com.hd.hse.service.workorder.queryinfo
 * Date:2014年10月17日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.utils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ClassName: UtilTools (工具类)<br/>
 * date: 2014年10月17日 <br/>
 *
 * @author zhaofeng
 * @version
 */
public class UtilTools {
	protected static Logger logger = LogUtils
			.getLogger(AbstractCheckListener.class);

	/**
	 * ConvertToSqlString:(将拼接起来的字符串，转化成sql类型的). <br/>
	 * date: 2014年10月17日 <br/>
	 *
	 * @author zhaofeng
	 * @param str
	 * @return
	 */
	public static String convertToSqlString(String str) {
		if (StringUtils.isEmpty(str))
			return "()";
		StringBuilder retsb = new StringBuilder();
		retsb.append("(");
		String[] strSplit = str.split(",");
		for (String strp : strSplit) {
			retsb.append("'").append(strp.split(":")[0]).append("',");
		}
		if (retsb.length() > 1) {
			retsb.delete(retsb.length() - 1, retsb.length());
		}
		retsb.append(")");
		return retsb.toString();
	}

	/**
	 * judgeMapValue:(校验map中是否包含该对象，并且返回). <br/>
	 * date: 2014年10月23日 <br/>
	 *
	 * @author zhaofeng
	 * @param map
	 * @param classz
	 * @param isMust
	 * @return
	 * @throws HDException
	 */
	public static Object judgeMapValue(Map<String, Object> map,
			Class<?> classz, boolean isMust) throws HDException {
		if (isMust) {
			if (!map.containsKey(classz.getName()))
				throw new HDException("系统错误，请联系管理员！");
			Object object = map.get(classz.getName());
			if (!classz.isInstance(object))
				throw new HDException("系统错误，请联系管理员！");
			return object;
		} else {
			if (!map.containsKey(classz.getName()))
				return null;
			Object object = map.get(classz.getName());
			if (!classz.isInstance(object))
				return null;
			return object;
		}
	}

	/**
	 * cloneMap:(将初始化map中的数据，clone到另一个map中去). <br/>
	 * date: 2014年10月24日 <br/>
	 *
	 * @author zhaofeng
	 * @param map
	 * @param cloneMap
	 * @param classz
	 */
	public static void cloneMap(Map<String, SuperEntity> map,
			Map<String, SuperEntity> cloneMap, Class<?> classz) {
		SuperEntity object = map.get(classz.getName());
		try {
			SuperEntity clone = object.clone();
			cloneMap.put(classz.getName(), clone);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * cloneList:(克隆list结合对象). <br/>
	 * date: 2015年3月4日 <br/>
	 *
	 * @author lxf
	 * @param list
	 * @param cloneList
	 */
	public static void cloneList(List<SuperEntity> list,
			List<SuperEntity> cloneList) {
		try {
			for (SuperEntity superEntity : list) {
				cloneList.add(superEntity.clone());
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * judgeMapListValue:(校验map中是否包含该对象的list集合，并且返回). <br/>
	 * date: 2014年10月23日 <br/>
	 *
	 * @author zhaofeng
	 * @param map
	 * @param classz
	 * @param isMust
	 * @return
	 * @throws HDException
	 */
	public static Object judgeMapListValue(Map<String, Object> map,
			Class<?> classz, boolean isMust) throws HDException {
		if (isMust) {
			if (!map.containsKey(classz.getName()))
				throw new HDException("系统错误，请联系管理员！");
			Object object = map.get(classz.getName());
			if (object instanceof List) {
				List list = (List) object;
				if (list.size() == 0)
					throw new HDException("系统错误，请联系管理员！");
				for (int i = 0; i < list.size(); i++) {
					if (!classz.isInstance(list.get(i)))
						throw new HDException("系统错误，请联系管理员！");
				}
			} else {
				throw new HDException("系统错误，请联系管理员！");
			}
			return object;
		} else {
			if (!map.containsKey(classz.getName()))
				return null;
			Object object = map.get(classz.getName());
			if (object instanceof List) {
				List list = (List) object;
				if (list.size() == 0)
					return null;
				for (int i = 0; i < list.size(); i++) {
					if (!classz.isInstance(list.get(i)))
						return null;
				}
			} else {
				return null;
			}
			return object;
		}
	}

	/**
	 * isRangeIn:(判断范围). <br/>
	 * date: 2014年10月30日 <br/>
	 *
	 * @author zhaofeng
	 * @param maxValue
	 * @param value
	 * @param minValue
	 * @param isbj
	 * @return
	 */
	public static boolean isRangeIn(String maxValue, float value,
			String minValue, boolean isbj) {
		if (isbj) {
			if (StringUtils.isEmpty(maxValue) && !StringUtils.isEmpty(minValue)) {
				// 没有上限
				if (Float.valueOf(minValue) <= value) {
					return true;
				}
			} else if (!StringUtils.isEmpty(maxValue)
					&& StringUtils.isEmpty(minValue)) {
				// 没有下线
				if (value <= Float.valueOf(maxValue)) {
					return true;
				}
			} else if (!StringUtils.isEmpty(maxValue)
					&& !StringUtils.isEmpty(minValue)) {
				// 正常判断，有上下线
				if (value <= Float.valueOf(maxValue)
						&& Float.valueOf(minValue) <= value) {
					return true;
				}
			} else if (StringUtils.isEmpty(maxValue)
					&& StringUtils.isEmpty(minValue)) {
				// 没有上下线
				return true;
			}

		} else {
			if (StringUtils.isEmpty(maxValue) && !StringUtils.isEmpty(minValue)) {
				// 没有上限
				if (Float.valueOf(minValue) < value) {
					return true;
				}
			} else if (!StringUtils.isEmpty(maxValue)
					&& StringUtils.isEmpty(minValue)) {
				// 没有下线
				if (value < Float.valueOf(maxValue)) {
					return true;
				}
			} else if (!StringUtils.isEmpty(maxValue)
					&& !StringUtils.isEmpty(minValue)) {
				// 正常判断，有上下线
				if (value < Float.valueOf(maxValue)
						&& Float.valueOf(minValue) < value) {
					return true;
				}
			} else if (StringUtils.isEmpty(maxValue)
					&& StringUtils.isEmpty(minValue)) {
				// 没有上下线
				return true;
			}
		}
		return false;
	}

	/**
	 * getSysCurrentTime:(得到系统当前时间). <br/>
	 * date: 2014年10月23日 <br/>
	 *
	 * @author zhaofeng
	 * @return
	 */
	public static String getSysCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return format.format(ServerDateManager.getCurrentDate());
	}

	/**
	 * dataTimeCompare:(两个时间比较大小). <br/>
	 * date: 2014年11月26日 <br/>
	 *
	 * @author lxf
	 * @param time1
	 * @param time2
	 * @return time1>time2返回ture 否则返回false
	 * @throws HDException
	 */
	public static boolean dataTimeCompare(String time1, String time2)
			throws HDException {
		boolean ret = false;
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (time1.length() == 10) {
				time1 = time1 + " 00:00:00";
			}
			if (time2.length() == 10) {
				time2 = time1 + " 00:00:00";
			}
			Date datetime1 = formart.parse(time1);
			Date datetime2 = formart.parse(time2);
			if (datetime1.compareTo(datetime2) > 0) {
				ret = true;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage() + "time1" + time1 + ";time2:" + time2,
					e);
			throw new HDException("气体检测时间格式不正确，不能审核！", e);
		}
		return ret;
	}

	/**
	 * toAddHours:(给时间增加小时). <br/>
	 * date: 2014年11月28日 <br/>
	 *
	 * @author zhaofeng
	 * @param date1
	 * @param hour
	 * @return
	 * @throws HDException
	 */
	public static String toAddMinutes(String date1, int minute)
			throws HDException {
		DateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		try {
			Date datetime1 = formart.parse(date1);
			Calendar ca = Calendar.getInstance();
			ca.setTime(datetime1);
			ca.add(Calendar.MINUTE, minute);
			return formart.format(ca.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("气体检测时间格式不正确，不能审核！", e);
		}
	}

	/**
	 * groupList:(将List集合分组). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author zhaofeng
	 * @param list
	 *            已经按照groupProperty属性排好序的集合
	 * @param classz
	 *            list中的子项Class
	 * @param groupProperty
	 *            分组的属性
	 * @param setValueProperty
	 *            组标题要赋值的属性
	 * @return
	 * @throws HDException
	 */
	public static List<?> groupList(List<SuperEntity> list, Class<?> classz,
			String groupProperty, String setValueProperty) throws HDException {
		if (list == null || list.size() == 0)
			return null;
		if (groupProperty == null || setValueProperty == null)
			return null;
		String tempValue = list.get(0).getAttribute(groupProperty) == null ? ""
				: list.get(0).getAttribute(groupProperty).toString();
		if (StringUtils.isEmpty(tempValue))
			return null;
		List<SuperEntity> returnList = new ArrayList<SuperEntity>();
		SuperEntity addEntity = null;
		List<SuperEntity> itemEntity = null;
		try {
			if (classz.isInstance(list.get(0))) {
				for (int i = 0; i < list.size(); i++) {
					tempValue = list.get(i).getAttribute(groupProperty) == null ? ""
							: list.get(i).getAttribute(groupProperty)
									.toString();
					if (tempValue==null)
						return null;
					if (addEntity == null
							|| !tempValue
									.equals(addEntity
											.getAttribute(setValueProperty) == null ? ""
											: addEntity.getAttribute(
													setValueProperty)
													.toString())) {
						if (addEntity != null && itemEntity != null) {
							addEntity.setChild(classz.getName(), itemEntity);
							returnList.add(addEntity);
						}
						addEntity = (SuperEntity) classz.newInstance();
						itemEntity = new ArrayList<SuperEntity>();
						addEntity.setAttribute(setValueProperty, tempValue);
					}
					itemEntity.add(list.get(i));
				}
				// 增加在以后一个分组
				if (addEntity != null && itemEntity != null) {
					addEntity.setChild(classz.getName(), itemEntity);
					returnList.add(addEntity);
				}
			}
			return returnList;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("集合分组失败！", e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("集合分组失败！", e);
		}
	}

	/**
	 * mergeList:(合并二维集合，成为一维集合). <br/>
	 * date: 2015年1月19日 <br/>
	 *
	 * @author zhaofeng
	 * @param list
	 * @param classz
	 * @return
	 */
	public static List<?> mergeList(List<SuperEntity> list, Class<?> classz) {
		if (list == null || list.size() == 0)
			return null;
		List returnList = new ArrayList();
		List<SuperEntity> tempList = null;
		for (int i = 0; i < list.size(); i++) {
			tempList = list.get(0).getChild(classz.getName());
			if (tempList != null) {
				returnList.addAll(tempList);
			}
		}
		return returnList;
	}

	/**
	 * getHostAndPort:(根据URL获取IP和端口号). <br/>
	 * date: 2015年4月27日 <br/>
	 *
	 * @author lxf
	 * @param url
	 * @return
	 */
	public static String getHostAndPort(String path) {
		String ip = null;
		try {
			URL url = new URL(path);
			int port = url.getPort();
			if (port == -1) {
				ip = "http://" + url.getHost() + File.separator;
			} else {
				ip = "http://" + url.getHost() + ":" + url.getPort()
						+ File.separator;
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		}
		return ip;
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();

	}
}
