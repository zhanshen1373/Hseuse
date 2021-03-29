/**
 * File:    UdZyxkJsaline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkJsaline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 2e6131fe-a325-4964-a635-dafb008b2627 */
@DBTable(tableName = "ud_zyxk_jsaline")
public class JSALineTable extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 5170898c-782f-48ac-9471-05ddcf197d8a */
   private static final long serialVersionUID = -4585664163024379739L;
   /** 主键
    * 
    * @pdOid c90f6b6c-9214-423a-a6dc-92c07dc1a692 */
   @DBField(id=true)
   private String ud_zyxk_jsalineid;
   /** 外键
    * 
    * @pdOid f004c4fd-1af3-4480-a204-e9639be821d6 */
   @DBField(foreign=true)
   private String ud_zyxk_jsaid;
   /** a3
    * 
    * @pdOid 6be32cb3-b3bb-4cbe-93c9-9ff63b3ee3b5 */
   @DBField
   private String harzardcode;
   /** a4
    * 
    * @pdOid 95ea7611-69d3-4321-a30f-097bb2001b1e */
   @DBField
   private String harzarddesc;
   /** a5
    * 
    * @pdOid f7ab249a-be05-4388-8920-b4b115e2d74e */
   @DBField
   private String yfkzcode;
   /** a6
    * 
    * @pdOid ee2b12bd-d143-48a8-ab2a-c62d62a7ad91 */
   @DBField
   private String yfkzcs;
   /** a7
    * 
    * @pdOid a669e85a-f2cf-4b29-b9cd-1f2ad3a3bcfe */
   @DBField
   private String isapply;
   /** a8
    * 
    * @pdOid 50eb0af8-f053-4e40-a757-e69a87d6417d */
   @DBField
   private String isconfirm;
   
   /** @return the ud_zyxk_jsalineid
    * 
    * @pdOid 9edba715-9ee1-4de1-b023-69e0a0d4b71b */
   public String getUd_zyxk_jsalineid() {
   	return ud_zyxk_jsalineid;
   }
   
   /** @param ud_zyxk_jsalineid the ud_zyxk_jsalineid to set
    * @pdOid 875e24bf-364b-4797-90fb-6558e9d7a18d */
   public void setUd_zyxk_jsalineid(String ud_zyxk_jsalineid) {
   	this.ud_zyxk_jsalineid = ud_zyxk_jsalineid;
   }
   
   /** @return the ud_zyxk_jsaid
    * 
    * @pdOid 1e79046f-4f0e-449e-bf86-a2a1cc820c66 */
   public String getUd_zyxk_jsaid() {
   	return ud_zyxk_jsaid;
   }
   
   /** @param ud_zyxk_jsaid the ud_zyxk_jsaid to set
    * @pdOid 6312c227-6993-4679-a8ec-bab94b912217 */
   public void setUd_zyxk_jsaid(String ud_zyxk_jsaid) {
   	this.ud_zyxk_jsaid = ud_zyxk_jsaid;
   }
   
   /** 设置 a3
    * 该字段是：a3
    * 
    * @param harzardcode
    * @pdOid ec44e520-0ce4-4eff-ac8b-1f1a4fe58d6a */
   public void setHarzardcode(String harzardcode) {
   	this.harzardcode = harzardcode;
   }
   
   /** 获取 a3
    * 该字段是：a3
    * 
    * 
    * @pdOid 4e4c8802-5d23-4b8c-9681-d3011acaf6be */
   public String getHarzardcode() {
   	return harzardcode;
   }
   
   /** 设置 a4
    * 该字段是：a4
    * 
    * @param harzarddesc
    * @pdOid 86379082-2eed-44f9-b335-120dd9df612f */
   public void setHarzarddesc(String harzarddesc) {
   	this.harzarddesc = harzarddesc;
   }
   
   /** 获取 a4
    * 该字段是：a4
    * 
    * 
    * @pdOid c2350482-e0d6-4f85-844e-09a8cc2019bf */
   public String getHarzarddesc() {
   	return harzarddesc;
   }
   
   /** 设置 a5
    * 该字段是：a5
    * 
    * @param yfkzcode
    * @pdOid 4a1fbe37-e1e6-4abd-a51b-fd4282af47ea */
   public void setYfkzcode(String yfkzcode) {
   	this.yfkzcode = yfkzcode;
   }
   
   /** 获取 a5
    * 该字段是：a5
    * 
    * 
    * @pdOid f84f0954-c3fd-410d-821a-8e4045d2fba0 */
   public String getYfkzcode() {
   	return yfkzcode;
   }
   
   /** 设置 a6
    * 该字段是：a6
    * 
    * @param yfkzcs
    * @pdOid 1d48f37d-437b-4c30-a957-9362e835d8a0 */
   public void setYfkzcs(String yfkzcs) {
   	this.yfkzcs = yfkzcs;
   }
   
   /** 获取 a6
    * 该字段是：a6
    * 
    * 
    * @pdOid 1927bfdc-e649-40dc-8ef8-7553fb5d33f2 */
   public String getYfkzcs() {
   	return yfkzcs;
   }
   
   /** 设置 a7
    * 该字段是：a7
    * 
    * @param isapply
    * @pdOid ae6b548f-260e-410e-bb52-a4669f1707a3 */
   public void setIsapply(String isapply) {
   	this.isapply = isapply;
   }
   
   /** 获取 a7
    * 该字段是：a7
    * 
    * 
    * @pdOid d913d21f-1809-42ca-a44a-6df80ec7d3e5 */
   public String getIsapply() {
   	return isapply;
   }
   
   /** 设置 a8
    * 该字段是：a8
    * 
    * @param isconfirm
    * @pdOid 6334bc30-3524-43f4-b239-1f79cbfb24f6 */
   public void setIsconfirm(String isconfirm) {
   	this.isconfirm = isconfirm;
   }
   
   /** 获取 a8
    * 该字段是：a8
    * 
    * 
    * @pdOid a2121990-2db7-4b95-9cdd-51b93e6efd0d */
   public String getIsconfirm() {
   	return isconfirm;
   }

}