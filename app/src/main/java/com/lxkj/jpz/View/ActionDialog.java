package com.lxkj.jpz.View;

import android.app.Dialog;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.jpz.R;
import com.lxkj.jpz.Utils.StringUtil_li;


/**
 * kylin on 2017/12/16.
 */

public class ActionDialog extends Dialog {

    Context context;//上下文
    private TextView dialog_title,dialog_content,dialog_left,dialog_right,tv_context_two,tv_context,tv_app;
    private ImageView tip;
    private EditText et_password;

    private View henXian;

    public TextView getTitleText(){
        return dialog_title;
    }

    public TextView getLeftText(){
        return dialog_left;
    }

    public TextView getRightText(){
        return dialog_right;
    }

    private OnActionClickListener onActionClickListener;
    private OnxieyiClickListener onxieyiClickListener;
    public interface OnActionClickListener{
        void onLeftClick();
        void onRightClick();
    }
    public void setOnActionClickListener(OnActionClickListener onActionClickListener){
        this.onActionClickListener = onActionClickListener;
    }

    public interface OnxieyiClickListener{
        void onLeftClick();
        void onRightClick();
        void onZhuce();
        void onyinsi();
    }
    public void setOnxieyiClickListener(OnxieyiClickListener onActionClickListener){
        this.onxieyiClickListener = onActionClickListener;
    }
    private OnPasswordClickListener onPasswordClickListener;
     public interface OnPasswordClickListener{
         void onPassword(String password);
         void onLeftClick();

     }
    public void setOnPasswordClickListener(OnPasswordClickListener onPasswordClickListener){
        this.onPasswordClickListener = onPasswordClickListener;
    }




    public ActionDialog(Context context, String title,String text,String text_two, String left, String right) {
        super(context, R.style.processDialog);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_action, null);
        dialog_title = view.findViewById(R.id.tv_title);
        dialog_left = view.findViewById(R.id.dialog_action_cancle);
        dialog_right = view.findViewById(R.id.dialog_action_confirm);
        henXian = view.findViewById(R.id.henXian);
        tv_context = view.findViewById(R.id.tv_context);
        tv_context_two = view.findViewById(R.id.tv_context_two);

        dialog_title.setText(title);
        dialog_left.setText(left);
        dialog_right.setText(right);
        tv_context.setText(text);
        tv_context_two.setText(text_two);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onRightClick();
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.actionDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public ActionDialog(Context context,String text) {
        super(context, R.style.processDialog);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_copy, null);
        dialog_title = view.findViewById(R.id.tv_title);
        dialog_left = view.findViewById(R.id.dialog_action_cancle);
        dialog_right = view.findViewById(R.id.dialog_action_confirm);
        henXian = view.findViewById(R.id.henXian);
        tv_context = view.findViewById(R.id.tv_context);

        tv_context.setText(text);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onLeftClick();
                }

                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onRightClick();
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.actionDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    public ActionDialog(final Context context, String title, String text, String left, String right) {
        super(context, R.style.processDialog);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_protocol, null);
        dialog_title = view.findViewById(R.id.tv_title);
        dialog_left = view.findViewById(R.id.dialog_action_cancle);
        dialog_right = view.findViewById(R.id.dialog_action_confirm);
        henXian = view.findViewById(R.id.henXian);
        tv_context = view.findViewById(R.id.tv_context);
        tv_app = view.findViewById(R.id.tv_app);
        final SpannableStringBuilder style = new SpannableStringBuilder();
        style.append(text);

        //设置部分文字点击事件
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(context.getResources().getColor(R.color.transparent));
                style.setSpan(bgColorSpan, 13, 19, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                if (onxieyiClickListener!=null){
                    onxieyiClickListener.onZhuce();
                }
            }
        };
        //设置部分文字点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                BackgroundColorSpan bgColorSpan1 = new BackgroundColorSpan(context.getResources().getColor(R.color.transparent));
                style.setSpan(bgColorSpan1, 20, 26, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                if (onxieyiClickListener!=null){
                    onxieyiClickListener.onyinsi();
                }
            }
        };
        style.setSpan(clickableSpan,13,19, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(clickableSpan1,20,26, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.red_them));
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(context.getResources().getColor(R.color.red_them));




        style.setSpan(foregroundColorSpan1, 13, 19, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(foregroundColorSpan, 20, 26, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);




        dialog_title.setText(title);
        dialog_left.setText(left);
        dialog_right.setText(right);
        tv_app.setMovementMethod(LinkMovementMethod.getInstance());
        tv_app.setText(style);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onxieyiClickListener!=null){
                    onxieyiClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onxieyiClickListener!=null){
                    onxieyiClickListener.onRightClick();
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.actionDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public ActionDialog(final Context context, String title, String left, String right) {
        super(context, R.style.processDialog);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_password, null);
        dialog_title = view.findViewById(R.id.tv_title);
        dialog_left = view.findViewById(R.id.dialog_action_cancle);
        dialog_right = view.findViewById(R.id.dialog_action_confirm);
        henXian = view.findViewById(R.id.henXian);
        tv_context= view.findViewById(R.id.tv_context);

        dialog_title.setText(title);
        dialog_left.setText(left);
        dialog_right.setText(right);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPasswordClickListener!=null){
                    onPasswordClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPasswordClickListener!=null){
                    if (StringUtil_li.isSpace(et_password.getText().toString())){
                        Toast.makeText(context,"密码不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        onPasswordClickListener.onPassword(et_password.getText().toString());
                    }
                }
                dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        //window.setWindowAnimations(R.style.actionDialog);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }



    private View divider;

//    public ActionDialog(Context context , String titleStr,String text ,String rightStr, boolean cancleAble) {
    public ActionDialog(Context context , boolean cancleAble) {
        super(context, R.style.processDialog);
        this.context = context;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        dialog_title = view.findViewById(R.id.tv_title);
        tv_context = view.findViewById(R.id.tv_context);
        dialog_left = view.findViewById(R.id.dialog_action_cancle);
        dialog_right = view.findViewById(R.id.dialog_action_confirm);
        henXian = view.findViewById(R.id.henXian);
//        dialog_title.setText(titleStr);
//        tv_context.setText(text);
//        dialog_left.setText("取消");
//        dialog_right.setText(rightStr);

        dialog_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onLeftClick();
                }
                dismiss();
            }
        });

        dialog_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onActionClickListener!=null){
                    onActionClickListener.onRightClick();
                }
                //dismiss();
            }
        });

        this.setContentView(view);
        this.setCancelable(cancleAble);
        this.setCanceledOnTouchOutside(false);

        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void setShowCancle(boolean b){
        if (b){
            dialog_left.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }else{
            dialog_left.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }
    }


    /*更新时的是否可取消*/
    public void setShowAction(boolean b){
        if (b){
            dialog_right.setVisibility(View.GONE);
            dialog_left.setVisibility(View.VISIBLE);
            divider.setVisibility(View.GONE);
            henXian.setVisibility(View.VISIBLE);
        }else{
            dialog_right.setVisibility(View.GONE);
            dialog_left.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
            henXian.setVisibility(View.GONE);
        }
    }


}
