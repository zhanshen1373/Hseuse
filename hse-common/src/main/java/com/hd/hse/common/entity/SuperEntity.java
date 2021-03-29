/**
 * Project Name:hse-common
 * File Name:EntityHelper.java
 * Package Name:com.hd.hse.common.entity
 * Date:2014年8月9日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.table.DBTableHelper;

/**
 * ClassName: SuperEntity (实体超类)<br/>
 * 属性必须是小写，get、set方法必须自动生成 date: 2014年8月9日 <br/>
 *
 * @author lg
 * @version
 */
public abstract class SuperEntity implements Serializable, Cloneable {
	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = -5823395508024605236L;

	/**
	 * modified:TODO(更新标识).
	 */
	private boolean modified;

	/**
	 * add:TODO(新增标识).
	 */
	private boolean add;

	/**
	 * delete:TODO(删除标识).
	 */
	private boolean delete;

	/**
	 * mapChilds:TODO(子表记录<子表实体类名,子表数据>).
	 */
	private Map<String, List<SuperEntity>> mapChilds;
	/**
	 * listListChilds:TODO(不同数据结构的子记录).
	 */
	private Map<String, List<List<SuperEntity>>> listListChilds;

	/**
	 * toBeAdd:(). <br/>
	 * TODO(判断是否新增数据).<br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public boolean toBeAdd() {
		return isAdd() && (!toBeDelete());
	}

	/**
	 * toBeDelete:(). <br/>
	 * TODO(判断是否删除数据).<br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public boolean toBeDelete() {
		return isDelete();
	}

	/**
	 * toBeUpdate:(). <br/>
	 * TODO(判断是否修改数据).<br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public boolean toBeUpdate() {
		return isModified() && (!isAdd()) && (!isDelete());
	}

	/**
	 * getAttribute:(). <br/>
	 * TODO(获取指定属性).<br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @param attributeName
	 * @return
	 */
	public Object getAttribute(String attributeName) {
		if (StringUtils.isEmpty(attributeName)) {
			return null;
		} else {
			return EntityHelper.getAttribute(this, attributeName);
		}
	}

	/**
	 * setAttribute:(). <br/>
	 * TODO(设置属性).<br/>
	 * date: 2014年8月9日 <br/>
	 *
	 * @author lg
	 * @param attributeName
	 * @param value
	 */
	public void setAttribute(String attributeName, Object value) {
		if (StringUtils.isEmpty(attributeName)) {
			return;
		} else {
			EntityHelper.setAttribute(this, attributeName, value);
		}
	}

	/**
	 * getChildClasses:(子表对应实体类名). <br/>
	 * date: 2014年8月10日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public String[] getChildClasses() {
		return null;
	}

	/**
	 * getChilds:(返回子表数据集). <br/>
	 * date: 2014年8月10日 <br/>
	 *
	 * @author lg
	 * @return
	 */
	public Map<String, List<SuperEntity>> getChilds() {
		return mapChilds;
	}

	/**
	 * getChild:(). <br/>
	 * date: 2014年8月10日 <br/>
	 *
	 * @author lg
	 * @param entityName实体类名
	 * @return
	 */
	public List<SuperEntity> getChild(String entityName) {
		if (mapChilds != null) {
			return mapChilds.get(entityName);
		}
		return null;
	}

	/**
	 * setChild:(设置子表数据). <br/>
	 * date: 2014年8月19日 <br/>
	 *
	 * @author lg
	 * @param entityName
	 * @param lstChild
	 */
	public void setChild(String entityName, List<SuperEntity> lstChild) {
		if (mapChilds == null) {
			mapChilds = new HashMap<String, List<SuperEntity>>();
		}
		if (!mapChilds.containsKey(entityName)) {
			mapChilds.put(entityName, new ArrayList<SuperEntity>());
		}
		mapChilds.get(entityName).addAll(lstChild);
	}

	/**
	 * clearChild:(清空子表数据). <br/>
	 * date: 2014年11月10日 <br/>
	 *
	 * @author lg
	 * @param entityName
	 */
	public void clearChild(String entityName){
		if (mapChilds != null && mapChilds.containsKey(entityName)) {
			mapChilds.get(entityName).clear();
		}
	}

	/**
	 * setListChild:(设置子数据集). <br/>
	 * date: 2014年10月18日 <br/>
	 *
	 * @author zhaofeng
	 * @param entityName
	 * @param lstChild
	 */
	public void setListChild(String entityName, List<List<SuperEntity>> lstChild) {
		if (listListChilds == null) {
			listListChilds = new HashMap<String, List<List<SuperEntity>>>();
		}
		if (!listListChilds.containsKey(entityName)) {
			listListChilds.put(entityName, new ArrayList<List<SuperEntity>>());
		}
		listListChilds.get(entityName).addAll(lstChild);
	}

	public List<List<SuperEntity>> getListChild(String entityName) {
		if (listListChilds != null) {
			return listListChilds.get(entityName);
		}
		return null;
	}

	public String getDBTableName() {
		return DBTableHelper.getTableName(this);
	}

	public String getPrimaryKey() {
		return DBTableHelper.getPrimaryKey(this);
	}

	public String getForeignKey() {
		return DBTableHelper.getForeignKey(this);
	}

	public String[] getDBTableColumns() {
		return DBTableHelper.getDBTableColumns(this);
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	@Override
	public SuperEntity clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		SuperEntity cloneEntity = (SuperEntity) super.clone();
		return cloneEntity;
	}

	/**
	 * cloneProperties:(克隆属性). <br/>
	 * date: 2014年10月25日 <br/>
	 *
	 * @author lg
	 * @param srcEntity
	 */
	public void cloneProperties(SuperEntity srcEntity) {
		Field[] fields = srcEntity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				setAttribute(field.getName(),
						srcEntity.getAttribute(field.getName()));
			}
		}
	}
}
