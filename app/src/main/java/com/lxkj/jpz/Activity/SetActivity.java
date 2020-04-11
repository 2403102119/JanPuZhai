package com.lxkj.jpz.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.AboutUsbean;
import com.lxkj.jpz.Bean.CheckUpdateBean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Service.DownloadReceiver;
import com.lxkj.jpz.Service.DownloadService;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.APKVersionCodeUtils;
import com.lxkj.jpz.Utils.ActivityManager;
import com.lxkj.jpz.Utils.DataCleanManager;
import com.lxkj.jpz.Utils.LocalManageUtil;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.vector.update_app.utils.AppUpdateUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Response;

public class SetActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SetActivity";
    private RelativeLayout rel2,rel1,rel3,yonghu,rel5;
    private String phone,emile;
    private TextView tv_login,tv_huancun,banbenhao;
    private LinearLayout ll_clear;
    private int numberServer;
    private int verCode;
    private String vername,lunange = "0";
    private ImageView im_kaiguan;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_set);
        setTopTitle(getString(R.string.set));
        rel2 = findViewById(R.id.rel2);
        rel1 = findViewById(R.id.rel1);
        tv_login = findViewById(R.id.tv_login);
        ll_clear = findViewById(R.id.ll_clear);
        tv_huancun = findViewById(R.id.tv_huancun);
        banbenhao = findViewById(R.id.banbenhao);
        yonghu = findViewById(R.id.yonghu);
        im_kaiguan = findViewById(R.id.im_kaiguan);
        rel5 = findViewById(R.id.rel5);
        banbenhao.setText(APKVersionCodeUtils.getVerName(SetActivity.this)+"");
        rel3 = findViewById(R.id.rel3);
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tv_huancun.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.i("111", "getSystemLocale: " + Locale.getDefault().getLanguage());
        Log.i("111", "getSystemLocale: " + Locale.getDefault().toString());
        if ("0".equals(SPTool.getSessionValue(SQSP.lunange))){
            im_kaiguan.setImageResource(R.mipmap.guan);
            lunange = "0";

        }else {
            im_kaiguan.setImageResource(R.mipmap.kai);
            lunange = "1";
        }
    }

    @Override
    protected void initEvent() {
        rel2.setOnClickListener(this);
        rel1.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        ll_clear.setOnClickListener(this);
        rel3.setOnClickListener(this);
        yonghu.setOnClickListener(this);
        im_kaiguan.setOnClickListener(this);
        rel5.setOnClickListener(this);
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);

    }
    @Override
    protected void initData() {
        phone = getIntent().getStringExtra("phone");
        emile = getIntent().getStringExtra("emile");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel2://意见反馈
                Intent intent = new Intent(SetActivity.this,IdeaActivity.class);
                startActivity(intent);
                break;
            case R.id.rel1://更改密码
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(SetActivity.this,ChangepasswordActivity.class);
                intent1.putExtra("emile",emile);
                intent1.putExtra("type","1");
                startActivity(intent1);
                break;
            case R.id.tv_login://退出登录
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                StyledDialog.init(SetActivity.this);
                StyledDialog.buildIosAlert("", "\r"+getString(R.string.tuichudenglu), new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {
                        SignOut();
                    }
                }).setBtnColor(R.color.mainColor2, R.color.mainColor1, 0).setBtnText(getString(R.string.cancel), getString(R.string.confirm)).show();
                break;
            case R.id.ll_clear:
                clearAllCachecatch();
                break;
            case R.id.rel3:
                checkUpdate();
                break;
            case R.id.yonghu://关于我们
//                Intent intent2 = new Intent(SetActivity.this,ProtocolActivity.class);
//                startActivity(intent2);
                aboutUs();
                break;
            case R.id.im_kaiguan://切换语言
                if (lunange.equals("0")){
                    selectLanguage(3);     //English
                    lunange = "1";
                    SPTool.addSessionMap(SQSP.lunange,"1");
                    im_kaiguan.setImageResource(R.mipmap.kai);
                    LocalManageUtil.onConfigurationChanged(this);
                    reStartApp();

                }else {
                    selectLanguage(1);   //简体中文
                    lunange = "0";
                    SPTool.addSessionMap(SQSP.lunange,"0");
                    im_kaiguan.setImageResource(R.mipmap.guan);
                    LocalManageUtil.onConfigurationChanged(this);
                    reStartApp();
                }
                break;
            case R.id.rel5://修改支付密码
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent2 = new Intent(SetActivity.this,ChangepaypasswordActivity.class);
                intent2.putExtra("emile",emile);
                startActivity(intent2);
                break;
        }
    }
    public void reStartApp(){
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
    //关于我们
    private void aboutUs() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "aboutUs");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(mContext) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("webTitle",getString(R.string.About_Us));
                intent.putExtra("webUrl",resultBean.getContentUrl());
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    /*清理缓存*/
    private void clearAllCachecatch() {
        DataCleanManager.clearAllCache(this);
        try {
            Log.i(TAG, "initViews: 剩余缓存" + DataCleanManager.getTotalCacheSize(this));
            tv_huancun.setText(DataCleanManager.getTotalCacheSize(this));
            showToast(getString(R.string.qinglihuancun));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //退出登录
    private void SignOut() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "SignOut");
        params.put("uid",SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(mContext) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                SPTool.addSessionMap(SQSP.isLogin, false);
                SPTool.addSessionMap(SQSP.uid, "");

                Intent intent3 = new Intent(SetActivity.this, LoginActivity.class);
                startActivity(intent3);
                ActivityManager.finishActivity();
                showToast(resultBean.resultNote);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //检查更新
    private void checkUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "checkUpdate");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<CheckUpdateBean>(mContext) {
            @Override
            public void onSuccess(Response response, CheckUpdateBean resultBean) {
                String content = resultBean.getDataObject().getUpdatecontent();
                String type = "0";
                String url = resultBean.getDataObject().getDownurl();
                //服务器版本号 2
                numberServer = Integer.parseInt(resultBean.getDataObject().getNum());
//                String version = resultBean.getVersion();

                //强制更新 0否 1是
                //String verName = APKVersionCodeUtils.getVerName(MainActivity.this);//1.1
                //本地版本号  1
                verCode = APKVersionCodeUtils.getVersionCode(SetActivity.this);
                Log.i(TAG, "onSuccess: "+verCode);

                if (verCode ==numberServer){
                    showToast("当前已是最新版本");
                }

                //int newVerName = Integer.parseInt(verName.replace(".", ""));//1.1----11


                switch (type) {
                    case "0":
                        xuanZe(numberServer, verCode, url);
                        break;
                    case "1":
                        qingZhi(numberServer, verCode, url);
                        break;
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.i(TAG, "onError: "+123456);
            }

        });
    }

    /*选择更新*/
    private void xuanZe(int numberServer, int verCode, String apkurl) {
        Log.i(TAG, "xuanZe: " + numberServer + "----" + verCode);
        if (numberServer > verCode) {
            Log.i(TAG, "xuanZe: 执行1111111111");
            AllenVersionChecker
                    .getInstance()
                    .downloadOnly(
                            UIData.create().setDownloadUrl(apkurl).setTitle(getString(R.string.faxianxinbanben)).setContent(getString(R.string.shifoulijigengxin))
                    ).setNotificationBuilder(
                    NotificationBuilder.create()
                            .setRingtone(true)
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTicker(getString(R.string.banbengengxin))
                            .setContentTitle(getString(R.string.banbengengxin))
                            .setContentText(getString(R.string.zhengzaixiazai))
            ).setShowDownloadingDialog(false).executeMission(mContext);
        } else {
            Log.i(TAG, "xuanZe: 执行22222222");
        }

    }

    /*强制更新*/
    private void qingZhi(int numberServer, int verCode, final String apkurl) {

        if (numberServer > verCode) {
            Log.i(TAG, "qingZhi: 执行33333");
            AlertDialog.Builder builder = new AlertDialog.Builder(SetActivity.this);
            builder.setTitle(getString(R.string.faxianxinbanben));


            //builder.setNegativeButton("取消", null);
            builder.setPositiveButton(getString(R.string.gengxin), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO 开始下载
                    startUpload(apkurl, APKVersionCodeUtils.getVersionCode(SetActivity.this));//下载最新的版本程序
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            Log.i(TAG, "qingZhi: 执行4444");
        }


    }


    /*应用下载*/
    private DownloadReceiver mReceiver;
    private ProgressDialog progressDialog;

    private void startUpload(String url, int versions) {
        Intent intent = new Intent();
        intent.setClass(SetActivity.this, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("name", getString(R.string.app_name) + versions);
        intent.putExtra("receiver", mReceiver);
        startService(intent);
        showProgressDialog();


    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(getString(R.string.zhengzaixiazai));
        progressDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            Log.i(TAG, "onActivityResult: 收到请求----执行安装");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "whsq.apk");
            AppUpdateUtils.installApp(SetActivity.this, file);
            //startInstall(file);
        }
    }


}
