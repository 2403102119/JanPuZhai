package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.lxkj.jpz.Adapter.OrderOkAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Addressbean;
import com.lxkj.jpz.Bean.Cartbean;
import com.lxkj.jpz.Bean.Freightbean;
import com.lxkj.jpz.Bean.ProductBuyBean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.Utils.ToastFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :确认订单界面
 */
public class OrderOkActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_payment;
    private String type;//判断从哪个页面跳转过来，0  商品详情   1  购物车
    private String ispifa;
    private View il_detail;
    private RecyclerView recycle;
    private TextView tv1,tv2,tv3,tv_count,tv_shi,tv_yunfei,tv_site,tv_name,tv_phone,tv_site_detail,tv_total;
    private RoundedImageView image2;
    private String productid,skuId,count,name,practical,spec,image_icon,address_id,zongji,amount,yunfei,zong;
    private LinearLayout ll_Address,ll_intent_site;
    private TextView tv_beizhu;
    private ArrayList array,idlist;
    LinearLayoutManager layoutManager;
    OrderOkAdapter adapter;
    List<Cartbean.DataListBean> list=new ArrayList<>();
    private EditText et_liuyan;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_order_ok);
        setTopTitle(getString(R.string.querendingdan));
        tv_payment = findViewById(R.id.tv_payment);
        il_detail = findViewById(R.id.il_detail);
        recycle = findViewById(R.id.recycle);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv_count = findViewById(R.id.tv_count);
        image2 = findViewById(R.id.image2);
        tv_shi = findViewById(R.id.tv_shi);
        tv_yunfei = findViewById(R.id.tv_yunfei);
        tv_site = findViewById(R.id.tv_site);
        ll_Address = findViewById(R.id.ll_Address);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_site_detail = findViewById(R.id.tv_site_detail);
        ll_intent_site = findViewById(R.id.ll_intent_site);
        tv_total = findViewById(R.id.tv_total);
        tv_beizhu = findViewById(R.id.tv_beizhu);
        et_liuyan = findViewById(R.id.et_liuyan);
    }

    @Override
    protected void initEvent() {
        tv_payment.setOnClickListener(this);
        ll_intent_site.setOnClickListener(this);

        layoutManager = new LinearLayoutManager(OrderOkActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new OrderOkAdapter(OrderOkActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OrderOkAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }
        });
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");//  0 详情  1 购物车
        ispifa = getIntent().getStringExtra("ispifa");//  0 否  1 是
        if (type.equals("0")){
            il_detail.setVisibility(View.VISIBLE);
            recycle.setVisibility(View.GONE);
            productid = getIntent().getStringExtra("productid");
            skuId = getIntent().getStringExtra("skuId");
            count = getIntent().getStringExtra("count");
            name = getIntent().getStringExtra("name");
            practical = getIntent().getStringExtra("practical");
            spec = getIntent().getStringExtra("spec");
            image_icon = getIntent().getStringExtra("image_icon");
            Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions()
                    .error(R.mipmap.logo)
                    .placeholder(R.mipmap.logo))
                    .load(image_icon)
                    .into(image2);
            tv1.setText(name);
            tv2.setText(getString(R.string.guige)+": "+spec);
            tv3.setText("$"+practical);
            tv_count.setText("×"+ count);
            BigDecimal shulaing = new BigDecimal(count);
            BigDecimal jiage = new BigDecimal(practical);
            BigDecimal zong = shulaing.multiply(jiage);
            zongji = zong.toString();
            tv_shi.setText("$"+zong.toString());
        }else {
            array = (ArrayList) getIntent().getSerializableExtra("list");
            idlist = (ArrayList) getIntent().getSerializableExtra("idlist");
            zong = getIntent().getStringExtra("zong");
            tv_shi.setText("$"+zong.toString());
            zongji = zong;
            list.clear();
            list.addAll(array);
            adapter.notifyDataSetChanged();



            il_detail.setVisibility(View.GONE);
            recycle.setVisibility(View.VISIBLE);
        }

        getFreight();
        getAddressList("1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_payment://付款
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                if (StringUtil_li.isSpace(address_id)){
                    showToast(getString(R.string.qingxuanzeshouhuodizhi));
                    return;
                }
                if (type.equals("0")){
                    productBuy(productid,skuId,count,et_liuyan.getText().toString(),amount,address_id,yunfei,ispifa);
                }else {
                    addCartOrder(idlist,et_liuyan.getText().toString(),zongji,address_id,yunfei,ispifa);
                }

                break;
            case R.id.ll_intent_site://选择收货地址
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(OrderOkActivity.this,ReceivewActivity.class);
                intent1.putExtra("type","1");
                startActivityForResult(intent1,111);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111&&resultCode == 222){
            address_id = data.getStringExtra("id");
            tv_name.setText(data.getStringExtra("name"));
            tv_phone.setText(StringUtil_li.replacePhone(data.getStringExtra("telephone")));
            tv_site_detail.setText(data.getStringExtra("address")+data.getStringExtra("addrDetail"));
            tv_site.setVisibility(View.GONE);
            ll_Address.setVisibility(View.VISIBLE);
        }
    }
    //获取平台运费
    private void getFreight() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "getFreight");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<Freightbean>(){
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, Freightbean resultBean) {
                if (Float.parseFloat(resultBean.getAmount())>0){
                    if (Float.parseFloat(resultBean.getMinMoney())<Float.parseFloat(zongji)){
                        tv_yunfei.setText(getString(R.string.baoyou));
                        yunfei = "0";
                        amount = zongji;
                        BigDecimal yunfei = new BigDecimal(resultBean.getAmount());
                        BigDecimal jiage = new BigDecimal(zongji);
                        BigDecimal zongji = yunfei.add(jiage);
                        tv_total.setText(zongji.toString());
                        tv_beizhu.setText("$"+zongji.toString());

                    }else {
                        amount = zongji;
                        tv_yunfei.setText("$"+resultBean.getAmount());
                        yunfei = resultBean.getAmount();
                        BigDecimal yunfei = new BigDecimal(resultBean.getAmount());
                        BigDecimal jiage = new BigDecimal(zongji);
                        BigDecimal zongji = yunfei.add(jiage);
                        tv_total.setText(zongji.toString());
                        tv_beizhu.setText("$"+zongji.toString());
                        amount = zongji.toString();
                    }
                }else {
                    yunfei = "0";
                    amount = zongji;
                    tv_yunfei.setText(getString(R.string.baoyou));
                    tv_beizhu.setText("$"+zongji.toString());
                    tv_total.setText("$"+zongji.toString());
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //收货地址列表
    private void getAddressList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "getAddressList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<Addressbean>(){
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, Addressbean resultBean) {
                for (int i = 0; i <resultBean.getDataList().size() ; i++) {
                    if (resultBean.getDataList().get(i).getIsDefault().equals("1")){
                        tv_site.setVisibility(View.GONE);
                        ll_Address.setVisibility(View.VISIBLE);
                        tv_name.setText(resultBean.getDataList().get(i).getName());
                        tv_phone.setText(StringUtil_li.replacePhone(resultBean.getDataList().get(i).getPhone()));
                        tv_site_detail.setText(resultBean.getDataList().get(i).getAddress()+resultBean.getDataList().get(i).getDetail());
                        address_id = resultBean.getDataList().get(i).getAddressId();
                        return;
                    }else {
                        tv_site.setVisibility(View.VISIBLE);
                        ll_Address.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //提交立即购买
    private void productBuy(String productId, String skuId, String count, String remark, final String amount, String addressId, String fright,String ispifa) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "productBuy");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("productId", productId);
        params.put("skuId", skuId);
        params.put("count", count);
        params.put("remark", remark);
        params.put("amount", amount);
        params.put("addressId", addressId);
        params.put("fright", fright);
        params.put("ispifa", ispifa);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ProductBuyBean>(mContext) {
            @Override
            public void onSuccess(Response response, ProductBuyBean resultBean) {
                Intent intent = new Intent(mContext,RechargeActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("moeny",amount);
                intent.putExtra("orderId",resultBean.getOrderId());
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    //提交购物车结算
    private void addCartOrder(List<String> cartid, String remark, final String amount, String addressId, String fright,String ispifa) {
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "addCartOrder");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("cartid",cartid);
        params.put("remark",remark);
        params.put("amount",amount);
        params.put("addressId",addressId);
        params.put("fright",fright);
        params.put("ispifa",ispifa);
        OkHttpHelper.getInstance().post_json_file(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ProductBuyBean>(mContext) {
            @Override
            public void onSuccess(Response response, ProductBuyBean resultBean) {

                Intent intent = new Intent(mContext,RechargeActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("moeny",amount);
                intent.putExtra("orderId",resultBean.getOrderId());
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
