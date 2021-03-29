package com.hd.hse.dc.business.web.wtdj;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dc.business.common.weblistener.up.UpListenerNew2;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * 问题登记上传
 * <p>
 * Created by liuyang on 2016年3月28日
 */
public class UpWtdjInfo extends UpListenerNew2 {
    private static Logger logger = LogUtils.getLogger(UpWtdjInfo.class);
    private List<TableDesc> listTableRelation = null;
    private SuperEntity supetemp;
    private StringBuilder strid = new StringBuilder();
    private List<String> uploadedImgPath = new ArrayList<String>();
    public static final String ISLOADED = "isloaded";

    public Object action(String action, Object... args) throws HDException {
        try {
            strid.delete(0, strid.length());
            if (null != args) {
                for (Object ob : args) {
                    if (ob instanceof List) {
                        @SuppressWarnings("unchecked")
                        List<SuperEntity> listEntity = (List<SuperEntity>) ob;
                        if (null != listEntity && listEntity.size() > 0) {
                            for (SuperEntity supe : listEntity) {
                                strid.append("'");
                                strid.append(supe.getAttribute(supe
                                        .getPrimaryKey()));
                                strid.append("',");
                                supetemp = supe;
                            }
                            if (null != strid && strid.length() > 0) {
                                strid.delete(strid.length() - 1, strid.length());
                            }
                        }
                        break;
                    }
                }
            }
            // 表示上传的id
            if (StringUtils.isEmpty(strid.toString())) {
                throw new HDException("没有传入上传的数据");
            }
            return super.action(action, args);
        } catch (HDException e) {
            getLogger().error(e.getMessage(), e);
            this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
        }
        return 0;
    }

    ;

    @Override
    public List<TableDesc> getRelation() {
        if (null == listTableRelation) {
            listTableRelation = new ArrayList<TableDesc>();
            TableDesc tb = null;
            // 位置卡
            tb = new TableDesc();
            tb.setTableName("ud_cbsgl_rz");
            tb.setPrimarykey("padrzid");
            listTableRelation.add(tb);
        }
        return listTableRelation;
    }

    @Override
    public HashMap<String, String> getUpDateSql() {
        StringBuilder sbsql = new StringBuilder();
        HashMap<String, String> hashsql = new HashMap<String, String>();
        if (supetemp != null) {
            sbsql.append("select * from ud_cbsgl_rz z where z.padrzid in(")
                    .append(strid.toString()).append(")");
        }
        hashsql.put("ud_cbsgl_rz", sbsql.toString());
        return hashsql;
    }

    @Override
    public void afterUploadDataError(String error) throws HDException {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterUploadDataInfo(Object pcdata) throws HDException {
        // 表示上传完图片后执行的动作
        // 表示根据返回结果执行相应的处理

        StringBuilder sql = new StringBuilder();
        if (strid.length() > 0) {
            sql.append("delete from UD_CBSGL_RZ where padrzid in(")
                    .append(strid).append(");");
            try {
                this.execute(sql.toString());
            } catch (HDException e) {
                setWritelog("删除UD_CBSGL_RZ表信息报错:" + e.getMessage());
                throw e;
            }
        }

    }

    @Override
    public void afterFileInfo(Object pcdata, Object... obj) throws HDException {
        // // 表示上传完文件后执行的动作
        List<String> listsuccess = null;
        if (pcdata instanceof List) {
            listsuccess = (List<String>) pcdata;
        }
        if (null != listsuccess) {
            // 删除数据库信息
            deleUpLoadedImgDB(listsuccess);
            // 删除本地文件信息
            deleUpLoadedImg(listsuccess);
        }
        // 删除已上传图片信息
        deleUpLoadedImgDB(uploadedImgPath);
        deleUpLoadedImg(uploadedImgPath);
    }

    /**
     * 删除已上传图片数据库数据
     *
     * @throws HDException
     */
    private void deleUpLoadedImgDB(List<String> listsuccess) throws HDException {
        StringBuilder sbdelete = new StringBuilder();
        List<String> listDelete = new ArrayList<String>();
        // 此处删除文件和删除数据库记录
        for (String sql : listsuccess) {
            sbdelete.append(
                    "delete from hse_sys_image where tablename = 'UD_CBSGL_RZ' and imagepath='")
                    .append(sql).append("';");
            listDelete.add(sbdelete.toString());
            sbdelete.delete(0, sbdelete.length());
        }
        // 删除数据库图片信息
        if (listDelete.size() > 0) {
            try {
                this.execute(listDelete);
            } catch (HDException e) {
                setWritelog("删除数据库图片信息报错:" + e.getMessage());
                throw e;
            }
        }
    }

    /**
     * 删除已上传图片
     */
    private void deleUpLoadedImg(List<String> listsuccess) {
        for (String path : listsuccess) {
            File file = new File(path);
            if (file.exists()) {
                File to = new File(file.getAbsolutePath() + ServerDateManager.getCurrentTimeMillis());
                file.renameTo(to);
                to.delete();
//                file.delete();
            }

            // 删除空文件夹
            String directoryPath = path.substring(0, path.lastIndexOf("/"));
            file = new File(directoryPath);
            if (file.isDirectory()) {
                String[] fileNames = file.list();
                if (fileNames == null || fileNames.length == 0) {
                    File to = new File(file.getAbsolutePath() + ServerDateManager.getCurrentTimeMillis());
                    file.renameTo(to);
                    to.delete();
//                    file.delete();
                }
            }
        }
    }

    @Override
    public List<HashMap<String, HashMap<String, Object>>> getFilePathInfo() {
        // String sql;
        // if (null != strid && strid.length() > 0) {
        // sql =
        // "select replace(imagepath,x'0a','') as imagepath,tableid from hse_sys_image where tablename='ud_cbsgl_rz' and tableid in ("
        // + strid.toString() + ");";
        // } else {
        // sql =
        // "select replace(imagepath,x'0a','') as imagepath,tableid from hse_sys_image where tablename='ud_cbsgl_rz'; ";
        // }
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
                    Iterator<Entry<String, Object>> iter = hash.entrySet()
                            .iterator();
                    String path = null;
                    mapwhere = new HashMap<String, Object>();
                    imagehash = new HashMap<String, HashMap<String, Object>>();
                    boolean isloaded = false;
                    while (iter.hasNext()) {
                        Entry<String, Object> entry = iter.next();
                        if (entry.getKey().equalsIgnoreCase("imagepath")) {
                            path = entry.getValue().toString();
                        }
                        if (entry.getKey().equalsIgnoreCase("tablename")) {
                            mapwhere.put(PadInterfaceRequest.TABLENAME,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("tableid")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.TABLEID,
                                    entry.getValue());
                            mapwhere.put(PadInterfaceRequest.KEYTABLIEID,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("zysqid")) {
                            mapwhere.put(PadInterfaceRequest.KEYZYPSTRID,
                                    entry.getValue());
                        }
                        if (entry.getKey().equalsIgnoreCase("childids")) {
                            mapwhere.put(PadInterfaceRequest.CHILDIDS,
                                    entry.getValue());
                        }
                        // 业务类型
                        if (entry.getKey().equalsIgnoreCase("funcode")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYFUNCODE,
                                    entry.getValue());
                        }
                        // 创建时间
                        if (entry.getKey().equalsIgnoreCase("createdate")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYCREATETIME,
                                    entry.getValue());
                            if (ISLOADED.equals(entry.getValue())) {
                                isloaded = true;
                                // break;
                            }
                        }
                        // 登录拍照人
                        if (entry.getKey().equalsIgnoreCase("createuser")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYPERSONID,
                                    entry.getValue());
                        }
                        // 登陆拍照人姓名
                        if (entry.getKey().equalsIgnoreCase("createusername")) {
                            // 业务id
                            mapwhere.put(PadInterfaceRequest.KEYPERSONDESC,
                                    entry.getValue());
                        }
                    }

                    if (null != path && path.length() > 0) {
                        if (isloaded) {
                            uploadedImgPath.add(path);
                        } else {
                            filename = getFileName(path);
                            // 文件名
                            mapwhere.put(PadInterfaceRequest.KEYFILENAME,
                                    filename);
                            // appName
                            mapwhere.put(PadInterfaceRequest.KEYAPPNAME,
                                    "appcbsrz");
                            // MAC地址
                            mapwhere.put(PadInterfaceRequest.KEYMAC,
                                    SystemProperty.getSystemProperty()
                                            .getPadmac());
                            imagehash.put(path, mapwhere);
                            listImage.add(imagehash);
                        }

                        // imagehash.clear();
                    }
                }
                //Log.e("listImage", listImage.size() + "");
            }
        } catch (HDException e) {
            setWritelog("读取数据库报错:" + e.getMessage());
        }
        return listImage;
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
        return hashmap;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getMethodType() {
        return PadInterfaceContainers.METHOD_COMMON_UPTABLE;
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

    @SuppressWarnings("unchecked")
    private List<HashMap<String, Object>> getDBimageInfo() throws HDException {
        String sql;
        if (null != strid && strid.length() > 0) {
            sql = "select replace(imagepath,x'0a','') as imagepath,tableid,createdate from hse_sys_image where tablename='UD_CBSGL_RZ' and tableid in ("
                    + strid.toString() + ");";
        } else {
            sql = "select replace(imagepath,x'0a','') as imagepath,tableid,createdate from hse_sys_image where tablename='UD_CBSGL_RZ'; ";
        }
        List<HashMap<String, Object>> maplist = null;
        maplist = (ArrayList<HashMap<String, Object>>) this.query(sql,
                new MapListResult());

        return maplist;
    }

    /**
     * 删除文件的方法 Created by liuyang on 2016年3月24日
     *
     * @param file
     */
    private void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            logger.error("文件不存在");
        }
    }

}
