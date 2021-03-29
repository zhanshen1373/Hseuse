/**
 * File:    UdZyxkZysqpdasc.java
 * Author:  hd
 * Company: 
 * Created: 2014-10-16 20:06:46
 * Purpose: 定义数据类 UdZyxkZysqpdasc
 * NOTE:    该文件为自动生成，请勿手工改动！
 */

package com.hd.hse.entity.base;

import android.R.integer;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/** @pdOid 8b1aa132-1196-49d0-a347-dcec520cc0b8 */
@DBTable(tableName = "ud_zyxk_zysqpdasc")
public class PDAWorkOrderInfoConfig extends
		com.hd.hse.common.entity.SuperEntity {
	/** @pdOid 69e9b127-6076-4e1d-b360-be6023ae4a42 */
	private static final long serialVersionUID = -2732692666578732427L;
	/**
	 * 主键
	 * 
	 * @pdOid 07a49bd9-3f62-4cc2-9b1a-61de5d70b085
	 */
	@DBField(id = true)
	private String ud_zyxk_zysqpdascid;
	/**
	 * tab名称
	 * 
	 * @pdOid 089377cd-6277-47b4-bd04-3058d5624dc5
	 */
	@DBField
	private String sname;
	/**
	 * 功能编码
	 * 
	 * @pdOid 83727c21-8d72-4298-b802-9602b25e2659
	 */
	@DBField
	private String pscode;
	/**
	 * 控件类型
	 * 
	 * @pdOid 9ba4cbd3-612f-4355-8da3-3a3baa60e4e4
	 */
	@DBField
	private Integer contype;
	/**
	 * 控件类型细分
	 * 
	 * @pdOid f95defc9-e59c-4d9b-b45f-c8cf9ba2d7c5
	 */
	@DBField
	private Integer contypelie;
	/**
	 * 审批环节点编码
	 * 
	 * @pdOid f2054d2b-63a5-4c7c-a770-1896c6c30397
	 */
	@DBField
	private String dycode;
	/**
	 * 是否启用其他
	 * 
	 * @pdOid 00d0135f-962c-4913-9f9d-d8157faa8a07
	 */
	@DBField
	private Integer otherisenable;
	/**
	 * 其他字段列明
	 * 
	 * @pdOid bc8fe877-bc41-4202-a776-7320d195ab43
	 */
	@DBField
	private String otherfield;
	/**
	 * 是否启用下拉框
	 * 
	 * @pdOid 9a971d8c-04fe-4682-99c8-95d13964ee3e
	 */
	@DBField
	private Integer cbisenable;
	/**
	 * 下拉框显示标题
	 * 
	 * @pdOid 0595e0c3-dfbf-408c-a7d8-ad0cdca110be
	 */
	@DBField
	private String cbname;
	/**
	 * 下拉框列表
	 * 
	 * @pdOid 8e38860d-0a86-4db8-84be-3f30c9f0a72a
	 */
	@DBField
	private String cbtype;
	/**
	 * 票类型
	 * 
	 * @pdOid 78c58768-83a2-48a5-9f5d-df39744fd9e6
	 */
	@DBField
	private String zypclass;
	/**
	 * 措施类别(同时存取下拉框措施列表)
	 * 
	 * @pdOid 45504759-1340-4984-ba50-46cf9ced7e73
	 */
	@DBField
	private String cstype;
	/**
	 * 排序
	 * 
	 * @pdOid 98909609-54b1-4b91-ac72-df75d8f9678c
	 */
	@DBField
	private Integer isorder;
	/**
	 * tab排序
	 * 
	 * @pdOid ddbeaea4-23f1-41e9-b0bc-55d431390e83
	 */
	@DBField
	private Integer taBorder;
	/**
	 * 是否保存
	 * 
	 * @pdOid 15ac5dad-8393-4008-afbe-d06183971bf1
	 */
	@DBField
	private Integer issavebtn;
	/**
	 * 时间戳
	 * 
	 * @pdOid a2b8a8e3-12e6-45d3-aa87-a4bc8389f790
	 */
	@DBField
	private String changedate;
	/**
	 * 是否激活
	 * 
	 * @pdOid b2d36a71-a0df-418e-8f86-5392125d022f
	 */
	@DBField
	private Integer isactive;
	/**
	 * 是否逐条
	 * 
	 * @pdOid 2d7a5c0d-0c64-41d6-8900-14beb59cb596
	 */
	@DBField
	private Integer conlevel;
	/**
	 * 措施和个人防火装备取值的类型
	 * 
	 * @pdOid e02de171-6b72-4395-86e9-0d36f45bd509
	 */
	@DBField
	private String valuewheretype;
	/**
	 * 项目标识
	 * 
	 * @pdOid bb9d13da-c90b-48d9-ad15-df1de5b40874
	 */
	@DBField
	private String itemtype;
	/**
	 * 删除
	 * 
	 * @pdOid 6743257f-f02a-492e-b35b-a79451fa6344
	 */
	@DBField
	private Integer dr;
	/**
	 * 标识
	 * 
	 * @pdOid 94049dd4-6667-4c93-b139-586d11c280e0
	 */
	@DBField
	private Integer tag;
	/**
	 * (删)编码
	 * 
	 * @pdOid 4667d10a-206e-4677-92cc-74a750094a3d
	 */
	@DBField
	private String scode;
	/**
	 * 类型描述
	 * 
	 * @pdOid 9de963a7-b55c-48ab-b3eb-f5af8d527a33
	 */
	@DBField
	private String contypedesc;
	/**
	 * 导航栏是否完成
	 * 
	 * @pdOid 50796fed-bde1-4139-b887-fb5d7b2841d4
	 */
	private Integer iscomplete;
	/**
	 * 每个子项数量
	 * 
	 * @pdOid 8cfb1994-4d46-4ae1-887c-826577539df2
	 */
	private Integer childCount;
	/**
	 * 措施批量审核
	 * 
	 * @pdOid d4f56ec0-0e8e-4e37-9c5a-16f92cb194d4
	 */
	@DBField
	private Integer batappr;
	/**
	 * 是否可以措施全部不适用
	 */
	@DBField
	private Integer iscscheck;
	/**
	 * currentCBCode:TODO(当前选择框选择中的编码).
	 * 
	 * 
	 * @pdOid 1dfa8ba1-d338-47bb-9656-98223ce855ee
	 */
	private String currentcbcode;

	/**
	 * 导航栏要显示的图标的资源id
	 */
	private int[] naviIconId;

	public int[] getNaviIconId() {
		return naviIconId;
	}

	public void setNaviIconId(int[] naviIconId) {
		this.naviIconId = naviIconId;
	}

	/**
	 * getCurrentCBCode:(当前选择框选择中的编码). <br/>
	 * date: 2014年10月24日 <br/>
	 * 
	 * 
	 * @return
	 * 
	 * @pdOid 2797e31b-d1bb-4457-9903-39b272482f54
	 * @author lxf
	 */
	public String getCurrentcbCode() {
		return currentcbcode;
	}

	/**
	 * setCurrentCBCode:(当前选择框选择中的编码). <br/>
	 * date: 2014年10月24日 <br/>
	 * 
	 * 
	 * @param currentCBCode
	 * @pdOid d756e631-ac33-4102-96d2-a69e66d86991
	 * @author lxf
	 */
	public void setCurrentcbCode(String currentCBCode) {
		this.currentcbcode = currentCBCode;
	}

	/**
	 * childCount.
	 * 
	 * 
	 * @return the childCount
	 * 
	 * @pdOid 9b4f30d8-85e6-4a2c-9e14-40a11b5c1654
	 */
	public Integer getChildCount() {
		return childCount;
	}

	/**
	 * childCount.
	 * 
	 * 
	 * @param childCount
	 *            the childCount to set
	 * @pdOid 70115fca-963e-404a-b352-9c41da6816a5
	 */
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	/**
	 * 设置 主键 该字段是：主键
	 * 
	 * @param sname
	 * @pdOid cbb81981-291b-4928-be94-417f01502f7f
	 */
	public void setSname(String sname) {
		this.sname = sname;
	}

	/**
	 * @return the ud_zyxk_zysqpdascid
	 * 
	 * @pdOid 76795eb6-4660-49d2-a6b9-4967f9f795c7
	 */
	public String getUd_zyxk_zysqpdascid() {
		return ud_zyxk_zysqpdascid;
	}

	/**
	 * @param ud_zyxk_zysqpdascid
	 *            the ud_zyxk_zysqpdascid to set
	 * @pdOid 8d843f76-435b-442d-84f1-f57be711c31e
	 */
	public void setUd_zyxk_zysqpdascid(String ud_zyxk_zysqpdascid) {
		this.ud_zyxk_zysqpdascid = ud_zyxk_zysqpdascid;
	}

	/**
	 * 设置 tab名称 该字段是：tab名称
	 * 
	 * @pdOid b46a8902-7091-4ebb-b06b-0d65c2b0f8b3
	 */
	public String getSname() {
		return sname;
	}

	/**
	 * 设置 功能编码 该字段是：功能编码
	 * 
	 * @param pscode
	 * @pdOid c50276b1-aa93-418d-ade4-8328d033d400
	 */
	public void setPscode(String pscode) {
		this.pscode = pscode;
	}

	/**
	 * 获取 功能编码 该字段是：功能编码
	 * 
	 * 
	 * @pdOid 3de608fd-cc38-4ade-b490-2cad4212ca01
	 */
	public String getPscode() {
		return pscode;
	}

	/**
	 * 设置 控件类型 该字段是：控件类型
	 * 
	 * @param contype
	 * @pdOid fab8029f-3056-4f83-92a2-1bab914e240a
	 */
	public void setContype(Integer contype) {
		this.contype = contype;
	}

	/**
	 * 获取 控件类型 该字段是：控件类型
	 * 
	 * 
	 * @pdOid 4470321a-5457-4002-b75b-073d47a377d0
	 */
	public Integer getContype() {
		return contype;
	}

	/**
	 * 设置 控件类型细分 该字段是：控件类型细分
	 * 
	 * @param contypelie
	 * @pdOid 8a448b46-2c14-4237-a122-0e35b2b20e84
	 */
	public void setContypelie(Integer contypelie) {
		this.contypelie = contypelie;
	}

	/**
	 * 获取 控件类型细分 该字段是：控件类型细分
	 * 
	 * 
	 * @pdOid 6f714374-9663-4397-b1dd-c741bfd2c3c8
	 */
	public Integer getContypelie() {
		return contypelie;
	}

	/**
	 * 设置 审批环节点编码 该字段是：审批环节点编码
	 * 
	 * @param dycode
	 * @pdOid 500c13c7-da1e-4135-ab7a-4f8bec2520a0
	 */
	public void setDycode(String dycode) {
		this.dycode = dycode;
	}

	/**
	 * 获取 审批环节点编码 该字段是：审批环节点编码
	 * 
	 * 
	 * @pdOid a01d7344-77d1-4f47-8718-21a9d3d19c14
	 */
	public String getDycode() {
		return dycode;
	}

	/**
	 * 设置 是否启用其他 该字段是：是否启用其他
	 * 
	 * @param otherisenable
	 * @pdOid 601335f9-5ed3-45bd-812e-4258217aa870
	 */
	public void setOtherisenable(Integer otherisenable) {
		this.otherisenable = otherisenable;
	}

	/**
	 * 获取 是否启用其他 该字段是：是否启用其他
	 * 
	 * 
	 * @pdOid a35fa7ac-bbaa-4553-9cf7-ad24a505f60d
	 */
	public Integer getOtherisenable() {
		return otherisenable;
	}

	/**
	 * 设置 其他字段列明 该字段是：其他字段列明
	 * 
	 * @param otherfield
	 * @pdOid db3048d1-1a1a-4927-9557-096efd9a36c4
	 */
	public void setOtherfield(String otherfield) {
		this.otherfield = otherfield;
	}

	/**
	 * 获取 其他字段列明 该字段是：其他字段列明
	 * 
	 * 
	 * @pdOid 926570fb-6b2a-433b-9c58-bbaf5604b373
	 */
	public String getOtherfield() {
		return otherfield;
	}

	/**
	 * 设置 是否启用下拉框 该字段是：是否启用下拉框
	 * 
	 * @param cbisenable
	 * @pdOid a7b2cce7-624c-4fa6-bc5e-d81dc57cc0f0
	 */
	public void setCbisenable(Integer cbisenable) {
		this.cbisenable = cbisenable;
	}

	/**
	 * 获取 是否启用下拉框 该字段是：是否启用下拉框
	 * 
	 * 
	 * @pdOid 44312e60-69c9-4e2e-9c3b-83b74604aa87
	 */
	public Integer getCbisenable() {
		return cbisenable;
	}

	/**
	 * 设置 下拉框显示标题 该字段是：下拉框显示标题
	 * 
	 * @param cbname
	 * @pdOid 6d892336-de38-4c1e-8bdc-2f5a4155dbcf
	 */
	public void setCbname(String cbname) {
		this.cbname = cbname;
	}

	/**
	 * 获取 下拉框显示标题 该字段是：下拉框显示标题
	 * 
	 * 
	 * @pdOid 6405f790-dc95-458a-bba4-afbd662501de
	 */
	public String getCbname() {
		return cbname;
	}

	/**
	 * 设置 下拉框列表 该字段是：下拉框列表
	 * 
	 * @param cbtype
	 * @pdOid ea2904af-f917-4e90-bc91-5b0efe37d72b
	 */
	public void setCbtype(String cbtype) {
		this.cbtype = cbtype;
	}

	/**
	 * 获取 下拉框列表 该字段是：下拉框列表
	 * 
	 * 
	 * @pdOid 3a7eda04-3f19-444a-991f-1a8fa13a0762
	 */
	public String getCbtype() {
		return cbtype;
	}

	/**
	 * 设置 票类型 该字段是：票类型
	 * 
	 * @param zypclass
	 * @pdOid e60fcd70-ad7f-4221-a411-dc52364f1217
	 */
	public void setZypclass(String zypclass) {
		this.zypclass = zypclass;
	}

	/**
	 * 获取 票类型 该字段是：票类型
	 * 
	 * 
	 * @pdOid 02598aa3-fcad-42e5-9851-ab7e2ad5fcdd
	 */
	public String getZypclass() {
		return zypclass;
	}

	/**
	 * 设置 措施类别(同时存取下拉框措施列表) 该字段是：措施类别(同时存取下拉框措施列表)
	 * 
	 * @param cstype
	 * @pdOid 67da85ca-64de-4535-b194-8e8bf44c9b90
	 */
	public void setCstype(String cstype) {
		this.cstype = cstype;
	}

	/**
	 * 获取 措施类别(同时存取下拉框措施列表) 该字段是：措施类别(同时存取下拉框措施列表)
	 * 
	 * 
	 * @pdOid 22ff87c7-c47f-45af-a18c-b9213353c795
	 */
	public String getCstype() {
		return cstype;
	}

	/**
	 * 设置 排序 该字段是：排序
	 * 
	 * @param isorder
	 * @pdOid 7f568776-2982-461a-97be-7fc1f51b1503
	 */
	public void setIsorder(Integer isorder) {
		this.isorder = isorder;
	}

	/**
	 * 获取 排序 该字段是：排序
	 * 
	 * 
	 * @pdOid aaa55000-1064-425d-83a0-a22ffd7b7d1e
	 */
	public Integer getIsorder() {
		return isorder;
	}

	/**
	 * 设置 tab排序 该字段是：tab排序
	 * 
	 * @param taBorder
	 * @pdOid 8cedd356-1963-4d58-8b52-3d92f60dc819
	 */
	public void setTaBorder(Integer taBorder) {
		this.taBorder = taBorder;
	}

	/**
	 * 获取 tab排序 该字段是：tab排序
	 * 
	 * 
	 * @pdOid 0b59fdf0-745d-4154-9d0c-9e9405d99140
	 */
	public Integer getTaBorder() {
		return taBorder;
	}

	/**
	 * 设置 是否保存 该字段是：是否保存
	 * 
	 * @param issavebtn
	 * @pdOid c8fe400c-b12b-4f2b-82b1-1bf14f156bf8
	 */
	public void setIssavebtn(Integer issavebtn) {
		this.issavebtn = issavebtn;
	}

	/**
	 * 获取 是否保存 该字段是：是否保存
	 * 
	 * 
	 * @pdOid 2e0c6bb9-6770-43f2-905a-a9e318ca1001
	 */
	public Integer getIssavebtn() {
		return issavebtn;
	}

	/**
	 * 设置 时间戳 该字段是：时间戳
	 * 
	 * @param changedate
	 * @pdOid 563c9652-a705-45c0-81bc-6ecbceacc721
	 */
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}

	/**
	 * 获取 时间戳 该字段是：时间戳
	 * 
	 * 
	 * @pdOid 2d5a93a3-94fe-495f-8f45-16c41b20f776
	 */
	public String getChangedate() {
		return changedate;
	}

	/**
	 * 设置 是否激活 该字段是：是否激活
	 * 
	 * @param isactive
	 * @pdOid 45a40241-ccc1-42db-a69a-2cbb19743111
	 */
	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}

	/**
	 * 获取 是否激活 该字段是：是否激活
	 * 
	 * 
	 * @pdOid 7e828b44-a0b6-41c4-8e32-3d27a8c2f9af
	 */
	public Integer getIsactive() {
		return isactive;
	}

	/**
	 * 设置 控件级别 该字段是：控件级别
	 * 
	 * @param conlevel
	 * @pdOid 8179b845-dee2-49ac-88b8-b023f883ca39
	 */
	public void setConlevel(Integer conlevel) {
		this.conlevel = conlevel;
	}

	/**
	 * 获取 控件级别 该字段是：控件级别
	 * 
	 * 
	 * @pdOid 1e5b3307-3c7c-41ef-9e44-1885499e4f26
	 */
	public Integer getConlevel() {
		return conlevel;
	}

	/**
	 * 设置 措施和个人防火装备取值的类型 该字段是：措施和个人防火装备取值的类型
	 * 
	 * @param valuewheretype
	 * @pdOid 4ddce568-8d4b-49bb-b5f0-f4dbadce3e38
	 */
	public void setValuewheretype(String valuewheretype) {
		this.valuewheretype = valuewheretype;
	}

	/**
	 * 获取 措施和个人防火装备取值的类型 该字段是：措施和个人防火装备取值的类型
	 * 
	 * 
	 * @pdOid 788d372f-b700-4b54-b9b8-1ff6be39747b
	 */
	public String getValuewheretype() {
		return valuewheretype;
	}

	/**
	 * 设置 项目标识 该字段是：项目标识
	 * 
	 * @param itemtype
	 * @pdOid c8cf7a88-5728-40ae-8ab4-464cbee5f2dc
	 */
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	/**
	 * 获取 项目标识 该字段是：项目标识
	 * 
	 * 
	 * @pdOid f1e7fc12-0463-44c7-a1a7-c6af65b9916d
	 */
	public String getItemtype() {
		return itemtype;
	}

	/**
	 * 设置 删除 该字段是：删除
	 * 
	 * @param dr
	 * @pdOid eab3282e-96e7-4f0f-9e39-1c139f24d10d
	 */
	public void setDr(Integer dr) {
		this.dr = dr;
	}

	/**
	 * 获取 删除 该字段是：删除
	 * 
	 * 
	 * @pdOid 34a94938-5dd5-42bb-bb0c-7c5e9bb78498
	 */
	public Integer getDr() {
		return dr;
	}

	/**
	 * 设置 标识 该字段是：标识
	 * 
	 * @param tag
	 * @pdOid 325e5724-9e0b-499b-88fd-51590fae7e8e
	 */
	public void setTag(Integer tag) {
		this.tag = tag;
	}

	/**
	 * 获取 标识 该字段是：标识
	 * 
	 * 
	 * @pdOid 572ee0e4-e167-4bcd-ac07-f9fd5b26eea7
	 */
	public Integer getTag() {
		return tag;
	}

	/**
	 * 设置 (删)编码 该字段是：(删)编码
	 * 
	 * @param scode
	 * @pdOid c8a0b594-df2a-424e-bfe0-80e819464b79
	 */
	public void setScode(String scode) {
		this.scode = scode;
	}

	/**
	 * 获取 (删)编码 该字段是：(删)编码
	 * 
	 * 
	 * @pdOid 5045b0b2-3c18-4621-9869-77fbe692a48f
	 */
	public String getScode() {
		return scode;
	}

	/** @pdOid 55fde5fb-0119-4423-aeea-a6ca93fb1066 */
	public String getContypedesc() {
		return contypedesc;
	}

	/**
	 * @param contypedesc
	 * @pdOid 8b503015-4965-456e-8aac-b7e8dff5d515
	 */
	public void setContypedesc(String contypedesc) {
		this.contypedesc = contypedesc;
	}

	/** @pdOid df427303-461d-4058-96d2-3494a71490c7 */
	public Integer getIsComplete() {
		return iscomplete;
	}

	/**
	 * @param iscomplete
	 * @pdOid 0b8fa6f4-a703-4bfd-88d0-98217b923448
	 */
	public void setIsComplete(Integer iscomplete) {
		this.iscomplete = iscomplete;
	}

	/** @pdOid 211eba8f-4cf5-4065-8ba3-412fedc2e4d7 */
	public Integer getBatappr() {
		return batappr;
	}

	/**
	 * @param batappr
	 * @pdOid b45edbab-621c-4905-8780-19eb666c4717
	 */
	public void setBatappr(Integer batappr) {
		this.batappr = batappr;
	}

	public Integer getIscscheck() {
		return iscscheck;
	}

	public void setIscscheck(Integer iscscheck) {
		this.iscscheck = iscscheck;
	}

}