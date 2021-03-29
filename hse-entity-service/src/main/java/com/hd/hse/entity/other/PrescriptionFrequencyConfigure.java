package com.hd.hse.entity.other;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * TODO 时效频次配置 ClassName: PrescriptionFrequencyConfigure ()<br/>
 * date: 2014年11月4日 <br/>
 *
 * @author wenlin
 * @pdOid aa98fe9d-9ab6-41e2-a5ad-74b3e9cc5288
 */
@DBTable(tableName = "ud_zyxk_sxpcpz")
public class PrescriptionFrequencyConfigure extends
        com.hd.hse.common.entity.SuperEntity {
    /**
     * @pdOid f6401efa-8b2e-4b1e-a212-a24a8cdfad16
     */
    private static final long serialVersionUID = 7668641812471447883L;
    /**
     * 主键
     *
     * @pdOid 54d0284a-a44c-4e88-90e6-6c4a0035b131
     */
    @DBField(id = true)
    private String ud_zyxk_sxpcpzid;
    /**
     * 作业类型
     *
     * @pdOid 6897ea27-d0ec-48aa-888c-066d7d74ae7e
     */
    @DBField
    private String jobtype;
    /**
     * 可延期次数
     *
     * @pdOid d05c7e50-ba02-40a4-b1c6-4a95e38a2bb2
     */
    @DBField
    private String delaycount;
    /**
     * 级别
     *
     * @pdOid 9442e617-c15f-4221-87c6-4e03d63d5ae0
     */
    @DBField
    private String zylevel;
    /**
     * 更新时间
     *
     * @pdOid c890d9ef-a72f-424d-80d9-76afe5fec850
     */
    @DBField
    private String changedate;
    /**
     * 最大延期次数
     *
     * @pdOid d6e2244b-2089-4061-a9e2-cbc34b00fd31
     */
    @DBField
    private String maxyxq;
    /**
     * 逻辑删除
     *
     * @pdOid 9ae7a725-519c-4c21-9bb2-66e6d54e78da
     */
    @DBField
    private String dr;
    /**
     * @pdOid 6b856f11-51f6-43fc-a5d6-b9872d27c920
     */
    @DBField
    private Integer tag;
    /**
     * 总期限(分钟数)
     *
     * @pdOid 735c658a-fa30-4ac7-b0bc-c5f81fac151d
     */
    @DBField
    private Integer zqx;
    /**
     * 是否连续延期
     */
    @DBField
    private Integer iscontinuousdelay;

    @DBField
    private Integer ispause;

    /**
     * @pdOid 40aaf55f-8226-47bc-8538-c24fb4b8d40f
     */
    public String getUd_zyxk_sxpcpzid() {
        return ud_zyxk_sxpcpzid;
    }

    /**
     * @param ud_zyxk_sxpcpzid
     * @pdOid 0ac10cdf-5819-4ee2-87d0-dae18e90ee06
     */
    public void setUd_zyxk_sxpcpzid(String ud_zyxk_sxpcpzid) {
        this.ud_zyxk_sxpcpzid = ud_zyxk_sxpcpzid;
    }

    /**
     * @pdOid 77dd8739-ee4e-42f6-80a6-804fbad3ed2e
     */
    public String getJobtype() {
        return jobtype;
    }

    /**
     * @param jobtype
     * @pdOid 088a046c-36d7-43e4-ae80-d051bf1d450d
     */
    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    /**
     * @pdOid db331c05-a2ca-4852-93b0-53eb1c89d619
     */
    public String getDelaycount() {
        return delaycount;
    }

    /**
     * @param delaycount
     * @pdOid 4df34bd4-467d-455a-a141-ee6f70878939
     */
    public void setDelaycount(String delaycount) {
        this.delaycount = delaycount;
    }

    /**
     * @pdOid 76beb1b3-000b-4c4d-bf99-9400fca018ba
     */
    public String getZylevel() {
        return zylevel;
    }

    /**
     * @param zylevel
     * @pdOid 33eb534a-1ce3-4c34-9b53-e17d18688e3f
     */
    public void setZylevel(String zylevel) {
        this.zylevel = zylevel;
    }

    /**
     * @pdOid f5f7fbb3-18e2-4500-bdd3-c6174367b237
     */
    public String getChangedate() {
        return changedate;
    }

    /**
     * @param changedate
     * @pdOid 556d14da-a7b9-418f-a278-6c6d44685a02
     */
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    /**
     * @pdOid 62419386-f36f-4be1-93c9-73ff3f6e5b4e
     */
    public String getMaxyxq() {
        return maxyxq;
    }

    /**
     * @param maxyxq
     * @pdOid c436b119-a39f-4b87-8101-c7f15d1143f4
     */
    public void setMaxyxq(String maxyxq) {
        this.maxyxq = maxyxq;
    }

    /**
     * @pdOid c125fef8-b803-4885-ad0c-f725f354bd4e
     */
    public String getDr() {
        return dr;
    }

    /**
     * @param dr
     * @pdOid 6da89739-5392-4e12-82fc-75444da85af2
     */
    public void setDr(String dr) {
        this.dr = dr;
    }

    /**
     * @pdOid c1b6c848-a95d-4dcb-875b-c61974d7fe56
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * @param tag
     * @pdOid 50609753-2f29-4d85-a326-6174f36882dd
     */
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    /**
     * @pdOid 877c4c94-4a9b-4128-91db-39127f8ba64b
     */
    public Integer getZqx() {
        return zqx;
    }

    /**
     * @param zqx
     * @pdOid 1285f22d-e824-4008-a425-f413d358c83d
     */
    public void setZqx(Integer zqx) {
        this.zqx = zqx;
    }

    public Integer getIscontinuousdelay() {
        return iscontinuousdelay;
    }

    public void setIscontinuousdelay(Integer iscontinuousdelay) {
        this.iscontinuousdelay = iscontinuousdelay;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public Integer getIspause() {
        return ispause;
    }

    public void setIspause(Integer ispause) {
        this.ispause = ispause;
    }
}