package com.hd.hse.wov.phone.ui.dailyrecord;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.R.bool;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView.ScaleType;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PhotoPreviewActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.web.wtdj.UpWtdjInfo;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.worklog.WorkLogEntry;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.wov.phone.R;

/**
 * ?????????????????????????????????
 * 
 * Created by wangdanfeng on 2016???3???11???
 */
public class DailyRecordActivity extends BaseFrameActivity implements
		OnClickListener, IEventListener {
	private static Logger logger = LogUtils
			.getLogger(DailyRecordActivity.class);
	private EditText edittextdescription;// ????????????
	private Button buttontype;// ????????????
	private Button buttonsave;// ????????????
	private TextView textviewname;// ?????????
	private GridView gridviewpic;// ????????????
	private TextView textviewtime;// ????????????
	private Button buttonsure;// ????????????????????????
	private Button buttoncacnle;// ????????????????????????
	private LinearLayout linearlayout;// ???????????????????????????

	private ListView listview;// ???????????????
	private View popview;// ???????????????
	private PopupWindow popupWindow;// ?????????
	private String[] listdataStrings = new String[] { "????????????", "???????????????" };// ?????????????????????

	private int currentPic;// ?????????????????????
	private int currentCamera;// ????????????????????????

	private GridViewAdapter gridviewadapter;
	private ArrayList<Image> imageList = new ArrayList<>();
	private List<Image> checkboxmark = new ArrayList<Image>();
	private boolean isFirstIn = true;
	private int width;
	private boolean isLongClick = false;

	private Context context;

	private WorkOrder workOrder;

	private boolean isSave; // ??????????????????????????????????????????

	private boolean flag = false; // ??????????????????????????????????????????
	private IQueryWorkInfo queryWorkInfo;

	private PDAWorkOrderInfoConfig pdaConfig;

	private WorkLogEntry workLog; // ?????????????????????
	// ???????????????
	/*
	 * private CheckBox checkBoxzyf;//??????????????????????????? private CheckBox
	 * checkBoxcbs;//??????????????????????????? private CheckBox checkBoxzypz;//??????????????????????????????
	 */
	private GridView gridviewCheckBox;// ????????????
	private GridViewCheckBoxAdapter mGridViewCheckBoxAdapter;// ???????????????adapter

	private List<Domain> problemTypeStr;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 233) {
				popupWindow.setFocusable(true);
				popupWindow.dismiss();
				buttontype.setText(msg.obj.toString());
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.hd_hse_wov_dailyrecord);
		// ?????????ActionBar
		setCustomActionBar(false, this, setActionBarItems(), true);
		// ?????????????????????
		setActionBartitleContent(getTitileName(), false);

		initData();
		initview();
	}

	private String[] setActionBarItems() {
		return new String[] { IActionBar.TV_BACK, IActionBar.TV_TITLE };
	}

	private String getTitileName() {
		return "????????????";
	}

	private void initData() {
		Intent intent = getIntent();
		workOrder = (WorkOrder) intent.getSerializableExtra(WorkOrder.class.getName());
		pdaConfig = new PDAWorkOrderInfoConfig();
		pdaConfig.setPscode("wtdj");
		queryWorkInfo = new QueryWorkInfo();

		workLog = new WorkLogEntry();
		workLog.setUd_zyxk_zysqid(workOrder.getUd_zyxk_zysqid());
		try {
			SequenceGenerator.genPrimaryKeyValue(new SuperEntity[] { workLog });
			workLog.setUd_cbsgl_rzid(workLog.getPadrzid());
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
		}
		try {
			problemTypeStr = queryWorkInfo.queryDomain("UDISSUETYPE", null);
		} catch (HDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initview() {
		edittextdescription = (EditText) findViewById(R.id.edittext_problem_description);
		buttontype = (Button) findViewById(R.id.button_problem_type);
		// buttontype.setOnClickListener(this);
		buttonsave = (Button) findViewById(R.id.button_save);
		buttonsave.setOnClickListener(this);
		textviewname = (TextView) findViewById(R.id.edittext_record_person);
		setname();// ?????????????????????????????????
		textviewtime = (TextView) findViewById(R.id.textview_recordtime);
		settime();// ?????????????????????????????????
		gridviewpic = (GridView) findViewById(R.id.gridview_pic);
		gridviewadapter = new GridViewAdapter();
		gridviewpic.setAdapter(gridviewadapter);
		gridviewpic.setOnItemClickListener(OnItemClickListener);
		gridviewpic.setOnItemLongClickListener(onItemLongClickListener);
		buttonsure = (Button) findViewById(R.id.button_sure);
		buttonsure.setOnClickListener(this);
		buttoncacnle = (Button) findViewById(R.id.button_cancle);
		buttoncacnle.setOnClickListener(this);
		linearlayout = (LinearLayout) findViewById(R.id.linearlayout);

		// ???????????????????????????popupwindow???????????????
		
		//?????????gridviewCheckBox???mGridViewCheckBoxAdapter????????????adapter
		gridviewCheckBox = (GridView) findViewById(R.id.gridview_checkbox);
		mGridViewCheckBoxAdapter = new GridViewCheckBoxAdapter<>(problemTypeStr);
		gridviewCheckBox.setAdapter(mGridViewCheckBoxAdapter);
		

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// ???????????????????????????view?????????
		super.onWindowFocusChanged(hasFocus);
		if (!flag) {
			flag = true;
			initpop();
		}
	}

	@SuppressWarnings("deprecation")
	private void initpop() {
		width = buttontype.getWidth();
		popview = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.hd_hse_wov_dailyrecord_poplist, null);
		listview = (ListView) popview
				.findViewById(R.id.hd_zyxk_poupwindow_listview);

		PopListViewAdapter adapter = new PopListViewAdapter(listdataStrings,
				context, handler);
		listview.setAdapter(adapter);

		popupWindow = new PopupWindow(popview, width,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setContentView(popview);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * ???????????????
	 * 
	 * Created by liuyang on 2016???3???24???
	 */
	private void setname() {
		PersonCard personCard = SystemProperty.getSystemProperty()
				.getLoginPerson();
		workLog.setCreateby(personCard.getPersonid());
		workLog.setCreatebydesc(personCard.getPersonid_desc());
		textviewname.setText(personCard.getPersonid_desc());
	}

	// ??????????????????
	private void settime() {
		String date = SystemProperty.getSystemProperty().getSysDateTime();
		textviewtime.setText(date);
		workLog.setCreatedate(date);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button_problem_type) {
			// popupWindow.showAsDropDown(buttontype, 0, 0);
		}

		if (v.getId() == R.id.button_save) {
			// TODO ??????,???????????????????????????
			// ????????????????????????????????????????????????????????????|| problemTypeStr.size() < 3???????????????????????????????????????
			if (edittextdescription.getText() == null
					|| edittextdescription.getText().toString().trim()
							.equals("") || problemTypeStr == null) {
				ToastUtils.toast(DailyRecordActivity.this,
						"?????????????????????????????????????????????????????????");
				return;

			}
			StringBuilder problemtype = new StringBuilder();
			HashMap<Integer, Boolean> check;

			if (mGridViewCheckBoxAdapter != null) {
				check = mGridViewCheckBoxAdapter.getCheck();
			} else {
				ToastUtils.toast(DailyRecordActivity.this, "??????????????????????????????????????????");
				return;
			}
			// ??????????????????????????????????????????????????????
			for (int i = 0; i < check.size(); i++) {
				if (check.get(i)) {
					if (problemtype.length() != 0) {
						problemtype.append(","
								+ problemTypeStr.get(i).getValue());
					} else {
						problemtype.append(problemTypeStr.get(i).getValue());
					}

				}
			}
			// ??????????????????????????????????????????????????????????????????
			if (problemtype.length() > 0) {
				workLog.setRztype(problemtype.toString()); // ????????????
				workLog.setZycontent(edittextdescription.getText().toString()); // ????????????
				if (!isSave) {
					saveWorkLog(workLog);
				}
				upWorkLog(workLog);

			} else {
				ToastUtils.toast(DailyRecordActivity.this,
						"?????????????????????????????????????????????????????????");
			}
		}

		// ????????????
		if (v.getId() == R.id.button_cancle) {
			isLongClick = false;
			linearlayout.setVisibility(View.GONE);
			gridviewadapter.notifyDataSetChanged();
			gridviewadapter.notifyDataSetInvalidated();

		}
		// ????????????
		if (v.getId() == R.id.button_sure) {
			if (checkboxmark.size() != 0) {
				for (int i = 0; i < checkboxmark.size(); i++) {
					File file = new File(checkboxmark.get(i).getImagepath());
					deleteFile(file);
					imageList.remove(checkboxmark.get(i));
				}
				deleteImgFromDB(checkboxmark);
				currentCamera = imageList.size();
				Log.e("????????????", imageList.size() + "");
				isLongClick = false;
				gridviewadapter.notifyDataSetChanged();

				gridviewadapter.notifyDataSetInvalidated();
				linearlayout.setVisibility(View.GONE);
				checkboxmark = new ArrayList<Image>();
			} else {
				ToastUtils.toast(DailyRecordActivity.this, "??????????????????????????????");
			}
		}

	}

	OnItemClickListener OnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (currentCamera == arg2) {
				// ???????????????????????????item???????????????
				touchImages();
			} else {
				// ????????????
				Intent intent = new Intent(DailyRecordActivity.this,
						PhotoPreviewActivity.class);
				intent.putExtra(PhotoPreviewActivity.SELECTEDINDEX, arg2);
				intent.putExtra(PhotoPreviewActivity.IMAGESET, imageList);
				startActivity(intent);
			}

		}
	};

	// ?????????????????????????????????
	OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {
			Log.e("????????????", currentCamera + "");
			if (arg2 != currentCamera) {
				checkboxmark = new ArrayList<Image>();
				linearlayout.setVisibility(View.VISIBLE);
				// arg1.findViewById(R.id.checkbox).setVisibility(View.VISIBLE);

				isLongClick = true;
				gridviewadapter.notifyDataSetChanged();
				gridviewadapter.notifyDataSetInvalidated();

			} else {
				// ????????????
				touchImages();
			}
			return false;
		}
	};

	private void touchImages() {
		// ??????Image
		Image image = new Image();
		image.setTablename("UD_CBSGL_RZ");// ??????
		image.setTableid(workLog.getPadrzid());// ?????????
		image.setImagepath(SystemProperty.getSystemProperty().getRootDBpath()
				+ File.separator + workOrder.getUd_zyxk_zysqid());// ???????????????
		image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
				.getPersonid());// ?????????
		image.setCreateusername(SystemProperty.getSystemProperty()
				.getLoginPerson().getPersonid_desc());
		// PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig)
		// currentNaviTouchEntity;
		// image.setFuncode(config.getPscode());// ????????????
		// image.setContype(config.getContype());
		image.setFuncode(pdaConfig.getPscode());// ????????????
		image.setContype(0);
		image.setImagename("????????????");
		Intent intent = new Intent(getBaseContext(),
				CameraCaptureActivity.class);
		intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (isFirstIn) {

			isFirstIn = false;
		} else {
			try {
				imageList = (ArrayList<Image>) queryWorkInfo.queryPhoto(
						workLog, pdaConfig);
				currentCamera = imageList.size();
				Log.e("	????????????", currentCamera + "");
				isLongClick = false;
				if (linearlayout.getVisibility() == View.VISIBLE) {
					linearlayout.setVisibility(View.GONE);
				}
				gridviewadapter.notifyDataSetChanged();
			} catch (HDException e) {
				logger.error(e);
				ToastUtils.imgToast(DailyRecordActivity.this,
						R.drawable.hd_hse_common_msg_wrong, "????????????????????????");
			}
		}
		super.onResume();
	}

	// ?????????????????????
	private void sortDatas() {

	}

	class GridViewAdapter<T extends SuperEntity> extends BaseAdapter {

		@Override
		public int getCount() {
			return imageList.size() + 1;
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.hd_hse_wov_daliyrecord_gridview_item,
								null);
				vh.img = (ImageView) convertView.findViewById(R.id.imageview);
				vh.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				vh.checkbox.setTag(position);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}

			if (isLongClick) {

				if (position != currentCamera) {
					vh.checkbox.setVisibility(View.VISIBLE);
					vh.checkbox.setChecked(false);
				} else {
					vh.checkbox.setVisibility(View.GONE);
				}

			}
			if (!isLongClick) {
				vh.checkbox.setVisibility(View.GONE);
			}

			vh.checkbox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								checkboxmark.add(imageList.get(position));
							} else {
								checkboxmark.remove(imageList.get(position));

							}

						}
					});
			if (position != currentCamera) {
				// ???????????????????????????????????????????????????
				// T entity = (T) imageList.get(position);
				// String imgPath = (String) entity.getAttribute("imagepath");
				// String imgname = (String) entity.getAttribute("imagename");
				// vh.img.setTag(imgPath);
				vh.img.setScaleType(ScaleType.CENTER_CROP);
				vh.img.setImageBitmap(BitmapFactory.decodeFile(
						imageList.get(position).getImagepath(), null));

			} else {
				vh.img.setImageResource(R.drawable.hd_hse_photo_add);
			}

			return convertView;

		}

		class ViewHolder {
			ImageView img;
			CheckBox checkbox;
		}

	}

	/**
	 * 
	 * @author yn
	 * ??????problemTypeStr????????????????????????checkbox,
	 * @param <T>
	 */
	class GridViewCheckBoxAdapter<T extends SuperEntity> extends BaseAdapter {
		private HashMap<Integer, Boolean> check;
		private List<Domain> problemTypeStr;

		public GridViewCheckBoxAdapter(List<Domain> problemTypeStr) {
			this.problemTypeStr = problemTypeStr;
			check = new HashMap<Integer, Boolean>();
			if (problemTypeStr != null && problemTypeStr.size() > 0) {
				for (int i = 0; i < problemTypeStr.size(); i++) {
					check.put(i, false);
				}
			}
		}

		@Override
		public int getCount() {

			return problemTypeStr.size();
		}

		@Override
		public Object getItem(int arg0) {
			return problemTypeStr.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater
						.from(getApplicationContext())
						.inflate(
								R.layout.hd_hse_wov_daliyrecord_gridview_checkbox_item,
								null);

				vh.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox_problemtype);

				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			vh.checkbox.setText(problemTypeStr.get(position).getDescription());

			vh.checkbox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								check.put(position, true);
							} else {
								check.put(position, false);

							}

						}
					});

			return convertView;

		}

		class ViewHolder {
			CheckBox checkbox;
		}

		/**
		 * 
		 * @return check
		 */
		public HashMap<Integer, Boolean> getCheck() {
			return check;
		}

	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {
		if (arg0 == IEventType.ACTIONBAR_BACK_CLICK) {

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(DailyRecordActivity.this)
					.setMessage("??????????????????")
					.setPositiveButton("??????",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									finish();
								}
							})
					.setNegativeButton("??????",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {

								}
							}).create().show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ????????????????????? Created by liuyang on 2016???3???24???
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.exists()) { // ????????????????????????
			if (file.isFile()) { // ?????????????????????
				file.delete(); // delete()?????? ??????????????? ??????????????????;
			} else if (file.isDirectory()) { // ??????????????????????????????
				File files[] = file.listFiles(); // ?????????????????????????????? files[];
				for (int i = 0; i < files.length; i++) { // ??????????????????????????????
					this.deleteFile(files[i]); // ??????????????? ???????????????????????????
				}
			}
			file.delete();
		} else {
			logger.error("???????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * Created by liuyang on 2016???3???24???
	 * 
	 * @param imgs
	 */
	private void deleteImgFromDB(List<Image> imgs) {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			BaseDao dao = new BaseDao();
			dao.deleteEntities(con, imgs.toArray(new Image[] {}));
			con.commit();
		} catch (SQLException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "?????????????????????");
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "????????????????????????");
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {

				}
			}
		}
	}

	/**
	 * ?????????????????? Created by liuyang on 2016???3???24???
	 * 
	 * @param workLog2
	 */
	private boolean saveWorkLog(WorkLogEntry workLog) {
		IConnectionSource conSrc = null;
		IConnection con = null;
		try {
			conSrc = ConnectionSourceManager.getInstance()
					.getJdbcPoolConSource();
			con = conSrc.getConnection();
			BaseDao dao = new BaseDao();
			dao.insertEntity(con, workLog);
			con.commit();
			isSave = true;
			return true;
		} catch (SQLException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "?????????????????????");
		} catch (DaoException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "????????????????????????");
		} finally {
			if (con != null) {
				try {
					conSrc.releaseConnection(con);
				} catch (SQLException e) {

				}
			}
		}
		return false;
	}

	private void upWorkLog(WorkLogEntry entry) {
		final ProgressDialog dialog = new ProgressDialog(
				DailyRecordActivity.this, true, "????????????...");
		// CBSWorklogUp worklog = new CBSWorklogUp();
		UpWtdjInfo worklog = new UpWtdjInfo();
		BusinessAction action = new BusinessAction(worklog);
		BusinessAsyncTask task = new BusinessAsyncTask(action,
				new AbstractAsyncCallBack() {

					@Override
					public void start(Bundle msgData) {

					}

					@Override
					public void processing(Bundle msgData) {

					}

					@Override
					public void error(Bundle msgData) {
						try {
							dialog.dismiss();
						} catch (Exception e) {
							logger.error(e);
						}
						ToastUtils
								.imgToast(DailyRecordActivity.this,
										R.drawable.hd_hse_common_msg_wrong,
										"???????????????????????????");
					}

					@Override
					public void end(Bundle msgData) {
						try {
							dialog.dismiss();
						} catch (Exception e) {
							logger.error(e);
						}
						ToastUtils
								.imgToast(DailyRecordActivity.this,
										R.drawable.hd_hse_common_msg_right,
										"???????????????????????????");
						DailyRecordActivity.this.finish();
					}
				});
		try {
			List<WorkLogEntry> data = new ArrayList<>();
			data.add(entry);
			task.execute("", data);
			if (dialog != null) {
				dialog.show();
			}
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(DailyRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
		}
	}

}
