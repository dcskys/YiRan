package com.dc.androidtool.DouBanMovie;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.listfragment.listview.ListViewFragment;
import com.dc.androidtool.DouBanMovie.listfragment.recycleview.RecycleViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dc on 2016/6/2.
 */
public class ViewToolFragment extends Fragment {

    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewToolsAdapter  adapter;
    private List<Fragment> fragmentList;
    private List<String>   tableList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       //view 2种写法view = View.inflate(getActivity(), R.layout.fragment_viewtool, null);
        view = inflater.inflate(R.layout.fragment_viewtool, container, false);
        inintView(view);

        return view;
    }


    private void inintView( View view) {

        viewPager = (ViewPager) view.findViewById(R.id.fragment_view_viewpager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new ListViewFragment());
        fragmentList.add(new RecycleViewFragment());


        tableList = new ArrayList<>();
        tableList.add("北美票房榜");
        tableList.add("豆瓣TOP250");


        adapter =  new ViewToolsAdapter(getChildFragmentManager(),fragmentList,tableList);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.fragment_view_tablayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED); //填充满屏幕的效果

        //fix tablayout no title bug
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager); //toolbar的基础上 才会呈现，没有toolbar直接用
            }
        });

    }


}
