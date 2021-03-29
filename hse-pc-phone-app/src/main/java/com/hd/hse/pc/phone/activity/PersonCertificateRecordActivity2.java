package com.hd.hse.pc.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dc.business.common.weblistener.down.GainCertificateImgURLs;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.pc.CheckPersonRecords;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.PersonCertificateRecordAdapter2;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 人员证书记录Activity Created by liuyang on 2016年9月26日
 */
public class PersonCertificateRecordActivity2 extends BaseFrameActivity
        implements IEventListener {

    private Logger logger = LogUtils
            .getLogger(PersonCertificateRecordActivity2.class);
    private List<PersonSpecialTypeWork> mSpecialTypeWorks; // 证书列表
    private PersonCard mCard;
    private ListView lv;
    private PersonCertificateRecordAdapter2 mPersonCertificateRecordAdapter;
    private GainCertificateImgURLs mGainCertificateImgURLs;
    private BusinessAction action;
    private BusinessAsyncTask asyncTask;
    private String[] urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_pc_phone_app_layout_certificate2);
        initActionbar();
        initView();
        initData();

    }

    private void initView() {
        lv = (ListView) findViewById(R.id.hse_pc_phone_app_layout_certificate_lv2);

    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        // 设置导航栏标题
        setActionBartitleContent("证书记录", false);
    }

    private void initData() {
        mCard = (PersonCard) getIntent().getSerializableExtra(
                PersonCard.class.getName());

        try {
            mSpecialTypeWorks = CheckPersonRecords.getInstance()
                    .checkPersonCertificateRecords(mCard.getPersonid());
            mPersonCertificateRecordAdapter = new PersonCertificateRecordAdapter2(
                    this, mSpecialTypeWorks);
            lv.setAdapter(mPersonCertificateRecordAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                    Intent intent = new Intent(PersonCertificateRecordActivity2.this, CertificateDisplayActivity.class);
//                    intent.putExtra(PersonCard.class.getName(), mCard);
//                    intent.putExtra(PadInterfaceRequest.KEY_UDCBSGLRYTZZYID, mSpecialTypeWorks.get(i).getUd_cbsgl_rytzzyid() + "");
//                    intent.putExtra(KEY_ZCPROJECT, mSpecialTypeWorks.get(i).getZcproject());
//                    startActivity(intent);

                    getPictureData(mCard, mSpecialTypeWorks.get(i).getUd_cbsgl_rytzzyid() + "", mSpecialTypeWorks.get(i).getZcproject());
                }
            });
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.imgToast(PersonCertificateRecordActivity2.this,
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }
        /*mSpecialTypeWorks = new ArrayList<>();
        PersonSpecialTypeWork specialTypeWork = new PersonSpecialTypeWork();
        specialTypeWork.setZsname("低压电器安装作业");
        specialTypeWork.setZsnum("129655666565852");
        specialTypeWork.setFzdate("2016年12月1日");
        specialTypeWork.setDqdate("2018年12月12日");
        mSpecialTypeWorks.add(specialTypeWork);*/
       /* mPersonCertificateRecordAdapter = new PersonCertificateRecordAdapter2(
                this, mSpecialTypeWorks);
        lv.setAdapter(mPersonCertificateRecordAdapter);*/

    }

    private void getPictureData(PersonCard mcard, String ud_cbsgl_rytzzyid, String zcproject) {


        if (mcard == null) {
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


        mGainCertificateImgURLs = new GainCertificateImgURLs(mCard.getIccard(), ud_cbsgl_rytzzyid, zcproject);


        action = new BusinessAction(mGainCertificateImgURLs);


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
                                PersonCertificateRecordActivity2.this,
                                R.drawable.hd_hse_common_msg_wrong,
                                msg.getMessage().contains("获取超时") ? msg
                                        .getMessage() : "没有证件扫描件");

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


                            Intent intent = new Intent(PersonCertificateRecordActivity2.this, CertificateDisplayActivity.class);
                            intent.putExtra(CertificateDisplayActivity.IMAGE_URL, urls[0]);

                            startActivity(intent);
                            //得到图片网址后，下载图片
//                                mImageLoader.setBg(urls[0], imageView);
                        }

                    }
                });
        try {

            asyncTask.execute(null, "");

        } catch (HDException e) {

        }

    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        // TODO Auto-generated method stub
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
