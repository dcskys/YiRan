package com.dc.androidtool.utils;

import android.content.Context;
import android.widget.Toast;

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
}