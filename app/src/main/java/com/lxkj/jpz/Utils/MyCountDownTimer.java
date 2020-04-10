package com.lxkj.jpz.Utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.lxkj.jpz.R;

/**
 * Created by Administrator on 2017/11/23.
 */

/*设置短信验证码多少秒后重新获取*/
public class MyCountDownTimer extends CountDownTimer {
    private Context context;
    private TextView textView;

    public MyCountDownTimer(Context context, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
//        textView.setTextColor(ContextCompat.getColor(context, R.color.blue));
//        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.round_bottom_right_grey_8));
    }

    @Override
    public void onFinish() {
        textView.setText(R.string.regain);
        textView.setClickable(true);
//        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
//        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.round_bottom_right_blue_8));
    }
}
