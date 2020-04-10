package com.lxkj.jpz.Activity;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.RecordAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.MyFriendsBean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class RecordActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    RecordAdapter adapter;
    private List<MyFriendsBean.DataListBean> yaoqinglist = new ArrayList<>();
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "RecordActivity";
    private LinearLayout ll_zhijie,ll_jianjie;
    private TextView tv_zhijie,tv_jiajie;
    private View view_zhijie,view_jianjie;
    private String type = "0";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_record);
        setTopTitle(getResources().getString(R.string.invite));
        smart = findViewById(R.id.smart);
        recycle = findViewById(R.id.recycle);
        ll_zhijie = findViewById(R.id.ll_zhijie);
        ll_jianjie = findViewById(R.id.ll_jianjie);
        tv_zhijie = findViewById(R.id.tv_zhijie);
        view_zhijie = findViewById(R.id.view_zhijie);
        tv_jiajie = findViewById(R.id.tv_jiajie);
        view_jianjie = findViewById(R.id.view_jianjie);
    }

    @Override
    protected void initEvent() {
        ll_jianjie.setOnClickListener(this);
        ll_zhijie.setOnClickListener(this);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                myFriends(type,String.valueOf(pageNoIndex));
                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    myFriends(type,String.valueOf(pageNoIndex));
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
        layoutManager = new LinearLayoutManager(RecordActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new RecordAdapter(RecordActivity.this, yaoqinglist);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }


        });
    }

    @Override
    protected void initData() {
        myFriends(type,"1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_zhijie://直接用户
                tv_zhijie.setTextColor(getResources().getColor(R.color.red_them));
                tv_jiajie.setTextColor(getResources().getColor(R.color.grey3));
                view_zhijie.setVisibility(View.VISIBLE);
                view_jianjie.setVisibility(View.INVISIBLE);
                myFriends("0","1");
                type = "0";
                break;
            case R.id.ll_jianjie://间接用户
                tv_zhijie.setTextColor(getResources().getColor(R.color.grey3));
                tv_jiajie.setTextColor(getResources().getColor(R.color.red_them));
                view_zhijie.setVisibility(View.INVISIBLE);
                view_jianjie.setVisibility(View.VISIBLE);
                myFriends("1","1");
                type = "1";
                break;
        }
    }
    //我的邀请
    private void myFriends(String type,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myFriends");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", type);
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<MyFriendsBean>(mContext) {
            @Override
            public void onSuccess(Response response, MyFriendsBean resultBean) {
                smart.finishRefresh();
                if (resultBean.getDataList() != null) {
                    totalPage = resultBean.getTotalPage();
                    if (pageNoIndex == 1) {
                        yaoqinglist.clear();
                    }
                    yaoqinglist.addAll(resultBean.getDataList());
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
}
