package com.hd.hse.common.component.phone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.entity.base.Domain;

/**
 * 带有复选框的ListView适配器
 * 
 * Created by liuyang on 2016年2月22日
 */
public class ListViewWithCheckBoxAdapter extends BaseAdapter {
	private Context context;
	private List<Domain> datas;
	private List<Domain> selectedDatas;
	private String otherString;

	public ListViewWithCheckBoxAdapter(Context context, List<Domain> datas,
			List<Domain> selectedDatas, String qt) {
		this.context = context;
		this.datas = datas;
		this.selectedDatas = selectedDatas;
		if (this.selectedDatas == null) {
			this.selectedDatas = new ArrayList<>();
		}
		this.otherString = qt;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	//
	// @Override
	// public View getView(final int position, View contentView,
	// ViewGroup viewGroup) {
	// ViewHolder holder = null;
	//
	// if (contentView == null) {
	// contentView = LayoutInflater.from(context).inflate(
	// R.layout.hd_hse_listview_with_checkbox_item, viewGroup,
	// false);
	// holder = new ViewHolder();
	// View layout = contentView.findViewById(R.id.layout);
	// final ImageView checkBox = (ImageView) contentView
	// .findViewById(R.id.checkbox);
	// final EditText editexText = (EditText) contentView
	// .findViewById(R.id.edittext);
	// layout.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// Toast.makeText(context, position + " / " +
	// datas.get(position).getDescription(), Toast.LENGTH_SHORT).show();
	// if (checkBox.getVisibility() == View.GONE) {
	// checkBox.setVisibility(View.VISIBLE);
	// selectedDatas.add(datas.get(position));
	// if (datas.get(position).getValue().equals("QT")) {
	// editexText.setVisibility(View.VISIBLE);
	// } else {
	// editexText.setVisibility(View.GONE);
	// }
	// } else {
	// checkBox.setVisibility(View.GONE);
	// selectedDatas.remove(datas.get(position));
	// editexText.setVisibility(View.GONE);
	// }
	// }
	// });
	// // checkBox.setOnClickListener(new View.OnClickListener() {
	// //
	// // @Override
	// // public void onClick(View arg0) {
	// // if (checkBox.isChecked()) {
	// //
	// // selectedDatas.add(datas.get(position));
	// // if (datas.get(position).getValue().equals("QT")) {
	// // editexText.setVisibility(View.VISIBLE);
	// // editexText
	// // .addTextChangedListener(new TextWatcher() {
	// //
	// // @Override
	// // public void onTextChanged(
	// // CharSequence arg0, int arg1,
	// // int arg2, int arg3) {
	// //
	// // }
	// //
	// // @Override
	// // public void beforeTextChanged(
	// // CharSequence arg0, int arg1,
	// // int arg2, int arg3) {
	// //
	// // }
	// //
	// // @Override
	// // public void afterTextChanged(
	// // Editable arg0) {
	// // // TODO Auto-generated method stub
	// // otherString = arg0.toString();
	// // Log.e("其他项备注", otherString);
	// //
	// // }
	// // });
	// // }
	// // } else {
	// // editexText.setVisibility(View.GONE);
	// // editexText.setText("");
	// // selectedDatas.remove(datas.get(position));
	// // otherString = "";
	// // }
	// // }
	// // });
	// holder.layout = layout;
	// holder.checkBox = checkBox;
	//
	// holder.textView = (TextView) contentView
	// .findViewById(R.id.textview);
	// holder.editText = editexText;
	//
	// contentView.setTag(holder);
	// } else {
	// holder = (ViewHolder) contentView.getTag();
	// }
	// holder.textView.setText(datas.get(position).getDescription());
	//
	// boolean isSelect = false;
	// for (Domain domain : selectedDatas) {
	// if (domain.getValue().equals(datas.get(position).getValue())) {
	// isSelect = true;
	//
	// break;
	// }
	// }
	// if (isSelect) {
	// holder.checkBox.setVisibility(View.VISIBLE);
	// } else {
	// holder.checkBox.setVisibility(View.GONE);
	// }
	// if (isSelect && datas.get(position).getValue().equals("QT")) {
	// holder.editText.setVisibility(View.VISIBLE);
	// holder.editText.setText(otherString);
	// } else {
	// holder.editText.setVisibility(View.GONE);
	// }
	// return contentView;
	// }

	@Override
	public View getView(final int arg0, View arg1, ViewGroup viewGroup) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.hd_hse_listview_with_checkbox_item, viewGroup, false);
		View layout = view.findViewById(R.id.layout);
		final ImageView checkBox = (ImageView) view.findViewById(R.id.checkbox);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (checkBox.getVisibility() == View.GONE) {
					checkBox.setVisibility(View.VISIBLE);
					selectedDatas.add(datas.get(arg0));
					if (datas.get(arg0).getValue().equals("QT")) {
						showDialog(arg0);
					}
				} else {
					checkBox.setVisibility(View.GONE);
					selectedDatas.remove(datas.get(arg0));
					if (datas.get(arg0).getValue().equals("QT")) {
						otherString = "";
						notifyDataSetChanged();
					}
				}
			}
		});
		textView.setText(datas.get(arg0).getDescription());
		boolean isSelect = false;
		for (Domain domain : selectedDatas) {
			if (domain.getValue().equals(datas.get(arg0).getValue())) {
				isSelect = true;
				break;
			}
		}
		if (isSelect) {
			checkBox.setVisibility(View.VISIBLE);
			if (datas.get(arg0).getValue().equals("QT")) {
				textView.setText(datas.get(arg0).getDescription() + otherString);
			}
		} else {
			checkBox.setVisibility(View.GONE);
		}
		return view;
	}

	/**
	 * 获取选中的项
	 * 
	 * Created by liuyang on 2016年2月22日
	 * 
	 * @return
	 */
	public List<Domain> getSelectedDatas() {
		// 当文本有内容的时候进行添加
		// if (otherString!=null && otherString.trim()!="") {
		// for (Domain domain : selectedDatas) {
		// if(domain.getValue().equals("QT")){
		// domain.setDescription(domain.getDescription()+otherString);
		//
		// }
		// }
		// }
		return this.selectedDatas;
	}

	// 获取其他项备注
	public String getOtherString() {
		return otherString;
	}

	public void setOtherString(String otherString) {
		this.otherString = otherString;
	}

	private class ViewHolder {
		View layout;
		ImageView checkBox;
		TextView textView;
		EditText editText;
	}

	private void showDialog(final int position) {
		final EditText inputServer = new EditText(context);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("请输入其他的隔离方法").setView(inputServer)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						selectedDatas.remove(datas.get(position));
						notifyDataSetChanged();
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				otherString = inputServer.getText().toString();
				notifyDataSetChanged();
			}
		});
		builder.show();
	}

}
