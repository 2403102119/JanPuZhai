package com.lxkj.jpz.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.WarehouseAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Areabean;
import com.lxkj.jpz.Bean.NoticeImagebean;
import com.lxkj.jpz.Bean.allSearchBean;
import com.lxkj.jpz.Http.BaseCallback;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.lxkj.jpz.View.FlowLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

import static com.lxkj.jpz.App.mContext;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :搜索结果
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_search;
    FlowLayout flow,flow_dajia;
    private List<String> list = new ArrayList<>();
    private List<String> dajia_list = new ArrayList<>();
    private EditText et_search;
    private ImageView im_delate;
    private static final String TAG = "SearchActivity";
    private LinearLayout ll_lishi,ll_remen;
    private NiceSpinner nice_spinner;
    private String search_type = "0";
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_search);
        baseTop.setVisibility(View.GONE);
        tv_search = findViewById(R.id.tv_search);
        flow = findViewById(R.id.flow);
        flow_dajia = findViewById(R.id.flow_dajia);
        et_search = findViewById(R.id.et_search);
        im_delate = findViewById(R.id.im_delate);
        ll_lishi = findViewById(R.id.ll_lishi);

    }

    @Override
    protected void initEvent() {
        tv_search.setOnClickListener(this);
        im_delate.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        allSearch();


        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    if (!TextUtils.isEmpty(et_search.getText().toString())) {
                        list.add(et_search.getText().toString());
                        ArrayList afterList = new ArrayList(new HashSet(list));
                        list = afterList;
                        SPTool.addSessionMap(SQSP.sosuojilu, list);

//                        setData();
                        String edStr = et_search.getText().toString().trim();
                        Intent intent = new Intent(SearchActivity.this,ClassifyActivity.class);
                        intent.putExtra("type","0");
                        intent.putExtra("categoryId", "0");
                        intent.putExtra("title","0");
                        intent.putExtra("edStr",edStr);
                        startActivity(intent);
                    } else {
                        ToastFactory.getToast(mContext, getString(R.string.guanjianzibunengweikong)).show();
                    }
                    list.addAll(SPTool.getDataList(SQSP.sosuojilu));

                    //往容器内添加TextView数据
//                    setData();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search://搜索
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    list.add(et_search.getText().toString());
                    ArrayList afterList = new ArrayList(new HashSet(list));
                    list = afterList;
                    SPTool.addSessionMap(SQSP.sosuojilu, list);
//                    setData();
                    String edStr = et_search.getText().toString().trim();
                    Intent intent = new Intent(SearchActivity.this,ClassifyActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("categoryId", "0");
                    intent.putExtra("title","0");
                    intent.putExtra("edStr",edStr);
                    startActivity(intent);
                } else {
                    ToastFactory.getToast(mContext, getString(R.string.guanjianzibunengweikong)).show();
                }
                list.addAll(SPTool.getDataList(SQSP.sosuojilu));

                //往容器内添加TextView数据
//                setData();
                break;
            case R.id.im_delate:
                list.clear();
                SPTool.addSessionMap(SQSP.sosuojilu, list);
                setData();

                break;
        }
    }

    //大家都在搜
    private void allSearch() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "allSearch");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new BaseCallback<allSearchBean>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, final allSearchBean resultBean) {
                dajia_list.clear();
                for (int i = 0; i <resultBean.getDataList().size() ; i++) {
                    dajia_list.add(resultBean.getDataList().get(i).getName());
                }
                setData1();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    private void setData(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flow != null) {
            flow.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            final TextView tv = new TextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.biankuang20);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClassifyActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("categoryId", "0");
                    intent.putExtra("title","0");
                    intent.putExtra("edStr",tv.getText().toString());
                    startActivity(intent);
                }
            });
            flow.addView(tv, layoutParams);
        }
    }
    private void setData1(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flow_dajia != null) {
            flow_dajia.removeAllViews();
        }
        for (int i = 0; i < dajia_list.size(); i++) {
            final TextView tv = new TextView(this);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(dajia_list.get(i));
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(R.drawable.biankuang20);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ClassifyActivity.class);
                    intent.putExtra("type","0");
                    intent.putExtra("categoryId", "0");
                    intent.putExtra("title","0");
                    intent.putExtra("edStr",tv.getText().toString());
                    startActivity(intent);
                }
            });
            flow_dajia.addView(tv, layoutParams);
        }
    }






    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(SPTool.getDataList(SQSP.sosuojilu));
//        if (list.size() == 0){
//            ll_lishi.setVisibility(View.GONE);
//        }else {
//            ll_lishi.setVisibility(View.VISIBLE);
//        }
        Log.i(TAG, "initData: "+list.size());
        //往容器内添加TextView数据
        setData();
    }
}
