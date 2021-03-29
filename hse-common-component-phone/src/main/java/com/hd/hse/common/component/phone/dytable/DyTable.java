package com.hd.hse.common.component.phone.dytable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.entity.SuperEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * ClassName: DyTable (动态列表)<br/>
 * date: 2014年9月4日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
@SuppressLint("Recycle")
public class DyTable extends LinearLayout {

	private Context context;
	private LayoutInflater inflate;
	private TableLayout table;
	private TextView descTv;
	private TextView numTv;

	/**
	 * poolTextView:TODO(将界面中显示数值的空间存入缓存中，用于刷新数据使用).
	 */
	private List<TextView> poolTextView = new ArrayList<TextView>();

	/**
	 * Creates a new instance of DyTable.
	 * 
	 * @param context
	 */
	public DyTable(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
//		initData();
		initView();
		// addTabRow();
	}

	/**
	 * Creates a new instance of DyTable.
	 * 
	 * @param context
	 * @param attrs
	 */
	public DyTable(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initAttrs(attrs);
//		initData();
		initView();
		// addTabRow();
	}

	/**
	 * rowCout:TODO(设置显示的列数).
	 */
	private int rowCout = 2;
	private int addCount = 0;
	/**
	 * mapSize:TODO(记录主表的对数).
	 */
	private int mapSize = 0;

	public int getRowCout() {
		return rowCout;
	}

	public void setRowCout(int rowCout) {
		this.rowCout = rowCout;
	}

	/**
	 * arrayNum:TODO(对应的数组编码).
	 */
	private int arrayNum = 0;

	public int getArrayNum() {
		return arrayNum;
	}

	public void setArrayNum(int arrayNum) {
		this.arrayNum = arrayNum;
	}

	/**
	 * arrayDesc:TODO(对应的数组描述).
	 */
	private int arrayDesc = 0;

	public int getArrayDesc() {
		return arrayDesc;
	}

	public void setArrayDesc(int arrayDesc) {
		this.arrayDesc = arrayDesc;
	}

	/**
	 * textDescSize:TODO(设置描述字体大小).
	 */
	private int textDescSize = 0;

	public int getTextDescSize() {
		return textDescSize;
	}

	public void setTextDescSize(int textDescSize) {
		this.textDescSize = textDescSize;
	}

	/**
	 * textValueSize:TODO(设置显示的值字体大小).
	 */
	private int textValueSize = 0;

	public int getTextValueSize() {
		return textValueSize;
	}

	public void setTextValueSize(int textValueSize) {
		this.textValueSize = textValueSize;
	}

	/**
	 * textDescColor:TODO(设置描述字体的颜色).
	 */
	private int textDescColor = 0;

	public int getTextDescColor() {
		return textDescColor;
	}

	public void setTextDescColor(int textDescColor) {
		this.textDescColor = textDescColor;
	}

	/**
	 * textValueColor:TODO(设置具体值字体的颜色).
	 */
	private int textValueColor = 0;

	public int getTextValueColor() {
		return textValueColor;
	}

	public void setTextValueColor(int textValueColor) {
		this.textValueColor = textValueColor;
	}

	/**
	 * itemHeight:TODO(每一项的高度).
	 */
	private int itemHeight = 0;

	public int getItemHeight() {
		return itemHeight;
	}

	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}

	/**
	 * isLines:TODO(是否显示分割线).
	 */
	private boolean isLines = true;

	public boolean isLines() {
		return isLines;
	}

	public void setLines(boolean isLines) {
		this.isLines = isLines;
	}

	/**
	 * childInfoNum:TODO(字表编码对应的资源文件ID).
	 */
	private int childInfoNum = 0;

	public int getChildInfoNum() {
		return childInfoNum;
	}

	public void setChildInfoNum(int childInfoNum) {
		this.childInfoNum = childInfoNum;
	}

	/**
	 * childInfoDesc:TODO(字表描述对应的资源文件ID).
	 */
	private int childInfoDesc = 0;

	public int getChildInfoDesc() {
		return childInfoDesc;
	}

	public void setChildInfoDesc(int childInfoDesc) {
		this.childInfoDesc = childInfoDesc;
	}

	/**
	 * childEntityClassName:TODO(指定实体对应的子类的className).
	 */
	private String[] childEntityClassName = {};

	public final String[] getChildEntityClassName() {
		return childEntityClassName;
	}

	public final void setChildEntityClassName(String[] childEntityClassName) {
		this.childEntityClassName = childEntityClassName;
	}

	/**
	 * map:TODO(编码+描述).
	 */
	private Map<String, String> map;
	private Map<String, String> childMap;
	
	/**
	 * initAttrs:(初始化获取xml文件中配置的属性值). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author zhaofeng
	 * @param attrs
	 */
	private void initAttrs(AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.DyTable);
		rowCout = ta.getInt(R.styleable.DyTable_rowCout, 2);
		arrayNum = ta.getResourceId(R.styleable.DyTable_arrayNum, 0);
		arrayDesc = ta.getResourceId(R.styleable.DyTable_arrayDesc, 0);
		textDescSize = ta.getResourceId(R.styleable.DyTable_textDescSize,
				R.dimen.text_size_xlarge);
		textValueSize = ta.getResourceId(R.styleable.DyTable_textValueSize,
				R.dimen.text_size_xlarge);
		textDescColor = ta.getColor(R.styleable.DyTable_textDescColor,
				android.R.color.black);
		textValueColor = ta.getColor(R.styleable.DyTable_textValueColor,
				android.R.color.black);
		itemHeight = ta.getInt(R.styleable.DyTable_itemHeight, 60);
		isLines = ta.getBoolean(R.styleable.DyTable_isLines, true);

		childInfoNum = ta.getResourceId(R.styleable.DyTable_childInfoNum, 0);
		childInfoDesc = ta.getResourceId(R.styleable.DyTable_childInfoDesc, 0);

	}

	/**
	 * initData:(初始化数据). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void initData() {
		if (arrayNum == 0 || arrayDesc == 0)
			return;
		map = new LinkedHashMap<String, String>();
		String[] descArray = context.getResources().getStringArray(arrayDesc);
		String[] numArray = context.getResources().getStringArray(arrayNum);
		int current=descArray.length>numArray.length?numArray.length:descArray.length;
		for (int i = 0; i < current; i++) {
			map.put(descArray[i], numArray[i]);
		}
		mapSize = map.size();
		if (childInfoNum == 0 || childInfoDesc == 0)
			return;
		childMap = new LinkedHashMap<String, String>();
		String[] childDescArray = context.getResources().getStringArray(
				childInfoDesc);
		String[] childNumArray = context.getResources().getStringArray(
				childInfoNum);
		for (int i = 0; i < childNumArray.length; i++) {
			childMap.put(childDescArray[i], childNumArray[i]);
		}
	}

	/**
	 * organizationData:(组织数据). <br/>
	 * date: 2014年9月13日 <br/>
	 * 
	 * @author zhaofeng
	 * @param superEntity
	 */
	private void organizationData(SuperEntity superEntity) {
		if (childMap == null)
			return;
		String newKey = "";
		String newValue = "";
		for (String key : childMap.keySet()) {
			String value = childMap.get(key);
			if (value.contains("-")) {
				String[] numSplit = value.split("-");
				if (!"".equals(numSplit[0].toString())) {
					newKey = (String) superEntity.getAttribute(numSplit[0])
							+ key;
				}
				if (!"".equals(numSplit[1].toString())) {
					newValue = (String) superEntity.getAttribute(numSplit[1]);
				}

				map.put(newKey, newValue);
			}
		}
	}

	/**
	 * initView:(初始化控件). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author zhaofeng
	 */
	private void initView() {
		inflate = LayoutInflater.from(context);
		View view = inflate.inflate(
				R.layout.hd_hse_common_component_dytable_layout, this, true);
		table = (TableLayout) view
				.findViewById(R.id.hd_hse_common_component_dytable_layout_table);
	}

	/**
	 * addTabRow:(动态增加行和列). <br/>
	 * date: 2014年9月4日 <br/>
	 * 
	 * @author zhaofeng
	 */
	@SuppressLint({ "NewApi", "ResourceAsColor" })
	public void initTabRow(SuperEntity superEntity) {
		addCount = 0;
		initData();//初始化数据
		if (map == null)
			return;
		if (childMap != null) {
			List<SuperEntity> list = superEntity
					.getChild(childEntityClassName[0]);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					organizationData(list.get(i));
				}
			}

		}
		addRowCol(map, superEntity);
	}

	/**
	 * addRowCol:(增加行列). <br/>
	 * date: 2014年9月13日 <br/>
	 * 
	 * @author zhaofeng
	 * @param map
	 * @param superEntity
	 */
	private void addRowCol(Map<String, String> map, SuperEntity superEntity) {
		TableRow tr = null;
		View lineView = null;
		if(addCount == 0)
			table.removeAllViews();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		RelativeLayout.LayoutParams relaParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT, 4);
		for (String key : map.keySet()) {
			if (addCount % rowCout == 0) {
				if (tr != null && tr.getParent() == null) {
					table.addView(tr, params);
					if (lineView != null) {
						table.addView(lineView, relaParams);
					}
				}
				tr = new TableRow(context);
				tr.setLayoutParams(params);
//				tr.setBackgroundColor(Color.RED);
				if (isLines) {
					lineView = new View(context);
					lineView.setLayoutParams(relaParams);
					lineView.setBackgroundResource(R.drawable.hd_hse_common_line);
				}
			}
			
			if (addCount < mapSize) {
				if (superEntity != null) {
					if(superEntity.getAttribute(map.get(key)) != null){
						descTv = createDescTV();
						numTv = createNumTV(key);
						descTv.setText(key);
						numTv.setText(superEntity.getAttribute(map.get(key)).toString());
						poolTextView.add(numTv);
						tr.addView(descTv);
						tr.addView(numTv);
						addCount++;
					}
				} else {
					numTv.setText(map.get(key));
				}
			} else {
				numTv.setText(map.get(key));
			}
			// numTv.setText("长度测试123456789123456789123456789");
		}
		if (tr != null) {
			table.addView(tr, params);
			if (lineView != null) {
				table.addView(lineView, relaParams);
			}
		}
	}

	/**
	 * TODO 创建table的字段名称
	 * createTextView:(). <br/>
	 * date: 2014年11月24日 <br/>
	 *
	 * @author wenlin
	 */
	public TextView createDescTV(){
		TextView descTv = new TextView(context);
		descTv.setTextColor(textDescColor);
		descTv.setTextSize(context.getResources()
				.getDimension(textDescSize));
		descTv.setHeight(itemHeight);
		descTv.getPaint().setFakeBoldText(true);
		descTv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		return descTv;
		
	}
	/**
	 * TODO 创建table的字段值
	 * createNumTV:(). <br/>
	 * date: 2014年11月24日 <br/>
	 *
	 * @author wenlin
	 * @param key
	 * @return
	 */
	public TextView createNumTV(String key){
		TextView numTv = new TextView(context);
		numTv.setPadding(0, 0, 80, 0);
		numTv.setTag(map.get(key));
		numTv.setTextColor(textValueColor);
		numTv.setTextSize(context.getResources()
				.getDimension(textValueSize));
		numTv.setHeight(itemHeight);
		numTv.getPaint().setFakeBoldText(true);
		numTv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		return numTv;
		
	}
	/**
	 * refresh:(加载完成后，对界面中的控件的数值进行更新). <br/>
	 * date: 2014年9月5日 <br/>
	 * 
	 * @author zhaofeng
	 * @param superEntry
	 */
	public void refresh(SuperEntity superEntry) {
		if (superEntry != null) {
			for (TextView tv : poolTextView) {
				tv.setText(superEntry.getAttribute(tv.getTag().toString()) == null ? ""
						: superEntry.getAttribute(tv.getTag().toString())
								.toString());
			}
		}
	}

}
