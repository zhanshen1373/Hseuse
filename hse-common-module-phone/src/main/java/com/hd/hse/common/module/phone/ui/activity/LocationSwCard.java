package com.hd.hse.common.module.phone.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.custom.LocationProgressDialog;
import com.hd.hse.common.module.phone.ui.custom.PositionDialog;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.common.module.phone.ui.utils.BitmapUtils;
import com.hd.hse.common.module.phone.ui.utils.DistanceUtils;
import com.hd.hse.common.module.phone.ui.utils.GPSStateUtil;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.service.card.PositionQuerier;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ClassName: LocationSwCard (位置卡刷卡)<br/>
 * date: 2015年1月20日 <br/>
 *
 * @author wenlin
 */

public class LocationSwCard extends ReadCardActivity {

    private static Logger logger = LogUtils.getLogger(LocationSwCard.class);

    /**
     * SER_KEY_TARGETCLASS:TODO(目标刷卡成功后，跳转应用).
     */
    public final static String SER_KEY_TARGETCLASS = "targetclass";

    /**
     * APP_MODULE:TODO(记录通过哪个模块过来的).
     */
    public final static String APP_MODULE = "appmodule";
    /**
     * imageView:TODO(刷卡动画).
     */
    private ImageView imageView;

    /**
     * 位置缓存
     */
    private GridView gvCache;
    /**
     * 位置缓存adapter
     */
    private LocationSwCardAdpter mLocationSwCardAdpter;
    /**
     * 位置缓存数据
     */
    private List<PositionCard> positionCardList;
    /**
     * imageButton:TODO(关闭按钮).
     */
    private ImageButton imageButton;
    /**
     * button:TODO(测试按钮).
     */
    private Button button;

    private Animation translateAnimation;

    private ImageView locationIV;

    private TextView warnTipTV;

    /**
     * cls:TODO(成功后跳转的界面).
     */
    private Class<?> cls;

    /**
     * SuccessCardRecord:TODO(记录上次刷卡成功的卡号).
     */
    private static String SuccessCardRecord;

    /**
     * bOnTime:TODO(定时刷卡).
     */
    private boolean bOnTime = false;

    /**
     * ON_TIME_TAG:TODO(定时消息标识).
     */
    private final static String ON_TIME_TAG = "ON_TIME_TAG";

    /**
     * mTimer:TODO(刷卡计时器).
     */
    public static Timer mTimer;

    /**
     * mTimerHandler:TODO(定时刷卡处理).
     */
    private Handler mTimerHandler;

    private TextView btnFindVirtualCard;
    private AppModule appModule;

    //
    // private IQueryRelativeConfig relation;
    //
    // private RelationTableName relationEntity;
    //
    // /**
    // * isReUseLocation:TODO(是否重用位置卡).
    // */
    // public static boolean isReUseLocation = false;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScreenSize();
        setContentView(R.layout.hd_hse_common_module_phone_swingcard_location);
        // 屏蔽点击其他区域消失
        setFinishOnTouchOutside(false);
        Intent intent = getIntent();
        cls = (Class<?>) intent.getSerializableExtra(SER_KEY_TARGETCLASS);

        appModule = (AppModule) intent.getSerializableExtra(APP_MODULE);
        // 定时刷卡
        bOnTime = intent.getBooleanExtra(ON_TIME_TAG, false);
        // lxf 添加，不是定时刷卡弹出界面，此时需要重置位置卡
        if (!bOnTime) {
            SuccessCardRecord = null;
        }
        // if (!isReUseLocation || mTimer == null || bOnTime) {
        // if (mTimer == null || bOnTime) {
        // 1.由定时器弹出的界面必须重新创建定时器，原因定时器的回调函数中已经关闭了定时器
        // 2.定时器为空的时候必须创建
        // 3.位置卡不重用时，必须创建
        // 4.第一次进来，isReUseLocation=false，创建，之后不同模块之间跳转就不会再次创建了
        // 5.操作过位置卡之后，返回到主界面再次进入时，isReUseLocation=true，但是mTimer=null，因此还是会创建
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new Timer();
        // }
        if (mTimerHandler == null)
            mTimerHandler = new OnTimeHandler();

        // 移动的imageview
        imageView = (ImageView) findViewById(R.id.hd_hse_commone_module_phone_iv_image_phone);
        imageView
                .setImageDrawable(BitmapUtils
                        .decodeBitmap(
                                getResources(),
                                R.drawable.hd_hse_common_module_phone_swinglocation_image_phone));
        // 关闭按钮
        imageButton = (ImageButton) findViewById(R.id.hd_hse_common_module_phone_locationswcard_ib_close);
        imageButton.setOnClickListener(new CloseClickListener());

        gvCache = (GridView) findViewById(R.id.hd_hse_common_module_phone_locationswcard_lv_cache);
        positionCardList = SystemProperty.getSystemProperty().getPositionCardList();
        QueryRelativeConfig queryRelativeConfig = new QueryRelativeConfig();
        boolean isQy = queryRelativeConfig
                .isHadRelative(IRelativeEncoding.REUSINGLOCATIONCARD);

        if (isQy && positionCardList != null) {
            if (positionCardList.size() > 8) {
                // 数据大于8条时，gridview设置固定高度
                android.widget.RelativeLayout.LayoutParams lp = new android.widget.RelativeLayout.LayoutParams(
                        android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
                        getResources().getDimensionPixelOffset(R.dimen.hd_hse_common_module_phone_swingcard_location_gv_height));
                gvCache.setLayoutParams(lp);
            }
            mLocationSwCardAdpter = new LocationSwCardAdpter(positionCardList,
                    LocationSwCard.this);
            gvCache.setAdapter(mLocationSwCardAdpter);
            gvCache.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    long currentTime = ServerDateManager.getCurrentTimeMillis();
                    long intervalTime = Long.parseLong(positionCardList.get(arg2).getTxtime()) * 60 * 1000;
                    long skTime = positionCardList.get(arg2).getSkTime();
                    if (currentTime - skTime > intervalTime) {
                        //移除时间超过Txtime的位置卡
                        ToastUtils.toast(LocationSwCard.this, "位置卡已失效，请重新刷卡");
                    } else {
                        SystemProperty.getSystemProperty().setPositionCard(
                                positionCardList.get(arg2));
                        startActivity(cls);
                    }


                }
            });
        }

        locationIV = (ImageView) findViewById(R.id.hd_hse_commone_module_phone_iv_image_location);
        locationIV
                .setImageDrawable(BitmapUtils
                        .decodeBitmap(
                                getResources(),
                                R.drawable.hd_hse_common_module_phone_swinglocation_image_locationcaed));

        warnTipTV = (TextView) findViewById(R.id.hd_hse_common_module_phone_tv_messagetip);
        warnTipTV.setCompoundDrawablesRelativeWithIntrinsicBounds(BitmapUtils
                        .decodeBitmap(getResources(),
                                R.drawable.hd_hse_common_module_phone_icon_warn), null,
                null, null);

        btnFindVirtualCard = (TextView) findViewById(R.id.hd_hse_commone_module_phone_find_virtual_card);
        // 获取标志位判断是否启用虚拟位置卡
        QueryRelativeConfig config = new QueryRelativeConfig();
        RelationTableName tableName = new RelationTableName();
        tableName.setSys_type(IRelativeEncoding.QYXNWZ);
        if (config.isHadRelative(tableName)) {
            btnFindVirtualCard.setVisibility(View.VISIBLE);
        } else {
            btnFindVirtualCard.setVisibility(View.GONE);
        }
        btnFindVirtualCard
                .setOnClickListener(new FindVirtualCardClickListener());

        // 测试按钮
        button = (Button) findViewById(R.id.locationtest);

        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.ISTEST);
        boolean istest = relation.isHadRelative(relationEntity);
        if (!istest) {
            button.setVisibility(View.GONE);
        }
        // relation = new QueryRelativeConfig();
        // relationEntity = new RelationTableName();
        // relationEntity.setSys_type(IRelativeEncoding.REUSINGLOCATIONCARD);
        // isReUseLocation = relation.isHadRelative(relationEntity);
        // // 判断其是否存在'重用位置'关系,并且是否刷位置卡
        // if (!bOnTime && isReUseLocation
        // && SystemProperty.getSystemProperty().getPositionCard() != null) {
        // startActivity(cls);
        // }
        // button点击事件
        button.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                startActivity(cls);
            }
        });
        // if (translateAnimation == null) {
        // setAnimationDrawable();
        // if (!translateAnimation.hasStarted())
        // // 启动动画
        // translateAnimation.startNow();
        // }
    }

    /**
     * initScreenSize:(初始化弹框尺寸). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @author wenlin
     */
    @SuppressWarnings("deprecation")
    public void initScreenSize() {
        // 没有导航栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 获取屏幕的尺寸
        Display display = this.getWindowManager().getDefaultDisplay();
        // 获取屏幕各尺寸参数
        LayoutParams mWinLayoutParams = this.getWindow().getAttributes();
        // 弹框宽度为全宽的0.9倍
        mWinLayoutParams.width = (int) (display.getWidth() * 0.9);
        // 弹框高度为屏幕的0.41倍
        mWinLayoutParams.height = (int) (display.getHeight() * 0.41);
    }

    /**
     * TODO 开始动画
     *
     * @see android.app.Fragment#onStart()
     */
    public void onStart() {
        super.onStart();
        // if (isReUseLocation
        // || SystemProperty.getSystemProperty().getPositionCard() == null) {
        // if (SystemProperty.getSystemProperty().getPositionCard() == null) {
        if (translateAnimation == null) {
            setAnimationDrawable();
            translateAnimation.startNow();
        } else {
            if (translateAnimation.hasEnded())
                // 启动动画
                translateAnimation.startNow();
        }
        // }
    }

    /**
     * TODO 刷卡动画初始化 setAnimationDrawable:() date: 2015年01月20日
     *
     * @author wenlin
     */
    private void setAnimationDrawable() {
        // 动画移动的轨迹
        translateAnimation = new TranslateAnimation(0, -250, 0, 0);
        // 单次动画持续时间
        translateAnimation.setDuration(2000);
        // 从头移动
        translateAnimation.setRepeatMode(Animation.RESTART);
        // 总共移动次数
        translateAnimation.setRepeatCount(100000);
        imageView.setAnimation(translateAnimation);
    }

    /**
     * TODO 停止动画
     *
     * @see android.app.Fragment#onStop()
     */
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        // if (translateAnimation != null) {
        // // 动画取消
        // translateAnimation.cancel();
        // }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * TODO 表示开启寻卡功能
     *
     * @see android.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        actionByCardID(cardnum);
    }

    /**
     * @see android.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        if (translateAnimation != null) {
            // 动画取消
            translateAnimation.cancel();
        }
        super.onPause();
    }

    @Override
    public void actionByCardID(String cardid) {
        // 刷卡，回调本方法
        if (cardid != null && cardid.length() > 0) {
            startActivityByCard(cardid);
        }
    }

    /**
     * startActivityByCard:(刷卡后取数据并启动新的界面). <br/>
     * date: 2015年01月20日 <br/>
     *
     * @param cardNum 卡号
     * @throws HDException
     * @author wenlin
     */
    private void startActivityByCard(String cardNum) {
        boolean iserror = false;
        if (!StringUtils.isEmpty(SuccessCardRecord)
                && !SuccessCardRecord.equalsIgnoreCase(cardNum)) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "请刷当前操作位置的位置卡");
            return;
        }
        // 位置卡查询
        PositionQuerier querier = new PositionQuerier();
        try {
            // 根据位置卡卡号查询的信息
            PositionCard perPositionCard = (PositionCard) querier
                    .query(cardNum);
            if (perPositionCard == null) {
                throw new AppException("无效卡或更新基础数据后再尝试刷卡！");
            }
            // 设置系统属性位置卡信息
            SuccessCardRecord = cardNum;
            SystemProperty.getSystemProperty().setPositionCard(perPositionCard);
            // 将当前位置卡加入集合
            SystemProperty.getSystemProperty().addPositionCard(perPositionCard);

            startActivity(cls);

        } catch (AppException e) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
            iserror = true;
        } catch (HDException e) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
            iserror = true;
        }
        // 错误处理
        if (iserror && !isend) {
            if (null != myReadCardThread
                    && myReadCardThread.getState() == java.lang.Thread.State.TERMINATED) {
                StartReadCardThread();
            } else {
                if (null != myReadCardThread) {
                    myReadCardThread.setStart();
                }
            }
        }
    }

    /**
     * startActivity:(跳转到指定作业票列表). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @param cls
     * @author wenlin
     */
    private void startActivity(Class<?> cls) {
        // // 销毁本Activity
        // popActivity();
        // 非定时刷卡，则启动下一页面
        if (!bOnTime) {
            Activity lastActivity = SystemApplication.getInstance().getLastActivity();
            Intent intent = new Intent(SystemApplication.getInstance()
                    .getLastActivity(), cls);
            if (appModule!=null){
                intent.putExtra("appmodule",appModule);
            }
            // 开启界面
            startActivity(intent);
        }
        // 启动定时器
        PositionCard posCard = SystemProperty.getSystemProperty()
                .getPositionCard();
        if (posCard != null && !StringUtils.isEmpty(posCard.getTxtime())) {
            long intervalTime = Long.parseLong(posCard.getTxtime()) * 60 * 1000;
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i("LocationCard", "mTimerHandler.sendEmptyMessage");
                    // 到时间，向UI发消息
                    mTimerHandler.sendEmptyMessage(IMessageWhat.START);
                }
            }, intervalTime);
        }
        // 销毁本Activity
        popActivity();
    }

    /**
     * TODO
     *
     * @see com.hd.hse.common.module.phone.ui.activity.BaseActivity#onKeyDown(int,
     * android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ClassName: CloseClickListener (关闭事件监听)<br/>
     * date: 2015年2月4日 <br/>
     *
     * @author lg
     * @version LocationSwCard
     */
    private class CloseClickListener implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            // 定时刷卡，则回到首页
            if (bOnTime) {
                SystemApplication.getInstance().exitToMain();
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                SystemProperty.getSystemProperty().setPositionCard(null);
            }
            // 非定时刷卡，则退出刷卡界面
            else {
                popActivity();
            }
        }

    }

    /**
     * ClassName: OnTimeHandler (定时器消息处理，定时刷卡)<br/>
     * date: 2015年2月4日 <br/>
     *
     * @author lg
     * @version LocationSwCard
     */
    private class OnTimeHandler extends Handler {

        /**
         * TODO
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (msg.what == IMessageWhat.START) {
                // 到达规定时间
                Log.i("LocationCard", "handle Message");
                SystemProperty.getSystemProperty().setPositionCard(null);
                if (AbstractAppModule.isSwCard) {
                    Intent intent = new Intent(LocationSwCard.this,
                            LocationSwCard.class);
                    intent.putExtra(ON_TIME_TAG, true);
                    startActivity(intent);
                }
                Log.i("LocationCard", "mTimer cancel");
                mTimer.cancel();
            }
        }

    }

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LocationProgressDialog mWaitingDialog;
    private LinkedList<Location> mLocationHeap;

    private boolean tmpIsLocation = false;

    private class FindVirtualCardClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            // IQueryWorkInfo _query = new QueryWorkInfo();

            // 查出所有虚拟位置卡
            // try {
            // List<PositionCard> _positionCards =
            // _query.queryVirtualCards(null);
            // } catch (HDException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            if (!GPSStateUtil.isGPSOpen(LocationSwCard.this)) {
                GPSStateUtil.openGPS(LocationSwCard.this);
                Toast.makeText(LocationSwCard.this, "请打开GPS", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mWaitingDialog == null) {
                mWaitingDialog = new LocationProgressDialog(LocationSwCard.this);
                mWaitingDialog
                        .setOnCancleClickListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 点击取消，只能在定位阶段取消。
                                if (tmpIsLocation) {
                                    // 正在定位中，可以申请终止操作
                                    stopLocation();
                                    dialog.dismiss();
                                } else {
                                    // 不能终止操作
                                    Toast.makeText(LocationSwCard.this,
                                            "不能终止！！！", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }

            mWaitingDialog.show();

            initLocation();

            openLocation();

			/*
             * IQueryWorkInfo _query = new QueryWorkInfo();
			 * 
			 * // 查出所有虚拟位置卡 try { List<PositionCard> _positionCards =
			 * _query.queryVirtualCards(null);
			 * 
			 * System.out.println("查到的位置卡："+_positionCards);
			 * 
			 * } catch (HDException e) { // TODO Auto-generated catch block
			 * e.printStackTrace();
			 * 
			 * }
			 */

        }

    }

    private void stopLocation() {
        mLocationManager.removeUpdates(mLocationListener);
        tmpIsLocation = false;
    }

    private void openLocation() {
        mLocationHeap.clear();
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, mLocationListener);

        tmpIsLocation = true;
    }

    private void initLocation() {
        // 设置 定位监听
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListenerImpl();
        mLocationHeap = new LinkedList<Location>();

    }

    private class LocationListenerImpl implements LocationListener {
        // 位置发生改变时调用
        @Override
        public void onLocationChanged(Location location) {
            if (mLocationHeap.size() < 10) {
                mLocationHeap.add(location);

                mWaitingDialog.setProgressMsg("已获得 " + mLocationHeap.size()
                        + " 次位置信息");
            } else {
                stopLocation();

                new Thread(new FixingCardTask()).run();

            }
        }

        // provider失效时调用
        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(LocationSwCard.this, provider + "GPS不可用", Toast.LENGTH_SHORT).show();
        }

        // provider启用时调用
        @Override
        public void onProviderEnabled(String provider) {
        }

        // 状态改变时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    private class FixingCardTask implements Runnable {
        @Override
        public void run() {

            logger.error("子线程启动，分析收集到的数据");
            Location _loc = analyzeValidLocation();

            if (_loc == null) {
                // 没有找到有效值， 继续打开gps定位。
                logger.error("没有找到有效值，重新打开定位");

                openLocation();

                return;
            }

            try {
                logger.error("找到有效值查询数据库");
                // 找到了有效值，根据该值查询数据库

                DecimalFormat _df = new DecimalFormat("######0.00");

                List<PositionCard> _cards = findCards(_loc.getLongitude(),
                        _loc.getLatitude());

                // 发送消息转到主线程处理
                Message _msg = Message.obtain();
                _msg.what = H_VIRTUAL_POSITION_CARD;
                _msg.obj = _cards;
                mHandler.sendMessage(_msg);

            } catch (HDException e) {
                logger.equals(e);
                mHandler.sendEmptyMessage(H_VIRTUAL_POSITION_CARD_DB_QUERY_ERROR);
            }

        }
    }

    private final static int H_VIRTUAL_POSITION_CARD = 0;
    private final static int H_VIRTUAL_POSITION_CARD_DB_QUERY_ERROR = 1;
    private Handler mHandler = new LocationHandler();

    private PositionDialog mPositionDialog;

    private class LocationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == H_VIRTUAL_POSITION_CARD) {
                mWaitingDialog.dismiss();

                List<PositionCard> _cards = (List<PositionCard>) msg.obj;

                if (_cards.size() == 1) {
                    // 匹配到一个位置卡，直接跳转
                    // 设置系统属性位置卡信息
                    Toast.makeText(LocationSwCard.this, "匹配到一个位置卡", Toast.LENGTH_SHORT).show();

                    SystemProperty.getSystemProperty().setPositionCard(
                            _cards.get(0));
                    startActivity(cls);
                }

                if (_cards.size() > 1) {
                    // 匹配到多个位置卡，弹出对话框让用户选择。
                    Toast.makeText(LocationSwCard.this,
                            "匹配到" + _cards.size() + "个位置卡", Toast.LENGTH_SHORT).show();

                    if (mPositionDialog == null) {
                        mPositionDialog = new PositionDialog<>(
                                LocationSwCard.this);
                    }
                    mPositionDialog.setDatas(_cards);
                    mPositionDialog.setiEventLsn(new MyIEventListener());
                    mPositionDialog.show();
                }

                if (_cards.size() == 0) {
                    ToastUtils.imgToast(LocationSwCard.this,
                            R.drawable.hd_common_message_error, "没有匹配到位置卡！！！");
                }
            } else if (msg.what == H_VIRTUAL_POSITION_CARD_DB_QUERY_ERROR) {
                ToastUtils.imgToast(LocationSwCard.this,
                        R.drawable.hd_common_message_error, "位置卡查询失败，请检查配置！！！");
            }

        }
    }

    private Location analyzeValidLocation() {
        Location _resultLocation = null;
        Location _otherLoc = null;

        boolean _isValid = false;

        while (!_isValid && mLocationHeap.size() >= 5) {
            // 取出第一个数据，与后四次比较，误差小于 0.00001位有效数据
            _resultLocation = mLocationHeap.remove(0);

            for (int i = 0; i < 4; i++) {
                _otherLoc = mLocationHeap.get(i);

                if ((_resultLocation.getLongitude() - _otherLoc.getLongitude()) < 0.00001) {
                    if ((_resultLocation.getLatitude() - _otherLoc
                            .getLatitude()) < 0.00001) {
                        // 此次比较合格,继续下一次
                        _isValid = true;
                        continue;
                    }
                }

                _isValid = false;
                // 不合格，中断整个循环
                break;
            }
        }

        logger.error("分析结束:" + _isValid);

        if (_isValid) {
            // 找到有效值，查询数据库
            logger.error("Lon:" + _resultLocation.getLongitude() + ";" + "lai:"
                    + _resultLocation.getLatitude());

            return _resultLocation;
        } else {
            return null;
        }
    }

    /**
     * 耗时操作，查询数据库，遍历位置卡 findCards:(). <br/>
     * date: 2015年3月26日 <br/>
     *
     * @param longitude
     * @param latitude
     * @return 匹配到的位置卡集合，如果没有匹配到，则返回的集合大小为0，而不是返回 null。
     * @throws HDException
     * @author xuxinwen
     */
    private List<PositionCard> findCards(double longitude, double latitude)
            throws HDException {
        List<PositionCard> _result = new ArrayList<PositionCard>();

        IQueryWorkInfo _query = new QueryWorkInfo();

        // 查出所有虚拟位置卡
        List<PositionCard> _positionCards = _query.queryVirtualCards(null);

        logger.error("从数据库中查到虚拟位置卡信息：" + _positionCards);

        if (_positionCards == null || _positionCards.size() == 0) {
            // 没有查询到数据，提示用户出错

            throw new HDException("没有可用的虚拟位置卡，请检查配置");
        }

        for (PositionCard item : _positionCards) {
            logger.error("本次遍历的虚拟位置卡的信息：Radiu" + item.getRadiu());

            Integer _radiu = item.getRadiu();

            if (_radiu == null) {

                continue;
            }

            double lng = Double.valueOf(item.getLongitude());
            double lat = Double.valueOf(item.getLatitude());
            if (DistanceUtils.getDistance(longitude, latitude, lng, lat) < _radiu) {
                logger.error("与当前位置匹配，添加到结果集中，");
                _result.add(item);
            }

            logger.error("本次遍历的虚拟位置卡结束：Radiu" + item.getRadiu());
        }

        return _result;
    }

    private class MyIEventListener implements IEventListener {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            if (arg0 == IEventType.POSITION_CARD) {
                SystemProperty.getSystemProperty().setPositionCard(
                        (PositionCard) arg1[0]);
                startActivity(cls);
            }
        }

    }

}
