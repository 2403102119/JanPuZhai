package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lxkj.jpz.Bean.ResonBean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/19 0019.
 * Describe :退款原因列表
 */

public class RounditemAdapter extends RecyclerView.Adapter<RounditemAdapter.MyHolder> {
    private Context context;
    private List<ResonBean.DataListBean> list;

    public RounditemAdapter(Context context, List<ResonBean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public RounditemAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_round_item, parent, false);
        return new RounditemAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RounditemAdapter.MyHolder holder, final int position) {

        //判断CheckBox的状态
        if(list.get(position).isChecked()){
            holder.imageSel.setChecked(true);//选中
        }else {
            holder.imageSel.setChecked(false);//未选中
        }

        holder.tv_title.setText(list.get(position).getTitle());
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
        TextView tv_title;
        CheckBox imageSel;
        public MyHolder(View itemView) {
            super(itemView);
            tv_title= itemView.findViewById(R.id.tv_title);
            imageSel= itemView.findViewById(R.id.imageSel);
        }
    }
    private RounditemAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RounditemAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
    }
}
