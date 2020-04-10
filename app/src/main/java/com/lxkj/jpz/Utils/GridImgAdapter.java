package com.lxkj.jpz.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.lxkj.jpz.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class GridImgAdapter extends BaseAdapter {

    private final static int IMG_SIZE = FormatUtil.pixOfDip(60);
    private final static int CELL_SPACE = FormatUtil.pixOfDip(90);

    private final Activity act;
    private final List<ImageItem> imgItems;
    private final ExecutorService exeService;
    private int maxCount = 15;
    private int realCount = 2;
    private DelImageListencer delImageListencer;
    private AddImageListencer addImageListencer;
    private int number;

    public interface DelImageListencer {

        public void delImageAtPostion(int postion, GridImgAdapter adapter);
    }

    public interface AddImageListencer {

        public void addImageClicked(GridImgAdapter adapter);
    }

    public GridImgAdapter setDelImageListencer(
            DelImageListencer delImageListencer) {
        this.delImageListencer = delImageListencer;
        return this;
    }

    public GridImgAdapter setAddImageListencer(
            AddImageListencer addImageListencer) {
        this.addImageListencer = addImageListencer;
        return this;
    }

    public GridImgAdapter(final Activity act, final List<ImageItem> imgList, int number) {
        this.act = act;
        this.imgItems = imgList;
        this.exeService = new ScheduledThreadPoolExecutor(3);
        this.number = number;
    }

    public GridImgAdapter setMaxSize(final int max) {
        this.maxCount = max;
        return this;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(act).inflate(
                    R.layout.inser_book_item, null);
            convertView.setLayoutParams(new GridView.LayoutParams(
                    CELL_SPACE, CELL_SPACE));
        }
        ImageView imgView = (ImageView) convertView.findViewById(R.id.image);
        imgView.getLayoutParams().width = IMG_SIZE;
        imgView.getLayoutParams().height = IMG_SIZE;
        View delView = convertView.findViewById(R.id.del);
        if (position >= realCount) {
            delView.setVisibility(View.GONE);
            imgView.setTag(null);
            imgView.setImageResource(R.mipmap.shangchuanzhaopianl);
            imgView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    addImage();
                }
            });
        } else {
            imgView.setImageResource(0);
            imgView.setOnClickListener(null);
            final ImageItem imgData = imgItems.get(position);
            String url = imgData.getThumbnailPath();
            if (null == url) {
                url = imgData.getImagePath();
            }
            if (url.startsWith("http")) {
                PicassoUtil.setImag(act, url, imgView);
            } else
                loadImage(imgView, url);
            delView.setVisibility(View.VISIBLE);
            delView.setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delImage(position);
                        }
                    });
        }
        return convertView;
    }

    protected void delImage(int position) {
        delImageListencer.delImageAtPostion(position, this);
    }

    protected void addImage() {
        addImageListencer.addImageClicked(this);
    }

    private void loadImage(final ImageView iv, final String string) {
        if (null == string) {
            return;
        }
        iv.setTag(string);
        exeService.execute(new Runnable() {

            @Override
            public void run() {
                final Bitmap bm = BitmapUtil.decodeSquare(string, IMG_SIZE);
                setImageBitmap(iv, string, bm);
            }
        });
    }

    private void setImageBitmap(final ImageView iv,
                                final String string, final Bitmap bm) {
        iv.post(new Runnable() {

            @Override
            public void run() {
                if (string.equals(iv.getTag()))
                    iv.setImageBitmap(bm);
            }
        });
    }

    @Override
    public int getCount() {
        realCount = realCount();
        if (realCount < maxCount) {
            return realCount + 1;
        } else {
            return realCount;
        }
    }

    private int realCount() {
        return (null != imgItems) ? imgItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
