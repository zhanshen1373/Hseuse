package com.hd.hse.pc.phone.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.ImageShowerActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dc.business.common.weblistener.down.DownCBSImage;
import com.hd.hse.dc.business.web.cbs.PCGetRulesRecordsCount;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.RyLine;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.PeopleInformation;
import com.hd.hse.pc.phone.adapter.PersonCheckAdapter;
import com.hd.hse.system.SystemProperty;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 人员检查界面
 * <p>
 * Created by liuyang on 2016年9月22日
 */
public class PersonCheckActivity extends BaseFrameActivity implements
        OnClickListener, IEventListener {
    /**
     * listview
     */
    private ListView lv;
    /**
     * 人员信息
     */
    private ArrayList<PeopleInformation> PeopleInformations;
    /**
     * 人员信息listview的adapter
     */
    private PersonCheckAdapter mPersonCheckAdapter;

    /**
     * 头像
     */
    private ImageView personImg;
    /**
     * 姓名
     */
    private TextView tvName;
    /**
     * 性别
     */
    private TextView tvSex;
    /**
     * 年龄
     */
    private TextView tvAge;
    /**
     * 所在公司名
     */
    private TextView tvCompany;
    /**
     * 省份证号
     */
    private TextView tvID;
    /**
     * 出入记录按钮
     */
    private RelativeLayout btnOutRecord;
    /**
     * 违章查询按钮
     */
    private RelativeLayout btnIllegalInquiry;
    /**
     * 违章次数
     */
    private TextView tvWzCount;
    /**
     * 工种按钮
     */
    private RelativeLayout btnProfession;
    /**
     * 监护人考试成绩
     */
    private RelativeLayout btnJhrScroe;
    /**
     * 是否为男性
     */
    private boolean isMan = false;
    /**
     * 是否存在头像缓存
     */
    private boolean existIconCache = false;

    private PersonCard mCard;

    private String headImagePath;// 人员头像存储路径
    /**
     * isshow:TODO(是否已经显示过头像).
     */
    private boolean isshow = false;

    BaseDao dao = null;
    private RyLine ryLine;
    private String cbsSex;
    private String cbsAge;
    private String cbsWorkType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hse_pc_phone_app_layout_person_check);
        mCard = (PersonCard) getIntent().getSerializableExtra(
                PersonCard.class.getName());
        dao = new BaseDao();


        initActionbar();
        initView();
        if (mCard.getIscbs() == 1) {
//            try {
//                ryLine = getRykData(mCard);
//            } catch (HDException e) {
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//                return;
//            }
//            if (ryLine != null) {
//                initData();
//            }
            initData();

        } else {
            //非承包商 不涉及到ryline表
            initfcbsData();
        }
//        initData();
    }

    private void initfcbsData() {


        /**
         * 人员信息是网上获取的，按照以下顺序，拼接成想要的数据，此为测试数据 <item>年龄</item> <item>入网时间</item>
         * <item>审查时间</item> <item>保险有效期</item> <item>劳动合同有效期</item>
         * <item>特殊资质有效期</item> <item>入厂有效期</item> <item>状态</item>
         * <item>工种</item> <item>累计违章次数</item>
         */
        PeopleInformations = new ArrayList<PeopleInformation>();
        //以下数据为测试数据，得到数据会需更改
        tvName.setText(mCard.getPersonid_desc());
        tvCompany.setText(mCard.getDepartment_desc());
        tvID.setText(mCard.getIccard());
        tvSex.setText(mCard.getSex());
        if (mCard.getAge() != null) {
            tvAge.setText(mCard.getAge() + "岁");
        }


        // 判断本地是否有头像缓存，若有显示出来，若没有显示默认头像
        //判断状态是否异常


        if ("已办卡".equals(mCard.getProcessstatus())) {
            //正常
        } else {
            //异常
        }

        getWzCount();

    }

    private RyLine getRykData(SuperEntity personCard) throws HDException {

        PersonCard psnCard = null;
        if (personCard instanceof PersonCard)
            psnCard = (PersonCard) personCard;


        StringBuilder sq = new StringBuilder();

//        sq.append("select * from ud_cbsgl_ryline ryl  where ryl.rowid in " +
//                "(select min(rowid) id from ud_cbsgl_ryline where  processstatus='已入厂' group by IDCARD) " +
//                "and (ryl.innercard='").append(psnCard.getAttribute("ryrcnum")).append("' or " +
//                "ryl.IDCARD='").append(psnCard.getPersonid()).append("') and ryl.name = '").append(psnCard.getPersonid_desc()).append("';");

        sq.append("select max(changedate),* from ud_cbsgl_ryline ryl where (ryl.innercard='" + psnCard.getAttribute("ryrcnum") + "' or ryl.IDCARD='" + psnCard.getPersonid() + "');");


        RyLine ryLine = null;
        try {
            ryLine = (RyLine) dao.executeQuery(sq.toString(),
                    new EntityResult(RyLine.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (ryLine != null) {
            return ryLine;
        } else {
            throw new HDException("承包商人员明细表中没有数据！");
        }
//        return null;

    }

    private void initActionbar() {
        // 初始化ActionBar
        setCustomActionBar(false, this, setActionBarItems());
        // 设置导航栏标题
        setActionBartitleContent(getTitileName(), false);
    }

    private String getTitileName() {
        return mCard.getPersonid_desc();
    }

    private String[] setActionBarItems() {
        return new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE};
    }

    /**
     * 初始化view
     */
    private void initView() {
        lv = (ListView) findViewById(R.id.hse_pc_phone_app_layout_person_check_lv);
        personImg = (ImageView) findViewById(R.id.hse_pc_phone_app_layout_person_check_img_icon);
        tvName = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_tv_name);
        tvSex = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_tv_sex);
        tvAge = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_tv_age);
        tvCompany = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_tv_company_name);
        tvID = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_tv_id);
        btnOutRecord = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_layout_person_check_rl_out_record);
        btnIllegalInquiry = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_layout_person_check_rl_illegal_inquiry);
        tvWzCount = (TextView) findViewById(R.id.hse_pc_phone_app_layout_person_check_rv_wz_count);
        btnProfession = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_layout_person_check_rl_profession);
        btnJhrScroe = (RelativeLayout) findViewById(R.id.hse_pc_phone_app_layout_person_check_rl_jhrScore);
        btnOutRecord.setOnClickListener(this);
        btnIllegalInquiry.setOnClickListener(this);
        btnProfession.setOnClickListener(this);
        personImg.setOnClickListener(this);
        btnJhrScroe.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /**
         * 人员信息是网上获取的，按照以下顺序，拼接成想要的数据，此为测试数据 <item>年龄</item> <item>入网时间</item>
         * <item>审查时间</item> <item>保险有效期</item> <item>劳动合同有效期</item>
         * <item>特殊资质有效期</item> <item>入厂有效期</item> <item>状态</item>
         * <item>工种</item> <item>累计违章次数</item>
         */
        //PeopleInformation peopleInformation = new PeopleInformation("年龄", mCard.getAge());
        //PeopleInformations.add(peopleInformation);
        /*PeopleInformation peopleInformation2 = new PeopleInformation("入网时间",
                mCard.getSwtime());
		PeopleInformations.add(peopleInformation2);
		PeopleInformation peopleInformation3 = new PeopleInformation("审查时间",
				mCard.getScdeadline());
		PeopleInformations.add(peopleInformation3);
		PeopleInformation peopleInformation4 = new PeopleInformation("保险有效期",
				mCard.getBxdeadline());
		PeopleInformations.add(peopleInformation4);
		PeopleInformation peopleInformation5 = new PeopleInformation("劳动合同有效期",
				mCard.getHtqdtime());
		PeopleInformations.add(peopleInformation5);
		PeopleInformation peopleInformation6 = new PeopleInformation("特殊资质有效期",
				mCard.getZzdeadline());
		PeopleInformations.add(peopleInformation6);
		PeopleInformation peopleInformation7 = new PeopleInformation("入厂有效期",
				mCard.getYxtime());
		PeopleInformations.add(peopleInformation7);
		PeopleInformation peopleInformation8 = new PeopleInformation("状态",
				mCard.getProcessstatus());
		PeopleInformations.add(peopleInformation8);
		PeopleInformation peopleInformation9 = new PeopleInformation("允许进入厂区", mCard.getJrdept_desc());
		PeopleInformations.add(peopleInformation9);
		PeopleInformation peopleInformation10 = new PeopleInformation("允许进入门岗",
				mCard.getJrdoor());
		PeopleInformations.add(peopleInformation10);*/

        //吉林2018/1/8




        /*PeopleInformation peopleInformation3 = new PeopleInformation("入场开始时间", mCard.getAge());
        PeopleInformations.add(peopleInformation3);
        PeopleInformation peopleInformation4 = new PeopleInformation("入场结束时间", mCard.getAge());
        PeopleInformations.add(peopleInformation4);*/



        /*PeopleInformation peopleInformation6 = new PeopleInformation("状态", mCard.getProcessstatus());
        PeopleInformations.add(peopleInformation6);*/


        // 以下数据为测试数据，得到数据会需更改
        tvName.setText(mCard.getPersonid_desc());
        tvCompany.setText(mCard.getDepartment_desc());
//        tvID.setText(ryLine.getIdcard());
        tvID.setText(mCard.getPersonid());
//  优化 tvSex.setText(mCard.getSex());
        tvSex.setText(mCard.getSex());
//  优化      if (mCard.getAge() != null) {
//            tvAge.setText(mCard.getAge() + "岁");
//        }

        if (mCard.getAge() != null) {
            tvAge.setText(mCard.getAge() + "岁");
        }

        // 判断本地是否有头像缓存，若有显示出来，若没有显示默认头像
        //判断状态是否异常


        if ("已办卡".equals(mCard.getProcessstatus())) {
            //正常
        } else {
            //异常
        }

        /*
        if ("已办卡".equals(ryLine.getProcessstatus())) {
            //正常
        } else {
            //异常
        }
        */
        getWzCount();
    }

    private void getWzCount() {
        PCGetRulesRecordsCount wzCount = new PCGetRulesRecordsCount();

        BusinessAction actionRules = new BusinessAction(wzCount);

        BusinessAsyncTask asyncTask = new BusinessAsyncTask(actionRules,
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
                                PersonCheckActivity.this,
                                R.drawable.hd_hse_common_msg_wrong,
                                msg.getMessage().contains("获取超时") ? msg
                                        .getMessage() : "获取违章次数失败！请联系管理员");

                    }

                    @Override
                    public void end(Bundle msgData) {
                        // TODO Auto-generated method stub

                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
//                        Integer count = (Integer) msg
//                                .getReturnResult();
                        HashMap result = (HashMap) msg.getReturnResult();
                        cbsSex = (String) result.get("sex");
                        cbsAge = (String) result.get("age");
                        String count = (String) result.get("count");
                        cbsWorkType = (String) result.get("worktype");
                        tvWzCount.setText(count + "");

                        PeopleInformations = new ArrayList<PeopleInformation>();

                        PeopleInformation peopleInformation = new PeopleInformation("性别", cbsSex);
                        PeopleInformations.add(peopleInformation);


                        PeopleInformation peopleInformation2 = new PeopleInformation("年龄", cbsAge);
                        PeopleInformations.add(peopleInformation2);

                        PeopleInformation peopleInformation5 = new PeopleInformation("工种", cbsWorkType);
                        PeopleInformations.add(peopleInformation5);

                        mPersonCheckAdapter = new PersonCheckAdapter(PeopleInformations, PersonCheckActivity.this);
                        lv.setAdapter(mPersonCheckAdapter);


                    }
                });
        try {
            if (mCard.getIscbs() == 1) {

                if (!TextUtils.isEmpty(mCard.getPersonid()))
                    asyncTask.execute(null, mCard.getPersonid());
            } else {
                //非承包商 不涉及到ryline表
                if (!TextUtils.isEmpty(mCard.getIccard()))
                    asyncTask.execute(null, mCard.getIccard());


            }


        } catch (HDException e) {
            ToastUtils.imgToast(PersonCheckActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "获取违章次数失败");

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ShowPersonImage();
    }

    @Override
    public void onClick(View arg0) {
        int id = arg0.getId();
        if (id == R.id.hse_pc_phone_app_layout_person_check_img_icon) {
            // 点击头像,判断本地是否有头像缓存，若有显示大图，若没有网络请求下载头像，完成后大图显示出来，并设置imgIcon
            onHeadImageClick();
            //在线
        } else if (id == R.id.hse_pc_phone_app_layout_person_check_rl_out_record) {
            // 点击出入记录按钮
            Intent intent = new Intent(this, TrainingRecordsActivity.class);
            intent.putExtra(PersonCard.class.getName(), mCard);
//            intent.putExtra(RyLine.class.getName(), ryLine);
            startActivity(intent);
            //在线
        } else if (id == R.id.hse_pc_phone_app_layout_person_check_rl_illegal_inquiry) {
            // 点击违章查询按钮

            Intent intent = new Intent(this, PersonRulesRecordActivity.class);
            intent.putExtra(PersonCard.class.getName(), mCard);
//            intent.putExtra(RyLine.class.getName(), ryLine);
            startActivity(intent);
            //在线
        } else if (id == R.id.hse_pc_phone_app_layout_person_check_rl_profession) {
            // 点击工种按钮
            Intent intent = new Intent(this,
                    PersonCertificateRecordActivity2.class);
            intent.putExtra(PersonCard.class.getName(), mCard);
            startActivity(intent);

        } else if (id == R.id.hse_pc_phone_app_layout_person_check_rl_jhrScore){
            // 点击监护人成绩按钮
            Intent intent = new Intent(this,
                    JhrExamRecordActivity.class);
            intent.putExtra(PersonCard.class.getName(), mCard);
            startActivity(intent);
        }

    }

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        // TODO Auto-generated method stub

    }

    /**
     * ShowPersonImage:(显示人员头像照片). <br/>
     * date: 2014年9月15日 <br/>
     *
     * @author lxf
     */
    private void ShowPersonImage() {
        if (isExist() && !isshow) {
            isshow = true;
            Bitmap bitmap = getImageBitmap(headImagePath);
            if (null != bitmap) {
                // bitmap=toRoundCorner(bitmap, 30);
                // bitmap=toRoundCorner(bitmap,120);
                if (personImg != null) {
                    personImg.setImageBitmap(bitmap);
                }

            }
        }
    }

    /**
     * isExist:(判断文件是否存在). <br/>
     * date: 2014年9月13日 <br/>
     *
     * @return
     * @author lxf
     */
    private boolean isExist() {
        headImagePath = SystemProperty.getSystemProperty().getRootImagePath()
                + File.separator + "personphoto" + File.separator
                + mCard.getPersonid() + ".jpg";
        File file = new File(headImagePath);
        if (file.exists()) {
            return true;
        }/*else{
            // 特殊处理，吉林石化承包商人员按照分厂管理，personid=分厂编码+身份证号
			headImagePath = SystemProperty.getSystemProperty()
					.getRootImagePath()
					+ File.separator
					+ "personphoto"
					+ File.separator
					+ mCard.getPersonid().substring(
							mCard.getPersonid().length() - 18) + ".jpg";
			String a;
			file = new File(headImagePath);
			if (file.exists()) {
				return true;
			}else{
				headImagePath = SystemProperty.getSystemProperty()
						.getRootImagePath()
						+ File.separator
						+ "personphoto"
						+ File.separator
						+ mCard.getPersonid().substring(
								mCard.getPersonid().length() - 15) + ".jpg";
				file = new File(headImagePath);
				if (file.exists()) {
					return true;
				}
			}
		}*/
        return false;
    }

    /**
     * 根据路径获取图片 流对象
     *
     * @param path
     * @return
     */
    private Bitmap getImageBitmap(String path) {

        FileInputStream f;
        try {
            f = new FileInputStream(path);
            Bitmap bm = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;// 图片的长宽都是原来的1/2
            BufferedInputStream bis = new BufferedInputStream(f);
            bm = BitmapFactory.decodeStream(bis, null, options);
            return bm;
        } catch (FileNotFoundException e) {
            ToastUtils.toast(PersonCheckActivity.this, "人员照片读取失败！");
        }
        return null;

    }

    private ProgressDialog prsDlg = null;

    public void onHeadImageClick() {

        if (isFastDoubleClick()) {
            return;
        }
        // 1.先判断本地是否存在
        if (isExist()) {
            // 存在加载本地文件
            ShowHeadImage();
        } else {
            prsDlg = new ProgressDialog(this, "下载服务器照片");
            prsDlg.setCancelable(true);
            prsDlg.setCanceledOnTouchOutside(false);
            prsDlg.show();
            // 本地不存在加载服务图片
            DownCBSImage basicinit = new DownCBSImage();
            BusinessAction action = new BusinessAction(basicinit);

            runProThreadVersion(action, mCard.getPersonid(), "");
        }

    }

    private void runProThreadVersion(BusinessAction action, Object listEntity,
                                     final String actionType) {
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {
                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                    }

                    @Override
                    public void error(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        if (msgData.containsKey(IActionType.WEB_INOROUTRECORD)) {

                        } else {
                            prsDlg.dismiss();
                            ToastUtils.toast(PersonCheckActivity.this,
                                    msg.getMessage());
                        }
                    }

                    @Override
                    public void end(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        if (msgData.containsKey(IActionType.WEB_INOROUTRECORD)) {

                        } else {
                            prsDlg.dismiss();
                            ShowHeadImage();
                        }

                    }
                });
        try {
            task.execute("", listEntity);
        } catch (HDException e) {
            ToastUtils.imgToast(PersonCheckActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "图片下载失败！");
        }
    }

    /**
     * ShowHeadImage:(弹出窗体显示头像). <br/>
     * date: 2014年9月13日 <br/>
     *
     * @author lxf
     */
    private void ShowHeadImage() {
        if (!isExist()) {
            return;
        }
        Intent intent = new Intent(this, ImageShowerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ImageShowerActivity.HEADIMG, headImagePath);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
