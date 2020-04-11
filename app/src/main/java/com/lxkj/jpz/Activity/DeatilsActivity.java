package com.lxkj.jpz.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxkj.jpz.Adapter.Recycle_oneAdapter;
import com.lxkj.jpz.Adapter.TuijianAdapter;
import com.lxkj.jpz.Adapter.ViewPagerAdatper;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Detailbean;
import com.lxkj.jpz.Bean.Param;
import com.lxkj.jpz.Bean.productcommentbean;
import com.lxkj.jpz.Bean.weixinbean;
import com.lxkj.jpz.Fragment.ShopBannerFragment;
import com.lxkj.jpz.Fragment.VideoFragment;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.Utils.TellUtil;
import com.lxkj.jpz.Utils.ToastFactory;
import com.lxkj.jpz.View.ActionDialog;
import com.lxkj.jpz.View.ChoiceParameterDialog;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static com.lxkj.jpz.App.context;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :商品详情
 */
public class DeatilsActivity extends BaseActivity implements View.OnClickListener {

    //    private Banner banner;
    List<String> BanString = new ArrayList<>();

    public void initSystemBar2(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private RecyclerView recycle,recycle_tuijian;
    LinearLayoutManager layoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Recycle_oneAdapter adapter;
    private List<productcommentbean.DataListBean> list=new ArrayList<>();
    private List<Detailbean.RecommentListBean> tuijian_list=new ArrayList<>();
    private LinearLayout ViewCui4,bottomView2,ll_comment,bottomView4;
    private String productid,shoucang;
    private TextView tv_price,tv_oldprice,tv_integral,tv_productName,tv_info,tv_detail,tv_pinglun,tv_Shopping_Cart,tv_guige;
    private WebView webView;
    private View view_detail,view_pinglun;
    private ImageView im_shoucang,im_shoucang3;
    private ChoiceParameterDialog choiceParameterDialog;
    List<Param.SkuBean> skuBeans = new ArrayList<>();
    List<Param.SpecBean> specBeans = new ArrayList<>();
    private  Param param = null;
    private String image_icon,ispifa;
    private String type = "0";
    private LinearLayout bottomView1;
    private String phone ="13303349099";
    private NestedScrollView ns;

    private ImageView finishBack;
    private TextView tv_time;
    private ActionDialog actionDialog;
    private ViewPagerAdatper viewPagerAdatper;
    private ViewPager viewpager;
    private TuijianAdapter tuijianAdapter;
    private LinearLayout ll_guige;
    private String sorttype = "1",addskuId,addcount,addname,addpractical,addspec;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_deatils);
        setTopTitle(getString(R.string.shangpinxiangqing));
//        banner = findViewById(R.id.banner);
        recycle = findViewById(R.id.recycle);
        recycle_tuijian = findViewById(R.id.recycle_tuijian);
        ViewCui4 = findViewById(R.id.ViewCui4);
        tv_price = findViewById(R.id.tv_price);
        tv_oldprice = findViewById(R.id.tv_oldprice);
        tv_integral = findViewById(R.id.tv_integral);
        tv_productName = findViewById(R.id.tv_productName);
        tv_info = findViewById(R.id.tv_info);
        webView = findViewById(R.id.webView);
        tv_detail = findViewById(R.id.tv_detail);
        tv_pinglun = findViewById(R.id.tv_pinglun);
        bottomView2 = findViewById(R.id.bottomView2);
        bottomView4 = findViewById(R.id.bottomView4);
        im_shoucang = findViewById(R.id.im_shoucang);
        bottomView1 = findViewById(R.id.bottomView1);
        finishBack = findViewById(R.id.finishBack);
        ll_comment = findViewById(R.id.ll_comment);
        viewpager = findViewById(R.id.viewpager);
        im_shoucang3 = findViewById(R.id.im_shoucang3);
        ll_guige = findViewById(R.id.ll_guige);
        tv_guige = findViewById(R.id.tv_guige);
        ns = findViewById(R.id.ns);
        tv_Shopping_Cart = findViewById(R.id.tv_Shopping_Cart);
        tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );



    }


    @Override
    protected void initEvent() {

        ViewCui4.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        tv_pinglun.setOnClickListener(this);
        bottomView2.setOnClickListener(this);
        bottomView4.setOnClickListener(this);
        tv_Shopping_Cart.setOnClickListener(this);
        bottomView1.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_guige.setOnClickListener(this);


        layoutManager = new LinearLayoutManager(mContext);
        recycle.setLayoutManager(layoutManager);
        adapter = new Recycle_oneAdapter(mContext, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Recycle_oneAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }

            @Override
            public void OnbuttonImage(int position, int item_position) {
                new PhotoPagerConfig.Builder(DeatilsActivity.this)
//                        .setBigImageUrls(list.get(position).getCommentImages())      //大图片url,可以是sd卡res，asset，网络图片.
                        .addSingleBigImageUrl(list.get(position).getCommentImages().get(item_position))
                        .setSavaImage(true)                                 //开启保存图片，默认false
                        .setPosition(position)                                     //默认展示第2张图片
                        .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                        .build();
            }

        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recycle_tuijian.setLayoutManager(staggeredGridLayoutManager);
        tuijianAdapter=new TuijianAdapter(mContext,tuijian_list);
        recycle_tuijian.setAdapter(tuijianAdapter);
        tuijianAdapter .setOnItemClickListener(new TuijianAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int item_position) {
                Intent intent = new Intent(mContext, DeatilsActivity.class);
                intent.putExtra("productid",tuijian_list.get(item_position).getProductid());
                startActivity(intent);
            }

        });
    }

    @Override
    protected void initData() {
        productid = getIntent().getStringExtra("productid");
        sorttype = getIntent().getStringExtra("sorttype");
        if (!StringUtil_li.isSpace(getIntent().getStringExtra("type"))){
            type = "1";
        }
        productDetail(productid);
        productCommentList(productid,"1");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ViewCui4://提交订单
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                if(choiceParameterDialog==null){
                    choiceParameterDialog = new ChoiceParameterDialog(this,param);
                    choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                        @Override
                        public void onSlectedChanged(boolean allSelected, String param) {

                        }
                        @Override
                        public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.piece));
                            choiceParameterDialog.dismiss();
                            addskuId = skuId;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;

                            Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                            intent.putExtra("type","0");
                            intent.putExtra("productid",productid);
                            intent.putExtra("skuId",addskuId);
                            intent.putExtra("count",addcount+"");
                            intent.putExtra("name",addname);
                            intent.putExtra("practical",addpractical);
                            intent.putExtra("spec",addspec);
                            intent.putExtra("image_icon",image_icon);
                            intent.putExtra("ispifa",ispifa);
                            startActivity(intent);
                        }
                        @Override
                        public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.piece));
                            choiceParameterDialog.dismiss();
                            addskuId = skuid;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;
                            addCart(productid,addskuId,addcount);
                        }

                        @Override
                        public void ondatu(String icon) {
                            if (!StringUtil_li.isSpace(icon)){
                                new PhotoPagerConfig.Builder(DeatilsActivity.this)
//                                        .setBigImageUrls(BanString)      //大图片url,可以是sd卡res，asset，网络图片.
                                        .addSingleBigImageUrl(icon)
                                        .setSavaImage(true)                                 //开启保存图片，默认false
                                        .setPosition(0)                                     //默认展示第2张图片
                                        .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                                        .build();
                            }
                        }
                    });
                }
                choiceParameterDialog.show();
                break;
            case R.id.bottomView2://收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                collectProduct(productid);
                break;
            case R.id.bottomView4://收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                collectProduct(productid);
                break;
            case R.id.tv_Shopping_Cart://加入购物车
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                if(choiceParameterDialog==null){
                    choiceParameterDialog = new ChoiceParameterDialog(this,param);
                    choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                        @Override
                        public void onSlectedChanged(boolean allSelected, String param) {

                        }

                        @Override
                        public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.piece));
                            choiceParameterDialog.dismiss();
                            addskuId = skuId;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;

                            Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                            intent.putExtra("type","0");
                            intent.putExtra("productid",productid);
                            intent.putExtra("skuId",addskuId);
                            intent.putExtra("count",addcount+"");
                            intent.putExtra("name",addname);
                            intent.putExtra("practical",addpractical);
                            intent.putExtra("spec",addspec);
                            intent.putExtra("image_icon",image_icon);
                            intent.putExtra("ispifa",ispifa);
                            startActivity(intent);
                        }

                        @Override
                        public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.piece));
                            choiceParameterDialog.dismiss();
                            addskuId = skuid;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;
                            addCart(productid,addskuId,addcount);
                        }

                        @Override
                        public void ondatu(String icon) {
                            if (!StringUtil_li.isSpace(icon)){
                                new PhotoPagerConfig.Builder(DeatilsActivity.this)
//                                        .setBigImageUrls(BanString)      //大图片url,可以是sd卡res，asset，网络图片.
                                        .addSingleBigImageUrl(icon)
                                        .setSavaImage(true)                                 //开启保存图片，默认false
                                        .setPosition(0)                                     //默认展示第2张图片
                                        .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                                        .build();
                            }
                        }
                    });
                }
                choiceParameterDialog.show();
                break;
            case R.id.ll_guige:
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                if(choiceParameterDialog==null){
                    choiceParameterDialog = new ChoiceParameterDialog(this,param);
                    choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                        @Override
                        public void onSlectedChanged(boolean allSelected, String param) {

                        }

                        @Override
                        public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.piece));
                            choiceParameterDialog.dismiss();
                            addskuId = skuId;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;

                            Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                            intent.putExtra("type","0");
                            intent.putExtra("productid",productid);
                            intent.putExtra("skuId",addskuId);
                            intent.putExtra("count",addcount+"");
                            intent.putExtra("name",addname);
                            intent.putExtra("practical",addpractical);
                            intent.putExtra("spec",addspec);
                            intent.putExtra("image_icon",image_icon);
                            intent.putExtra("ispifa",ispifa);
                            startActivity(intent);
                        }

                        @Override
                        public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                            tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.price));
                            choiceParameterDialog.dismiss();
                            addskuId = skuid;
                            addcount = String.valueOf(count);
                            addname = name;
                            addpractical = practical;
                            addspec = spec;
                            addCart(productid,addskuId,addcount);
                        }

                        @Override
                        public void ondatu(String icon) {
                            if (!StringUtil_li.isSpace(icon)){
                                new PhotoPagerConfig.Builder(DeatilsActivity.this)
//                                        .setBigImageUrls(BanString)      //大图片url,可以是sd卡res，asset，网络图片.
                                        .addSingleBigImageUrl(icon)
                                        .setSavaImage(true)                                 //开启保存图片，默认false
                                        .setPosition(0)                                     //默认展示第2张图片
                                        .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                                        .build();
                            }
                        }
                    });
                }
                choiceParameterDialog.show();
                break;
            case R.id.bottomView1://客服
                customer();
                break;
            case R.id.ll_comment://用户评论
                Intent intent = new Intent(mContext, EvaluateActivity.class);
                intent.putExtra("productid",productid);
                startActivity(intent);
                break;


        }
    }
    @PermissionGrant(SQSP.PMS_LOCATION)
    public void pmsLocationSuccess() {
        //权限授权成功
        TellUtil.tell(mContext, phone);
    }
    /*拨打电话*/
    private void callPhone() {
        if (null != phone) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MPermissions.requestPermissions(this, SQSP.PMS_LOCATION,
                        Manifest.permission.CALL_PHONE
                );
            } else {
                pmsLocationSuccess();
            }
        }
    }
    private void showImage(final ImageView iv, int position) {
        List<Object> urls = new ArrayList<>();
        urls.addAll(BanString);
        new XPopup.Builder(DeatilsActivity.this).asImageViewer(iv, position, urls, new OnSrcViewUpdateListener() {
            @Override
            public void onSrcViewUpdate(ImageViewerPopupView popupView, int position) {
                popupView.updateSrcView(iv);
            }
        }, new DeatilsActivity.ImageLoader())
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
    private void setGoodsData(final Detailbean resultBean) {

        ArrayList<Fragment> fragmentsList = new ArrayList<>();
        if (TextUtils.isEmpty(resultBean.getProductDetail().getVideo())) {
        } else {
            fragmentsList.add(VideoFragment.newInstance(resultBean.getProductDetail().getVideo(), resultBean.getProductDetail().getVideo()+"?x-oss-process=video/snapshot,t_1000,f_jpg,w_0,h_0,m_fast"));
        }
        if (resultBean.getProductDetail().getProductImages().size() > 0)
            fragmentsList.add(ShopBannerFragment.newInstance(resultBean.getProductDetail().getProductImages()));
        viewPagerAdatper = new ViewPagerAdatper(getSupportFragmentManager(), fragmentsList);
        viewpager.setAdapter(viewPagerAdatper);
        //滚动时判断轮播页面是否可见
        ns.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断某个控件是否可见
                Rect scrollBounds = new Rect();
                ns.getHitRect(scrollBounds);
                if (viewpager.getLocalVisibleRect(scrollBounds)) {//可见
                } else {//完全不可见

//                    EventBus.getDefault().post(new EventBusVideo("关闭视频的播放"));
                }
            }
        });

    }
    //商品详情
    private void productDetail(String productid) {
        final Map<String, String> params = new HashMap<>();
        params.put("cmd", "productDetail");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Detailbean>(mContext) {
            @Override
            public void onSuccess(Response response, Detailbean resultBean) {

                ispifa = resultBean.getProductDetail().getIspifa();

                BanString.clear();
                for (int i = 0; i <resultBean.getProductDetail().getProductImages().size() ; i++) {
                    BanString.add(resultBean.getProductDetail().getProductImages().get(i));
                }
                setGoodsData(resultBean);

                shoucang = resultBean.getProductDetail().getIsCollect();
                if (shoucang.equals("1")){
                    im_shoucang.setImageResource(R.mipmap.shoucang1);
                    im_shoucang3.setImageResource(R.mipmap.shoucang);
                }else {
                    im_shoucang.setImageResource(R.mipmap.yishoucang1);
                    im_shoucang3.setImageResource(R.mipmap.yishoucang);
                }
                tv_price.setText(resultBean.getProductDetail().getPrice());
                tv_oldprice.setText("$ "+resultBean.getProductDetail().getOldPrice());
                tv_integral.setText(getString(R.string.yishou)+resultBean.getProductDetail().getSales()+getString(R.string.piece));
                tv_productName.setText(resultBean.getProductDetail().getProductName());
                tv_info.setText(resultBean.getProductDetail().getInfo());
                webSetting(resultBean.getProductDetail().getUrl());

                param = new Param();
                specBeans= new ArrayList<>();
                skuBeans = new ArrayList<>();

                Param.SpecBean specBean1 = new Param.SpecBean();
                Param.SpecBean specBean2 = new Param.SpecBean();

                List<String> speclist1= new ArrayList<>();
                List<String> speclist2 = new ArrayList<>();
                specBean1.setSpecName(resultBean.getProductDetail().getSname1());
                if (!StringUtil.isEmpty(resultBean.getProductDetail().getSname2())){
                    specBean2.setSpecName(resultBean.getProductDetail().getSname2());
                }
                for (int i = 0; i <resultBean.getProductDetail().getSkuList().size() ; i++) {
                    if (!speclist1.contains(resultBean.getProductDetail().getSkuList().get(i).getSkuName1()))
                        speclist1.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName1());
                    if (!StringUtil.isEmpty(resultBean.getProductDetail().getSkuList().get(i).getSkuName2())){
                        if (!speclist2.contains(resultBean.getProductDetail().getSkuList().get(i).getSkuName2()))
                            speclist2.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName2());
                    }
                    List<String> spec = new ArrayList<>();
                    spec.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName1());
                    if (!StringUtil.isEmpty(resultBean.getProductDetail().getSkuList().get(i).getSkuName2()))
                        spec.add(resultBean.getProductDetail().getSkuList().get(i).getSkuName2());
                    Param.SkuBean skuBean = new Param.SkuBean(resultBean.getProductDetail().getSkuList().get(i).getSkuStock(),resultBean.getProductDetail().getSkuList().get(i).getSkuPrice(),
                            resultBean.getProductDetail().getSkuList().get(i).getSkuId(),spec);

                    skuBeans.add(skuBean);
                }
                specBean1.setSpecValue(speclist1);
                specBean2.setSpecValue(speclist2);
                specBeans.add(specBean1);
                if (!StringUtil.isEmpty(specBean2.getSpecName()))
                    specBeans.add(specBean2);
                param.setSpec(specBeans);
                param.setSku(skuBeans);
                param.setIcon(resultBean.getProductDetail().getProductImages().get(0));
                param.setName(resultBean.getProductDetail().getProductName());
                image_icon = resultBean.getProductDetail().getProductImages().get(0);
                param.setPrice(resultBean.getProductDetail().getPrice());
                param.setStartbuy(resultBean.getProductDetail().getStartbuy());
                param.setIspifa(resultBean.getProductDetail().getIspifa());

                tuijian_list.clear();
                tuijian_list.addAll(resultBean.getRecommentList());
                tuijianAdapter.notifyDataSetChanged();

                if (type.equals("1")){
                    if(choiceParameterDialog==null){
                        choiceParameterDialog = new ChoiceParameterDialog(mContext,param);
                        choiceParameterDialog.setSelectedListener(new ChoiceParameterDialog.SelectedListener() {
                            @Override
                            public void onSlectedChanged(boolean allSelected, String param) {

                            }
                            @Override
                            public void onComfirm(int count, String skuId,String name,String practical,String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.price));
                                choiceParameterDialog.dismiss();
                                addskuId = skuId;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;

                                Intent intent = new Intent(DeatilsActivity.this,OrderOkActivity.class);
                                intent.putExtra("type","0");
                                intent.putExtra("productid",productid);
                                intent.putExtra("skuId",addskuId);
                                intent.putExtra("count",addcount+"");
                                intent.putExtra("name",addname);
                                intent.putExtra("practical",addpractical);
                                intent.putExtra("spec",addspec);
                                intent.putExtra("image_icon",image_icon);
                                intent.putExtra("ispifa",ispifa);
                                startActivity(intent);
                            }
                            @Override
                            public void ongouwuche(int count, String skuid, String name, String practical, String spec) {
                                tv_guige.setText(name+"    "+spec+"    "+count+getString(R.string.price));
                                choiceParameterDialog.dismiss();
                                addskuId = skuid;
                                addcount = String.valueOf(count);
                                addname = name;
                                addpractical = practical;
                                addspec = spec;
                                addCart(productid,addskuId,addcount);
                            }

                            @Override
                            public void ondatu(String icon) {
                                if (!StringUtil_li.isSpace(icon)){
                                    new PhotoPagerConfig.Builder(DeatilsActivity.this)
//                                        .setBigImageUrls(BanString)      //大图片url,可以是sd卡res，asset，网络图片.
                                            .addSingleBigImageUrl(icon)
                                            .setSavaImage(true)                                 //开启保存图片，默认false
                                            .setPosition(0)                                     //默认展示第2张图片
                                            .setOpenDownAnimate(false)                          //是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                                            .build();
                                }
                            }
                        });
                    }
                    choiceParameterDialog.show();
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //商品评价列表
    private void productCommentList(String productid,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productCommentList");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", "0");
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<productcommentbean>(mContext) {
            @Override
            public void onSuccess(Response response, productcommentbean resultBean) {
                if (resultBean.getDataList().size()!= 0){
                    ll_comment.setVisibility(View.VISIBLE);
                    recycle.setVisibility(View.VISIBLE);
                    list.clear();
                    list.add(resultBean.getDataList().get(0));
                    adapter.notifyDataSetChanged();
                }else {
                    ll_comment.setVisibility(View.VISIBLE);
                    recycle.setVisibility(View.GONE);
                }


            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //收藏/取消收藏/删除商品收藏
    private void collectProduct(String productid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "collectProduct");
        params.put("productid",productid);
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                if (shoucang.equals("0")){
                    im_shoucang.setImageResource(R.mipmap.yishoucang1);
                    im_shoucang3.setImageResource(R.mipmap.yishoucang);
                    shoucang = "1";
                }else {
                    im_shoucang.setImageResource(R.mipmap.shoucang1);
                    im_shoucang3.setImageResource(R.mipmap.shoucang);
                    shoucang = "0";
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //联系客服
    private void customer() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "customer");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<weixinbean>(mContext) {
            @Override
            public void onSuccess(Response response, final weixinbean resultBean) {
//                actionDialog = new ActionDialog(mContext,resultBean.getWX());
//                actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
//                    @Override
//                    public void onLeftClick() {//一键复制
//                        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//                        String text;
//                        text = resultBean.getWX();
//
//                        ClipData myClip = ClipData.newPlainText("text", text);
//                        myClipboard.setPrimaryClip(myClip);
//                        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
//                        actionDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onRightClick() {//确定
//                        actionDialog.dismiss();
//                    }
//                });
//                actionDialog.show();
                phone = resultBean.getPhone();
                callPhone();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //添加购物车
    private void addCart(String productid,String skuId,String count) {
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

    private void webSetting(String url){
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        webSettings.setSupportZoom(false);// 支持缩放
//        webSettings.setTextSize(WebSettings.TextSize.LARGEST);//显示字体大小

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else{
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

}
