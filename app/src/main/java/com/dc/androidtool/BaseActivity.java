package com.dc.androidtool;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dc.androidtool.utils.control.ActivityCollector;

public class BaseActivity extends AppCompatActivity {
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        TAG = this.getClass().getSimpleName();//得到是哪一个activity
        Log.d("TAG",TAG);

        ActivityCollector.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this); //调用活动管理器
    }



    /*在任意一个activity 中想要直接退出程序只要调用这句话可以了
    *
    *   ActivityCollector.finishAll();*/
}
