package com.hd.hse.dc.business.common.weblistener.down;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.ListMapListWebResult;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * ClassName: BusDownListener (业务数据的下载)<br/>
 * date: 2015年3月20日 <br/>
 * 
 * @author lxf
 * @version
 */
public abstract class BusDownListener extends GainPCDataListener {

	String returnAction;
	HashMap<String, TableDesc> hashRelation;
	HashMap<String, Object> returnhasmap;
	HashMap<String, Object> hasReturn;

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		hasReturn = new HashMap<String, Object>();
		try {
			super.action(action, args);
		} catch (HDException e) {
			setWritelog("获取PC端数据报错", e);
			if (getIsThrowException()) {
				throw new HDException(e.getMessage());
			} else {
				this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
			}
		}
		return hasReturn;
	}

	@Override
	public void beforDataInfo(Object... obj) throws HDException {
		if (isCancel) {
			isCancel = false;
			return;
		}
		this.sendMessage(IMessageWhat.PROCESSING, 0, 5, "连接服务器,请求下载数据");
		super.beforDataInfo(obj);
		hashRelation = getTableRelation();
		if (null == hashRelation && hashRelation.size() == 0) {
			throw new HDException("请实现getTableRelation()方法，设置业务关联表;");
		}
		returnAction = getReturnPCAction();
		if (StringUtils.isEmpty(returnAction)) {
			throw new HDException("请实现getReturnPCAction()方法，设置回传动作类型;");
		}
		returnhasmap = getReturnPCMapInfo();
		if (null == returnhasmap) {
			throw new HDException("getReturnPCMapInfo()必须设置为HashMap对象;");
		}
		if (isCancel) {
			isCancel = false;
			return;
		}
		this.sendMessage(IMessageWhat.PROCESSING, 5, 50, "连接服务器,请求下载数据");
	}

	@Override
	public void afterDataInfo(Object pcdata, Object... objj) throws HDException {
		// TODO Auto-generated method stub
		super.afterDataInfo(pcdata, objj);
		if (isCancel) {
			isCancel = false;
			return;
		}
		this.sendMessage(IMessageWhat.PROCESSING, 50, 90, "开始本地处理数据");
		// 解析 数据
		StringBuilder sbstr = new StringBuilder();
		if (pcdata instanceof List) {
			for (Object obj : (List) pcdata) {
				if (obj instanceof HashMap) {
					HashMap<String, List<String>> hasmapsql = (HashMap<String, List<String>>) obj;
					// key是主键
					Iterator<Entry<String, List<String>>> iter = hasmapsql
							.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<String, List<String>> entry = (Entry<String, List<String>>) iter
								.next();
						// 表示一套票一套票执行
						try {
							this.execute(entry.getValue());
							setWriteDubuglog(entry.getValue().toString());
							String strid = entry.getKey();
							if (strid.contains("@")) {
								for (String stid : strid.split("@")[1]
										.split(",")) {
									if (sbstr.indexOf(stid) == -1) {
										sbstr.append(stid).append(",");
									}
								}
							} else {
								if (sbstr.indexOf(strid) == -1) {
									sbstr.append("'").append(strid)
											.append("',");
								}
							}
							hasReturn.put(entry.getKey(), "1");
						} catch (Exception e) {
							// 如果失败 得到key
							hasReturn.put(entry.getKey(), "0");
						}
					}
				}
			}
		}
		if (sbstr.length() > 0) {
			HashMap<String, Object> returnhasmap = getReturnPCMapInfo();
			sbstr.delete(sbstr.length() - 1, sbstr.length());
			returnhasmap.put(PadInterfaceRequest.KEYZYPSTRID, sbstr.toString());
			try {
				// 回调PC端(注意下载不接受回执的返回信息)
				sClient.action(returnAction, returnhasmap);
			} catch (HDException e1) {
				// 表示回传打标记报错
				returnPCinfoError(sbstr.toString());
				throw e1;
			}
		}
		this.sendMessage(IMessageWhat.END, 90, 100, "成功");
	}

	@Override
	public BaseWebResult getResultChangeType() {
		// TODO Auto-generated method stub
		try {
			return new ListMapListWebResult(getAnalyzeDataAdapter(),
					getTableRelation());
		} catch (HDException e) {
			setWritelog(e.getMessage());
		}
		return null;
	}

	/**
	 * getIsThrow:(是否跑出异常). <br/>
	 * date: 2015年11月25日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public boolean getIsThrowException() {
		return false;
	}

	/**
	 * 获取表的关系
	 * */
	public abstract HashMap<String, TableDesc> getTableRelation();

	/**
	 * getReturnPCAction:(设置返回成功的动作). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract String getReturnPCAction();

	/**
	 * getReturnPCMapInfo:(下载成功，返回PC端数据). <br/>
	 * date: 2014年10月9日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract HashMap<String, Object> getReturnPCMapInfo();

	/**
	 * returnPCinfoError:(回传PC端信息报错时执行的方法). <br/>
	 * date: 2015年3月17日 <br/>
	 * 
	 * @author lxf
	 * @param str
	 * @throws HDException
	 */
	public abstract void returnPCinfoError(String str) throws HDException;

}
