package com.hd.hse.dc.business.common.weblistener.up;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.weblistener.AbsWebListener;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.AndroidInterface;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.padinterface.PadInterfaceUpFile;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class UpListenerNew1 extends AbsWebListener {
    private static Logger logger = LogUtils.getLogger(UpListenerNew1.class);
    protected AbstractDataAdapter dataAdapter = null;
    protected HashMap<String, Object> hasmap = null;
    protected List<TableDesc> listmapRelation;
    HashMap<String, String> hashlistsql;
    List<HashMap<String, HashMap<String, Object>>> listfile = null;
    protected HashMap<String, List<Map<String, Object>>> listinfo = null;

    @Override
    public Object action(String action, Object... args) throws HDException {
        Object obj;
        try {
            super.action(action, args);
            hasmap = null;
            beforeFileInfo(args);
            beforUpDataInfo(args);

            obj = upFileInfo(args);
            afterFileInfo(obj, args);
            obj = upDataInfo(args);
            afterUpDataInfo(obj, args);
        } catch (HDException e) {
            setWritelog(e.getMessage());
            throw e;
        }
        return obj;
    }

    public void beforeFileInfo(Object... obj) {

    }

    public Object upFileInfo(Object... obj) throws HDException {
        this.sendMessage(IMessageWhat.PROCESSING, 0, 10, "链接服务器,请求上传数据");
        // 返回要上传图片信息
        listfile = getFilePathInfo();
        if (null == listfile || listfile.size() == 0) {
            this.sendMessage(IMessageWhat.PROCESSING, 10, 70, "连接服务器,请求上传数据");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 10, 30, "连接服务器,请求上传数据");
            List<String> listsuccess = uploadFile(listfile);
            if (null != listsuccess && null != listfile
                    && listfile.size() != listsuccess.size()) {
                // 表示上传图片报错
                throw new HDException("上传照片成功：" + listsuccess.size() + ";失败："
                        + (listfile.size() - listsuccess.size()));
            }
            return listsuccess;
        }
        return null;
    }

    /**
     * afterFileInfo:(上传图片执行的方法). <br/>
     * date: 2015年4月1日 <br/>
     *
     * @param obj
     * @author lxf
     */
    public abstract void afterFileInfo(Object pcdata, Object... obj)
            throws HDException;

    /**
     * beforDown:(下载前动作). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @param obj
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    public void beforUpDataInfo(Object... obj) throws HDException {
        listmapRelation = getRelation();
        if (null == listmapRelation || listmapRelation.size() == 0) {
            setWritelog("请实现getRelation重写，并赋予相应值;");
        }
        dataAdapter = this.getAnalyzeDataAdapter();
        if (null == dataAdapter) {
            setWritelog("实例化数据适配器对象为null,请检查配置参数;");
        }
        Object objparam = initParam();
        if (HashMap.class.isInstance(objparam)) {
            hasmap = (HashMap<String, Object>) objparam;
        } else {
            throw new HDException("initParam()必须设置为HashMap对象;");
        }

        boolean t = true;
        HashMap<String, Integer> hjdmp = SystemProperty.getSystemProperty().getHjdmp();
        HashMap<String, String> pagetypemp = SystemProperty.getSystemProperty().getPagetypemp();
        if (hjdmp != null) {

            String pauseStatus = pagetypemp.get("PauseStatus");

            if (obj instanceof Object[]) {

                List<WorkOrder> workOrders = (List<WorkOrder>) obj[0];
                for (int i = 0; i < workOrders.size(); i++) {
                    int dataFRyjl = getDataFRyjl(pauseStatus, workOrders.get(i).getUd_zyxk_zysqid());

                    if (hjdmp.get(workOrders.get(i).getUd_zyxk_zysqid()) != null) {
                        Integer integer = hjdmp.get(workOrders.get(i).getUd_zyxk_zysqid());
                        int i1 = integer.intValue();
                        if (i1 != dataFRyjl) {
                            t = false;
                            //删除关闭取消功能里中断/中断结束环节点没一次走完情况下，向zyspryjl表里存的值
                            deleteSqlFRyjl(pauseStatus, workOrders.get(i).getUd_zyxk_zysqid());
                        }
                    }


                }
            }
            SystemProperty.getSystemProperty().setHjdmp(null);
            SystemProperty.getSystemProperty().setPagetypemp(null);
        }


        // 获取上传sql语句
        hashlistsql = getUpDateSql();
        if (null == hashlistsql || hashlistsql.size() == 0) {
            setWritelog("必须实现getListSql()方法,并返回伤处数据的语句;");
        }
        listinfo = getUpDateInfo(hashlistsql);

        if (obj instanceof Object[]) {
            if (hjdmp != null) {
                List<WorkOrder> workOrders = (List<WorkOrder>) obj[0];
                for (int i = 0; i < workOrders.size(); i++) {
                    //是在关闭取消界面过来的才会走
                    //环节点走完的情况
                    if (t) {
                        deleteSqlFRyjl(pagetypemp.get("PauseStatus"), workOrders.get(i).getUd_zyxk_zysqid());
                    }


                }
            }

        }


        if (null == listinfo || listinfo.size() == 0) {
            throw new HDException("没有查询到上传的数据!");
        }
    }


    private int getDataFRyjl(String pausestatus, String udzysqid) {
        BaseDao baseDao = new BaseDao();
        String sql = "select * from ud_zyxk_zyspryjl where dycode='" + pausestatus + "'and ud_zyxk_zysqid='" + udzysqid + "'";
        List<WorkApprovalPersonRecord> workApprovalPersonRecords = null;
        try {
            workApprovalPersonRecords = (List<WorkApprovalPersonRecord>) baseDao.
                    executeQuery(sql, new EntityListResult(WorkApprovalPersonRecord.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (workApprovalPersonRecords != null) {
            return workApprovalPersonRecords.size();
        }
        return -1;
    }

    private void deleteSqlFRyjl(String pausestatus, String udzysqid) {
        BaseDao baseDao = new BaseDao();
        IConnectionSource conSrc = null;
        IConnection connection = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = conSrc.getNonTransConnection();

            String sql = "delete from ud_zyxk_zyspryjl where dycode='" + pausestatus + "'and ud_zyxk_zysqid='" + udzysqid + "'";
            try {
                baseDao.executeUpdate(connection, sql);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conSrc != null) {
                try {
                    conSrc.releaseConnection(connection);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
            }
        }


    }

    /**
     * down:(下载动作). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @param obj
     * @throws HDException
     * @author Administrator
     */
    public Object upDataInfo(Object... objj) throws HDException {
        // 把数据转换成上传需要的格式
        try {
            String upstr = dataChangeFormat(listinfo, listmapRelation);
            // 记录上传信息
            // setWritelog(upstr);
            setWriteDubuglog(upstr);
            // logger.error(upstr, null);
            hasmap.put(PadInterfaceRequest.KEYDATA, upstr);
        } catch (JSONException e) {
            setWritelog("组织上传数据格式报错", e);
            throw new HDException("组织上传数据格式报错;");
        }
        // 上传作业票,注意接收返回结果信息
        if (null == listfile || listfile.size() == 0) {
            this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "连接服务器,请求上传数据");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 30, 70, "连接服务器,请求上传数据");
        }
        Object obj = null;
        try {
            // logger.error(hasmap.toString(), null);
            obj = sClient.action(getMethodType(), hasmap);// client.UpLoadBusiness(hasmap);
        } catch (HDException e) {
            afterUploadDataError("上传数据报错");
            throw e;
        }
        return obj;

    }

    /**
     * afterdown:(下载后的动作). <br/>
     * date: 2015年3月19日 <br/>
     *
     * @param obj
     * @author lxf
     */
    private void afterUpDataInfo(Object pcdata, Object... objj)
            throws HDException {
        PadInterfaceResponse response = null;
        if (pcdata instanceof PadInterfaceResponse) {
            response = (PadInterfaceResponse) pcdata;
        }
        if (null == listfile || listfile.size() == 0) {
            this.sendMessage(IMessageWhat.PROCESSING, 80, 98, "处理本地数据信息");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "处理本地数据信息");
        }
        // 处理上传成功后方法
        afterUploadDataInfo(pcdata);
        if (null != response && response.getFailedList().size() > 0) {
            StringBuilder msg = new StringBuilder();
            for (Map<String, String> listMap : response.getFailedList()) {
                if (listMap.containsKey(PadInterfaceRequest.KEYRETURNMESSAGE)) {
                    msg.append(
                            listMap.get(PadInterfaceRequest.KEYRETURNMESSAGE)
                                    .toString()).append(";");
                }
            }
            setWritelog(msg.toString());
            throw new HDException("上传成功:" + response.getSucessfulList().size()
                    + "条;失败:" + response.getFailedList().size() + "条;");
        }
        this.sendMessage(IMessageWhat.END, 98, 100, "上传成功");
    }

    /**
     * 返回上传成功的图片信息
     */
    private List<String> uploadFile(
            List<HashMap<String, HashMap<String, Object>>> listImage) {
        // 循环上传图片
        List<String> listsuccess = new ArrayList<String>();
        for (HashMap<String, HashMap<String, Object>> hasmap : listImage) {
            // 此时key表示路径
            Iterator<Entry<String, HashMap<String, Object>>> iter = hasmap
                    .entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, HashMap<String, Object>> entry = iter.next();
                String image = entry.getKey();
                HashMap<String, Object> map = entry.getValue();
                try {
                    File file = new File(image);
                    if (!file.exists()) {
                        listsuccess.add(image);
                        continue;
                    }
                    Object obj = sClient.upFile(
                            PadInterfaceContainers.METHOD_COMMON_UPFILE, image,
                            map);
                    if (obj instanceof PadInterfaceResponse) {
                        PadInterfaceResponse response = (PadInterfaceResponse) obj;
                        if (response.getFlag().equalsIgnoreCase("T")) {
                            listsuccess.add(image);
                        }
                    } else {
                        listsuccess.add(image);
                    }
                } catch (Exception e) {
                    setWritelog("上传图片报错:", e);
                }
            }
        }
        return listsuccess;
    }

    /**
     * 表示数据转化
     *
     * @param dateinfo
     * @param relation
     * @return String
     * @throws JSONException
     */
    private String dataChangeFormat(
            HashMap<String, List<Map<String, Object>>> dateinfo,
            List<TableDesc> relation) throws JSONException {
        return dataAdapter.create(dateinfo, relation);
    }

    /**
     * 根据语句查询数据库要上传的数据
     */
    private HashMap<String, List<Map<String, Object>>> getUpDateInfo(
            HashMap<String, String> hashlistSql) throws HDException {
        HashMap<String, List<Map<String, Object>>> update = new HashMap<String, List<Map<String, Object>>>();
        String tablename;
        Iterator<Entry<String, String>> iter = hashlistSql.entrySet()
                .iterator();
        String sql;
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            tablename = entry.getKey();
            sql = entry.getValue();
            @SuppressWarnings("unchecked")
            ArrayList<Map<String, Object>> map = (ArrayList<Map<String, Object>>) this
                    .query(sql, new MapListResult());
            if (map.size() > 0) {
                update.put(tablename, (List<Map<String, Object>>) map);
            }
        }
        return update;
    }

    private void changeImageByte(List<PadInterfaceUpFile> listupfile)
            throws HDException {
        if (listupfile != null) {
            List<PadInterfaceUpFile> listTemp = new ArrayList<PadInterfaceUpFile>();
            for (PadInterfaceUpFile padInterfaceUpFile : listupfile) {
                byte[] imagebyte = (GetBytesByImagePath(padInterfaceUpFile
                        .getImagepath()));
                if (imagebyte == null) {
                    listTemp.add(padInterfaceUpFile);
                } else {
                    padInterfaceUpFile.setImagebyte(imagebyte);
                }
            }
            listupfile.removeAll(listTemp);
        }
    }

    /**
     * GetBytesByImagePath:(将图片转化成byte数组). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @param strFile
     * @return
     * @throws HDException
     * @author lxf
     */
    private byte[] GetBytesByImagePath(String strFile) throws HDException {
        try {
            File file = new File(strFile);
            if (!file.exists()) {
                return null;
            }
            FileInputStream stream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] b = new byte[AndroidInterface.FILE_DOWNLOAD_SIZE];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            setWritelog("组织上传数据格式报错", e);
            throw new HDException("处理图片报错,联系管理员");
        }
    }

    /**
     * 获取上传表的关系 注意：关系顺序是：从孙子--子---父
     */
    public abstract List<TableDesc> getRelation();

    /**
     * 获取上传数据的SQL语句
     */
    public abstract HashMap<String, String> getUpDateSql();

    /**
     * afterUploadDataError:(上传报错执行的方法). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @param error
     * @throws HDException
     * @author lxf
     */
    public abstract void afterUploadDataError(String error) throws HDException;

    /**
     * afterUploadDataInfo:(上传成功后的方法). <br/>
     * date: 2015年3月25日 <br/>
     *
     * @param pcdata
     * @throws HDException
     * @author Administrator
     */
    public abstract void afterUploadDataInfo(Object pcdata) throws HDException;

    /**
     * 获取上传图片路径
     */
    public abstract List<HashMap<String, HashMap<String, Object>>> getFilePathInfo();

}
