package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxkj.jpz.Adapter.MessageAdapter;
import com.lxkj.jpz.Adapter.Store_listAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Messagebean;
import com.lxkj.jpz.Bean.contactCustomerBena;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.View.SlideRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Store_listActivity extends BaseActivity {

    LinearLayoutManager layoutManager;
    Store_listAdapter adapter;
    List<contactCustomerBena.DataListBean> list=new ArrayList<>();
    private RecyclerView recycle;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_store_list);
        setTopTitle(getString(R.string.mendianliebiao));
        recycle = findViewById(R.id.recycle);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(Store_listActivity.this);
        recycle.setLayoutManager(layoutManager);
        adapter = new Store_listAdapter(Store_listActivity.this,list);
        recycle.setAdapter(adapter);
        adapter.setOnItemClickListener(new Store_listAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }
        });
    }
    @Override
    protected void initData() {
      list.clear();
      list.addAll(SQSP.shouyelist);
      adapter.notifyDataSetChanged();
    }
}
