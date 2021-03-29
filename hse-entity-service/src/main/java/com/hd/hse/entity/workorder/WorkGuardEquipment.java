/**
 * File:    UdZyxkZysq2udZyxkGrfhzb.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZysq2udZyxkGrfhzb
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 0e388073-d608-4a16-8256-aa33f95f4207 */
@DBTable(tableName = "ud_zyxk_zysq2ud_zyxk_grfhzb")
public class WorkGuardEquipment extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 6e92d097-d1ab-419c-8b73-043758ed6bbd */
   private static final long serialVersionUID = 8236250351605754485L;
   /** 唯一外键
    * 
    * @pdOid db9a5278-5523-4ced-9cff-9e2327bfd691 */
   @DBField(foreign=true)
   private String ud_zyxk_zysqid;
   /** 唯一标识
    * 
    * @pdOid da044df6-74fa-4774-9e62-8a5e5b30caf2 */
   @DBField(id=true)
   private String ud_zyxk_zysq2ud_zyxk_grfhzbid;
   /** 描述
    * 
    * @pdOid 0f3ebba5-ebd3-4018-93fb-b500f1bc2926 */
   @DBField
   private String description;
   /** 是否被选上
    * 
    * @pdOid 815ba163-9d5a-452a-a04e-93e2cf5c4b65 */
   @DBField
   private Integer isselect;
   /** 是否确认
    * 
    * @pdOid 3533c27b-100b-4398-af44-d87f8210cec3 */
   @DBField
   private Integer value;
   /** 个人防护装备编号
    * 
    * @pdOid c3f5cad7-0611-4618-8e1e-62ff58950daa */
   @DBField
   private String grfhzbnum;
   /** 上传
    * 
    * @pdOid d68eb416-214e-4168-a864-04faa81f5761 */
   @DBField
   private Integer isupload;
   /** a2
    * 
    * @pdOid e1a72b11-a093-43e5-95d7-e734481a2227 */
   @DBField
   private Integer paixu;
   /** 是否伪删除
    * 
    * @pdOid 72092045-3e09-4e92-8a99-3c1d684753e4 */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid 2478b272-92eb-483d-b4d2-159ec7cc5868 */
   @DBField
   private Integer tag;
   /**  是否pad选中
    * 
    * @pdOid 1ff1a208-5198-4f19-8e4c-c90c341c4d72 */
   @DBField
   private Integer ispadselect;
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 0d23bb79-2a94-4706-a963-fb9400827583 */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid c49d9cce-3a05-4c70-8469-84ae049fc0d9 */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid 9e82b7fa-54e8-445b-8a01-5cb6a5ccc83a */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 28e05d56-d659-4b68-94a4-7875668dcd14 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 是否被选上
    * 该字段是：是否被选上
    * 
    * @param isselect
    * @pdOid 0c87c53c-d75d-4e99-8e57-217217105b74 */
   public void setIsselect(Integer isselect) {
   	this.isselect = isselect;
   }
   
   /** 获取 是否被选上
    * 该字段是：是否被选上
    * 
    * 
    * @pdOid e17e69ac-ad9b-4a9a-b505-e8957be4253b */
   public Integer getIsselect() {
   	return isselect;
   }
   
   /** 设置 是否确认
    * 该字段是：是否确认
    * 
    * @param value
    * @pdOid 7cbe9300-2f4e-4c29-9062-2f219f120e7b */
   public void setValue(Integer value) {
   	this.value = value;
   }
   
   /** 获取 是否确认
    * 该字段是：是否确认
    * 
    * 
    * @pdOid 0ac98c2f-df07-4569-9164-3e1e58336dc1 */
   public Integer getValue() {
   	return value;
   }
   
   /** 设置 个人防护装备编号
    * 该字段是：个人防护装备编号
    * 
    * @param grfhzbnum
    * @pdOid 463821d7-ffd2-4e52-bf6b-b9e24470130a */
   public void setGrfhzbnum(String grfhzbnum) {
   	this.grfhzbnum = grfhzbnum;
   }
   
   /** 获取 个人防护装备编号
    * 该字段是：个人防护装备编号
    * 
    * 
    * @pdOid ab60c7ee-b1ce-43d1-9708-6573e1d36a2a */
   public String getGrfhzbnum() {
   	return grfhzbnum;
   }
   
   /** 设置 上传
    * 该字段是：上传
    * 
    * @param isupload
    * @pdOid d435ec38-76be-4ac7-93ee-43b09eb35df4 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 上传
    * 该字段是：上传
    * 
    * 
    * @pdOid a5e1eef0-5acf-4583-bb83-e04204adee43 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param paixu
    * @pdOid c371bf69-33b2-4706-babf-84c743e7daef */
   public void setPaixu(Integer paixu) {
   	this.paixu = paixu;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid 5c0098aa-27de-45e6-8875-563764fa5f6e */
   public Integer getPaixu() {
   	return paixu;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid eac83a06-1701-4a84-8f17-c12609f6e50f */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid 960536ba-9d96-4b92-8f3e-e0b7044e3a53 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 5cfc6e78-0506-4f83-8fd2-6795e80b9621 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid 587183d8-9415-42b4-b410-54b646fcc1f2 */
   public Integer getTag() {
   	return tag;
   }
   
   /** @pdOid b4053492-1136-4c49-a49a-61cc58589630 */
   public Integer getIspadselect() {
   	return ispadselect;
   }
   
   /** @param ispadselect
    * @pdOid 14cf2a50-9a29-4e8a-aa75-7a50f8a86a2f */
   public void setIspadselect(Integer ispadselect) {
   	this.ispadselect = ispadselect;
   }
   
   /** @pdOid 56df88e1-da69-4177-a2e9-2fe6f2862556 */
   public String getUd_zyxk_zysq2ud_zyxk_grfhzbid() {
   	return ud_zyxk_zysq2ud_zyxk_grfhzbid;
   }
   
   /** @param ud_zyxk_zysq2ud_zyxk_grfhzbid
    * @pdOid c3068ee5-218b-4a31-9c79-0cdaccbedf05 */
   public void setUd_zyxk_zysq2ud_zyxk_grfhzbid(String ud_zyxk_zysq2ud_zyxk_grfhzbid) 		{
   	this.ud_zyxk_zysq2ud_zyxk_grfhzbid = ud_zyxk_zysq2ud_zyxk_grfhzbid;
   }

}