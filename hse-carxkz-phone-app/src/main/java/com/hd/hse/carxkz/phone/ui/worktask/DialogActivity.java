package com.hd.hse.carxkz.phone.ui.worktask;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hd.hse.carxkz.phone.R;
import com.hd.hse.common.component.phone.dialog.UpCarXkzProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.business.readcard.ReadCardManyTimes;
import com.hd.hse.common.module.phone.ui.activity.ReadCardActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.CarXkz;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.sql.SQLException;

public class DialogActivity extends ReadCardActivity {

    private EditText sqrEdit;
    private EditText sdfzrEdit;
    private EditText engineerEdit;
    private Button commitButton;
    //实体类中数据
    private CarXkz carXkz;
    //数据库中数据
    private CarXkz data;
    private PersonCard psnCard = null;
    private UpCarXkzProgressDialog upzyp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = getData();
        if (upzyp == null) {
            upzyp = new UpCarXkzProgressDialog(this);
            upzyp.setGetDataSourceListener(eventLst);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        carXkz = (CarXkz) bundle.getSerializable("bundle");
        setContentView(R.layout.activity_dialog);

        sqrEdit = (EditText) findViewById(R.id.sp_sqr_edittext);
        sdfzrEdit = (EditText) findViewById(R.id.sp_sdfzr_textview);
        engineerEdit = (EditText) findViewById(R.id.sp_engineer_textview);
        if (data != null) {
            sqrEdit.setText((CharSequence) data.getAttribute("sqr"));
            sdfzrEdit.setText((CharSequence) data.getAttribute("sdmanager"));
            engineerEdit.setText((CharSequence) data.getAttribute("hseengineer"));
        }
        commitButton = (Button) findViewById(R.id.commit);
        commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carXkz.setAttribute("sqr", sqrEdit.getText().toString());
                carXkz.setAttribute("sdmanager", sdfzrEdit.getText().toString());
                carXkz.setAttribute("hseengineer", engineerEdit.getText().toString());

                if (TextUtils.isEmpty(sqrEdit.getText().toString())){
                    ToastUtils.toast(DialogActivity.this, "申请人不能为空!");
                    return;
                }
                if (TextUtils.isEmpty(sdfzrEdit.getText().toString())){
                    ToastUtils.toast(DialogActivity.this, "属地负责人不能为空!");
                    return;
                }

                if (TextUtils.isEmpty(engineerEdit.getText().toString())){
                    ToastUtils.toast(DialogActivity.this, "HSE工程师不能为空!");
                    return;
                }
                saveOrUpdate();

                if (!TextUtils.isEmpty(engineerEdit.getText().toString())) {
                    //上传
                    upzyp.execute("上传", "上传车辆许可证信息，请耐心等待.....", "");

                }

            }
        });


    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            // TODO Auto-generated method stub
            switch (eventType) {

                case IEventType.DOWN_WORK_LIST_LOAD:
                    // 成功上传
                    //删除数据库中数据
                    deleteSqlFCarXkz();
                    //删除文件夹中图片
                    deleteDir();
                    //关闭上一个页面
                    setResult(RESULT_OK);
                    //关闭当前页面
                    finish();

                    break;
                case IEventType.DOWN_WORK_LIST_SHOW:
                    // 上传失败
                    ToastUtils.toast(DialogActivity.this, objects.toString());
                    break;


                default:
                    break;
            }
        }
    };


    private void deleteDir() {
        File file = new File(TaskTabulationActivity.cameraline);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        files[i].delete();
                    }
                }
                file.delete();
            } else {
                file.delete();
            }
        }
    }

    private void deleteSqlFCarXkz() {
        BaseDao baseDao = new BaseDao();
        IConnectionSource conSrc = null;
        IConnection connection = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = conSrc.getNonTransConnection();

            String sql = "delete from ud_zyxk_car_xkz";
            try {
                baseDao.executeUpdate(connection, sql);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conSrc != null) {
                try {
                    conSrc.releaseConnection(connection);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
            }
        }


    }


    private void saveOrUpdate() {
        BaseDao baseDao = new BaseDao();
        IConnectionSource conSrc = null;
        IConnection connection = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            connection = conSrc.getNonTransConnection();


            if (data != null) {

                baseDao.updateEntity(connection, carXkz, new String[]{"ud_zyxk_car_xkzid", "deptunit", "deptunit_desc", "carunit", "driver", "carnumber", "jhr", "enterposition",
                        "enterreason", "entertime", "zxaqcscode", "zxaqcsdesc", "bcaqcs", "sqr", "sdmanager", "hseengineer", "routepicturepath"});
            } else {
                if (StringUtils.isEmpty(carXkz.getUd_zyxk_car_xkzid())) {
                    SequenceGenerator
                            .genPrimaryKeyValue(new SuperEntity[]{carXkz});
                    baseDao.insertEntity(connection, carXkz);
                }
            }
            ToastUtils.toast(DialogActivity.this, "保存成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DaoException d) {
            d.printStackTrace();
        } finally {
            if (conSrc != null) {
                try {
                    conSrc.releaseConnection(connection);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block

                }
            }
        }
    }

    private CarXkz getData() {
        BaseDao baseDao = new BaseDao();
        String sql = "select * from ud_zyxk_car_xkz";
        CarXkz workApprovalPersonRecords = null;
        try {

            workApprovalPersonRecords = (CarXkz) baseDao.
                    executeQuery(sql, new EntityResult(CarXkz.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        return workApprovalPersonRecords;
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionByCardID(cardnum);
    }

    @Override
    public void actionByCardID(String cardid) {
        if (cardnum != null && !"".equals(cardnum)) {
            if (carXkz.getDeptunit_desc() == null || carXkz.getZxaqcsdesc() == null || carXkz.getCarnumber() == null || carXkz.getDriver() == null ||
                    carXkz.getCarnumber() == null || carXkz.getJhr() == null || carXkz.getEnterposition() == null || carXkz.getEnterreason() == null ||
                    carXkz.getEntertime() == null || carXkz.getRoutepicturepath() == null) {
                ToastUtils.toast(this, "必填项不能为空!");
                return;
            }
            if (NFCReader.getIsPositived()) {
                cardnum = cardnum.substring(0, cardnum.length() - 0);
            } else {
                cardnum = cardnum.substring(0);
            }
            String cardNum = null;
            if (StringUtils.isEmpty(cardnum)) {
                if (!NFCReader.nfcReadCardEnable()) {
                    cardNum = ReadCardManyTimes.getReadCardNum();
                }
            } else {
                cardNum = cardnum;
            }

            BaseDao baseDao = new BaseDao();
            IConnectionSource conSrc = null;
            IConnection connection = null;

            try {
                conSrc = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
                connection = conSrc.getNonTransConnection();
                //查询环节点人员
                StringBuilder sbSql = new StringBuilder();

                sbSql.append("select * from ud_zyxk_ryk ");
                // 通过人员卡登录
                if (!StringUtils.isEmpty(cardNum)) {
                    sbSql.append(" where pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                } else {
                    throw new HDException("不存在该卡号的人员！");
                }
                psnCard = (PersonCard) baseDao.executeQuery(sbSql.toString(),
                        new EntityResult(PersonCard.class));

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DaoException e) {
                e.printStackTrace();
            } catch (HDException e) {
                e.printStackTrace();
            } finally {
                if (conSrc != null) {
                    try {
                        conSrc.releaseConnection(connection);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block

                    }
                }
            }

            if (psnCard != null) {
                if (sqrEdit.hasFocus()) {
                    sqrEdit.setText(psnCard.getPersonid_desc());
                } else if (sdfzrEdit.hasFocus()) {
                    sdfzrEdit.setText(psnCard.getPersonid_desc());
                } else if (engineerEdit.hasFocus()) {
                    engineerEdit.setText(psnCard.getPersonid_desc());
                }
            } else {
                ToastUtils.toast(this, "不存在该卡号的人员!");
            }


        }
    }

}
