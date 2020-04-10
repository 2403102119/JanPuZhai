package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.lxkj.jpz.Bean.Addressbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;

import java.util.List;

/**
 * Created by Administrator on 2019/10/10 0010.
 */

public class ShippingAdapter extends  RecyclerView.Adapter<ShippingAdapter.MyHolder>  {
    private Context context;
    private List<Addressbean.DataListBean> list;
    public ShippingAdapter(Context context, List<Addressbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public ShippingAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shipping, parent, false);
        return new ShippingAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ShippingAdapter.MyHolder holder, final int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_phone.setText(list.get(position).getPhone());
        holder.tv_site.setText(list.get(position).getAddress()+"  "+list.get(position).getDetail());
        if (StringUtil_li.isSpace(list.get(position).getIsDefault())|list.get(position).getIsDefault().equals("0")){
            holder.imageSel.setVisibility(View.GONE);
        }else {
            holder.imageSel.setVisibility(View.VISIBLE);
        }
        holder.tv_redact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnRedact(position);
            }
        });
        holder.imageSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnCheck(position);
            }
        });
        holder.tv_delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnDelate(position);
            }
        });
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
        TextView tv_name,tv_phone,tv_site,imageSel;
        ImageView tv_delate,tv_redact;
        public MyHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_site = itemView.findViewById(R.id.tv_site);
            imageSel = itemView.findViewById(R.id.imageSel);
            tv_redact = itemView.findViewById(R.id.tv_redact);
            tv_delate = itemView.findViewById(R.id.tv_delate);
        }
    }
    private ShippingAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ShippingAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnDelate(int firstPosition);
        void OnRedact(int firstPosition);
        void OnCheck(int firstPosition);

    }
}
