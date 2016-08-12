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

    private String TAG = LongRunningService.class.getSimpleName();

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
            public void run() {  //这里面执行具体的操作逻辑 ，这里模拟了网络请求
                try {
                    Thread.sleep(5 * 1000);
                    Log.d(TAG, "我执行了 " + new Date().
                            toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              //  stopSelf(); //停止服务 可以测试是否自动重启服务
            }
        }).start();

        //可以和前台服务的定时任务相结合 ,通过定时广播来启动服务
     /*   AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000; // 这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour; //任务触发的时间间隔

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); //广播，getBroadcast
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//看笔记，唤醒cpu启动广播
       //android4.4 以后定时任务不准， 想要精准可以使用setExact()代替set*/

        // AlarmManager.setWindow  严格准确的定时任务

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "后台定时服务被销毁了.... ");
    }
}
