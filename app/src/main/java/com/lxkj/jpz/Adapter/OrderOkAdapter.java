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
import com.lxkj.jpz.Bean.Cartbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/3 0003.
 * Describe :
 */

public class OrderOkAdapter  extends  RecyclerView.Adapter<OrderOkAdapter.MyHolder>  {
    private Context context;
    private List<Cartbean.DataListBean> list;
    public OrderOkAdapter(Context context, List<Cartbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public OrderOkAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_gift, parent, false);
        return new OrderOkAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderOkAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getImage())
                .into(holder.image2);
        if (!StringUtil_li.isSpace(list.get(position).getSkuName2())){
            holder.tv2.setText("规格："+list.get(position).getSkuName1()+" "+list.get(position).getSkuName2());
        }else {
            holder.tv2.setText("规格："+list.get(position).getSkuName1());
        }

        holder.tv1.setText(list.get(position).getProductName());
        holder.tv_count.setText("×"+list.get(position).getCount());
        holder.tv3.setText("¥"+list.get(position).getPrice());
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
        TextView tv1,tv2,tv_number,tv_count,tv3;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv3 = itemView.findViewById(R.id.tv3);
        }
    }
    private OrderOkAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OrderOkAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
    }
}
