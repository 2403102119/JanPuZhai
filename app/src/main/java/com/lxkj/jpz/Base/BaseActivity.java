package com.lxkj.jpz.Base;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.ActivityManager;
import com.lxkj.jpz.Utils.MUIToast;
import com.lxkj.jpz.Utils.StatusBarUtil;



public abstract class BaseActivity extends AppCompatActivity {

//    ¥  ¥   ￥

    private Dialog dialog;
    protected TextView topTitle, rightText;
    protected ImageView rightIcon;
    private FrameLayout container;
    private LinearLayout root;
    public ImageView baseback;
    protected View baseTop;
    public Context mContext;
    protected View view;
    @Override
    public Resources getResources() {
        // 字体大小不跟随系统
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = this;
        root = findViewById(R.id.root);

        container = (FrameLayout) findViewById(R.id.container);
        baseTop = findViewById(R.id.top);
        view = findViewById(R.id.view);
        topTitle = (TextView) baseTop.findViewById(R.id.title);
        rightText = (TextView) baseTop.findViewById(R.id.rightText);
        rightIcon = (ImageView) baseTop.findViewById(R.id.rightIcon);
        baseback = (ImageView) baseTop.findViewById(R.id.back);
        baseback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ActivityManager.addActivity(this);
        initView(savedInstanceState);
        initEvent();
        initData();
//        StatusBarUtil.setColor(this, Color.parseColor("#ffffff"));
//        StatusBarUtil.StatusBarLightMode(this);
////        设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
    }

    protected void setContainer(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutId, container, true);
    }

    public static final int TOP_PRIMARY_COLOR = 0x66;
    public static final int TOP_PRIMARY_WHITE = 0x67;

    protected void setTopPrimaryColor(int style) {
        if (style == TOP_PRIMARY_COLOR) {
            baseTop.setBackgroundColor(getResources().getColor(R.color.red_them));
            baseback.setImageResource(R.mipmap.nav_back_white);
            topTitle.setTextColor(Color.parseColor("#ffffff"));
        } else {
            baseTop.setBackgroundColor(Color.parseColor("#ffffff"));
            baseback.setImageResource(R.mipmap.nav_back_black);
            topTitle.setTextColor(Color.parseColor("#333333"));
            root.setFitsSystemWindows(true);
            rightText.setTextColor(ContextCompat.getColor(this, R.color.grey3));
            StatusBarUtil.setColor(this, Color.parseColor("#ffffff"));
            StatusBarUtil.StatusBarLightMode(this);
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseTop.findViewById(R.id.statusbar).setVisibility(View.VISIBLE);
            }else{
                baseTop.findViewById(R.id.statusbar).setVisibility(View.GONE);
            }*/

        }
    }

    protected void setTopTitle(String title) {
        topTitle.setText(title);
    }

    protected void setRightText(String text, View.OnClickListener listener) {
        rightText.setVisibility(View.VISIBLE);
        rightText.setText(text);
        rightText.setOnClickListener(listener);
    }


    public void showToast(String msg) {
        MUIToast.show(this, msg);
    }

    public void showToast(int id) {
        MUIToast.toast(this, id);
    }

    public void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    // 初始化ui
    protected abstract void initView(Bundle savedInstanceState);

    // 添加监听器
    protected abstract void initEvent();

    // 初始化数据
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
       //JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //JPushInterface.onPause(this);
    }




    public void showLoading(){
        showLoading(false);
    }

    public void showLoading(boolean cancelable){
        showLoading(null ,cancelable);
    }

    public void showLoading(String msg, boolean cancelable) {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.LightDialog);
            View view = getLayoutInflater().inflate(R.layout.dialog_loading, null);
            if (!TextUtils.isEmpty(msg)){
                TextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(msg);
            }
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);

        }
        if (!isFinishing() && dialog != null && !dialog.isShowing()) {
            dialog.setCancelable(cancelable);
            dialog.show();
        }
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }











}
