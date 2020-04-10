package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.NoticeImagebean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;

public class activitActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_baoming;
    private PopupWindow popupWindow;// 声明PopupWindow
    private LinearLayout ll_sell_item;
    private RelativeLayout ll_sell;
    private String url;
    private WebView webView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_activit);
        setTopTitle(getString(R.string.online_activities));
        tv_baoming = findViewById(R.id.tv_baoming);
        webView = findViewById(R.id.webView);
    }

    @Override
    protected void initEvent() {
        tv_baoming.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        webSetting(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_baoming://报名
                state();
                ll_sell_item.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                popupWindow.showAtLocation(v, Gravity.CENTER,0,10);
                break;
        }
    }
    private void webSetting(String url){
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(false);// 支持缩放
//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);//显示字体大小

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    //填写信息
    public void state(){
        popupWindow=new PopupWindow(mContext);
        View view=getLayoutInflater().inflate(R.layout.state,null);
        ll_sell_item= view.findViewById(R.id.ll_sell_item);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        ll_sell=view.findViewById(R.id.ll_sell);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        final EditText et_name = view.findViewById(R.id.et_name);
        final EditText et_phone = view.findViewById(R.id.et_phone);
        final EditText et_wx = view.findViewById(R.id.et_wx);
        final EditText et_quyu = view.findViewById(R.id.et_quyu);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast(getString(R.string.xingming));
                    return;
                }
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_mobile_phone_number));
                    return;
                }
                if (StringUtil_li.isSpace(et_wx.getText().toString())){
                    showToast(getString(R.string.weixin));
                    return;
                }
                if (StringUtil_li.isSpace(et_quyu.getText().toString())){
                    showToast(getString(R.string.shuru_area));
                    return;
                }
                enterfor(et_name.getText().toString(),et_phone.getText().toString(),et_wx.getText().toString(),et_quyu.getText().toString());
            }
        });
        ll_sell_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });

    }


    //活动报名
    private void enterfor(String name,String phone,String wx,String address) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "enterfor");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("name", name);
        params.put("phone", phone);
        params.put("wx", wx);
        params.put("address", address);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<NoticeImagebean>(mContext) {

            @Override
            public void onSuccess(Response response, final NoticeImagebean resultBean) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
                tv_baoming.setText(getString(R.string.registered));
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }



}
