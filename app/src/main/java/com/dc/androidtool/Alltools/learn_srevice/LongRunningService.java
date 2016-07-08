package com.dc.androidtool.Alltools.learn_srevice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

/*长期在后台执行定时任务的服务*/
public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return  null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {  //这里面执行具体的操作逻辑
                Log.d("LongRunningService", "executed at " + new Date().
                        toString());
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000; // 这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour; //任务触发的时间间隔

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); //广播，getBroadcast
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//看笔记，唤醒cpu启动广播

       //android4.4 以后定时任务不准， 想要精准可以使用setExact()代替set

        return super.onStartCommand(intent, flags, startId);
    }
}
