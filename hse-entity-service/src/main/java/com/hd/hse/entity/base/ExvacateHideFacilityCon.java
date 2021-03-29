/**
 * File:    UdZyxkYcssqk.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkYcssqk
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 1160e3d6-a997-418e-90f9-41f2aa8e8c15 */
@DBTable(tableName = "ud_zyxk_ycssqk")
public class ExvacateHideFacilityCon extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 1e20d4a5-35ba-40a1-9fb7-117b0a02f4ff */
   private static final long serialVersionUID = -7307964829026576037L;
   /** 主键
    * 
    * @pdOid f691b46f-8275-4b92-92ef-218a94f5d91f */
   @DBField(id=true)
   private Integer ud_zyxk_ycssqkid;
   /** 描述
    * 
    * @pdOid eeba4947-8bfa-4e8c-a547-91bb2e4ff962 */
   @DBField
   private String description;
   /** 外键
    * 
    * @pdOid 6bd15e61-b9d2-48b7-bf09-f0b20ffb67bd */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 隐藏设施情况
    * 
    * @pdOid 839b19b5-759a-4025-b654-fd90a5c74085 */
   @DBField
   private String ycssqk;
   /** 管径、走向、深度、位置
    * 
    * @pdOid 51442314-a825-4b46-8b53-65a883a6f4e1 */
   @DBField
   private String gjzxsdwz;
   /** 有无隐蔽设施
    * 
    * @pdOid 95909eab-3619-4937-8867-e7419fc18f52 */
   @DBField
   private Integer isornot;
   /** 隔离阀/开关位置
    * 
    * @pdOid f1d34efa-fb2a-48d1-9372-415b7b4931e0 */
   @DBField
   private String glfkgwz;
   /** 参考图号
    * 
    * @pdOid 66a4aa01-0769-40c9-b89c-6e6e8c94eeec */
   @DBField
   private String ckth;
   /** 专业确认
    * 
    * @pdOid d64863c8-4019-4ba6-9c64-c587e8c4df35 */
   @DBField
   private String zyqr;
   /** a1
    * 
    * @pdOid 67d8a007-e2cd-4f6f-b400-1099bbef6255 */
   @DBField
   private Integer issh;
   /** 上传
    * 
    * @pdOid e3942c62-72fb-4acb-b07f-78797f2d5424 */
   @DBField
   private Integer isupload;
   /** 是否伪删除
    * 
    * @pdOid 217ba0cd-86e4-48b1-ab07-324fff47fa2e */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid 9daf3275-a623-4f13-8dc7-3d7c3fb048eb */
   @DBField
   private Integer tag;
   
   /** @return the ud_zyxk_ycssqkid
    * 
    * @pdOid 924a47b5-08be-4390-8062-bb33347853e8 */
   public Integer getUd_zyxk_ycssqkid() {
   	return ud_zyxk_ycssqkid;
   }
   
   /** @param ud_zyxk_ycssqkid the ud_zyxk_ycssqkid to set
    * @pdOid afda8588-5adf-4527-8cc8-6c3b7b2dcff9 */
   public void setUd_zyxk_ycssqkid(Integer ud_zyxk_ycssqkid) {
   	this.ud_zyxk_ycssqkid = ud_zyxk_ycssqkid;
   }
   
   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid 89fc2360-f462-43f7-9a1a-6a4a44146a88 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid 10daae7e-3642-40d3-82df-125a33be2f37 */
   public String getDescription() {
   	return description;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid ac96b88c-a30c-43f2-9c19-ef305168f3ea */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 02a206ce-8067-4214-b1c2-015448819cc9 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 隐藏设施情况
    * 该字段是：隐藏设施情况
    * 
    * @param ycssqk
    * @pdOid 831cf065-8658-4513-bcab-963be9eaccb2 */
   public void setYcssqk(String ycssqk) {
   	this.ycssqk = ycssqk;
   }
   
   /** 获取 隐藏设施情况
    * 该字段是：隐藏设施情况
    * 
    * 
    * @pdOid b385bf27-6c50-4b59-b851-605e5192fa47 */
   public String getYcssqk() {
   	return ycssqk;
   }
   
   /** 设置 管径、走向、深度、位置
    * 该字段是：管径、走向、深度、位置
    * 
    * @param gjzxsdwz
    * @pdOid 2d9561e8-0114-49fc-8b05-8295c16c61d5 */
   public void setGjzxsdwz(String gjzxsdwz) {
   	this.gjzxsdwz = gjzxsdwz;
   }
   
   /** 获取 管径、走向、深度、位置
    * 该字段是：管径、走向、深度、位置
    * 
    * 
    * @pdOid 6d29154b-9b57-43ec-a9b1-99a12048f223 */
   public String getGjzxsdwz() {
   	return gjzxsdwz;
   }
   
   /** 设置 有无隐蔽设施
    * 该字段是：有无隐蔽设施
    * 
    * @param isornot
    * @pdOid 1ea662f6-2a18-4b48-b4bf-e156ff5fb4c0 */
   public void setIsornot(Integer isornot) {
   	this.isornot = isornot;
   }
   
   /** 获取 有无隐蔽设施
    * 该字段是：有无隐蔽设施
    * 
    * 
    * @pdOid bb3dcbf9-dc38-4292-8e5e-be9be1f60227 */
   public Integer getIsornot() {
   	return isornot;
   }
   
   /** 设置 隔离阀/开关位置
    * 该字段是：隔离阀/开关位置
    * 
    * @param glfkgwz
    * @pdOid e49b8443-2524-4a48-bf45-7b4a8e8afc02 */
   public void setGlfkgwz(String glfkgwz) {
   	this.glfkgwz = glfkgwz;
   }
   
   /** 获取 隔离阀/开关位置
    * 该字段是：隔离阀/开关位置
    * 
    * 
    * @pdOid fe0c0ae5-ea66-4bad-b32c-e7c7edb8bb3d */
   public String getGlfkgwz() {
   	return glfkgwz;
   }
   
   /** 设置 参考图号
    * 该字段是：参考图号
    * 
    * @param ckth
    * @pdOid 76027c78-1871-49af-ad02-aaba5f716885 */
   public void setCkth(String ckth) {
   	this.ckth = ckth;
   }
   
   /** 获取 参考图号
    * 该字段是：参考图号
    * 
    * 
    * @pdOid 258cecd2-fdfa-4d34-b61c-72ce2798c1d1 */
   public String getCkth() {
   	return ckth;
   }
   
   /** 设置 专业确认
    * 该字段是：专业确认
    * 
    * @param zyqr
    * @pdOid 0e3ce971-f5f9-47c1-ad10-ec19614dc7e4 */
   public void setZyqr(String zyqr) {
   	this.zyqr = zyqr;
   }
   
   /** 获取 专业确认
    * 该字段是：专业确认
    * 
    * 
    * @pdOid 2d3bbb5a-061f-4cf9-a9af-3982c076f0a1 */
   public String getZyqr() {
   	return zyqr;
   }
   
   /** 设置 a1
    * 该字段是：a1
    * 
    * @param issh
    * @pdOid f3168849-17b0-468d-8a4c-c6d9334be14d */
   public void setIssh(Integer issh) {
   	this.issh = issh;
   }
   
   /** 获取 a1
    * 该字段是：a1
    * 
    * 
    * @pdOid f82deaa6-de46-4cb7-9dc7-ea61b919693b */
   public Integer getIssh() {
   	return issh;
   }
   
   /** 设置 上传
    * 该字段是：上传
    * 
    * @param isupload
    * @pdOid 8f06cac4-10dd-4594-88fa-78292f2eb5d5 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 上传
    * 该字段是：上传
    * 
    * 
    * @pdOid 4cc03c14-0ddc-4381-92d9-a73aae0e737a */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid e3614751-181b-407f-8d04-d3b39b4bbd99 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid a2f60820-f097-4d65-9ae3-3399e1f37d62 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 221ecc1d-945e-46a2-872c-fb894439bbc3 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid eae3b801-bba1-4093-a8b6-740843bbc229 */
   public Integer getTag() {
   	return tag;
   }

}