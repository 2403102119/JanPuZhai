package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;

public class AlipaywinActivity extends BaseActivity implements View.OnClickListener{


    private TextView tv_login;
    private TextView tv_money,tv_name;
    private String money;
    private String name,type;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_alipaywin);

        setTopTitle(getString(R.string.Show_the_result));
        tv_login = findViewById(R.id.tv_login);
        tv_money = findViewById(R.id.tv_money);
        tv_name = findViewById(R.id.tv_name);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        money = getIntent().getStringExtra("money");
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        tv_money.setText(money);
        if (type.equals("0")){//银行卡
            tv_name.setText(getString(R.string.bank_card)+" - "+name);
        }else {//支付宝
            tv_name.setText(getString(R.string.alipay)+" - "+name);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                Intent intent = new Intent(AlipaywinActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
