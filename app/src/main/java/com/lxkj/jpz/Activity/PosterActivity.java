package com.lxkj.jpz.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :邀请海报
 */
public class PosterActivity extends BaseActivity {



    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_poster);
        setTopTitle(getResources().getString(R.string.Invite_poster));
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
