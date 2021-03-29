package com.hd.hse.dc.business.common.weblistener.down;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.ListMapListWebResult;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName: SpecialDownListener (特殊作业票的下载)<br/>
 * date: 2015年3月20日 <br/>
 * 
 * @author lxf
 * @version
 */
public abstract class SpecialDownListener extends GainPCDataListener {

	HashMap<String, TableDesc> hashRelation;

	@Override
	public void beforDataInfo(Object... obj) throws HDException {
		// TODO Auto-generated method stub
		this.sendMessage(IMessageWhat.PROCESSING, 0, 5, "连接服务器,请求下载数据");
		super.beforDataInfo(obj);
		// 记录成功和失败的票的主键 eg: id:1或0
		try {

			hashRelation = getTableRelation();
			if (null == hashRelation && hashRelation.size() == 0) {
				throw new HDException("请实现getTableRelation()方法，设置业务关联表;");
			}
//			HashMap<String, Object> hashParam = null;
//			Object objparam = initParam();
//			if (HashMap.class.isInstance(objparam)) {
//				hashParam = (HashMap<String, Object>) objparam;
//			} else {
//				throw new HDException("initParam()必须设置为HashMap对象;");
//			}
			// this.sendMessage(IMessageWhat.PROCESSING, 5, 10,
			// "连接服务器,请求下载数据中");
			// String changeDate = getTableChangeDate();
			// if (StringUtils.isEmpty(changeDate)) {
			// changeDate = "1999-03-26 15:51:14";
			// }
			// String updateMainTable = getUpdateMainTable();
			// if (StringUtils.isEmpty(updateMainTable)) {
			// throw new HDException("请实现getUpdateMainTable()方法，设置更新的表名;");
			// }
			//
			// hashParam.put(PadInterfaceRequest.KEYTABLE, updateMainTable + "&"
			// + changeDate);
			this.sendMessage(IMessageWhat.PROCESSING, 5, 30, "连接服务器,请求下载数据");

		} catch (HDException e) {
			setWritelog("下载数据失败", e);
			this.sendMessage(IMessageWhat.ERROR, 9, 9,
					e.getMessage().indexOf("超时") > 0 ? e.getMessage()
							: "下载数据失败,请联系管理员!");
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void afterDataInfo(Object pcdata, Object... objj) throws HDException {
		// TODO Auto-generated method stub

		// 解析 数据
		boolean isfail = false;
		this.sendMessage(IMessageWhat.PROCESSING, 30, 90, "开始本地处理数据");
		if (pcdata instanceof List) {
			for (Object obj : (List) pcdata) {
				if (obj instanceof HashMap) {
					HashMap<String, List<String>> hasmapsql = (HashMap<String, List<String>>) obj;
					// key是主键
					Iterator<Entry<String, List<String>>> iter = hasmapsql.entrySet()
							.iterator();
					int times = 0;
					List<String> listsql = new ArrayList<String>();
					while (iter.hasNext()) {
						times++;
						Entry<String, List<String>> entry = iter.next();
						// 表示一套票一套票执行
						try {
							listsql.addAll(entry.getValue());
							if (times >= 100 || !iter.hasNext()) {
								if (!iter.hasNext()) {
									// 表示执行删除标记为1的
									List<String> getsql = getAfterMethodSql(hashRelation);
									if (null != getsql && getsql.size() > 0)
										listsql.addAll(getsql);
								}
								this.execute(listsql);
								setWriteDubuglog(listsql.toString());
								times = 0;
								listsql.clear();
							}
							// hasReturn.put(entry.getKey(), "1");
						} catch (Exception e) {
							// 如果失败 得到key
							isfail = true;
							setWritelog(e.getMessage());
							// hasReturn.put(entry.getKey(), "0");
						}
					}
				}
			}
		}
		if (isfail) {
			this.sendMessage(IMessageWhat.END, 98, 100, "部分执行失败!");
		} else {
			this.sendMessage(IMessageWhat.END, 98, 100, "执行成功");
		}
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
	 * 获取表的关系
	 * */
	public abstract HashMap<String, TableDesc> getTableRelation();

	/**
	 * 获得业务表时间戳的语句
	 * */
	public abstract String getTableChangeDate();

	/**
	 * getUpdateMainTable:(设置更新的表明). <br/>
	 * date: 2014年9月11日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract String getUpdateMainTable();

	/**
	 * getAfterMethodSql:(拼写删除标记语句). <br/>
	 * date: 2014年9月19日 <br/>
	 * 
	 * @author lxf
	 * @param hashRelation
	 * @return
	 */
	public List<String> getAfterMethodSql(
			HashMap<String, TableDesc> hashRelation) {
		List<String> listsql = new ArrayList<String>();

		Iterator<Entry<String, TableDesc>> iter = hashRelation.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Entry<String, TableDesc> entry = iter.next();
			TableDesc tb = entry.getValue();
			listsql.add("delete from " + tb.getTableName() + " where dr=1;");
		}

		return listsql;
	}

}
