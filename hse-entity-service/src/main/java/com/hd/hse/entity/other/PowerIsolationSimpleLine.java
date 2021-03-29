/**
 * File:    UdZyxkNlgldline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkNlgldline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 892e8e38-5ad8-4372-bc28-5944cf03b299 */
@DBTable(tableName = "ud_zyxk_nlgldline")
public class PowerIsolationSimpleLine extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid e8a09da9-b72c-4fad-921c-509beb76a4ce */
   private static final long serialVersionUID = 2198637123659088526L;
   /** 主键
    * 
    * @pdOid afa6177a-6e43-459d-9af2-dbb83fbf511c */
   @DBField(id=true)
   private Integer ud_zyxk_nlgldlineid;
   /** 外键
    * 
    * @pdOid 13116e82-4537-4cbc-a00c-eabc753e6b75 */
   @DBField(foreign=true)
   private Integer ud_zyxk_nlgldid;
   /** 序号
    * 
    * @pdOid 79e02cd9-6b26-46f2-9a9d-5c4ca757de52 */
   @DBField
   private Integer seqnum;
   /** 位置
    * 
    * @pdOid 2d0c0511-3e20-4a56-96d2-dcd094fdba38 */
   @DBField
   private String location;
   /** 位置描述
    * 
    * @pdOid 14831578-a40d-4c5e-acf7-4b3453ec3400 */
   @DBField
   private String location_desc;
   /** 工作内容描述
    * 
    * @pdOid 726583ee-aaba-463f-ab24-200014aaf49c */
   @DBField
   private String gznrdesc;
   /** 数量
    * 
    * @pdOid a3f26f52-74ad-4c9c-9668-f8b7c69915e0 */
   @DBField
   private Integer count;
   /** 挂签编号
    * 
    * @pdOid 588aed71-65dc-47be-97d0-f48d76194c77 */
   @DBField
   private String jcnum;
   /** 挂签分类
    * 
    * @pdOid ee630fee-8384-416f-b982-2ed3cc343d74 */
   @DBField
   private String jcclass;
   /** 能量隔离单号
    * 
    * @pdOid 4f52c8dd-719a-4f51-a358-84d2e20177d8 */
   @DBField
   private String nlgldnum;
   /** 能量/物料
    * 
    * @pdOid f9dc0638-2104-4f01-af28-3b5b89b19cfa */
   @DBField
   private String nlwl;
   /** 隔离方法
    * 
    * @pdOid c10e793c-40eb-4d05-a8b8-fe6e767133d6 */
   @DBField
   private String glff;
   /** 上锁挂牌点
    * 
    * @pdOid 97869363-520a-4f7e-a9dc-92af78556e7a */
   @DBField
   private String ssgpd;
   /** 挂牌点
    * 
    * @pdOid 8cd3313a-87de-4c51-9ae8-135aceb7fc14 */
   @DBField
   private String gpd;
   /** 其他
    * 
    * @pdOid e4a71460-1208-43e9-b0d8-9041effdce8a */
   @DBField
   private String nlglqt;
   /** a1
    * 
    * @pdOid 13485330-fde0-418e-aa3c-5ba74bf35cae */
   @DBField
   private String datasource;
   
   /** 设置 序号
    * 该字段是：序号
    * 
    * @param seqnum
    * @pdOid e767df2d-1d0b-4c04-9ae2-abbef026ece4 */
   public void setSeqnum(Integer seqnum) {
   	this.seqnum = seqnum;
   }
   
   /** 获取 序号
    * 该字段是：序号
    * 
    * 
    * @pdOid 79da46a0-5ffa-41dd-84e0-0e47f93e6189 */
   public Integer getSeqnum() {
   	return seqnum;
   }
   
   /** 设置 位置
    * 该字段是：位置
    * 
    * @param location
    * @pdOid 54a8e835-8c71-4985-a246-2dc471eafa2b */
   public void setLocation(String location) {
   	this.location = location;
   }
   
   /** 获取 位置
    * 该字段是：位置
    * 
    * 
    * @pdOid 8f1d469d-47bc-4deb-9732-44e4fcd60b1f */
   public String getLocation() {
   	return location;
   }
   
   /** @return the ud_zyxk_nlgldlineid
    * 
    * @pdOid 90c89bfd-c339-4f6e-b03b-f8d223bbabf0 */
   public Integer getUd_zyxk_nlgldlineid() {
   	return ud_zyxk_nlgldlineid;
   }
   
   /** @param ud_zyxk_nlgldlineid the ud_zyxk_nlgldlineid to set
    * @pdOid e5c36f20-8fe8-459d-9e5a-037e4bce4ea6 */
   public void setUd_zyxk_nlgldlineid(Integer ud_zyxk_nlgldlineid) {
   	this.ud_zyxk_nlgldlineid = ud_zyxk_nlgldlineid;
   }
   
   /** @return the ud_zyxk_nlgldid
    * 
    * @pdOid d7f377c8-6040-4a89-9907-8990c2d4883f */
   public Integer getUd_zyxk_nlgldid() {
   	return ud_zyxk_nlgldid;
   }
   
   /** @param ud_zyxk_nlgldid the ud_zyxk_nlgldid to set
    * @pdOid f3c8ec4e-dd49-40f0-ab60-ea5c7aa9df2b */
   public void setUd_zyxk_nlgldid(Integer ud_zyxk_nlgldid) {
   	this.ud_zyxk_nlgldid = ud_zyxk_nlgldid;
   }
   
   /** @return the location_desc
    * 
    * @pdOid b2d04deb-1b3a-4293-a812-1568076b4987 */
   public String getLocation_desc() {
   	return location_desc;
   }
   
   /** @param location_desc the location_desc to set
    * @pdOid 852b2c25-b037-42dd-a737-11e6415a22db */
   public void setLocation_desc(String location_desc) {
   	this.location_desc = location_desc;
   }
   
   /** 设置 工作内容描述
    * 该字段是：工作内容描述
    * 
    * @param gznrdesc
    * @pdOid fd1f2d71-d375-433a-a93d-2c4f65cb930f */
   public void setGznrdesc(String gznrdesc) {
   	this.gznrdesc = gznrdesc;
   }
   
   /** 获取 工作内容描述
    * 该字段是：工作内容描述
    * 
    * 
    * @pdOid 1e5a8648-99cc-4136-9326-2211ffc6b02c */
   public String getGznrdesc() {
   	return gznrdesc;
   }
   
   /** 设置 数量
    * 该字段是：数量
    * 
    * @param count
    * @pdOid c78dbdf1-c8e9-4fa7-9cf8-2b2d99a90243 */
   public void setCount(Integer count) {
   	this.count = count;
   }
   
   /** 获取 数量
    * 该字段是：数量
    * 
    * 
    * @pdOid cbfdf9b4-de39-42eb-9096-8b5d9b909406 */
   public Integer getCount() {
   	return count;
   }
   
   /** 设置 挂签编号
    * 该字段是：挂签编号
    * 
    * @param jcnum
    * @pdOid 4028aad8-f7f1-427b-8142-da46152804c4 */
   public void setJcnum(String jcnum) {
   	this.jcnum = jcnum;
   }
   
   /** 获取 挂签编号
    * 该字段是：挂签编号
    * 
    * 
    * @pdOid 2b212e73-9294-4fae-aa21-fb665e583cfe */
   public String getJcnum() {
   	return jcnum;
   }
   
   /** 设置 挂签分类
    * 该字段是：挂签分类
    * 
    * @param jcclass
    * @pdOid 7c2b53a4-baea-49c7-a42c-37060e7eebae */
   public void setJcclass(String jcclass) {
   	this.jcclass = jcclass;
   }
   
   /** 获取 挂签分类
    * 该字段是：挂签分类
    * 
    * 
    * @pdOid 2f9e31a6-d07c-4329-926e-185eb0fcfe96 */
   public String getJcclass() {
   	return jcclass;
   }
   
   /** 设置 能量隔离单号
    * 该字段是：能量隔离单号
    * 
    * @param nlgldnum
    * @pdOid 4dd8b212-1c86-4ab6-95c2-8d591343cc8d */
   public void setNlgldnum(String nlgldnum) {
   	this.nlgldnum = nlgldnum;
   }
   
   /** 获取 能量隔离单号
    * 该字段是：能量隔离单号
    * 
    * 
    * @pdOid 091a838b-d3be-418a-9733-e86a69b53f25 */
   public String getNlgldnum() {
   	return nlgldnum;
   }
   
   /** 设置 能量/物料
    * 该字段是：能量/物料
    * 
    * @param nlwl
    * @pdOid 0fd2da87-a8a9-4852-a371-1c84707af34b */
   public void setNlwl(String nlwl) {
   	this.nlwl = nlwl;
   }
   
   /** 获取 能量/物料
    * 该字段是：能量/物料
    * 
    * 
    * @pdOid 5f70d094-cc78-405c-b71a-37fe7d610979 */
   public String getNlwl() {
   	return nlwl;
   }
   
   /** 设置 隔离方法
    * 该字段是：隔离方法
    * 
    * @param glff
    * @pdOid d8052e99-796a-4a26-a1e6-8c1dcfb513de */
   public void setGlff(String glff) {
   	this.glff = glff;
   }
   
   /** 获取 隔离方法
    * 该字段是：隔离方法
    * 
    * 
    * @pdOid 29589681-d5b3-4ae8-87b0-c37342639aae */
   public String getGlff() {
   	return glff;
   }
   
   /** 设置 上锁挂牌点
    * 该字段是：上锁挂牌点
    * 
    * @param ssgpd
    * @pdOid ae97346d-d00f-4667-a3d9-2f728b369d5a */
   public void setSsgpd(String ssgpd) {
   	this.ssgpd = ssgpd;
   }
   
   /** 获取 上锁挂牌点
    * 该字段是：上锁挂牌点
    * 
    * 
    * @pdOid ab8f4bf8-e661-4832-be1a-f1ab66303737 */
   public String getSsgpd() {
   	return ssgpd;
   }
   
   /** 设置 挂牌点
    * 该字段是：挂牌点
    * 
    * @param gpd
    * @pdOid 9e860448-4614-4866-a5b0-f01a3df4ea0a */
   public void setGpd(String gpd) {
   	this.gpd = gpd;
   }
   
   /** 获取 挂牌点
    * 该字段是：挂牌点
    * 
    * 
    * @pdOid 98a80bb0-de38-426f-8353-7d14a3d49252 */
   public String getGpd() {
   	return gpd;
   }
   
   /** 设置 其他
    * 该字段是：其他
    * 
    * @param nlglqt
    * @pdOid 3a4ed0d4-72d0-4602-a64b-ef3c3061298d */
   public void setNlglqt(String nlglqt) {
   	this.nlglqt = nlglqt;
   }
   
   /** 获取 其他
    * 该字段是：其他
    * 
    * 
    * @pdOid b68bb9a4-98a2-4cc9-87ff-9d2843e0e888 */
   public String getNlglqt() {
   	return nlglqt;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param datasource
    * @pdOid cd20bab3-7238-429b-be47-6645fe61b953 */
   public void setDatasource(String datasource) {
   	this.datasource = datasource;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid bddab168-32a6-4833-b48f-e6d426d97e49 */
   public String getDatasource() {
   	return datasource;
   }

}