package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.WalletAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Walletbean;
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

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :我的钱包
 */
public class WalletActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    WalletAdapter adapter;
    List<Walletbean.DataListBean> list=new ArrayList<>();
    private TextView tv_alipay,tv_bank,tv_balance;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "WalletActivity";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_wallet);
        setTopTitle("我的钱包");
        recycle = findViewById(R.id.recycle);
        tv_alipay = findViewById(R.id.tv_alipay);
        tv_bank = findViewById(R.id.tv_bank);
        tv_balance = findViewById(R.id.tv_balance);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
        tv_alipay.setOnClickListener(this);
        tv_bank.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                walletList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    walletList(String.valueOf(pageNoIndex));
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

        layoutManager = new LinearLayoutManager(WalletActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new WalletAdapter(WalletActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new WalletAdapter.OnItemClickListener() {
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
            case R.id.tv_alipay://提现到支付宝
                Intent intent = new Intent(WalletActivity.this,Withdraw_alipayActivity.class);
                intent.putExtra("money",tv_balance.getText().toString());
                startActivity(intent);
                break;
            case R.id.tv_bank://提现到银行卡
                Intent intent1 = new Intent(WalletActivity.this,Withdraw_bankActivity.class);
                intent1.putExtra("money",tv_balance.getText().toString());
                startActivity(intent1);
                break;
        }
    }


    //钱包明细页
    private void walletList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "walletList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Walletbean>(mContext) {
            @Override
            public void onSuccess(Response response, Walletbean resultBean) {
                tv_balance.setText(resultBean.getBalance());
                smart.finishRefresh();
                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    list.clear();
                }
                list.addAll(resultBean.getDataList());

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
        walletList("1");
    }
}
