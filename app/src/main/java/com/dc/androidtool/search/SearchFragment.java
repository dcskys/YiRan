package com.dc.androidtool.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.ViewToolsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索界面
 */
public class SearchFragment  extends Fragment{

    private View view;

    //init viewpager and tablayout
    private ViewPager viewPager;
    private TabLayout tabLayout;

    //init search pager adapter
    private ViewToolsAdapter adapter;

    private List<Fragment> fragmentList;
    private List<String>   tableList;

    //init edit text
    EditText input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view 2种写法view = View.inflate(getActivity(), R.layout.fragment_viewtool, null);
        view = inflater.inflate(R.layout.fragment_search, container, false);
        inintView(view);

        return view;
    }

    private void inintView(View view) {

        //init view pager and tab layout
        viewPager = (ViewPager) view.findViewById(R.id.fragment_search_viewpager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new SearchFragmentBook());
        fragmentList.add(new SearchFragmentMovie());

        tableList = new ArrayList<>();
        tableList.add("书籍");
        tableList.add("电影");

        adapter =  new ViewToolsAdapter(getChildFragmentManager(),fragmentList,tableList);
        viewPager.setAdapter(adapter);


        tabLayout = (TabLayout) view.findViewById(R.id.fragment_search_tablayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //fix tablayout no title bug
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager); //关联viewPager//toolbar的基础上 才会呈现，没有toolbar直接用
            }
        });


        //init edit text and search button
        input = (EditText) view.findViewById(R.id.fragment_search_text);
        ImageButton search = (ImageButton) view.findViewById(R.id.fragment_search_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }

    private void getData() {

        if (!input.getText().toString().equals("")) {
            String inputText = input.getText().toString();
            inputText = inputText.replace(" ", "\b");

            SearchFragmentMovie movieSearchFragment = (SearchFragmentMovie) adapter.getItem(1);
            movieSearchFragment.search(inputText);
            SearchFragmentBook bookSearchFragment = (SearchFragmentBook) adapter.getItem(0);
            bookSearchFragment.search(inputText);
        } else {
            Toast.makeText(getActivity(), "输入为空", Toast.LENGTH_SHORT).show();
        }
    }

}
