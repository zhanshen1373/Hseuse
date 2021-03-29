/**
 * File:    HseSysLog.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysLog
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 00cff806-d95d-43b3-adf9-cdba1c46367c */
@DBTable(tableName = "hse_sys_log")
public class RecordLog extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid f743eae2-9d5b-4ef8-91a4-154a32b98628 */
   private static final long serialVersionUID = -6301248111290524846L;
   /** 主键
    * 
    * @pdOid 52bcecc8-1931-43a5-8178-d2ca31bea763 */
   @DBField(id=true)
   private Integer sysId;
   /** 编码
    * 
    * @pdOid d47397fe-b081-4379-8598-1a02e007fe5c */
   @DBField
   private String actioncode;
   /** 名称
    * 
    * @pdOid 74a1fe20-67a3-4447-af1e-76cf5045a4d3 */
   @DBField
   private String actionname;
   /** 动作类型
    * 
    * @pdOid 2c30d20c-9cd0-49c8-900c-e88e632ec4a7 */
   @DBField
   private String actiontype;
   /** 创建日期
    * 
    * @pdOid 2fb050be-ff66-4bf7-b025-801c843427d3 */
   @DBField
   private String createdate;
   /** 操作人名称
    * 
    * @pdOid 643cb0d1-cb77-4732-8345-5748e37024e9 */
   @DBField
   private String opusername;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param sysId
    * @pdOid 6669cbb3-1ea2-47bf-ac4b-fd66a7febea7 */
   public void setSysId(Integer sysId) {
   	this.sysId = sysId;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid 37e51ca2-08bb-4a81-8b90-7a553aa65061 */
   public Integer getSysId() {
   	return sysId;
   }
   
   /** 设置 编码
    * 该字段是：编码
    * 
    * @param actioncode
    * @pdOid 8419dd45-2526-450a-b461-bc0cea3e79eb */
   public void setActioncode(String actioncode) {
   	this.actioncode = actioncode;
   }
   
   /** 获取 编码
    * 该字段是：编码
    * 
    * 
    * @pdOid 707052a3-de81-4d0f-a1cf-a648b85e0ed4 */
   public String getActioncode() {
   	return actioncode;
   }
   
   /** 设置 名称
    * 该字段是：名称
    * 
    * @param actionname
    * @pdOid d1ac7c22-1717-4932-be60-641d2a2af477 */
   public void setActionname(String actionname) {
   	this.actionname = actionname;
   }
   
   /** 获取 名称
    * 该字段是：名称
    * 
    * 
    * @pdOid 5e4fba8e-9202-444a-ac56-ebe35adb6118 */
   public String getActionname() {
   	return actionname;
   }
   
   /** 设置 动作类型
    * 该字段是：动作类型
    * 
    * @param actiontype
    * @pdOid c814709c-183a-4dec-a44b-55285dccfbfb */
   public void setActiontype(String actiontype) {
   	this.actiontype = actiontype;
   }
   
   /** 获取 动作类型
    * 该字段是：动作类型
    * 
    * 
    * @pdOid 761522c5-d223-41dd-9687-d50aac0a3521 */
   public String getActiontype() {
   	return actiontype;
   }
   
   /** 设置 创建日期
    * 该字段是：创建日期
    * 
    * @param createdate
    * @pdOid 8df48e30-dca5-41f5-9329-0d13a500ca53 */
   public void setCreatedate(String createdate) {
   	this.createdate = createdate;
   }
   
   /** 获取 创建日期
    * 该字段是：创建日期
    * 
    * 
    * @pdOid 8cce9032-b0f6-4a77-9da5-138f29ff994e */
   public String getCreatedate() {
   	return createdate;
   }
   
   /** 设置 操作人名称
    * 该字段是：操作人名称
    * 
    * @param opusername
    * @pdOid 49bfddb2-b29c-402e-b5b3-1da1acc0f07f */
   public void setOpusername(String opusername) {
   	this.opusername = opusername;
   }
   
   /** 获取 操作人名称
    * 该字段是：操作人名称
    * 
    * 
    * @pdOid c062ff6f-4263-4067-9795-5866f1b83442 */
   public String getOpusername() {
   	return opusername;
   }

}