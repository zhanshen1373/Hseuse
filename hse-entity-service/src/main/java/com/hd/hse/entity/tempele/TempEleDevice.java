package com.hd.hse.entity.tempele;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 临时用电设备列表
 * 
 * Created by liuyang on 2016年7月19日
 */
@DBTable(tableName = "ud_zyxk_ydsb")
public class TempEleDevice extends SuperEntity {
	/**
	 * 
	 * Created by liuyang on 2016年7月19日
	 */
	private static final long serialVersionUID = 1450905390730826383L;
	/**
	 * 主键
	 */
	@DBField(id = true)
	private Integer ud_zyxk_ydsbid;
	/**
	 * 负荷
	 */
	@DBField
	private double fh;
	/**
	 * 设备名称
	 */
	@DBField
	private String assetname;
	/**
	 * 设备类型
	 */
	@DBField
	private String version;
	/**
	 * 删除标识
	 */
	@DBField
	private int dr;
	/**
	 * 修改时间
	 */
	@DBField
	private String changedate;
	/**
	 * 数量
	 */
	private Integer count;

	private boolean isChoiced;

	public Integer getUd_zyxk_ydsbid() {
		return ud_zyxk_ydsbid;
	}

	public void setUd_zyxk_ydsbid(Integer ud_zyxk_ydsbid) {
		this.ud_zyxk_ydsbid = ud_zyxk_ydsbid;
	}

	public double getFh() {
		return fh;
	}

	public void setFh(double fh) {
		this.fh = fh;
	}

	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getDr() {
		return dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	public String getChangedate() {
		return changedate;
	}

	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public boolean isChoiced() {
		return isChoiced;
	}

	public void setChoiced(boolean isChoiced) {
		this.isChoiced = isChoiced;
	}

}
