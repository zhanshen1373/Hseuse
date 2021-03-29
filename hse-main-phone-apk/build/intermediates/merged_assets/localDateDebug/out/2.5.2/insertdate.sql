insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.service.workorder.checkrules.CheckCanApprove','校验审批人是否为空',1);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.cc.service.checkrules.CheckWorkToTask','关联的大小票状态判断',2);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.cc.service.checkrules.CancelCheckElectricityAndFire','关联的电火票状态判断',3);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.cc.service.saveinfo.SaveWorkPauseReason','保存暂停的信息',4);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.service.workorder.saveinfo.SaveApprovalIn','保存审批的信息',5);
insert into checkactioncfg (actiontype,actiontypedesc,classname,classnamedesc,px) values('PAUSE','确认暂停','com.hd.hse.cc.service.saveinfo.PauseSaveWorkStatus','更改作业票状态',7);
insert into hse_sys_record_ts values(25 , 'ud_cbsgl_khbz' , 'ud_cbsgl_khbzid', "违章考核标准表", 1,1,1,'',0,1,0);
insert into hse_sys_appmodule values('zcrytj','在场人员统计','hd_hse_tjfx_zcrytj',null,'hd_hse_tjfx_zcrytj'," 'ZCRYTJ','ZCRYTJ_TBL' ",13,1,1,0,'TJFX',1,0);
