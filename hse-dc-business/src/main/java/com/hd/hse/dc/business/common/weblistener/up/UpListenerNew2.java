package com.hd.hse.dc.business.common.weblistener.up;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.business.util.TableDesc;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.result.MapListResult;
import com.hd.hse.dc.business.common.util.AbstractDataAdapter;
import com.hd.hse.dc.business.common.weblistener.AbsWebListener;
import com.hd.hse.padinterface.AndroidInterface;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;
import com.hd.hse.padinterface.PadInterfaceUpFile;

import org.apache.log4j.Logger;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * created by yangning on 2017/8/15 11:46.
 */

public abstract class UpListenerNew2 extends AbsWebListener {
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
        this.sendMessage(IMessageWhat.PROCESSING, 0, 10, "???????????????,??????????????????");
        // ???????????????????????????
        listfile = getFilePathInfo();
        if (null == listfile || listfile.size() == 0) {
            this.sendMessage(IMessageWhat.PROCESSING, 10, 70, "???????????????,??????????????????");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 10, 30, "???????????????,??????????????????");
            List<String> listsuccess = uploadFile(listfile);
            if (null != listsuccess && null != listfile
                    && listfile.size() != listsuccess.size()) {
                // ????????????????????????
                throw new HDException("?????????????????????" + listsuccess.size() + ";?????????"
                        + (listfile.size() - listsuccess.size()));
            }
            return listsuccess;
        }
        return null;
    }

    /**
     * afterFileInfo:(???????????????????????????). <br/>
     * date: 2015???4???1??? <br/>
     *
     * @param obj
     * @author lxf
     */
    public abstract void afterFileInfo(Object pcdata, Object... obj)
            throws HDException;

    /**
     * beforDown:(???????????????). <br/>
     * date: 2015???3???19??? <br/>
     *
     * @param obj
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    public void beforUpDataInfo(Object... obj) throws HDException {
        listmapRelation = getRelation();
        if (null == listmapRelation || listmapRelation.size() == 0) {
            setWritelog("?????????getRelation???????????????????????????;");
        }
        dataAdapter = this.getAnalyzeDataAdapter();
        if (null == dataAdapter) {
            setWritelog("?????????????????????????????????null,?????????????????????;");
        }
        Object objparam = initParam();
        if (HashMap.class.isInstance(objparam)) {
            hasmap = (HashMap<String, Object>) objparam;
        } else {
            throw new HDException("initParam()???????????????HashMap??????;");
        }
        // ????????????sql??????
        hashlistsql = getUpDateSql();
        if (null == hashlistsql || hashlistsql.size() == 0) {
            setWritelog("????????????getListSql()??????,??????????????????????????????;");
        }
        listinfo = getUpDateInfo(hashlistsql);
        if (null == listinfo || listinfo.size() == 0) {
            throw new HDException("??????????????????????????????!");
        }
    }

    /**
     * down:(????????????). <br/>
     * date: 2015???3???19??? <br/>
     *
     * @param obj
     * @throws HDException
     * @author Administrator
     */
    public Object upDataInfo(Object... objj) throws HDException {
        // ???????????????????????????????????????
        try {
            String upstr = dataChangeFormat(listinfo, listmapRelation);
            // ??????????????????
            // setWritelog(upstr);
            setWriteDubuglog(upstr);
            // logger.error(upstr, null);
            hasmap.put(PadInterfaceRequest.KEYDATA, upstr);
        } catch (JSONException e) {
            setWritelog("??????????????????????????????", e);
            throw new HDException("??????????????????????????????;");
        }
        // ???????????????,??????????????????????????????
        if (null == listfile || listfile.size() == 0) {
            this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "???????????????,??????????????????");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 30, 70, "???????????????,??????????????????");
        }
        Object obj = null;
        try {
            // logger.error(hasmap.toString(), null);
            obj = sClient.action(getMethodType(), hasmap);// client.UpLoadBusiness(hasmap);
        } catch (HDException e) {
            afterUploadDataError("??????????????????");
            throw e;
        }
        return obj;

    }

    /**
     * afterdown:(??????????????????). <br/>
     * date: 2015???3???19??? <br/>
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
            this.sendMessage(IMessageWhat.PROCESSING, 80, 98, "????????????????????????");
        } else {
            this.sendMessage(IMessageWhat.PROCESSING, 70, 80, "????????????????????????");
        }

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
            throw new HDException("????????????:" + response.getSucessfulList().size()
                    + "???;??????:" + response.getFailedList().size() + "???;");
        } else {
            // ???????????????????????????
            afterUploadDataInfo(pcdata);
        }
        this.sendMessage(IMessageWhat.END, 98, 100, "????????????");
    }

    /**
     * ?????????????????????????????????
     */
    private List<String> uploadFile(
            List<HashMap<String, HashMap<String, Object>>> listImage) {
        // ??????????????????
        List<String> listsuccess = new ArrayList<String>();
        for (HashMap<String, HashMap<String, Object>> hasmap : listImage) {
            // ??????key????????????
            Iterator<Map.Entry<String, HashMap<String, Object>>> iter = hasmap
                    .entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, HashMap<String, Object>> entry = iter.next();
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
                    setWritelog("??????????????????:", e);
                }
            }
        }
        return listsuccess;
    }

    /**
     * ??????????????????
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
     * ?????????????????????????????????????????????
     */
    private HashMap<String, List<Map<String, Object>>> getUpDateInfo(
            HashMap<String, String> hashlistSql) throws HDException {
        HashMap<String, List<Map<String, Object>>> update = new HashMap<String, List<Map<String, Object>>>();
        String tablename;
        Iterator<Map.Entry<String, String>> iter = hashlistSql.entrySet()
                .iterator();
        String sql;
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
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
     * GetBytesByImagePath:(??????????????????byte??????). <br/>
     * date: 2015???3???25??? <br/>
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
            setWritelog("??????????????????????????????", e);
            throw new HDException("??????????????????,???????????????");
        }
    }

    /**
     * ???????????????????????? ????????????????????????????????????--???---???
     */
    public abstract List<TableDesc> getRelation();

    /**
     * ?????????????????????SQL??????
     */
    public abstract HashMap<String, String> getUpDateSql();

    /**
     * afterUploadDataError:(???????????????????????????). <br/>
     * date: 2015???3???25??? <br/>
     *
     * @param error
     * @throws HDException
     * @author lxf
     */
    public abstract void afterUploadDataError(String error) throws HDException;

    /**
     * afterUploadDataInfo:(????????????????????????). <br/>
     * date: 2015???3???25??? <br/>
     *
     * @param pcdata
     * @throws HDException
     * @author Administrator
     */
    public abstract void afterUploadDataInfo(Object pcdata) throws HDException;

    /**
     * ????????????????????????
     */
    public abstract List<HashMap<String, HashMap<String, Object>>> getFilePathInfo();

}
