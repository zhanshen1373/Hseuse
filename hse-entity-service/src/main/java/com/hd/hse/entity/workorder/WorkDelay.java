/**
 * File:    UdZyxkZyyq.java
 * Author:  hd
 * Company: 
 * Created: 2014-10-16 20:19:40
 * Purpose: 定义数据类 UdZyxkZyyq
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import android.R.integer;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 8fcce005-a480-4772-819f-f0dfbfab4d33 */
@DBTable(tableName = "ud_zyxk_zyyq")
public class WorkDelay extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 0c5832cd-9a3a-42f4-b8f5-10526a7b0b2f */
   private static final long serialVersionUID = 7141249439400315417L;
   /** 主键
    * 
    * @pdOid 90a569c6-f730-404e-b308-b2de47bbc885 */
   @DBField(id=true)
   private String ud_zyxk_zyyqid;
   /** 作业申请id
    * 
    * @pdOid 32867bf5-c3ab-49ae-9097-88a2a2909290 */
   @DBField
   private String ud_zyxk_zysqid;
   /** 描述
    * 
    * @pdOid 5e46357e-5089-439e-81d5-4e722a394941 */
   @DBField
   private String description;
   /** 延期开始时间
    * 
    * @pdOid 9380c803-1779-4bd1-98e5-3d1a3be9fbfd */
   @DBField
   private String yqstarttime;
   /** 延期结束时间
    * 
    * @pdOid e26faf57-fb9c-49a0-87ca-0a44e3c7126f */
   @DBField
   private String yqendtime;
   /** 延期申请人
    * 
    * @pdOid 5e461ac3-2d25-41f6-b334-d1bc8dff912a */
   @DBField
   private String yqsqperson;
   /** 相关方
    * 
    * @pdOid f8d1f961-eed6-46af-8438-18e57fdca06c */
   @DBField
   private String xgfperson;
   /** 延期批准人
    * 
    * @pdOid 5b6a7df6-c410-4d61-8018-33aee8591f95 */
   @DBField
   private String yqpzperson;
   /** 监护人
    * 
    * @pdOid 1a8a5cd5-0aa8-48fc-93fe-3c35159da7df */
   @DBField
   private String jhperson;
   /** 属地单位
    * 
    * @pdOid 5f7b0f5a-b25c-44e9-9e7d-bbf61d08f217 */
   @DBField
   private String sddw;
   /** 供电单位
    * 
    * @pdOid ca92d5f9-43f2-49a3-a6ab-2c0f58066672 */
   @DBField
   private String gddw;
   /** 电器主管单位
    * 
    * @pdOid 11d2eb28-64e2-448a-8060-3b28a8412e32 */
   @DBField
   private String dqzgdw;
   /** 批准时间
    * 
    * @pdOid 5e041a38-c8c3-4200-a618-26b4a8c31943 */
   @DBField
   private String auditime;
   /** 是否上传
    * 
    * @pdOid e8f50230-8cc6-40d7-9d19-3a3064b05e59 */
   @DBField
   private Integer isupload;
   /** overtag
    * 
    * @pdOid 9f68bb32-839d-4725-aac7-2de1d1a5e1a5 */
   @DBField
   private Integer overtag;
   /** 属地监护人
    * 
    * @pdOid 4834e0d4-5485-41da-ab80-b9826d91f546 */
   @DBField
   private String chq_yqxdjhr;
   /** 作业单位监护人
    * 
    * @pdOid f803d256-e6e4-4e65-b881-ef8b761a76b5 */
   @DBField
   private String chq_yqzydwjhr;
   /** 作业申请复查编号
    * 
    * @pdOid d8bd2bfd-54e1-4ead-b9e7-d1f201ef540a */
   @DBField
   private String zycsfcnum;
   /** yqtepttime
    * 
    * @pdOid 0b98d883-5852-4f7d-96c5-9ad93042fb43 */
   @DBField
   private String yqtepttime;
   /** dr
    * 
    * @pdOid cb96d5cf-2358-454b-a9c9-bcaf6a0059cf */
   @DBField
   private Integer dr;
   /** tag
    * 
    * @pdOid 3a7bc698-152b-4eb3-948c-e05f911272c6 */
   @DBField
   private Integer tag;
   
   /** @return the ud_zyxk_zyyqid
    * 
    * @pdOid a8485e0d-913b-43a9-8061-7f3c0d53e6c3 */
   public String getUd_zyxk_zyyqid() {
   	return ud_zyxk_zyyqid;
   }
   
   /** @param ud_zyxk_zyyqid the ud_zyxk_zyyqid to set
    * @pdOid 70af8cf5-e1ec-49e0-903f-204488939190 */
   public void setUd_zyxk_zyyqid(String ud_zyxk_zyyqid) {
   	this.ud_zyxk_zyyqid = ud_zyxk_zyyqid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 620f9e0d-079e-451d-9271-0918a67630c5 */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid c4e1a67f-fbb9-4bca-8f4f-45c2832a935b */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the chq_yqxdjhr
    * 
    * @pdOid 343b9ef5-08f1-4b2c-b32a-ede5aad24dc6 */
   public String getChq_yqxdjhr() {
   	return chq_yqxdjhr;
   }
   
   /** @param chq_yqxdjhr the chq_yqxdjhr to set
    * @pdOid 745d04ab-5f4d-4c8e-bc84-8bebcc04a72b */
   public void setChq_yqxdjhr(String chq_yqxdjhr) {
   	this.chq_yqxdjhr = chq_yqxdjhr;
   }
   
   /** @return the chq_yqzydwjhr
    * 
    * @pdOid fc63a8a9-d4a5-4f94-b3cb-798e7c0c978d */
   public String getChq_yqzydwjhr() {
   	return chq_yqzydwjhr;
   }
   
   /** @param chq_yqzydwjhr the chq_yqzydwjhr to set
    * @pdOid 54c309a2-4af3-438f-bd4b-b84f32acee78 */
   public void setChq_yqzydwjhr(String chq_yqzydwjhr) {
   	this.chq_yqzydwjhr = chq_yqzydwjhr;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid c0c16945-ef4c-4d85-8b66-9e6f66c356cd */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid b5087269-85f1-4b8d-b021-706946005f68 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 延期开始时间
    * 该字段是：延期开始时间
    * 
    * @param yqstarttime
    * @pdOid 474726ce-6313-45e3-8c85-b583637423f8 */
   public void setYqstarttime(String yqstarttime) {
   	this.yqstarttime = yqstarttime;
   }
   
   /** 获取 延期开始时间
    * 该字段是：延期开始时间
    * 
    * 
    * @pdOid 0f257785-966a-4e2f-92e6-3e5b6bce7d70 */
   public String getYqstarttime() {
   	return yqstarttime;
   }
   
   /** 设置 延期结束时间
    * 该字段是：延期结束时间
    * 
    * @param yqendtime
    * @pdOid 6760333b-4bc0-4379-8f37-c78a34851344 */
   public void setYqendtime(String yqendtime) {
   	this.yqendtime = yqendtime;
   }
   
   /** 获取 延期结束时间
    * 该字段是：延期结束时间
    * 
    * 
    * @pdOid 47b214b7-4b6a-4be0-9e3a-c096685c5d25 */
   public String getYqendtime() {
   	return yqendtime;
   }
   
   /** 设置 延期申请人
    * 该字段是：延期申请人
    * 
    * @param yqsqperson
    * @pdOid d7288148-4cee-492f-a8dd-fceaa956f65b */
   public void setYqsqperson(String yqsqperson) {
   	this.yqsqperson = yqsqperson;
   }
   
   /** 获取 延期申请人
    * 该字段是：延期申请人
    * 
    * 
    * @pdOid e614fab7-f9ba-4a86-8d6f-a181b33e51ba */
   public String getYqsqperson() {
   	return yqsqperson;
   }
   
   /** 设置 相关方
    * 该字段是：相关方
    * 
    * @param xgfperson
    * @pdOid 7f6af3ce-9565-40ab-8a81-36c743ab8644 */
   public void setXgfperson(String xgfperson) {
   	this.xgfperson = xgfperson;
   }
   
   /** 获取 相关方
    * 该字段是：相关方
    * 
    * 
    * @pdOid 0c964d43-d4b8-4755-872a-d8a96713f09c */
   public String getXgfperson() {
   	return xgfperson;
   }
   
   /** 设置 延期批准人
    * 该字段是：延期批准人
    * 
    * @param yqpzperson
    * @pdOid 2816ad7c-a829-47b8-b55a-504036cfcd08 */
   public void setYqpzperson(String yqpzperson) {
   	this.yqpzperson = yqpzperson;
   }
   
   /** 获取 延期批准人
    * 该字段是：延期批准人
    * 
    * 
    * @pdOid 0ff0c84b-d907-4801-8fd7-86c20ac3c38d */
   public String getYqpzperson() {
   	return yqpzperson;
   }
   
   /** 设置 监护人
    * 该字段是：监护人
    * 
    * @param jhperson
    * @pdOid 2adee810-c587-4714-b1db-bd59037be33e */
   public void setJhperson(String jhperson) {
   	this.jhperson = jhperson;
   }
   
   /** 获取 监护人
    * 该字段是：监护人
    * 
    * 
    * @pdOid 9d47b9cb-01ba-4f5d-8355-c945b66f6bd4 */
   public String getJhperson() {
   	return jhperson;
   }
   
   /** 设置 属地单位
    * 该字段是：属地单位
    * 
    * @param sddw
    * @pdOid f247ecc2-cf90-43bf-b43e-d2ba9c5c2e6a */
   public void setSddw(String sddw) {
   	this.sddw = sddw;
   }
   
   /** 获取 属地单位
    * 该字段是：属地单位
    * 
    * 
    * @pdOid eb2cedee-e8e5-483c-85e0-c69a18819bb6 */
   public String getSddw() {
   	return sddw;
   }
   
   /** 设置 供电单位
    * 该字段是：供电单位
    * 
    * @param gddw
    * @pdOid 927ca565-7266-4e84-9d89-7216df94468a */
   public void setGddw(String gddw) {
   	this.gddw = gddw;
   }
   
   /** 获取 供电单位
    * 该字段是：供电单位
    * 
    * 
    * @pdOid d60976fe-c752-4da0-8cbb-9eee5f864467 */
   public String getGddw() {
   	return gddw;
   }
   
   /** 设置 电器主管单位
    * 该字段是：电器主管单位
    * 
    * @param dqzgdw
    * @pdOid 28d13790-0ba0-401c-b1c8-c2dcef8e6c15 */
   public void setDqzgdw(String dqzgdw) {
   	this.dqzgdw = dqzgdw;
   }
   
   /** 获取 电器主管单位
    * 该字段是：电器主管单位
    * 
    * 
    * @pdOid 933a987e-938c-4240-88cb-c036575233dc */
   public String getDqzgdw() {
   	return dqzgdw;
   }
   
   /** 设置 批准时间
    * 该字段是：批准时间
    * 
    * @param auditime
    * @pdOid 21451592-9bd0-443a-96ae-2ed2d665077c */
   public void setAuditime(String auditime) {
   	this.auditime = auditime;
   }
   
   /** 获取 批准时间
    * 该字段是：批准时间
    * 
    * 
    * @pdOid 2dde1cd9-1ecb-401e-bac2-a9c621bd6773 */
   public String getAuditime() {
   	return auditime;
   }
   
   /** 设置 是否上传
    * 该字段是：是否上传
    * 
    * @param isupload
    * @pdOid 56d06241-6222-4c43-9bdb-83b337ff7c5e */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 是否上传
    * 该字段是：是否上传
    * 
    * 
    * @pdOid b1a07663-4aa5-4368-b83f-d10060c93326 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 overtag
    * 该字段是：overtag
    * 
    * @param overtag
    * @pdOid f1114cab-5a89-44cd-bc8e-b14e0aa5118b */
   public void setOvertag(Integer overtag) {
   	this.overtag = overtag;
   }
   
   /** 获取 overtag
    * 该字段是：overtag
    * 
    * 
    * @pdOid a13c7d44-1d27-46be-8138-f6179110f25c */
   public Integer getOvertag() {
   	return overtag;
   }
   
   /** 设置 作业申请复查编号
    * 该字段是：作业申请复查编号
    * 
    * @param zycsfcnum
    * @pdOid d48d1b3f-eab7-4b7d-9745-cd49955ac05b */
   public void setZycsfcnum(String zycsfcnum) {
   	this.zycsfcnum = zycsfcnum;
   }
   
   /** 获取 作业申请复查编号
    * 该字段是：作业申请复查编号
    * 
    * 
    * @pdOid 73edc94c-d33d-48f3-812d-5409befea1c6 */
   public String getZycsfcnum() {
   	return zycsfcnum;
   }
   
   /** 设置 yqtepttime
    * 该字段是：yqtepttime
    * 
    * @param yqtepttime
    * @pdOid e610e982-6bc1-48b0-a2a4-4a439243c50b */
   public void setYqtepttime(String yqtepttime) {
   	this.yqtepttime = yqtepttime;
   }
   
   /** 获取 yqtepttime
    * 该字段是：yqtepttime
    * 
    * 
    * @pdOid 7cb26ca4-eaef-4f43-a074-6c900ceebd7b */
   public String getYqtepttime() {
   	return yqtepttime;
   }
   
   /** 设置 dr
    * 该字段是：dr
    * 
    * @param dr
    * @pdOid ae6d3440-6590-45c6-a3cb-33a91cfe5896 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 dr
    * 该字段是：dr
    * 
    * 
    * @pdOid a7dfc5ca-9c2f-455a-9712-7a4ed8d8e578 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 tag
    * 该字段是：tag
    * 
    * @param tag
    * @pdOid d77ee219-6c5c-4c87-a313-e715bbf60dfe */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 tag
    * 该字段是：tag
    * 
    * 
    * @pdOid f37333db-6fd7-4b7c-975e-e54821041d4e */
   public Integer getTag() {
   	return tag;
   }

}