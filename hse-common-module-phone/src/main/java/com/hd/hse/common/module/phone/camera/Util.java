package com.hd.hse.common.module.phone.camera;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@SuppressWarnings("all")
public class Util {
    public final static String TAG = "Nemesis.Util";

    public static final int ORIENTATION_HYSTERESIS = 5;
    public static final String REVIEW_ACTION = "com.android.camera.action.REVIEW";
    public static final String ACTION_NEW_PICTURE = "android.hardware.action.NEW_PICTURE";
    public static final String ACTION_NEW_VIDEO = "android.hardware.action.NEW_VIDEO";
    private static Point mScreenSize = new Point();
    private static int mRotation = 90;

    private static DateFormat mJpegDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");

    public static int getDisplayRotation(Activity activity) {
        if (activity != null) {
            int rotation = activity.getWindowManager().getDefaultDisplay()
                    .getRotation();
            switch (rotation) {
                case Surface.ROTATION_0:
                    mRotation = 0;
                    break;
                case Surface.ROTATION_90:
                    mRotation = 90;
                    break;
                case Surface.ROTATION_180:
                    mRotation = 180;
                    break;
                case Surface.ROTATION_270:
                    mRotation = 270;
                    break;
            }
        }

        return mRotation;
    }

    public static int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation = false;
        if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            dist = Math.min(dist, 360 - dist);
            changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
        }
        if (changeOrientation) {
            return ((orientation + 45) / 90 * 90) % 360;
        }
        return orientationHistory;
    }

    public static Point getScreenSize(Activity activity) {
        if (activity != null) {
            WindowManager service =
                    (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            service.getDefaultDisplay().getSize(mScreenSize);
        }
        return mScreenSize;
    }

    public static Size getOptimalPreviewSize(Activity currentActivity,
            List<Size> sizes, double targetRatio) {
        final double ASPECT_TOLERANCE = 0.01;
        if (sizes == null) {
            return null;
        }

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        Point point = getScreenSize(currentActivity);
        int targetHeight = Math.min(point.x, point.y);
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            Log.w(TAG, "No preview size match the aspect ratio");
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public static void fadeIn(View view, float startAlpha, float endAlpha, long duration) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        view.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static void fadeIn(View view) {
        fadeIn(view, 0F, 1F, 400);

        view.setEnabled(true);
    }

    public static void fadeOut(View view) {
        if (view.getVisibility() != View.VISIBLE) return;

        view.setEnabled(false);
        Animation animation = new AlphaAnimation(1F, 0F);
        animation.setDuration(400);
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    public static float dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static boolean deviceNeedsStopPreviewToShoot() {
        String[] oldDevices = {"smdk4210", "aries"};

        boolean needs = Arrays.asList(oldDevices).contains(Build.BOARD);

        Log.e(TAG, "Device " + Build.BOARD + (needs ? " needs ": " doesn't need ") + "to stop preview");
        return needs;
    }

    public static boolean deviceNeedsDisableZSL() {
        String[] noZSL = {"apexqtmo", "expressatt", "aegis2vzw"};

        boolean needs = Arrays.asList(noZSL).contains(Build.PRODUCT);

        Log.e(TAG, "Device " + Build.PRODUCT + (needs ? " needs ": " doesn't need ") + "to disable ZSL");
        return needs;
    }

}
