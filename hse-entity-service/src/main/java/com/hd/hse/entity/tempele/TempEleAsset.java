package com.hd.hse.entity.tempele;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * 临时用电设备已选列表
 * 
 * Created by liuyang on 2016年7月19日
 */
@DBTable(tableName = "ud_zyxk_ydasset")
public class TempEleAsset extends SuperEntity {
	/**
	 * 
	 * Created by liuyang on 2016年7月19日
	 */
	private static final long serialVersionUID = 26401997132593178L;
	/**
	 * 主键
	 */
	@DBField(id = true)
	private String ud_zyxk_ydassetid;
	/**
	 * 用电设备id
	 */
	@DBField
	private int ud_zyxk_ydsbid;
	/**
	 * 作业申请id
	 */
	@DBField(foreign = true)
	private String ud_zyxk_zysqid;
	/**
	 * 设备名称
	 */
	@DBField
	private String assetname;
	/**
	 * 数量
	 */
	@DBField
	private int count;
	/**
	 * 负荷
	 */
	@DBField
	private double fh;
	/**
	 * 修改时间
	 */
	@DBField
	private String changedate;
	/**
	 * 设备类型
	 */
	@DBField
	private String version;

	public String getUd_zyxk_ydassetid() {
		return ud_zyxk_ydassetid;
	}

	public void setUd_zyxk_ydassetid(String ud_zyxk_ydassetid) {
		this.ud_zyxk_ydassetid = ud_zyxk_ydassetid;
	}

	public int getUd_zyxk_ydsbid() {
		return ud_zyxk_ydsbid;
	}

	public void setUd_zyxk_ydsbid(int ud_zyxk_ydsbid) {
		this.ud_zyxk_ydsbid = ud_zyxk_ydsbid;
	}

	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	public String getAssetname() {
		return assetname;
	}

	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getFh() {
		return fh;
	}

	public void setFh(double fh) {
		this.fh = fh;
	}

	public String getChangedate() {
		return changedate;
	}

	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
