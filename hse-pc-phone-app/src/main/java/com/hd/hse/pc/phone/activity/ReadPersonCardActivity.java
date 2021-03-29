package com.hd.hse.pc.phone.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hd.hse.business.util.SYSConstant;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.business.readcard.ReadCardThread;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.homepage.PersonCheckApp;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员检查的读卡界面 Created by liuyang on 2016年9月22日
 */
public class ReadPersonCardActivity extends BaseFrameActivity implements IEventListener {
    private static Logger logger = LogUtils
            .getLogger(ReadPersonCardActivity.class);
    private AnimatorSet animatorSet;
    /**
     * 动画集合
     */
    private ArrayList<Animator> animatorList;
    /**
     * nfc动画背景
     */
    RelativeLayout rlBg;
    /**
     * 动画延迟开始时间
     */
    final private int delayTime = 1500;
    /**
     * 动画总时间
     */
    final private int durationTime = 4500;

    /**
     * 关系检查 Created by liuyang on 2016年9月22日
     */
    public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();

    /**
     * 独立线程读卡
     */
    // 是否初始化完成标志
    public boolean flag = false;
    // 刷卡线程
    public ReadCardThread myReadCardThread;
    // 刷卡handler
    public Handler myhandler;
    // 是否结束刷卡
    public boolean isend = false;
    /**
     * NFC读卡功能
     */
    // 默认的读卡设备
    private NfcAdapter nfcAdapter;
    // 意图拦截器
    private PendingIntent pendingIntent;
    // 意图拦截类型列表
    private IntentFilter[] intentFiltersArray;
    // 是否开启了nfc功能
    private boolean nfcenable = false;

    public Object getNfcAdapter() {
        return nfcAdapter;
    }

    public boolean getNfcEnable() {
        return nfcAdapter.isEnabled();
    }

    public String cardnum = "";
    /**
     * 需要拦截的卡
     */
    private String[][] techListsArray = new String[][]{
            new String[]{NfcV.class.getName()},
            new String[]{MifareClassic.class.getName(), NfcA.class.getName(),
                    Ndef.class.getName()},
            new String[]{IsoDep.class.getName(), NfcA.class.getName(),
                    NdefFormatable.class.getName()},
            new String[]{NfcF.class.getName()},
            new String[]{NfcA.class.getName()}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hse_pc_phone_app_layout_read_person_card);
        // 获取默认的NFC控制器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFC拦截器
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{ndef};
        // 初始化ActionBar
        checkQyQR();
        setActionBartitleContent(getTitileName(), false);
        initView();
        initAnimation();
        //startAnimation();
    }



    /**
     * 判断是直接跳到刷卡界面还是从人员选择检查选择检查模式界面跳过来的
     */
    private void checkQyQR() {

        boolean qyQR = getIntent().getBooleanExtra(PersonCheckApp.QYQR, false);
        if (qyQR) {
            setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE});
        } else {
            setCustomActionBar(true, this, setActionBarItems());
            setNavContent(getNavContentData(), "hse-pc-phone-app");// 设置导航栏标题
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /** 满足过滤条件的标签及数据格式，继续处理，不满足的会弹出选择对应的应用 */
        if (NFCReader.nfcReadCardEnable()) {
            if (nfcAdapter != null) {
                if (nfcAdapter.isEnabled()) {
                    nfcenable = true;
                    nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                            intentFiltersArray, techListsArray);
                    // 读卡号
                    cardnum = getIntent().getStringExtra("nfcmsg");
                    if (cardnum != null && !cardnum.trim().equals("")) {
                        try {
                            // 去掉前几位（大连项目特殊需求）
                            IQueryRelativeConfig relationConfig = new QueryRelativeConfig();
                            RelationTableName config = new RelationTableName();
                            config.setSys_type(IRelativeEncoding.ISSUBSTRING);
                            RelationTableName relation = relationConfig.getRelativeObj(config);
                            int num = 0;
                            if (relation != null
                                    && !StringUtils.isEmpty(relation.getInput_value())
                                    && NumberUtils.isNumber(relation.getInput_value())) {
                                num = Integer.valueOf(relation.getInput_value());
                            }
                            cardnum = cardnum.substring(num);
                            PersonCard card = getPersonCard(cardnum);
                            cardnum = "";
                            if (card != null) {
                                Intent intent = new Intent(
                                        ReadPersonCardActivity.this,
                                        PersonCheckActivity.class);
                                intent.putExtra(PersonCard.class.getName(),
                                        card);
                                startActivity(intent);
                            }
                        } catch (HDException e) {
                            logger.error(e);
                            ToastUtils.imgToast(ReadPersonCardActivity.this,
                                    R.drawable.hd_hse_common_msg_wrong,
                                    e.getMessage());
                        } finally {
                            cardnum = ""; // 清空刷卡信息
                        }
                    }
                    getIntent().putExtra("nfcmsg", "");
                } else {
                    ToastUtils.toast(ReadPersonCardActivity.this, "NFC刷卡功能未开启");
                }

            }
        } else {
            // 表示开启寻卡功能
            isend = false;
            StartReadCardThread();
        }

        startAnimation();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (nfcAdapter != null) {
            nfcenable = true;
            nfcAdapter.disableForegroundDispatch(this);
            if (cardnum != null && cardnum.length() != 0) {
                getIntent().removeExtra("nfcmsg");
            }
        } else if (null != myReadCardThread) {
            myReadCardThread.setStop();
            isend = true;// 表示关闭寻卡功能
        }
        stopAnimation();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String id = NFCReader.readCardId(tagFromIntent.getId(), true);
            intent.putExtra("nfcmsg", id);
        }
    }

    ;

    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bd = new Bundle();
            bd = msg.getData();
            String info = bd.getString(SYSConstant.KEYCARDCODE);
            if (!info.trim().equals("")) {
                myReadCardThread.setStop();
                actionByCardID(info);
            }
        }
    }

    /**
     * StartReadCardThread:(开启读卡线程). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @author lxf
     */
    public void StartReadCardThread() {
        myhandler = new MyHandler();
        myReadCardThread = new ReadCardThread(myhandler);
        myReadCardThread.start();
    }

    /**
     * (获取刷卡人员信息) Created by liuyang on 2016年9月22日
     *
     * @param cardNum
     * @return
     * @throws HDException
     */
    private PersonCard getPersonCard(String cardNum) throws HDException {
        StringBuilder sbSql = new StringBuilder();
        sbSql.setLength(0);
        // 获取人员信息
        PersonCard psnCard = new PersonCard();
        try {
            String cardNumMD5 = "";
            // 判断是否需要对人员卡加密
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.CARDISMD5);
            relationEntity.setSys_fuction(null);
            relationEntity.setSys_value("");
            if (relativeConfig.isHadRelative(relationEntity)) {
                cardNumMD5 = UtilTools.string2MD5(cardNum);
            }
            sbSql.append("select * from ud_zyxk_ryk ");
            // 通过人员卡登录
            if (!StringUtils.isEmpty(cardNum)) {
                sbSql.append(" where pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                sbSql.append(" or pcardnum='").append(cardNumMD5).append("' COLLATE NOCASE;");
            } else {
                throw new HDException("不存在该卡号的人员！");
            }
            BaseDao dao = new BaseDao();
            psnCard = (PersonCard) dao.executeQuery(sbSql.toString(),
                    new EntityResult(PersonCard.class));
            if (psnCard == null)
                throw new HDException("无效刷卡用户！");
            if (psnCard.getIscan() == 0)
                throw new HDException("该人员卡已失效，请联系相关负责人！");
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("判断审批权限失败！");
        }
        return psnCard;
    }

    public void actionByCardID(String cardid) {
    }

    /**
     * 初始化view
     */
    private void initView() {
        rlBg = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_rl_credit_card);

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorList = new ArrayList<Animator>();
    }

    /**
     * 初始化nfc刷卡动画
     */
    private void initAnimation() {
        for (int i = 0; i < rlBg.getChildCount(); i++) {
            ImageView img = (ImageView) rlBg.getChildAt(i);
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(img,
                    "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * delayTime);
            alphaAnimator.setDuration(durationTime);
            animatorList.add(alphaAnimator);

            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(img,
                    "ScaleX", 1.0f, 3f);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * delayTime);
            scaleXAnimator.setDuration(durationTime);
            animatorList.add(scaleXAnimator);

            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(img,
                    "ScaleY", 1.0f, 3f);
            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * delayTime);
            scaleYAnimator.setDuration(durationTime);
            animatorList.add(scaleYAnimator);

        }

        animatorSet.playTogether(animatorList);

    }

    /**
     * 开始nfc刷卡动画
     */
    private void startAnimation() {
        if (!animatorSet.isRunning()) {
            animatorSet.start();
        }
    }

    /**
     * 结束nfc刷卡动画
     */
    private void stopAnimation() {
        if (animatorSet.isRunning()) {
            animatorSet.cancel();
        }
    }

    private String getTitileName() {
        return "人员检查";
    }

    public String[] setActionBarItems() {
        return new String[]{IActionBar.IBTN_RETURN, IActionBar.TV_TITLE};
    }

    /**
     * getNavContentData:(获取导航数据). <br/>
     * date: 2015年2月6日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<AppModule> getNavContentData() {
        return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        // TODO Auto-generated method stub

    }

}
