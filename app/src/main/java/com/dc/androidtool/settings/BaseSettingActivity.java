package com.dc.androidtool.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dc.androidtool.R;

public class BaseSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_setting);
    }
}
