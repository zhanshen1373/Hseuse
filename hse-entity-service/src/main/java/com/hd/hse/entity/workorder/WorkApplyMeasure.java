/**
 * File:    UdZyxkZysq2precaution.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkZysq2precaution
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.workorder;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 1c9fd856-ee78-4ae5-b6dd-2685568db8bd */
@DBTable(tableName = "ud_zyxk_zysq2precaution")
public class WorkApplyMeasure extends com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 2b8badf8-b1df-4666-b3b4-8ac3bd7d8ca7 */
	private static final long serialVersionUID = 7235324390586389355L;
	/**
	 * 外键唯一标识
	 * 
	 * @pdOid 54372f92-2add-4a49-b8cc-7c93fb7c5c99
	 */
	@DBField(foreign = true)
	private String ud_zyxk_zysqid;
	/**
	 * 措施ID
	 * 
	 * @pdOid 966c4c79-ce11-45fd-a948-ee12cae442c0
	 */
	@DBField
	private String precautionid;
	/**
	 * 措施描述
	 * 
	 * @pdOid b88c28d5-fde0-40b9-83e5-cb8f522f0e45
	 */
	@DBField
	private String description;
	/**
	 * 措施类别
	 * 
	 * @pdOid 1b1fbb0c-e6fc-4421-9c33-a16641c2863f
	 */
	@DBField
	private String cstype;
	/**
	 * 是否选择
	 * 
	 * @pdOid 9a64d9f3-e9e7-4ec2-93d9-46a97ce56b7c
	 */
	@DBField
	private Integer isselect;
	/**
	 * 是否确认
	 * 
	 * @pdOid 7e4af5fd-f51f-4fb0-b4b0-2ab22360b7ec
	 */
	@DBField
	private Integer value;
	/**
	 * 唯一标识
	 * 
	 * @pdOid fb3f485d-1b73-49a2-93c5-3e0c3840bb4e
	 */
	@DBField(id = true)
	private String ud_zyxk_zysq2precautionid;
	/**
	 * 危害识别编号
	 * 
	 * @pdOid dad4c1d9-5b61-4f15-93ff-f12e77114006
	 */
	@DBField
	private String hazardid;
	/**
	 * 其他措施对应字段
	 * 
	 * @pdOid 0be707b2-952a-453f-a419-ccd036c9efcd
	 */
	@DBField
	private String vqtcsfield;
	/**
	 * 输入文本
	 * 
	 * @pdOid 0123a98b-88fc-4982-a40d-9cd99677256c
	 */
	@DBField
	private String inputtext;
	/**
	 * 上传
	 * 
	 * @pdOid cb1cf21b-3554-4160-a05d-a2860f2ec172
	 */
	@DBField
	private Integer isupload;
	/**
	 * a2
	 * 
	 * @pdOid 0bd87fbd-a995-43cd-a037-ee6131fd4042
	 */
	@DBField
	private String isconfirm;
	/**
	 * a3
	 * 
	 * @pdOid 5d3a5337-51aa-468d-8c4b-8268b0f8998a
	 */
	@DBField
	private Integer paixu;
	/**
	 * 是否必须落实措施
	 * 
	 * @pdOid 146f85a8-306f-4aff-9c2d-cfb712c7a5c6
	 */
	@DBField
	private Integer isnecessary;
	/**
	 * a4
	 * 
	 * @pdOid 66ce45bd-c69b-4985-b371-f497ca63ccb7
	 */
	@DBField
	private Integer issupplement;
	/**
	 * 是否从分析单选择的措施列
	 * 
	 * @pdOid b1593de1-dc5b-4aea-8c75-7636a1511025
	 */
	@DBField
	private Integer iszyfxfxadd;
	/**
	 * 是否伪删除
	 * 
	 * @pdOid d9acef67-bb29-4dd2-bf0d-b06dc1f60f64
	 */
	@DBField
	private Integer dr;
	/**
	 * 标记
	 * 
	 * @pdOid 45551454-f1bb-4d76-ba38-24cc06ce8f18
	 */
	@DBField
	private Integer tag;
	/**
	 * 检查结果
	 * 
	 * @pdOid 146eb790-244d-4a17-9a3e-8f7478dbffad
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
	 * @pdOid c2be6a73-eedd-4c88-ba4c-ff86739ad3bd
	 */
	private String whdesc;
	/**
	 * 用于逐条审批的表id
	 */
	private String ud_id;
	/**
	 * 用于逐条审批的审核人id
	 */
	private String personid;
	/**
	 * 用于逐条措施中的审核人描述
	 * 
	 * @pdOid 283f4d58-342b-4fe5-9c14-8050e04a6d1f
	 */
	private String persondesc;

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

	/**
	 * isqtjc:TODO(表示措施条目是否是气体检测).
	 */
	private Integer isqtjc;

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

	public String getUd_id() {
		return ud_id;
	}

	public void setUd_id(String ud_id) {
		this.ud_id = ud_id;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	/**
	 * persondesc.
	 * 
	 * 
	 * @return the persondesc
	 * 
	 * @pdOid 0c280e01-1fd9-4238-8fc3-4480b9e7c3b9
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
	 * @pdOid 12786c3e-f226-4074-9588-50866105b157
	 */
	public void setPersondesc(String persondesc) {
		this.persondesc = persondesc;
	}

	/**
	 * showallname:TODO(显示所有的刷卡人员信息).
	 */
	private String showallname;

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
	 * getWhdesc:(危害描述). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * 
	 * @return
	 * 
	 * @pdOid 61dba490-bd30-45e3-a1fa-77f460682376
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
	 * @pdOid cb19d815-321a-4300-b418-04500659d3e6
	 * @author lxf
	 */
	public void setWhdesc(String whdesc) {
		this.whdesc = whdesc;
	}

	/**
	 * 设置 措施ID 该字段是：措施ID
	 * 
	 * 
	 * @param precautionid
	 * @pdOid 6202f6a9-478c-473c-971a-d27ff58bda6e
	 */
	public void setPrecautionid(String precautionid) {
		this.precautionid = precautionid;
	}

	/**
	 * 获取 措施ID 该字段是：措施ID
	 * 
	 * 
	 * @pdOid f670d644-4590-46a3-b5d8-80d6caeebb4c
	 */
	public String getPrecautionid() {
		return precautionid;
	}

	/**
	 * 设置 措施描述 该字段是：措施描述
	 * 
	 * 
	 * @param description
	 * @pdOid a9daecae-304c-4a56-9da1-7b1395ee1b25
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取 措施描述 该字段是：措施描述
	 * 
	 * 
	 * @pdOid 04577386-a34f-4ed3-bce1-2da92f94c3db
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 措施类别 该字段是：措施类别
	 * 
	 * 
	 * @param cstype
	 * @pdOid fc0184a3-431d-46bc-a3bc-39f0909a98fe
	 */
	public void setCstype(String cstype) {
		this.cstype = cstype;
	}

	/**
	 * 获取 措施类别 该字段是：措施类别
	 * 
	 * 
	 * @pdOid 94de33fd-42f9-4ea0-9223-3968d9c24554
	 */
	public String getCstype() {
		return cstype;
	}

	/**
	 * 设置 是否选择 该字段是：是否选择
	 * 
	 * 
	 * @param isselect
	 * @pdOid 860a4801-ee9b-4359-9e27-4c39b3a85243
	 */
	public void setIsselect(Integer isselect) {
		this.isselect = isselect;
	}

	/**
	 * 获取 是否选择 该字段是：是否选择
	 * 
	 * 
	 * @pdOid 73f9455a-93a0-4ac2-8a65-f209b6ab2e4e
	 */
	public Integer getIsselect() {
		return isselect;
	}

	/**
	 * 设置 是否确认 该字段是：是否确认
	 * 
	 * 
	 * @param value
	 * @pdOid 67385a35-0f33-4496-a69e-d27be3ff166a
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * 获取 是否确认 该字段是：是否确认
	 * 
	 * 
	 * @pdOid 9dc4d9c3-6b58-4eee-a218-918af39c6b65
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @return the ud_zyxk_zysqid
	 * 
	 * @pdOid 688865f8-8325-4484-9815-fd7ae4008883
	 */
	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	/**
	 * @param ud_zyxk_zysqid
	 *            the ud_zyxk_zysqid to set
	 * @pdOid c1fb64eb-f677-4a4a-befa-82045a1d564a
	 */
	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	/**
	 * @return the ud_zyxk_zysq2precautionid
	 * 
	 * @pdOid 51061a4f-cb38-4cf9-9718-e69e7b7ab43c
	 */
	public String getUd_zyxk_zysq2precautionid() {
		return ud_zyxk_zysq2precautionid;
	}

	/**
	 * @param ud_zyxk_zysq2precautionid
	 *            the ud_zyxk_zysq2precautionid to set
	 * @pdOid ffb8b86b-5e0d-48e0-b303-bca6961d74cb
	 */
	public void setUd_zyxk_zysq2precautionid(String ud_zyxk_zysq2precautionid) {
		this.ud_zyxk_zysq2precautionid = ud_zyxk_zysq2precautionid;
	}

	/**
	 * 设置 危害识别编号 该字段是：危害识别编号
	 * 
	 * 
	 * @param hazardid
	 * @pdOid 704983af-367f-4a6f-ac15-1ceef9aaea6e
	 */
	public void setHazardid(String hazardid) {
		this.hazardid = hazardid;
	}

	/**
	 * 获取 危害识别编号 该字段是：危害识别编号
	 * 
	 * 
	 * @pdOid 412b2b07-8968-423b-8df2-2d3bded08d6a
	 */
	public String getHazardid() {
		return hazardid;
	}

	/**
	 * 设置 其他措施对应字段 该字段是：其他措施对应字段
	 * 
	 * 
	 * @param vqtcsfield
	 * @pdOid 3f33c271-b20c-42e0-adae-1fcf4534a8b4
	 */
	public void setVqtcsfield(String vqtcsfield) {
		this.vqtcsfield = vqtcsfield;
	}

	/**
	 * 获取 其他措施对应字段 该字段是：其他措施对应字段
	 * 
	 * 
	 * @pdOid fc628975-f864-422c-ab0f-827d797ba85c
	 */
	public String getVqtcsfield() {
		return vqtcsfield;
	}

	/**
	 * 设置 输入文本 该字段是：输入文本
	 * 
	 * 
	 * @param inputtext
	 * @pdOid 678e9b65-045c-4cf2-89a9-c72725b02677
	 */
	public void setInputtext(String inputtext) {
		this.inputtext = inputtext;
	}

	/**
	 * 获取 输入文本 该字段是：输入文本
	 * 
	 * 
	 * @pdOid e102ca19-ad65-4336-b8b9-9785f2984367
	 */
	public String getInputtext() {
		return inputtext;
	}

	/**
	 * 设置 上传 该字段是：上传
	 * 
	 * 
	 * @param isupload
	 * @pdOid 911e9af8-8309-44be-8870-f6527cf1fb8f
	 */
	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	/**
	 * 获取 上传 该字段是：上传
	 * 
	 * 
	 * @pdOid fc5f8188-e411-46d9-a05f-7f6fcab9a840
	 */
	public Integer getIsupload() {
		return isupload;
	}

	/**
	 * 设置 a2 该字段是：a2
	 * 
	 * 
	 * @param isconfirm
	 * @pdOid 31d3d420-a516-4494-8638-20b94b2f8dc9
	 */
	public void setIsconfirm(String isconfirm) {
		this.isconfirm = isconfirm;
	}

	/**
	 * 获取 a2 该字段是：a2
	 * 
	 * 
	 * @pdOid 71daf7b9-99da-4c3d-9e29-b8f567bba565
	 */
	public String getIsconfirm() {
		return isconfirm;
	}

	/**
	 * 设置 a3 该字段是：a3
	 * 
	 * 
	 * @param paixu
	 * @pdOid 56663f34-6739-4298-8883-1d56fd7bd2d2
	 */
	public void setPaixu(Integer paixu) {
		this.paixu = paixu;
	}

	/**
	 * 获取 a3 该字段是：a3
	 * 
	 * 
	 * @pdOid 7d13bd36-ffb9-43a9-9832-949d34ebdc16
	 */
	public Integer getPaixu() {
		return paixu;
	}

	/**
	 * 设置 是否必须落实措施 该字段是：是否必须落实措施
	 * 
	 * 
	 * @param isnecessary
	 * @pdOid 1d0ead87-81d3-40f8-901d-faedd2feb4a3
	 */
	public void setIsnecessary(Integer isnecessary) {
		this.isnecessary = isnecessary;
	}

	/**
	 * 获取 是否必须落实措施 该字段是：是否必须落实措施
	 * 
	 * 
	 * @pdOid ae0241a5-e2c8-4e34-830b-c32289141a95
	 */
	public Integer getIsnecessary() {
		return isnecessary;
	}

	/**
	 * 设置 a4 该字段是：a4
	 * 
	 * 
	 * @param issupplement
	 * @pdOid 5099f1bf-4831-4b14-bb99-773bcc6221ed
	 */
	public void setIssupplement(Integer issupplement) {
		this.issupplement = issupplement;
	}

	/**
	 * 获取 a4 该字段是：a4
	 * 
	 * 
	 * @pdOid 2f1fb5b2-8039-4a83-bb3b-ac0c86cb35c8
	 */
	public Integer getIssupplement() {
		return issupplement;
	}

	/**
	 * 设置 是否从分析单选择的措施列 该字段是：是否从分析单选择的措施列
	 * 
	 * 
	 * @param iszyfxfxadd
	 * @pdOid ce664199-9983-4563-aaa1-d6a990bdeb01
	 */
	public void setIszyfxfxadd(Integer iszyfxfxadd) {
		this.iszyfxfxadd = iszyfxfxadd;
	}

	/**
	 * 获取 是否从分析单选择的措施列 该字段是：是否从分析单选择的措施列
	 * 
	 * 
	 * @pdOid 18d81c20-3263-481c-ad66-445c602577e3
	 */
	public Integer getIszyfxfxadd() {
		return iszyfxfxadd;
	}

	/**
	 * 设置 是否伪删除 该字段是：是否伪删除
	 * 
	 * 
	 * @param dr
	 * @pdOid 3521c7a5-4db4-431f-a68a-2c6f8509c147
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 是否伪删除 该字段是：是否伪删除
	 * 
	 * 
	 * @pdOid 51d671c8-3a23-4276-9ab1-6693c0355b34
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 标记 该字段是：标记
	 * 
	 * 
	 * @param tag
	 * @pdOid 4dd02726-7c22-4eb6-b207-1419aa207770
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 获取 标记 该字段是：标记
	 * 
	 * 
	 * @pdOid ec94071f-6295-4d0f-9597-859b68c64e3b
	 */
	public Integer getTag() {
		return tag;
	}

	/**
	 * 获取措施状态 setStatus:(). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * 
	 * @pdOid 896db242-7c91-4ef2-b7dd-048c4516fb10
	 * @author zhulei
	 */
	public Integer getCheckresult() {
		return checkresult;
	}

	/**
	 * 设置措施状态 setStatus:(). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * 
	 * @param checkstatus
	 * @pdOid 589926f7-b97f-4d07-bcf4-c550b044726e
	 * @author zhulei
	 */
	public void setCheckresult(Integer checkstatus) {
		this.checkresult = checkstatus;
	}

	public String getZyptype() {
		return zyptype;
	}

	public void setZyptype(String zyptype) {
		this.zyptype = zyptype;
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

	/**
	 * getIsqtjc:(). <br/>
	 * date: 2015年6月5日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public Integer getIsqtjc() {
		return isqtjc;
	}

	/**
	 * setIsqtjc:(). <br/>
	 * date: 2015年6月5日 <br/>
	 * 
	 * @author lxf
	 * @param isqtjc
	 */
	public void setIsqtjc(Integer isqtjc) {
		this.isqtjc = isqtjc;
	}
}