package com.lxkj.jpz.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gyf.immersionbar.ImmersionBar;
import com.lxkj.jpz.Bean.privacyBean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.View.ActionDialog;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";
    private Handler handler=new Handler();
    private Context context ;
    private ActionDialog actionDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
        setContentView(R.layout.activity_start);
        privacyPolicy();

    }
    //协议列表
    private void privacyPolicy() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "privacyPolicy");
        OkHttpHelper.getInstance().post_json(this, NetClass.BASE_URL, params, new BaseCallback<privacyBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final privacyBean resultBean) {
                actionDialog = new ActionDialog(StartActivity.this,"温馨提示","请您在使用前仔细阅读并同意"+resultBean.getDataList().get(0).getTitle()+"和"+resultBean.getDataList().get(1).getTitle()+",其中的重点条款已为您标注，方便您了解自己的权利。","不同意","同意并使用");
                actionDialog.setOnxieyiClickListener(new ActionDialog.OnxieyiClickListener() {
                    @Override
                    public void onLeftClick() {
                        actionDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onRightClick() {
                        actionDialog.dismiss();
                        SPTool.addSessionMap(SQSP.xieyi, "1");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                                finish();
                            }
                        },3000);
                    }

                    @Override
                    public void onZhuce() {
                        Intent intent = new Intent(StartActivity.this,WebViewActivity.class);
                        intent.putExtra("webTitle",resultBean.getDataList().get(0).getTitle());
                        intent.putExtra("webUrl",resultBean.getDataList().get(0).getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onyinsi() {
                        Intent intent = new Intent(StartActivity.this,WebViewActivity.class);
                        intent.putExtra("webTitle",resultBean.getDataList().get(1).getTitle());
                        intent.putExtra("webUrl",resultBean.getDataList().get(1).getUrl());
                        startActivity(intent);
                    }
                });
                if (SPTool.getSessionValue(SQSP.xieyi).equals("1")){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
                        }
                    },3000);
                }else{
                    actionDialog.show();
                }
                Log.i(TAG, "onSuccess: "+"走onSuccess");
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }



}
