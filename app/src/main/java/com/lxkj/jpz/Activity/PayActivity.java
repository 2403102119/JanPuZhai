package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Utils.ActionPopoverUtils;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.Utils.ToastFactory;



/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :支付界面
 */
public class PayActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "PayActivity";
    private TextView tv_pay,tv_money;
    private String moeny,orderid;
    private ImageView wexin,alipay;
    private String type = "0";
    private String alipaytype = "2";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_pay);
        setTopTitle("选择付款方式");

        tv_pay = findViewById(R.id.tv_pay);
        tv_money = findViewById(R.id.tv_money);
        wexin = findViewById(R.id.wexin);
        alipay = findViewById(R.id.alipay);
    }

    @Override
    protected void initEvent() {
        tv_pay.setOnClickListener(this);
        wexin.setOnClickListener(this);
        alipay.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        moeny = getIntent().getStringExtra("moeny");
        orderid = getIntent().getStringExtra("orderid");
        tv_money.setText(moeny);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay://立即支付
                 if (type.equals("0")){

                 }else if (alipaytype.equals("3")){

                 }else {
                     showToast("请选择支付方式");
                 }
                break;
            case R.id.wexin://微信
                if (type.equals("1")){
                    wexin.setImageResource(R.mipmap.xuanzhong);
                    alipay.setImageResource(R.mipmap.weixuan);
                    type = "0";
                    alipaytype= "2";
                }else if (type.equals("0")){
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipay.setImageResource(R.mipmap.weixuan);
                    type= "1";
                    alipaytype= "2";
                }
                break;
            case R.id.alipay:

                if (alipaytype.equals("2")){
                    alipay.setImageResource(R.mipmap.xuanzhong);
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipaytype= "3";
                    type = "1";
                }else if (alipaytype.equals("3")){
                    alipay.setImageResource(R.mipmap.weixuan);
                    wexin.setImageResource(R.mipmap.weixuan);
                    alipaytype= "2";
                    type = "1";
                }
                break;
        }
    }

    private int ceilInt(double number) {
        return (int) Math.ceil(number);
    }

    private String toastMsg;
    private boolean isDetail;//是否是详情跳转









}
