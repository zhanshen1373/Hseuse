package com.hd.hse.dc.business.web.zyxk;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dc.business.common.weblistener.up.UpListenerNew1;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UpCarXkzInfo extends UpListenerNew1 {

    private static Logger logger = LogUtils.getLogger(UpZYXKInfoNew1.class);
    private static List<TableDesc> listTableRelation = null;
    private StringBuilder strid = new StringBuilder();
    private StringBuilder stridNoBack = new StringBuilder();
    /**
     * upStrid:TODO(记录上传主表数据的主键).
     */
    private String upStrid = null;
    /**
     * yn2016/10/11
     * 获取imei所需要的上下文
     */
    private Context context;

    public UpCarXkzInfo(Context context) {
        this.context = context;
    }

    @Override
    public List<TableDesc> getRelation() {
        // 配置下载Json表的关系
        if (null == listTableRelation) {
            listTableRelation = new ArrayList<TableDesc>();

            TableDesc tb = null;
            // 作业申请表
            tb = new TableDesc();
            tb.setTableName("ud_zyxk_car_xkz");
            tb.setPrimarykey("ud_zyxk_car_xkzid");
            listTableRelation.add(tb);
        }
        return listTableRelation;
    }

    @Override
    public HashMap<String, String> getUpDateSql() {
        StringBuilder sbsql = new StringBuilder();
        HashMap<String, String> hashsql = new HashMap<String, String>();

        String mac = SystemProperty.getSystemProperty().getPadmac();
        if (StringUtils.isEmpty(mac)) {
            mac = "mac失败";
        }
        String imei = getIMEI(context);
        if (StringUtils.isEmpty(imei)) {
            imei = "imei失败";
        }


        // 车辆许可证表 UD_ZYXK_CAR_XKZ
        sbsql.append(
                "select ud_zyxk_car_xkzid,deptunit,deptunit_desc,carunit,driver,carnumber,jhr,enterposition,enterreason,entertime," +
                        "zxaqcscode,zxaqcsdesc,bcaqcs,sqr,sdmanager,hseengineer,routepicturepath,")
                .append(" 'insert' as v_action,ud_zyxk_car_xkzid as uuid from ud_zyxk_car_xkz;");
        hashsql.put("ud_zyxk_car_xkz", sbsql.toString());
        sbsql.delete(0, sbsql.length());


        return hashsql;


    }

    @Override
    public Object initParam() throws HDException {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
                .getSystemProperty().getLoginPerson().getPersonid());
        String dept = SystemProperty.getSystemProperty().getLoginPerson()
                .getDepartment();
        if (!StringUtils.isEmpty(dept)) {
            hashmap.put(PadInterfaceRequest.KEYDEPTNUM, dept);
        }
        //得到imei并上传
        String imei = getIMEI(context);
        if (imei != null) {
            hashmap.put(PadInterfaceRequest.KEYIMEI, imei);
        }
        return hashmap;
    }

    /**
     * 得到imei
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        // TODO Auto-generated method stub
        return PadInterfaceContainers.METHOD_CAR_XKZ;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        try {
            return super.action(action, args);
        } catch (HDException e) {
            getLogger().error(e.getMessage(), e);
            this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
        }
        return 0;
    }

    @Override
    public void beforUpDataInfo(Object... args) throws HDException {
        // TODO Auto-generated method stub
        super.beforUpDataInfo(args);
    }

    @Override
    public void afterUploadDataError(String error) throws HDException {
        // 将错误信息插入到数据库中
        List<String> listsql = new ArrayList<String>();
        if (!StringUtils.isEmpty(upStrid)) {
            String tablename = "ud_zyxk_zysq";
            String[] str = upStrid.split(",");
            StringBuilder sbsql = new StringBuilder();
            listsql.add("delete from hse_sys_record_error where tablename='"
                    + tablename + "' and tableid in (" + upStrid + ");");
            for (int i = 0; i < str.length; i++) {
                sbsql.delete(0, sbsql.length());
                sbsql.append(
                        "insert into hse_sys_record_error(tableid,tablename,tag)values(")
                        .append(str[i]).append(",'").append(tablename)
                        .append("','1');");
                listsql.add(sbsql.toString());
            }
        }
        if (listsql.size() > 0) {
            this.execute(listsql);
        }
    }


    /**
     * getStrZYSQID:(获取要处理票的主键). <br/>
     * date: 2014年9月25日 <br/>
     *
     * @return
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    private String getDealZYSQID(String action, String zysqid)
            throws HDException {
        StringBuilder sbstr = new StringBuilder();
        String sql = null;
        // select
        if ("select".equals(action)) {
            sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where (status='NULLIFY' and spstatus='OVERSTATE') or (status='CQCLOSE' and spstatus='OVERCLOSE');";
        } else if ("delete".equals(action)) {
            // 判断是否全部删除
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            boolean rel = relation
                    .isHadRelative(IRelativeEncoding.UPDATAHANDLE);
            // HashMap<String, Object> hashMapReuslt;
            // hashMapReuslt = (HashMap<String, Object>) this
            // .query("select sys_value,Input_value from sys_relation_info where ifnull(dr,0)=0 and ifnull(isqy,1)=1 and sys_type='"
            // + IRelativeEncoding.UPDATAHANDLE + "';",
            // new MapResult());
            // if (null != hashMapReuslt && hashMapReuslt.size() > 0) {
            if (rel) {
                sql = " select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where ud_zyxk_zysqid in ("
                        + zysqid + ");";
            } else {
                sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where status in ('CLOSE','CAN','WAPPR','CQCLOSE','REMOTE','NULLIFY','"
                        + IWorkOrderStatus.APPAUDITED
                        + "') and ud_zyxk_zysqid in (" + zysqid + ");";
            }
        }
        // close:关闭;CAN:取消; WAPPR:退回(票初始状态);CQCLOSE:超期关闭;APPR：审批中;NULLIFY:作废
        // 应该是STATUS='CQCLOSE' spstatus='OVERCLOSE'超期关闭/ STATUS='NULLIFY'
        // spstatus='OVERSTATE'自动作废

        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) this
                .query(sql, new MapListResult());
        if (null != result && result.size() > 0) {
            for (HashMap<String, Object> map : result) {
                // 取大写数据
                if (map.containsKey("UD_ZYXK_ZYSQID")) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID").toString())
                            .append("',");
                }
                // 取小写数据
                if (map.containsKey("UD_ZYXK_ZYSQID".toLowerCase())) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID".toLowerCase())
                                    .toString()).append("',");
                }

            }
        }
        if (sbstr.length() > 1) {
            sbstr.delete(sbstr.length() - 1, sbstr.length());
            return sbstr.toString();
        }
        return null;
    }

    /**
     * getDealBackZYSQID:(获取作退货作业票的主键信息). <br/>
     * date: 2014年12月4日 <br/>
     *
     * @param zysqid
     * @return
     * @throws HDException
     * @author lxf
     */
    private String getDealBackZYSQID(String zysqid) throws HDException {
        StringBuilder sbstr = new StringBuilder();
        String sql = null;
        sql = "select UD_ZYXK_ZYSQID from UD_ZYXK_ZYSQ where status ='WAPPR'  and ud_zyxk_zysqid in ("
                + zysqid + ");";
        @SuppressWarnings("unchecked")
        List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) this
                .query(sql, new MapListResult());
        if (null != result && result.size() > 0) {
            for (HashMap<String, Object> map : result) {
                // 取大写数据
                if (map.containsKey("UD_ZYXK_ZYSQID")) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID").toString())
                            .append("',");
                }
                // 取小写数据
                if (map.containsKey("UD_ZYXK_ZYSQID".toLowerCase())) {
                    sbstr.append("'")
                            .append(map.get("UD_ZYXK_ZYSQID".toLowerCase())
                                    .toString()).append("',");
                }

            }
        }
        if (sbstr.length() > 1) {
            sbstr.delete(sbstr.length() - 1, sbstr.length());
            return sbstr.toString();
        }
        return null;
    }

    /**
     * getDBimageInfo:(读取数据库照片信息). <br/>
     * date: 2014年12月4日 <br/>
     *
     * @param zysqid
     * @return
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings({"unchecked"})
    private List<HashMap<String, Object>> getDBimageInfo()
            throws HDException {
        String sql = "select ud_zyxk_car_xkzid as tabletpicture,routepicturepath as imagepath from ud_zyxk_car_xkz;";


        List<HashMap<String, Object>> maplist = null;
        maplist = (ArrayList<HashMap<String, Object>>) this.query(sql,
                new MapListResult());

        return maplist;
    }


    /**
     * 根绝路径返回文件名
     *
     * @param path 路径
     * @return 文件名
     */
    private String getFileName(String path) {
        String filename;
        int start = path.lastIndexOf("/");
        int end = path.length();
        if (start != -1 && end != -1) {
            filename = path.substring(start + 1, end);
        } else {
            filename = "hd_zhangjie.jpg";
        }
        return filename;
    }


    @Override
    public void afterUploadDataInfo(Object pcdata) throws HDException {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterFileInfo(Object pcdata, Object... obj) throws HDException {


    }

    @Override
    public List<HashMap<String, HashMap<String, Object>>> getFilePathInfo() {
        // TODO Auto-generated method stub
        List<HashMap<String, Object>> maplist = null;
        List<HashMap<String, HashMap<String, Object>>> listImage = new ArrayList<HashMap<String, HashMap<String, Object>>>();
        HashMap<String, HashMap<String, Object>> imagehash;
        HashMap<String, Object> mapwhere;
        String filename;
        try {
            maplist = getDBimageInfo();
            if (null != maplist) {
                for (HashMap<String, Object> hash : maplist) {
                    // 表示取出每一行
                    Iterator<Map.Entry<String, Object>> iter = hash.entrySet()
                            .iterator();
                    String path = null;
                    mapwhere = new HashMap<String, Object>();
                    imagehash = new HashMap<String, HashMap<String, Object>>();
                    while (iter.hasNext()) {
                        Map.Entry<String, Object> entry = iter.next();
                        if (entry.getKey().equalsIgnoreCase("imagepath")) {
                            path = Environment.getExternalStorageDirectory() + File.separator + "zyxkapp" + File.separator + "carxkz" +
                                    File.separator + entry.getValue().toString();
                        }

                        if (entry.getKey().equalsIgnoreCase("tabletpicture")) {

//                            mapwhere.put(PadInterfaceRequest.TABLEID,
//                                    entry.getValue());
                            //车辆许可证表关联图片的字段
                            mapwhere.put(PadInterfaceRequest.UPLOADPICTUREID,
                                    entry.getValue());
                        }

                    }
                    if (null != path && path.length() > 0) {
                        filename = getFileName(path);
                        // 文件名
                        mapwhere.put(PadInterfaceRequest.KEYFILENAME, filename);
                        // appName
                        mapwhere.put(PadInterfaceRequest.KEYAPPNAME, "appclxkz");
                        // MAC地址
                        mapwhere.put(PadInterfaceRequest.KEYMAC, SystemProperty
                                .getSystemProperty().getPadmac());
                        imagehash.put(path, mapwhere);
                        listImage.add(imagehash);
                        // imagehash.clear();
                    }
                }
            }
        } catch (HDException e) {
            setWritelog("读取数据库报错:" + e.getMessage());
        }
        return listImage;
    }


}
