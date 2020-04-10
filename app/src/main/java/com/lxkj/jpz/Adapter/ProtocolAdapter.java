package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jpz.Bean.privacyBean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/11 0011.
 * Describe :
 */

public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.MyHolder>  {
    private Context context;
    private List<privacyBean.DataListBean> list;
    public ProtocolAdapter(Context context, List<privacyBean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public ProtocolAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new ProtocolAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ProtocolAdapter.MyHolder holder, final int position) {
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
        public MyHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
    private ProtocolAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ProtocolAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
