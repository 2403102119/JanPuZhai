package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jpz.Bean.WuliuBean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/6 0006.
 * Describe :物流信息Adapter
 */

public class CourierAdapter  extends  RecyclerView.Adapter<CourierAdapter.MyHolder> {
    private Context context;
    private List<WuliuBean.TracesBean> list;
    public CourierAdapter(Context context, List<WuliuBean.TracesBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public CourierAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_kuaidi, parent, false);
        return new CourierAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CourierAdapter.MyHolder holder, final int position) {

        // 第一个位置
        if (holder.getLayoutPosition() == 0) {
            holder.view3.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.INVISIBLE);
            holder.tvName.getPaint().setFakeBoldText(true);
            holder.tvTime.getPaint().setFakeBoldText(true);
            holder.view1.setBackgroundResource(R.drawable.biankuang28);
            holder.view3.setBackgroundColor(Color.parseColor("#FFDD2828"));
            holder.tvName.setTextColor(Color.parseColor("#FFDD2828"));
            holder.tvTime.setTextColor(Color.parseColor("#FFDD2828"));
        } else {
            if (holder.getLayoutPosition() == list.size()-1){
                holder.view3.setVisibility(View.INVISIBLE);
                holder.view1.setBackgroundResource(R.drawable.biankuang29);
                holder.tvName.getPaint().setFakeBoldText(false);
                holder.tvTime.getPaint().setFakeBoldText(false);
                holder.view.setVisibility(View.VISIBLE);
                holder.tvName.setTextColor(Color.parseColor("#FFAAAAAA"));
                holder.tvTime.setTextColor(Color.parseColor("#FFAAAAAA"));
            }else {
                holder.view1.setBackgroundResource(R.drawable.biankuang29);
                holder.tvName.getPaint().setFakeBoldText(false);
                holder.tvTime.getPaint().setFakeBoldText(false);
                holder.view3.setVisibility(View.VISIBLE);
                holder.view.setVisibility(View.VISIBLE);
                holder.tvName.setTextColor(Color.parseColor("#FFAAAAAA"));
                holder.tvTime.setTextColor(Color.parseColor("#FFAAAAAA"));
            }

        }

        holder.tvName.setText(list.get(position).getAcceptStation());
        holder.tvTime.setText(list.get(position).getAcceptTime());
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
        TextView tvName,tvTime;
        View view,view3,view1;
        public MyHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            view = itemView.findViewById(R.id.view);
            view3 = itemView.findViewById(R.id.view3);
            view1 = itemView.findViewById(R.id.view1);
        }
    }
    private CourierAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(CourierAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnDealte(int position);


    }
}
