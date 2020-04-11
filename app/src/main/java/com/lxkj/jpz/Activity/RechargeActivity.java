package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Brokeragebean;
import com.lxkj.jpz.Bean.rechargeBalanceBean;
import com.lxkj.jpz.Fragment.PayFragment;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.View.PayPwdView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :充值
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, PayPwdView.InputCallBack{

    private TextView tv_login,tv_money;
    private String type1;// 0 充值  1  支付
    private String moeny,ispifa;
    private String zhifufangshi = "0",orderId;
    private LinearLayout ll_fukuan,ll_chonghzi,ll_weixin,ll_zhifubao,ll_paypal,ll_yue,ll_xianxia;
    private EditText et_jine;
    private ImageView im_weixin,im_zhifubao,im_paypal,im_yue,im_xianxia;
    private View v2,v1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_recharge);
        tv_login = findViewById(R.id.tv_login);
        ll_fukuan = findViewById(R.id.ll_fukuan);
        ll_chonghzi = findViewById(R.id.ll_chonghzi);
        et_jine = findViewById(R.id.et_jine);
        tv_money = findViewById(R.id.tv_money);
        ll_weixin = findViewById(R.id.ll_weixin);
        ll_zhifubao = findViewById(R.id.ll_zhifubao);
        ll_paypal = findViewById(R.id.ll_paypal);
        ll_yue = findViewById(R.id.ll_yue);
        im_weixin = findViewById(R.id.im_weixin);
        im_zhifubao = findViewById(R.id.im_zhifubao);
        im_paypal = findViewById(R.id.im_paypal);
        im_yue = findViewById(R.id.im_yue);
        ll_xianxia = findViewById(R.id.ll_xianxia);
        im_xianxia = findViewById(R.id.im_xianxia);
        v2 = findViewById(R.id.v2);
        v1 = findViewById(R.id.v1);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        ll_zhifubao.setOnClickListener(this);
        ll_weixin.setOnClickListener(this);
        ll_paypal.setOnClickListener(this);
        ll_yue.setOnClickListener(this);
        ll_xianxia.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type1 = getIntent().getStringExtra("type");
        orderId = getIntent().getStringExtra("orderId");
        if (!StringUtil_li.isSpace(getIntent().getStringExtra("ispifa"))){
            ispifa = getIntent().getStringExtra("ispifa");
            if (ispifa.equals("1")&&SPTool.getSessionValue(SQSP.ispifa).equals("3")){
                ll_xianxia.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);
            }
        }
        if (type1.equals("0")){
            setTopTitle(getString(R.string.top_up));
            tv_login.setText(getString(R.string.top_up));
            ll_fukuan.setVisibility(View.GONE);
            ll_yue.setVisibility(View.GONE);
            ll_xianxia.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            ll_chonghzi.setVisibility(View.VISIBLE);

        }else {
            moeny = getIntent().getStringExtra("moeny");
            setTopTitle(getString(R.string.fukuan));
            tv_login.setText(getString(R.string.zhifu));
            tv_money.setText(moeny);
            ll_fukuan.setVisibility(View.VISIBLE);
            ll_chonghzi.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (type1.equals("0")){
                    if (StringUtil_li.isSpace(et_jine.getText().toString())){
                        showToast(getString(R.string.qingshuruchongzhijine));
                        return;
                    }
                    rechargeBalance(et_jine.getText().toString());
                }else {
                   if (!zhifufangshi.equals("0")){
                       Bundle bundle = new Bundle();
                       bundle.putString(PayFragment.EXTRA_CONTENT, " " + moeny);
                       if (SPTool.getSessionValue(SQSP.setPwd).equals("0")){
                           bundle.putString(PayFragment.EXTRA_TilEE, getString(R.string.shezhijiaoyimima));
                       }else {
                           bundle.putString(PayFragment.EXTRA_TilEE, getString(R.string.shurujiaoyimima));
                       }
                       PayFragment fragment = new PayFragment();
                       fragment.setArguments(bundle);
                       fragment.setPaySuccessCallBack(RechargeActivity.this);
                       fragment.show(getSupportFragmentManager(), "Pay");
                   }else {

                   }
                }



                break;
            case R.id.ll_weixin://微信支付
                zhifufangshi = "0";
                im_weixin.setImageResource(R.mipmap.xuanzhong);
                im_zhifubao.setImageResource(R.mipmap.weixuan);
                im_paypal.setImageResource(R.mipmap.weixuan);
                im_yue.setImageResource(R.mipmap.weixuan);
                im_xianxia.setImageResource(R.mipmap.weixuan);

                break;
            case R.id.ll_zhifubao://支付宝支付
                zhifufangshi = "0";
                im_weixin.setImageResource(R.mipmap.weixuan);
                im_zhifubao.setImageResource(R.mipmap.xuanzhong);
                im_paypal.setImageResource(R.mipmap.weixuan);
                im_yue.setImageResource(R.mipmap.weixuan);
                im_xianxia.setImageResource(R.mipmap.weixuan);
                break;
            case R.id.ll_paypal://PayPal支付
                zhifufangshi = "0";
                im_weixin.setImageResource(R.mipmap.weixuan);
                im_zhifubao.setImageResource(R.mipmap.weixuan);
                im_paypal.setImageResource(R.mipmap.xuanzhong);
                im_yue.setImageResource(R.mipmap.weixuan);
                im_xianxia.setImageResource(R.mipmap.weixuan);
                break;
            case R.id.ll_yue://余额支付
                zhifufangshi = "1";
                im_weixin.setImageResource(R.mipmap.weixuan);
                im_zhifubao.setImageResource(R.mipmap.weixuan);
                im_paypal.setImageResource(R.mipmap.weixuan);
                im_yue.setImageResource(R.mipmap.xuanzhong);
                im_xianxia.setImageResource(R.mipmap.weixuan);
                break;
            case R.id.ll_xianxia://线下支付
                zhifufangshi = "0";
                im_weixin.setImageResource(R.mipmap.weixuan);
                im_zhifubao.setImageResource(R.mipmap.weixuan);
                im_paypal.setImageResource(R.mipmap.weixuan);
                im_yue.setImageResource(R.mipmap.weixuan);
                im_xianxia.setImageResource(R.mipmap.xuanzhong);
                payBalance(zhifufangshi,moeny,orderId,"");
                break ;
        }
    }
    //充值余额
    private void rechargeBalance(String amount) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "rechargeBalance");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("amount",amount);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<rechargeBalanceBean>(mContext) {
            @Override
            public void onSuccess(Response response, rechargeBalanceBean resultBean) {
                showToast(getString(R.string.chongzhichenggong));
                finish();

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //线下支付、余额支付
    private void payBalance(String type,String amount,String orderid,String password) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "payBalance");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type",type);
        params.put("amount",amount);
        params.put("orderid",orderid);
        params.put("password",password);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<rechargeBalanceBean>(mContext) {
            @Override
            public void onSuccess(Response response, rechargeBalanceBean resultBean) {
                if (type1.equals("2")){
                    Intent intent = new Intent(mContext,PayOkActivity.class);
                    intent.putExtra("type",type1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext,PayOkActivity.class);
                    startActivity(intent);
                }

               PayFragment fragment = new PayFragment();
                fragment.dismiss();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //设置支付密码
    private void setPayPassword(String payPassword) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "payBalance");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("payPassword",payPassword);

        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<rechargeBalanceBean>(mContext) {
            @Override
            public void onSuccess(Response response, rechargeBalanceBean resultBean) {
                payBalance(zhifufangshi,moeny,orderId,payPassword);

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void onInputFinish(String result) {
        if (SPTool.getSessionValue(SQSP.setPwd).equals("0")){
            setPayPassword(result);
        }else {
            payBalance(zhifufangshi,moeny,orderId,result);
        }

    }
}
