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
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/13 0013.
 * Describe :首页二级Adapter
 */

public class Home1_itemAdapter extends RecyclerView.Adapter<Home1_itemAdapter.MyHolder> {
    private Context context;
    private List<FirsePagebean.JprouctListBean.PListBean> list;
    public Home1_itemAdapter(Context context, List<FirsePagebean.JprouctListBean.PListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Home1_itemAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);
        return new Home1_itemAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Home1_itemAdapter.MyHolder holder, final int position) {
//        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
//                .error(R.mipmap.ic_launcher)
//                .placeholder(R.mipmap.ic_launcher))
//                .load(list.get(position).getLogo())
//                .into(holder.im_logo);
//
//        holder.tv_title.setText(list.get(position).getTitle());
//        if (StringUtil_li.isSpace(list.get(position).getOldPrice())){
//            holder.tv_oldPrice.setText("¥ 0.00");
//        }else{
//
//            holder.tv_oldPrice.setText("¥"+StringUtil_li.changTVsize(list.get(position).getOldPrice()));
//        }
//        holder.tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
//        holder.tv_Price.setText(StringUtil_li.changTVsize(list.get(position).getPrice()));
//        holder.tv_pepo.setText(list.get(position).getSales()+"人付款");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.OnItemClickListener(position);
//            }
//        });

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
        RecyclerView recycle;
        RoundedImageView im_logo;
        TextView tv_title,tv_oldPrice,tv_Price,tv_pepo;
        public MyHolder(View itemView) {
            super(itemView);
            im_logo = itemView.findViewById(R.id.im_logo);
            recycle = itemView.findViewById(R.id.recycle);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_oldPrice = itemView.findViewById(R.id.tv_oldPrice);
            tv_Price = itemView.findViewById(R.id.tv_Price);
            tv_pepo = itemView.findViewById(R.id.tv_pepo);
        }
    }
    private Home1_itemAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Home1_itemAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
