/**
 * File:    UdZyxkSxkj.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkSxkj
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 1a884e5f-962b-49d0-af19-d7cc7d8ffb82 */
@DBTable(tableName = "ud_zyxk_sxkj")
public class RestrictedSpace extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid e6a00742-da57-4d88-afb8-8fb31a906701 */
   private static final long serialVersionUID = 5809660279895981182L;
   /** 主键
    * 
    * @pdOid 176683e8-1a80-48df-9a6b-223bced24f4e */
   @DBField(id=true)
   private Integer udzyxksxkjid;
   /** 外键
    * 
    * @pdOid 84dce73e-e13a-4ef9-bfb4-2850933d9107 */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 受限空间类别
    * 
    * @pdOid dcaee792-b350-4190-9338-2de500a74839 */
   @DBField
   private String sxkjtype;
   /** 作业类型
    * 
    * @pdOid c9f79928-1c7a-4ed4-999f-44aad688e80f */
   @DBField
   private String zytype;
   /** 救援计划
    * 
    * @pdOid fd922ba8-a648-43ac-8ad8-d0622e04c9b4 */
   @DBField
   private String jyplan;
   /** 生产工艺、设备、环境风险(补充措施)
    * 
    * @pdOid decd8cd2-3557-4ec1-bbe6-831d4701ba70 */
   @DBField
   private String scgybccs;
   /** 施工作业风险(补充措施)
    * 
    * @pdOid 3f89f060-0670-4dd2-8e8d-455d1ac91df4 */
   @DBField
   private String sgzyfxbccs;
   /** 属地单位监护人
    * 
    * @pdOid a23e2653-4856-4bd7-88da-07c2dc7a984a */
   @DBField
   private String sddwjhperson;
   /** 作业单位监护人
    * 
    * @pdOid cc62e283-c047-48d9-b1bd-7031f16dfa13 */
   @DBField
   private String zydwjhperson;
   /** a1
    * 
    * @pdOid a5d9c82b-13b5-4b7a-9db9-d917098e0d9a */
   @DBField
   private String gyfzperson;
   /** a2
    * 
    * @pdOid abcfa211-d194-48bf-91b8-1bdb72369d29 */
   @DBField
   private String gydeptlevel;
   /** a3
    * 
    * @pdOid 9eedd03e-3e50-4ee8-88ec-652ff92c4f20 */
   @DBField
   private String gytime;
   /** a4
    * 
    * @pdOid 8ce18ec6-e569-4d31-9cc8-dd6da2eac995 */
   @DBField
   private String assetfzperson;
   /** a5
    * 
    * @pdOid fa4d8a54-4440-4166-8850-1ffe31da9a0f */
   @DBField
   private String assetdeptlevel;
   /** a6
    * 
    * @pdOid 5b7f58cd-7f51-4011-ba62-229739126dcd */
   @DBField
   private String assetdate;
   /** a7
    * 
    * @pdOid 055a4776-2fa8-467e-8bf9-8b40afdd466d */
   @DBField
   private String aqfzperson;
   /** a8
    * 
    * @pdOid 1cd9209f-5cbe-4f2a-a94d-b458b5dda53e */
   @DBField
   private String aqdeptlevel;
   /** a9
    * 
    * @pdOid 4aee1a70-afad-40df-b2da-bb3933cd2151 */
   @DBField
   private String aqtime;
   /** a10
    * 
    * @pdOid b391367e-053c-4f24-aeac-89527459fba2 */
   @DBField
   private String sddwfzperson;
   /** a11
    * 
    * @pdOid 531ac979-d438-4895-ad9c-59568809a682 */
   @DBField
   private String fczglead;
   /** a12
    * 
    * @pdOid a652565f-aa53-46da-a71e-87b5dedb7c87 */
   @DBField
   private String leadtime;
   
   /** 设置 主键
    * 该字段是：主键
    * 
    * @param udzyxksxkjid
    * @pdOid 24efb861-36f0-4863-ad13-41f26b8977e6 */
   public void setudzyxksxkjid(Integer udzyxksxkjid) {
   	this.udzyxksxkjid = udzyxksxkjid;
   }
   
   /** 获取 主键
    * 该字段是：主键
    * 
    * 
    * @pdOid c748c321-92d8-4afb-9052-d423ff4a6321 */
   public Integer getudzyxksxkjid() {
   	return udzyxksxkjid;
   }
   
   /** @return the udzyxksxkjid
    * 
    * @pdOid 6785df92-4fdd-491f-a171-c1b11e2e4686 */
   public Integer getUdzyxksxkjid() {
   	return udzyxksxkjid;
   }
   
   /** @param udzyxksxkjid the udzyxksxkjid to set
    * @pdOid e57c5148-55db-4ea7-834f-f818253bc825 */
   public void setUdzyxksxkjid(Integer udzyxksxkjid) {
   	this.udzyxksxkjid = udzyxksxkjid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 8365c296-1e9b-47a1-87c5-80bd23aece3f */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 2e2c426a-095b-4115-afcf-712ea97d56c3 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 受限空间类别
    * 该字段是：受限空间类别
    * 
    * @param sxkjtype
    * @pdOid c15182a3-7605-4cd4-9453-19bb3ab7efa8 */
   public void setSxkjtype(String sxkjtype) {
   	this.sxkjtype = sxkjtype;
   }
   
   /** 获取 受限空间类别
    * 该字段是：受限空间类别
    * 
    * 
    * @pdOid f59547fd-d1cf-412a-96d3-9be598fea7e6 */
   public String getSxkjtype() {
   	return sxkjtype;
   }
   
   /** 设置 作业类型
    * 该字段是：作业类型
    * 
    * @param zytype
    * @pdOid 6d8cf696-2866-453d-9abf-32047ce71338 */
   public void setZytype(String zytype) {
   	this.zytype = zytype;
   }
   
   /** 获取 作业类型
    * 该字段是：作业类型
    * 
    * 
    * @pdOid e51bb89c-24d0-4c41-afbd-a7085f847ddd */
   public String getZytype() {
   	return zytype;
   }
   
   /** 设置 救援计划
    * 该字段是：救援计划
    * 
    * @param jyplan
    * @pdOid faf60bd9-7124-4726-b96a-441271083fbe */
   public void setJyplan(String jyplan) {
   	this.jyplan = jyplan;
   }
   
   /** 获取 救援计划
    * 该字段是：救援计划
    * 
    * 
    * @pdOid a4823f3a-6827-4706-bd12-734b8146db0c */
   public String getJyplan() {
   	return jyplan;
   }
   
   /** 设置 生产工艺、设备、环境风险(补充措施)
    * 该字段是：生产工艺、设备、环境风险(补充措施)
    * 
    * @param scgybccs
    * @pdOid 317593a7-16d5-46c5-8e9d-73ce61fd1ab1 */
   public void setScgybccs(String scgybccs) {
   	this.scgybccs = scgybccs;
   }
   
   /** 获取 生产工艺、设备、环境风险(补充措施)
    * 该字段是：生产工艺、设备、环境风险(补充措施)
    * 
    * 
    * @pdOid 869cf0de-d9f4-48c1-bc10-0fe324b99fe5 */
   public String getScgybccs() {
   	return scgybccs;
   }
   
   /** 设置 施工作业风险(补充措施)
    * 该字段是：施工作业风险(补充措施)
    * 
    * @param sgzyfxbccs
    * @pdOid b9538052-f92a-49cc-a7b6-ae017269672c */
   public void setSgzyfxbccs(String sgzyfxbccs) {
   	this.sgzyfxbccs = sgzyfxbccs;
   }
   
   /** 获取 施工作业风险(补充措施)
    * 该字段是：施工作业风险(补充措施)
    * 
    * 
    * @pdOid 4670ce9f-06ff-4ae3-8288-170b4462c353 */
   public String getSgzyfxbccs() {
   	return sgzyfxbccs;
   }
   
   /** 设置 属地单位监护人
    * 该字段是：属地单位监护人
    * 
    * @param sddwjhperson
    * @pdOid e58ea5ef-673d-4e0a-8226-8298cbc26ec8 */
   public void setSddwjhperson(String sddwjhperson) {
   	this.sddwjhperson = sddwjhperson;
   }
   
   /** 获取 属地单位监护人
    * 该字段是：属地单位监护人
    * 
    * 
    * @pdOid cd2eb330-c1ca-4b3d-a728-cbc534ea73b7 */
   public String getSddwjhperson() {
   	return sddwjhperson;
   }
   
   /** 设置 作业单位监护人
    * 该字段是：作业单位监护人
    * 
    * @param zydwjhperson
    * @pdOid 9f8f14df-52b3-4d21-8773-1bc1c42002bd */
   public void setZydwjhperson(String zydwjhperson) {
   	this.zydwjhperson = zydwjhperson;
   }
   
   /** 获取 作业单位监护人
    * 该字段是：作业单位监护人
    * 
    * 
    * @pdOid a3856912-9b14-43e0-9177-5826eae5aba0 */
   public String getZydwjhperson() {
   	return zydwjhperson;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param gyfzperson
    * @pdOid 992d02c7-131f-478e-9fbb-e3f733a8b4c3 */
   public void setGyfzperson(String gyfzperson) {
   	this.gyfzperson = gyfzperson;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid eb332727-2796-427b-be8f-0d46b14f6c70 */
   public String getGyfzperson() {
   	return gyfzperson;
   }
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param gydeptlevel
    * @pdOid d2454575-d9ee-43d1-8823-9c1fe00c4a47 */
   public void setGydeptlevel(String gydeptlevel) {
   	this.gydeptlevel = gydeptlevel;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid 3fecaa39-8dfa-4e80-aea1-7330417147aa */
   public String getGydeptlevel() {
   	return gydeptlevel;
   }
   
   /** 设置 a3
    * 该字段是：a3
    * 
    * @param gytime
    * @pdOid d40a0258-0338-45ea-ba2b-5a189f1c2a1d */
   public void setGytime(String gytime) {
   	this.gytime = gytime;
   }
   
   /** 获取 a3
    * 该字段是：a3
    * 
    * 
    * @pdOid d4bf74fb-e01c-4633-bdf7-06e3fe34b1df */
   public String getGytime() {
   	return gytime;
   }
   
   /** 设置 a4
    * 该字段是：a4
    * 
    * @param assetfzperson
    * @pdOid a59e20a3-2a34-4a8c-9707-1dff34614ee2 */
   public void setAssetfzperson(String assetfzperson) {
   	this.assetfzperson = assetfzperson;
   }
   
   /** 获取 a4
    * 该字段是：a4
    * 
    * 
    * @pdOid b40c873b-554a-4341-af07-60307a9ce56c */
   public String getAssetfzperson() {
   	return assetfzperson;
   }
   
   /** 设置 a5
    * 该字段是：a5
    * 
    * @param assetdeptlevel
    * @pdOid d07e097c-2fc5-44d0-94a8-d11ab718155b */
   public void setAssetdeptlevel(String assetdeptlevel) {
   	this.assetdeptlevel = assetdeptlevel;
   }
   
   /** 获取 a5
    * 该字段是：a5
    * 
    * 
    * @pdOid 4c1a78a5-859f-4322-b20e-d8415f407a34 */
   public String getAssetdeptlevel() {
   	return assetdeptlevel;
   }
   
   /** 设置 a6
    * 该字段是：a6
    * 
    * @param assetdate
    * @pdOid d9e38392-3cfd-4c0d-ab7e-817df2571b62 */
   public void setAssetdate(String assetdate) {
   	this.assetdate = assetdate;
   }
   
   /** 获取 a6
    * 该字段是：a6
    * 
    * 
    * @pdOid 4d1fe6d0-5158-436e-9fbe-23e45417c3a9 */
   public String getAssetdate() {
   	return assetdate;
   }
   
   /** 设置 a7
    * 该字段是：a7
    * 
    * @param aqfzperson
    * @pdOid 46d2a643-f427-400f-83ab-a17ff8c65c6d */
   public void setAqfzperson(String aqfzperson) {
   	this.aqfzperson = aqfzperson;
   }
   
   /** 获取 a7
    * 该字段是：a7
    * 
    * 
    * @pdOid d39b9179-f4e8-47a1-af3c-91a312e30468 */
   public String getAqfzperson() {
   	return aqfzperson;
   }
   
   /** 设置 a8
    * 该字段是：a8
    * 
    * @param aqdeptlevel
    * @pdOid d97da4f8-a9c3-4995-b17d-aa38deb9a4cb */
   public void setAqdeptlevel(String aqdeptlevel) {
   	this.aqdeptlevel = aqdeptlevel;
   }
   
   /** 获取 a8
    * 该字段是：a8
    * 
    * 
    * @pdOid 0d47c7f2-7b26-468e-af13-4779497ecd50 */
   public String getAqdeptlevel() {
   	return aqdeptlevel;
   }
   
   /** 设置 a9
    * 该字段是：a9
    * 
    * @param aqtime
    * @pdOid f112b1da-4431-4dbc-aced-1f4c7be8c867 */
   public void setAqtime(String aqtime) {
   	this.aqtime = aqtime;
   }
   
   /** 获取 a9
    * 该字段是：a9
    * 
    * 
    * @pdOid 4535e3dc-a281-4fc0-904b-f71ef98ecf56 */
   public String getAqtime() {
   	return aqtime;
   }
   
   /** 设置 a10
    * 该字段是：a10
    * 
    * @param sddwfzperson
    * @pdOid bd77c456-3f85-4d4d-add4-65e819d283b5 */
   public void setSddwfzperson(String sddwfzperson) {
   	this.sddwfzperson = sddwfzperson;
   }
   
   /** 获取 a10
    * 该字段是：a10
    * 
    * 
    * @pdOid f22c5a04-eabf-40c1-bd8d-572c12846af1 */
   public String getSddwfzperson() {
   	return sddwfzperson;
   }
   
   /** 设置 a11
    * 该字段是：a11
    * 
    * @param fczglead
    * @pdOid e4f07b6b-acd9-4b37-983a-664390357b12 */
   public void setFczglead(String fczglead) {
   	this.fczglead = fczglead;
   }
   
   /** 获取 a11
    * 该字段是：a11
    * 
    * 
    * @pdOid 07e41550-6237-43b7-b325-df0c041473c7 */
   public String getFczglead() {
   	return fczglead;
   }
   
   /** 设置 a12
    * 该字段是：a12
    * 
    * @param leadtime
    * @pdOid b949f5b4-cea8-4d80-ba81-7fc4c034404e */
   public void setLeadtime(String leadtime) {
   	this.leadtime = leadtime;
   }
   
   /** 获取 a12
    * 该字段是：a12
    * 
    * 
    * @pdOid 8a17ad59-0fd2-40bf-9008-85be1946b975 */
   public String getLeadtime() {
   	return leadtime;
   }

}