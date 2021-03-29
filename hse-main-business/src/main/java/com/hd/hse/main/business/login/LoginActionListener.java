/**
 * Project Name:hse-cbs-app
 * File Name:LoginActionListener.java
 * Package Name:com.hd.hse.cbs.business.login
 * Date:2014年9月3日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.main.business.login;

import android.os.Message;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.listener.basicdata.BasicDataInit;
import com.hd.hse.dc.business.listener.basicdata.LoginDataUpdate;
import com.hd.hse.dc.business.listener.common.PCCheckUser;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.RecentlyLoginPerson;
import com.hd.hse.service.checkdata.CheckDataConfig;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:LoginActionListener (登录业务处理).<br/>
 * Date: 2014年9月3日 <br/>
 *
 * @author lg
 * @see
 */
public class LoginActionListener extends AbstractActionListener {
    private boolean isRememberPWD;
    private boolean isAutoLogin;

    private LoginDataUpdate dataUpdate; // 更新基础数据
    private BasicDataInit dataInit; // 初始化基础数据

    private static Logger logger = LogUtils
            .getLogger(LoginActionListener.class);
    public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
    private BusinessAction action = new BusinessAction();
    private CheckDataConfig checkDataConfig = new CheckDataConfig();

    @Override
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        // 登录
        if (IActionType.LOGIN_LOGIN.equals(action)) {
            return login(args[0]);
        }
        return super.action(action);
    }

    /**
     * login:(登录验证). <br/>
     * date: 2014年9月3日 <br/>
     *
     * @param user
     * @throws AppException
     * @author lg
     * @return登录用户信息
     */
    private PersonCard login(Object user) {
        PersonCard psnCard = (PersonCard) user;
        isRememberPWD = psnCard.isRememberPWD();
        isAutoLogin = psnCard.isAutologin();
        PersonCard clonePsn = null;
        boolean nullFlag = false;
        try {
            isCancel = false;
            // 本地验证
            PersonCard loginPsn = localChk(psnCard);
            clonePsn = localChk(psnCard);
            // 空库时，远程验证
            if (loginPsn == null || loginPsn.getUuid() == null
                    || loginPsn.getUuid().isEmpty()) {
                // if (nullFlag) {
                loginPsn = remoteLogin(psnCard);
                // }
            }
            if (loginPsn == null) {
                throw new AppException("用户名或密码错误，请确认！");
            } else { // 登录成功，记录登录人
                SystemProperty.getSystemProperty().setLoginPerson(loginPsn);
                // 保存登录人信息
                if (isCancel) {
                    isCancel = false;
                    return null;
                }
                updateRtyLoginInfo(loginPsn);
                if (isCancel) {
                    isCancel = false;
                    return null;
                }
                // 空库和非空库时，更新基础数据
                nullFlag = isNullDB();
                if (nullFlag) {
                    // 空库执行初始化
                    initDownLoadData();
                } else {
                    // 非空库执行更新
                    remoteDownloadData();
                }

                // 成功登录
                if (isCancel) {
                    isCancel = false;
                    return null;
                }

                sendAsyncLoginSusMsg();
                return loginPsn;
            }
        } catch (AppException e) {
            if (clonePsn == null) {
                logger.error("登录验证出错：" + e.getMessage(), e);
                sendAsyncErrMsg(e.getMessage());
            } else {
                clonePsn.setUuid("default_uuid");
                SystemProperty.getSystemProperty().setLoginPerson(clonePsn);
                // 保存登录人信息
                try {
                    updateRtyLoginInfo(clonePsn);
                    // 空库和非空库时，更新基础数据
                    nullFlag = isNullDB();
                    if (nullFlag) {
                        // 空库执行初始化
                        initDownLoadData();
                    } else {
                        // 非空库执行更新
                        remoteDownloadData();
                    }
                    // 成功登录
                    sendAsyncLoginSusMsg();
                    return clonePsn;
                } catch (AppException e1) {
                    logger.error("登录验证出错：" + e.getMessage(), e);
                    sendAsyncErrMsg(e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * localChk:(本地验证). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @param psnCard
     * @return
     * @throws AppException
     * @author lg
     */
    private PersonCard localChk(PersonCard psnCard) throws AppException {
        PersonCard loginPsn = null;
        StringBuilder sbSql = new StringBuilder();
        BaseDao dao = new BaseDao();
        try {
            String psnID = psnCard.getPersonid();// 人员编号
            String pwd = psnCard.getPassword();// 密码
            String cardNum = psnCard.getPcardnum();// 人员卡卡号
            //记录的是移动大厅的
            String encryptParams = psnCard.getEncryptParams();
            sbSql.append("select * from ud_zyxk_ryk where islogin = 1 and iscan = 1");

            if (encryptParams != null) {
                //移动大厅
                // 通过人员卡登录
                if (!StringUtils.isEmpty(cardNum)) {
                    String cardNumMD5 = "";
                    // 判断是否需要对人员卡加密
                    if (ISCARDISMD5()) {
                        cardNumMD5 = UtilTools.string2MD5(cardNum);
                        sbSql.append(" and (pcardnum='").append(cardNum)
                                .append("' COLLATE NOCASE or pcardnum='").append(cardNumMD5)
                                .append("' COLLATE NOCASE )");
                    } else {
                        sbSql.append(" and pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                    }
                }
                // 通过用户名、密码登录
                else if (!StringUtils.isEmpty(psnID)) {
                    sbSql.append(" and personid='").append(psnID).append("'");
                }

            } else {
                //正常
                // 通过人员卡登录
                if (!StringUtils.isEmpty(cardNum)) {
                    String cardNumMD5 = "";
                    // 判断是否需要对人员卡加密
                    if (ISCARDISMD5()) {
                        cardNumMD5 = UtilTools.string2MD5(cardNum);
                        sbSql.append(" and (pcardnum='").append(cardNum)
                                .append("' COLLATE NOCASE or pcardnum='").append(cardNumMD5)
                                .append("' COLLATE NOCASE )");
                    } else {
                        sbSql.append(" and pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                    }
                }
                // 通过用户名、密码登录
                else if (!StringUtils.isEmpty(psnID) && !StringUtils.isEmpty(pwd)) {
                    sbSql.append(" and personid='").append(psnID).append("'");
                    sbSql.append(" and (password='").append(pwd).append("'");
                    sbSql.append(" or password is null)");
                } else {
                    throw new AppException("请输入用户名、密码或采用刷卡登录！");
                }


            }


//			else if (!StringUtils.isEmpty(psnID) && !StringUtils.isEmpty(pwd)) {
//                sbSql.append(" and personid='").append(psnID).append("'");
//                sbSql.append(" and (password='").append(pwd).append("'");
//                sbSql.append(" or password is null)");
//            } else {
//                throw new AppException("请输入用户名、密码或采用刷卡登录！");
//            }
            loginPsn = (PersonCard) dao.executeQuery(sbSql.toString(),
                    new EntityResult(PersonCard.class));
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error("登录验证出错：" + e.getMessage(), e);
            throw new AppException("登录验证失败，请联系系统管理员！");
        }
        return loginPsn;
    }

    /**
     * isNullDB:(判断是否为空库). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @return
     * @throws AppException
     * @author lg
     */
    @SuppressWarnings("unchecked")
    private boolean isNullDB() throws AppException {
        StringBuilder selSql = new StringBuilder();
        boolean nullFlag = false;
        selSql.append("select count(personid) as psncount from ud_zyxk_ryk where personid!='0001';");
        BaseDao dao = new BaseDao();
        try {
            HashMap<String, Integer> map = (HashMap<String, Integer>) dao
                    .executeQuery(selSql.toString(), new MapResult());
            if (map == null || map.get("psncount") == 0) {
                nullFlag = true;
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new AppException("登录验证失败，请联系系统管理员！");
        }
        return nullFlag;
    }

    /**
     * remoteLogin:(远程登录验证). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @param psnCard
     * @return
     * @throws AppException
     * @author lg
     */
    private PersonCard remoteLogin(PersonCard psnCard) throws AppException {
        PersonCard loginPsn = null;
        try {
            // 判断是否需要对人员卡加密
            // lxf add
            String cardNum = psnCard.getPcardnum();// 人员卡卡号
            if (!StringUtils.isEmpty(cardNum)) {
                if (ISCARDISMD5()) {
                    String cardNumMD5 = "";
                    cardNumMD5 = UtilTools.string2MD5(cardNum);
                    psnCard.setPcardnum(cardNumMD5);
                }
            }
            sendAsyncProcessingMsg("远程登录验证");
            PCCheckUser pcChkUser = new PCCheckUser();
            pcChkUser.setAsyncTask(getAsyncTask());
            loginPsn = (PersonCard) pcChkUser.action(IActionType.LOGIN_LOGIN,
                    psnCard);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e.getMessage());
        }
        return loginPsn;

    }

    /**
     * ISCARDISMD5:(ren yuan kai shi fou jia mi). <br/>
     * date: 2015年6月5日 <br/>
     *
     * @return
     * @author zhaofeng
     */
    private boolean ISCARDISMD5() {
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.CARDISMD5);
        relationEntity.setSys_fuction(null);
        relationEntity.setSys_value("");
        if (relativeConfig.isHadRelative(relationEntity)) {

            return true;
        }
        return false;
    }

    /**
     * updateRtyLoginInfo:(保留最新登录5人记录). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @param psnCard
     * @throws AppException
     * @author lg
     */
    private void updateRtyLoginInfo(PersonCard psnCard) throws AppException {
        boolean delFlag = true;// 是否需要删除历史记录
        int maxNum = 5;// 最大历史记录条数
        int hisOrder = 5;// 相同历史记录的顺序号
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            if (!isSavedUserInfo(psnCard.getPersonid())[0].equals("")) {
                psnCard.setPassword(isSavedUserInfo(psnCard.getPersonid())[0]);
                if (isSavedUserInfo(psnCard.getPersonid())[1].equals("1")) {
                    psnCard.setAutoLogin(true);
                }
            }
            // 历史登录记录中若存在此人，则先删除
            StringBuilder selSql = new StringBuilder();
            selSql.append("select * from hse_sys_login where usercode = '")
                    .append(psnCard.getPersonid()).append("'");
            BaseDao dao = new BaseDao();
            RecentlyLoginPerson hisLoginPsn = (RecentlyLoginPerson) dao
                    .executeQuery(selSql.toString(), new EntityResult(
                            RecentlyLoginPerson.class));
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();

            if (hisLoginPsn != null) {
                hisOrder = hisLoginPsn.getSysorder();
                StringBuilder delSql = new StringBuilder();
                delSql.append("delete from hse_sys_login where usercode = '")
                        .append(psnCard.getPersonid()).append("'");
                dao.executeUpdate(con, delSql.toString());
                delFlag = false;
            }
            // 历史顺序+1
            StringBuilder updateSql = new StringBuilder();
            updateSql
                    .append("update hse_sys_login set sysorder = sysorder + 1 where sysorder <= ")
                    .append(hisOrder);
            dao.executeUpdate(con, updateSql.toString());
            // 删除顺序大于5
            if (delFlag) {
                StringBuilder delSql = new StringBuilder();
                delSql.append("delete from hse_sys_login where sysorder > ")
                        .append(maxNum);
                dao.executeUpdate(con, delSql.toString());
            }
            // 保存最新登录人
            RecentlyLoginPerson newLoginPsn = new RecentlyLoginPerson();
            newLoginPsn.setUsercode(psnCard.getPersonid());
            newLoginPsn.setUsername(psnCard.getPersonid_desc());
            newLoginPsn.setSysorder(1);
            newLoginPsn.setCreatedate(SystemProperty.getSystemProperty()
                    .getSysDateTime());
            newLoginPsn.setUuid(psnCard.getUuid());

            if (isRememberPWD) {
                newLoginPsn.setPassword(psnCard.getPassword());
            } else {
                newLoginPsn.setPassword("");
            }

            if (isAutoLogin) {
                newLoginPsn.setAutologin(1);
            } else {
                newLoginPsn.setAutologin(0);
            }

            dao.insertEntity(con, newLoginPsn);
            con.commit();
        } catch (DaoException e) {
            logger.error("保存历史登录信息出错：" + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("保存历史登录信息出错：" + e.getMessage(), e);
        } finally {
            if (conSrc != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
            }
        }
    }

    /**
     * 数据库中是否存有当前用户
     * <p>
     * Created by wdf on 2016年4月15日
     */
    private String[] isSavedUserInfo(String personid) {
        String[] userinfo = null;
        String userPassword = "";
        String autologin = "" + 0;
        String[] str = {"usercode", "password", "autologin"};
        try {
            List<RecentlyLoginPerson> rlpList = (List) action.queryEntities(
                    RecentlyLoginPerson.class, str, " usercode = '" + personid
                            + "'");
            int count = rlpList.size();
            if (count > 0) {
                if (rlpList.get(0).getPassword() != null) {
                    userPassword = rlpList.get(0).getPassword();
                    autologin = "" + rlpList.get(0).getAutologin();
                }

            }
        } catch (HDException e) {

        }
        userinfo = new String[]{userPassword, autologin};
        return userinfo;
    }

    /**
     * remoteDownloadData:(更新基础数据，不能影响登录，异常不处理). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author lg
     */
    private void remoteDownloadData() {

        sendAsyncProcessingMsg("更新基础数据");
        dataUpdate = new LoginDataUpdate();
        dataUpdate.setAsyncTask(getAsyncTask());
        try {
            checkDataConfig.updateFail();
            dataUpdate.action(null);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            logger.error("更新基础数据出错：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("更新基础数据出错：" + e.getMessage(), e);
        }

    }

    /**
     * initDownLoadData:(数据初始化). <br/>
     * date: 2014年11月19日 <br/>
     *
     * @author lxf
     */
    public void initDownLoadData() {
        sendAsyncProcessingMsg("初始化基础数据");
        dataInit = new BasicDataInit();
        dataInit.setAsyncTask(getAsyncTask());
        try {
            checkDataConfig.initDataFail();
            dataInit.action(null);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            logger.error("初始化基础数据：" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("初始化基础数据：" + e.getMessage(), e);
        }
    }

    /**
     * sendAsyncProcessingMsg:(登录等待消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @param msg
     * @author lg
     */
    private void sendAsyncProcessingMsg(String msg) {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.PROCESSING;
        message.getData().putString(IActionType.LOGIN_LOGIN, msg);
        getAsyncTask().sendMessage(message);
    }

    /**
     * sendAsyncSusMsg:(登录成功后发送消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author lg
     */
    private void sendAsyncLoginSusMsg() {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.END;
        message.getData().putString(IActionType.LOGIN_LOGIN, "登录成功");
        getAsyncTask().sendMessage(message);
    }

    /**
     * sendAsyncErrMsg:(发送异步错误消息). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @param msg
     * @author lg
     */
    private void sendAsyncErrMsg(String msg) {
        Message message = getAsyncTask().obtainMessage();
        message.what = IMessageWhat.ERROR;
        message.getData().putString(IActionType.LOGIN_LOGIN, msg);
        getAsyncTask().sendMessage(message);
    }

    public void setIsCancel() {
        this.isCancel = true;
        if (dataUpdate != null) {
            dataUpdate.isCancel = true;
        }
        if (dataInit != null) {
            dataInit.isCancel = true;
        }
        sendAsyncErrMsg("登录任务已被取消");
    }

}
