package com.lxkj.jpz.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Activity.BrokerageActivity;
import com.lxkj.jpz.Activity.CollectActivity;
import com.lxkj.jpz.Activity.EarningsActivity;
import com.lxkj.jpz.Activity.EvaluationActivity;
import com.lxkj.jpz.Activity.FaqActivity;
import com.lxkj.jpz.Activity.InviteActivity;
import com.lxkj.jpz.Activity.LoginActivity;
import com.lxkj.jpz.Activity.MercenaryActivity;
import com.lxkj.jpz.Activity.MessageActivity;
import com.lxkj.jpz.Activity.OrderActivity;
import com.lxkj.jpz.Activity.PersonalActivity;
import com.lxkj.jpz.Activity.ReceivewActivity;
import com.lxkj.jpz.Activity.RelationActivity;
import com.lxkj.jpz.Activity.SetActivity;
import com.lxkj.jpz.Activity.WalletActivity;
import com.lxkj.jpz.Activity.WebViewActivity;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseFragment;
import com.lxkj.jpz.Bean.AboutUsbean;
import com.lxkj.jpz.Bean.UserInfobean;
import com.lxkj.jpz.Http.OkHttpHelper;
import com.lxkj.jpz.Http.SpotsCallBack;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;
import com.lxkj.jpz.Uri.NetClass;
import com.lxkj.jpz.Utils.SPTool;
import com.lxkj.jpz.Utils.ToastFactory;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class Home4Fragment extends BaseFragment implements View.OnClickListener{
    private final static String TAG = "Home4Fragment";
    private LinearLayout ll_about,ll_personal_details,ll_receiver_address,ll_set,ll_collect,ll_faq,ll_wallet,ll_evaluation,ll_brokerage,ll_message,ll_updateIcon;
    private TextView tv_all,tv_name,tv_phone,tv_balance,tv_todyIncome,tv_historyIncome,tv_pifashang;
    private RoundedImageView ri_icon;
    private String name,icon_url,phone,sex,identify,address,InviteCode,emile;
    private LinearLayout ll_obligation,ll_Overhang,ll_receiving,ll_evaluated,ll_refund,ll_quanbu;
    private ImageView im_message,im_vip;
    private String isopen;//是否开通佣金 0未开通 1已开通
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }
    private void initView(View view) {
        ll_personal_details = view.findViewById(R.id.ll_personal_details);
        ll_receiver_address = view.findViewById(R.id.ll_receiver_address);
        ll_set = view.findViewById(R.id.ll_set);
        ll_collect = view.findViewById(R.id.ll_collect);
        ll_faq = view.findViewById(R.id.ll_faq);
        ll_wallet = view.findViewById(R.id.ll_wallet);
        ll_evaluation = view.findViewById(R.id.ll_evaluation);
        ll_brokerage = view.findViewById(R.id.ll_brokerage);
        ll_message = view.findViewById(R.id.ll_message);
        tv_all = view.findViewById(R.id.tv_all);
        tv_name = view.findViewById(R.id.tv_name);
        ri_icon = view.findViewById(R.id.ri_icon);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_balance = view.findViewById(R.id.tv_balance);
        tv_todyIncome = view.findViewById(R.id.tv_todyIncome);
        tv_historyIncome = view.findViewById(R.id.tv_historyIncome);
        ll_updateIcon = view.findViewById(R.id.ll_updateIcon);
        ll_about = view.findViewById(R.id.ll_about);
        ll_obligation = view.findViewById(R.id.ll_obligation);
        ll_Overhang = view.findViewById(R.id.ll_Overhang);
        ll_receiving = view.findViewById(R.id.ll_receiving);
        ll_evaluated = view.findViewById(R.id.ll_evaluated);
        ll_refund = view.findViewById(R.id.ll_refund);
        im_message = view.findViewById(R.id.im_message);
        ll_quanbu = view.findViewById(R.id.ll_quanbu);
        tv_pifashang = view.findViewById(R.id.tv_pifashang);
        im_vip = view.findViewById(R.id.im_vip);

        ll_personal_details.setOnClickListener(this);
        ll_receiver_address.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_faq.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        ll_evaluation.setOnClickListener(this);
        ll_brokerage.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        ll_updateIcon.setOnClickListener(this);
        ll_obligation.setOnClickListener(this);
        ll_Overhang.setOnClickListener(this);
        ll_receiving.setOnClickListener(this);
        ll_evaluated.setOnClickListener(this);
        ll_refund.setOnClickListener(this);
        ll_quanbu.setOnClickListener(this);
    }
    private void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_personal_details://个人资料
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("icon_url",icon_url);
                intent.putExtra("phone",phone);
                intent.putExtra("sex",sex);
                intent.putExtra("identify",identify);
                intent.putExtra("address",address);
                startActivity(intent);
                break;
            case R.id.ll_receiver_address://收货地址
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), ReceivewActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_set://设置
                Intent intent2 = new Intent(getActivity(), SetActivity.class);
                intent2.putExtra("phone",phone);
                intent2.putExtra("emile",emile);
                startActivity(intent2);
                break;
            case R.id.ll_collect://我的收藏
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent3 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_faq://我的收益
//                Intent intent4 = new Intent(getActivity(), FaqActivity.class);
//                startActivity(intent4);
                Intent intent4 = new Intent(getActivity(), EarningsActivity.class);
                startActivity(intent4);
                break;
            case R.id.ll_wallet://我的钱包
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
//                Intent intent5 = new Intent(getActivity(), WalletActivity.class);
//                startActivity(intent5);
                break;
            case R.id.ll_evaluation://我的评价
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent6 = new Intent(getActivity(), EvaluationActivity.class);
                startActivity(intent6);
                break;
            case R.id.ll_brokerage://我的余额
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                    Intent intent7 = new Intent(getActivity(), BrokerageActivity.class);
                    startActivity(intent7);

                break;
            case R.id.ll_message://消息
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent8 = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent8);
                break;
            case R.id.ll_quanbu://全部订单
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent9 = new Intent(getActivity(), OrderActivity.class);
                intent9.putExtra("position","0");
                startActivity(intent9);
                break;
            case R.id.ll_updateIcon://我的邀请
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent10 = new Intent(getActivity(), InviteActivity.class);
                intent10.putExtra("InviteCode",InviteCode);
                startActivity(intent10);
                break;
            case R.id.ll_about://关于我们
//                aboutUs();
                Intent intent16 = new Intent(getContext(), RelationActivity.class);
                startActivity(intent16);
                break;
            case R.id.ll_obligation://待付款
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent11 = new Intent(getActivity(), OrderActivity.class);
                intent11.putExtra("position","1");
                startActivity(intent11);
                break;
            case R.id.ll_Overhang://待发货
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent12 = new Intent(getActivity(),OrderActivity.class);
                intent12.putExtra("position","2");
                startActivity(intent12);
                break;
            case R.id.ll_receiving://待收货
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                Intent intent13 = new Intent(getActivity(),OrderActivity.class);
                intent13.putExtra("position","3");
                startActivity(intent13);
                break;
            case R.id.ll_evaluated://待评价
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent14 = new Intent(getActivity(),OrderActivity.class);
                intent14.putExtra("position","4");
                startActivity(intent14);
                break;
            case R.id.ll_refund://退款售后
                if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                    ToastFactory.getToast(getActivity(), "请先登录").show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    //finish();
                    return;
                }
                Intent intent15 = new Intent(getActivity(),OrderActivity.class);
                intent15.putExtra("position","5");
                startActivity(intent15);
                break;

        }
    }



    //个人信息
    private void userInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "userInfo");
        params.put("uid", SPTool.getSessionValue(SQSP.uid));
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<UserInfobean>(getContext()) {
            @Override
            public void onSuccess(Response response, UserInfobean resultBean) {
                tv_name.setText(resultBean.getDataObject().getName());
                name = resultBean.getDataObject().getName();
                icon_url = resultBean.getDataObject().getIcon();
                phone = resultBean.getDataObject().getPhone();
                sex = resultBean.getDataObject().getSex();
                identify = resultBean.getDataObject().getIdentify();
                address = resultBean.getDataObject().getAddress();
                InviteCode = resultBean.getDataObject().getInviteCode();
                emile = resultBean.getDataObject().getEmail();
                SPTool.addSessionMap(SQSP.setPwd, resultBean.getDataObject().getSetPwd());

                if (resultBean.getDataObject().getIdentify().equals("0")){
                    tv_pifashang.setVisibility(View.GONE);
                    im_vip.setVisibility(View.GONE);
                }else if (resultBean.getDataObject().getIdentify().equals("1")){
                    tv_pifashang.setVisibility(View.GONE);
                    im_vip.setVisibility(View.VISIBLE);
                }else if (resultBean.getDataObject().getIdentify().equals("2")){
                    tv_pifashang.setVisibility(View.GONE);
                    im_vip.setVisibility(View.VISIBLE);
                }else {
                    tv_pifashang.setVisibility(View.VISIBLE);
                    im_vip.setVisibility(View.GONE);
                }

                SQSP.user_icon = resultBean.getDataObject().getIcon();


                Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                        .error(R.mipmap.ic_figure_head)
                        .placeholder(R.mipmap.ic_figure_head))
                        .load(resultBean.getDataObject().getIcon())
                        .into(ri_icon);
                tv_phone.setText(resultBean.getDataObject().getPhone());
                tv_balance.setText(resultBean.getDataObject().getBalance());
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    //关于我们
    private void aboutUs() {
        Map<String, String> params = new HashMap<>();
        params.put("cmd", "aboutUs");
        OkHttpHelper.getInstance().post_json(getContext(), NetClass.BASE_URL, params, new SpotsCallBack<AboutUsbean>(getContext()) {
            @Override
            public void onSuccess(Response response, AboutUsbean resultBean) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("webTitle","柬埔寨");
                intent.putExtra("webUrl",resultBean.getContentUrl());
                startActivity(intent);
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
            if (TextUtils.isEmpty(SPTool.getSessionValue(SQSP.uid))){
                return;
            }
            userInfo();
            //TODO give the signal that the fragment is visible
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO give the signal that the fragment is invisible
    }

}
