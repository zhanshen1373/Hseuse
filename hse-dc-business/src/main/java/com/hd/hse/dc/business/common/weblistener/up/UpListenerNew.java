package com.hd.hse.dc.business.common.weblistener.up;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import android.os.Environment;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.weblistener.AbsWebListener;
import com.hd.hse.padinterface.AndroidInterface;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.padinterface.PadInterfaceUpFile;

public abstract class UpListenerNew extends AbsWebListener {
	private AbstractDataAdapter dataAdapter = null;
	HashMap<String, Object> hasmap = null;
	List<TableDesc> listmapRelation;
	HashMap<String, String> hashlistsql;
	List<PadInterfaceUpFile> listfile = null;

	@Override
	public Object action(String action, Object... args) throws HDException {
		Object obj;
		try {
			super.action(action, args);
			hasmap = null;
			beforUpDataInfo(args);
			obj = upDataInfo(args);
			afterUpDataInfo(obj, args);
		} catch (HDException e) {
			setWritelog(e.getMessage());
			throw e;
		}
		return obj;
	}

	/**
	 * beforDown:(下载前动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author lxf
	 * @param obj
	 * @throws HDException
	 */
	@SuppressWarnings("unchecked")
	public void beforUpDataInfo(Object... obj) throws HDException {
		listmapRelation = getRelation();
		if (null == listmapRelation || listmapRelation.size() == 0) {
			setWritelog("请实现getRelation重写，并赋予相应值;");
		}
		dataAdapter = this.getAnalyzeDataAdapter();
		if (null == dataAdapter) {
			setWritelog("实例化数据适配器对象为null,请检查配置参数;");
		}
		Object objparam = initParam();
		if (HashMap.class.isInstance(objparam)) {
			hasmap = (HashMap<String, Object>) objparam;
		} else {
			throw new HDException("initParam()必须设置为HashMap对象;");
		}
		// 获取上传sql语句
		hashlistsql = getUpDateSql();
		if (null == hashlistsql || hashlistsql.size() == 0) {
			setWritelog("必须实现getListSql()方法,并返回伤处数据的语句;");
		}

	}

	/**
	 * down:(下载动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author Administrator
	 * @param obj
	 * @throws HDException
	 */
	@SuppressWarnings("unchecked")
	public Object upDataInfo(Object... objj) throws HDException {
		HashMap<String, List<Map<String, Object>>> listinfo = getUpDateInfo(hashlistsql);
		if (null == listinfo || listinfo.size() == 0) {
			throw new HDException("没有查询到上传的数据!");
		}
		this.sendMessage(IMessageWhat.PROCESSING, 0, 10, "链接服务器,请求上传数据");
		// 表示根绝sql语句组织上传的数据
		listfile = getListUploadFile();
		// 把数据转换成上传需要的格式
		try {
			if (null == listfile || listfile.size() == 0) {
				this.sendMessage(IMessageWhat.PROCESSING, 10, 70,
						"连接服务器,请求上传数据");
			} else {
				changeImageByte(listfile);
				if (listfile.size() > 0) {
					hasmap.put(PadInterfaceRequest.KEYUPLOADFILE, listfile);
				}
				this.sendMessage(IMessageWhat.PROCESSING, 10, 30,
						"连接服务器,请求上传数据");
			}
			String upstr = dataChangeFormat(listinfo, listmapRelation);
			// 记录上传信息
			setWriteDubuglog(upstr);
			hasmap.put(PadInterfaceRequest.KEYDATA, upstr);
		} catch (JSONException e) {
			setWritelog("组织上传数据格式报错", e);
			throw new HDException("组织上传数据格式报错;");
		}

		// 上传作业票,注意接收返回结果信息
		if (null == listfile || listfile.size() == 0) {
			this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "连接服务器,请求上传数据");
		} else {
			this.sendMessage(IMessageWhat.PROCESSING, 30, 70, "连接服务器,请求上传数据");
		}
		Object obj = null;
		try {
			obj = sClient.action(getMethodType(), hasmap);// client.UpLoadBusiness(hasmap);
		} catch (HDException e) {
			afterUploadDataError("上传数据报错");
			throw e;
		}
		return obj;

	}

	/**
	 * afterdown:(下载后的动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author lxf
	 * @param obj
	 */
	private void afterUpDataInfo(Object pcdata, Object... objj)
			throws HDException {
		PadInterfaceResponse response = null;
		if (pcdata instanceof PadInterfaceResponse) {
			response = (PadInterfaceResponse) pcdata;
		}
		if (null == listfile || listfile.size() == 0) {
			this.sendMessage(IMessageWhat.PROCESSING, 80, 98, "处理本地数据信息");
		} else {
			this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "处理本地数据信息");
		}
		// 处理上传成功后方法
		afterUploadDataInfo(pcdata);

		if (null != response && response.getFailedList().size() > 0) {
			StringBuilder msg = new StringBuilder();
			for (Map<String, String> listMap : response.getFailedList()) {
				if (listMap.containsKey(PadInterfaceRequest.KEYRETURNMESSAGE)) {
					msg.append(
							listMap.get(PadInterfaceRequest.KEYRETURNMESSAGE)
									.toString()).append(";");
				}
			}
			setWritelog(msg.toString());
			throw new HDException("上传成功:" + response.getSucessfulList().size()
					+ "条;失败:" + response.getFailedList().size() + "条;");
		}
		this.sendMessage(IMessageWhat.END, 98, 100, "上传成功");
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
			HashMap<String, String> hashlistSql) throws HDException {
		HashMap<String, List<Map<String, Object>>> update = new HashMap<String, List<Map<String, Object>>>();
		String tablename;
		Iterator<Entry<String, String>> iter = hashlistSql.entrySet()
				.iterator();
		String sql;
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			tablename = entry.getKey();
			sql = entry.getValue();
			@SuppressWarnings("unchecked")
			ArrayList<Map<String, Object>> map = (ArrayList<Map<String, Object>>) this
					.query(sql, new MapListResult());
			if (map.size() > 0) {
				update.put(tablename, (List<Map<String, Object>>) map);
			}
		}
		return update;
	}

	/**
	 * changeImageStream:(把地址path转化为 stream对象). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author lxf
	 * @param listupfile
	 * @throws HDException
	 */
	private void changeImageStream(List<PadInterfaceUpFile> listupfile)
			throws HDException {
		if (listupfile != null) {
			for (PadInterfaceUpFile padInterfaceUpFile : listupfile) {
				try {
					File file = new File(padInterfaceUpFile.getImagepath());
					if (!file.exists()) {
						continue;
					}
					InputStream data = new BufferedInputStream(
							new FileInputStream(
									padInterfaceUpFile.getImagepath()));
					padInterfaceUpFile.setImageInputStream(data);
				} catch (FileNotFoundException e) {
					throw new HDException("处理图片报错,联系管理员");
				}
			}
		}
	}

	private void changeImageByte(List<PadInterfaceUpFile> listupfile)
			throws HDException {
		if (listupfile != null) {
			List<PadInterfaceUpFile> listTemp = new ArrayList<PadInterfaceUpFile>();
			for (PadInterfaceUpFile padInterfaceUpFile : listupfile) {
				byte[] imagebyte = (GetBytesByImagePath(padInterfaceUpFile
						.getImagepath()));
				if (imagebyte == null) {
					listTemp.add(padInterfaceUpFile);
				} else {
					padInterfaceUpFile.setImagebyte(imagebyte);
				}
			}
			listupfile.removeAll(listTemp);
		}
	}

	/**
	 * GetBytesByImagePath:(将图片转化成byte数组). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author lxf
	 * @param strFile
	 * @return
	 * @throws HDException
	 */
	private byte[] GetBytesByImagePath(String strFile) throws HDException {
		try {
			File file = new File(strFile);
			if (!file.exists()) {
				return null;
			}
			FileInputStream stream = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] b = new byte[AndroidInterface.FILE_DOWNLOAD_SIZE];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (Exception e) {
			setWritelog("组织上传数据格式报错", e);
			throw new HDException("处理图片报错,联系管理员");
		}
	}

	/**
	 * 获取上传表的关系 注意：关系顺序是：从孙子--子---父
	 * */
	public abstract List<TableDesc> getRelation();

	/**
	 * 获取上传数据的SQL语句
	 * */
	public abstract HashMap<String, String> getUpDateSql();

	/**
	 * afterUploadDataError:(上传报错执行的方法). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author lxf
	 * @param error
	 * @throws HDException
	 */
	public abstract void afterUploadDataError(String error) throws HDException;

	/**
	 * afterUploadDataInfo:(上传成功后的方法). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author Administrator
	 * @param pcdata
	 * @throws HDException
	 */
	public abstract void afterUploadDataInfo(Object pcdata) throws HDException;

	/**
	 * 获取上传图片集合
	 * */
	public abstract List<PadInterfaceUpFile> getListUploadFile();

}
