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
 * Created by Administrator on 2019/11/7 0007.
 */

public class NiceAdapter extends RecyclerView.Adapter<NiceAdapter.MyHolder> {
    private Context context;
    private List<String> list;
    private String imageUrl;

    public NiceAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public NiceAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selectlayout, parent, false);
        return new NiceAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(NiceAdapter.MyHolder holder, final int position) {
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onItemClickListener.OnItemClickListener(position);
             }
         });
         holder.name.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }else {
            return  list.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
       TextView name;
        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);

        }
    }
    private NiceAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(NiceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
