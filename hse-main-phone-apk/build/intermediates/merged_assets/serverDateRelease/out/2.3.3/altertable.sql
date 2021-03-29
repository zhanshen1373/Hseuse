ALTER TABLE ud_zyxk_padmenuctrl RENAME TO ud_zyxk_padmenuctrl_temp;
CREATE TABLE ud_zyxk_padmenuctrl(
       ud_zyxk_padmenuctrlid NUMBER not null,
       qxrole VARCHAR2(1000),
       childdept VARCHAR2(1000),
       deptnum VARCHAR2(1000),
       menuctrl VARCHAR2(1000),
       isenable NUMBER not null,
       changedate VARCHAR2(30),
       tag INT,
       dr INT
);
INSERT INTO ud_zyxk_padmenuctrl SELECT * FROM ud_zyxk_padmenuctrl_temp;
DROP TABLE ud_zyxk_padmenuctrl_temp;
ALTER TABLE hse_sys_image ADD childids verchar(50);