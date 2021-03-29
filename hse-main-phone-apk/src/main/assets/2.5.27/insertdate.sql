insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.service.workorder.checkrules.CheckCanApprove',
'校验审批人是否为空',1);

insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.cc.service.checkrules.CheckWorkToTask',
'关联的大小票状态判断',2);

insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.cc.service.checkrules.CancelCheckElectricityAndFire',
'关联的电火票状态判断',3);

insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.cc.service.saveinfo.SaveWorkInterruptReason',
'关闭票并保存中断信息',4);

insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.service.workorder.saveinfo.SaveApprovalIn',
'保存审批信息',5);

insert into checkActionCfg values(null,'INTERRUPT','确认中断','com.hd.hse.cc.service.saveinfo.CcSaveWorkStatus',
'更改作业票状态',6);

insert into checkActionCfg values(null,'INTERRUPTEND','确认中断结束','com.hd.hse.service.workorder.checkrules.CheckCanApprove',
'校验审批人是否为空',1);

insert into checkActionCfg values(null,'INTERRUPT','确认中断结束','com.hd.hse.cc.service.checkrules.CheckWorkToTask',
'关联的大小票状态判断',2);

insert into checkActionCfg values(null,'INTERRUPT','确认中断结束','com.hd.hse.cc.service.checkrules.CancelCheckElectricityAndFire',
'关联的电火票状态判断',3);

insert into checkActionCfg values(null,'INTERRUPT','确认中断结束','com.hd.hse.cc.service.saveinfo.SaveWorkInterruptEndReason',
'关闭票并保存中断信息',4);

insert into checkActionCfg values(null,'INTERRUPT','确认中断结束','com.hd.hse.service.workorder.saveinfo.SaveApprovalIn',
'保存审批信息',5);

insert into checkActionCfg values(null,'INTERRUPT','确认中断结束','com.hd.hse.cc.service.saveinfo.CcSaveWorkStatus',
'更改作业票状态',6);