package com.dc.androidtool.Alltools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dc.androidtool.R;

/**
 * 所有的工具类汇总
 */
public class ToolsFragment extends Fragment implements View.OnClickListener {


    private View view;
    private CardView networkerCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alltoolsfragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        networkerCardView = (CardView) view.findViewById(R.id.tools_network_item);

        networkerCardView.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tools_network_item:
                break;

        }


    }
}
