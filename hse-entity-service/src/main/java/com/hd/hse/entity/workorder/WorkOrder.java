/**
 * Project Name:hse-entity-service
 * File Name:WorkOrder.java
 * Package Name:com.hd.hse.entity.workorder
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.entity.workorder;

import android.graphics.Bitmap;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

import java.util.List;

/**
 * ClassName:WorkOrder (作业票).<br/>
 * Date: 2014年9月23日 <br/>
 *
 * @author lg
 * @pdOid a5da35b0-c634-4916-8e92-4e1588d5c77c
 * @see
 */
@DBTable(tableName = "ud_zyxk_zysq")
public class WorkOrder extends com.hd.hse.common.entity.SuperEntity {
    /**
     * serialVersionUID:TODO().
     *
     * @pdOid bc1414c3-f22a-4f67-a0b7-b1b02f8816bc
     */
    private static final long serialVersionUID = 6278955275530284767L;
    /**
     * 任务外键
     *
     * @pdOid f008ef29-bb6b-4e39-b60b-6a3cd20a9318
     */
    @DBField(foreign = true)
    private String ud_zyxk_worktaskid;
    /**
     * 唯一标识
     *
     * @pdOid 0969d9a8-c095-48a8-8be2-d1f58302e74f
     */
    @DBField(id = true)
    private String ud_zyxk_zysqid;
    /**
     * 作业预约主键
     *
     * @pdOid 2c383f01-a109-4248-a5ba-4a3787c8f228
     */
    @DBField
    private String ud_zyxk_zyyyid;
    /**
     * 作业票父级主键
     *
     * @pdOid 967da76d-e018-418a-8e79-654c0466b3bc
     */
    @DBField
    private String parent_id;
    /**
     * 作业票类型
     *
     * @pdOid d16a1308-aa12-4dca-9b28-b2455f8d8526
     */
    @DBField
    private String zyptype;
    /**
     * 作业类别
     *
     * @pdOid 719cc0c1-9884-4598-a3c3-d447e6d72328
     */
    @DBField
    private String zypclass;
    /**
     * 补票标记
     *
     * @pdOid 53147f0c-f113-47e9-957c-b999f39258b9
     */
    @DBField
    private Integer bpflag;
    /**
     * 申请单号
     *
     * @pdOid 5ab5a616-2900-4a8c-966c-88407b18fc7c
     */
    @DBField
    private String zysqnum;
    /**
     * 受影响相关方
     *
     * @pdOid df57a2a4-cb59-412c-ab20-c8f4f861e627
     */
    @DBField
    private String xgf;
    /**
     * 是否附安全工作方案
     *
     * @pdOid 895d436e-5895-4016-bbdf-453464b7f2c8
     */
    @DBField
    private Integer isfaqgzfa;
    /**
     * 是否附图纸
     *
     * @pdOid 73878a1e-8549-4d81-b250-e2e6620df9b9
     */
    @DBField
    private Integer isftz;
    /**
     * 图纸说明
     *
     * @pdOid c7a903f0-5330-45bc-b9a1-deea91dc57ba
     */
    @DBField
    private String tzsm;
    /**
     * 其他附件（危害识别等）
     *
     * @pdOid f2badc33-5b0c-43db-81d8-36b503ff1b30
     */
    @DBField
    private String qtfj;
    /**
     * 作业性质
     *
     * @pdOid 304e20bf-dddd-4f4a-b7b7-70cc0d0b9d6c
     */
    @DBField
    private String zyxz;
    /**
     * 预约单号
     *
     * @pdOid a440ee47-a472-467b-9640-b39ed677679f
     */
    @DBField
    private String zyyynum;
    /**
     * 查询选择器
     *
     * @pdOid 5a4ed3a7-decc-414a-8ce7-a8f79c901fc2
     */
    @DBField
    private String sddept;
    /**
     * 日期选择器
     *
     * @pdOid 4716f42a-ee0a-4070-9fdc-b251f0040e02
     */
    @DBField
    private String zydate;
    /**
     * 文本框
     *
     * @pdOid 75f15b99-57c2-4db3-b55f-5a11a8f28813
     */
    @DBField
    private String zyname;
    /**
     * 文本域
     *
     * @pdOid 0d1947ae-8e54-4b1e-9b45-235bb3204be3
     */
    @DBField
    private String zysite;
    /**
     * 作业内容
     *
     * @pdOid 7b80c8b9-6d84-435a-b28d-2e4d74c7847e
     */
    @DBField
    private String zycontent;
    /**
     * 是否能量隔离
     *
     * @pdOid 5530f94a-1c63-4770-845e-012b56a7d2b2
     */
    @DBField
    private Integer isnlgl;
    /**
     * 是否气体检测
     *
     * @pdOid 27f3e522-b1fd-4197-871d-cc0d870936c7
     */
    @DBField
    private Integer isqtjc;
    /**
     * 检测位置
     *
     * @pdOid 30651262-9b40-4c26-af79-04207587fd67
     */
    @DBField
    private String jclocation;
    /**
     * 检测时效
     *
     * @pdOid 94f766a7-2e38-4f04-aeda-032f562e52fa
     */
    @DBField
    private String jcsx;
    /**
     * 监护人名称
     *
     * @pdOid d111daa4-acf6-4656-bb6a-663a3416ad84
     */
    @DBField
    private String jhrname;
    /**
     * 预计作业人员数量
     *
     * @pdOid 0d60505a-9642-47be-8d9e-7c76d4058bbd
     */
    @DBField
    private Integer yjzyrysl;
    /**
     * 预计特种作业人员数量
     *
     * @pdOid 390ee52c-c147-4ae7-ad88-9bee339325ad
     */
    @DBField
    private Integer yjtzzyrysl;
    /**
     * 作业开始时间
     *
     * @pdOid 2af5f910-7361-4a73-b233-5366ed3db376
     */
    @DBField
    private String zystarttime;
    /**
     * 作业结束时间
     *
     * @pdOid 9c1e0c8e-a3cf-43e5-b67d-6accf03e2849
     */
    @DBField
    private String zyendtime;
    /**
     * 作业位置
     *
     * @pdOid 31ae1379-3a81-44d8-aeb1-141039291bd1
     */
    @DBField
    private String zylocation;
    /**
     * 是否有相关方
     *
     * @pdOid f5383618-0727-468a-a65f-84d8ade84e68
     */
    @DBField
    private Integer isyxgf;
    /**
     * 是否可以延期
     *
     * @pdOid f3cfcdf7-b530-414e-8ecf-7f745e7d1d9e
     */
    @DBField
    private String iskyyq;
    /**
     * 延期次数
     *
     * @pdOid e1951f8f-d425-481a-9586-3002cd73087a
     */
    @DBField
    private Integer yqcount;
    /**
     * 危害识别
     *
     * @pdOid dc1bd28d-f83f-444a-be3f-0021fb020113
     */
    @DBField
    private String whshib;
    /**
     * 其他危害
     *
     * @pdOid c5e611fd-e6f6-4258-a454-8b95a8e53ec9
     */
    @DBField
    private String qthazard;
    /**
     * 工作前安全措施
     *
     * @pdOid 1a56b9b3-8389-4a1d-a737-2fc3d0cecc98
     */
    @DBField
    private String gzqaqcs;
    /**
     * 其他措施
     *
     * @pdOid 2c7b0b6d-8d30-4773-96a2-a13ccb7e32da
     */
    @DBField
    private String qtcs;
    /**
     * 个人防护装备
     *
     * @pdOid 047fea94-8a6b-4a1c-860d-dce67751ca12
     */
    @DBField
    private String grfhzb;
    /**
     * 补充防范措施
     *
     * @pdOid c244de8c-358e-4a0a-b183-405a6bbdc575
     */
    @DBField
    private String bcffcs;
    /**
     * 措施确认人
     *
     * @pdOid 174e9098-5e2f-420a-b80f-2c2742affc8f
     */
    @DBField
    private String csqrperson;
    /**
     * 作业申请人
     *
     * @pdOid bdd23a6e-70dd-412e-8fec-bf19eb068700
     */
    @DBField
    private String zysqperson;
    /**
     * 作业批准人
     *
     * @pdOid a8333d7b-ac07-45c4-854d-5a79300ab832
     */
    @DBField
    private String zypzperson;
    /**
     * 取消原因
     *
     * @pdOid dbfd9f83-6423-4d32-8b6b-d3c89da12c32
     */
    @DBField
    private String reasonqx;
    /**
     * 取消提出人
     *
     * @pdOid cf90621f-b633-4fb2-8c72-6ca9f1074f7c
     */
    @DBField
    private String tcpersonqx;
    /**
     * 取消批准人
     *
     * @pdOid 7d2dfff1-dcb1-46ee-8a9a-606faa2eb8f0
     */
    @DBField
    private String pzpersonqx;
    /**
     * 取消日期
     *
     * @pdOid 769399f4-7b89-4b53-8698-2c6c4c0b21ea
     */
    @DBField
    private String qxtime;
    /**
     * 关闭方式
     *
     * @pdOid 51267b27-173c-484c-a2c8-bc47d1aecd5a
     */
    @DBField
    private String gbtype;
    /**
     * 关闭说明
     *
     * @pdOid 5ed088b0-ca21-4558-bc6d-12b6c09242ea
     */
    @DBField
    private String gbsm;
    /**
     * 关闭申请人
     *
     * @pdOid 176cbcc9-b7f3-4691-a868-f94c1baecb28
     */
    @DBField
    private String sqpersongb;
    /**
     * 申请人审核时间
     *
     * @pdOid 17964979-c192-49a5-a9bc-261bce6a546c
     */
    @DBField
    private String sqrshtime;
    /**
     * 关闭相关方
     *
     * @pdOid e56bd8e7-58df-40a5-80b4-061a4d0ad92a
     */
    @DBField
    private String xgfgb;
    /**
     * 相关方审核时间
     *
     * @pdOid 6e780462-8d0e-4ed5-9b27-481acb0108e2
     */
    @DBField
    private String xgfshtime;
    /**
     * 关闭批准人
     *
     * @pdOid 1e516747-fc6b-4fb3-878f-0b6834be6342
     */
    @DBField
    private String pzrgb;
    /**
     * 审核时间
     *
     * @pdOid 368682d8-a71d-455a-a68c-f36784053969
     */
    @DBField
    private String pzrshtime;
    /**
     * 状态(待确定)
     *
     * @pdOid a15bfead-53f2-4631-8cd0-0961371ed4b8
     */
    @DBField
    private String status;
    /**
     * 状态日期
     *
     * @pdOid 7e1a66b2-7d92-4664-8e74-f0785d543e09
     */
    @DBField
    private String statusdate;
    /**
     * 历史标记
     *
     * @pdOid 37324ec5-5c45-4f74-97d3-9facb2dbad0c
     */
    @DBField
    private String historyflag;
    /**
     * 作业区域编码
     *
     * @pdOid e50c0938-99db-4ddd-bbb8-0ad6b2350e1c
     */
    @DBField
    private String zyarea;
    /**
     * 作业级别
     *
     * @pdOid a7fa4119-482a-4a21-bcd6-bfaa514ffbe0
     */
    @DBField
    private String zylevel;
    /**
     * 作业级别描述
     *
     * @pdOid 986c7bb8-e66b-4d32-af83-f26be0c5984d
     */
    @DBField
    private String zylevel_desc;
    /**
     * 作业单位
     *
     * @pdOid 9e8257d9-4ca0-4744-9efa-be43628f8346
     */
    @DBField
    private String zydept;
    /**
     * 作业单位描述
     *
     * @pdOid 4c6c9c4e-dc80-48f3-8162-2c8ad44ca4cb
     */
    @DBField
    private String zydept_desc;
    /**
     * 属地单位描述
     *
     * @pdOid c40a57b8-d76c-467f-979c-2b271e8094ed
     */
    @DBField
    private String sddept_desc;
    /**
     * 能量隔离??号
     *
     * @pdOid 7f0d1ce5-663a-4423-8ea9-584647c7c200
     */
    @DBField
    private String nlgldnum;
    /**
     * 作业批准时间
     *
     * @pdOid 7ecf66cf-3206-47e7-8f87-8a6172caf7fd
     */
    @DBField
    private String zypztime;
    /**
     * 作业1单位1
     *
     * @pdOid afa3e672-01ca-4a77-a4c4-3e422973b032
     */
    @DBField
    private String chq_zy1dw1;
    /**
     * 作业1单位2
     *
     * @pdOid 1a986fe6-9a9a-4ff2-a891-0489c55c03a1
     */
    @DBField
    private String chq_zy1dw2;
    /**
     * 作业1单位1确认人1
     *
     * @pdOid 92655b7e-cef5-4f6a-a2d7-651188a78170
     */
    @DBField
    private String chq_zy1dw1qrr1;
    /**
     * 作业1单位2确认人2
     *
     * @pdOid 596a8961-4f99-4539-9feb-5048f1bb5d13
     */
    @DBField
    private String chq_zy1dw2qrr2;
    /**
     * 作业1单位1确认1时间
     *
     * @pdOid 93de45ac-d22b-493d-9164-f420abe805b2
     */
    @DBField
    private String chq_zy1dw1qrr1time;
    /**
     * 属地单位负责人（措施）
     *
     * @pdOid 4c15da02-8c84-4ce0-8760-6c0222c2986d
     */
    @DBField
    private String sddwfzperson;
    /**
     * 作业单位现场负责人（措施）
     *
     * @pdOid 7a7e826f-f0de-42fb-97d1-283844505a49
     */
    @DBField
    private String zydwxcfzperson;
    /**
     * 属地单位负责人签字时间
     *
     * @pdOid 7d41dfe2-4909-4352-9749-ec9808dc3aaf
     */
    @DBField
    private String sddwfzpersontime;
    /**
     * 作业单位现场负责人签字时间
     *
     * @pdOid f96df952-6252-4fb2-b5dc-56ff467f1011
     */
    @DBField
    private String zydwxcfzpersontime;
    /**
     * 拆线人
     *
     * @pdOid bb731d72-8951-4338-8aab-27d9de1b3a51
     */
    @DBField
    private String cxperson;
    /**
     * 作业1单位2确认人2时间
     *
     * @pdOid 9109eb72-829a-42e0-804f-d057394c0426
     */
    @DBField
    private String chq_zy1dw2qrr2time;
    /**
     * 关闭作业单位监护人
     *
     * @pdOid f69c53c3-618d-421e-ac62-8b37467a3fc1
     */
    @DBField
    private String chq_gbzydwjhr;
    /**
     * 拆线人签字时间
     *
     * @pdOid 4d20bd66-69b6-4e71-8bba-dc55436250c6
     */
    @DBField
    private String cxtime;
    /**
     * 关闭属地监护人
     *
     * @pdOid 1f3c4874-f834-4529-841d-19da9a5e4fc8
     */
    @DBField
    private String chq_gbxdjhr;
    /**
     * 关闭属地监护人时间
     *
     * @pdOid 972f2392-e231-41bc-8f27-590ea5070467
     */
    @DBField
    private String chq_gbxdjhrtime;
    /**
     * 关闭作业单位监护人时间
     *
     * @pdOid 8f449377-8baa-4121-8a7c-cd6f9f18ed25
     */
    @DBField
    private String chq_gbzydwjhrtime;
    /**
     * 是否承包商
     *
     * @pdOid bad7268f-1714-474a-8f3e-715d11da0a0e
     */
    @DBField
    private Integer iscbs;
    /**
     * 检查位置
     *
     * @pdOid 85866632-210b-4aaf-bdc9-f564094298c6
     */
    @DBField
    private String jclocation_desc;
    /**
     * 已延期次数
     *
     * @pdOid 58491374-b01c-4354-b87c-3064a8fb22e2
     */
    @DBField
    private Integer yyqcount;
    /**
     * 作业区域
     *
     * @pdOid 283ff547-544e-4bfb-a979-bf0978bed237
     */
    @DBField
    private String zyarea_desc;
    /**
     * 取消提出时间
     *
     * @pdOid 214491d8-6ed5-4437-ad58-7f5c662626f9
     */
    @DBField
    private String tcpersonqxtime;
    /**
     * 作业作废确认人
     *
     * @pdOid acf64b90-168f-45cd-a9c5-6c1eb57fcbd4
     */
    @DBField
    private String zfperson;
    /**
     * 作业作废确认人时间
     *
     * @pdOid 99385032-0986-4ad1-8536-a11c8d39a39d
     */
    @DBField
    private String zfpersontime;
    /**
     * 作业作废原因
     *
     * @pdOid a1391dc8-b068-482e-8fa3-4af42ab74883
     */
    @DBField
    private String zfcause;
    /**
     * 作业位置描述
     *
     * @pdOid 8881b96e-7394-43bd-a517-0e6ba26c9000
     */
    @DBField
    private String zylocation_desc;
    /**
     * 实际结束时间
     *
     * @pdOid 224e970a-5e5e-4e27-bf41-5059c60e52ff
     */
    @DBField
    private String sjendtime;
    /**
     * 实际开始时间
     *
     * @pdOid d9d409cb-85a1-4e2c-9a26-d8fb019cad78
     */
    @DBField
    private String sjstarttime;
    /**
     * 个人防护装备措施
     *
     * @pdOid 65bbc04e-5fb2-4e01-8407-1c33b62c2b67
     */
    @DBField
    private String grfhequipment;
    /**
     * 设备交出后状态
     *
     * @pdOid 20ac3065-ab9c-45de-88cb-7f17235c56e2
     */
    @DBField
    private Integer afterissuit;
    /**
     * 设备交出前状态
     *
     * @pdOid c8caff6d-03fc-4173-a788-d9eb41afbfb8
     */
    @DBField
    private Integer beforeissuit;
    /**
     * 工作是否合格
     *
     * @pdOid 5ade25e5-20af-4ecc-ae9e-623d0f7a87eb
     */
    @DBField
    private String isqualify;
    /**
     * 验收结果
     *
     * @pdOid a1af0212-1209-435b-858f-b63927e1a2e1
     */
    @DBField
    private String checkresult;
    /**
     * 是否已验收
     *
     * @pdOid 5cfd6a7c-9d54-4dea-bdcc-5c8602e040f9
     */
    @DBField
    private Integer ischecked;
    /**
     * 监护方式
     *
     * @pdOid 1522bbd6-b9cd-4b9d-8082-8cfe9bb48015
     */
    @DBField
    private String custodytype;
    /**
     * 动火票
     *
     * @pdOid e57fc8a5-1969-4d58-9f34-caac13e116d2
     */
    @DBField
    private String dhzy_id;
    /**
     * 是否强制关联
     *
     * @pdOid f295a841-8109-4025-9a48-59a43c192ce3
     */
    @DBField
    private Integer isrelation;
    /**
     * a1
     *
     * @pdOid 563d3e0f-dac1-40c0-ab0c-82868dc498e6
     */
    @DBField
    private String xgfqx;
    /**
     * a2
     *
     * @pdOid 4dce026c-a927-4655-be56-44c51ebce3f8
     */
    @DBField
    private String zyjhperson;
    /**
     * a3
     *
     * @pdOid a01b31c5-2bf4-43e0-98e2-a2e68f1acef2
     */
    @DBField
    private String spstatus;
    /**
     * a4
     *
     * @pdOid 66c59fc8-bdcd-4316-95c2-3c4555af2262
     */
    @DBField
    private Integer pdasignpoint;
    /**
     * a5
     *
     * @pdOid cfc801dc-c054-4227-847f-5fc328e03bc6
     */
    @DBField
    private Integer gyfxxjcs;
    /**
     * a6
     *
     * @pdOid 78fde21d-75be-4204-a1f1-43659ccd3aea
     */
    @DBField
    private Integer zyfxxjcs;
    /**
     * a7
     *
     * @pdOid a04ab3d3-3e97-441a-bc57-034d5f4c672e
     */
    @DBField
    private Integer vpdamsgcontent;
    /**
     * a8
     *
     * @pdOid 8fdb5b94-f7ee-44dc-81df-7427e01c0dc8
     */
    @DBField
    private String issave;
    /**
     * a9
     *
     * @pdOid a3d1bd42-0ac6-4202-86c3-f7263aa28854
     */
    @DBField
    private Integer csnum;
    /**
     * 已经保存的措施界面编码"PGTYPE"开头
     *
     * @pdOid f928d682-0ecb-4d1a-9667-b8e2b14a9393
     */
    @DBField
    private String cssavenum;
    /**
     * a11
     *
     * @pdOid 9c38115b-744f-4155-b9b2-55b06bdb4f61
     */
    @DBField
    private String cssavefied;
    /**
     * a12
     *
     * @pdOid a465d09d-11af-4ab9-a0a2-3fb4da6aefbe
     */
    @DBField
    private String parent_status;
    /**
     * a13
     *
     * @pdOid fd827e7c-4486-4e13-8334-c05e00715b7e
     */
    @DBField
    private String functionpoint;
    /**
     * a14
     *
     * @pdOid 447fd2f1-0257-4c33-a337-c13e6ad7efda
     */
    @DBField
    private String initlevel;
    /**
     * a15
     *
     * @pdOid e98cff58-ce39-49ea-bd6d-d1a6ac485ded
     */
    @DBField
    private String dzwjname;
    /**
     * a16
     *
     * @pdOid bfa2bb2f-76c2-4121-b69f-b478133c34f2
     */
    @DBField
    private String wjzl;
    /**
     * a17
     *
     * @pdOid 8cdcbd7a-5527-4f51-80e3-abd574b44473
     */
    @DBField
    private String dzzl;
    /**
     * a18
     *
     * @pdOid 0e3a8643-059c-45cc-8b62-1a56a2970e75
     */
    @DBField
    private String qzjedzh;
    /**
     * a19
     *
     * @pdOid a715bb72-ae7a-4fbe-8ab0-09dcf1ca3bcb
     */
    @DBField
    private String hightandenvironment;
    /**
     * a20
     *
     * @pdOid cc4a5357-4449-49dc-a9cd-b9859cac71f0
     */
    @DBField
    private String mbzyxz;
    /**
     * a21
     *
     * @pdOid 04164539-4396-4030-874e-c0cb06bc0003
     */
    @DBField
    private String mbcode;
    /**
     * a22
     *
     * @pdOid 781f5ddf-9461-41fb-9606-113164f22745
     */
    @DBField
    private String mbtype;
    /**
     * a23
     *
     * @pdOid 4f4b2668-425f-4cf3-b4e9-5aff895fec55
     */
    @DBField
    private String mblocation;
    /**
     * a24
     *
     * @pdOid 5874e487-7ada-41f4-96de-c62e9f71d713
     */
    @DBField
    private String gaskettype;
    /**
     * a25
     *
     * @pdOid 0aa7fb22-c302-475f-ad8b-43c5abb664c6
     */
    @DBField
    private String jz;
    /**
     * a26
     *
     * @pdOid 277f15fd-de36-4ad5-a79c-b7d2f5c7d9fe
     */
    @DBField
    private String temp;
    /**
     * a27
     *
     * @pdOid c0c86373-3957-440d-9d29-b09e9fc41668
     */
    @DBField
    private String mpa;
    /**
     * a28
     *
     * @pdOid 9412f908-0f53-43f1-ba5e-964c5fdbc37b
     */
    @DBField
    private String fcjgtab;
    /**
     * a29
     *
     * @pdOid fc33aa33-5c04-4d0f-81c5-348c0792ee76
     */
    @DBField
    private String mbclass;
    /**
     * a30
     *
     * @pdOid 50b86e61-2dc0-4d68-bdb3-4a936121d55b
     */
    @DBField
    private String ud_zyxk_zyfxfxid;
    /**
     * a31
     *
     * @pdOid a2839190-8c0d-455b-a460-bcb90791a9af
     */
    @DBField
    private String fxstatus;
    /**
     * a32
     *
     * @pdOid 648e29c9-488f-4d96-b4e7-c9f5d26ce00b
     */
    @DBField
    private String zftype;
    /**
     * a33
     *
     * @pdOid d3cdf5d6-be47-4b65-9d98-25908f70412a
     */
    @DBField
    private Integer maxyxq;
    /**
     * a34
     *
     * @pdOid cfca5877-2233-46fb-978c-1c8117538eda
     */
    @DBField
    private String gjjy;
    /**
     * a35
     *
     * @pdOid c427dd74-f646-4f83-add6-dc806a682b75
     */
    @DBField
    private Integer ud_zyxk_jsaid;
    /**
     * a36
     *
     * @pdOid a7813dd3-5a39-4591-8078-d36d4da0f3c8
     */
    @DBField
    private float total;
    /**
     * a37
     *
     * @pdOid e36f5df2-0f0f-4c1e-882b-7d3da89b4091
     */
    @DBField
    private Integer zqx;
    /**
     * 是否伪删除
     *
     * @pdOid 21eac9fe-1fb4-4677-a10e-55e8ea398ec6
     */
    @DBField
    private Integer dr;
    /**
     * 上传
     *
     * @pdOid f3fb8d0b-ee94-4ef2-ab0d-5bd5efdf252e
     */
    @DBField
    private Integer isupload;
    /**
     * 标记
     *
     * @pdOid a47a5630-5d08-40da-a1b7-9b54878eccc9
     */
    @DBField
    private Integer tag;
    /**
     * 危害类型
     * <p>
     * Created by liuyang on 2016/03/11
     */
    @DBField
    private String whtype;
    /**
     * 作业类型描述
     *
     * @pdOid 1a5106c0-eb91-431f-9d92-a324777a335a
     */
    private String zypclassname;
    /**
     * 状态描述
     *
     * @pdOid 4f79405e-dce9-4684-9a07-75cc717879dc
     */
    private String statusname;
    /**
     * 关闭方式描述
     *
     * @pdOid 2c127007-9f99-49df-93f8-4eabf135590b
     */
    private String gbtype_desc;
    /**
     * 界面展示延期的次数描述
     *
     * @pdOid 15b14f06-e332-40de-818c-79c9996fb9ed
     */
    private String yqcountdescdesc;

    /**
     * 是否已下到本地
     */
    private boolean downloaded = true;

    /**
     * jczy:TODO(进车作业).
     */
    @DBField
    private String jczy;

    /**
     * jcyy:TODO(进车原因)
     */
    @DBField
    private String jcyy;

    /**
     * cx:TODO(车型).
     */
    @DBField
    private String cx;

    /**
     * cphm:TODO(车牌号码).
     */
    @DBField
    private String cphm;

    /**
     * jsyxm:TODO(驾驶员).
     */
    @DBField
    private String jsyxm;

    /**
     * jszhm:TODO(驾驶证).
     */
    @DBField
    private String jszhm;

    /**
     * jdcaqxslxt:TODO(行驶路线).
     */
    @DBField
    private String jdcaqxslxt;
    /**
     * 动火级别 Created by liuyang on 2016年9月18日
     */
    @DBField
    private String dhlevel;
    /**
     * 受限空间级别 Created by liuyang on 2016年9月18日
     */
    @DBField
    private String sxkjlevel;
    /**
     * 射线级别 Created by liuyang on 2016年9月18日
     */
    @DBField
    private String sxlevel;
    /**
     * 高处级别 Created by liuyang on 2016年9月18日
     */
    @DBField
    private String gclevel;
    /**
     * 作业票类别名称 Created by liuyang on 2016年10月26日
     */
    @DBField
    private String zyptypedesc;
    /**
     * 是否需要上传
     */
    @DBField
    private int needupload;

    @DBField
    private String topzypclass;
    /**
     * 是否暂停
     */
    @DBField
    private Integer ispause;
    /**
     * 暂停时间
     */
    @DBField
    private String pausetime;
    /**
     * 优先级ID
     */
    @DBField
    private String ud_zyxk_prtylineid;
    /**
     * 时效频次ID
     */
    @DBField
    private String ud_zyxk_sxpcpzid;
    /**
     * 是否符合
     */
    @DBField
    private String isaccord;
    /**
     * 气体检测暂停人
     */
    @DBField
    private String qtjcpauseby;
    /**
     * 气体检测暂停时间
     */
    @DBField
    private String qtjcpausetime;
    /**
     * 气体检测停止人
     */
    @DBField
    private String qtjcstopby;
    /**
     * 气体检测停止时间
     */
    @DBField
    private String qtjcstoptime;

    /**
     * String类型的jcsx转换为int类型的
     */
    @DBField
    private int cjcsx;

    @DBField
    private String jctime;

    @DBField
    private String hctime;

    @DBField
    private int isxfdjh;

    @DBField
    private String pausestatus;

    @DBField
    private String jcqgyjz;

    @DBField
    private String jcqwd;

    @DBField
    private String jcqyl;

    @DBField
    private String jchgyjz;

    @DBField
    private String jchwd;

    @DBField
    private String jchyl;

    @DBField
    private String ydkrjz;

    @DBField
    private String zwwxw;

    @DBField
    private String xftd;

    private List<SxtBean.CamerasBean> cameras;

    private List<String> bitmapList;

    public List<String> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<String> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<SxtBean.CamerasBean> getCameras() {
        return cameras;
    }

    public void setCameras(List<SxtBean.CamerasBean> cameras) {
        this.cameras = cameras;
    }

    public String getYdkrjz() {
        return ydkrjz;
    }

    public void setYdkrjz(String ydkrjz) {
        this.ydkrjz = ydkrjz;
    }

    public String getZwwxw() {
        return zwwxw;
    }

    public void setZwwxw(String zwwxw) {
        this.zwwxw = zwwxw;
    }

    public String getXftd() {
        return xftd;
    }

    public void setXftd(String xftd) {
        this.xftd = xftd;
    }

    public String getJcqgyjz() {
        return jcqgyjz;
    }

    public void setJcqgyjz(String jcqgyjz) {
        this.jcqgyjz = jcqgyjz;
    }

    public String getJcqwd() {
        return jcqwd;
    }

    public void setJcqwd(String jcqwd) {
        this.jcqwd = jcqwd;
    }

    public String getJcqyl() {
        return jcqyl;
    }

    public void setJcqyl(String jcqyl) {
        this.jcqyl = jcqyl;
    }

    public String getJchgyjz() {
        return jchgyjz;
    }

    public void setJchgyjz(String jchgyjz) {
        this.jchgyjz = jchgyjz;
    }

    public String getJchwd() {
        return jchwd;
    }

    public void setJchwd(String jchwd) {
        this.jchwd = jchwd;
    }

    public String getJchyl() {
        return jchyl;
    }

    public void setJchyl(String jchyl) {
        this.jchyl = jchyl;
    }

    public String getPausestatus() {
        return pausestatus;
    }

    public void setPausestatus(String pausestatus) {
        this.pausestatus = pausestatus;
    }

    public int getIsxfdjh() {
        return isxfdjh;
    }

    public void setIsxfdjh(int isxfdjh) {
        this.isxfdjh = isxfdjh;
    }

    public String getHctime() {
        return hctime;
    }

    public void setHctime(String hctime) {
        this.hctime = hctime;
    }

    public String getJctime() {
        return jctime;
    }

    public void setJctime(String jctime) {
        this.jctime = jctime;
    }

    public int getCjcsx() {
        return cjcsx;
    }

    public void setCjcsx(int cjcsx) {
        this.cjcsx = cjcsx;
    }

    //	private boolean flag;
//
//
//	public boolean isFlag() {
//		return flag;
//	}
//
//	public void setFlag(boolean flag) {
//		this.flag = flag;
//	}

    /**
     * @return the downloaded
     */
    public boolean isDownloaded() {
        return this.downloaded;
    }

    /**
     * @param downloaded the downloaded to set
     */
    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    /**
     * * 设置 作业票父级主键 该字段是：作业票父级主键 * * @param Integer * parent_id
     * <p>
     * public void setparent_Id(Integer parent_Id) { this.parent_Id = parent_Id;
     * }
     * <p>
     * <p>
     * * 获取 作业票父级主键 该字段是：作业票父级主键
     * <p>
     * public Integer getparent_Id() { return parent_Id; } yqcountdescdesc.
     *
     * @return the yqcountdescdesc
     * @pdOid 7ac33782-b2db-4b47-90b7-ed89a3cd7237
     */
    public String getYqcountdescdesc() {
        return yqcountdescdesc;
    }

    /**
     * yqcountdescdesc.
     *
     * @param yqcountdescdesc the yqcountdescdesc to set
     * @pdOid 904b5c0b-3fde-4fd9-a101-d5969fac8d69
     */
    public void setYqcountdescdesc(String yqcountdescdesc) {
        this.yqcountdescdesc = yqcountdescdesc;
    }

    /**
     * 设置 作业票类型 该字段是：作业票类型
     *
     * @param zyptype
     * @pdOid 02d44d6a-73ce-4364-ac01-3bb03b9f5db8
     */
    public void setZyptype(String zyptype) {
        this.zyptype = zyptype;
    }

    /**
     * 获取 作业票类型 该字段是：作业票类型
     *
     * @pdOid 61065d37-c096-4e3c-8724-a425078d7221
     */
    public String getZyptype() {
        return zyptype;
    }

    public String getZyptypedesc() {
        return zyptypedesc;
    }

    public void setZyptypedesc(String zyptypedesc) {
        this.zyptypedesc = zyptypedesc;
    }

    public int getNeedupload() {
        return needupload;
    }

    public void setNeedupload(int needupload) {
        this.needupload = needupload;
    }

    /**
     * 设置 作业类别 该字段是：作业类别
     *
     * @param zypclass
     * @pdOid c695bee3-e1fc-4f1d-b6fc-02ad7b0b00e2
     */
    public void setZypclass(String zypclass) {
        this.zypclass = zypclass;
    }

    /**
     * 获取 作业类别 该字段是：作业类别
     *
     * @pdOid f61df25d-a4db-4baf-a64d-54d3ff3667d4
     */
    public String getZypclass() {
        return zypclass;
    }

    /**
     * 设置 补票标记 该字段是：补票标记
     *
     * @param bpflag
     * @pdOid 0738414b-d972-4a30-ace0-f1753e61f63a
     */
    public void setBpflag(Integer bpflag) {
        this.bpflag = bpflag;
    }

    /**
     * 获取 补票标记 该字段是：补票标记
     *
     * @pdOid 90f4d534-0a6f-4d5e-902f-2e2f9b266a08
     */
    public Integer getBpflag() {
        return bpflag;
    }

    /**
     * 设置 申请单号 该字段是：申请单号
     *
     * @param zysqnum
     * @pdOid 7c793918-a2e6-4ae1-b13c-37c895eaf6b3
     */
    public void setZysqnum(String zysqnum) {
        this.zysqnum = zysqnum;
    }

    /**
     * 获取 申请单号 该字段是：申请单号
     *
     * @pdOid a316bf16-4125-454a-866e-808df6094c7d
     */
    public String getZysqnum() {
        return zysqnum;
    }

    /**
     * 设置 受影响相关方 该字段是：受影响相关方
     *
     * @param xgf
     * @pdOid a2270ae4-b15a-4fd6-b6f7-0c5cca86c8d0
     */
    public void setXgf(String xgf) {
        this.xgf = xgf;
    }

    /**
     * 获取 受影响相关方 该字段是：受影响相关方
     *
     * @pdOid 1e0bcb99-d382-4ab8-bfcc-482c9fb89df0
     */
    public String getXgf() {
        return xgf;
    }

    /**
     * 设置 是否附安全工作方案 该字段是：是否附安全工作方案
     *
     * @param isfaqgzfa
     * @pdOid d9256165-ac3a-4130-af89-7e3ef132e4e2
     */
    public void setIsfaqgzfa(Integer isfaqgzfa) {
        this.isfaqgzfa = isfaqgzfa;
    }

    /**
     * 获取 是否附安全工作方案 该字段是：是否附安全工作方案
     *
     * @pdOid 85d8e9cb-b404-43ae-bf87-5245783173da
     */
    public Integer getIsfaqgzfa() {
        return isfaqgzfa;
    }

    /**
     * 设置 是否附图纸 该字段是：是否附图纸
     *
     * @param isftz
     * @pdOid 38b9d357-bf9b-41c7-baeb-e6e5bdda95fa
     */
    public void setIsftz(Integer isftz) {
        this.isftz = isftz;
    }

    /**
     * 获取 是否附图纸 该字段是：是否附图纸
     *
     * @pdOid 5963cfcb-e7f2-49b7-a1a9-c794d525ef65
     */
    public Integer getIsftz() {
        return isftz;
    }

    /**
     * 设置 图纸说明 该字段是：图纸说明
     *
     * @param tzsm
     * @pdOid dd3362d0-adf7-4e85-bac4-906333e185cb
     */
    public void setTzsm(String tzsm) {
        this.tzsm = tzsm;
    }

    /**
     * 获取 图纸说明 该字段是：图纸说明
     *
     * @pdOid dd001fdd-917a-479e-a219-d833a52a595a
     */
    public String getTzsm() {
        return tzsm;
    }

    /**
     * 设置 其他附件（危害识别等） 该字段是：其他附件（危害识别等）
     *
     * @param qtfj
     * @pdOid 8d74f94e-388f-4e91-b18a-80813ae027d8
     */
    public void setQtfj(String qtfj) {
        this.qtfj = qtfj;
    }

    /**
     * 获取 其他附件（危害识别等） 该字段是：其他附件（危害识别等）
     *
     * @pdOid 6677a333-16bd-49b8-b579-a1dd2ced2f90
     */
    public String getQtfj() {
        return qtfj;
    }

    /**
     * 设置 作业性质 该字段是：作业性质
     *
     * @param zyxz
     * @pdOid 9f264638-4e6b-4bf2-8279-1991a3935c32
     */
    public void setZyxz(String zyxz) {
        this.zyxz = zyxz;
    }

    /**
     * 获取 作业性质 该字段是：作业性质
     *
     * @pdOid 108ae302-af1e-4579-ade0-158a7097b09e
     */
    public String getZyxz() {
        return zyxz;
    }

    /**
     * 设置 预约单号 该字段是：预约单号
     *
     * @param zyyynum
     * @pdOid df6c5c9d-100a-456b-ba2d-72cf73ec3eeb
     */
    public void setZyyynum(String zyyynum) {
        this.zyyynum = zyyynum;
    }

    /**
     * 获取 预约单号 该字段是：预约单号
     *
     * @pdOid 56d3677f-6c94-44b5-87e4-9dc269ba7f4f
     */
    public String getZyyynum() {
        return zyyynum;
    }

    /**
     * 设置 查询选择器 该字段是：查询选择器
     *
     * @param sddept
     * @pdOid b713929c-b7c4-4c6a-9c40-a1a68e2d4bde
     */
    public void setSddept(String sddept) {
        this.sddept = sddept;
    }

    /**
     * 获取 查询选择器 该字段是：查询选择器
     *
     * @pdOid dc7358d5-0042-4620-8d30-9798fcb31ddc
     */
    public String getSddept() {
        return sddept;
    }

    /**
     * 设置 日期选择器 该字段是：日期选择器
     *
     * @param zydate
     * @pdOid cf91b328-e5fc-4e43-82f2-c21f794dd0a9
     */
    public void setZydate(String zydate) {
        this.zydate = zydate;
    }

    /**
     * 获取 日期选择器 该字段是：日期选择器
     *
     * @pdOid 223a5883-2210-4f50-8910-a3e86a1d5f17
     */
    public String getZydate() {
        return zydate;
    }

    /**
     * 设置 文本框 该字段是：文本框
     *
     * @param zyname
     * @pdOid 09b4cb91-f784-448c-8128-822002a30ec8
     */
    public void setZyname(String zyname) {
        this.zyname = zyname;
    }

    /**
     * 获取 文本框 该字段是：文本框
     *
     * @pdOid 4162dff7-2d4a-4da4-99dd-3b41b551daf4
     */
    public String getZyname() {
        return zyname;
    }

    /**
     * 设置 文本域 该字段是：文本域
     *
     * @param zysite
     * @pdOid bf1cba5a-f47a-4dc8-83a1-8d0675a90916
     */
    public void setZysite(String zysite) {
        this.zysite = zysite;
    }

    /**
     * 获取 文本域 该字段是：文本域
     *
     * @pdOid c56120a5-9105-4026-b40d-cc6f82661201
     */
    public String getZysite() {
        return zysite;
    }

    /**
     * 设置 作业内容 该字段是：作业内容
     *
     * @param zycontent
     * @pdOid b9fbee5f-1d49-483f-a1d6-73880796c3fa
     */
    public void setZycontent(String zycontent) {
        this.zycontent = zycontent;
    }

    /**
     * 获取 作业内容 该字段是：作业内容
     *
     * @pdOid 8ae52911-8050-435f-aa84-67f319d596c8
     */
    public String getZycontent() {
        return zycontent;
    }

    /**
     * 设置 是否能量隔离 该字段是：是否能量隔离
     *
     * @param isnlgl
     * @pdOid d446d993-8e1a-48bb-9e87-2d9e87f0870e
     */
    public void setIsnlgl(Integer isnlgl) {
        this.isnlgl = isnlgl;
    }

    /**
     * 获取 是否能量隔离 该字段是：是否能量隔离
     *
     * @pdOid e0774f6f-e9b7-4bae-bead-9688e7fe11aa
     */
    public Integer getIsnlgl() {
        return isnlgl;
    }

    /**
     * 设置 是否气体检测 该字段是：是否气体检测
     *
     * @param isqtjc
     * @pdOid f847358e-296d-4ef4-8985-40ee1e307b72
     */
    public void setIsqtjc(Integer isqtjc) {
        this.isqtjc = isqtjc;
    }

    /**
     * 获取 是否气体检测 该字段是：是否气体检测
     *
     * @pdOid 8ae4b645-51f6-4122-8a59-6d5305f6bc23
     */
    public Integer getIsqtjc() {
        return isqtjc;
    }

    /**
     * 设置 检测位置 该字段是：检测位置
     *
     * @param jclocation
     * @pdOid c1e8f696-73b1-4358-ab42-a50235917857
     */
    public void setJclocation(String jclocation) {
        this.jclocation = jclocation;
    }

    /**
     * 获取 检测位置 该字段是：检测位置
     *
     * @pdOid e9ad0173-56b3-4006-9127-7eea5adad531
     */
    public String getJclocation() {
        return jclocation;
    }

    /**
     * 设置 检测时效 该字段是：检测时效
     *
     * @param jcsx
     * @pdOid 0f73bca2-d251-45d9-ba59-450d9f95a3c2
     */
    public void setJcsx(String jcsx) {
        this.jcsx = jcsx;
    }

    /**
     * 获取 检测时效 该字段是：检测时效
     *
     * @pdOid 2397ecc0-b1f0-40e0-862e-f6eec048c7ab
     */
    public String getJcsx() {
        return jcsx;
    }

    /**
     * 设置 监护人名称 该字段是：监护人名称
     *
     * @param jhrname
     * @pdOid c3199fda-2689-468a-9cdf-0424569bc0e8
     */
    public void setJhrname(String jhrname) {
        this.jhrname = jhrname;
    }

    /**
     * 获取 监护人名称 该字段是：监护人名称
     *
     * @pdOid b56de0df-5efd-4abf-b411-37c254cbca52
     */
    public String getJhrname() {
        return jhrname;
    }

    /**
     * 设置 预计作业人员数量 该字段是：预计作业人员数量
     *
     * @param yjzyrysl
     * @pdOid 4d657c3b-6285-421f-a951-6062413f2ef3
     */
    public void setYjzyrysl(Integer yjzyrysl) {
        this.yjzyrysl = yjzyrysl;
    }

    /**
     * 获取 预计作业人员数量 该字段是：预计作业人员数量
     *
     * @pdOid 6711fd59-ee19-4cee-9614-f50c2d4e7243
     */
    public Integer getYjzyrysl() {
        return yjzyrysl;
    }

    /**
     * 设置 预计特种作业人员数量 该字段是：预计特种作业人员数量
     *
     * @param yjtzzyrysl
     * @pdOid 5f0ce7ef-858a-41e0-8dfd-f8bbbe0bb16d
     */
    public void setYjtzzyrysl(Integer yjtzzyrysl) {
        this.yjtzzyrysl = yjtzzyrysl;
    }

    /**
     * 获取 预计特种作业人员数量 该字段是：预计特种作业人员数量
     *
     * @pdOid 9a31b0db-c6b1-44cc-80ca-53fdf953c288
     */
    public Integer getYjtzzyrysl() {
        return yjtzzyrysl;
    }

    /**
     * 设置 作业开始时间 该字段是：作业开始时间
     *
     * @param zystarttime
     * @pdOid 49285723-8f53-4856-8e57-385d91ed02c2
     */
    public void setZystarttime(String zystarttime) {
        this.zystarttime = zystarttime;
    }

    /**
     * 获取 作业开始时间 该字段是：作业开始时间
     *
     * @pdOid cca134f7-3033-494d-8064-89f9f618c176
     */
    public String getZystarttime() {
        return zystarttime;
    }

    /**
     * 设置 作业结束时间 该字段是：作业结束时间
     *
     * @param zyendtime
     * @pdOid cc7f085e-e9b9-483b-81d9-52d0e37520ce
     */
    public void setZyendtime(String zyendtime) {
        this.zyendtime = zyendtime;
    }

    /**
     * 获取 作业结束时间 该字段是：作业结束时间
     *
     * @pdOid 3ebb5d7d-614f-4c04-86e2-df3e14d89296
     */
    public String getZyendtime() {
        return zyendtime;
    }

    /**
     * 设置 作业位置 该字段是：作业位置
     *
     * @param zylocation
     * @pdOid 3618cdd6-2578-45cd-81d9-9856850dd139
     */
    public void setZylocation(String zylocation) {
        this.zylocation = zylocation;
    }

    /**
     * 获取 作业位置 该字段是：作业位置
     *
     * @pdOid c00d58b9-fd05-4847-91bf-c8218228ab83
     */
    public String getZylocation() {
        return zylocation;
    }

    /**
     * 设置 是否有相关方 该字段是：是否有相关方
     *
     * @param isyxgf
     * @pdOid 36c52bad-2279-4eac-be8f-3893c490ebd7
     */
    public void setIsyxgf(Integer isyxgf) {
        this.isyxgf = isyxgf;
    }

    /**
     * 获取 是否有相关方 该字段是：是否有相关方
     *
     * @pdOid c7517a22-1903-46ce-93f1-89192178d474
     */
    public Integer getIsyxgf() {
        return isyxgf;
    }

    /**
     * 设置 是否可以延期 该字段是：是否可以延期
     *
     * @param iskyyq
     * @pdOid c855f9fe-bca7-4e60-a9b5-b71157dce2ec
     */
    public void setIskyyq(String iskyyq) {
        this.iskyyq = iskyyq;
    }

    /**
     * 获取 是否可以延期 该字段是：是否可以延期
     *
     * @pdOid 3698902e-b876-4c1f-ba4f-5603089cd31b
     */
    public String getIskyyq() {
        return iskyyq;
    }

    /**
     * 设置 延期次数 该字段是：延期次数
     *
     * @param yqcount
     * @pdOid f23aa59e-327a-42a9-aa3e-8169b2e6125a
     */
    public void setYqcount(Integer yqcount) {
        this.yqcount = yqcount;
    }

    /**
     * 获取 延期次数 该字段是：延期次数
     *
     * @pdOid d75b297f-7440-436a-a804-e0b7b867fb8a
     */
    public Integer getYqcount() {
        return yqcount;
    }

    /**
     * 设置 危害识别 该字段是：危害识别
     *
     * @param whshib
     * @pdOid 89f98d15-7542-4294-9580-e0bf6354de98
     */
    public void setWhshib(String whshib) {
        this.whshib = whshib;
    }

    /**
     * 获取 危害识别 该字段是：危害识别
     *
     * @pdOid 502d728c-51c2-4ad3-b512-3306030a5c66
     */
    public String getWhshib() {
        return whshib;
    }

    /**
     * 设置 其他危害 该字段是：其他危害
     *
     * @param qthazard
     * @pdOid 195187a7-2c19-4a72-90e2-b426a5c6ea1e
     */
    public void setQthazard(String qthazard) {
        this.qthazard = qthazard;
    }

    /**
     * 获取 其他危害 该字段是：其他危害
     *
     * @pdOid 74f0a522-884f-47cc-b54f-99937542ac26
     */
    public String getQthazard() {
        return qthazard;
    }

    /**
     * 设置 工作前安全措施 该字段是：工作前安全措施
     *
     * @param gzqaqcs
     * @pdOid e5bf89c0-c270-494c-b68d-4f78949b1a27
     */
    public void setGzqaqcs(String gzqaqcs) {
        this.gzqaqcs = gzqaqcs;
    }

    /**
     * 获取 工作前安全措施 该字段是：工作前安全措施
     *
     * @pdOid 19e29938-8daa-43bf-a0b1-bc0d2e9f9ea3
     */
    public String getGzqaqcs() {
        return gzqaqcs;
    }

    /**
     * 设置 其他措施 该字段是：其他措施
     *
     * @param qtcs
     * @pdOid da3cd65e-173d-4a38-822d-f0ff250c2f56
     */
    public void setQtcs(String qtcs) {
        this.qtcs = qtcs;
    }

    /**
     * 获取 其他措施 该字段是：其他措施
     *
     * @pdOid ac0df603-08c5-4582-bae0-233255059c34
     */
    public String getQtcs() {
        return qtcs;
    }

    /**
     * 设置 个人防护装备 该字段是：个人防护装备
     *
     * @param grfhzb
     * @pdOid df68d4d1-08ea-4c7c-9c8e-caada96b5548
     */
    public void setGrfhzb(String grfhzb) {
        this.grfhzb = grfhzb;
    }

    /**
     * 获取 个人防护装备 该字段是：个人防护装备
     *
     * @pdOid 6c622c57-8e4f-4f30-83df-0934faa808df
     */
    public String getGrfhzb() {
        return grfhzb;
    }

    /**
     * 设置 补充防范措施 该字段是：补充防范措施
     *
     * @param bcffcs
     * @pdOid 978666f8-da83-4e92-aa8a-9eb5d69b2aa1
     */
    public void setBcffcs(String bcffcs) {
        this.bcffcs = bcffcs;
    }

    /**
     * 获取 补充防范措施 该字段是：补充防范措施
     *
     * @pdOid 8d618a55-ab2d-4c33-b438-6c40c3fdc72d
     */
    public String getBcffcs() {
        return bcffcs;
    }

    /**
     * 设置 措施确认人 该字段是：措施确认人
     *
     * @param csqrperson
     * @pdOid e5aa561d-9cd0-4617-a226-344be75e14ee
     */
    public void setCsqrperson(String csqrperson) {
        this.csqrperson = csqrperson;
    }

    /**
     * 获取 措施确认人 该字段是：措施确认人
     *
     * @pdOid 57d79998-ba06-43a3-8065-2c073490de5a
     */
    public String getCsqrperson() {
        return csqrperson;
    }

    /**
     * 设置 作业申请人 该字段是：作业申请人
     *
     * @param zysqperson
     * @pdOid de53743c-b740-49a0-a84d-c26be60eb0b5
     */
    public void setZysqperson(String zysqperson) {
        this.zysqperson = zysqperson;
    }

    /**
     * 获取 作业申请人 该字段是：作业申请人
     *
     * @pdOid 33efcd27-cd77-4e84-b280-ada54ac56948
     */
    public String getZysqperson() {
        return zysqperson;
    }

    /**
     * 设置 作业批准人 该字段是：作业批准人
     *
     * @param zypzperson
     * @pdOid e02a6f32-307a-448d-a162-b79bde43dd97
     */
    public void setZypzperson(String zypzperson) {
        this.zypzperson = zypzperson;
    }

    /**
     * 获取 作业批准人 该字段是：作业批准人
     *
     * @pdOid 1ffa0ff5-748e-4bb3-b047-52d09735e847
     */
    public String getZypzperson() {
        return zypzperson;
    }

    /**
     * 设置 取消原因 该字段是：取消原因
     *
     * @param reasonqx
     * @pdOid a4d26132-984f-477b-b83f-15921bc19f01
     */
    public void setReasonqx(String reasonqx) {
        this.reasonqx = reasonqx;
    }

    /**
     * 获取 取消原因 该字段是：取消原因
     *
     * @pdOid 4c05c8a6-df4f-454e-b78d-64521eda8c70
     */
    public String getReasonqx() {
        return reasonqx;
    }

    /**
     * 设置 取消提出人 该字段是：取消提出人
     *
     * @param tcpersonqx
     * @pdOid 4627622c-1a40-4334-a2ef-14eb6bdda624
     */
    public void setTcpersonqx(String tcpersonqx) {
        this.tcpersonqx = tcpersonqx;
    }

    /**
     * 获取 取消提出人 该字段是：取消提出人
     *
     * @pdOid 78c44625-67d4-4804-8802-885bf11406fa
     */
    public String getTcpersonqx() {
        return tcpersonqx;
    }

    /**
     * 设置 取消批准人 该字段是：取消批准人
     *
     * @param pzpersonqx
     * @pdOid 8ce6fe92-e01d-45cc-b607-65fbb9c513a8
     */
    public void setPzpersonqx(String pzpersonqx) {
        this.pzpersonqx = pzpersonqx;
    }

    /**
     * 获取 取消批准人 该字段是：取消批准人
     *
     * @pdOid 53606f27-53fb-4a46-bbd1-6628c8a913ba
     */
    public String getPzpersonqx() {
        return pzpersonqx;
    }

    /**
     * 设置 取消日期 该字段是：取消日期
     *
     * @param qxtime
     * @pdOid 42a13f90-2f65-41cc-839e-8e40d6572105
     */
    public void setQxtime(String qxtime) {
        this.qxtime = qxtime;
    }

    /**
     * 获取 取消日期 该字段是：取消日期
     *
     * @pdOid a3e64e1c-ce9e-4f8b-8291-6665bdb81e2c
     */
    public String getQxtime() {
        return qxtime;
    }

    /**
     * 设置 关闭方式 该字段是：关闭方式
     *
     * @param gbtype
     * @pdOid e85ae744-44f6-4d1d-a27a-c3a3266ed779
     */
    public void setGbtype(String gbtype) {
        this.gbtype = gbtype;
    }

    /**
     * 获取 关闭方式 该字段是：关闭方式
     *
     * @pdOid c604c5f2-9a9d-413c-83f6-db3889ec9030
     */
    public String getGbtype() {
        return gbtype;
    }

    /**
     * 设置 关闭说明 该字段是：关闭说明
     *
     * @param gbsm
     * @pdOid 7862e795-8068-4ecc-bb89-facc5f4b1e08
     */
    public void setGbsm(String gbsm) {
        this.gbsm = gbsm;
    }

    /**
     * 获取 关闭说明 该字段是：关闭说明
     *
     * @pdOid 0dd93080-47de-42de-9722-ef6b3726d9c8
     */
    public String getGbsm() {
        return gbsm;
    }

    /**
     * 设置 关闭申请人 该字段是：关闭申请人
     *
     * @param sqpersongb
     * @pdOid c0b0cfb2-acd2-4ec8-8269-c5f70e96179d
     */
    public void setSqpersongb(String sqpersongb) {
        this.sqpersongb = sqpersongb;
    }

    /**
     * 获取 关闭申请人 该字段是：关闭申请人
     *
     * @pdOid 636bc699-86e1-4735-bbbf-1855b05be1f2
     */
    public String getSqpersongb() {
        return sqpersongb;
    }

    /**
     * 设置 申请人审核时间 该字段是：申请人审核时间
     *
     * @param sqrshtime
     * @pdOid 0cf14f36-573f-4eeb-8585-a8c588004241
     */
    public void setSqrshtime(String sqrshtime) {
        this.sqrshtime = sqrshtime;
    }

    /**
     * 获取 申请人审核时间 该字段是：申请人审核时间
     *
     * @pdOid 46d32788-c6b1-4ee2-ade0-48051e744814
     */
    public String getSqrshtime() {
        return sqrshtime;
    }

    /**
     * 设置 关闭相关方 该字段是：关闭相关方
     *
     * @param xgfgb
     * @pdOid 10731166-6ef5-49aa-af32-676548e4434d
     */
    public void setXgfgb(String xgfgb) {
        this.xgfgb = xgfgb;
    }

    /**
     * 获取 关闭相关方 该字段是：关闭相关方
     *
     * @pdOid 86f73d3a-7a87-4537-a244-99d446903996
     */
    public String getXgfgb() {
        return xgfgb;
    }

    /**
     * 设置 相关方审核时间 该字段是：相关方审核时间
     *
     * @param xgfshtime
     * @pdOid 58871e07-5cc8-4f1d-897c-940a41154a1f
     */
    public void setXgfshtime(String xgfshtime) {
        this.xgfshtime = xgfshtime;
    }

    /**
     * 获取 相关方审核时间 该字段是：相关方审核时间
     *
     * @pdOid a2b9c0f3-5bb1-40ba-bf57-e6021c63fee1
     */
    public String getXgfshtime() {
        return xgfshtime;
    }

    /**
     * 设置 关闭批准人 该字段是：关闭批准人
     *
     * @param pzrgb
     * @pdOid 05bc4bd9-7018-42fb-8670-1296071a65e0
     */
    public void setPzrgb(String pzrgb) {
        this.pzrgb = pzrgb;
    }

    /**
     * 获取 关闭批准人 该字段是：关闭批准人
     *
     * @pdOid 0104b9b2-22bf-460b-b300-5452d95859c8
     */
    public String getPzrgb() {
        return pzrgb;
    }

    /**
     * 设置 审核时间 该字段是：审核时间
     *
     * @param pzrshtime
     * @pdOid 4de88885-a59b-45d6-8755-30b09e91eab4
     */
    public void setPzrshtime(String pzrshtime) {
        this.pzrshtime = pzrshtime;
    }

    /**
     * 获取 审核时间 该字段是：审核时间
     *
     * @pdOid dd32ca23-1653-4eb2-828c-138462245615
     */
    public String getPzrshtime() {
        return pzrshtime;
    }

    /**
     * 设置 状态(待确定) 该字段是：状态(待确定)
     *
     * @param status
     * @pdOid 18debae9-dd14-4fc8-83f2-f9e4947dd507
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取 状态(待确定) 该字段是：状态(待确定)
     *
     * @pdOid a397aeb7-369d-4442-a29d-e2db55d82a81
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 状态日期 该字段是：状态日期
     *
     * @param statusdate
     * @pdOid 997184f0-9c47-4c0f-9046-e1080ed916b7
     */
    public void setStatusdate(String statusdate) {
        this.statusdate = statusdate;
    }

    /**
     * 获取 状态日期 该字段是：状态日期
     *
     * @pdOid a25d20c3-52af-44a4-ab45-7deb8318b3b1
     */
    public String getStatusdate() {
        return statusdate;
    }

    /**
     * 设置 历史标记 该字段是：历史标记
     *
     * @param historyflag
     * @pdOid d9e6b806-10b1-423f-b776-206017082e04
     */
    public void setHistoryflag(String historyflag) {
        this.historyflag = historyflag;
    }

    /**
     * 获取 历史标记 该字段是：历史标记
     *
     * @pdOid f5243ce5-1d3c-451a-a935-2b2b649da181
     */
    public String getHistoryflag() {
        return historyflag;
    }

    /**
     * 设置 作业区域编码 该字段是：作业区域编码
     *
     * @param zyarea
     * @pdOid faf17184-f7cd-48dc-9e14-7793f1b9f00b
     */
    public void setZyarea(String zyarea) {
        this.zyarea = zyarea;
    }

    /**
     * 获取 作业区域编码 该字段是：作业区域编码
     *
     * @pdOid e2842295-2730-4e0e-a75f-260630786722
     */
    public String getZyarea() {
        return zyarea;
    }

    /**
     * 设置 作业级别 该字段是：作业级别
     *
     * @param zylevel
     * @pdOid 30238fb3-17dd-4f65-a193-30efbf11fc82
     */
    public void setZylevel(String zylevel) {
        this.zylevel = zylevel;
    }

    /**
     * 获取 作业级别 该字段是：作业级别
     *
     * @pdOid cab9b01a-d029-4c7e-972c-8ef0d1d3d92d
     */
    public String getZylevel() {
        return zylevel;
    }

    /**
     * 设置 作业单位 该字段是：作业单位
     *
     * @param zydept
     * @pdOid d80b6a4c-cdba-4aeb-8e62-643cddd21b13
     */
    public void setZydept(String zydept) {
        this.zydept = zydept;
    }

    /**
     * 获取 作业单位 该字段是：作业单位
     *
     * @pdOid d8d93e61-b35f-4268-a8ad-30b1b4ae336d
     */
    public String getZydept() {
        return zydept;
    }

    /**
     * 设置 能量隔离??号 该字段是：能量隔离??号
     *
     * @param nlgldnum
     * @pdOid 8ce88de3-207e-4757-8b8c-f13273e6402c
     */
    public void setNlgldnum(String nlgldnum) {
        this.nlgldnum = nlgldnum;
    }

    /**
     * 获取 能量隔离??号 该字段是：能量隔离??号
     *
     * @pdOid 2f75b075-89f0-4978-a553-ddfffca71c46
     */
    public String getNlgldnum() {
        return nlgldnum;
    }

    /**
     * 设置 作业批准时间 该字段是：作业批准时间
     *
     * @param zypztime
     * @pdOid 9f8b4e2d-05c6-4716-b9f8-2143347c4b07
     */
    public void setZypztime(String zypztime) {
        this.zypztime = zypztime;
    }

    /**
     * 获取 作业批准时间 该字段是：作业批准时间
     *
     * @pdOid dd0b2927-e22e-424c-bff7-a68efe3a5585
     */
    public String getZypztime() {
        return zypztime;
    }

    /**
     * 设置 属地单位负责人（措施） 该字段是：属地单位负责人（措施）
     *
     * @param sddwfzperson
     * @pdOid 2acdcb3a-2095-4988-9252-6adc267de5b6
     */
    public void setSddwfzperson(String sddwfzperson) {
        this.sddwfzperson = sddwfzperson;
    }

    /**
     * 获取 属地单位负责人（措施） 该字段是：属地单位负责人（措施）
     *
     * @pdOid a5ed6a8a-8fcb-4c0f-99b9-6ceee6fdecfa
     */
    public String getSddwfzperson() {
        return sddwfzperson;
    }

    /**
     * 设置 作业单位现场负责人（措施） 该字段是：作业单位现场负责人（措施）
     *
     * @param zydwxcfzperson
     * @pdOid 97a098b3-9c09-405c-9d06-dfa9e988f238
     */
    public void setZydwxcfzperson(String zydwxcfzperson) {
        this.zydwxcfzperson = zydwxcfzperson;
    }

    /**
     * 获取 作业单位现场负责人（措施） 该字段是：作业单位现场负责人（措施）
     *
     * @pdOid e61eb21f-d178-4a97-a15c-db6ea8246ea4
     */
    public String getZydwxcfzperson() {
        return zydwxcfzperson;
    }

    /**
     * 设置 属地单位负责人签字时间 该字段是：属地单位负责人签字时间
     *
     * @param sddwfzpersontime
     * @pdOid 229f733d-95fc-4868-8bbe-d42e4c2abe00
     */
    public void setSddwfzpersontime(String sddwfzpersontime) {
        this.sddwfzpersontime = sddwfzpersontime;
    }

    /**
     * 获取 属地单位负责人签字时间 该字段是：属地单位负责人签字时间
     *
     * @pdOid f286b8eb-9f9c-4e91-91e7-436646ccfa8a
     */
    public String getSddwfzpersontime() {
        return sddwfzpersontime;
    }

    /**
     * 设置 作业单位现场负责人签字时间 该字段是：作业单位现场负责人签字时间
     *
     * @param zydwxcfzpersontime
     * @pdOid fe43dbe9-696e-461f-97ba-23d00077fc0e
     */
    public void setZydwxcfzpersontime(String zydwxcfzpersontime) {
        this.zydwxcfzpersontime = zydwxcfzpersontime;
    }

    /**
     * 获取 作业单位现场负责人签字时间 该字段是：作业单位现场负责人签字时间
     *
     * @pdOid b44a88fe-e26e-44ff-857f-0f3c325ff020
     */
    public String getZydwxcfzpersontime() {
        return zydwxcfzpersontime;
    }

    /**
     * 设置 拆线人 该字段是：拆线人
     *
     * @param cxperson
     * @pdOid a98592fb-2b33-41b4-b096-4362b298ddab
     */
    public void setCxperson(String cxperson) {
        this.cxperson = cxperson;
    }

    /**
     * 获取 拆线人 该字段是：拆线人
     *
     * @pdOid 17cc16bf-1dba-4dce-9a31-f13ba24b6f56
     */
    public String getCxperson() {
        return cxperson;
    }

    /**
     * 设置 拆线人签字时间 该字段是：拆线人签字时间
     *
     * @param cxtime
     * @pdOid a173ca24-e363-41e0-ab3c-bf9ef1930888
     */
    public void setCxtime(String cxtime) {
        this.cxtime = cxtime;
    }

    /**
     * 获取 拆线人签字时间 该字段是：拆线人签字时间
     *
     * @pdOid 9f33fe98-fd62-4f7a-a8af-1ae9e86b28fa
     */
    public String getCxtime() {
        return cxtime;
    }

    /**
     * 设置 是否承包商 该字段是：是否承包商
     *
     * @param iscbs
     * @pdOid 8ee9d99b-ad26-43d6-9233-1781b4915aec
     */
    public void setIscbs(Integer iscbs) {
        this.iscbs = iscbs;
    }

    /**
     * 获取 是否承包商 该字段是：是否承包商
     *
     * @pdOid 42fac294-d34c-46e3-85ba-588cb38ad63b
     */
    public Integer getIscbs() {
        return iscbs;
    }

    /**
     * 设置 已延期次数 该字段是：已延期次数
     *
     * @param yyqcount
     * @pdOid f1338a3d-a548-4546-9843-745cfbb27040
     */
    public void setYyqcount(Integer yyqcount) {
        this.yyqcount = yyqcount;
    }

    /**
     * 获取 已延期次数 该字段是：已延期次数
     *
     * @pdOid 30792eeb-6479-4f84-b9a2-a1fb3ac4ad47
     */
    public Integer getYyqcount() {
        return yyqcount;
    }

    /**
     * 设置 取消提出时间 该字段是：取消提出时间
     *
     * @param tcpersonqxtime
     * @pdOid 6e35ca35-6e90-4c5f-8821-b02a81614fe1
     */
    public void setTcpersonqxtime(String tcpersonqxtime) {
        this.tcpersonqxtime = tcpersonqxtime;
    }

    /**
     * 获取 取消提出时间 该字段是：取消提出时间
     *
     * @pdOid 4b7c6bfa-a002-4d0b-98bb-3590b4df6838
     */
    public String getTcpersonqxtime() {
        return tcpersonqxtime;
    }

    /**
     * 设置 作业作废确认人 该字段是：作业作废确认人
     *
     * @param zfperson
     * @pdOid 20bc19d2-0c59-48d4-afb1-cc25b91bd30b
     */
    public void setZfperson(String zfperson) {
        this.zfperson = zfperson;
    }

    /**
     * 获取 作业作废确认人 该字段是：作业作废确认人
     *
     * @pdOid cb202f4e-a6b6-4406-9716-ff3af4fa3445
     */
    public String getZfperson() {
        return zfperson;
    }

    /**
     * 设置 作业作废确认人时间 该字段是：作业作废确认人时间
     *
     * @param zfpersontime
     * @pdOid 6b3a9dc2-a5c9-46ac-be01-bfca28b9ad64
     */
    public void setZfpersontime(String zfpersontime) {
        this.zfpersontime = zfpersontime;
    }

    /**
     * 获取 作业作废确认人时间 该字段是：作业作废确认人时间
     *
     * @pdOid 3044392c-0278-423b-9f70-f4c40a510825
     */
    public String getZfpersontime() {
        return zfpersontime;
    }

    /**
     * 设置 作业作废原因 该字段是：作业作废原因
     *
     * @param zfcause
     * @pdOid dd91e24c-8fe2-4ff2-8625-72dc345dac75
     */
    public void setZfcause(String zfcause) {
        this.zfcause = zfcause;
    }

    /**
     * 获取 作业作废原因 该字段是：作业作废原因
     *
     * @pdOid 5bc1a5d5-adcb-482b-ad2b-b973dd690204
     */
    public String getZfcause() {
        return zfcause;
    }

    /**
     * 设置 实际结束时间 该字段是：实际结束时间
     *
     * @param sjendtime
     * @pdOid abe8bcc4-ac06-4b5a-94fa-05a2bf5ca4a8
     */
    public void setSjendtime(String sjendtime) {
        this.sjendtime = sjendtime;
    }

    /**
     * 获取 实际结束时间 该字段是：实际结束时间
     *
     * @pdOid 73977d7c-fa69-4af7-b56c-7fc42f62bf29
     */
    public String getSjendtime() {
        return sjendtime;
    }

    /**
     * 设置 实际开始时间 该字段是：实际开始时间
     *
     * @param sjstarttime
     * @pdOid b97edf6e-177c-4450-8c31-f7d63caac5cd
     */
    public void setSjstarttime(String sjstarttime) {
        this.sjstarttime = sjstarttime;
    }

    /**
     * 获取 实际开始时间 该字段是：实际开始时间
     *
     * @pdOid a2714fbc-baf5-4fdf-8e2f-5d13f667b7ea
     */
    public String getSjstarttime() {
        return sjstarttime;
    }

    /**
     * 设置 个人防护装备措施 该字段是：个人防护装备措施
     *
     * @param grfhequipment
     * @pdOid 5ddae899-c8b4-427c-8351-9baf1ac7eabb
     */
    public void setGrfhequipment(String grfhequipment) {
        this.grfhequipment = grfhequipment;
    }

    /**
     * 获取 个人防护装备措施 该字段是：个人防护装备措施
     *
     * @pdOid 90911531-b88a-45bd-bdfe-45a2aa43d33a
     */
    public String getGrfhequipment() {
        return grfhequipment;
    }

    /**
     * 设置 设备交出后状态 该字段是：设备交出后状态
     *
     * @param afterissuit
     * @pdOid 969a0f82-33e4-4906-b8a4-fcbc7eed8822
     */
    public void setAfterissuit(Integer afterissuit) {
        this.afterissuit = afterissuit;
    }

    /**
     * 获取 设备交出后状态 该字段是：设备交出后状态
     *
     * @pdOid 188623e6-d3a7-4bce-be20-e948e04c9b26
     */
    public Integer getAfterissuit() {
        return afterissuit;
    }

    /**
     * 设置 设备交出前状态 该字段是：设备交出前状态
     *
     * @param beforeissuit
     * @pdOid d26fe13f-9006-46a7-8501-a1f2a85af259
     */
    public void setBeforeissuit(Integer beforeissuit) {
        this.beforeissuit = beforeissuit;
    }

    /**
     * 获取 设备交出前状态 该字段是：设备交出前状态
     *
     * @pdOid 9d4d1fd4-0619-4f3b-8379-e38a8bba345f
     */
    public Integer getBeforeissuit() {
        return beforeissuit;
    }

    /**
     * 设置 工作是否合格 该字段是：工作是否合格
     *
     * @param isqualify
     * @pdOid f3b62dd5-27c1-4aae-be78-6063f260d442
     */
    public void setIsqualify(String isqualify) {
        this.isqualify = isqualify;
    }

    /**
     * 获取 工作是否合格 该字段是：工作是否合格
     *
     * @pdOid d4841f01-1fd8-442b-b836-da21956c93cd
     */
    public String getIsqualify() {
        return isqualify;
    }

    /**
     * 设置 验收结果 该字段是：验收结果
     *
     * @param checkresult
     * @pdOid 46012e35-1491-4cc8-a11e-155e5d51d794
     */
    public void setCheckresult(String checkresult) {
        this.checkresult = checkresult;
    }

    /**
     * 获取 验收结果 该字段是：验收结果
     *
     * @pdOid 15e7447b-6a29-4f72-a5bc-a02aeda5e4d6
     */
    public String getCheckresult() {
        return checkresult;
    }

    /**
     * 设置 是否已验收 该字段是：是否已验收
     *
     * @param ischecked
     * @pdOid b0ed4676-3755-4487-9f21-d1087d2d3b2b
     */
    public void setIschecked(Integer ischecked) {
        this.ischecked = ischecked;
    }

    /**
     * 获取 是否已验收 该字段是：是否已验收
     *
     * @pdOid 0b215642-5610-41c1-b9b1-afac9cfdd742
     */
    public Integer getIschecked() {
        return ischecked;
    }

    /**
     * 设置 监护方式 该字段是：监护方式
     *
     * @param custodytype
     * @pdOid 0c00f0ff-4eb6-4b4e-be86-d8a2c9eb578e
     */
    public void setCustodytype(String custodytype) {
        this.custodytype = custodytype;
    }

    /**
     * 获取 监护方式 该字段是：监护方式
     *
     * @pdOid 88384cb7-e2e2-490f-be20-9ca4f6f79b5f
     */
    public String getCustodytype() {
        return custodytype;
    }

    /**
     * 设置 是否强制关联 该字段是：是否强制关联
     *
     * @param isrelation
     * @pdOid 6405bfa9-1be1-4ed7-b7d2-6d8f05714b72
     */
    public void setIsrelation(Integer isrelation) {
        this.isrelation = isrelation;
    }

    /**
     * 获取 是否强制关联 该字段是：是否强制关联
     *
     * @pdOid ad80f777-862d-49a4-85ce-4b74d787affc
     */
    public Integer getIsrelation() {
        return isrelation;
    }

    /**
     * 设置 a1 该字段是：a1
     *
     * @param xgfqx
     * @pdOid 5a94f582-dd17-4701-ad4d-f53456f65dbd
     */
    public void setXgfqx(String xgfqx) {
        this.xgfqx = xgfqx;
    }

    /**
     * 获取 a1 该字段是：a1
     *
     * @pdOid 98c6e920-a606-4f0c-8a50-5771293327f8
     */
    public String getXgfqx() {
        return xgfqx;
    }

    /**
     * 设置 a2 该字段是：a2
     *
     * @param zyjhperson
     * @pdOid d3767110-4a01-4a43-a6c6-5ca78c609054
     */
    public void setZyjhperson(String zyjhperson) {
        this.zyjhperson = zyjhperson;
    }

    /**
     * 获取 a2 该字段是：a2
     *
     * @pdOid 0114f3e6-8f0c-495f-a246-380bbca523d8
     */
    public String getZyjhperson() {
        return zyjhperson;
    }

    /**
     * 设置 a3 该字段是：a3
     *
     * @param spstatus
     * @pdOid 1013a89b-f33b-473a-9239-2e601f25a889
     */
    public void setSpstatus(String spstatus) {
        this.spstatus = spstatus;
    }

    /**
     * 获取 a3 该字段是：a3
     *
     * @pdOid aa4c5324-3923-41d9-a805-1cdc6a4fcf54
     */
    public String getSpstatus() {
        return spstatus;
    }

    /**
     * 设置 a4 该字段是：a4
     *
     * @param pdasignpoint
     * @pdOid e30cee49-5eed-41df-87d3-9f0b8bb9ea58
     */
    public void setPdasignpoint(Integer pdasignpoint) {
        this.pdasignpoint = pdasignpoint;
    }

    /**
     * 获取 a4 该字段是：a4
     *
     * @pdOid 7db2306f-9a35-4a77-bc24-4764212ccb9d
     */
    public Integer getPdasignpoint() {
        return pdasignpoint;
    }

    /**
     * 设置 a5 该字段是：a5
     *
     * @param gyfxxjcs
     * @pdOid 05095b91-266a-4d3f-87a1-79f8639584e5
     */
    public void setGyfxxjcs(Integer gyfxxjcs) {
        this.gyfxxjcs = gyfxxjcs;
    }

    /**
     * 获取 a5 该字段是：a5
     *
     * @pdOid a7e2457f-354e-4968-a08e-e415759a190b
     */
    public Integer getGyfxxjcs() {
        return gyfxxjcs;
    }

    /**
     * 设置 a6 该字段是：a6
     *
     * @param zyfxxjcs
     * @pdOid a69334a5-0c7f-4e0f-8cf6-1ea668cb3258
     */
    public void setZyfxxjcs(Integer zyfxxjcs) {
        this.zyfxxjcs = zyfxxjcs;
    }

    /**
     * 获取 a6 该字段是：a6
     *
     * @pdOid 3e583921-4fff-4f80-bab9-a7a6307eba51
     */
    public Integer getZyfxxjcs() {
        return zyfxxjcs;
    }

    /**
     * 设置 a7 该字段是：a7
     *
     * @param vpdamsgcontent
     * @pdOid 3d6f1ca9-0e49-4247-b699-4a39141f1801
     */
    public void setVpdamsgcontent(Integer vpdamsgcontent) {
        this.vpdamsgcontent = vpdamsgcontent;
    }

    /**
     * 获取 a7 该字段是：a7
     *
     * @pdOid a2ac0db2-5e0e-4b5d-a912-98631630ca6a
     */
    public Integer getVpdamsgcontent() {
        return vpdamsgcontent;
    }

    /**
     * 设置 a8 该字段是：a8
     *
     * @param issave
     * @pdOid eaec6c5f-1b54-4b70-842f-cf9d7ee52979
     */
    public void setIssave(String issave) {
        this.issave = issave;
    }

    /**
     * 获取 a8 该字段是：a8
     *
     * @pdOid b6842af6-4f59-4d1f-a223-75c66fff87b4
     */
    public String getIssave() {
        return issave;
    }

    /**
     * 设置 a9 该字段是：a9
     *
     * @param csnum
     * @pdOid e21d9d0f-085a-401b-b925-a2e0dbaf39ba
     */
    public void setCsnum(Integer csnum) {
        this.csnum = csnum;
    }

    /**
     * 获取 a9 该字段是：a9
     *
     * @pdOid 58db17cd-3d4f-4097-ad95-2be37a00bad3
     */
    public Integer getCsnum() {
        return csnum;
    }

    /**
     * 设置 a10 该字段是：a10
     *
     * @param cssavenum
     * @pdOid 764814e4-d5b5-4f08-babc-59464e4bd453
     */
    public void setCssavenum(String cssavenum) {
        this.cssavenum = cssavenum;
    }

    /**
     * 获取 a10 该字段是：a10
     *
     * @pdOid 5d748bd1-c495-4937-84a4-f2bb2510ac14
     */
    public String getCssavenum() {
        return cssavenum;
    }

    /**
     * 设置 a11 该字段是：a11
     *
     * @param cssavefied
     * @pdOid 829c5b9b-812c-4dd9-b4eb-8f4299939a29
     */
    public void setCssavefied(String cssavefied) {
        this.cssavefied = cssavefied;
    }

    /**
     * 获取 a11 该字段是：a11
     *
     * @pdOid 741ac5fd-8067-4298-9eb3-e7f42274cd4a
     */
    public String getCssavefied() {
        return cssavefied;
    }

    /**
     * 设置 a13 该字段是：a13
     *
     * @param functionpoint
     * @pdOid da9ef046-fd3e-4a40-994e-449a85b50b53
     */
    public void setFunctionpoint(String functionpoint) {
        this.functionpoint = functionpoint;
    }

    /**
     * 获取 a13 该字段是：a13
     *
     * @pdOid c0ff8167-73fe-4f54-80ed-4afc365f6f6c
     */
    public String getFunctionpoint() {
        return functionpoint;
    }

    /**
     * 设置 a14 该字段是：a14
     *
     * @param initlevel
     * @pdOid f8f673d8-775a-44d4-91ee-fa6e162927dd
     */
    public void setInitlevel(String initlevel) {
        this.initlevel = initlevel;
    }

    /**
     * 获取 a14 该字段是：a14
     *
     * @pdOid 8a3031c8-f136-40ff-8f2b-b73e0b11dc04
     */
    public String getInitlevel() {
        return initlevel;
    }

    /**
     * 设置 a15 该字段是：a15
     *
     * @param dzwjname
     * @pdOid 80b3ee7c-d47e-46ec-9004-79faa04d377b
     */
    public void setDzwjname(String dzwjname) {
        this.dzwjname = dzwjname;
    }

    /**
     * 获取 a15 该字段是：a15
     *
     * @pdOid e1721131-f5bb-4bca-8738-87aa22c2388d
     */
    public String getDzwjname() {
        return dzwjname;
    }

    /**
     * 设置 a16 该字段是：a16
     *
     * @param wjzl
     * @pdOid 15a67e04-b665-4d44-a658-a0a799163e95
     */
    public void setWjzl(String wjzl) {
        this.wjzl = wjzl;
    }

    /**
     * 获取 a16 该字段是：a16
     *
     * @pdOid 49f8c632-a845-4268-beb4-e509471eb641
     */
    public String getWjzl() {
        return wjzl;
    }

    /**
     * 设置 a17 该字段是：a17
     *
     * @param dzzl
     * @pdOid 03c88c6b-6881-4b29-b9b6-0038b7ca49a1
     */
    public void setDzzl(String dzzl) {
        this.dzzl = dzzl;
    }

    /**
     * 获取 a17 该字段是：a17
     *
     * @pdOid 9da283d6-d3b3-4ef8-8acf-78b4eb7aa5ef
     */
    public String getDzzl() {
        return dzzl;
    }

    /**
     * 设置 a18 该字段是：a18
     *
     * @param qzjedzh
     * @pdOid 510fc1fe-6412-4378-b1fd-2727b535c176
     */
    public void setQzjedzh(String qzjedzh) {
        this.qzjedzh = qzjedzh;
    }

    /**
     * 获取 a18 该字段是：a18
     *
     * @pdOid fa0e0127-c23f-45dd-94c7-524fe53c4e4d
     */
    public String getQzjedzh() {
        return qzjedzh;
    }

    /**
     * 设置 a19 该字段是：a19
     *
     * @param hightandenvironment
     * @pdOid 2e99763d-8d83-45d2-a30d-509015fbaf00
     */
    public void setHightandenvironment(String hightandenvironment) {
        this.hightandenvironment = hightandenvironment;
    }

    /**
     * 获取 a19 该字段是：a19
     *
     * @pdOid 928f24a8-5721-4ce7-945a-3ac7290ad398
     */
    public String getHightandenvironment() {
        return hightandenvironment;
    }

    /**
     * 设置 a20 该字段是：a20
     *
     * @param mbzyxz
     * @pdOid 4e21d964-578e-481c-9921-b12dcd7e1cbb
     */
    public void setMbzyxz(String mbzyxz) {
        this.mbzyxz = mbzyxz;
    }

    /**
     * 获取 a20 该字段是：a20
     *
     * @pdOid 234f0a72-6e0e-4f78-a8e8-8c3455b1cb55
     */
    public String getMbzyxz() {
        return mbzyxz;
    }

    /**
     * 设置 a21 该字段是：a21
     *
     * @param mbcode
     * @pdOid c33ebc9b-3b1c-4879-83ba-8aa86440679d
     */
    public void setMbcode(String mbcode) {
        this.mbcode = mbcode;
    }

    /**
     * 获取 a21 该字段是：a21
     *
     * @pdOid c1ab7d3a-224b-4fcd-a28c-0627d8204dbb
     */
    public String getMbcode() {
        return mbcode;
    }

    /**
     * 设置 a22 该字段是：a22
     *
     * @param mbtype
     * @pdOid 27996465-42cd-4f4e-b0cf-d0e84fb7977e
     */
    public void setMbtype(String mbtype) {
        this.mbtype = mbtype;
    }

    /**
     * 获取 a22 该字段是：a22
     *
     * @pdOid ff250ffd-8341-4ed3-9d70-e60749009a99
     */
    public String getMbtype() {
        return mbtype;
    }

    /**
     * 设置 a23 该字段是：a23
     *
     * @param mblocation
     * @pdOid 4d9a2997-ac27-4e7e-b323-a134a9ff1cca
     */
    public void setMblocation(String mblocation) {
        this.mblocation = mblocation;
    }

    /**
     * 获取 a23 该字段是：a23
     *
     * @pdOid c4c9bffa-3787-42e2-80b4-259795a6af7f
     */
    public String getMblocation() {
        return mblocation;
    }

    /**
     * 设置 a24 该字段是：a24
     *
     * @param gaskettype
     * @pdOid 8363be40-92f9-4448-af52-544b3b0bc831
     */
    public void setGaskettype(String gaskettype) {
        this.gaskettype = gaskettype;
    }

    /**
     * 获取 a24 该字段是：a24
     *
     * @pdOid 9bcc9f72-8b51-4258-be99-2c88cd771fe1
     */
    public String getGaskettype() {
        return gaskettype;
    }

    /**
     * 设置 a25 该字段是：a25
     *
     * @param jz
     * @pdOid 8b567b1c-42de-42bc-8442-49f96bc45b14
     */
    public void setJz(String jz) {
        this.jz = jz;
    }

    /**
     * 获取 a25 该字段是：a25
     *
     * @pdOid 7b511c67-3cda-4bda-86bd-ccaa48a1d268
     */
    public String getJz() {
        return jz;
    }

    /**
     * 设置 a26 该字段是：a26
     *
     * @param temp
     * @pdOid 1e3e44ae-ee55-4fe3-8caf-f93d5f20de96
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * 获取 a26 该字段是：a26
     *
     * @pdOid aa70929a-b224-48ba-bae2-6aad65d89172
     */
    public String getTemp() {
        return temp;
    }

    /**
     * 设置 a27 该字段是：a27
     *
     * @param mpa
     * @pdOid 7c80213e-7fc2-4379-adb3-1e74982c8757
     */
    public void setMpa(String mpa) {
        this.mpa = mpa;
    }

    /**
     * 获取 a27 该字段是：a27
     *
     * @pdOid bea7be23-466d-4446-9cf2-12fcab779eef
     */
    public String getMpa() {
        return mpa;
    }

    /**
     * 设置 a28 该字段是：a28
     *
     * @param fcjgtab
     * @pdOid a638e97b-de3c-4615-a4fa-bf5ca5572562
     */
    public void setFcjgtab(String fcjgtab) {
        this.fcjgtab = fcjgtab;
    }

    /**
     * 获取 a28 该字段是：a28
     *
     * @pdOid 55ced6bb-b094-4106-afc3-728305562a11
     */
    public String getFcjgtab() {
        return fcjgtab;
    }

    /**
     * 设置 a29 该字段是：a29
     *
     * @param mbclass
     * @pdOid 6fa32d8c-5b35-4b06-b45f-98ef9d19ddbe
     */
    public void setMbclass(String mbclass) {
        this.mbclass = mbclass;
    }

    /**
     * 获取 a29 该字段是：a29
     *
     * @pdOid b1dbae68-521d-40fb-b28e-4fa0f2071e38
     */
    public String getMbclass() {
        return mbclass;
    }

    /**
     * 设置 a31 该字段是：a31
     *
     * @param fxstatus
     * @pdOid a0ee654c-92b0-4982-91c4-3641b9cb8b7b
     */
    public void setFxstatus(String fxstatus) {
        this.fxstatus = fxstatus;
    }

    /**
     * 获取 a31 该字段是：a31
     *
     * @pdOid 27a115d5-bbfa-4ebc-95f9-2275d68d7469
     */
    public String getFxstatus() {
        return fxstatus;
    }

    /**
     * 设置 a32 该字段是：a32
     *
     * @param zftype
     * @pdOid 35e021e1-6adb-4548-a573-64cbcb77b0bf
     */
    public void setZftype(String zftype) {
        this.zftype = zftype;
    }

    /**
     * 获取 a32 该字段是：a32
     *
     * @pdOid 03a92d85-8966-4c87-92c4-c04937d9f926
     */
    public String getZftype() {
        return zftype;
    }

    /**
     * 设置 a33 该字段是：a33
     *
     * @param maxyxq
     * @pdOid 2cab35f3-e4ca-448c-b75d-b934e96bb13a
     */
    public void setMaxyxq(Integer maxyxq) {
        this.maxyxq = maxyxq;
    }

    /**
     * 获取 a33 该字段是：a33
     *
     * @pdOid 2dcaafe2-efda-4c72-9b4b-aff91096b3cd
     */
    public Integer getMaxyxq() {
        return maxyxq;
    }

    /**
     * 设置 a34 该字段是：a34
     *
     * @param gjjy
     * @pdOid a96606e5-f2ed-4ec6-8830-526a82dfe9d9
     */
    public void setGjjy(String gjjy) {
        this.gjjy = gjjy;
    }

    /**
     * 获取 a34 该字段是：a34
     *
     * @pdOid 0e9df8a2-45b4-4f80-bc53-5280ec75d909
     */
    public String getGjjy() {
        return gjjy;
    }

    /**
     * 设置 a36 该字段是：a36
     *
     * @param total
     * @pdOid be1781c1-0ade-42e7-9637-7332206bfccd
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * 获取 a36 该字段是：a36
     *
     * @pdOid cbd8fb90-a6ad-4c2f-93a7-c4b2143fd00a
     */
    public float getTotal() {
        return total;
    }

    /**
     * 设置 a37 该字段是：a37
     *
     * @param zqx
     * @pdOid 42ba82ad-aa64-4d11-a088-8884da32a989
     */
    public void setZqx(Integer zqx) {
        this.zqx = zqx;
    }

    /**
     * 获取 a37 该字段是：a37
     *
     * @pdOid da393f4e-5477-4900-94bc-17213d5b2138
     */
    public Integer getZqx() {
        return zqx;
    }

    /**
     * 设置 是否伪删除 该字段是：是否伪删除
     *
     * @param dr
     * @pdOid 66fde44a-ffbf-4b41-b314-0bbf8836510e
     */
    public void setDr(Integer dr) {
        this.dr = dr;
    }

    /**
     * 获取 是否伪删除 该字段是：是否伪删除
     *
     * @pdOid 2461ec18-4241-4f4b-9053-5c9ad53ae80c
     */
    public Integer getDr() {
        return dr;
    }

    /**
     * 设置 上传 该字段是：上传
     *
     * @param isupload
     * @pdOid 6d7947e0-a9a6-42bf-b3d8-e859cf780940
     */
    public void setIsupload(Integer isupload) {
        this.isupload = isupload;
    }

    /**
     * 获取 上传 该字段是：上传
     *
     * @pdOid 6b756cc9-96f5-46c8-8495-d9fddcab8bba
     */
    public Integer getIsupload() {
        return isupload;
    }

    /**
     * 设置 标记 该字段是：标记
     *
     * @param tag
     * @pdOid 2517b6e4-031c-4ca9-8c25-8acf655c1f95
     */
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    /**
     * 获取 标记 该字段是：标记
     *
     * @pdOid cac6f632-45a6-439c-94d5-ebcccbbdc2cc
     */
    public Integer getTag() {
        return tag;
    }

    /**
     * @pdOid 0f446c62-60eb-4eac-bbec-2be26fe1d072
     */
    public String getUd_zyxk_worktaskid() {
        return ud_zyxk_worktaskid;
    }

    /**
     * @param ud_zyxk_worktaskid
     * @pdOid be0e1bba-ef3b-4bc6-a035-8585f92940e2
     */
    public void setUd_zyxk_worktaskid(String ud_zyxk_worktaskid) {
        this.ud_zyxk_worktaskid = ud_zyxk_worktaskid;
    }

    /**
     * @pdOid a4e83a03-5fab-4457-b19a-29b135e01b59
     */
    public String getUd_zyxk_zysqid() {
        return ud_zyxk_zysqid;
    }

    /**
     * @param ud_zyxk_zysqid
     * @pdOid 6b781ff3-dbc2-4670-b990-57ab192700fe
     */
    public void setUd_zyxk_zysqid(String ud_zyxk_zysqid) {
        this.ud_zyxk_zysqid = ud_zyxk_zysqid;
    }

    /**
     * @pdOid 4ec00427-df07-4fa1-9ea1-030541ca34dd
     */
    public String getUd_zyxk_zyyyid() {
        return ud_zyxk_zyyyid;
    }

    /**
     * @param ud_zyxk_zyyyid
     * @pdOid 98bd29af-e7c0-4628-a6ea-7079a2df16d3
     */
    public void setUd_zyxk_zyyyid(String ud_zyxk_zyyyid) {
        this.ud_zyxk_zyyyid = ud_zyxk_zyyyid;
    }

    /**
     * @return the parent_id
     * @pdOid 72ae06cd-6ba2-468e-9275-094ad52f1ec0
     */
    public String getParent_id() {
        return parent_id;
    }

    /**
     * @param parent_id the parent_id to set
     * @pdOid 811f7126-d6c4-4f62-ae36-1467d832f592
     */
    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    /**
     * @pdOid fec3dd9b-67a9-44bb-b7a5-73a7973b5692
     */
    public String getZylevel_desc() {
        return zylevel_desc;
    }

    /**
     * @param zylevel_desc
     * @pdOid a1ca297b-6464-4755-ad23-d8030591e6ce
     */
    public void setZylevel_desc(String zylevel_desc) {
        this.zylevel_desc = zylevel_desc;
    }

    /**
     * @pdOid f063c819-c2b4-4a03-8d9b-c7a345a2d918
     */
    public String getZydept_desc() {
        return zydept_desc;
    }

    /**
     * @param zydept_desc
     * @pdOid 6081846d-3e9a-4290-af13-c5cd1958c05e
     */
    public void setZydept_desc(String zydept_desc) {
        this.zydept_desc = zydept_desc;
    }

    /**
     * @pdOid f0aa2799-d17a-418d-b513-9aece877120e
     */
    public String getSddept_desc() {
        return sddept_desc;
    }

    /**
     * @param sddept_desc
     * @pdOid 9f4711f3-9cdd-4c2b-a9d5-6603e2d7a995
     */
    public void setSddept_desc(String sddept_desc) {
        this.sddept_desc = sddept_desc;
    }

    /**
     * @pdOid 9e4ded9a-7714-4207-9ace-f8e7cd532671
     */
    public String getChq_zy1dw1() {
        return chq_zy1dw1;
    }

    /**
     * @param chq_zy1dw1
     * @pdOid bf568d5d-26fe-49c3-b057-7181ec27f505
     */
    public void setChq_zy1dw1(String chq_zy1dw1) {
        this.chq_zy1dw1 = chq_zy1dw1;
    }

    /**
     * @pdOid fdaac097-e342-4ac1-8415-52ed2ac954ba
     */
    public String getChq_zy1dw2() {
        return chq_zy1dw2;
    }

    /**
     * @param chq_zy1dw2
     * @pdOid 6191aae0-235a-4728-90e5-0a179ac1624d
     */
    public void setChq_zy1dw2(String chq_zy1dw2) {
        this.chq_zy1dw2 = chq_zy1dw2;
    }

    /**
     * @pdOid 74c7108c-fd1b-4c22-b61b-e49f3e97eb96
     */
    public String getChq_zy1dw1qrr1() {
        return chq_zy1dw1qrr1;
    }

    /**
     * @param chq_zy1dw1qrr1
     * @pdOid 8cfe4101-81f5-44e0-b320-451c5fa9c8db
     */
    public void setChq_zy1dw1qrr1(String chq_zy1dw1qrr1) {
        this.chq_zy1dw1qrr1 = chq_zy1dw1qrr1;
    }

    /**
     * @pdOid 34b1b795-95db-4917-a065-70628fec798f
     */
    public String getChq_zy1dw2qrr2() {
        return chq_zy1dw2qrr2;
    }

    /**
     * @param chq_zy1dw2qrr2
     * @pdOid eae5d58e-9e02-43a9-a3b6-e091e5dad9a7
     */
    public void setChq_zy1dw2qrr2(String chq_zy1dw2qrr2) {
        this.chq_zy1dw2qrr2 = chq_zy1dw2qrr2;
    }

    /**
     * @pdOid 98e9ca98-fb27-4af5-b4a2-62eeff0b3bd1
     */
    public String getChq_zy1dw1qrr1time() {
        return chq_zy1dw1qrr1time;
    }

    /**
     * @param chq_zy1dw1qrr1time
     * @pdOid 2d78fb0e-b33a-4582-ace9-837e104d5dbb
     */
    public void setChq_zy1dw1qrr1time(String chq_zy1dw1qrr1time) {
        this.chq_zy1dw1qrr1time = chq_zy1dw1qrr1time;
    }

    /**
     * @pdOid c1b96096-bb97-484c-92f4-261d08498e90
     */
    public String getChq_zy1dw2qrr2time() {
        return chq_zy1dw2qrr2time;
    }

    /**
     * @param chq_zy1dw2qrr2time
     * @pdOid 6688a023-107a-43fa-826f-9722bd4b3f06
     */
    public void setChq_zy1dw2qrr2time(String chq_zy1dw2qrr2time) {
        this.chq_zy1dw2qrr2time = chq_zy1dw2qrr2time;
    }

    /**
     * @pdOid f640e141-5fa8-41e5-95e8-af3c6495b0b9
     */
    public String getChq_gbzydwjhr() {
        return chq_gbzydwjhr;
    }

    /**
     * @param chq_gbzydwjhr
     * @pdOid 2aa4287a-3dbb-4590-b2e8-b351a037c73e
     */
    public void setChq_gbzydwjhr(String chq_gbzydwjhr) {
        this.chq_gbzydwjhr = chq_gbzydwjhr;
    }

    /**
     * @pdOid a290a1ad-08c4-488b-a788-bb4b7143506e
     */
    public String getChq_gbxdjhr() {
        return chq_gbxdjhr;
    }

    /**
     * @param chq_gbxdjhr
     * @pdOid b92c6fa3-f85a-4cd3-8760-ef451e69a3a6
     */
    public void setChq_gbxdjhr(String chq_gbxdjhr) {
        this.chq_gbxdjhr = chq_gbxdjhr;
    }

    /**
     * @pdOid d102ddc4-9519-4782-b215-19f9b48434c8
     */
    public String getChq_gbxdjhrtime() {
        return chq_gbxdjhrtime;
    }

    /**
     * @param chq_gbxdjhrtime
     * @pdOid c53db507-86bf-45e4-92eb-3cb4ff7cafc9
     */
    public void setChq_gbxdjhrtime(String chq_gbxdjhrtime) {
        this.chq_gbxdjhrtime = chq_gbxdjhrtime;
    }

    /**
     * @pdOid 8de96aec-f853-48b3-abb5-56298880dc7d
     */
    public String getChq_gbzydwjhrtime() {
        return chq_gbzydwjhrtime;
    }

    /**
     * @param chq_gbzydwjhrtime
     * @pdOid 17c6ad0d-5e97-457e-86b5-9c8e23b0435f
     */
    public void setChq_gbzydwjhrtime(String chq_gbzydwjhrtime) {
        this.chq_gbzydwjhrtime = chq_gbzydwjhrtime;
    }

    /**
     * @pdOid cdf8926e-94bd-4026-9ded-6a743e3f92cc
     */
    public String getJclocation_desc() {
        return jclocation_desc;
    }

    /**
     * @param jclocation_desc
     * @pdOid fde5a311-ae73-4271-88e0-1fc6a4d96540
     */
    public void setJclocation_desc(String jclocation_desc) {
        this.jclocation_desc = jclocation_desc;
    }

    /**
     * @pdOid 623c06d5-0b24-414c-9196-1dec2dd4d229
     */
    public String getZyarea_desc() {
        return zyarea_desc;
    }

    /**
     * @param zyarea_desc
     * @pdOid ec9ff6fb-5218-42de-b570-01a8f4be25ba
     */
    public void setZyarea_desc(String zyarea_desc) {
        this.zyarea_desc = zyarea_desc;
    }

    /**
     * @pdOid 774be730-78e4-4113-a597-2dc4dd00bb17
     */
    public String getZylocation_desc() {
        return zylocation_desc;
    }

    /**
     * @param zylocation_desc
     * @pdOid 657085c8-adbd-472e-a19d-8a4883ac1dc8
     */
    public void setZylocation_desc(String zylocation_desc) {
        this.zylocation_desc = zylocation_desc;
    }

    /**
     * @pdOid 476514bc-3acd-48dd-8a95-0f2e08b32f18
     */
    public String getDhzy_id() {
        return dhzy_id;
    }

    /**
     * @param dhzy_id
     * @pdOid 117badde-b26a-411e-9d02-796128c00edd
     */
    public void setDhzy_id(String dhzy_id) {
        this.dhzy_id = dhzy_id;
    }

    /**
     * @pdOid c92a73e7-dd69-4629-b9fa-f71884a6cbe1
     */
    public String getParent_status() {
        return parent_status;
    }

    /**
     * @param parent_status
     * @pdOid 7d19568c-e604-49fa-af48-75dbc5a507c8
     */
    public void setParent_status(String parent_status) {
        this.parent_status = parent_status;
    }

    /**
     * @pdOid bc72d2f1-e558-471c-9d93-d0fcb9800249
     */
    public String getUd_zyxk_zyfxfxid() {
        return ud_zyxk_zyfxfxid;
    }

    /**
     * @param ud_zyxk_zyfxfxid
     * @pdOid 4a2e8b56-27af-4f2c-a8c4-ac67956404d3
     */
    public void setUd_zyxk_zyfxfxid(String ud_zyxk_zyfxfxid) {
        this.ud_zyxk_zyfxfxid = ud_zyxk_zyfxfxid;
    }

    /**
     * @pdOid 85e5a243-e76e-4d5c-83ef-30584afd8e6b
     */
    public Integer getUd_zyxk_jsaid() {
        return ud_zyxk_jsaid;
    }

    /**
     * @param ud_zyxk_jsaid
     * @pdOid 1220d4bd-d271-4d2e-862e-c6d595865812
     */
    public void setUd_zyxk_jsaid(Integer ud_zyxk_jsaid) {
        this.ud_zyxk_jsaid = ud_zyxk_jsaid;
    }

    /**
     * @pdOid ac9799de-8209-4daa-b974-f2b275c9e36e
     */
    public String getZypclassname() {
        return zypclassname;
    }

    /**
     * @param zypclassname
     * @pdOid bc8eed93-586e-4bed-a4bf-79cf9771bb51
     */
    public void setZypclassname(String zypclassname) {
        this.zypclassname = zypclassname;
    }

    /**
     * @pdOid 45f793a3-ebbc-4d8c-b9ac-5bfb298ff264
     */
    public String getStatusname() {
        return statusname;
    }

    /**
     * @param statusname
     * @pdOid af1ffd40-c1b2-4a2e-9712-a50661b95782
     */
    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    /**
     * gbtype_desc.
     *
     * @return the gbtype_desc
     * @pdOid d26f3eaf-b242-4454-88e8-cb2a64b02c3a
     */
    public String getGbtype_desc() {
        return gbtype_desc;
    }

    /**
     * gbtype_desc.
     *
     * @param gbtype_desc the gbtype_desc to set
     * @pdOid d6a18746-5750-4330-ac52-d1b0357065c5
     */
    public void setGbtype_desc(String gbtype_desc) {
        this.gbtype_desc = gbtype_desc;
    }

    /**
     * jczy.
     *
     * @return the jczy
     */
    public String getJczy() {
        return jczy;
    }

    /**
     * jczy.
     *
     * @param jczy the jczy to set
     */
    public void setJczy(String jczy) {
        this.jczy = jczy;
    }

    /**
     * jcyy.
     *
     * @return the jcyy
     */
    public String getJcyy() {
        return jcyy;
    }

    /**
     * jcyy.
     *
     * @param jcyy the jcyy to set
     */
    public void setJcyy(String jcyy) {
        this.jcyy = jcyy;
    }

    /**
     * cx.
     *
     * @return the cx
     */
    public String getCx() {
        return cx;
    }

    /**
     * cx.
     *
     * @param cx the cx to set
     */
    public void setCx(String cx) {
        this.cx = cx;
    }

    /**
     * cphm.
     *
     * @return the cphm
     */
    public String getCphm() {
        return cphm;
    }

    /**
     * cphm.
     *
     * @param cphm the cphm to set
     */
    public void setCphm(String cphm) {
        this.cphm = cphm;
    }

    /**
     * jsyxm.
     *
     * @return the jsyxm
     */
    public String getJsyxm() {
        return jsyxm;
    }

    /**
     * jsyxm.
     *
     * @param jsyxm the jsyxm to set
     */
    public void setJsyxm(String jsyxm) {
        this.jsyxm = jsyxm;
    }

    /**
     * jszhm.
     *
     * @return the jszhm
     */
    public String getJszhm() {
        return jszhm;
    }

    /**
     * jszhm.
     *
     * @param jszhm the jszhm to set
     */
    public void setJszhm(String jszhm) {
        this.jszhm = jszhm;
    }

    /**
     * jdcaqxslxt.
     *
     * @return the jdcaqxslxt
     */
    public String getJdcaqxslxt() {
        return jdcaqxslxt;
    }

    /**
     * jdcaqxslxt.
     *
     * @param jdcaqxslxt the jdcaqxslxt to set
     */
    public void setJdcaqxslxt(String jdcaqxslxt) {
        this.jdcaqxslxt = jdcaqxslxt;
    }

    public String getDhlevel() {
        return dhlevel;
    }

    public void setDhlevel(String dhlevel) {
        this.dhlevel = dhlevel;
    }

    public String getSxkjlevel() {
        return sxkjlevel;
    }

    public void setSxkjlevel(String sxkjlevel) {
        this.sxkjlevel = sxkjlevel;
    }

    public String getSxlevel() {
        return sxlevel;
    }

    public void setSxlevel(String sxlevel) {
        this.sxlevel = sxlevel;
    }

    public String getGclevel() {
        return gclevel;
    }

    public void setGclevel(String gclevel) {
        this.gclevel = gclevel;
    }

    /**
     * sameTypeCount:TODO(统计同一类别和等级的作业票的数量).
     */
    private int sameTypeCount = 1;

    public int getSameTypeCount() {
        return sameTypeCount;
    }

    public void setSameTypeCount(int sameTypeCount) {
        this.sameTypeCount = sameTypeCount;
    }

    public String getWhtype() {
        return whtype;
    }

    public void setWhtype(String whtype) {
        this.whtype = whtype;
    }

    public String getTopzypclass() {
        return topzypclass;
    }

    public void setTopzypclass(String topzypclass) {
        this.topzypclass = topzypclass;
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

    public String getPausetime() {
        return pausetime;
    }

    public void setPausetime(String pausetime) {
        this.pausetime = pausetime;
    }

    public String getUd_zyxk_prtylineid() {
        return ud_zyxk_prtylineid;
    }

    public void setUd_zyxk_prtylineid(String ud_zyxk_prtylineid) {
        this.ud_zyxk_prtylineid = ud_zyxk_prtylineid;
    }

    public String getUd_zyxk_sxpcpzid() {
        return ud_zyxk_sxpcpzid;
    }

    public void setUd_zyxk_sxpcpzid(String ud_zyxk_sxpcpzid) {
        this.ud_zyxk_sxpcpzid = ud_zyxk_sxpcpzid;
    }

    public String getIsaccord() {
        return isaccord;
    }

    public void setIsaccord(String isaccord) {
        this.isaccord = isaccord;
    }

    public String getQtjcpauseby() {
        return qtjcpauseby;
    }

    public void setQtjcpauseby(String qtjcpauseby) {
        this.qtjcpauseby = qtjcpauseby;
    }

    public String getQtjcpausetime() {
        return qtjcpausetime;
    }

    public void setQtjcpausetime(String qtjcpausetime) {
        this.qtjcpausetime = qtjcpausetime;
    }

    public String getQtjcstopby() {
        return qtjcstopby;
    }

    public void setQtjcstopby(String qtjcstopby) {
        this.qtjcstopby = qtjcstopby;
    }

    public String getQtjcstoptime() {
        return qtjcstoptime;
    }

    public void setQtjcstoptime(String qtjcstoptime) {
        this.qtjcstoptime = qtjcstoptime;
    }

}