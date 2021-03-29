/**
 * File:    UdZyxkGrfhzb.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkGrfhzb
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 6c0d32b2-9259-4df7-b71b-76b8422acc6b */
@DBTable(tableName = "ud_zyxk_grfhzb")
public class PersonGuardEquipment extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 77810261-49e6-43ff-85a9-c0daf617a13c */
   private static final long serialVersionUID = 1176580135911829806L;
   /** 唯一标识
    * 
    * @pdOid 8a4785a7-7168-4a7c-86ff-b37b74d4ab42 */
   @DBField(id=true)
   private Integer ud_zyxk_grfhzbid;
   /** a2
    * 
    * @pdOid f4691b89-15bd-4f20-a9d3-148455a536be */
   @DBField
   private String wttype;
   /** 个人防护装备编号
    * 
    * @pdOid cdae3671-9648-4370-9406-c64031a847a6 */
   @DBField
   private String grfhzbnum;
   /** 描述
    * 
    * @pdOid 402f492a-0520-4c4d-832a-4228bc485fd5 */
   @DBField
   private String description;
   /** 类别
    * 
    * @pdOid 66c8c612-403b-4010-9dfd-3a3ac3cf5743 */
   @DBField
   private String type;
   /** a6
    * 
    * @pdOid 960f4bb6-1856-4df0-a549-68ecd99e2b0b */
   @DBField
   private Integer ud_zyxk_zysqid;
   /** 默认选中
    * 
    * @pdOid f5d2c7e0-ba98-4ce6-b352-981e138b76eb */
   @DBField
   private Integer isselect;
   /** 是否伪删除
    * 
    * @pdOid 63e32297-a977-4af1-ba86-1bf2b2a045b9 */
   @DBField
   private Integer dr;
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param wttype
    * @pdOid 45870f8f-98d5-4aa9-b2af-60d17b167b97 */
   public void setWttype(String wttype) {
   	this.wttype = wttype;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid d658ef89-7985-4f8f-a078-128c6642ebff */
   public String getWttype() {
   	return wttype;
   }
   
   /** 设置 个人防护装备编号
    * 该字段是：个人防护装备编号
    * 
    * @param grfhzbnum
    * @pdOid a9b9aefc-2f15-4eba-b4c9-d2e5946cfcba */
   public void setGrfhzbnum(String grfhzbnum) {
   	this.grfhzbnum = grfhzbnum;
   }
   
   /** 获取 个人防护装备编号
    * 该字段是：个人防护装备编号
    * 
    * 
    * @pdOid 0bf3c9b4-4125-4888-ac18-58302a5e86fe */
   public String getGrfhzbnum() {
   	return grfhzbnum;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid c8524d43-b126-41e0-9241-64215fdfaaaf */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 3ef9d302-903e-4b95-8d47-2c69dcb89934 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 类别
    * 该字段是：类别
    * 
    * @param type
    * @pdOid 59b81630-efe3-43ad-9b8e-d1c773dab202 */
   public void setType(String type) {
   	this.type = type;
   }
   
   /** 获取 类别
    * 该字段是：类别
    * 
    * 
    * @pdOid b4fc155c-1f25-4576-97ac-be96346838aa */
   public String getType() {
   	return type;
   }
   
   /** 设置 默认选中
    * 该字段是：默认选中
    * 
    * @param isselect
    * @pdOid 03f411e4-401e-404b-98ae-bbf956b347e7 */
   public void setIsselect(Integer isselect) {
   	this.isselect = isselect;
   }
   
   /** 获取 默认选中
    * 该字段是：默认选中
    * 
    * 
    * @pdOid 463c7bbd-3f8d-424a-b991-044950f2f177 */
   public Integer getIsselect() {
   	return isselect;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 74825191-ce22-486a-8609-298b81bb87ea */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid 715c1484-ac3a-4a0f-ba17-c690cbce074a */
   public Integer getDr() {
   	return dr;
   }
   
   /** @pdOid dcfd7c95-febc-4b42-a014-1ae507ffcaf1 */
   public Integer getUd_zyxk_grfhzbid() {
   	return ud_zyxk_grfhzbid;
   }
   
   /** @param ud_zyxk_grfhzbid
    * @pdOid 37cf4e33-3f49-4e62-9cd7-66f939f6a578 */
   public void setUd_zyxk_grfhzbid(Integer ud_zyxk_grfhzbid) {
   	this.ud_zyxk_grfhzbid = ud_zyxk_grfhzbid;
   }
   
   /** @pdOid e06fd337-6682-4689-8408-d10d7e4a5b25 */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid
    * @pdOid c61e22b9-e5ad-4481-8ff1-52895c763d04 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }

}