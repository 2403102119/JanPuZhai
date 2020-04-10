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
import com.lxkj.jpz.Bean.Collectbean;
import com.lxkj.jpz.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/14 0014.
 * Describe :
 */

public class CollectAdapter  extends  RecyclerView.Adapter<CollectAdapter.MyHolder>  {
    private Context context;
    private List<Collectbean.DataListBean> list;
    public CollectAdapter(Context context, List<Collectbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public CollectAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_collect, parent, false);
        return new CollectAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getLogo())
                .into(holder.image1);
        holder.tv_sales.setText("销量"+list.get(position).getSales());
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_price.setText("¥ "+list.get(position).getOldPrice());
        holder.im_delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onItemClickListener.OnDealte(position);
            }
        });
        holder.im_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnDetail(position);
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
       RoundedImageView image1;
       TextView tv_title;
       TextView tv_price,tv_sales;
       ImageView im_delate,im_add;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_delate = itemView.findViewById(R.id.im_delate);
            tv_sales = itemView.findViewById(R.id.tv_sales);
            im_add = itemView.findViewById(R.id.im_add);
        }
    }
    private CollectAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CollectAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnDealte(int position);
        void OnDetail(int position);


    }
}
