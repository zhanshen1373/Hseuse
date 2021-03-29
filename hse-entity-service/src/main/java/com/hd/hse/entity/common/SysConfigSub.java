/**
 * File:    HseSysConfigSub.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysConfigSub
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 7234fc2f-b9b4-4fd2-88a3-1e41d7559991 */
@DBTable(tableName = "hse_sys_config_sub")
public class SysConfigSub extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 1eaee133-3024-427a-bf86-0f13bea43248 */
   private static final long serialVersionUID = 8553031692009379435L;
   /** 主键
    * 
    * @pdOid 9f392927-18bc-4eb0-87e7-482351be1d69 */
   @DBField(id=true)
   private Integer sysid;
   /** 编码
    * 
    * @pdOid fc1b4bee-182c-434b-a0a5-25c32cfd19dc */
   @DBField
   private String syscode;
   /** Maximo
    * 
    * @pdOid 576bdd14-b544-42f3-a0b9-07c064a683f0 */
   @DBField
   private String maximoplatform;
   /** 描述
    * 
    * @pdOid 41a1b404-ea0f-443e-9062-aae5edc9d4a9 */
   @DBField
   private String sysdesc;
   /** 老平台
    * 
    * @pdOid 44119aac-a29a-417f-a338-68115de9cfb6 */
   @DBField
   private String oldplatform;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param sysid
    * @pdOid f07bde69-c07a-4bfe-b437-db97d64d3232 */
   public void setSysid(Integer sysid) {
   	this.sysid = sysid;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid 9e43bd26-173a-4b8d-ad57-396e962ad26b */
   public Integer getSysid() {
   	return sysid;
   }
   
   /** 设置 编码
    * 该字段是：编码
    * 
    * @param syscode
    * @pdOid 8b56ff84-8590-47ac-af46-6e939bff1c58 */
   public void setSyscode(String syscode) {
   	this.syscode = syscode;
   }
   
   /** 获取 编码
    * 该字段是：编码
    * 
    * 
    * @pdOid 54da9dc1-87be-432d-b500-95d047245b1b */
   public String getSyscode() {
   	return syscode;
   }
   
   /** 设置 Maximo
    * 该字段是：Maximo
    * 
    * @param maximoplatform
    * @pdOid 576c98dc-5967-40d7-b123-64053f8c4179 */
   public void setMaximoplatform(String maximoplatform) {
   	this.maximoplatform = maximoplatform;
   }
   
   /** 获取 Maximo
    * 该字段是：Maximo
    * 
    * 
    * @pdOid 595772df-866b-4bfe-abaa-0b3aa6e78207 */
   public String getMaximoplatform() {
   	return maximoplatform;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param sysdesc
    * @pdOid 0adf926d-2ebd-4ad1-9854-d87284f8e1a8 */
   public void setSysdesc(String sysdesc) {
   	this.sysdesc = sysdesc;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 78f739bf-f272-49de-ae1e-bba65eaeb32e */
   public String getSysdesc() {
   	return sysdesc;
   }
   
   /** 设置 老平台
    * 该字段是：老平台
    * 
    * @param oldplatform
    * @pdOid 90d1e6e5-0ca9-4bb4-ad74-aec6e7a09bcc */
   public void setOldplatform(String oldplatform) {
   	this.oldplatform = oldplatform;
   }
   
   /** 获取 老平台
    * 该字段是：老平台
    * 
    * 
    * @pdOid b2b63956-fee1-4695-a0c1-077ae4b69f0a */
   public String getOldplatform() {
   	return oldplatform;
   }

}