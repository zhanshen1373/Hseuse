package com.hd.hse.common.module.phone.camera;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.media.MediaRecorder;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.hd.hse.common.module.phone.R;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

@SuppressWarnings("all")
public class CameraManager {
    private final static String TAG = "CameraManager";

    private final static int FOCUS_WIDTH = 80;
    private final static int FOCUS_HEIGHT = 80;

    private final static boolean DEBUG_LOG_PARAMS = false;

    private CameraPreview mPreview;
    private Camera mCamera;
    private boolean mCameraReady;
    private int mCurrentFacing;
    private AutoFocusMoveCallback mAutoFocusMoveCallback;
    private Camera.Parameters mParameters;
    private int mOrientation;
    private MediaRecorder mMediaRecorder;
    private CameraReadyListener mCameraReadyListener;
    private Handler mHandler;
    private Activity mContext;
    private boolean mIsModeSwitching;
    private List<NameValuePair> mPendingParameters;
    private CameraRenderer mRenderer;
    private boolean mIsRecordingHint;
    private boolean mIsPreviewStarted;
    private boolean mParametersBatch;

    public Context getContext() {
        return mContext;
    }

    public interface CameraReadyListener {
        public void onCameraReady();

        public void onCameraFailed();
    }

    private class ParametersThread extends Thread {
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }

                    String existingParameters = getParameters().flatten();
                    if (existingParameters == null) continue;

                    List<NameValuePair> copy = new ArrayList<NameValuePair>(mPendingParameters);
                    mPendingParameters.clear();

                    Camera.Parameters params = getParameters();

                    for (NameValuePair pair : copy) {
                        String key = pair.getName();
                        String val = pair.getValue();
                        Log.v(TAG, "Setting parameter " + key + " to " + val);
                        params.set(key, val);
                    }

                    try {
                        mCamera.setParameters(params);
                    } catch (RuntimeException e) {
                        Log.e(TAG, "Could not set parameters batch", e);
                    }

                    mParameters = null;
                }
            }
        }
    }

    private ParametersThread mParametersThread = null;
    final Object mParametersSync = new Object();

    public CameraManager(Activity context) {
        mPreview = new CameraPreview();
        mMediaRecorder = new MediaRecorder();
        mCameraReady = true;
        mHandler = new Handler();
        mIsModeSwitching = false;
        mContext = context;
        mPendingParameters = new ArrayList<NameValuePair>();
        mParametersThread = new ParametersThread();
        mParametersThread.start();
        mIsRecordingHint = false;
        mRenderer = new CameraRenderer();
        mIsPreviewStarted = false;
    }

    public boolean open(final int cameraId) {
        if (mCamera != null) {
            releaseCamera();
        }

        mCameraReady = false;

        new Thread() {
            public void run() {
                try {
                    if (mCamera != null) {
                        Log.e(TAG, "Previous camera not closed! Not opening");
                        return;
                    }

                    mCamera = Camera.open(cameraId);


                    Log.v(TAG, "Camera is open");

                    if (Build.VERSION.SDK_INT >= 17) {
                        mCamera.enableShutterSound(false);
                    }
                    mCamera.setPreviewCallback(mPreview);
                    mCurrentFacing = cameraId;
                    mParameters = mCamera.getParameters();

                    if (DEBUG_LOG_PARAMS) {
                        String params = mCamera.getParameters().flatten();
                        final int step = params.length() > 256 ? 256 : params.length();
                        for (int i = 0; i < params.length(); i += step) {
                            Log.d(TAG, params);
                            params = params.substring(step);
                        }
                    }

                    if (Build.DEVICE.equals("mako")) {
                        Camera.Size maxSize = mParameters.getSupportedPictureSizes().get(0);
                        mParameters.setPictureSize(maxSize.width, maxSize.height);
                    }

                    if (mAutoFocusMoveCallback != null) {
                        setAutoFocusMoveCallback(mAutoFocusMoveCallback);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error while opening cameras: " + e.getMessage(), e);

                    if (mCameraReadyListener != null) {
                        mCameraReadyListener.onCameraFailed();
                    }

                    return;
                }

                mPreview.notifyCameraChanged(false);

                if (mCameraReadyListener != null) {
                    mCameraReadyListener.onCameraReady();
                }

                mPreview.setPauseCopyFrame(false);
                mCameraReady = true;
            }
        }.start();

        return true;
    }

    public void setCameraReadyListener(CameraReadyListener listener) {
        mCameraReadyListener = listener;
    }

    public CameraPreview getPreviewSurface() {
        return mPreview;
    }

    public CameraRenderer getRenderer() {
        return mRenderer;
    }

    public int getCurrentFacing() {
        return mCurrentFacing;
    }

    public Camera.Parameters getParameters() {
        synchronized (mParametersSync) {
            if (mCamera == null) {
                Log.w(TAG, "getParameters when camera is null");
                return null;
            }

            int tries = 0;
            while (mParameters == null) {
                try {
                    mParameters = mCamera.getParameters();
                    break;
                } catch (RuntimeException e) {
                    Log.e(TAG, "Error while getting parameters: ", e);
                    if (tries < 3) {
                        tries++;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "Failed to get parameters after 3 tries");
                        break;
                    }
                }
            }
        }

        return mParameters;
    }

    public void pause() {
        mPreview.setPauseCopyFrame(true);
        releaseCamera();
        mParametersThread.interrupt();
        mParametersThread = null;
    }

    public void resume() {
        reconnectToCamera();
        mParametersThread = new ParametersThread();
        mParametersThread.start();
    }

    public void forceCloseCamera() {
        if (mCamera != null) {
            try {
                mCamera.release();
                mCamera = null;
                mParameters = null;
            } catch (Exception e) {

            }
        }
    }

    private void releaseCamera() {
        if (mCamera != null && mCameraReady) {
            Log.v(TAG, "Releasing camera facing " + mCurrentFacing);
            mCamera.release();
            mCamera = null;
            mParameters = null;
            mPreview.notifyCameraChanged(false);
            mCameraReady = true;
        }
    }

    public void reconnectToCamera() {
        if (mCameraReady) {
            open(mCurrentFacing);
        } else {
            Log.e(TAG, "reconnectToCamera but camera not ready!");
        }
    }

    public void setPreviewSize(int width, int height) {
        if (mCamera != null) {
            Camera.Parameters params = getParameters();
            params.setPreviewSize(width, height);

            Log.v(TAG, "Preview size is " + width + "x" + height);

            if (!mIsModeSwitching) {
                synchronized (mParametersSync) {
                    try {
                        safeStopPreview();
                        mParameters = params;
                        mCamera.setParameters(mParameters);
                        mPreview.notifyPreviewSize(width, height);

                        updateDisplayOrientation();
                        safeStartPreview();

                        mPreview.setPauseCopyFrame(false);
                    } catch (RuntimeException ex) {
                        Log.e(TAG, "Unable to set Preview Size", ex);
                    }
                }

                Log.d(TAG, "setPreviewSize - stop");
            }
        }
    }

    private void safeStartPreview() {
        if (!mIsPreviewStarted && mCamera != null) {
            Log.d(TAG, "safeStartPreview");
            mCamera.startPreview();
            mIsPreviewStarted = true;
        }
    }

    private void safeStopPreview() {
        if (mIsPreviewStarted && mCamera != null) {
            Log.d(TAG, "safeStopPreview");
            mCamera.stopPreview();
            mIsPreviewStarted = false;
        }
    }

    public void setParameters(Camera.Parameters params) {
        synchronized (mParametersSync) {
            mCamera.setParameters(params);
        }
    }

    public void setLockSetup(boolean lock) {
        final Camera.Parameters params = getParameters();

        if (params == null) {
            return;
        }

        if (params.isAutoExposureLockSupported()) {
            params.setAutoExposureLock(lock);
        }

        if (params.isAutoWhiteBalanceLockSupported()) {
            params.setAutoWhiteBalanceLock(lock);
        }

        new Thread() {
            public void run() {
                synchronized (mParametersSync) {
                    try {
                        mCamera.setParameters(params);
                    } catch (RuntimeException e) {

                    }
                }
            }
        }.start();
    }

    public void setPictureSize(String sz) {
        String[] splat = sz.split("x");
        int width = Integer.parseInt(splat[0]);
        int height = Integer.parseInt(splat[1]);

        Log.v(TAG, "setPictureSize " + width + "x" + height);
        Camera.Parameters params = getParameters();
        params.setPictureSize(width, height);

//        Camera.Size optimalPreview = Util.getOptimalPreviewSize(mContext, params.getSupportedPreviewSizes(), ((float) width / (float) height));
//        setPreviewSize(optimalPreview.width, optimalPreview.height);
    }

    public void takeSnapshot(final Camera.ShutterCallback shutterCallback, final Camera.PictureCallback raw, final Camera.PictureCallback jpeg) {
        if (Util.deviceNeedsStopPreviewToShoot()) {
            safeStopPreview();
        }

        SoundManager.getSingleton().play(SoundManager.SOUND_SHUTTER);

        if (mCamera != null) {
            new Thread() {
                public void run() {
                    try {
                        mCamera.takePicture(shutterCallback, raw, jpeg);
                    } catch (RuntimeException e) {
                        Log.e(TAG, "Unable to take picture", e);
                    }
                }
            }.start();
        }
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(int orientation) {
        orientation += 90;
        if (mOrientation == orientation) return;

        mOrientation = orientation;

        Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        Camera.getCameraInfo(mCurrentFacing, info);
        //orientation = (360 - orientation + 45) / 90 * 90;
        int rotation = 0;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotation = (info.orientation - orientation + 360) % 360;
        } else {  // back-facing camera
            rotation = (info.orientation + orientation) % 360;
        }
    }

    public void restartPreviewIfNeeded() {
        new Thread() {
            public void run() {
                synchronized (mParametersSync) {
                    try {
                        mCamera.startPreview();
                        mPreview.setPauseCopyFrame(false);
                    } catch (Exception e) {

                    }

                    mIsPreviewStarted = true;
                }
            }
        }.start();
    }

    public void updateDisplayOrientation() {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(mCurrentFacing, info);
        int degrees = 0;

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }

    public boolean doAutofocus(final AutoFocusCallback cb) {
        if (mCamera != null) {
            try {
                setLockSetup(false);
                mCamera.cancelAutoFocus();
                mHandler.post(new Runnable() {
                    public void run() {
                        try {
                            mCamera.autoFocus(cb);
                        } catch (Exception e) {

                        }
                    }
                });

            } catch (Exception e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public void setFocusPoint(int x, int y) {
        if (x < -1000 || x > 1000 || y < -1000 || y > 1000) {
            Log.e(TAG, "setFocusPoint: values are not ideal " + "x= " + x + " y= " + y);
            return;
        }
        Camera.Parameters params = getParameters();

        if (params != null && params.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusArea = new ArrayList<Camera.Area>();
            focusArea.add(new Camera.Area(new Rect(x, y, x + FOCUS_WIDTH, y + FOCUS_HEIGHT), 1000));

            params.setFocusAreas(focusArea);

            try {
                mCamera.setParameters(params);
            } catch (Exception e) {

            }
        }
    }

    public void setAutoFocusMoveCallback(AutoFocusMoveCallback cb) {
        mAutoFocusMoveCallback = cb;

        List<String> focusModes = mParameters.getSupportedFocusModes();
        if (mCamera != null && focusModes != null && focusModes.contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            try {
                mCamera.setAutoFocusMoveCallback(cb);
            } catch (RuntimeException e) {
                Log.e(TAG, "Unable to set AutoFocusMoveCallback", e);
            }
        }
    }

    public void setRenderToTexture(SurfaceTexture texture) {
        mPreview.setRenderToTexture(texture);
        mPreview.notifyCameraChanged(true);
    }

    public class CameraPreview implements Camera.PreviewCallback {
        private final static String TAG = "CameraManager.CameraPreview";

        private SurfaceTexture mTexture;
        private byte[] mLastFrameBytes;
        private boolean mPauseCopyFrame;

        public CameraPreview() {
        }

        public void setRenderToTexture(SurfaceTexture texture) {
            mTexture = texture;
        }

        public void setPauseCopyFrame(boolean pause) {
            mPauseCopyFrame = pause;

            if (!pause && mCamera != null) {
                postCallbackBuffer();
            }
        }

        public void notifyPreviewSize(int width, int height) {
            mLastFrameBytes = new byte[2048000];
            mRenderer.updateRatio(width, height);
        }

        public byte[] getLastFrameBytes() {
            return mLastFrameBytes;
        }

        public void notifyCameraChanged(boolean startPreview) {
            synchronized (mParametersSync) {
                if (mCamera != null) {
                    if (startPreview) {
                        safeStopPreview();
                    }

                    setupCamera();

                    try {
                        mCamera.setPreviewTexture(mTexture);

                        if (startPreview) {
                            updateDisplayOrientation();
                            safeStartPreview();
                            postCallbackBuffer();
                        }
                    } catch (RuntimeException e) {
                        Log.e(TAG, "Cannot set preview texture", e);
                    } catch (IOException e) {
                        Log.e(TAG, "Error setting camera preview", e);
                    }
                }
            }
        }

        public void restartPreview() {
            synchronized (mParametersSync) {
                if (mCamera != null) {
                    try {
                        safeStopPreview();
                        mCamera.setParameters(mParameters);

                        updateDisplayOrientation();
                        safeStartPreview();
                        setPauseCopyFrame(false);

                    } catch (RuntimeException e) {
                        Log.e(TAG, "Cannot set preview texture", e);
                    }
                }
            }
        }

        public void postCallbackBuffer() {
            if (mCamera != null && !mPauseCopyFrame) {
                mCamera.addCallbackBuffer(mLastFrameBytes);
                mCamera.setPreviewCallbackWithBuffer(CameraPreview.this);
            }
        }

        private void setupCamera() {
            try {
                Resources res = mContext.getResources();

                if (res != null) {
                    if (res.getBoolean(R.bool.config_qualcommZslCameraMode) && !Util.deviceNeedsDisableZSL()) {
                        mParameters.set("camera-mode", 1);
                    }
                }
                mCamera.setDisplayOrientation(90);
                mCamera.setParameters(mParameters);

                postCallbackBuffer();
            } catch (Exception e) {
                Log.e(TAG, "Could not set device specifics");

            }
        }

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (mCamera != null && !mPauseCopyFrame) {
                mCamera.addCallbackBuffer(mLastFrameBytes);
            }
        }
    }

    public class CameraRenderer implements GLSurfaceView.Renderer {
        private final float[] mTransformMatrix;
        int mTexture;
        private SurfaceTexture mSurface;

        private final static String VERTEX_SHADER =
                "attribute vec4 vPosition;\n" +
                        "attribute vec2 a_texCoord;\n" +
                        "varying vec2 v_texCoord;\n" +
                        "uniform mat4 u_xform;\n" +
                        "void main() {\n" +
                        "  gl_Position = vPosition;\n" +
                        "  v_texCoord = vec2(u_xform * vec4(a_texCoord, 1.0, 1.0));\n" +
                        "}\n";

        private final static String FRAGMENT_SHADER =
                "#extension GL_OES_EGL_image_external : require\n" +
                        "precision mediump float;\n" +
                        "uniform samplerExternalOES s_texture;\n" +
                        "varying vec2 v_texCoord;\n" +
                        "void main() {\n" +
                        "  gl_FragColor = texture2D(s_texture, v_texCoord);\n" +
                        "}\n";

        private FloatBuffer mVertexBuffer, mTextureVerticesBuffer;
        private int mProgram;
        private int mPositionHandle;
        private int mTextureCoordHandle;
        private int mTransformHandle;
        private int mWidth;
        private int mNaturalWidth;
        private int mHeight;
        private int mNaturalHeight;
        private float mNaturalRatio;
        private float mRatio = 4.0f / 3.0f;
        private float mUpdateRatioTo = -1;
        private Object fSync = new Object();

        static final int COORDS_PER_VERTEX = 2;
        private float mSquareVertices[];
        private final float mTextureVertices[] = {1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

        public CameraRenderer() {
            mTransformMatrix = new float[16];
        }

        public void updateRatio(int width, int height) {
            synchronized (fSync) {
                mUpdateRatioTo = 1;

                float ratio = (float) width / (float) height;

                if (ratio != mNaturalRatio) {
                    float widthRatio = (float) mNaturalWidth / (float) width;
                    float heightRatio = (float) mNaturalHeight / (float) height;
                    mRatio = widthRatio / heightRatio;
                } else {
                    mRatio = 1;
                }
                Log.d(TAG, "updateRatio " + width + "x" + height + " mRatio=" + mRatio);
            }
        }

        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            mTexture = createTexture();
            mSurface = new SurfaceTexture(mTexture);
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

            setRenderToTexture(mSurface);

            ByteBuffer bb2 = ByteBuffer.allocateDirect(mTextureVertices.length * 4);
            bb2.order(ByteOrder.nativeOrder());
            mTextureVerticesBuffer = bb2.asFloatBuffer();
            mTextureVerticesBuffer.put(mTextureVertices);
            mTextureVerticesBuffer.position(0);

            // Load shaders
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

            mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
            GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
            GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
            GLES20.glLinkProgram(mProgram);

            GLES20.glUseProgram(mProgram);

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTexture);

            mSquareVertices = new float[]{1.0f * 1.0f,
                    1.0f, -1.0f,
                    1.0f, -1.0f,
                    -1.0f,
                    1.0f * 1.0f, -1.0f};

            ByteBuffer bb = ByteBuffer.allocateDirect(mSquareVertices.length * 4);
            bb.order(ByteOrder.nativeOrder());
            mVertexBuffer = bb.asFloatBuffer();
            mVertexBuffer.put(mSquareVertices);
            mVertexBuffer.position(0);

            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
            GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                    false, 0, mVertexBuffer);
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "a_texCoord");
            GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                    false, 0, mTextureVerticesBuffer);
            GLES20.glEnableVertexAttribArray(mTextureCoordHandle);

            mTransformHandle = GLES20.glGetUniformLocation(mProgram, "u_xform");
        }

        public void onDrawFrame(GL10 unused) {
            synchronized (fSync) {
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

                if (mUpdateRatioTo > 0) {
                    Log.d(TAG, "onDrawFrame " + " mRatio=" + mRatio);
                    int deltaWidth = (int) Math.abs(mWidth - mWidth * mRatio);
                    GLES20.glViewport(-deltaWidth / 2, 0,
                            (int) (mWidth * mRatio + deltaWidth / 2.0f), mHeight);
                    mUpdateRatioTo = -1;
                }

                if (mSurface != null) {
                    mSurface.updateTexImage();
                    mSurface.getTransformMatrix(mTransformMatrix);
                    GLES20.glUniformMatrix4fv(mTransformHandle, 1, false, mTransformMatrix, 0);
                }

                if (mUpdateRatioTo > 0) {
                    GLES20.glViewport(0, 0, (int) (mWidth * mRatio), mHeight);
                    mUpdateRatioTo = -1;
                }

                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
            }
        }

        public void onSurfaceChanged(GL10 unused, int width, int height) {
            mWidth = width;
            mHeight = height;

            if (mWidth > mHeight) {
                mNaturalWidth = mWidth;
                mNaturalHeight = mHeight;
            } else {
                mNaturalWidth = mHeight;
                mNaturalHeight = mWidth;
            }
            mNaturalRatio = (float) mNaturalWidth / (float) mNaturalHeight;
            mRatio = mNaturalRatio;
            GLES20.glViewport(0, 0, width, height);
            Log.d(TAG, "onSurfaceChanged " + width + "x" + height + " mNaturalRatio=" + mNaturalRatio);
        }

        public int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);

            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);

            return shader;
        }

        private int createTexture() {
            int[] texture = new int[1];

            GLES20.glGenTextures(1, texture, 0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);

            return texture[0];
        }
    }
}