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

import com.dc.androidtool.Alltools.ui_fullSize.Ui_fullSizeActivity;
import com.dc.androidtool.R;

/**
 * 所有的工具类汇总
 */
public class ToolsFragment extends Fragment implements View.OnClickListener {


    private View view;
    private CardView networkerCardView;
    private Button ui_fullSize;

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

        networkerCardView.setOnClickListener(this);
        ui_fullSize.setOnClickListener(this);


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

        }


    }
}
