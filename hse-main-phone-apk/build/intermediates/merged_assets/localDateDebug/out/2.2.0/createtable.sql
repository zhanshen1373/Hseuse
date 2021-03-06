CREATE TABLE ud_zyxk_nlgld (
  UD_ZYXK_NLGLDID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  UD_ZYXK_ZYSQID VARCHAR2(50),
  SSDEPT VARCHAR2 (50),
  ZZLOCATION VARCHAR2 (50),
  GLASSET VARCHAR2 (50),
  GLSTATUS VARCHAR2 (20),
  STATUS VARCHAR2 (50),
  HAZARD VARCHAR2 (100),
  HAZARD_DESC VARCHAR2 (200),
  ZYLOCATION VARCHAR2 (50),
  BXPERSON VARCHAR2 (30),
  CSPERSON VARCHAR2 (30),
  ZYPERSON VARCHAR2 (30),
  PZPERSON VARCHAR2 (30),
  PZTIME VARCHAR2 (50),
  NLGLDNUM varchar2 (20),
  IsUpLoad integer NOT NULL DEFAULT 0,
  NLY NVARCHAR2
);

CREATE TABLE ud_zyxk_nlgldline (
	UD_ZYXK_NLGLDLINEID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	UD_ZYXK_NLGLDID NUMBER NOT NULL,
	SEQNUM NUMBER NULL,
	LOCATION VARCHAR2 (50) NULL,
	LOCATION_DESC VARCHAR2 (50) NULL,
	GZNRDESC VARCHAR2 (50) NULL,
	COUNT INTEGER NULL,
	JCNUM VARCHAR2 (20) NULL,
	JCCLASS VARCHAR2 (50) NULL,
	NLGLDNUM varchar2 (20) NULL,
	NLWL varchar2 (50),
	GLFF varchar2 (50),
	SSGPD varchar2 (50),
	GPD varchar2 (50),
	NLGLQT varchar2 (50),
	IsUpLoad integer NOT NULL DEFAULT 0,
	dataSource varchar2 (50),
  type varchar2 (20)
);