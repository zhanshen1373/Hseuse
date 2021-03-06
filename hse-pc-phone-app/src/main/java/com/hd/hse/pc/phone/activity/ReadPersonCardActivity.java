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
 * ??????????????????????????? Created by liuyang on 2016???9???22???
 */
public class ReadPersonCardActivity extends BaseFrameActivity implements IEventListener {
    private static Logger logger = LogUtils
            .getLogger(ReadPersonCardActivity.class);
    private AnimatorSet animatorSet;
    /**
     * ????????????
     */
    private ArrayList<Animator> animatorList;
    /**
     * nfc????????????
     */
    RelativeLayout rlBg;
    /**
     * ????????????????????????
     */
    final private int delayTime = 1500;
    /**
     * ???????????????
     */
    final private int durationTime = 4500;

    /**
     * ???????????? Created by liuyang on 2016???9???22???
     */
    public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();

    /**
     * ??????????????????
     */
    // ???????????????????????????
    public boolean flag = false;
    // ????????????
    public ReadCardThread myReadCardThread;
    // ??????handler
    public Handler myhandler;
    // ??????????????????
    public boolean isend = false;
    /**
     * NFC????????????
     */
    // ?????????????????????
    private NfcAdapter nfcAdapter;
    // ???????????????
    private PendingIntent pendingIntent;
    // ????????????????????????
    private IntentFilter[] intentFiltersArray;
    // ???????????????nfc??????
    private boolean nfcenable = false;

    public Object getNfcAdapter() {
        return nfcAdapter;
    }

    public boolean getNfcEnable() {
        return nfcAdapter.isEnabled();
    }

    public String cardnum = "";
    /**
     * ??????????????????
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
        // ???????????????NFC?????????
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // NFC?????????
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{ndef};
        // ?????????ActionBar
        checkQyQR();
        setActionBartitleContent(getTitileName(), false);
        initView();
        initAnimation();
        //startAnimation();
    }



    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void checkQyQR() {

        boolean qyQR = getIntent().getBooleanExtra(PersonCheckApp.QYQR, false);
        if (qyQR) {
            setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE});
        } else {
            setCustomActionBar(true, this, setActionBarItems());
            setNavContent(getNavContentData(), "hse-pc-phone-app");// ?????????????????????
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /** ?????????????????????????????????????????????????????????????????????????????????????????????????????? */
        if (NFCReader.nfcReadCardEnable()) {
            if (nfcAdapter != null) {
                if (nfcAdapter.isEnabled()) {
                    nfcenable = true;
                    nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                            intentFiltersArray, techListsArray);
                    // ?????????
                    cardnum = getIntent().getStringExtra("nfcmsg");
                    if (cardnum != null && !cardnum.trim().equals("")) {
                        try {
                            // ?????????????????????????????????????????????
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
                            cardnum = ""; // ??????????????????
                        }
                    }
                    getIntent().putExtra("nfcmsg", "");
                } else {
                    ToastUtils.toast(ReadPersonCardActivity.this, "NFC?????????????????????");
                }

            }
        } else {
            // ????????????????????????
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
            isend = true;// ????????????????????????
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
     * StartReadCardThread:(??????????????????). <br/>
     * date: 2014???9???12??? <br/>
     *
     * @author lxf
     */
    public void StartReadCardThread() {
        myhandler = new MyHandler();
        myReadCardThread = new ReadCardThread(myhandler);
        myReadCardThread.start();
    }

    /**
     * (????????????????????????) Created by liuyang on 2016???9???22???
     *
     * @param cardNum
     * @return
     * @throws HDException
     */
    private PersonCard getPersonCard(String cardNum) throws HDException {
        StringBuilder sbSql = new StringBuilder();
        sbSql.setLength(0);
        // ??????????????????
        PersonCard psnCard = new PersonCard();
        try {
            String cardNumMD5 = "";
            // ????????????????????????????????????
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.CARDISMD5);
            relationEntity.setSys_fuction(null);
            relationEntity.setSys_value("");
            if (relativeConfig.isHadRelative(relationEntity)) {
                cardNumMD5 = UtilTools.string2MD5(cardNum);
            }
            sbSql.append("select * from ud_zyxk_ryk ");
            // ?????????????????????
            if (!StringUtils.isEmpty(cardNum)) {
                sbSql.append(" where pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                sbSql.append(" or pcardnum='").append(cardNumMD5).append("' COLLATE NOCASE;");
            } else {
                throw new HDException("??????????????????????????????");
            }
            BaseDao dao = new BaseDao();
            psnCard = (PersonCard) dao.executeQuery(sbSql.toString(),
                    new EntityResult(PersonCard.class));
            if (psnCard == null)
                throw new HDException("?????????????????????");
            if (psnCard.getIscan() == 0)
                throw new HDException("???????????????????????????????????????????????????");
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("???????????????????????????");
        }
        return psnCard;
    }

    public void actionByCardID(String cardid) {
    }

    /**
     * ?????????view
     */
    private void initView() {
        rlBg = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_rl_credit_card);

        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorList = new ArrayList<Animator>();
    }

    /**
     * ?????????nfc????????????
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
     * ??????nfc????????????
     */
    private void startAnimation() {
        if (!animatorSet.isRunning()) {
            animatorSet.start();
        }
    }

    /**
     * ??????nfc????????????
     */
    private void stopAnimation() {
        if (animatorSet.isRunning()) {
            animatorSet.cancel();
        }
    }

    private String getTitileName() {
        return "????????????";
    }

    public String[] setActionBarItems() {
        return new String[]{IActionBar.IBTN_RETURN, IActionBar.TV_TITLE};
    }

    /**
     * getNavContentData:(??????????????????). <br/>
     * date: 2015???2???6??? <br/>
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
