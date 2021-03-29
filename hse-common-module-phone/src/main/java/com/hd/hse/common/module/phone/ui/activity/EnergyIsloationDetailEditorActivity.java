package com.hd.hse.common.module.phone.ui.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hd.hse.common.component.phone.adapter.ListViewWithCheckBoxAdapter;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.EnergyIsolationDetail;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * 能量隔离详情编辑界面 用来新增和修改能量隔离详情
 * 
 * 用来修改时请传入待修改的能量隔离详情对象,code为DATA_CODE
 * 
 * Created by liuyang on 2016年2月19日
 */
public class EnergyIsloationDetailEditorActivity extends BaseFrameActivity implements IEventListener{
	private static Logger logger = LogUtils
			.getLogger(EnergyIsloationDetailEditorActivity.class);
	public static final String DATA_CODE = "data";
	private List<Domain> glffList; // 待选的隔离方法
	private List<Domain> selectedGLFFs; // 选中的隔离方法
	private EnergyIsolationDetail data;
	private EditText nlwlEdit, ssgpdEdit, gpdEdit;
	private ListView glffListView;
	private Button saveBtn;
	private ListViewWithCheckBoxAdapter adapter;
	private String qt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.hd_hse_energy_isloation_detail_editor_activity);
		// 初始化ActionBar
		setCustomActionBar(false, this, setActionBarItems(), true);
		// 设置导航栏标题
		setActionBartitleContent(getTitileName(), false);
		findViewById();
		findGlffList();
		data = (EnergyIsolationDetail) getIntent().getSerializableExtra(
				DATA_CODE);
		if (data != null) {
			setTitle("编辑当前");
			nlwlEdit.setText(data.getNlwl());
			ssgpdEdit.setText(data.getSsgpd());
			gpdEdit.setText(data.getGpd());
			qt = data.getNlglqt();
			// 获取已选择的隔离方法列表
			selectedGLFFs = new ArrayList<>();
			String glffStr = data.getGlff();
			// 防止隔离方法为空时崩溃
			if (glffStr == null) {
				glffStr = "";
			}
			String[] glffs = glffStr.split(",");
			for (String s : glffs) {
				for (Domain d : glffList) {
					if (s.equals(d.getValue())) {
						selectedGLFFs.add(d);
					}
				}
			}
		} else {
			data = new EnergyIsolationDetail();
			qt = "";
			setTitle("新增序号");
		}
		adapter = new ListViewWithCheckBoxAdapter(
				EnergyIsloationDetailEditorActivity.this, glffList,
				selectedGLFFs, qt);
		glffListView.setAdapter(adapter);
	
		saveBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 保存物料/能量
				if (nlwlEdit.getText() != null
						&& !nlwlEdit.getText().toString().equals("")) {
					data.setNlwl(nlwlEdit.getText().toString());
				} else {
					ToastUtils.imgToast(
							EnergyIsloationDetailEditorActivity.this,
							R.drawable.hd_hse_common_msg_wrong, "请输入物料/能量!");
					return;
				}
				// 保存上锁挂牌点
				data.setSsgpd(ssgpdEdit.getText().toString());
				// 保存挂牌点
				if (gpdEdit.getText() != null
						&& !gpdEdit.getText().toString().equals("")) {
					data.setGpd(gpdEdit.getText().toString());
				} else {
					ToastUtils.imgToast(
							EnergyIsloationDetailEditorActivity.this,
							R.drawable.hd_hse_common_msg_wrong, "请输入挂牌点!");
					return;
				}
				// 保存隔离方法
				selectedGLFFs = adapter.getSelectedDatas();
				if (selectedGLFFs != null && selectedGLFFs.size() != 0) {
					String glff = "";
					for (Domain d : selectedGLFFs) {
						glff += glff.equals("") ? d.getValue() : ("," + d
								.getValue());
					}
					data.setGlff(glff);
				} else {
					ToastUtils.imgToast(
							EnergyIsloationDetailEditorActivity.this,
							R.drawable.hd_hse_common_msg_wrong, "请至少选择一项隔离方法!");
					return;
				}
				// 保存隔离方法  其他
				String qt = adapter.getOtherString();
				data.setNlglqt(qt);
				Intent intent = new Intent();
				intent.putExtra(DATA_CODE, data);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	private void findGlffList() {
		try {
			glffList = new QueryWorkInfo().queryDomain("NLGLFF", null);
		} catch (HDException e) {
			logger.error(e);
			ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
					e.getMessage());
		}
	}

	private void findViewById() {
		nlwlEdit = (EditText) findViewById(R.id.nlwl_edit);
		ssgpdEdit = (EditText) findViewById(R.id.ssgpd_edit);
		gpdEdit = (EditText) findViewById(R.id.gpd_edit);
		glffListView = (ListView) findViewById(R.id.glff_list);
		saveBtn = (Button) findViewById(R.id.save_btn);
//		listViewListener();
		
	}
	/**
	 * 对ListView列表项进行监听
	 * 
	 * Created by wangdanfeng on 2016年3月3日
	 */
//	private void  listViewListener() {
//		glffListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//
//				CheckBox checkBox=(CheckBox) arg1.findViewById(R.id.checkbox);
//				TextView textView=(TextView) arg1.findViewById(R.id.textview);
//			
//				if(checkBox.isChecked()){
//					checkBox.setChecked(false);
//				} else {
//					checkBox.setChecked(true);
//				}
//			
//			}
//		});
//		
//		glffListView.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				CheckBox checkBox=(CheckBox) arg1.findViewById(R.id.checkbox);
//				TextView textView=(TextView) arg1.findViewById(R.id.textview);
//				ToastUtils.toast(getApplicationContext(), textView.getText().toString());
//				
//				if(glffList.get(arg2).getValue().equals("QT")){
//					ToastUtils.toast(getApplicationContext(), textView.getText().toString());
//					View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.hd_hse_commmon_component_glff_alerdialog_other, null);
//					final EditText et=(EditText) view.findViewById(R.id.edittext_other);
//					AlertDialog.Builder builder=new AlertDialog.Builder(mActivity);
//					builder.setView(view);
//				
//					builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface arg0, int arg1) {
//							
//						}
//					});
//					builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface arg0, int arg1) {
//							ToastUtils.toast(getApplicationContext(), et.getText().toString());
//							
//						}
//					});
//					builder.create().show();
//				} 
//				return false;
//			}
//		});
//		
//		
//	}
	
	
	private String[] setActionBarItems() {
		return new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE};
	}
	private String getTitileName() {
		return getIntent().getStringExtra("title");
	}

	@Override
	public void eventProcess(int arg0, Object... arg1) throws HDException {
		
	}

}
