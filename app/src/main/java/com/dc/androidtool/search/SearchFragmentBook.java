package com.dc.androidtool.search;

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
import com.dc.androidtool.search.entity.BookItemData;
import com.dc.androidtool.utils.control.GsonRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索书籍界面呈现
 */
public class SearchFragmentBook extends Fragment {

    private View view;
    private static String bookUrl;
    
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager manager;
    private BookRecyclerAdapter adapter;

    private RecyclerView recyclerView;


    private int curPage = 0; //标识用来翻页

    private List<BookItemData.BooksBean> list;  //呈现的 应该是 MovieItemData 中subjects数组


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view 2种写法view = View.inflate(getActivity(), R.layout.fragment_viewtool, null);
        view = inflater.inflate(R.layout.fragment_search_book, container, false);
        inintView(view);

        return view;
    }

    private void inintView(View view) {

        list =  new ArrayList<>();

        //init recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_search_book_recycler);
        manager = new LinearLayoutManager(getActivity());
        adapter = new BookRecyclerAdapter(getActivity(),list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        //init swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_search_book_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (bookUrl != null) {
                    loadData(0);
                } else {
                    Toast.makeText(getActivity(), "还没有要搜索的耶", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
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
                            if (curPage>500){ //不准确
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

    public void search(String bookName) {
        bookUrl = "https://api.douban.com/v2/book/search?q=" + bookName + "&start=";
        swipeRefreshLayout.setRefreshing(true);
        loadData(0);
    }

    //加载界面
    private void loadData(final int page) {

        /*使用自定义的类进行加载*/
        GsonRequest<BookItemData> gsonRequest = new GsonRequest<BookItemData>(bookUrl + page,
                BookItemData.class, new Response.Listener<BookItemData>() {
            @Override
            public void onResponse(BookItemData bookItemData) {

                if(page == 0) {         //当加载最新界面时，清空所有集合，重新加载
                    list.clear();
                }

                curPage = page;   //更新一下当前页

                addData(bookItemData);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();

                swipeRefreshLayout.setRefreshing(false);
            }
        }
        );
        //StringRequest 来获取数据
        /*StringRequest stringRequest = new StringRequest
                (Request.Method.GET, bookUrl+page, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        if(page == 0) {         //当加载最新界面时，清空所有集合，重新加载
                            list.clear();
                        }

                        curPage = page;   //更新一下当前页

                        //将解析的json 数据 s 添加到集合中去
                        addData(new Gson().fromJson(s, BookItemData.class));

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getActivity(), "网络出错", Toast.LENGTH_SHORT).show();

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        stringRequest.setTag("GET");

        MyApplication.getRequestQueue().add(stringRequest);*/

        gsonRequest.setTag("GET");
        MyApplication.getRequestQueue().add(gsonRequest);
    }

    private void addData(BookItemData bookItemData) {
        //取出实体类中的数组
        for (BookItemData.BooksBean booksBean : bookItemData.getBooks()) {

            if (!list.contains(booksBean)) {
                list.add(booksBean);
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
