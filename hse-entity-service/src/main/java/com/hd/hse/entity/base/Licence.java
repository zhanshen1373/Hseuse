package com.hd.hse.entity.base;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 
 * @author yn
 *
 */
@DBTable(tableName = "ud_sy_licence")
public class Licence extends com.hd.hse.common.entity.SuperEntity {
	/**
	 * id
	 */
	@DBField(id = true)
	private int ud_sy_licenceid;
	/**
	 * 描述
	 */
	@DBField
	private String description;
	
	/**
	 * imeia
	 */
	@DBField
	private String imeia;
	
	/**
	 * imeib
	 */
	@DBField
	private String imeib;
	
	
	@DBField
	private int hasld ;
	
	@DBField
	private String changedate;
	
	@DBField
	private String rowstamp;
	
	@DBField
	private String jobdept;
	
	@DBField
	private String jobdept_desc;
	
	@DBField
	private String sddept;
	
	@DBField
	private String sddept_desc;
	
	@DBField
	private int tag;
	@DBField
	private int dr;

	public int getUd_sy_licenceid() {
		return ud_sy_licenceid;
	}

	public void setUd_sy_licenceid(int ud_sy_licenceid) {
		this.ud_sy_licenceid = ud_sy_licenceid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImeia() {
		return imeia;
	}

	public void setImeia(String imeia) {
		this.imeia = imeia;
	}

	public String getImeib() {
		return imeib;
	}

	public void setImeib(String imeib) {
		this.imeib = imeib;
	}

	public int getHasld() {
		return hasld;
	}

	public void setHasld(int hasld) {
		this.hasld = hasld;
	}

	public String getChangedate() {
		return changedate;
	}

	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	public String getRowstamp() {
		return rowstamp;
	}

	public void setRowstamp(String rowstamp) {
		this.rowstamp = rowstamp;
	}

	

	public String getSddept() {
		return sddept;
	}

	public void setSddept(String sddept) {
		this.sddept = sddept;
	}

	public String getSddept_desc() {
		return sddept_desc;
	}

	public void setSddept_desc(String sddept_desc) {
		this.sddept_desc = sddept_desc;
	}

	public String getJobdept() {
		return jobdept;
	}

	public void setJobdept(String jobdept) {
		this.jobdept = jobdept;
	}

	public String getJobdept_desc() {
		return jobdept_desc;
	}

	public void setJobdept_desc(String jobdept_desc) {
		this.jobdept_desc = jobdept_desc;
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
