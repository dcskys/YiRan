package com.dc.androidtool.Alltools.learn_srevice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


/*绑定activty*/
public class MyService extends Service {

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {  //开始下载的方法
            Log.d("MyService", "startDownload executed");
        }
        public int getProgress() {   //查看下载的方法
            Log.d("MyService", "getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {  //绑定服务后才能调用这个方法

       return mBinder;
    }


    @Override
    public void onCreate() {  //onCreate()方法会在服务创建的时候调用，
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//onStartCommand()方法会在每次服务启动的时候调用
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
