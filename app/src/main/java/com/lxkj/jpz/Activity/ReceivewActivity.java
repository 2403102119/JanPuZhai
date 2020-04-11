package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.ShippingAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Addressbean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.View.ActionDialog;
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

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :添加地址
 */
public class ReceivewActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView address_recycle;
    LinearLayoutManager layoutManager;
    ShippingAdapter adapter;
    List<Addressbean.DataListBean> list=new ArrayList<>();
    private TextView tv_address;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    private static final String TAG = "ReceivewActivity";
    private String type,id;
    private ActionDialog actionDialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_receivew);
        setTopTitle(getResources().getString(R.string.shipping_address));
        address_recycle = findViewById(R.id.address_recycle);
        tv_address = findViewById(R.id.tv_address);
        smart = findViewById(R.id.smart);
    }

    @Override
    protected void initEvent() {
        tv_address.setOnClickListener(this);


        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;
                getAddressList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    getAddressList(String.valueOf(pageNoIndex));
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
                delAddress(id);
            }
        });
        layoutManager = new LinearLayoutManager(ReceivewActivity.this);
        address_recycle.setLayoutManager(layoutManager);
        adapter = new ShippingAdapter(ReceivewActivity.this, list);
        address_recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new ShippingAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                if (!StringUtil_li.isSpace(type)){
                    Intent intent = new Intent();
                    intent.putExtra("id",list.get(firstPosition).getAddressId());
                    intent.putExtra("name",list.get(firstPosition).getName());
                    intent.putExtra("telephone",list.get(firstPosition).getPhone());
                    intent.putExtra("address",list.get(firstPosition).getAddress());
                    intent.putExtra("addrDetail",list.get(firstPosition).getDetail());
                    setResult(222,intent);
                    finish();
                }else {

                }

            }

            @Override
            public void OnDelate(int firstPosition) {//删除
                id = list.get(firstPosition).getAddressId();
                actionDialog.show();
            }

            @Override
            public void OnRedact(int firstPosition) {//编辑
                Intent intent = new Intent(ReceivewActivity.this,Add_addressActivity.class);
                intent.putExtra("type","0");
                intent.putExtra("name",list.get(firstPosition).getName());
                intent.putExtra("phone",list.get(firstPosition).getPhone());
                intent.putExtra("address",list.get(firstPosition).getAddress());
                intent.putExtra("detail",list.get(firstPosition).getDetail());
                intent.putExtra("addressid",list.get(firstPosition).getAddressId());
                startActivity(intent);
            }

            @Override
            public void OnCheck(int firstPosition) {//设为默认地址
                if (list.get(firstPosition).getIsDefault().equals("0")){
                    updateAddress(list.get(firstPosition).getAddressId(),
                            list.get(firstPosition).getName(),
                            list.get(firstPosition).getPhone(),
                            list.get(firstPosition).getAddress(),
                            list.get(firstPosition).getDetail(),
                            "1");
                }else {
                    updateAddress(list.get(firstPosition).getAddressId(),
                            list.get(firstPosition).getName(),
                            list.get(firstPosition).getPhone(),
                            list.get(firstPosition).getAddress(),
                            list.get(firstPosition).getDetail(),
                            "0");
                }
            }
        });
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_address://添加地址
                Intent intent = new Intent(ReceivewActivity.this,Add_addressActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
        }
    }


    //收货地址列表
    private void getAddressList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "getAddressList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Addressbean>(mContext) {
            @Override
            public void onSuccess(Response response, Addressbean resultBean) {

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

    //修改收货地址
    private void updateAddress(String addressId,String name,String phone,String address,String detail,String isdefault) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateAddress");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("addressId", addressId);
        params.put("name",name);
        params.put("phone",phone);
        params.put("address",address);
        params.put("detail",detail);
        params.put("isdefault",isdefault);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<ResultBean>() {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                getAddressList("1");
            }

            @Override
            public void onFailure(Request request, Exception e) {

            }
            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //删除收货地址
    private void delAddress(String addressId) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "delAddress");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("addressId", addressId);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<ResultBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                getAddressList("1");
                actionDialog.dismiss();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList("1");
    }
}
