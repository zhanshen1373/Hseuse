package com.hd.hse.dc.business.web.cbs;



import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.UpListener;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: CBSWorklogUp (承包商工作日志上传)<br/>
 * date: 2014年9月1日 <br/>
 * 
 * @author lxf
 * @version
 */

public class CBSWorklogUp extends UpListener {

	private static Logger logger = LogUtils.getLogger(CBSWorklogUp.class);
	private static List<TableDesc> ltable = null;
	private StringBuilder strid=new StringBuilder();
	
	
	@Override
	public Object action(String action, Object... args) throws HDException {
		if (null != args) {
			for (Object ob : args) {
				if (ob instanceof List) {
					@SuppressWarnings("unchecked")
					List<SuperEntity> listEntity = (List<SuperEntity>)ob;
					if (null != listEntity && listEntity.size() > 0) {
						for (SuperEntity supe : listEntity) {
							strid.append("'");
							strid.append(supe.getAttribute(supe.getPrimaryKey()));
							strid.append("',");
						}
						if (null != strid && strid.length() > 0) {
							strid.delete(strid.length() - 1, strid.length());
						}
					}
					break;
				}
			}
		}
		return super.action(action, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HashMap<String, HashMap<String, Object>>> getImagePath() {
		String sql;
		if (null != strid && strid.length() > 0) {
			sql = "select replace(imagepath,x'0a','') as imagepath,tableid from hse_sys_image where tablename='ud_cbsgl_rz' and tableid in ("
					+ strid.toString() + ");";
		} else {
			sql = "select replace(imagepath,x'0a','') as imagepath,tableid from hse_sys_image where tablename='ud_cbsgl_rz'; ";
		}
		List<HashMap<String, Object>> maplist = null;
		List<HashMap<String, HashMap<String, Object>>> listImage = new ArrayList<HashMap<String, HashMap<String, Object>>>();
		HashMap<String, HashMap<String, Object>> imagehash;
		HashMap<String, Object> mapwhere;
		String filename;
		try {
			maplist = (ArrayList<HashMap<String, Object>>) this.Query(sql,
					new MapListResult());
			if (null != maplist) {
				for (HashMap<String, Object> hash : maplist) {
					// 表示取出每一行
					Iterator<Entry<String, Object>> iter = hash.entrySet()
							.iterator();
					String path = null;
					mapwhere = new HashMap<String, Object>();
					imagehash = new HashMap<String, HashMap<String, Object>>();
					while (iter.hasNext()) {
						Entry<String, Object> entry = iter.next();
						if (entry.getKey().equalsIgnoreCase("imagepath")) {
							path = entry.getValue().toString();
						}
						if (entry.getKey().equalsIgnoreCase("tableid")) {
							mapwhere.put(PadInterfaceRequest.KEYTABLIEID, entry.getValue());
						}
					}
					if (null != path && path.length() > 0) {
						filename = getFileName(path);
						mapwhere.put(PadInterfaceRequest.KEYFILENAME, filename);
						mapwhere.put(PadInterfaceRequest.KEYAPPNAME, "appcbsrz");
						imagehash.put(path, mapwhere);
						listImage.add(imagehash);
						// imagehash.clear();
					}
				}
			}
		} catch (HDException e) {
			setWritelog("读取数据库报错:" + e.getMessage());
		}
		return listImage;
	}

	/**
	 * 根绝路径返回文件名
	 * @param path 路径
	 * @return 文件名
	 */
	private String getFileName(String path) {
		String filename;
		int start = path.lastIndexOf("/");
		int end = path.length();
		if (start != -1 && end != -1) {
			filename = path.substring(start + 1, end);
		} else {
			filename = "hd_zhangjie.jpg";
		}
		return filename;
	}

	@Override
	public List<TableDesc> getRelation() {
		// 设置上传表的关系
		if (null == ltable) {
			ltable = new ArrayList<TableDesc>();
			TableDesc tb = new TableDesc();
			tb.setTableName("UD_CBSGL_RZ");
			tb.setPrimarykey("PADRZID");
			ltable.add(tb);
		}
		return ltable;
	}

	@Override
	public WebConfig getWebConfigParam() {
		// 表示设置上传参数
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

	@Override
	public Object initParam() {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty.getSystemProperty().getLoginPerson().getPersonid());
		//hashmap.put(PadInterfaceRequest.KEYDEPTNUM, SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
		String dept=SystemProperty.getSystemProperty().getLoginPerson().getDepartment();
		if(!StringUtils.isEmpty(dept))
		{
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM,dept);
		}
		return hashmap;
	}
	@Override
	public HashMap<String,String> getUpDateSql() {
		HashMap<String, String> hasmap=new HashMap<String, String>();
		String sql=null;
		
		if (null != strid && strid.length() > 0) {
			sql="select * from UD_CBSGL_RZ where padrzid in("+strid+");";
		} else {
			sql="select * from UD_CBSGL_RZ;";
		}
		if(!StringUtils.isEmpty(sql))
		{
			hasmap.put("UD_CBSGL_RZ".toLowerCase(), sql);
		}
		return hasmap;
	}
	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void afterUploadData(PadInterfaceResponse response)
			throws HDException {
		// 表示上传完图片后执行的动作
		if (null != response) {
			// 表示根据返回结果执行相应的处理
			StringBuilder sbsucess = new StringBuilder();
			StringBuilder sql = new StringBuilder();
			for (Map<String, String> hasmap : response.getSucessfulList()) {
				if (hasmap.containsKey("id"))
					sbsucess.append("'").append(hasmap.get("id").toString())
							.append("',");
			}
			if (sbsucess.length() > 0) {
				sbsucess.delete(sbsucess.length() - 1, sbsucess.length());
				sql.append("delete from UD_CBSGL_RZ where padrzid in(")
						.append(sbsucess).append(");");
				try {
					this.execute(sql.toString());
				} catch (HDException e) {
					setWritelog("删除UD_CBSGL_RZ表信息报错:" + e.getMessage());
					throw e;
				}
			}
		}
	}

	@Override
	public void afterUploadFile(List<String> listsuccess) throws HDException {
		// 表示上传完文件后执行的动作
		if (null != listsuccess) {
			StringBuilder sbdelete = new StringBuilder();
			List<String> listDelete = new ArrayList<String>();
			// 此处删除文件和删除数据库记录
			for (String sql : listsuccess) {
				sbdelete.append(
						"delete from hse_sys_image where tablename='ud_cbsgl_rz' and imagepath='")
						.append(sql).append("';");
				listDelete.add(sbdelete.toString());
				sbdelete.delete(0, sbdelete.length());
			}
			// 删除数据库图片信息
			if (listDelete.size() > 0) {
				try {
					this.execute(listDelete);
				} catch (HDException e) {
					setWritelog("删除数据库图片信息报错:" + e.getMessage());
					throw e;
				}
			}
			// 删除本地文件信息
			for (String path : listsuccess) {
				File file = new File(path);
				if (file.exists()) {
					file.delete();
				}
			}

		}
	}
	
}
