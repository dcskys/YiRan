package com.dc.androidtool.DouBanMovie.listfragment.listview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dc.androidtool.MyApplication;
import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.entity.MovieListItemData;
import com.dc.androidtool.utils.NetWorkUtil;
import com.dc.androidtool.utils.cache.NorthAmericaCache;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ListView实现  北美票房榜
 */
public class ListViewFragment extends Fragment {

    private static String americaUrl = "https://api.douban.com/v2/movie/us_box";

    private View view;

    private MovieListViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;  //谷歌的下拉刷新控件
    private ListView listView;

    private List<MovieListItemData.SubjectsBean> list;  //呈现的 应该是 MovieListItemData 中subjects数组


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //view 2种写法view = View.inflate(getActivity(), R.layout.fragment_viewtool, null);
        view = inflater.inflate(R.layout.listfragment_listviewfragment, container, false);

        inintView(view);
        //init when begin fragment  启动fragment自动刷新
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });

        return view;
    }


    private void inintView(View view) {


        list =  new ArrayList<>();

        //init listView
        listView = (ListView) view.findViewById(R.id.fragment_movie_america_listview);
        adapter = new MovieListViewAdapter(list,getActivity());
        listView.setAdapter(adapter);

        //init swipe refresh layout 下拉刷新布局
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_movie_america_swipe);
        //设置进度圈的背景色
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE); //进度圈大小

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  //刷新

                loadData();

            }
        });



    }

    private void loadData() {

        if (NetWorkUtil.isNetWorkConnected(getActivity())) {//网络可用
            StringRequest stringRequest = new StringRequest
                    (Request.Method.GET, americaUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            list.clear();
                            NorthAmericaCache.getInstance(getActivity()).clearAllCache();//清除数据库缓存

                            //将解析的json 数据 s 添加到集合中去
                            addData(new Gson().fromJson(s, MovieListItemData.class));
                            NorthAmericaCache.getInstance(getActivity()).addResultCache(s, 0);//缓存到数据库

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(getActivity(), "刷新出错", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });

            stringRequest.setTag("GET");
            MyApplication.getRequestQueue().add(stringRequest);

        }else { //无网读取缓存
                list.clear();
                Toast.makeText(getActivity(),"没网络当前为缓存数据",Toast.LENGTH_LONG).show();

            list.addAll(NorthAmericaCache.getInstance(getActivity()).getCacheByPage(0));
            adapter.notifyDataSetChanged();

            swipeRefreshLayout.setRefreshing(false);

        }
    }

    private void addData(MovieListItemData movieListItemData) {

        //取出实体类中的数组
        for (MovieListItemData.SubjectsBean subjectsBean : movieListItemData.getSubjects()) {

            if (!list.contains(subjectsBean)) {
                list.add(subjectsBean);
            }
        }
        adapter.notifyDataSetChanged();  //刷新适配器
        swipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyApplication.getRequestQueue().cancelAll("GET"); //取消请求
    }


}
