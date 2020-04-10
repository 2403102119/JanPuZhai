package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.jpz.Adapter.IdeaAdapter;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.Bean.Ideabean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.lxkj.jpz.Utils.ToastFactory;
import com.lxkj.jpz.View.ActionDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class IdeaActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_sell_item;
    private RelativeLayout ll_sell;
    private LinearLayout ll_feedback;
    private PopupWindow popupWindow;// 声明PopupWindow
    private View popupView;// 声明PopupWindow对应的视图
    private TranslateAnimation animation;// 声明平移动画
    private IdeaAdapter ideaAdapter;
    private List<String> list = new ArrayList<>();
    private TextView tv_type,tv_login;
    private EditText et_itea;
    private ActionDialog actionDialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_idea);
        setTopTitle(getString(R.string.feedback));
        ll_feedback = findViewById(R.id.ll_feedback);
        tv_type = findViewById(R.id.tv_type);
        et_itea = findViewById(R.id.et_itea);
        tv_login = findViewById(R.id.tv_login);

        actionDialog = new ActionDialog(mContext,false);
        actionDialog.setOnActionClickListener(new ActionDialog.OnActionClickListener() {
            @Override
            public void onLeftClick() {
                actionDialog.dismiss();
            }

            @Override
            public void onRightClick() {
                actionDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void initEvent() {
        ll_feedback.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
//        feedType();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_feedback://获取设备类型
                state();
                ll_sell_item.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                popupWindow.showAtLocation(view, Gravity.TOP,0,10);
                break;
            case R.id.tv_login://提交
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(mContext, getString(R.string.Please_login_first)).show();
                    startActivity(new Intent(mContext, LoginActivity.class));
                    //finish();
                    return;
                }
//                if (StringUtil_li.isSpace(tv_type.getText().toString())){
//                    showToast("请选择反馈类型");
//                    return;
//                }
                if (StringUtil_li.isSpace(et_itea.getText().toString())){
                    showToast("请填写反馈信息");
                    return;
                }
                feedBack(et_itea.getText().toString(),tv_type.getText().toString());
                break;
        }
    }


    public  void  state(){
        popupWindow=new PopupWindow(this);
        View view=getLayoutInflater().inflate(R.layout.commodity,null);
        ll_sell_item= view.findViewById(R.id.ll_sell_item);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        ll_sell=view.findViewById(R.id.ll_sell);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        RecyclerView wheel = view.findViewById(R.id.wheel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(IdeaActivity.this);
        ideaAdapter = new IdeaAdapter(IdeaActivity.this, list);//代理类型
        wheel.setLayoutManager(layoutManager);
        wheel.setAdapter(ideaAdapter);
        ideaAdapter.setOnItemClickListener(new IdeaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_type.setText(list.get(firstPosition));
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });

        ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });

    }


//    //反馈类型
//    private void feedType() {
//        Map<String, String> params = new HashMap<>();
//        params.put("cmd", "feedType");
//        OkHttpHelper.getInstance().post_json(this, NetClass.BASE_URL, params, new SpotsCallBack<Ideabean>(this) {
//            @Override
//            public void onSuccess(Response response, Ideabean resultBean) {
//                list.clear();
//                for (int i = 0; i <resultBean.getDataList().size() ; i++) {
//                    list.add(resultBean.getDataList().get(i).getType());
//                }
//                ideaAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//            }
//        });
//    }

    //反馈类型
    private void feedBack(String content,String type) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "feedBack");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("content",content);
        params.put("type",type);
        OkHttpHelper.getInstance().post_json(this, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(this) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                actionDialog.show();

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

}
