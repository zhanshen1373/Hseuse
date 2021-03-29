/**
 * File:    UdZyxkQtjc.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkQtjc
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid d50d1a97-33d8-4e68-979c-e9417a17044b */
@DBTable(tableName = "ud_zyxk_qtjc")
public class GasDetection extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid c5fedb19-5494-44ff-94c4-9ef08e80a99f */
   private static final long serialVersionUID = 661550786299872790L;
   /** 主键
    * 
    * @pdOid 3deb22c5-cd33-4bf3-9c6d-e701e7ccd612 */
   @DBField(id=true)
   private String ud_zyxk_qtjcid;
   /** 外键
    * 
    * @pdOid 66e28b7a-4309-49e3-ae22-c37d2011d853 */
   @DBField(foreign=true)
   private String ud_zyxk_zysqid;
   /** 检测时间
    * 
    * @pdOid 9c7ede98-fb56-47d9-bdcc-64089c5d15e4 */
   @DBField
   private String jctime;
   /** 检测位置
    * 
    * @pdOid ebf3fe4b-6fd7-4597-bbba-ce1a73ddf408 */
   @DBField
   private String jclocation;
   /** 气体类别
    * 
    * @pdOid e5eec12c-652b-4dc8-8091-d19a68fb40e4 */
   @DBField
   private String qttype;
   /** 气体值
    * 
    * @pdOid 194f0577-7c93-48dc-a387-aa03038b64fe */
   @DBField
   private Double qtvalue;
   /** 允许限度
    * 
    * @pdOid b60607c6-c1f3-49ef-854c-a33e485ac4ac */
   @DBField
   private String yxlimit;
   /** 检测人
    * 
    * @pdOid 651d64f6-850b-4415-97c4-52ec1893559b */
   @DBField
   private String jcperson;
   /** 确认人
    * 
    * @pdOid 10e6d0ef-14d6-4d74-9472-daf21441a6b1 */
   @DBField
   private String qrperson;
   /** 是否合格
    * 
    * @pdOid 40bf7588-56e2-41f2-b8b4-b87d835b49ea */
   @DBField
   private Integer ishg;
   /** 样品分析确认人
    * 
    * @pdOid cafbfe26-d57d-42cb-8a65-8b1b71f366a0 */
   @DBField
   private String ypfxqrperson;
   /** 样品采集时间
    * 
    * @pdOid e33baf08-0d9e-4ddb-9b2e-cdce261c412b */
   @DBField
   private String ypcjtime;
   /** 检测部门
    * 
    * @pdOid 591e12eb-4ccc-4972-8e64-a7a26c470a77 */
   @DBField
   private String jcdept;
   /** 检测方式
    * 
    * @pdOid 5a17ceb9-3229-40d9-9228-3c3571b6bae9 */
   @DBField
   private String jcmethod;
   /** a10
    * 
    * @pdOid 92e2fda0-25e8-4052-9d2d-003b9749f8ff */
   @DBField
   private String audittime;
   /** 序号
    * 
    * @pdOid c8192a74-6569-4742-ba81-666278829416 */
   @DBField
   private Integer seqnum;
   /** 上传
    * 
    * @pdOid b7a33b18-408c-4002-bee1-34999a32333d */
   @DBField
   private Integer isupload;
   /** 检测位置名称
    * 
    * @pdOid 1a1fe52c-876c-4f98-8a7b-9b0c571f3887 */
   @DBField
   private String jclocation_desc;
   /** 属地单位项目负责人意见
    * 
    * @pdOid a968c882-8bb5-4feb-b0e6-0fd732a762af */
   @DBField
   private String sddwxmfzyj;
   /** 属地单位项目负责人
    * 
    * @pdOid 9d0c4923-f469-4034-8666-9c9014f6e575 */
   @DBField
   private String sddwxmfzperson;
   /** a3
    * 
    * @pdOid f980d384-b084-4176-a906-3dfcc1bbb651 */
   @DBField
   private String instrumentnum;
   /** 是否伪删除
    * 
    * @pdOid ca80d21b-2c61-440f-abc4-8f30909f053d */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid b6fba85c-44c7-420f-abb8-ea9238f66711 */
   @DBField
   private Integer tag;
   /** 审批完成标记
    * 
    * @pdOid 0da69c0b-6869-4457-8755-c6af7fdfdbf8 */
   @DBField
   private String writenbypda;
   /**
    * 是否需要上传
    */
   @DBField
   private int needupload;
   
   /**
    * 是否只有氧气不合格
    */
   @DBField
   private Integer isonlyoxygenbelowgrade;


/** @return the ud_zyxk_qtjcid
    * 
    * @pdOid 524474a2-8794-453d-b1f7-4d54fc971d45 */
   public String getUd_zyxk_qtjcid() {
   	return ud_zyxk_qtjcid;
   }
   
   /** @param ud_zyxk_qtjcid the ud_zyxk_qtjcid to set
    * @pdOid 5cac0289-1617-437b-b1b1-58a604b8f179 */
   public void setUd_zyxk_qtjcid(String ud_zyxk_qtjcid) {
   	this.ud_zyxk_qtjcid = ud_zyxk_qtjcid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 7afe9fd6-1dc8-494d-8006-e97536a26529 */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid a92debba-4ca9-477e-b419-75c50ee27220 */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 检测时间
    * 该字段是：检测时间
    * 
    * @param jctime
    * @pdOid f812f5e4-f658-443d-b667-9182c81a0a6c */
   public void setJctime(String jctime) {
   	this.jctime = jctime;
   }
   
   /** 获取 检测时间
    * 该字段是：检测时间
    * 
    * 
    * @pdOid 57a9063d-ca4b-4c41-ba2f-86398bddd50f */
   public String getJctime() {
   	return jctime;
   }
   
   /** 设置 检测位置
    * 该字段是：检测位置
    * 
    * @param jclocation
    * @pdOid 308641fc-d339-4818-b04d-7d063ddecdde */
   public void setJclocation(String jclocation) {
   	this.jclocation = jclocation;
   }
   
   /** 获取 检测位置
    * 该字段是：检测位置
    * 
    * 
    * @pdOid 21b38cbc-369d-48ed-bd18-57f90e53f937 */
   public String getJclocation() {
   	return jclocation;
   }
   
   /** 设置 气体类别
    * 该字段是：气体类别
    * 
    * @param qttype
    * @pdOid 922b20df-29f9-4f86-adef-56aff3f3fae8 */
   public void setQttype(String qttype) {
   	this.qttype = qttype;
   }
   
   /** 获取 气体类别
    * 该字段是：气体类别
    * 
    * 
    * @pdOid ffdb5aa3-6192-4c36-a1c9-d25a7fd8ddcc */
   public String getQttype() {
   	return qttype;
   }
   
   /** 设置 气体值
    * 该字段是：气体值
    * 
    * @param qtvalue
    * @pdOid 27cfcca7-1a88-4084-bd8f-dfaf64975f9f */
   public void setQtvalue(Double qtvalue) {
   	this.qtvalue = qtvalue;
   }
   
   /** 获取 气体值
    * 该字段是：气体值
    * 
    * 
    * @pdOid 52676168-20ea-4f93-889e-86d02160fcb6 */
   public Double getQtvalue() {
   	return qtvalue;
   }
   
   /** 设置 允许限度
    * 该字段是：允许限度
    * 
    * @param yxlimit
    * @pdOid 887debff-a160-4709-a27c-3730e2165404 */
   public void setYxlimit(String yxlimit) {
   	this.yxlimit = yxlimit;
   }
   
   /** 获取 允许限度
    * 该字段是：允许限度
    * 
    * 
    * @pdOid 6e5d8201-9227-49b0-8020-fee3427309aa */
   public String getYxlimit() {
   	return yxlimit;
   }
   
   /** 设置 检测人
    * 该字段是：检测人
    * 
    * @param jcperson
    * @pdOid ff0d49c5-c9bc-4990-b67d-b9c6eed3aeda */
   public void setJcperson(String jcperson) {
   	this.jcperson = jcperson;
   }
   
   /** 获取 检测人
    * 该字段是：检测人
    * 
    * 
    * @pdOid f865b8af-6aa6-4059-aba5-a8d8fbd51d96 */
   public String getJcperson() {
   	return jcperson;
   }
   
   /** 设置 确认人
    * 该字段是：确认人
    * 
    * @param qrperson
    * @pdOid 854e62e7-d6b3-487d-b3a0-0eda183130da */
   public void setQrperson(String qrperson) {
   	this.qrperson = qrperson;
   }
   
   /** 获取 确认人
    * 该字段是：确认人
    * 
    * 
    * @pdOid 134472b9-62a4-439a-8419-0390304f82bd */
   public String getQrperson() {
   	return qrperson;
   }
   
   /** 设置 是否合格
    * 该字段是：是否合格
    * 
    * @param ishg
    * @pdOid 20b886c4-9c09-45a3-876e-a6c58787e45a */
   public void setIshg(Integer ishg) {
   	this.ishg = ishg;
   }
   
   /** 获取 是否合格
    * 该字段是：是否合格
    * 
    * 
    * @pdOid 405fd5e1-425d-413c-811d-e303258472e6 */
   public Integer getIshg() {
   	return ishg;
   }
   
   /** 设置 样品分析确认人
    * 该字段是：样品分析确认人
    * 
    * @param ypfxqrperson
    * @pdOid c8f0d08d-b725-4f4e-b2e0-c163708ebe2c */
   public void setYpfxqrperson(String ypfxqrperson) {
   	this.ypfxqrperson = ypfxqrperson;
   }
   
   /** 获取 样品分析确认人
    * 该字段是：样品分析确认人
    * 
    * 
    * @pdOid 14f75e29-2e55-417b-a367-d5899f42cb88 */
   public String getYpfxqrperson() {
   	return ypfxqrperson;
   }
   
   /** 设置 样品采集时间
    * 该字段是：样品采集时间
    * 
    * @param ypcjtime
    * @pdOid c045a35c-8219-4566-bd1c-2d6da743dfa8 */
   public void setYpcjtime(String ypcjtime) {
   	this.ypcjtime = ypcjtime;
   }
   
   /** 获取 样品采集时间
    * 该字段是：样品采集时间
    * 
    * 
    * @pdOid 9d6d1711-4ace-4f47-87cc-f4e52200f7d4 */
   public String getYpcjtime() {
   	return ypcjtime;
   }
   
   /** 设置 检测部门
    * 该字段是：检测部门
    * 
    * @param jcdept
    * @pdOid b8de940f-a837-43d8-a82a-6e5ad7117d30 */
   public void setJcdept(String jcdept) {
   	this.jcdept = jcdept;
   }
   
   /** 获取 检测部门
    * 该字段是：检测部门
    * 
    * 
    * @pdOid 937ec25b-d2db-427d-a068-2794b2a4d1c7 */
   public String getJcdept() {
   	return jcdept;
   }
   
   /** 设置 检测方式
    * 该字段是：检测方式
    * 
    * @param jcmethod
    * @pdOid 9d7fcaf9-51b9-4c7e-a9aa-adcaaab6360f */
   public void setJcmethod(String jcmethod) {
   	this.jcmethod = jcmethod;
   }
   
   /** 获取 检测方式
    * 该字段是：检测方式
    * 
    * 
    * @pdOid 2209de23-b691-4f14-8805-99975e1153b5 */
   public String getJcmethod() {
   	return jcmethod;
   }
   
   /** 设置 a10
    * 该字段是：a10
    * 
    * @param audittime
    * @pdOid ce3100dd-2665-45d3-ad56-f1bcf1ceb3e2 */
   public void setAudittime(String audittime) {
   	this.audittime = audittime;
   }
   
   /** 获取 a10
    * 该字段是：a10
    * 
    * 
    * @pdOid 4099d3aa-a1ce-417f-8dd4-584fdeb4ba1f */
   public String getAudittime() {
   	return audittime;
   }
   
   /** 设置 序号
    * 该字段是：序号
    * 
    * @param seqnum
    * @pdOid 30484d6d-7fa8-463b-9539-21c43c300aaf */
   public void setSeqnum(Integer seqnum) {
   	this.seqnum = seqnum;
   }
   
   /** 获取 序号
    * 该字段是：序号
    * 
    * 
    * @pdOid e053a866-0aa0-4983-b2ef-574a6042a3cf */
   public Integer getSeqnum() {
   	return seqnum;
   }
   
   /** 设置 上传
    * 该字段是：上传
    * 
    * @param isupload
    * @pdOid 8fec1d27-8185-4046-960c-be3e8c7cf819 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 上传
    * 该字段是：上传
    * 
    * 
    * @pdOid 0ab56f02-f1a7-4545-837a-9b9bcb7f9923 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** @return the jclocation_desc
    * 
    * @pdOid f39359b0-acac-48f3-9e18-126311d995f0 */
   public String getJclocation_desc() {
   	return jclocation_desc;
   }
   
   /** @param jclocation_desc the jclocation_desc to set
    * @pdOid 76d8e2c3-a1ef-41e4-8607-c0f312a11eef */
   public void setJclocation_desc(String jclocation_desc) {
   	this.jclocation_desc = jclocation_desc;
   }
   
   /** 设置 属地单位项目负责人意见
    * 该字段是：属地单位项目负责人意见
    * 
    * @param sddwxmfzyj
    * @pdOid 97fa1aaa-d78c-4c14-8ead-9055769c5c29 */
   public void setSddwxmfzyj(String sddwxmfzyj) {
   	this.sddwxmfzyj = sddwxmfzyj;
   }
   
   /** 获取 属地单位项目负责人意见
    * 该字段是：属地单位项目负责人意见
    * 
    * 
    * @pdOid c13895f2-39d2-4bcb-a040-8646678e49bb */
   public String getSddwxmfzyj() {
   	return sddwxmfzyj;
   }
   
   /** 设置 属地单位项目负责人
    * 该字段是：属地单位项目负责人
    * 
    * @param sddwxmfzperson
    * @pdOid 99ca877e-5640-4dca-91a4-e7961eccb379 */
   public void setSddwxmfzperson(String sddwxmfzperson) {
   	this.sddwxmfzperson = sddwxmfzperson;
   }
   
   /** 获取 属地单位项目负责人
    * 该字段是：属地单位项目负责人
    * 
    * 
    * @pdOid de7d8949-0f12-4999-9224-c9611cce638f */
   public String getSddwxmfzperson() {
   	return sddwxmfzperson;
   }
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param writenbypda
    * @pdOid 9042e100-49b3-4689-bbfe-f1450c829852 */
   public void setWritenbypda(String writenbypda) {
   	this.writenbypda = writenbypda;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid ad5d4b72-c465-459f-bc57-e426232cfd20 */
   public String getWritenbypda() {
   	return writenbypda;
   }
   
   /** 设置 a3
    * 该字段是：a3
    * 
    * @param instrumentnum
    * @pdOid 5634e068-67df-47b4-96b4-6e8c9693895c */
   public void setInstrumentnum(String instrumentnum) {
   	this.instrumentnum = instrumentnum;
   }
   
   /** 获取 a3
    * 该字段是：a3
    * 
    * 
    * @pdOid 7f8d2760-1c4b-4cf6-8c68-a68c94c160c5 */
   public String getInstrumentnum() {
   	return instrumentnum;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 9d1f8a0b-b386-4c4e-81c6-c5d8f103a588 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid 3d7c53d1-7cca-4836-a824-dd2c393d37c8 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid ec0e3908-b44a-4095-b9fd-1305d38bc13b */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid 272a0ad4-42ee-4a97-a1e4-73beeaab7c79 */
   public Integer getTag() {
		return tag;
	}

public int getNeedupload() {
	return needupload;
}

public void setNeedupload(int needupload) {
	this.needupload = needupload;
}

public Integer getIsonlyoxygenbelowgrade() {
	return isonlyoxygenbelowgrade;
}

public void setIsonlyoxygenbelowgrade(Integer isonlyoxygenbelowgrade) {
	this.isonlyoxygenbelowgrade = isonlyoxygenbelowgrade;
}



}