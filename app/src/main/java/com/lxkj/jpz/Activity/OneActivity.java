package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Adapter.SpecialAdapter;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Areabean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :专区一
 */
public class OneActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recycle;
    StaggeredGridLayoutManager layoutManager;
    SpecialAdapter adapter;
    List<Areabean.DataListBean> list=new ArrayList<>();
    private String image5;
    private ImageView im_top;
    private String type,image,position;
    private RelativeLayout rl_title;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "OneActivity";
    private String title;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_one);
        recycle = findViewById(R.id.recycle);
        im_top = findViewById(R.id.im_top);
        recycle = findViewById(R.id.recycle);
        rl_title = findViewById(R.id.rl_title);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
//        smart.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                pageNoIndex = 1;
//                areaProductList(String.valueOf(pageNoIndex),position);
//
//                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
//            }
//        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    areaProductList(String.valueOf(pageNoIndex),position);
                    Log.i(TAG, "onLoadMore: 执行上拉加载");
                    smart.finishLoadMore();
                } else {
                    Log.i(TAG, "onLoadMore: 相等不可刷新");
                    //smartRefreshLayout.setEnableLoadMore(false);
                    smart.finishRefresh(2000, true);//传入false表示刷新失败
                    smart.finishLoadMore();
                }
            }
        });

        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(layoutManager);
        adapter = new SpecialAdapter(OneActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new SpecialAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent = new Intent(OneActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    protected void initData() {

        position = "3";
        image5 = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        setTopTitle(title);
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(image5)
                .into(im_top);
    }
    //专区商品列表
    private void areaProductList(String nowPage,String position) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaProductList");
        params.put("type",position);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Areabean>(mContext) {
            @Override
            public void onSuccess(Response response, Areabean resultBean) {
//                smart.finishRefresh();

                if (resultBean.getDataList() != null) {

                    totalPage = resultBean.getTotalPage();
                    if (pageNoIndex == 1) {
                        list.clear();
                    }
                    list.addAll(resultBean.getDataList());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
//                smart.finishRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        areaProductList("1",position);
    }
}
