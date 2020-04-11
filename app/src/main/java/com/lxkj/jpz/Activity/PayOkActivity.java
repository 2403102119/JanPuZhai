package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :支付成功
 */
public class PayOkActivity extends BaseActivity implements View.OnClickListener{

    private TextView logout,fanhui;
    private String type;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_pay_ok);
        setTopTitle("支付成功");
        baseback.setVisibility(View.GONE);
        logout = findViewById(R.id.logout);
        fanhui = findViewById(R.id.fanhui);
    }

    @Override
    protected void initEvent() {
        logout.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        if (StringUtil_li.isSpace(type)){

        }else {
            logout.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            Intent intent1 = new Intent(PayOkActivity.this,MainActivity.class);
            startActivity(intent1);
            finish();
        }

        return super.onKeyUp(keyCode, event);
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
            case R.id.fanhui:
                Intent intent1 = new Intent(PayOkActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
