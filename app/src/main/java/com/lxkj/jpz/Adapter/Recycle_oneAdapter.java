package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lxkj.jpz.Bean.productcommentbean;
import com.lxkj.jpz.R;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/10/11 0011.
 */

public class Recycle_oneAdapter extends RecyclerView.Adapter<Recycle_oneAdapter.MyHolder> {
    private Context context;
    private List<productcommentbean.DataListBean> list;
    private List<String> item_list = new ArrayList<>();
    StaggeredGridLayoutManager layoutManager;
    Recycle_one_itemAdapter recycleOneItemAdapter;
    public Recycle_oneAdapter(Context context, List<productcommentbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public Recycle_oneAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleone, parent, false);
        return new Recycle_oneAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(Recycle_oneAdapter.MyHolder holder, final int position) {
        holder.tv_nickName.setText(list.get(position).getUserName());
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getUserIcon())
                .into(holder.im_userIcon);
        holder.rb.setRating(Float.parseFloat(list.get(position).getCommentScore()));
        holder.tv_adtime.setText(list.get(position).getCommentDate());
        holder.tv_content.setText(list.get(position).getCommentContent());
        item_list.clear();
        for (int i = 0; i <list.get(position).getCommentImages().size() ; i++) {
            item_list.add(list.get(position).getCommentImages().get(i));
        }
        layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        holder.recycle.setLayoutManager(layoutManager);
        recycleOneItemAdapter=new Recycle_one_itemAdapter(context,item_list);
        holder.recycle.setAdapter(recycleOneItemAdapter);
        recycleOneItemAdapter.setOnItemClickListener(new Recycle_one_itemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                onItemClickListener.OnbuttonImage(position,firstPosition);
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
        RecyclerView recycle;
        TextView tv_nickName,tv_adtime,tv_content;
        RoundedImageView im_userIcon;
        SimpleRatingBar rb;
        public MyHolder(View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.recycle);
            tv_nickName = itemView.findViewById(R.id.tv_nickName);
            im_userIcon = itemView.findViewById(R.id.im_userIcon);
            rb = itemView.findViewById(R.id.rb);
            tv_adtime = itemView.findViewById(R.id.tv_adtime);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
    private Recycle_oneAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(Recycle_oneAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position,int item_position);

    }
}
