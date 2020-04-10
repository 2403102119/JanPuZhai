package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Commonbean;
import com.lxkj.jpz.Bean.pifaInitPageBean;
import com.lxkj.jpz.Bean.vipInitPageBean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class VipActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_payment,tv_yinpai,tv_jinpai,tv_jiage,tv_quanyi;
    private String type;// 0 vip  1 批发
    private LinearLayout ll_jinpai,ll_yinpai,ll_vip;
    private View view_jinpai,view_yinpai;
    private String image1,image2,contentUrl1,contentUrl2,money1,money2;
    private RoundedImageView ri_vip;
    private WebView webView;
    private String amount;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_vip);

        tv_payment = findViewById(R.id.tv_payment);
        ll_jinpai = findViewById(R.id.ll_jinpai);
        ll_yinpai = findViewById(R.id.ll_yinpai);
        view_jinpai = findViewById(R.id.view_jinpai);
        view_yinpai = findViewById(R.id.view_yinpai);
        tv_yinpai = findViewById(R.id.tv_yinpai);
        tv_jinpai = findViewById(R.id.tv_jinpai);
        tv_jiage = findViewById(R.id.tv_jiage);
        tv_quanyi = findViewById(R.id.tv_quanyi);
        ri_vip = findViewById(R.id.ri_vip);
        webView = findViewById(R.id.webView);
        ll_vip = findViewById(R.id.ll_vip);
    }

    @Override
    protected void initEvent() {
        tv_payment.setOnClickListener(this);
        ll_jinpai.setOnClickListener(this);
        ll_yinpai.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        if (type.equals("0")){
            setTopTitle(getString(R.string.Member_prepaid_phone));
            tv_quanyi.setText(getString(R.string.Distribution_rights));
            ll_vip.setVisibility(View.VISIBLE);
            vipInitPage();
        }else {
            setTopTitle(getString(R.string.Apply_for_wholesale));
            tv_quanyi.setText(getString(R.string.Wholesaler_interest));
            ll_vip.setVisibility(View.GONE);
            pifaInitPage();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_payment:
                Intent intent = new Intent(mContext,RechargeActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("moeny",amount);
                startActivity(intent);
                break;
            case R.id.ll_jinpai://金牌VIP
                tv_yinpai.setTextColor(getResources().getColor(R.color.grey3));
                tv_jinpai.setTextColor(getResources().getColor(R.color.red_them));
                view_jinpai.setVisibility(View.VISIBLE);
                view_yinpai.setVisibility(View.INVISIBLE);

                tv_jiage.setText(money1);
                amount = money1;
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(image1)
                        .into(ri_vip);
                webSetting(contentUrl1);
                break;
            case R.id.ll_yinpai://银牌VIP
                tv_yinpai.setTextColor(getResources().getColor(R.color.red_them));
                tv_jinpai.setTextColor(getResources().getColor(R.color.grey3));
                view_jinpai.setVisibility(View.INVISIBLE);
                view_yinpai.setVisibility(View.VISIBLE);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(image2)
                        .into(ri_vip);
                tv_jiage.setText(money2);
                amount = money2;
                webSetting(contentUrl2);
                break;
        }
    }


    //申请vip页
    private void vipInitPage() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","vipInitPage");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<vipInitPageBean>(mContext) {
            @Override
            public void onSuccess(Response response, vipInitPageBean resultBean) {
                image1 = resultBean.getImage1();
                image2 = resultBean.getImage2();
                contentUrl1 = resultBean.getContentUrl1();
                contentUrl2 = resultBean.getContentUrl2();
                money1 = resultBean.getMoney1();
                money2 = resultBean.getMoney2();
                amount = money1;
                tv_jiage.setText("$"+money1);
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(image1)
                        .into(ri_vip);
                webSetting(contentUrl1);
            }
            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //批发商申请页
    private void pifaInitPage() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","pifaInitPage");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<pifaInitPageBean>(mContext) {
            @Override
            public void onSuccess(Response response, pifaInitPageBean resultBean) {
                tv_jiage.setText("$"+resultBean.getMoney());
                amount = resultBean.getMoney();
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.logo)
                        .placeholder(R.mipmap.logo))
                        .load(resultBean.getImage())
                        .into(ri_vip);
                webSetting(resultBean.getContentUrl());

            }
            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


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

}
