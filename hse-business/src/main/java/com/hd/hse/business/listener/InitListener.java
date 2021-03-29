package com.hd.hse.business.listener;



import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:InitListener (初始化和更新数据监听类).<br/>
 * 完成 Date: 2014年8月25日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */
public abstract class InitListener extends AbstractWebListener {

	@SuppressWarnings("unchecked")
	public Object action(String action,Object... args) throws HDException {
		try {
			super.action(action,args);
			StringBuilder sbtag = new StringBuilder();
			this.SendMessage(IMessageWhat.PROCESSING, 0, 5, "连接服务器,请求下载数据");
			String sql = "select SYS_TABLENAME,SYS_TABLEID,SYS_TS from hse_sys_record_ts ";
			String where = getWhere();
			//默认表示初始化
			if (!StringUtils.isEmpty(where)) {
				sql += " where " + where + ";";
			} else {
				sql += " where SYS_INIT=1;";
			}
			HashMap<String, Object> hasmap = null;
			Object objparam=initParam();
			if (HashMap.class.isInstance(objparam)) {
				hasmap = (HashMap<String, Object>)objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			
			// 下载动作
			// 1.获取下载的表信息+TS//2.调用PC端方法//3.打标记处理//4.插入数据库//5.成功删除标记（失败回滚）
			// 表示更新动作
			// 1.获取要下载的表信息//2.调用PC端方法//3.打标记处理//4.插入数据库//5.成功删除打标记重复的数据（失败回滚）//6.还原标记
			List<String> listsql= null;
			Long date1 = System.currentTimeMillis();
			listsql = getOpSql(sql, hasmap);
			Long date2 = System.currentTimeMillis();
			this.SendMessage(IMessageWhat.PROCESSING, 35, 90, "开始本地处理数据");
			Long date3 = System.currentTimeMillis();
			if(null!=listsql && listsql.size()>0)
			execute(listsql);
			Long date4 = System.currentTimeMillis();
			this.SendMessage(IMessageWhat.END, 98, 100, "成功");
			
			sbtag.append("PC端下载耗时:").append((date2 - date1))
					.append("|插入数据库耗时:").append((date4 - date3));
			setWriteDublog(sbtag.toString());
		} catch (HDException e) {
			setWritelog("下载数据失败"+e.getMessage());
			this.SendMessage(IMessageWhat.ERROR, 9, 9,e.getMessage().indexOf("超时")>0?e.getMessage():"下载数据失败,请联系管理员!");
		}
		return 1;
	}


	/**
	 * 设置条件读取数据
	 * */
	public abstract String getWhere();

	/**
	 * getInit:(是否初始化). <br/>
	 * date: 2014年8月24日 <br/>
	 * 
	 * @author lxf
	 * @return boolean
	 */
	public abstract boolean getInit();

	/**
	 * 拼写打标记、插入、删除 语句
	 * */
	@SuppressWarnings("unchecked")
	private List<String> getOpSql(String sql1, HashMap<String, Object> hasmap)
			throws HDException {
		List<String> listsql = new ArrayList<String>();
		List<String> listDelete = new ArrayList<String>();
		List<String> listUpdate = new ArrayList<String>();
		List<String> listDeleteDr=new ArrayList<String>();
		StringBuilder sbtable = new StringBuilder();

		List<HashMap<String, Object>> retlist = null;
		retlist = (ArrayList<HashMap<String, Object>>) this.Query(sql1,
				new MapListResult());

		if (retlist.size() > 0) {

			StringBuilder sql = new StringBuilder();
			// 此处使用循环
			String tabename = null;
			for (HashMap<String, Object> map : retlist) {
				if (!map.containsKey("SYS_TABLENAME".toLowerCase())) {
					throw new HDException(
							"key:SYS_TABLENAME  不存在".toLowerCase()
									+ this.getClass().getName());
				}
				tabename = (map.get("SYS_TABLENAME".toLowerCase())).toString();
				if (!StringUtils.isEmpty(tabename)) {
					// 打标记
					sql.append("update ").append(tabename)
							.append(" set tag=1;");
					listsql.add(sql.toString());
					sql.setLength(0);

					if (sbtable.length() > 0) {
						sbtable.append(",");
					}
					sbtable.append(tabename);
					sbtable.append("&");
					if (getInit()) {
						// 初始化
						sbtable.append("1999-03-26 15:51:14");
						sql.setLength(0);
						// 删除打标记的数据
						sql.append("delete from ").append(tabename)
								.append(" where tag=1;");
						listDelete.add(sql.toString());
						sql.setLength(0);
					} else {
						// 更新
						if (map.get("SYS_TS".toLowerCase()) == null || map.get("SYS_TS".toLowerCase()).toString().equalsIgnoreCase("null")) {
							sbtable.append("1999-03-26 15:51:14");
						} else {
							sbtable.append(map.get("SYS_TS".toLowerCase().toString()));
						}

						sql.setLength(0);
						// 删除重复的数据
						String tableid = map.get("SYS_TABLEID".toLowerCase())
								.toString();
						sql.append("delete from ").append(tabename)
								.append(" where tag=1 and ").append(tableid)
								.append(" in (").append("select ")
								.append(tableid).append(" from ")
								.append(tabename).append(" group by ")
								.append(tableid).append(" having count(*)>1);");
						listDelete.add(sql.toString());
						sql.setLength(0);
						// 表示还原标记
						sql.append("update ").append(tabename)
								.append(" set tag=0;");
						listDelete.add(sql.toString());
						sql.setLength(0);
					}
					sql.setLength(0);
					sql.append("update hse_sys_record_ts").append(" set SYS_TS=")
							.append(" (select max(changedate) from  ")
							.append(tabename).append(") where SYS_TABLENAME='")
							.append(tabename).append("';");
					listUpdate.add(sql.toString());
					sql.setLength(0);
					sql.append("delete from ").append(tabename).append(" where dr=1;");
					listDeleteDr.add(sql.toString());
					sql.setLength(0);

				}
			}
		} else {
			throw new HDException("hse_sys_record_ts表没有配置要下载的表;");
		}
		// 判断下载的票
		if (sbtable.length() == 0) {
			throw new HDException("hse_sys_record_ts 记录配置不正确;");
		}
		hasmap.put(PadInterfaceRequest.KEYTABLE, sbtable.toString());
		// 连接数据库
		this.SendMessage(IMessageWhat.PROCESSING, 5, 15, "连接服务器,请求下载数据");
		Long date2 = System.currentTimeMillis();

		List<String> listsqlInsert=null;
		Object obj= client.InitBaseInfo(hasmap);
		if (obj instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) obj;
			setWritelog("pc报错："+response.getExceptionmsg());
			throw new HDException("下载失败,请联系管理员");
		}
		else
		{
			listsqlInsert=(List<String>)obj;
		}

		Long date3 = System.currentTimeMillis();
		setWriteDublog("服务器下载耗时："+(date3-date2));
		this.SendMessage(IMessageWhat.PROCESSING, 15, 35, "开始本地处理数据");
		if (null == listsqlInsert || listsqlInsert.size() == 0) {
			//throw new HDException("无下载的数据");
			return null;
		}
		// 3.打标记和插入数据库同意事物执行
		listsql.addAll(listsqlInsert);
		listsql.addAll(listDelete);
		listsql.addAll(listUpdate);
		listsql.addAll(listDeleteDr);
		
		return listsql;
	}
}
