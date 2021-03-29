package com.hd.hse.entity.tempele;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 临时用电 子表
 * 
 * Created by liuyang on 2016年7月19日
 */
@DBTable(tableName = "ud_zyxk_lsydzy")
public class TempEleZy extends SuperEntity {
	/**
	 * 
	 * Created by liuyang on 2016年7月19日
	 */
	private static final long serialVersionUID = 5335892039982255997L;
	/**
	 * 主键
	 */
	@DBField(id = true)
	private Integer ud_zyxk_lsydzyid;
	/**
	 * 作业申请ID
	 */
	@DBField(foreign = true)
	private String ud_zyxk_zysqid;
	/**
	 * 临时用电目的
	 */
	@DBField
	private String lsydyt;
	/**
	 * 工作电压
	 */
	@DBField
	private String gzdy;
	/**
	 * 电源接入点
	 */
	@DBField
	private String dyjrd;
	/**
	 * 负荷合计
	 */
	@DBField
	private double sbfh;

	public Integer getUd_zyxk_lsydzyid() {
		return ud_zyxk_lsydzyid;
	}

	public void setUd_zyxk_lsydzyid(Integer ud_zyxk_lsydzyid) {
		this.ud_zyxk_lsydzyid = ud_zyxk_lsydzyid;
	}

	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	public String getLsydyt() {
		return lsydyt;
	}

	public void setLsydyt(String lsydyt) {
		this.lsydyt = lsydyt;
	}

	public String getGzdy() {
		return gzdy;
	}

	public void setGzdy(String gzdy) {
		this.gzdy = gzdy;
	}

	public String getDyjrd() {
		return dyjrd;
	}

	public void setDyjrd(String dyjrd) {
		this.dyjrd = dyjrd;
	}

	public double getSbfh() {
		return sbfh;
	}

	public void setSbfh(double sbfh) {
		this.sbfh = sbfh;
	}
}
