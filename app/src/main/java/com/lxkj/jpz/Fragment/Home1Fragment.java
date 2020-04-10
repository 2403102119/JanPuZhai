package com.lxkj.jpz.Fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jpz.Activity.DeatilsActivity;
import com.lxkj.jpz.Activity.MessageActivity;
import com.lxkj.jpz.Activity.OneActivity;
import com.lxkj.jpz.Activity.RelationActivity;
import com.lxkj.jpz.Activity.SearchActivity;
import com.lxkj.jpz.Activity.SerchActivity;
import com.lxkj.jpz.Activity.VipActivity;
import com.lxkj.jpz.Activity.WarehousesActivity;
import com.lxkj.jpz.Activity.WebViewActivity;
import com.lxkj.jpz.Adapter.GuessAdapter;
import com.lxkj.jpz.Adapter.Home1Adapter;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseFragment;
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.Bean.NoticeImagebean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.MainActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.lxkj.jpz.View.GlideImageLoader;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;


public class Home1Fragment extends BaseFragment implements View.OnClickListener,TextView.OnEditorActionListener{
    private final static String TAG = "Home1Fragment";
    private Banner banner,banner_item;
    List<String> BanString = new ArrayList<>();
    List<String> BanString_item = new ArrayList<>();
    private RecyclerView banner_recycle;
    LinearLayoutManager layoutManager;
    Home1Adapter adapter;
    List<FirsePagebean.JprouctListBean> list=new ArrayList<>();
    private TextView et_seek;
    private TextView tv1;
    private PopupWindow popupWindow;
    private LinearLayout ll_sell,ll_fenlei,ll_pifa;
    private LinearLayout ll_relation,ll_vip;
    private ImageView im_return;
    private String image_tejia,image_jingpin,image_zhekou,image_shangpin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        banner = view.findViewById(R.id.banner);

        ll_relation = view.findViewById(R.id.ll_relation);
        banner_recycle = view.findViewById(R.id.banner_recycle);

        et_seek = view.findViewById(R.id.et_seek);
        tv1 =  view.findViewById(R.id.tv1);
        banner_item =  view.findViewById(R.id.banner_item);

        im_return =  view.findViewById(R.id.im_return);
        ll_fenlei =  view.findViewById(R.id.ll_fenlei);
        ll_vip =  view.findViewById(R.id.ll_vip);
        ll_pifa =  view.findViewById(R.id.ll_pifa);

        String XingQu = SPTool.getSessionValue(SQSP.Shi);
        if (!TextUtils.isEmpty(XingQu)) {
            tv1.setText(XingQu);
        } else {
            tv1.setText("郑州市");
        }
//        tv_mercenary.setOnClickListener(this);
//        tv_warehouses.setOnClickListener(this);
//        tv_special.setOnClickListener(this);
        ll_relation.setOnClickListener(this);
        et_seek.setOnClickListener(this);
        im_return.setOnClickListener(this);
        ll_fenlei.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_pifa.setOnClickListener(this);
        et_seek.setOnEditorActionListener(this);

        layoutManager = new LinearLayoutManager(getActivity());
        banner_recycle.setLayoutManager(layoutManager);
        adapter = new Home1Adapter(getActivity(), list);
        banner_recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Home1Adapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int position) {
                if (list.get(position).getType().equals("0")){//特价专区
                    Intent intent = new Intent(getActivity(), WarehousesActivity.class);
                    intent.putExtra("image",image_tejia);
                    intent.putExtra("type","1");
                    intent.putExtra("title",getString(R.string.Special_zone));
                    startActivity(intent);
                }else if (list.get(position).getType().equals("1")){//精品专区
                    Intent intent = new Intent(getActivity(), WarehousesActivity.class);
                    intent.putExtra("image",image_jingpin);
                    intent.putExtra("type","2");
                    intent.putExtra("title",getString(R.string.Boutique_zone));
                    startActivity(intent);
                }else if (list.get(position).getType().equals("2")){//折扣专区
                    Intent intent = new Intent(getActivity(), WarehousesActivity.class);
                    intent.putExtra("image",image_zhekou);
                    intent.putExtra("type","3");
                    intent.putExtra("title",getString(R.string.Discount_zone));
                    startActivity(intent);
                }else {//商品专区
                    Intent intent = new Intent(getActivity(), WarehousesActivity.class);
                    intent.putExtra("image",image_shangpin);
                    intent.putExtra("type","4");
                    intent.putExtra("title",getString(R.string.Commodity_zone));
                    startActivity(intent);
                }

            }

            @Override
            public void OnDetailClickListener(int position, int item_position) {
                Intent intent = new Intent(getContext(), DeatilsActivity.class);
                intent.putExtra("productid",list.get(position).getPList().get(item_position).getProductid());
                startActivity(intent);
            }

        });


    }


    private void initData() {
        noticeImage();
        userLogin();


    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch(actionId){
            case EditorInfo.IME_ACTION_DONE:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    public  void  state(String url){
        popupWindow=new PopupWindow(getContext());
        View view=getLayoutInflater().inflate(R.layout.popup_image,null);
//        ll_sell_item= view.findViewById(R.id.ll_sell_item);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setClippingEnabled(false);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        ll_sell=view.findViewById(R.id.ll_sell);
        ImageView image = view.findViewById(R.id.image);
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(url)
                .into(image);
        ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.tv_mercenary://成为佣兵
//                if (SPTool.getSessionValue(SQSP.yongjin).equals("1")){
//                    Intent intent7 = new Intent(getActivity(), BrokerageActivity.class);
//                    startActivity(intent7);
//                }else {
//                    Intent intent = new Intent(getActivity(), MercenaryActivity.class);
//                    intent.putExtra("image4",image4);
//                    startActivity(intent);
//                }
//                break;
//            case R.id.tv_warehouses://品牌清仓

//                break;
//            case R.id.tv_special://特价限购
//                Intent intent2 = new Intent(getActivity(), SpecialActivity.class);
//                intent2.putExtra("image5",image5);
//                startActivity(intent2);
//                break;
            case R.id.ll_relation://联系我们
                Intent intent = new Intent(getContext(), RelationActivity.class);
                startActivity(intent);
                break;
            case R.id.et_seek://搜索
                Intent intent1 = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.im_return://消息
                Intent intent2 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_fenlei://全部分类
                ((MainActivity) getActivity()).viewPager.setCurrentItem(1);
                break;
            case R.id.ll_vip://申请VIP
                Intent intent3 = new Intent(getActivity(), VipActivity.class);
                intent3.putExtra("type","0");
                startActivity(intent3);
                break;
            case R.id.ll_pifa://申请批发
                Intent intent4 = new Intent(getActivity(),VipActivity.class);
                intent4.putExtra("type","1");
                startActivity(intent4);
                break;
        }
    }

    //首页初始化
    private void userLogin() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "firstPageinit");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<FirsePagebean>(getContext()) {
            @Override
            public void onSuccess(Response response, final FirsePagebean resultBean) {
                image_tejia = resultBean.getImage3();
                image_jingpin = resultBean.getImage4();
                image_zhekou = resultBean.getImage5();
                image_shangpin = resultBean.getImage6();

                BanString.clear();
                BanString_item.clear();
                for (int i = 0; i <resultBean.getBannerList1().size() ; i++) {
                    BanString.add(resultBean.getBannerList1().get(i).getImage());
                }
                for (int i = 0; i <resultBean.getBannerList2().size() ; i++) {
                    BanString_item.add(resultBean.getBannerList2().get(i).getImage());
                }
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner.setImageLoader(new GlideImageLoader());
                banner.setBannerAnimation(Transformer.DepthPage);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setDelayTime(5000);
                banner.isAutoPlay(true);
                banner.setIndicatorGravity(BannerConfig.CENTER);
                banner.setImages(BanString).setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(getContext(), DeatilsActivity.class);
                        intent.putExtra("productid",resultBean.getBannerList1().get(position).getProductid());
                        startActivity(intent);
                    }
                }).start();


                banner_item.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                banner_item.setImageLoader(new GlideImageLoader());
                banner_item.setBannerAnimation(Transformer.DepthPage);
                banner_item.setIndicatorGravity(BannerConfig.CENTER);
                banner_item.setDelayTime(5000);
                banner_item.isAutoPlay(true);
                banner_item.setIndicatorGravity(BannerConfig.CENTER);
                banner_item.setImages(BanString_item).setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Intent intent = new Intent(getContext(), WebViewActivity.class);
                        intent.putExtra("webTitle",getString(R.string.neirongxiangqing));
                        intent.putExtra("webUrl",resultBean.getBannerList2().get(position).getUrl());
                        startActivity(intent);
                    }
                }).start();

                list.clear();
                list.addAll(resultBean.getJprouctList());
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //首页通知图
    private void noticeImage() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "noticeImage");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<NoticeImagebean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final NoticeImagebean resultBean) {
                if (resultBean.getState().equals("1")){
                    state(resultBean.getImage());
                    ll_sell.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.activity_translate_in));
                    popupWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }




    @Override
    public void onDestroy() {
        super.onDestroy();

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

    private void showImage(final ImageView iv, int position) {
        List<Object> urls = new ArrayList<>();
        urls.addAll(BanString);
        new XPopup.Builder(getActivity()).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
            }
        }, new ImageLoader())
                .show();
    }


    class ImageLoader implements XPopupImageLoader {
        @Override
        public void loadImage(int position, @NonNull Object url, @NonNull ImageView imageView) {
            //必须指定Target.SIZE_ORIGINAL，否则无法拿到原图，就无法享用天衣无缝的动画
            Glide.with(imageView).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).override(Target.SIZE_ORIGINAL)).into(imageView);
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
