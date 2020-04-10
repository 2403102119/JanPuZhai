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
import com.lxkj.jpz.Bean.FirsePagebean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/13 0013.
 * Describe :
 */

public class GuessAdapter  extends RecyclerView.Adapter<GuessAdapter.MyHolder>  {
    private Context context;
    private List<FirsePagebean.JprouctListBean.PListBean> list;
    public GuessAdapter(Context context, List<FirsePagebean.JprouctListBean.PListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public GuessAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_guess, parent, false);
        return new GuessAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(GuessAdapter.MyHolder holder, final int position) {
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
    private GuessAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(GuessAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
