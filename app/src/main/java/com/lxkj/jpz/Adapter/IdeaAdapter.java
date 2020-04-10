package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jpz.R;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/25 0025.
 * Describe :
 */

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.MyHolder>{
    private Context context;
    private List<String> list;
    public IdeaAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public IdeaAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_idea, parent, false);
        return new IdeaAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(IdeaAdapter.MyHolder holder, final int position) {
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onItemClickListener.OnItemClickListener(position);
                 }
             });
             holder.tv_bankname.setText(list.get(position));
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
         TextView tv_bankname;
        public MyHolder(View itemView) {
            super(itemView);
            tv_bankname = itemView.findViewById(R.id.tv_bankname);
        }
    }
    private IdeaAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(IdeaAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
