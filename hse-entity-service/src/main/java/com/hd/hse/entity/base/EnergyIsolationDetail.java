package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 能量隔离单详情表
 * 
 * @date 2016/2/16
 * @author liuyang
 * 
 */
@DBTable(tableName = "ud_zyxk_nlgldline")
public class EnergyIsolationDetail extends SuperEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2176704280606951438L;
	// 主键
	@DBField(id = true)
	private int ud_zyxk_nlgldlineid;
	// 关联主表id
	@DBField(foreign = true)
	private int ud_zyxk_nlgldid;
	// 排序
	@DBField
	private int seqnum;
	@DBField
	private String location;
	@DBField
	private String location_desc;
	@DBField
	private String gznrdesc;
	@DBField
	private int count;
	@DBField
	private String jcnum;
	@DBField
	private String jcclass;
	// 能量隔离单序号
	@DBField
	private String nlgldnum;
	// 能量/物料
	@DBField
	private String nlwl;
	// 隔离方法
	@DBField
	private String glff;
	// 上锁挂牌点
	@DBField
	private String ssgpd;
	// 挂牌点
	@DBField
	private String gpd;
	// 其他
	@DBField
	private String nlglqt;
	@DBField
	private String datasource;

	public int getUd_zyxk_nlgldlineid() {
		return ud_zyxk_nlgldlineid;
	}

	public void setUd_zyxk_nlgldlineid(int ud_zyxk_nlgldlineid) {
		this.ud_zyxk_nlgldlineid = ud_zyxk_nlgldlineid;
	}

	public int getUd_zyxk_nlgldid() {
		return ud_zyxk_nlgldid;
	}

	public void setUd_zyxk_nlgldid(int ud_zyxk_nlgldid) {
		this.ud_zyxk_nlgldid = ud_zyxk_nlgldid;
	}

	public int getSeqnum() {
		return seqnum;
	}

	public void setSeqnum(int seqnum) {
		this.seqnum = seqnum;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation_desc() {
		return location_desc;
	}

	public void setLocation_desc(String location_desc) {
		this.location_desc = location_desc;
	}

	public String getGznrdesc() {
		return gznrdesc;
	}

	public void setGznrdesc(String gznrdesc) {
		this.gznrdesc = gznrdesc;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getJcnum() {
		return jcnum;
	}

	public void setJcnum(String jcnum) {
		this.jcnum = jcnum;
	}

	public String getJcclass() {
		return jcclass;
	}

	public void setJcclass(String jcclass) {
		this.jcclass = jcclass;
	}

	public String getNlgldnum() {
		return nlgldnum;
	}

	public void setNlgldnum(String nlgldnum) {
		this.nlgldnum = nlgldnum;
	}

	public String getNlwl() {
		return nlwl;
	}

	public void setNlwl(String nlwl) {
		this.nlwl = nlwl;
	}

	public String getGlff() {
		return glff;
	}

	public void setGlff(String glff) {
		this.glff = glff;
	}

	public String getSsgpd() {
		return ssgpd;
	}

	public void setSsgpd(String ssgpd) {
		this.ssgpd = ssgpd;
	}

	public String getGpd() {
		return gpd;
	}

	public void setGpd(String gpd) {
		this.gpd = gpd;
	}

	public String getNlglqt() {
		return nlglqt;
	}

	public void setNlglqt(String nlglqt) {
		this.nlglqt = nlglqt;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
}
