package com.lxkj.jpz.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Activity.ClassifyActivity;
import com.lxkj.jpz.Adapter.SecondAdapter;
import com.lxkj.jpz.Adapter.StairAdapter;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseFragment;
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.Bean.NoticeImagebean;
import com.lxkj.jpz.Bean.productCategorybean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;

/**
 * Created ：李迪迦
 * on:2019.11.13
 * Describe :分类
 */
public class Home2Fragment extends BaseFragment implements View.OnClickListener{

    private ImageView back,im_title_image;
    private TextView title;
    private RecyclerView recyclerViewLeft;
    private RecyclerView recyclerViewRight;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private StairAdapter stairAdapter;
    private SecondAdapter secondAdapter;
    private List<productCategorybean.DataListBean> stairlist = new ArrayList<>();
    private List<productCategorybean.DataListBean.ChildrenListBean> secondlist = new ArrayList<>();
    private String toptit,categoryId;
    int selectePosition = 0;
    List<productCategorybean.DataListBean> allSecondlist = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class, container, false);
//        Utility2.setActionBar(getContext());
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        back = view.findViewById(R.id.back);
        title = view.findViewById(R.id.title);
        recyclerViewLeft = view.findViewById(R.id.RecyclerViewLeft);
        recyclerViewRight = view.findViewById(R.id.RecyclerViewRight);
        im_title_image = view.findViewById(R.id.im_title_image);

        back.setVisibility(View.GONE);

    }
    private void initData() {



        productCategory();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewLeft.setLayoutManager(layoutManager);
        stairAdapter = new StairAdapter(getActivity(), stairlist);
        recyclerViewLeft.setAdapter(stairAdapter);
        stairAdapter.setOnItemClickListener(new StairAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                selectePosition = position;
                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(stairlist.get(position).getCategoryImage())
                        .into(im_title_image);
                title.setText(stairlist.get(position).getCategoryName());
                secondlist.clear();
                secondlist.addAll(allSecondlist.get(position).getChildrenList());
                secondAdapter.notifyDataSetChanged();
            }

        });

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewRight.setLayoutManager(staggeredGridLayoutManager);
        secondAdapter=new SecondAdapter(getActivity(),secondlist);
        recyclerViewRight.setAdapter(secondAdapter);
        secondAdapter .setOnItemClickListener(new SecondAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
                Intent intent = new Intent(getActivity(), ClassifyActivity.class);
                intent.putExtra("categoryId", secondlist.get(position).getChildCategoryId());
                intent.putExtra("title",secondlist.get(position).getChildCategoryName());
                intent.putExtra("bean", (Serializable) allSecondlist.get(selectePosition).getChildrenList());
                intent.putExtra("type","1");
                intent.putExtra("edStr"," ");
                startActivity(intent);
            }

        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }


    //商品分类
    private void productCategory() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productCategory");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<productCategorybean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final productCategorybean resultBean) {
                allSecondlist = resultBean.getDataList();
//                productCategorybean allCategoryListBean = new productCategorybean();

                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(resultBean.getDataList().get(0).getCategoryImage())
                        .into(im_title_image);
                stairlist.clear();
                secondlist.clear();
                stairlist.addAll(resultBean.getDataList());
                secondlist.addAll(resultBean.getDataList().get(0).getChildrenList());
                secondAdapter.notifyDataSetChanged();
                stairAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {

        } else if (!isVisibleToUser) {
            onPause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }



}
