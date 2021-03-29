/**
 * File:    UdZyxkQtjcline.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-26 17:26:39
 * Purpose: 定义数据类 UdZyxkQtjcline
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 80e619b5-627f-4594-93d3-496018b1f4b2 */
@DBTable(tableName = "ud_zyxk_qtjcline")
public class GasDetectSub extends com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 87a73a22-f239-4f59-b77f-16f97d508247 */
	private static final long serialVersionUID = 912220943649857908L;
	/** @pdOid 0551b9b6-9023-411e-864d-72b70202498d */
	@DBField(id = true)
	private String ud_zyxk_qtjclineid;
	/**
	 * 主键
	 * 
	 * @pdOid b4a5dbce-60b4-4b69-b120-cba701f7a95c
	 */
	@DBField
	private String ud_zyxk_zysqid;
	/**
	 * 气体类别
	 * 
	 * @pdOid 6212020b-82fb-401e-b0a7-8707697efb77
	 */
	@DBField
	private String qttype;
	/**
	 * 气体值
	 * 
	 * @pdOid c7b03344-f583-4ed4-ae0e-58a9183c3d79
	 */
	@DBField
	private Float qtvalue;
	/**
	 * 序列号
	 * 
	 * @pdOid 31b7e0b8-b718-4d94-ad27-fa77bd86cbdf
	 */
	@DBField
	private Integer seqnum;
	/**
	 * 外键
	 * 
	 * @pdOid 5a28be9c-3ac6-48a2-946c-984285e5100e
	 */
	@DBField(foreign = true)
	private String ud_zyxk_qtjcid;
	/**
	 * 允许限度
	 * 
	 * @pdOid a9acb7f6-0f0b-4e69-b0e7-3f9e8bfe9be7
	 */
	@DBField
	private String yxlimit;
	/**
	 * 上传
	 * 
	 * @pdOid 1a981642-b1a7-4a86-ab3a-71bed4c0eb83
	 */
	@DBField
	private Integer isupload;
	/**
	 * a3
	 * 
	 * @pdOid dab7773a-9627-45c8-a572-50c8499c550a
	 */
	@DBField
	private String qtresult;
	/**
	 * 是否伪删除
	 * 
	 * @pdOid 41ba0cc8-374f-46f3-9a21-31bd662121e6
	 */
	@DBField
	private Integer dr;
	/**
	 * 标记
	 * 
	 * @pdOid b86cb92f-4406-4a98-9949-4a69c1dbacfd
	 */
	@DBField
	private Integer tag;
	/**
	 * 审批完成标记
	 * 
	 * @pdOid 78536332-ec77-4202-b709-78d5038c121b
	 */
	private String writenbypda;
	/**
	 * domainid:TODO(气体检测主类别).
	 * 
	 * 
	 * @pdOid 48aa3187-3a65-4056-933a-c8032509ea24
	 */
	private String domainid;
	/**
	 * description:TODO(气体类型描述).
	 * 
	 * 
	 * @pdOid e6a34954-661c-4929-a834-3f6d1b6d6344
	 */
	private String description;
	/**
	 * maxvalue:TODO(该气体范围的上线).
	 * 
	 * 
	 * @pdOid 99aff1c9-0e20-442e-b35b-260d9ff58027
	 */
	private Float maxvalue;
	/**
	 * minvalue:TODO(该气体范围的下线).
	 * 
	 * 
	 * @pdOid 1c8807db-076b-4557-9a8a-8932680ec0b7
	 */
	private Float minvalue;
	/**
	 * isbj:TODO(气体范围中是否包括边界).
	 * 
	 * 
	 * @pdOid 2a08f1ce-6c26-46af-bfcc-661f7aedee52
	 */
	private Integer isbj;

	/**
	 * 新增气体检测数量字段
	 * 
	 * @pdOid 0da69c0b-6869-4457-8755-c6af7fdfdbf7
	 */
	@DBField
	private String qtsvalue;
	/**
	 * 气体检测是否合格
	 */
	private int isPass;

	public String getQtsvalue() {

		return qtsvalue;
	}

	public void setQtsvalue(String qtsvalue) {

		this.qtsvalue = qtsvalue;
	}

	/**
	 * ud_zyxk_qtjclineid.
	 * 
	 * 
	 * @return the ud_zyxk_qtjclineid
	 * 
	 * @pdOid 5dcdddb2-0e93-44f4-b8ff-0f84f5126e8d
	 */
	public String getUd_zyxk_qtjclineid() {
		return ud_zyxk_qtjclineid;
	}

	/**
	 * ud_zyxk_qtjclineid.
	 * 
	 * 
	 * @param ud_zyxk_qtjclineid
	 *            the ud_zyxk_qtjclineid to set
	 * @pdOid 03c7637b-e47c-4d78-9618-25e4859ef8f3
	 */
	public void setUd_zyxk_qtjclineid(String ud_zyxk_qtjclineid) {
		this.ud_zyxk_qtjclineid = ud_zyxk_qtjclineid;
	}

	/** @pdOid 9043dc07-1057-47ef-b6ae-978fc3c06ecb */
	public String getDomainid() {
		return domainid;
	}

	/**
	 * @param domainid
	 * @pdOid d02418c6-2e92-4ee0-8773-fc19d9fb752d
	 */
	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	/** @pdOid 633177d2-7ab1-488f-8216-d8a6416aab2d */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 * @pdOid 26e49e00-d91e-43fb-92ca-850ff452cd44
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @pdOid 0add9e7e-4fa9-461f-9ced-daa8d5ad1917 */
	public Float getMaxvalue() {
		return maxvalue;
	}

	/**
	 * @param maxvalue
	 * @pdOid b449ef5a-5a90-4177-919a-aebf2123a9b1
	 */
	public void setMaxvalue(Float maxvalue) {
		this.maxvalue = maxvalue;
	}

	/** @pdOid 412a6237-35c3-4027-aba9-9ef59194b035 */
	public Float getMinvalue() {
		return minvalue;
	}

	/**
	 * @param minvalue
	 * @pdOid 5d6e6ac0-a3e0-40f2-aaf8-4d4e092918a5
	 */
	public void setMinvalue(Float minvalue) {
		this.minvalue = minvalue;
	}

	/** @pdOid 027cd561-4436-4233-872b-b45799cde215 */
	public Integer getIsbj() {
		return isbj;
	}

	/**
	 * @param isbj
	 * @pdOid 226633f1-7dfe-43d8-beb9-f009bfa1f377
	 */
	public void setIsbj(Integer isbj) {
		this.isbj = isbj;
	}

	/**
	 * @return the ud_zyxk_zysqid
	 * 
	 * @pdOid ba9d6a8b-4ce5-4960-8902-a0cfcd6876ac
	 */
	public String getUd_zyxk_zysqid() {
		return ud_zyxk_zysqid;
	}

	/**
	 * @param ud_zyxk_zysqid
	 *            the ud_zyxk_zysqid to set
	 * @pdOid ea5469d3-7e73-4df0-9eda-cee989ef2aa2
	 */
	public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
		this.ud_zyxk_zysqid = ud_zyxk_zysqid;
	}

	/**
	 * @return the ud_zyxk_qtjcid
	 * 
	 * @pdOid 46554c21-0f96-44d1-9fb7-bad7cbb46370
	 */
	public String getUd_zyxk_qtjcid() {
		return ud_zyxk_qtjcid;
	}

	/**
	 * @param ud_zyxk_qtjcid
	 *            the ud_zyxk_qtjcid to set
	 * @pdOid dc50fdf4-8793-41be-8d84-b3cdd1249746
	 */
	public void setUd_zyxk_qtjcid(String ud_zyxk_qtjcid) {
		this.ud_zyxk_qtjcid = ud_zyxk_qtjcid;
	}

	/**
	 * 设置 气体类别 该字段是：气体类别
	 * 
	 * @param qttype
	 * @pdOid 2f7d2aaa-cbdf-480f-ba11-604cc7ddeb53
	 */
	public void setQttype(String qttype) {
		this.qttype = qttype;
	}

	/**
	 * 获取 气体类别 该字段是：气体类别
	 * 
	 * 
	 * @pdOid 0a0ad3d3-2320-4a7b-abe2-7bfe1a95c4cf
	 */
	public String getQttype() {
		return qttype;
	}

	/**
	 * 设置 气体值 该字段是：气体值
	 * 
	 * @param qtvalue
	 * @pdOid fc7f1035-9b3d-4be7-85e1-9a0595a22353
	 */
	public void setQtvalue(Float qtvalue) {
		this.qtvalue = qtvalue;
	}

	/**
	 * 获取 气体值 该字段是：气体值
	 * 
	 * 
	 * @pdOid 82ad9403-ed9c-4c01-bbf3-f629921283ea
	 */
	public Float getQtvalue() {
		return qtvalue;
	}

	/**
	 * 设置 序列号 该字段是：序列号
	 * 
	 * @param seqnum
	 * @pdOid ca0bde1e-65f8-4675-86cf-3b4bcf93bba1
	 */
	public void setSeqnum(Integer seqnum) {
		this.seqnum = seqnum;
	}

	/**
	 * 获取 序列号 该字段是：序列号
	 * 
	 * 
	 * @pdOid b0a4a70e-14d2-4b91-a64d-ece8b5657dd4
	 */
	public Integer getSeqnum() {
		return seqnum;
	}

	/**
	 * 设置 允许限度 该字段是：允许限度
	 * 
	 * @param yxlimit
	 * @pdOid b5fe11dc-8ea4-4c6c-82ed-3f3e6bf7bb2a
	 */
	public void setYxlimit(String yxlimit) {
		this.yxlimit = yxlimit;
	}

	/**
	 * 获取 允许限度 该字段是：允许限度
	 * 
	 * 
	 * @pdOid 6aaa76b9-aab0-4867-ba4f-4394e461f696
	 */
	public String getYxlimit() {
		return yxlimit;
	}

	/**
	 * 设置 上传 该字段是：上传
	 * 
	 * @param isupload
	 * @pdOid 81e2c190-3acf-4ead-9425-c11c7d4a7c1f
	 */
	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	/**
	 * 获取 上传 该字段是：上传
	 * 
	 * 
	 * @pdOid 1c4ebf93-9d03-4886-a929-86eb67f630f1
	 */
	public Integer getIsupload() {
		return isupload;
	}

	/**
	 * 设置 a2 该字段是：a2
	 * 
	 * @param writenbypda
	 * @pdOid ee0f5a2d-8fec-4908-944c-46fd4c20fc62
	 */
	public void setWritenbypda(String writenbypda) {
		this.writenbypda = writenbypda;
	}

	/**
	 * 获取 a2 该字段是：a2
	 * 
	 * 
	 * @pdOid ddd7b593-9a27-4f48-bf1d-570068d7dcd6
	 */
	public String getWritenbypda() {
		return writenbypda;
	}

	/**
	 * 设置 a3 该字段是：a3
	 * 
	 * @param qtresult
	 * @pdOid fbc64978-25b2-4c2d-9bb3-3406699a5882
	 */
	public void setQtresult(String qtresult) {
		this.qtresult = qtresult;
	}

	/**
	 * 获取 a3 该字段是：a3
	 * 
	 * 
	 * @pdOid 9ad518c1-3dd6-4413-bcae-e33590cdbd6e
	 */
	public String getQtresult() {
		return qtresult;
	}

	/**
	 * 设置 是否伪删除 该字段是：是否伪删除
	 * 
	 * @param dr
	 * @pdOid adbc47a8-d232-4163-a565-760885a3e9b4
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 是否伪删除 该字段是：是否伪删除
	 * 
	 * 
	 * @pdOid feb0bb6c-0114-4ce2-b624-b354aa87baa2
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 标记 该字段是：标记
	 * 
	 * @param tag
	 * @pdOid aecdfbb6-f781-4290-9179-006f8228d638
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 获取 标记 该字段是：标记
	 * 
	 * 
	 * @pdOid ec629847-7f12-49f1-ba6b-6ed30eb80a88
	 */
	public Integer getTag() {
		return tag;
	}

	public int getIsPass() {
		return isPass;
	}

	public void setIsPass(int isPass) {
		this.isPass = isPass;
	}

}