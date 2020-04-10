package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/26 0026.
 * Describe :
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.MyHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;
    private int selectPosition = -1;
    public LeftAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    public void setSelectPosition(int position){
        this.selectPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public LeftAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stair, parent, false);
        return new LeftAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final LeftAdapter.MyHolder holder, final int position) {
        holder.tv_type.setText(list.get(position));
        holder.ll_sell.setTag(position);
        if (position==selectPosition){
            holder.view_green.setBackgroundColor(context.getResources().getColor(R.color.appColor));
            holder.ll_sell.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tv_type.setTextColor(Color.parseColor("#111111"));
        }else {
            holder.view_green.setBackgroundColor(context.getResources().getColor(R.color.view_color));
            holder.ll_sell.setBackgroundColor(context.getResources().getColor(R.color.view_color));
            holder.tv_type.setTextColor(Color.parseColor("#666666"));
        }


    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public void onClick(View v) {
        //注意这里要给某一个控件添加点击事件
        int position = (int) v.getTag();      //getTag()获取数据
        if (onItemClickListener != null) {
            switch (v.getId()) {
                case R.id.ll_sell:
                    selectPosition = position;
                    onItemClickListener.OnItemClickListener(position);
                    notifyDataSetChanged();
                    break;
                default:
                    selectPosition = position;
                    break;
            }
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_sell;
        View view_green;
        TextView tv_type;
        public MyHolder(View itemView) {
            super(itemView);

            ll_sell = itemView.findViewById(R.id.ll_sell);
            view_green = itemView.findViewById(R.id.view_green);
            tv_type = itemView.findViewById(R.id.tv_type);
            ll_sell.setOnClickListener(LeftAdapter.this);
        }
    }

    /*------------------以下为item中的button控件点击事件处理------------------*/
    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    private LeftAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(LeftAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);

    }
}
