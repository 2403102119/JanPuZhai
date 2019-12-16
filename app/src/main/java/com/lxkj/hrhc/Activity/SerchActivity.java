package com.lxkj.hrhc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.hrhc.Adapter.SerchAdapter;
import com.lxkj.hrhc.Adapter.WarehouseAdapter;
import com.lxkj.hrhc.Base.BaseActivity;
import com.lxkj.hrhc.Bean.AboutUsbean;
import com.lxkj.hrhc.Bean.Areabean;
import com.lxkj.hrhc.Bean.Seachbean;
import com.lxkj.hrhc.Http.OkHttpHelper;
import com.lxkj.hrhc.Http.SpotsCallBack;
import com.lxkj.hrhc.R;
import com.lxkj.hrhc.SQSP;
import com.lxkj.hrhc.Uri.NetClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :搜索界面
 */
public class SerchActivity extends BaseActivity{
    private RecyclerView recycle;
    LinearLayoutManager layoutManager;
    SerchAdapter adapter;
    List<Seachbean.DataListBean> list=new ArrayList<>();
    private String text;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_serch);
        setTopTitle("搜索结果");
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(SerchActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new SerchAdapter(SerchActivity.this, list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new SerchAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                Intent intent = new Intent(SerchActivity.this,DeatilsActivity.class);
                intent.putExtra("productid",list.get(firstPosition).getProductid());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        text = getIntent().getStringExtra("text");
        searchProduct(text,"1");
    }

    //搜索商品
    private void searchProduct(String keywords,String nowPage) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "searchProduct");
        params.put("keywords", keywords);
        params.put("nowPage", nowPage);
        params.put("pageCount", SQSP.pagerSize);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Seachbean>(mContext) {
            @Override
            public void onSuccess(Response response, Seachbean resultBean) {
                 list.clear();
                 list.addAll(resultBean.getDataList());
                 adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
