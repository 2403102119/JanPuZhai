package com.lxkj.jpz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lxkj.jpz.Bean.Orderdetailbean;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.GridImgAdapter;
import com.lxkj.jpz.Utils.ImageItem;
import com.lxkj.jpz.View.FeedBackGridView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created ：李迪迦
 * on:2019/12/3 0003.
 * Describe :
 */

public class AppraiseAdapter extends  RecyclerView.Adapter<AppraiseAdapter.MyHolder>  {

    private GridImgAdapter bannerImageAdapter;
    private EvaluateListener evaluateListener;
    private AddImageListener addImageListener;
    private int currentImage = -2;
    private Context context;
    private List<Orderdetailbean.OrderDetailBean.OrderItemBean> list;

    public AppraiseAdapter(Context context, List<Orderdetailbean.OrderDetailBean.OrderItemBean> list,AddImageListener addImageListener) {
        this.context = context;
        this.list = list;
        this.addImageListener = addImageListener;
    }
    public void setOnEvaluateListener(EvaluateListener evaluateListener) {
        this.evaluateListener = evaluateListener;
    }
    public void setImageData(int postion, ArrayList<ImageItem> evaluateSelectPath) {
        list.get(postion).getEvaluateSelectPath().addAll(evaluateSelectPath);
        notifyDataSetChanged();
    }
    @Override
    public AppraiseAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appraise, parent, false);
        return new AppraiseAdapter.MyHolder(view);
    }
    @Override
    public void onBindViewHolder(AppraiseAdapter.MyHolder holder, final int position) {
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions()
                .error(R.mipmap.logo)
                .placeholder(R.mipmap.logo))
                .load(list.get(position).getProductImage())
                .into(holder.image2);
        holder.tv1.setText(list.get(position).getProductName());
        holder.tv2.setText("规格："+list.get(position).getProductSkuName1()+"  "+list.get(position).getProductSkuName2());
        holder.tv_number.setText("×"+list.get(position).getProductCount());
        holder.tv3.setText("共"+list.get(position).getProductCount()+"件");
        BigDecimal privice = new BigDecimal(list.get(position).getProductPrice());
        BigDecimal count = new BigDecimal(list.get(position).getProductCount());
        holder.tv_money.setText("合计: ¥"+privice.multiply(count).toString());



        //添加图片

        bannerImageAdapter = new GridImgAdapter((Activity) context, list.get(position).getEvaluateSelectPath(), -1);
        holder.gvBannerImage.setAdapter(bannerImageAdapter);
        bannerImageAdapter.setMaxSize(3);

        bannerImageAdapter.setAddImageListencer(new GridImgAdapter.AddImageListencer() {
            @Override
            public void addImageClicked(GridImgAdapter adapter) {
                if (null != addImageListener)
                    addImageListener.onAdd(position, list.get(position).getmSelectPath());
            }
        });
        bannerImageAdapter.setDelImageListencer(new GridImgAdapter.DelImageListencer() {
            @Override
            public void delImageAtPostion(int childPostion, GridImgAdapter adapter) {
                currentImage = adapter.getNumber();
                if (currentImage == -1) {
                    if (null != addImageListener)
                        addImageListener.onRemove(position,childPostion);
                    list.get(position).getEvaluateSelectPath().remove(childPostion);
                    notifyDataSetChanged();
                }
            }
        });

        holder.score.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                if (null != evaluateListener)
                    evaluateListener.onEvaluate(position, (int) rating + "", null);
            }
        });


        //内容
        holder.editFeed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != evaluateListener)
                    evaluateListener.onEvaluate(position, null, editable.toString());
            }
        });


    }
    @Override
    public long getItemId(int i) {
        return i;
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
        RoundedImageView image2;
        TextView tv1,tv2,tv_number,tv3,tv_money;
        FeedBackGridView gvBannerImage;
        SimpleRatingBar score;
        EditText editFeed;
        LinearLayout ll_all;
        public MyHolder(View itemView) {
            super(itemView);
            image2 = itemView.findViewById(R.id.image2);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv3 = itemView.findViewById(R.id.tv3);
            tv_money = itemView.findViewById(R.id.tv_money);
            gvBannerImage = itemView.findViewById(R.id.photo1);
            score = itemView.findViewById(R.id.mr_score);
            editFeed = itemView.findViewById(R.id.editFeed);
            ll_all = itemView.findViewById(R.id.ll_all);
        }
    }
    private AppraiseAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(AppraiseAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void OnItemClickListener(int firstPosition);
    }


    public interface AddImageListener {
        void onAdd(int position, ArrayList<String> mSelectPath);
        void onRemove(int positon,int childPosition);
    }
    public interface EvaluateListener {
        void onEvaluate(int position, String point, String content);
    }
}
