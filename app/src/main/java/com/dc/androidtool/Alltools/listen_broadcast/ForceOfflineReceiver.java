package com.dc.androidtool.Alltools.listen_broadcast;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.dc.androidtool.LoginActivity;
import com.dc.androidtool.utils.control.ActivityCollector;

/**
 * 用来实现强制下线的广播
 */
public class ForceOfflineReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {



            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setTitle("警告");
            dialogBuilder.setMessage("你的账户已下线，请重新登录");
            dialogBuilder.setCancelable(false); //不可取消
            dialogBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    ActivityCollector.finishAll(); // 销毁所有活动
                    Intent intent = new Intent(context, LoginActivity.class);
                    //于我们是在广播接收器里启动活动的，因此一定要给Intent 加入FLAG_ACTIVITY_NEW_TASK 这个标志
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//而是无论是否存在，都重新启动新的activity
                    context.startActivity(intent); // 重新启动LoginActivity

                }
            });

        AlertDialog alertDialog = dialogBuilder.create();
        // 需要设置AlertDialog的类型，保证在广播接收器中可以正常弹出
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();





    }
}
