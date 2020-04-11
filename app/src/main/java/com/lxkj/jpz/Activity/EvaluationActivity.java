package com.lxkj.jpz.Activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jpz.Adapter.EvaluationAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Evluationbean;
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

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :我的评价
 */
public class EvaluationActivity extends BaseActivity {
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    EvaluationAdapter adapter;
    List<Evluationbean.DataListBean> list = new ArrayList<>();
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "EvaluationActivity";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_evaluation);
        setTopTitle(getResources().getString(R.string.My_evaluation));
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        smart = findViewById(R.id.smart);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                myComment(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    myComment(String.valueOf(pageNoIndex));
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
        layoutManager = new LinearLayoutManager(EvaluationActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new EvaluationAdapter(EvaluationActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new EvaluationAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {


            }

            @Override
            public void OnbuttonImage(int position) {//点击查看大图
                showImage(new ImageView(mContext), position);
            }


        });
    }

    @Override
    protected void initData() {
        myComment("1");
    }


    //我的评价
    private void myComment(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "myComment");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Evluationbean>(mContext) {
            @Override
            public void onSuccess(Response response, Evluationbean resultBean) {

                smart.finishRefresh();

                if (resultBean.getDataList() != null) {
                    totalPage = Integer.parseInt(resultBean.getTotalPage());
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
        urls.clear();
        urls.addAll(list.get(position).getImages());
        new XPopup.Builder(mContext).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
                //popupView.updateSrcView((ImageView) recyclerView0.getChildAt(position));
    }
}, new EvaluationActivity.ImageLoader())
                .show();
    }
    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.yongjinzhongxin).override(Target.SIZE_ORIGINAL)).into(imageView);
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
