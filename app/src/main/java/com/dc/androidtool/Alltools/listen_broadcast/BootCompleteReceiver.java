package com.dc.androidtool.Alltools.listen_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.dc.androidtool.Alltools.learn_srevice.ServiceUtil;
import com.dc.androidtool.utils.Constants.CommonConstants;


/*
* 静态注册的广播 用于实现开机启动*/
public class BootCompleteReceiver extends BroadcastReceiver {
    public BootCompleteReceiver() {
    }
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;  //这样写才不报错。。

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {  //开机启动
            Log.i("BootBroadcastReceiver", "BroadcastReceiver onReceive here.... ");

            Handler handler = new Handler(Looper.getMainLooper());
            //after reboot the device,about 2 minutes later,upload the POI info to server
            handler.postDelayed(new Runnable() { //延迟2分钟
                @Override
                public void run() {
                     //判断服务是否在运行
                    if(!ServiceUtil.isServiceRunning(mContext, CommonConstants.POI_SERVICE)){
                        ServiceUtil.invokeTimerPOIService(mContext);
                    }
                }
            }, CommonConstants.BROADCAST_ELAPSED_TIME_DELAY);
        }

        //开机启动，不要添加过多逻辑  ，可以进行打开通知或启动服务
        Toast.makeText(context, "监听开机启动", Toast.LENGTH_LONG).show();
    }
}
