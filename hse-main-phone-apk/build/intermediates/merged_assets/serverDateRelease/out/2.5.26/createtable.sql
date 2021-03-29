create table ud_cbsgl_ryline
(
  ud_cbsgl_rylineid NUMBER not null,
  ud_cbsgl_rynum    VARCHAR2(30),
  cbsdjnum          VARCHAR2(30),
  name              VARCHAR2(20),
  ishmd             NUMBER not null,
  sex               VARCHAR2(10),
  worktype          VARCHAR2(50),
  innercard         VARCHAR2(30),
  ygdeptnum_desc    VARCHAR2(50),
  age               NUMBER,
  swtime            DATE,
  starttime         DATE,
  endtime           DATE,
  processstatus     VARCHAR2(30),
  idcard            VARCHAR2(20),
  checkresult       VARCHAR2(30),
  tjresult          VARCHAR2(30),
  trainresult       VARCHAR2(30),
  changedate        DATE,
  dr                NUMBER not null,
  tag INT
)

