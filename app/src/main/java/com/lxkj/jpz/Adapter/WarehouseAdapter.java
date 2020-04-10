package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Areabean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/18 0018.
 * Describe :品牌闪购列表
 */

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.MyHolder>{
    private Context context;
    private List<Areabean.DataListBean> list;
    public WarehouseAdapter(Context context, List<Areabean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public WarehouseAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_warehouse, parent, false);
        return new WarehouseAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(WarehouseAdapter.MyHolder holder, final int position) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher))
                .load(list.get(position).getLogo())
                .into(holder.image1);
        holder.tv1.setText(list.get(position).getTitle());
        holder.tv2.setText(list.get(position).getOldPrice());
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
        TextView tv1,tv2;
        ImageView tv7;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv7 = itemView.findViewById(R.id.tv7);
        }
    }
    private WarehouseAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(WarehouseAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
