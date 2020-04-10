package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.NoticeImagebean;
import com.lxkj.jpz.Bean.contactCustomerBena;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :联系我们
 */
public class RelationActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_activit,ll_mendian;
    private TextView tv_dianhua,tv_wx,tv_shijian,tv_youxiang,tv_Face;
    private String url;
    private List<contactCustomerBena.DataListBean> mendianlist = new ArrayList<>();
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_relation);
        setTopTitle(getString(R.string.contact_us));
        ll_activit = findViewById(R.id.ll_activit);
        tv_dianhua = findViewById(R.id.tv_dianhua);
        tv_wx = findViewById(R.id.tv_wx);
        tv_shijian = findViewById(R.id.tv_shijian);
        tv_youxiang = findViewById(R.id.tv_youxiang);
        tv_Face = findViewById(R.id.tv_Face);
        ll_mendian = findViewById(R.id.ll_mendian);
    }

    @Override
    protected void initEvent() {
        ll_activit.setOnClickListener(this);
        ll_mendian.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        contactCustomer();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_activit://线下活动
                if (StringUtil_li.isSpace(url)){
                    showToast(getString(R.string.zanwuxianxiahuodong));
                    return;
                }
                Intent intent = new Intent(mContext,activitActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                break;
            case R.id.ll_mendian://门店列表
                Intent intent1 = new Intent(mContext,Store_listActivity.class);
                startActivity(intent1);
                break;
        }
    }


    //联系我们
    private void contactCustomer() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "contactCustomer");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<contactCustomerBena>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final contactCustomerBena resultBean) {
                tv_dianhua.setText(resultBean.getPhone());
                tv_wx.setText(resultBean.getWX());
                tv_shijian.setText(resultBean.getWorktime());
                tv_youxiang.setText(resultBean.getEmail());
                tv_Face.setText(resultBean.getFacebook());
                url = resultBean.getUrl();
                SQSP.shouyelist = resultBean.getDataList();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
