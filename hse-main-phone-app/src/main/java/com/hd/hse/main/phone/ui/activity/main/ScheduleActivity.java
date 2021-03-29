package com.hd.hse.main.phone.ui.activity.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.main.phone.R;

import java.util.Map;

public class ScheduleActivity extends NaviFrameActivity {

    // 拼接URL
    String url = null;
    BaseDao dao;
    public static final String CLICK_DEPT = "dept";
    public static final String ZYLX = "zylx";
    private String content;
    private String actionBar[];
    private WebView webView = null;
    // 错误提示页面
    private View errView;
    private TextView errMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        actionBar = new String[]{IActionBar.TV_BACK,
                 IActionBar.TV_TITLE};
//        IActionBar.IBTN_LEVELTWO_MORE,

        // 设置导航栏信息
        setCustomActionBar(iEventListener, actionBar);
        // 设置导航栏标题
        setActionBartitleContent(content, false);

        setCustomMenuBar(new String[]{IActionBar.ITEM_RECORD,});

    }

    private void init() {

        errView = View
                .inflate(this, R.layout.hd_hse_common_error_page, null);
        errMsg = (TextView) errView.findViewById(R.id.hd_hse_common_err_desc);
        errView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                refresh();
            }
        });


        Intent intent = getIntent();
        String dept = (String) intent.getSerializableExtra("dept");
        String zylx = (String) intent.getSerializableExtra("zylx");

        if ("zylx99".equals(zylx)) {
            content = "作业数量";
        } else if ("zylx01".equals(zylx)) {
            content = "动火";
        } else if ("zylx02".equals(zylx)) {
            content = "受限空间";
        } else if ("zylx04".equals(zylx)) {
            content = "高处";
        } else if ("zylx05".equals(zylx)) {
            content = "吊装";
        } else if ("zylx06".equals(zylx)) {
            content = "管线";
        }

        dao = new BaseDao();

        StringBuilder urlSbSql = new StringBuilder();

        urlSbSql.append("select (forcast.ip || lastdesc.name) as tableurl from");
        urlSbSql.append("(select ('http://' || sysurl || '/') as ip from hse_sys_config where syscode in ('inurl','outurl') and enable = 1) as forcast,");
        urlSbSql.append("(select  (description||'&zydept=")
                .append(dept).append("&zypclass=")
                .append(zylx).append("') as name from alndomain aln where aln.value = 'SYZYPLB' and domainid = 'UDRQURL') as lastdesc;");

        Map<String, Object> map = null;
        try {
            map = (Map) dao.executeQuery(
                    urlSbSql.toString(), new MapResult());
            if (map != null && map.size() > 0) {
                url = map.get("tableurl") == null ? null : map.get("tableurl")
                        .toString();
            }
        } catch (DaoException e) {
            Log.e("sf", e.getMessage());
        }
        refresh();


    }


    IEventListener iEventListener = new IEventListener() {

        public void eventProcess(int arg0, Object... objects)
                throws HDException {
            if (IEventType.ACTIONBAR_RECORD_CLICK == arg0) {

//                sendBackWorkOrder();
            }
        }
    };

    private void refresh() {
        final ProgressDialog proDialog = new ProgressDialog(this, "加载中。。。");
        proDialog.show();
        if (webView != null) {
            webView.removeAllViews();
        }
        webView = new WebView(this);
        // 设置缩放支持
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        WebViewClient wClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                proDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String flingUrl) {

                proDialog.dismiss();
                webView.addView(errView, LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                super.onReceivedError(view, errorCode, description, flingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        };

        webView.setWebViewClient(wClient);
        webView.loadUrl(url);

        setContentView(webView);

    }


}
