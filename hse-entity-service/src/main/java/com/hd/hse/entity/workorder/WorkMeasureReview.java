/**
 * File:    UdZyxkZycsfc.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZycsfc
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 79234f3d-35e1-4315-b77c-98935b659c50 */
@DBTable(tableName = "ud_zyxk_zycsfc")
public class WorkMeasureReview extends com.hd.hse.common.entity.SuperEntity {
   /** @pdOid 3224a0d7-cea4-4d8f-a8f9-6a0704fdeffc */
   private static final long serialVersionUID = -3191834546806434048L;
   /** 描述
    * 
    * @pdOid 314180d7-aaaa-4886-85a2-2923e0977586 */
   @DBField
   private String description;
   /** 外键
    * 
    * @pdOid b3d30c5f-6bcb-4bca-9ff5-5e1029c7d4e2 */
   @DBField(foreign=true)
   private String ud_zyxk_zysqid;
   /** 作业许可证号
    * 
    * @pdOid 0e20b22c-2a22-487f-871a-7cbf6568d913 */
   @DBField
   private String zysqnum;
   /** 审批时间
    * 
    * @pdOid c0abbe74-4cf4-4f55-8cbe-e6bdab038934 */
   @DBField
   private String spdate;
   /** 作业措施复查编号
    * 
    * @pdOid 923886ea-ada8-456d-9fe8-d152ca2bcca6 */
   @DBField(id=true)
   private String zycsfcnum;
   /** 措施刷卡人
    * 
    * @pdOid f232b864-a040-49e6-aab2-8d3e81f8ad54 */
   @DBField
   private String spperson;
   /** 措施确认人
    * 
    * @pdOid da70f8c1-2b98-4701-b725-36bdff027e9c */
   @DBField
   private String qrperson;
   /** 确认热刷卡时间
    * 
    * @pdOid c752c0be-9d66-4ffd-a598-c7fe5b8e060e */
   @DBField
   private String qrdate;
   /** 措施批准人
    * 
    * @pdOid a4a618bd-8a32-4950-9901-c93505142539 */
   @DBField
   private String pzperson;
   /** 批准时间
    * 
    * @pdOid aac445b3-6aec-4c37-a3ec-6e01a0ef0cd5 */
   @DBField
   private String pzdate;
   /** 页面标示,区分“延期”和“复查”
    * 
    * @pdOid df49f6d2-8077-4724-bc26-cc8d3e3b2223 */
   @DBField
   private String pagetype;
   /** 状态
    * 
    * @pdOid 20d0c53a-9e18-4ae5-a393-9ab3082c70be */
   @DBField
   private String status;
   /** a2
    * 
    * @pdOid fd44b103-c8c5-4663-8f0e-8a88249c0528 */
   @DBField
   private String datasource;
   /** 上传
    * 
    * @pdOid 9439c2d2-8e6a-452f-bd2b-0549dac26fdb */
   @DBField
   private Integer isupload;
   /** 监护???式
    * 
    * @pdOid 76f11e7c-787c-4cb3-892b-c12af2ba5b81 */
   @DBField
   private String jhfs;
   /** 班次
    * 
    * @pdOid ca9c748c-cdf6-4b35-b79c-4db2195d4748 */
   @DBField
   private String shift;
   /** 监护人
    * 
    * @pdOid 1e9f5ce2-8d61-43fd-ad54-eb5b117a9723 */
   @DBField
   private String jhperson;
   /** 监护人描述
    * 
    * @pdOid 9c0f8c20-8d03-4a45-b05e-4ad0ecac67bf */
   @DBField
   private String jhperson_desc;
   /** 监护人刷卡时间
    * 
    * @pdOid 70d04e12-4203-4662-b92f-d995e60ba4ef */
   @DBField
   private String jhdate;
   /** 标记
    * 
    * @pdOid 3b272034-33ca-4088-9b65-c74d09fac987 */
   @DBField
   private Integer tag;
   /** 是否伪删除
    * 
    * @pdOid 79ffa8f7-9ccd-4271-b84d-83e5bf1741e6 */
   @DBField
   private Integer dr;
   /** 措施编号
    * 
    * @pdOid 0eae2a81-62d5-461f-b63b-4de3fb71eda2 */
   @DBField
	private Integer csnum;

	/**
	 * cssavenum:TODO(界面编码).
	 */
	@DBField
	private String cssavenum;

   /** 设置 描述
    * 该字段是：描述
    * 
    * @param description
    * @pdOid ba6f8d63-45c3-4908-8bce-6f143ec5a585 */
   public void setDescription(String description) {
   	this.description = description;
   }
   
   /** 获取 描述
    * 该字段是：描述
    * 
    * 
    * @pdOid f8abf346-5cb8-4bd0-98dc-c8715df0f071 */
   public String getDescription() {
   	return description;
   }
   
   /** 设置 作业许可证号
    * 该字段是：作业许可证号
    * 
    * @param zysqnum
    * @pdOid 2594bd2d-e24b-42db-a7d2-b11e41eda221 */
   public void setZysqnum(String zysqnum) {
   	this.zysqnum = zysqnum;
   }
   
   /** 获取 作业许可证号
    * 该字段是：作业许可证号
    * 
    * 
    * @pdOid ff94d146-dec4-46a7-9b41-4b037122da44 */
   public String getZysqnum() {
   	return zysqnum;
   }
   
   /** 设置 审批时间
    * 该字段是：审批时间
    * 
    * @param spdate
    * @pdOid 9c81d133-2fbf-4f40-a72a-0d9b6c3e43b1 */
   public void setSpdate(String spdate) {
   	this.spdate = spdate;
   }
   
   /** 获取 审批时间
    * 该字段是：审批时间
    * 
    * 
    * @pdOid 4d20d6cb-18f5-4778-8f25-178de8766eaf */
   public String getSpdate() {
   	return spdate;
   }
   
   /** 设置 作业措施复查编号
    * 该字段是：作业措施复查编号
    * 
    * @param zycsfcnum
    * @pdOid 6d42ffee-60de-4a24-81f7-fbd76f669dbf */
   public void setZycsfcnum(String zycsfcnum) {
   	this.zycsfcnum = zycsfcnum;
   }
   
   /** 获取 作业措施复查编号
    * 该字段是：作业措施复查编号
    * 
    * 
    * @pdOid 2b4aa3e6-b257-43b6-9eae-d49354a2433a */
   public String getZycsfcnum() {
   	return zycsfcnum;
   }
   
   /** 设置 措施刷卡人
    * 该字段是：措施刷卡人
    * 
    * @param spperson
    * @pdOid 387b7310-76e3-4394-804a-2625a7355706 */
   public void setSpperson(String spperson) {
   	this.spperson = spperson;
   }
   
   /** 获取 措施刷卡人
    * 该字段是：措施刷卡人
    * 
    * 
    * @pdOid 9129f5c1-9703-423e-8d76-f2be0d0df39d */
   public String getSpperson() {
   	return spperson;
   }
   
   /** 设置 措施确认人
    * 该字段是：措施确认人
    * 
    * @param qrperson
    * @pdOid fd95ffd2-72ee-47c9-9cfa-27795f692e53 */
   public void setQrperson(String qrperson) {
   	this.qrperson = qrperson;
   }
   
   /** 获取 措施确认人
    * 该字段是：措施确认人
    * 
    * 
    * @pdOid 9d855cdf-b3f5-4c0f-a5c8-78ffb9d4f9d2 */
   public String getQrperson() {
   	return qrperson;
   }
   
   /** 设置 确认热刷卡时间
    * 该字段是：确认热刷卡时间
    * 
    * @param qrdate
    * @pdOid c940f970-27ef-4365-97d9-f42f0c6cb6f5 */
   public void setQrdate(String qrdate) {
   	this.qrdate = qrdate;
   }
   
   /** 获取 确认热刷卡时间
    * 该字段是：确认热刷卡时间
    * 
    * 
    * @pdOid 1f0a7399-8bad-4350-bf66-708cbdaad948 */
   public String getQrdate() {
   	return qrdate;
   }
   
   /** 设置 措施批准人
    * 该字段是：措施批准人
    * 
    * @param pzperson
    * @pdOid e818b56e-eb93-4156-baaa-fd61dea6af72 */
   public void setPzperson(String pzperson) {
   	this.pzperson = pzperson;
   }
   
   /** 获取 措施批准人
    * 该字段是：措施批准人
    * 
    * 
    * @pdOid 5091dfda-b7ea-4326-a21f-ca9a9671aac0 */
   public String getPzperson() {
   	return pzperson;
   }
   
   /** 设置 批准时间
    * 该字段是：批准时间
    * 
    * @param pzdate
    * @pdOid e25dc039-d8a5-4db2-908b-41bf65a1c7cf */
   public void setPzdate(String pzdate) {
   	this.pzdate = pzdate;
   }
   
   /** 获取 批准时间
    * 该字段是：批准时间
    * 
    * 
    * @pdOid 8a135deb-d6a5-4ef2-b747-d074745f5141 */
   public String getPzdate() {
   	return pzdate;
   }
   
   /** 设置 页面标示
    * 该字段是：页面标示
    * 
    * @param pagetype
    * @pdOid b05ed839-815c-42e8-8654-9c109fddb5fb */
   public void setPagetype(String pagetype) {
   	this.pagetype = pagetype;
   }
   
   /** 获取 页面标示
    * 该字段是：页面标示
    * 
    * 
    * @pdOid eb00f13f-ae1a-434e-90b5-ba633843757c */
   public String getPagetype() {
   	return pagetype;
   }
   
   /** 设置 状态
    * 该字段是：状态
    * 
    * @param status
    * @pdOid 1107a2ee-0cce-49e7-9899-667d2e95a102 */
   public void setStatus(String status) {
   	this.status = status;
   }
   
   /** 获取 状态
    * 该字段是：状态
    * 
    * 
    * @pdOid 6f88b227-9077-403b-97f9-22727641e5f0 */
   public String getStatus() {
   	return status;
   }
   
   /** 设置 a2
    * 该字段是：a2
    * 
    * @param datasource
    * @pdOid 9d332005-03cd-481e-b0a2-8afb954c2856 */
   public void setDatasource(String datasource) {
   	this.datasource = datasource;
   }
   
   /** 获取 a2
    * 该字段是：a2
    * 
    * 
    * @pdOid 0f4a4e3e-2852-48c9-a5f7-7129970f7ee7 */
   public String getDatasource() {
   	return datasource;
   }
   
   /** 设置 上传
    * 该字段是：上传
    * 
    * @param isupload
    * @pdOid 7c38b87a-d6fd-456a-a06c-c90ab9c85a6e */
   public void setIsupload(Integer isupload) {
   	this.isupload = isupload;
   }
   
   /** 获取 上传
    * 该字段是：上传
    * 
    * 
    * @pdOid 35f227a5-f8d9-48cc-9c40-4337b557fd60 */
   public Integer getIsupload() {
   	return isupload;
   }
   
   /** 设置 监护???式
    * 该字段是：监护???式
    * 
    * @param jhfs
    * @pdOid 54f48d54-8de9-46a3-a13c-91ad2d60057f */
   public void setJhfs(String jhfs) {
   	this.jhfs = jhfs;
   }
   
   /** 获取 监护???式
    * 该字段是：监护???式
    * 
    * 
    * @pdOid f37405fe-a986-4308-a5b1-860f26c2a710 */
   public String getJhfs() {
   	return jhfs;
   }
   
   /** 设置 班次
    * 该字段是：班次
    * 
    * @param shift
    * @pdOid e9081c2a-182c-44d5-ac36-20eec7efd848 */
   public void setShift(String shift) {
   	this.shift = shift;
   }
   
   /** 获取 班次
    * 该字段是：班次
    * 
    * 
    * @pdOid 8211134a-c51b-4eee-b7db-c9051ffb46d2 */
   public String getShift() {
   	return shift;
   }
   
   /** 设置 监护人
    * 该字段是：监护人
    * 
    * @param jhperson
    * @pdOid 71ee95a8-594c-4f7f-af96-9181a437c196 */
   public void setJhperson(String jhperson) {
   	this.jhperson = jhperson;
   }
   
   /** 获取 监护人
    * 该字段是：监护人
    * 
    * 
    * @pdOid a822322f-f60f-4e3e-9a1c-7fed0562b0d8 */
   public String getJhperson() {
   	return jhperson;
   }
   
   /** @return the ud_zyxk_zysqid
    * 
    * @pdOid 3ba27781-abe2-44b7-98f0-209c036e17ba */
   public String getUd_zyxk_zysqid() {
   	return ud_zyxk_zysqid;
   }
   
   /** @param ud_zyxk_zysqid the ud_zyxk_zysqid to set
    * @pdOid 519592ab-4305-44f0-ad50-9ba71a6ac283 */
   public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
   	this.ud_zyxk_zysqid = ud_zyxk_zysqid;
   }
   
   /** @return the jhperson_desc
    * 
    * @pdOid 12942946-af15-4434-90ca-ee39cbff7875 */
   public String getJhperson_desc() {
   	return jhperson_desc;
   }
   
   /** @param jhperson_desc the jhperson_desc to set
    * @pdOid 235f1eac-2bf6-43d5-acac-bce6319dd9f2 */
   public void setJhperson_desc(String jhperson_desc) {
   	this.jhperson_desc = jhperson_desc;
   }
   
   /** 设置 监护人刷卡时间
    * 该字段是：监护人刷卡时间
    * 
    * @param jhdate
    * @pdOid a381e9f4-9b22-42ff-bb14-f0663fee3a34 */
   public void setJhdate(String jhdate) {
   	this.jhdate = jhdate;
   }
   
   /** 获取 监护人刷卡时间
    * 该字段是：监护人刷卡时间
    * 
    * 
    * @pdOid 78942d44-339a-43ea-aba4-b19ef3bfed59 */
   public String getJhdate() {
   	return jhdate;
   }
   
   /** 设置 标记
    * 该字段是：标记
    * 
    * @param tag
    * @pdOid 43026eb9-20ad-4b52-b522-05dfc9262c95 */
   public void setTag(Integer tag) {
   	this.tag = tag;
   }
   
   /** 获取 标记
    * 该字段是：标记
    * 
    * 
    * @pdOid cdf3f8ce-a4cb-4fd8-83ec-52bddcaac142 */
   public Integer getTag() {
   	return tag;
   }
   
   /** 设置 是否伪删除
    * 该字段是：是否伪删除
    * 
    * @param dr
    * @pdOid 1e3a5776-e980-439b-b9d5-ca9030aee8ab */
   public void setDr(Integer dr) {
   	this.dr = dr;
   }
   
   /** 获取 是否伪删除
    * 该字段是：是否伪删除
    * 
    * 
    * @pdOid ce873854-d756-4486-aed1-f83147ee63ef */
   public Integer getDr() {
   	return dr;
   }
   
   /** @pdOid 24d1f0fc-818b-462e-a8a6-46abed43c616 */
   public Integer getCsnum() {
   	return csnum;
   }
   
   /** @param csnum
    * @pdOid ae0a5cbe-2aba-42b6-b347-8b3d1fd988aa */
   public void setCsnum(Integer csnum) {
		this.csnum = csnum;
	}

	public String getCssavenum() {
		return cssavenum;
	}

	public void setCssavenum(String cssavenum) {
		this.cssavenum = cssavenum;
	}

}