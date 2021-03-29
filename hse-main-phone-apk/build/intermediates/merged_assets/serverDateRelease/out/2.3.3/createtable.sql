create table ud_zyxk_ydsb
(
  ud_zyxk_ydsbid INTEGER,
  fh             NUMBER(12,2),
  assetname      VARCHAR(50),
  version        VARCHAR(50),
  dr             NUMBER DEFAULT 0,
  changedate     VARCHAR(20),
  tag			 INT
);
create table ud_zyxk_ydasset
(
  ud_zyxk_ydassetid VARCHAR(50),
  ud_zyxk_ydsbid	INTEGER,
  ud_zyxk_zysqid    VARCHAR(50),
  assetname         VARCHAR(50),
  count             NUMBER,
  fh                NUMBER(12,2),
  changedate        VARCHAR(20),
  version			      VARCHAR(50),
  tag				        INT,
  dr                NUMBER DEFAULT 0,
  isupload          INT
);
create table ud_zyxk_lsydzy
(
  ud_zyxk_lsydzyid  INTEGER,
  ud_zyxk_zysqid    VARCHAR(50),
  lsydyt            VARCHAR(100),
  gzdy              VARCHAR(100),
  dyjrd             VARCHAR(100),
  sbfh              NUMBER(16,2),
  tag				        INT,
  changedate        VARCHAR(20),
  dr                NUMBER DEFAULT 0,
  isupload          INT
);