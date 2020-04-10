package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.View.MyWebView;

public class WebViewActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);

        ImageView finishBack = findViewById(R.id.finishBack);
        TextView titleName = findViewById(R.id.titleName);
        finishBack.setVisibility(View.VISIBLE);
        finishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyWebView webView1 = findViewById(R.id.webView);
        webView = webView1.getWebView();

        Intent intent = getIntent();
        String webTitle = intent.getStringExtra("webTitle");
        String webUrl = intent.getStringExtra("webUrl");
        //String isTitle = intent.getStringExtra("isTitle");

        titleName.setText(webTitle);
        webView.loadUrl(webUrl);

        WebSettings settings = webView1.getWebView().getSettings();
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置支持js
        settings.setJavaScriptEnabled(true);
        // 关闭缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 设置出现缩放工具
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        // 扩大比例的缩放
        settings.setUseWideViewPort(true);
        // 自适应屏幕
        settings.setLoadWithOverviewMode(true);


        //解决Url跨域问题
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (!url.startsWith("http:") || !url.startsWith("https:")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }

                view.loadUrl(url);
                return true;
            }
        });


    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

}
