/**
 * File:    UdCbsglJcjl.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdCbsglJcjl
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 97b3e4c6-8b10-4724-b884-55523cf64152 */
@DBTable(tableName = "ud_cbsgl_jcjl")
public class ReviewRecord extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 8c904a10-86c7-4a9b-8222-20e220fcdceb */
   private static final long serialVersionUID = -5427593390652516570L;
   /** ID
    * 
    * @pdOid 49f1cbce-39c1-436a-93d1-a28175b0a3e2 */
   @DBField(id=true)
   private Integer ud_cbsgl_jcjlid;
   /** 作业许可证编号
    * 
    * @pdOid 24326c65-92c8-4602-8854-ddf06e592a83 */
   @DBField
   private String zycardnum;
   /** 级别
    * 
    * @pdOid 2f8e61f7-5414-4b5d-b878-64cfc5958c5a */
   @DBField
   private String jllevel;
   /** 检查单位
    * 
    * @pdOid 1f5e42d0-004c-4d0c-b7cd-1059a7695c20 */
   @DBField
   private String jcdept;
   /** 检查单位
    * 
    * @pdOid 39a50ad4-78fa-4023-afae-8345cc4e159e */
   @DBField
   private String jcdept_desc;
   /** 问题责任人
    * 
    * @pdOid ba0c9a61-576b-4229-b143-5c97dd53e023 */
   @DBField
   private String zrperson;
   /** 检查人
    * 
    * @pdOid ea98f6b3-1185-4340-8736-d2cc1d600774 */
   @DBField
   private String zrperson_desc;
   /** 被检单位
    * 
    * @pdOid 61c2656c-2d6c-4e4d-9127-f17b0ac80345 */
   @DBField
   private String bjcdept;
   /** 被检查单位
    * 
    * @pdOid 5d0c04a2-e82d-4430-9fab-f4026c61471a */
   @DBField
   private String bjcdept_desc;
   /** 检查事实描述
    * 
    * @pdOid a85907f4-834c-4a3a-a897-4b16fba9311a */
   @DBField
   private String jcresult;
   /** 检查/违约时间
    * 
    * @pdOid a0deb5af-3edc-4dcb-9582-3f631438bc78 */
   @DBField
   private String wydate;
   /** 违约地点
    * 
    * @pdOid 0a348b5b-2726-458a-a18c-3bfc63160d7b */
   @DBField
   private String wyaddress;
   /** 是否有问题
    * 
    * @pdOid a3b0ded8-7f58-47ea-8455-474fe9e89e23 */
   @DBField
   private String isproblem;
   /** 检查人
    * 
    * @pdOid 57a84d5c-f644-4360-9ffe-9753bd662478 */
   @DBField
   private String jzperson;
   /** 检查人
    * 
    * @pdOid 242864bc-2349-4d79-9759-7291db4b4615 */
   @DBField
   private String jzperson_description;
   /** 是否整改
    * 
    * @pdOid b277695c-3944-453f-8110-0edb9f4186e4 */
   @DBField
   private Integer iszg;
   /** 整改状态
    * 
    * @pdOid 52b0d92a-c017-4b9e-bafb-b0fae574be1a */
   @DBField
   private String zgstatus;
   /** 复查人
    * 
    * @pdOid 6601d3ca-1544-42a5-8f7c-7cdfbe1435eb */
   @DBField
   private String fcperson;
   /** 复查人
    * 
    * @pdOid a68d7184-4016-46f0-a8c5-f75e7a3bc880 */
   @DBField
   private String fcperson_desc;
   /** 复查时间
    * 
    * @pdOid a7b8bb23-1b7d-4df9-92ba-c6bd23736aa0 */
   @DBField
   private String fctime;
   /** 作业申请ID
    * 
    * @pdOid d697d18c-6be1-44a0-8a43-9f72c1a193a6 */
   @DBField
   private Integer ud_zyxk_zysqid;
   /** 上传
    * 
    * @pdOid 33f73659-b2b7-4ba2-b2d5-551eb7f115df */
   @DBField
   private Integer isupload;
   /** 整改人
    * 
    * @pdOid e22736c7-1630-4bc4-bb58-25b213854e1e */
   @DBField
   private String zgperson;
   /** 整改人
    * 
    * @pdOid 72cf2c8b-634c-4094-ae6e-ff3d59462fcd */
   @DBField
   private String zgperson_desc;
   /** 整改时间
    * 
    * @pdOid 36746509-45de-4848-90c1-574c619cb9de */
   @DBField
   private String zgtime;
   /** pda上传记录id
    * 
    * @pdOid 76fd4c8b-1f8a-4267-8a6d-88401de810f3 */
   @DBField
   private String pdajcjlid;
   
   /** @return the ud_cbsgl_jcjlid
    * 
    * @pdOid 1124c706-d73f-4136-b71c-c9329745923b */
   public Integer getUd_cbsgl_jcjlid() {
   	return ud_cbsgl_jcjlid;
   }
   
   /** @param ud_cbsgl_jcjlid the ud_cbsgl_jcjlid to set
    * @pdOid 52cafd9e-fb78-4937-a04c-3ec3a6618c98 */
   public void setUd_cbsgl_jcjlid(Integer ud_cbsgl_jcjlid) {
   	this.ud_cbsgl_jcjlid = ud_cbsgl_jcjlid;
   }
   
   /** @return the zycardnum
    * 
    * @pdOid fdd9505b-b220-416b-b69a-2dbe76d8f949 */
   public String getZycardnum() {
   	return zycardnum;
   }
   
   /** @param zycardnum the zycardnum to set
    * @pdOid b8e46e3d-6082-4c42-beb3-8067be104e71 */
   public void setZycardnum(String zycardnum) {
   	this.zycardnum = zycardnum;
   }
   
   /** @return the jllevel
    * 
    * @pdOid 842c9755-9df8-410c-a1de-6f732b440209 */
   public String getJllevel() {
   	return jllevel;
   }
   
   /** @param jllevel the jllevel to set
    * @pdOid f086e79e-2b06-464c-b234-5f429471a5bd */
   public void setJllevel(String jllevel) {
   	this.jllevel = jllevel;
   }
   
   /** @return the jcdept
    * 
    * @pdOid 9544dbea-3b40-40aa-8297-09cbd6ed7b7f */
   public String getJcdept() {
   	return jcdept;
   }
   
   /** @param jcdept the jcdept to set
    * @pdOid 567c6146-54de-4360-877d-7d4184bf3e7d */
   public void setJcdept(String jcdept) {
   	this.jcdept = jcdept;
   }
   
   /** @return the jcdept_desc
    * 
    * @pdOid c439fad3-c5da-4a1b-95a4-69309d29410a */
   public String getJcdept_desc() {
   	return jcdept_desc;
   }
   
   /** @param jcdept_desc the jcdept_desc to set
    * @pdOid a0755f48-868d-4b90-a3e0-ed1d1cea7584 */
   public void setJcdept_desc(String jcdept_desc) {
   	this.jcdept_desc = jcdept_desc;
   }
   
   /** @return the zrperson
    * 
    * @pdOid c49a607f-0199-46ea-8ede-d4e6e11a6d49 */
   public String getZrperson() {
   	return zrperson;
   }
   
   /** @param zrperson the zrperson to set
    * @pdOid 34223f8c-a23b-4d17-9d0c-63eaa8814826 */
   public void setZrperson(String zrperson) {
   	this.zrperson = zrperson;
   }
   
   /** @return the zrperson_desc
    * 
    * @pdOid 6f2501df-caf5-42ae-b570-4df4a21ca912 */
   public String getZrperson_desc() {
   	return zrperson_desc;
   }
   
   /** @param zrperson_desc the zrperson_desc to set
    * @pdOid 42ea9ea6-4aa1-4a40-8c1b-b16517a520af */
   public void setZrperson_desc(String zrperson_desc) {
   	this.zrperson_desc = zrperson_desc;
   }
   
   /** @return the bjcdept
    * 
    * @pdOid 00c1ffd2-4fae-4a11-ac14-c505a41b603d */
   public String getBjcdept() {
   	return bjcdept;
   }
   
   /** @param bjcdept the bjcdept to set
    * @pdOid 2b1b56e1-b6f0-45e9-a2c7-04a906c058cb */
   public void setBjcdept(String bjcdept) {
   	this.bjcdept = bjcdept;
   }
   
   /** @return the bjcdept_desc
    * 
    * @pdOid 6dc19edd-f0f7-476b-9749-638f2afbfd68 */
   public String getBjcdept_desc() {
   	return bjcdept_desc;
   }
   
   /** @param bjcdept_desc the bjcdept_desc to set
    * @pdOid 73a4abd9-5cef-4650-9473-3e47504725cf */
   public void setBjcdept_desc(String bjcdept_desc) {
   	this.bjcdept_desc = bjcdept_desc;
   }
   
   /** @return the jcresult
    * 
    * @pdOid 9efe0616-40cf-4dde-ad23-b6f3f0fd6f94 */
   public String getJcresult() {
   	return jcresult;
   }
   
   /** @param jcresult the jcresult to set
    * @pdOid 7ee2eb80-a474-485a-9d9f-d1144f144794 */
   public void setJcresult(String jcresult) {
   	this.jcresult = jcresult;
   }
   
   /** @return the wydate
    * 
    * @pdOid ff60f2d7-5dd5-4f6c-aaf4-6ea9c07cda6d */
   public String getWydate() {
   	return wydate;
   }
   
   /** @param wydate the wydate to set
    * @pdOid 6578dd14-5cb0-4970-abd4-79ab993d105f */
   public void setWydate(String wydate) {
   	this.wydate = wydate;
   }
   
   /** @return the wyaddress
    * 
    * @pdOid 6f9e1b0b-bace-437e-beba-a2ce5458f057 */
   public String getWyaddress() {
   	return wyaddress;
   }
   
   /** @param wyaddress the wyaddress to set
    * @pdOid 65f3fdf0-abfb-42a2-a9b1-947fa834f460 */
   public void setWyaddress(String wyaddress) {
   	this.wyaddress = wyaddress;
   }
   
   /** @return the isproblem
    * 
    * @pdOid 51b76b9f-4bf6-4c4c-9467-19d176676146 */
   public String getIsproblem() {
   	return isproblem;
   }
   
   /** @param isproblem the isproblem to set
    * @pdOid 26cf3b6d-86bf-4636-b073-2a3ad2eb3d69 */
   public void setIsproblem(String isproblem) {
   	this.isproblem = isproblem;
   }
   
   /** @return the jzperson
    * 
    * @pdOid f40c11af-2d9a-4226-a0e3-402a7664ed69 */
   public String getJzperson() {
   	return jzperson;
   }
   
   /** @param jzperson the jzperson to set
    * @pdOid dddd832d-f5ea-439a-8964-c8641a8563f3 */
   public void setJzperson(String jzperson) {
   	this.jzperson = jzperson;
   }
   
   /** @return the jzperson_description
    * 
    * @pdOid a21ec92c-6f09-4d7e-9e57-de05952cd776 */
   public String getJzperson_description() {
   	return jzperson_description;
   }
   
   /** @param jzperson_description the jzperson_description to set
    * @pdOid 928fbed9-7066-4fa2-8e4a-45d13d9b9cfa */
   public void setJzperson_description(String jzperson_description) {
   	this.jzperson_description = jzperson_description;
   }
   
   /** @return the iszg
    * 
    * @pdOid 8fb40141-dd5d-45e1-8f63-9523505e9460 */
   public Integer getIszg() {
   	return iszg;
   }
   
   /** @param iszg the iszg to set
    * @pdOid c950c799-93d6-4703-829a-c76e6b5aa99a */
   public void setIszg(Integer iszg) {
   	this.iszg = iszg;
   }
   
   /** @return the zgstatus
    * 
    * @pdOid d1e8f054-976c-43bb-bd67-2c42b2c54389 */
   public String getZgstatus() {
   	return zgstatus;
   }
   
   /** @param zgstatus the zgstatus to set
    * @pdOid 0a28b1aa-0ca1-4b06-aa98-d10bbf2c5464 */
   public void setZgstatus(String zgstatus) {
   	this.zgstatus = zgstatus;
   }
   
   /** @return the fcperson
    * 
    * @pdOid 912f587f-6ee6-4ecd-a8b8-6c54232c434b */
   public String getFcperson() {
   	return fcperson;
   }
   
   /** @param fcperson the fcperson to set
    * @pdOid 7ccaae78-df10-4e53-b9ce-5a2b1d5404d9 */
   public void setFcperson(String fcperson) {
   	this.fcperson = fcperson;
   }
   
   /** @return the fcperson_desc
    * 
    * @pdOid 4fcef2e2-9958-4a15-beff-401e6de5e661 */
   public String getFcperson_desc() {
   	return fcperson_desc;
   }
   
   /** @param fcperson_desc the fcperson_desc to set
    * @pdOid f0a87cae-0aec-43b9-82cc-df8d7bdcd1e6 */
   public void setFcperson_desc(String fcperson_desc) {
   	this.fcperson_desc = fcperson_desc;
   }
   
   /** @return the fctime
    * 
    * @pdOid 0aee6bac-fba3-4912-98b9-ad5d131b7483 */
   public String getFctime() {
   	return fctime;
   }
   
   /** @param fctime the fctime to set
    * @pdOid 3cb7812e-bae1-48cc-8d59-04762883c9aa */
   public void setFctime(String fctime) {
   	this.fctime = fctime;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 6e21ada8-41ae-4c8d-8584-98f01b77452c */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 5823be50-c183-4fb2-8a93-9f5cc6283ad1 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the isupload
    * 
    * @pdOid 5143519e-93be-4fcf-b5bf-13a7a5dc9ba2 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** @param isupload the isupload to set
    * @pdOid dfd0be31-8159-4ef5-88da-ae78a08da17f */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** @return the zgperson
    * 
    * @pdOid affa5e40-1d62-45ca-b0e2-02bb9573c8a0 */
   public String getZgperson() {
   	return zgperson;
   }
   
   /** @param zgperson the zgperson to set
    * @pdOid c1864a5f-75c5-4dc6-a4be-a8f62b9351f3 */
   public void setZgperson(String zgperson) {
   	this.zgperson = zgperson;
   }
   
   /** @return the zgperson_desc
    * 
    * @pdOid 86c82cd0-4158-4e64-8a56-55572eb3b062 */
   public String getZgperson_desc() {
   	return zgperson_desc;
   }
   
   /** @param zgperson_desc the zgperson_desc to set
    * @pdOid 95acc67a-d35e-4c89-a8b1-c597bbcef3e8 */
   public void setZgperson_desc(String zgperson_desc) {
   	this.zgperson_desc = zgperson_desc;
   }
   
   /** @return the zgtime
    * 
    * @pdOid 5cc709d6-4142-4702-b687-cbaf419e622f */
   public String getZgtime() {
   	return zgtime;
   }
   
   /** @param zgtime the zgtime to set
    * @pdOid 7f41942b-caf4-43d8-bdf1-0de9cd6d17a7 */
   public void setZgtime(String zgtime) {
   	this.zgtime = zgtime;
   }
   
   /** @return the pdajcjlid
    * 
    * @pdOid fc88cbf4-166e-430c-8ca5-40214871be88 */
   public String getPdajcjlid() {
   	return pdajcjlid;
   }
   
   /** @param pdajcjlid the pdajcjlid to set
    * @pdOid e8c3b101-c05f-460d-9227-374f902dc148 */
   public void setPdajcjlid(String pdajcjlid) {
   	this.pdajcjlid = pdajcjlid;
   }

}