package com.dc.androidtool.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dc.androidtool.R;

/**
 * 个人设置界面
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

       private  View view;
       private LinearLayout base_setting;

    public static final String  ACTION = "com.dc.androidtool.settings.BaseSettingActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_setingsfragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        base_setting = (LinearLayout) view.findViewById(R.id.base_setting);

        base_setting.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.base_setting:

                 //启动一个带有附加信息的隐式Intent
                Intent i = new Intent(ACTION);
                i.addCategory("com.dc.androidtool.MY_CATE");
                startActivity(i);

                break;


        }

    }
}
