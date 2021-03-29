package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;
/**
 * @author yn 1017/6/7
 * 统计分析实体类
 */
@DBTable(tableName = "ud_sys_appanlscfg")
public class AppAnlsCfg extends SuperEntity{
	@DBField(id = true)
	private int ud_sys_appanlscfgid;
	@DBField
	private String description;
	@DBField
	private String anlsname;
	@DBField
	private String anlscode;
	@DBField
	private String condition;
	@DBField
	private String rqurl;
	@DBField
	private String icon;
	@DBField
	private String changedate;
	@DBField
	private int dr;
	@DBField
	private int tag;
	@DBField
	private String deptnum;
	@DBField
	private String childdept;
	@DBField
	private int isenable;
	public int getUd_sys_appanlscfgid() {
		return ud_sys_appanlscfgid;
	}
	public void setUd_sys_appanlscfgid(int ud_sys_appanlscfgid) {
		this.ud_sys_appanlscfgid = ud_sys_appanlscfgid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAnlsname() {
		return anlsname;
	}
	public void setAnlsname(String anlsname) {
		this.anlsname = anlsname;
	}
	public String getAnlscode() {
		return anlscode;
	}
	public void setAnlscode(String anlscode) {
		this.anlscode = anlscode;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getRqurl() {
		return rqurl;
	}
	public void setRqurl(String rqurl) {
		this.rqurl = rqurl;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	public String getDeptnum() {
		return deptnum;
	}
	public void setDeptnum(String deptnum) {
		this.deptnum = deptnum;
	}
	public String getChilddept() {
		return childdept;
	}
	public void setChilddept(String childdept) {
		this.childdept = childdept;
	}
	public int getIsenable() {
		return isenable;
	}
	public void setIsenable(int isenable) {
		this.isenable = isenable;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
