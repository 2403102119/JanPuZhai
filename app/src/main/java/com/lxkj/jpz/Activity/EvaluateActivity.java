package com.lxkj.jpz.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jpz.Adapter.Recycle_oneAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.productcommentbean;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EvaluateActivity extends BaseActivity implements View.OnClickListener{

    private RecyclerView re_1;
    LinearLayoutManager layoutManager;
    Recycle_oneAdapter adapter;
    List<productcommentbean.DataListBean> list=new ArrayList<>();
    List<Map<String,Object>> list2=new ArrayList<>();
    private String productId;
    private static final String TAG = "EvaluateActivity";
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;

    private TextView tv_sell,tv_praise,tv_medium,tv_bad;
    private String productid,type = "0"	;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_evaluate);
        re_1 = findViewById(R.id.recycle);
        setTopTitle(getString(R.string.yonghupinglun));
        smart = findViewById(R.id.smart);
        tv_sell = findViewById(R.id.tv_sell);
        tv_praise = findViewById(R.id.tv_praise);
        tv_medium = findViewById(R.id.tv_medium);
        tv_bad = findViewById(R.id.tv_bad);
    }

    @Override
    protected void initEvent() {
        tv_sell.setOnClickListener(this);
        tv_praise.setOnClickListener(this);
        tv_medium.setOnClickListener(this);
        tv_bad.setOnClickListener(this);
        /*下拉*/
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                commentList(productId,type	,String.valueOf(pageNoIndex));
                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });


        //上拉
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    commentList(productId,type	,String.valueOf(pageNoIndex));
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
        layoutManager = new LinearLayoutManager(EvaluateActivity.this);
        re_1.setLayoutManager(layoutManager);
        adapter = new Recycle_oneAdapter(EvaluateActivity.this, list);
        re_1.setAdapter(adapter);
        adapter.setOnItemClickListener(new Recycle_oneAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {


            }

            @Override
            public void OnbuttonImage(int position,int item_position) {//点击查看大图
//                showImage(new ImageView(mContext), position);
                new PhotoPagerConfig.Builder(EvaluateActivity.this)
//                        .setBigImageUrls(list.get(position).getCommentImages())      //大图片url,可以是sd卡res，asset，网络图片.
                        .addSingleBigImageUrl(list.get(position).getCommentImages().get(item_position))
                        .setSavaImage(true)                                 //开启保存图片，默认false
                        .setPosition(position)                                     //默认展示第2张图片
                        .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                        .build();
            }


        });
    }

    @Override
    protected void initData() {
        productId = getIntent().getStringExtra("productid");
        commentList(productId,type	,"1");
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.tv_sell://全部
                 tv_sell.setBackgroundResource(R.drawable.biankuang1);

                 tv_praise.setBackgroundResource(R.drawable.biankuang24);
                 tv_medium.setBackgroundResource(R.drawable.biankuang24);
                 tv_bad.setBackgroundResource(R.drawable.biankuang24);
                 type = "0";
                 pageNoIndex = 1;
                 commentList(productId,type,"1");
                 break;
             case R.id.tv_praise://好评
                 tv_sell.setBackgroundResource(R.drawable.biankuang24);
                 tv_medium.setBackgroundResource(R.drawable.biankuang24);
                 tv_praise.setBackgroundResource(R.drawable.biankuang1);
                 type = "1";
                 pageNoIndex = 1;
                 commentList(productId,type,"1");
                 break;
             case R.id.tv_medium://中评
                 tv_sell.setBackgroundResource(R.drawable.biankuang24);
                 tv_praise.setBackgroundResource(R.drawable.biankuang24);
                 tv_medium.setBackgroundResource(R.drawable.biankuang1);

                 break;
             case R.id.tv_bad://差评
                 tv_sell.setBackgroundResource(R.drawable.biankuang24);
                 tv_praise.setBackgroundResource(R.drawable.biankuang24);
                 tv_medium.setBackgroundResource(R.drawable.biankuang24);
                 tv_bad.setBackgroundResource(R.drawable.biankuang1);
                 type = "2";
                 pageNoIndex = 1;
                 commentList(productId,type,"1");
                 break;
         }
    }

    //商品评价列表
    private void commentList(String productid,String type,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productCommentList");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", type	);
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<productcommentbean>(mContext) {
            @Override
            public void onSuccess(Response response, productcommentbean resultBean) {
                tv_sell.setText(getString(R.string.all)+resultBean.getAllNum());
                tv_praise.setText(getString(R.string.haoping)+resultBean.getGoodNum());
                tv_bad.setText(getString(R.string.chaping)+resultBean.getBadNum());
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

    private void showImage(final ImageView iv, int position) {
        List<Object> urls = new ArrayList<>();
//        urls.addAll(list.get(position).getImages());
        new XPopup.Builder(mContext).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
                //popupView.updateSrcView((ImageView) recyclerView0.getChildAt(position));
            }
        }, new EvaluateActivity.ImageLoader())
                .show();
    }
    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.logo).override(Target.SIZE_ORIGINAL)).into(imageView);
        }

        @Override
        public File getImageFile(@NonNull Context context, @NonNull Object uri) {
            try {
                return Glide.with(context).downloadOnly().load(uri).submit().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
