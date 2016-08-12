package com.dc.androidtool.Alltools.learn_srevice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dc.androidtool.R;

/*前台服务*/
public class MyStageService extends Service {

    private NotificationCompat.Builder notifyBuilder;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();


        notifyBuilder = new NotificationCompat.Builder(this);

        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

            /*设置small icon*/
        notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
            /*设置title*/
        notifyBuilder.setContentTitle("服务");
            /*设置详细文本*/
        notifyBuilder.setContentText("前台服务");
                /*设置点击事件*/
        notifyBuilder.setContentIntent(pendingIntent);

        startForeground(1, notifyBuilder.build());  //设置为前台服务
        //stopForeground
        Log.d("MyService", "onCreate executed");

    }
}
