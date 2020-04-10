package com.lxkj.jpz.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :提现成功
 */
public class Withdrawal_successActivity extends BaseActivity implements View.OnClickListener{

   private String money;
   private TextView tv_jine;
   private TextView tv_login;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_withdrawal_success);
        setTopTitle(getString(R.string.tixianhcneggong));
        tv_jine = findViewById(R.id.tv_jine);
        tv_login = findViewById(R.id.tv_login);
    }

    @Override
    protected void initEvent() {
        money = getIntent().getStringExtra("money");
        tv_jine.setText("$"+money);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                finish();
                break ;
        }
    }
}
