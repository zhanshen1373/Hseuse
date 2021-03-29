alter table ud_zyxk_zyspqx add jobnum varchar(50);
alter table ud_zyxk_zyspqx add job_desc varchar(200);
alter table ud_zyxk_qtjcsx add isreusable int;
alter table hse_sys_login add uuid text;
alter table ud_zyxk_ryk add uuid text;

ALTER TABLE ud_zyxk_wzk RENAME TO ud_zyxk_wzk_temp;
CREATE TABLE ud_zyxk_wzk (
  ud_zyxk_wzkid nvarchar(50), 
  location nvarchar(50), 
  description nvarchar(50), 
  locationcardid nvarchar(50), 
  location_desc nvarchar(50), 
  changedate nvarchar(19), 
  tag INT, 
  dr INT, 
  txtime nvarchar(19), 
  isVirtualCard int DEFAULT 0, 
  longitude NVARCHAR(50), 
  latitude NVARCHAR(50), 
  radiu int);
INSERT INTO ud_zyxk_wzk SELECT * FROM ud_zyxk_wzk_temp;
DROP TABLE ud_zyxk_wzk_temp;

alter table hse_sys_image add zysqid NVARCHAR(50);
alter table hse_sys_image add updatedate NUMBER;

alter table ud_zyxk_zyspqx add ISDZQM NVARCHAR(20);
