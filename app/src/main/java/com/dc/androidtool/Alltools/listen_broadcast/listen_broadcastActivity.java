package com.dc.androidtool.Alltools.listen_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dc.androidtool.BaseActivity;
import com.dc.androidtool.R;

/*动态监听网络状态变化*/
public class listen_broadcastActivity extends BaseActivity implements View.OnClickListener {

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    private Button forceOffline ;  //用来实现强制下线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_broadcast);


        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);


        forceOffline = (Button) findViewById(R.id.force_offline);
        forceOffline.setOnClickListener(this);

    }



    //注销广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.force_offline:    //强制下线
                 //通过广播可以实现 不依赖任何界面 在任何地方实现强制下线
                showLog("点击实现");
                Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
                sendBroadcast(intent);
                break;
        }

    }


    //动态的内部类广播 这样每当网络状态发生变化时，onReceive()方法就会得到执行，
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
         showToast("网络状态发生变化");
            //过getSystemService()方法得到了ConnectivityManager 的
            //实例，这是一个系统服务类，专门用于管理网络连接的
            ConnectivityManager connectionManager = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()) {
                showToast("网络是可用的");
            } else {
                showToast("网络不可用");
            }
        }
    }

}
