package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.BindingBean;
import com.lxkj.jpz.Bean.Checkphonebean;
import com.lxkj.jpz.Bean.Servicecontextbean;
import com.lxkj.jpz.Bean.Yzmcodebean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.MyCountDownTimer;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class BindingActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_login,faCode,tv_agreement;
    private EditText edit1,edit2,et_3,et_4,et_5;
    private String loginType,openId,yzmcode;
    private String contentUrl;
    private static final String TAG = "BindingActivity";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_binding);
        setTopTitle(getString(R.string.binding));

        tv_login = findViewById(R.id.tv_login);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        et_5 = findViewById(R.id.et_5);
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

        loginType = getIntent().getStringExtra("loginType");
        openId = getIntent().getStringExtra("openId");
        Log.i(TAG, "onSuccess: "+openId);
        serviceContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_mobile_phone_number));
                    return;
                }
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast(getString(R.string.Please_enter_the_verification_code));
                    return;
                }
                if (!edit2.getText().toString().equals(yzmcode)){
                    showToast(getString(R.string.The_verification_code_is_incorrect));
                    return;
                }
//                if (StringUtil_li.isSpace(et_3.getText().toString())){
//                    showToast("请设置密码");
//                    return;
//                }
//                if (StringUtil_li.isSpace(et_4.getText().toString())){
//                    showToast("请再次输入密码");
//                    return;
//                }
//                if (!et_4.getText().toString().equals(et_3.getText().toString())){
//                    showToast("再次输入密码不正确");
//                    return;
//                }
//                if (StringUtil_li.isSpace(et_5.getText().toString())){
//                    showToast("请输入邀请码");
//                    return;
//                }
                bindPhone(loginType,openId,edit1.getText().toString(),et_5.getText().toString());
                break;
            case R.id.faCode:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_mobile_phone_number));
                    return;
                }
                checkPhone(edit1.getText().toString());
                break;
            case R.id.tv_agreement:
                Intent intent = new Intent(BindingActivity.this,WebViewActivity.class);
                intent.putExtra("webTitle",getString(R.string.User_registration_agreement));
                intent.putExtra("webUrl",contentUrl);
                startActivity(intent);
                break;
        }
    }


    //绑定手机号
    private void bindPhone(String loginType,String openId,String phone,String invite) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","bindPhone");
        params.put("loginType", loginType);
        params.put("openId", openId);
        params.put("phone", phone);
        params.put("invite", invite);
        params.put("equipid", StringUtil_li.getSerialNumber());
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<BindingBean>(mContext) {
            @Override
            public void onSuccess(Response response, BindingBean resultBean) {
                showToast(resultBean.getResultNote());
                String uid = resultBean.getUid();
                SPTool.addSessionMap(SQSP.uid, uid);
                SPTool.addSessionMap(SQSP.isLogin, true);
                App.login = true;
                Intent intent = new Intent(BindingActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


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
                    showToast(getString(R.string.The_phone_number_already_exists));
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
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(BindingActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
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
