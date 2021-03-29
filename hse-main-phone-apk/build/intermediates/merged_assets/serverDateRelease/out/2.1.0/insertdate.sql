insert into hse_sys_record_ts(sys_tablename,sys_tableid,sys_tabledesc,sys_init,sys_base,sys_cbspeople,dr,sys_logindateupdate)
values('ud_zyxk_gwgl','ud_zyxk_jobid','岗位表',1,1,0,0,1);
insert into hse_sys_appmodule
(code,name,resimg,resimgpress,clickdealclass,layoutorder,enabled,tag,dr,type,isswcard)
values
('hse-vp-phone-app','虚拟位置','hd_hse_nav_model_vp','','com.hd.hse.vp.phone.ui.event.homepage.ShiftChangeApp','9','1',0,0,'SJ',0);
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.service.workorder.checkrules.merge.MergeCheckCanApprove','校验审批人是否为空','1');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.service.workorder.checkrules.merge.MergeCheckWorkOrderStatus','校验作业票状态审批INPRG','2');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeCheckGasDetectLimition','气体检测失效配置，是否过期','4');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeCheckGasValue','校验气体检测结果（检测时间倒序）','5');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeCheckZyendDateIsRight','校验作业票实际结束时间','6');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeCheckWorkToTask','校验关联的大票的状态','7');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeCheckElectricityAndFire','校验关联的火票的状态','8');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.service.workorder.saveinfo.merge.MergeSaveApprovalIn','保存审批信息','9');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.service.workorder.saveinfo.merge.MergeSaveWorkStatus','更改作业票状态','10');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGESIGN','现场核查-合并会签刷卡','com.hd.hse.osc.service.Merge.MergeSaveWorkRealStartTime','更改作业票实际开始时间','11');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'CANMERGESIGN','合并审批校验措施','com.hd.hse.osc.service.Merge.MergeCheckAllMearsurConfirm','现场核查-合并审批验证措施是否审核','1');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.service.workorder.checkrules.merge.MergeCheckCanApprove','校验审批人是否为空','1');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.cc.service.merge.MergeCheckWorkToTask','关联的大小票状态判断','2');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.cc.service.merge.MergeCloseCheckElectricityAndFire','关联的电火票状态判断','3');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.cc.service.merge.MergeSaveWorkCloseReason','关闭票并保存相关信息','4');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.service.workorder.saveinfo.merge.MergeSaveApprovalIn','保存审批信息','5');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.cc.service.merge.MergeCcSaveWorkRealEndTime','保存作业票时间','6');
insert into checkactioncfg (actiontype, actiontypedesc, classname, classnamedesc, px) values ( 'MERGECLOSE','合并确认关闭','com.hd.hse.cc.service.merge.MergeCcSaveWorkStatus','更改作业票状态','7');
insert into hse_sys_appmodule (code, name, resimg, resimgpress, clickdealclass, layoutorder, enabled, tag, dr, type, modelnum, isswcard) values (
'hse-wov-phone-app-tjfx', '统计分析', 'hd_hse_nav_model_tjfx', 'hd_hse_nav_model_tjfx', 'com.hd.hse.wov.phone.ui.event.homepage.StatisticsApp', 10, 1, 0, 0, 'SJ', 'LL02',0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('dhyyfx','动火原因分析','hd_hse_tjfx_dhyyfx','hd_hse_tjfx_dhyyfx','1',1,1,1,0,'TJFX',"'DHRESIONHZ','DHRESIONHZ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('zylxtj','作业类型统计','hd_hse_tjfx_zylxtj','hd_hse_tjfx_zylxtj','1',2,1,1,0,'TJFX',"'GDWBTZYLXTJ','GDWBTZYLXTJ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('zyslytj','作业数量月统计','hd_hse_tjfx_zyslytj','hd_hse_tjfx_zyslytj','4',3,1,4,0,'TJFX',"'GDWZYPAYTJ','GDWZYPAYTJ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('tzzyjbtj','特种作业级别统计','hd_hse_tjfx_tzzyjbtj','hd_hse_tjfx_tzzyjbtj','2',4,1,2,0,'TJFX',"'GTZZYAJBTJ','GTZZYAJBTJ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('fpltj','废票率统计','hd_hse_tjfx_fpltj','hd_hse_tjfx_fpltj','1',5,1,1,0,'TJFX',"'FPLTJ','FPLTJ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('wtpztj','问题票证统计','hd_hse_tjfx_wtpztj','hd_hse_tjfx_wtpztj','1',6,1,1,0,'TJFX',"'WTPZTJ','WTPZTJ_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('tzzyyytb','特种作业原因同比','hd_hse_tjfx_tzzyyytb','hd_hse_tjfx_tzzyyytb','3',7,1,3,0,'TJFX',"'GTZZYYYTB','GTZZYYYTB_TBL'",0);

insert into hse_sys_appmodule (code, name, resimg, resimgpress, modelnum, layoutorder, enabled, tag, dr, type, clickdealclass, isswcard)
values ('zypsltb','作业票数量同比','hd_hse_tjfx_zypsltb','hd_hse_tjfx_zypsltb','3',8,1,3,0,'TJFX',"'GDWZYPTB','GDWZYPTB_TBL'",0);
