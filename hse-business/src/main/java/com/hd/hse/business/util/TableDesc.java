package com.hd.hse.business.util;

/**
 * ClassName: TableDesc (表的描述)<br/>
 * date: 2014年8月27日  <br/>
 *
 * @author lxf
 * @version 
 */
public class TableDesc {
	
	private String tableName;
	private String primarykey;
	private String foreignkey;
	private String foreignMainTable;
	
	

	/**
	 * getTableName:(获取表明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * setTableName:(设置表明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * getPrimarykey:(获取主键列明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getPrimarykey() {
		return primarykey;
	}
	/**
	 * setPrimarykey:(设置主键列明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
	/**
	 * getForeignkey:(获取外键列明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getForeignkey() {
		return foreignkey;
	}
	/**
	 * setForeignkey:(设置外键列明). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public void setForeignkey(String foreignkey) {
		this.foreignkey = foreignkey;
	}
	/**
	 * getForeignMainTable:(设置外键关联表(即父表)). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public String getForeignMainTable() {
		return foreignMainTable;
	}
	/**
	 * setForeignMainTable:(获取外键关联表(即父表)). <br/>
	 * date: 2014年8月27日 <br/>
	 *
	 * @author lxf
	 * @return
	 */
	public void setForeignMainTable(String foreignMainTable) {
		this.foreignMainTable = foreignMainTable;
	}




}
