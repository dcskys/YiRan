package com.dc.androidtool.Alltools.listen_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/*
* 静态注册的广播 用于实现开机启动*/
public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //开机启动，不要添加过多逻辑  ，可以进行打开通知或启动服务
        Toast.makeText(context, "Boot Complete", Toast.LENGTH_LONG).show();
    }
}
