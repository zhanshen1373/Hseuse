create table ud_zyxk_priority
(
  ud_zyxk_priorityid NUMBER not null,
  description        VARCHAR2(50),
  siteid             VARCHAR2(8),
  orgid              VARCHAR2(8),
  hasld              NUMBER not null,
  rowstamp           VARCHAR2(40) not null,
  dept               VARCHAR2(50),
  dept_desc          VARCHAR2(50),
  prtyname           VARCHAR2(50),
  isenable           NUMBER not null,
  createby           VARCHAR2(50),
  createdate         DATE,
  changeby           VARCHAR2(50),
  changedate         DATE,
  tag                INT,
  dr                 INT
);

create table ud_zyxk_prtyline
(
  ud_zyxk_prtylineid NUMBER not null,
  description        VARCHAR2(50),
  zypclass           VARCHAR2(50),
  priority           NUMBER,
  changeby           VARCHAR2(50),
  changedate         DATE,
  zyplevel           VARCHAR2(50),
  tag                INT,
  dr                 INT
);
