package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 能量隔离单 Created by liuYang on 2016/2/16
 * 
 * @author liuyang
 * 
 */
@DBTable(tableName = "ud_zyxk_nlgld")
public class EnergyIsolation extends SuperEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2069225293658449048L;
	// 能量隔离单id
	@DBField(id = true)
	private int ud_zyxk_nlgldid;
	// 作业申请id
	@DBField(foreign = true)
	private int ud_zyxk_zysqid;
	// 能量隔离单编号
	@DBField
	private String nlgldnum;
	// 隔离系统/设备
	@DBField
	private String glasset;
	// 隔离状态
	@DBField
	private String glstatus;
	// 状态
	@DBField
	private String status;
	// 危害识别
	@DBField
	private String hazard;
	@DBField
	private int idupload;

	public int getUd_zyxk_nlgldid() {
		return ud_zyxk_nlgldid;
	}

	public void setUd_zyxk_nlgldid(int ud_zyxk_nlgldid) {
		this.ud_zyxk_nlgldid = ud_zyxk_nlgldid;
	}

	public int getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	public void setUd_zyxk_zysqid(int ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	public String getNlgldnum() {
		return nlgldnum;
	}

	public void setNlgldnum(String nlgldnum) {
		this.nlgldnum = nlgldnum;
	}

	public String getGlasset() {
		return glasset;
	}

	public void setGlasset(String glasset) {
		this.glasset = glasset;
	}

	public String getGlstatus() {
		return glstatus;
	}

	public void setGlstatus(String glstatus) {
		this.glstatus = glstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHazard() {
		return hazard;
	}

	public void setHazard(String hazard) {
		this.hazard = hazard;
	}

	public int getIdupload() {
		return idupload;
	}

	public void setIdupload(int idupload) {
		this.idupload = idupload;
	}

}
