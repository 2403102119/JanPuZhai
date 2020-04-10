package com.lxkj.jpz.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.lxkj.jpz.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by kxn on 2018/7/17 0017.
 */
public class PicassoUtil {
    public static void setImag(Context context, String url, ImageView iv) {
        if (!StringUtil.isEmpty(url))
            Picasso.with(context).load(url).placeholder(R.mipmap.ic_defaut).error(R.mipmap.ic_defaut).into(iv);
        else
            Picasso.with(context).load(R.mipmap.ic_defaut).into(iv);
    }
    public static void setImag(Context context, int res, ImageView iv) {
            Picasso.with(context).load(res).placeholder(R.mipmap.ic_defaut).error(R.mipmap.ic_defaut).into(iv);
    }
    public static void setImag(Context context, File file, ImageView iv) {
        Picasso.with(context).load(file).into(iv);
    }
}
