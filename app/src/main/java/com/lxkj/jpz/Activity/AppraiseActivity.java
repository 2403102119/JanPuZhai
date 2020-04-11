package com.lxkj.jpz.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.AppraiseAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.OrderCommentBean;
import com.lxkj.jpz.Bean.Orderdetailbean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.ImageItem;
import com.lxkj.jpz.Utils.NetUtil;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack;
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.Response;
import top.zibin.luban.Luban;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :去评价
 */
public class AppraiseActivity extends BaseActivity implements View.OnClickListener, UpLoadFileCallBack, AppraiseAdapter.AddImageListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    private AppraiseAdapter adapter;
    private TextView okID;
    List<Orderdetailbean.OrderDetailBean.OrderItemBean> list=new ArrayList<>();
    private String orderid;
    private static final String TAG = "AppraiseActivity";
    private int positon;
    private List<OrderCommentBean> commentBeans;
    private ArrayList<ImageItem> evaluateSelectPath = new ArrayList<>();//评价图片
    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 2;
    List<String> listUrl1 = new ArrayList<String>();
    private int type = 0;//选择图片类型 0 banner,1 goods
    ArrayList<String> YaSouList = new ArrayList<>();
    private UpFileUtil upFileUtil;
    private int select_number = 3;
    private List<String> urllist = new ArrayList<>();


    private void checkPmsExternalStorage() {
        MPermissions.requestPermissions(this, SQSP.PMS_CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }

    @PermissionGrant(SQSP.PMS_CAMERA)
    public void pmsExternalStorageSuccess() {
        //权限授权成功
        if (type == 0) {
            MultiImageSelector.create(mContext)
                    .showCamera(true) // 是否显示相机. 默认为显示
                    .count(select_number) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                    .multi() // 多选模式, 默认模式;
                    .origin(mSelectPath) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(AppraiseActivity.this, REQUEST_IMAGE);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initPhotoError() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initPhotoError();
        setContainer(R.layout.activity_appraise);

        setTopTitle(getString(R.string.evaluation));
        recycle = findViewById(R.id.recycle);
        okID = findViewById(R.id.okID);
        commentBeans = new ArrayList<>();
        upFileUtil = new UpFileUtil(this, this);
    }

    @Override
    protected void initEvent() {
        okID.setOnClickListener(this);
        layoutManager = new LinearLayoutManager(AppraiseActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new AppraiseAdapter(AppraiseActivity.this, list,this);
        recycle.setAdapter(adapter);
        adapter.setOnEvaluateListener(new AppraiseAdapter.EvaluateListener() {
            @Override
            public void onEvaluate(int position, String point, String content) {
                if (null != commentBeans) {
                    if (null != point)
                        commentBeans.get(position).setCommentScore(point.substring(0,1));
                    if (null != content)
                        commentBeans.get(position).setContent(content);
                }
            }
        });
    }

    @Override
    protected void initData() {
        orderid = getIntent().getStringExtra("orderid");
        orderDetail(orderid);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_IMAGE) {
                if (resultCode == RESULT_OK) {
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                    StringBuilder sb = new StringBuilder();
                    Log.i(TAG, "onActivityResult: "+positon);

                    evaluateSelectPath.clear();

                    for (String p : mSelectPath) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.setThumbnailPath(p);
                        sb.append(p);
                        sb.append("\n");
                        evaluateSelectPath.add(imageItem);
                    }
                }
                adapter.setImageData(positon, evaluateSelectPath);
                try {
                    if (mSelectPath.size() > 0) {
                        listUrl1.clear();
                    }
                    List<File> files = Luban.with(AppraiseActivity.this).load(mSelectPath).get();
                    YaSouList.clear();
                    for (int i = 0; i < files.size(); i++) {
                        YaSouList.add(files.get(i).toString());
                    }

                    if (NetUtil.isNetWork(AppraiseActivity.this)) {
                        upFileUtil.setListPath(YaSouList);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.okID:
                addOrderComment(orderid,commentBeans);
                break;
        }
    }

    //订单详情
    private void orderDetail(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "orderDetail");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Orderdetailbean>(mContext) {
            @Override
            public void onSuccess(Response response, Orderdetailbean resultBean) {

                for (int i = 0; i < resultBean.getOrderDetail().getOrderItem().size(); i++) {
                    OrderCommentBean orderCommentBean = new OrderCommentBean();
                    orderCommentBean.setItemId(resultBean.getOrderDetail().getOrderItem().get(i).getItemId());
                    orderCommentBean.setProductId(resultBean.getOrderDetail().getOrderItem().get(i).getProductId());
                    orderCommentBean.setCommentScore("5");
                    orderCommentBean.setContent(getString(R.string.This_user_has_no_comment));
                    commentBeans.add(orderCommentBean);
                }


                list.clear();
                list.addAll(resultBean.getOrderDetail().getOrderItem());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //添加订单评价
    private void addOrderComment(String orderid,List<OrderCommentBean> comment) {
        for (int i = 0; i < comment.size(); i++) {
            comment.get(i).setImages(comment.get(i).upLoadImages.toString().substring(1,comment.get(i).upLoadImages.toString().length() -1));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "addOrderComment");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("orderid",orderid);
        params.put("comment",comment);
        OkHttpHelper.getInstance().post_json_file(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    public void onAdd(int position, ArrayList<String> mSelectPath) {
        this.positon = position;
        this.mSelectPath = mSelectPath;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPmsExternalStorage();
        } else {
            pmsExternalStorageSuccess();
        }
    }

    @Override
    public void onRemove(int positon,int childPosition) {
        commentBeans.get(positon).upLoadImages.remove(childPosition);
        amendurl(childPosition);
    }


    @Override
    public void uoLoad(@NotNull String url) {
        Log.i(TAG, "uoLoad: 上传返回--------" + url);
    }

    @Override
    public void uoLoad(@NotNull List<String> url) {
        for (int i = 0; i < url.size() ; i++) {
            url.get(i).replace(" ","");
            urllist.add(url.get(i));
        }
        commentBeans.get(positon).upLoadImages.clear();
        commentBeans.get(positon).upLoadImages.addAll(urllist);
    }
    public void amendurl(int chposition){
        urllist.remove(chposition);
        commentBeans.get(positon).upLoadImages.clear();
        commentBeans.get(positon).upLoadImages.addAll(urllist);
        select_number = 3-urllist.size();
        Log.i(TAG, "amendurl: "+urllist);
    }

}
