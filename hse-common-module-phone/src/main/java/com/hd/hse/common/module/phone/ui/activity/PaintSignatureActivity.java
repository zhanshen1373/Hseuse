/**
 * Project Name:hse-common-module-phone
 * File Name:PaintSignatureActivity.java
 * Package Name:com.hd.hse.common.module.phone.ui.activity
 * Date:2015年10月23日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.component.phone.custom.PaintView;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * ClassName:PaintSignatureActivity ().<br/>
 * Date: 2015年10月23日 <br/>
 *
 * @author LiuYang
 * @version
 * @see
 */
public class PaintSignatureActivity extends Activity implements OnClickListener {

    private PaintView paintView;
    private Button okBtn, escBtn, redoBtn;

    private Intent sendIntent;
    public static final String READCADRACTION = "readCardAction";
    public static final String WORKAPPROVALPERMISSION = "workApprovalPermission";
    public static final String IMAGE = "image";
    public static final String WORKORDER = "workOrder";

    private SuperEntity workApprovalPermission;
    private WorkOrder workOrder;
    private List<WorkOrder> workOrders;

    private static Logger logger = LogUtils
            .getLogger(PaintSignatureActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hd_hse_common_component_dialog_paint_signature);
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        paintView.redo();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        Intent intent = getIntent();
        Object obj = (SuperEntity) intent
                .getSerializableExtra(WORKAPPROVALPERMISSION);
        if (obj == null) {
            logger.error("传入环节点 为null;");
            // return;
        }
        workApprovalPermission = (SuperEntity) obj;

        obj = intent.getSerializableExtra(WORKORDER);
        if (obj instanceof List) {
            workOrders = (List<WorkOrder>) obj;
        } else {
            workOrder = (WorkOrder) obj;
        }
    }

    private void initView() {
        paintView = (PaintView) findViewById(R.id.paintview);
        okBtn = (Button) findViewById(R.id.ok_btn);
        escBtn = (Button) findViewById(R.id.esc_btn);
        redoBtn = (Button) findViewById(R.id.redo_btn);

        okBtn.setOnClickListener(this);
        escBtn.setOnClickListener(this);
        redoBtn.setOnClickListener(this);
    }

    private void sendBroadCast() {
        // TODO Auto-generated method stub
        if (sendIntent == null) {
            sendIntent = new Intent(); // Itent就是我们要发送的内容
        }
        sendIntent.putExtra(READCADRACTION, 1);
        sendIntent.setAction(READCADRACTION); // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
        sendBroadcast(sendIntent); // 发送广播
    }

    /**
     * 保存签名图片 saveImage:(). <br/>
     * date: 2015年10月26日 <br/>
     *
     * @author LiuYang
     */
    private Image saveImage() {
        Bitmap bitmap = paintView.getBitmap();
        // 获取file name...
        Long time = ServerDateManager.getCurrentTimeMillis();
        String filePath = Environment.getExternalStorageDirectory().getPath()
                + "/zyxkapp/pic";
        String fileName = time.toString() + ".png";
        // 保存图片
        File file = new File(filePath, fileName);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.flush();
            out.close();
            // // 保存电子签名信息等待上传...
            // BusinessAction businessAction = new BusinessAction();
            // Image image = (Image) businessAction.addEntity(Image.class);
            Image image = new Image();

            /********************************************/
            // int lastin = ((WorkApprovalPermission) workApprovalPermission)
            // .getPersonid().lastIndexOf(",");

            // tableName
            // if (lastin != -1) {
            // image.setTablename("ud_onemeasure_person");
            // } else {
            // image.setTablename("ud_zyxk_zyspryjl");
            // }
            // image.setTableid((String) workApprovalPermission
            // .getAttribute("ud_zyxk_zyspryjlid")); // tableID
            /********************************************/

            image.setImagepath(filePath + "/" + fileName); // 图片路径
            image.setImagename(fileName); // 图片名
            image.setCreateuser(SystemProperty.getSystemProperty()
                    .getLoginPerson().getPersonid());// 创建人
            image.setCreateusername(SystemProperty.getSystemProperty()
                    .getLoginPerson().getPersonid_desc()); // 创建人姓名
            image.setFuncode("DZQM");

            String timeStr = ServerDateManager.getCurrentTime();
            image.setCreatedate(timeStr); // 创建时间
            image.setUpdateDate(time);
            // businessAction.saveEntity(image);

            return image;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static void saveImageToDB(boolean isItemByItem,
                                     WorkApprovalPermission curApproveNode, WorkOrder workOrder,
                                     List<WorkOrder> workOrders, Image img) {
        if (workOrders != null) {
            String zyspryjlids = curApproveNode.getModified_spryjlid();
            String[] tableIds = curApproveNode.getModified_spryjlid()
                    .split(",");
            String[] orderIds = curApproveNode.getStrzysqid().split(",");
            for (int i = 0; i < orderIds.length; i++) {
                String orderId = orderIds[i].substring(1,
                        orderIds[i].length() - 1);
                curApproveNode.setModified_spryjlid(tableIds[i]);
                if (i != 0) {
                    String fromPath = img.getImagepath();
                    String imgName = i + img.getImagename();
                    // String imgPath =
                    // Environment.getExternalStorageDirectory()
                    // .getPath() + "/zyxkapp/pic/" + imgName;
                    String imgPath = SystemProperty.getSystemProperty()
                            .getRootDBpath()
                            + File.separator
                            + "pic"
                            + File.separator + imgName;
                    img.setImagename(imgName);
                    img.setImagepath(imgPath);
                    copySdcardFile(fromPath, imgPath);
                }
                if (tableIds[i] != null && !tableIds[i].equals("")
                        && !tableIds[i].equals("null")) {
                    saveImgToDB(isItemByItem, curApproveNode, orderId, img);
                }
            }
            curApproveNode.setModified_spryjlid(zyspryjlids);
        } else {
            saveImgToDB(isItemByItem, curApproveNode,
                    workOrder.getUd_zyxk_zysqid(), img);
        }
    }

    private static void saveImgToDB(boolean isItemByItem,
                                    WorkApprovalPermission curApproveNode, String workOrderId, Image img) {
        BusinessAction action = new BusinessAction();
        try {
            Image image = (Image) action.addEntity(Image.class);
            if (isItemByItem) {
                image.setTablename("ud_onemeasure_person");
                image.setTableid(curApproveNode.getUd_onemeasure_person_ids());
            } else {
                image.setTablename("ud_zyxk_zyspryjl");
                if (curApproveNode.getModified_spryjlid() != null
                        && !curApproveNode.getModified_spryjlid().equals("")) {
                    image.setTableid(curApproveNode.getModified_spryjlid());
                } else {
                    image.setTableid(curApproveNode.getUd_zyxk_zyspryjlid());
                }
            }
            image.setImagepath(img.getImagepath());
            image.setImagename(img.getImagename());
            image.setCreateuser(img.getCreateuser());
            image.setCreateusername(img.getCreateusername());
            image.setCreatedate(img.getCreatedate());
            image.setFuncode(img.getFuncode());
            image.setZysqid(workOrderId);
            image.setUpdateDate(img.getUpdateDate());
            action.saveEntity(image);
        } catch (HDException e) {
            logger.error(e);
        }
    }

    private static int copySdcardFile(String fromFile, String toFile) {
        try {
            File outFile = new File(toFile);
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(outFile);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.ok_btn) {
            if (!paintView.hasDraw()) {
                ToastUtils.toast(PaintSignatureActivity.this, "请签名！！");
            } else {
                String editStr = "图片签名";
                if (PaintSignatureActivity.this.workApprovalPermission
                        .getAttribute("persondesc") != null
                        && !"".equals(PaintSignatureActivity.this.workApprovalPermission
                        .getAttribute("persondesc"))) {
                    editStr = PaintSignatureActivity.this.workApprovalPermission
                            .getAttribute("persondesc") + "," + editStr;
                }
                PaintSignatureActivity.this.workApprovalPermission
                        .setAttribute("personid", editStr);
                PaintSignatureActivity.this.workApprovalPermission
                        .setAttribute("persondesc", editStr);
                Image image = saveImage();
                Intent intent = getIntent();
                intent.putExtra(PaintSignatureActivity.WORKAPPROVALPERMISSION,
                        workApprovalPermission);
                intent.putExtra(PaintSignatureActivity.IMAGE, image);
                if (workOrder != null) {
                    intent.putExtra(WORKORDER, workOrder);
                } else {
                    intent.putExtra(WORKORDER, (Serializable) workOrders);
                }
                setResult(IEventType.TPQM_TYPE, intent);
                sendBroadCast();
                PaintSignatureActivity.this.finish();
            }
        } else if (id == R.id.esc_btn) {
            sendBroadCast();
            PaintSignatureActivity.this.finish();
        } else if (id == R.id.redo_btn) {
            paintView.redo();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        sendBroadCast();
        return super.onKeyDown(keyCode, event);
    }
}
