CREATE TABLE ud_sys_appanlscfg
(
  ud_sys_appanlscfgid NUMBER ,
  description         VARCHAR2(50),
  anlsname            VARCHAR2(300),
  anlscode            VARCHAR2(150),
  condition           VARCHAR2(300),
  deptnum             VARCHAR2(1000),
  childdept           VARCHAR2(3000),
  rqurl               VARCHAR2(150),
  icon                VARCHAR2(100),
  changedate          nvarchar(19),
  dr                  NUMBER ,  
  tag                 NUMBER ,  
  isenable            NUMBER 
);
