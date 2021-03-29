/**
 * File:    UdZyxkZyspryjl.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZyspryjl
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid ec025052-f92a-4d4e-a5c2-bef002f13b36 */
@DBTable(tableName = "ud_zyxk_zyspryjl")
public class WorkApprovalPersonRecord extends
		com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 93839881-f48b-4c9a-b6ae-80bcc42bf450 */
	private static final long serialVersionUID = 4428984928114175966L;
	/**
	 * 唯一标识
	 * 
	 * @pdOid 87f2b49e-669e-4ea3-9d99-72d3e1cebf1c
	 */
	@DBField(id = true)
	private String ud_zyxk_zyspryjlid;
	/**
	 * 外键唯一标识
	 * 
	 * @pdOid 11bfd2ac-8388-439d-930a-00f0feb78bde
	 */
	@DBField(foreign = true)
	private String ud_zyxk_zysqid;
	/**
	 * 类别
	 * 
	 * @pdOid e0352bdd-610a-4e67-95e3-2607e4e13059
	 */
	@DBField
	private String dycode;
	/**
	 * 部门
	 * 
	 * @pdOid c77a3c14-d895-4361-9b96-edbe203ed43b
	 */
	@DBField
	private String dept;
	/**
	 * 人员
	 * 
	 * @pdOid 8068a13c-7ea4-47c6-8154-ca0a181d140c
	 */
	@DBField
	private String person;
	/**
	 * 时间
	 * 
	 * @pdOid 46a216a6-13b1-471b-968f-e71a619708c2
	 */
	@DBField
	private String sptime;
	/**
	 * 类型
	 * 
	 * @pdOid 21ba2af7-545a-4efd-97ef-89c07f6e3080
	 */
	@DBField
	private String type;
	/**
	 * 值信息
	 * 
	 * @pdOid 0d272e6d-3e56-40a6-88e5-40f42bae679d
	 */
	@DBField
	private String value;
	/**
	 * 审批环节
	 * 
	 * @pdOid 32f0cf6f-e71f-42c7-ae02-daebcc7a60ac
	 */
	@DBField
	private String spnode;
	/**
	 * 上传
	 * 
	 * @pdOid 8dfda551-d44d-4166-b465-44da3097034c
	 */
	@DBField
	private Integer isupload;
	/**
	 * a2
	 * 
	 * @pdOid 7f87db17-a99a-4acc-817a-8e58d6e374dd
	 */
	@DBField
	private Integer inputall;
	/**
	 * a3
	 * 
	 * @pdOid a58fa773-50c8-47b2-a15e-678c64eb7469
	 */
	@DBField
	private String person_name;
	/**
	 * a4
	 * 
	 * @pdOid b861199b-f710-4548-a2e6-24b32c2722cc
	 */
	@DBField
	private String tablename;
	/**
	 * a5
	 * 
	 * @pdOid f8ccba8b-baea-4915-85c6-e739de57321f
	 */
	@DBField
	private String tableid;

	/**
	 * 工种 Created by liuyang on 2016年11月11日
	 */
	@DBField
	private String job;
	/**
	 * 工种编号 Created by liuyang on 2016年11月11日
	 */
	@DBField
	private String code;
	@DBField
	/**
	 * 准操项目
	 * Created by liuyang on 2016年12月1日
	 */
	private String zcproject;

	/**
	 * 用于缓存保存tableid的值
	 * 
	 * @pdOid fe2e9e2b-814a-4a9d-ba97-9e219e2affe0
	 */
	private String temptableid;

	/**
	 * 作业审批权限id
	 */
	@DBField
	private String ud_zyxk_zyspqxid;

	/**
	 * temptableid.
	 * 
	 * 
	 * @return the temptableid
	 * 
	 * @pdOid ac99feb5-09bb-4d26-90dc-f4c4d3b4f187
	 */
	public String getTemptableid() {
		return temptableid;
	}

	/**
	 * temptableid.
	 * 
	 * 
	 * @param temptableid
	 *            the temptableid to set
	 * @pdOid 4e2aa200-5679-450b-b056-2745613a87bf
	 */
	public void setTemptableid(String temptableid) {
		this.temptableid = temptableid;
	}

	/**
	 * 设置 部门 该字段是：部门
	 * 
	 * @param dept
	 * @pdOid 71c38e28-3ccb-45fb-897a-f138b09b4c67
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * 获取 部门 该字段是：部门
	 * 
	 * 
	 * @pdOid e6ae1445-2301-41fe-b521-9d098ef6de51
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * 设置 人员 该字段是：人员
	 * 
	 * @param person
	 * @pdOid 773eff92-b91a-45d5-809a-3f92dfa278b7
	 */
	public void setPerson(String person) {
		this.person = person;
	}

	/**
	 * 获取 人员 该字段是：人员
	 * 
	 * 
	 * @pdOid ba77d2e2-a7c7-4693-9c10-4fded3b58ea4
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * 设置 时间 该字段是：时间
	 * 
	 * @param sptime
	 * @pdOid 8af15c10-db8b-4fa4-803a-be3c09b00723
	 */
	public void setSptime(String sptime) {
		this.sptime = sptime;
	}

	/**
	 * 获取 时间 该字段是：时间
	 * 
	 * 
	 * @pdOid 8cf0a9b9-e464-4d6c-9d6c-6fd1ae2957eb
	 */
	public String getSptime() {
		return sptime;
	}

	/**
	 * 设置 类型 该字段是：类型
	 * 
	 * @param type
	 * @pdOid 5e8bb41a-ef5b-45bb-b698-ed3df8408b3f
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 类型 该字段是：类型
	 * 
	 * 
	 * @pdOid 7de44cd1-2597-4cf6-a63d-38dbd527acc1
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 值信息 该字段是：值信息
	 * 
	 * @param value
	 * @pdOid a0f33dce-a6ca-46c6-827e-8a54de23ac99
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取 值信息 该字段是：值信息
	 * 
	 * 
	 * @pdOid e0cd9c8b-5c9f-4117-ab71-8944b4d9c1bb
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置 审批环节 该字段是：审批环节
	 * 
	 * @param spnode
	 * @pdOid d2d91efa-2035-4d8b-ab71-76d3c8921b92
	 */
	public void setSpnode(String spnode) {
		this.spnode = spnode;
	}

	/**
	 * 获取 审批环节 该字段是：审批环节
	 * 
	 * 
	 * @pdOid f6f71ee7-7725-47f4-90c9-d774d55fae1d
	 */
	public String getSpnode() {
		return spnode;
	}

	/**
	 * 设置 上传 该字段是：上传
	 * 
	 * @param isupload
	 * @pdOid 0c62b53a-de5e-48dd-8a2f-3c7772ed903f
	 */
	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	/**
	 * 获取 上传 该字段是：上传
	 * 
	 * 
	 * @pdOid a16b8cd9-6028-477f-a63f-2f731f99184d
	 */
	public Integer getIsupload() {
		return isupload;
	}

	/**
	 * 设置 a2 该字段是：a2
	 * 
	 * @param inputall
	 * @pdOid 282d1a90-5f1c-48a6-b8db-c11aa7ac9d65
	 */
	public void setInputall(Integer inputall) {
		this.inputall = inputall;
	}

	/**
	 * 获取 a2 该字段是：a2
	 * 
	 * 
	 * @pdOid 731ce8b5-a09d-4662-bc63-4c926df6b9b6
	 */
	public Integer getInputall() {
		return inputall;
	}

	/**
	 * 设置 a4 该字段是：a4
	 * 
	 * @param tablename
	 * @pdOid 33576cff-9200-496c-bf62-a4d55d80d011
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	/**
	 * 获取 a4 该字段是：a4
	 * 
	 * 
	 * @pdOid 60948d51-1de8-4de4-80d8-b7ac15805850
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * 设置 a5 该字段是：a5
	 * 
	 * @param tableid
	 * @pdOid bbd8dd2e-d11e-4572-b197-10f8ead3f8a0
	 */
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	/**
	 * 获取 a5 该字段是：a5
	 * 
	 * 
	 * @pdOid 07bda0e8-b5a6-4d9d-b2ac-22744df13dc9
	 */
	public String getTableid() {
		return tableid;
	}

	/**
	 * @return the ud_zyxk_zyspryjlid
	 * 
	 * @pdOid 19043f1a-c288-42fd-8b07-4e2aeff97bc8
	 */
	public String getUd_zyxk_zyspryjlid() {
		return ud_zyxk_zyspryjlid;
	}

	/**
	 * @param ud_zyxk_zyspryjlid
	 *            the ud_zyxk_zyspryjlid to set
	 * @pdOid 009eaa15-6e2e-490f-9c5f-7e915d7a012a
	 */
	public void setUd_zyxk_zyspryjlid(String ud_zyxk_zyspryjlid) {
		this.ud_zyxk_zyspryjlid = ud_zyxk_zyspryjlid;
	}

	/**
	 * @return the ud_zyxk_zysqid
	 * 
	 * @pdOid 03454e72-6a30-4c68-9bbb-8b14d04c52d7
	 */
	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	/**
	 * @param ud_zyxk_zysqid
	 *            the ud_zyxk_zysqid to set
	 * @pdOid 3eea74b3-eafb-45b7-ad25-f3ad34624624
	 */
	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	/**
	 * @return the person_name
	 * 
	 * @pdOid a2b866ec-53ac-49c1-a05b-ce10d5a33922
	 */
	public String getPerson_name() {
		return person_name;
	}

	/**
	 * @param person_name
	 *            the person_name to set
	 * @pdOid d53d7c46-16de-4170-831e-eabe4030bd72
	 */
	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	/** @pdOid ac8b7093-b6fc-4ecd-8bc7-3d0cb1669f64 */
	public String getDycode() {
		return dycode;
	}

	/**
	 * @param dycode
	 * @pdOid 4e9670e6-f77f-4fce-b4d1-473d2d82fda7
	 */
	public void setDycode(String dycode) {
		this.dycode = dycode;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getZcproject() {
		return zcproject;
	}

	public void setZcproject(String zcproject) {
		this.zcproject = zcproject;
	}
	public String getUd_zyxk_zyspqxid() {
		return ud_zyxk_zyspqxid;
	}

	public void setUd_zyxk_zyspqxid(String ud_zyxk_zyspqxid) {
		this.ud_zyxk_zyspqxid = ud_zyxk_zyspqxid;
	}

}