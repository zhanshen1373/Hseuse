package com.hd.hse.common.module.phone.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.custom.PositionProgressDialog;
import com.hd.hse.common.module.phone.ui.custom.PositionProgressDialog.PositionCallback;
import com.hd.hse.common.module.phone.ui.utils.GPSStateUtil;
import com.hd.hse.constant.IActionType;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.vp.service.saveinfo.SaveLocationActionListener;

public class LocationCardActivity extends BaseFrameActivity
		implements
			OnClickListener,
			IEventListener,
			PositionCallback {

	public static final String PARAMS = "PARAMS";
	public static final String POSITION = "position";

	private int position = -1;
	private PositionCard positionCard;

	private TextView positionDescTV;
	private TextView longitudeTV;
	private TextView latitudeTV;
	private EditText radiusTV;

	private View getPositionTV;
	private TextView saveTV;

	/**
	 * action:TODO(后台业务处理).
	 */
	private BusinessAction action;

	/**
	 * asyncTask:TODO(登录异步处理).
	 */
	private BusinessAsyncTask asyncTask;

	private SaveLocationActionListener saveListener;
	/**
	 * prsDlg:TODO(等待).
	 */
	private ProgressDialog prsDlg;

	private PositionProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_position_card_info_activity);

		// 初始化ActionBar
		setCustomActionBar(false, this, setActionBarItems());
		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);

		// 初始化位置卡数据
		position = getIntent().getIntExtra(POSITION, -1);
		positionCard = (PositionCard) getIntent().getSerializableExtra(PARAMS);

		positionDescTV = (TextView) findViewById(R.id.tv_position_desc);
		positionDescTV.setText(positionCard.getLocation_desc());

		longitudeTV = (TextView) findViewById(R.id.tv_position_longitude);
		longitudeTV.setText(String.valueOf(positionCard.getLongitude()));

		latitudeTV = (TextView) findViewById(R.id.tv_position_latitude);
		latitudeTV.setText(String.valueOf(positionCard.getLatitude()));

		radiusTV = (EditText) findViewById(R.id.tv_position_radius);
		radiusTV.setText(String.valueOf(positionCard.getRadiu()));

		getPositionTV = findViewById(R.id.tv_get_position);
		saveTV = (TextView) findViewById(R.id.tv_position_save);

		getPositionTV.setOnClickListener(this);
		saveTV.setOnClickListener(this);

		saveListener = new SaveLocationActionListener();
		action = new BusinessAction(saveListener);

		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(action,
					new AbstractAsyncCallBack() {

						@Override
						public void start(Bundle msgData) {
							// TODO Auto-generated method stub

						}

						@Override
						public void processing(Bundle msgData) {
							// TODO Auto-generated method stub
							if (msgData.containsKey(IActionType.VP_SAVELOCTION)) {
								prsDlg.showMsg(msgData
										.getString(IActionType.VP_SAVELOCTION));
							}
						}

						/**
						 * TODO 登录失败，只处理登录验证失败的信息，基础数据更新的错误提示不处理
						 * 
						 * @see com.hd.hse.business.task.AbstractAsyncCallBack#error(android.os.Bundle)
						 */
						@Override
						public void error(Bundle msgData) {
							// TODO Auto-generated method stub
							if (msgData.containsKey(IActionType.VP_SAVELOCTION)) {
								prsDlg.dismiss();
								String msg = msgData
										.getString(IActionType.VP_SAVELOCTION);
								ToastUtils
										.toast(LocationCardActivity.this, msg);
							}
							// AysncTaskMessage msg = (AysncTaskMessage) msgData
							// .getSerializable("p");
							// String error = msg.getMessage();
							// prsDlg.dismiss();
							// ToastUtils.toast(LocationCardActivity.this,
							// error);
						}

						/**
						 * TODO 登录成功，只处理登录成功的信息
						 * 
						 * @see com.hd.hse.business.task.AbstractAsyncCallBack#end(android.os.Bundle)
						 */
						@Override
						public void end(Bundle msgData) {
							// TODO Auto-generated method stub
							if (msgData.containsKey(IActionType.VP_SAVELOCTION)) {
								// 表示PC端保存成功
								prsDlg.dismiss();
								try {
									saveListener.action(
											IActionType.VP_LOCATIONSAVELOCTION,
											positionCard);
									ToastUtils.toast(LocationCardActivity.this,
											"保存成功!");
								} catch (HDException e) {
									// TODO Auto-generated catch block
									ToastUtils.toast(LocationCardActivity.this,
											e.getMessage());
								}
							}
						}
					});
		}
		dialog = new PositionProgressDialog(this);
		dialog.setPositionCallback(this);
	}

	private String getTitileName() {
		return "虚拟位置维护";
	}

	private String[] setActionBarItems() {
		return new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE};
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.tv_get_position) { // 获取经纬度
			if (!GPSStateUtil.isGPSOpen(LocationCardActivity.this)) {
				GPSStateUtil.openGPS(LocationCardActivity.this);
				Toast.makeText(LocationCardActivity.this, "请打开GPS",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (this.dialog != null && !this.dialog.isShowing()) {
				this.dialog.show();
			}

		} else if (id == R.id.tv_position_save) { // 保存修改的信息
			String longitude = longitudeTV.getText().toString().trim();
			String latitude = latitudeTV.getText().toString().trim();
			String radius = radiusTV.getText().toString().trim();

			if (isDigit(longitude) && isDigit(latitude) && isInteger(radius)) {
				positionCard.setLongitude(/* Float.valueOf( */longitude/* ) */);
				positionCard.setLatitude(/* Float.valueOf( */latitude/* ) */);
				positionCard.setRadiu(Integer.valueOf(radius));

				try {
					asyncTask.execute(IActionType.VP_SAVELOCTION, positionCard);
					prsDlg = new ProgressDialog(this, "保存位置信息");
					prsDlg.show();
				} catch (HDException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isDigit(String number) {
		try {
			Float.valueOf(number);// 把字符串强制转换为数字
			return true;// 如果是数字，返回True
		} catch (Exception e) {
			return false;// 如果抛出异常，返回False
		}
	}

	public boolean isInteger(String number) {
		try {
			Integer.valueOf(number);
			return true;
		} catch (Exception e) {
			ToastUtils.imgToast(LocationCardActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "请输入整数");
			return false;
		}
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(LocationListActivity.POSITION, position);
		data.putExtra(LocationListActivity.PARAMS, positionCard);
		setResult(Activity.RESULT_OK, data);
		super.finish();
	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {

	}

	@Override
	public void onCancel() {

	}

	@Override
	public void onSaveData(Location location) {
		if (location != null) {
			longitudeTV.setText(cutFloat(location.getLongitude(), 6));
			latitudeTV.setText(cutFloat(location.getLatitude(), 6));
		}
	}
	
	/**
	 * 
	 * cutFloat:(截取float小数点后后  i 位). <br/>
	 * date: 2015年11月27日 <br/>
	 *
	 * @author LiuYang
	 * @param d
	 * @param i
	 * @return
	 */
	private String cutFloat(double d, int i) {
		String str = String.valueOf(d);
		System.out.println(str);
		str = str.substring(0, (str.indexOf(".") + i + 1));
		return str;
	}
}
