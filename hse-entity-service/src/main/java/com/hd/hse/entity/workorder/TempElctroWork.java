/**
 * File:    UdZyxkLsydzy.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkLsydzy
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 482c61f1-ad50-435a-8a9a-a328254fd3fd */
@DBTable(tableName = "ud_zyxk_lsydzy")
public class TempElctroWork extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 5c2a81bd-e72b-48d6-8748-429ad810f182 */
   private static final long serialVersionUID = 1749580835446572338L;
   /** 主键
    * 
    * @pdOid 3086be99-d04d-4c43-b10a-0ecfc1dc3868 */
   @DBField(id=true)
   private Integer ud_zyxk_lsydzyid;
   /** 外键
    * 
    * @pdOid afdbb1cc-7a5b-4a6e-9b65-e0f69c40d0ab */
   @DBField(foreign=true)
   private Integer ud_zyxk_zysqid;
   /** 临时用电用途
    * 
    * @pdOid d4e3a453-c730-413e-b692-8dee318f90f5 */
   @DBField
   private String lsydyt;
   /** 工作电压
    * 
    * @pdOid 94b6d214-301d-4518-b17f-4d83faf7a889 */
   @DBField
   private String gzdy;
   /** 电源接入点
    * 
    * @pdOid 2b847691-3248-4874-838f-b83cfeea4674 */
   @DBField
   private String dyjrd;
   /** 安全注意事项
    * 
    * @pdOid 1f1782ab-8236-4625-a3a3-9c9f96b3ac8a */
   @DBField
   private String aqzysx;
   /** 用电单位确认人
    * 
    * @pdOid 6758dc33-c764-429e-90af-681c1e60c0f2 */
   @DBField
   private String yddwqrperson;
   /** 供电单位确认人
    * 
    * @pdOid f64780a9-96b6-482b-aa59-6981f851b09b */
   @DBField
   private String gddwqrperson;
   /** 电气主管部门
    * 
    * @pdOid 93434913-c3ba-4077-b820-be8e0313adfb */
   @DBField
   private String dqzgdept;
   /** 审核日期1
    * 
    * @pdOid 83533ea2-d4e0-4b79-bd13-d4c4c11cceb5 */
   @DBField
   private String dqshtime;
   /** 接线人签名
    * 
    * @pdOid 7a1ca029-4815-43aa-9652-040b1f518a62 */
   @DBField
   private String jxrqm;
   /** 电工证号
    * 
    * @pdOid 5df9f1e0-55cf-4c6b-888c-33904a030c50 */
   @DBField
   private String jxrdgzh;
   /** 送电时间
    * 
    * @pdOid effd94ca-4be4-44c9-80c4-5de0c42a80a8 */
   @DBField
   private String sdtime;
   /** 送电电工证号
    * 
    * @pdOid aaf0ed31-f83f-4b99-a48d-8ede6b71cd7f */
   @DBField
   private String sddgzh;
   /** 签名
    * 
    * @pdOid 77ac54a3-c649-4c09-884f-624d72be81ed */
   @DBField
   private String sdqm;
   /** 供电单位审核人
    * 
    * @pdOid dfb477a4-2fc4-4944-81af-1150cac58053 */
   @DBField
   private String gddwshperson;
   /** 审核日期
    * 
    * @pdOid 1f96cf0f-1962-4628-a873-055b82c41003 */
   @DBField
   private String gddwshtime;
   /** 断电时间
    * 
    * @pdOid 4592bd78-29ae-4302-8863-74065b4765e9 */
   @DBField
   private String ddtime;
   /** 断电人签名
    * 
    * @pdOid 16d96446-1be2-4d48-a867-0b84b5877cec */
   @DBField
   private String ddperson;
   /** 断电电工证号
    * 
    * @pdOid 110bd4cd-c89f-4da1-b6b2-8b9c96484e82 */
   @DBField
   private String dddgzh;
   /** 拆线时间
    * 
    * @pdOid 223956ac-59a5-4742-9ae4-fd8281d90c62 */
   @DBField
   private String cxtime;
   /** 拆线人签名
    * 
    * @pdOid 7048cfdf-e06c-427a-be91-18d2cf0b3363 */
   @DBField
   private String cxperson;
   /** 拆线人签名描述
    * 
    * @pdOid 3d5fa776-a325-4a3e-8f7f-701f3cdeb0d2 */
   @DBField
   private String cxpersondesc;
   /** 拆线电工证号
    * 
    * @pdOid 9da4f410-75f2-439b-87a5-c0836471d806 */
   @DBField
   private String cxdgzh;
   
   /** @return the ud_zyxk_lsydzyid
    * 
    * @pdOid 9e1b580c-8d5e-4d37-ba62-ecc2389626dd */
   public Integer getUd_zyxk_lsydzyid() {
   	return ud_zyxk_lsydzyid;
   }
   
   /** @param ud_zyxk_lsydzyid the ud_zyxk_lsydzyid to set
    * @pdOid 6ff54a95-6154-4066-9250-87e40f385ec7 */
   public void setUd_zyxk_lsydzyid(Integer ud_zyxk_lsydzyid) {
   	this.ud_zyxk_lsydzyid = ud_zyxk_lsydzyid;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 0557aa6b-bc1f-49c1-9840-7cef1ebbdb78 */
   public Integer getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 6ed8290f-b6d9-42d3-a456-9ebbfc7586c6 */
   public void setUd_zyxk_zysqid(Integer ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the lsydyt
    * 
    * @pdOid 4a43a807-ca6f-4e05-9fe7-f2e9d75c9340 */
   public String getLsydyt() {
   	return lsydyt;
   }
   
   /** @param lsydyt the lsydyt to set
    * @pdOid 971defcd-7d41-41b0-a745-3c85a6da215b */
   public void setLsydyt(String lsydyt) {
   	this.lsydyt = lsydyt;
   }
   
   /** @return the gzdy
    * 
    * @pdOid 55abdb21-98e3-45aa-9cb3-2d0d15bebb75 */
   public String getGzdy() {
   	return gzdy;
   }
   
   /** @param gzdy the gzdy to set
    * @pdOid f5710e34-a5d6-4898-8abd-0b576c3f91ee */
   public void setGzdy(String gzdy) {
   	this.gzdy = gzdy;
   }
   
   /** @return the dyjrd
    * 
    * @pdOid 0e057911-e849-4f1e-b796-bf341f036611 */
   public String getDyjrd() {
   	return dyjrd;
   }
   
   /** @param dyjrd the dyjrd to set
    * @pdOid 86f8dd34-9ced-4ce3-9500-c35c821e53e8 */
   public void setDyjrd(String dyjrd) {
   	this.dyjrd = dyjrd;
   }
   
   /** @return the aqzysx
    * 
    * @pdOid 50723a28-36d7-41f9-b971-83d9c2fdb07d */
   public String getAqzysx() {
   	return aqzysx;
   }
   
   /** @param aqzysx the aqzysx to set
    * @pdOid 34c2e15c-e745-412d-8b1a-b40afe404e14 */
   public void setAqzysx(String aqzysx) {
   	this.aqzysx = aqzysx;
   }
   
   /** @return the yddwqrperson
    * 
    * @pdOid 1219f8a4-07f2-4933-961c-79991cc97fde */
   public String getYddwqrperson() {
   	return yddwqrperson;
   }
   
   /** @param yddwqrperson the yddwqrperson to set
    * @pdOid 14bcfbdf-07d0-4652-9a08-5d52517f3627 */
   public void setYddwqrperson(String yddwqrperson) {
   	this.yddwqrperson = yddwqrperson;
   }
   
   /** @return the gddwqrperson
    * 
    * @pdOid aadb5de6-cd4d-4cfb-a177-754a3f8f3e34 */
   public String getGddwqrperson() {
   	return gddwqrperson;
   }
   
   /** @param gddwqrperson the gddwqrperson to set
    * @pdOid a0960208-635b-4667-b17e-256130ffa18b */
   public void setGddwqrperson(String gddwqrperson) {
   	this.gddwqrperson = gddwqrperson;
   }
   
   /** @return the dqzgdept
    * 
    * @pdOid 45240a05-c386-48ae-a077-56b3a655a4df */
   public String getDqzgdept() {
   	return dqzgdept;
   }
   
   /** @param dqzgdept the dqzgdept to set
    * @pdOid 0d64328c-1f5e-436c-b92c-a2d2f4dce96c */
   public void setDqzgdept(String dqzgdept) {
   	this.dqzgdept = dqzgdept;
   }
   
   /** @return the dqshtime
    * 
    * @pdOid 70fc76b8-f188-4291-ab89-206fc934fc7f */
   public String getDqshtime() {
   	return dqshtime;
   }
   
   /** @param dqshtime the dqshtime to set
    * @pdOid 8c426871-317e-4472-af5a-8105280bdd71 */
   public void setDqshtime(String dqshtime) {
   	this.dqshtime = dqshtime;
   }
   
   /** @return the jxrqm
    * 
    * @pdOid 4d2bd1dc-7513-46dc-a1be-ad9e15cd0c1d */
   public String getJxrqm() {
   	return jxrqm;
   }
   
   /** @param jxrqm the jxrqm to set
    * @pdOid 34937533-918b-448f-98c0-eb515e9e72ab */
   public void setJxrqm(String jxrqm) {
   	this.jxrqm = jxrqm;
   }
   
   /** @return the jxrdgzh
    * 
    * @pdOid 27787d80-a865-4622-ad2f-206889113c99 */
   public String getJxrdgzh() {
   	return jxrdgzh;
   }
   
   /** @param jxrdgzh the jxrdgzh to set
    * @pdOid b25056ad-7534-452f-9484-0579917e51c4 */
   public void setJxrdgzh(String jxrdgzh) {
   	this.jxrdgzh = jxrdgzh;
   }
   
   /** @return the sdtime
    * 
    * @pdOid 876d0875-11a1-410b-9a6b-2ccaa5a66e7a */
   public String getSdtime() {
   	return sdtime;
   }
   
   /** @param sdtime the sdtime to set
    * @pdOid e863b973-b305-4750-9932-a3d56c0ecba8 */
   public void setSdtime(String sdtime) {
   	this.sdtime = sdtime;
   }
   
   /** @return the sddgzh
    * 
    * @pdOid b2c369e5-47fd-42eb-971a-796fc10c5d59 */
   public String getSddgzh() {
   	return sddgzh;
   }
   
   /** @param sddgzh the sddgzh to set
    * @pdOid da9ad4de-2ade-43ef-aa55-ae406b94d44e */
   public void setSddgzh(String sddgzh) {
   	this.sddgzh = sddgzh;
   }
   
   /** @return the sdqm
    * 
    * @pdOid e5b70822-5b3b-4dc8-a9c2-6f8f8854bdaa */
   public String getSdqm() {
   	return sdqm;
   }
   
   /** @param sdqm the sdqm to set
    * @pdOid e7ad000e-a6c7-4b41-81ed-85fa99399bb8 */
   public void setSdqm(String sdqm) {
   	this.sdqm = sdqm;
   }
   
   /** @return the gddwshperson
    * 
    * @pdOid 27b1154c-ca99-4083-b7f2-1f72f4fbda7a */
   public String getGddwshperson() {
   	return gddwshperson;
   }
   
   /** @param gddwshperson the gddwshperson to set
    * @pdOid 0d37970c-0f94-4f08-85a7-2424c5934c51 */
   public void setGddwshperson(String gddwshperson) {
   	this.gddwshperson = gddwshperson;
   }
   
   /** @return the gddwshtime
    * 
    * @pdOid de8bb832-d3f9-4c8e-a50b-9c3b51c9148d */
   public String getGddwshtime() {
   	return gddwshtime;
   }
   
   /** @param gddwshtime the gddwshtime to set
    * @pdOid 459127d3-4e28-42ba-ab25-dd078a5e1892 */
   public void setGddwshtime(String gddwshtime) {
   	this.gddwshtime = gddwshtime;
   }
   
   /** @return the ddtime
    * 
    * @pdOid 0ab33143-9cfe-4e6a-8ebd-c745008c21f3 */
   public String getDdtime() {
   	return ddtime;
   }
   
   /** @param ddtime the ddtime to set
    * @pdOid 7301c545-0a06-4d98-987d-31738ddf1cea */
   public void setDdtime(String ddtime) {
   	this.ddtime = ddtime;
   }
   
   /** @return the ddperson
    * 
    * @pdOid 69107aa9-f290-4047-988a-20c9e9a17c94 */
   public String getDdperson() {
   	return ddperson;
   }
   
   /** @param ddperson the ddperson to set
    * @pdOid e97cb7f5-9b0e-4d12-bddc-d2fb5386b5a6 */
   public void setDdperson(String ddperson) {
   	this.ddperson = ddperson;
   }
   
   /** @return the dddgzh
    * 
    * @pdOid a2fbba27-31ce-45c4-97f2-03464a23db64 */
   public String getDddgzh() {
   	return dddgzh;
   }
   
   /** @param dddgzh the dddgzh to set
    * @pdOid aecf2b40-35c0-4f0f-b016-e18555a3e817 */
   public void setDddgzh(String dddgzh) {
   	this.dddgzh = dddgzh;
   }
   
   /** @return the cxtime
    * 
    * @pdOid 410abd87-3cbe-4936-a52e-9a0721dffd14 */
   public String getCxtime() {
   	return cxtime;
   }
   
   /** @param cxtime the cxtime to set
    * @pdOid 68dec51e-cd0a-4372-9f29-a81c06861980 */
   public void setCxtime(String cxtime) {
   	this.cxtime = cxtime;
   }
   
   /** @return the cxperson
    * 
    * @pdOid 03516f80-320e-4ede-aae1-b0b393201f9a */
   public String getCxperson() {
   	return cxperson;
   }
   
   /** @param cxperson the cxperson to set
    * @pdOid 80295aac-5163-45ec-a34e-960ca51e48c8 */
   public void setCxperson(String cxperson) {
   	this.cxperson = cxperson;
   }
   
   /** @return the cxpersondesc
    * 
    * @pdOid 28723b07-4d69-470b-bf63-79fc8bb59a2a */
   public String getCxpersondesc() {
   	return cxpersondesc;
   }
   
   /** @param cxpersondesc the cxpersondesc to set
    * @pdOid 3d339cff-3a21-4885-a553-ae3aac1ab6d5 */
   public void setCxpersondesc(String cxpersondesc) {
   	this.cxpersondesc = cxpersondesc;
   }
   
   /** @return the cxdgzh
    * 
    * @pdOid 711d2ebd-0419-476b-b2ea-30b1594b934f */
   public String getCxdgzh() {
   	return cxdgzh;
   }
   
   /** @param cxdgzh the cxdgzh to set
    * @pdOid 426823ac-aa01-4cc8-8d1a-ed8f9e67b9c3 */
   public void setCxdgzh(String cxdgzh) {
   	this.cxdgzh = cxdgzh;
   }

}