package com.hd.hse.common.module.phone.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.lidroid.xutils.ViewUtils;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class ExamineActivity<T extends SuperEntity> extends BaseActivity {
	private static Logger logger = LogUtils.getLogger(ExamineActivity.class);
	private final String IACTIONTYPE = "iactiontype";
	private final String PARASMAP = "parasMap";
	private ExamineListView examine;
	private WorkOrder workOrder;
	private Intent intent;
	private List<T> examineList;
	private SuperEntity spEntity;
	public static IEventListener iEventLsn;
	private ProgressDialog prgDialog;
	private int postion;
	/**
	 * modifyListSuperEntity:TODO(完成操作后的数据集).
	 */
	private List<SuperEntity> modifyListSuperEntity;
	/**
	 * paras:TODO(危害/审批环节信息).
	 */
	private HashMap<String, Object> parasMap;
	/**
	 * asyTask:TODO(后台异步任务).
	 */
	private BusinessAsyncTask asyTask;
	/**
	 * busAction:TODO(后台业务处理对象).
	 */
	private BusinessAction busAction;
	// 获取当前导航栏配置实体
	private SuperEntity currentNaviTouchEntity;
	/**
	 * 事件类型
	 */
	private String iActionType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.hd_hse_common_examine_layout_activity);
		ViewUtils.inject(this);

		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		examine = (ExamineListView) this.findViewById(R.id.hd_hse_examine_elv);
		View view = (View) findViewById(R.id.hd_hse_examine_ll);
		View chileView = (View) findViewById(R.id.hd_hse_examine_child_ll);
		chileView.setVisibility(View.VISIBLE);
		// 控件显示动画
		Animation animation = AnimationUtils.loadAnimation(
				ExamineActivity.this, R.anim.hd_hse_common_in_from_down);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(400);
		chileView.startAnimation(animation);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ExamineActivity.this.finish();
			}
		});
	}

	/**
	 * 当activity销毁时监听置空
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.iEventLsn = null;
	}

	private void initData() {
		// TODO Auto-generated method stub
		intent = this.getIntent();
		workOrder = (WorkOrder) intent.getSerializableExtra("workOrder");
		iActionType = intent.getStringExtra(IACTIONTYPE);
		examineList = (List<T>) intent.getSerializableExtra("examineList");
		parasMap = (HashMap<String, Object>) intent
				.getSerializableExtra(PARASMAP);
		if (examineList != null && examineList.size() > 0) {
			examine.setData(examineList);
		}
		examine.setIEventListener(eventListent);
	}

	private IEventListener eventListent = new IEventListener() {

		@SuppressWarnings("unchecked")
		@Override
		public void eventProcess(int eventType, Object... objects) {
			if (eventType == IEventType.EXAMINE_TOEXAMINE_ClICK) {
				try {
					postion = (int) objects[0];
					if (iEventLsn != null) {
						iEventLsn.eventProcess(
								IEventType.EXAMINE_TOEXAMINE_ClICK,
								(int) objects[0]);
					}
					Intent intent = new Intent(ExamineActivity.this,
							ReadCardExamineActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("workOrder", workOrder);
					bundle.putSerializable("workApprovalPermission",
							(WorkApprovalPermission) examineList.get(postion));
					spEntity = (SuperEntity) objects[1];
					intent.putExtras(bundle);
					ExamineActivity.this.startActivityForResult(intent, 1);

				} catch (HDException e) {
					logger.error(e.getMessage());
				}
				// ExamineActivity.this.startActivity(intent);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 1 && intent != null) {
			try {
				spEntity = (SuperEntity) intent
						.getSerializableExtra("workApprovalPermission");
				//审批
				saveModifiedData();
				if (spEntity != null) {
					examine.setCurrentEntity(spEntity);
				}
			} catch (HDException e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * TODO 保存审批环节 saveModifiedData:(). date: 2014年10月23日
	 * 
	 * @author zhulei
	 * @param eventType
	 * @param objects
	 * @throws HDException
	 */
	public void saveModifiedData() throws HDException {
		busAction = new BusinessAction(new CheckControllerActionListener());
		asyTask = new BusinessAsyncTask(busAction);

		prgDialog = new ProgressDialog(this, "审核信息保存");
		prgDialog.show();
		try {
			iEventLsn.eventProcess(IEventType.EXAMINE_EXAMINE_ClICK, parasMap);
			// 设置审批环节点信息
			parasMap.put(WorkApprovalPermission.class.getName(),
					examineList.get(postion));
			asyTask.execute(iActionType, parasMap);
			iEventLsn.eventProcess(IEventType.EXAMINE_EXAMINE_ClICK_AFTER);
		} catch (HDException e) {
			logger.error(e.getMessage());
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					"保存信息出错，请联系管理员!");
			throw e;
		}
	}

}
