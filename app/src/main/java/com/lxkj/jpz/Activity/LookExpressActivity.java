package com.lxkj.jpz.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.CourierAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.KuaiDiModel;
import com.lxkj.jpz.Bean.WuliuBean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.GsonUtil;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 查看物流
 */
public class LookExpressActivity extends BaseActivity {


    RecyclerView mRecKuaidi;
    TextView mExpCode;
    private CourierAdapter adapter;
    private String emsno;
    private String emscode;
    private String expCode;
    LinearLayoutManager layoutManager;
    List<WuliuBean.TracesBean> list=new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_look_logistics);
        setTitle(getString(R.string.kuaidixinxi));
        mRecKuaidi = findViewById(R.id.rec_kuaidi);
        mExpCode = findViewById(R.id.expCode);
    }

    @Override
    protected void initEvent() {
        layoutManager = new LinearLayoutManager(LookExpressActivity.this);
        mRecKuaidi.setLayoutManager(layoutManager);
        adapter = new CourierAdapter(LookExpressActivity.this, list);
        mRecKuaidi.setAdapter(adapter);
        adapter.setOnItemClickListener(new CourierAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {

            }

            @Override
            public void OnDealte(int position) {
            }


        });
    }

    @Override
    protected void initData() {
        emsno = getIntent().getStringExtra("emsno");
        emscode = getIntent().getStringExtra("emscode");
        expCode = getIntent().getStringExtra("expCode");

        mExpCode.setText(expCode +":"+ emsno);
        kdniaoInfo();
    }


    private static final String TAG = "LookExpressActivity";
    /**
     * 获取快递信息
     */
    private void kdniaoInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "kdniaoInfo");
        params.put("emsno", emsno);//快递编号
        params.put("emscode", emscode);//	物流编码
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<KuaiDiModel>(mContext) {
            @Override
            public void onSuccess(Response response, KuaiDiModel resultBean) {
                WuliuBean dtat = GsonUtil.parseJsonWithGson(resultBean.getInfo(), WuliuBean.class);

                list.addAll(dtat.getTraces());
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }



}
