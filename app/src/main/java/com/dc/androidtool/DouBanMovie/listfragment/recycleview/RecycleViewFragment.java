package com.dc.androidtool.DouBanMovie.listfragment.recycleview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dc.androidtool.MyApplication;
import com.dc.androidtool.R;
import com.dc.androidtool.DouBanMovie.entity.MovieItemData;
import com.dc.androidtool.utils.NetWorkUtil;
import com.dc.androidtool.utils.cache.TopMovieCache;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 豆瓣api 实现   TOP250
 */
public class RecycleViewFragment extends Fragment {

    private View view;

    private MovieRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;

    private int curPage = 0; //标识用来翻页

    private List<MovieItemData.SubjectsBean> list;  //呈现的 应该是 MovieItemData 中subjects数组


    private static String top250Url = "https://api.douban.com/v2/movie/top250?start=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //view 2种写法view = View.inflate(getActivity(), R.layout.fragment_viewtool, null);
        view = inflater.inflate(R.layout.listfragment_recycleviewfragment, container, false);
        inintView(view);

        //init when begin fragment  启动fragment自动刷新
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadData(0);
            }
        });


        return view;
    }


    private void inintView(View view) {

        list =  new ArrayList<>();

        //init recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_movie_top250_recycler);

        manager = new LinearLayoutManager(getActivity());
        adapter = new MovieRecyclerAdapter(getActivity(),list);
        recyclerView.setLayoutManager(manager);  ///设置布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //设置Item增加、移除动画
        recyclerView.setAdapter(adapter);

        //init swipe refresh layout 下拉刷新布局
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_movie_top250_swipe);
        //设置进度圈的背景色
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE); //进度圈大小

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {  //刷新

                loadData(0);

            }
        });

        //下拉加载更多功能
        inintEvent();

    }

    private void inintEvent() {

        //滑动监听
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;   //用来标记是否向最后一个滑动

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                   //当不滚动的时候
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                  // 获取最后一个完全显示的item的postion
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    //判断是否滚动到底部 且横向的
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多  的代码

                        if (!swipeRefreshLayout.isRefreshing()) {
                              if (curPage>260){
                                  Toast.makeText(getActivity(),"加载到底了",Toast.LENGTH_LONG).show();
                              }else{
                                  swipeRefreshLayout.setRefreshing(true);  //这里直接使用上啦的效果
                                  loadData(curPage+20);
                              }

                        }
                    }
                }

            }
                   //dx 表示横向    dy 表示纵向
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    isSlidingToLast = true;

                } else {
                    isSlidingToLast = false;
                }
            }
        });


    }


    private void loadData(final int page) {

        if (NetWorkUtil.isNetWorkConnected(getActivity())) {//网络可用

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, top250Url +page, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if(page == 0) {         //当加载最新界面时，清空所有集合，重新加载
                            list.clear();
                            TopMovieCache.getInstance(getActivity()).clearAllCache();//清除数据库缓存
                        }
                        curPage = page;   //更新一下当前页
                        //将解析的json 数据 s 添加到集合中去
                        addData(new Gson().fromJson(s, MovieItemData.class));
                        TopMovieCache.getInstance(getActivity()).addResultCache(s, page);//缓存到数据库

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
        }else {//没有网络时
            if (page == 0) {
                list.clear();
                Toast.makeText(getActivity(),"没网络当前为缓存数据",Toast.LENGTH_LONG).show();
            }
            curPage = page;   //更新一下当前页
            Toast.makeText(getActivity(),"没网络当前为缓存数据",Toast.LENGTH_LONG).show();
            list.addAll( TopMovieCache.getInstance(getActivity()).getCacheByPage(page));
            adapter.notifyDataSetChanged();

            swipeRefreshLayout.setRefreshing(false);

        }
    }

    private void addData(MovieItemData movieItemData) {
            //取出实体类中的数组
        for (MovieItemData.SubjectsBean subjectsBean : movieItemData.getSubjects()) {

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
