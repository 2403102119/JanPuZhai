package com.lxkj.jpz.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Adapter.MerecenaryAdapter;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Areabean;
import com.lxkj.jpz.Bean.CommissionBean;
import com.lxkj.jpz.Http.BaseCallback;
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

import okhttp3.Request;
import okhttp3.Response;

public class MercenaryActivity extends BaseActivity implements View.OnClickListener {

    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private RecyclerView recycle;
    List<Areabean.DataListBean> list=new ArrayList<>();
    private MerecenaryAdapter adapter;
    private FrameLayout fl_top;
    private String image4;
    private RoundImageView ri_table;
    private NestedScrollView ns;
    private int fadingHeight = 600; // 当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）
    private Drawable drawable; // 顶部渐变布局需设置的Drawable
    private RelativeLayout rl_title;//导航栏
    private static final int START_ALPHA = 0;//scrollview滑动开始位置
    private static final int END_ALPHA = 255;//scrollview滑动结束位置
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "MercenaryActivity";

    /**
     * 主题沉浸栏设置透明 * @return
     */
    public void initSystemBar2(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private String ContentUrl;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_mercenary);
        initSystemBar2(this);
        baseTop.setVisibility(View.GONE);
        ImageView finishBack = findViewById(R.id.finishBack);
        TextView titleName = findViewById(R.id.titleName);
        finishBack.setVisibility(View.VISIBLE);
        finishBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleName.setText("成为佣兵");
        recycle = findViewById(R.id.recycle);
        fl_top = findViewById(R.id.fl_top);
        ri_table = findViewById(R.id.ri_table);
        rl_title = findViewById(R.id.rl_title);
        ns = findViewById(R.id.ns);
        smart = findViewById(R.id.smart);
//        drawable = getResources().getDrawable(R.color.white);
//        drawable.setAlpha(START_ALPHA);
//        rl_title.setBackgroundDrawable(drawable);
//        //调用方法
//        ns.setOnScrollChangeListener(scrollChangedListener);
    }
    /**
     * ScrollView的滚动监听
     */
    private NestedScrollView.OnScrollChangeListener scrollChangedListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
            if (i1 > fadingHeight) {
                i1 = fadingHeight; // 当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
            } else if (i1 < 0) {
                i1 = 0;
            } else {
            }
            drawable.setAlpha(i1 * (END_ALPHA - START_ALPHA) / fadingHeight
                    + START_ALPHA);
        }
    };

    @Override
    protected void initEvent() {

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                areaProductList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    areaProductList(String.valueOf(pageNoIndex));
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

        ri_table.setOnClickListener(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle.setLayoutManager(staggeredGridLayoutManager);
        adapter=new MerecenaryAdapter(MercenaryActivity.this,list);
        recycle.setAdapter(adapter);
        adapter .setOnItemClickListener(new MerecenaryAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))) {
                    ToastFactory.getToast(mContext, "请先登录").show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent = new Intent(MercenaryActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",list.get(position).getProductid());
                startActivity(intent);
            }

        });


    }

    @Override
    protected void initData() {
        image4 = getIntent().getStringExtra("image4");
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(image4)
                .into(ri_table);
        commissionRule();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ri_table:
                Intent intent = new Intent(mContext,WebViewActivity.class);
                intent.putExtra("webTitle","佣金规则");
                intent.putExtra("webUrl",ContentUrl);
                startActivity(intent);
                break;
        }
    }

    //专区商品列表
    private void areaProductList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "areaProductList");
        params.put("type", "0");
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
    //佣金规则
    private void commissionRule() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "commissionRule");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<CommissionBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, CommissionBean resultBean) {
                ContentUrl=resultBean.getContentUrl();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        areaProductList("1");
    }
}
