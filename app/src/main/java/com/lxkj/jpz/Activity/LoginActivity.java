package com.lxkj.jpz.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.AuthorizeloginBean;
import com.lxkj.jpz.Bean.Commonbean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.MD5Util;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.Utils.ToastFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:2019.11.12
 * Describe :登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private TextView tv_regist,tv_forget,tv_login,tv_youxiang;
    private ImageView WX,QQ;
    private String jupshID;
    private EditText edit1,edit2;
    String QQName = "com.tencent.mobileqq";
    String WXName = "com.tencent.mm";
    private String type;
    /**
     * 授权监听
     */
    private UMShareAPI mShareAPI;
    private String thirdUid;
    private String nickName;
    private String userIcon;
    private String login_type;


    // 要申请的权限
    private String[] permissions = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,//读SD卡
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,//写SD卡
//            Manifest.permission.READ_PHONE_STATE,//获取手机状态
            Manifest.permission.CAMERA,//相机
            Manifest.permission.LOCATION_HARDWARE,//位置
            Manifest.permission.ACCESS_FINE_LOCATION,//位置
            Manifest.permission.ACCESS_COARSE_LOCATION,//位置
            Manifest.permission.CALL_PHONE,//打电话
//            Manifest.permission.READ_CALENDAR,//读日历
//            Manifest.permission.WRITE_CALENDAR,//写日历

    };
    //权限数组下标
    //权限申请返回码
    private int requestCodePre = 321;
    //系统设置权限申请返回码
    private int requestCodeSer = 123;
    private ImageView wx;
    private ImageView qq;
    private ImageView zfb;
    private Intent intent;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_login);
        baseTop.setVisibility(View.GONE);
        tv_regist = findViewById(R.id.tv_regist);
        tv_forget = findViewById(R.id.tv_forget);
        tv_login = findViewById(R.id.tv_login);
        WX = findViewById(R.id.WX);
        QQ = findViewById(R.id.QQ);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        tv_youxiang = findViewById(R.id.tv_youxiang);
        checkPermissons();
        jupshID = SPTool.getSessionValue(SQSP.JupshID);
        mShareAPI = UMShareAPI.get(mContext);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermissions.requestPermissions(this, SQSP.PMS_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            );
        } else {
            pmsLocationSuccess();
        }
    }

    @Override
    protected void initEvent() {
        tv_regist.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        WX.setOnClickListener(this);
        QQ.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_youxiang.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_regist://注册
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget://忘记密码
                Intent intent1 = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent1);
                break;
            case R.id.WX://三方登陆(微信)
//                type = "1";
//                if (!isAvilible(mContext, WXName)) {
//                    ToastFactory.getToast(mContext, "请安装微信客户端").show();
//                    return;
//                }
//                ToastFactory.getToast(mContext, "正在跳转微信登录,请稍后...").show();
//                UMShareAPI.get(mContext).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, umOauthListener);
                Intent intent2 = new Intent(mContext,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.QQ://三方登陆(QQ)
                type = "2";
                if (!isAvilible(mContext, QQName)) {
                    ToastFactory.getToast(mContext, "请安装QQ客户端").show();
                    return;
                }
                ToastFactory.getToast(mContext, "正在跳转QQ登录,请稍后...").show();
                UMShareAPI.get(mContext).doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, umOauthListener);
                break;
            case R.id.tv_login://登录
                if (StringUtil_li.isSpace(edit1.getText().toString())){
                    showToast(getString(R.string.Please_enter_your_mobile_phone_number));
                    return;
                }
                if (StringUtil_li.isSpace(edit2.getText().toString())){
                    showToast(getString(R.string.enter_your_PIN));
                    return;
                }
                userLogin(edit1.getText().toString(), MD5Util.encrypt(edit2.getText().toString()),jupshID ,"0","");
                break;
            case R.id.tv_youxiang://邮箱登录
                Intent intent3 = new Intent(mContext,MailboxActivity.class);
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @PermissionGrant(SQSP.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        //ToastFactory.getToast(mContext, "成功").show();
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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    //第三方登录
    private void userAuthorizeLogin(final String loginType, final String openId, String token, String equipid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd","userAuthorizeLogin");
        params.put("loginType", loginType);
        params.put("openId", openId);
        params.put("token", token);
        params.put("equipid", equipid);

        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<AuthorizeloginBean>(mContext) {
            @Override
            public void onSuccess(Response response, AuthorizeloginBean resultBean) {
                if (resultBean.getIsBind().equals("0")){
                    Intent intent3 = new Intent(LoginActivity.this,BindingActivity.class);
                    intent3.putExtra("loginType",loginType);
                    intent3.putExtra("openId",openId);
                    startActivity(intent3);
                    Log.i(TAG, "onSuccess: "+openId);
                }else {
                    String uid = resultBean.getUid();
                    SPTool.addSessionMap(SQSP.uid, uid);
                    SPTool.addSessionMap(SQSP.isLogin, true);
                    App.login = true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });


    }

    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }
    private UMAuthListener umOauthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "onStart: 授权");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            if (SHARE_MEDIA.QQ.equals(share_media))
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            else if (SHARE_MEDIA.WEIXIN.equals(share_media))
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
            else if (SHARE_MEDIA.SINA.equals(share_media))
                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.SINA, umAuthListener);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i(TAG, "onError: " + "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i(TAG, "onCancel: " + "授权取消");
        }
    };
    /**
     * 登陆监听
     */
    //三方类型 1微信 2QQ 3支付宝
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.i(TAG, "onStart: 登录");
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

            for (String key : map.keySet()) {
                System.out.println(TAG + "key= 返回的昵称" + key + " and value= " + map.get(key));
            }

            login_type = "1";
            //昵称
            nickName = map.get("name");
            Log.i(TAG, "onComplete: 返回的昵称" + nickName);
            //头像
            userIcon = map.get("iconurl");
            //第三方平台id
            thirdUid = map.get("uid");
            if (SHARE_MEDIA.QQ.equals(share_media)) {
                login_type = "0";
                userAuthorizeLogin(login_type,thirdUid,JPushInterface.getRegistrationID(LoginActivity.this),StringUtil_li.getSerialNumber());
            } else if (SHARE_MEDIA.WEIXIN.equals(share_media)) {
                login_type = "1";
                userAuthorizeLogin(login_type,thirdUid,JPushInterface.getRegistrationID(LoginActivity.this),StringUtil_li.getSerialNumber());
            } else if (SHARE_MEDIA.SINA.equals(share_media)) {
                login_type = "3";
            }

           /*
            if (gender.equals("男"))
                sex = "1";
            else
                sex = "2";*/
            //thirdLogin(NetMethodName.threeLogin,thirdUid, nickName, userIcon, login_type, sex);
            //0微信 1QQ

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i(TAG,"onError: " + "授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Log.i(TAG, "onCancel: " + "授权取消");
        }
    };

    /**
     * 检查运行时权限
     */
    private void checkPermissons() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean perimissionFlas = false;
            for (String permissionStr : permissions) {
                // 检查该权限是否已经获取
                int per = ContextCompat.checkSelfPermission(this, permissionStr);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (per != PackageManager.PERMISSION_GRANTED) {
                    perimissionFlas = true;
                }
            }
            if (perimissionFlas) {
                // 如果有权限没有授予允许，就去提示用户请求授权
                ActivityCompat.requestPermissions(this, permissions, requestCodePre);
            }
        }
    }


    /**
     * 用户权限申请的回调方法grantResults授权结果
     *
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数
     *                     组的长度，数组的数据PERMISSION_GRANTED表示允许权限，PERMISSION_DENIED表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestCodePre) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 判断该权限是否已经授权
                boolean grantFlas = false;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        //-----------存在未授权-----------
                        grantFlas = true;
                    }
                }
                if (grantFlas) {
                    //-----------未授权-----------
                    // 判断用户是否点击了不再提醒。(检测该权限是否还可以申请)
                    // shouldShowRequestPermissionRationale合理的解释应该是：如果应用之前请求过此权限
                    //但用户拒绝了请求且未勾选"Don’t ask again"(不在询问)选项，此方法将返回 true。
                    //注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中勾选了
                    //"Don’t ask again" 选项，此方法将返回 false。如果设备规范禁止应用具有该权限，此方法会返回 false。
                    boolean shouldShowRequestFlas = false;
                    for (String per : permissions) {
                        if (shouldShowRequestPermissionRationale(per)) {
                            //-----------存在未授权-----------
                            shouldShowRequestFlas = true;
                        }
                    }
                    if (shouldShowRequestFlas) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setTitle("提示");
                        builder.setMessage("当前还有必要权限没有授权，是否前往授权？");
                        builder.setCancelable(false);
                        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                goToAppSetting();
                            }
                        });
                        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else {
                    //-----------授权成功-----------
                    //Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, requestCodeSer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
