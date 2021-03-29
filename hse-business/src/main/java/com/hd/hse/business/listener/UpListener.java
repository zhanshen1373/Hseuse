package com.hd.hse.business.listener;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.AbstractDataAdapter;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName:UpListener (上传业务数据监听类).<br/>
 * Date: 2014年8月25日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */
public abstract class UpListener extends AbstractWebListener {

	private AbstractDataAdapter dataAdapter = null;

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		// 表示上传作业票//1.请求上传的数据//2.选择上传的票//3.创建数据上传格式//4.上传票信息//5.上传图片信息
		// （如果失败准对图片打标记，同时出现上传图片按钮）//6.等待反馈//7.是不是应该删除表
		// 获取关系
		PadInterfaceResponse response = null;
		try {

			super.action(action, args);
			// 设置上传数据的关系
			List<TableDesc> listmapRelation = getRelation();
			if (null == listmapRelation || listmapRelation.size() == 0) {
				setWritelog("请实现getRelation重写，并赋予相应值;");
			}
			dataAdapter = this.getAnalyzeDataAdapter();
			if (null == dataAdapter) {
				setWritelog("实例化数据适配器对象为null,请检查配置参数;");
			}
			HashMap<String, Object> hasmap = null;
			Object objparam = initParam();
			if (HashMap.class.isInstance(objparam)) {
				hasmap = (HashMap<String, Object>) objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			// 获取上传sql语句
			HashMap<String,String> hashlistsql = getUpDateSql();
			if (null == hashlistsql || hashlistsql.size() == 0) {
				setWritelog("必须实现getListSql()方法,并返回伤处数据的语句;");
			}
			// 上边是检查条件

			// 下边开始处理操作

			this.SendMessage(IMessageWhat.PROCESSING, 0, 10, "链接服务器,请求上传数据");
			// 表示根绝sql语句组织上传的数据
			HashMap<String, List<Map<String, Object>>> listinfo = getUpDateInfo(hashlistsql);
			if (null == listinfo || listinfo.size() == 0) {
				return null;
			}
			List<HashMap<String, HashMap<String, Object>>> listfile = getImagePath();
			// 把数据转换成上传需要的格式
			try {
				if (null == listfile || listfile.size() == 0) {
					this.SendMessage(IMessageWhat.PROCESSING, 10, 70,
							"连接服务器,请求上传数据");
				} else {
					this.SendMessage(IMessageWhat.PROCESSING, 10, 30,
							"连接服务器,请求上传数据");
				}
				String upstr = dataChangeFormat(listinfo, listmapRelation);
				//写日志
				setWriteDublog(upstr);
				hasmap.put(PadInterfaceRequest.KEYDATA, upstr);
			} catch (JSONException e) {
				setWritelog("组织上传数据格式报错", e);
				throw new HDException("组织上传数据格式报错;");
			}

			// 上传作业票,注意接收返回结果信息
			if (null == listfile || listfile.size() == 0) {
				this.SendMessage(IMessageWhat.PROCESSING, 70, 80,
						"连接服务器,请求上传数据");
			} else {
				this.SendMessage(IMessageWhat.PROCESSING, 10, 70,
						"连接服务器,请求上传数据");
			}
			Object obj = client.UpLoadBusiness(hasmap);

			if (obj instanceof PadInterfaceResponse) {
				response = (PadInterfaceResponse) obj;
			}
			if (null == listfile || listfile.size() == 0) {
				this.SendMessage(IMessageWhat.PROCESSING, 80, 98, "处理本地数据信息");
			} else {
				this.SendMessage(IMessageWhat.PROCESSING, 70, 80, "处理本地数据信息");
			}

			afterUploadData(response);

			List<String> listsuccess = null;
			// 上传图片
			if (null != listfile && listfile.size() > 0) {
				// 图片部分上传成功
				this.SendMessage(IMessageWhat.PROCESSING, 80, 90,
						"连接服务器,请求上传图片");
				listsuccess = uploadFile(listfile);
				this.SendMessage(IMessageWhat.PROCESSING, 90, 98, "处理本地图片信息");
				afterUploadFile(listsuccess);
				if (null != listsuccess && null != listfile
						&& listfile.size() != listsuccess.size()) {
					// 表示上传图片报错
					throw new HDException("删除图片报错!");
				}
			}
			if (null != response && response.getFailedList().size() > 0) {
				throw new HDException("数据上传成功:"
						+ response.getSucessfulList().size() + "条;失败:"
						+ response.getFailedList().size());
			}
			this.SendMessage(IMessageWhat.END, 98, 100, "上传成功");
		} catch (HDException e) {
			setWritelog("上传数据报错:", e);
			this.SendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage(), response);
		}
		return null;
	}

	/**
	 * 表示数据转化
	 * 
	 * @param dateinfo
	 * @param relation
	 * @return String
	 * @throws JSONException
	 */
	private String dataChangeFormat(
			HashMap<String, List<Map<String, Object>>> dateinfo,
			List<TableDesc> relation) throws JSONException {
		return dataAdapter.create(dateinfo, relation);
	}

	/**
	 * 根据语句查询数据库要上传的数据
	 * */
	private HashMap<String, List<Map<String, Object>>> getUpDateInfo(
			HashMap<String,String> hashlistSql) throws HDException {
		HashMap<String, List<Map<String, Object>>> update = new HashMap<String, List<Map<String, Object>>>();
		String tablename;
	   Iterator<Entry<String,String>> iter=hashlistSql.entrySet().iterator();
	   String sql;
	   while(iter.hasNext())
	   {
		   Entry<String, String> entry=iter.next();
		   tablename=entry.getKey();
		   sql=entry.getValue();
		   @SuppressWarnings("unchecked")
			ArrayList<Map<String, Object>> map = (ArrayList<Map<String, Object>>) this
					.Query(sql, new MapListResult());
		   if (map.size() > 0) {
				update.put(tablename,(List<Map<String, Object>>) map);
			}
	   }
		return update;
	}
	/**
	 * 返回上传成功的图片信息
	 * */
	private List<String> uploadFile(
			List<HashMap<String, HashMap<String, Object>>> listImage) {
		// 循环上传图片
		List<String> listsuccess = new ArrayList<String>();
		for (HashMap<String, HashMap<String, Object>> hasmap : listImage) {
			// 此时key表示路径
			Iterator<Entry<String, HashMap<String, Object>>> iter = hasmap
					.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, HashMap<String, Object>> entry = iter.next();
				String image = entry.getKey();
				HashMap<String, Object> map = entry.getValue();
				try {
					File file = new File(image);
					if (!file.exists()) {
						listsuccess.add(image);
						continue;
					}
					Object obj = client.UpLoadFile(image, map);
					if (obj instanceof PadInterfaceResponse) {
						PadInterfaceResponse response = (PadInterfaceResponse) obj;
						if (response.getFlag().equalsIgnoreCase("T")) {
							listsuccess.add(image);
						}
					} else {
						listsuccess.add(image);
					}
				} catch (Exception e) {
				}
			}
		}
		return listsuccess;
	}

	/**
	 * 获取上传数据的SQL语句
	 * */
	public abstract HashMap<String,String> getUpDateSql();

	/**
	 * 获取上传图片路径
	 * */
	public abstract List<HashMap<String, HashMap<String, Object>>> getImagePath();

	/**
	 * 获取上传表的关系 注意：关系顺序是：从孙子--子---父
	 * */
	public abstract List<TableDesc> getRelation();

	/**
	 * 上传成功数据后执行的动作
	 * 
	 * @throws HDException
	 * */
	public abstract void afterUploadData(PadInterfaceResponse response)
			throws HDException;

	/**
	 * 表示上传文件成功后执行的动作
	 * */
	public abstract void afterUploadFile(List<String> listsuccess)
			throws HDException;

}
