package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Bean.Orderbean;
import com.lxkj.jpz.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/19 0019.
 * Describe :订单二级列表
 */

public class OrderitemAdapter extends  RecyclerView.Adapter<OrderitemAdapter.MyHolder>{
    private Context context;
    private List<Orderbean.DataListBean.OrderItemBean> list;

    public OrderitemAdapter(Context context, List<Orderbean.DataListBean.OrderItemBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public OrderitemAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_item_order, parent, false);
        return new OrderitemAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderitemAdapter.MyHolder holder, final int position) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getProductImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(0).getProductName());
        holder.tv2.setText(list.get(0).getProductSkuName1());
        holder.tv_number.setText("×"+list.get(0).getProductCount());
        holder.tv3.setText("共"+list.size()+"件");
        holder.tv_money.setText("合计: $"+list.get(0).getZongji());
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
            return 1;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        RoundedImageView image2;
        TextView tv1,tv2,tv_number,tv3,tv_money;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv3 = itemView.findViewById(R.id.tv3);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }
    private OrderitemAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OrderitemAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);

    }
}
