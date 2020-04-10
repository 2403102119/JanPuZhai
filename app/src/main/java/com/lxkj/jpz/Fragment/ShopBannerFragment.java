package com.lxkj.jpz.Fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.lxkj.jpz.R;
import com.lxkj.jpz.View.GlideImageLoader;
import com.lxkj.jpz.View.NoLoopBanner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author ZXY
 * @since 2019/12/13
 * Email：1120202716@qq.com
 * function：发现轮播图
 */
public class ShopBannerFragment extends LazyFragment {

    @BindView(R.id.banner)
    NoLoopBanner mBanner;
    private List<String> bannerList;
    private ArrayList<String> mBannerList;


    public static ShopBannerFragment newInstance(List<String> bannerList) {
        ShopBannerFragment fragmentCommon = new ShopBannerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bannerList", (Serializable) bannerList);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    protected void onUserVisible() {

    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_banner;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            bannerList = (List<String>) bundle.getSerializable("bannerList");
        }
        setBanner();
    }

    private void setBanner() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
//        banner2.setBannerAnimation(Transformer.CubeOut);
        //设置标题集合（当banner样式有显示title时）
//        banner2.setBannerTitles("当banner样式有显示title时");
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        //点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                new PhotoPagerConfig.Builder(getActivity())
                                .setBigImageUrls(mBannerList)      //大图片url,可以是sd卡res，asset，网络图片.
//                        .addSingleBigImageUrl(list.get(1))
                                .setSavaImage(true)                                 //开启保存图片，默认false
                                .setPosition(position)                                     //默认展示第2张图片
                                .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                                .build();
            }
        });
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                mBanner.toRealPosition(i);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mBannerList = new ArrayList<>();
//        for (ShopFirstPageMoel.BannerListBean bannerListBean : bannerList) {
//            mBannerList.add(bannerListBean.getImage());
//        }
        mBannerList .addAll(bannerList) ;
        mBanner.setImages(mBannerList);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

}
