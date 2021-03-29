package com.hd.hse.entity.sys;

import android.R.integer;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

@DBTable(tableName = "ud_zyxk_padmenuctrl")
public class RoleDetect extends com.hd.hse.common.entity.SuperEntity {

	
	@DBField
	private Integer ud_zyxk_padmenuctrlid;
	
	@DBField
	private String qxrole;
	
	@DBField
	private String childdept;
	
	@DBField
	private String deptnum;
	
	@DBField
	private String menuctrl;
	
	@DBField
	private String changedate;

	@DBField
	private String role;

	@DBField
	private Integer isenable;

	@DBField
	private Integer tag;

	@DBField
	private Integer dr;

	public Integer getUd_zyxk_padmenuctrlid() {
		return ud_zyxk_padmenuctrlid;
	}

	public void setUd_zyxk_padmenuctrlid(Integer ud_zyxk_padmenuctrlid) {
		this.ud_zyxk_padmenuctrlid = ud_zyxk_padmenuctrlid;
	}

	public String getQxrole() {
		return qxrole;
	}

	public void setQxrole(String qxrole) {
		this.qxrole = qxrole;
	}

	public String getChilddept() {
		return childdept;
	}

	public void setChilddept(String childdept) {
		this.childdept = childdept;
	}

	public String getDeptnum() {
		return deptnum;
	}

	public void setDeptnum(String deptnum) {
		this.deptnum = deptnum;
	}

	public String getMenuctrl() {
		return menuctrl;
	}

	public void setMenuctrl(String menuctrl) {
		this.menuctrl = menuctrl;
	}

	public String getChangedate() {
		return changedate;
	}

	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getIsenable() {
		return isenable;
	}

	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Integer getDr() {
		return dr;
	}

	public void setDr(Integer dr) {
		this.dr = dr;
	}
	
	
	
	

}
