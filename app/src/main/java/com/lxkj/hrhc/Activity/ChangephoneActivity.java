package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.Yzmcodebean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.ResultBean;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.MyCountDownTimer;
import com.lxkj.hrhc.Utils.SPTool;
import com.lxkj.hrhc.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :修改手机号
 */
public class ChangephoneActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_login,faCode;
    private EditText et_password,et_phone,et_code;
    private String code;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_changephone);
        setTopTitle("修改手机号");

        tv_login = findViewById(R.id.tv_login);
        et_password = findViewById(R.id.et_password);
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        faCode = findViewById(R.id.faCode);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        faCode.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(et_password.getText().toString())){
                    showToast("请输入登录密码");
                    return;
                }
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入新手机号");
                    return;
                }
                if (StringUtil_li.isSpace(et_code.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }
                if (!et_code.getText().toString().equals(code)){
                    showToast("验证码不正确");
                    return;
                }
                updateUserPhone(et_password.getText().toString(),et_phone.getText().toString());
                break;
            case R.id.faCode:
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入新手机号");
                    return;
                }
                getValidateCode(et_phone.getText().toString());
                break;
        }
    }
    //获取验证码
    private void getValidateCode(String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","getValidateCode");
        params.put("phone", phone);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Yzmcodebean>(mContext) {
            @Override
            public void onSuccess(Response response, Yzmcodebean resultBean) {
                showToast(resultBean.getResultNote());
                code = resultBean.getCode();
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(ChangephoneActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //修改手机号
    private void updateUserPhone(final String password,final String newPhone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateUserPhone");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("password",password);
        params.put("newPhone",newPhone);
        OkHttpHelper.getInstance().post_json(ChangephoneActivity.this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(ChangephoneActivity.this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent();
                intent.putExtra("phone",newPhone);
                setResult(333,intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
