CREATE TABLE ud_zyxk_pdapage
(
  ud_zyxk_padpageid NUMBER not null,
  description       VARCHAR2(50),
  changeby          VARCHAR2(50),
  changedate        DATE,
  childdept         VARCHAR2(50),
  createby          VARCHAR2(50),
  createdate        DATE,
  deptnum           VARCHAR2(50),
  deptnum_desc      VARCHAR2(50),
  isenable          NUMBER not null,
  menuctrl          VARCHAR2(1000),
  orgid             VARCHAR2(50),
  qxname            VARCHAR2(50),
  qxrole            VARCHAR2(1000),
  qxrole_desc       VARCHAR2(1000),
  siteid            VARCHAR2(50),
  parameter         VARCHAR2(4000),
  parameter_desc    VARCHAR2(4000),
  tag INT,
  dr INT
);
