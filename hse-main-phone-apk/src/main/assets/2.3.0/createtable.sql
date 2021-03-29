CREATE TABLE ud_zyxk_padmenuctrl(
       ud_zyxk_padmenuctrlid NUMBER not null,
       qxrole VARCHAR2(1000),
       childdept VARCHAR2(1000),
       deptnum VARCHAR2(1000),
       menuctrl VARCHAR2(1000),
       isenable NUMBER not null,
       changedate DATE,
       tag INT,
       dr INT
);