package com.dc.androidtool.Alltools;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dc.androidtool.Alltools.learn_git_pic.Learn_git_picActivity;
import com.dc.androidtool.Alltools.learn_location.learn_locationActivity;
import com.dc.androidtool.Alltools.learn_okhttp.learn_okhttpActivity;
import com.dc.androidtool.Alltools.learn_pic.Learn_picActivity;
import com.dc.androidtool.Alltools.learn_provider.ContentProviderActivity;
import com.dc.androidtool.Alltools.learn_srevice.ServiceActivity;
import com.dc.androidtool.Alltools.learn_ui.LearnUIActivity;
import com.dc.androidtool.Alltools.learn_view.LearnSelfViewActivity;
import com.dc.androidtool.Alltools.learn_webView.webviewActivity;
import com.dc.androidtool.Alltools.listen_broadcast.listen_broadcastActivity;
import com.dc.androidtool.Alltools.notification.NotificationActivity;
import com.dc.androidtool.Alltools.save_data.save_dataActivity;
import com.dc.androidtool.Alltools.ui_fullSize.Ui_fullSizeActivity;
import com.dc.androidtool.R;

/**
 * 所有的工具类汇总
 */
public class ToolsFragment extends Fragment implements View.OnClickListener {


    private View view;
    private CardView networkerCardView;
    private Button ui_fullSize;
    private Button listen_broadcast;
    private Button save_data;
    private Button learn_Provider;
    private Button learn_Notification;
    private Button learn_service;
    private Button learn_webview;
    private Button learn_location;
    private Button learn_pic;
    private Button learn_pic_git;
    private Button learn_okhttp;

    private Button learn_selfView;

    private Button LearnUI;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alltoolsfragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        networkerCardView = (CardView) view.findViewById(R.id.tools_network_item);

        ui_fullSize = (Button) view.findViewById(R.id.ui_fullSize);
        listen_broadcast= (Button) view.findViewById(R.id.listen_broadcast);
        save_data= (Button) view.findViewById(R.id.save_data);
        learn_Provider= (Button) view.findViewById(R.id.learn_Provider);
        learn_Notification= (Button) view.findViewById(R.id.learn_Notification);
        learn_service= (Button) view.findViewById(R.id.learn_service);
        learn_webview= (Button) view.findViewById(R.id.learn_webview);
        learn_location= (Button) view.findViewById(R.id.learn_location);

        learn_pic= (Button) view.findViewById(R.id.learn_pic);
        learn_pic_git= (Button) view.findViewById(R.id.learn_pic_git);
        learn_okhttp= (Button) view.findViewById(R.id.learn_okhttp);
        learn_selfView= (Button) view.findViewById(R.id.learn_selfView);
        LearnUI= (Button) view.findViewById(R.id.LearnUI);


        networkerCardView.setOnClickListener(this);
        ui_fullSize.setOnClickListener(this);
        listen_broadcast.setOnClickListener(this);
        save_data.setOnClickListener(this);
        learn_Provider.setOnClickListener(this);
        learn_Notification.setOnClickListener(this);
        learn_service.setOnClickListener(this);
        learn_webview.setOnClickListener(this);
        learn_location.setOnClickListener(this);
        learn_pic.setOnClickListener(this);
        learn_pic_git.setOnClickListener(this);
        learn_okhttp.setOnClickListener(this);
        learn_selfView.setOnClickListener(this);

        LearnUI.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tools_network_item:
                break;

            case R.id.ui_fullSize:  //适配平板和手机
                Intent i = new Intent(getActivity(), Ui_fullSizeActivity.class);
                startActivity(i);
                break;
            case R.id.listen_broadcast://动态广播监听网络变化

                Intent listenIntent = new Intent(getActivity(), listen_broadcastActivity.class);
                startActivity(listenIntent);
                break;

            case R.id.save_data:  //数据储存
                Intent saveIntent = new Intent(getActivity(), save_dataActivity.class);
                startActivity(saveIntent);
                break;

            case R.id.learn_Provider: //内容提供者
                Intent ProviderIntent = new Intent(getActivity(), ContentProviderActivity.class);
                startActivity(ProviderIntent);
                break;

            case R.id.learn_Notification: //通知
                Intent NotificationIntent = new Intent(getActivity(), NotificationActivity.class);
                startActivity(NotificationIntent);
                break;

            case R.id.learn_service: //服务
                Intent ServiceIntent = new Intent(getActivity(), ServiceActivity.class );
                startActivity(ServiceIntent);
                break;

            case R.id.learn_webview: //webView
                Intent webviewIntent = new Intent(getActivity(), webviewActivity.class );
                startActivity(webviewIntent);
                break;

            case R.id.learn_location: //位置定位
                Intent locationIntent = new Intent(getActivity(), learn_locationActivity.class );
                startActivity(locationIntent);
                break;

            case R.id.learn_pic: //自己写的图片选择器
                Intent picIntent = new Intent(getActivity(), Learn_picActivity.class );
                startActivity(picIntent);
                break;

            case R.id.learn_pic_git: //第三方库的使用
                Intent picIntent2 = new Intent(getActivity(), Learn_git_picActivity.class );
                startActivity(picIntent2);
                break;

            case R.id.learn_okhttp: //第三方库的使用
                Intent okhttpIntent = new Intent(getActivity(), learn_okhttpActivity.class );
                startActivity(okhttpIntent);
                break;

            case R.id.learn_selfView: //自定义view的使用
                Intent selfViewIntent = new Intent(getActivity(), LearnSelfViewActivity.class );
                startActivity(selfViewIntent);
                break;

            case R.id.LearnUI: //自定义view的使用
                Intent LearnUIIntent = new Intent(getActivity(), LearnUIActivity.class );
                startActivity(LearnUIIntent);
                break;









        }


    }
}
