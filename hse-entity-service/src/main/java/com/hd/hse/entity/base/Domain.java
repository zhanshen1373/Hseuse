/**
 * File:    Alndomain.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 Alndomain
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 6fcd61f1-8926-4c54-a158-8a7a9c6d550a */
@DBTable(tableName = "alndomain")
public class Domain extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid bea88f7a-fe5c-4f7c-af33-f1f26008daf7 */
   private static final long serialVersionUID = 6531034385583781841L;
   /** 域名
    * 
    * @pdOid 273344e1-b478-49c7-bea4-c4f87fdaeabb */
   @DBField
   private String domainid;
   /** 域值
    * 
    * @pdOid 2b04ef81-489e-468c-9b7e-42e7a6316c1f */
   @DBField
   private String value;
   /** 域描述
    * 
    * @pdOid 7747c6d1-6093-4143-8917-90a7b2eb9739 */
   @DBField
   private String description;
   /** 是否伪删除
    * 
    * @pdOid 8e84cd20-1202-42ef-9783-305351855f4e */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid 5a3db2ac-c1c9-4e04-9fef-3fafec63243f */
   @DBField
   private Integer tag;
   /** 是否被选中（用于多选）
    * 
    * @pdOid 4dfaad91-a08f-4bc8-bee9-c858d8f6312a */
   private int isselected;
   
   /** 设置 域名
    * 该字段是：域名
    * 
    * @param domainid
    * @pdOid 50ddb890-4e6c-4bf7-8056-37792f0deee4 */
   public void setDomainid(String domainid) {
   	this.domainid = domainid;
   }
   
   /** 获取 域名
    * 该字段是：域名
    * 
    * 
    * @pdOid c32a018c-8043-4c09-9fff-8f9c7298b63d */
   public String getDomainid() {
   	return domainid;
   }
   
   /** 设置 域值
    * 该字段是：域值
    * 
    * @param value
    * @pdOid 429b4ef0-6d42-45e4-89f6-57452761381f */
   public void setValue(String value) {
   	this.value = value;
   }
   
   /** 获取 域值
    * 该字段是：域值
    * 
    * 
    * @pdOid c8f55198-a5f5-48ff-bfc3-d462e8a65767 */
   public String getValue() {
   	return value;
   }
   
   /** 设置 域描述
    * 该字段是：域描述
    * 
    * @param description
    * @pdOid cd454775-bbfc-4aab-b5d7-7cfbd36ef7ca */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 域描述
    * 该字段是：域描述
    * 
    * 
    * @pdOid 0e5f993b-97f4-4f57-81d9-fe747ac4deaf */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 8a128c6a-dde4-4f32-bd2b-2d1c59c3d7c2 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid d29ae133-2cf9-43ef-b802-7a0e2ed60153 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 10c15dc6-246a-4834-87d5-5e593dc6e22a */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid c85808dc-5124-494b-af15-45f8ee808d25 */
   public Integer getTag() {
   	return tag;
   }
   
   /** @pdOid 2a9c8b91-7e7f-408f-9a35-568535e8450f */
   public int getIsselected() {
   	return isselected;
   }
   
   /** @param isselected
    * @pdOid 97bcf06f-3661-4a44-83e5-4036ffd2e27f */
   public void setIsselected(int isselected) {
   	this.isselected = isselected;
   }

}