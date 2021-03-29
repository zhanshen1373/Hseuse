/**
 * File:    UdZyxkDzzy.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkDzzy
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid cd6c9203-942c-4b16-9eae-647fb9d2cdd0 */
@DBTable(tableName = "ud_zyxk_dzzy")
public class HoistingWork extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 287f3f85-73cc-4bbc-b28c-d350ce69aaa1 */
   private static final long serialVersionUID = 32068412932557425L;
   /** 主键
    * 
    * @pdOid a0e42e75-7f96-482d-91f9-c2b6bf031827 */
   @DBField(id=true)
   private String ud_zyxk_dzzyid;
   /** 外键
    * 
    * @pdOid 187dfc70-4ba9-409d-a5bb-07bf77491623 */
   @DBField(foreign=true)
   private String ud_zyxk_zysqid;
   /** 吊装物件名称
    * 
    * @pdOid cb643fb5-8e82-44e1-85ef-9eb12747d0a1 */
   @DBField
   private String dzwjname;
   /** a4
    * 
    * @pdOid 1f67fbd9-a9f8-4a8c-b480-a4c06fa29a89 */
   @DBField
   private String wjsize;
   /** a5
    * 
    * @pdOid db94def4-28a3-4231-86f6-6f16da873967 */
   @DBField
   private String wjzl;
   /** 吊装重量(吨)
    * 
    * @pdOid 0c4e6058-ad9c-40c4-885b-0201b79ab091 */
   @DBField
   private String dzzl;
   /** 吊装工作半径(米)
    * 
    * @pdOid 248533c8-623d-4ed6-8560-d25c31648181 */
   @DBField
   private String dzgzbj;
   /** 吊装高度(米)
    * 
    * @pdOid de1e3c2c-017a-4cc0-b917-cddb0a87ba69 */
   @DBField
   private String dzgd;
   /** 起重机额定载荷(吨)
    * 
    * @pdOid d2c5fc1d-ab9c-4730-8296-1db6c4785e2a */
   @DBField
   private String qzjedzh;
   /** 是否附吊装方案
    * 
    * @pdOid 5228408a-02b0-4a5a-a57f-ef1b7bc9fdcc */
   @DBField
   private Integer isfdzfa;
   /** 吊装准备
    * 
    * @pdOid f890dcdc-aa70-4915-be29-5306a04487f5 */
   @DBField
   private String dzzb;
   /** 吊装区域
    * 
    * @pdOid ce7bac27-2824-4c87-a6d2-a3ddc2ab94cd */
   @DBField
   private String dzqy;
   /** 起重机及人员
    * 
    * @pdOid 994cef89-495b-495d-8a02-339cb64a9b39 */
   @DBField
   private String qzjjry;
   /** 其他检查
    * 
    * @pdOid e81ce60d-3335-4120-97c5-70784ed243d6 */
   @DBField
   private String qtjc;
   /** a15
    * 
    * @pdOid 27319eba-3c02-46fd-9cdd-6aa48c5f5c78 */
   @DBField
   private String sddwjhperson;
   /** 吊装作业指挥人
    * 
    * @pdOid c7090265-b504-455d-9d36-e9e75e067e01 */
   @DBField
   private String dzzyzhperson;
   /** a17
    * 
    * @pdOid a3e17fe7-be6c-4ad2-84fc-8c0aec8c1c70 */
   @DBField
   private String zyzhrpztime;
   /** a18
    * 
    * @pdOid 296a36b7-3d78-4e1a-a566-adce438809ce */
   @DBField
   private String zydwfzperson;
   /** a19
    * 
    * @pdOid b2110f2f-0c5b-4a6b-9ba8-82b4422afccb */
   @DBField
   private String zydwpztime;
   /** 吊装任务类型
    * 
    * @pdOid 2b93233b-380e-45c1-a3a0-bb66f31a04c7 */
   @DBField
   private String dztaskpe;
   /** 上传
    * 
    * @pdOid 4cf9a5f8-65f8-470c-80d9-65bda51b0a16 */
   @DBField
   private Integer isupload;
   /** 是否伪删除
    * 
    * @pdOid 72ce071d-eab4-4393-a942-44341c61d6ab */
   @DBField
   private Integer dr;
   /** 标记
    * 
    * @pdOid 0eab9fa2-44ee-410f-b912-9b48e261ba2c */
   @DBField
   private Integer tag;
   
   /** @return the ud_zyxk_dzzyid
    * 
    * @pdOid b725f52f-fb08-47e7-bbc6-b7668ac2ca90 */
   public String getUd_zyxk_dzzyid() {
   	return ud_zyxk_dzzyid;
   }
   
   /** @param ud_zyxk_dzzyid the ud_zyxk_dzzyid to set
    * @pdOid e3999de5-c6ba-4325-bff7-7b03f91b7895 */
   public void setUd_zyxk_dzzyid(String ud_zyxk_dzzyid) {
   	this.ud_zyxk_dzzyid = ud_zyxk_dzzyid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid fc3c7ffa-dfde-43be-aa36-afc49fe0816d */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 9dcf833b-dfd3-4270-a6e4-473de0d826dd */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** 设置 吊装物件名称
    * 该字段是：吊装物件名称
    * 
    * @param dzwjname
    * @pdOid f3f29228-fefc-4568-801a-65f23ae04fe1 */
   public void setDzwjname(String dzwjname) {
   	this.dzwjname = dzwjname;
   }
   
   /** 获取 吊装物件名称
    * 该字段是：吊装物件名称
    * 
    * 
    * @pdOid 0bcb61a1-0e69-463f-a2c8-583c74c57f96 */
   public String getDzwjname() {
   	return dzwjname;
   }
   
   /** 设置 a4
    * 该字段是：a4
    * 
    * @param wjsize
    * @pdOid d6577bb8-8850-44fe-aee3-19d7778ea9c0 */
   public void setWjsize(String wjsize) {
   	this.wjsize = wjsize;
   }
   
   /** 获取 a4
    * 该字段是：a4
    * 
    * 
    * @pdOid 1b20e094-5c7b-4b43-b415-25c15755d8dc */
   public String getWjsize() {
   	return wjsize;
   }
   
   /** 设置 a5
    * 该字段是：a5
    * 
    * @param wjzl
    * @pdOid 2c825a0e-61f6-4eb4-b45c-153ee0ed3051 */
   public void setWjzl(String wjzl) {
   	this.wjzl = wjzl;
   }
   
   /** 获取 a5
    * 该字段是：a5
    * 
    * 
    * @pdOid 71532042-490d-4066-ab36-3d7fcc66f465 */
   public String getWjzl() {
   	return wjzl;
   }
   
   /** 设置 吊装重量(吨)
    * 该字段是：吊装重量(吨)
    * 
    * @param dzzl
    * @pdOid cc626a80-5825-4dcb-9c93-1be2e3c6707f */
   public void setDzzl(String dzzl) {
   	this.dzzl = dzzl;
   }
   
   /** 获取 吊装重量(吨)
    * 该字段是：吊装重量(吨)
    * 
    * 
    * @pdOid dfd259f9-ec0a-4293-a1fe-d86b41f20c6f */
   public String getDzzl() {
   	return dzzl;
   }
   
   /** 设置 吊装工作半径(米)
    * 该字段是：吊装工作半径(米)
    * 
    * @param dzgzbj
    * @pdOid 79382709-7e12-498b-9f32-50ec9ba0ed7b */
   public void setDzgzbj(String dzgzbj) {
   	this.dzgzbj = dzgzbj;
   }
   
   /** 获取 吊装工作半径(米)
    * 该字段是：吊装工作半径(米)
    * 
    * 
    * @pdOid 9bc7dd0a-ddee-4423-94f3-543a45f2b951 */
   public String getDzgzbj() {
   	return dzgzbj;
   }
   
   /** 设置 吊装高度(米)
    * 该字段是：吊装高度(米)
    * 
    * @param dzgd
    * @pdOid 4acad849-ed12-427c-bd69-b58105b459d4 */
   public void setDzgd(String dzgd) {
   	this.dzgd = dzgd;
   }
   
   /** 获取 吊装高度(米)
    * 该字段是：吊装高度(米)
    * 
    * 
    * @pdOid a3ed80a5-d34b-472c-9e41-3a7add30922d */
   public String getDzgd() {
   	return dzgd;
   }
   
   /** 设置 起重机额定载荷(吨)
    * 该字段是：起重机额定载荷(吨)
    * 
    * @param qzjedzh
    * @pdOid 77033f84-cfb7-46fd-9cac-76106c452cf0 */
   public void setQzjedzh(String qzjedzh) {
   	this.qzjedzh = qzjedzh;
   }
   
   /** 获取 起重机额定载荷(吨)
    * 该字段是：起重机额定载荷(吨)
    * 
    * 
    * @pdOid 6fd1cac4-568c-4e58-bfb3-1246026c5bb4 */
   public String getQzjedzh() {
   	return qzjedzh;
   }
   
   /** 设置 是否附吊装方案
    * 该字段是：是否附吊装方案
    * 
    * @param isfdzfa
    * @pdOid 5e9a20a7-da59-4156-8cee-1b6158a9936a */
   public void setIsfdzfa(Integer isfdzfa) {
   	this.isfdzfa = isfdzfa;
   }
   
   /** 获取 是否附吊装方案
    * 该字段是：是否附吊装方案
    * 
    * 
    * @pdOid ed604306-177a-4507-acd8-b8c8b65a4275 */
   public Integer getIsfdzfa() {
   	return isfdzfa;
   }
   
   /** 设置 吊装准备
    * 该字段是：吊装准备
    * 
    * @param dzzb
    * @pdOid 6635d70d-b00c-4e67-a10f-f87dd331464a */
   public void setDzzb(String dzzb) {
   	this.dzzb = dzzb;
   }
   
   /** 获取 吊装准备
    * 该字段是：吊装准备
    * 
    * 
    * @pdOid 93936a44-04cf-444b-bb0f-5626ec3599f0 */
   public String getDzzb() {
   	return dzzb;
   }
   
   /** 设置 吊装区域
    * 该字段是：吊装区域
    * 
    * @param dzqy
    * @pdOid 5df57656-ad96-49e3-a9a9-eb51f5ead7a2 */
   public void setDzqy(String dzqy) {
   	this.dzqy = dzqy;
   }
   
   /** 获取 吊装区域
    * 该字段是：吊装区域
    * 
    * 
    * @pdOid 5813491e-1e4b-4039-8105-d498db6cf3ad */
   public String getDzqy() {
   	return dzqy;
   }
   
   /** 设置 起重机及人员
    * 该字段是：起重机及人员
    * 
    * @param qzjjry
    * @pdOid ab74ccb0-73a5-40c9-80dc-2a9d987f5d7d */
   public void setQzjjry(String qzjjry) {
   	this.qzjjry = qzjjry;
   }
   
   /** 获取 起重机及人员
    * 该字段是：起重机及人员
    * 
    * 
    * @pdOid f5a9216e-db88-49b8-ba0f-a3bc5cbc9a09 */
   public String getQzjjry() {
   	return qzjjry;
   }
   
   /** 设置 其他检查
    * 该字段是：其他检查
    * 
    * @param qtjc
    * @pdOid 2e1ad602-7548-456f-86ec-426827152bec */
   public void setQtjc(String qtjc) {
   	this.qtjc = qtjc;
   }
   
   /** 获取 其他检查
    * 该字段是：其他检查
    * 
    * 
    * @pdOid 5f19a8b7-728d-428a-a992-df754f9cab4b */
   public String getQtjc() {
   	return qtjc;
   }
   
   /** 设置 a15
    * 该字段是：a15
    * 
    * @param sddwjhperson
    * @pdOid 050f1a34-2f43-4815-bca3-3caab056543b */
   public void setSddwjhperson(String sddwjhperson) {
   	this.sddwjhperson = sddwjhperson;
   }
   
   /** 获取 a15
    * 该字段是：a15
    * 
    * 
    * @pdOid f0deae86-f91c-4030-b7cb-e7d78133fc81 */
   public String getSddwjhperson() {
   	return sddwjhperson;
   }
   
   /** 设置 吊装作业指挥人
    * 该字段是：吊装作业指挥人
    * 
    * @param dzzyzhperson
    * @pdOid cc60f7c9-c1eb-4c60-817b-a5bcb29e27db */
   public void setDzzyzhperson(String dzzyzhperson) {
   	this.dzzyzhperson = dzzyzhperson;
   }
   
   /** 获取 吊装作业指挥人
    * 该字段是：吊装作业指挥人
    * 
    * 
    * @pdOid f4a871f8-cbe5-4809-83b6-27ad7a1be198 */
   public String getDzzyzhperson() {
   	return dzzyzhperson;
   }
   
   /** 设置 a17
    * 该字段是：a17
    * 
    * @param zyzhrpztime
    * @pdOid e548ad66-bb3f-4864-8a50-2fe976f79737 */
   public void setZyzhrpztime(String zyzhrpztime) {
   	this.zyzhrpztime = zyzhrpztime;
   }
   
   /** 获取 a17
    * 该字段是：a17
    * 
    * 
    * @pdOid 3a490364-8576-4d24-87bc-3da954e1e74e */
   public String getZyzhrpztime() {
   	return zyzhrpztime;
   }
   
   /** 设置 a18
    * 该字段是：a18
    * 
    * @param zydwfzperson
    * @pdOid 6165e71a-f6ab-48b4-b5ec-289c6816a3f9 */
   public void setZydwfzperson(String zydwfzperson) {
   	this.zydwfzperson = zydwfzperson;
   }
   
   /** 获取 a18
    * 该字段是：a18
    * 
    * 
    * @pdOid a07d9057-d67d-49fd-8a7a-544b08b6abb3 */
   public String getZydwfzperson() {
   	return zydwfzperson;
   }
   
   /** 设置 a19
    * 该字段是：a19
    * 
    * @param zydwpztime
    * @pdOid d48ac51f-3d8f-427d-8cf9-fefa5a76c24b */
   public void setZydwpztime(String zydwpztime) {
   	this.zydwpztime = zydwpztime;
   }
   
   /** 获取 a19
    * 该字段是：a19
    * 
    * 
    * @pdOid 68bfc79a-432b-4cc4-855d-cdce4cf6a2f9 */
   public String getZydwpztime() {
   	return zydwpztime;
   }
   
   /** 设置 吊装任务类型
    * 该字段是：吊装任务类型
    * 
    * @param dztaskpe
    * @pdOid 6a6cd8c9-d436-4a52-b6ac-e780bdb956bb */
   public void setDztaskpe(String dztaskpe) {
   	this.dztaskpe = dztaskpe;
   }
   
   /** 获取 吊装任务类型
    * 该字段是：吊装任务类型
    * 
    * 
    * @pdOid ad344c16-f6a1-4930-ab04-1ec9b4be8519 */
   public String getDztaskpe() {
   	return dztaskpe;
   }
   
   /** 设置 上传
    * 该字段是：上传
    * 
    * @param isupload
    * @pdOid 3379c8aa-c6a0-453b-a123-543afca37c16 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 上传
    * 该字段是：上传
    * 
    * 
    * @pdOid 0c8af661-3647-43cc-9e13-03aadc8c3c49 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 01b3dc82-a42c-48ab-afb7-99914d1ad223 */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid 853a299b-9397-4e70-9803-c60bde083580 */
   public Integer getDr() {
   	return dr;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid b7a192be-9968-4a76-a587-772d059166fd */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid cce1d55d-00df-42ab-a3c2-32a5174a7656 */
   public Integer getTag() {
   	return tag;
   }

}