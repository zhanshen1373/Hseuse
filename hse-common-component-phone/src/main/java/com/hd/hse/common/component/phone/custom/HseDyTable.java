package com.hd.hse.common.component.phone.custom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.entity.SuperEntity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * ClassName: HseDyTable (导航栏标题点击弹出列表)<br/>
 * date: 2014年12月30日 <br/>
 * 
 * @author wenlin
 * @version
 */
@SuppressLint("NewApi")
public class HseDyTable extends LinearLayout {

	private Context context;
	private LayoutInflater inflate;
	private TableLayout table;
	private Map<String, String> numDescMap;
	private TextView numTv;

	private List<TextView> poolTextView = new ArrayList<TextView>();

	public HseDyTable(Context context) {
		super(context);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public HseDyTable(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	public HseDyTable(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	/**
	 * initView:(). <br/>
	 * date: 2014年12月30日 <br/>
	 * 
	 * @author wenlin
	 */
	private void initView() {
		// TODO Auto-generated method stub
		inflate = LayoutInflater.from(context);
		View view = inflate.inflate(
				R.layout.hd_hse_commom_component_phone_actionbar_dytable,
				this, true);
		table = (TableLayout) view
				.findViewById(R.id.hd_hse_common_component_phone_actionbar_dytable);

	}

	public void initTabRow(SuperEntity superEntity) {
		initData();
		if (numDescMap == null)
			return;
		addRowCol(numDescMap, superEntity);
	}

	/**
	 * initData:(初始化). <br/>
	 * date: 2014年12月30日 <br/>
	 * 
	 * @author wenlin
	 */
	private void initData() {
		// TODO Auto-generated method stub
		String[] arrayDesc = context.getResources().getStringArray(
				R.array.hd_hse_common_component_phone_dytable_workorder_desc);
		String[] arrayNum = context.getResources().getStringArray(
				R.array.hd_hse_common_component_phone_dytable_workorder_num);
		numDescMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < arrayNum.length; i++) {
			numDescMap.put(arrayDesc[i], arrayNum[i]);
		}
	}

	/**
	 * addRowCol:(装载行列内容). <br/>
	 * date: 2014年12月30日 <br/>
	 * 
	 * @author wenlin
	 * @param numDescMap2
	 * @param superEntity
	 */
	private void addRowCol(Map<String, String> numDescMap,
			SuperEntity superEntity) {
		// TODO Auto-generated method stub
		TableRow tr = null;
		View lineView = null;
		TextView descTv = null;
		numTv = null;
		table.removeAllViews();
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams relaParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, 4);
		for (String key : numDescMap.keySet()) {
			if (tr != null && tr.getParent() == null) {
				table.addView(tr, params);
				if (lineView != null && superEntity.getAttribute(numDescMap.get(key)) != null) {
					// 画线
					table.addView(lineView, relaParams);
				}
			}
			tr = new TableRow(context);
			tr.setLayoutParams(params);

			lineView = new View(context);
			lineView.setLayoutParams(relaParams);
			lineView.setBackgroundResource(R.drawable.hd_component_step_check_divider_dash_line);
			if (superEntity != null) {
				if (superEntity.getAttribute(numDescMap.get(key)) != null) {
					descTv = creatDescTv();
					numTv = creatNumTv(key);
					descTv.setText(key);
					numTv.setText(superEntity.getAttribute(numDescMap.get(key))
							.toString());
					poolTextView.add(numTv);
					tr.addView(descTv);
					tr.addView(numTv);
				}
			} else {
				descTv = creatDescTv();
				numTv = creatNumTv(key);
				descTv.setText(key);
				numTv.setText(numDescMap.get(key));
				tr.addView(descTv);
				tr.addView(numTv);
			}
		}
		if (tr != null) {
			table.addView(tr, params);
			if (lineView != null) {
				table.addView(lineView, relaParams);
			}
		}
	}

	/**
	 * TODO 内容显示
	 * splitWorkName:(). <br/>
	 * date: 2015年2月12日 <br/>
	 *
	 * @author wenlin
	 * @param src
	 * @return
	 */
	public String splitWorkName(String src) {
		StringBuffer _result = new StringBuffer(20);
		boolean isn = false;
		if (src != null && !src.isEmpty()) {
			char[] _charArray = src.toCharArray();

			int len = _charArray.length;

			for (int i = 0; i < len; i++) {
				_result.append(_charArray[i]);
				// 内容超过50字节换行
				if (_result.toString().getBytes().length > 45 && _result.toString().getBytes().length < 100 && !isn) {
					isn = true;
					_result.append(System.getProperty("line.separator"));
					numTv.setHeight(85);
				}else if(_result.toString().getBytes().length > 100 ){
					// 内容超过100字节后内容用省略号代替
					_result.deleteCharAt(_result.length() -1 );
					_result.append("...");
				}
			}
		}
		return _result.toString();
	}
	
	
	/**
	 * creatNumTv:(创建table的字段值控件). <br/>
	 * date: 2014年12月30日 <br/>
	 * 
	 * @author wenlin
	 * @return
	 */
	private TextView creatNumTv(String key) {
		TextView numTv = new TextView(context);
		numTv.setPadding(0, (int)context.getResources().getDimension(R.dimen.hd_common_component_phone_actionbar_control_padding), 0, (int)context.getResources().getDimension(R.dimen.hd_common_component_phone_actionbar_control_padding));
		numTv.setTag(numDescMap.get(key));
		numTv.setTextColor(context.getResources().getColor(
				R.color.hd_hse_common_component_phone_fontnum));
		numTv.setTextSize(context.getResources().getDimension(
				R.dimen.hd_common_phone_textsize));
		numTv.getPaint().setFakeBoldText(false);
		numTv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		return numTv;
	}

	/**
	 * creatDescTv:(创建table的字段名称控件). <br/>
	 * date: 2014年12月30日 <br/>
	 * 
	 * @author wenlin
	 * @return
	 */
	private TextView creatDescTv() {
		TextView descTv = new TextView(context);
		descTv.setTextColor(context.getResources().getColor(
				R.color.hd_hse_common_component_phone_fontnum));
		descTv.setTextSize(context.getResources().getDimension(
				R.dimen.hd_common_phone_textsize));
		descTv.setPadding(0, 0, (int)context.getResources().getDimension(R.dimen.hd_common_component_phone_actionbar_textview_padding), 0);
		descTv.getPaint().setFakeBoldText(false);
		descTv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		return descTv;
	}

	/**
	 * refresh:(加载完成后，对界面中的控件数值进行更新). <br/>
	 * date: 2015年1月4日 <br/>
	 * 
	 * @author wenlin
	 * @param superEntity
	 */
	public void refresh(SuperEntity superEntity) {
		if (superEntity != null) {
			for (TextView tv : poolTextView) {
				tv.setText(superEntity.getAttribute(tv.getTag().toString()) == null ? ""
						: superEntity.getAttribute(tv.getTag().toString())
								.toString());
			}
		}
	}
}
