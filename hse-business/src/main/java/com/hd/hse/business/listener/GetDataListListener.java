package com.hd.hse.business.listener;

import com.hd.hse.business.util.AbstractDataAdapter;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.padinterface.PadInterfaceResponse;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * ClassName:GetDataListListener (查询PC数据监听类).<br/>
 * Date: 2014年8月24日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */
public abstract class GetDataListListener extends AbstractWebListener {

	@SuppressWarnings("unchecked")
	public Object action(String action, Object... args) throws HDException {
		Object retObj = null;
		try {
			super.action(action, args);
			HashMap<String, Object> hasmap = null;
			String businessType = getBusinessType();
			if (StringUtils.isEmpty(businessType)) {
				throw new HDException("请实现getBusinessType()方法，设置下载的业务类型;");
			}

			Object objparam = initParam();
			if (HashMap.class.isInstance(objparam)) {
				hasmap = (HashMap<String, Object>) objparam;
			} else {
				throw new HDException("initParam()必须设置为HashMap对象;");
			}
			Class<?> entityClass = getEntityClass();
			if (null == entityClass) {
				throw new HDException("getEntityClass()必须设置要转化成的对象;");
			}
			// 表示PC端获取数据
			String retStr = null;
			Object obj = client.DownLoadBuesinessList(hasmap, businessType);
			if (obj instanceof PadInterfaceResponse) {
				PadInterfaceResponse response = (PadInterfaceResponse) obj;
				setWritelog("pc报错："+response.getExceptionmsg());
				throw new HDException("查询数据失败!,请联系管理员");
			} else if (obj instanceof Integer) {
				retObj = obj;
			} else if (obj instanceof String){
				retStr = (String) obj;
			}else if (obj instanceof HashMap){
				retObj=obj;
			}
			if (!StringUtils.isEmpty(retStr)) {
				//retStr = "{'UD_CBSGL_CL':[{'carnum':'1294','carname':'海顿','UD_CBSGL_CLJS':[{'carnum':'1294','UD_CBSGL_CLJSID':'901','NAME':'文林'},{'carnum':'1294','UD_CBSGL_CLJSID':'902','NAME':'张杰'}]},{'carnum':'1295','carname':'海顿新科','UD_CBSGL_CLJS':[{'carnum':'1295','UD_CBSGL_CLJSID':'903','NAME':'白头'},{'carnum':'1295','UD_CBSGL_CLJSID':'904','NAME':'赵峰'}]}]}";

				AbstractDataAdapter adapter = getAnalyzeDataAdapter();

				IProcessResultSet type = getType();
				if (null == type) {
					type = new MapListResult();
				}
				if (type instanceof EntityListResult) {
					retObj = adapter.toEntityList(retStr, entityClass);
				}
			}
		} catch (Exception e) {
			setWritelog(e.getMessage());
			throw new HDException(e.getMessage());
		}
		return retObj;
	}

	/**
	 * 下载业务类型
	 * */
	public abstract String getBusinessType();

	/**
	 * 设置返回查询结结果对象
	 * */
	public abstract IProcessResultSet getType();

	/**
	 * getEntityClass:(要转换的实体类). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract Class<?> getEntityClass();

	/**
	 * getColumns:(设置xml需要转换的字段). <br/>
	 * date: 2014年9月23日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public abstract String[] getColumns();

}
