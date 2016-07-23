package com.dc.androidtool.Alltools.learn_srevice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

public class ServiceActivity extends BaseActivity implements View.OnClickListener, ServiceConnection {

    private Button startService;
    private Button stopService;

    private Button bindService;
    private Button unbindService;

    private Button stage_service;

    private Button Timer_service;


    private MyService.DownloadBinder downloadBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        initView();

        initEvent();
    }

    private void initView() {
        startService = (Button) findViewById(R.id.start_Service);
        stopService = (Button) findViewById(R.id.stop_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        bindService = (Button) findViewById(R.id.bind_service);
        unbindService = (Button) findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);

        stage_service = (Button) findViewById(R.id.stage_service);
        stage_service.setOnClickListener(this);

        Timer_service = (Button) findViewById(R.id.Timer_service);
        Timer_service.setOnClickListener(this);



    }

    private void initEvent() {
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_Service:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent); // 启动服务
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent); // 停止服务

                /*那服务有没有什么办法让自已停止下来呢？
                只需要在MyService 的任何一个位置调用stopSelf()方法就能让这个服务停止下*/
                break;

            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, this, BIND_AUTO_CREATE); // 绑定服务
                break;
            case R.id.unbind_service:
                unbindService(this); // 解绑服务
                break;

            case R.id.stage_service:
                Intent stageIntent = new Intent(this, MyStageService.class);
                startService(stageIntent); // 启动前台服务
                break;

            case R.id.Timer_service:
                Intent TimerIntent = new Intent(this, LongRunningService.class);
                startService(TimerIntent); // 启动长期的后台定时任务

                break;

            default:
                break;
        }

    }


    //第2个参数不用this 的写法
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();  //调用IBinder中方法
            downloadBinder.getProgress();
        }
    };


    /*绑定成功调用*/
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        downloadBinder = (MyService.DownloadBinder) service;
        downloadBinder.startDownload();  //调用bundle中方法
        downloadBinder.getProgress();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
