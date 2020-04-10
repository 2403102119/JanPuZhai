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
import com.lxkj.jpz.Bean.Productlistbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class RightAdapter extends RecyclerView.Adapter<RightAdapter.MyHolder>  {
    private Context context;
    private List<Productlistbean.DataListBean> list;
    public RightAdapter(Context context, List<Productlistbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public RightAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_right, parent, false);
        return new RightAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RightAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getLogo())
                .into(holder.image2);
        holder.tv_name.setText(list.get(position).getTitle());
        holder.tv_money.setText(list.get(position).getOldPrice());
        if (StringUtil_li.isSpace( list.get(position).getOldPrice())){
            holder.tv_oldPrice.setText("$0.00");
        }else {

            holder.tv_oldPrice.setText("$"+list.get(position).getOldPrice());
        }
        holder.tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG );
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
        TextView tv_name,tv_money,tv_oldPrice;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_money = itemView.findViewById(R.id.tv_money);
            tv_oldPrice = itemView.findViewById(R.id.tv_oldPrice);
        }
    }
    private RightAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RightAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
