/**
 * File:    UdZyxkGxasset.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkGxasset
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 3b14164a-c5e8-4f4d-a3c4-4321e41e3d19 */
@DBTable(tableName = "ud_zyxk_gxasset")
public class PipeEquipment extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 6b13b3e2-8cd8-4135-b382-36424b27c1ce */
   private static final long serialVersionUID = 8215770176386463853L;
   /** 主键
    * 
    * @pdOid f8a716b9-9eb9-417d-911b-f9b8bb0f09ab */
   @DBField(id=true)
   private Integer ud_zyxk_gxassetid;
   /** 外键唯一标识
    * 
    * @pdOid d90d0c36-5e2f-4ef3-9abe-57831bf3be1f */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 是否附管线打开位置示意图
    * 
    * @pdOid e5d1b148-f2f4-4319-8692-c760be16ad58 */
   @DBField
   private Integer isfsyt;
   /** 是否附管线打开安全工作方案
    * 
    * @pdOid 31d63aab-d201-4dce-940b-c200093fc3dd */
   @DBField
   private Integer isfgxaqgzfa;
   /** 介质
    * 
    * @pdOid b8315241-b53f-4fb3-a8d3-8ba26c1c9520 */
   @DBField
   private String jz;
   /** 其他危害
    * 
    * @pdOid b49c6695-f17b-408a-abb8-a47fd3fb526a */
   @DBField
   private String qthazard;
   /** 工艺负责人
    * 
    * @pdOid b7d17b6b-743e-4481-87c9-6b704501cf01 */
   @DBField
   private String gyfzperson;
   /** 2日期
    * 
    * @pdOid 2af42207-dce0-4f6e-af47-f7eab7810e6b */
   @DBField
   private String gydate;
   /** 设备负责人
    * 
    * @pdOid c3a80790-498d-4bad-a71e-8a31d9d0fdf5 */
   @DBField
   private String assetfzperson;
   /** 1日期
    * 
    * @pdOid b12e3f60-1a34-428b-bc52-d0c71834d585 */
   @DBField
   private String assetdate;
   /** 安全负责人
    * 
    * @pdOid eee8b21a-637c-4365-9679-3fe8c7f4b9c1 */
   @DBField
   private String aqfzperson;
   /** 日期
    * 
    * @pdOid 349d8dd9-f9c2-4ace-84db-fc386a48c668 */
   @DBField
   private String aqdate;
   
   /** 设置 是否附管线打开位置示意图
    * 该字段是：是否附管线打开位置示意图
    * 
    * @param isfsyt
    * @pdOid 894ba6aa-1e48-4eca-93c0-5faae7d80e6e */
   public void setIsfsyt(Integer isfsyt) {
   	this.isfsyt = isfsyt;
   }
   
   /** 获取 是否附管线打开位置示意图
    * 该字段是：是否附管线打开位置示意图
    * 
    * 
    * @pdOid 4d64a339-e57b-412d-b054-3271c7a99553 */
   public Integer getIsfsyt() {
   	return isfsyt;
   }
   
   /** 设置 是否附管线打开安全工作方案
    * 该字段是：是否附管线打开安全工作方案
    * 
    * @param isfgxaqgzfa
    * @pdOid b3ddc47d-64c5-46d2-adfb-8c7fcc508b0b */
   public void setIsfgxaqgzfa(Integer isfgxaqgzfa) {
   	this.isfgxaqgzfa = isfgxaqgzfa;
   }
   
   /** 获取 是否附管线打开安全工作方案
    * 该字段是：是否附管线打开安全工作方案
    * 
    * 
    * @pdOid 6a73ced2-45eb-44b8-a876-333062aa7f6f */
   public Integer getIsfgxaqgzfa() {
   	return isfgxaqgzfa;
   }
   
   /** 设置 介质
    * 该字段是：介质
    * 
    * @param jz
    * @pdOid df4c7bfd-521c-4fff-b966-e2dabf7d5bc1 */
   public void setJz(String jz) {
   	this.jz = jz;
   }
   
   /** 获取 介质
    * 该字段是：介质
    * 
    * 
    * @pdOid 1b4f9dc8-43d5-4be7-98c7-2bcd4baa70d8 */
   public String getJz() {
   	return jz;
   }
   
   /** 设置 其他危害
    * 该字段是：其他危害
    * 
    * @param qthazard
    * @pdOid 2cf0f638-6eff-4bf2-bc80-b7f4d52434f6 */
   public void setQthazard(String qthazard) {
   	this.qthazard = qthazard;
   }
   
   /** 获取 其他危害
    * 该字段是：其他危害
    * 
    * 
    * @pdOid a1428c61-6865-4886-a7f3-8739a53a4f7c */
   public String getQthazard() {
   	return qthazard;
   }
   
   /** 设置 工艺负责人
    * 该字段是：工艺负责人
    * 
    * @param gyfzperson
    * @pdOid 1ba32b75-b15f-45c6-b48c-aedf49aa3243 */
   public void setGyfzperson(String gyfzperson) {
   	this.gyfzperson = gyfzperson;
   }
   
   /** 获取 工艺负责人
    * 该字段是：工艺负责人
    * 
    * 
    * @pdOid afd48390-0f4d-49b7-9148-3aed27002377 */
   public String getGyfzperson() {
   	return gyfzperson;
   }
   
   /** 设置 2日期
    * 该字段是：2日期
    * 
    * @param gydate
    * @pdOid 6a122310-ae28-451b-9808-ca59a8c2a825 */
   public void setGydate(String gydate) {
   	this.gydate = gydate;
   }
   
   /** 获取 2日期
    * 该字段是：2日期
    * 
    * 
    * @pdOid d7e3efe7-4a33-492b-9b0c-514b9cd3643a */
   public String getGydate() {
   	return gydate;
   }
   
   /** 设置 设备负责人
    * 该字段是：设备负责人
    * 
    * @param assetfzperson
    * @pdOid 821782e2-e43a-439e-8e36-0eac7c1e2b2b */
   public void setAssetfzperson(String assetfzperson) {
   	this.assetfzperson = assetfzperson;
   }
   
   /** 获取 设备负责人
    * 该字段是：设备负责人
    * 
    * 
    * @pdOid 6063cae8-132d-481c-ade0-06c1eabe2b55 */
   public String getAssetfzperson() {
   	return assetfzperson;
   }
   
   /** 设置 1日期
    * 该字段是：1日期
    * 
    * @param assetdate
    * @pdOid 6b1d7cae-b988-462c-b334-a57556002c74 */
   public void setAssetdate(String assetdate) {
   	this.assetdate = assetdate;
   }
   
   /** 获取 1日期
    * 该字段是：1日期
    * 
    * 
    * @pdOid 22826c3c-7c2a-4e7f-88bb-646cec7b2bd4 */
   public String getAssetdate() {
   	return assetdate;
   }
   
   /** 设置 安全负责人
    * 该字段是：安全负责人
    * 
    * @param aqfzperson
    * @pdOid d7e13da5-308f-47c0-b8a3-d5a63c61e7e2 */
   public void setAqfzperson(String aqfzperson) {
   	this.aqfzperson = aqfzperson;
   }
   
   /** 获取 安全负责人
    * 该字段是：安全负责人
    * 
    * 
    * @pdOid 63c50ae4-d9a1-43b6-b047-3a29d6173bec */
   public String getAqfzperson() {
   	return aqfzperson;
   }
   
   /** 设置 日期
    * 该字段是：日期
    * 
    * @param aqdate
    * @pdOid 03d6d75f-09b9-44de-af62-5f36e7653dba */
   public void setAqdate(String aqdate) {
   	this.aqdate = aqdate;
   }
   
   /** 获取 日期
    * 该字段是：日期
    * 
    * 
    * @pdOid cc46b7a4-5bb2-4397-878c-c9b9108430db */
   public String getAqdate() {
   	return aqdate;
   }

}