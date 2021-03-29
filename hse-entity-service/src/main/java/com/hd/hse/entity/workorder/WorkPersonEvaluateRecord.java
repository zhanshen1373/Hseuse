/**
 * File:    UdZyxkRypj.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkRypj
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 04418d9c-27a7-4d8b-b7d2-253b9b9582fa */
@DBTable(tableName = "ud_zyxk_rypj")
public class WorkPersonEvaluateRecord extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid c34a1748-f903-4ba9-b466-98960092c8e2 */
   private static final long serialVersionUID = 8449651848083221309L;
   /** 主键
    * 
    * @pdOid 489d1824-d94d-48ec-bac6-f1ec073d68a8 */
   @DBField(id=true)
   private String ud_zyxk_rypjid;
   /** 描述
    * 
    * @pdOid b7077c23-2658-4ca9-a640-85e13a5a81d3 */
   @DBField
   private String description;
   /** 外键
    * 
    * @pdOid 1305b4a4-a47a-4212-94f1-e41daa2b3d5a */
   @DBField(foreign=true)
   private Integer ud_zyxk_jsaid;
   /** JSA评价外键
    * 
    * @pdOid 7e593a7d-9aea-4762-a89a-5a225ddadcbd */
   @DBField
   private Integer ud_zyxk_jsapjid;
   /** UD_ZYXK_RYPJ
    * 
    * @pdOid fc9ddc63-36d1-40a1-b456-b62240c33ba1 */
   @DBField
   private String content;
   /** 得分
    * 
    * @pdOid 248fd3df-8ce0-48c0-aec2-7d5586ede9ff */
   @DBField
   private float score;
   /** 显示排序
    * 
    * @pdOid 00a12ed6-b207-4c92-a87e-98dd547d921e */
   @DBField
   private Integer ordernum;
   /** 标记
    * 
    * @pdOid 588a7efc-d7b8-4c8e-a258-fe3e6e5ed968 */
   @DBField
   private String remark;
   /** a1
    * 
    * @pdOid 9e64f4bf-db6f-4983-98fa-fcf17947ac17 */
   @DBField
   private String datasource;
   /** 人员评价编号
    * 
    * @pdOid ba44c806-363f-4167-8b4f-400bf83c94f2 */
   @DBField
   private String ud_zyxk_rypjnum;
   
   /** @return the ud_zyxk_rypjid
    * 
    * @pdOid 974977bc-0325-46e1-9edf-4a82a9f207fd */
   public String getUd_zyxk_rypjid() {
   	return ud_zyxk_rypjid;
   }
   
   /** @param ud_zyxk_rypjid the ud_zyxk_rypjid to set
    * @pdOid 703fb381-6347-4b91-847a-7228476e62a2 */
   public void setUd_zyxk_rypjid(String ud_zyxk_rypjid) {
   	this.ud_zyxk_rypjid = ud_zyxk_rypjid;
   }
   
   /** @return the description
    * 
    * @pdOid f65fcb1d-33ee-4696-b1dd-78244dbf6173 */
   public String getDescription() {
   	return description;
   }
   
   /** @param description the description to set
    * @pdOid 8f81f85a-81d5-485f-8028-abd93cc69adb */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** @return the ud_zyxk_jsaid
    * 
    * @pdOid bdec1480-f91a-4be6-8d52-19b4dbaa3301 */
   public Integer getUd_zyxk_jsaid() {
   	return ud_zyxk_jsaid;
   }
   
   /** @param ud_zyxk_jsaid the ud_zyxk_jsaid to set
    * @pdOid 706f3dc9-2be5-4480-aa5d-ea7c9dcbd935 */
   public void setUd_zyxk_jsaid(Integer ud_zyxk_jsaid) {
   	this.ud_zyxk_jsaid = ud_zyxk_jsaid;
   }
   
   /** @return the ud_zyxk_jsapjid
    * 
    * @pdOid b3e11581-b91b-4999-976d-4466636f9f8b */
   public Integer getUd_zyxk_jsapjid() {
   	return ud_zyxk_jsapjid;
   }
   
   /** @param ud_zyxk_jsapjid the ud_zyxk_jsapjid to set
    * @pdOid 41213e35-ff9a-454a-bdb2-677eac6c8e8e */
   public void setUd_zyxk_jsapjid(Integer ud_zyxk_jsapjid) {
   	this.ud_zyxk_jsapjid = ud_zyxk_jsapjid;
   }
   
   /** @return the content
    * 
    * @pdOid ef10eee2-426c-409a-a594-e3350bbcc2e0 */
   public String getContent() {
   	return content;
   }
   
   /** @param content the content to set
    * @pdOid 08952b08-19ab-4f49-8c2a-e164a294233c */
   public void setContent(String content) {
   	this.content = content;
   }
   
   /** @return the score
    * 
    * @pdOid 66f26a5e-ec62-4e23-a6bd-990d7ad34752 */
   public float getScore() {
   	return score;
   }
   
   /** @param score the score to set
    * @pdOid 7d3c2504-991b-4e69-bd86-6eb5e42478ac */
   public void setScore(float score) {
   	this.score = score;
   }
   
   /** @return the ordernum
    * 
    * @pdOid 5cf579f3-622c-4bb2-8371-79eed04200ae */
   public Integer getOrdernum() {
   	return ordernum;
   }
   
   /** @param ordernum the ordernum to set
    * @pdOid 330f020f-0e8d-44d9-9ae6-2a050a48b511 */
   public void setOrdernum(Integer ordernum) {
   	this.ordernum = ordernum;
   }
   
   /** @return the remark
    * 
    * @pdOid a8394dd3-e230-4a76-b9a9-6788e7b747c2 */
   public String getRemark() {
   	return remark;
   }
   
   /** @param remark the remark to set
    * @pdOid 82c1253a-380e-414a-8205-b9fa242b512f */
   public void setRemark(String remark) {
   	this.remark = remark;
   }
   
   /** @return the datasource
    * 
    * @pdOid fea35a65-a8c8-49b6-8eb3-e638f616c47c */
   public String getDatasource() {
   	return datasource;
   }
   
   /** @param datasource the datasource to set
    * @pdOid 1736cc25-ccf0-4d22-9a1e-32721c9b5756 */
   public void setDatasource(String datasource) {
   	this.datasource = datasource;
   }
   
   /** @return the ud_zyxk_rypjnum
    * 
    * @pdOid be610e0a-b917-4509-b88b-27631411ddad */
   public String getUd_zyxk_rypjnum() {
   	return ud_zyxk_rypjnum;
   }
   
   /** @param ud_zyxk_rypjnum the ud_zyxk_rypjnum to set
    * @pdOid def27f21-0f7e-4295-98f1-2f9102d06515 */
   public void setUd_zyxk_rypjnum(String ud_zyxk_rypjnum) {
   	this.ud_zyxk_rypjnum = ud_zyxk_rypjnum;
   }

}