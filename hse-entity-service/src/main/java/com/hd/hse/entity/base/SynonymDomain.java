/**
 * Project Name:hse-entity-service
 * File Name:SynonymDomain.java
 * Package Name:com.hd.hse.entity.base
 * Date:2014年10月8日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** ClassName:SynonymDomain (同义词域).<br/>
 * Date:     2014年10月8日  <br/>
 * 
 * @author   lg
 * @version
 * @see
 * 
 * @pdOid 1d97644a-f397-4a9b-b194-e1cda590c408 */
@DBTable(tableName = "synonymdomain")
public class SynonymDomain extends com.hd.hse.common.entity.SuperEntity {
   /** serialVersionUID:TODO().
    * 
    * 
    * @pdOid 65d3d901-750b-4fec-b5f4-e1eccdcba2de */
   private static final long serialVersionUID = -3779461877965437950L;
   /** 主键
    * 
    * @pdOid 38597cc4-f0e8-4d19-a77c-447085a3fbca */
   @DBField(id=true)
   private String domainid;
   /** 编码
    * 
    * @pdOid a62c6daa-ff1a-4f49-8137-624570652082 */
   @DBField
   private String value;
   /** 名称
    * 
    * @pdOid 8b66aa2b-3f60-44a3-9ffb-44ae2258bf25 */
   @DBField
   private String description;
   /** 标记
    * 
    * @pdOid ba46f3a7-9cfa-487d-8e1c-d6365e3a86fb */
   @DBField
   private Integer tag;
   /** 删除
    * 
    * @pdOid f26c726e-3c90-4ccc-9091-d544976bbde7 */
   @DBField
   private Integer dr;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param domainid
    * @pdOid 7302d7d7-6ca8-4d48-8b74-7b2801436bb3 */
   public void setDomainid(String domainid) {
   	this.domainid = domainid;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid 7ebface1-3c27-429d-b3f0-3ddf1cedc5a7 */
   public String getDomainid() {
   	return domainid;
   }
   
   /** 设置 编码
    * 该字段是：编码
    * 
    * @param value
    * @pdOid 25500221-d60d-44bf-a3d9-cea49606d638 */
   public void setValue(String value) {
   	this.value = value;
   }
   
   /** 获取 编码
    * 该字段是：编码
    * 
    * 
    * @pdOid b0024776-5fd9-4273-9e76-518d84ecc510 */
   public String getValue() {
   	return value;
   }
   
   /** 设置 名称
    * 该字段是：名称
    * 
    * @param description
    * @pdOid 8595646e-1192-4c33-969d-2c89b264da81 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 名称
    * 该字段是：名称
    * 
    * 
    * @pdOid 6c8db529-1dba-4916-ab48-848dc13c239a */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid db70581c-b2dc-45f7-af9a-1af0f6e97053 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid 70da5534-083a-4e77-b0f8-9659cf798258 */
   public Integer getTag() {
   	return tag;
   }
   
   /** 设置 删除
    * 该字段是：删除
    * 
    * @param dr
    * @pdOid a26a31f1-dd54-4e1e-be21-bedabf9311ed */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 删除
    * 该字段是：删除
    * 
    * 
    * @pdOid a1b61b5b-bafc-4d40-9647-77b0f680548c */
   public Integer getDr() {
   	return dr;
   }

}