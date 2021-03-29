/**
 * Project Name:hse-common-module-phone
 * File Name:ShowSignDialog.java
 * Package Name:com.hd.hse.common.module.phone.ui.activity
 * Date:2015年10月20日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.component.phone.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.adapter.SignListViewAdapter;
import com.hd.hse.common.component.phone.custom.PaintSignatureDialog.OnCustomClickListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ShowSignDialog ().<br/>
 * Date: 2015年10月20日 <br/>
 *
 * @author LiuYang
 * @see
 */
public class ShowSignDialog extends Dialog {
    private static final Logger loggle = LogUtils
            .getLogger(ShowSignDialog.class);

    private TextView txtView;
    private GridView txtGrid;
    private GridView imgGrid;
    private WorkApprovalPermission wap;
    private WorkApplyMeasure wam;
    List<Image> signImgUrls;
    private Handler mHandler;
    private SignListViewAdapter listViewAdapter;
    public static final int HANDLER_WHAT = 222;
    private boolean isClick = false;
    private Context mContext;

    public ShowSignDialog(Context context, Handler handler,
                          WorkApprovalPermission wap) {
        super(context, R.style.PaintSignatureDialog);
        this.mContext = context;
        this.mHandler = handler;
        this.wap = wap;
    }

    public ShowSignDialog(Context context, SignListViewAdapter listViewAdapter,
                          WorkApprovalPermission wap) {
        super(context, R.style.PaintSignatureDialog);
        this.mContext = context;
        this.listViewAdapter = listViewAdapter;
        this.wap = wap;
    }

    public ShowSignDialog(Context context, WorkApprovalPermission wap) {
        super(context, R.style.PaintSignatureDialog);
        this.mContext = context;
        this.wap = wap;
    }

    public ShowSignDialog(Context context, WorkApplyMeasure wam) {
        super(context, R.style.PaintSignatureDialog);
        this.mContext = context;
        this.wam = wam;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_component_dialog_show_signature);
        txtView = (TextView) findViewById(R.id.txtview);
        txtGrid = (GridView) findViewById(R.id.txt_grid);
        imgGrid = (GridView) findViewById(R.id.img_grid);
        // String personid = null;
        String persondesc = null;
        if (wap != null) {
            // personid = wap.getPersonid();
            persondesc = wap.getPersondesc();
            if (wap.getIsdzqm() == null || wap.getIsdzqm().equals("0")) {
                txtView.setVisibility(View.GONE);
                txtGrid.setVisibility(View.VISIBLE);
                txtGrid.setAdapter(new TxtGridAdapter(wap));
            } else {
                txtView.setVisibility(View.GONE);
                txtGrid.setVisibility(View.GONE);
                String ud_zyxk_zyspryjlid = wap.getUd_zyxk_zyspryjlid();
                String strzyspryjlid = wap.getStrzyspryjlid();
                signImgUrls = getSignImgUrls(strzyspryjlid, ud_zyxk_zyspryjlid);
                initImageGrid(signImgUrls);
            }
        } else if (wam != null) {
            persondesc = wam.getPersondesc();
            if (persondesc.indexOf("图片签名") < 0) {
                txtGrid.setVisibility(View.GONE);
                txtView.setVisibility(View.VISIBLE);
                txtView.setText(persondesc);
                if (wam.getPersonid().equals(wam.getPersondesc())) {
                    txtView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            if (isClick) {
                                ToastUtils.toast(getContext(), "正在处理，请稍后！");
                                return;
                            }
                            isClick = true;
                            final String oldName = wam.getPersondesc();
                            final EditText input = new EditText(getContext());
                            input.setText(oldName);
                            // 光标移到最后
                            Editable ea = input.getText();
                            input.setSelection(ea.length());
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    getContext());
                            builder.setTitle("修改签字人").setView(input)
                                    .setNegativeButton("取消", null);
                            builder.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface arg0, int arg1) {
                                            String newName = input.getText()
                                                    .toString();
                                            if (newName != null
                                                    && !newName.trim().equals(
                                                    "")) {
                                                updateName(wam.getUd_id(),
                                                        newName);
                                                txtView.setText(newName);
                                            } else {
                                                ToastUtils
                                                        .imgToast(
                                                                getContext(),
                                                                R.drawable.hd_hse_common_msg_wrong,
                                                                "签字人不能为空！");
                                            }
                                        }
                                    });
                            builder.show();
                        }
                    });
                }
            } else {
                IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
                try {
                    String[] tableIds = queryWorkInfo.querySignInfoItemByItem(
                            wam.getUd_zyxk_zysqid(), wam.getPrecautionid());
                    signImgUrls = queryWorkInfo.querySignImgUrls(
                            "ud_onemeasure_person", tableIds);
                    initImageGrid(signImgUrls);
                } catch (HDException e) {
                    loggle.error(e);
                    ToastUtils.imgToast(getContext(),
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
        }
        // if (personid != null) {
        // personid = personid.replaceAll("图片签名,", "");
        // personid = personid.replaceAll("图片签名", "");
        // }
        // if (persondesc != null) {
        // persondesc = persondesc.replaceAll("图片签名,", "");
        // persondesc = persondesc.replaceAll("图片签名", "");
        // }
        // if (personid != null && !personid.equals("") && persondesc != null
        // && !persondesc.equals("")) {
        // String[] persons = personid.split(",");
        // String[] persondescs = persondesc.split(",");
        // if (persons.length == persondescs.length) {
        // txtView.setVisibility(View.GONE);
        // txtGrid.setVisibility(View.VISIBLE);
        // txtGrid.setAdapter(new TxtGridAdapter(persons, persondescs));
        // } else {
        // txtGrid.setVisibility(View.GONE);
        // txtView.setVisibility(View.VISIBLE);
        // txtView.setText(persondesc);
        // }
        // } else {
        // txtView.setVisibility(View.GONE);
        // txtGrid.setVisibility(View.GONE);
        // }
        // initImageGrid(signImgUrls);
    }

    private void initImageGrid(final List<Image> signImgUrls) {
        final List<Image> distinctImgs = distinct(signImgUrls);
        final ImageGridAdapter adapter = new ImageGridAdapter(distinctImgs);
        imgGrid.setAdapter(adapter);
        imgGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view,
                                    int postion, long id) {
                final Image img = distinctImgs.get(postion);
                final List<Image> imgs = getSameImages(signImgUrls,
                        img.getUpdateDate());
                PaintSignatureDialog dialog = new PaintSignatureDialog(
                        getContext(), new OnCustomClickListener() {

                    @Override
                    public void onCustomClick(Bitmap bitmap) {
                        long time = ServerDateManager.getCurrentTimeMillis();
                        for (Image image : imgs) {
                            updateImg(image, bitmap, time);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
            }
        });
    }

    private List<Image> getSignImgUrls(String strzyspryjlid,
                                       String ud_zyxk_zyspryjlid) {
        List<Image> signImgUrls = new ArrayList<Image>();
        String[] zyspryjlids = null;
        if (strzyspryjlid != null && !strzyspryjlid.equals("")) {
            zyspryjlids = strzyspryjlid.split(",");
        } else {
            zyspryjlids = new String[]{ud_zyxk_zyspryjlid};
        }
        IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
        try {
            signImgUrls = queryWorkInfo.querySignImgUrls("ud_zyxk_zyspryjl",
                    zyspryjlids);
        } catch (HDException e) {
            loggle.error(e);
        }
        return signImgUrls;
    }

    private void updateName(String ud_id, String personName) {
        IConnectionSource connectionSource = null;
        IConnection connection = null;
        try {
            BaseDao dao = new BaseDao();
            // 建立JDBC连接
            connectionSource = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = connectionSource.getConnection();
            String sql = "update ud_onemeasure_person set opcode = '"
                    + personName + "',opname = '" + personName
                    + "' where ud_id = '" + ud_id + "'";
            dao.executeUpdate(connection, sql);
            connection.commit();
        } catch (SQLException e) {
            loggle.error(e);
            ToastUtils.imgToast(getContext(),
                    R.drawable.hd_hse_common_msg_wrong, "修改失败！");
        } catch (DaoException e) {
            loggle.error(e);
            ToastUtils.imgToast(getContext(),
                    R.drawable.hd_hse_common_msg_wrong, "修改失败！");
        } finally {
            if (connectionSource != null) {
                try {
                    // 释放连接
                    connectionSource.releaseConnection(connection);
                    isClick = false;
                } catch (SQLException e) {
                    loggle.error(e.getMessage(), e);
                }
            }
        }

    }

    private class TxtGridAdapter extends BaseAdapter {
        private WorkApprovalPermission wap;
        private String[] personids;
        private String[] persiondescs;
        private ArrayList<String> persionidsList;
        private ArrayList<String> persiondescsList;

        TxtGridAdapter(WorkApprovalPermission wap) {
            this.wap = wap;
            this.personids = wap.getPersonid().split(",");
            this.persiondescs = wap.getPersondesc().split(",");
//            List temppersonids = Arrays.asList(personids);
//            List temppersiondescs = Arrays.asList(persiondescs);
//            this.persionidsList = new ArrayList(temppersonids);
//            this.persiondescsList = new ArrayList<>(temppersiondescs);
        }

        @Override
        public int getCount() {
            return
//                    persiondescsList.size();
                    persiondescs.length;
        }

        @Override
        public Object getItem(int arg0) {
            return
//                    persiondescsList.get(arg0);
                    persiondescs[arg0];
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(
                        R.layout.hd_hse_show_sign_dialog_txtgrid_item, null);
                holder = new ViewHolder();
                holder.txtView = (TextView) view.findViewById(R.id.txt);
//                holder.relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout_txt);
//                holder.imageView = (ImageView) view.findViewById(R.id.relativeLayout_image);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
//            holder.txtView.setText(persiondescsList.get(position));
            holder.txtView.setText(persiondescs[position]);

//            if (!holder.txtView.getText().equals("")) {
//                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.hd_hse_phone_message_error));
//
//            } else {
//                holder.imageView.setImageDrawable(null);
//            }

            /*
             * 只有手签的数据才能修改，合并会签的时候有重名的不能修改
			 */
            boolean canEdit = true;

//            if (wap != null && wap.getStrzysqid() != null) {
////             合并会签
//                for (int i = 0; i < persiondescsList.size(); i++) {
//                    if (i != position
//                            && persiondescsList.get(i)
//                            .equals(persiondescsList.get(position))) {
//                        canEdit = false;
//                        break;
//                    }
//                }
//
//            }

            if (persiondescs[position].equals(personids[position])) {
                // 手签的数据
                if (wap != null && wap.getStrzysqid() != null) {
                    // 合并会签
                    for (int i = 0; i < persiondescs.length; i++) {
                        if (i != position
                                && persiondescs[i]
                                .equals(persiondescs[position])) {
                            canEdit = false;
                            break;
                        }
                    }
                }
            } else {
                // 非手签的数据
                canEdit = false;
            }


            if (canEdit) {
                holder.txtView.setTextColor(getContext().getResources()
                        .getColor(R.color.hd_common_module_static_black));



                holder.txtView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (isClick) {
                            ToastUtils.toast(getContext(), "正在处理，请稍后！");
                            return;
                        }
                        isClick = true;
                        final String oldName = persiondescs[position];
                        final EditText input = new EditText(getContext());
                        input.setText(persiondescs[position]);
                        // 光标移到最后
                        Editable ea = input.getText();
                        input.setSelection(ea.length());
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                getContext());
                        builder.setTitle("修改签字人").setView(input)
                                .setNegativeButton("取消", null);
                        builder.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        String newName = input.getText()
                                                .toString();
                                        if (newName != null
                                                && !newName.trim().equals("")) {
                                            if (wap != null) {
                                                if (wap.getStrzyspryjlid() == null) {
                                                    // 不合并
                                                    String zyspjlid = wap
                                                            .getUd_zyxk_zyspryjlid();
                                                    String newPersonId = "";
                                                    String newPersonName = "";
                                                    for (int i = 0; i < personids.length; i++) {
                                                        if (i == position) {
                                                            if (newName != null
                                                                    && !newName.trim().equals("")) {

                                                                newPersonId += i == 0 ? ("'" + newName)
                                                                        : ("," + newName);
                                                                newPersonName += i == 0 ? ("'" + newName)
                                                                        : ("," + newName);
                                                            } else {

                                                                newPersonId += i == 0 ? ("'" + newName)
                                                                        : (newName);
                                                                newPersonName += i == 0 ? ("'" + newName)
                                                                        : (newName);

                                                            }

                                                        } else {

                                                            if (newName != null
                                                                    && !newName.trim().equals("")) {

                                                                newPersonId += i == 0 ? ("'" + personids[i])
                                                                        : ("," + personids[i]);
                                                                newPersonName += i == 0 ? ("'" + persiondescs[i])
                                                                        : ("," + persiondescs[i]);

                                                            } else {

                                                                if (newPersonId.equals("'")) {
                                                                    newPersonId += i == 0 ? ("'" + personids[i])
                                                                            : (personids[i]);
                                                                    newPersonName += i == 0 ? ("'" + persiondescs[i])
                                                                            : (persiondescs[i]);
                                                                } else {
                                                                    newPersonId += i == 0 ? ("'" + personids[i])
                                                                            : ("," + personids[i]);
                                                                    newPersonName += i == 0 ? ("'" + persiondescs[i])
                                                                            : ("," + persiondescs[i]);
                                                                }
                                                            }

                                                        }
                                                    }
                                                    newPersonId += "'";
                                                    newPersonName += "'";
                                                    wap.setPersonid(newPersonId
                                                            .substring(
                                                                    1,
                                                                    newPersonId
                                                                            .length() - 1));
                                                    wap.setPersondesc(newPersonName
                                                            .substring(
                                                                    1,
                                                                    newPersonName
                                                                            .length() - 1));
                                                    wap.setDefaultpersonid(newPersonId
                                                            .substring(
                                                                    1,
                                                                    newPersonId
                                                                            .length() - 1));
                                                    wap.setDefaultpersondesc(newPersonName
                                                            .substring(
                                                                    1,
                                                                    newPersonName
                                                                            .length() - 1));
                                                    updateName(zyspjlid,
                                                            newPersonId,
                                                            newPersonName);
                                                    txtGrid.setAdapter(new TxtGridAdapter(
                                                            wap));
                                                    if (mHandler != null) {
                                                        Message msg = mHandler
                                                                .obtainMessage();
                                                        msg.what = HANDLER_WHAT;
                                                        msg.obj = wap;
                                                        mHandler.sendMessage(msg);
                                                    }
                                                    if (listViewAdapter != null) {
                                                        listViewAdapter
                                                                .setCurrentWAPermission(wap);
                                                    }
                                                } else {
                                                    // 合并
                                                    String zyspjlids = wap
                                                            .getStrzyspryjlid();
                                                    updateName(zyspjlids
                                                                    .split(","),
                                                            oldName, newName);
                                                    String personid = (","
                                                            + wap.getPersonid() + ",")
                                                            .replaceAll(","
                                                                    + oldName
                                                                    + ",", ","
                                                                    + newName
                                                                    + ",");
                                                    personid = personid
                                                            .substring(
                                                                    1,
                                                                    personid.length() - 1);
                                                    wap.setPersonid(personid);
                                                    String persondesc = (","
                                                            + wap.getPersondesc() + ",")
                                                            .replaceAll(","
                                                                    + oldName
                                                                    + ",", ","
                                                                    + newName
                                                                    + ",");
                                                    persondesc = persondesc
                                                            .substring(
                                                                    1,
                                                                    persondesc
                                                                            .length() - 1);
                                                    wap.setPersondesc(persondesc);
                                                    // wap.setDefaultpersonid(wap.getPersonid().replaceAll(oldName,
                                                    // newName));
                                                    // wap.setDefaultpersondesc(wap.getPersondesc().replaceAll(oldName,
                                                    // newName));
                                                    txtGrid.setAdapter(new TxtGridAdapter(
                                                            wap));
                                                    if (listViewAdapter != null) {
                                                        listViewAdapter
                                                                .setCurrentWAPermission(wap);
                                                    }
                                                }
                                            }

                                        } else {
                                            ToastUtils
                                                    .imgToast(
                                                            getContext(),
                                                            R.drawable.hd_hse_common_msg_wrong,
                                                            "签字人不能为空！");
                                        }
                                    }
                                });
                        builder.show();
                    }
                });

            } else {
                holder.txtView.setTextColor(getContext().getResources()
                        .getColor(R.color.hd_hse_common_gray));
            }
            return view;
        }

        private class ViewHolder {
            TextView txtView;
            RelativeLayout relativeLayout;
            ImageView imageView;
        }

        private void updateName(String[] zyspryjlids, String oldName,
                                String newName) {
            IConnectionSource connectionSource = null;
            IConnection connection = null;
            try {
                String zyspryjlid = "";
                for (int i = 0; i < zyspryjlids.length; i++) {
                    zyspryjlid += i == 0 ? ("'" + zyspryjlids[i] + "'") : (",'"
                            + zyspryjlids[i] + "'");
                }
                BaseDao dao = new BaseDao();
                // 建立JDBC连接
                connectionSource = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
                connection = connectionSource.getConnection();
                String sql = "update ud_zyxk_zyspryjl set person = substr(replace(','||person||',', ',"
                        + oldName
                        + ",',',"
                        + newName
                        + ",'),2,length(replace(','||person||',', ',"
                        + oldName
                        + ",',',"
                        + newName
                        + ",'))-2), person_name = substr(replace(','||person_name||',', ',"
                        + oldName
                        + ",',',"
                        + newName
                        + ",'),2,length(replace(','||person_name||',', ',"
                        + oldName
                        + ",',',"
                        + newName
                        + ",'))-2) where ud_zyxk_zyspryjlid in("
                        + zyspryjlid
                        + ");";
                dao.executeUpdate(connection, sql);
                connection.commit();
            } catch (SQLException e) {
                loggle.error(e);
                ToastUtils.imgToast(getContext(),
                        R.drawable.hd_hse_common_msg_wrong, "修改失败！");
            } catch (DaoException e) {
                loggle.error(e);
                ToastUtils.imgToast(getContext(),
                        R.drawable.hd_hse_common_msg_wrong, "修改失败！");
            } finally {
                if (connectionSource != null) {
                    try {
                        // 释放连接
                        connectionSource.releaseConnection(connection);
                        isClick = false;
                    } catch (SQLException e) {
                        loggle.error(e.getMessage(), e);
                    }
                }
            }

        }

        private void updateName(String zysqryjlid, String personids,
                                String personNames) {
            IConnectionSource connectionSource = null;
            IConnection connection = null;
            try {
                BaseDao dao = new BaseDao();
                // 建立JDBC连接
                connectionSource = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
                connection = connectionSource.getConnection();
                String sql = "update ud_zyxk_zyspryjl set person = "
                        + personids + ",person_name = " + personNames
                        + " where ud_zyxk_zyspryjlid = '" + zysqryjlid + "'";
                dao.executeUpdate(connection, sql);
                connection.commit();
            } catch (SQLException e) {
                loggle.error(e);
                ToastUtils.imgToast(getContext(),
                        R.drawable.hd_hse_common_msg_wrong, "修改失败！");
            } catch (DaoException e) {
                loggle.error(e);
                ToastUtils.imgToast(getContext(),
                        R.drawable.hd_hse_common_msg_wrong, "修改失败！");
            } finally {
                if (connectionSource != null) {
                    try {
                        // 释放连接
                        connectionSource.releaseConnection(connection);
                        isClick = false;
                    } catch (SQLException e) {
                        loggle.error(e.getMessage(), e);
                    }
                }
            }

        }

    }

    private class ImageGridAdapter extends BaseAdapter {
        private List<Image> signImgUrls;

        public ImageGridAdapter(List<Image> signImgUrls) {
            this.signImgUrls = signImgUrls;
        }

        @Override
        public int getCount() {
            return signImgUrls.size();
        }

        @Override
        public Object getItem(int arg0) {
            return signImgUrls.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int postion, View contentView, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (contentView == null) {
                contentView = LayoutInflater.from(getContext()).inflate(
                        R.layout.hd_hse_show_sign_dialog_imggrid_item, null);
                holder = new ViewHolder();
                holder.imgView = (ImageView) contentView.findViewById(R.id.img);
                contentView.setTag(holder);
            } else {
                holder = (ViewHolder) contentView.getTag();
            }
            try {
                FileInputStream fis = new FileInputStream(signImgUrls.get(
                        postion).getImagepath());
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                holder.imgView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            return contentView;
        }

        private class ViewHolder {
            public ImageView imgView;
        }
    }

    /**
     * 图片去重 distinct:(). <br/>
     * date: 2015年12月2日 <br/>
     *
     * @param imgs
     * @return
     * @author LiuYang
     */
    private List<Image> distinct(List<Image> imgs) {
        List<Image> images = new ArrayList<Image>();
        images.addAll(imgs);
        for (int i = 0; i < images.size(); i++) {
            for (int j = 1; j < images.size() - i; j++) {
                if (images.get(i).getUpdateDate() == images.get(i + j)
                        .getUpdateDate()) {
                    images.remove(images.get(i + j));
                }
            }
        }
        return images;
    }

    /**
     * 查找相同的图片。即修改时间相同的图片 getSameImages:(). <br/>
     * date: 2015年12月2日 <br/>
     *
     * @param imgs
     * @param updateDate
     * @return
     * @author LiuYang
     */
    private List<Image> getSameImages(List<Image> imgs, long updateDate) {
        List<Image> images = new ArrayList<Image>();
        for (Image image : imgs) {
            if (image.getUpdateDate() == updateDate) {
                images.add(image);
            }
        }
        return images;
    }

    /**
     * 修改图片并修改图片修改时间 updateImg:(). <br/>
     * date: 2015年12月2日 <br/>
     *
     * @param img
     * @param bitmap
     * @param time
     * @author LiuYang
     */
    private void updateImg(Image img, Bitmap bitmap, long time) {
        IConnectionSource connectionSource = null;
        IConnection connection = null;
        try {
            File file = new File(img.getImagepath());
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
            img.setUpdateDate(time);
            BaseDao dao = new BaseDao();
            // 建立JDBC连接
            connectionSource = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = connectionSource.getConnection();
            dao.updateEntity(connection, img, new String[]{"updatedate"});
            connection.commit();
        } catch (FileNotFoundException e) {
            loggle.error(e);
        } catch (IOException e) {
            loggle.error(e);
        } catch (SQLException e) {
            loggle.error(e);
        } catch (DaoException e) {
            loggle.error(e);
        } finally {
            if (connectionSource != null) {
                try {
                    // 释放连接
                    connectionSource.releaseConnection(connection);
                } catch (SQLException e) {
                    loggle.error(e.getMessage(), e);
                }
            }
        }
    }

}
