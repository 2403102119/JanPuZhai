package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.Bean.Detailsrefoundbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/2 0002.
 * Describe :
 */

public class DetailsrefundAdapter extends  RecyclerView.Adapter<DetailsrefundAdapter.MyHolder> {
    private Context context;
    private List<Detailsrefoundbean.OrderDetailBean.OrderItemBean> list;

    public DetailsrefundAdapter(Context context, List<Detailsrefoundbean.OrderDetailBean.OrderItemBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public DetailsrefundAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_gift, parent, false);
        return new DetailsrefundAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailsrefundAdapter.MyHolder holder, final int position) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getProductImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getProductName());
        if (!StringUtil_li.isSpace(list.get(position).getProductSkuName2())){
            holder.tv2.setText(context.getString(R.string.guige)+"："+list.get(position).getProductSkuName1()+"  "+list.get(position).getProductSkuName2());
        }else {

            holder.tv2.setText(context.getString(R.string.guige)+"："+list.get(position).getProductSkuName1());
        }
        holder.tv_count.setText("×"+list.get(position).getProductCount());
        BigDecimal privice = new BigDecimal(list.get(position).getProductPrice());
        BigDecimal count = new BigDecimal(list.get(position).getProductCount());
        holder.tv3.setText("$"+privice.multiply(count).toString());
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
        TextView tv1,tv2,tv_count,tv3;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv3 = itemView.findViewById(R.id.tv3);
        }
    }
    private DetailsrefundAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(DetailsrefundAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);

    }
}
