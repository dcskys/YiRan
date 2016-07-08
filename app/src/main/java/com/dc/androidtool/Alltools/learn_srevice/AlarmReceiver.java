package com.dc.androidtool.Alltools.learn_srevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/*定时任务启动的广播
*
* 实现了一个长期在后台执行的定时任务*/
public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);  //在广播中去启动服务
    }
}
