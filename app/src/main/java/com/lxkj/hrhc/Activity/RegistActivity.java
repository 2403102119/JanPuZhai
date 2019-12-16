package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.Checkphonebean;
import com.lxkj.hrhc.Bean.Commonbean;
import com.lxkj.hrhc.Bean.Servicecontextbean;
import com.lxkj.hrhc.Bean.Yzmcodebean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.Uri.NetClass;
import com.lxkj.hrhc.Utils.MD5Util;
import com.lxkj.hrhc.Utils.MyCountDownTimer;
import com.lxkj.hrhc.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:2019.11.12
 * Describe :注册页面
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_login,faCode,tv_agreement;
    private EditText et_phone,edit2,tv_password,et_enter_password,et_invite_code;
    private CheckBox cb_weixin;
    private String contentUrl,yzmcode = "fdafgsdgshgshgsah";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_regist);
        setTopTitle("注册");
        tv_login = findViewById(R.id.tv_login);
        et_phone = findViewById(R.id.et_phone);
        edit2 = findViewById(R.id.edit2);
        tv_password = findViewById(R.id.tv_password);
        et_enter_password = findViewById(R.id.et_enter_password);
        et_invite_code = findViewById(R.id.et_invite_code);
        cb_weixin = findViewById(R.id.cb_weixin);
        faCode = findViewById(R.id.faCode);
        tv_agreement = findViewById(R.id.tv_agreement);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        faCode.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        serviceContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login://注册成功
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast("请输入验证码");
                    return;
                }

                if (!yzmcode.equals(edit2.getText().toString())){
                    showToast("验证码不正确");
                    return;
                }
                if (StringUtil_li.isSpace(tv_password.getText().toString())){
                    showToast("请设置密码");
                    return;
                }
                if (StringUtil_li.isSpace(et_enter_password.getText().toString())){
                    showToast("请再次输入密码");
                    return;
                }
                if (!et_enter_password.getText().toString().equals(tv_password.getText().toString())){
                    showToast("密码验证不通过");
                    return;
                }
                if (StringUtil_li.isSpace(et_invite_code.getText().toString())){
                    showToast("请填写邀请码");
                    return;
                }
                if (cb_weixin.isChecked()==false){
                    showToast("请阅读并同意用户协议");
                    return;
                }

                userRegist(et_phone.getText().toString(), MD5Util.encrypt(tv_password.getText().toString()),et_invite_code.getText().toString(),StringUtil_li.getSerialNumber());
                break;
            case R.id.faCode://下发验证码
                if (StringUtil_li.isSpace(et_phone.getText().toString())){
                    showToast("请输入手机号");
                    return;
                }
                checkPhone(et_phone.getText().toString());
                break;
            case R.id.tv_agreement://用户协议
                Intent intent = new Intent(RegistActivity.this,WebViewActivity.class);
                intent.putExtra("webTitle","用户注册协议");
                intent.putExtra("webUrl",contentUrl);
                startActivity(intent);
                break;
        }
    }
    //验证手机号是否注册
    private void checkPhone(final String phone) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","checkPhone");
        params.put("phone", phone);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Checkphonebean>(mContext) {
            @Override
            public void onSuccess(Response response, Checkphonebean resultBean) {
                if (resultBean.getExistence().equals("0")){
                    getValidateCode(phone);
                }else {
                    showToast("手机号码已存在");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


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
                yzmcode = resultBean.getCode();
                Log.i("123456", "onSuccess: "+yzmcode);
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(RegistActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //注册
    private void userRegist(String phone, String password, String invite,String equipid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userRegist");
        params.put("phone", phone);
        params.put("password", password);
        params.put("invite", invite);
        params.put("equipid", equipid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //注册协议
    private void serviceContent() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","serviceContent");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Servicecontextbean>(mContext) {
            @Override
            public void onSuccess(Response response, Servicecontextbean resultBean) {
                contentUrl = resultBean.getContentUrl();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
}
