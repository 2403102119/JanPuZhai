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
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Areabean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/13 0013.
 * Describe :
 */

public class TowAdapter extends RecyclerView.Adapter<TowAdapter.MyHolder> {
    private Context context;
    private List<Areabean.DataListBean> list;
    public TowAdapter(Context context, List<Areabean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public TowAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_two, parent, false);
        return new TowAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(TowAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.bai)
                .placeholder(R.mipmap.bai))
                .load(list.get(position).getLogo())
                .into(holder.image1);
        holder.tv1.setText(list.get(position).getTitle());
        holder.tv2.setText(StringUtil_li.changTVsize(list.get(position).getPrice()));
        if (StringUtil_li.isSpace(list.get(position).getOldPrice())){
            holder.tv_oldprice.setText("¥ 0");
        }else {
            holder.tv_oldprice.setText("¥"+list.get(position).getOldPrice());
        }
        holder.tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
        holder.tv7.setText(list.get(position).getSales()+"人付款");

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
        TextView tv1,tv2,tv_oldprice,tv7;
        public MyHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_oldprice = itemView.findViewById(R.id.tv_oldprice);
            tv7 = itemView.findViewById(R.id.tv7);
        }
    }
    private TowAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(TowAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
