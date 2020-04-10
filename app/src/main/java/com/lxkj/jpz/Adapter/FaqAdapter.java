package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jpz.Bean.Faqbean;
import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/15 0015.
 * Describe :常见问题列表
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyHolder> {
    private Context context;
    private List<Faqbean.DataListBean> list;
    public FaqAdapter(Context context, List<Faqbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public FaqAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new FaqAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(FaqAdapter.MyHolder holder, final int position) {
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
    private FaqAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(FaqAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
