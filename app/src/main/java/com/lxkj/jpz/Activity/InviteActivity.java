package com.lxkj.jpz.Activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;

import static com.lxkj.jpz.App.mContext;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :我的邀请
 */
public class InviteActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_poster,ll_invite_code,ll_invitejilu;
    private PopupWindow popupWindow;
    private LinearLayout ll_sell;
    private String InviteCode;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_invite);
        setTopTitle(getResources().getString(R.string.My_invitation));
        ll_poster = findViewById(R.id.ll_poster);
        ll_invite_code = findViewById(R.id.ll_invite_code);
        ll_invitejilu = findViewById(R.id.ll_invitejilu);
    }

    @Override
    protected void initEvent() {
        ll_poster.setOnClickListener(this);
        ll_invite_code.setOnClickListener(this);
        ll_invitejilu.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        InviteCode = getIntent().getStringExtra("InviteCode");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_poster://邀请海报
                Intent intent= new Intent(mContext,PosterActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_invite_code://邀请码
                state();
                ll_sell.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.activity_translate_in));
                popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
                break;
            case R.id.ll_invitejilu://邀请记录
                Intent intent1 = new Intent(mContext,RecordActivity.class);
                startActivity(intent1);
                break;
        }
    }
    public  void  state(){
        popupWindow=new PopupWindow(mContext);
        View view=getLayoutInflater().inflate(R.layout.popup_code,null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setClippingEnabled(false);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        ll_sell=view.findViewById(R.id.ll_sell);
        TextView tv_InviteCode = view.findViewById(R.id.tv_InviteCode);
        tv_InviteCode.setText(getString(R.string.Invitation_code)+"："+InviteCode);
        ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                ll_sell.clearAnimation();
            }
        });


    }
}
