package com.lxkj.jpz.Fragment;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxkj.jpz.R;
import com.lxkj.jpz.View.CustomJzvd.MyJzvdStd;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @author ZXY
 * @since 2019/12/13
 * Email：1120202716@qq.com
 * function：
 */
public class VideoFragment extends LazyFragment {

    private String videoUrl;
    private String videoImg;
    @BindView(R.id.jz_video)
    MyJzvdStd myJzvdStd;
    private ImageView im_xiazai;

    public static VideoFragment newInstance(String videoUrl, String videoImg) {
        VideoFragment fragmentCommon = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", videoUrl);
        bundle.putString("videoImg", videoImg);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
        super.onUserInvisible();
        myJzvdStd.releaseAllVideos();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //home back
//        JzvdStd.goOnPlayOnResume();
//    }

    @Override
    public void onPause() {
        super.onPause();
        //     Jzvd.clearSavedProgress(this, null);
        //home back
//        JzvdStd.goOnPlayOnPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            videoUrl = bundle.getString("videoUrl");
            videoImg = bundle.getString("videoImg");
        }

        myJzvdStd.setUp(videoUrl, "", JzvdStd.SCREEN_NORMAL);
        //两个变量控制全屏前后的屏幕方向
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        myJzvdStd.startFullscreenDirectly(getActivity(), JzvdStd.class,videoUrl, "");
        myJzvdStd.startVideo();

        RequestOptions options = new RequestOptions()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .format(DecodeFormat.PREFER_ARGB_8888)
//                .centerCrop();
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(getActivity())
                .load(videoImg)
                .apply(options)
                .into(myJzvdStd.thumbImageView);
//

//         myJzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
//                 , "饺子快长大");
//        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(myJzvdStd.thumbImageView);
//

    }

    /**
     * 视频存在本地
     *
     * @param paramContext
     * @param paramFile
     * @param paramLong
     * @return
     */
    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File sd1 = Environment.getExternalStorageDirectory();
            String path1 = sd1.getPath() + "/lfmf";
            File myfile1 = new File(path1);
            if (!myfile1.exists()) {
                myfile1.mkdir();
            }
            File file = new File(myfile1, "lfmf.mp4");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // 在onStop时释放掉播放器
        JzvdStd.releaseAllVideos();
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(EventBusVideo event) {
//        /* Do something */
//        if (event.getAction().equals("关闭视频的播放")) {
//            JzvdStd.releaseAllVideos();
//        }
//    }


}
