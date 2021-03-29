/**
 * File:    Precaution.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 Precaution
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 69f48c04-135f-45ab-892f-0c25f342ccea */
@DBTable(tableName = "precaution")
public class Precaution extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 721c866a-bfd0-409b-9029-e4ca50eeef3a */
   private static final long serialVersionUID = 7668641812471447883L;
   /** 预防措施
    * 
    * @pdOid 2994db8f-a209-4843-8a70-356cab13a71e */
   @DBField
   private String precautionid;
   /** 描述
    * 
    * @pdOid 4debc0c2-9b27-427c-a8f1-13c173be1382 */
   @DBField
   private String description;
   /** a1
    * 
    * @pdOid 55742187-07e0-4e15-a905-c33897b1e2bb */
   @DBField
   private String udwttype;
   /** a2
    * 
    * @pdOid e359d0f0-b68d-460a-afe6-4556c8a32c22 */
   @DBField
   private String udcsclass;
   /** 同危害识别表
    * 
    * @pdOid d453ed91-be77-40df-a03d-bbe158bcea0f */
   @DBField
   private Integer hazardid;
   
   /** 设置 预防措施
    * 该字段是：预防措施
    * 
    * @param precautionid
    * @pdOid d30b3c71-650a-4091-b884-44a8a6a1c891 */
   public void setPrecautionid(String precautionid) {
   	this.precautionid = precautionid;
   }
   
   /** 获取 预防措施
    * 该字段是：预防措施
    * 
    * 
    * @pdOid a1546618-ac00-4893-9a45-6cd94e6669cd */
   public String getPrecautionid() {
   	return precautionid;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid 80e20c5e-f66d-47da-bd65-f4288e9819c9 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 809e5989-26c2-41d5-bd2f-fe6214b7f024 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param udwttype
    * @pdOid 2f3f89c4-4fff-4ed6-85e2-5b383bdaf58a */
   public void setUdwttype(String udwttype) {
   	this.udwttype = udwttype;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid 2fa83c09-c588-471a-aa7b-61e0bfe61f2b */
   public String getUdwttype() {
   	return udwttype;
   }
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param udcsclass
    * @pdOid 7d109e40-fc57-4c24-aae1-7977e9367054 */
   public void setUdcsclass(String udcsclass) {
   	this.udcsclass = udcsclass;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid 31ad17ac-49ae-4453-badb-c89a14c8c0c9 */
   public String getUdcsclass() {
   	return udcsclass;
   }
   
   /** 设置 同危害识别表
    * 该字段是：同危害识别表
    * 
    * @param hazardid
    * @pdOid 9a33ddde-8f6e-4586-83e4-984b4af5411c */
   public void setHazardid(Integer hazardid) {
   	this.hazardid = hazardid;
   }
   
   /** 获取 同危害识别表
    * 该字段是：同危害识别表
    * 
    * 
    * @pdOid 973aea7f-bbfa-4daf-a5b7-a6e7369a76ff */
   public Integer getHazardid() {
   	return hazardid;
   }

}