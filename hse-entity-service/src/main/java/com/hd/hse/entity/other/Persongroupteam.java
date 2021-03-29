/**
 * File:    Persongroupteam.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 Persongroupteam
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid f536088d-4fb4-464a-aa16-0959efb39d2e */
@DBTable(tableName = "persongroupteam")
public class Persongroupteam extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid bcaf3737-b17c-4bd1-90ca-b3c5a624e771 */
   private static final long serialVersionUID = 1194368544911155130L;
   /** 人员组
    * 
    * @pdOid ee949b73-1f9f-4682-b7ea-87663cfa137c */
   @DBField
   private String persongroup;
   /** 人员
    * 
    * @pdOid 9e280144-8f4f-42a9-90a2-5924f1b6776f */
   @DBField
   private String resppartygroup;
   /** 删除标识
    * 
    * @pdOid 8570323e-ef74-46c9-a110-3712ccb67da7 */
   @DBField
   private Integer dr;
   /** 修改时间
    * 
    * @pdOid e3a79b4b-bde9-4a3e-a243-80036b916d11 */
   @DBField
   private String changedate;
   /** PERSONGROUPTEAMID
    * 
    * @pdOid 7cf276bc-ce12-4b9c-9a3b-0d6482af4c28 */
   @DBField
   private String persongroupteamid;
   
   /** 设置 人员组
    * 该字段是：人员组
    * 
    * @param persongroup
    * @pdOid 20707246-60c3-47ef-a552-345a4519e105 */
   public void setPersongroup(String persongroup) {
   	this.persongroup = persongroup;
   }
   
   /** 获取 人员组
    * 该字段是：人员组
    * 
    * 
    * @pdOid 50a77e43-3e0c-42a4-aab8-c2195e9efbcb */
   public String getPersongroup() {
   	return persongroup;
   }
   
   /** 设置 人员
    * 该字段是：人员
    * 
    * @param resppartygroup
    * @pdOid d2cee18c-7f37-43ee-b159-4d4a951ec62b */
   public void setResppartygroup(String resppartygroup) {
   	this.resppartygroup = resppartygroup;
   }
   
   /** 获取 人员
    * 该字段是：人员
    * 
    * 
    * @pdOid 9910325c-7ee1-4e66-9cdb-3428302656af */
   public String getResppartygroup() {
   	return resppartygroup;
   }
   
   /** 设置 删除标识
    * 该字段是：删除标识
    * 
    * @param dr
    * @pdOid 0a51b18c-4de1-4e02-a44a-3477a7b91384 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除标识
    * 该字段是：删除标识
    * 
    * 
    * @pdOid 762ce866-f6f0-450d-9cfa-71b4cb8ea0f0 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 修改时间
    * 该字段是：修改时间
    * 
    * @param changedate
    * @pdOid fc0ec7c0-77eb-434c-a364-f3952260b4b6 */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** 获取 修改时间
    * 该字段是：修改时间
    * 
    * 
    * @pdOid c13c590e-5ad6-4fd3-b67c-fce915cc79e4 */
   public String getChangedate() {
   	return changedate;
   }
   
   /** 设置 PERSONGROUPTEAMID
    * 该字段是：PERSONGROUPTEAMID
    * 
    * @param persongroupteamid
    * @pdOid 2be88f6f-c905-4280-8820-504b828c8463 */
   public void setPersongroupteamid(String persongroupteamid) {
   	this.persongroupteamid = persongroupteamid;
   }
   
   /** 获取 PERSONGROUPTEAMID
    * 该字段是：PERSONGROUPTEAMID
    * 
    * 
    * @pdOid 9e4067af-a48e-448a-8390-0ffae2640faf */
   public String getPersongroupteamid() {
   	return persongroupteamid;
   }

}