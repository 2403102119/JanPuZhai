package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.lxkj.jpz.Bean.Banklistbean;
import com.lxkj.jpz.Bean.Withdrawal;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.ResultBean;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

public class Withdraw_bankActivity extends BaseActivity implements View.OnClickListener{


    private TextView tv_login,tv_opening_bank;
    private String money,lowest;
    private TextView tv_usable,tv_sell;
    private EditText et_money,et_name,et_bankcode;
    private LinearLayout ll_sell_item;
    private RelativeLayout ll_sell;
    private PopupWindow popupWindow;// 声明PopupWindow
    private View popupView;// 声明PopupWindow对应的视图
    private TranslateAnimation animation;// 声明平移动画
    private IdeaAdapter ideaAdapter;
    private List<String> list = new ArrayList<>();
    private List<String> Bankid_list = new ArrayList<>();
    private String bank_no;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_withdraw_bank);
        setTopTitle("提现");
        tv_login = findViewById(R.id.tv_login);
        tv_usable = findViewById(R.id.tv_usable);
        tv_sell = findViewById(R.id.tv_sell);
        et_money = findViewById(R.id.et_money);
        tv_opening_bank = findViewById(R.id.tv_opening_bank);
        et_name = findViewById(R.id.et_name);
        et_bankcode = findViewById(R.id.et_bankcode);
    }

    @Override
    protected void initEvent() {
        tv_login.setOnClickListener(this);
        tv_sell.setOnClickListener(this);
        tv_opening_bank.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        money = getIntent().getStringExtra("money");
        tv_usable.setText("可用余额"+money);

        setWithdrawal();
        banksList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login://提现
                if (StringUtil_li.isSpace(et_name.getText().toString())){
                    showToast("请填写持卡人姓名");
                    return;
                }
                if (StringUtil_li.isSpace(tv_opening_bank.getText().toString())){
                    showToast("请选择开户行");
                    return;
                }
                if (StringUtil_li.isSpace(et_bankcode.getText().toString())){
                    showToast("请输入银行卡号");
                    return;
                }
                if (StringUtil_li.isSpace(et_money.getText().toString())){
                    showToast("请输入提现金额");
                    return;
                }
                if (Float.parseFloat(lowest)>Float.parseFloat(et_money.getText().toString())){
                    showToast("提现金额不得小于"+lowest+"元");
                    return;
                }
                subWithdrawal("","",et_money.getText().toString(),et_name.getText().toString(),et_bankcode.getText().toString(),tv_opening_bank.getText().toString(),bank_no);
                break;
            case R.id.tv_sell://全部提现
                et_money.setText(money);
                break;
            case R.id.tv_opening_bank://选择开户行
                state();
                ll_sell_item.startAnimation(AnimationUtils.loadAnimation(this,R.anim.activity_translate_in));
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(Withdraw_bankActivity.this);
        ideaAdapter = new IdeaAdapter(Withdraw_bankActivity.this, list);//代理类型
        wheel.setLayoutManager(layoutManager);
        wheel.setAdapter(ideaAdapter);
        ideaAdapter.setOnItemClickListener(new IdeaAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                tv_opening_bank.setText(list.get(firstPosition));
                bank_no = Bankid_list.get(firstPosition);
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
    //提现金额设置
    private void setWithdrawal() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "setWithdrawal");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Withdrawal>(mContext) {
            @Override
            public void onSuccess(Response response, Withdrawal resultBean) {
                lowest = resultBean.getAmount();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //提交提现申请
    private void subWithdrawal(String zfbaccount, String zfbname, final String amount, final String bankcardname, String bankcardnum, String bankname, String bankno) {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "subWithdrawal");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        params.put("type", "1");
        params.put("zfbaccount",zfbaccount);
        params.put("zfbname",zfbname);
        params.put("amount",amount);
        params.put("bankcardname",bankcardname);
        params.put("bankcardnum",bankcardnum);
        params.put("bankname",bankname);
        params.put("bankno",bankno);
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<ResultBean>(mContext) {
            @Override
            public void onSuccess(Response response, ResultBean resultBean) {
                showToast(resultBean.getResultNote());
                Intent intent = new Intent(Withdraw_bankActivity.this,AlipaywinActivity.class);
                intent.putExtra("money",amount);
                intent.putExtra("type","0");
                intent.putExtra("name",bankcardname);
                startActivity(intent);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    //开户行列表
    private void banksList() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "banksList");
        OkHttpHelper.getInstance().post_json(mContext, NetClass.BASE_URL, params, new SpotsCallBack<Banklistbean>(mContext) {
            @Override
            public void onSuccess(Response response, Banklistbean resultBean) {
                list.clear();
                Bankid_list.clear();
                for (int i = 0; i <resultBean.getBanks().size() ; i++) {
                    list.add(resultBean.getBanks().get(i).getBankname());
                    Bankid_list.add(resultBean.getBanks().get(i).getBankno());
                }
                ideaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
