package com.dc.androidtool.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dc.androidtool.R;

/**
 * 个人设置界面
 */
public class SettingsFragment extends Fragment{

       private  View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_setingsfragment, container, false);

        return view;
    }
}
