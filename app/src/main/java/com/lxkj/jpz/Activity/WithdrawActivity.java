package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Brokeragebean;
import com.lxkj.jpz.Bean.rechargeBalanceBean;
import com.lxkj.jpz.Bean.setWithdrawal;
import com.lxkj.jpz.Fragment.PayFragment;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.View.ActionDialog;
import com.lxkj.jpz.View.PayPwdView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class WithdrawActivity extends BaseActivity implements View.OnClickListener, PayPwdView.InputCallBack{
    private static final String TAG = "WithdrawActivity";
    private TextView tv_login,tv_shouxu;
    private EditText et_jine,et_name,et_number;
    private String shouxufei,balance,xianzhijine;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_withdraw);
        setTopTitle(getString(R.string.shenqingtixian));
        tv_login = findViewById(R.id.tv_login);
        et_jine = findViewById(R.id.et_jine);
        et_name = findViewById(R.id.tv_name);
        et_number = findViewById(R.id.et_number);
        tv_shouxu = findViewById(R.id.tv_shouxu);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setWithdrawal();
        balance = getIntent().getStringExtra("balance");

        et_jine.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }



            @Override

            public void afterTextChanged(Editable editable) {
                BigDecimal bigDecimal = new BigDecimal(shouxufei);
                BigDecimal bigDecimal1 = new BigDecimal(et_jine.getText().toString());
                BigDecimal bigDecimal2 = bigDecimal.multiply(bigDecimal1);
                BigDecimal bigDecimal3 = new BigDecimal(bigDecimal2.toString());
                tv_shouxu.setText(getString(R.string.shouxufei)+bigDecimal3);
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login://申请提现
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast(getString(R.string.Please_enter_name));
                    return;
                }
                if (StringUtil_li.isSpace(et_number.getText().toString())){
                    showToast(getString(R.string.shuruyinhangkazhanghao));
                    return;
                }
                if (StringUtil_li.isSpace(et_jine.getText().toString())){
                    showToast(getString(R.string.shurutixianjine));
                    return;
                }
                 if (Double.parseDouble(balance)>Double.parseDouble(xianzhijine)){
                     Bundle bundle = new Bundle();
                     bundle.putString(PayFragment.EXTRA_CONTENT, " " + et_jine.getText().toString());
                     if (SPTool.getSessionValue(SQSP.setPwd).equals("0")){
                         bundle.putString(PayFragment.EXTRA_TilEE, getString(R.string.shezhijiaoyimima));
                     }else {
                         bundle.putString(PayFragment.EXTRA_TilEE, getString(R.string.shurujiaoyimima));
                     }
                     PayFragment fragment = new PayFragment();
                     fragment.setArguments(bundle);
                     fragment.setPaySuccessCallBack(WithdrawActivity.this);
                     fragment.show(getSupportFragmentManager(), "Pay");
                 }else {
                     showToast(getString(R.string.keyongyue)+getString(R.string.manzu)+xianzhijine+getString(R.string.yuan));
                 }


                break;
        }
    }

    @Override
    public void onInputFinish(String result) {
        if (SPTool.getSessionValue(SQSP.setPwd).equals("0")){
            Log.i(TAG, "onInputFinish: "+result);
            if (StringUtil_li.isSpace(et_name.getText().toString())){
                showToast(getString(R.string.Please_enter_name));
                return;
            }
            if (StringUtil_li.isSpace(et_number.getText().toString())){
                showToast(getString(R.string.shuruyinhangkazhanghao));
                return;
            }
            if (StringUtil_li.isSpace(et_jine.getText().toString())){
                showToast(getString(R.string.shurutixianjine));
                return;
            }
            setPayPassword(result);
        }else {
            Log.i(TAG, "onInputFinish: "+result);
            if (StringUtil_li.isSpace(et_name.getText().toString())){
                showToast(getString(R.string.Please_enter_name));
                return;
            }
            if (StringUtil_li.isSpace(et_number.getText().toString())){
                showToast(getString(R.string.shuruyinhangkazhanghao));
                return;
            }
            if (StringUtil_li.isSpace(et_jine.getText().toString())){
                showToast(getString(R.string.shurutixianjine));
                return;
            }
            subWithdrawal(et_jine.getText().toString(),result,et_name.getText().toString(),et_number.getText().toString(),"","");
        }

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
                subWithdrawal(et_jine.getText().toString(),payPassword,et_name.getText().toString(),et_number.getText().toString(),"","");

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
        //提交提现申请
    private void subWithdrawal(final String amount, String password, String bankcardname, String bankcardnum, String bankname, String bankno) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "subWithdrawal");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("amount",amount);
        params.put("password", password);
        params.put("bankcardname", bankcardname);
        params.put("bankcardnum", bankcardnum);
        params.put("bankname", bankname);
        params.put("bankno", bankno);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                Intent intent = new Intent(mContext,Withdrawal_successActivity.class);
                intent.putExtra("money",amount);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //提现金额设置
    private void setWithdrawal() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "setWithdrawal");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<setWithdrawal>(mContext) {
            @Override
            public void onSuccess(Response response, setWithdrawal resultBean) {
                shouxufei = resultBean.getRate();
                xianzhijine = resultBean.getAmount();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
