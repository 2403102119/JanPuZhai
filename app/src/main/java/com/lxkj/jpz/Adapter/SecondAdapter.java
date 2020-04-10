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
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.Bean.productCategorybean;
import com.lxkj.jpz.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/13 0013.
 * Describe :二级分类
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyHolder>  {
    private Context context;
    private List<productCategorybean.DataListBean.ChildrenListBean> list;
    public SecondAdapter(Context context, List<productCategorybean.DataListBean.ChildrenListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public SecondAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_second, parent, false);
        return new SecondAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final SecondAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(list.get(position).getChildCategoryImage())
                .into(holder.image1);
        holder.tv1.setText(list.get(position).getChildCategoryName());
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
        RoundedImageView image1;
        TextView tv1;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv1 = itemView.findViewById(R.id.tv1);
        }
    }
    private SecondAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SecondAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
