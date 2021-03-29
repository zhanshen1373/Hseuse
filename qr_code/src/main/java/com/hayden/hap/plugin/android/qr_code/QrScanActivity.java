package com.hayden.hap.plugin.android.qr_code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 二维码扫描页面
 * Created by LiuYang on 2017/09/12
 */
public class QrScanActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //initToolBar();
        // 执行扫面Fragment的初始化操作
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        // 二维码扫描回调函数
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                bundle.putString(CodeUtils.RESULT_STRING, result);
                resultIntent.putExtras(bundle);
                QrScanActivity.this.setResult(RESULT_OK, resultIntent);
                QrScanActivity.this.finish();
            }

            @Override
            public void onAnalyzeFailed() {
                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                bundle.putString(CodeUtils.RESULT_STRING, "");
                resultIntent.putExtras(bundle);
                QrScanActivity.this.setResult(RESULT_OK, resultIntent);
                QrScanActivity.this.finish();
            }
        });
        // 替换扫描控件
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container,
                captureFragment).commit();
        // 设置闪光灯开关
        CheckBox light = (CheckBox) findViewById(R.id.lightCheckbox);
        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CodeUtils.isLightEnable(b);
            }
        });
    }

    /**
     * 初始化顶部工具栏
     *//*
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(QrScanActivity.this, R
                    .mipmap.back_icon));
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qr_scanner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        else if (item.getItemId() == R.id.gallery) {
            PhotoSelector.openRadioSelector(QrScanActivity.this, false, new
                    RadioSelectorCallback() {

                        @Override
                        public void onComplite(String selectedImgPath) {
                            MyCodeUtils.analyzeBitmap(selectedImgPath, new CodeUtils
                                    .AnalyzeCallback() {
                                @Override
                                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                    Intent resultIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                                    bundle.putString(CodeUtils.RESULT_STRING, result);
                                    resultIntent.putExtras(bundle);
                                    QrScanActivity.this.setResult(RESULT_OK, resultIntent);
                                    QrScanActivity.this.finish();
                                }

                                @Override
                                public void onAnalyzeFailed() {
                                    Intent resultIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
                                    bundle.putString(CodeUtils.RESULT_STRING, "");
                                    resultIntent.putExtras(bundle);
                                    QrScanActivity.this.setResult(RESULT_OK, resultIntent);
                                    QrScanActivity.this.finish();
                                }
                            });
                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }*/
}
