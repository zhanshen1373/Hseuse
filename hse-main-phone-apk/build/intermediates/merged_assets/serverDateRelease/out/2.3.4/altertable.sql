ALTER TABLE ud_onemeasure_person ADD zypclass VARCHAR(50);

ALTER TABLE ud_zyxk_zyspqx ADD priorityid NUMBER;

ALTER TABLE ud_zyxk_zysq ADD
  dhzylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  dhlevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxkjyy              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxkjzylx            VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxkjlevel           VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  wjyy                VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  wjzylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  wjlevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxyy                VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxzylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  sxlevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gcyy                VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gczylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gclevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  dzyy                VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  dzzylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  dzlevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gxyy                VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gxzylx              VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  gxlevel             VARCHAR2(100);
ALTER TABLE ud_zyxk_zysq ADD
  dhxz                VARCHAR2(50);

alter table ud_zyxk_zyspqx add relatedzy varchar(50);
alter table UD_ZYXK_ZYSQPDASC add topzypclass VARCHAR(50);

alter table ud_zyxk_zysq2precaution add zyptype varchar(50);

alter table ud_cbsgl_rz add jcdept varchar(50);
alter table ud_cbsgl_rz add jcdept_desc varchar(50);
alter table ud_cbsgl_rz add zyarea varchar(50);
alter table ud_cbsgl_rz add zyarea_desc varchar(50);
alter table ud_cbsgl_rz add sddept varchar(50);
alter table ud_cbsgl_rz add sddept_desc varchar(50);
alter table ud_cbsgl_rz add fzperson varchar(50);
alter table ud_cbsgl_rz add fzperson_desc varchar(50);
alter table ud_cbsgl_rz add zrdept varchar(50);
alter table ud_cbsgl_rz add zrdept_desc varchar(50);
alter table ud_cbsgl_rz add wyj varchar(50);
alter table ud_cbsgl_rz add iszg int;
alter table ud_cbsgl_rz add ishmd int;
alter table ud_cbsgl_rz add isupload int;
alter table ud_cbsgl_rz add dr int;
alter table ud_cbsgl_rz add tag int;
alter table ud_cbsgl_rz add zyname varchar(50);

alter table ud_zyxk_ryk add age varchar(10);
alter table ud_zyxk_ryk add swtime varchar(20);
alter table ud_zyxk_ryk add scdeadline varchar(20);
alter table ud_zyxk_ryk add bxdeadline varchar(20);
alter table ud_zyxk_ryk add htqdtime varchar(20);
alter table ud_zyxk_ryk add processstatus varchar(20);
alter table ud_zyxk_ryk add yxtime varchar(20);
alter table ud_zyxk_ryk add zzdeadline varchar(20);