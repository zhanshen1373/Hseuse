package com.hd.hse.pc.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hayden.hap.plugin.android.qr_code.QrCode;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.homepage.PersonCheckApp;
import com.hd.hse.system.SystemProperty;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * created by yangning on 2019/3/12 14:37.
 */
public class PersonCheckMode extends BaseFrameActivity implements IEventListener {
    private TextView tvSk;
    private TextView tvQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personcheck_mode_layout);

        // 初始化ActionBar
        setCustomActionBar(true, this, setActionBarItems());
        setNavContent(getNavContentData(), "hse-pc-phone-app");// 设置导航栏标题
        setActionBartitleContent(getTitileName(), false);
        QrCode.init(getApplicationContext());
        initView();
    }

    private void initView() {
        tvSk = (TextView) findViewById(R.id.tvSk);
        tvQR = (TextView) findViewById(R.id.tvQR);
        tvSk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到刷卡页面
                Intent intent = new Intent(PersonCheckMode.this, ReadPersonCardActivity.class);
                intent.putExtra(PersonCheckApp.QYQR, true);
                startActivity(intent);
            }
        });
        tvQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到扫描二维码
                QrCode.openQrScanner(PersonCheckMode.this);
            }
        });
    }

    public List<AppModule> getNavContentData() {
        return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
    }

    private String getTitileName() {
        return "人员检查";
    }

    public String[] setActionBarItems() {
        return new String[]{IActionBar.IBTN_RETURN, IActionBar.TV_TITLE};
    }

    @Override
    public void eventProcess(int eventType, Object... objects) throws HDException {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrCode.REQUEST_CODE && data != null) {
            //处理二维码扫描信息

            int type = data.getIntExtra(CodeUtils.RESULT_TYPE, -1);
            if (type == CodeUtils.RESULT_SUCCESS) {
                String result = data.getStringExtra(CodeUtils.RESULT_STRING);
                try {
                    PersonCard personCard = getPersonCard(result);
                    Intent intent = new Intent(
                            PersonCheckMode.this,
                            PersonCheckActivity.class);
                    intent.putExtra(PersonCard.class.getName(),
                            personCard);
                    startActivity(intent);
                } catch (HDException e) {
                    e.printStackTrace();
                    ToastUtils.imgToast(PersonCheckMode.this,
                            R.drawable.hd_hse_common_msg_wrong,
                            e.getMessage());
                }
            } else {
                ToastUtils.toast(PersonCheckMode.this, "扫码失败，请重试...");
            }
        }
    }

    private PersonCard getPersonCard(String personid) throws HDException {
        StringBuilder sbSql = new StringBuilder();
        sbSql.setLength(0);
        // 获取人员信息
        PersonCard psnCard;
        try {

            sbSql.append("select * from ud_zyxk_ryk ");
            // 通过人员卡登录
            if (!StringUtils.isEmpty(personid)) {
                sbSql.append(" where personid='").append(personid).append("' COLLATE NOCASE");
            } else {
                throw new HDException("不存在该personid的人员！");
            }
            BaseDao dao = new BaseDao();
            psnCard = (PersonCard) dao.executeQuery(sbSql.toString(),
                    new EntityResult(PersonCard.class));
            if (psnCard == null)
                throw new HDException("不存在该personid的人员！");
            /*if (psnCard.getIscan() == 0)
                throw new HDException("该人员卡已失效，请联系相关负责人！");*/
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            throw new DaoException("查询人员卡表失败！");
        }
        return psnCard;
    }
}
