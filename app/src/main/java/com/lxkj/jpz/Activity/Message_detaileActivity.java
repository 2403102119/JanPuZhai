package com.lxkj.jpz.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;

public class Message_detaileActivity extends BaseActivity {

  private String title,time,context;
  private TextView tv_title,tv_time,tv_context;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_message_detaile);
        setTopTitle("消息详情");
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_context = findViewById(R.id.tv_context);
    }

    @Override
    protected void initEvent() {
        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        context = getIntent().getStringExtra("context");

        tv_title.setText(title);
        tv_time.setText(time);
        tv_context.setText(context);
    }

    @Override
    protected void initData() {

    }
}
