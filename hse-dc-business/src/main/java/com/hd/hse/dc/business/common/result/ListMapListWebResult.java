package com.hd.hse.dc.business.common.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;

/**
 * ClassName: ListMapWebResult (listMap)<br/>
 * date: 2015年3月20日 <br/>
 * 
 * @author lxf
 * @version
 */
public class ListMapListWebResult extends BaseWebResult {

	private AbstractDataAdapter adapter;
	HashMap<String, TableDesc> hashRelation;

	public ListMapListWebResult(AbstractDataAdapter adapter,
			HashMap<String, TableDesc> hasMap) {
		// TODO Auto-generated constructor stub
		this.adapter = adapter;
		hashRelation = hasMap;
	}

	@Override
	public Object changeResultType(Object rs) throws HDException {
		// TODO Auto-generated method stub
		List<Object> listTemp = new ArrayList<Object>();
		if (adapter == null) {
			throw new HDException("请传入解析器对象");
		}
		if (rs instanceof List) {
			@SuppressWarnings("unchecked")
			List<String> retStr = (List<String>) rs;
			for (String str : retStr) {
				HashMap<String, List<String>> hasmapsql = adapter.resSql(str,
						hashRelation);
				listTemp.add(hasmapsql);
			}
		} else if (rs instanceof String) {
			HashMap<String, List<String>> hasmapsql = adapter.resSql(
					rs.toString(), hashRelation);
			listTemp.add(hasmapsql);
		}
		return listTemp;
	}

}
