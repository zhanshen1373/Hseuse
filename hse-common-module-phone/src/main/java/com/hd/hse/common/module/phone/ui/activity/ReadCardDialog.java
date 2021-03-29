package com.hd.hse.common.module.phone.ui.activity;

import org.apache.log4j.Logger;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author zl
 * 
 * @param <T>
 */
public class ReadCardDialog<T extends SuperEntity> extends Dialog {
	private static Logger logger = LogUtils.getLogger(ReadCardDialog.class);
	/**
	 * 取消按钮
	 */
	private TextView cancelBtn;
	/**
	 * 确认按钮
	 */
	private TextView sureBtn;
	/**
	 * 读卡输入区
	 */
	private EditText readCardEdit;
	/**
	 * 刷卡人描述
	 */
	private TextView readCardTV;
	/**
	 * 实体
	 */
	private T spEntity;
	// 调用接口
	private IEventListener iEventLsn;
	/**
	 * 是否多人刷卡
	 */
	private int ismoreRead;
	private Dialog dialog;
	private RelativeLayout readCardRL;

	public ReadCardDialog(Context context, int theme) {
		super(context, theme);
		this.dialog = this;
		setContentView(R.layout.hd_common_component_readcard);
		setCanceledOnTouchOutside(false); // 禁止点击空白消失
		initView();
		initData();
	}

	private void initView() {
		cancelBtn = (TextView) findViewById(R.id.hd_hse_common_readcard_cancel_tv);
		sureBtn = (TextView) findViewById(R.id.hd_hse_common_readcard_sure_tv);
		readCardEdit = (EditText) findViewById(R.id.hd_hse_common_readcard_et);
		readCardTV = (TextView) findViewById(R.id.hd_hse_common_readcard_tv);
		readCardRL = (RelativeLayout) findViewById(R.id.hd_hse_common_readcardrl);
		readCardRL.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				readCardEdit.setText("测试数据");
				readCardEdit.setHint("ceshi");
			}
		});

		cancelBtn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		sureBtn.setOnClickListener(new SureClickListenerImpl());
	}

	private void initData() {
		if (spEntity != null) {
			readCardEdit.setText((CharSequence) spEntity
					.getAttribute("persondesc"));
			readCardEdit.setHint((CharSequence) spEntity
					.getAttribute("personid"));
			readCardTV.setText((CharSequence) spEntity
					.getAttribute("spfield_desc"));
		}
	}

	/**
	 * 设置实体类
	 * 
	 * @param spEntity
	 */
	public void setSpEntity(T spEntity) {
		this.spEntity = spEntity;
		readCardEdit.setHint((CharSequence) spEntity.getAttribute("personid"));
		readCardEdit
				.setText((CharSequence) spEntity.getAttribute("persondesc"));
	}

	/**
	 * 获得实体
	 * 
	 * @return
	 */
	public T getSpEntity() {
		return spEntity;
	}

	/**
	 * 添加监听事件
	 * 
	 * @param iEventLsn
	 */
	public void setiEventLsn(IEventListener iEventLsn) {
		this.iEventLsn = iEventLsn;
	}

	class SureClickListenerImpl implements android.view.View.OnClickListener {
		public void onClick(View v) {
			if (dialog != null) {
				if (iEventLsn != null && readCardEdit.getHint() != null) {
					try {
						spEntity.setAttribute("personid",
								(CharSequence) readCardEdit.getHint());
						spEntity.setAttribute("persondesc", readCardEdit
								.getText().toString());
						iEventLsn.eventProcess(
								IEventType.EXAMINE_EXAMINE_ClICK, spEntity,
								dialog);
					} catch (HDException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}
}
