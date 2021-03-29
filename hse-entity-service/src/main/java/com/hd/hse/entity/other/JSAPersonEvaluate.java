/**
 * File:    UdZyxkJsapj.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkJsapj
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 759e657c-af79-4a1c-9d84-1d6d49f840ae */
@DBTable(tableName = "ud_zyxk_jsapj")
public class JSAPersonEvaluate extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid eae4d439-b36c-4db3-94ee-ce30fe00f2a5 */
   private static final long serialVersionUID = 3199717036175477269L;
   /** 唯一序号
    * 
    * @pdOid 009c81d9-4d1c-46a3-a33e-03834c110496 */
   @DBField
   private Integer ud_zyxk_jsapjid;
   /** 评价项目
    * 
    * @pdOid 09158a43-49cc-43f5-a043-f2f10770fb87 */
   @DBField
   private String description;
   /** Ispx
    * 
    * @pdOid f92afe84-6bbb-49f7-9c59-c5f72eb2ea5b */
   @DBField
   private Integer ispx;
   /** 备用1
    * 
    * @pdOid fc1059f0-40cc-4356-935f-04d2e56b09a4 */
   @DBField
   private String content;
   /** 备用
    * 
    * @pdOid 81fea9af-ef23-4443-a937-99564d283f74 */
   @DBField
   private String score;
   /** a6
    * 
    * @pdOid 658786eb-5abf-4d1a-9769-b212df0406d6 */
   @DBField
   private Integer dr;
   
   /** @return the ud_zyxk_jsapjid
    * 
    * @pdOid 2d7da30a-4310-4fe1-aff5-bc057b379b35 */
   public Integer getUd_zyxk_jsapjid() {
   	return ud_zyxk_jsapjid;
   }
   
   /** @param ud_zyxk_jsapjid the ud_zyxk_jsapjid to set
    * @pdOid 744012de-31d6-4b11-81e5-f0f132b63287 */
   public void setUd_zyxk_jsapjid(Integer ud_zyxk_jsapjid) {
   	this.ud_zyxk_jsapjid = ud_zyxk_jsapjid;
   }
   
   /** 设置 评价项目
    * 该字段是：评价项目
    * 
    * @param description
    * @pdOid 160ce55c-5c5b-4329-aea6-9efac31fd3c2 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 评价项目
    * 该字段是：评价项目
    * 
    * 
    * @pdOid dd9a75fa-97e5-4bd4-9abe-13f89064e628 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 Ispx
    * 该字段是：Ispx
    * 
    * @param ispx
    * @pdOid 8279c3ae-575a-4fa4-8f3f-c8a819535814 */
   public void setIspx(Integer ispx) {
   	this.ispx = ispx;
   }
   
   /** 获取 Ispx
    * 该字段是：Ispx
    * 
    * 
    * @pdOid b3092964-8997-446c-9c04-c7fe5ddbbba9 */
   public Integer getIspx() {
   	return ispx;
   }
   
   /** 设置 备用1
    * 该字段是：备用1
    * 
    * @param content
    * @pdOid ad16edb2-b949-43eb-8ac0-7f4f3d23238a */
   public void setContent(String content) {
   	this.content = content;
   }
   
   /** 获取 备用1
    * 该字段是：备用1
    * 
    * 
    * @pdOid 560333d3-e058-4500-bacd-efde87e2ad3e */
   public String getContent() {
   	return content;
   }
   
   /** 设置 备用
    * 该字段是：备用
    * 
    * @param score
    * @pdOid 2fa21591-6f48-4d05-9d8d-7d31f7aaf2ae */
   public void setScore(String score) {
   	this.score = score;
   }
   
   /** 获取 备用
    * 该字段是：备用
    * 
    * 
    * @pdOid dcc4fd1e-833e-4a2d-89cc-4a35df5c6058 */
   public String getScore() {
   	return score;
   }
   
   /** 设置 a6
    * 该字段是：a6
    * 
    * @param dr
    * @pdOid ae1c7830-e630-4144-bbfc-4309fc7998c3 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 a6
    * 该字段是：a6
    * 
    * 
    * @pdOid 64f1b52d-1683-46a9-b0a7-31c870b9dcff */
   public Integer getDr() {
   	return dr;
   }

}