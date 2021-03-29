package com.hd.hse.business.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName:AbstractBaseOPData (创建解析字符串).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 **/
public abstract class AbstractDataAdapter {
	
	public abstract HashMap<String,List<String>> resSql(String str,String primarykey) throws HDException;
	/**
	 * ResInsert:(解析成Sql语句) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param
	 * @return List
	 * @throws HDException 
	 * */
	public abstract HashMap<String,List<String>> resSql(String str,HashMap<String,TableDesc> hashmap) throws HDException;

	/**
	 * ResInsert:(解析成Object) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param
	 * @return object
	 * @throws HDException 
	 * */
	public abstract List<SuperEntity> toEntityList(String data, Class<?> entityClass) throws HDException;

	/**
	 * ResInsert:(解析成Object) Data: 2014年8月11日 <br/>
	 * 
	 * @author lxf
	 * @param
	 * @return Json字符串
	 * @throws JSONException 
	 * */
	public abstract String create(
			HashMap<String, List<Map<String, Object>>> dataHm,
			List<TableDesc> relation) throws JSONException;

}
