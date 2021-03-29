package com.hd.hse.common.component.phone.custom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.adapter.HSEDialogAdapter;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.dc.business.common.weblistener.down.GainRouteUrl;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

import org.apache.log4j.Logger;

/**
 * TODO 弹出框 ClassName: MyAlertDialog ()<br/>
 * date: 2015年2月12日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class MyAlertDialog extends AlertDialog implements OnClickListener {
	private static Logger logger = LogUtils.getLogger(MyAlertDialog.class);
	/**
	 * superEntity:TODO(作业票实体).
	 */
	public SuperEntity superEntity;

	public ListView dytable;

	public Context context;

	public Boolean isDjLx;

	public Button routeBtn;

	private ProgressDialog dialog;

	public HSEDialogAdapter mDialogAdapter;

	public MyAlertDialog(Context context) {
		super(context);
	}

	public MyAlertDialog(Context context, int theme, SuperEntity superEntity,
			Boolean isDjLx) {
		super(context, theme);
		this.context = context;
		this.superEntity = superEntity;
		this.isDjLx = isDjLx;
	}

	public MyAlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_component_actionbar_titletouch_popwindow);
		dytable = (ListView) this
				.findViewById(R.id.hd_hse_common_component_dialog_lv);
		mDialogAdapter = new HSEDialogAdapter(context, superEntity, isDjLx);
		dytable.setAdapter(mDialogAdapter);
		// 关闭按钮
		ImageButton ib = (ImageButton) this
				.findViewById(R.id.hd_hse_common_component_phone_actionbar_ib_close);
		ib.setOnClickListener(this);
		// dytable.initTabRow(superEntity);
		routeBtn = (Button) this.findViewById(R.id.img_btn_route);
		setRoute(superEntity);
	}

	public void setTabRow(SuperEntity superEntity) {// 判断是否走一票制
		setRoute(superEntity);
		this.superEntity=superEntity;
		mDialogAdapter.setCurentSuperEntity(superEntity);
	}
	
	private void setRoute(SuperEntity superEntity) {
		IQueryRelativeConfig relation = new QueryRelativeConfig();
		RelationTableName relationEntity = new RelationTableName();
		relationEntity.setSys_type(IRelativeEncoding.ISZYSQOFONE);
		boolean iszysqofone = relation.isHadRelative(relationEntity);
		if (iszysqofone) {
			String zyptype = (String) superEntity.getAttribute("zyptype");
			if (zyptype != null && zyptype.contains(IWorkOrderZypClass.ZYPCLASS_JCP)) {
				routeBtn.setVisibility(View.VISIBLE);
				routeBtn.setOnClickListener(this);
			} else {
				routeBtn.setVisibility(View.GONE);
			}
		} else {
			routeBtn.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(final View v) {
		int id = v.getId();
		if (id == R.id.hd_hse_common_component_phone_actionbar_ib_close) {
			dismiss();
		} else if (id == R.id.img_btn_route) {
			String workId = "";
			if (superEntity instanceof WorkTask) {
				workId = (String) superEntity.getAttribute("ud_zyxk_worktaskid");
			} else {
				workId = (String) superEntity.getAttribute("ud_zyxk_zysqid");
			}
			GainRouteUrl gainPDFUrl = new GainRouteUrl(workId);
			BusinessAction action = new BusinessAction(gainPDFUrl);
			BusinessAsyncTask task = new BusinessAsyncTask(action, callBack);
			try {
				task.execute("");
				dialog = new ProgressDialog(context);
				dialog.setMessage("正在获取文件地址...");
				dialog.show();
			} catch (HDException e) {
				logger.error(e);
				ToastUtils.imgToast(context,
						R.drawable.hd_hse_common_msg_wrong, "获取文件失败！！");
			}
		}
	}

	private AbstractAsyncCallBack callBack = new AbstractAsyncCallBack() {

		@Override
		public void start(Bundle msgData) {

		}

		@Override
		public void processing(Bundle msgData) {

		}

		@Override
		public void end(Bundle msgData) {
			AysncTaskMessage msg = (AysncTaskMessage) msgData
					.getSerializable("p");
			String url = (String) msg.getReturnResult();
			Class<?> clazz;
			try {
				clazz = Class
						.forName("com.hd.hse.common.module.phone.ui.activity.ImageShowerActivity");
				Intent intent = new Intent(context, clazz);
				Bundle bundle = new Bundle();
				bundle.putString("headimage", url);
				bundle.putBoolean("fromHttp", true);
				intent.putExtras(bundle);
				context.startActivity(intent);
				dialog.cancel();
			} catch (ClassNotFoundException e) {
				logger.error("获取文件失败！！");
				ToastUtils.imgToast(context,
						R.drawable.hd_hse_common_msg_wrong, "获取文件失败！！");
				dialog.cancel();
			}
		}

		@Override
		public void error(Bundle msgData) {
			logger.error("获取文件失败！！");
			ToastUtils.imgToast(context, R.drawable.hd_hse_common_msg_wrong,
					"未获取到路线图文件！！");
			dialog.cancel();
		}

	};
}
