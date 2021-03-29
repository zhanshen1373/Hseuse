package com.hd.hse.padinterface;

import java.io.InputStream;
import java.io.Serializable;

/**
 * ClassName: PadInterfaceUpFile (接口上传文件对象)<br/>
 * date: 2015年3月25日 <br/>
 * 
 * @author lxf
 * @version
 */
@SuppressWarnings("serial")
public class PadInterfaceUpFile implements Serializable {

	// 属性
	/**
	 * tablename:TODO(表明).
	 */
	private String tablename;
	/**
	 * tableid:TODO(外键).
	 */
	private String tableid;
	/**
	 * imagepath:TODO(图片路径).
	 */
	private String imagepath;
	/**
	 * imagename:TODO(图片名字).
	 */
	private String imagename;
	/**
	 * createdate:TODO(创建日期).
	 */
	private String createdate;
	/**
	 * createuser:TODO(创建用户).
	 */
	private String createuser;
	/**
	 * id:TODO(主键).
	 */
	private String id;
	/**
	 * funcode:TODO(功能编码).
	 */
	private String funcode;
	/**
	 * createusername:TODO(创建用户名称).
	 */
	private String createusername;

	/**
	 * macaddress:TODO(MAC 地址).
	 */
	private String macaddress;

	/**
	 * appname:TODO(app名字).
	 */
	private String appname;

	/**
	 * imageInputStream:TODO(文件流).
	 */
	private InputStream imageInputStream;
	/**
	 * imagebyte:TODO(byte 存取文件).
	 */
	private byte[] imagebyte;

	private String childids;

	public String getChildids() {
		return childids;
	}

	public void setChildids(String childids) {
		this.childids = childids;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public byte[] getImagebyte() {
		return imagebyte;
	}

	public void setImagebyte(byte[] imagebyte) {
		this.imagebyte = imagebyte;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	/**
	 * getImageInputStream:(获取文件流). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public InputStream getImageInputStream() {
		return imageInputStream;
	}

	/**
	 * setImageInputStream:(设置文件流). <br/>
	 * date: 2015年3月25日 <br/>
	 * 
	 * @author lxf
	 * @param imageInputStream
	 */
	public void setImageInputStream(InputStream imageInputStream) {
		this.imageInputStream = imageInputStream;
	}

	public PadInterfaceUpFile() {
	};

}
