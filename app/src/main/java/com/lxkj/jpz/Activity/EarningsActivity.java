package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.Broketragedapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Brokeragebean;
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

public class EarningsActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    Broketragedapter adapter;
    List<Brokeragebean.WalletDetailBean> list=new ArrayList<>();
    private String type = "1";
    private TextView tv_login;
    private TextView tv_code,tv_one;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "BrokerageActivity";
    private String balance;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_earnings);
        setTopTitle(getResources().getString(R.string.My_income));
        recycle = findViewById(R.id.recycle);
        tv_code = findViewById(R.id.tv_code);
        tv_one = findViewById(R.id.tv_one);
        smart = findViewById(R.id.smart);
        tv_login = findViewById(R.id.tv_login);
    }

    @Override
    protected void initEvent() {
        tv_one.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                commissionAward(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    commissionAward(String.valueOf(pageNoIndex));
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

        layoutManager = new LinearLayoutManager(EarningsActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new Broketragedapter(EarningsActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Broketragedapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {


            }
        });
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_one:
                type = "1";
                commissionAward("1");
                break;
            case R.id.tv_login://提现
                Intent intent = new Intent(mContext,WithdrawActivity.class);
                intent.putExtra("balance",balance);
                startActivity(intent);
                break;
        }
    }


    //佣金奖励页面
    private void commissionAward(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "walletList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", "1");
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Brokeragebean>(mContext) {
            @Override
            public void onSuccess(Response response, Brokeragebean resultBean) {
                tv_code.setText("$"+resultBean.getBalance());
                balance = resultBean.getBalance();

                smart.finishRefresh();

                totalPage = Integer.parseInt(resultBean.getTotalPage());
                if (pageNoIndex == 1) {
                    list.clear();
                }
                list.addAll(resultBean.getWalletDetail());
                adapter.notifyDataSetChanged();

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
        commissionAward("1");
    }

}
