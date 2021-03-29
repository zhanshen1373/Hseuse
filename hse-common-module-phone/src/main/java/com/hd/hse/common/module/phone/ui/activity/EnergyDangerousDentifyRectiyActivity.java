package com.hd.hse.common.module.phone.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.hd.hse.common.component.phone.adapter.ListViewWithCheckBoxAdapter;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * 危害识别界面
 * 
 * Created by wangdanfeng on 2016年2月29日
 */
public class EnergyDangerousDentifyRectiyActivity extends BaseFrameActivity
		implements OnClickListener, OnItemClickListener, IEventListener {
	public static String REQUEST_CODE = "dangerousrectify";
	private QueryWorkInfo queryWorkInfo;
	private List<Domain> whsbList; // 危害识别项列表数据
	private List<Domain> selectedwhsb = new ArrayList<>();// 选中的危害识别
	private String selectedwhsball;
	private ListView listviewwhsb;// 显示危害识别项列表
	private ListViewWithCheckBoxAdapter listviewadapter;
	private Button buttonsave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_energy_whsb_rectify);
		// 初始化ActionBar
		setCustomActionBar(false, this, setActionBarItems(), true);
		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);
		initData();
		listviewwhsb = (ListView) findViewById(R.id.listview_energy_dangerous);
		buttonsave = (Button) findViewById(R.id.save_btn_whsb);
		listviewadapter = new ListViewWithCheckBoxAdapter(
				getApplicationContext(), whsbList, selectedwhsb, "");
		listviewwhsb.setAdapter(listviewadapter);
		listviewwhsb.setOnItemClickListener(this);
		buttonsave.setOnClickListener(this);
	}

	public void initData() {

		queryWorkInfo = new QueryWorkInfo();
		try {
			whsbList = queryWorkInfo.queryDomain("UDHARM", null);
		} catch (HDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedwhsball = getIntent().getStringExtra(REQUEST_CODE);
		if (selectedwhsball != null && !selectedwhsball.equals("")) {
			String[] whsbselecteditems = selectedwhsball.split(",");
			if (whsbselecteditems != null) {
				for (String s : whsbselecteditems) {
					for (Domain d : whsbList) {
						if (s.equals(d.getValue())) {
							selectedwhsb.add(d);
						}
					}
				}
			}
		}

	}

	private String[] setActionBarItems() {
		return new String[] { IActionBar.TV_BACK, IActionBar.TV_TITLE };
	}

	private String getTitileName() {
		return getIntent().getStringExtra("title");
	}

	@Override
	public void onClick(View arg0) {
		selectedwhsb = listviewadapter.getSelectedDatas();
		String s = "";
		if (selectedwhsb != null && selectedwhsb.size() != 0) {

			for (Domain items : selectedwhsb) {
				s += s.equals("") ? items.getValue() : ("," + items.getValue());
			}
			Intent intent = new Intent();
			intent.putExtra(REQUEST_CODE, s);
			setResult(RESULT_OK, intent);
			finish();
		} else {
			ToastUtils.imgToast(EnergyDangerousDentifyRectiyActivity.this,
					R.drawable.hd_hse_common_msg_wrong, "请至少选择一项危害识别！");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.checkbox);
		if (checkBox.isChecked()) {
			checkBox.setChecked(false);
		} else {
			checkBox.setChecked(true);
		}

	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {
		//
		// AlertDialog.Builder builder= new AlertDialog.Builder(this);
		// builder.setTitle("提示");
		// builder.setMessage("确定退出当前界面吗？");
		// builder.setNegativeButton("确定", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		// finish();
		// arg0.dismiss();
		//
		// }
		// });
		//
		// builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface arg0, int arg1) {
		//
		// arg0.dismiss();
		// }
		// });
		//
		// builder.create();
		// builder.show();
	}
}
