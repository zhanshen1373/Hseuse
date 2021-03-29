package com.hd.hse.entity.worklog;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

@DBTable(tableName = "UD_CBSGL_KHBZ")
public class KhbzEntity extends SuperEntity {
	/**
	 * 风险级别
	 */
	@DBField
	private String fxjb;
	/**
	 * 违章标准id
	 */
	@DBField
	private String ud_cbsgl_khbzid;
	/**
	 * 违章风险值
	 */
	@DBField
	private Integer wzfxz;
	/**
	 * 违章类别
	 */
	@DBField
	private String wzlb;
	/**
	 * 违章条款
	 */
	@DBField
	private String wztk;
	/**
	 * 改变时间
	 */
	@DBField
	private String changedate;
	@DBField
	private int tag;
	@DBField
	private int dr;
	
	public String getFxjb() {
		return fxjb;
	}
	public void setFxjb(String fxjb) {
		this.fxjb = fxjb;
	}
	public String getUd_cbsgl_khbzid() {
		return ud_cbsgl_khbzid;
	}
	public void setUd_cbsgl_khbzid(String ud_cbsgl_khbzid) {
		this.ud_cbsgl_khbzid = ud_cbsgl_khbzid;
	}
	public Integer getWzfxz() {
		return wzfxz;
	}
	public void setWzfxz(Integer wzfxz) {
		this.wzfxz = wzfxz;
	}
	public String getWzlb() {
		return wzlb;
	}
	public void setWzlb(String wzlb) {
		this.wzlb = wzlb;
	}
	public String getWztk() {
		return wztk;
	}
	public void setWztk(String wztk) {
		this.wztk = wztk;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
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
