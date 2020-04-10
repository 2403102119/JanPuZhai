package com.lxkj.jpz.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.LeftAdapter;
import com.lxkj.jpz.Adapter.RightAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.Bean.Productlistbean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.ToastFactory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;

public class ClassifyActivity extends BaseActivity implements View.OnClickListener{

    private String childCategoryId,title,categoryId,edStr;
    private RecyclerView recyclerViewRight;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private List<String> stairlist = new ArrayList<>();
    private List<String> stairidlist = new ArrayList<>();
    private List<Productlistbean.DataListBean> secondlist = new ArrayList<>();
    private LinearLayout ll_sales,ll_price;
    private TextView tv_zonghe,tv_xiaoliang,tv_jiage;
    private ImageView im_shang,im_xia;
    private static final String TAG = "ClassifyActivity";
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private String sprttype;
    private String type,position1 = "1",position_jiage = "3";
    private String ID;
    private EditText et_search;
    private ImageView im_back;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_classify);

        baseTop.setVisibility(View.GONE);

        recyclerViewRight = findViewById(R.id.RecyclerViewRight);
        ll_sales = findViewById(R.id.ll_sales);
        tv_zonghe = findViewById(R.id.tv_zonghe);
        tv_xiaoliang = findViewById(R.id.tv_xiaoliang);
        tv_jiage = findViewById(R.id.tv_jiage);
        ll_price = findViewById(R.id.ll_price);
        im_xia = findViewById(R.id.im_xia);
        im_shang = findViewById(R.id.im_shang);
        smart = findViewById(R.id.smart);
        et_search = findViewById(R.id.et_search);
        im_back = findViewById(R.id.im_back);
    }

    @Override
    protected void initEvent() {
        ll_sales.setOnClickListener(this);
        tv_zonghe.setOnClickListener(this);
        ll_price.setOnClickListener(this);
        im_back.setOnClickListener(this);

        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;

                productList(categoryId,position1,"1");

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    productList(categoryId,position1,"1");
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
        recyclerViewRight.setLayoutManager(staggeredGridLayoutManager);
        rightAdapter=new RightAdapter(mContext,secondlist);
        recyclerViewRight.setAdapter(rightAdapter);
        rightAdapter .setOnItemClickListener(new RightAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {

                Intent intent = new Intent(ClassifyActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",secondlist.get(position).getProductid());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {




        title = getIntent().getStringExtra("title");
        categoryId = getIntent().getStringExtra("categoryId");
        type = getIntent().getStringExtra("type");
        edStr = getIntent().getStringExtra("edStr");
        if (type.equals("0")){
            searchProduct(edStr,"0","1");
        }else {
            productList(categoryId,"0","1");
        }


        et_search.setText(edStr);


        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(et_search.getText().toString())) {
                        if (type.equals("0")){
                            searchProduct(et_search.getText().toString(),position1,"1");
                        }else {
                            productList(categoryId,position1,"1");
                        }
                    } else {
                        ToastFactory.getToast(mContext, "关键字不能为空").show();
                    }
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_sales://销量
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.red_them));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));

                if (position1.equals("1")){
                    position1 = "2";
                    im_shang.setImageResource(R.mipmap.shang);
                }else {
                    position1 = "1";
                    im_shang.setImageResource(R.mipmap.xia);
                }
                pageNoIndex = 1;
                if (type.equals("0")){
                    searchProduct(et_search.getText().toString(),position1,"1");
                }else {
                    productList(categoryId,position1,"1");
                }
                break;
            case R.id.tv_zonghe://综合
                tv_zonghe.setTextColor(getResources().getColor(R.color.red_them));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.black));
                pageNoIndex = 1;
                if (type.equals("0")){
                    searchProduct(et_search.getText().toString(),"0","1");
                }else {
                    productList(categoryId,"0","1");
                }
                break;
            case R.id.ll_price://价格
                tv_zonghe.setTextColor(getResources().getColor(R.color.black));
                tv_xiaoliang.setTextColor(getResources().getColor(R.color.black));
                tv_jiage.setTextColor(getResources().getColor(R.color.red_them));
                im_xia.setImageResource(R.mipmap.xia);
                if (position_jiage.equals("3")){
                    position1 = "4";
                    position_jiage = "4";
                    im_xia.setImageResource(R.mipmap.shang);
                }else {
                    position_jiage = "3";
                    im_xia.setImageResource(R.mipmap.xia);
                    position1 = "3";
                }
                pageNoIndex = 1;
                if (type.equals("0")){
                    searchProduct(et_search.getText().toString(),position1,"1");
                }else {
                    productList(categoryId,position1,"1");
                }
                break;
            case R.id.im_back:
                finish();
                break;
        }
    }

    //根据分类id获取商品列表
    private void productList(String childCategoryId,String sortType,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productList");
        params.put("childCategoryId",childCategoryId);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Productlistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Productlistbean resultBean) {

                smart.finishRefresh();

                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    secondlist.clear();
                }
                secondlist.addAll(resultBean.getDataList());
                rightAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
    //搜索商品
    private void searchProduct(String keywords,String sortType,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "searchProduct");
        params.put("keywords",keywords);
        params.put("sortType",sortType);
        params.put("nowPage",nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Productlistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Productlistbean resultBean) {

                smart.finishRefresh();

                totalPage = resultBean.getTotalPage();
                if (pageNoIndex == 1) {
                    secondlist.clear();
                }
                secondlist.addAll(resultBean.getDataList());
                rightAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }
}
