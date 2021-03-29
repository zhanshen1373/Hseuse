package com.hd.hse.business.listener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName: SpecialDownListener (特殊业务数据下载)<br/>
 * date: 2014年8月27日 <br/>
 * 
 * @author lxf
 * @version
 */

public abstract class SpecialDownListener extends AbstractWebListener {

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		
		// 记录成功和失败的票的主键 eg: id:1或0
		HashMap<String, Object> hasReturn = new HashMap<String, Object>();
		try {
			super.action(action, args);
			this.SendMessage(IMessageWhat.PROCESSING, 0, 5, "连接服务器,请求下载数据");
			HashMap<String, TableDesc> hashRelation = getTableRelation();
			if (null == hashRelation && hashRelation.size() == 0) {
				throw new HDException("请实现getTableRelation()方法，设置业务关联表;");
			}

			String businessType = getBusinessType();
			if (StringUtils.isEmpty(businessType)) {
				throw new HDException("请实现getBusinessType()方法，设置下载的业务类型;");
			}
			HashMap<String, Object> hashParam = null;
			Object objparam=initParam();
			if (HashMap.class.isInstance(objparam)) {
				hashParam = (HashMap<String, Object>)objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			this.SendMessage(IMessageWhat.PROCESSING, 5, 10, "连接服务器,请求下载数据中");
			String changeDate = getTableChangeDate();
			if (StringUtils.isEmpty(changeDate)) {
				changeDate = "1999-03-26 15:51:14";
			}
			String updateMainTable=getUpdateMainTable();
			if (StringUtils.isEmpty(updateMainTable)) {
				throw new HDException("请实现getUpdateMainTable()方法，设置更新的表名;");
			}
			
			hashParam.put(PadInterfaceRequest.KEYTABLE, updateMainTable+"&"+changeDate);
			this.SendMessage(IMessageWhat.PROCESSING, 10, 30, "连接服务器,请求下载数据");
			List<String> retStr = null;// client.DownLoadBusiness(hashParam,businessType);

			Object obj = client.DownLoadBusiness(hashParam, businessType);
			if (obj instanceof PadInterfaceResponse) {
				PadInterfaceResponse response = (PadInterfaceResponse) obj;
				setWritelog("pc报错："+response.getExceptionmsg());
				throw new HDException("下载失败,请联系管理员");
			} else {
				retStr =new ArrayList<String>();
				retStr.add(obj.toString());//(List<String>) obj;
			}

			this.SendMessage(IMessageWhat.PROCESSING, 30, 90, "开始本地处理数据");
			// 解析 数据
			boolean isfail = false;
			for (String str : retStr) {
				HashMap<String, List<String>> hasmapsql = resDowninfo(
						hashRelation, str);
				// key是主键
				Iterator<Entry<String, List<String>>> iter = hasmapsql
						.entrySet().iterator();
				int times=0;
				List<String> listsql=new ArrayList<String>();
				while (iter.hasNext()) {
					times++;
					Entry<String, List<String>> entry = iter.next();
					// 表示一套票一套票执行
					try {
						listsql.addAll(entry.getValue());
						if(times>=100 ||!iter.hasNext())
						{
							if(!iter.hasNext())
							{
								//表示执行删除标记为1的
							   List<String> getsql=getAfterMethodSql(hashRelation);
							   if(null!=getsql && getsql.size()>0)
								   listsql.addAll(getsql);
							}
							this.execute(listsql);
							//setWritelog(listsql.toString());
							times=0;
							listsql.clear();
						}
						//hasReturn.put(entry.getKey(), "1");
					} catch (Exception e) {
						// 如果失败 得到key
						isfail = true;
						setWritelog(e.getMessage());
						//hasReturn.put(entry.getKey(), "0");
					}
				}
			}
			if (isfail) {
				this.SendMessage(IMessageWhat.END, 98, 100, "部分执行失败!");
			} else {
				this.SendMessage(IMessageWhat.END, 98, 100, "执行成功");
			}
		} catch (HDException e) {
			setWritelog("下载数据失败", e);
			this.SendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage().indexOf("超时")>0?e.getMessage():"下载数据失败,请联系管理员!");
		}
		return null;
	}

	/**
	 * 上传业务类型
	 * */
	public abstract String getBusinessType();

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
	public List<String> getAfterMethodSql(HashMap<String, TableDesc> hashRelation)
	{
		List<String> listsql=new ArrayList<String>();

			Iterator<Entry<String, TableDesc>> iter = hashRelation.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, TableDesc> entry=iter.next();
				TableDesc tb=entry.getValue();
				listsql.add("delete from "+tb.getTableName()+" where dr=1;");
			}

		return listsql;
	}
	


}
