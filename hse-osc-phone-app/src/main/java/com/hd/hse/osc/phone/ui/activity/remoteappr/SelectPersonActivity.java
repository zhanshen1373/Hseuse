package com.hd.hse.osc.phone.ui.activity.remoteappr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.RemoteAppr;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.adapter.remoteappr.SelectPersonAdapter;

import java.util.ArrayList;
import java.util.Map;

public class SelectPersonActivity extends BaseFrameActivity implements
        OnClickListener {
    private SelectPersonAdapter selectPersonAdapter;
    private ListView lv;
    private Button btCancle;
    private Button btEnture;
    private ArrayList<PersonCard> personCards;
    public static final int RESULTCODE = 0x41556;
    private ArrayList<RemoteAppr> remoteApprs = null;
    private int position = -1;
    private boolean isSingleSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_common_module_phone_select_person_layout);
        initView();
        initdata();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.hd_hse_common_module_phone_select_person_layout_lv);
        btCancle = (Button) findViewById(R.id.hd_hse_common_module_phone_select_person_layout_cancle);
        btEnture = (Button) findViewById(R.id.hd_hse_common_module_phone_select_person_layout_enture);
        btCancle.setOnClickListener(this);
        btEnture.setOnClickListener(this);
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1) {
            if (eventType == IEventType.ACTIONBAR_RETURN_CLICK) {
                finish();
            }
        }
    };

    private void initdata() {
        setCustomActionBar(false, eventLst, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE});
        setActionBartitleContent("人员选择", false);
        Intent intent = getIntent();
        remoteApprs = (ArrayList<RemoteAppr>) intent
                .getSerializableExtra(RemoteApprActivity.REMOTEAPPRS);
        position = intent.getIntExtra(RemoteApprActivity.POSITION, -1);
        if (remoteApprs == null || remoteApprs.size() <= 0 || position == -1) {
            ToastUtils.toast(getApplicationContext(), "传入数据有误");
            return;
        }
        personCards = qureyPersonCard(remoteApprs.get(position).getQxrole());
        if (personCards != null && personCards.size() > 0) {
            selectPersonAdapter = new SelectPersonAdapter(this, personCards);
        } else {
            ToastUtils.toast(this, "未搜索到相关远程审批人员");
            return;
        }
        RemoteAppr appr = remoteApprs.get(position);
        if (appr.getBpermulcard() != null && appr.getBpermulcard() == 0) {
            isSingleSelect = true;
        } else {
            isSingleSelect = false;
        }
        lv.setAdapter(selectPersonAdapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Boolean checked = selectPersonAdapter.getCheckBoxChecked(arg2);
                if (checked == null) {
                    ToastUtils.toast(SelectPersonActivity.this, "未知错误");
                } else {
                    selectPersonAdapter.setCheckBoxChecked(arg2, !checked);
                    if (isSingleSelect) {
                        selectPersonAdapter.setOtherItemFalse(arg2);
                    }
                    selectPersonAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private ArrayList<PersonCard> qureyPersonCard(String qxrole) {

        if (qxrole != null) {
            //处理qxrole为'a','b','c'格式
            String qxroles[] = qxrole.split(",");
            qxrole = "";
            for (String qx : qxroles) {
                if (qxrole.equals("")) {
                    qxrole = "'" + qx + "'";
                } else {
                    qxrole += ",'" + qx + "'";
                }
            }

            StringBuffer sql = new StringBuffer();
            sql.append(
                    "select * from ud_zyxk_ryk where personid in ( select resppartygroup from persongroupteam where persongroup in (")
                    .append(qxrole).append("))");
            BaseDao dao = new BaseDao();
            try {
                ArrayList<PersonCard> personCards = (ArrayList<PersonCard>) dao
                        .executeQuery(sql.toString(), new EntityListResult(
                                PersonCard.class));
                if (personCards != null) {
                    //过滤掉没有手机号的人员
                    for (int i = 0; i < personCards.size(); i++) {
                        if (personCards.get(i).getPrimaryphone() == null
                                || "".equals(personCards.get(i).getPrimaryphone())) {
                            personCards.remove(i);
                            i--;
                        }
                    }

                }
                return personCards;
            } catch (DaoException e) {
                e.printStackTrace();
                ToastUtils.toast(getApplicationContext(), e.getMessage());
                return null;
            }

        } else {
            ToastUtils.toast(getApplicationContext(), "未得到qxrole字段");
            return null;
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hd_hse_common_module_phone_select_person_layout_cancle) {
            // 取消
            finish();

        } else if (v.getId() == R.id.hd_hse_common_module_phone_select_person_layout_enture) {
            // 确定
            onClickEnture();
        }

    }

    private void onClickEnture() {
        Intent i = new Intent();
        if (selectPersonAdapter != null) {
            Map<Integer, Boolean> checkMap = selectPersonAdapter.getCheckMap();
            ArrayList<PersonCard> personCardList = new ArrayList<PersonCard>();
            for (int j = 0; j < personCards.size(); j++) {
                if (checkMap.containsKey(j)) {
                    if (checkMap.get(j)) {
                        personCardList.add(personCards.get(j));
                    }
                }
            }
            if (personCardList != null && personCardList.size() > 0) {
                RemoteAppr remoteAppr = remoteApprs.get(position);
                remoteAppr.setApprove_person_name("");
                remoteAppr.setApprove_personid("");
                for (PersonCard personCard : personCardList) {
                    if (remoteAppr.getApprove_person_name() == null
                            || remoteAppr.getApprove_person_name().equals("")) {
                        remoteAppr.setApprove_person_name(personCard
                                .getPersonid_desc());
                    } else {
                        remoteAppr.setApprove_person_name(remoteAppr
                                .getApprove_person_name()
                                + ","
                                + personCard.getPersonid_desc());
                    }
                    if (remoteAppr.getApprove_personid() == null
                            || remoteAppr.getApprove_personid().equals("")) {
                        remoteAppr
                                .setApprove_personid(personCard.getPersonid());
                    } else {
                        remoteAppr.setApprove_personid(remoteAppr
                                .getApprove_personid()
                                + ","
                                + personCard.getPersonid());
                    }
                }
                i.putExtra(RemoteApprActivity.REMOTEAPPRS, remoteApprs);
                setResult(RESULTCODE, i);
                finish();
            } else {
                ToastUtils.toast(getApplicationContext(), "你还未勾选远程审批人员");
            }

        } else {
            finish();
            ToastUtils.toast(SelectPersonActivity.this, "未搜索到相关远程审批人员");
        }

    }
}
