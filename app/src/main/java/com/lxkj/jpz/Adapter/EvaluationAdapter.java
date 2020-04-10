package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.Evluationbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.SQSP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class EvaluationAdapter extends  RecyclerView.Adapter<EvaluationAdapter.MyHolder> {
    private Context context;
    private List<Evluationbean.DataListBean> list;
    private List<String> item_list = new ArrayList<>();
    StaggeredGridLayoutManager layoutManager;
    Recycle_one_itemAdapter recycleOneItemAdapter;
    private static final String TAG = "EvaluationAdapter";
    public EvaluationAdapter(Context context, List<Evluationbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public EvaluationAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleone, parent, false);
        return new EvaluationAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(EvaluationAdapter.MyHolder holder, final int position) {

        for (int i = 0; i <list.get(position).getImages().size() ; i++) {
//            if (StringUtil_li.isSpace(list.get(position).getImages().get(i)))
                item_list=list.get(position).getImages();
        }
        Log.i(TAG, "onBindViewHolder: "+item_list);

        layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        holder.recycle.setLayoutManager(layoutManager);
        recycleOneItemAdapter=new Recycle_one_itemAdapter(context,item_list);
        holder.recycle.setAdapter(recycleOneItemAdapter);
        recycleOneItemAdapter.setOnItemClickListener(new Recycle_one_itemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                onItemClickListener.OnbuttonImage(position);
            }
        });

        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(SQSP.user_icon)
                .into(holder.im_userIcon);
       holder.tv_nickName.setText(list.get(position).getTitle());

        holder.rb.setRating(Float.parseFloat(list.get(position).getScore()));


        holder.tv_content.setText(list.get(position).getContent());

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
        ImageView im_userIcon;
        TextView tv_nickName,tv_content;
        SimpleRatingBar rb;
        public MyHolder(View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.recycle);
            im_userIcon = itemView.findViewById(R.id.im_userIcon);
            tv_nickName = itemView.findViewById(R.id.tv_nickName);
            rb = itemView.findViewById(R.id.rb);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
    private EvaluationAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(EvaluationAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);

    }
}
