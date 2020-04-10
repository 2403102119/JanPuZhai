package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.lxkj.jpz.Adapter.MessageAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Messagebean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.View.SlideRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class MessageActivity extends BaseActivity implements View.OnClickListener{

    private SlideRecyclerView sr_message;
    LinearLayoutManager layoutManager;
    MessageAdapter adapter;
    List<Messagebean.DataListBean> list=new ArrayList<>();
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "MessageActivity";

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_message);
        setTopTitle(getString(R.string.xiaoxi));
        sr_message = findViewById(R.id.sr_message);
    }

    @Override
    protected void initEvent() {
        smart = findViewById(R.id.smart);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                messageList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    messageList(String.valueOf(pageNoIndex));
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
        layoutManager = new LinearLayoutManager(MessageActivity.this);
        sr_message.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(MessageActivity.this, list);
        sr_message.setAdapter(adapter);
        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
             Intent intent = new Intent(MessageActivity.this,Message_detaileActivity.class);
             intent.putExtra("title",list.get(firstPosition).getTitle());
             intent.putExtra("time",list.get(firstPosition).getAdtime());
             intent.putExtra("context",list.get(firstPosition).getContent());
             startActivity(intent);
            }

            @Override
            public void OnDelate(int position) {
                delMessage(list.get(position).getMsgid());
            }
        });
    }

    @Override
    protected void initData() {
        messageList("1");
    }

    @Override
    public void onClick(View v) {

    }

    //消息列表
    private void messageList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "messageList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Messagebean>(mContext) {
            @Override
            public void onSuccess(Response response, Messagebean resultBean) {
                list.clear();

                adapter.notifyDataSetChanged();

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

            }
        });
    }

    //删除信息
    private void delMessage(String msgid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "delMessage");
        params.put("msgid",msgid);

        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                messageList("1");
                showToast(resultBean.getResultNote());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
