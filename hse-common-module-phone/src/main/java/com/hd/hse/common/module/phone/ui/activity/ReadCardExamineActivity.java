/**
 * Project Name:hse-common-module
 * File Name:ReadCardActivity.java
 * Package Name:com.hd.hse.common.module.ui.activity
 * Date:2014年11月6日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.custom.SingleChoiceAdapter;
import com.hd.hse.common.module.phone.ui.utils.SwipingCardUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.other.ContractorAptitudeConfig;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.checkright.CheckApproveRight;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ClassName:ReadCardActivity (配置读卡activity的超类).<br/>
 * Date: 2014年11月6日 <br/>
 *
 * @author zhulei
 * @see
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ReadCardExamineActivity<T extends SuperEntity> extends
        ReadCardActivity {
    private static Logger logger = LogUtils
            .getLogger(ReadCardExamineActivity.class);
    public static final String READCADRACTION = "readCardAction";
    // 刷卡区
    private EditText cardEdit;
    // 审批环节描述
    private TextView exmineDesc;
    private String spfield_desc = "";
    // 审批环节
    private SuperEntity workApprovalPermission;
    // 作业票
    private WorkOrder workOrder;

    private PersonSpecialTypeWork pstw;
    /**
     * workOrdersList:TODO(作业票集合).
     */
    private List<SuperEntity> workOrdersList;
    public static final String WORKORDER = "workOrder";
    public static final String WORKAPPROVALPERMISSION = "workApprovalPermission";
    private String[] persondesc;
    private Intent sendIntent;

    private String defaultJob; // 默认工种
    private String defaultCode; // 默认工种编号
    private String defaultZcProject; // 默认准操项目
    // 获取方法
    // private Method setSoftInputShownOnFocus;
    private boolean isfeast = true;
    /**
     * 是否读卡
     */
    private boolean hasReadCard = false;
    /**
     * 是否是读卡改变的editText
     */
    private boolean isReadCard = false;

    /**
     * 拍照按钮
     */
    private RelativeLayout btTakePicture;
    /**
     * 图片保存路径
     */
    private String path = null;
    /**
     * 是否启用拍照
     */
    private boolean isQyCamrea = false;
    /**
     * 是否强制拍照
     */
    private boolean isForcedCamrea = false;

    /**
     * 存放照片文件夹的文件夹路径,带"/"
     */
    private final String saveImgPath = SystemProperty.getSystemProperty()
            .getRootDBpath() + File.separator;
    /**
     * 图片临时存放路径
     */
    private final String tempPath = SystemProperty.getSystemProperty()
            .getRootDBpath() + File.separator + "temp.jpg";
    /**
     *
     */
    private Image image = null;
    public static String IMAGE = "image";
    /**
     * 刷卡完成标识
     */
    private ImageView imgSkComplete;
    /**
     * 拍照完成标识
     */
    private ImageView imgCameraComplete;
    /**
     * 完成按钮
     */
    private Button btEnsure;
    /**
     * 是否完成拍照
     */
    private boolean isCompleteCamera = false;
    /**
     * 是否完成刷卡或者手书
     */
    private boolean isCompleteSk = false;
    /**
     * 拍照返回图片显示
     */
    private ImageView imgHeadPhoto;
    /**
     * pcardnum
     */
    private String pcardnum;

    private CheckApproveRight checkApproveRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSysConfig();
            // 根据是否启用拍照字段显示不同的界面布局
            if (isQyCamrea) {
                setContentView(R.layout.hd_common_component_readcard_new);
            } else {
                setContentView(R.layout.hd_common_component_readcard);
            }

            cardnum = null;
            pcardnum = null;
            initView();
            initData();
        } catch (Exception e) {
            logger.error(e);
            ToastUtils.toast(this, "系统出错,请联系管理员!");
        }
    }

    /**
     * 得到isQyCamrea，isForcedCamrea字段的值
     */
    private void getSysConfig() {
        String sql = "select * from sys_relation_info where sys_type = '"
                + IRelativeEncoding.PHOTOCONFIGUR + "'";
        BaseDao dao = new BaseDao();
        try {
            RelationTableName mRelationTableName = (RelationTableName) dao
                    .executeQuery(sql,
                            new EntityResult(RelationTableName.class));
            if (mRelationTableName != null && mRelationTableName.getIsqy() == 1) {
                isQyCamrea = true;
            }
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void initView() {

        View view = findViewById(R.id.hd_hse_common_readcard_rl);
        if (isQyCamrea) {
            btTakePicture = (RelativeLayout) findViewById(R.id.hd_hse_readcard_bt_take_picture);
            cardEdit = (EditText) findViewById(R.id.hd_hse_common_readcard_et_person_name);
            imgSkComplete = (ImageView) findViewById(R.id.hd_common_component_readcard_img_sk_complete);
            imgCameraComplete = (ImageView) findViewById(R.id.hd_common_component_readcard_img_camrea_complete);
            btEnsure = (Button) findViewById(R.id.hd_hse_common_bt_ensure);
            imgHeadPhoto = (ImageView) findViewById(R.id.hd_hse_readcard_img_head_photo);
            btEnsure.setOnClickListener(sureClick);
            // 点击拍照按钮
            btTakePicture.setOnClickListener(camreaClick);
        } else {
            TextView cancelBT = (TextView) this
                    .findViewById(R.id.hd_hse_common_readcard_cancel_tv);
            TextView sureBT = (TextView) this
                    .findViewById(R.id.hd_hse_common_readcard_sure_tv);
            exmineDesc = (TextView) findViewById(R.id.hd_hse_common_readcard_tv);
            cardEdit = (EditText) findViewById(R.id.hd_hse_common_readcard_et);
            // 压缩读卡界面图片
            setBackground(view, R.drawable.hd_hse_common_readcard_bg);
            cancelBT.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    sendBroadCast();
                    ReadCardExamineActivity.this.finish();
                }
            });
            sureBT.setOnClickListener(sureClick);
        }

        cardEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("NewApi")
            @Override
            public void afterTextChanged(Editable arg0) {
                if (!isReadCard) {
                    // 如果手动改变了刷卡的信息就要重置person信息
                    hasReadCard = false;
                    cardnum = null;
                    pcardnum = null;
                    workApprovalPermission.setAttribute("personid",
                            workApprovalPermission
                                    .getAttribute("defaultpersonid"));
                    workApprovalPermission.setAttribute("persondesc",
                            workApprovalPermission
                                    .getAttribute("defaultpersondesc"));


                }
                isReadCard = false;

                // 启用拍照，进行判断，刷卡或手书步骤是否完成
                if (isQyCamrea) {
                    String personName = cardEdit.getText().toString().trim();
                    if (null != personName && !"".equals(personName)) {
                        isCompleteSk = true;
                        imgSkComplete.setVisibility(View.VISIBLE);
                    } else {
                        isCompleteSk = false;
                        imgSkComplete.setVisibility(View.INVISIBLE);
                    }
                    changeBtEnsureState(btEnsure);
                }

            }
        });

        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.ISTEST);
        boolean istest = relation.isHadRelative(relationEntity);
        if (istest) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    cardnum = "22";
                    onResume();
                }
            });
        }
    }

    private void initData() {
        Intent intent = getIntent();

        String[] spfildstr;
        Object obj = intent.getSerializableExtra(WORKORDER);
        if (obj == null) {
            this.finish();
            logger.error("传入workorder 为null;");
            return;
        }
        if (obj instanceof List) {
            workOrdersList = (List<SuperEntity>) obj;
        } else {
            workOrder = (WorkOrder) obj;
        }
        obj = (SuperEntity) intent.getSerializableExtra(WORKAPPROVALPERMISSION);
        if (obj == null) {
            logger.error("传入环节点 为null;");
            // return;
        }
        workApprovalPermission = (SuperEntity) obj;
        // 判断是否强制拍照
        if (isQyCamrea) {
            int photoconfigur = Integer.parseInt(workApprovalPermission
                    .getAttribute("photoconfigur").toString());
            if (photoconfigur == 1) {
                isForcedCamrea = true;
            } else {
                isForcedCamrea = false;
            }
        }

        spfield_desc = workApprovalPermission.getAttribute("spfield_desc")
                .toString();
        spfildstr = spfield_desc.split("#");
        if (spfildstr.length > 0) {
            spfield_desc = spfildstr[spfildstr.length - 1];
        } else {
            spfield_desc = "刷卡人";
        }
        if (isQyCamrea) {
            cardEdit.setHint(spfield_desc);
        } else {
            exmineDesc.setText(spfield_desc);
        }

        // 判断是否为手动输入
        if ((int) workApprovalPermission.getAttribute("isinput") == 1) {
            cardEdit.setFocusable(true);
            cardEdit.setFocusableInTouchMode(true);
            cardEdit.requestFocus();
        }
        if (workApprovalPermission.getAttribute("job") != null) {
            defaultJob = workApprovalPermission.getAttribute("job").toString();
        }
        if (workApprovalPermission.getAttribute("code") != null) {
            defaultCode = workApprovalPermission.getAttribute("code")
                    .toString();
        }

        if (workApprovalPermission.getAttribute("zcproject") != null) {
            defaultZcProject = workApprovalPermission.getAttribute("zcproject")
                    .toString();
        }

    }

    /**
     * 点击拍照按钮
     */
    private OnClickListener camreaClick = new OnClickListener() {
        @Override
        public void onClick(View arg0) {
            // 调用系统相机
            File temp = new File(tempPath);
            // 若临时文件存在，删掉
            if (temp.exists()) {
                temp.delete();
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = Uri.fromFile(temp); // 保存到临时文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, 1);
        }

    };

    private OnClickListener sureClick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            String cardEditstr = ReadCardExamineActivity.this.cardEdit
                    .getText().toString();
            if ((int) workApprovalPermission.getAttribute("isinput") != 1
                    && !hasReadCard && workApprovalPermission != null
                    && (int) workApprovalPermission.getAttribute("ismust") == 1) {
                ToastUtils.imgToast(ReadCardExamineActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, "请刷卡！");
                return;
            } else if (ReadCardExamineActivity.this.cardEdit.getText() == null
                    || "".equals(cardEditstr)) {
                ToastUtils.imgToast(ReadCardExamineActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, "请输入！");
                return;
            } else if (isQyCamrea && isForcedCamrea && !isCompleteCamera) {
                ToastUtils.imgToast(ReadCardExamineActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, "请点击拍照按钮进行拍照！");
                return;
            }

            //(吉林项目要求)，同一人不能同时为作业人，作业监护人（不能既当运动员又当裁判）
            if (workOrdersList == null&& !TextUtils.isEmpty(pcardnum)) {
                if (checkApproveRight == null) {
                    checkApproveRight = new CheckApproveRight();
                }
                try {
                    checkApproveRight.isRepellentHjd(workApprovalPermission, pcardnum, workOrder);
                } catch (HDException e) {
                    ToastUtils.toast(ReadCardExamineActivity.this, e.getMessage());
                    return;
                }
            }
            // 防止多点击
            if (!isfeast) {
                return;
            }
            if (!hasReadCard) {
                String personids = "";
                if (ReadCardExamineActivity.this.workApprovalPermission
                        .getAttribute("persondesc") != null
                        && !"".equals(ReadCardExamineActivity.this.workApprovalPermission
                        .getAttribute("persondesc"))) {
                    personids = ReadCardExamineActivity.this.workApprovalPermission
                            .getAttribute("personid") + "," + cardEditstr;
                    cardEditstr = ReadCardExamineActivity.this.workApprovalPermission
                            .getAttribute("persondesc") + "," + cardEditstr;
                } else {
                    personids = cardEditstr;
                }


                if (workApprovalPermission.getAttribute("bpermulcard") != null) {
                    //不能重复刷卡
                    if ((int) workApprovalPermission.getAttribute("bpermulcard") == 0) {
                        String[] split = cardEditstr.split(",");
                        String[] split1 = personids.split(",");
                        workApprovalPermission.setAttribute("persondesc", split[split.length - 1]);
                        workApprovalPermission.setAttribute("personid", split1[split1.length - 1]);
                    } else {
                        //可以重复刷卡
                        ReadCardExamineActivity.this.workApprovalPermission
                                .setAttribute("personid", personids);
                        ReadCardExamineActivity.this.workApprovalPermission
                                .setAttribute("persondesc", cardEditstr);
                    }

                }


            }
            if (pstw != null) {
                if (defaultJob != null && !defaultJob.equals("")) {

                    workApprovalPermission.setAttribute("job", defaultJob + ","
                            + pstw.getCertificatetype());
                    workApprovalPermission.setAttribute("code", defaultCode
                            + "," + pstw.getZsnum());

                    workApprovalPermission.setAttribute("zcproject",
                            defaultZcProject + "," + pstw.getZcproject());
                } else {
                    workApprovalPermission.setAttribute("job",
                            pstw.getCertificatetype());
                    workApprovalPermission
                            .setAttribute("code", pstw.getZsnum());
                    workApprovalPermission.setAttribute("zcproject",
                            pstw.getZcproject());
                }
            }
            isfeast = false;

            Intent intent = getIntent();
            intent.putExtra(IMAGE, image);
            intent.putExtra(
                    ReadCardExamineActivity.this.WORKAPPROVALPERMISSION,
                    ReadCardExamineActivity.this.workApprovalPermission);
            if (workOrder != null) {
                intent.putExtra(WORKORDER, workOrder);
            } else {
                intent.putExtra(WORKORDER, (Serializable) workOrdersList);
            }

            setResult(IEventType.READCARD_TYPE, intent);

            sendBroadCast();
            hasReadCard = false;
            finish();
        }
    };

    @Override
    protected void onResume() {
        try {
            super.onResume();
            if (cardnum != null && !"".equals(cardnum)) {
                // 去掉前几位（大连项目特殊需求）
                IQueryRelativeConfig relationConfig = new QueryRelativeConfig();
                RelationTableName config = new RelationTableName();
                config.setSys_type(IRelativeEncoding.ISSUBSTRING);
                RelationTableName relation = relationConfig
                        .getRelativeObj(config);
                int num = 0;
                if (relation != null
                        && !StringUtils.isEmpty(relation.getInput_value())
                        && NumberUtils.isNumber(relation.getInput_value())) {
                    num = Integer.valueOf(relation.getInput_value());
                }
                if (NFCReader.getIsPositived()) {
                    cardnum = cardnum.substring(0, cardnum.length() - num);
                    pcardnum = cardnum;
                } else {
                    cardnum = cardnum.substring(num);
                    pcardnum = cardnum;
                }
                if (workOrder == null) {
                    SwipingCardUtils.swipingCard(workOrdersList,
                            workApprovalPermission, cardnum);

                    if (workApprovalPermission.getAttribute("bpermulcard") != null) {
                        //不能重复刷卡
                        if ((int) workApprovalPermission.getAttribute("bpermulcard") == 0) {
                            //不能累加刷卡的

                            //经过上一步，persondesc是累加的值，这步取的是最新的值，并赋值给persondesc
                            persondesc = workApprovalPermission
                                    .getAttribute("persondesc").toString().split(",");
                            workApprovalPermission.setAttribute("persondesc", persondesc[persondesc.length - 1]);
                        }

                    }


                } else {
                    SwipingCardUtils
                            .swipingCard(ReadCardExamineActivity.this, handler,
                                    workOrder, workApprovalPermission, cardnum);


                    if (workApprovalPermission.getAttribute("bpermulcard") != null) {
                        //不能重复刷卡
                        if ((int) workApprovalPermission.getAttribute("bpermulcard") == 0) {
                            //不能累加刷卡的

                            //经过上一步，persondesc是累加的值，这步取的是最新的值，并赋值给persondesc
                            persondesc = workApprovalPermission
                                    .getAttribute("persondesc").toString().split(",");
                            workApprovalPermission.setAttribute("persondesc", persondesc[persondesc.length - 1]);
                        }

                    }


                }
                if (cardEdit != null) {
                    persondesc = workApprovalPermission
                            .getAttribute("persondesc").toString().split(",");

                    isReadCard = true;
                    cardEdit.setText(persondesc[persondesc.length - 1]);


                    if (workApprovalPermission.getAttribute("bpermulcard") != null) {
                        //不能重复刷卡
                        if ((int) workApprovalPermission.getAttribute("bpermulcard") == 0) {
                            //不能累加刷卡的
                            workApprovalPermission.setAttribute("persondesc", persondesc[persondesc.length - 1]);
                        }

                    }


                    // 启用拍照，强制拍照，并且还未拍照，刷卡后会跳转到拍照页面
                    if (isQyCamrea && isForcedCamrea && !isCompleteCamera) {
                        camreaClick.onClick(btTakePicture);
                    }
                }
                hasReadCard = true;
                cardnum = null;
            }
        } catch (HDException e) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
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

    @Override
    public void actionByCardID(String cardid) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        sendBroadCast();
        return super.onKeyDown(keyCode, event);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                final List<PersonSpecialTypeWork> pstws = (List<PersonSpecialTypeWork>) msg.obj;
                /*
                 * if (pstws != null && pstws.size() == 1) { pstw =
                 * pstws.get(0); try { checkpstw(pstw, workOrder,
                 * workApprovalPermission); } catch (HDException e) {
                 * logger.error(e);
                 * ToastUtils.imgToast(ReadCardExamineActivity.this,
                 * R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                 * hasReadCard = false;
                 * ReadCardExamineActivity.this.cardEdit.setText(""); } } else
                 */
                if (pstws != null && pstws.size() != 0/* && pstws.size() >= 1 */) {
                    String[] items = new String[pstws.size()];
                    for (int i = 0; i < pstws.size(); i++) {
                        PersonSpecialTypeWork pstw = pstws.get(i);
                        if (pstw.getZcproject() == null
                                || pstw.getZcproject().equals("")) {
                            items[i] = pstw.getDescription();
                        } else
                            items[i] = pstws.get(i).getZcproject();

                        if (pstw.getZsnum() != null) {
                            items[i] += " : " + pstw.getZsnum();
                        } else {
                            items[i] += " :空";
                        }
                    }
                    Builder builder = new AlertDialog.Builder(
                            ReadCardExamineActivity.this);
                    View inflate = getLayoutInflater().inflate(R.layout.zdydialog, null);


                    ListView lv = (ListView) inflate.findViewById(R.id.zdydialog_listview);
                    final SingleChoiceAdapter singleChoiceAdapter = new SingleChoiceAdapter(
                            ReadCardExamineActivity.this, items);
                    lv.setAdapter(singleChoiceAdapter);
                    lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            singleChoiceAdapter.setchoseobj(position);
                            singleChoiceAdapter.notifyDataSetChanged();
                            pstw = pstws.get(position);
                        }
                    });
                    builder.setView(inflate);

//                    builder.setSingleChoiceItems(items, -1,
//                            new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface arg0,
//                                                    int arg1) {
//                                    pstw = pstws.get(arg1);
//                                }
//                            });

                    builder.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    try {
                                        checkpstw(pstw, workOrder,
                                                workApprovalPermission);
                                    } catch (HDException e) {
                                        logger.error(e);
                                        ToastUtils.toast(
                                                ReadCardExamineActivity.this,
                                                e.getMessage());
                                        hasReadCard = false;
                                        ReadCardExamineActivity.this.cardEdit
                                                .setText("");
                                    } finally {
                                        arg0.dismiss();
                                    }
                                }
                            });
                    builder.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    pstw = null;
                                    hasReadCard = false;
                                    ReadCardExamineActivity.this.cardEdit
                                            .setText("");
                                    arg0.dismiss();
                                }
                            });
                    builder.setCancelable(false);
                    // 判断JOBTABOO字段（健康体检建议）是否为空
                    BaseDao dao = new BaseDao();
                    String sql = "select * from ud_zyxk_ryk where pcardnum = '"
                            + pcardnum + "' COLLATE NOCASE";
                    pcardnum = null;
                    PersonCard p = null;
                    try {
                        p = (PersonCard) dao.executeQuery(sql,
                                new EntityResult(PersonCard.class));
                    } catch (DaoException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (p != null && p.getJOBTABOO() != null
                            && !p.getJOBTABOO().equals("")) {
                        TextView tv = new TextView(ReadCardExamineActivity.this);
                        tv.setText("体检作业禁忌建议:\n" + p.getJOBTABOO());
                        tv.setTextSize(18);
                        tv.setTextColor(Color.RED);
                        tv.setPadding(15, 10, 10, 15);
                        builder.setView(tv);
                    }
                    builder.create().show();
                } else {
                    try {
                        checkpstw(pstw, workOrder, workApprovalPermission);
                    } catch (HDException e) {
                        logger.error(e);
                        ToastUtils.toast(ReadCardExamineActivity.this,
                                e.getMessage());
                        hasReadCard = false;
                        ReadCardExamineActivity.this.cardEdit.setText("");
                    }
                }
            }
        }

    };

    private void checkpstw(PersonSpecialTypeWork pstw, SuperEntity workEntity,
                           SuperEntity nodeEntity) throws HDException {
        try {
            IQueryRelativeConfig relation = new QueryRelativeConfig();
            RelationTableName relationEntityYpz = new RelationTableName();
            relationEntityYpz.setSys_type(IRelativeEncoding.ISZYSQOFONE);
            StringBuilder sbSql = new StringBuilder();
            BaseDao dao = new BaseDao();
            if (pstw == null || pstw.getDqdate() == null) {
                sbSql.setLength(0);

                boolean iszysqofone = relation.isHadRelative(relationEntityYpz);
                if (iszysqofone) {
                    sbSql.append(
                            "select domain.description as description,sszz from ud_zyxk_cbszz inner join (select * from alndomain where domainid in('ZZ_CERTIFICATE','CERTIFICATETYPE')) as domain on sszz=domain.value where topzypclass='");
                } else {
                    sbSql.append(
                            "select domain.description as description,sszz from ud_zyxk_cbszz inner join (select * from alndomain where domainid in('ZZ_CERTIFICATE','CERTIFICATETYPE')) as domain on sszz=domain.value where zytype='");
                }

                sbSql.append(workEntity.getAttribute("zypclass"))
                        .append("'  and ud_zyxk_zyspqxid='");
                sbSql.append(nodeEntity.getAttribute("ud_zyxk_zyspqxid")
                        .toString());
                sbSql.append("';");
                List<ContractorAptitudeConfig> mapRS = (List<ContractorAptitudeConfig>) dao
                        .executeQuery(sbSql.toString(), new EntityListResult(
                                ContractorAptitudeConfig.class));
                if (mapRS == null || mapRS.size() == 0) {
                    throw new HDException("该节点资质配置缺失，请联系管理员。");
                } else {
                    if (pstw != null && pstw.getDqdate() == null) {
                        String str = "";
                        for (ContractorAptitudeConfig config : mapRS) {
                            str += config.getDescription() + "/";
                        }
                        str = str.substring(0, str.length() - 1);
                        throw new HDException("【" + str + "】证书请配置到期时间！");
                    } else {
                        String str = "";
                        for (ContractorAptitudeConfig config : mapRS) {
                            str += config.getDescription() + "/";
                        }
                        str = str.substring(0, str.length() - 1);
                        throw new HDException("系统未查到【" + str
                                + "】证书，若有请到主管单位登记！");
                    }
                }

            }

            if (pstw != null && !compareDate(pstw.getDqdate(), null)) {
                IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
                boolean config = false;
                for (Domain domain : queryWorkInfo.queryDomain(
                        "ZZ_CERTIFICATE','CERTIFICATETYPE", null)) {
                    if (domain.getValue().equals(pstw.getCertificatetype())) {
                        config = true;
                        throw new HDException(domain.getDescription()
                                + "证书已经到期，不能审核！");
                    }
                }
                if (!config) {
                    throw new HDException(pstw.getCertificatetype()
                            + "证书已经到期，不能审核！");
                }
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询承包商人员资质失败，请联系管理员！");
        }
    }

    /**
     * compareDate:(比较时间大小). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @param date1
     * @param date2
     * @return
     * @throws HDException 人员资质，承包商人员
     * @author zhaofeng
     */
    private boolean compareDate(String date1, String date2) throws HDException {
        DateFormat df = null;
        if (date1.length() == 19) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            if (date2 == null) {
                if (df.parse(date1).getTime() >= ServerDateManager.getCurrentTimeMillis())
                    return true;
            } else {
                if (df.parse(date1).getTime() >= df.parse(date2).getTime())
                    return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            throw new HDException("时间格式不正确！");
        }
        return false;
    }

    /**
     * 回调，拍照处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (isQyCamrea) {
            // 启用拍照
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = BitmapFactory.decodeFile(tempPath);
                bitmap = com.hd.hse.common.component.phone.util.ThumbBitmapUtils
                        .extractMiniThumb(bitmap, imgHeadPhoto.getWidth(),
                                imgHeadPhoto.getHeight());
                imgHeadPhoto.setImageBitmap(bitmap);
                image = new Image();
                isCompleteCamera = true;
                imgCameraComplete.setVisibility(View.VISIBLE);
            } else {
                image = null;
                isCompleteCamera = false;
                imgCameraComplete.setVisibility(View.INVISIBLE);
                imgHeadPhoto.setImageBitmap(null);
            }
            changeBtEnsureState(btEnsure);
        } else {
            image = null;
        }

    }

    /**
     * 改变完成按钮的状态
     */
    private void changeBtEnsureState(Button btEnsure) {
        if (btEnsure != null) {
            if (!isCompleteSk
                    || ((isQyCamrea && isForcedCamrea) && !isCompleteCamera)) {
                btEnsure.setBackground(getResources().getDrawable(
                        R.drawable.hd_hse_common_button_bg_disable_click));
            } else {
                btEnsure.setBackground(getResources().getDrawable(
                        R.drawable.hd_hse_common_button_bg_selector));
            }
        }

    }

}
