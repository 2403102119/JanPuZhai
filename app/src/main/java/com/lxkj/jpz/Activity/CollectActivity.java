package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lxkj.jpz.Adapter.CollectAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.AboutUsbean;
import com.lxkj.jpz.Bean.Collectbean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.View.ActionDialog;
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
 * Describe :我的收藏
 */
public class CollectActivity extends BaseActivity {

    private RecyclerView collec_recycle;
    LinearLayoutManager layoutManager;
    CollectAdapter adapter;
    List<Collectbean.DataListBean> list=new ArrayList<>();
    private static final String TAG = "CollectActivity";
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private ActionDialog actionDialog;
    private String id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_collect);
        setTopTitle(getResources().getString(R.string.my_favorite));
        collec_recycle = findViewById(R.id.collec_recycle);
    }

    @Override
    protected void initEvent() {
        smart = findViewById(R.id.smart);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                myCollect(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    myCollect(String.valueOf(pageNoIndex));
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

        actionDialog = new ActionDialog(mContext,"提示","","确认删除","取消","确认");
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                actionDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                collectProduct(id);
            }
        });
        layoutManager = new LinearLayoutManager(CollectActivity.this);
        collec_recycle.setLayoutManager(layoutManager);
        adapter = new CollectAdapter(CollectActivity.this, list);
        collec_recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                if (list.get(firstPosition).getSkuList().size()==1){
                    addCart(list.get(firstPosition).getProductid(),list.get(firstPosition).getSkuList().get(0).getSkuId(),"1",list.get(firstPosition).getIspifa());
                }else {
                    Intent intent = new Intent(mContext, DeatilsActivity.class);
                    intent.putExtra("productid",list.get(firstPosition).getProductid());
                    intent.putExtra("type","1");
                    startActivity(intent);
                }
            }

            @Override
            public void OnDealte(int position) {
                id = list.get(position).getProductid();
                actionDialog.show();
            }

            @Override
            public void OnDetail(int position) {

                Intent intent = new Intent(mContext, DeatilsActivity.class);
                intent.putExtra("productid",list.get(position).getProductid());
                startActivity(intent);

            }


        });
    }

    @Override
    protected void initData() {
        myCollect("1");
    }


    //我的收藏
    private void myCollect(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myCollect");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Collectbean>(mContext) {
            @Override
            public void onSuccess(Response response, Collectbean resultBean) {

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

    //收藏/取消收藏/删除商品收藏
    private void collectProduct(String productId) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "collectProduct");
        params.put("productId", productId);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(mContext) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                myCollect("1");
                showToast(resultBean.getResultNote());
                actionDialog.dismiss();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //添加购物车
    private void addCart(String productid,String skuId,String count,String ispifa) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "addCart");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("skuId",skuId);
        params.put("count",count);
        params.put("ispifa",ispifa);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
