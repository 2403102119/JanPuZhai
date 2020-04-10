package com.lxkj.jpz.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lxkj.jpz.Activity.LoginActivity;
import com.lxkj.jpz.Activity.OrderOkActivity;
import com.lxkj.jpz.Adapter.ShoppingAdapter;
import com.lxkj.jpz.Base.BaseFragment;
import com.lxkj.jpz.Bean.Cartbean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.lxkj.jpz.View.ActionDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;


public class Home3Fragment extends BaseFragment implements View.OnClickListener{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    ShoppingAdapter adapter;
    ArrayList<Cartbean.DataListBean> list=new ArrayList<>();
    ArrayList<Cartbean.DataListBean> list_intent=new ArrayList<>();
    private TextView tuiJianTV,titleName;
    private ImageView finishBack;
    private ArrayList<String> cartidlist = new ArrayList<>();
    private LinearLayout ll_wu;
    private static final String TAG = "Home3Fragment";
    private ImageView imageSel;
    private boolean imageSelcheck;
    private TextView tv_accounts,tv_sell_moeney;
    private List<String> zongjine = new ArrayList<>();
    private List<Float> moneylist = new ArrayList<>();
    private List<String> pifalist = new ArrayList<>();
    private String zong,pifa;
    private ActionDialog actionDialog;
    private SmartRefreshLayout smart;
    private int pageNoIndex = 1;
    private int totalPage = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_colleg, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        recycle = view.findViewById(R.id.recycle);
        tuiJianTV = view.findViewById(R.id.tuiJianTV);
        titleName = view.findViewById(R.id.titleName);
        finishBack = view.findViewById(R.id.finishBack);
        ll_wu = view.findViewById(R.id.ll_wu);
        imageSel = view.findViewById(R.id.imageSel);
        tv_accounts = view.findViewById(R.id.tv_accounts);
        tv_sell_moeney = view.findViewById(R.id.tv_sell_moeney);
        smart = view.findViewById(R.id.smart);
        finishBack.setVisibility(View.INVISIBLE);
        tuiJianTV.setText(getResources().getString(R.string.delete));
        titleName.setText(getResources().getString(R.string.shopping_trolley));
        tuiJianTV.setTextColor(getResources().getColor(R.color.white));
        tuiJianTV.setVisibility(View.VISIBLE);

        tv_accounts.setOnClickListener(this);
        tuiJianTV.setOnClickListener(this);
        imageSel.setOnClickListener(this);

        smart.setEnableNestedScroll(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNoIndex = 1;

                getCartList(String.valueOf(pageNoIndex));

                Log.i(TAG, "onRefresh: 执行下拉刷新方法");
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNoIndex < totalPage) {
                    pageNoIndex++;
                    getCartList(String.valueOf(pageNoIndex));
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

        layoutManager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(layoutManager);
        adapter = new ShoppingAdapter(getActivity(),list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
            @Override
            public void OnDetal(int position) {

                if (list.get(position).isIscheck() == true){
                    cartidlist.add(list.get(position).getCartId());
                    pifa = list.get(position).getIspifa();
                    pifalist.add(list.get(position).getIspifa());
                    BigDecimal qian = new BigDecimal(list.get(position).getPrice());
                    BigDecimal shulaing = new BigDecimal(list.get(position).getCount());
                    BigDecimal jine = qian.multiply(shulaing);
                    moneylist.add(jine.floatValue());
                    list_intent.add(list.get(position));
                }else {
                    cartidlist.remove(list.get(position).getCartId());
                    pifalist.remove(list.get(position).getIspifa());
                    BigDecimal qian = new BigDecimal(list.get(position).getPrice());
                    BigDecimal shulaing = new BigDecimal(list.get(position).getCount());
                    BigDecimal jine = qian.multiply(shulaing);
                    moneylist.remove(jine.floatValue());
                    list_intent.remove(list.get(position));
                }
                float a = 0;
                for (int i = 0; i <moneylist.size() ; i++) {
                    a += moneylist.get(i);
                }
                BigDecimal bigDecimal = new BigDecimal(a);
                tv_sell_moeney.setText(bigDecimal.setScale(2,RoundingMode.HALF_DOWN).toString());
                zong = a+"";

                imageSelcheck = true;
                for (int i = 0; i <list.size() ; i++) {
                    if (list.get(i).isIscheck() == false){
                        imageSelcheck = false;
                    }
                }
                if (imageSelcheck)
                    imageSel.setImageResource(R.mipmap.xuanzhong);
                else
                    imageSel.setImageResource(R.mipmap.weixuan);
                Log.i(TAG, "OnDetal: "+cartidlist);
            }

            @Override
            public void Onselect(int position, String mount) {
                updateCart(list.get(position).getCartId(),mount,position);
            }

            @Override
            public void OnDelate(int position) {
                cartidlist.clear();
                cartidlist.add(list.get(position).getCartId());
                actionDialog.show();
            }
        });
    }
    private void initData() {
        actionDialog = new ActionDialog(getContext(),"提示","","确认删除","取消","确认");
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                actionDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                delCart(cartidlist);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageSel:
                imageSelcheck = !imageSelcheck;
                cartidlist.clear();
                list_intent.clear();
                moneylist.clear();
                pifalist.clear();

                if (imageSelcheck == true){
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setIscheck(true);
                        BigDecimal qian = new BigDecimal(list.get(i).getPrice());
                        BigDecimal shulaing = new BigDecimal(list.get(i).getCount());
                        BigDecimal jine = qian.multiply(shulaing);
                        moneylist.add(jine.floatValue());
                        //list_intent = list;
                        cartidlist.add(list.get(i).getCartId());
                        pifa = list.get(0).getIspifa();
                        pifalist.add(list.get(i).getIspifa());
                    }
                    list_intent.addAll(list);
                    imageSel.setImageResource(R.mipmap.xuanzhong);
                }else {
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setIscheck(false);
                        BigDecimal qian = new BigDecimal(list.get(i).getPrice());
                        BigDecimal shulaing = new BigDecimal(list.get(i).getCount());
                        BigDecimal jine = qian.multiply(shulaing);
                        moneylist.remove(jine.floatValue());
                        list_intent.clear();
                    }
                    cartidlist.clear();
                    list_intent.clear();
                    moneylist.clear();
                    pifalist.clear();
                    imageSel.setImageResource(R.mipmap.weixuan);
                }
                float a = 0;
                for (int i = 0; i <moneylist.size() ; i++) {
                    a += moneylist.get(i);
                }
                BigDecimal bigDecimal = new BigDecimal(a);

//                SpannableString spannableString = new SpannableString(String.valueOf(a));
//                if (String.valueOf(a).contains(".")) {
//                    spannableString.setSpan(new RelativeSizeSpan(0.8f), String.valueOf(a).indexOf("."), String.valueOf(a).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//                tv_sell_moeney.setText(spannableString);
                tv_sell_moeney.setText(bigDecimal.setScale(2, RoundingMode.HALF_DOWN).toString());
                zong = a+"";
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_accounts://结算
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                if (cartidlist.size() == 0){
                    Toast.makeText(getContext(),"请选择商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i <pifalist.size() ; i++) {
                    if (!pifalist.get(0).equals(pifalist.get(i))){
                        Toast.makeText(getContext(),"批发商品和普通商品不能一起购买",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                Intent intent = new Intent(getActivity(), OrderOkActivity.class);
                intent.putExtra("type","1");
                intent.putExtra("list", list_intent);
                intent.putExtra("idlist", cartidlist);
                intent.putExtra("zong", zong);
                intent.putExtra("ispifa", pifa);
                startActivity(intent);
                break;
            case R.id.tuiJianTV://删除
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                if (cartidlist.size() == 0){
                    Toast.makeText(getContext(),"请选择需要删除的商品",Toast.LENGTH_SHORT).show();
                    return;
                }
                delCart(cartidlist);
                break;

        }
    }

    //购物车列表
    private void getCartList(String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "getCartList");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("nowPage",nowPage);
        params.put("pageCount",SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<Cartbean>(getContext()) {
            @Override
            public void onSuccess(Response response, Cartbean resultBean) {
                smart.finishRefresh();
                cartidlist.clear();
                list_intent.clear();
                moneylist.clear();

                if (resultBean.getDataList().size()!=0){
                    recycle.setVisibility(View.VISIBLE);
                    ll_wu.setVisibility(View.GONE);
                    totalPage = resultBean.getTotalPage();
                    if (pageNoIndex == 1) {
                        list.clear();
                    }
                    list.addAll(resultBean.getDataList());
                    adapter.notifyDataSetChanged();

                }else {
                    tv_sell_moeney.setText("0.00");
                    recycle.setVisibility(View.GONE);
                    ll_wu.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                smart.finishRefresh();
            }
        });
    }

    //购物车商品数量修改
    private void updateCart(String cartid,String count,int position) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "updateCart");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("cartid",cartid);
        params.put("count",count);
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new BaseCallback<ResultBean>() {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
//                getCartList("1");
                int a = 0;
                BigDecimal bigDecimal2 = null;

                if (list.get(position).isIscheck() == true){
                    if (Integer.parseInt(list.get(position).getCount())>Integer.parseInt(count)){
                        a = Integer.parseInt(list.get(position).getCount()) - Integer.parseInt(count);
                        BigDecimal bigDecimal = new BigDecimal(tv_sell_moeney.getText().toString());
                        BigDecimal bigDecimal1 = new BigDecimal(a);
                        BigDecimal bigDecimal3 = new BigDecimal(list.get(position).getPrice());
                        BigDecimal bigDecimal4 = bigDecimal1.multiply(bigDecimal3);
                        bigDecimal2 = bigDecimal.subtract(bigDecimal4);
                        tv_sell_moeney.setText(bigDecimal2.setScale(2, RoundingMode.HALF_DOWN).toString());
                    }else  if (Integer.parseInt(list.get(position).getCount())<Integer.parseInt(count)){
                        a = Integer.parseInt(count)-Integer.parseInt(list.get(position).getCount());
                        BigDecimal bigDecimal = new BigDecimal(tv_sell_moeney.getText().toString());
                        BigDecimal bigDecimal1 = new BigDecimal(a);
                        BigDecimal bigDecimal3 = new BigDecimal(list.get(position).getPrice());
                        BigDecimal bigDecimal4 = bigDecimal1.multiply(bigDecimal3);
                        bigDecimal2 = bigDecimal.add(bigDecimal4);
                        tv_sell_moeney.setText(bigDecimal2.setScale(2, RoundingMode.HALF_DOWN).toString());
                    }else {

                    }
                }else {

                }
                list.get(position).setCount(count);

                adapter.notifyDataSetChanged();
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

    //删除购物车
    private void delCart(List<String> cartid){
        Map<String, Object> params = new HashMap<>();
        params.put("cmd", "delCart");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("cartid",cartid);
        OkHttpHelper.getInstance().post_json_file(getContext(), NetClass.BASE_URL, params, new BaseCallback<ResultBean>() {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                getCartList("1");
                tv_sell_moeney.setText("0.00");
                actionDialog.dismiss();
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        } else if (!isVisibleToUser) {
            onPause();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            getCartList("1");
            cartidlist.clear();
            list_intent.clear();
            moneylist.clear();
            imageSel.setImageResource(R.mipmap.weixuan);
            imageSelcheck = false;
            tv_sell_moeney.setText("0.00");
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }
}
