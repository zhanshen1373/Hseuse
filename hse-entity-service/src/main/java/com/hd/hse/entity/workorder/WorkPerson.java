/**
 * File:    UdZyxkRypjline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkRypjline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 99954e84-f9e6-45d9-b3fd-2efd1660582a */
@DBTable(tableName = "ud_zyxk_rypjline")
public class WorkPerson extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 6f6f717d-072c-4118-8ca9-fd833059e192 */
   private static final long serialVersionUID = -1526091562025782676L;
   /** 主键
    * 
    * @pdOid 16e1cb4c-28d8-4248-8e16-8ea3eb1f895c */
   @DBField(id=true)
   private String ud_zyxk_rypjlineid;
   /** 描述
    * 
    * @pdOid 50f9891c-7ce6-4066-a18d-dfaabeb69412 */
   @DBField
   private String description;
   /** 父级外键
    * 
    * @pdOid 811ea2a7-7471-42b2-b87d-ed6f6ccbc579 */
   @DBField(foreign=true)
   private String ud_zyxk_rypjid;
   /** 关联项唯一序号
    * 
    * @pdOid 84b140d9-146e-4a18-9a11-9fcaf70921a3 */
   @DBField
   private Integer Ud_zyxk_jsapjid;
   /** 评价结果
    * 
    * @pdOid 4b281af5-105c-4145-8b28-563987e8283a */
   @DBField
   private String content;
   /** 备用2
    * 
    * @pdOid a3f3ffdd-1663-4df1-86e3-c77dcaba0ac5 */
   @DBField
   private float score;
   /** 显示排序
    * 
    * @pdOid 409e308c-12ea-49bc-8955-a289bbc33974 */
   @DBField
   private Integer ordernum;
   /** 备用1
    * 
    * @pdOid df67b4d8-4aee-4875-addf-1eab4e067475 */
   @DBField
   private String remark;
   /** a1
    * 
    * @pdOid b165d834-4d64-412c-9529-673118d47927 */
   @DBField
   private String datasource;
   /** 理解程度ID
    * 
    * @pdOid 86e0b39d-b7c1-45c9-9e48-b36b01ce62f5 */
   @DBField
   private Integer ud_zyxk_jsapjlineid;
   /** 评价类型
    * 
    * @pdOid 4fb736a9-3340-4880-9c3c-23ab56723fa0 */
   @DBField
   private String pjtype;
   /** 人员评价编号
    * 
    * @pdOid ab5bf7f0-ec86-4e22-a27d-2b453ac3fc5e */
   @DBField
   private String ud_zyxk_rypjnum;
   
   /** @return the ud_zyxk_rypjlineid
    * 
    * @pdOid c068f915-d7a6-417d-842e-bc9532ee1c68 */
   public String getUd_zyxk_rypjlineid() {
   	return ud_zyxk_rypjlineid;
   }
   
   /** @param ud_zyxk_rypjlineid the ud_zyxk_rypjlineid to set
    * @pdOid 91dc7971-3037-4445-9d47-47251674a863 */
   public void setUd_zyxk_rypjlineid(String ud_zyxk_rypjlineid) {
   	this.ud_zyxk_rypjlineid = ud_zyxk_rypjlineid;
   }
   
   /** @return the description
    * 
    * @pdOid f575466e-462a-44a5-bd3d-9ef179719084 */
   public String getDescription() {
   	return description;
   }
   
   /** @param description the description to set
    * @pdOid 091e090d-ce47-4a9d-877e-a5a895253423 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** @return the ud_zyxk_rypjid
    * 
    * @pdOid 45adf546-ecca-43cf-acf4-65d25c5713e9 */
   public String getUd_zyxk_rypjid() {
   	return ud_zyxk_rypjid;
   }
   
   /** @param ud_zyxk_rypjid the ud_zyxk_rypjid to set
    * @pdOid b5801a11-0229-4b1d-bae0-b86c082d6f25 */
   public void setUd_zyxk_rypjid(String ud_zyxk_rypjid) {
   	this.ud_zyxk_rypjid = ud_zyxk_rypjid;
   }
   
   /** @return the ud_zyxk_jsapjid
    * 
    * @pdOid cf3aa816-5efc-4da6-ba22-2358349f8ade */
   public Integer getUd_zyxk_jsapjid() {
   	return Ud_zyxk_jsapjid;
   }
   
   /** @param ud_zyxk_jsapjid the ud_zyxk_jsapjid to set
    * @pdOid 321e9ca0-6bcc-4edc-bc9d-f174a618ea76 */
   public void setUd_zyxk_jsapjid(Integer ud_zyxk_jsapjid) {
   	Ud_zyxk_jsapjid = ud_zyxk_jsapjid;
   }
   
   /** @return the content
    * 
    * @pdOid 0ef069a9-bd18-4c69-9610-69ed9a43feb5 */
   public String getContent() {
   	return content;
   }
   
   /** @param content the content to set
    * @pdOid c5483adf-b16a-49cd-8cba-7ade09b7eb8d */
   public void setContent(String content) {
   	this.content = content;
   }
   
   /** @return the score
    * 
    * @pdOid 906a7163-cf5c-4507-9f97-0680a036132d */
   public float getScore() {
   	return score;
   }
   
   /** @param score the score to set
    * @pdOid 30cf61ba-30a3-47ae-9a61-7a6d9b9d715d */
   public void setScore(float score) {
   	this.score = score;
   }
   
   /** @return the ordernum
    * 
    * @pdOid 76791c2b-936d-4f92-8d87-dc2e92d5c12b */
   public Integer getOrdernum() {
   	return ordernum;
   }
   
   /** @param ordernum the ordernum to set
    * @pdOid c3f993b2-3948-44ac-a661-ac8c8945c811 */
   public void setOrdernum(Integer ordernum) {
   	this.ordernum = ordernum;
   }
   
   /** @return the remark
    * 
    * @pdOid 6171db72-ebc1-4a88-9a9c-42b3760120db */
   public String getRemark() {
   	return remark;
   }
   
   /** @param remark the remark to set
    * @pdOid e364d69e-33c7-4f9d-92de-7370f9d8164e */
   public void setRemark(String remark) {
   	this.remark = remark;
   }
   
   /** @return the datasource
    * 
    * @pdOid 44610bb2-625e-4ffb-a41e-4bbc4fd3a894 */
   public String getDatasource() {
   	return datasource;
   }
   
   /** @param datasource the datasource to set
    * @pdOid 8db12ffa-b477-4bf3-b098-a428778e6829 */
   public void setDatasource(String datasource) {
   	this.datasource = datasource;
   }
   
   /** @return the ud_zyxk_jsapjlineid
    * 
    * @pdOid ba361a85-f74f-4b05-844d-954afda83c46 */
   public Integer getUd_zyxk_jsapjlineid() {
   	return ud_zyxk_jsapjlineid;
   }
   
   /** @param ud_zyxk_jsapjlineid the ud_zyxk_jsapjlineid to set
    * @pdOid 45bf6872-7437-4659-83d7-9e28991c8878 */
   public void setUd_zyxk_jsapjlineid(Integer ud_zyxk_jsapjlineid) {
   	this.ud_zyxk_jsapjlineid = ud_zyxk_jsapjlineid;
   }
   
   /** @return the pjtype
    * 
    * @pdOid a4fb3688-7f8d-4cbc-b81b-37d2a2d20778 */
   public String getPjtype() {
   	return pjtype;
   }
   
   /** @param pjtype the pjtype to set
    * @pdOid adf6c6f9-ac56-4ad9-95e6-c895c54a60a5 */
   public void setPjtype(String pjtype) {
   	this.pjtype = pjtype;
   }
   
   /** @return the ud_zyxk_rypjnum
    * 
    * @pdOid b30de501-1a05-414f-a03a-e5cafa93ee06 */
   public String getUd_zyxk_rypjnum() {
   	return ud_zyxk_rypjnum;
   }
   
   /** @param ud_zyxk_rypjnum the ud_zyxk_rypjnum to set
    * @pdOid 7ecb593c-607f-4f03-8bb9-39ace63790df */
   public void setUd_zyxk_rypjnum(String ud_zyxk_rypjnum) {
   	this.ud_zyxk_rypjnum = ud_zyxk_rypjnum;
   }

}