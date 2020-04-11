package com.lxkj.jpz.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.jpz.Bean.Orderbean;
import com.lxkj.jpz.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/11/19 0019.
 * Describe :订单列表
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    private Context context;
    private List<Orderbean.DataListBean> list;
    private List<Orderbean.DataListBean.OrderItemBean> item_list = new ArrayList<>();
    LinearLayoutManager layoutManager;
    OrderitemAdapter recycleOneItemAdapter;
    public OrderAdapter(Context context, List<Orderbean.DataListBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public OrderAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderAdapter.MyHolder holder, final int position) {
        item_list.clear();

        BigDecimal privice = new BigDecimal(list.get(position).getOrderPrice());
        BigDecimal count = new BigDecimal(list.get(position).getFright());
        list.get(position).getOrderItem().get(0).setZongji(privice.add(count).toString());

        item_list.addAll(list.get(position).getOrderItem());

        holder.tv1.setText(context.getString(R.string.dingdanbianhao)+""+list.get(position).getOrderid());
        if (list.get(position).getStatus().equals("0")){
            holder.tv2.setText(context.getString(R.string.obligation));
            holder.tv_qufukaun.setText(context.getString(R.string.qufukuan));
            holder.tv_chakan.setVisibility(View.GONE);
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("1")){
            holder.tv2.setText(context.getString(R.string.To_send_the_goods));
        }else if (list.get(position).getStatus().equals("2")){
            holder.tv2.setText(context.getString(R.string.wait_for_receiving));
            holder.tv_qufukaun.setText(context.getString(R.string.quedingshouhuo));
            holder.tv_chakan.setVisibility(View.VISIBLE);
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("3")){
            holder.tv2.setText(context.getString(R.string.remain_to_be_evaluated));
            holder.tv_qufukaun.setText(context.getString(R.string.qupingjia));
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("4")){
            holder.tv2.setText(context.getString(R.string.yiwancheng));
        }else if (list.get(position).getStatus().equals("5")){
            holder.tv2.setText(context.getString(R.string.yiquxiao));
        }else if (list.get(position).getStatus().equals("6")){
            holder.tv2.setText(context.getString(R.string.tuikaunzhong));
            holder.tv_qufukaun.setText(context.getString(R.string.chakanxiangqing));
            holder.tv_qufukaun.setVisibility(View.VISIBLE);
        }else if (list.get(position).getStatus().equals("7")){
            holder.tv2.setText(context.getString(R.string.yituikuan));
        }else if (list.get(position).getStatus().equals("8")){
            holder.tv2.setText(context.getString(R.string.tuikuanyijujue));
        }
        holder.tv_time.setText(list.get(position).getAdtime());
        layoutManager = new LinearLayoutManager(context);
        holder.recycle.setLayoutManager(layoutManager);
        recycleOneItemAdapter=new OrderitemAdapter(context,item_list);
        holder.recycle.setAdapter(recycleOneItemAdapter);
        recycleOneItemAdapter.setOnItemClickListener(new OrderitemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int firstPosition) {
                onItemClickListener.OnbuttonImage(position);
            }
        });
        holder.tv_chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.chakanwuliu(position);
            }
        });
        holder.tv_qufukaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tv_qufukaun.getText().toString().equals(context.getString(R.string.chakanxiangqing))){
                    onItemClickListener.tuikuanxiangqing(position);
                }else if (holder.tv_qufukaun.getText().toString().equals(context.getString(R.string.qupingjia))){
                    onItemClickListener.qupingjia(position);
                }else if (holder.tv_qufukaun.getText().toString().equals(context.getString(R.string.quedingshouhuo))){
                    onItemClickListener.querenshouhuo(position);
                }else if (holder.tv_qufukaun.getText().toString().equals(context.getString(R.string.qufukuan))){
                    onItemClickListener.qufukuan(position);
                }
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
        RecyclerView recycle;
        TextView tv1,tv2,tv_time,tv_chakan,tv_qufukaun;
        public MyHolder(View itemView) {
            super(itemView);
            recycle = itemView.findViewById(R.id.item_recycle);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_chakan = itemView.findViewById(R.id.tv_chakan);
            tv_qufukaun = itemView.findViewById(R.id.tv_qufukaun);
        }
    }
    private OrderAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OrderAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
        void OnbuttonImage(int position);
        void tuikuanxiangqing(int position);
        void qupingjia(int position);
        void chakanwuliu(int position);
        void querenshouhuo(int position);
        void qufukuan(int position);

    }
}
