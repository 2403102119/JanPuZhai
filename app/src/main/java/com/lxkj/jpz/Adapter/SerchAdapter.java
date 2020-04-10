package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Seachbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/27 0027.
 * Describe :
 */

public class SerchAdapter extends RecyclerView.Adapter<SerchAdapter.MyHolder>  {
    private Context context;
    private List<Seachbean.DataListBean> list;
    public SerchAdapter(Context context, List<Seachbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public SerchAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_warehouse, parent, false);
        return new SerchAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SerchAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getLogo())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getTitle());
        holder.tv_oldPrice.setText("¥"+list.get(position).getOldPrice());
        holder.tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        holder.tv_money.setText(StringUtil_li.changTVsize(list.get(position).getPrice()));
        holder.tv_sales.setText(list.get(position).getSales()+"人付款");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        RoundedImageView image2;
        TextView tv1,tv_money,tv_oldPrice,tv_sales;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_oldPrice = itemView.findViewById(R.id.tv_oldPrice);
            tv_sales = itemView.findViewById(R.id.tv_sales);
        }
    }
    private SerchAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SerchAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
