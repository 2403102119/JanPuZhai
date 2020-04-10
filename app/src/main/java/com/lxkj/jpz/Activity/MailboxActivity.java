package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Checkphonebean;
import com.lxkj.jpz.Bean.Commonbean;
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
import com.lxkj.jpz.Utils.ToastFactory;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :"邮箱登录
 */
public class MailboxActivity extends BaseActivity  implements View.OnClickListener{
    private EditText edit1,edit2;
    private TextView faCode,tv_login;
    private String yzmcode = "fdafgsdgshgshgsah";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_mailbox);
        setTopTitle("");
        edit1 = findViewById(R.id.edit1);
        faCode = findViewById(R.id.faCode);
        edit2 = findViewById(R.id.edit2);
        tv_login = findViewById(R.id.tv_login);
    }

    @Override
    protected void initEvent() {
        faCode.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.faCode:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_email_number));
                    return;
                }
                getValidateCode(edit1.getText().toString());
                break;
            case R.id.tv_login:
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_email_number));
                    return;
                }
                if (StringUtil_li .isSpace(edit2.getText().toString())){
                    showToast(getString(R.string.Please_enter_the_verification_code));
                    return;
                }
                if (!yzmcode.equals(edit2.getText().toString())){
                    showToast(getString(R.string.The_verification_code_is_incorrect));
                    return;
                }
                userLogin("","",SPTool.getSessionValue(SQSP.JupshID),"1",edit1.getText().toString());
                break;

        }
    }

    //登录
    private void userLogin(String phone, String password, String token,String type,String email) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userLogin");
        params.put("phone", phone);
        params.put("password", password);
        params.put("token", token);
        params.put("type", type);
        params.put("email", email);

        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Commonbean>(mContext) {
            @Override
            public void onSuccess(Response response, Commonbean resultBean) {
                ToastFactory.getToast(mContext, resultBean.resultNote).show();

                if (resultBean.getResult().equals("0")) {
                    String uid = resultBean.getUid();
                    SPTool.addSessionMap(SQSP.uid, uid);
                    SPTool.addSessionMap(SQSP.isLogin, true);
                    App.login = true;
                    Intent intent = new Intent(MailboxActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }
    //验证邮箱是否注册
    private void checkPhone(final String email) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","checkEmail");
        params.put("email", email);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Checkphonebean>(mContext) {
            @Override
            public void onSuccess(Response response, Checkphonebean resultBean) {
                if (resultBean.getExistence().equals("0")){

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
        params.put("phone", "");
        params.put("type", "1");
        params.put("email", phone);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Yzmcodebean>(mContext) {
            @Override
            public void onSuccess(Response response, Yzmcodebean resultBean) {
                showToast(resultBean.getResultNote());
                yzmcode = resultBean.getCode();
                Log.i("123456", "onSuccess: "+yzmcode);
                //设置短信验证码多少秒后重新获取
                MyCountDownTimer timer = new MyCountDownTimer(MailboxActivity.this, faCode, 60 * 1000, 1000);
                timer.start();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

}
