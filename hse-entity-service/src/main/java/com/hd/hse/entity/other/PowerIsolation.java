/**
 * File:    UdZyxkNlgld.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkNlgld
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid ba56badd-2825-4e9e-8190-a8ecd2c61762 */
@DBTable(tableName = "ud_zyxk_nlgld")
public class PowerIsolation extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid d2149beb-2ccf-4b13-a30f-b0bad5bdbaa0 */
   private static final long serialVersionUID = -5602168454967022657L;
   /** 主键
    * 
    * @pdOid 35132484-2c63-4d7c-95f0-737f95e91ca0 */
   @DBField(id=true)
   private Integer ud_zyxk_nlgldid;
   /** 所属单位
    * 
    * @pdOid 5789d719-7e06-4c33-904b-0c7d75621126 */
   @DBField
   private String ssdept;
   /** 装置位置
    * 
    * @pdOid ae515461-61e7-4ed0-80d0-8ca2b9b802ef */
   @DBField
   private String zzlocation;
   /** 隔离设备
    * 
    * @pdOid 99a5eaa0-159c-4214-8edc-1dcb1e846d0a */
   @DBField
   private String glasset;
   /** 隔离状态
    * 
    * @pdOid 6c77acdb-8e15-4762-b566-536d0c1c622a */
   @DBField
   private String glstatus;
   /** 状态
    * 
    * @pdOid cfe2e272-9a9e-46bd-805d-24c43f9d6313 */
   @DBField
   private String status;
   /** 危害
    * 
    * @pdOid 6f8450d5-5c75-4a0a-be57-b70a2b0ef7de */
   @DBField
   private String hazard;
   /** 危害描述
    * 
    * @pdOid b875a3f4-1ee8-41ac-b1a5-628ce20ad294 */
   @DBField
   private String hazard_desc;
   /** 作业地点
    * 
    * @pdOid e8091950-9afb-4ba3-b566-3ee76e2003d1 */
   @DBField
   private String zylocation;
   /** 编写人
    * 
    * @pdOid 1bc9857b-8b19-4267-828d-57df989da932 */
   @DBField
   private String bxperson;
   /** 测试人
    * 
    * @pdOid 3902e892-5de8-49f8-a4eb-de8d7e4887d1 */
   @DBField
   private String csperson;
   /** 作业人
    * 
    * @pdOid a31ed5e4-cb2b-4924-9718-0e104d220434 */
   @DBField
   private String zyperson;
   /** 批准人
    * 
    * @pdOid 32f79f8a-5083-4fd9-9bfc-dc520718a1bc */
   @DBField
   private String pzperson;
   /** 批准时间
    * 
    * @pdOid bb0a6a4a-d412-47d4-9b63-6eaaf92c756c */
   @DBField
   private String pztime;
   /** 能量隔离单号
    * 
    * @pdOid 411c52bc-874f-4957-be7b-a2be1890493c */
   @DBField
   private String nlgldnum;
   /** a1
    * 
    * @pdOid a9150eac-62ec-491c-bb1a-509b1567daa1 */
   @DBField
   private Integer isupload;
   
   /** @return the ud_zyxk_nlgldid
    * 
    * @pdOid d4299993-ff19-47ae-98e1-445a1cd30834 */
   public Integer getUd_zyxk_nlgldid() {
   	return ud_zyxk_nlgldid;
   }
   
   /** @param ud_zyxk_nlgldid the ud_zyxk_nlgldid to set
    * @pdOid 955da26f-1556-4bad-b4d0-8df0850cc9d5 */
   public void setUd_zyxk_nlgldid(Integer ud_zyxk_nlgldid) {
   	this.ud_zyxk_nlgldid = ud_zyxk_nlgldid;
   }
   
   /** 设置 所属单位
    * 该字段是：所属单位
    * 
    * @param ssdept
    * @pdOid bc1951c3-2711-4ace-b592-179f9cbd318a */
   public void setSsdept(String ssdept) {
   	this.ssdept = ssdept;
   }
   
   /** 获取 所属单位
    * 该字段是：所属单位
    * 
    * 
    * @pdOid 4b306e71-16f0-4117-81dd-1a2eafc963ff */
   public String getSsdept() {
   	return ssdept;
   }
   
   /** 设置 装置位置
    * 该字段是：装置位置
    * 
    * @param zzlocation
    * @pdOid 4440c335-ff97-4b36-b1d7-3beb63f50559 */
   public void setZzlocation(String zzlocation) {
   	this.zzlocation = zzlocation;
   }
   
   /** 获取 装置位置
    * 该字段是：装置位置
    * 
    * 
    * @pdOid f6dbd1bf-f878-4e10-952e-9f27c3219872 */
   public String getZzlocation() {
   	return zzlocation;
   }
   
   /** 设置 隔离设备
    * 该字段是：隔离设备
    * 
    * @param glasset
    * @pdOid 191ab492-3a00-4515-a315-ab9f555dd414 */
   public void setGlasset(String glasset) {
   	this.glasset = glasset;
   }
   
   /** 获取 隔离设备
    * 该字段是：隔离设备
    * 
    * 
    * @pdOid 67dfc76e-5e84-4909-882b-e97104fb27d8 */
   public String getGlasset() {
   	return glasset;
   }
   
   /** 设置 隔离状态
    * 该字段是：隔离状态
    * 
    * @param glstatus
    * @pdOid e4269cc0-420c-4a5e-80cc-2bace6776885 */
   public void setGlstatus(String glstatus) {
   	this.glstatus = glstatus;
   }
   
   /** 获取 隔离状态
    * 该字段是：隔离状态
    * 
    * 
    * @pdOid 3bd838ae-75fd-4607-95aa-d8d9234febbc */
   public String getGlstatus() {
   	return glstatus;
   }
   
   /** 设置 状态
    * 该字段是：状态
    * 
    * @param status
    * @pdOid d5660c91-6bcc-4529-b683-1d23b6a52e17 */
   public void setStatus(String status) {
   	this.status = status;
   }
   
   /** 获取 状态
    * 该字段是：状态
    * 
    * 
    * @pdOid fbe0680c-d883-44db-9e70-badf29f2d0e6 */
   public String getStatus() {
   	return status;
   }
   
   /** 设置 危害
    * 该字段是：危害
    * 
    * @param hazard
    * @pdOid 497ca197-3576-464f-a148-1e7da28673da */
   public void setHazard(String hazard) {
   	this.hazard = hazard;
   }
   
   /** 获取 危害
    * 该字段是：危害
    * 
    * 
    * @pdOid 7293fdd8-3045-4270-9d73-36edb1e394cd */
   public String getHazard() {
   	return hazard;
   }
   
   /** @return the hazard_desc
    * 
    * @pdOid b6296f99-c156-48ff-9f9b-d2d41aa3ec29 */
   public String getHazard_desc() {
   	return hazard_desc;
   }
   
   /** @param hazard_desc the hazard_desc to set
    * @pdOid 82f7b1b0-bba3-4dd2-ae76-b04c3cd53a7a */
   public void setHazard_desc(String hazard_desc) {
   	this.hazard_desc = hazard_desc;
   }
   
   /** 设置 作业地点
    * 该字段是：作业地点
    * 
    * @param zylocation
    * @pdOid 2a51f33a-7229-43da-9050-c0f1f0af8950 */
   public void setZylocation(String zylocation) {
   	this.zylocation = zylocation;
   }
   
   /** 获取 作业地点
    * 该字段是：作业地点
    * 
    * 
    * @pdOid e19f97bf-567e-419b-a936-f1ebc1a57daa */
   public String getZylocation() {
   	return zylocation;
   }
   
   /** 设置 编写人
    * 该字段是：编写人
    * 
    * @param bxperson
    * @pdOid d9715902-3030-47ad-8104-a64a378f8e5c */
   public void setBxperson(String bxperson) {
   	this.bxperson = bxperson;
   }
   
   /** 获取 编写人
    * 该字段是：编写人
    * 
    * 
    * @pdOid 2f70485f-bc32-4094-8786-140df95a34d6 */
   public String getBxperson() {
   	return bxperson;
   }
   
   /** 设置 测试人
    * 该字段是：测试人
    * 
    * @param csperson
    * @pdOid 6f1ab60a-7612-4685-b4cc-2f582a44ccc9 */
   public void setCsperson(String csperson) {
   	this.csperson = csperson;
   }
   
   /** 获取 测试人
    * 该字段是：测试人
    * 
    * 
    * @pdOid 3021a920-3536-48f1-a176-b415b4077687 */
   public String getCsperson() {
   	return csperson;
   }
   
   /** 设置 作业人
    * 该字段是：作业人
    * 
    * @param zyperson
    * @pdOid 591b8ff8-ad7f-42f6-85e1-76dadf5b6d09 */
   public void setZyperson(String zyperson) {
   	this.zyperson = zyperson;
   }
   
   /** 获取 作业人
    * 该字段是：作业人
    * 
    * 
    * @pdOid 596122e9-dd1f-45ec-8acc-9617f96086cf */
   public String getZyperson() {
   	return zyperson;
   }
   
   /** 设置 批准人
    * 该字段是：批准人
    * 
    * @param pzperson
    * @pdOid 3f6ee5b5-0b2e-48ad-b464-5419d54c5fdb */
   public void setPzperson(String pzperson) {
   	this.pzperson = pzperson;
   }
   
   /** 获取 批准人
    * 该字段是：批准人
    * 
    * 
    * @pdOid a09b865b-f5ee-4266-8da4-0356f0f6c24c */
   public String getPzperson() {
   	return pzperson;
   }
   
   /** 设置 批准时间
    * 该字段是：批准时间
    * 
    * @param pztime
    * @pdOid 88909683-3ec2-4d03-8ac6-f103943222a8 */
   public void setPztime(String pztime) {
   	this.pztime = pztime;
   }
   
   /** 获取 批准时间
    * 该字段是：批准时间
    * 
    * 
    * @pdOid 1752a757-2d2b-48a0-8305-2ae335a69dd2 */
   public String getPztime() {
   	return pztime;
   }
   
   /** 设置 能量隔离单号
    * 该字段是：能量隔离单号
    * 
    * @param nlgldnum
    * @pdOid 455a58a5-565c-4c3d-921a-f44d91645670 */
   public void setNlgldnum(String nlgldnum) {
   	this.nlgldnum = nlgldnum;
   }
   
   /** 获取 能量隔离单号
    * 该字段是：能量隔离单号
    * 
    * 
    * @pdOid ba0c3684-dca5-4d38-a83f-df9162da6aa9 */
   public String getNlgldnum() {
   	return nlgldnum;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param isupload
    * @pdOid ad3650cd-a4c4-4fac-ac9e-222ac93eed99 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid ac72f4c3-6b22-436b-9660-2a3f39508572 */
   public Integer getIsupload() {
   	return isupload;
   }

}