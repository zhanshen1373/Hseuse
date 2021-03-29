create table ud_sy_licence
(
  ud_sy_licenceid NUMBER not null,
  description     VARCHAR2(50),
  imeia           VARCHAR2(50),
  imeib           VARCHAR2(50),
  hasld           NUMBER ,
  changedate      VARCHAR2(50),
  rowstamp        VARCHAR2(40) ,
  jobdept          VARCHAR2(50),
  jobdept_desc     VARCHAR2(50),
  sddept          VARCHAR2(50),
  sddept_desc     VARCHAR2(50),
  tag int, 
  dr int
)