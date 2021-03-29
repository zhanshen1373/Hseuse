package com.hd.hse.pc.phone.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.web.cbs.PCGetInAndOutFactoryRecords;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.other.InAndOutFactoryRecordEntity;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.PersonAccessRecordAdapter;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * 人员出入厂记录Activity Created by liuyang on 2016年9月26日
 */
public class PersonAccessRecordActivity extends BaseFrameActivity implements
		IEventListener {
	private static Logger logger = LogUtils
			.getLogger(PersonAccessRecordActivity.class);
	/**
	 * adapter
	 */
	private PersonAccessRecordAdapter mPersonAccessRecordAdapter;
	/**
	 * listview
	 */
	private ListView lv;
	/**
	 * 数据
	 */
	private ArrayList<InAndOutFactoryRecordEntity> mPersonAccessRecords;
	
	private PCGetInAndOutFactoryRecords record;

	/**
	 * actionRecord:TODO(出入厂监听).
	 */
	private BusinessAction actionRecord;
	private BusinessAsyncTask asyncTask;
	private ProgressDialog prsDlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hse_pc_phone_app_person_access_record_layout);
		initActionbar();
		initView();
		// initDataForTest();
		initData();
	}

	private void initActionbar() {
		// 初始化ActionBar
		setCustomActionBar(false, this, new String[] { IActionBar.TV_BACK,
				IActionBar.TV_TITLE});
		// 设置导航栏标题
		setActionBartitleContent("出入厂记录", false);
		// 设置右侧菜单栏
		
	}

	/**
	 * 初始化测试数据
	 */
	private void initDataForTest() {
		// 测试数据
		InAndOutFactoryRecordEntity mPersonAccessRecord = new InAndOutFactoryRecordEntity();
		mPersonAccessRecord.setCrdatetime("2016-09-27");
		mPersonAccessRecord.setLocation("海顿新科");
		for (int i = 0; i < 6; i++) {
			mPersonAccessRecords.add(mPersonAccessRecord);
		}
		mPersonAccessRecordAdapter.notifyDataSetChanged();

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		prsDlg = new ProgressDialog(this, "数据努力加载中。。。");
		if (null == record) {
			record = new PCGetInAndOutFactoryRecords();
		}
		if (null == actionRecord) {
			actionRecord = new BusinessAction(record);
		}
		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(actionRecord,
					new AbstractAsyncCallBack() {

						@Override
						public void start(Bundle msgData) {
							// TODO Auto-generated method stub

						}

						@Override
						public void processing(Bundle msgData) {
							prsDlg.show();
						}

						@Override
						public void error(Bundle msgData) {
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							ToastUtils.imgToast(
									PersonAccessRecordActivity.this,
									R.drawable.hd_hse_common_msg_wrong,
									msg.getMessage().contains("超时") ? msg
											.getMessage() : "获取出入厂记录错误！请联系管理员");
							prsDlg.dismiss();
						}

						@Override
						public void end(Bundle msgData) {
							// TODO Auto-generated method stub
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							List<InAndOutFactoryRecordEntity> datas = (List<InAndOutFactoryRecordEntity>) msg
									.getReturnResult();
							if (datas != null && datas.size() > 0) {
								datas = Sort(datas, "getCrdatetime", "desc");
								mPersonAccessRecords.addAll(datas);
								mPersonAccessRecordAdapter
										.notifyDataSetChanged();
							}
							prsDlg.dismiss();
						}
					});
		}
		try {
			String innercard = ((PersonCard) getIntent().getSerializableExtra(
					PersonCard.class.getName())).getInnercard();
			if (innercard != null && !"".equals(innercard)) {
				asyncTask.execute(IActionType.WEB_INOROUTRECORD, innercard);
			} else {
				ToastUtils.imgToast(PersonAccessRecordActivity.this,
						R.drawable.hd_hse_common_msg_wrong, "无效卡（入场证号不能为空）！");
			}
		} catch (HDException e) {
			ToastUtils.imgToast(PersonAccessRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "加载数据出错！请联系管理员！");
		}
	}

	@SuppressWarnings("unchecked")
	public List<InAndOutFactoryRecordEntity> Sort(
			List<InAndOutFactoryRecordEntity> list, final String method,
			final String sort) {
		Collections.sort(list, new Comparator() {
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Method m1 = ((InAndOutFactoryRecordEntity) a).getClass()
							.getMethod(method, new Class[]{});
					Method m2 = ((InAndOutFactoryRecordEntity) b).getClass()
							.getMethod(method, new Class[]{});
					if (sort != null && "desc".equals(sort))// 倒序
						ret = m2.invoke(((InAndOutFactoryRecordEntity) b), new Object[]{})
								.toString()
								.compareTo(
										m1.invoke(
												((InAndOutFactoryRecordEntity) a),
												new Object[]{}).toString());
					else
						// 正序
						ret = m1.invoke(((InAndOutFactoryRecordEntity) a), new Object[]{})
								.toString()
								.compareTo(
										m2.invoke(
												((InAndOutFactoryRecordEntity) b),
												new Object[]{}).toString());
				} catch (NoSuchMethodException ne) {
					logger.error(ne.getMessage());
				} catch (IllegalAccessException ie) {
					logger.error(ie.getMessage());
				} catch (InvocationTargetException it) {
					logger.error(it.getMessage());
				}
				return ret;
			}
		});
		return list;
	}

	private void initView() {
		lv = (ListView) findViewById(R.id.hse_pc_phone_app_person_access_record_layout_lv);
		mPersonAccessRecords = new ArrayList<InAndOutFactoryRecordEntity>();
		mPersonAccessRecordAdapter = new PersonAccessRecordAdapter(this,
				mPersonAccessRecords);
		lv.setAdapter(mPersonAccessRecordAdapter);
	}

	@Override
	public void eventProcess(int eventType, Object... objects)
			throws HDException {

	}

}
