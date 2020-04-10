package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Messagebean;
import com.lxkj.jpz.Bean.contactCustomerBena;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/4/8 0008.
 * Describe :
 */

public class Store_listAdapter extends RecyclerView.Adapter<Store_listAdapter.MyHolder>  {
    private Context context;
    private List<contactCustomerBena.DataListBean> list;
    public Store_listAdapter(Context context, List<contactCustomerBena.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Store_listAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false);
        return new Store_listAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Store_listAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getLogo())
                .into(holder.im_icon);
        holder.tv_site.setText(list.get(position).getAddress());
        holder.tv_phone.setText(list.get(position).getPhone());
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
           ImageView im_icon;
           TextView tv_site,tv_phone;
        public MyHolder(View itemView) {
            super(itemView);
            im_icon = itemView.findViewById(R.id.im_icon);
            tv_site = itemView.findViewById(R.id.tv_site);
            tv_phone = itemView.findViewById(R.id.tv_phone);
        }
    }
    private Store_listAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Store_listAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);



    }
}
