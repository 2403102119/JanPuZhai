package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.App;
import com.lxkj.jpz.Bean.MyFriendsBean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created ：李迪迦
 * on:2020/3/20 0020.
 * Describe :
 */

public class RecordAdapter extends  RecyclerView.Adapter<RecordAdapter.MyHolder>   {
    private Context context;
    private List<MyFriendsBean.DataListBean> list;
    public RecordAdapter(Context context, List<MyFriendsBean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public RecordAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        return new RecordAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.MyHolder holder, final int position) {
        Glide.with(App.context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.ic_figure_head)
                .placeholder(R.mipmap.ic_figure_head))
                .load(list.get(position).getIcon())
                .into(holder.ri_icon);
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_phone.setText(StringUtil_li.replacePhone(list.get(position).getPhone()));
        holder.tv_moey.setText("+"+list.get(position).getAmount());
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
       private RoundedImageView ri_icon;
       private TextView tv_name,tv_phone,tv_moey;
        public MyHolder(View itemView) {
            super(itemView);
            ri_icon = itemView.findViewById(R.id.ri_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_moey = itemView.findViewById(R.id.tv_moey);
        }
    }
    private RecordAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecordAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);


    }
}
