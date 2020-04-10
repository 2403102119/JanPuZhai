package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Withdrawal;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :提现到支付宝界面
 */
public class Withdraw_alipayActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_login,tv_sell,tv_usable;
    private String money;
    private EditText et_money,et_number,et_name;
    private String lowest;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_withdraw_alipay);
        setTopTitle(getString(R.string.withdraw_deposit));
        tv_login = findViewById(R.id.tv_login);
        tv_sell = findViewById(R.id.tv_sell);
        et_money = findViewById(R.id.et_money);
        tv_usable = findViewById(R.id.tv_usable);
        et_number = findViewById(R.id.et_number);
        et_name = findViewById(R.id.et_name);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        tv_sell.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        money = getIntent().getStringExtra("money");
        tv_usable.setText(getString(R.string.keyongyue)+money);
        setWithdrawal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login://提现
                if (StringUtil_li.isSpace(et_number.getText().toString())){
                   showToast(getString(R.string.tianxiezhifubaozhanghao));
                   return;
                }
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast(getString(R.string.tianxiezhifubaoxingming));
                    return;
                }
                if (StringUtil_li.isSpace(et_money.getText().toString())){
                    showToast(getString(R.string.shurutixianjine));
                    return;
                }
                if (Float.parseFloat(lowest)>Float.parseFloat(et_money.getText().toString())){
                    showToast(getString(R.string.tixianjinebuxiaoyu)+lowest+getString(R.string.yuan));
                    return;
                }

                subWithdrawal(et_number.getText().toString(),et_name.getText().toString(),et_money.getText().toString(),"","","","");

                break;
            case R.id.tv_sell: //全部提现
                et_money.setText(money);
                break;
        }
    }


    //提现金额设置
    private void setWithdrawal() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "setWithdrawal");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Withdrawal>(mContext) {
            @Override
            public void onSuccess(Response response, Withdrawal resultBean) {
                lowest = resultBean.getAmount();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //提交提现申请
    private void subWithdrawal(String zfbaccount, final String zfbname, final String amount, final String bankcardname, String bankcardnum, String bankname, String bankno) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "subWithdrawal");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", "0");
        params.put("zfbaccount",zfbaccount);
        params.put("zfbname",zfbname);
        params.put("amount",amount);
        params.put("bankcardname",bankcardname);
        params.put("bankcardnum",bankcardnum);
        params.put("bankname",bankname);
        params.put("bankno",bankno);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent(Withdraw_alipayActivity.this,AlipaywinActivity.class);
                intent.putExtra("money",amount);
                intent.putExtra("type","1");
                intent.putExtra("name",zfbname);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
