package com.hd.hse.carxkz.phone.ui.worktask;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.hd.hse.carxkz.phone.R;
import com.hd.hse.carxkz.phone.ui.adapter.MyAdapter;
import com.hd.hse.carxkz.phone.ui.zdylistview.ZdyListview;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.HseActionBarBranchMenu;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Department;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.CarXkz;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class TaskTabulationActivity extends BaseListBusActivity implements View.OnClickListener {


    private ZdyListview listView;
    private MyAdapter myAdapter;
    private String[] xkz_des;
    private ImageView cameraImageView;
    private TextView cameraTextView;
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_SP = 2;

    private HseActionBarBranchMenu hseBBM;
    private LinearLayout mainLinearlayout;
    private Bitmap bitmap;
    private Button saveButton;
    private Button spButton;
    private CarXkz carXkz;
    private String[] xkz_key;
    private HashMap<String, String> hashMap = new HashMap<>();
    public static String cameraline = SystemProperty.getSystemProperty().getRootDBpath()
            + File.separator + "carxkz";
    private Spinner sdSpinner;
    private Spinner zxaqcsSpinner;
    private IQueryWorkInfo queryWorkInfo;
    private List<Department> depts;
    private List<String> dept_desc;
    private BaseDao dao;
    private List<String> aqcs_desc;
    private List<Domain> aqcs;
    private int sdSpinnerSelectN;
    private int zxaqcsSpinnerSelectN;
    private EditText carxkz_bcaqcsEd;
    private TextView carxkz_entertime;
    private Calendar ca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_tabulation);
        initSelfView();
        initData();
        ca = Calendar.getInstance();
        carXkz = getData();

        aqcs_desc = new ArrayList<>();
        for (int w = 0; w < aqcs.size(); w++) {
            aqcs_desc.add(aqcs.get(w).getDescription());
        }

        dept_desc = new ArrayList<String>();
        for (int i = 0; i < depts.size(); i++) {
            dept_desc.add(depts.get(i).getDescription());
        }

        if (carXkz == null) {
            carXkz = new CarXkz();
        } else {


            for (int i = 0; i < dept_desc.size(); i++) {
                if (dept_desc.get(i).equals(carXkz.getAttribute("deptunit_desc"))) {
                    sdSpinnerSelectN = i;
                }
            }

            for (int k = 0; k < aqcs_desc.size(); k++) {
                if (aqcs_desc.get(k).equals(carXkz.getAttribute("zxaqcsdesc"))) {
                    zxaqcsSpinnerSelectN = k;
                }
            }

            if (carXkz.getAttribute("bcaqcs") != null) {
                carxkz_bcaqcsEd.setText(carXkz.getAttribute("bcaqcs").toString());
            }
            if (carXkz.getAttribute("entertime") != null) {
                carxkz_entertime.setText(carXkz.getAttribute("entertime").toString());
            }

        }
        myAdapter = new MyAdapter(this, xkz_des, carXkz, hashMap);
        listView.setAdapter(myAdapter);


        cameraImageView.post(new Runnable() {
            @Override
            public void run() {
                if (carXkz.getAttribute("routepicturepath") != null) {
                    String partpath = (String) carXkz.getAttribute("routepicturepath");
                    String path = TaskTabulationActivity.cameraline + File.separator + partpath;
                    showImageView(path);
                }
            }
        });


        setCustomActionBar(true, new IEventListener() {
            @Override
            public void eventProcess(int eventType, Object... objects) throws HDException {
                if (eventType == IEventType.ACTIONBAR_MORE_CLICK) {
                    if (hseBBM != null)
                        hseBBM.showAsDropDown((View) objects[0]);
                }

            }
        }, new String[]{IActionBar.IV_LMORE, IActionBar.TV_TITLE, IActionBar.TV_MORE});
        setActionBartitleContent(getTitileName(), false);


        hseBBM = new HseActionBarBranchMenu(getApplicationContext(), new IEventListener() {
            @Override
            public void eventProcess(int eventType, Object... objects) throws HDException {
                if (eventType == IEventType.ACTIONBAR_PHOTOGRAPH_CLICK) {

                    Image image = new Image();
                    image.setImagepath(TaskTabulationActivity.cameraline);// 文件夹路径
                    image.setImagename("rpicture");
                    Intent intent = new Intent(TaskTabulationActivity.this,
                            CameraCaptureActivity.class);
                    intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);

                    startActivityForResult(intent, TaskTabulationActivity.REQUEST_IMAGE);
                }
            }
        },
                new String[]{IActionBar.ITEM_PHOTOGTAPH});

        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    PhotoView photoView = (PhotoView) LayoutInflater.from(TaskTabulationActivity.this).inflate(R.layout.photoview, null);

                    photoView.setImageBitmap(bitmap);
                    PopupWindow popupWindow = new PopupWindow(photoView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0xaaaaaaaa));
                    popupWindow.showAtLocation(mainLinearlayout, Gravity.CENTER, 0, 0);
                    bgAlpha(0.5f);
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            bgAlpha(1.0f);
                        }
                    });
                }

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dept_desc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        sdSpinner.setAdapter(adapter);
        sdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {


                carXkz.setAttribute("deptunit_desc", dept_desc.get(pos));
                carXkz.setAttribute("deptunit", depts.get(pos).getDeptnum());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        sdSpinner.setSelection(sdSpinnerSelectN, true);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aqcs_desc);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        zxaqcsSpinner.setAdapter(adapter1);
        zxaqcsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {


                carXkz.setAttribute("zxaqcscode", aqcs.get(pos).getValue());
                carXkz.setAttribute("zxaqcsdesc", aqcs_desc.get(pos));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        zxaqcsSpinner.setSelection(zxaqcsSpinnerSelectN, true);


    }


    private void bgAlpha(float alpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = alpha;// 0.0-1.0
        this.getWindow().setAttributes(lp);
    }


    private void initSelfView() {

        mainLinearlayout = (LinearLayout) findViewById(R.id.task_tabulation);
        listView = (ZdyListview) findViewById(R.id.carxkz_listview);
        cameraImageView = (ImageView) findViewById(R.id.carxkz_camera_imageview);
        cameraTextView = (TextView) findViewById(R.id.carxkz_camera_textview);
        carxkz_bcaqcsEd = (EditText) findViewById(R.id.carxkz_bcaqcs);
        saveButton = (Button) findViewById(R.id.save);
        spButton = (Button) findViewById(R.id.sp);
        sdSpinner = (Spinner) findViewById(R.id.spinner_1);
        zxaqcsSpinner = (Spinner) findViewById(R.id.spinner_2);
        carxkz_entertime = (TextView) findViewById(R.id.carxkz_entertime);
        saveButton.setOnClickListener(this);
        spButton.setOnClickListener(this);
        carxkz_entertime.setOnClickListener(this);


        listView.setFocusable(false);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE) {
                try {
                    String partpath = data.getStringExtra("path");
                    String allpath = TaskTabulationActivity.cameraline + File.separator + partpath;
                    showImageView(allpath);
                    carXkz.setAttribute("routepicturepath", partpath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_SP) {
                finish();
            }
        }
    }

    private void showImageView(String path) {
        if (path != null) {
            FileInputStream fis = null; //根据uri的path获得原图的字节流
            try {
                fis = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //生成原图
            bitmap = BitmapFactory.decodeStream(fis);


            Bitmap bitmap1 = fitImage(cameraImageView, bitmap);
            cameraTextView.setVisibility(View.GONE);
            cameraImageView.setImageBitmap(bitmap1);
        }

    }

    private boolean getSysRelationConfig() {
        String sql = "select * from sys_relation_info where sys_type = '"
                + IRelativeEncoding.AQJDDEPTGL + "'";
        dao = new BaseDao();
        try {
            RelationTableName mRelationTableName = (RelationTableName) dao
                    .executeQuery(sql,
                            new EntityResult(RelationTableName.class));
            if (mRelationTableName != null && mRelationTableName.getIsqy() == 1) {
                // 启用了过滤
                return true;
            } else if (mRelationTableName != null
                    && mRelationTableName.getIsqy() == 0) {
                // 没有启用过滤
                return false;
            }
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private Bitmap fitImage(ImageView imageView, Bitmap bitmap) {
        // 图片根据控件大小等比例缩放拉伸
        float widthScale = imageView.getWidth() * 1.0f / bitmap.getWidth();
        float heightScale = imageView.getHeight() * 1.0f / bitmap.getHeight();
// 设置长宽拉伸缩放比
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
// 拉伸缩放图片
        Bitmap newBt = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return newBt;
    }

    /**
     * 过滤掉承包商和组织机构
     *
     * @return
     */
    private List<Department> filterDept(List<Department> depts) {
        for (int i = 0; i < depts.size(); i++) {

            String deptNum = depts.get(i).getDeptnum();
            if (deptNum != null
                    && (deptNum.equals("A11") || deptNum.equals("ZZJG"))) {
                depts.remove(i);
                i--;
            }
        }
        return depts;
    }

    public List<Domain> getAQCS() {


        String sql = "select value,description from alndomain where domainid='CARXKZ_AQCS'";
        List<Domain> domainList = null;
        try {
            domainList = (List<Domain>) dao.
                    executeQuery(sql, new EntityListResult(Domain.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }


        return domainList;
    }

    @Override
    protected void initData() {
        Resources res = getResources();
        xkz_des = res.getStringArray(R.array.carxkz_description);
        xkz_key = res.getStringArray(R.array.carxkz_key);
        for (int i = 0; i < xkz_des.length; i++) {
            hashMap.put(xkz_des[i], xkz_key[i]);
        }

        queryWorkInfo = new QueryWorkInfo();
        boolean qy = getSysRelationConfig();
        try {
            depts = queryWorkInfo.queryDept(false, null);
            if (qy) {
                // 过滤掉承包商
                for (int i = 0; i < depts.size(); i++) {

                    Department dept = depts.get(i);
                    String parent = dept.getParent();
                    int isfilter = dept.getIsfilter();
                    // 等于1是启用过滤
                    if (isfilter == 1
                            || (null != parent && "A11".equals(parent))) {
                        depts.remove(dept);
                        i--;
                    }

                }
                depts = filterDept(depts);


            } else {
                // 过滤掉承包商
                for (int i = 0; i < depts.size(); i++) {
                    Department dept = depts.get(i);
                    String parent = dept.getParent();

                    if (null != parent && "A11".equals(parent)) {
                        depts.remove(dept);
                        i--;
                    }

                }
                depts = filterDept(depts);


            }

        } catch (HDException e) {
            ToastUtils.imgToast(TaskTabulationActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }
        //加入当前登陆人的属地单位
        PersonCard loginPerson = SystemProperty.getSystemProperty().getLoginPerson();
        String department = loginPerson.getDepartment();
        String department_desc = loginPerson.getDepartment_desc();
        Department d = new Department();
        d.setDeptnum(department);
        d.setDescription(department_desc);
        depts.add(0, d);

        aqcs = getAQCS();

        //查询属地单位，执行安全措施
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "车辆许可证申请";
    }


    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-carxkz-phone-app";
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
    public void onClick(View v) {
        if (v.getId() == R.id.save) {

            carXkz.setAttribute("bcaqcs", carxkz_bcaqcsEd.getText().toString());
            BaseDao baseDao = new BaseDao();
            IConnectionSource conSrc = null;
            IConnection connection = null;

            try {
                conSrc = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
                connection = conSrc.getNonTransConnection();


                CarXkz data = getData();
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
                ToastUtils.toast(TaskTabulationActivity.this, "保存成功");
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
        } else if (v.getId() == R.id.sp) {
            carXkz.setAttribute("bcaqcs", carxkz_bcaqcsEd.getText().toString());
            Intent intent = new Intent(TaskTabulationActivity.this, DialogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("bundle", carXkz);
            intent.putExtra("intent", bundle);
            TaskTabulationActivity.this.startActivityForResult(intent, TaskTabulationActivity.REQUEST_SP);

        } else if (v.getId() == R.id.carxkz_entertime) {
            final DatePickerDialog datePicker = new DatePickerDialog(TaskTabulationActivity.this, null,
                    ca.get(Calendar.YEAR),
                    ca.get(Calendar.MONTH),
                    ca.get(Calendar.DAY_OF_MONTH));
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "确认",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //确定的逻辑代码在监听中实现
                            DatePicker picker = datePicker.getDatePicker();
                            int year = picker.getYear();
                            int monthOfYear = picker.getMonth() + 1;
                            int dayOfMonth = picker.getDayOfMonth();
                            DecimalFormat df=new DecimalFormat("00");
                            String str2=df.format(monthOfYear);
                            String str3=df.format(dayOfMonth);
                            String endTime = year + "-" + str2 + "-" + str3;
                            carxkz_entertime.setText(endTime);
                            carXkz.setAttribute("entertime", endTime);
                        }
                    });
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //取消什么也不用做
                        }
                    });
            datePicker.getDatePicker().setSpinnersShown(false);
            datePicker.getDatePicker().setCalendarViewShown(true);
            datePicker.show();
        }
    }
}
