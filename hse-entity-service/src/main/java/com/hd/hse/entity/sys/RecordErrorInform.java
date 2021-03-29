package com.hd.hse.entity.sys;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * ClassName: RecordErrorInform ()<br/>
 * date: 2015年3月17日 <br/>
 * 
 * @author wenlin
 * @version
 */
@DBTable(tableName = "hse_sys_record_error")
public class RecordErrorInform extends SuperEntity {

	/**
	 * serialVersionUID:TODO().
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * hsreid:TODO(主键).
	 */
	@DBField(id = true)
	private Integer hsreid;
	/**
	 * tableid:TODO(外键).
	 */
	@DBField
	private String tableid;

	/**
	 * tablename:TODO(表名).
	 */
	@DBField
	private String tablename;
	/**
	 * errortype:TODO(错误类别).
	 */
	@DBField
	private String errortype;
	/**
	 * clickdealclass:TODO(注册动作类).
	 */
	@DBField
	private String clickdealclass;
	/**
	 * tag:TODO(是否解决).
	 */
	@DBField
	private int tag;
	/**
	 * dr:TODO(是否删除).
	 */
	@DBField
	private int dr;
	public Integer getHsreid() {
		return hsreid;
	}
	public void setHsreid(Integer hsreid) {
		this.hsreid = hsreid;
	}
	public String getTableid() {
		return tableid;
	}
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getErrortype() {
		return errortype;
	}
	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}
	public String getClickdealclass() {
		return clickdealclass;
	}
	public void setClickdealclass(String clickdealclass) {
		this.clickdealclass = clickdealclass;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
}
