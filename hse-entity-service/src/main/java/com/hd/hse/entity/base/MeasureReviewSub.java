/**
 * File:    UdZyxkZycsfcline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZycsfcline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 95bf1413-c253-4975-ad9c-4657b406d9c2 */
@DBTable(tableName = "ud_zyxk_zycsfcline")
public class MeasureReviewSub extends com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 5cf4f8c1-60fb-43fd-9039-f47d78d2d7f7 */
	private static final long serialVersionUID = -4230543301787274155L;
	/**
	 * 描述
	 * 
	 * @pdOid 22d16c96-6c1a-49bf-90ea-7cf369be59ea
	 */
	@DBField
	private String description;
	/**
	 * 措施类型
	 * 
	 * @pdOid 2a90ab5e-7b67-4ba8-8e65-7c5105c00934
	 */
	@DBField
	private String cstype;
	/**
	 * 危害外键
	 * 
	 * @pdOid 8a09490c-8529-4f4c-9fc3-0f07ecd39956
	 */
	@DBField(foreign = true)
	private String hazardid;
	/**
	 * 是否选中
	 * 
	 * @pdOid e142f0cb-fd77-4ff2-9a49-767b7a00314d
	 */
	@DBField
	private Integer isselect;
	/**
	 * 外键
	 * 
	 * @pdOid 54bd048c-250a-4e1b-a169-d3ebe1177855
	 */
	@DBField(foreign = true)
	private String precautionid;
	/**
	 * 值
	 * 
	 * @pdOid 438ca1c6-052b-482b-9555-bb494ac91a2d
	 */
	@DBField
	private Integer value;
	/**
	 * 编号
	 * 
	 * @pdOid 9f611e62-33ac-4335-85b4-7a4b6a34c6cd
	 */
	@DBField
	private String zycsfcnum;
	/**
	 * 子表编号
	 * 
	 * @pdOid 6e27906c-8cdd-4867-9521-56f85d2f27cf
	 */
	@DBField(id = true)
	private String zycsfclinenum;
	/**
	 * 其他措施对应字段
	 * 
	 * @pdOid 44f6408c-7ace-427c-b620-67e06bb30aa7
	 */
	@DBField
	private String vqtcsfield;
	/**
	 * a3
	 * 
	 * @pdOid dce5954e-b86e-48bb-b04a-3848ebe0baed
	 */
	@DBField
	private String isconfirm;
	/**
	 * a4
	 * 
	 * @pdOid fbfd7109-59c8-40b2-83ae-8861aae30345
	 */
	@DBField
	private Integer paixu;
	/**
	 * 是否上传
	 * 
	 * @pdOid a5bfe971-cbff-448f-995a-36fbb0e63631
	 */
	@DBField
	private Integer isupload;
	/**
	 * 是否伪删除
	 * 
	 * @pdOid 6efd6351-8569-4f00-877a-a0ccf5a29d37
	 */
	@DBField
	private Integer dr;
	/**
	 * 标记
	 * 
	 * @pdOid 91a13d87-edc3-4403-83cb-ac9e7471b1f3
	 */
	@DBField
	private Integer tag;
	/**
	 * 检查结果
	 * 
	 * @pdOid 28e8a3f0-5e31-44ed-9d36-9b5f63cd77d3
	 */
	@DBField
	private Integer checkresult;
	/**
	 * 作业票类别 Created by liuyang on 2016年10月18日
	 */
	@DBField
	private String zyptype;
	/**
	 * 危害描述
	 * 
	 * @pdOid 808de8a7-d1df-44a7-a085-82a89abd83f3
	 */
	private String whdesc;
	/**
	 * 用于逐条措施中的审核人描述
	 * 
	 * @pdOid 870ba017-67a7-4221-9d5e-3613b0bc9017
	 */
	private String persondesc;

	/**
	 * showallname:TODO(显示所有的刷卡人员信息).
	 */
	private String showallname;
	/**
	 * isselectitem:TODO(当前选中项目).
	 */
	private boolean isselectitem;

	/**
	 * 措施类别描述
	 */
	private String cstydesc;
	/**
	 * oldcheckresult:TODO(旧的结果).
	 */
	private Integer oldcheckresult;

	public Integer getOldcheckresult() {
		return oldcheckresult;
	}

	public void setOldcheckresult(Integer oldcheckresult) {
		this.oldcheckresult = oldcheckresult;
	}

	public String getCstydesc() {
		return cstydesc;
	}

	public void setCstydesc(String cstydesc) {
		this.cstydesc = cstydesc;
	}

	/**
	 * getShowallname:(). <br/>
	 * date: 2014年12月5日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public String getShowallname() {
		return showallname;
	}

	/**
	 * setShowallname:(). <br/>
	 * date: 2014年12月5日 <br/>
	 * 
	 * @author lxf
	 * @param showallname
	 */
	public void setShowallname(String showallname) {
		this.showallname = showallname;
	}

	/**
	 * checkresult.
	 * 
	 * 
	 * @return the checkresult
	 * 
	 * @pdOid 0d6054e3-aeb9-408a-b098-538e51bc3e4a
	 */
	public Integer getCheckresult() {
		return checkresult;
	}

	/**
	 * checkresult.
	 * 
	 * 
	 * @param checkresult
	 *            the checkresult to set
	 * @pdOid b53ea48d-db73-4dbd-a34a-d9e610056555
	 */
	public void setCheckresult(Integer checkresult) {
		this.checkresult = checkresult;
	}

	public String getZyptype() {
		return zyptype;
	}

	public void setZyptype(String zyptype) {
		this.zyptype = zyptype;
	}

	/**
	 * 设置 描述 该字段是：描述
	 * 
	 * @param description
	 * @pdOid 8d892253-d175-497d-b01a-848576c883bb
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取 描述 该字段是：描述
	 * 
	 * 
	 * @pdOid 9aadc9da-f4d5-4ad2-b2c6-6d22934b7666
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 措施类型 该字段是：措施类型
	 * 
	 * @param cstype
	 * @pdOid 2856a58e-f79e-4d4c-9be0-a77488e2d51e
	 */
	public void setCstype(String cstype) {
		this.cstype = cstype;
	}

	/**
	 * 获取 措施类型 该字段是：措施类型
	 * 
	 * 
	 * @pdOid 00b1462a-ab7e-4fb3-98d9-3ad19db612af
	 */
	public String getCstype() {
		return cstype;
	}

	/**
	 * 设置 危害外键 该字段是：危害外键
	 * 
	 * @param hazardid
	 * @pdOid 7035dcf9-2e60-4f0c-a6cc-7356423006ee
	 */
	public void setHazardid(String hazardid) {
		this.hazardid = hazardid;
	}

	/**
	 * 获取 危害外键 该字段是：危害外键
	 * 
	 * 
	 * @pdOid 816f8567-a9a5-439c-9960-8fe84afc9e48
	 */
	public String getHazardid() {
		return hazardid;
	}

	/**
	 * 设置 是否选中 该字段是：是否选中
	 * 
	 * @param isselect
	 * @pdOid fab74927-138a-42a5-80f7-df2a061b7d20
	 */
	public void setIsselect(Integer isselect) {
		this.isselect = isselect;
	}

	/**
	 * 获取 是否选中 该字段是：是否选中
	 * 
	 * 
	 * @pdOid 2ac10d3d-3f2a-444e-95ff-0a6572084adc
	 */
	public Integer getIsselect() {
		return isselect;
	}

	/**
	 * 设置 外键 该字段是：外键
	 * 
	 * @param precautionid
	 * @pdOid 721a87b2-c610-45e6-af9f-16bee9a54f33
	 */
	public void setPrecautionid(String precautionid) {
		this.precautionid = precautionid;
	}

	/**
	 * 获取 外键 该字段是：外键
	 * 
	 * 
	 * @pdOid c37742a1-b236-41e3-9682-d136af9c3d60
	 */
	public String getPrecautionid() {
		return precautionid;
	}

	/**
	 * 设置 值 该字段是：值
	 * 
	 * @param value
	 * @pdOid 7eb287c3-f782-4447-9952-c6fc754752c0
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * 获取 值 该字段是：值
	 * 
	 * 
	 * @pdOid 3b7400d2-bde6-4d5f-80cf-52e084f36855
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 设置 编号 该字段是：编号
	 * 
	 * @param zycsfcnum
	 * @pdOid 4472c8af-eeb6-45c3-95f1-6dc55debcdc6
	 */
	public void setZycsfcnum(String zycsfcnum) {
		this.zycsfcnum = zycsfcnum;
	}

	/**
	 * 获取 编号 该字段是：编号
	 * 
	 * 
	 * @pdOid e316c43f-82dc-4e97-ab4e-e694d588102d
	 */
	public String getZycsfcnum() {
		return zycsfcnum;
	}

	/**
	 * 设置 子表编号 该字段是：子表编号
	 * 
	 * @param zycsfclinenum
	 * @pdOid 850dedc9-a6c9-4fce-848e-bee93cd263f8
	 */
	public void setZycsfclinenum(String zycsfclinenum) {
		this.zycsfclinenum = zycsfclinenum;
	}

	/**
	 * 获取 子表编号 该字段是：子表编号
	 * 
	 * 
	 * @pdOid 2a9eb3d4-75f5-47ce-9045-8eadf4145741
	 */
	public String getZycsfclinenum() {
		return zycsfclinenum;
	}

	/**
	 * 设置 其他措施对应字段 该字段是：其他措施对应字段
	 * 
	 * @param vqtcsfield
	 * @pdOid b8199922-e2d4-4300-9db3-108e595419bf
	 */
	public void setVqtcsfield(String vqtcsfield) {
		this.vqtcsfield = vqtcsfield;
	}

	/**
	 * 获取 其他措施对应字段 该字段是：其他措施对应字段
	 * 
	 * 
	 * @pdOid 6f6f9f6c-68e8-4a4a-a114-8b5566e4f82b
	 */
	public String getVqtcsfield() {
		return vqtcsfield;
	}

	/**
	 * 设置 a3 该字段是：a3
	 * 
	 * @param isconfirm
	 * @pdOid 3a592faa-baad-4981-9831-c2d47b975e71
	 */
	public void setIsconfirm(String isconfirm) {
		this.isconfirm = isconfirm;
	}

	/**
	 * 获取 a3 该字段是：a3
	 * 
	 * 
	 * @pdOid fcaa5452-e831-4a04-a142-b046411bde58
	 */
	public String getIsconfirm() {
		return isconfirm;
	}

	/**
	 * 设置 a4 该字段是：a4
	 * 
	 * @param paixu
	 * @pdOid 18f91cac-e4eb-471f-b79e-1147247611c2
	 */
	public void setPaixu(Integer paixu) {
		this.paixu = paixu;
	}

	/**
	 * 获取 a4 该字段是：a4
	 * 
	 * 
	 * @pdOid 6127f874-e31c-407f-a666-a9eda4536289
	 */
	public Integer getPaixu() {
		return paixu;
	}

	/**
	 * 设置 是否上传 该字段是：是否上传
	 * 
	 * @param isupload
	 * @pdOid ca811ce4-6a45-4b47-abcd-74c89429db22
	 */
	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	/**
	 * 获取 是否上传 该字段是：是否上传
	 * 
	 * 
	 * @pdOid bd34a626-03ba-400b-9f17-de1d2894cdb7
	 */
	public Integer getIsupload() {
		return isupload;
	}

	/**
	 * 设置 是否伪删除 该字段是：是否伪删除
	 * 
	 * @param dr
	 * @pdOid e37db1c7-5c18-4ed2-83ae-8c27ff02a404
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 是否伪删除 该字段是：是否伪删除
	 * 
	 * 
	 * @pdOid 8e97d50d-68d9-46bd-8665-faa880da847e
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 标记 该字段是：标记
	 * 
	 * @param tag
	 * @pdOid 5659ea3a-645e-4444-93ed-071fd20000a4
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 获取 标记 该字段是：标记
	 * 
	 * 
	 * @pdOid a61fbf36-a06f-43aa-9ef0-195a0f2129d7
	 */
	public Integer getTag() {
		return tag;
	}

	/**
	 * getWhdesc:(危害描述). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * 
	 * @return
	 * 
	 * @pdOid 5651191c-2110-44c0-ba7c-a9f843297750
	 * @author lxf
	 */
	public String getWhdesc() {
		return whdesc;
	}

	/**
	 * setWhdesc:(危害描述). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * 
	 * @param whdesc
	 * @pdOid cab4261b-93dc-4640-842d-e07c115d7303
	 * @author lxf
	 */
	public void setWhdesc(String whdesc) {
		this.whdesc = whdesc;
	}

	/**
	 * persondesc.
	 * 
	 * 
	 * @return the persondesc
	 * 
	 * @pdOid 6c6934c4-3e4f-4fdd-9fd7-48af5730173a
	 */
	public String getPersondesc() {
		return persondesc;
	}

	/**
	 * persondesc.
	 * 
	 * 
	 * @param persondesc
	 *            the persondesc to set
	 * @pdOid ebd255b3-a522-4fcd-8df2-513f4a8f6ffd
	 */
	public void setPersondesc(String persondesc) {
		this.persondesc = persondesc;
	}

	/**
	 * isTitle:TODO(是否是标题项).
	 */
	private boolean isTitle = false;

	/**
	 * isTitle.
	 * 
	 * @return the isTitle
	 */
	public boolean isTitle() {
		return isTitle;
	}

	/**
	 * isTitle.
	 * 
	 * @param isTitle
	 *            the isTitle to set
	 */
	public void setTitle(boolean isTitle) {
		this.isTitle = isTitle;
	}

	/**
	 * isIsselectitem:(当前选中项目). <br/>
	 * date: 2015年2月11日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public boolean isIsselectitem() {
		return isselectitem;
	}

	/**
	 * setIsselectitem:(当前选中项目). <br/>
	 * date: 2015年2月11日 <br/>
	 * 
	 * @author lxf
	 * @param isselectitem
	 */
	public void setIsselectitem(boolean isselectitem) {
		this.isselectitem = isselectitem;
	}
}