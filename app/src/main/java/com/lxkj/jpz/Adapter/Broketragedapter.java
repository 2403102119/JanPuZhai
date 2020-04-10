package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Brokeragebean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/18 0018.
 * Describe :邀请好友列表
 */

public class Broketragedapter extends RecyclerView.Adapter<Broketragedapter.MyHolder>{
    private Context context;
    private List<Brokeragebean.WalletDetailBean> list;
    public Broketragedapter(Context context, List<Brokeragebean.WalletDetailBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Broketragedapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_broketrage, parent, false);
        return new Broketragedapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Broketragedapter.MyHolder holder, final int position) {

        holder.tv_nickName.setText(list.get(position).getTitle());
        holder.tv_adtime.setText(list.get(position).getAdtime());
        if (list.get(position).getType().equals("0")){
            holder.tv_amount.setText("+$"+list.get(position).getAmount()+"元");
        }else {
            holder.tv_amount.setText("-$"+list.get(position).getAmount()+"元");
        }

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
         ImageView im_userIcon;
         TextView tv_nickName,tv_adtime,tv_amount;
        public MyHolder(View itemView) {
            super(itemView);
            im_userIcon = itemView.findViewById(R.id.im_userIcon);
            tv_nickName = itemView.findViewById(R.id.tv_nickName);
            tv_adtime = itemView.findViewById(R.id.tv_adtime);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }
    private Broketragedapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Broketragedapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
