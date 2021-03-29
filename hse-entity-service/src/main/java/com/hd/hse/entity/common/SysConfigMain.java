/**
 * File:    HseSysConfig.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysConfig
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 60dfebc1-4682-4e65-b683-e72c41620c44 */
@DBTable(tableName = "hse_sys_config")
public class SysConfigMain extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid d7a8affc-fc2c-4e95-a5c0-e7c9e2f827a6 */
   private static final long serialVersionUID = -1138863049964578980L;
   /** 编码
    * 
    * @pdOid ea04a547-b575-4bd6-adca-5b98bab067d8 */
   @DBField(id=true)
   private String syscode;
   /** 描述
    * 
    * @pdOid 11699c52-9c0e-4f3f-8b98-96b7f66336ac */
   @DBField
   private String sysdesc;
   /** url
    * 
    * @pdOid 7eda0c7b-8d59-4167-8c75-b0a0c8122601 */
   @DBField
   private String sysurl;
   /** 是否启用
    * 
    * @pdOid 1dda1a4b-16df-42a7-a9a4-4c6dee8c3866 */
   @DBField
   private Integer enable;
   
   /** 设置 编码
    * 该字段是：编码
    * 
    * @param syscode
    * @pdOid 9829bf42-fbee-4566-971b-b890dbcd3072 */
   public void setSyscode(String syscode) {
   	this.syscode = syscode;
   }
   
   /** 获取 编码
    * 该字段是：编码
    * 
    * 
    * @pdOid d29147e5-a046-4bfb-a2ee-2da5cc5d94b8 */
   public String getSyscode() {
   	return syscode;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param sysdesc
    * @pdOid 17aabb2a-5127-42c4-8b73-04927da11f7c */
   public void setSysdesc(String sysdesc) {
   	this.sysdesc = sysdesc;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 1b733f70-ed81-4382-b45b-947707c8609e */
   public String getSysdesc() {
   	return sysdesc;
   }
   
   /** 设置 url
    * 该字段是：url
    * 
    * @param sysurl
    * @pdOid 451de34f-5e26-40ee-8ee3-ec83a1abed9f */
   public void setSysurl(String sysurl) {
   	this.sysurl = sysurl;
   }
   
   /** 获取 url
    * 该字段是：url
    * 
    * 
    * @pdOid 75bd44c7-5469-4beb-9a9f-d727977ccbd3 */
   public String getSysurl() {
   	return sysurl;
   }
   
   /** 设置 是否启用
    * 该字段是：是否启用
    * 
    * @param enable
    * @pdOid b9c13ff6-ca45-4fb4-8444-eff80b1c0a78 */
   public void setEnable(Integer enable) {
   	this.enable = enable;
   }
   
   /** 获取 是否启用
    * 该字段是：是否启用
    * 
    * 
    * @pdOid 2af34a1f-46fa-4d18-9eb8-e4affe5899d0 */
   public Integer getEnable() {
   	return enable;
   }

}