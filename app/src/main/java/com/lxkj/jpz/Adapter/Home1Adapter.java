package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Activity.DeatilsActivity;
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/13 0013.
 * Describe :首页一级Adapter
 */

public class Home1Adapter extends RecyclerView.Adapter<Home1Adapter.MyHolder>  {
    private Context context;
    private List<FirsePagebean.JprouctListBean> list;
    private List<FirsePagebean.JprouctListBean.PListBean> item_list = new ArrayList<>();
    private GuessAdapter guessAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private static final String TAG = "ShopAdapter";
    public Home1Adapter(Context context, List<FirsePagebean.JprouctListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Home1Adapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_adapter, parent, false);
        return new Home1Adapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Home1Adapter.MyHolder holder, final int position) {


        if (!StringUtil_li.isSpace(list.get(position).getType())){
            if (list.get(position).getType().equals("0")){
                holder.tv_zhuanqu.setText(context.getString(R.string.Special_zone));
            }else if (list.get(position).getType().equals("1")){
                holder.tv_zhuanqu.setText(context.getString(R.string.Boutique_zone));
            }else if (list.get(position).getType().equals("2")){
                holder.tv_zhuanqu.setText(context.getString(R.string.Discount_zone));
            }else {
                holder.tv_zhuanqu.setText(context.getString(R.string.Commodity_zone));
            }
        }


        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        holder.guessrecycle.setLayoutManager(staggeredGridLayoutManager);
        guessAdapter=new GuessAdapter(context,list.get(position).getPList());
        holder.guessrecycle.setAdapter(guessAdapter);
        guessAdapter .setOnItemClickListener(new GuessAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener( int item_position) {
                onItemClickListener.OnDetailClickListener(position,item_position);

            }

        });

//        item_list.clear();
//        item_list.addAll(list.get(position).getPList());
//        guessAdapter.notifyDataSetChanged();

        holder.ll_zhuanqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        RecyclerView guessrecycle;
        TextView tv_zhuanqu;
        LinearLayout ll_zhuanqu;
        public MyHolder(View itemView) {
            super(itemView);
            guessrecycle = itemView.findViewById(R.id.guessrecycle);
            tv_zhuanqu = itemView.findViewById(R.id.tv_zhuanqu);
            ll_zhuanqu = itemView.findViewById(R.id.ll_zhuanqu);
        }
    }
    private Home1Adapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Home1Adapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);
        void OnDetailClickListener(int position,int item_position);

    }
}
