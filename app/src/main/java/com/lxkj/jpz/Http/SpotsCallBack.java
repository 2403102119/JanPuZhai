package com.lxkj.jpz.Http;

import android.app.Dialog;
import android.content.Context;

import com.hss01248.dialog.StyledDialog;

import okhttp3.Request;
import okhttp3.Response;


public abstract class SpotsCallBack<T> extends BaseCallback<T> {
    private Context mContext;
    private Dialog mDialog;


    public SpotsCallBack(Context context) {
        mContext = context;
        StyledDialog.init(context);
        showDialog();
    }


    public void showDialog() {
        try {
            mDialog = StyledDialog.buildLoading().show();
        } catch (Exception e) {
        }
    }

    public void dismissDialog() {
        try {
            mDialog.dismiss();
        } catch (Exception e) {

        }
    }
    @Override
    public void onFailure(Request request, Exception e) {
        dismissDialog();
    }


    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }
}
