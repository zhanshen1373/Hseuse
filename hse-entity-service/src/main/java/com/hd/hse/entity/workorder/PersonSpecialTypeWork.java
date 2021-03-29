/**
 * File:    UdCbsglRytzzy.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdCbsglRytzzy
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid cb91408a-0669-462b-b855-210c175762a2 */
@DBTable(tableName = "ud_cbsgl_rytzzy")
public class PersonSpecialTypeWork extends com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 2050f197-aca8-4eef-a539-8c398a628ab4 */
	private static final long serialVersionUID = 8998601008344239450L;
	/**
	 * a1
	 * 
	 * @pdOid 39ac2418-ab96-4de7-9b20-b6e08d3030d7
	 */
	@DBField(id = true)
	private Integer ud_cbsgl_rytzzyid;
	/**
	 * 证书类别
	 * 
	 * @pdOid 17e40b97-67bb-4c50-9357-1ca5b33eca8a
	 */
	@DBField
	private String certificatetype;
	/**
	 * 身份证号
	 * 
	 * @pdOid 430e65b9-5dfa-4bbe-9de4-8cd1d3be450e
	 */
	@DBField
	private String idcard;
	/**
	 * 发证日期
	 * 
	 * @pdOid 2e443a25-e655-47e1-b273-7192dfd58914
	 */
	@DBField
	private String fzdate;
	/**
	 * 到期日期
	 * 
	 * @pdOid d3c7e32c-f26d-4fbe-abb8-0df33280908f
	 */
	@DBField
	private String dqdate;
	/**
	 * 更新时间
	 * 
	 * @pdOid e0cffe21-9e4e-4cc4-b4e6-3463066cdd07
	 */
	@DBField
	private String changedate;
	/**
	 * 标记删除
	 * 
	 * @pdOid b5957048-cab8-441e-b4f8-53da4f585250
	 */
	@DBField
	private Integer dr;
	/**
	 * a8
	 * 
	 * @pdOid 94487be9-a384-4938-b1e2-2ffcb1d1c396
	 */
	@DBField
	private String zsnum;
	/**
	 * a9
	 * 
	 * @pdOid 26102107-5d01-48d0-be3f-fa2acf29425c
	 */
	@DBField
	private String zsname;
	/**
	 * 准操项目
	 * Created by liuyang on 2016年11月30日
	 */
	private String zcproject;

	/**
	 * 乌石化证书名称 Created by liuyang on 2016年10月21日
	 */
	private String zsname_for_wsh;

	/**
	 * 证书类别描述 Created by liuyang on 2016年11月12日
	 */
	private String description;

	/**
	 * @return the ud_cbsgl_rytzzyid
	 * 
	 * @pdOid 313c3dc2-fedb-41a6-8265-5698103d4f21
	 */
	public Integer getUd_cbsgl_rytzzyid() {
		return ud_cbsgl_rytzzyid;
	}

	/**
	 * @param ud_cbsgl_rytzzyid
	 *            the ud_cbsgl_rytzzyid to set
	 * @pdOid 81440ded-5b9c-482b-a2e2-1f8c682b0f75
	 */
	public void setUd_cbsgl_rytzzyid(Integer ud_cbsgl_rytzzyid) {
		this.ud_cbsgl_rytzzyid = ud_cbsgl_rytzzyid;
	}

	/**
	 * 设置 证书类别 该字段是：证书类别
	 * 
	 * @param certificatetype
	 * @pdOid 26c4ddb7-89d6-4391-9e11-2102f2815b0f
	 */
	public void setCertificatetype(String certificatetype) {
		this.certificatetype = certificatetype;
	}

	/**
	 * 获取 证书类别 该字段是：证书类别
	 * 
	 * 
	 * @pdOid ed58e04e-56b7-415d-8c6d-879f96d6393d
	 */
	public String getCertificatetype() {
		return certificatetype;
	}

	/**
	 * 设置 身份证号 该字段是：身份证号
	 * 
	 * @param idcard
	 * @pdOid a67a573a-4514-41e6-8595-7ff933d1f999
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * 获取 身份证号 该字段是：身份证号
	 * 
	 * 
	 * @pdOid eed5a576-525a-4967-9c1a-bea93b9a0e63
	 */
	public String getIdcard() {
		return idcard;
	}

	/**
	 * 设置 发证日期 该字段是：发证日期
	 * 
	 * @param fzdate
	 * @pdOid c3ce2043-f46e-4c6a-b2ac-1d1f5bcef6ac
	 */
	public void setFzdate(String fzdate) {
		this.fzdate = fzdate;
	}

	/**
	 * 获取 发证日期 该字段是：发证日期
	 * 
	 * 
	 * @pdOid 37c13691-511b-4a31-9e2e-917c52f5bcb0
	 */
	public String getFzdate() {
		return fzdate;
	}

	/**
	 * 设置 到期日期 该字段是：到期日期
	 * 
	 * @param dqdate
	 * @pdOid 9c7f3d42-0eb4-4613-8244-280a5b496b0f
	 */
	public void setDqdate(String dqdate) {
		this.dqdate = dqdate;
	}

	/**
	 * 获取 到期日期 该字段是：到期日期
	 * 
	 * 
	 * @pdOid 59c22ddb-8278-4298-8e47-2d3bef9cfd5d
	 */
	public String getDqdate() {
		return dqdate;
	}

	/**
	 * 设置 更新时间 该字段是：更新时间
	 * 
	 * @param changedate
	 * @pdOid 3677febd-84d4-4f17-9fa9-481058e2f28c
	 */
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	/**
	 * 获取 更新时间 该字段是：更新时间
	 * 
	 * 
	 * @pdOid e486583e-3f89-41f3-af8c-59f840e09ace
	 */
	public String getChangedate() {
		return changedate;
	}

	/**
	 * 设置 标记删除 该字段是：标记删除
	 * 
	 * @param dr
	 * @pdOid cff99d45-923e-49ac-beee-ef4e7c2a7f4a
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 标记删除 该字段是：标记删除
	 * 
	 * 
	 * @pdOid 6d958a1e-8ffe-4e75-a3a8-e15e5af9cc34
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 a8 该字段是：a8
	 * 
	 * @param zsnum
	 * @pdOid cfce0a99-22de-4bd1-90da-11065911c602
	 */
	public void setZsnum(String zsnum) {
		this.zsnum = zsnum;
	}

	/**
	 * 获取 a8 该字段是：a8
	 * 
	 * 
	 * @pdOid c8e39d69-b7bf-4b36-bb5b-07855f1a9eb1
	 */
	public String getZsnum() {
		return zsnum;
	}

	/**
	 * 设置 a9 该字段是：a9
	 * 
	 * @param zsname
	 * @pdOid 5b085a04-ebd7-4cff-9676-3b14b55f6bdb
	 */
	public void setZsname(String zsname) {
		this.zsname = zsname;
	}

	/**
	 * 获取 a9 该字段是：a9
	 * 
	 * 
	 * @pdOid 6a57273e-6582-4d57-9006-efff3dda031f
	 */
	public String getZsname() {
		return zsname;
	}

	public String getZsname_for_wsh() {
		return zsname_for_wsh;
	}

	public void setZsname_for_wsh(String zsname_for_wsh) {
		this.zsname_for_wsh = zsname_for_wsh;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getZcproject() {
		return zcproject;
	}

	public void setZcproject(String zcproject) {
		this.zcproject = zcproject;
	}

}