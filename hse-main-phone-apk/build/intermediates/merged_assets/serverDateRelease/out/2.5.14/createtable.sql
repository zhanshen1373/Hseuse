CREATE TABLE ud_hsebz_zyxx
(
    ud_hsebz_zyxxid NUMBER not null,
    description     VARCHAR2(50),
    zyno            NUMBER,
    zyname          VARCHAR2(50),
    zycode          VARCHAR2(50),
    zymaxscore      NUMBER,
    pass            NUMBER(16,2),
    ud_hsebz_zymcid VARCHAR2(50),
    zrcs            VARCHAR2(50),
    zrcscode        VARCHAR2(50),
    hasld           NUMBER,
    rowstamp        VARCHAR2(40),
    changedate      DATE,
    tag INT,
    dr INT
);
