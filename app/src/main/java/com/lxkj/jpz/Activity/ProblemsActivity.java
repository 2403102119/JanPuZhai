package com.lxkj.jpz.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :问题详情
 */
public class ProblemsActivity extends BaseActivity {

    private String title,context;
    private TextView tv_title,tv_context;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_problems);
        tv_title = findViewById(R.id.tv_title);
        tv_context = findViewById(R.id.tv_context);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        title = getIntent().getStringExtra("title");
        context = getIntent().getStringExtra("context");
        tv_title.setText(title);
        tv_context.setText(context);
    }
}
