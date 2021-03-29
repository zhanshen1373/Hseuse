/**
 * File:    UdOnemeasurePerson.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:54:18
 * Purpose: ��������� UdOnemeasurePerson
 * NOTE:    ���ļ�Ϊ�Զ���ɣ������ֹ��Ķ���
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid ef897cff-44c9-4688-8248-bdf94e84ef02 */
@DBTable(tableName = "ud_onemeasure_person")
public class MultitermMeasureAffirm extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid c962e551-9e70-4f87-87c6-01b9d0f32561 */
   private static final long serialVersionUID = -4247751614497370531L;
   /** ҵ����������
    * 
    * @pdOid 79fcade7-923f-48b4-8d45-6b1a6fffd6cf */
   @DBField
   private String tableid;
   /** 操作项结果
    * 
    * @pdOid f1fb8f99-a77c-4546-ac89-c2694b3cc218 */
   @DBField
   private String status;
   /** ������ı���
    * 
    * @pdOid db2b0f85-9e48-41b5-8af4-9a9d4373b0e3 */
   @DBField
   private String opid;
   /** �����˱���
    * 
    * @pdOid 9f6631ea-955c-4db5-b3ba-f10e61f72b80 */
   @DBField
   private String opcode;
   /** ���������
    * 
    * @pdOid a3779f9d-987a-4f6e-b77e-9d51d9470a7d */
   @DBField
   private String opname;
   /** ����ʱ��
    * 
    * @pdOid 4311e14c-0d4d-4588-ac1e-1e215d7f2b46 */
   @DBField
   private String sptime;
   /** ����ڵ����
    * 
    * @pdOid fd770e04-aad1-4251-94c3-cab3ef52837d */
   @DBField
   private String spnode;
   /** Tab����
    * 
    * @pdOid 03eccfa1-f93a-49a0-a3a2-4f191324cb85 */
   @DBField
   private String tabcode;
   /** ҵ�����
    * 
    * @pdOid 555c484e-a8ef-4b3d-a0b4-3d33eeedc8c0 */
   @DBField
   private String objectname;
   /** ����ڵ��������ڵ����
    * 
    * @pdOid 427d92b7-6c24-46a9-ab67-78aeebcd1ddf */
   @DBField
   private String spnodename;
   /** a3
    * 
    * @pdOid e9a31967-104b-4a5f-9f38-833db73f7bcf */
   @DBField
   private Integer isupload;
   /** a4
    * 
    * @pdOid 02e06659-8960-4a1a-b7b7-92dcf565923c */
   @DBField
   private String tablename;
   /** a5
    * 
    * @pdOid ec68b73f-5f3d-45b9-8326-c19ca361ca74 */
   @DBField
   private String datasource;
   /** a1
    * 
    * @pdOid f4ba637b-f7df-476a-8dde-e87f8364f0b4 */
   @DBField(id=true)
   private String ud_id;
   /** a2
    * 
    * @pdOid f82acf4d-ede1-4752-a712-881b51774b27 */
   @DBField
   private String ud_type;
   /** @pdOid 2af8fa41-330e-4cc0-a76b-166853074505 */
   @DBField
   private String ud_zyxk_zysqid;
   
   /** ud_zyxk_zysqid.
    * 
    * 
    * @return  the ud_zyxk_zysqid
    * 
    * @pdOid 3e718210-3e3f-4357-b1f1-c143aa7d01b2 */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** ud_zyxk_zysqid.
    * 
    * 
    * @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 7ac0f8c1-d408-4004-bea7-0acf77ab4459 */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the tableid
    * 
    * @pdOid 4103f94a-4585-4bac-9076-97f18dda6374 */
   public String getTableid() {
   	return tableid;
   }
   
   /** @param tableid the tableid to set
    * @pdOid 1ca399d1-21da-4b6b-97c8-efd801b11339 */
   public void setTableid(String tableid) {
   	this.tableid = tableid;
   }
   
   /** @return the status
    * 
    * @pdOid 31edd0bb-78fc-4c94-848b-e6ff4425bf66 */
   public String getStatus() {
   	return status;
   }
   
   /** @param status the status to set
    * @pdOid a99219e5-9289-45f3-a67e-3a9b550f9ed2 */
   public void setStatus(String status) {
   	this.status = status;
   }
   
   /** @return the opid
    * 
    * @pdOid a078b32b-727a-4799-a25c-02a83358e67d */
   public String getOpid() {
   	return opid;
   }
   
   /** @param opid the opid to set
    * @pdOid ce84f59a-fab2-435c-8089-82ecb6b63986 */
   public void setOpid(String opid) {
   	this.opid = opid;
   }
   
   /** @return the opcode
    * 
    * @pdOid f3c5a332-22fe-4fd9-9b64-705e0a01b45d */
   public String getOpcode() {
   	return opcode;
   }
   
   /** @param opcode the opcode to set
    * @pdOid 330b8d59-fe1f-4f5a-8295-b585ac54160d */
   public void setOpcode(String opcode) {
   	this.opcode = opcode;
   }
   
   /** @return the opname
    * 
    * @pdOid 9dfd8618-a960-4445-91a7-75cb3d0d43bb */
   public String getOpname() {
   	return opname;
   }
   
   /** @param opname the opname to set
    * @pdOid b75c4d5f-5ee0-4c5b-b720-c6de1e47c88c */
   public void setOpname(String opname) {
   	this.opname = opname;
   }
   
   /** @return the sptime
    * 
    * @pdOid 86a639a5-70b2-4668-9689-516310224d3e */
   public String getSptime() {
   	return sptime;
   }
   
   /** @param sptime the sptime to set
    * @pdOid 33fea6d7-97dc-47d1-b850-77e9e68dd5a8 */
   public void setSptime(String sptime) {
   	this.sptime = sptime;
   }
   
   /** @return the spnode
    * 
    * @pdOid 555dcb06-b28e-435a-b7fc-567babac167a */
   public String getSpnode() {
   	return spnode;
   }
   
   /** @param spnode the spnode to set
    * @pdOid 39a3a34d-ca94-433b-94e2-3a3822c51608 */
   public void setSpnode(String spnode) {
   	this.spnode = spnode;
   }
   
   /** @return the tabcode
    * 
    * @pdOid 696f94b4-143e-49f1-890e-4cf4c9447796 */
   public String getTabcode() {
   	return tabcode;
   }
   
   /** @param tabcode the tabcode to set
    * @pdOid d1ec426b-34c9-4818-9e48-17a39eacd5dc */
   public void setTabcode(String tabcode) {
   	this.tabcode = tabcode;
   }
   
   /** @return the objectname
    * 
    * @pdOid 9afbbe72-f003-4464-bfaa-9d444794e07f */
   public String getObjectname() {
   	return objectname;
   }
   
   /** @param objectname the objectname to set
    * @pdOid d79cbc10-b427-412e-b1ad-ed2e436ee549 */
   public void setObjectname(String objectname) {
   	this.objectname = objectname;
   }
   
   /** @return the spnode_name
    * 
    * @pdOid 94ad8df5-8899-48a7-9c5e-299ccffae91c */
   public String getSpnodename() {
   	return spnodename;
   }
   
   /** @param spnodename
    * @pdOid ec1eea48-6c82-47a5-9cca-9947a5f98602 */
   public void setSpnode_name(String spnodename) {
   	this.spnodename = spnodename;
   }
   
   /** @return the isupload
    * 
    * @pdOid 423c75d7-1df2-49a5-ae28-9a3469eb7659 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** @param isupload the isupload to set
    * @pdOid 3e4e475f-d520-4f00-b758-1dbc6e4889f2 */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** @return the tablename
    * 
    * @pdOid cccc8fbb-e4a1-4116-8169-941ddbb054cb */
   public String getTablename() {
   	return tablename;
   }
   
   /** @param tablename the tablename to set
    * @pdOid ee3d8f27-3924-4a9a-ad94-9f9648b8a4d1 */
   public void setTablename(String tablename) {
   	this.tablename = tablename;
   }
   
   /** @return the datasource
    * 
    * @pdOid 4028edf8-84cb-41ee-a715-c7d917876010 */
   public String getDatasource() {
   	return datasource;
   }
   
   /** @param datasource the datasource to set
    * @pdOid 4631231c-4c4e-4d05-8e1f-b112cd3dc100 */
   public void setDatasource(String datasource) {
   	this.datasource = datasource;
   }
   
   /** @return the ud_id
    * 
    * @pdOid df5a9939-5c30-4ec2-a960-1b403250ce26 */
   public String getUd_id() {
   	return ud_id;
   }
   
   /** @param ud_id the ud_id to set
    * @pdOid a7760ac3-0e52-4a0f-9953-a0ffa97b298f */
   public void setUd_id(String ud_id) {
   	this.ud_id = ud_id;
   }
   
   /** @return the ud_type
    * 
    * @pdOid 91f43967-058c-48de-8690-d738d0a5be3b */
   public String getUd_type() {
   	return ud_type;
   }
   
   /** @param ud_type the ud_type to set
    * @pdOid 0f7cba13-8638-4285-b71a-c97eb51cecf9 */
   public void setUd_type(String ud_type) {
   	this.ud_type = ud_type;
   }

}