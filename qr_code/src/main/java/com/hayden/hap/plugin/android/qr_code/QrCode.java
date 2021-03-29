package com.hayden.hap.plugin.android.qr_code;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;


import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 二维码生成和扫描
 * Created by young4979 on 2017/9/11.
 */

public class QrCode {
    private static Context mContext;
    private static Activity mActivity;
    public static final int REQUEST_CODE = 100;

    /**
     * 打开二维码扫描页面
     * @param activity acitvity对象
     */
    public static void openQrScanner(Activity activity) {
        mActivity = activity;
        checkPermission();
    }

    /**
     * 生成二维码
     * @param textContent 二维码内容
     * @param w 二维码宽度
     * @param h 二维码高度
     * @param logoRes logo
     * @return 二维码的bitmap
     */
    public static Bitmap createQrCode(String textContent, int w, int h, int logoRes) {
        return CodeUtils.createImage(textContent, w, h, BitmapFactory.decodeResource(mContext.getResources(), logoRes));
    }

    /**
     * 生成二维码
     * @param textContent 二维码内容
     * @param w 二维码宽度
     * @param h 二维码高度
     * @param logo logo
     * @return 二维码的bitmap
     */
    public static Bitmap createQrCode(String textContent, int w, int h,  Bitmap logo) {
        return CodeUtils.createImage(textContent, w, h, logo);
    }

    /**
     * 生成二维码
     * @param textContent 二维码内容
     * @param w 二维码宽度
     * @param h 二维码高度
     * @return 二维码的bitmap
     */
    public static Bitmap createQrCode(String textContent, int w, int h) {
        return CodeUtils.createImage(textContent, w, h, null);
    }

    public static void init(Context context) {
        ZXingLibrary.initDisplayOpinion(context);  // 初始化二维码扫描控件
        mContext = context;
    }

    /**
     * 获取相机授权
     */
    private static void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isEnableCamera = ContextCompat.checkSelfPermission(mActivity,
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            if (!isEnableCamera) {
                ActivityCompat.requestPermissions(mActivity, new
                        String[]{
                        Manifest.permission.CAMERA}, 3);
            } else {
                startCameraActivity();
            }
        } else {
            startCameraActivity();
        }
    }

    /**
     * 授权回调函数
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 未授权
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                        .setMessage("如果禁止该权限，您将无法扫描二维码")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                checkPermission();
                                return;
                            }
                        });
                builder.show();
            }
        }
        startCameraActivity();
    }

    private static void startCameraActivity() {
        Intent intent = new Intent(mActivity, QrScanActivity.class);
        mActivity.startActivityForResult(intent, REQUEST_CODE);
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) return;
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    //mScanResultTv.setText(result);
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    //mScanResultTv.setText("扫描失败");
//                }
//            }
//        }
//    }
}
