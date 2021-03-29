/**
 * File:    Persongroup.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 Persongroup
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 18dd5b75-b068-4ac5-8715-7317d2cb3739 */
@DBTable(tableName = "persongroup")
public class Persongroup extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 173bc840-37e0-4e1f-8c44-0dabbb039c44 */
   private static final long serialVersionUID = 4620846811404916799L;
   /** 人员组
    * 
    * @pdOid 8b76a80d-b3ab-42f5-abaf-7262ee8cd4b0 */
   @DBField
   private String persongroup;
   /** 描述
    * 
    * @pdOid d22bcd8d-c424-49f1-92cf-29394a28fe58 */
   @DBField
   private String description;
   /** 所属部门编码
    * 
    * @pdOid afe9f5ba-9476-463e-ad03-803ba6866cac */
   @DBField
   private String ssdept;
   /** 所属部门描述
    * 
    * @pdOid 9337ff55-8143-4b1f-a8d0-ac5524a800bf */
   @DBField
   private String ssdept_desc;
   /** 已删除部门
    * 
    * @pdOid cca4cca4-bead-46b9-95a2-1dd804045da0 */
   @DBField
   private String vdeldept;
   /** 删除标识
    * 
    * @pdOid 8fdacae8-331b-4c6c-a5db-1e17746fba1d */
   @DBField
   private Integer dr;
   /** 修改时间
    * 
    * @pdOid e7f814f6-9257-4ee6-9c20-6a9ee0f395d9 */
   @DBField
   private String changedate;
   /** 唯一标识
    * 
    * @pdOid 388a6276-da9b-4906-9861-12d966dbc721 */
   @DBField
   private String persongroupid;
   
   /** 设置 人员组
    * 该字段是：人员组
    * 
    * @param persongroup
    * @pdOid ff05c710-a044-451f-b44d-b98b7294fe46 */
   public void setPersongroup(String persongroup) {
   	this.persongroup = persongroup;
   }
   
   /** 获取 人员组
    * 该字段是：人员组
    * 
    * 
    * @pdOid cf3ad1e2-ea3f-452e-94ab-ca2574263193 */
   public String getPersongroup() {
   	return persongroup;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid 3ddd04ae-ec3f-4438-99ad-581955d4599e */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid a25ed8fe-a672-4e0c-bc31-41a7b3462d8e */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 所属部门编码
    * 该字段是：所属部门编码
    * 
    * @param ssdept
    * @pdOid 6f6a3c99-38db-463e-a4da-87b59d1a5540 */
   public void setSsdept(String ssdept) {
   	this.ssdept = ssdept;
   }
   
   /** 获取 所属部门编码
    * 该字段是：所属部门编码
    * 
    * 
    * @pdOid 60e73521-b168-4456-93c1-cd0f5e667dbc */
   public String getSsdept() {
   	return ssdept;
   }
   
   /** @return the ssdept_desc
    * 
    * @pdOid 7b2e4a71-bd4b-4ea0-b998-29436b0839fc */
   public String getSsdept_desc() {
   	return ssdept_desc;
   }
   
   /** @param ssdept_desc the ssdept_desc to set
    * @pdOid fb0b10f2-03e2-4895-8516-5af597c13a06 */
   public void setSsdept_desc(String ssdept_desc) {
   	this.ssdept_desc = ssdept_desc;
   }
   
   /** 设置 已删除部门
    * 该字段是：已删除部门
    * 
    * @param vdeldept
    * @pdOid 27abf23d-0ef7-4a44-9481-8711045e3a4b */
   public void setVdeldept(String vdeldept) {
   	this.vdeldept = vdeldept;
   }
   
   /** 获取 已删除部门
    * 该字段是：已删除部门
    * 
    * 
    * @pdOid 9ea35577-a489-423b-b159-33c97e7e6cd6 */
   public String getVdeldept() {
   	return vdeldept;
   }
   
   /** 设置 删除标识
    * 该字段是：删除标识
    * 
    * @param dr
    * @pdOid 927ead4d-cbb4-4d1b-9959-525251fb0065 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除标识
    * 该字段是：删除标识
    * 
    * 
    * @pdOid 7314d1eb-26e0-4442-aa76-078035a3335d */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 修改时间
    * 该字段是：修改时间
    * 
    * @param changedate
    * @pdOid 24967e17-bcc6-4ffd-9570-a71ec7249bcc */
   public void setChangedate(String changedate) {
   	this.changedate = changedate;
   }
   
   /** 获取 修改时间
    * 该字段是：修改时间
    * 
    * 
    * @pdOid 7701bf9e-ab9a-45b0-9733-b8d319d86d2d */
   public String getChangedate() {
   	return changedate;
   }
   
   /** 设置 唯一标识
    * 该字段是：唯一标识
    * 
    * @param persongroupid
    * @pdOid 0ef66d38-a3ea-4aa2-8d46-1cf811413218 */
   public void setPersongroupid(String persongroupid) {
   	this.persongroupid = persongroupid;
   }
   
   /** 获取 唯一标识
    * 该字段是：唯一标识
    * 
    * 
    * @pdOid 45f5c2a9-734b-46cd-882d-5e92f1a162d9 */
   public String getPersongroupid() {
   	return persongroupid;
   }

}