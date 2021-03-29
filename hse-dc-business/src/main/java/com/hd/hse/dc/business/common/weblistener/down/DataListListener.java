package com.hd.hse.dc.business.common.weblistener.down;

import com.hd.hse.common.exception.HDException;

/**
 * ClassName: DataListListener (获取数据列表)<br/>
 * date: 2015年3月20日 <br/>
 * 
 * @author lxf
 * @version
 */
public abstract class DataListListener extends GainPCDataListener {

	@Override
	public void beforDataInfo(Object... obj) throws HDException {
		// TODO Auto-generated method stub
		super.beforDataInfo(obj);
		Class<?> entityClass = getEntityClass();
		if (null == entityClass) {
			throw new HDException("getEntityClass()必须设置要转化成的对象;");
		}
	}

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
