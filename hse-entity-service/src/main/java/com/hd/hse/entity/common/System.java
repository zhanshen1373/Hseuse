/**
 * File:    HseSysRecordTs.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysRecordTs
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid bf5adb57-3274-4da4-b730-6bf627e4652c */
@DBTable(tableName = "hse_sys_record_ts")
public class System extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 0d2d8463-43f7-4cea-86e1-5fa957581c9f */
   private static final long serialVersionUID = -6093621043208860538L;
   /** 主键
    * 
    * @pdOid 8356b4f8-9d37-443e-8acf-4932dfaf10d6 */
   @DBField(id=true)
   private Integer sysId;
   /** 标的名称
    * 
    * @pdOid f69cc61e-83fe-44ff-ac35-cdb4777e90e5 */
   @DBField
   private String sysTablename;
   /** 表的主键
    * 
    * @pdOid 3b20d3b0-a66c-4a3d-90eb-45edae43b08b */
   @DBField
   private String sysTableid;
   /** 表的描述
    * 
    * @pdOid 25e35f7a-8ca0-4871-8188-889c90bdb42c */
   @DBField
   private String sysTabledesc;
   /** 初始化
    * 
    * @pdOid f585f98e-bcf2-471e-ab29-09d767e26996 */
   @DBField
   private Integer sysInit;
   /** 更新
    * 
    * @pdOid d9467316-0a3d-4f95-99e8-288c8a208259 */
   @DBField
   private Integer sysBase;
   /** 承包商人员
    * 
    * @pdOid 07488339-83b4-4ea6-94c9-fc5605d04ff7 */
   @DBField
   private Integer sysCbspeople;
   /** 时间戳
    * 
    * @pdOid e7f80499-9fc8-4ebd-a7ce-e91df41137f5 */
   @DBField
   private String sysTs;
   /** 删除
    * 
    * @pdOid 16a9a059-1997-4fff-93e5-4a1613911a5f */
   @DBField
   private Integer dr;
   /** 登录更新
    * 
    * @pdOid 5c42b8ea-ec6b-48a9-b964-a18af3a7c199 */
   @DBField
   private Integer sysLogindateupdate;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param sysId
    * @pdOid 1285f54f-935c-4599-9f0f-1c484bb18f24 */
   public void setSysId(Integer sysId) {
   	this.sysId = sysId;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid b825bb4e-e4e2-414c-80b3-78f98524f371 */
   public Integer getSysId() {
   	return sysId;
   }
   
   /** 设置 标的名称
    * 该字段是：标的名称
    * 
    * @param sysTablename
    * @pdOid 910cd436-d363-4185-be8d-5052c7594e65 */
   public void setSysTablename(String sysTablename) {
   	this.sysTablename = sysTablename;
   }
   
   /** 获取 标的名称
    * 该字段是：标的名称
    * 
    * 
    * @pdOid b8d619f5-ef66-486f-b5e5-db3356dc888b */
   public String getSysTablename() {
   	return sysTablename;
   }
   
   /** 设置 表的主键
    * 该字段是：表的主键
    * 
    * @param sysTableid
    * @pdOid df1fe0f7-db2b-48c5-a947-625b6473e4fe */
   public void setSysTableid(String sysTableid) {
   	this.sysTableid = sysTableid;
   }
   
   /** 获取 表的主键
    * 该字段是：表的主键
    * 
    * 
    * @pdOid 716c31d8-bc81-42e8-8723-e2258f5239bd */
   public String getSysTableid() {
   	return sysTableid;
   }
   
   /** 设置 表的描述
    * 该字段是：表的描述
    * 
    * @param sysTabledesc
    * @pdOid fbc9c10f-5a9c-41fe-96c9-9ee4e125ddf1 */
   public void setSysTabledesc(String sysTabledesc) {
   	this.sysTabledesc = sysTabledesc;
   }
   
   /** 获取 表的描述
    * 该字段是：表的描述
    * 
    * 
    * @pdOid e3d9bf47-80cf-4d53-aeea-f203ed257d10 */
   public String getSysTabledesc() {
   	return sysTabledesc;
   }
   
   /** 设置 初始化
    * 该字段是：初始化
    * 
    * @param sysInit
    * @pdOid ffe6fa12-8947-4697-bb1c-919dadd1a2a5 */
   public void setSysInit(Integer sysInit) {
   	this.sysInit = sysInit;
   }
   
   /** 获取 初始化
    * 该字段是：初始化
    * 
    * 
    * @pdOid 8f03f0cc-499d-4204-ae8f-8aecf0b250a3 */
   public Integer getSysInit() {
   	return sysInit;
   }
   
   /** 设置 更新
    * 该字段是：更新
    * 
    * @param sysBase
    * @pdOid 13a1df3f-35db-43d8-93d7-3ce9f1f1c810 */
   public void setSysBase(Integer sysBase) {
   	this.sysBase = sysBase;
   }
   
   /** 获取 更新
    * 该字段是：更新
    * 
    * 
    * @pdOid d368d353-7053-418e-a224-f9c56c4d6755 */
   public Integer getSysBase() {
   	return sysBase;
   }
   
   /** 设置 承包商人员
    * 该字段是：承包商人员
    * 
    * @param sysCbspeople
    * @pdOid 5cb6be31-6348-499c-86e6-e742f84d9bf6 */
   public void setSysCbspeople(Integer sysCbspeople) {
   	this.sysCbspeople = sysCbspeople;
   }
   
   /** 获取 承包商人员
    * 该字段是：承包商人员
    * 
    * 
    * @pdOid fde2e536-5507-4b1e-8479-001d50d423f5 */
   public Integer getSysCbspeople() {
   	return sysCbspeople;
   }
   
   /** 设置 时间戳
    * 该字段是：时间戳
    * 
    * @param sysTs
    * @pdOid 273e8e06-f80f-4b74-87e5-8d04754f2973 */
   public void setSysTs(String sysTs) {
   	this.sysTs = sysTs;
   }
   
   /** 获取 时间戳
    * 该字段是：时间戳
    * 
    * 
    * @pdOid c718c382-5b36-46fb-9079-ed82791d9df0 */
   public String getSysTs() {
   	return sysTs;
   }
   
   /** 设置 删除
    * 该字段是：删除
    * 
    * @param dr
    * @pdOid 139840ca-c44d-426c-b791-a10db599a93d */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除
    * 该字段是：删除
    * 
    * 
    * @pdOid 8e2db3ce-0478-4632-b774-2ce6842403b4 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 登录更新
    * 该字段是：登录更新
    * 
    * @param sysLogindateupdate
    * @pdOid c9dedffc-d07c-43d6-a980-1166ce6e905f */
   public void setSysLogindateupdate(Integer sysLogindateupdate) {
   	this.sysLogindateupdate = sysLogindateupdate;
   }
   
   /** 获取 登录更新
    * 该字段是：登录更新
    * 
    * 
    * @pdOid c19361cd-e299-4529-933a-2b006dc6e697 */
   public Integer getSysLogindateupdate() {
   	return sysLogindateupdate;
   }

}