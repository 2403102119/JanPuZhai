package com.lxkj.jpz.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.jpz.Base.BaseActivity;
import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * Created ：李迪迦
 * on:${DATE}.
 * Describe :邀请海报
 */
public class PosterActivity extends BaseActivity {


  private ImageView icon1;
    private Bitmap qrCode;
    private Bitmap logo;
    private TextView tv_baocun;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContainer(R.layout.activity_poster);
        setTopTitle(getResources().getString(R.string.Invite_poster));
        icon1 = findViewById(R.id.icon1);
        tv_baocun = findViewById(R.id.tv_baocun);
        logo = BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
        qrCode = CodeCreator.createQRCode("https://www.pgyer.com/L9eP", 400, 400, logo);
        icon1.setImageBitmap(qrCode);
    }

    @Override
    protected void initEvent() {
        tv_baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringUtil_li.saveBmp2Gallery (mContext,createBitmap(icon1),"柬埔寨");
            }
        });
    }

    @Override
    protected void initData() {

    }
    private Bitmap createBitmap(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
