create table calendar
(
  calnum      VARCHAR2(80),
  description VARCHAR2(100),
  startdate   VARCHAR2(200),
  enddate     VARCHAR2(200),
  orgid       VARCHAR2(200),
  calendarid  int not null,
  langcode    VARCHAR2(300),
  hasld       int,
  rowstamp    VARCHAR2(400),
  createby    VARCHAR2(200),
  createdept  VARCHAR2(200),
  createdate  VARCHAR2(200),
  changeby    VARCHAR2(300),
  changedate VARCHAR2(200),
  udnoteinfo  VARCHAR2(200),
  dr int,
  tag int

)

