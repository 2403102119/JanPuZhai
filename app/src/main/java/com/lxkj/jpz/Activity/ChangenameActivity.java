package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.View.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class ChangenameActivity extends BaseActivity implements View.OnClickListener {


    private TextView tv_login;
    private ClearEditText  tv_name;
    private String name;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_changename);
        setTopTitle(getString(R.string.Modify_the_nickname));
        tv_login = findViewById(R.id.tv_login);
        tv_name = findViewById(R.id.tv_name);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(tv_name.getText().toString())){
                    showToast(getString(R.string.Please_fill_in_the_user_name));
                    return;
                }
                updateUserName(tv_name.getText().toString());
                break;
        }
    }

    //修改昵称
    private void updateUserName(final String name) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateUserName");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("name",name);
        OkHttpHelper.getInstance().post_json(ChangenameActivity.this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(ChangenameActivity.this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(222,intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
