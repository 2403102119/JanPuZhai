package com.lxkj.jpz.Service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.webkit.MimeTypeMap;


import com.lxkj.jpz.Utils.ToastFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by ray_L_Pain on 2017/10/27.
 */

public class DownloadService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    private final static String APKNAME = "whsq.apk";

    public final static int UPDATE_PROGRESS = 0;

    public final static int UPDATE_FAILED = 2;



    public DownloadService() {
        super("DownloadService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DownloadService","服务启动了");
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onHandleIntent(Intent intent) {
        isEnvironmentAvailable();

        String urlToDownload = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");

        Log.e("DownloadService",urlToDownload);
//        download(urlToDownload);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),APKNAME);

        if (file.exists()){
            file.delete();
        }


        InputStream is = null;
        FileOutputStream fos = null;

        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            URL url = new URL(urlToDownload);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.connect();
            int fileLength = conn.getContentLength();
            Log.e("DownloadService","fileLength == " + fileLength);

            if (conn.getResponseCode() == 200){
                is = conn.getInputStream();
                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = is.read(data)) != -1) {
                    total += count;
                    Bundle bundle = new Bundle();
                    bundle.putInt("process", (int) (total * 100/fileLength));
                    receiver.send(UPDATE_PROGRESS,bundle);
                    fos.write(data, 0, count);
                    fos.flush();
                }
                Log.e("DownloadService","total == " + total);
                //install();
            } else {
                Log.e("DownloadService",conn.getResponseCode()+"");
                receiver.send(UPDATE_FAILED,null);
            }

        } catch (MalformedURLException e) {
            receiver.send(UPDATE_FAILED,null);
            e.printStackTrace();
        } catch (IOException e) {
            receiver.send(UPDATE_FAILED,null);
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void download(String url){
        Uri uri = Uri.parse(url);
        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(request.NETWORK_MOBILE | request.NETWORK_WIFI);

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("我惠省钱");
        request.setVisibleInDownloadsUi(true);
        request.setAllowedOverRoaming(false);
        request.setDestinationInExternalFilesDir(this,Environment.DIRECTORY_DOWNLOADS,APKNAME);
        manager.enqueue(request);
    }


    private void isEnvironmentAvailable(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //sdcard状态是没有挂载的情况
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ToastFactory.getToast(getApplicationContext(),"SD卡没有挂载").show();
                }
            });
            return ;
        }
    }

    private void install() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(FileProvider.getUriForFile(this,"com.lx.whsq.Fileprovider",new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),APKNAME)), "application/vnd.android.package-archive");
            startActivity(install);

        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(
                    new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),APKNAME)),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        }
    }
}
