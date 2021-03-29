/**
 * Project Name:hse-cbs-app
 * File Name:SystemProperty.java
 * Package Name:com.hd.hse.pub.system
 * Date:2014��9��2��
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.system;

import android.os.Environment;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.util.SYSConstant;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.common.SysActionAgeConfig;
import com.hd.hse.entity.other.Persongroupteam;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.sys.RoleDetect;
import com.hd.hse.entity.sys.ShouYeDetect;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * ClassName: SystemProperty (系统属性)<br/>
 * date: 2014年9月3日 <br/>
 *
 * @author lg
 */
public class SystemProperty {

    private static Logger logger = LogUtils.getLogger(SystemProperty.class);

    // 启动系统是，给赋值
    public static String rootpath = Environment.getExternalStorageDirectory()
            + File.separator + "zyxkapp";

    private String rootDBpath;

    private String rootVersionPath;

    private String rootImagePath;

    /**
     * rootPhotoPath:TODO(照片路径).
     */
    private String rootPhotoPath;

    private static SystemProperty instance;

    /**
     * loginPerson:TODO(登录人).
     */
    private PersonCard loginPerson;

    /**
     * position:TODO(位置卡，刷位置卡时记录).
     */
    private PositionCard positionCard;
    /**
     * 位置卡，刷位置卡时记录集合
     */
    private List<PositionCard> positionCardList;

    /**
     * webconfig:TODO(上传、下载数据配置).
     */
    private WebConfig webDataConfig;

    /**
     * imageupconfig:TODO(上传、下载图片配置).
     */
    private WebConfig webImageConfig;

    /**
     * padmac:TODO(获取MAC地址).
     */
    private String padmac;

    private List<AppModule> listMainAppModule;
    private boolean mShouye;
    private boolean wShouye;

    private boolean CheckResultIsUpdate;
    //关闭取消功能里的存放环节点的map
    private HashMap<String, Integer> hjdmp;
    private HashMap<String, String> pagetypemp;
    //措施确认点击了审核按钮
    private boolean isClickSHbt;
    //升级apk是否点击了取消按钮
    private boolean isClickCancel;

    private HashMap<String, List> hs;
    private WorkOrder workOrder;


    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public HashMap<String, List> getHs() {
        return hs;
    }

    public void setHs(HashMap<String, List> hs) {
        this.hs = hs;
    }


    public boolean isClickCancel() {
        return isClickCancel;
    }

    public void setClickCancel(boolean clickCancel) {
        isClickCancel = clickCancel;
    }

    public boolean isClickSHbt() {
        return isClickSHbt;
    }

    public void setClickSHbt(boolean clickSHbt) {
        isClickSHbt = clickSHbt;
    }

    public boolean isCheckResultIsUpdate() {
        return CheckResultIsUpdate;
    }

    public void setCheckResultIsUpdate(boolean checkResultIsUpdate) {
        CheckResultIsUpdate = checkResultIsUpdate;
    }


    public HashMap<String, String> getPagetypemp() {
        return pagetypemp;
    }

    public void setPagetypemp(HashMap<String, String> pagetypemp) {
        this.pagetypemp = pagetypemp;
    }

    public HashMap<String, Integer> getHjdmp() {
        return hjdmp;
    }

    public void setHjdmp(HashMap<String, Integer> hjdmp) {
        this.hjdmp = hjdmp;
    }


    private SystemProperty() {

    }

    public static SystemProperty getSystemProperty() {
        if (StringUtils.isEmpty(rootpath)) {
            //
        }
        if (instance == null)
            instance = new SystemProperty();
        return instance;
    }

    public static SystemProperty getSystemProperty(String mrootpath) {
        rootpath = mrootpath;
        if (StringUtils.isEmpty(rootpath)) {
            //
        }
        if (instance == null)
            instance = new SystemProperty();
        return instance;
    }

    /**
     * getRootDBpath:(数据库存放路径). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getRootDBpath() {
        if (StringUtils.isEmpty(rootDBpath)) {
            rootDBpath = rootpath;
        }
        return rootDBpath;
    }

    /**
     * getRootDBpath:(设置数据库存放路径). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public void setRootDBpath(String rootdbPath) {
        rootpath = rootdbPath;
    }

    /**
     * getRootVersionPath:(升级版本保存路径). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getRootVersionPath() {
        if (StringUtils.isEmpty(rootVersionPath)) {
            rootVersionPath = rootpath + File.separator + "version";
        }
        return rootVersionPath;
    }

    /**
     * getRootImagePath:(照片保存路径). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getRootImagePath() {
        if (StringUtils.isEmpty(rootImagePath)) {
            rootImagePath = rootpath + File.separator + "image";
        }
        return rootImagePath;

    }

    /**
     * getRootPhotoPath:(照片路径). <br/>
     * date: 2014年9月13日 <br/>
     *
     * @return
     * @author lg
     */
    public String getRootPhotoPath() {
        if (StringUtils.isEmpty(rootPhotoPath)) {
            rootPhotoPath = rootpath + File.separator + "photo";
        }
        return rootPhotoPath;
    }

    /**
     * getSysDateTime:(系统当前时间). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @author lg
     * @return格式：yyyy-MM-dd HH:mm:ss
     */
    // @SuppressLint("SimpleDateFormat")
    public String getSysDateTime() {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        return dtFormat.format(ServerDateManager.getCurrentDate());
    }

    public String getSysSHDateTime() {
        SimpleDateFormat dtFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH时mm分ss秒", Locale.CHINA);
        return dtFormat.format(ServerDateManager.getCurrentDate());
    }

    public PersonCard getLoginPerson() {
        return loginPerson;
    }

    public void setLoginPerson(PersonCard loginPerson) {
        this.loginPerson = loginPerson;
    }

    @SuppressWarnings("unchecked")
    public WebConfig getWebDataConfig() {
        if (null == webDataConfig) {
            BaseDao dao = new BaseDao();
            // 读取IP
            String sql = "select syscode,sysurl from hse_sys_config where syscode in('inurl','outurl') and enable=1;";
            // 读取url
            String sql2 = "select syscode,maximoplatform,oldplatform from  hse_sys_config_sub where syscode='dataurl';";
            // 读取超时时间
            String sql3 = "select syscode,sysurl from hse_sys_config where syscode='timeout';";
            String url = null;
            HashMap<String, Object> configMainRes = null;
            HashMap<String, Object> confingSubRes = null;
            HashMap<String, Object> confingTimeOut = null;
            try {
                // 读取主表配置
                configMainRes = (HashMap<String, Object>) dao.executeQuery(sql,
                        new MapResult());

                if (null != configMainRes) {
                    Object obj = configMainRes.get("sysurl");
                    if (obj != null) {
                        url = configMainRes.get("sysurl").toString();
                    }
                }
                // 读取子表配置
                confingSubRes = (HashMap<String, Object>) dao.executeQuery(
                        sql2, new MapResult());
                //
                confingTimeOut = (HashMap<String, Object>) dao.executeQuery(
                        sql3, new MapResult());
            } catch (DaoException e) {
                logger.error("查询配置信息报错", e);
            }
            if (StringUtils.isEmpty(url)) {
                logger.error("没有配置访问地址");
            }
            url = "http://" + url + File.separator;
            if (null == confingSubRes || confingSubRes.size() == 0) {
                logger.error("请准确配置hse_sys_config表数据。");
            } else {
                int timeout = 10000;
                if (null != confingTimeOut && confingTimeOut.size() != 0) {
                    timeout = Integer.parseInt(confingTimeOut.get("sysurl")
                            .toString());
                }
                webDataConfig = WebConfig.newInstance();
                webDataConfig.setTimeOut(timeout);
                webDataConfig.setUrl(url
                        + confingSubRes.get("maximoplatform").toString());
                webDataConfig.setClientType(SYSConstant.CLIENTHESSION);
                webDataConfig.setDataAnalyzetype(SYSConstant.ANALYZEJSON);
            }
        }
        return webDataConfig;
    }

    /**
     * clearWebConfig:(清空网络配置属性). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @author lg
     */
    public void clearWebConfig() {
        this.webDataConfig = null;
        this.webImageConfig = null;
    }

    public WebConfig getWebImageConfig() {
        return webImageConfig;
    }

    /**
     * getPadmac:(获取mac地址). <br/>
     * date: 2014年10月10日 <br/>
     *
     * @return
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    public String getPadmac() {
        if (StringUtils.isEmpty(padmac)) {
            HashMap<String, Object> configMac = null;
            BaseDao dao = new BaseDao();
            // 读取IP
            String sql = "select syscode,sysurl from hse_sys_config where syscode ='padmac';";
            // 读取主表配置
            try {
                configMac = (HashMap<String, Object>) dao.executeQuery(sql,
                        new MapResult());
            } catch (DaoException e) {
                logger.error("读取MAC地址报错", e);
            }
            if (null != configMac) {
                Object obj = configMac.get("sysurl");
                if (obj != null) {
                    padmac = configMac.get("sysurl").toString();
                }
            }
            // 如果还是空，此时需要生产
            if (StringUtils.isEmpty(padmac)) {
                padmac = getUUID();
                sql = "insert into hse_sys_config(sysdesc,syscode,sysurl)values('mac地址','padmac','"
                        + padmac + "');";
                IConnectionSource conSrc = null;
                IConnection connection = null;
                try {
                    conSrc = ConnectionSourceManager.getInstance()
                            .getJdbcPoolConSource();
                    connection = conSrc.getConnection();
                    dao.executeUpdate(connection, sql);
                    connection.commit();
                } catch (SQLException e) {
                    logger.error("插入MAC地址报错", e);
                } catch (DaoException e) {
                    logger.error("插入MAC地址报错", e);
                } finally {
                    if (connection != null) {
                        try {
                            conSrc.releaseConnection(connection);
                        } catch (SQLException e) {

                        }
                    }
                }

            }
        }
        return padmac;
    }

    private String getHashValue(HashMap<String, String> hash, String key,
                                boolean enableMaximo) {
        String str = null;
        if (hash.containsKey("syscode")
                && hash.get("syscode").equalsIgnoreCase(key)) {
            if (hash.containsKey("maximoplatform")) {
                str = hash.get("maximoplatform");
            }
        }
        return str;
    }

    public PositionCard getPositionCard() {
        return positionCard;
    }

    public void setPositionCard(PositionCard positionCard) {
        this.positionCard = positionCard;

    }

    /**
     * 得到位置卡集合，需要去除已过期的位置卡
     *
     * @return positionCardList
     */
    public List<PositionCard> getPositionCardList() {
        if (positionCardList != null) {
            for (int i = 0; i < positionCardList.size(); i++) {
                PositionCard mPositionCard = positionCardList.get(i);
                if (StringUtils.isEmpty(mPositionCard.getTxtime())) {
                    // 移除txtime为空的 位置卡
                    positionCardList.remove(mPositionCard);
                    i--;
                    continue;
                }
                long currentTime = ServerDateManager.getCurrentTimeMillis();
                long intervalTime = Long.parseLong(mPositionCard.getTxtime()) * 60 * 1000;
                long skTime = mPositionCard.getSkTime();
                if (currentTime - skTime > intervalTime) {
                    // 移除时间超过Txtime的位置卡
                    positionCardList.remove(mPositionCard);
                    i--;
                }
            }
        }
        return positionCardList;
    }

    /**
     * 刷卡成功后将positionCard加入集合
     *
     * @param positionCard
     */
    public void addPositionCard(PositionCard positionCard) {
        if (positionCardList == null) {
            positionCardList = new ArrayList<PositionCard>();
        }
        // 去除重复的位置卡
        for (PositionCard mPositionCard : positionCardList) {
            if (positionCard.getLocationcardid().equals(
                    mPositionCard.getLocationcardid())) {
                positionCardList.remove(mPositionCard);
                break;
            }
        }
        positionCard.setSkTime(ServerDateManager.getCurrentTimeMillis());
        positionCardList.add(positionCard);
    }

    /**
     * 清除位置卡缓存信息
     */
    public void clearPositionCardList() {
        if (positionCardList != null) {
            positionCardList.clear();
            positionCardList = null;
        }

    }


    /**
     * getUUID:(获取唯一值). <br/>
     * date: 2014年9月19日 <br/>
     *
     * @return
     * @author lxf
     */
    private static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    // 获取位置卡超时间
    private static Float locationTimeOut = new Float(-1.0);

    /**
     * getPadmac:(获取位置卡超时时间). <br/>
     * date: 2014年11月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public static Float getLocationTimeOut() {
        if (locationTimeOut == -1) {
            QueryWorkInfo queryWorkInfo = new QueryWorkInfo();
            try {
                SysActionAgeConfig sysaction = queryWorkInfo
                        .querySysActionAgeConfig("BACK_SERVICE_AUTO_TIMEOUT_LOCATION_EXITE");
                if (sysaction != null) {
                    locationTimeOut = sysaction.getSx();
                }
            } catch (HDException e) {
                logger.error("读取位置卡超时时间出错", e);
            }
        }
        return locationTimeOut;
    }

    // 获取锁屏超时时间
    private static Float LockScreenExiteTimeOut = new Float(-1.0);

    /**
     * getPadmac:(获取锁屏超时时间). <br/>
     * date: 2014年11月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public static Float getTimeoutExiteTime() {
        if (LockScreenExiteTimeOut == -1.0) {
            QueryWorkInfo queryWorkInfo = new QueryWorkInfo();
            try {
                SysActionAgeConfig sysaction = queryWorkInfo
                        .querySysActionAgeConfig("BACK_SERVICE_AUTO_TIMEOUT_EXITE");
                if (sysaction != null) {
                    LockScreenExiteTimeOut = sysaction.getSx();
                }
            } catch (HDException e) {
                logger.error("读取锁屏超时时间出错", e);
            }

        }
        return LockScreenExiteTimeOut;
    }

    /**
     * getAppModulelist:(获取公共模块信息). <br/>
     * date: 2015年1月7日 <br/>
     *
     * @return
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    public List<AppModule> getMainAppModulelist(String type) {
        // if (listMainAppModule == null) { //添加缓存机制后会导致菜单权限管理初始化以后不能获取到最新的数据
        listMainAppModule = new ArrayList<AppModule>();
        try {
            BusinessAction action = new BusinessAction();

            IQueryRelativeConfig config = new QueryRelativeConfig();
            RelationTableName newRelaction = config
                    .getRelativeObj(IRelativeEncoding.SHOWMODELSTR);
            String functionStr = "";
            if (newRelaction != null
                    && !StringUtils.isEmpty(newRelaction.getSys_fuction())) {
                functionStr = UtilTools.convertToSqlString(newRelaction
                        .getSys_fuction());
            }
            action = new BusinessAction();
            String sql = "enabled = 1 and type='" + type + "' ";
            if (!StringUtils.isEmpty(functionStr)) {
                sql += " and (modelnum in " + functionStr
                        + " or ifnull(modelnum,'')='') ";
            }
            sql += " order by layoutorder asc;";
            // 数据库中注册的模块
            List<SuperEntity> tempListSuper = action.queryEntities(
                    AppModule.class, null, sql);
            List temp = tempListSuper;
            listMainAppModule = temp;
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
        // }

        if (type.equals("SJ")) {
            // 获取标志位判断是否启用PAD端菜单权限控制
            // Modify by liuyang on 2016/3/18
            QueryRelativeConfig config = new QueryRelativeConfig();
            RelationTableName tableName = new RelationTableName();
            tableName.setSys_type(IRelativeEncoding.ISMENUCTRL);
            if (config.isHadRelative(tableName)) {
                // 查询菜单权限的人员组
                // String sql =
                // "select b.persongroup as persongroup from persongroupteam a left join persongroup b where resppartygroup = '"
                // + getLoginPerson().getPersonid()
                // +
                // "' and a.persongroup = b.persongroup and b.ismenuctrl = 1;";
                try {
                    // List persongroupList = (List) dao.executeQuery(sql,
                    // new MapListResult());
                    BaseDao dao = new BaseDao();
                    List<AppModule> appModules = null;
                    List<RoleDetect> roleDetect = null;
                    // if (persongroupList == null || persongroupList.size() ==
                    // 0) {
                    // // 如果该人员没有人员组，则根据部门查询菜单权限
                    // sql =
                    // "select distinct a.* from hse_sys_appmodule a left join ud_zyxk_padmenuctrl b"
                    // +
                    // " where a.type = 'SJ' and b.menuctrl like '%'||a.code||'%' and b.isenable = 1 and ','||b.childdept||',' like '%,"
                    // + getLoginPerson().getDepartment() + ",%';  ";
                    // } else {
                    // // 如果有人员组，则根据人员组查询菜单权限
                    // sql =
                    // "select distinct d.* from hse_sys_appmodule d left join ud_zyxk_padmenuctrl c left join persongroupteam a left join persongroup b"
                    // +
                    // " where d.type = 'SJ' and c.menuctrl like '%'||d.code||'%' and c.isenable = 1 and resppartygroup = '"
                    // + getLoginPerson().getPersonid()
                    // +
                    // "' and a.persongroup = b.persongroup and b.ismenuctrl = 1 and c.qxrole like '%'||b.persongroup ||'%';";
                    // }
                    // appModules = (List<AppModule>) dao.executeQuery(sql,
                    // new EntityListResult(AppModule.class));
                    // 根据人员组查询菜单权限
                    String sql = "select distinct d.* from hse_sys_appmodule d left join ud_zyxk_padmenuctrl c left join persongroupteam a left join persongroup b"
                            + " where d.type = 'SJ' and c.menuctrl like '%'||d.code||'%' and c.isenable = 1 and resppartygroup = '"
                            + getLoginPerson().getPersonid()
                            + "' and a.persongroup = b.persongroup and (b.ismenuctrl = 1 or b.ismenuctrl is null) and (c.qxrole like '%'||b.persongroup ||'%' or c.qxrole is null);";
                    appModules = (List<AppModule>) dao.executeQuery(sql,
                            new EntityListResult(AppModule.class));
                    if (appModules == null || appModules.size() == 0) {
                        // 如果人员组里面没有权限，则根据部门查询菜单权限
                        sql = "select distinct a.* from hse_sys_appmodule a left join ud_zyxk_padmenuctrl b"
                                + " where a.type = 'SJ' and b.menuctrl like '%'||a.code||'%' and b.isenable = 1 and ','||b.childdept||',' like '%,"
                                + getLoginPerson().getDepartment() + ",%';  ";
                        appModules = (List<AppModule>) dao.executeQuery(sql,
                                new EntityListResult(AppModule.class));
                    }
                    // 两个查询结果取交集
                    // listMainAppModule.retainAll(appModules);
                    List<AppModule> temps = new ArrayList<>();
                    for (int i = 0; i < listMainAppModule.size(); i++) {
                        for (int j = 0; j < appModules.size(); j++) {
                            if (listMainAppModule.get(i).getCode()
                                    .equals(appModules.get(j).getCode())) {
                                temps.add(listMainAppModule.get(i));
                            }
                        }
                    }
                    listMainAppModule = temps;

                    // 根据人员组查询菜单权限
                    String sqt = "select distinct c.* from  ud_zyxk_padmenuctrl c left join persongroupteam a left join persongroup b"
                            + " where  c.isenable = 1 and resppartygroup = '"
                            + getLoginPerson().getPersonid()
                            + "' and a.persongroup = b.persongroup and b.ismenuctrl = 1 and c.qxrole like '%'||b.persongroup ||'%';";
                    roleDetect = (List<RoleDetect>) dao.executeQuery(sqt,
                            new EntityListResult(RoleDetect.class));
                    if (roleDetect == null || roleDetect.size() == 0) {
                        // 如果人员组里面没有权限，则根据部门查询菜单权限
                        sqt = "select distinct b.* from  ud_zyxk_padmenuctrl b"
                                + " where  b.isenable = 1 and ','||b.childdept||',' like '%,"
                                + getLoginPerson().getDepartment() + ",%';  ";
                        roleDetect = (List<RoleDetect>) dao.executeQuery(sqt,
                                new EntityListResult(RoleDetect.class));
                    }

                    if (roleDetect != null && roleDetect.size() > 0) {
                        for (RoleDetect rd : roleDetect) {
                            String role = rd.getRole();
                            if (role != null) {
                                if (role.equals("manager")) {
                                    setFlag("管理人员");
                                    break;
                                } else if (("user").equals(role)) {
                                    setFlag("使用人员");
                                }
                            }

                        }
                    }

                    //在UD_ZYXK_PDAPAGE表中查询是否有兰州首页
                    setLanZhouMShouYe(false);
                    setLanZhouWShouYe(false);
                    setBothShouYe(false);
                    String sperson = "select persongroup from persongroupteam where resppartygroup='" + getLoginPerson().getPersonid() + "'";

                    List<Persongroupteam> persongroupteams = (List<Persongroupteam>) dao.executeQuery(sperson,
                            new EntityListResult(Persongroupteam.class));

                    String sy = "select menuctrl,qxrole from ud_zyxk_pdapage where isenable=1";


                    List<ShouYeDetect> shouYeDetects = (List<ShouYeDetect>) dao.executeQuery(sy,
                            new EntityListResult(ShouYeDetect.class));
                    if (persongroupteams != null && persongroupteams.size() > 0 && shouYeDetects != null && shouYeDetects.size() > 0) {
                        for (int i = 0; i < persongroupteams.size(); i++) {
                            for (int j = 0; j < shouYeDetects.size(); j++) {
                                if (shouYeDetects.get(j).getQxrole() != null) {
                                    if (shouYeDetects.get(j).getQxrole().contains(persongroupteams.get(i).getPersongroup())) {
                                        if ("MANPAGE".equals(shouYeDetects.get(j).getMenuctrl())) {
                                            setLanZhouMShouYe(true);
                                            mShouye = true;
                                        } else if ("USEPAGE".equals(shouYeDetects.get(j).getMenuctrl())) {
                                            setLanZhouWShouYe(true);
                                            wShouye = true;
                                        }
                                    }
                                }

                            }

                        }
                        if (mShouye && wShouye) {
                            setBothShouYe(true);
                        }
                        mShouye = false;
                        wShouye = false;

                    }

                } catch (DaoException e) {
                    logger.error(e);
                }
            }
        }
        if (type.equals("SJ")) {
            // 获取标志位判断是否启用虚拟位置卡
            // Modify by liuyang on 2016/3/1
            QueryRelativeConfig config = new QueryRelativeConfig();
            RelationTableName tableName = new RelationTableName();
            tableName.setSys_type(IRelativeEncoding.QYXNWZ);
            if (config.isHadRelative(tableName)) {
                for (AppModule a : listMainAppModule) {
                    if (a.getCode().equals("hse-vp-phone-app")) {
                        a.setEnabled(1);
                    }
                }
            } else {
                for (AppModule a : listMainAppModule) {
                    if (a.getCode().equals("hse-vp-phone-app")) {
                        a.setEnabled(0);
                    }
                }
            }
        }

        selectAppmodule();
        return listMainAppModule;

    }

    private boolean lanZhouWShouYe = false;

    public boolean isLanZhouWShouYe() {
        return lanZhouWShouYe;
    }

    public void setLanZhouWShouYe(boolean lanZhouWShouYe) {
        this.lanZhouWShouYe = lanZhouWShouYe;
    }

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private void selectAppmodule() {

    }

    private boolean lanZhouMShouYe = false;

    public void setLanZhouMShouYe(boolean lanZhouShouYe) {
        this.lanZhouMShouYe = lanZhouShouYe;
    }

    public boolean isLanZhouMShouYe() {
        return lanZhouMShouYe;
    }

    private boolean bothShouYe;

    public void setBothShouYe(boolean bothShouYe) {
        this.bothShouYe = bothShouYe;
    }

    public boolean isBothShouYe() {
        return bothShouYe;
    }
}
