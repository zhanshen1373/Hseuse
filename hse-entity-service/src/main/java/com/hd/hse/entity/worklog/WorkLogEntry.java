package com.hd.hse.entity.worklog;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;
import com.hd.hse.entity.common.Image;

import java.sql.Date;


@DBTable(tableName = "UD_CBSGL_RZ")
public class WorkLogEntry extends SuperEntity {

    /**
     * Created by Administrator on 2016年3月24日
     */
    private static final long serialVersionUID = 1447443330548921587L;
    /**
     * serialVersionUID:TODO().
     */
    @DBField
    private String ud_cbsgl_rzid; // PAD暂时不用(PC端主键)
    @DBField(id = true)
    private String padrzid; // 主键
    @DBField
    private String title; // 标题
    @DBField
    private String zycontent; // 工作内容
    @DBField
    private String rztype; // 日志类别
    @DBField
    private String createby; // 填写人
    @DBField
    private String createdate; // 创建时间
    @DBField
    private String site; // 地点
    @DBField
    private String v_action; // 更新插入
    @DBField
    private String createbydesc; // 填写人描述
    @DBField
    private String wcgz; // 完成工作
    @DBField
    private String fxwt; // 发现问题
    @DBField
    private String cljg; // 处理结果
    @DBField
    private String jhsx; // 计划事项
    @DBField
    private String iccard;// 身份证号（可多个）
    @DBField
    private String innercard;// 违章人员卡（可多个）
    @DBField
    private String rulesdescription; // 违章人员描述（可多个）
    // @DBField
    // private String wtfl;// 问题分类
    // @DBField
    // private String laborunit;// 用工单位编码
    // @DBField
    // private String laborunit_desc;// 用工单位描述
    private boolean isSelect;
    @DBField
    private String ud_zyxk_zysqid;// 作业票ID
    @DBField
    private String jcdept; // 检查单位
    @DBField
    private String jcdept_desc; // 检查单位描述
    @DBField
    private String zyarea; // 属地部门
    @DBField
    private String zyarea_desc; // 属地部门描述
    @DBField
    private String sddept; // 属地单位
    @DBField
    private String sddept_desc; // 属地单位描述
    @DBField
    private String fzperson; // 负责人
    @DBField
    private String fzperson_desc; // 负责人描述
    @DBField
    private String zrdept; // 责任单位
    @DBField
    private String zrdept_desc; // 责任单位描述
    @DBField
    private String wyj; // 违约金（罚款金）
    @DBField
    private int iszg; // 是否整改
    @DBField
    private int ishmd; // 是否转入黑名单
    @DBField
    private int isupload; // 上传标志
    @DBField
    private int dr;
    @DBField
    private int tag;
    @DBField
    private String zyname;
    @DBField
    private String examyj; //考核标准依据
    @DBField
    private int iskhd; // 是否开具考核单
    @DBField
    private String recordtype;//
    @DBField
    private int istypicissue;//是否典型问题
    @DBField
    private String zgcs;//整改措施
    @DBField
    private String penaltypoint;//扣分
    @DBField
    private String zgyzjl;//整改验证结论
    @DBField
    private int isys;//是否已验收
    @DBField
    private String reform_date;//限制整改日期

    @DBField
    private String breachlocation;//检查地点


    public String getBreachlocation() {
        return breachlocation;
    }

    public void setBreachlocation(String breachlocation) {
        this.breachlocation = breachlocation;
    }
    public String getReform_date() {
        return reform_date;
    }

    public void setReform_date(String reform_date) {
        this.reform_date = reform_date;
    }

    public String getZgyzjl() {
        return zgyzjl;
    }

    public void setZgyzjl(String zgyzjl) {
        this.zgyzjl = zgyzjl;
    }

    public int getIsys() {
        return isys;
    }

    public void setIsys(int isys) {
        this.isys = isys;
    }

    public int getIstypicissue() {
        return istypicissue;
    }

    public void setIstypicissue(int istypicissue) {
        this.istypicissue = istypicissue;
    }

    public String getZgcs() {
        return zgcs;
    }

    public void setZgcs(String zgcs) {
        this.zgcs = zgcs;
    }

    public String getPenaltypoint() {
        return penaltypoint;
    }

    public void setPenaltypoint(String penaltypoint) {
        this.penaltypoint = penaltypoint;
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    public String getExamyj() {
        return examyj;
    }

    public void setExamyj(String examyj) {
        this.examyj = examyj;
    }

    public int getIskhd() {
        return iskhd;
    }

    public void setIskhd(int iskhd) {
        this.iskhd = iskhd;
    }

    /**
     * 禁入原因 Created by liuyang on 2016年10月19日
     */
    @DBField
    private String rejectreason;
    /**
     * 备注 Created by liuyang on 2016年10月19日
     */
    @DBField
    private String remarks;
    /**
     * 风险级别
     */
    @DBField
    private String fxjb;
    /**
     * 违章标准id
     */
    @DBField
    private String ud_cbsgl_khbzid;
    /**
     * 违章风险值
     */
    @DBField
    private Integer wzfxz;
    /**
     * 违章类别
     */
    @DBField
    private String wzlb;
    /**
     * 违章条款
     */
    @DBField
    private String wztk;

    @DBField
    private String cbszjzrr;  //施工单位直接责任人

    @DBField
    private String cbszjzrr_desc; // 施工单位直接责任人描述

    @DBField
    private String cbsjhr;  //施工单位作业监护人

    @DBField
    private String cbsjhr_desc; // 施工单位作业监护人描述

    @DBField
    private String cbsaqglry;  //施工单位项目安全员

    @DBField
    private String cbsaqglry_desc; // 施工单位项目安全员描述

    @DBField
    private String cbsxmfzr;  //施工单位项目负责人

    @DBField
    private String cbsxmfzr_desc; // 施工单位项目负责人描述

    @DBField
    private int isddh; //是否施工作业类问题

    @DBField
    private String llbj; //来源标记


    public String getLlbj() {
        return llbj;
    }

    public void setLlbj(String llbj) {
        this.llbj = llbj;
    }

    public String getCbszjzrr() {
        return cbszjzrr;
    }

    public void setCbszjzrr(String cbszjzrr) {
        this.cbszjzrr = cbszjzrr;
    }

    public String getCbszjzrr_desc() {
        return cbszjzrr_desc;
    }

    public void setCbszjzrr_desc(String cbszjzrr_desc) {
        this.cbszjzrr_desc = cbszjzrr_desc;
    }

    public String getCbsjhr() {
        return cbsjhr;
    }

    public void setCbsjhr(String cbsjhr) {
        this.cbsjhr = cbsjhr;
    }

    public String getCbsjhr_desc() {
        return cbsjhr_desc;
    }

    public void setCbsjhr_desc(String cbsjhr_desc) {
        this.cbsjhr_desc = cbsjhr_desc;
    }

    public String getCbsaqglry() {
        return cbsaqglry;
    }

    public void setCbsaqglry(String cbsaqglry) {
        this.cbsaqglry = cbsaqglry;
    }

    public String getCbsaqglry_desc() {
        return cbsaqglry_desc;
    }

    public void setCbsaqglry_desc(String cbsaqglry_desc) {
        this.cbsaqglry_desc = cbsaqglry_desc;
    }

    public String getCbsxmfzr() {
        return cbsxmfzr;
    }

    public void setCbsxmfzr(String cbsxmfzr) {
        this.cbsxmfzr = cbsxmfzr;
    }

    public String getCbsxmfzr_desc() {
        return cbsxmfzr_desc;
    }

    public void setCbsxmfzr_desc(String cbsxmfzr_desc) {
        this.cbsxmfzr_desc = cbsxmfzr_desc;
    }

    public int getIsddh() {
        return isddh;
    }

    public void setIsddh(int isddh) {
        this.isddh = isddh;
    }

    /**
     * 设置 主键 该字段是：主键
     *
     * @param String padrzid
     */
    public void setPadrzid(String padrzid) {
        this.padrzid = padrzid;
    }

    /**
     * 获取 主键 该字段是：主键
     */
    public String getPadrzid() {
        return padrzid;
    }

    /**
     * 设置 标题 该字段是：标题
     *
     * @param String titile
     */
    public void setTitle(String titile) {
        this.title = titile;
    }

    /**
     * 获取 标题 该字段是：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置 工作内容 该字段是：工作内容
     *
     * @param String zycontent
     */
    public void setZycontent(String zycontent) {
        this.zycontent = zycontent;
    }

    /**
     * 获取 工作内容 该字段是：工作内容
     */
    public String getZycontent() {
        return zycontent;
    }

    /**
     * 设置 日志类别 该字段是：日志类别
     *
     * @param String rztype
     */
    public void setRztype(String rztype) {
        this.rztype = rztype;
    }

    /**
     * 获取 日志类别 该字段是：日志类别
     */
    public String getRztype() {
        return rztype;
    }

    /**
     * 设置 填写人 该字段是：填写人
     *
     * @param String createby
     */
    public void setCreateby(String createby) {
        this.createby = createby;
    }

    /**
     * 获取 填写人 该字段是：填写人
     */
    public String getCreateby() {
        return createby;
    }

    /**
     * 设置 创建时间 该字段是：创建时间
     *
     * @param DateTime createdate
     */
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    /**
     * 获取 创建时间 该字段是：创建时间
     */
    public String getCreatedate() {
        return createdate;
    }

    /**
     * 设置 地点 该字段是：地点
     *
     * @param String site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * 获取 地点 该字段是：地点
     */
    public String getSite() {
        return site;
    }

    /**
     * 设置 填写人描述 该字段是：填写人描述
     *
     * @param String createbydesc
     */
    public void setCreatebydesc(String createbydesc) {
        this.createbydesc = createbydesc;
    }

    /**
     * 获取 填写人描述 该字段是：填写人描述
     */
    public String getCreatebydesc() {
        return createbydesc;
    }

    /**
     * 设置 完成工作 该字段是：完成工作
     *
     * @param String wcgz
     */
    public void setWcgz(String wcgz) {
        this.wcgz = wcgz;
    }

    /**
     * 获取 完成工作 该字段是：完成工作
     */
    public String getWcgz() {
        return wcgz;
    }

    /**
     * 设置 发现问题 该字段是：发现问题
     *
     * @param String fxwt
     */
    public void setFxwt(String fxwt) {
        this.fxwt = fxwt;
    }

    /**
     * 获取 发现问题 该字段是：发现问题
     */
    public String getFxwt() {
        return fxwt;
    }

    /**
     * 设置 处理结果 该字段是：处理结果
     *
     * @param String cljg
     */
    public void setCljg(String cljg) {
        this.cljg = cljg;
    }

    /**
     * 获取 处理结果 该字段是：处理结果
     */
    public String getCljg() {
        return cljg;
    }

    /**
     * 设置 计划事项 该字段是：计划事项
     *
     * @param String jhsx
     */
    public void setJhsx(String jhsx) {
        this.jhsx = jhsx;
    }

    /**
     * 获取 计划事项 该字段是：计划事项
     */
    public String getJhsx() {
        return jhsx;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getUd_cbsgl_rzid() {
        return ud_cbsgl_rzid;
    }

    public void setUd_cbsgl_rzid(String ud_cbsgl_rzid) {
        this.ud_cbsgl_rzid = ud_cbsgl_rzid;
    }

    public String getV_action() {
        return v_action;
    }

    public void setV_action(String v_action) {
        this.v_action = v_action;
    }

    public String getIccard() {
        return iccard;
    }

    public void setIccard(String iccard) {
        this.iccard = iccard;
    }

    public String getRulesdescription() {
        return rulesdescription;
    }

    public void setRulesdescription(String rulesdescription) {
        this.rulesdescription = rulesdescription;
    }

    public String getInnercard() {
        return innercard;
    }

    public void setInnercard(String innercard) {
        this.innercard = innercard;
    }

    // public String getWtfl() {
    // return wtfl;
    // }
    //
    // public void setWtfl(String wtfl) {
    // this.wtfl = wtfl;
    // }

    // public String getLaborunit() {
    // return laborunit;
    // }
    //
    // public void setLaborunit(String laborunit) {
    // this.laborunit = laborunit;
    // }
    //
    // public String getLaborunit_desc() {
    // return laborunit_desc;
    // }
    //
    // public void setLaborunit_desc(String laborunit_desc) {
    // this.laborunit_desc = laborunit_desc;
    // }

    public String getUd_zyxk_zysqid() {
        return ud_zyxk_zysqid;
    }

    public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
        this.ud_zyxk_zysqid = ud_zyxk_zysqid;
    }

    @Override
    public String[] getChildClasses() {
        return new String[]{Image.class.getName()};
    }

    public String getJcdept() {
        return jcdept;
    }

    public void setJcdept(String jcdept) {
        this.jcdept = jcdept;
    }

    public String getJcdept_desc() {
        return jcdept_desc;
    }

    public void setJcdept_desc(String jcdept_desc) {
        this.jcdept_desc = jcdept_desc;
    }

    public String getZyarea() {
        return zyarea;
    }

    public void setZyarea(String zyarea) {
        this.zyarea = zyarea;
    }

    public String getZyarea_desc() {
        return zyarea_desc;
    }

    public void setZyarea_desc(String zyarea_desc) {
        this.zyarea_desc = zyarea_desc;
    }

    public String getSddept() {
        return sddept;
    }

    public void setSddept(String sddept) {
        this.sddept = sddept;
    }

    public String getSddept_desc() {
        return sddept_desc;
    }

    public void setSddept_desc(String sddept_desc) {
        this.sddept_desc = sddept_desc;
    }

    public String getFzperson() {
        return fzperson;
    }

    public void setFzperson(String fzperson) {
        this.fzperson = fzperson;
    }

    public String getFzperson_desc() {
        return fzperson_desc;
    }

    public void setFzperson_desc(String fzperson_desc) {
        this.fzperson_desc = fzperson_desc;
    }

    public String getZrdept() {
        return zrdept;
    }

    public void setZrdept(String zrdept) {
        this.zrdept = zrdept;
    }

    public String getZrdept_desc() {
        return zrdept_desc;
    }

    public void setZrdept_desc(String zrdept_desc) {
        this.zrdept_desc = zrdept_desc;
    }

    public String getWyj() {
        return wyj;
    }

    public void setWyj(String wyj) {
        this.wyj = wyj;
    }

    public int getIszg() {
        return iszg;
    }

    public void setIszg(int iszg) {
        this.iszg = iszg;
    }

    public int getIshmd() {
        return ishmd;
    }

    public void setIshmd(int ishmd) {
        this.ishmd = ishmd;
    }

    public int getIsupload() {
        return isupload;
    }

    public void setIsupload(int isupload) {
        this.isupload = isupload;
    }

    public int getDr() {
        return dr;
    }

    public void setDr(int dr) {
        this.dr = dr;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getZyname() {
        return zyname;
    }

    public void setZyname(String zyname) {
        this.zyname = zyname;
    }

    public String getRejectreason() {
        return rejectreason;
    }

    public void setRejectreason(String rejectreason) {
        this.rejectreason = rejectreason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFxjb() {
        return fxjb;
    }

    public void setFxjb(String fxjb) {
        this.fxjb = fxjb;
    }

    public String getUd_cbsgl_khbzid() {
        return ud_cbsgl_khbzid;
    }

    public void setUd_cbsgl_khbzid(String ud_cbsgl_khbzid) {
        this.ud_cbsgl_khbzid = ud_cbsgl_khbzid;
    }

    public Integer getWzfxz() {
        return wzfxz;
    }

    public void setWzfxz(Integer wzfxz) {
        this.wzfxz = wzfxz;
    }

    public String getWzlb() {
        return wzlb;
    }

    public void setWzlb(String wzlb) {
        this.wzlb = wzlb;
    }

    public String getWztk() {
        return wztk;
    }

    public void setWztk(String wztk) {
        this.wztk = wztk;
    }

}
