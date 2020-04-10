package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Orderdetailbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/28 0028.
 * Describe :
 */

public class OrderdetailAdapter extends  RecyclerView.Adapter<OrderdetailAdapter.MyHolder>  {
    private Context context;
    private List<Orderdetailbean.OrderDetailBean.OrderItemBean> list;
    public OrderdetailAdapter(Context context, List<Orderdetailbean.OrderDetailBean.OrderItemBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public OrderdetailAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_gift, parent, false);
        return new OrderdetailAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderdetailAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getProductImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getProductName());
        holder.tv3.setText("¥ "+list.get(position).getProductPrice());
        if (!StringUtil_li.isSpace(list.get(position).getProductSkuName2())){
            holder.tv2.setText("规格："+list.get(position).getProductSkuName1()+" "+list.get(position).getProductSkuName2());
        }else {
            holder.tv2.setText("规格："+list.get(position).getProductSkuName1());
        }

        holder.tv_count.setText("×"+list.get(position).getProductCount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnDealte(position);
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
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv_count;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv_count = itemView.findViewById(R.id.tv_count);
        }
    }
    private OrderdetailAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OrderdetailAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnDealte(int position);


    }
}
