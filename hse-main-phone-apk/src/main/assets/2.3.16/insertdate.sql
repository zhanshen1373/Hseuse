insert into checkactioncfg values(
       null,       
       'HAZARD',       
       '现场核查-危害确认',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       6
);
insert into checkactioncfg values(
       null,       
       'PRECAUTION',       
       '现场核查-措施确认',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       6
);
insert into checkactioncfg values(
       null,       
       'PPE',       
       '现场核查-PPE确认',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       6
);
insert into checkactioncfg values(
       null,       
       'SIGN',       
       '现场核查-会签刷卡',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       13
);
insert into checkactioncfg values(
       null,       
       'GAS',       
       '现场核查-气体检测（含检测人、确认人）',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       8
);
insert into checkactioncfg values(
       null,       
       'RETURN',       
       '确认退回',       
       'com.hd.hse.service.workorder.saveinfo.SetWorkOrderUpState',       
       '设置作业票上传标记',
       4
);

update checkactioncfg set px = px + 1 where actiontype = 'SIGN' and px >= 3;
insert into checkactioncfg values(
       null,       
       'SIGN',       
       '现场核查-会签刷卡',       
       'com.hd.hse.osc.service.checkrules.CheckTabIsFinishedForAsyncAppr',       
       '一票制校验措施危害个人防护是否完成',
       3
);
update checkactioncfg set px = px + 1 where actiontype = 'SIGN' and px >= 10;
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values ('HOISTING','现场核查-吊物吊车信息','com.hd.hse.osc.service.saveinfo.SaveHoistingInfo','保存吊物吊车信息',2);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values ('HOISTING','现场核查-吊物吊车信息','com.hd.hse.osc.service.saveinfo.SaveWorkTabOpeNum','更新吊物吊车完成标记',4);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values ('SIGN','现场核查-会签刷卡','com.hd.hse.osc.service.checkrules.CheckHoistingInfo','校验吊物吊车结果',10);