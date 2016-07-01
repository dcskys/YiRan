package com.dc.androidtool;


import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.androidtool.Alltools.ToolsFragment;
import com.dc.androidtool.DouBanMovie.ViewToolFragment;
import com.dc.androidtool.homedetails.HomeFragment;
import com.dc.androidtool.search.SearchFragment;
import com.dc.androidtool.settings.SettingsFragment;


/*
* 抽屉式菜单总框架
* */
public class MainActivity extends BaseActivity {


    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView; //抽屉
    private Toolbar toolbar;
    //头布局
    private View NavigationViewHead;

    private ImageView head_iv;
    private TextView  head_tv_username;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  //加载布局

        initEvent();

        initDp();//计算当前屏幕的dpi




    }

    private void initDp() {
        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;

        showLog("xdpi is " + xdpi);
        showLog("ydpi is " + ydpi);

    }


    private void initEvent() {

        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("依然的小玩意");
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_layout, new HomeFragment()).commit();

        //set tool bar toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                onItemClickedNavigationMenu(menuItem);
                return true;
            }
        });

        NavigationViewHead = mNavigationView.getHeaderView(0);
        head_iv = (ImageView) NavigationViewHead.findViewById(R.id.header_id_userImage);
        head_tv_username = (TextView) NavigationViewHead.findViewById(R.id.header_id_username);
    }


    private void onItemClickedNavigationMenu(MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.navigation_menu_movie:
                toolbar.setTitle("豆瓣电影");
                fragmentManager.beginTransaction().replace(R.id.content_layout, new ViewToolFragment()).commit();

                break;

            case R.id.navigation_menu_search:
                toolbar.setTitle("搜索");
                fragmentManager.beginTransaction().replace(R.id.content_layout,  new SearchFragment()).commit();
                break;
            case R.id.nav_settings:
                toolbar.setTitle("我的设置");
                fragmentManager.beginTransaction().replace(R.id.content_layout, new SettingsFragment()).commit();

                break;

            case R.id.nav_tools:
                toolbar.setTitle("工具类");
                fragmentManager.beginTransaction().replace(R.id.content_layout, new ToolsFragment()).commit();
                break;

            default:
                break;

        }

        mDrawerLayout.closeDrawer(mNavigationView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // 绑定菜单布局
        getMenuInflater().inflate(R.menu.menu_navigation_view, menu);
        return true;  //ture 菜单才能显示出来
    }


    //菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_settings:
                break;

        }

        return super.onOptionsItemSelected(item);
    }







}
