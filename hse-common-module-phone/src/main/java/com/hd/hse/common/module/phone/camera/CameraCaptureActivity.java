package com.hd.hse.common.module.phone.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.camera.CameraManager.CameraReadyListener;
import com.hd.hse.common.module.phone.camera.CameraManager.CameraRenderer;
import com.hd.hse.common.module.phone.camera.SnapshotManager.SnapshotListener;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;

import org.apache.log4j.Logger;

import java.util.List;

@SuppressWarnings("all")
public class CameraCaptureActivity extends Activity implements CameraReadyListener, SnapshotListener, OnClickListener {

    private static final String TAG = CameraCaptureActivity.class.getSimpleName();
    private static Logger logger = LogUtils.getLogger(CameraCaptureActivity.class);

    public final static int CAMERA_MODE_PHOTO = 1;
    private CameraManager mCameraManager;
    private SnapshotManager mSnapshotManager;
    private static int sCameraMode = CAMERA_MODE_PHOTO;
    private GLSurfaceView mGLSurfaceView;
    private ShutterButton mShutterButton;
    private Handler mHander;

    private CameraOrientationEventListener mOrientationListener;
    private int mOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;
    private int mOrientationCompensation = 0;

    private ScaleGestureDetector mZoomGestureDetector;

    private FocusHudRing mFocusHudRing;
    private FocusManager mFocusManager;
    private boolean mIsFocusing = false;

    private Image imageEntity;
    public static final String ENTITY_ARGS = "entity";

    /**
     * 时间间隔为1s
     */
    private final long timeLag = 1000;
    private long currentTime;
    private long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.hd_hse_common_module_phone_layout_camera_capture);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        mHander = new Handler();

        Intent intent = getIntent();
        imageEntity = (Image) intent.getSerializableExtra(ENTITY_ARGS);

        setupCamera();
        SoundManager.getSingleton().preload(this);

        mFocusHudRing = (FocusHudRing) findViewById(R.id.hud_ring_focus);

        mShutterButton = (ShutterButton) findViewById(R.id.btn_shutter);
        mShutterButton.setOnClickListener(this);

        mZoomGestureDetector = new ScaleGestureDetector(this, new ZoomGestureListener());

        mOrientationListener = new CameraOrientationEventListener(this);
        mOrientationListener.enable();

        findViewById(R.id.gl_renderer_container).setOnTouchListener(mPreviewTouchListener);
    }

    private void setupCamera() {
        mCameraManager = new CameraManager(this);
        ((MainApplication) getApplication()).setCameraManager(mCameraManager);

        setGLRenderer(mCameraManager.getRenderer());
        mCameraManager.setCameraReadyListener(this);
        mCameraManager.open(Camera.CameraInfo.CAMERA_FACING_BACK);

    }

    private void setGLRenderer(CameraRenderer renderer) {
        FrameLayout container = (FrameLayout) findViewById(R.id.gl_renderer_container);
        if (mGLSurfaceView != null) {
            container.removeView(mGLSurfaceView);
            mGLSurfaceView = null;
        }

        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(renderer);

        container.addView(mGLSurfaceView);
    }

    @Override
    public void onCameraReady() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Camera.Parameters params = mCameraManager.getParameters();
                if (params == null) {
                    mHander.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            onCameraReady();
                        }
                    }, 20);
                    return;
                }

                mCameraManager.updateDisplayOrientation();
                Camera.Size picSize = params.getPictureSize();
                Camera.Size sz = Util.getOptimalPreviewSize(CameraCaptureActivity.this, params.getSupportedPreviewSizes(),
                        ((float) picSize.width / (float) picSize.height));
                if (sz == null) {
                    return;
                }

                mCameraManager.setPreviewSize(sz.width, sz.height);

                List<Size> pictureSizes = params.getSupportedPictureSizes();
                int pictureWidth = 0;
                int pictureHeight = 0;
                for (int i = 0; i < pictureSizes.size(); i++) {
                    Size size = pictureSizes.get(i);
                    int sizeWidth = size.width;
                    int sizeHeight = size.height;
                    if (i == 0) {
                        pictureWidth = sizeWidth;
                        pictureHeight = sizeHeight;
                    } else {
                        if (Math.abs(pictureWidth - 1280) > Math.abs(sizeWidth - 1280)) {
                            pictureWidth = sizeWidth;
                            pictureHeight = sizeHeight;
                        }
                    }

                    System.out.println("支持的照片尺寸=" + size.width + " " + size.height);
                }
                mCameraManager.setPictureSize(pictureWidth + "x" + pictureHeight);

                if (mFocusManager == null) {
                    mFocusManager = new FocusManager(mCameraManager);
                    mFocusManager.setFocusListener(new CameraFocusListener());
                }

                mFocusHudRing.setManagers(mCameraManager, mFocusManager);

                if (mSnapshotManager == null) {
                    mSnapshotManager = new SnapshotManager(mCameraManager);
                    mSnapshotManager.setSnapshotListener(CameraCaptureActivity.this);
                }
            }
        });
    }

    private class CameraFocusListener implements FocusManager.FocusListener {

        @Override
        public void onFocusStart(final boolean smallAdjust) {
            mIsFocusing = true;
            runOnUiThread(new Runnable() {
                public void run() {
                    mFocusHudRing.animateWorking(smallAdjust ? 200 : 1500);
                }
            });
        }

        @Override
        public void onFocusReturns(final boolean smallAdjust, final boolean success) {
            mIsFocusing = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mFocusHudRing.animatePressUp();

                    if (!smallAdjust) {
                        mFocusHudRing.setFocusImage(success);
                    } else {
                        mFocusHudRing.setFocusImage(true);
                    }
                }
            });
        }
    }

    @Override
    public void onCameraFailed() {
        Log.e(TAG, "不能开启Camera!!!");
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(CameraCaptureActivity.this, "相机无法打开！请打开相机的系统权限或重新启动相机", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static int getCameraMode() {
        return sCameraMode;
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id.btn_shutter) {
            if (mSnapshotManager != null) {
                //两次拍照时间大于1s
                currentTime = ServerDateManager.getCurrentTimeMillis();
                if (currentTime - lastTime >= timeLag) {
                    mSnapshotManager.queueSnapshot(imageEntity);
                    lastTime = currentTime;
                }

            }
        }
    }

    @Override
    protected void onResume() {
        if (mCameraManager != null) {
            mCameraManager.resume();
        }

        super.onResume();

        mOrientationListener.enable();
    }

    @Override
    protected void onPause() {
        if (mCameraManager != null) {
            mCameraManager.pause();
        }

        if (mOrientationListener != null) {
            mOrientationListener.disable();
        }

        super.onPause();
    }

    private class CameraOrientationEventListener extends OrientationEventListener {
        public CameraOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == ORIENTATION_UNKNOWN) {
                return;
            }
            mOrientation = Util.roundOrientation(orientation, mOrientation);
            mCameraManager.setOrientation(mOrientation);

            Configuration config = getResources().getConfiguration();
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            Util.getDisplayRotation(CameraCaptureActivity.this);


            if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
                    && config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                    || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)
                    && config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
            }

            int orientationCompensation = mOrientation; // + (nativeLandscape ? 0 : 90);
            if (orientationCompensation == 90) {
                orientationCompensation += 180;
            } else if (orientationCompensation == 270) {
                orientationCompensation -= 180;
            }

            float angleDelta = orientationCompensation - mOrientationCompensation;
            if (angleDelta >= 270) {
                orientationCompensation -= 360;
            }

            if (mOrientationCompensation != orientationCompensation) {
                mOrientationCompensation = orientationCompensation;
                updateInterfaceOrientation();
            }
        }
    }

    private View.OnTouchListener mPreviewTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mZoomGestureDetector.onTouchEvent(event);
            return true;
        }
    };

    private class ZoomGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Camera.Parameters params = mCameraManager.getParameters();
            if (params == null) {
                return false;
            }

            if (!mIsFocusing) {
                if (detector.getScaleFactor() > 1.f) {
                    params.setZoom(Math.min(params.getZoom() + 1, params.getMaxZoom()));
                } else if (detector.getScaleFactor() < 1.f) {
                    params.setZoom(Math.max(params.getZoom() - 1, 0));
                } else {
                    return false;
                }

                mCameraManager.setParameters(params);
            }
            return true;
        }
    }

    public void updateInterfaceOrientation() {
        setViewRotation(mShutterButton, mOrientationCompensation);
    }

    public static void setViewRotation(View v, float rotation) {
        v.animate().rotation(rotation).setDuration(200)
                .setInterpolator(new DecelerateInterpolator()).start();
    }

    @Override
    public void onSnapshotSavedSuccess() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(CameraCaptureActivity.this, "照片保存成功", Toast.LENGTH_SHORT).show();
                if (imageEntity.getAttribute("imagename").equals("rpicture")) {
                    Intent intent = new Intent();
                    String path = (String) imageEntity.getAttribute("path");
                    intent.putExtra("path", path);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSnapshotSavedFailed() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(CameraCaptureActivity.this, "照片保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}