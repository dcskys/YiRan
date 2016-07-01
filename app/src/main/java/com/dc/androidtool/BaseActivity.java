package com.dc.androidtool;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.dc.androidtool.utils.Logger;
import com.dc.androidtool.utils.ToastUtils;
import com.dc.androidtool.utils.control.ActivityCollector;

public class BaseActivity extends AppCompatActivity {
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//不在活动中显示标题栏，（如果不设置主题的话可以用）

        setContentView(R.layout.activity_base);
        TAG = this.getClass().getSimpleName();//得到是哪一个activity
        Log.d("TAG",TAG);

        ActivityCollector.addActivity(this);
    }


    protected void showToast(String msg) {

        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    protected void showLog(String msg) {

        Logger.show(TAG, msg);
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
