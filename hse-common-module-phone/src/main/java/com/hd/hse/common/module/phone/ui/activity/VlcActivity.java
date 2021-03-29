package com.hd.hse.common.module.phone.ui.activity;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.camera.CloseSxt;
import com.hd.hse.common.module.phone.camera.DelayCloseSxt;
import com.hd.hse.common.module.phone.ui.custom.ZdyRelativeLayout;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.SxtBean;
import com.hd.hse.system.SystemProperty;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.IVLCVout;

import java.util.ArrayList;

public class VlcActivity extends AppCompatActivity implements View.OnClickListener {


    private SurfaceView surfaceView;
    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;
    private String mUrl = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
    private IVLCVout vlcVout;
    private ZdyRelativeLayout syView;
    private LinearLayout exitfullping_ll;
    private RelativeLayout enterfullping_rl;
    private ImageView exitfullping_iv;
    private ImageView enterfullping_iv;
    private TextView topleftTv;
    private TextView toprightTv;
    private TextView bottomleftTv;
    private TextView bottomrightTv;
    private TextView centerTv;
    private int height;
    private int width;
    private String url;
    private int exitfullping_llheight;
    private int enterfullping_rlheight;
    private SxtBean.CamerasBean camerasBean;
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //隐藏掉条目
                    ObjectAnimator animator = ObjectAnimator.ofFloat(exitfullping_ll, "translationY", 0, -exitfullping_ll.getHeight());
                    animator.setDuration(2000);
                    animator.start();

                    ObjectAnimator animatorp = ObjectAnimator.ofFloat(enterfullping_rl, "translationY", 0, enterfullping_rl.getHeight());
                    animatorp.setDuration(2000);
                    animatorp.start();
                    break;
                case 1:
                    delayCloseSxt.geteDownLoadWorkList("", camerasBean, "delay");
                    break;

            }
            super.handleMessage(msg);
        }
    };
    private int widthPixels;
    private int heightPixels;
    private CloseSxt closeSxt;
    private DelayCloseSxt delayCloseSxt;
    private int statusBarHeight;
    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        statusBarHeight = getStatusBarHeight(this);
        setContentView(R.layout.activity_vlc);
        closeSxt = new CloseSxt(this);
        closeSxt.setGetDataSourceListener(eventLst);
        delayCloseSxt = new DelayCloseSxt(this);
        delayCloseSxt.setGetDataSourceListener(eventLst);
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("address");
            camerasBean = (SxtBean.CamerasBean) intent.getSerializableExtra("cameras");
        }

        surfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        syView = (ZdyRelativeLayout) findViewById(R.id.sy_view);
        exitfullping_ll = findViewById(R.id.exitfullping_ll);
        enterfullping_rl = findViewById(R.id.enterfullping_rl);
        exitfullping_iv = findViewById(R.id.exitfullping_iv);
        enterfullping_iv = findViewById(R.id.enterfullping_iv);
        topleftTv = findViewById(R.id.topleft);
        toprightTv = findViewById(R.id.topright);
        bottomleftTv = findViewById(R.id.bottomleft);
        bottomrightTv = findViewById(R.id.bottomright);
        centerTv = findViewById(R.id.center);
        preInit();
        init(surfaceView);
        initEvent();
        syView.setHandler(hd);
        hd.sendEmptyMessageDelayed(0, 2000);
        hd.sendEmptyMessageDelayed(1, 10000);
    }

    IEventListener eventLst = new IEventListener() {
        @SuppressWarnings("unchecked")
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.CLOSE_SXT:

                    if (mMediaPlayer != null) {
                        mMediaPlayer.pause();
                        mMediaPlayer.stop();
                        vlcVout.detachViews();
                        mMediaPlayer = null;
                        mLibVLC.release();
                        mLibVLC = null;
                    }


                    if (hd != null) {
                        hd.removeCallbacksAndMessages(null);
                        hd = null;
                    }
                    finish();

                    break;
                case IEventType.DELAY_CLOSE_SXT:
                    if (hd != null)
                        hd.sendEmptyMessageDelayed(1, 10000);
                    break;

            }
        }
    };


    private void initEvent() {


        exitfullping_ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                exitfullping_llheight = exitfullping_ll.getHeight();

                exitfullping_ll.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
        enterfullping_rl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                enterfullping_rlheight = enterfullping_rl.getHeight();

                enterfullping_rl.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        exitfullping_iv.setOnClickListener(this);
        enterfullping_iv.setOnClickListener(this);
        syView.setOnClickListener(this);

    }


    private void preInit() {
        final ArrayList<String> args = new ArrayList<>();
        args.add("-vvv");
        mLibVLC = new LibVLC(this, args);


        Media media = new Media(mLibVLC, Uri.parse(url));
        media.setHWDecoderEnabled(true, true);
        media.addOption(":no-audio");
        media.addOption(":network-caching=150");
        media.addOption(":file-caching=150");
        media.addOption(":sout-mux-caching=150");
        media.addOption(":live-caching=150");
        mMediaPlayer = new MediaPlayer(media);
        mMediaPlayer.setScale(0);

        media.release();

        mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
            @Override
            public void onEvent(MediaPlayer.Event event) {

                if (event.type == MediaPlayer.Event.EncounteredError) {
                    Toast.makeText(VlcActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!vlcVout.areViewsAttached()) {
            SurfaceHolder holder = surfaceView.getHolder();
            holder.setKeepScreenOn(true);
            vlcVout.setVideoSurface(holder.getSurface(), holder);
            if (layoutParams != null) {
                mMediaPlayer.setAspectRatio(layoutParams.width + ":" + layoutParams.height);
                vlcVout.setWindowSize(layoutParams.width, layoutParams.height);
            } else {
                mMediaPlayer.setAspectRatio(width + ":" + height);
                vlcVout.setWindowSize(width, height);
            }
            vlcVout.attachViews();
        }


    }

    public void init(final SurfaceView sView) {

        SurfaceHolder holder = sView.getHolder();
        holder.setKeepScreenOn(true);
        vlcVout = mMediaPlayer.getVLCVout();
        vlcVout.setVideoSurface(holder.getSurface(), sView.getHolder());
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        topleftTv.setText(personCard.getPersonid());
        toprightTv.setText(personCard.getPersonid());
        bottomleftTv.setText(personCard.getPersonid());
        bottomrightTv.setText(personCard.getPersonid());
        centerTv.setText(personCard.getPersonid());


        sView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = sView.getHeight();
                width = sView.getWidth();
                sView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mMediaPlayer.setAspectRatio(width + ":" + height);
                vlcVout.attachViews();
                vlcVout.setWindowSize(width, height);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying())
            mMediaPlayer.play();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeSxt.geteDownLoadWorkList("", camerasBean, "");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.exitfullping_iv) {
//            if (mMediaPlayer != null) {
//                mMediaPlayer.pause();
//                mMediaPlayer.stop();
//                vlcVout.detachViews();
//                mMediaPlayer = null;
//                mLibVLC.release();
//                mLibVLC = null;
//            }
            closeSxt.geteDownLoadWorkList("", camerasBean, "");

        } else if (i == R.id.enterfullping_iv) {
            //判断当前屏幕方向
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                //切换竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            } else {
                //切换横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            }
        } else if (i == R.id.sy_view) {
            float y = getvh(exitfullping_ll);
            if (y <= -(exitfullping_llheight - statusBarHeight)) {
                showLayoutView();
            }
        }


    }

    private float getvh(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    private void showLayoutView() {
        //展示出上下条目
        ObjectAnimator animator = ObjectAnimator.ofFloat(exitfullping_ll, "translationY", -exitfullping_llheight, 0);
        animator.setDuration(2000);
        animator.start();

        ObjectAnimator animatorp = ObjectAnimator.ofFloat(enterfullping_rl, "translationY", enterfullping_rlheight, 0);
        animatorp.setDuration(2000);
        animatorp.start();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //变成横屏了
            GetScreenWH(newConfig.orientation);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //变成竖屏了
            GetScreenWH(newConfig.orientation);
        }

    }

    private void GetScreenWH(int fx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;

        layoutParams = surfaceView.getLayoutParams();
        layoutParams.width = widthPixels;
        if (fx == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            layoutParams.height = heightPixels - statusBarHeight;
        } else if (fx == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            layoutParams.height = height;
        }
        surfaceView.setLayoutParams(layoutParams);
        syView.setLayoutParams(layoutParams);

        mMediaPlayer.setAspectRatio(layoutParams.width + ":" + layoutParams.height);
        vlcVout.setWindowSize(layoutParams.width, layoutParams.height);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
