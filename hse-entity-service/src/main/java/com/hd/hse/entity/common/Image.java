/**
 * File:    HseSysImage.java
 * Author:  hd
 * Company: 
 * Created: 2014-09-25 11:58:43
 * Purpose: 定义数据类 HseSysImage
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 1879ac36-863a-4b6e-8e2d-f8b12370b428 */
@DBTable(tableName = "hse_sys_image")
public class Image extends com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 523c4cc0-f8f2-49db-a009-04e38ae3948f */
	private static final long serialVersionUID = -4450846856442315003L;
	/**
	 * 表明
	 * 
	 * @pdOid 38431966-e4b7-4365-9415-1b53dfeec2e4
	 */
	@DBField
	private String tablename;
	/**
	 * 表主键
	 * 
	 * @pdOid dc76aa30-d752-4ebf-ab66-d8b2b06a0817
	 */
	@DBField
	private String tableid;
	/**
	 * 路径
	 * 
	 * @pdOid 2678cdcf-78d4-42b8-86ea-51a141434929
	 */
	@DBField
	private String imagepath;
	/**
	 * 名称
	 * 
	 * @pdOid 4b3994c2-2da3-4855-98b7-44650c4e16ac
	 */
	@DBField
	private String imagename;
	/**
	 * 创建时间
	 * 
	 * @pdOid 17081003-4c19-469a-8c12-46846892116e
	 */
	@DBField
	private String createdate;
	/**
	 * 创建人
	 * 
	 * @pdOid 31df5686-818d-4ecf-9a88-867cc920814b
	 */
	@DBField
	private String createuser;
	/**
	 * 主键
	 * 
	 * @pdOid 842963f9-1654-4451-bbc6-d8e491f45999
	 */
	@DBField(id = true)
	private String id;
	/**
	 * 对应的功能
	 * 
	 * @pdOid 0ef7944c-0b22-410e-8829-0f3cb5b4e40a
	 */
	@DBField
	private String funcode;
	/**
	 * 控件类型
	 * 
	 * @pdOid fb5c6d4a-b057-43ab-8143-ee620fc7d634
	 */
	@DBField
	private Integer contype;
	/**
	 * createusername:TODO(创建人名字).
	 * 
	 * 
	 * @pdOid 82dbdddf-0c2f-447b-9d81-9ad795505418
	 */
	@DBField
	private String createusername;

	/**
	 * 作业申请ID
	 */
	@DBField
	private String zysqid;

	/**
	 * 修改时间 使用时间戳精确到毫秒
	 */
	@DBField
	private long updateDate;

	@DBField
	private String childids;

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * getCreateusername:(创建人名字). <br/>
	 * date: 2014年11月21日 <br/>
	 * 
	 * 
	 * @return
	 * 
	 * @pdOid 5cbad9cf-6ff6-4f18-9560-506779a61457
	 * @author lxf
	 */
	public String getCreateusername() {
		return createusername;
	}

	/**
	 * setCreateusername:(创建人名字). <br/>
	 * date: 2014年11月21日 <br/>
	 * 
	 * 
	 * @param createusername
	 * @pdOid b866bdb3-92b5-4e09-ad1e-97896092d6e4
	 * @author lxf
	 */
	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	/**
	 * @return the contype
	 * 
	 * @pdOid dd21f3aa-66aa-4979-a26d-8a9854b52009
	 */
	public Integer getContype() {
		return contype;
	}

	/**
	 * @param contype
	 *            the contype to set
	 * @pdOid edc09235-fd53-46ec-9cef-edd30018daa0
	 */
	public void setContype(Integer contype) {
		this.contype = contype;
	}

	/**
	 * 设置 表明 该字段是：表明
	 * 
	 * 
	 * @param tablename
	 * @pdOid d9b663ab-80ea-4bba-bad9-3e15c715eddb
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	/**
	 * 获取 表明 该字段是：表明
	 * 
	 * 
	 * @pdOid 14ed9263-6a12-4343-82f5-e636b50c861a
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * 设置 表主键 该字段是：表主键
	 * 
	 * 
	 * @param tableid
	 * @pdOid b52de189-5c1c-4a43-a74f-2c6c218ffd73
	 */
	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	/**
	 * 获取 表主键 该字段是：表主键
	 * 
	 * 
	 * @pdOid f8559e36-a92b-426a-8006-fe0d08fe49a8
	 */
	public String getTableid() {
		return tableid;
	}

	/**
	 * 设置 路径 该字段是：路径
	 * 
	 * 
	 * @param imagepath
	 * @pdOid 18e464d1-e88d-4773-9cde-91d1e806bf45
	 */
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	/**
	 * 获取 路径 该字段是：路径
	 * 
	 * 
	 * @pdOid e670ce60-104f-44ae-8c55-1791a2e40061
	 */
	public String getImagepath() {
		return imagepath;
	}

	/**
	 * 设置 名称 该字段是：名称
	 * 
	 * 
	 * @param imagename
	 * @pdOid 909eb0ae-b500-467b-a9ea-9f4a6b130460
	 */
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	/**
	 * 获取 名称 该字段是：名称
	 * 
	 * 
	 * @pdOid f5c1b411-32dc-4c72-93f8-1b1c6a58edde
	 */
	public String getImagename() {
		return imagename;
	}

	/**
	 * 设置 创建时间 该字段是：创建时间
	 * 
	 * 
	 * @param createdate
	 * @pdOid ab956672-ac10-41fa-a01e-01a0ff764074
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	/**
	 * 获取 创建时间 该字段是：创建时间
	 * 
	 * 
	 * @pdOid 6ca7cd87-4333-4252-b424-fec54386656d
	 */
	public String getCreatedate() {
		return createdate;
	}

	/**
	 * 设置 创建人 该字段是：创建人
	 * 
	 * 
	 * @param createuser
	 * @pdOid f739a710-79ce-4ea8-b5e4-e1f0082fe110
	 */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	/**
	 * 获取 创建人 该字段是：创建人
	 * 
	 * 
	 * @pdOid 9c013e64-3b05-43c3-a3a9-cd66a751dfa8
	 */
	public String getCreateuser() {
		return createuser;
	}

	/**
	 * 设置 主键 该字段是：主键
	 * 
	 * 
	 * @param id
	 * @pdOid 3f0a3e0a-7b3e-4b05-80a9-85498dcd2b56
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取 主键 该字段是：主键
	 * 
	 * 
	 * @pdOid 9d68427e-161f-49bc-b420-99505b53c478
	 */
	public String getId() {
		return id;
	}

	/** @pdOid e87777cb-1168-4ea2-822f-8ae8cccfcf48 */
	public String getFuncode() {
		return funcode;
	}

	/**
	 * @param funcode
	 * @pdOid 14c6afa5-405f-4a49-aace-21fd6e9ccd4e
	 */
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getZysqid() {
		return zysqid;
	}

	public void setZysqid(String zysqid) {
		this.zysqid = zysqid;
	}

	public long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(long updateDate) {
		this.updateDate = updateDate;
	}

	public String getChildids() {
		return childids;
	}

	public void setChildids(String childids) {
		this.childids = childids;
	}

}