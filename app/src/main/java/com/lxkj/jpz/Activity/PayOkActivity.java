package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :支付成功
 */
public class PayOkActivity extends BaseActivity implements View.OnClickListener{

    private TextView logout;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_pay_ok);
        logout = findViewById(R.id.logout);
    }

    @Override
    protected void initEvent() {
        logout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                Intent intent = new Intent(PayOkActivity.this,OrderActivity.class);
                intent.putExtra("position","0");
                startActivity(intent);
                finish();
                break;
        }
    }
}
