package com.hd.hse.pc.phone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ImageLoader;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PhotoPreviewFromNetActivity;
import com.hd.hse.dc.business.common.weblistener.down.GainCertificateImgURLs;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.pc.CheckPersonRecords;
import com.hd.hse.pc.phone.R;
import com.hd.hse.pc.phone.adapter.PersonCertificateRecordAdapter;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 人员证书记录Activity Created by liuyang on 2016年9月26日
 */
public class PersonCertificateRecordActivity extends BaseFrameActivity
		implements IEventListener {
	private Logger logger = LogUtils
			.getLogger(PersonCertificateRecordActivity.class);
	private List<PersonSpecialTypeWork> mSpecialTypeWorks; // 证书列表
	private PersonCard mCard;
	private ListView lv;
	private PersonCertificateRecordAdapter mPersonCertificateRecordAdapter;
	private ImageLoader mImageLoader;
	/**
	 * 图片名
	 */
	private String[] names;
	/**
	 * 图片网址
	 */
	private String[] urls;
	private GridView mGridView;
	private GridPhotoAdapter mGridPhotoAdapter;
	private BusinessAction action;
	private BusinessAsyncTask asyncTask;
	private GainCertificateImgURLs mGainCertificateImgURLs;
	private String iPAndPort;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hse_pc_phone_app_layout_certificate);
		initActionbar();
		initView();
		initData();

	}
	
	private void initView() {
		lv = (ListView) findViewById(R.id.hse_pc_phone_app_layout_certificate_lv);
		mGridView =(GridView) findViewById(R.id.hse_pc_phone_app_layout_certificate_gv);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent i = new Intent(PersonCertificateRecordActivity.this,
						PhotoPreviewFromNetActivity.class);

				Bundle bundle = new Bundle();
				bundle.putSerializable(PhotoPreviewFromNetActivity.IMAGESET,
						urls);
				bundle.putInt(PhotoPreviewFromNetActivity.SELECTEDINDEX, arg2);
				i.putExtras(bundle);
				PersonCertificateRecordActivity.this.startActivity(i);
			}
		});
	}

	private void initActionbar() {
		// 初始化ActionBar
		setCustomActionBar(false, this, new String[] { IActionBar.TV_BACK,
				IActionBar.TV_TITLE });
		// 设置导航栏标题
		setActionBartitleContent("证书记录", false);
	}

	private void initData() {
		QueryWorkInfo queryWorkInfo = new QueryWorkInfo();
		try {
			iPAndPort = "http://" + queryWorkInfo.queryIPAndPort(null)+"/";
		} catch (HDException e) {
			e.printStackTrace();
		}
		
		mImageLoader=new ImageLoader();
		mCard = (PersonCard) getIntent().getSerializableExtra(
				PersonCard.class.getName());

		try {
			mSpecialTypeWorks = CheckPersonRecords.getInstance()
					.checkPersonCertificateRecords(mCard.getPersonid());
				mPersonCertificateRecordAdapter = new PersonCertificateRecordAdapter(
						this, mSpecialTypeWorks);
				lv.setAdapter(mPersonCertificateRecordAdapter);
				
		} catch (HDException e) {

			logger.error(e);
			ToastUtils.imgToast(PersonCertificateRecordActivity.this,
					R.drawable.hd_hse_common_msg_wrong, e.getMessage());
		}
		
		
		/*if (null == mGainCertificateImgURLs) {
			mGainCertificateImgURLs = new GainCertificateImgURLs(mCard.getPersonid());
		}
		if (null == action) {
			action = new BusinessAction(mGainCertificateImgURLs);
		}
		if (asyncTask == null) {
			asyncTask = new BusinessAsyncTask(action,
					new AbstractAsyncCallBack() {

						@Override
						public void start(Bundle msgData) {
							// TODO Auto-generated method stub
						}

						@Override
						public void processing(Bundle msgData) {

						}

						@Override
						public void error(Bundle msgData) {
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							ToastUtils.imgToast(
									PersonCertificateRecordActivity.this,
									R.drawable.hd_hse_common_msg_wrong,
									msg.getMessage().contains("获取超时") ? msg
											.getMessage() : "获取图片网址错误！请联系管理员");

						}

						@Override
						public void end(Bundle msgData) {
							// TODO Auto-generated method stub
							AysncTaskMessage msg = (AysncTaskMessage) msgData
									.getSerializable("p");
							 Map<String, String[]> retStr=(Map<String, String[]>) msg.getReturnResult();
							 if (null!=retStr) {
								names=retStr.get(PadInterfaceRequest.KEY_NAME);
								urls=retStr.get(PadInterfaceRequest.KEY_URL);
								for (int i = 0; i < urls.length; i++) {
									String tmp[]=urls[i].split("/");
									urls[i]="";
									for (int j = 0; j < tmp.length; j++) {
										if (isContainChinese(tmp[j])) {
											try {
												tmp[j]=URLEncoder.encode(tmp[j], "utf-8");
											} catch (UnsupportedEncodingException e) {
												e.printStackTrace();
											}
										}
										if (j==0) {
											urls[i]+=tmp[j];
										}else {
											urls[i]+=("/"+tmp[j]);
										}
										
									}
									Log.e("url", urls[i]);
								}
								
								//得到图片网址后
								mGridPhotoAdapter=new GridPhotoAdapter();
								mGridView.setAdapter(mGridPhotoAdapter);
							}

						}
					});
			try {

				asyncTask.execute(null, "");

			} catch (HDException e) {

			}
		
		}	*/
		
		
	}
	/**
	 * 判断字符串是否为中文
	 * @param str
	 * @return
	 */
	public  boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

	@Override
	public void eventProcess(int eventType, Object... objects)
			throws HDException {
		// TODO Auto-generated method stub

	}
	
	
	
	public class GridPhotoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return urls.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return urls[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public GridPhotoAdapter() {
			// mTimer=new
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			ViewHolder vh = null;
			if (arg1 == null) {
				vh = new ViewHolder();
				arg1 = LayoutInflater.from(PersonCertificateRecordActivity.this).inflate(
						R.layout.hse_pc_phone_app_layout_certificate_img_item, null);
				vh.img = (ImageView) arg1
						.findViewById(R.id.hse_pc_phone_app_layout_certificate_img_item_top_iv);
				vh.tv = (TextView) arg1
						.findViewById(R.id.hse_pc_phone_app_layout_certificate_img_item_tv);
				arg1.setTag(vh);
			} else {
				vh = (ViewHolder) arg1.getTag();
			}

			vh.img.setImageResource(R.drawable.hd_hse_common_component_phone_default_photo);
			mImageLoader.setBg(urls[arg0], vh.img);
			

			vh.tv.setText(names[arg0]);
			return arg1;

		}

		public class ViewHolder {
			private ImageView img;
			private TextView tv;
		}

	}
}
