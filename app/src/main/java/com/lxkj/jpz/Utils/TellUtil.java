package com.lxkj.jpz.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.lxkj.jpz.R;


/**
 * Created by kxn on 2018/5/15 0015.
 */

public class TellUtil {
    public static void tell(final Context context, final String phoneNum) {
        StyledDialog.init(context);
        StyledDialog.buildIosAlert("拨打电话", "确认要拨打"+phoneNum+"？", new MyDialogListener() {
            @Override
            public void onFirst() {
            }

            @Override
            public void onSecond() {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                context.startActivity(intent);
            }
        }).setBtnColor(R.color.txt_lv2, R.color.appColor, 0).setBtnText("取消", "确定").show();
    }
}
