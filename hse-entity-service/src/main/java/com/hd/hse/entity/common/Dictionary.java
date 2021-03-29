/**
 * File:    Alndomain.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 Alndomain
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 62cef918-e954-48b0-b0be-6b17f39779a2 */
@DBTable(tableName = "alndomain")
public class Dictionary extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 9a1a8bcd-efd9-43a5-ae57-5b769bc4a5d0 */
   private static final long serialVersionUID = -393548000386425556L;
   /** 主键
    * 
    * @pdOid 5b117155-3050-478a-8dfd-6070185e23c3 */
   @DBField(id=true)
   private String domainid;
   /** 编码
    * 
    * @pdOid b13e8086-f943-4a14-8076-5c334b6d1231 */
   @DBField
   private String value;
   /** 名称
    * 
    * @pdOid cf0bf110-1597-4fa6-b7c1-8e614f33abc7 */
   @DBField
   private String description;
   /** 标记
    * 
    * @pdOid 4748c86e-04e4-4c8e-b53b-5fe377c03040 */
   @DBField
   private Integer tag;
   /** 删除
    * 
    * @pdOid 73df314c-b55d-4ec6-ba54-e1e9f2941324 */
   @DBField
   private Integer dr;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param domainid
    * @pdOid 23a418dc-a576-4e8b-85e0-1616571e50bf */
   public void setDomainid(String domainid) {
   	this.domainid = domainid;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid 00a9e8b7-cfff-417d-9b80-2b547a7f78b5 */
   public String getDomainid() {
   	return domainid;
   }
   
   /** 设置 编码
    * 该字段是：编码
    * 
    * @param value
    * @pdOid 814f9679-b387-4e44-b434-56eb1f6d954d */
   public void setValue(String value) {
   	this.value = value;
   }
   
   /** 获取 编码
    * 该字段是：编码
    * 
    * 
    * @pdOid 602b1b95-261c-4b49-abec-fb6d6f965bf0 */
   public String getValue() {
   	return value;
   }
   
   /** 设置 名称
    * 该字段是：名称
    * 
    * @param description
    * @pdOid 90a6de00-c849-4294-a694-2364a845b1ef */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 名称
    * 该字段是：名称
    * 
    * 
    * @pdOid ca7d516d-c94f-4e59-94d7-574479e5f5da */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 714b0231-f351-42c6-b367-2231308625ed */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid da43667f-11a1-4447-8e59-b2b1d36a6aaf */
   public Integer getTag() {
   	return tag;
   }
   
   /** 设置 删除
    * 该字段是：删除
    * 
    * @param dr
    * @pdOid 96729e6f-18ce-4628-95b4-f7f34cb23d60 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除
    * 该字段是：删除
    * 
    * 
    * @pdOid 172e2cbd-1910-46ec-8b3c-78f951b9d833 */
   public Integer getDr() {
   	return dr;
   }

}