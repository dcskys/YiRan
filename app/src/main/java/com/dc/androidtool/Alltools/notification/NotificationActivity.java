package com.dc.androidtool.Alltools.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;


/**/
public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    private Button sendNotice;
    private NotificationManager manager;
    private NotificationCompat.Builder notifyBuilder;
    private PendingIntent pendingIntent;

    public static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
    }

    private void initView() {

        sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);

        //在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(this,NotificationActivity.class);
        resultIntent.putExtra("data","这是通知的内容");
        //设置通知的点击事件（pendingIntent延时启动的intent）
        pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

    }

    int i = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notice:


                i++;
                showLog(i+"");

                notifyBuilder = new NotificationCompat.Builder(this);
            /*设置small icon*/
                notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
            /*设置title*/
                notifyBuilder.setContentTitle("通知");
            /*设置详细文本*/
                notifyBuilder.setContentText("Hello world"+i);
                /*设置点击事件*/
                notifyBuilder.setContentIntent(pendingIntent);


                manager.notify(NOTIFICATION_ID, notifyBuilder.build());
                break;
            //mNotificationManager.cancel(id);取消通知根据id
        }

    }
}
