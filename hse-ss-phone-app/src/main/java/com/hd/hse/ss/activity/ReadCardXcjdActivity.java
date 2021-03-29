package com.hd.hse.ss.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.business.util.SYSConstant;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.business.readcard.ReadCardThread;
import com.hd.hse.common.module.phone.ui.activity.BaseActivity;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.ss.R;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * 
 * @author yn
 * 
 */
public class ReadCardXcjdActivity extends BaseActivity {
	private static Logger logger = LogUtils
			.getLogger(ReadCardXcjdActivity.class);
	public static final String READCADRACTION = "readCardAction";
	// 刷卡区
	private EditText cardEdit;
	// 审批环节描述
	private TextView exmineDesc;
	/**
	 * 关系检查 Created by liuyang on 2016年9月22日
	 */
	public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();

	/** 独立线程读卡 */
	// 是否初始化完成标志
	public boolean flag = false;
	// 刷卡线程
	public ReadCardThread myReadCardThread;
	// 刷卡handler
	public Handler myhandler;
	// 是否结束刷卡
	public boolean isend = false;
	/** NFC读卡功能 */
	// 默认的读卡设备
	private NfcAdapter nfcAdapter;
	// 意图拦截器
	private PendingIntent pendingIntent;
	// 意图拦截类型列表
	private IntentFilter[] intentFiltersArray;
	// 是否开启了nfc功能
	private boolean nfcenable = false;

	/**
	 * 是否是读卡改变的editText
	 */
	private boolean isReadCard = false;
	/**
	 * 手否可手填人名
	 */
	private boolean isCanSt = false;

	public Object getNfcAdapter() {
		return nfcAdapter;
	}

	public boolean getNfcEnable() {
		return nfcAdapter.isEnabled();
	}

	public String cardnum = "";
	private String name = "";
	private ArrayList<PersonCard> mPersonCards = new ArrayList<PersonCard>();
	/** 需要拦截的卡 */
	private String[][] techListsArray = new String[][] {
			new String[] { NfcV.class.getName() },
			new String[] { MifareClassic.class.getName(), NfcA.class.getName(),
					Ndef.class.getName() },
			new String[] { IsoDep.class.getName(), NfcA.class.getName(),
					NdefFormatable.class.getName() },
			new String[] { NfcF.class.getName() },
			new String[] { NfcA.class.getName() } };
	/**
	 * 只能刷一个人的卡
	 */
	private int singlePersonState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.hd_common_component_readcard2);

		// 获取默认的NFC控制器
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// NFC拦截器
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		intentFiltersArray = new IntentFilter[] { ndef };

		initView();
		initData();

	}

	private void initView() {
		// TODO Auto-generated method stub
		TextView cancelBT = (TextView) this
				.findViewById(R.id.hd_hse_common_readcard_cancel_tv);
		TextView sureBT = (TextView) this
				.findViewById(R.id.hd_hse_common_readcard_sure_tv);
		exmineDesc = (TextView) findViewById(R.id.hd_hse_common_readcard_tv);
		View view = findViewById(R.id.hd_hse_common_readcard_rl);
		// 压缩读卡界面图片
		setBackground(view, R.drawable.hd_hse_common_readcard_bg);
		cardEdit = (EditText) findViewById(R.id.hd_hse_common_readcard_et);
		cardEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (!isReadCard) {
					PersonCard card = new PersonCard();
					card.setPersonid_desc(arg0.toString());
					if (singlePersonState == 1) {
						mPersonCards.clear();
						mPersonCards.add(card);
					}
				}
				isReadCard = false;
			}
		});

		cancelBT.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		sureBT.setOnClickListener(sureClick);

	}

	private void initData() {
		Intent intent = getIntent();
		String person = intent.getStringExtra(SupervisionDetailActivity.NAME);
		singlePersonState = intent.getIntExtra(
				SupervisionDetailActivity.ISSINGLEPERSON, 0);
		isCanSt=intent.getBooleanExtra(SupervisionDetailActivity.EDITTEXTISFOCUSABLE, false);
		if (isCanSt) {
			cardEdit.setFocusable(true);
			//Log.e("Focusable", "true");
			
		} else {
			cardEdit.setFocusable(false);
			//Log.e("Focusable", "false");
		}

		exmineDesc.setText(person);

	}

	private OnClickListener sureClick = new OnClickListener() {
		public void onClick(View arg0) {
			if (mPersonCards.size() == 0) {
				if (isCanSt) {
					ToastUtils.toast(ReadCardXcjdActivity.this, "请填写人员名");
				}else {
					ToastUtils.toast(ReadCardXcjdActivity.this, "请刷卡");
				}
				return;
			}
			Intent i = new Intent();
			Bundle bundle = new Bundle();
			bundle.putSerializable(PersonCard.class.getName(), mPersonCards);
			i.putExtras(bundle);
			setResult(SupervisionDetailActivity.READCARDEXAMINEACTIVITYCODE, i);
			finish();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		/** 满足过滤条件的标签及数据格式，继续处理，不满足的会弹出选择对应的应用 */
		if (NFCReader.nfcReadCardEnable()) {
			if (nfcAdapter != null) {
				if (nfcAdapter.isEnabled()) {
					nfcenable = true;
					nfcAdapter.enableForegroundDispatch(this, pendingIntent,
							intentFiltersArray, techListsArray);
					// 读卡号
					cardnum = getIntent().getStringExtra("nfcmsg");
					if (cardnum != null && !cardnum.trim().equals("")) {
						try {
							// 去掉前几位（大连项目特殊需求）
							IQueryRelativeConfig relationConfig = new QueryRelativeConfig();
							RelationTableName config = new RelationTableName();
							config.setSys_type(IRelativeEncoding.ISSUBSTRING);
							RelationTableName relation = relationConfig
									.getRelativeObj(config);
							int num = 0;
							if (relation != null
									&& !StringUtils.isEmpty(relation
											.getInput_value())
									&& NumberUtils.isNumber(relation
											.getInput_value())) {
								num = Integer
										.valueOf(relation.getInput_value());
							}
							cardnum = cardnum.substring(num);
							PersonCard card = getPersonCard(cardnum);
							if (singlePersonState == 1) {
								name = card.getPersonid_desc();
								cardEdit.setText(name);
								mPersonCards.clear();
								mPersonCards.add(card);
								return;
							}

							// 相同人员
							for (int i = 0; i < mPersonCards.size(); i++) {
								if (mPersonCards.get(i).getPersonid()
										.equals(card.getPersonid())) {
									cardnum = "";
									return;
								}
							}
							mPersonCards.add(card);
							cardnum = "";
							// 设置cardEdit
							if ("".equals(name)) {
								name += card.getPersonid_desc();
							} else {
								name += "," + card.getPersonid_desc();
							}
							isReadCard = true;
							cardEdit.setText(name);
						} catch (HDException e) {
							logger.error(e);
							ToastUtils.imgToast(this,
									R.drawable.hd_hse_common_msg_wrong,
									e.getMessage());
						} finally {
							cardnum = ""; // 清空刷卡信息
						}
					}
					getIntent().putExtra("nfcmsg", "");
				} else {
					ToastUtils.toast(this, "NFC刷卡功能未开启");
				}

			}
		} else {
			// 表示开启寻卡功能
			isend = false;
			StartReadCardThread();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (nfcAdapter != null) {
			nfcenable = true;
			nfcAdapter.disableForegroundDispatch(this);
			if (cardnum != null && cardnum.length() != 0) {
				getIntent().removeExtra("nfcmsg");
			}
		} else if (null != myReadCardThread) {
			myReadCardThread.setStop();
			isend = true;// 表示关闭寻卡功能
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
			Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String id = NFCReader.readCardId(tagFromIntent.getId(), true);
			intent.putExtra("nfcmsg", id);
		}
	};

	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle bd = new Bundle();
			bd = msg.getData();
			String info = bd.getString(SYSConstant.KEYCARDCODE);
			if (!info.trim().equals("")) {
				myReadCardThread.setStop();
			}
		}
	}

	/**
	 * StartReadCardThread:(开启读卡线程). <br/>
	 * date: 2014年9月12日 <br/>
	 * 
	 * @author lxf
	 */
	public void StartReadCardThread() {
		myhandler = new MyHandler();
		myReadCardThread = new ReadCardThread(myhandler);
		myReadCardThread.start();
	}

	/**
	 * (获取刷卡人员信息) Created by liuyang on 2016年9月22日
	 * 
	 * @param cardNum
	 * @return
	 * @throws HDException
	 */
	private PersonCard getPersonCard(String cardNum) throws HDException {
		StringBuilder sbSql = new StringBuilder();
		sbSql.setLength(0);
		// 获取人员信息
		PersonCard psnCard = new PersonCard();
		try {
			String cardNumMD5 = "";
			// 判断是否需要对人员卡加密
			RelationTableName relationEntity = new RelationTableName();
			relationEntity.setSys_type(IRelativeEncoding.CARDISMD5);
			relationEntity.setSys_fuction(null);
			relationEntity.setSys_value("");
			if (relativeConfig.isHadRelative(relationEntity)) {
				cardNumMD5 = UtilTools.string2MD5(cardNum);
			}
			sbSql.append("select * from ud_zyxk_ryk ");
			// 通过人员卡登录
			if (!StringUtils.isEmpty(cardNum)) {
				sbSql.append(" where pcardnum='").append(cardNum).append("' COLLATE NOCASE");
				sbSql.append(" or pcardnum='").append(cardNumMD5).append("' COLLATE NOCASE;");
			} else {
				throw new HDException("不存在该卡号的人员！");
			}
			Log.e("现场监督sql", sbSql.toString());
			BaseDao dao = new BaseDao();
			psnCard = (PersonCard) dao.executeQuery(sbSql.toString(),
					new EntityResult(PersonCard.class));
			if (psnCard == null)
				throw new HDException("无效刷卡用户！");
			if (psnCard.getIscan() == 0)
				throw new HDException("该人员卡已失效，请联系相关负责人！");
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new DaoException("判断审批权限失败！");
		}
		return psnCard;
	}

}