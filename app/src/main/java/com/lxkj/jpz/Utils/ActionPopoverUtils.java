package com.lxkj.jpz.Utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lxkj.jpz.R;


/**
 * Created by Administrator on 2017/11/29.
 */
public class ActionPopoverUtils {

    private Activity activity;

    public ActionPopoverUtils(Activity activity) {
        this.activity = activity;
    }

    ;


    private OnOnlyTipClickListener onOnlyTipClickListener;

    public interface OnOnlyTipClickListener {
        void onOnlyTipClick();
    }

    public void setOnOnlyTipClickListener(OnOnlyTipClickListener onOnlyTipClickListener) {
        this.onOnlyTipClickListener = onOnlyTipClickListener;
    }

    /*只有一个提示框的操作*/
    public void showTipDialog(String tipStr) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_tip, null);
        TextView tip = (TextView) view.findViewById(R.id.tip);
        tip.setText(tipStr);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onOnlyTipClickListener != null) {
                    onOnlyTipClickListener.onOnlyTipClick();
                }
            }
        });
    }
}
