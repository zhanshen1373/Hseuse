package com.hd.hse.entity.base;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * TODO 关系表名
 * ClassName: RelationTableName ()<br/>
 * date: 2014年11月25日  <br/>
 *
 * @author wenlin
 * @pdOid 619ee1f8-27d2-40eb-a58e-b5271321d737
 */
@DBTable(tableName = "sys_relation_info")
public class RelationTableName extends com.hd.hse.common.entity.SuperEntity {
    /**
     * @pdOid 83c1bee3-0256-479a-82db-c0ac1bfba271
     */
    private static final long serialVersionUID = -526928946215714340L;
    /**
     * 主键
     *
     * @pdOid 93ec6342-4a93-4004-b451-8dae3971b3c0
     */
    @DBField(id = true)
    private Integer sys_id;
    /**
     * 关系类型
     *
     * @pdOid 3529e2b4-9474-4d15-a9cb-21befe42745f
     */
    @DBField
    private String sys_type;
    /**
     * 具体功能点
     *
     * @pdOid 743ce897-94c5-4edd-a833-bda5e466ecb0
     */
    @DBField
    private String sys_fuction;
    /**
     * 具体作业票
     *
     * @pdOid 6db913d3-30b0-4a3c-b48a-6c79ac089eca
     */
    @DBField
    private String sys_value;
    /**
     * 需要手动输入的
     *
     * @pdOid 5a5f3b05-e90a-4109-a912-e0419034acda
     */
    @DBField
    private String Input_value;
    /**
     * 关系描述
     *
     * @pdOid e4b24054-369a-411b-bb5e-03cb6398699f
     */
    @DBField
    private String sys_type_desc;
    /**
     * 逻辑删除
     *
     * @pdOid 05900367-2b04-4b58-a65b-cefdf5a58fc4
     */
    @DBField
    private Integer dr;
    /**
     * 是否启用
     *
     * @pdOid 5bb3ea25-7d7c-45fc-bc43-b363befb663e
     */
    @DBField
    private Integer isqy;
    /**
     * 时间戳
     *
     * @pdOid 08a747db-614f-4061-a7d8-36a92da52cf8
     */
    @DBField
    private String changedate;


    /**
     * @pdOid 6b0145e2-54f0-4f2a-99d7-99c7234cd885
     */
    public RelationTableName() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @pdOid d8b962b3-e55d-4825-8e59-ad5498259376
     */
    public Integer getSys_id() {
        return sys_id;
    }

    /**
     * @param sys_id
     * @pdOid 148d7e62-ff24-4455-82b3-3c1815a3b2de
     */
    public void setSys_id(Integer sys_id) {
        this.sys_id = sys_id;
    }

    /**
     * @pdOid 2ee9eb27-4b57-41d3-a2a0-1fb4e6e70bc5
     */
    public String getSys_type() {
        return sys_type;
    }

    /**
     * @param sys_type
     * @pdOid 11aa7011-54ef-45f1-b91c-a3827bd29c6f
     */
    public void setSys_type(String sys_type) {
        this.sys_type = sys_type;
    }

    /**
     * @pdOid 42f360f4-15f6-4c3f-877d-9852b3e00880
     */
    public String getSys_fuction() {
        return sys_fuction;
    }

    /**
     * @param sys_fuction
     * @pdOid f8e0f0a7-f5bf-4900-9a1f-ceac1b36c66b
     */
    public void setSys_fuction(String sys_fuction) {
        this.sys_fuction = sys_fuction;
    }

    /**
     * @pdOid dbbf3207-d3bd-4bd6-bf90-c3d066163613
     */
    public String getSys_value() {
        return sys_value;
    }

    /**
     * @param sys_value
     * @pdOid 454e1fce-db6c-4e10-85d5-48da784a2773
     */
    public void setSys_value(String sys_value) {
        this.sys_value = sys_value;
    }

    /**
     * @pdOid 278c4653-cef6-4209-9606-d6fbb43f6599
     */
    public String getInput_value() {
        return Input_value;
    }

    /**
     * @param input_value
     * @pdOid 468b8569-018f-4d0a-b254-95d327b7f45b
     */
    public void setInput_value(String input_value) {
        Input_value = input_value;
    }

    /**
     * @pdOid 9ae8b0dd-910a-4d13-a837-c147be71d47f
     */
    public String getSys_type_desc() {
        return sys_type_desc;
    }

    /**
     * @param sys_type_desc
     * @pdOid 004b5d59-c2d3-4fb0-878d-42d6725148c6
     */
    public void setSys_type_desc(String sys_type_desc) {
        this.sys_type_desc = sys_type_desc;
    }

    /**
     * @pdOid df76e362-84f3-4d31-ace9-1fcf915308b2
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * @param dr
     * @pdOid b2aae2ff-48e1-49ae-ac1b-c3b89b6745fb
     */
    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * @pdOid 9fb76116-6e30-4103-8a0f-4126706b8517
     */
    public Integer getIsqy() {
        return isqy;
    }

    /**
     * @param isqy
     * @pdOid 99488f8a-6e66-46a0-9634-6cc0c65604c8
     */
    public void setIsqy(Integer isqy) {
        this.isqy = isqy;
    }

    /**
     * @pdOid 3320b70b-97cb-47f6-b681-5c2f6261c485
     */
    public String getChangedate() {
        return changedate;
    }

    /**
     * @param changedate
     * @pdOid aa1ae421-e2cc-424d-b9a6-56394777438d
     */
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

}