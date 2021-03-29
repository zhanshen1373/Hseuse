package com.hd.hse.pc.phone.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ImageLoader;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.custom.PinchImageView;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dc.business.common.weblistener.down.GainCertificateImgURLs;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.pc.phone.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by yangning on 2018/1/10 10:47.
 * 展示工作证图片
 */

public class CertificateDisplayActivity extends BaseFrameActivity
        implements IEventListener {
    private PinchImageView imageView;
    private PersonCard mCard;
    private String ud_cbsgl_rytzzyid;
    private String zcproject;
    /**
     * 图片网址
     */
    private String[] urls;
    private BusinessAction action;
    private BusinessAsyncTask asyncTask;
    private GainCertificateImgURLs mGainCertificateImgURLs;
    private ImageLoader mImageLoader;
    private String iPAndPort;
    public static final String IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_module_imageshoweractivity_layout);
        imageView = (PinchImageView) findViewById(R.id.hd_hse_common_module_imageshoweractivity_layout_img);
        imageView.setImageResource(R.drawable.hd_hse_common_component_phone_default_photo);
        initActionbar();
        initData();
    }

    private void initData() {

        mImageLoader = new ImageLoader();
        Intent intent = getIntent();
        String url = (String) intent.getSerializableExtra(CertificateDisplayActivity.IMAGE_URL);


        mImageLoader.setBg(url, imageView);

        /*
        mCard = (PersonCard) intent.getSerializableExtra(
                PersonCard.class.getName());
        ud_cbsgl_rytzzyid = intent.getStringExtra(PadInterfaceRequest.KEY_UDCBSGLRYTZZYID);
        zcproject = intent.getStringExtra(PadInterfaceRequest.KEY_ZCPROJECT);
        if (mCard == null) {
            ToastUtils.toast(getApplicationContext(), "mCard不能为空");
            return;
        }
        if (TextUtils.isEmpty(ud_cbsgl_rytzzyid)) {
            ToastUtils.toast(getApplicationContext(), "ud_cbsgl_rytzzyid不能为空");
            return;
        }
        if (TextUtils.isEmpty(zcproject)) {
            ToastUtils.toast(getApplicationContext(), "zcproject不能为空");
            return;
        }

        if (null == mGainCertificateImgURLs) {
            mGainCertificateImgURLs = new GainCertificateImgURLs(mCard.getIccard(), ud_cbsgl_rytzzyid, zcproject);
        }
        if (null == action) {
            action = new BusinessAction(mGainCertificateImgURLs);
        }
        if (asyncTask == null) {
            asyncTask = new BusinessAsyncTask(action,
                    new AbstractAsyncCallBack() {

                        @Override
                        public void start(Bundle msgData) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void processing(Bundle msgData) {

                        }

                        @Override
                        public void error(Bundle msgData) {
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            ToastUtils.imgToast(
                                    CertificateDisplayActivity.this,
                                    R.drawable.hd_hse_common_msg_wrong,
                                    msg.getMessage().contains("获取超时") ? msg
                                            .getMessage() : "获取图片网址错误！请检查附件是否上传");

                        }

                        @Override
                        public void end(Bundle msgData) {
                            // TODO Auto-generated method stub
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            Map<String, String[]> retStr = (Map<String, String[]>) msg.getReturnResult();
                            if (null != retStr) {
                                urls = retStr.get(PadInterfaceRequest.KEY_URL);
                                for (int i = 0; i < urls.length; i++) {
                                    String tmp[] = urls[i].split("/");
                                    urls[i] = "";
                                    for (int j = 0; j < tmp.length; j++) {
                                        if (isContainChinese(tmp[j])) {
                                            try {
                                                tmp[j] = URLEncoder.encode(tmp[j], "utf-8");
                                            } catch (UnsupportedEncodingException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (j == 0) {
                                            urls[i] += tmp[j];
                                        } else {
                                            urls[i] += ("/" + tmp[j]);
                                        }
                                        //urls[i] =iPAndPort+urls[i];

                                    }
                                }

                                //得到图片网址后，下载图片
                                mImageLoader.setBg(urls[0], imageView);
                            }

                        }
                    });
            try {

                asyncTask.execute(null, "");

            } catch (HDException e) {

            }
        }
        */
    }

    @Override
    public void eventProcess(int eventType, Object... objects) throws HDException {

    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        // 设置导航栏标题
        setActionBartitleContent("证件扫描件", false);
    }

    /**
     * 判断字符串是否为中文
     *
     * @param str
     * @return
     */
    public boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}
