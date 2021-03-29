/**
 * File:    UdZyxkJsapjline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkJsapjline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 39cf5a70-a7e9-4948-b746-641a808f829e */
@DBTable(tableName = "ud_zyxk_jsapjline")
public class JSAPersonEvaluationRow extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid b6f04dc7-abb5-448d-92ee-e5f58fb11807 */
   private static final long serialVersionUID = -4511719014644395789L;
   /** 主键
    * 
    * @pdOid 249ce49e-df92-494a-8e2b-7e13fa310dce */
   @DBField(id=true)
   private Integer ud_zyxk_jsapjlineid;
   /** 描述
    * 
    * @pdOid e1ee9ef8-c895-46a5-a29f-091c5f09bc94 */
   @DBField
   private String description;
   /** 父级唯一序号
    * 
    * @pdOid e8eda502-7bf0-4768-ac1f-ec203550d124 */
   @DBField
   private Integer ud_zyxk_jsapjid;
   /** 分数
    * 
    * @pdOid fc6e0626-fd21-48ee-9861-9c335a26e6c9 */
   @DBField
   private Double score;
   /** 类型
    * 
    * @pdOid 2cffaef1-0d25-46c6-b146-b83ca45f21b7 */
   @DBField
   private String pjtype;
   /** 排序编号
    * 
    * @pdOid 5639424f-cd6a-4cd8-890f-b8336e745af9 */
   @DBField
   private Integer ispx;
   /** 是否伪删除
    * 
    * @pdOid 3406dbd3-f325-49a4-901d-80b232be08aa */
   @DBField
   private Integer dr;
   /** 备用
    * 
    * @pdOid 3805119b-83d2-4c48-93b0-cfa1ea34bb6a */
   @DBField
   private String content;
   
   /** @return the ud_zyxk_jsapjlineid
    * 
    * @pdOid 532114c6-cac0-450d-98cb-528190933366 */
   public Integer getUd_zyxk_jsapjlineid() {
   	return ud_zyxk_jsapjlineid;
   }
   
   /** @param ud_zyxk_jsapjlineid the ud_zyxk_jsapjlineid to set
    * @pdOid 68ef8baa-4db9-4fd4-b22c-c58e1748e6f0 */
   public void setUd_zyxk_jsapjlineid(Integer ud_zyxk_jsapjlineid) {
   	this.ud_zyxk_jsapjlineid = ud_zyxk_jsapjlineid;
   }
   
   /** @return the ud_zyxk_jsapjid
    * 
    * @pdOid 9848df86-a961-4440-8351-27e39fd0e8b0 */
   public Integer getUd_zyxk_jsapjid() {
   	return ud_zyxk_jsapjid;
   }
   
   /** @param ud_zyxk_jsapjid the ud_zyxk_jsapjid to set
    * @pdOid 0d749d69-3503-4b5f-85dd-3d23609eada6 */
   public void setUd_zyxk_jsapjid(Integer ud_zyxk_jsapjid) {
   	this.ud_zyxk_jsapjid = ud_zyxk_jsapjid;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid b5d822c6-e72e-460c-a5a5-f2797b8f55e3 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid d97923fb-8ea8-44ad-babf-9f8f86121967 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 分数
    * 该字段是：分数
    * 
    * @param score
    * @pdOid ddce5bdf-3323-48f6-86a7-e58f014d7ddc */
   public void setScore(Double score) {
   	this.score = score;
   }
   
   /** 获取 分数
    * 该字段是：分数
    * 
    * 
    * @pdOid a17bbb4f-1153-41ff-b3b0-c2a78eb03080 */
   public Double getScore() {
   	return score;
   }
   
   /** 设置 类型
    * 该字段是：类型
    * 
    * @param pjtype
    * @pdOid 87a55a10-74e8-4901-a2c6-88e6d126d1c9 */
   public void setPjtype(String pjtype) {
   	this.pjtype = pjtype;
   }
   
   /** 获取 类型
    * 该字段是：类型
    * 
    * 
    * @pdOid c9ae1069-6091-4478-9523-9fdf4554007b */
   public String getPjtype() {
   	return pjtype;
   }
   
   /** 设置 排序编号
    * 该字段是：排序编号
    * 
    * @param ispx
    * @pdOid 7851f79d-595b-41cc-9747-6ab24715b4da */
   public void setIspx(Integer ispx) {
   	this.ispx = ispx;
   }
   
   /** 获取 排序编号
    * 该字段是：排序编号
    * 
    * 
    * @pdOid f55a3a67-27ec-4a54-bc70-ba81efa4be3c */
   public Integer getIspx() {
   	return ispx;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid c0d6e4d6-7d82-4829-9991-156fcbd9841a */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid 2424b2b0-90f5-4066-83b2-b9e4588e6937 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 备用
    * 该字段是：备用
    * 
    * @param content
    * @pdOid 05f264a3-7e2c-492f-aa6f-66107be21148 */
   public void setContent(String content) {
   	this.content = content;
   }
   
   /** 获取 备用
    * 该字段是：备用
    * 
    * 
    * @pdOid 41f8278d-395e-49e4-9d15-ba1c89b33f48 */
   public String getContent() {
   	return content;
   }

}