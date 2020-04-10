package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Adapter.WarehouseAdapter;
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
import com.lxkj.jpz.View.RoundImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :品牌清仓
 */
public class WarehousesActivity extends BaseActivity {

    private RecyclerView recycle;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    WarehouseAdapter adapter;
    List<Areabean.DataListBean> list=new ArrayList<>();
    private String image,title,type;
    private RoundImageView tv_top;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "WarehousesActivity";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_warehouses);
        recycle = findViewById(R.id.recycle);
        tv_top = findViewById(R.id.tv_top);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                areaProductList(type,String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    areaProductList(type,String.valueOf(pageNoIndex));
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
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(staggeredGridLayoutManager);
        adapter = new WarehouseAdapter(WarehousesActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new WarehouseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(WarehousesActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        type = getIntent().getStringExtra("type");
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(image)
                .into(tv_top);

        setTopTitle(title);
        areaProductList(type,"1");
    }

    //专区商品列表
    private void areaProductList(String type,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaProductList");
        params.put("type", type);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Areabean>(mContext) {
            @Override
            public void onSuccess(Response response, Areabean resultBean) {
                smart.finishRefresh();

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
                smart.finishRefresh();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
