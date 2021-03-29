insert into hse_sys_record_ts values(23,'ud_zyxk_ydsb','ud_zyxk_ydsbid','用电设备表',1,1,0,null,0,1,0);

insert into checkactioncfg values(null, 'SIGN', '现场核查-会签刷卡', 'com.hd.hse.osc.service.checkrules.CheckTempEleInfo', '校验临时用电结果', 9);

insert into checkactioncfg values(null, 'ELE', '现场核查-临时用电', 'com.hd.hse.service.workorder.checkrules.CheckCanApprove', '校验审批人是否为空', 1);
insert into checkactioncfg values(null, 'ELE', '现场核查-临时用电', 'com.hd.hse.osc.service.saveinfo.SaveTempEleInfo', '保存临时用电信息', 2);
insert into checkactioncfg values(null, 'ELE', '现场核查-临时用电', 'com.hd.hse.service.workorder.saveinfo.SaveApprovalIn', '保存审批信息', 3);
insert into checkactioncfg values(null, 'ELE', '现场核查-临时用电', 'com.hd.hse.osc.service.saveinfo.SaveWorkTabOpeNum', '更新临时用电完成标记', 4);