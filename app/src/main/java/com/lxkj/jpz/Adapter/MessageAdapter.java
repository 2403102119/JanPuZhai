package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.jpz.Bean.Messagebean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/18 0018.
 * Describe :消息列表
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {
    private Context context;
    private List<Messagebean.DataListBean> list;
    public MessageAdapter(Context context, List<Messagebean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public MessageAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MessageAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MyHolder holder, final int position) {
        holder.ll_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClickListener(position);
            }
        });
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_content.setText(list.get(position).getContent());
        holder.tv_adtime.setText(list.get(position).getAdtime());
        holder.tv_delate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnDelate(position);
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
        LinearLayout ll_sell;
        TextView tv_title,tv_content,tv_adtime,tv_delate;
        public MyHolder(View itemView) {
            super(itemView);
            ll_sell = itemView.findViewById(R.id.ll_sell);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_adtime = itemView.findViewById(R.id.tv_adtime);
            tv_delate = itemView.findViewById(R.id.tv_delate);
        }
    }
    private MessageAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MessageAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnDelate(int position);


    }
}
