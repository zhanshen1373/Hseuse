/**
 * Project Name:hse-cbs-app
 * File Name:ContractorCarDriver.java
 * Package Name:com.hd.hse.cbs.pub.car
 * Date:2014年9月12日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.entity.contractor;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName:ContractorCarDriver (承包商车辆驾驶员).<br/>
 * Date: 2014年9月12日 <br/>
 * 
 * 
 * @author lg
 * @version
 * @see
 * 
 * @pdOid b7c537d1-c282-4d16-80b9-9aa119280bbb */
@DBTable(tableName = "ud_cbsgl_cljs")
public class ContractorCarDriver extends com.hd.hse.common.entity.SuperEntity {
   /** serialVersionUID:TODO().
    * 
    * 
    * @pdOid 5ce59c77-212a-4763-8f53-41d6b200194a */
   private static final long serialVersionUID = -2799382908357367573L;
   /**  主键
    * 
    * @pdOid af2c8ece-9aa9-4ac1-b0d4-424a5f52679f */
   @DBField(id = true)
   private String ud_cbsgl_cljsid;
   /**  车牌号 外键
    * 
    * @pdOid 4db709da-eb56-44fb-a5b6-f2dbc727b7bf */
   @DBField(foreign = true)
   private String carnum;
   /**  驾驶员
    * 
    * @pdOid 2c930891-2340-46d6-987a-737d8a91b7b2 */
   @DBField
   private String name;
   /**  驾驶证
    * 
    * @pdOid 1a135aef-b674-4960-bb84-8e6feb5c1d03 */
   @DBField
   private String drivenum;
   /**  驾驶证档案编码
    * 
    * @pdOid 8d545528-9287-4574-9c38-5a708981079e */
   @DBField
   private String driverdocnum;
   /**  驾驶证到期日期
    * 
    * @pdOid 84a0526a-f48a-43c7-9e6d-6d0827831cb9 */
   @DBField
   private String lasttime;
   /**  准驾类型
    * 
    * @pdOid cb74fec0-969e-46d1-a591-e59759f8731f */
   @DBField
   private String zjtype;
   /**  初领从业资格证日期
    * 
    * @pdOid ce88d905-3c58-4cc1-a996-5698285738d2 */
   @DBField
   private String clcyzgzdate;
   /**  时间戳
    * 
    * @pdOid 9500bfb6-9fd5-4709-94ee-cc3136c17456 */
   @DBField
   private String changedate;
   /**  标记
    * 
    * @pdOid c0cb0602-e8d2-426f-a9c3-3a9c57d5d8a6 */
   @DBField
   private Integer tag;
   /**  删除
    * 
    * @pdOid 62267d13-7829-4304-9d84-ab6648c7fadf */
   @DBField
   private Integer dr;
   /**  安全公里累计
    * 
    * @pdOid 12671a80-b38c-41ee-9092-d4adbef0ad23 */
   @DBField
   private Integer safekmcount;
   /**  联系方式
    * 
    * @pdOid adbe9fd5-c3b9-46ea-8c02-14210817de7c */
   @DBField
   private Integer jsyphone;
   
   /** @pdOid 342f30c1-5ad2-48c9-a969-eb8cf36912af */
   public String getUd_cbsgl_cljsid() {
   	return ud_cbsgl_cljsid;
   }
   
   /** @param ud_cbsgl_cljsid
    * @pdOid 31d4be27-c3e6-4d67-9465-1a824a0dc2d5 */
   public void setUd_cbsgl_cljsid(String ud_cbsgl_cljsid) {
   	this.ud_cbsgl_cljsid = ud_cbsgl_cljsid;
   }
   
   /** @pdOid 58a0955b-6075-4036-835f-5006f510a119 */
   public String getCarnum() {
   	return carnum;
   }
   
   /** @param carnum
    * @pdOid b00684d8-7c22-493d-b109-6bf16ca94085 */
   public void setCarnum(String carnum) {
   	this.carnum = carnum;
   }
   
   /** @pdOid 619a0c70-c07b-4835-bba4-ca68012f45cb */
   public String getName() {
   	return name;
   }
   
   /** @param name
    * @pdOid 2c08c36e-719c-45ea-892d-a94b1312a105 */
   public void setName(String name) {
   	this.name = name;
   }
   
   /** @pdOid a490a9b2-3662-41ff-a298-60aa825be01c */
   public String getDrivenum() {
   	return drivenum;
   }
   
   /** @param drivenum
    * @pdOid 56444a64-27f3-4caa-a8e6-8d4805e886d2 */
   public void setDrivenum(String drivenum) {
   	this.drivenum = drivenum;
   }
   
   /** @pdOid ca2c5007-bb7d-4b2f-9a6b-353d6068f0f9 */
   public String getDriverdocnum() {
   	return driverdocnum;
   }
   
   /** @param driverdocnum
    * @pdOid bc19aae7-8499-4503-b49b-87b7835ca486 */
   public void setDriverdocnum(String driverdocnum) {
   	this.driverdocnum = driverdocnum;
   }
   
   /** @pdOid 85576a70-6b43-4031-9b68-bee3def5ea09 */
   public String getLasttime() {
   	return lasttime;
   }
   
   /** @param lasttime
    * @pdOid 18d15a4c-9bcf-4e42-b2c1-5ceecc6757ee */
   public void setLasttime(String lasttime) {
   	this.lasttime = lasttime;
   }
   
   /** @pdOid 0fea4f4b-2018-4d98-9131-30ebfe8279aa */
   public String getZjtype() {
   	return zjtype;
   }
   
   /** @param zjtype
    * @pdOid 9a2597b2-3682-42c7-8e7a-eb18591b90e0 */
   public void setZjtype(String zjtype) {
   	this.zjtype = zjtype;
   }
   
   /** @pdOid 011fed87-4c1a-4689-a48f-77323ea48558 */
   public String getClcyzgzdate() {
   	return clcyzgzdate;
   }
   
   /** @param clcyzgzdate
    * @pdOid 38d33a12-a835-4b52-9f2e-4b9860305fea */
   public void setClcyzgzdate(String clcyzgzdate) {
   	this.clcyzgzdate = clcyzgzdate;
   }
   
   /** @pdOid 455fef74-70d2-4ffa-972f-19f7e16a067f */
   public String getChangedate() {
   	return changedate;
   }
   
   /** @param changedate
    * @pdOid d1a1a5f6-b24c-4531-be24-dba80821e6d1 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** @pdOid 98a18fd8-30d8-4b9d-8f73-35b28fb1cf04 */
   public Integer getTag() {
   	return tag;
   }
   
   /** @param tag
    * @pdOid 234b34b7-3cbb-482d-93a8-6c557dc25add */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** @pdOid d1055c54-ad8a-40c5-aa09-6b17addb8b61 */
   public Integer getDr() {
   	return dr;
   }
   
   /** @param dr
    * @pdOid 57bfc690-1f51-492b-aa49-536c95ade583 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** @pdOid e955504d-7858-498a-9fb3-c1099218280e */
   public static long getSerialversionuid() {
   	return serialVersionUID;
   }
   
   /** @pdOid df561d68-0de0-42fe-9f84-ae9ff1c1d2d1 */
   public Integer getSafekmcount() {
   	return safekmcount;
   }
   
   /** @param safekmcount
    * @pdOid bc390358-17c2-4f48-9820-9d10b66b8c75 */
   public void setSafekmcount(Integer safekmcount) {
   	this.safekmcount = safekmcount;
   }
   
   /** @pdOid 484c1873-768a-466b-ba54-9881e6749993 */
   public Integer getJsyphone() {
   	return jsyphone;
   }
   
   /** @param jsyphone
    * @pdOid e56e875f-b1f2-40a4-9551-aa75d70945bd */
   public void setJsyphone(Integer jsyphone) {
   	this.jsyphone = jsyphone;
   }

}