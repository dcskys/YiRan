package com.dc.androidtool.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.dc.androidtool.MyApplication;

/**
 * 消息提示类
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {  //存在，可以直接修改Toast的内容，防止多个使用时，一个接一个的调用
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }



    /*任意使用*/
    public static void Short(@NonNull CharSequence sequence) {
        Toast.makeText(MyApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }

    public static void Long(@NonNull CharSequence sequence) {
        Toast.makeText(MyApplication.getContext(), sequence, Toast.LENGTH_SHORT).show();
    }
}