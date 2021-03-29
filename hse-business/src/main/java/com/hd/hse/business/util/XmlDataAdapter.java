package com.hd.hse.business.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;

/**
 * ClassName:OPXML (处理XML).<br/>
 * Date: 2014年8月21日 <br/>
 * 
 * @author lxf
 * @version
 * @see
 */
public class XmlDataAdapter extends AbstractDataAdapter {

	private static XmlDataAdapter resInstance;

	 /** 实例化对象 */
	public static XmlDataAdapter getInstance() {
		 if(null==resInstance)
		{
			 resInstance= new XmlDataAdapter();
		}
		return resInstance;
	}

	private XmlDataAdapter() {

	}
	@Override
	public  HashMap<String,List<String>> resSql(String str,String primarykey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  HashMap<String,List<String>> resSql(String str,HashMap<String,TableDesc> hashmap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SuperEntity> toEntityList(String data, Class entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String create(HashMap<String, List<Map<String, Object>>> dataHm,
			List<TableDesc> relation) {
		// TODO Auto-generated method stub
		return null;
	}

}
