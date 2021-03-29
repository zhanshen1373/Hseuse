/**
 * File:    UdZyxkCbszz.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkCbszz
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 72e0291f-05c7-4350-ac00-879025addfc3 */
@DBTable(tableName = "ud_zyxk_cbszz")
public class ContractorAptitudeConfig extends
		com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 3f0442c5-b5bd-4e89-9980-9d95c7da66b7 */
	private static final long serialVersionUID = -8635179581583451122L;
	/**
	 * a1
	 * 
	 * @pdOid 8db57fa0-d133-4f3e-ad8f-519b75de98da
	 */
	@DBField
	private Integer ud_zyxk_cbszzid;
	/**
	 * a2
	 * 
	 * @pdOid 6ea068fd-a968-4d4a-94fb-66fb660fe337
	 */
	@DBField
	private Integer ud_zyxk_zyspqxid;
	/**
	 * s3
	 * 
	 * @pdOid bb23fb8e-6922-464e-bbf3-dfa45d6134d2
	 */
	@DBField
	private String zytype;
	/**
	 * 审批环节
	 * 
	 * @pdOid 1878af23-2166-463d-bd9f-3689e71a0859
	 */
	@DBField
	private String spfield;
	/**
	 * a5
	 * 
	 * @pdOid 58bcb602-3514-4eec-b07b-93ed37d537b6
	 */
	@DBField
	private String spfield_desc;
	/**
	 * a6
	 * 
	 * @pdOid 9645736d-687e-4144-81fd-f19b7c9614ea
	 */
	@DBField
	private String sszz;
	/**
	 * 登记时间7
	 * 
	 * @pdOid 0a007cd9-c725-4486-a5ff-7f6bb6aa23bb
	 */
	@DBField
	private String changedate;
	/**
	 * 删除
	 * 
	 * @pdOid 95107a7c-f203-4614-95d4-c7a652c86dce
	 */
	@DBField
	private Integer dr;
	@DBField
	private String topzypclass;
	private String description;

	/**
	 * @return the ud_zyxk_cbszzid
	 * 
	 * @pdOid 84c6f2a7-7f97-4b05-879b-0635f36860ce
	 */
	public Integer getUd_zyxk_cbszzid() {
		return ud_zyxk_cbszzid;
	}

	/**
	 * @param ud_zyxk_cbszzid
	 *            the ud_zyxk_cbszzid to set
	 * @pdOid d49c02c8-a0d4-4c84-8323-bc901504eb5d
	 */
	public void setUd_zyxk_cbszzid(Integer ud_zyxk_cbszzid) {
		this.ud_zyxk_cbszzid = ud_zyxk_cbszzid;
	}

	/**
	 * @return the ud_zyxk_zyspqxid
	 * 
	 * @pdOid 7fa1c7f8-78a6-4236-a716-9bc778f8adf0
	 */
	public Integer getUd_zyxk_zyspqxid() {
		return ud_zyxk_zyspqxid;
	}

	/**
	 * @param ud_zyxk_zyspqxid
	 *            the ud_zyxk_zyspqxid to set
	 * @pdOid c617e683-4236-49bc-bd0c-295ea5d4cf00
	 */
	public void setUd_zyxk_zyspqxid(Integer ud_zyxk_zyspqxid) {
		this.ud_zyxk_zyspqxid = ud_zyxk_zyspqxid;
	}

	/**
	 * @return the spfield_desc
	 * 
	 * @pdOid 3e69ceaf-7788-45f4-8c62-1531cb23d44f
	 */
	public String getSpfield_desc() {
		return spfield_desc;
	}

	/**
	 * @param spfield_desc
	 *            the spfield_desc to set
	 * @pdOid df086d13-76c8-4d21-8346-028307d3fb88
	 */
	public void setSpfield_desc(String spfield_desc) {
		this.spfield_desc = spfield_desc;
	}

	/**
	 * 设置 s3 该字段是：s3
	 * 
	 * @param zytype
	 * @pdOid 3bfa4d89-3c1e-4e38-8cc9-0da949050343
	 */
	public void setZytype(String zytype) {
		this.zytype = zytype;
	}

	/**
	 * 获取 s3 该字段是：s3
	 * 
	 * 
	 * @pdOid 2e3318b2-256e-45ff-b368-80736a8449cc
	 */
	public String getZytype() {
		return zytype;
	}

	/**
	 * 设置 审批环节 该字段是：审批环节
	 * 
	 * @param spfield
	 * @pdOid 6e5b79da-6f42-4f95-a9be-2b7a2d2482db
	 */
	public void setSpfield(String spfield) {
		this.spfield = spfield;
	}

	/**
	 * 获取 审批环节 该字段是：审批环节
	 * 
	 * 
	 * @pdOid 32ee8994-8d3c-4d70-b084-a70551743d87
	 */
	public String getSpfield() {
		return spfield;
	}

	/**
	 * 设置 a6 该字段是：a6
	 * 
	 * @param sszz
	 * @pdOid 63659c24-1670-454a-a412-3bbb6afb9c34
	 */
	public void setSszz(String sszz) {
		this.sszz = sszz;
	}

	/**
	 * 获取 a6 该字段是：a6
	 * 
	 * 
	 * @pdOid e5139f18-fe09-4f1a-87cb-1f588982755e
	 */
	public String getSszz() {
		return sszz;
	}

	/**
	 * 设置 登记时间7 该字段是：登记时间7
	 * 
	 * @param changedate
	 * @pdOid 29594132-91f1-4a2b-8e81-0478736ef893
	 */
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	/**
	 * 获取 登记时间7 该字段是：登记时间7
	 * 
	 * 
	 * @pdOid cae9b320-954d-4d00-9a75-4ba1342e2764
	 */
	public String getChangedate() {
		return changedate;
	}

	/**
	 * 设置 删除 该字段是：删除
	 * 
	 * @param dr
	 * @pdOid f8123f56-2dd1-4a03-9612-577abca70e20
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 删除 该字段是：删除
	 * 
	 * 
	 * @pdOid 62dd6fcd-39ac-4328-9388-e48727bce2c4
	 */
	public Integer getDr() {
		return dr;
	}

	public String getTopzypclass() {
		return topzypclass;
	}

	public void setTopzypclass(String topzypclass) {
		this.topzypclass = topzypclass;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}